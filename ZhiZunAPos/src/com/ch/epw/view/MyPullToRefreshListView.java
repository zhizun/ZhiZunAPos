package com.ch.epw.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.ch.epw.task.GetFavListTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewFavorListParentAdapter;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Favor;
import com.zhizun.pos.bean.FavorList;

public class MyPullToRefreshListView {
	
	private Activity mActivity;
	private ListView mListView;
	private PullToRefreshListView mPullListView;
	private int nextPage;	//即将请求的页码
	private int pageCount;	//总页数
	private int onceCount;	//每次请求数量

	private MyBaseAdapter listViewMyParentAdapter;
	private List<Favor> listItems;		//数据集合

	public MyPullToRefreshListView(PullToRefreshListView mPullListView,Activity activity){
		this.mPullListView = mPullListView;
		this.mActivity = activity;
		this.mListView = mPullListView.getRefreshableView();
		mListView.setDivider(mActivity.getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(25);
		// 上拉加载不可用
		this.mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		this.mPullListView.setScrollLoadEnabled(true);
		this.onceCount = Constant.LOADDATACOUNT;
	}

	protected AsyncTask<String, ? ,? > getNewRefreshTask(){
		GetFavListTask getFavTask = new GetFavListTask(mActivity, new TaskCallBack(){
			@Override
			public void onTaskFinshed(BaseBean result){
				FavorList favorList = (FavorList)result;
				if(favorList != null && "0".equals(favorList.getStatus())){
					MyPullToRefreshListView.this.setPageCount(favorList.getPageCount());
					if(listItems == null){
						listItems = new ArrayList<Favor>(favorList.getFavList());
						listViewMyParentAdapter = new ListViewFavorListParentAdapter(mActivity, listItems);
						mListView.setAdapter(listViewMyParentAdapter);
					}else{
						listItems.addAll(favorList.getFavList());
					}
				}
				MyPullToRefreshListView.this.refreshEnd();
			}
		});
		
		return getFavTask;
	}
	

	/**
	 * 设置刷新监听事件并执行第一次刷新
	 */
	public void readyForRefresh(){
		
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				listItems = null;
				mPullListView.setHasData(true);
				mPullListView.setHasMoreData(true);
				nextPage = 1;
				getNewRefreshTask().execute(String.valueOf(1), String.valueOf(onceCount));
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getNewRefreshTask().execute(String.valueOf(nextPage),String.valueOf(onceCount));
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
	}
	
	public void setPageCount(int pageCount){
		this.pageCount = pageCount;
	}
	
	public void refreshEnd(){
		if(listViewMyParentAdapter != null){
			listViewMyParentAdapter.notifyDataSetChanged();
		}
		mPullListView.onPullDownRefreshComplete();
		mPullListView.onPullUpRefreshComplete();
		setLastUpdateTime();
		
		if (nextPage >= pageCount) {
			mPullListView.setHasMoreData(false);
		}
		nextPage++;

		if(listItems == null||listItems.size() == 0 ){
			mPullListView.setHasData(false);
		}
	}

	private void setLastUpdateTime(){
		SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String text = mDateFormat.format(new Date());
		mPullListView.setLastUpdatedLabel(text);
	}

}
