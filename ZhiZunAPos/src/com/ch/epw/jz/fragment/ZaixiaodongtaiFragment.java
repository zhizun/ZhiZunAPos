package com.ch.epw.jz.fragment;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewCommentsAdapter;
import com.zhizun.pos.adapter.ListViewDynamicParentAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseFragment;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.DynamicTeacherList;

/**
 * 在校动态 家长端 创建人：李林中 创建日期：2014-12-15 上午10:45:29 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ZaixiaodongtaiFragment extends BaseFragment {
	private final static String TAG = ZaixiaodongtaiFragment.class.getName();
	Activity activity;
	Integer dynamicCount;// 在校动态数量 共用字段
	DynamicTeacherList dynamicTeacherList;
	List<DynamicTeacher> list;
	DynamicTeacher dynamicTeacher;
	ListViewDynamicParentAdapter dynamicParentAdapter;
	ListViewCommentsAdapter commentsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void contentList() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件
		Log.i("tag", "mPullListView=" + mPullListView);
		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(25);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new DynamicListTask().execute(1 + "", Constant.LOADDATACOUNT
						+ "");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				int page = (int) (Math.ceil(dynamicCount
						/ (double) Constant.LOADDATACOUNT));

				mCurPage = mCurPage + 1;
				Log.i(TAG, "mCurPage=" + mCurPage);
				if (mCurPage > page) {
					mCurPage = page;
					hasMoreData = false;
					mPullListView.setHasMoreData(hasMoreData);
					return;
				}

				Log.i(TAG, "mCurPage2=" + mCurPage);
				Log.i(TAG, "dynamicCount=" + dynamicCount);
				Log.i(TAG, "page=" + page);
				new DynamicListTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(activity, R.layout.list_zxdt_jz, null);
		mPullListView = (PullToRefreshListView) view
				.findViewById(R.id.ll_activity_zxdt_parent);

		// 加载列表
		contentList();
		return view;
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	/**
	 * 获取动态 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class DynamicListTask extends
			AsyncTask<String, Void, DynamicTeacherList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected DynamicTeacherList doInBackground(String... params) {

			try {
				dynamicTeacherList = AppContext.getApp().getMyDynamic(
						params[0], params[1]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				dynamicTeacherList = null;
			}
			return dynamicTeacherList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(DynamicTeacherList result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dynamicCount = Integer.parseInt(result.getDataCount());
					if (dynamicCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDivider(null);
						mListView.setDividerHeight(0);

					} else if (dynamicCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}

					if (list != null && list.size() > 0) {
						list.addAll(result.getDynamicTeacherList());
						dynamicParentAdapter.notifyDataSetChanged();
					} else {

						list = result.getDynamicTeacherList();
						dynamicParentAdapter = new ListViewDynamicParentAdapter(
								activity, list);
						// 得到实际的ListView
						mListView.setAdapter(dynamicParentAdapter);
					}

				} else {
					UIHelper.ToastMessage(activity, result.getStatusMessage());
					return;
				}
			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(activity);
				}
				return;
			}

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
		}
	}

}
