package com.ch.epw.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class StickyScrollView extends ScrollView {
	private static final String STICKY = "sticky";

	private OnScrollListener onScrollListener;

	private List<View> mStickyViews;
	
	private View lastRelatedView;	//上次滚动时悬浮框对应菜单项
	private View currRelatedView;	//当前滚动时悬浮框对应菜单项
	private View nextRelatedView;

	private boolean isStickyAdded = false;

	public StickyScrollView(Context context) {
		this(context, null);
	}

	public StickyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StickyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mStickyViews = new LinkedList<View>();
		lastRelatedView = null;
		currRelatedView = null;
	}

	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	public int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(!isStickyAdded){
			findViewByStickyTag((ViewGroup)getChildAt(0));
			isStickyAdded = true;
		}
		
		if(mStickyViews != null && mStickyViews.size() > 0){
			boolean relaFlag = false;	//是否找到悬浮框关联的布局
			int firstVisiblePos = -1;	//记录第一个可见的布局，在当前滚动位置无法定位都任何一个布局时指定默认的悬浮框
			for( int k = 0; k < mStickyViews.size(); k++){
				View vr = mStickyViews.get(k);
				if(firstVisiblePos < 0 && vr.getVisibility() == View.VISIBLE){
					firstVisiblePos = k;
				}

				if(t >= vr.getTop() && t < vr.getBottom()){
					lastRelatedView = currRelatedView;	//记录上一个悬浮框关联布局
					currRelatedView = vr;
					if( k < mStickyViews.size() - 1 ){	//判断是否有下个关联布局，没有的话置为空
						nextRelatedView = mStickyViews.get(k+1);
					}else{
						nextRelatedView = null;
					}
					relaFlag = true;
					break;
				}
			}
			
			//找不到当前滚动位置对应的菜单项
			//分两种情况：
			//1. 页面第一次进入时，即 lastRelatedView 为  null 时, currRelatedView取第一个sticky view的位置
			//2. 滚动位置在页面中间的divider上, 此时 lastRelatedView 不为null，currRelatedView取上次的位置
			if(!relaFlag && firstVisiblePos >= 0){
				if(lastRelatedView == null){
					currRelatedView = mStickyViews.get(firstVisiblePos);
				}else{
					currRelatedView = lastRelatedView;
				}
			}
		}

		if (onScrollListener != null) {
			onScrollListener.onScroll(t);
		}
	}

	private String getStringTagForView(View v) {
		Object tag = v.getTag();
		return String.valueOf(tag);
	}

	private void findViewByStickyTag(ViewGroup viewGroup) {
		int childCount = ((ViewGroup) viewGroup).getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = viewGroup.getChildAt(i);

			if (getStringTagForView(child).contains(STICKY)) {
				mStickyViews.add(child);
			}

			if (child instanceof ViewGroup) {
				findViewByStickyTag((ViewGroup) child);
			}
		}
	}

	/**
	 * 
	 * 滚动的回调接口
	 * 
	 * @author xiaanming
	 * 
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * 
		 * @param scrollY
		 * 
		 */
		public void onScroll(int scrollY);
	}

	public View getCurrRelatedView() {
		return currRelatedView;
	}

	public void setCurrRelatedView(View currRelatedView) {
		this.currRelatedView = currRelatedView;
	}

}
