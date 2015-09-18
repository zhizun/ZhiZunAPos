package com.zhizun.pos.adapter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.fortysevendeg.swipelistview.SwipeListView;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 宝宝所属机构ListViewAdapter
 */
public class ListViewBabyOrgAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Org> listItems; // 数据集合
	private SwipeListView mSwipeListView;

	public ListViewBabyOrgAdapter(Context context, List<Org> listItems,
			SwipeListView mSwipeListView) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.mSwipeListView = mSwipeListView;
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
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.package_row, null);
			holder.iv_list_babyinfo_org_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_babyinfo_org_item_logo);
			holder.tv_list_babyinfo_org_item_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_babyinfo_org_item_orgname);
			holder.tv_list_babyinfo_org_item_claname = (TextView) convertView
					.findViewById(R.id.tv_list_babyinfo_org_item_claname);
			holder.back = (LinearLayout) convertView.findViewById(R.id.back);
			holder.tv_package_row_exit = (TextView) convertView
					.findViewById(R.id.tv_package_row_exit);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Org org = listItems.get(position);
		showPicture(org.getLogoPath(),
				holder.iv_list_babyinfo_org_item_logo, Options.getListOptions(R.drawable.default_logo));
		holder.tv_list_babyinfo_org_item_orgname.setText(org.getName());
		holder.tv_list_babyinfo_org_item_claname.setText(org.getClaName());

		holder.tv_package_row_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSwipeListView.closeAnimate(position);
				mSwipeListView.dismiss(position);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_babyinfo_org_item_logo;
		TextView tv_list_babyinfo_org_item_orgname;
		TextView tv_list_babyinfo_org_item_claname;
		LinearLayout back;
		TextView tv_package_row_exit;
	}

}
