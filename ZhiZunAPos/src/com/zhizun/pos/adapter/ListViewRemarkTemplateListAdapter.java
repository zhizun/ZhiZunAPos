package com.zhizun.pos.adapter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.RemarkTeplate;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 发送点评里面的 点评模板列表ListViewAdapter
 */
public class ListViewRemarkTemplateListAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<RemarkTeplate> listItems; // 数据集合

	public ListViewRemarkTemplateListAdapter(Context context,
			List<RemarkTeplate> listItems) {
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
			convertView = View.inflate(context,
					R.layout.list_zxdp_js_template_item, null);

			holder.tv_list_zxdp_js_template_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_zxdp_js_template_item_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RemarkTeplate remarkTeplate = listItems.get(position);

		if (null != remarkTeplate.getItem()
				&& !remarkTeplate.getItem().equals("")) {
			holder.tv_list_zxdp_js_template_item_content.setText(remarkTeplate
					.getItem());
		} else {
			holder.tv_list_zxdp_js_template_item_content.setText("");
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_zxdp_js_template_item_content;
	}

}
