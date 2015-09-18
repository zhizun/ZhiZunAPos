package com.zhizun.pos.adapter;

import java.util.List;

import com.ch.epw.utils.DateTools;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyepePrizeBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 首页营销 ListViewAdapter
 */
public class ListViewMarketingAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<MyepePrizeBean> listItems; // 数据集合

	public ListViewMarketingAdapter(Context context,
			List<MyepePrizeBean> listItems) {
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
			convertView = View.inflate(context, R.layout.list_marketing_item,
					null);

			holder.iv_list_common_title_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_common_title_logo);
			holder.tv_list_common_title_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_orgname);
			holder.tv_list_common_title = (TextView) convertView
					.findViewById(R.id.tv_list_common_title);
			holder.tv_list_common_title_time = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MyepePrizeBean myepePrizeBean = listItems.get(position);
		String imgUrlString = myepePrizeBean.getEvtThumbPicPath();
		ImageUtils.showImageAsCommonPic(holder.iv_list_common_title_logo, imgUrlString);
		//showPicture(imgUrlString, holder.iv_list_common_title_logo, options);
		holder.tv_list_common_title_orgname.setText("来自"
				+ myepePrizeBean.getCreateOrgName());
		holder.tv_list_common_title_time
				.setText(myepePrizeBean.getStartTime());
		holder.tv_list_common_title.setText(myepePrizeBean.getTitle());
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_common_title_logo;
		TextView tv_list_common_title_orgname;
		TextView tv_list_common_title_time;
		TextView tv_list_common_title;
	}

}
