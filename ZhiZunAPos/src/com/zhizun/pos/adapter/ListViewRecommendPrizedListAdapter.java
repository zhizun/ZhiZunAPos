package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyRecommendationPicker;

public class ListViewRecommendPrizedListAdapter extends MyBaseAdapter{
	private Context context;// 运行上下文
	private List<MyRecommendationPicker> listItems; // 数据集合
	public ListViewRecommendPrizedListAdapter(Context context,
			List<MyRecommendationPicker> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
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
			convertView = View.inflate(context, R.layout.list_my_prized_recommend_picker_item,
					null);
			holder.tv_picker_time = (TextView) convertView
					.findViewById(R.id.tv_picker_time);
			holder.tv_picker = (TextView) convertView
					.findViewById(R.id.tv_picker);
			holder.tv_isdeal = (TextView) convertView
					.findViewById(R.id.tv_isdeal);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyRecommendationPicker picker = listItems.get(position);
		if (null!=picker.getReferralTime()&& !picker.getReferralTime().equals("")) {
			holder.tv_picker_time.setText(picker.getReferralTimeForDisplay());
		}
			holder.tv_picker.setText(picker.getReferralPhone());
			
			holder.tv_isdeal.setText(("1".equals(picker.getIsVerify()) ? "是":"否"));
		
		return convertView;
	}
	static class ViewHolder {

		TextView tv_picker_time;
		TextView tv_picker;
		TextView tv_isdeal;
	}

}
