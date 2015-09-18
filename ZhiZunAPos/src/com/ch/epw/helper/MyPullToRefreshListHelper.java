package com.ch.epw.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.ch.epw.task.BaseRefeshTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;

public class MyPullToRefreshListHelper {

	private Context context;
	private PullToRefreshListView mPullListView;
	private List listItems; // 数据集合
	private Class asyncTask;
	private TaskCallBack getDataCallBack;
	//task执行时需要额外参数
	private String[] extraParams;

	private ListView mListView;
	private int nextPage; 	// 即将请求的页码
	private int dataCount; 	// 总记录数

	public MyPullToRefreshListHelper(Context context,
			PullToRefreshListView mPullListView, List listItems) {
		this.context = context;
		this.mPullListView = mPullListView;
		this.listItems = listItems;

		// 上拉加载不可用
		this.mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		this.mPullListView.setScrollLoadEnabled(true);
		mListView = mPullListView.getRefreshableView();
	}
	
	public void setGetDataCallBack(Class asyncTask, String[] params, TaskCallBack getDataCallBack){
		this.asyncTask = asyncTask;
		this.getDataCallBack = getDataCallBack;
		this.extraParams = params;
	}

	protected BaseRefeshTask getNewRefreshTask() {
		BaseRefeshTask newTask = null;
		try {
			newTask = (BaseRefeshTask) asyncTask.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (newTask != null) {
			newTask.setContext(context);
			newTask.setCallBack(getDataCallBack);
		}

		return newTask;
	}

	/**
	 * 首次加载数据
	 */
	protected void onLoad(){
		mPullListView.setHasData(true);
		mPullListView.setHasMoreData(true);
		nextPage = 1;
		if(listItems != null){
			listItems.clear();
			notifyRefreshListView();
		}
		getNewRefreshTask().addExtraParams(extraParams).execute(String.valueOf(1),
				String.valueOf(Constant.LOADDATACOUNT));
	}
	
	/**
	 * 加载更多数据
	 */
	protected void loadMore(){
		if(listItems == null || listItems.size() >= dataCount){
			mPullListView.setHasMoreData(false);
			return;
		}

		getNewRefreshTask().addExtraParams(extraParams).execute(String.valueOf(nextPage),
				String.valueOf(Constant.LOADDATACOUNT));
	}
	
	/**
	 * 设置刷新监听事件并执行第一次刷新
	 */
	public void readyForRefresh() {

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				onLoad();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadMore();
			}
		});

		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
	}
	
	/**
	 * 手工强制刷新
	 */
	public void manualForceRefresh(){
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
	}

	public void setPageCount(String pageCount) {
		this.dataCount = Integer.parseInt(pageCount);
	}
	
	private void notifyRefreshListView(){
		BaseAdapter adapter = null;
		if(mListView.getAdapter() instanceof HeaderViewListAdapter) {
			HeaderViewListAdapter listAdapter=(HeaderViewListAdapter)mListView.getAdapter();
			adapter = (BaseAdapter) listAdapter.getWrappedAdapter();
		}else{
			adapter = (BaseAdapter) mListView.getAdapter();
		}
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public void refreshEnd() {
		
		notifyRefreshListView();

		if (listItems == null || listItems.size() == 0) {
			mPullListView.setHasData(false);
			mListView.setDividerHeight(0);
			mListView.setDivider(null);
		}else{
			if(listItems.size() >= dataCount){
				mPullListView.setHasMoreData(false);
			}
		}

		mPullListView.onPullDownRefreshComplete();
		mPullListView.onPullUpRefreshComplete();
		setLastUpdateTime();
		nextPage++;
	}

	private void setLastUpdateTime() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String text = mDateFormat.format(new Date());
		mPullListView.setLastUpdatedLabel(text);
	}
}
