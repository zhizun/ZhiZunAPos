package com.ch.epw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Created by yangzhenwei on 2014/12/30.
 */
public class CatsListView extends ListView {

	private final String TAG = CatsListView.class.getName();

	public CatsListView(Context context) {
		super(context);
	}

	public CatsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CatsListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int height = getMeasuredHeight();
		int width = 0;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		// Log.i(TAG,"widthMode "+widthMode+" widthSize "+widthSize+" height "+height);
		//
		// if(widthMode == MeasureSpec.EXACTLY) {
		// width = widthSize;
		// }else {
		// if(widthMode == MeasureSpec.AT_MOST) {
		// final int childCount = getChildCount();
		// for(int i=0;i<childCount;i++) {
		// View view = getChildAt(i);
		// measureChild(view, widthMeasureSpec, heightMeasureSpec);
		// width = Math.max(width, view.getMeasuredWidth());
		// }
		// }
		// }
		//
		// setMeasuredDimension(width, height);
	}
}
