package com.zhizun.pos.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;

@SuppressLint({ "ViewConstructor" })
public class CustomPopupWindow extends PopupWindow {
	public CustomPopupWindow(Context paramContext, View paramView) {
		super(paramContext);
		setContentView(paramView);
		setWidth(-2);
		setHeight(-2);
		setBackgroundDrawable(new ColorDrawable(0));
		setFocusable(true);
	}

	public CustomPopupWindow(Context paramContext, View paramView, int paramInt) {
		this(paramContext, paramView);
		setWidth(paramInt);
		setHeight(-2);
	}

	public CustomPopupWindow(Context paramContext, View paramView, int paramInt1, int paramInt2) {
		this(paramContext, paramView);
		setWidth(paramInt1);
		setHeight(paramInt2);
	}

	public void showMenu(View paramView, int paramInt1, int paramInt2) {
		showAsDropDown(paramView, paramInt1, paramInt2);
		setFocusable(true);
		setOutsideTouchable(true);
		update();
	}

	public void showMenu(View paramView, int paramInt1, int paramInt2, int paramInt3) {
		showAtLocation(paramView, paramInt1, paramInt2, paramInt3);
		setFocusable(true);
		setOutsideTouchable(true);
		update();
	}
}