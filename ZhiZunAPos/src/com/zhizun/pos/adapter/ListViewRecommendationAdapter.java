package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyepePrizeBean;

public class ListViewRecommendationAdapter extends MyBaseAdapter {
	private Context context;
	private List<MyepePrizeBean> listItems;
	private String type;

	public ListViewRecommendationAdapter(Context context,List<MyepePrizeBean> listItems,String type){
		super();
		this.context=context;
		this.listItems=listItems;
		this.type=type;
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
			convertView = View.inflate(context, R.layout.list_recommandation_event_item,
					null);
			holder.rl_have=(RelativeLayout) convertView.findViewById(R.id.rl_have);
			holder.iv_list_my_prized_item_logo = (TextView) convertView
					.findViewById(R.id.iv_list_my_prized_item_logo);
			holder.tv_list_my_notice_item_title = (TextView) convertView
					.findViewById(R.id.tv_list_my_notice_item_title);
			holder.tv_list_my_prized_item_con=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_con);
			holder.tv_list_my_prized_item_num=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_num);
			holder.tv_list_my_prized_item_have=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_have);
			holder.tv_list_my_prized_item_have_to_receive=(TextView) convertView
					.findViewById(R.id.tv_list_my_prized_item_have_to_receive);
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
		int userRecommendNum=0;
		try {
			userRecommendNum = Integer.valueOf(prizeBean.getUserRecommendNum());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		int perAwardNum=1;
		try {
			perAwardNum = Integer.valueOf(prizeBean.getPerAwardNum());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (type.equals(Constant.RECOMMEND_AWARD)) {
			holder.tv_list_my_prized_item_con.setText("已成功推荐："+userRecommendNum);
			if (userRecommendNum==0) {			
				holder.tv_list_my_prized_item_num.setText("已获得：无");
			} else{			
				if (perAwardNum==0) {
					holder.tv_list_my_prized_item_num.setText("已获得："+userRecommendNum+"*"+prizeBean.getIntroducerBonus());
				}else {
					int gotPrize = (userRecommendNum < perAwardNum) ? userRecommendNum :perAwardNum;			
					holder.tv_list_my_prized_item_num.setText("已获得："+gotPrize+"*"+prizeBean.getIntroducerBonus());
				}
			}
			
			String userPickNum=prizeBean.getUserPickNum();
			if (null!=userPickNum &&!userPickNum.equals("0") && !userPickNum.equals("")) {
				holder.rl_have.setVisibility(View.VISIBLE);
				holder.tv_list_my_prized_item_have.setText("已领取："+userPickNum);
				holder.tv_list_my_prized_item_have_to_receive.setText("可获得："+userPickNum+"*"+prizeBean.getReferralBonus());
			}else {
				holder.rl_have.setVisibility(View.GONE);
			}
		}else {
			holder.tv_list_my_prized_item_con.setText(prizeBean.getStartTime());
			holder.tv_list_my_prized_item_num.setText("已领取："+prizeBean.getUserPickNum());
		}
		
		return convertView;
	}
	static class ViewHolder {
		RelativeLayout rl_have;
		TextView iv_list_my_prized_item_logo;//
		TextView tv_list_my_notice_item_title;
		TextView tv_list_my_prized_item_con;
		TextView tv_list_my_prized_item_num;
		TextView tv_list_my_prized_item_have;
		TextView tv_list_my_prized_item_have_to_receive;
	}
}
