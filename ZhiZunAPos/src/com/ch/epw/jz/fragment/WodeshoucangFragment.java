package com.ch.epw.jz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ch.epw.view.MyPullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseFragment;
/**
 * 我的收藏 家长端
 * 创建人：李林中
 * 创建日期：2014-12-15  上午10:45:29
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class WodeshoucangFragment extends BaseFragment {
	private final static String TAG = WodeshoucangFragment.class.getName();
	private Activity activity;
	private MyPullToRefreshListView myPullToRefreshListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(activity, R.layout.list_tzgg_jz, null);
		mPullListView = (PullToRefreshListView) view
				.findViewById(R.id.ll_activity_tzgg_parent);
		myPullToRefreshListView = new MyPullToRefreshListView(mPullListView, activity);
		myPullToRefreshListView.readyForRefresh();
		return view;
	}
}
