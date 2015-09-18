package com.zhizun.pos.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import com.ch.epw.js.activity.AttenceRemarksActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.AttenceDetail;

/**
 * 考勤 编辑 教师端 ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewAttenceEditTeacherAdapter extends MyBaseAdapter {

	private static final String TAG = ListViewAttenceEditTeacherAdapter.class
			.getName();
	private Context context;// 运行上下文
	private ArrayList<AttenceDetail> listItems; // 数据集合
	private Handler handler;
	private int attencenum;// 已到增加数量
	private int abstentnum;// 未到增加数量

	public ListViewAttenceEditTeacherAdapter(Context context,
			ArrayList<AttenceDetail> listItems, Handler handler,
			int attencenum, int abstentnum) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.handler = handler;
		this.attencenum = attencenum;
		this.abstentnum = abstentnum;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.list_attence_js_item_edit_listviewitem, null);

			holder.iv_list_attence_js_item_listviewitem_yes = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_listviewitem_yes);
			holder.iv_list_attence_js_item_listviewitem_no = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_listviewitem_no);
			holder.tr_list_attence_js_item_listviewitem_title = (TableRow) convertView
					.findViewById(R.id.tr_list_attence_js_item_listviewitem_title);
			holder.tv_list_attence_js_item_listviewitem_stuname = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_listviewitem_stuname);
			holder.iv_list_attence_js_item_listviewitem_bz = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_listviewitem_bz);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final AttenceDetail attenceDetail = listItems.get(position);
		if (position == 0) {
			holder.tr_list_attence_js_item_listviewitem_title
					.setVisibility(View.VISIBLE);
		} else {
			holder.tr_list_attence_js_item_listviewitem_title
					.setVisibility(View.GONE);
		}
		if (attenceDetail.getStatus().equals("1")) {
			holder.iv_list_attence_js_item_listviewitem_yes
					.setImageResource(R.drawable.kqyd_green);
			holder.iv_list_attence_js_item_listviewitem_no
					.setImageResource(R.drawable.kqwd_gray);

		} else {
			holder.iv_list_attence_js_item_listviewitem_yes
					.setImageResource(R.drawable.kqyd_gray);
			holder.iv_list_attence_js_item_listviewitem_no
					.setImageResource(R.drawable.kqwd_red);
		}
		if (attenceDetail.getStuName() != null
				&& !attenceDetail.getStuName().equals("")) {
			holder.tv_list_attence_js_item_listviewitem_stuname
					.setText(attenceDetail.getStuName());
		} else {
			holder.tv_list_attence_js_item_listviewitem_stuname.setText("");
		}
		holder.iv_list_attence_js_item_listviewitem_yes
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 如果是已到状态，直接返回
						if (listItems.get(position).getStatus()
								.equals(Constant.ATTENCE_STATUS_ATTEND)) {
							return;
						}
						if (v.getTag().equals("kqyd_gray")) {
							holder.iv_list_attence_js_item_listviewitem_yes
									.setImageResource(R.drawable.kqyd_green);
							holder.iv_list_attence_js_item_listviewitem_no
									.setImageResource(R.drawable.kqwd_gray);
							listItems.get(position).setStatus(
									Constant.ATTENCE_STATUS_ATTEND);
							attencenum = attencenum + 1;
							abstentnum = abstentnum - 1;
							sendMessageHandler();
						}
					}
				});
		holder.iv_list_attence_js_item_listviewitem_no
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 如果是未状态，直接返回
						if (listItems.get(position).getStatus()
								.equals(Constant.ATTENCE_STATUS_ABSENT)) {
							return;
						}
						if (v.getTag().equals("kqwd_gray")) {
							holder.iv_list_attence_js_item_listviewitem_yes
									.setImageResource(R.drawable.kqyd_gray);
							holder.iv_list_attence_js_item_listviewitem_no
									.setImageResource(R.drawable.kqwd_red);
							listItems.get(position).setStatus(
									Constant.ATTENCE_STATUS_ABSENT);
							attencenum = attencenum - 1;
							abstentnum = abstentnum + 1;
							sendMessageHandler();
						}
					}

				});
		if (attenceDetail.getRemarks() != null
				&& attenceDetail.getRemarks().size() != 0) {
			holder.iv_list_attence_js_item_listviewitem_bz
					.setImageResource(R.drawable.kqbz_pressed);
		} else {
			holder.iv_list_attence_js_item_listviewitem_bz
					.setImageResource(R.drawable.kqbz_normal);
		}
		holder.iv_list_attence_js_item_listviewitem_bz
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								AttenceRemarksActivity.class);
						intent.putExtra("listItems", listItems);
						intent.putExtra("position", position);
						context.startActivity(intent);
					}
				});
		return convertView;
	}

	private void sendMessageHandler() {

		Message msg = handler.obtainMessage();
		msg.what = 1;
		msg.arg1 = attencenum;
		msg.arg2 = abstentnum;
		Log.i(TAG, "attencenum=" + attencenum + ",abstentnum=" + abstentnum);
		handler.sendMessage(msg);

	}

	static class ViewHolder {
		ImageView iv_list_attence_js_item_listviewitem_yes;//
		ImageView iv_list_attence_js_item_listviewitem_no;//
		TableRow tr_list_attence_js_item_listviewitem_title;
		TextView tv_list_attence_js_item_listviewitem_stuname;
		ImageView iv_list_attence_js_item_listviewitem_bz;
	}
}
