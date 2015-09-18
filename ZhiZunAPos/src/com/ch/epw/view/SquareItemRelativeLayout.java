package com.ch.epw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 正方形布局容器
 * 
 * 用于在gridView中固定列数后，自动适应布局元素为高度与宽度一致
 */

public class SquareItemRelativeLayout extends RelativeLayout {

	private final String TAG = SquareItemRelativeLayout.class.getName();

	public SquareItemRelativeLayout(Context context) {
		super(context);
	}

	public SquareItemRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareItemRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
