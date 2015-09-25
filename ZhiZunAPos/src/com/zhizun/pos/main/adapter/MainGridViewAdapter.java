package com.zhizun.pos.main.adapter;

import com.zhizun.pos.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGridViewAdapter extends BaseAdapter {
	private Context context;
	private String[] title;
	private Integer[] imgs;
	
	
	public MainGridViewAdapter(Context context,String[] title,Integer[] imgs) {
		super();
		this.context = context;
		this.title=title;
		this.imgs=imgs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return title[position];
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
			convertView = View.inflate(context, R.layout.zhizun_main_gridview,
					null);
			holder.main_text=(TextView) convertView.findViewById(R.id.main_text);
			holder.main_image=(ImageView) convertView.findViewById(R.id.main_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.main_text.setText(title[position]);
		holder.main_image.setImageResource(imgs[position]);
		return convertView;
	}
	static class ViewHolder {
		ImageView main_image;
		TextView main_text;
	}
}
