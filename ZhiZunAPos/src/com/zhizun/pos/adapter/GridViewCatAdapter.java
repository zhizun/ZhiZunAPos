package com.zhizun.pos.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.R.raw;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.InterestSec;
import com.zhizun.pos.bean.Photo;

/**
 * GrideView 兴趣爱好Adapter
 */
public class GridViewCatAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<InterestSec> listItems; // 数据集合

	public GridViewCatAdapter(Context context, List<InterestSec> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
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
					R.layout.myepei_babyinfo_catgridview_item, null);

			holder.tv_myepei_babyinfo_catlist_item_content = (TextView) convertView
					.findViewById(R.id.tv_myepei_babyinfo_catlist_item_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		InterestSec interestSec = listItems.get(position);

		if (interestSec.getItemName() != null
				&& !interestSec.getItemName().equals("")) {
			holder.tv_myepei_babyinfo_catlist_item_content.setText(interestSec
					.getItemName());
		} else {
			holder.tv_myepei_babyinfo_catlist_item_content.setText("");
		}

		if (interestSec.getIsSelected()) {
			// convertView.setBackgroundColor(Color.parseColor(context
			// .getResources().getString(R.color.darkblue_3)));
			convertView.setBackgroundResource(R.drawable.bg_babyinfo_cat_press);
			holder.tv_myepei_babyinfo_catlist_item_content
					.setTextColor(Color.WHITE);
		} else {
			// convertView.setBackgroundColor(Color.parseColor(context
			// .getResources().getString(R.color.grays_zan_background)));
			convertView
					.setBackgroundResource(R.drawable.bg_babyinfo_cat_normal);
			holder.tv_myepei_babyinfo_catlist_item_content.setTextColor(Color
					.parseColor(context.getResources().getString(
							R.color.gray_font)));
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_myepei_babyinfo_catlist_item_content;
	}

}
