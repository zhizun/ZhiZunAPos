package com.ch.epw.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.ListView;
/**
 * 不滚动的listview
 * 创建日期：2014-12-15  上午10:56:56
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class NoScrollListView extends ListView {
	  public NoScrollListView(Context context, AttributeSet attrs){  
	         super(context, attrs);  
	    }  
	 
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
	         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	         super.onMeasure(widthMeasureSpec, mExpandSpec);  
	    }  
}
