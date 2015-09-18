package com.zhizun.pos.adapter;

import java.util.ArrayList;



import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 功能描述：ViewPager适配器，用来绑定数据和view 创建人：李林中 创建日期：2014-5-19 下午5:52:52 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private ArrayList<View> views;

	public ViewPagerAdapter(ArrayList<View> views) {
		this.views = views;
	}

	/**
	 * 获得当前界面数
	 */
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	/**
	 * 初始化position位置的界面
	 */
	@Override
	public Object instantiateItem(View view, int position) {

		((ViewPager) view).addView(views.get(position), 0);

		return views.get(position);
	}

	/**
	 * 判断是否由对象生成界面
	 */
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	/**
	 * 销毁position位置的界面
	 */
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeView(views.get(position));
	}



}
