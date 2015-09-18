package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.bean.SortTreeNode;

/**
 * 列表视图的筛选列表
 * 
 *
 */
public class SortItemListAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<SortTreeNode> listItems; // 数据集合
	private int layoutId;

	public SortItemListAdapter(Context context, int layoutId, List<SortTreeNode> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, layoutId, null);
			holder.tv_item_name = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final SortTreeNode treeNode = (SortTreeNode) getItem(position);
		
		if (null != treeNode && treeNode.getItemName() != null) {
			holder.tv_item_name.setText(treeNode.getItemName());
		}
		
		// 获取上次选中状态的记录
		SortTreeNode checkedItem = getCheckedItem();
		if (checkedItem == null) {
			// 如果当前列表中没有选中状态的item，对于有子列表的，默认选中第一个
			if (treeNode.getSubItemList() != null
					&& treeNode.getSubItemList().size() > 0) {
				setItemCheckState(0);
			}
		}

		if (treeNode.isChecked()) {
			if (treeNode.getSubItemList() != null
					&& treeNode.getSubItemList().size() > 0) {
				((ListView) parent).performItemClick(convertView, position,
						position);
			}
			convertView.setBackgroundColor(Color.WHITE);
			holder.tv_item_name.setTextColor(Color.rgb(1, 160, 109));
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
			holder.tv_item_name.setTextColor(Color.GRAY);
		}

		return convertView;
	}

	class ViewHolder {
		TextView tv_item_name;
	}

	/**
	 * 设置指定下标的元素为选中状态
	 * 
	 * @param position
	 */
	public void setItemCheckState(int position) {
		if (listItems == null || listItems.size() == 0) {
			return;
		}

		// 清除当前的选中状态，传入null，默认会取当前选中状态的item
		resetItemCheckState(null);

		if (position >= 0 && position < listItems.size()) {
			listItems.get(position).setChecked(true);
		}
	}

	/**
	 * 重置指定item（或当前选中item）及其下级item的选中状态
	 * 
	 * @param node
	 */
	public void resetItemCheckState(SortTreeNode node) {
		SortTreeNode treeNode = node;
		if (treeNode == null) {
			treeNode = getCheckedItem();
			if (treeNode == null) {
				return;
			}
		}

		if (treeNode.getSubItemList() != null){
			for (SortTreeNode subNode : treeNode.getSubItemList()) {
				resetItemCheckState(subNode);
			}
		}
		treeNode.setChecked(false);
	}

	protected SortTreeNode getCheckedItem() {
		SortTreeNode searchItem = null;
		if (listItems != null && listItems.size() > 0) {
			for (int k = 0; k < listItems.size(); k++) {
				SortTreeNode item = listItems.get(k);
				if (item.isChecked()) {
					searchItem = item;
					break;
				}
			}
		}
		return searchItem;
	}
}

