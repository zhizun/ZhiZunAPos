package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ch.epw.utils.ImageUtils;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.UserLogin;

public class ListViewCourseCommentAdapter extends MyBaseAdapter {
	private Context context;
	private List<FriendsCommentListBean> listItems;

	public ListViewCourseCommentAdapter(Context context,
			List<FriendsCommentListBean> listItems) {
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
			convertView = View.inflate(context,
					R.layout.list_item_course_comment, null);
			holder.tv_comment_nickname = (TextView) convertView.findViewById(R.id.tv_comment_nickname);
			holder.iv_user_photo = (ImageView) convertView
					.findViewById(R.id.iv_user_photo);
			holder.ll_course_comment_starlist = (ListView) convertView
					.findViewById(R.id.ll_course_comment_starlist);
			holder.tv_comment_content = (TextView) convertView.findViewById(R.id.tv_comment_content);
			holder.tv_comment_date = (TextView) convertView.findViewById(R.id.tv_comment_date);
			holder.gv_pic_gridlist = (GridView) convertView.findViewById(R.id.gv_pic_gridlist);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final FriendsCommentListBean courseComment = listItems.get(position);
		ImageUtils.showImageAsPhoto(holder.iv_user_photo,
				courseComment.getUserPhoto());
//		holder.tv_comment_nickname.setText(courseComment.getUserShortName());
		if (courseComment.getIsAnonymous().equals("0")) {//getIsAnonymous()	0:匿名
			if (courseComment.getShowUserName() != null
					&& !courseComment.getShowUserName().equals("")) {
				holder.tv_comment_nickname
						.setText(courseComment.getShowUserName());// 朋友圈显示名称
			} else if (courseComment.getUserShortName() != null
					&& !courseComment.getUserShortName().equals("")) {
				holder.tv_comment_nickname.setText(courseComment
						.getUserShortName());
			} else {
				holder.tv_comment_nickname.setText(courseComment.getUserPhone());// 附近显示手机号
			}
		}else {
			holder.tv_comment_nickname.setText("佚名");//佚名
		}
		
		
		String loginUserId = null;
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin != null && userLogin.getUserInfo() != null) {
			loginUserId = userLogin.getUserInfo().getUserId();
		}
		if (courseComment.getUserId().equals(loginUserId)) {
			holder.tv_comment_nickname.setText("我");// 我的评价
		}

		if (courseComment.getRatingList() != null
				&& courseComment.getRatingList().size() > 0) {

			ListViewRatingAdapter ratingListAdapter = new ListViewRatingAdapter(
				context, R.layout.course_comment_star_item,
				courseComment.getRatingList().subList(0, 1));
			holder.ll_course_comment_starlist.setAdapter(ratingListAdapter);
		}

		holder.tv_comment_content.setText(courseComment.getContent());
		holder.tv_comment_date.setText(courseComment.getComDate());

		if (courseComment.getPicList() != null
				&& courseComment.getPicList().size() > 0) {
			holder.gv_pic_gridlist.setVisibility(View.VISIBLE);
			FriendsGridViewImagesAdapter listViewImagesAdapter = new FriendsGridViewImagesAdapter(
					context, courseComment.getPicList());
			holder.gv_pic_gridlist.setAdapter(listViewImagesAdapter);
			holder.gv_pic_gridlist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ArrayList<String> imgsUrl = new ArrayList<String>();
					for (Photo photo : courseComment.getPicList()) {
						imgsUrl.add(photo.getThumbPath() + "/"
								+ photo.getThumbSaveName());
					}
					Intent intent = new Intent();
					intent.putStringArrayListExtra("infos", imgsUrl);
					intent.putExtra("imgPosition", position);
					intent.setClass(context, ImageShowActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			});
		} else {
			holder.gv_pic_gridlist.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView iv_user_photo;
		TextView tv_comment_nickname;
		ListView ll_course_comment_starlist;
		TextView tv_comment_content;
		TextView tv_comment_date;
		GridView gv_pic_gridlist;
	}
}
