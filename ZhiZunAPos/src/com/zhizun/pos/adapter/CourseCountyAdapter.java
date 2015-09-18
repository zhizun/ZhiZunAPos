package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.R;
import com.zhizun.pos.activity.CourseApply;
import com.zhizun.pos.adapter.CourseListViewAdapter.ViewHolder;
import com.zhizun.pos.bean.CourseListItemList;
import com.zhizun.pos.bean.CourseRegions;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 区域列表
 * @author lilinzhong
 *
 * 2015-6-29上午10:56:33
 */
public class CourseCountyAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<CourseRegions> listItems; // 数据集合
	
	public CourseCountyAdapter(Context context,
			ArrayList<CourseRegions> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
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
			convertView = View.inflate(context, R.layout.course_county_list_item,
					null);
			holder.tv_text=(TextView) convertView.findViewById(R.id.tv_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final CourseRegions courseListItemList = listItems.get(position);
		
		if (null!=courseListItemList.getValue()&&!courseListItemList.getValue().equals("")) {
			  holder.tv_text.setText(courseListItemList.getValue());
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_text;
	}
}
