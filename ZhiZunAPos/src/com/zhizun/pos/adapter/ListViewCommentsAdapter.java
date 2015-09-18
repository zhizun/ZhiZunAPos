package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;

/**
 * 优惠ListViewAdapter
 */
public class ListViewCommentsAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Comments> listItems; // 数据集合

	public ListViewCommentsAdapter(Context context, List<Comments> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;

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
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_common_pinglun,
					null);

			holder.tv_list_common_pinglun_sendname = (TextView) convertView
					.findViewById(R.id.tv_list_common_pinglun_sendname);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Comments comments = listItems.get(position);

		if (null != comments.getUserAppe()
				&& !comments.getUserAppe().equals("")) {
			if (comments.getUserId().equals(
					AppContext.getApp().getUserLoginSharedPre().getUserInfo()
							.getUserId())) {
				comments.setUserAppe("您");
			}
			if (comments.getReplyUserID().equals(
					AppContext.getApp().getUserLoginSharedPre().getUserInfo()
							.getUserId())) {
				comments.setReplyUserAppe("您");
			}
			if (null == comments.getReplyCommentID()
					|| comments.getReplyCommentID().equals("")) {

				if (null != comments.getCommentContent()
						&& !comments.getCommentContent().equals("")) {
					String sendStr = "<font color='#17815f'>"
							+ comments.getUserAppe() + "：</font>"
							+ comments.getCommentContent();
					holder.tv_list_common_pinglun_sendname.setText(Html
							.fromHtml(sendStr));
				}

			} else {

				String str = "<font color='#17815f'>" + comments.getUserAppe()
						+ "</font> 回复 " + "<font color='#17815f'>"
						+ comments.getReplyUserAppe() + "：</font>"
						+ comments.getCommentContent();
				holder.tv_list_common_pinglun_sendname.setText(Html
						.fromHtml(str));
			}

		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_common_pinglun_sendname;// 评论发送人
	}

}
