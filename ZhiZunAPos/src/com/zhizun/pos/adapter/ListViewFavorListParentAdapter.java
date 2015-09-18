package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.DateUtil;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Favor;

public class ListViewFavorListParentAdapter extends MyBaseAdapter{

	public static final String TAG = ListViewFavorListParentAdapter.class.getName();
	private Context context;// 运行上下文
	private List<Favor> listItems; // 数据集合

	public ListViewFavorListParentAdapter(Context context,	List<Favor> listItems) {
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
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_wdsc_jz_item,
					null);
			holder.iv_list_common_title_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_common_title_logo);
			holder.tv_list_common_title_teachername = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_teachername);
			holder.tv_list_common_title_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_orgname);
			holder.tv_list_common_title_time = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_time);
			holder.tv_list_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_item_content);
			holder.ngv_list_wdsc_item_imagelist = (NoScrollGridView)convertView
					.findViewById(R.id.ngv_list_wdsc_item_imagelist);
			holder.ll_list_wdsc_item_starlist = (NoScrollListView)convertView
					.findViewById(R.id.ll_list_wdsc_item_starlist);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Favor favor = (Favor)getItem(position);
		
		//userName 中实际包含 机构名称 + 用户名，中间空格分隔
		//在接口不变的情况下，对这种情况进行处理
		String userOrgName = favor.getUserName();
		String patten = "\\s+";
		if(userOrgName != null){
			String []names = userOrgName.split(patten);
			if(names.length == 2){
				favor.setOrgName(names[0]);
				favor.setUserName(names[1]);
			}
		}
		holder.tv_list_common_title_teachername.setText(favor
				.getUserName());
		 
		showPicture(favor.getPhotopath(), holder.iv_list_common_title_logo,
				options);

		if(favor.getOrgName() != null){
			holder.tv_list_common_title_orgname.setText("来自"+ favor.getOrgName());
		}else{
			holder.tv_list_common_title_orgname.setText("");
		}

		holder.tv_list_common_title_time.setText(DateUtil
				.getSimpleChineseDateTimeWithoutSec(favor.getFavTime(),
						"yyyy-MM-dd HH:mm:ss"));
		
		if(favor.getContent()!=null && !favor.getContent().equals("")){
			//兼容考勤记录的数据格式
			holder.tv_list_item_content.setVisibility(View.VISIBLE);
			holder.tv_list_item_content.setText(Html.fromHtml(favor.getContent()));
		}else{
			holder.tv_list_item_content.setVisibility(View.GONE);
		}

		//显示图片
		if (favor.getPhotoList() != null
				&& favor.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, favor.getPhotoList());
			holder.ngv_list_wdsc_item_imagelist.setVisibility(View.VISIBLE);
			holder.ngv_list_wdsc_item_imagelist.setAdapter(listViewImagesAdapter);
		} else {
			holder.ngv_list_wdsc_item_imagelist.setVisibility(View.GONE);
		}
		
		//显示评分
		if (favor.getRatingList() != null && favor.getRatingList().size() > 0) {
			ListViewRatingAdapter listViewRatingAdapter = new ListViewRatingAdapter(
					context, favor.getRatingList());
			holder.ll_list_wdsc_item_starlist.setVisibility(View.VISIBLE);
			holder.ll_list_wdsc_item_starlist.setAdapter(listViewRatingAdapter);
		} else {
			holder.ll_list_wdsc_item_starlist.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_common_title_logo;//
		TextView tv_list_common_title_teachername;
		TextView tv_list_common_title_orgname;
		TextView tv_list_common_title_time;
		TextView tv_list_item_content;
		
		NoScrollGridView ngv_list_wdsc_item_imagelist;	//图片列表
		NoScrollListView ll_list_wdsc_item_starlist;	//在校点评评分列表
	}
}
