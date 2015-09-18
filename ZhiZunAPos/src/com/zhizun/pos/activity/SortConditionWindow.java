package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.SortItemListAdapter;
import com.zhizun.pos.bean.SortTreeNode;
import com.zhizun.pos.bean.SortTreeNodeWrapper;
import com.ch.epw.utils.Constant;

public class SortConditionWindow<CONTENT_VIEW extends AbsListView> extends PopupWindow {

	private Context mContext;
	private int layoutId;
	List<SortTreeNode> listItems;
	List<SortTreeNode> nearbyAreaSettingsItems;
	SortItemListAdapter primaryListAdapter;
	OnSortItemCheckedListener onSortItemCheckedListener;

	// 记录每个一级列表元素对应的adapter，传入layoutId作为key为了以后扩展
	private static Map<Integer, SortItemListAdapter> primaryListAdpterMap = new HashMap<Integer, SortItemListAdapter>();

	public SortConditionWindow(Context context, int layoutId, List<SortTreeNode> metaList) {
		super(context);
		mContext = context;
		this.layoutId = layoutId;
		if(this.layoutId == R.layout.sort_area_window_layout){
			nearbyAreaSettingsItems = SortTreeNodeWrapper.parse(Constant.nearbyAreaSettings);
		}else{
			nearbyAreaSettingsItems = new ArrayList<SortTreeNode>();
		}

		listItems = nearbyAreaSettingsItems;
		if (metaList != null) {
			listItems.addAll(metaList);
		}
		initView();
	}
	
	@SuppressLint("NewApi")
	private void initView() {

		View view = LayoutInflater.from(mContext).inflate(layoutId, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(false);
		setAnimationStyle(0);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		final ListView ll_sort_item_primary_category = (ListView) view
				.findViewById(R.id.ll_sort_item_primary_category);
		
		final CONTENT_VIEW ll_sort_item_second_category = (CONTENT_VIEW) view
				.findViewById(R.id.ll_sort_item_second_category);

		primaryListAdapter = new SortItemListAdapter(mContext,
				R.layout.sort_first_category_item, listItems);
		// 记录layout与adpter的映射关系
		// SortConditionWindow生成了几个实例，就有几对映射关系，重置时，将鄋adpter都重置
		primaryListAdpterMap.put(layoutId, primaryListAdapter);
		ll_sort_item_primary_category.setAdapter(primaryListAdapter);
		
		OnItemClickListener sortItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SortItemListAdapter adapter = null;
				if(parent.getAdapter() instanceof HeaderViewListAdapter) {
					HeaderViewListAdapter listAdapter=(HeaderViewListAdapter)parent.getAdapter();
					adapter = (SortItemListAdapter) listAdapter.getWrappedAdapter();
				}else{
					adapter = (SortItemListAdapter) parent.getAdapter();
				}

				Object item = adapter.getItem(position);
				if (item != null) {
					SortTreeNode treeNode = (SortTreeNode) item;
					if (!treeNode.isChecked()) {
						adapter.setItemCheckState(position);
					}
					adapter.notifyDataSetChanged();
					if(treeNode.getSubItemList() != null ){
						SortItemListAdapter secondListAdapter = new SortItemListAdapter(
										mContext,
										R.layout.sort_second_category_item,
										treeNode.getSubItemList());
						ll_sort_item_second_category.setAdapter(secondListAdapter);

						if (treeNode.getSubItemList().size() == 0) {
							if (onSortItemCheckedListener != null) {
								onSortItemCheckedListener
										.onSortItemChecked(treeNode);
							}
						}
					}
				}
			}
		};
		ll_sort_item_primary_category.setOnItemClickListener(sortItemClickListener);
		ll_sort_item_second_category.setOnItemClickListener(sortItemClickListener);

		Button bt_sort_reset = (Button) view.findViewById(R.id.bt_sort_reset);
		if (bt_sort_reset != null) {
			bt_sort_reset.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 调用primaryListAdpterMap中所有的SortItemListAdapter对象的重置选中项方法
					Iterator<SortItemListAdapter> iter = primaryListAdpterMap
							.values().iterator();
					while (iter.hasNext()) {
						iter.next().resetItemCheckState(null);
					}

					if (onSortItemCheckedListener != null) {
						onSortItemCheckedListener.onSortItemChecked(null);
					}
				}
			});
		}

		// 点击下方的遮罩区域，隐藏窗口
		View blank_holder = view.findViewById(R.id.blank_holder);
		if (blank_holder != null) {
			blank_holder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SortConditionWindow.this.dismiss();
				}
			});
		}
	}

	public void refreshListData(List<SortTreeNode> metaList){
		listItems = nearbyAreaSettingsItems;
		if (metaList != null) {
			listItems.addAll(metaList);
		}
		primaryListAdapter.notifyDataSetChanged();
	}

	public OnSortItemCheckedListener getOnSortItemCheckedListener() {
		return onSortItemCheckedListener;
	}

	public void setOnSortItemCheckedListener(
			OnSortItemCheckedListener onSortItemCheckedListener) {
		this.onSortItemCheckedListener = onSortItemCheckedListener;
	}

	public static interface OnSortItemCheckedListener {
		public void onSortItemChecked(SortTreeNode treeNode);
	}
}
