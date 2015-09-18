package com.zhizun.pos.adapter;

import java.util.List;

import android.R.raw;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Photo;

/**
 * GrideView 考勤Adapter
 */
public class GridViewAttenceAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<AttenceDetail> listItems; // 数据集合

	public GridViewAttenceAdapter(Context context, List<AttenceDetail> listItems) {
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
					R.layout.list_attence_js_item_gridviewitem, null);

			holder.iv_list_attence_js_item_gridviewitem_isorno = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_gridviewitem_isorno);
			holder.tr_list_attence_js_item_gridviewitem_title = (TableRow) convertView
					.findViewById(R.id.tr_list_attence_js_item_gridviewitem_title);
			holder.tv_list_attence_js_item_gridviewitem_stuname = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_gridviewitem_stuname);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AttenceDetail attenceDetail = listItems.get(position);
		if (position == 0 || position == 1) {
			holder.tr_list_attence_js_item_gridviewitem_title
					.setVisibility(View.VISIBLE);
		} else {
			holder.tr_list_attence_js_item_gridviewitem_title
					.setVisibility(View.GONE);
		}
		if (attenceDetail.getStatus().equals("1")) {
			holder.iv_list_attence_js_item_gridviewitem_isorno
					.setImageResource(R.drawable.kqyd_green);
		} else {
			holder.iv_list_attence_js_item_gridviewitem_isorno
					.setImageResource(R.drawable.kqwd_red);
		}
		if (attenceDetail.getStuName() != null
				&& !attenceDetail.getStuName().equals("")) {
			holder.tv_list_attence_js_item_gridviewitem_stuname
					.setText(attenceDetail.getStuName());
		} else {
			holder.tv_list_attence_js_item_gridviewitem_stuname.setText("");
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_attence_js_item_gridviewitem_isorno;//
		TableRow tr_list_attence_js_item_gridviewitem_title;
		TextView tv_list_attence_js_item_gridviewitem_stuname;
	}

}
