package com.ch.epw.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.GridView;
import android.widget.ListView;

/**
 * 不滚动的GridView 
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NoScrollGridView extends GridView {
	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
