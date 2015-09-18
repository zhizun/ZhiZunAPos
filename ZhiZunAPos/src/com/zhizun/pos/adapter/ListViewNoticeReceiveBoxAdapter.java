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
import com.zhizun.pos.bean.NoticeBox;

/**
 * 通知收件箱列表ListViewAdapter
 */
public class ListViewNoticeReceiveBoxAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<NoticeBox> listItems; // 数据集合

	public ListViewNoticeReceiveBoxAdapter(Context context,
			List<NoticeBox> listItems) {
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
			convertView = View.inflate(context, R.layout.list_my_notice_item,
					null);
			holder.tv_list_my_notice_item_datetime = (TextView) convertView
					.findViewById(R.id.tv_list_my_notice_item_datetime);
			holder.iv_list_my_notice_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_my_notice_item_logo);
			holder.tv_list_my_notice_item_name = (TextView) convertView
					.findViewById(R.id.tv_list_my_notice_item_name);
			holder.tv_list_my_notice_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_my_notice_item_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		NoticeBox noticeBox = listItems.get(position);
		if (noticeBox.getIsRead().equals(Constant.NOTICE_READ)) {
			holder.iv_list_my_notice_item_logo
					.setImageResource(R.drawable.notice_read);
		}
		if (noticeBox.getIsRead().equals(Constant.NOTICE_UNREAD)) {
			holder.iv_list_my_notice_item_logo
					.setImageResource(R.drawable.notice_unread);
		}

		if (null != noticeBox.getSendTime()
				&& !noticeBox.getSendTime().equals("")) {
			holder.tv_list_my_notice_item_datetime.setText(noticeBox
					.getSendTime());
		} else {
			holder.tv_list_my_notice_item_datetime.setText("");
		}

		if (null != noticeBox.getUserName()
				&& !noticeBox.getUserName().equals("")) {
			holder.tv_list_my_notice_item_name.setText(noticeBox.getUserName());
		} else {
			holder.tv_list_my_notice_item_name.setText("");
		}

		if (null != noticeBox.getContent()
				&& !noticeBox.getContent().equals("")) {
			holder.tv_list_my_notice_item_content.setText(noticeBox
					.getContent());
		} else {
			holder.tv_list_my_notice_item_content.setText("");
		}

		return convertView;
	}

	static class ViewHolder {

		ImageView iv_list_my_notice_item_logo;//
		TextView tv_list_my_notice_item_datetime;
		TextView tv_list_my_notice_item_name;
		TextView tv_list_my_notice_item_content;
	}

}
