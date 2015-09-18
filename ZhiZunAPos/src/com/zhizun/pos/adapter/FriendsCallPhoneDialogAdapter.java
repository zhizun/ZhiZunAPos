package com.zhizun.pos.adapter;

import java.util.List;

import com.zhizun.pos.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendsCallPhoneDialogAdapter extends BaseAdapter {
	private Context context;
	private List<String> phoneList;
	public FriendsCallPhoneDialogAdapter(Context context,List<String> phoneList) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.phoneList=phoneList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return phoneList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return phoneList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.call_phone_listview_item, null);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String phone=phoneList.get(position);
		holder.tv_phone.setText(phone);

		return convertView;
	}
	static class ViewHolder {
		TextView tv_phone;

	}
}
