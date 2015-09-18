package com.zhizun.pos.activity;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ch.epw.helper.MyPullToRefreshListHelper;
import com.ch.epw.task.FriendsCommeTask;
import com.ch.epw.task.FriendsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.LocationUtils;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsCommentAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CircleofFriendsBean;
import com.zhizun.pos.bean.FriendsCommentBean;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.PhoneContactBean;
import com.zhizun.pos.bean.UserLogin;

/**
 * 朋友圈
 * 
 * @author lilinzhong
 * 
 *         2015-8-18上午10:33:08
 */
public class FriendsCircleActivity extends BaseActivity implements
		OnClickListener {

	public static final String ACTION_RELOAD_COMMENT_LIST = FriendsCircleActivity.class
			.getName() + ".RELOAD_DATA";

	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	private ArrayList<PhoneContactBean> phoneContactList = new ArrayList<PhoneContactBean>();
	private TitleBarView titleBarView;
	private LinearLayout ll_getMyComment;
	private LinearLayout ll_invite_friends;
	private ArrayList<FriendsCommentListBean> listItems;
	private String latitude = "";
	private String longitude = "";
	private LinearLayout ll_iwant_comment;
	private SharedPreferences mySharedPreferences;
	private TextView tv_zanwu;
	private LinearLayout ll_friends_no_context;
	private RelativeLayout rl_friends_listview;
	private String friendsTag = "8";// 默认值，用来比较系统osAllowReadContacts值是否改变
	private String tagString = "";
	private RelativeLayout tv_more_friends;
	private String osAllowReadContacts;
	private String isFirstTimeAskUser;
	private Button bt_fabiao;
	private String userId;

	private MyPullToRefreshListHelper mvHelper;

	// 定义重新刷新数据通知
	private BroadcastReceiver reloadReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (FriendsCircleActivity.ACTION_RELOAD_COMMENT_LIST.equals(intent
					.getAction())) {
				if (mvHelper != null) {
					mvHelper.manualForceRefresh();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_circle);
		// 注册刷新页面通知
		IntentFilter reloadDataFilter = new IntentFilter(FriendsCircleActivity.ACTION_RELOAD_COMMENT_LIST);
		registerReceiver(reloadReceiver, reloadDataFilter);

		initView();
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin != null && userLogin.getUserInfo() != null) {
			userId = userLogin.getUserInfo().getUserId();
		}
	}

	public void getPhoneContacts() {
		boolean status = readSystemContacts();
		if (!status) {// 操作系统级别不允许读取通讯录，弹出对话框2，读取附近机构评价
			uploadPhoneContacts(false, ""); // 用户不允许读取通讯录，调用接口做记录
			Intent intent = new Intent(this,
					FriendsStartErrorActivityDialog.class);
			startActivityForResult(intent, 2);
			osAllowReadContacts="0";
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("osAllowReadContacts", "0");
			editor.commit();
		} else {// 用户允许读取通讯录，上传通讯录，读取朋友圈评价
			tv_zanwu.setVisibility(View.GONE);
			tv_more_friends.setVisibility(View.GONE);
			uploadPhoneContacts();
			osAllowReadContacts="1";
			// 系统级读取通讯录允许，改变osAllowReadContacts值为1
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putString("osAllowReadContacts", "1");
			editor.commit();
		}
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_circle_of_friends);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_my_circle_of_friends);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(null);
		tv_zanwu = (TextView) findViewById(R.id.tv_zanwu);
		tv_more_friends = (RelativeLayout) findViewById(R.id.tv_more_friends);
		tv_more_friends.setOnClickListener(this);
		ll_getMyComment = (LinearLayout) findViewById(R.id.ll_getMyComment);
		ll_invite_friends = (LinearLayout) findViewById(R.id.ll_invite_friends);
		ll_iwant_comment = (LinearLayout) findViewById(R.id.ll_iwant_comment);
		rl_friends_listview = (RelativeLayout) findViewById(R.id.rl_friends_listview);
		bt_fabiao = (Button) findViewById(R.id.bt_fabiao);
		bt_fabiao.setOnClickListener(this);
		mPullListView = (PullToRefreshListView) findViewById(R.id.ll_circle_of_friends);

		ll_friends_no_context = (LinearLayout) findViewById(R.id.ll_friends_no_context);

		ll_getMyComment.setOnClickListener(this);
		ll_iwant_comment.setOnClickListener(this);
		ll_invite_friends.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mySharedPreferences = getSharedPreferences("UserSelect",
				FriendsCircleActivity.MODE_PRIVATE);
		isFirstTimeAskUser = mySharedPreferences.getString(
				"isFirstTimeAskUser", "1");
		osAllowReadContacts = mySharedPreferences.getString(
				"osAllowReadContacts", "-1");
		if (!friendsTag.equals(osAllowReadContacts)) {
			tv_more_friends.setVisibility(View.GONE);
			tv_zanwu.setVisibility(View.GONE);
			if (osAllowReadContacts.equals("-1")) {// 系统级别状态判断，-1没设置过。1：用户允许，0：用户不允许
				// 1.判断，默认值为-1，用户没设置过，谈是否启用通讯录对话框
				if (isFirstTimeAskUser.equals("1")) {
					Intent intent = new Intent(this,
							FriendsStartUsingActivityDialog.class);
					startActivityForResult(intent, 1);
					SharedPreferences.Editor editor = mySharedPreferences.edit();
					editor.putString("isFirstTimeAskUser", "0");
					editor.commit();
				} else {
					getNearFriends();// 读取附近朋友圈
				}
				friendsTag="-1";
			} else if (osAllowReadContacts.equals("1")) {
				// 读取通讯录并上传
				getPhoneContacts();
				friendsTag="1";
			} else if (osAllowReadContacts.equals("0")) {// 获取附近朋友评价
				getNearFriends();
				friendsTag="0";
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (1 == resultCode && 1 == requestCode) {
			getPhoneContacts();
		} else {
			tv_zanwu.setVisibility(View.GONE);
			tv_more_friends.setVisibility(View.GONE);
			// 用户选择，暂不启用通讯录，读取附近朋友圈
			getNearFriends();
		}
	}
/**
 * 获取附近朋友圈
 */
	public void getNearFriends() {
		BDLocation location = LocationUtils.getLastKnownLocation();
		if (location != null) {// 经纬度必传，所以没有获取到经纬度，就显示空页面
			ll_friends_no_context.setVisibility(View.GONE);
			getFriendsCircle(location, "6");// 6代表调用附近接口
		} else {// 显示暂无内容，显示暂无内容banner条，显示我要发表评论，
			tv_zanwu.setVisibility(View.GONE);
			rl_friends_listview.setVisibility(View.GONE);
			ll_friends_no_context.setVisibility(View.VISIBLE);
			if (!osAllowReadContacts.equals("1")) {
				tv_more_friends.setVisibility(View.VISIBLE);
			}else {
				tv_more_friends.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 获取朋友圈列表
	 * @param location
	 */
	private void getFriendsCircle(BDLocation location, String tag) {
		if (null != location) {
			latitude = String.valueOf(location.getLatitude());
			longitude = String.valueOf(location.getLongitude());
		}
		tagString = tag;
		listItems = new ArrayList<FriendsCommentListBean>();
		// 将Activity、mPullListView、listItems绑定
		mvHelper = new MyPullToRefreshListHelper(this, mPullListView, listItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(20);
		mListView.setAdapter(new FriendsCommentAdapter(this, listItems, "0"));
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		final TaskCallBack getDataCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				FriendsCommentBean friendsCommentBean = (FriendsCommentBean) result;
				if (friendsCommentBean != null && "0".equals(friendsCommentBean.getStatus())) {
					if (!osAllowReadContacts.equals("1")) {
						tv_more_friends.setVisibility(View.VISIBLE);
					}else {
						tv_more_friends.setVisibility(View.GONE);
					}
					mvHelper.setPageCount(friendsCommentBean.getCount());
					if (Integer.parseInt(friendsCommentBean.getCount()) > 0) {// 有数据，显示朋友圈评价
						listItems.addAll(friendsCommentBean
								.getFriendsCommentListBean());
						if (tagString.equals("6")) {//表示附近显示暂无熟人banner条
							tv_zanwu.setVisibility(View.VISIBLE);
						}
					} else {
						mPullListView.setHasData(false);
						if (tagString.equals("6")) {// 附近没有数据，就不显示,也就是显示我要发表评论
							tv_more_friends.setVisibility(View.VISIBLE);
						} else{// 朋友圈没有数据，显示附近
							getNearFriends();
							tv_more_friends.setVisibility(View.GONE);
							tv_zanwu.setVisibility(View.VISIBLE);
						}
					}
				}
				// 必须在每次task执行结束调用mvController.refreshEnd()
				mvHelper.refreshEnd();
				mPullListView.setHasData(true);
			}
		};
		mvHelper.setGetDataCallBack(FriendsCommeTask.class, new String[] {
				latitude, longitude, tag, userId }, getDataCallBack);
		mvHelper.readyForRefresh();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(reloadReceiver);
		super.onDestroy();
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
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				phoneContact.setPhoneNum(StringUtils.StringFilter(phoneNumber));
				phoneContact.setDesplayName(contactName);
				phoneContactList.add(phoneContact);
			}
			phoneCursor.close();
		}
		return isAllow;
	}

	/**
	 * 上传通讯录
	 */
	private void uploadPhoneContacts() {
		StringBuffer json = new StringBuffer("[");
		if (phoneContactList != null && phoneContactList.size() > 0) {
			for (int i = 0; i < phoneContactList.size(); i++) {
				PhoneContactBean phoneContact = phoneContactList.get(i);
				json.append("{\"phoneNumber\":\"" + phoneContact.getPhoneNum()
						+ "\",\"name\":\"" + phoneContact.getDesplayName()
						+ "\"}");
				if (i != phoneContactList.size() - 1) {
					json.append(",");
				}
				System.out.println("姓名：" + phoneContact.getDesplayName());
				System.out.println("电话：" + phoneContact.getPhoneNum());
			}
		}
		json.append("]");
		uploadPhoneContacts(true, json.toString());
	}

	public void uploadPhoneContacts(final boolean userAllowReadContacts,
			String json) {
		new FriendsTask(this, new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {

				CircleofFriendsBean circleofFriendsBean = (CircleofFriendsBean) result;
				if (circleofFriendsBean != null) {
					if (circleofFriendsBean.getStatus().equals("0")) {// 同步成功,
						if (userAllowReadContacts) {
							BDLocation location = LocationUtils
									.getLastKnownLocation();
							getFriendsCircle(location, "0");// 请求朋友圈评价
						}
					} else {
						UIHelper.ToastMessage(FriendsCircleActivity.this,
								"查找朋友失败");
						BDLocation location = LocationUtils
								.getLastKnownLocation();
						getFriendsCircle(location, "0");// 请求朋友圈评价
					}

				}
			}

		}).execute(userAllowReadContacts ? "1" : "0", json);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.ll_getMyComment:// 我的评价
			intent.setClass(this, FriendsMyCommentActivity.class);
			// intent=new Intent(this,FriendsMyCommentActivity.class);
			startActivity(intent);
			break;

		case R.id.bt_fabiao:// 没有数据的时候
			intent.setClass(this, MyCourseCommentEditActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_iwant_comment:// 我要评价
			intent.setClass(this, MyCourseCommentEditActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_invite_friends:// 邀请好友使用
			intent.setClass(this, FriendsInviteActivity.class);
			intent.putExtra("course", "1");// 邀请好友使用分享
			intent.putExtra("commentId", "");
			intent.putExtra("org", "");// 机构名
			startActivity(intent);
			break;
		case R.id.tv_more_friends:// 可以通过通讯录找到更多好友评价
			if (osAllowReadContacts.equals("-1")) {
				intent.setClass(this, FriendsStartUsingActivityDialog.class);
				startActivityForResult(intent, 1);
			} else if (osAllowReadContacts.equals("0")) {
				intent.setClass(this, SetJurisdictionActicity.class);
				startActivity(intent);
			}
			break;
		}
	}
}
