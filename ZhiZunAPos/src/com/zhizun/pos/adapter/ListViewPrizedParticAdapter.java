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

public class ListViewPrizedParticAdapter extends MyBaseAdapter{
	private Context context;// 运行上下文
	private List<MyPrizedShareList> listItems; // 数据集合
	public ListViewPrizedParticAdapter(Context context,
			List<MyPrizedShareList> listItems) {
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
			convertView = View.inflate(context, R.layout.list_my_prized_partic_item,
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
		MyPrizedShareList prizedShareList = listItems.get(position);
		String smpType=prizedShareList.getSmpType();
			if (smpType.equals(Constant.SMP_TYPE_WECHAT_MOMENTS)) {//微信朋友圈
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.wechat_moments));
			}else if (smpType.equals(Constant.SMP_TYPE_WEIBO_SINA)) {// 新浪微博
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.microblog));
			}else if (smpType.equals(Constant.SMP_TYPE_QQ_ZONE)){ // QQ空间
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.qq_zone));
			}else if (smpType.equals(Constant.SMP_TYPE_QQ)){ // QQ应用
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.qqapp));
			}else if (smpType.equals(Constant.SMP_TYPE_WECHAT)){ //微信
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.weixin));
			}else if (smpType.equals(Constant.SMP_TYPE_WECHAT_FAVORITE)){ //微信收藏
				holder.im_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.weixincollect));
			}
		if (null!=prizedShareList.getShareTime()&& !prizedShareList.getShareTime().equals("")) {
			holder.tv_time.setText(prizedShareList.getShareTime());
		}
		if (prizedShareList.getIsValid().equals("1")) {
			holder.tv_content.setText("分享成功");
			holder.tv_content.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_tag.setVisibility(View.GONE);
		}else {
			holder.tv_content.setText("无效分享");
			holder.tv_content.setTextColor(context.getResources().getColor(R.color.red));
			holder.tv_tag.setVisibility(View.VISIBLE);
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
