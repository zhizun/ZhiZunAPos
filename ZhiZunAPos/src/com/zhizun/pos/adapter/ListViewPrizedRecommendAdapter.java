package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyPrizedShareList;
import com.zhizun.pos.bean.RecommendationData;
/**
 * 推荐有奖	推荐详细列表适配器
 * @author 
 *
 */
public class ListViewPrizedRecommendAdapter extends MyBaseAdapter{
	private Context context;// 运行上下文
	private List<RecommendationData> listItems; // 数据集合
	private String type;
	public ListViewPrizedRecommendAdapter(Context context,
			List<RecommendationData> listItems,String type) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.type=type;
	}
	@Override
	public int getCount() {
		if (type.equals(Constant.RECOMMEND_AWARD)) {
			return listItems.size();
		}else {
			return listItems.get(0).getPickerList().size();
		}
	}
	@Override
	public Object getItem(int position) {
		if (type.equals(Constant.RECOMMEND_AWARD)) {
			return listItems.get(position);
		}else {
			return listItems.get(0).getPickerList().get(position);
		}
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
			convertView = View.inflate(context, R.layout.list_my_prized_recommend_item,
					null);
			holder.im_logo = (ImageView) convertView
					.findViewById(R.id.im_logo);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tv_tag = (TextView) convertView
					.findViewById(R.id.tv_tag);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (type.equals(Constant.RECOMMEND_AWARD)) {
		RecommendationData datasList = listItems.get(position);
		int smpType = 10;
		try {
			smpType = Integer.valueOf(datasList.getRecommendation().getSmpType());
		} catch (NumberFormatException e) {
		}
		switch (smpType) {
		case 0:// 微信
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.weixin));
			break;
		case 1:// 微信朋友圈
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.wechat_moments));
			break;
		case 2:// 微信收藏
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.weixincollect));
			break;
		case 3:// QQ
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.qqapp));
			break;
		case 4:// QQ空间
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.qq_zone));
			break;
		case 5:// 新浪微博
			holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.microblog));
			break;
		}
			if (null!=datasList.getRecommendation().getShareTime()&& !datasList.getRecommendation().getShareTime().equals("")) {
				holder.tv_time.setText(datasList.getRecommendation().getShareTimeForDisplay());
			}
			holder.tv_content.setText(datasList.getPickerList().size()+"");
			holder.tv_tag.setText(datasList.getVerifiedCount()+"");
		}else {
			RecommendationData datasList = listItems.get(0);
			if (datasList.getPickerList().size()>0) {
				holder.tv_time.setText(datasList.getPickerList().get(position).getReferralTimeForDisplay());
				holder.tv_content.setText(datasList.getPickerList().get(position).getXcode());
				holder.tv_tag.setText((Constant.RECOMMEND_AWARD.equals(datasList.getPickerList().get(position).getIsVerify()) ? "已验证":"未验证"));
			}
		}
		
		return convertView;
	}
	static class ViewHolder {

		ImageView im_logo;//
		TextView tv_time;
		TextView tv_content;
		TextView tv_tag;
	}

}
