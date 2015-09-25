package com.zhizun.pos.main.listview;

import com.zhizun.pos.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater layoutInflater;
	String[][] cities;
	public int foodpoition;

	public SubAdapter(Context context, String[][] cities,int position) {
		this.context = context;
		this.cities = cities;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.foodpoition = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cities[foodpoition].length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		final int location=position;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.zhiun_sublist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(cities[foodpoition][position]);
		viewHolder.textView.setTextColor(Color.BLACK);
		
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
