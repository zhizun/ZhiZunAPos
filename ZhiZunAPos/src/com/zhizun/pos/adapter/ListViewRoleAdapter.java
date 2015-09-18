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
import com.zhizun.pos.bean.UserRole;
import com.zhizun.pos.widget.circularimage.CircularImage;

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
import android.widget.ListView;
import android.widget.TextView;

/**
 * 角色ListViewAdapter
 */
public class ListViewRoleAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<UserRole> listItems; // 数据集合

	public ListViewRoleAdapter(Context context, List<UserRole> listItems) {
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
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.dialog_selecter_role_item, null);
			holder.iv_dialog_select_role_item_pic = (CircularImage) convertView
					.findViewById(R.id.iv_dialog_select_role_item_pic);
			holder.tv_dialog_select_role_item_orgname = (TextView) convertView
					.findViewById(R.id.tv_dialog_select_role_item_orgname);
			holder.tv_dialog_select_role_item_rolename = (TextView) convertView
					.findViewById(R.id.tv_dialog_select_role_item_rolename);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		UserRole userRole = listItems.get(position);
		showPicture(userRole.getImagePath(),
				holder.iv_dialog_select_role_item_pic, options);
		holder.tv_dialog_select_role_item_orgname
				.setText(userRole.getOrgName());
		holder.tv_dialog_select_role_item_rolename.setText(userRole
				.getRoleName());
		return convertView;
	}

	static class ViewHolder {
		CircularImage iv_dialog_select_role_item_pic;
		TextView tv_dialog_select_role_item_orgname;
		TextView tv_dialog_select_role_item_rolename;
	}

}
