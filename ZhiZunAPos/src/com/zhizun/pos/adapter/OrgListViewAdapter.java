package com.zhizun.pos.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.CourseListViewActivity;
import com.zhizun.pos.activity.SearchNearbyHotsActivity;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.CourseCatBean;
import com.zhizun.pos.bean.CourseListItemList;

/**
 * 首页机构列表 ListViewAdapter
 */
public class OrgListViewAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private ArrayList<CourseListItemList> listItems; // 数据集合

	public OrgListViewAdapter(Context context,
			ArrayList<CourseListItemList> listItems) {
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
			convertView = View.inflate(context, R.layout.list_org_item,
					null);

			holder.iv_list_common_title_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_common_title_logo);
			holder.tv_list_common_title_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_orgname);
			holder.tv_list_common_title = (TextView) convertView
					.findViewById(R.id.tv_list_common_title);
			holder.tv_list_common_more_cour = (TextView) convertView
					.findViewById(R.id.tv_list_common_more_cour);
			holder.tv_list_org_distance = (TextView) convertView
					.findViewById(R.id.tv_list_org_distance);
			holder.tv_list_org_viewnum = (TextView) convertView
					.findViewById(R.id.tv_list_org_viewnum);
			holder.ll_list_org_optional_desc = (RelativeLayout) convertView
					.findViewById(R.id.ll_list_org_optional_desc);
			// 从 找机构 页面 或者 附近 加载机构列表时，显示距离和报名数
			// 从首页加载机构列表时。默认不显示机构距离和报名数
			if(context.getClass().isAssignableFrom(CourseListViewActivity.class)
			|| context.getClass().isAssignableFrom(SearchNearbyHotsActivity.class)		
			){
				holder.ll_list_org_optional_desc.setVisibility(View.VISIBLE);
			}
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final CourseListItemList courItemBean = listItems.get(position);
		if (courItemBean.getCategory().equals("0")) {
			holder.tv_list_common_more_cour.setVisibility(View.GONE);
		}else {
			holder.tv_list_common_more_cour.setVisibility(View.VISIBLE);
		}
		String imgUrlString = "";
		if (null != courItemBean.getLogoPath()) {
			imgUrlString =courItemBean.getLogoPath();
		}else {
			holder.iv_list_common_title_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.default_photo));
		}
		ImageUtils.showImageAsLogo(holder.iv_list_common_title_logo, imgUrlString);
		//showPicture(imgUrlString, holder.iv_list_common_title_logo, options);
		int catLen = courItemBean.getCourseCatBeanList().size();
		String catStrings = "";
		if (catLen > 0) {
			for (int i = 0; i < catLen; i++) {
				CourseCatBean catBean = courItemBean.getCourseCatBeanList().get(i);
				if(catBean.getCatPriName() != null && !catStrings.contains(catBean.getCatPriName())){
					if (!catBean.getCatPriName().equals("null")) {
						catStrings = catStrings + catBean.getCatPriName() + "|";
					}
				}
			}
			if(catStrings.endsWith("|")){
				catStrings = catStrings.substring(0, catStrings.length() - 1);
			}
		}
		if (catStrings.equals("")) {
			holder.tv_list_common_title_orgname.setVisibility(View.GONE);
		}else {
			holder.tv_list_common_title_orgname.setVisibility(View.VISIBLE);
			holder.tv_list_common_title_orgname.setText(catStrings);
		}
		
		String distStrDesc = "";
		if(courItemBean.getDistance() != null && !courItemBean.getDistance().equals("")){
			try{
				BigDecimal disance = new BigDecimal(courItemBean.getDistance());
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
		holder.tv_list_org_distance.setText(distStrDesc);
		
		// if(courItemBean.getViewNum() != null &&
		// !courItemBean.getViewNum().equals("") ){
		// String viewNumDesc = courItemBean.getViewNum() + "人浏览";
		// holder.tv_list_org_viewnum.setText(viewNumDesc);
		// }
		if (!TextUtils.isEmpty(courItemBean.getCommentNum())
				&& !courItemBean.getCommentNum().equals("0")) {
			String viewNumDesc = courItemBean.getCommentNum() + "人评论";
			holder.tv_list_org_viewnum.setText(viewNumDesc);
		} else {
			holder.tv_list_org_viewnum.setText("");
		}

		holder.tv_list_common_title.setText(courItemBean.getName());
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_common_title_logo;
		TextView tv_list_common_title_orgname;
		TextView tv_list_common_more_cour;
		TextView tv_list_common_title;
		TextView tv_list_org_distance;
		TextView tv_list_org_viewnum;
		RelativeLayout ll_list_org_optional_desc;
	}

}
