package com.zhizun.pos.fragment.mymessage;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import com.ch.epw.task.SystemMessageTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.MySystemMessageDeatilActivity;
import com.zhizun.pos.adapter.SystemMessageListViewAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseFragment;
import com.zhizun.pos.bean.MySystemMessageBean;
import com.zhizun.pos.bean.MySystemMessageList;
/**
 * 我的系统消息界面
 * @author lilinzhong
 *
 * 2015-7-14下午4:39:47
 */
public class SystemMessageListFragment extends BaseFragment {
	private final static String TAG = SystemMessageListFragment.class.getName();
	Activity activity;

	private MySystemMessageList mySystemMessageList;
	private RadioButton radiobutton_cour;
	private List<MySystemMessageBean> list;
	Integer dynamicCount;
	private SystemMessageListViewAdapter systemMessageAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.mymessage_system_list_fragment, null);
			mPullListView = (PullToRefreshListView) view.findViewById(R.id.ll_my_message);
			radiobutton_cour = (RadioButton) activity.findViewById(R.id.radiobutton_cour);
			contentList();
			//条用系统消息数量接口
		     new SystemMessageTask(activity, new TaskCallBack() {}).execute();
		return view;
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
		mListView.setDividerHeight(2);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				MySystemMessageBean message = (MySystemMessageBean)parent.getAdapter().getItem(position);
				if(message == null){
					return;
				}

				message.setIsRead("1");
				systemMessageAdapter.notifyDataSetChanged();
					
				//调用系统消息数量接口
				new SystemMessageTask(activity, new TaskCallBack() {}).execute();
				
				Intent intent = new Intent();
				intent.putExtra("receiveId",list.get(position).getRecieverId());
		      	intent.setClass(activity,MySystemMessageDeatilActivity.class);
		      	startActivity(intent);  
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
				new MyMessageListTask().execute(1 + "", Constant.LOADDATACOUNT
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
				new MyMessageListTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
		// 下拉刷新和上啦加载代码结束=------
	}
	/**
	 * 获取我的获取我的系统消息
	 */
	private class MyMessageListTask extends
			AsyncTask<String, Void, MySystemMessageList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected MySystemMessageList doInBackground(String... params) {

			try {
				mySystemMessageList = AppContext.getApp().getMessageList(
						params[0], params[1]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				mySystemMessageList = null;
			}
			return mySystemMessageList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(MySystemMessageList result) {

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
						list.addAll(result.getMySystemMessageBean());
						systemMessageAdapter.notifyDataSetChanged();
					} else {

						list = result.getMySystemMessageBean();
						systemMessageAdapter = new SystemMessageListViewAdapter(
								activity, list);
						// 得到实际的ListView
						mListView.setAdapter(systemMessageAdapter);
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
