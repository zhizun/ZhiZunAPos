package com.zhizun.pos.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.CourseApply;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.activity.OrgIntroDetailActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.CourseListItemList;
import com.zhizun.pos.bean.UserLogin;

public class CourseListViewAdapter extends MyBaseAdapter {
	private Context context;// 运行上下文
	private List<CourseListItemList> listItems; // 数据集合

	public CourseListViewAdapter(Context context,
			ArrayList<CourseListItemList> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
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
			convertView = View.inflate(context, R.layout.activity_course_listview_itme,
					null);
			holder.image_course_log = (ImageView) convertView
					.findViewById(R.id.image_course_log);
			holder.tv_course_content = (TextView) convertView
					.findViewById(R.id.tv_course_content);
			holder.tv_course_name = (TextView) convertView
					.findViewById(R.id.tv_course_name);
			holder.tv_money=(TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_distance=(TextView) convertView.findViewById(R.id.tv_distance);
			holder.bt_apply=(Button) convertView.findViewById(R.id.bt_apply);
			holder.tv_signnum=(TextView) convertView.findViewById(R.id.tv_signnum);
			holder.lv_wang=(LinearLayout) convertView.findViewById(R.id.lv_wang);
			holder.lv_xianshi=(LinearLayout) convertView.findViewById(R.id.lv_xianshi);
			holder.lv_suishi=(LinearLayout) convertView.findViewById(R.id.lv_suishi);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final CourseListItemList courseListItemList = listItems.get(position);
		String imgUrlString = "";
		if (null != courseListItemList.getThumbPath()) {
			imgUrlString=courseListItemList.getThumbPath();
		}else {
			holder.image_course_log.setImageDrawable(context.getResources().getDrawable(R.drawable.default_photo));
		}

//		showPicture(imgUrlString, holder.image_course_log, options);
		ImageUtils.showImageCourse(holder.image_course_log, imgUrlString);

		holder.tv_course_content.setText(courseListItemList.getName());
		if (courseListItemList.getCourseOrgBean()!=null) {
			holder.tv_course_name.setText(courseListItemList.getCourseOrgBean().getOrgName());
		}else {
			holder.tv_course_name.setVisibility(View.GONE);
		}
		if (courseListItemList.getPrice()==-1) {
			holder.tv_money.setText("暂无价格");
		}else {
			holder.tv_money.setText("¥" + courseListItemList.getPrice());
		}
		String distStrDesc = "";
		if (null!=courseListItemList.getDistance()&&!courseListItemList.getDistance().equals("")) {
			try{
				BigDecimal disance = new BigDecimal(courseListItemList.getDistance());
				if(disance.compareTo(BigDecimal.ONE) < 0){
					distStrDesc = "距离" + disance.movePointLeft(-3).intValue()
							+ "m";
				}else{
					distStrDesc = "距离"
							+ disance.setScale(1, BigDecimal.ROUND_CEILING)
							+ "km";
				}
			}catch (NumberFormatException e){
				distStrDesc = "距离未知";
			}
		}else {
			distStrDesc = "距离未知";
		}
		holder.tv_distance.setText(distStrDesc);
		
		// 如果是从机构详情页面调用的课程列表，隐藏距离（lyc 2015/9/9 15:56:32）
		if(context.getClass().isAssignableFrom(OrgIntroDetailActivity.class)){
			holder.tv_distance.setText("");
		}
		
		if (null!=courseListItemList.getRefundable()&&!courseListItemList.getRefundable().equals("")) {
			if (courseListItemList.getRefundable().equals("0")) {
				holder.lv_suishi.setVisibility(View.GONE);
				holder.lv_xianshi.setVisibility(View.GONE);
			}else if(courseListItemList.getRefundable().equals("1")){
				holder.lv_suishi.setVisibility(View.VISIBLE);
				holder.lv_xianshi.setVisibility(View.GONE);
			}else if(courseListItemList.getRefundable().equals("2")){
				holder.lv_suishi.setVisibility(View.GONE);
				holder.lv_xianshi.setVisibility(View.VISIBLE);
			}
		}

		if (courseListItemList.getOlSalesInfo()==0) {
			holder.lv_wang.setVisibility(View.GONE);
		}else {
			holder.lv_wang.setVisibility(View.VISIBLE);
		}
		
		/*
		 * 报名数暂不显示，修改为显示评论数 if
		 * (null!=courseListItemList.getSignNum()&&!courseListItemList
		 * .getSignNum().equals("")&&
		 * !courseListItemList.getSignNum().equals("null")&&
		 * !courseListItemList.getSignNum().equals("0")) {
		 * holder.tv_signnum.setText(courseListItemList.getSignNum() + "人报名");
		 * }else { holder.tv_signnum.setText(""); }
		 */
		if (!TextUtils.isEmpty(courseListItemList.getCommentNum())
				&& !courseListItemList.getCommentNum().equals("0")) {
			String viewNumDesc = courseListItemList.getCommentNum() + "人评论";
			holder.tv_signnum.setText(viewNumDesc);
		} else {
			holder.tv_signnum.setText("");
		}

		holder.bt_apply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isLogin()) {
					Intent intent=new Intent(context,CourseApply.class);
					intent.putExtra("courId", courseListItemList.getCourId());
					intent.putExtra("ownOrgId", courseListItemList.getOrgId());
					context.startActivity(intent);
				}
				
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView image_course_log;
		TextView tv_course_content;
		TextView tv_course_name;
		TextView tv_money;
		TextView tv_distance;
		Button bt_apply;
		TextView tv_signnum;
		LinearLayout lv_wang;
		LinearLayout lv_xianshi;
		LinearLayout lv_suishi;
	}

	private boolean isLogin() {
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("pageIndex", 2);
			context.startActivity(intent);
			return false;
		}
		return true;
	}
}
