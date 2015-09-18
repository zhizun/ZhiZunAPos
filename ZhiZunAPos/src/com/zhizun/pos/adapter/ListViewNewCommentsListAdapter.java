package com.zhizun.pos.adapter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.DynamicTeacher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 最新回复详情里面的评论列表ListViewAdapter
 */
public class ListViewNewCommentsListAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Comments> listItems; // 数据集合

	public ListViewNewCommentsListAdapter(Context context,
			List<Comments> listItems) {
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
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.list_newcomment_comments, null);

			holder.tv_list_newcomment_comments_name = (TextView) convertView
					.findViewById(R.id.tv_list_newcomment_comments_name);
			holder.iv_list_newcomment_comments_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_newcomment_comments_logo);
			holder.tv_list_newcomment_comments_date = (TextView) convertView
					.findViewById(R.id.tv_list_newcomment_comments_date);
			holder.tv_list_newcomment_comments_content = (TextView) convertView
					.findViewById(R.id.tv_list_newcomment_comments_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Comments comments = listItems.get(position);

		showPicture(
				comments.getUserPhoto(),
				holder.iv_list_newcomment_comments_logo, options);
		if (null != comments.getUserAppe()
				&& !comments.getUserAppe().equals("")) {
			holder.tv_list_newcomment_comments_name.setText(comments
					.getUserAppe());
		}else {
			holder.tv_list_newcomment_comments_name.setText("");
		}

		if (null != comments.getCommentTime()
				&& !comments.getCommentTime().equals("")) {
			holder.tv_list_newcomment_comments_date.setText(comments
					.getCommentTime());
		}else {
			holder.tv_list_newcomment_comments_date.setText("");
		}

		if (null != comments.getUserAppe()
				&& !comments.getUserAppe().equals("")) {
			if (null == comments.getReplyCommentID()
					|| comments.getReplyCommentID().equals("")) {

				if (null != comments.getCommentContent()
						&& !comments.getCommentContent().equals("")) {

					holder.tv_list_newcomment_comments_content.setText(comments
							.getCommentContent());
				}

			} else {

				String str = "回复 " + "<font color='#127C64'>"
						+ comments.getReplyUserAppe() + "</font>" + "："
						+ comments.getCommentContent();
				holder.tv_list_newcomment_comments_content.setText(Html
						.fromHtml(str));
			}

		}else {
			holder.tv_list_newcomment_comments_content.setText("");
		}

		return convertView;
	}

	static class ViewHolder {

		ImageView iv_list_newcomment_comments_logo;//
		TextView tv_list_newcomment_comments_name;
		TextView tv_list_newcomment_comments_date;
		TextView tv_list_newcomment_comments_content;
	}

}
