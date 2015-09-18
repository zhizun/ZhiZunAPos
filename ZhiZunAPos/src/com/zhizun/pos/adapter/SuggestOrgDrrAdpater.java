package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.bean.SuggestName;

public class SuggestOrgDrrAdpater extends BaseAdapter {
	private Context context;
	List<SuggestName> suggestNames;

	public SuggestOrgDrrAdpater(Context context,List<SuggestName> suggestNames) {
		this.context=context;
		this.suggestNames=suggestNames;
	}

	@Override
	public int getCount() {
		return suggestNames.size();
	}

	@Override
	public Object getItem(int position) {
		return suggestNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.suggest_name_list_item_drr, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_item_name);
			holder.tv_orgDrr = (TextView) convertView
					.findViewById(R.id.tv_item_drr);
			holder.ll_drr_g=(LinearLayout) convertView.findViewById(R.id.ll_drr_g);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (suggestNames.size()>0) {
			SuggestName suggestName=suggestNames.get(position);
			if (suggestName != null) {
				if (suggestName.getSearchType().equals("1")) {
					if (suggestName.getOrgName()!=null&&!suggestName.getOrgName().equals("")) {
						holder.tv_name.setText(suggestName.getOrgName());
					}
					if (suggestName.getOrgDrr()!=null&&!suggestName.getOrgDrr().equals("")) {
						holder.tv_orgDrr.setText(suggestName.getOrgDrr());
					}
				}else {
					if (suggestName.getCourName()!=null&&!suggestName.getCourName().equals("")) {
						holder.tv_name.setText(suggestName.getCourName());
					}
					holder.ll_drr_g.setVisibility(View.GONE);
				}
			}
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_name;// 机构名字
		TextView tv_orgDrr;// 机构地址
		
		LinearLayout ll_drr_g;
		
	}
}
