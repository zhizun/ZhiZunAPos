package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.NoticeTemplete;
import com.zhizun.pos.bean.Result;

/**
 * 通知模版 ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewNoticeTempleteAdapter extends MyBaseAdapter implements
		OnClickListener {

	public static final String TAG = ListViewNoticeTempleteAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<NoticeTemplete> listItems; // 数据集合
	
	private Callback mCallback;

	/**
	 * 自定义接口，用于回调按钮点击事件到Activity
	 * 
	 * @author Ivan Xu 2014-11-26
	 */
	public interface Callback {
		public void click(View v);
	}

	public ListViewNoticeTempleteAdapter(Context context,
			List<NoticeTemplete> listItems, Callback callback) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
		this.mCallback = callback;
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
					R.layout.list_notice_templete_item, null);

			holder.tv_list_notice_templete_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_notice_templete_item_content);

			holder.btn_list_notice_templete_delete = (Button) convertView
					.findViewById(R.id.btn_list_notice_templete_delete);

			holder.btn_list_notice_templete_send = (Button) convertView
					.findViewById(R.id.btn_list_notice_templete_send);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final NoticeTemplete noticeTemplete = listItems.get(position);

		if (null != noticeTemplete.getContent()
				&& !noticeTemplete.getContent().equals("")) {
			holder.tv_list_notice_templete_item_content.setText(noticeTemplete
					.getContent());
		}else {
			holder.tv_list_notice_templete_item_content.setText("");
		}
		//屏蔽所有的模板删除功能--by lyc 2015/3/4 16:09:26
//		if (noticeTemplete.getIsSystem().equals("1")) {
			holder.btn_list_notice_templete_delete.setVisibility(View.GONE);
//		}
		holder.btn_list_notice_templete_delete.setOnClickListener(this);
		holder.btn_list_notice_templete_delete.setTag(position);
		holder.btn_list_notice_templete_send.setOnClickListener(this);
		holder.btn_list_notice_templete_send.setTag(position);
		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_notice_templete_item_content;//
		Button btn_list_notice_templete_delete;//
		Button btn_list_notice_templete_send;//
	}

	@Override
	public void onClick(View v) {

		mCallback.click(v);
	}
}
