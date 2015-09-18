package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.phone.view.CharacterParser;
import com.ch.epw.phone.view.PinyinComparator;
import com.ch.epw.phone.view.SideBar;
import com.ch.epw.phone.view.SideBar.OnTouchingLetterChangedListener;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsContactsListViewAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.PhoneContactBean;

public class FriendsContactsListViewActivity extends BaseActivity {
	private static final String[] PHONES_PROJECTION = new String[] {
		Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private List<PhoneContactBean> phoneContactList = new ArrayList<PhoneContactBean>();
//	private FriendsContactsListViewAdapter adapter;
	private ListView lv_listview;
	private TitleBarView titleBarView;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private FriendsContactsListViewAdapter adapter;
	private TextView tv_yaoqing;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private TextView phone_size;
	private CheckBox title_check_box;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			phone_size.setText(msg.arg1+"/"+phoneContactList.size());
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_contacts_listview);
		showProgressDialog(this, "", getResources().getString(R.string.load_ing));
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		boolean status=readSystemContacts();
		if (!status) {//操作系统级别不允许读取通讯录，弹出对话框2，读取附近机构评价
			Toast.makeText(this, "您已拒绝读取通讯录", Toast.LENGTH_LONG).show();
			}
		closeProgressDialog();
		initView();
	}
	private void initView() {
		
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_activity_friends_phone);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText("选择通讯录好友");
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		//设置右侧触摸监听
				sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
					
					@Override
					public void onTouchingLetterChanged(String s) {
						//该字母首次出现的位置
						int position = adapter.getPositionForSection(s.charAt(0));
						if(position != -1){
							lv_listview.setSelection(position);
						}
						
					}
				});
		lv_listview=(ListView)findViewById(R.id.lv_listview);
		// 根据a-z进行排序源数据
		Collections.sort(phoneContactList, pinyinComparator);
		adapter=new FriendsContactsListViewAdapter(FriendsContactsListViewActivity.this, phoneContactList,handler);
		lv_listview.setAdapter(adapter);
		lv_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Uri smsToUri = Uri.parse("smsto:"+phoneContactList.get(position).getDesplayName());  
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
				intent.putExtra("sms_body", "益培网上有很多熟人评价，能帮助你更好的找到合适的培训班，快来试试吧！"+Constant.courseDownladApp);  
				startActivity(intent);  
			}
		});
		phone_size=(TextView)findViewById(R.id.phone_size);
		phone_size.setText("0/"+phoneContactList.size());
		title_check_box=(CheckBox)findViewById(R.id.title_check_box);
		title_check_box.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (title_check_box.isChecked()) {
					for (int i = 0; i < phoneContactList.size(); i++) {
						phoneContactList.get(i).setCheckTag(true);
					}
					phone_size.setText(phoneContactList.size()+"/"+phoneContactList.size());
				}else {
					for (int i = 0; i < phoneContactList.size(); i++) {
						phoneContactList.get(i).setCheckTag(false);
					}
					phone_size.setText("0/"+phoneContactList.size());
				}
				adapter.notifyDataSetChanged();
			}
		});
		tv_yaoqing=(TextView) findViewById(R.id.tv_yaoqing);//点击邀请加入
		tv_yaoqing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer phoneName = new StringBuffer();
				for (int i = 0; i < phoneContactList.size(); i++) {
					if (phoneContactList.get(i).isCheckTag()) {
						phoneName.append(phoneContactList.get(i).getDesplayName());
						phoneName.append(",");
					}
				}
				Uri smsToUri = Uri.parse("smsto:"+phoneName.toString());  
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);  
				intent.putExtra("sms_body", "益培网上有很多熟人评价，能帮助你更好的找到合适的培训班，快来试试吧！"+Constant.courseDownladApp);  
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 获取手机联系人
	 */
	private boolean readSystemContacts() {
		boolean isAllow = false;
		ContentResolver resolver = this.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				isAllow = true;
				PhoneContactBean phoneContact = new PhoneContactBean();
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				phoneContact.setPhoneNum(StringUtils.StringFilter(phoneNumber));
				phoneContact.setDesplayName(contactName);
				phoneContact.setCheckTag(false);
				//汉字转换成拼音
				String pinyin = characterParser.getSelling(contactName);
				String sortString = pinyin.substring(0, 1).toUpperCase();
				
				// 正则表达式，判断首字母是否是英文字母
				if(sortString.matches("[A-Z]")){
					phoneContact.setSortLetters(sortString.toUpperCase());
				}else{
					phoneContact.setSortLetters("#");
				}
				phoneContactList.add(phoneContact);
			}
			phoneCursor.close();
		}
		return isAllow;
	}

}
