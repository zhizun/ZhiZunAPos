package com.zhizun.pos.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.activity.NewCommentsDetailActivity;
import com.zhizun.pos.adapter.ListViewNewCommonCommentsAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.NewCommonCommentsList;

/**
 * 最新回复 通用 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NewCommonCommentsActivity extends BaseActivity {

	protected static final String TAG = NewCommonCommentsActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;

	ListViewNewCommonCommentsAdapter listViewNewCommentsAdapter;
	Integer dataCount;
	List<NewCommonComments> list;
	NewCommonCommentsList newCommentsList;
	private String type; // 动态类型。老师端获取最新回复列表时按照动态类型查询，家长端不区分动态类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_new_comment);
		context = this;
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		if (type == null) {
			type = "";
		}
		initView();
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

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new GetCommonNewCommentsListTask().execute(AppContext.getApp()
						.getToken(), 1 + "", Constant.LOADDATACOUNT + "", type);
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
				new GetCommonNewCommentsListTask().execute(AppContext.getApp()
						.getToken(), mCurPage + "",
						Constant.LOADDATACOUNT + "", type);
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------
	}

	@SuppressLint("ResourceAsColor")
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_common_new_comment);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.latest_reply);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		// 下拉刷新控件
		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_activity_common_new_comment);
	}

	/**
	 * 获取最新回复 通用 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetCommonNewCommentsListTask extends
			AsyncTask<String, Void, NewCommonCommentsList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected NewCommonCommentsList doInBackground(String... params) {

			try {
				newCommentsList = AppContext.getApp()
						.getLatestCommentReplyList(params[0], params[1],
								params[2], params[3]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				newCommentsList = null;
			}
			return newCommentsList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(NewCommonCommentsList result) {

			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (result != null) {

				if (result.getStatus().equals("0")) {
					// 发送广播通知 来刷新动态最新回复的小红点 减少一条
					Intent intent = new Intent();
					intent.setAction("com.ch.epw.REFRESH_SETREAD_NEWCOMMENTCOUNT_LIST");
					sendBroadcast(intent);

					dataCount = Integer.parseInt(result.getDataCount());
					if (dataCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
					} else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (dataCount > 0) {
						mListView
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										Log.i(TAG, "position=" + position);
										/*
										 * Class clazzClass = null; if
										 * (list.get(position).getType()
										 * .equals(Constant.COMMNETTYPE_ZXDT)) {
										 * clazzClass =
										 * DynamicNewCommentsDetailActivity
										 * .class; } if
										 * (list.get(position).getType()
										 * .equals(Constant.COMMNETTYPE_JZXS)) {
										 * clazzClass =
										 * VoiceNewCommentsDetailActivity.class;
										 * }
										 * 
										 * if (clazzClass != null) { Intent
										 * intent = new Intent(context,
										 * clazzClass);
										 * intent.putExtra("msgFromTag", 0);
										 * intent.putExtra("newCommonComments",
										 * list.get(position));
										 * startActivity(intent); intoAnim(); }
										 */

										Intent intent = new Intent(context,
												NewCommentsDetailActivity.class);
										intent.putExtra("msgFromTag", 0);
										intent.putExtra("newCommonComments",
												list.get(position));
										startActivity(intent);
										intoAnim();
									}
								});
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getNewCommonCommentsList());
						listViewNewCommentsAdapter.notifyDataSetChanged();
					} else {
						list = result.getNewCommonCommentsList();
						listViewNewCommentsAdapter = new ListViewNewCommonCommentsAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView.setAdapter(listViewNewCommentsAdapter);
					}

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

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
