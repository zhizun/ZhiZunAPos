package com.zhizun.pos.adapter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.BabyInfo;
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
 * 宝宝资料ListViewAdapter
 */
public class ListViewBabyInfoListAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<BabyInfo> listItems; // 数据集合

	public ListViewBabyInfoListAdapter(Context context,
			List<BabyInfo> listItems) {
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
					R.layout.list_popwindow_babyinfo_list_item, null);

			holder.tv_list_zxdp_js_template_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_zxdp_js_template_item_content);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BabyInfo babyInfo = listItems.get(position);

		if (null != babyInfo.getName()
				&& !babyInfo.getName().equals("")) {
			holder.tv_list_zxdp_js_template_item_content.setText(babyInfo
					.getName());
		} else {
			holder.tv_list_zxdp_js_template_item_content.setText("");
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_zxdp_js_template_item_content;
	}

}
