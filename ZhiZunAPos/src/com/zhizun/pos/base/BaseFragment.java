package com.zhizun.pos.base;

import java.text.SimpleDateFormat;
import java.util.Date;


import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

/**
 * fragment基类 创建人：李林中 创建日期：2014-12-15 上午10:01:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class BaseFragment extends Fragment {
	// 下拉刷新相关参数
	protected ListView mListView;
	protected PullToRefreshListView mPullListView;

	protected int mCurPage = 1;// 当前页面
	protected SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	protected SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyyMMdd");
	protected boolean hasMoreData = true;

	// 图片缓存插件
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	// 下拉加载下拉刷新要用到
	// 最后一次刷新时间
	protected void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	protected String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

	// 返回动画
	public void backAnim(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

	// 前进动画
	public void intoAnim(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

}
