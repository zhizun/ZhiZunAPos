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
import com.zhizun.pos.bean.Org;
import com.zhizun.pos.bean.UserRole;
import com.zhizun.pos.widget.circularimage.CircularImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择班级 ListViewAdapter
 */
public class ListViewSelectClassAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Org> listItems; // 数据集合

	public ListViewSelectClassAdapter(Context context, List<Org> listItems) {
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
					R.layout.dialog_selecter_class_item, null);
			holder.cb_dialog_selecter_class_item_state = (CheckBox) convertView
					.findViewById(R.id.cb_dialog_selecter_class_item_state);
			holder.tv_dialog_selecter_class_item_name = (TextView) convertView
					.findViewById(R.id.tv_dialog_selecter_class_item_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Org org = listItems.get(position);
		if (!TextUtils.isEmpty(org.getClaName())) {
			holder.tv_dialog_selecter_class_item_name.setText(org.getClaName());
		} else {
			holder.tv_dialog_selecter_class_item_name.setText("");
		}
		holder.cb_dialog_selecter_class_item_state.setChecked(org
				.getCheckState());
		return convertView;
	}

	static class ViewHolder {
		CheckBox cb_dialog_selecter_class_item_state;
		TextView tv_dialog_selecter_class_item_name;

	}

}
