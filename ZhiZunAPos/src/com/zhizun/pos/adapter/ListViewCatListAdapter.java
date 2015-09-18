package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.InterestPri;

/**
 * 兴趣爱好ListViewAdapter
 */
public class ListViewCatListAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<InterestPri> listItems; // 数据集合
	private int mIndex = -1;

	public ListViewCatListAdapter(Context context, List<InterestPri> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;

	}

	public void setViewBackGround(int index) {
		this.mIndex = index;
	}

	public int getCount() {
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
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.myepei_babyinfo_catlist_item, null);

			holder.tv_myepei_babyinfo_catlist_item_content = (TextView) convertView
					.findViewById(R.id.tv_myepei_babyinfo_catlist_item_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		InterestPri interestPri = listItems.get(position);

		if (null != interestPri.getCatName()
				&& !interestPri.getCatName().equals("")) {
			holder.tv_myepei_babyinfo_catlist_item_content.setText(interestPri
					.getCatName());
		} else {
			holder.tv_myepei_babyinfo_catlist_item_content.setText("");
		}
		if (position == mIndex) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.parseColor(context
					.getResources().getString(R.color.grays_zan_background)));

		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_myepei_babyinfo_catlist_item_content;
	}

}
