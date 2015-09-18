package com.ch.epw.js.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ch.epw.task.GetNewCommentsCountTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.NewCommonCommentsActivity;
import com.zhizun.pos.adapter.ListViewAgentHistoryAdapter;
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.adapter.ListViewHomeworkTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.AgentHistoryList;
import com.zhizun.pos.bean.AgentStatusProgress;
import com.zhizun.pos.bean.Homework;
import com.zhizun.pos.bean.HomeworkTeacherList;

/**
 * 代理历史 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiTeacherAgentHistoryActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = MyepeiTeacherAgentHistoryActivity.class
			.getName();
	Context context;
	AgentHistoryList agentHistoryList;
	ListViewAgentHistoryAdapter listViewAgentHistoryAdapter;
	TitleBarView titleBarView;
	Integer dataCount;
	List<AgentStatusProgress> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_agenthistory_teacher);
		context = this;
		initView();

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zxdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.text_history_list);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		mPullListView = (PullToRefreshListView) findViewById(R.id.lv_my_notice_list);
		content();
	}

	private void content() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件

		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		// listview每行间隔
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(25);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new QueryDelegationTask().execute(1 + "",
						Constant.LOADDATACOUNT + "");

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				int page = (int) (Math.ceil(dataCount
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
				Log.i(TAG, "dataCount=" + dataCount);
				Log.i(TAG, "page=" + page);

				new QueryDelegationTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------

	}

	/**
	 * 代理历史记录 创建人：李林中 创建日期：2015-1-4 下午4:37:44 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class QueryDelegationTask extends
			AsyncTask<String, Void, AgentHistoryList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected AgentHistoryList doInBackground(String... params) {

			try {
				agentHistoryList = AppContext.getApp().queryDelegation(
						params[0], params[1]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				agentHistoryList = null;

			}
			return agentHistoryList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(AgentHistoryList result) {

			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(agentHistoryList
							.getDataCount());
					if (dataCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
					} else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}

					if (list != null && list.size() > 0) {
						list.addAll(result.getAgentStatusProgressList());
						listViewAgentHistoryAdapter.notifyDataSetChanged();
					} else {
						list = result.getAgentStatusProgressList();
						listViewAgentHistoryAdapter = new ListViewAgentHistoryAdapter(
								MyepeiTeacherAgentHistoryActivity.this, list);
						// 得到实际的ListView
						mListView.setAdapter(listViewAgentHistoryAdapter);
					}

					mPullListView.onPullDownRefreshComplete();
					mPullListView.onPullUpRefreshComplete();
					mPullListView.setHasMoreData(hasMoreData);
					setLastUpdateTime();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}

			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
