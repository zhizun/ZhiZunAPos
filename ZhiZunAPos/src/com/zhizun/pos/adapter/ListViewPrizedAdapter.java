package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyepePrizeBean;

public class ListViewPrizedAdapter extends MyBaseAdapter {
	private Context context;
	private List<MyepePrizeBean> listItems;

	public ListViewPrizedAdapter(Context context,List<MyepePrizeBean> listItems){
		super();
		this.context=context;
		this.listItems=listItems;
	}
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_prized_event_item,
					null);
			holder.iv_list_my_prized_item_logo = (TextView) convertView
					.findViewById(R.id.iv_list_my_prized_item_logo);
			holder.tv_list_my_notice_item_title = (TextView) convertView
					.findViewById(R.id.tv_list_my_notice_item_title);
			holder.tv_list_my_prized_item_time=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_time);
			holder.tv_list_my_prized_item_con=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_con);
			holder.tv_list_my_prized_item_num=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyepePrizeBean prizeBean=listItems.get(position);
		if (prizeBean.getStatus().equals("0")) {
			holder.iv_list_my_prized_item_logo.setText("参与中");
			holder.iv_list_my_prized_item_logo.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.textview_border));
		}else {
			holder.iv_list_my_prized_item_logo.setText("过期");
			holder.iv_list_my_prized_item_logo.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.textview_border_gray));
			holder.iv_list_my_prized_item_logo.setTextColor(context.getResources().getColor(R.color.white));
		}
		if (null!=prizeBean.getTitle()&& !prizeBean.getTitle().equals("")) {
			holder.tv_list_my_notice_item_title.setText(prizeBean.getTitle());
		}
		if (null!=prizeBean.getStartTime()&& !prizeBean.getStartTime().equals("")) {
			holder.tv_list_my_prized_item_time.setText(prizeBean.getStartTime());
		}
		if (null!=prizeBean.getUserShareNum()&& !prizeBean.getUserShareNum().equals("")) {
				holder.tv_list_my_prized_item_con.setText("已分享："+prizeBean.getUserShareNum());
		}
		if (null!=prizeBean.getUserValidShareNum()&& !prizeBean.getUserValidShareNum().equals("")) {
			holder.tv_list_my_prized_item_num.setText("有效分享："+prizeBean.getUserValidShareNum());
		}

		return convertView;
	}
	static class ViewHolder {

		TextView iv_list_my_prized_item_logo;//
		TextView tv_list_my_notice_item_title;
		TextView tv_list_my_prized_item_time;
		TextView tv_list_my_prized_item_con;
		TextView tv_list_my_prized_item_num;
	}
}
