package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.PhoneContactBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class FriendsContactsListViewAdapter extends BaseAdapter implements SectionIndexer{
	private List<PhoneContactBean> list = null;
	private Context mContext;
	private List<Integer> checkPosition=new ArrayList<Integer>();
	Handler handler;
	
	public FriendsContactsListViewAdapter(Context mContext, List<PhoneContactBean> list,Handler handler) {
		this.mContext = mContext;
		this.list = list;
		this.handler=handler;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<PhoneContactBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final PhoneContactBean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.friends_contacts_listview_item, null);
			viewHolder.tvTitle = (CheckBox) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.tv_phone_num=(TextView) view.findViewById(R.id.tv_phone_num);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		PhoneContactBean phoneContactBean=list.get(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setChecked(phoneContactBean.isCheckTag());
		viewHolder.tvTitle.setText(phoneContactBean.getDesplayName());
		viewHolder.tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!checkPosition.contains(position)) {//记录位置，如果不包含这个位置，那说明没有选中，设为true，把位置添加进去
					list.get(position).setCheckTag(true);
					checkPosition.add(position);
				}else {//如果包含这个位置，说明选中，
					list.get(position).setCheckTag(false);
					for (int i = 0; i < checkPosition.size(); i++) {
						if (position==checkPosition.get(i)) {
							checkPosition.remove(i);
						};
					}
				}
				Message msg = Message.obtain();
				msg.arg1 = checkPosition.size();
				handler.sendMessage(msg);
			}
		});
		viewHolder.tv_phone_num.setText(phoneContactBean.getPhoneNum());
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		CheckBox tvTitle;
		TextView tv_phone_num;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	@Override
	public Object[] getSections() {
		return null;
	}
}