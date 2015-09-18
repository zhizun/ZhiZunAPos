package com.zhizun.pos.activity;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhizun.pos.R;
import com.zhizun.pos.bean.SortTreeNode;

public class MenuPopWindow extends PopupWindow {

	private Context mContext;
	List<SortTreeNode> listItems;
	OnMenuItemCheckedListener onMenuItemCheckedListener;


	public MenuPopWindow(Context context, List<SortTreeNode> listItems) {
		super(context);
		mContext = context;
		this.listItems = listItems;
		initView();
	}

	private void initView() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.list_popwindow_item_course, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		ListView lv_menu_item_list = (ListView) view.findViewById(R.id.lv_menu_item_list);
		if(listItems != null && listItems.size() > 0){
			String[] menuItemArray = new String[listItems.size()];
			for (int k = 0; k < listItems.size(); k++) {
				menuItemArray[k] = listItems.get(k).getItemName();
			}
			lv_menu_item_list.setAdapter(new ArrayAdapter<String>(mContext,
					R.layout.menu_text_item, menuItemArray));
			lv_menu_item_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (onMenuItemCheckedListener != null) {
						onMenuItemCheckedListener.OnMenuItemChecked(listItems
								.get(position));
					}
				}
			});
		}
		
	}

	public static interface OnMenuItemCheckedListener {
		public void OnMenuItemChecked(SortTreeNode treeNode);
	}

	public OnMenuItemCheckedListener getOnMenuItemCheckedListener() {
		return onMenuItemCheckedListener;
	}

	public void setOnMenuItemCheckedListener(
			OnMenuItemCheckedListener onMenuItemCheckedListener) {
		this.onMenuItemCheckedListener = onMenuItemCheckedListener;
	}
}
