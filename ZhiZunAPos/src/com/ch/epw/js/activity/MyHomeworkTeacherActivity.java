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
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.adapter.ListViewHomeworkTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Homework;
import com.zhizun.pos.bean.HomeworkTeacherList;

/**
 * 家庭作业 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyHomeworkTeacherActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = MyHomeworkTeacherActivity.class
			.getName();
	Context context;
	HomeworkTeacherList homeworkTeacherList;
	ListViewHomeworkTeacherAdapter listViewHomeworkTeacherAdapter;
	TitleBarView titleBarView;
	Integer dataCount;
	List<Homework> list;
	FrameLayout fl_common_zxhf;

	// RelativeLayout rl_my_notice_teacher_all, rl_my_notice_teacher_waitsend,
	// rl_my_notice_teacher_beensent;
	// View v_my_notice_teacher_divider_all,
	// v_my_notice_teacher_divider_waitsend,
	// v_my_notice_teacher_divider_beensent;

	String type = Constant.STATUS_ALL;// 查看的家庭作业类型，all：全部，task：等待发送，sent：已发送，

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_homework_teacher);
		context = this;
		initView();
		new GetNewCommentsCountTask(context, newReplyMsgBadgeView).execute(
				AppContext.getApp().getToken(), Constant.COMMNETTYPE_JTZY);
		// 注册通知 刷新发件箱列表
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_HOMEWORK_LIST");
		registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		newReplyMsgBadgeView.setTargetView(fl_common_zxhf);
		new GetNewCommentsCountTask(context, newReplyMsgBadgeView).execute(
				AppContext.getApp().getToken(), Constant.COMMNETTYPE_JTZY);
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// rl_my_notice_teacher_all.performClick();
			content();
		}
	};

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zxdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);
		titleBarView.setTitleText(R.string.text_homework_all);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getBtnRight().setBackgroundResource(R.drawable.fadongtai);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SendHomeworkActivity.class);
				startActivity(intent);
				intoAnim();
			}
		});
		mPullListView = (PullToRefreshListView) findViewById(R.id.lv_my_notice_list);

		fl_common_zxhf = (FrameLayout) findViewById(R.id.fl_common_zxhf);
		fl_common_zxhf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						NewCommonCommentsActivity.class);
				intent.putExtra("type", Constant.COMMNETTYPE_JTZY);
				startActivity(intent);
				intoAnim();
			}
		});
		newReplyMsgBadgeView = new BadgeView(context);
		newReplyMsgBadgeView.setBadgeGravity(Gravity.RIGHT
				| Gravity.CENTER_VERTICAL);
		newReplyMsgBadgeView.setBadgeMargin(0, 0, 2, 0);
		newReplyMsgBadgeView.setTargetView(fl_common_zxhf);
		// rl_my_notice_teacher_all = (RelativeLayout)
		// findViewById(R.id.rl_my_notice_teacher_all);
		// rl_my_notice_teacher_waitsend = (RelativeLayout)
		// findViewById(R.id.rl_my_notice_teacher_waitsend);
		// rl_my_notice_teacher_beensent = (RelativeLayout)
		// findViewById(R.id.rl_my_notice_teacher_beensent);
		// v_my_notice_teacher_divider_all =
		// findViewById(R.id.v_my_notice_teacher_divider_all);
		// v_my_notice_teacher_divider_waitsend =
		// findViewById(R.id.v_my_notice_teacher_divider_waitsend);
		// v_my_notice_teacher_divider_beensent =
		// findViewById(R.id.v_my_notice_teacher_divider_beensent);

		// rl_my_notice_teacher_all.setOnClickListener(this);
		// rl_my_notice_teacher_waitsend.setOnClickListener(this);
		// rl_my_notice_teacher_beensent.setOnClickListener(this);
		// rl_my_notice_teacher_all.performClick();
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
				new GetHomeworkListTask().execute(1 + "",
						Constant.LOADDATACOUNT + "", type);

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

				new GetHomeworkListTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "", type);
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------

	}

	/**
	 * 获得家庭作业列表 创建人：李林中 创建日期：2015-1-4 下午4:37:44 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetHomeworkListTask extends
			AsyncTask<String, Void, HomeworkTeacherList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected HomeworkTeacherList doInBackground(String... params) {

			try {
				homeworkTeacherList = AppContext.getApp().getHomeworkList(
						params[0], params[1], params[2]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				homeworkTeacherList = null;

			}
			return homeworkTeacherList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(HomeworkTeacherList result) {

			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(homeworkTeacherList
							.getDataCount());
					if (dataCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
					}
					else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}

					if (list != null && list.size() > 0) {
						list.addAll(result.getHomeworkTeacherList());
						listViewHomeworkTeacherAdapter.notifyDataSetChanged();
					} else {
						list = result.getHomeworkTeacherList();
						listViewHomeworkTeacherAdapter = new ListViewHomeworkTeacherAdapter(
								MyHomeworkTeacherActivity.this, list);
						// 得到实际的ListView
						mListView.setAdapter(listViewHomeworkTeacherAdapter);
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

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode == REQUEST_COLDE && resultCode == RESULT_ACCEPT_COLDE) {
	// list.remove(data.getIntExtra("position", 0));
	// listViewNoticeSendBoxAdapter.notifyDataSetChanged();
	// }
	// }

	@Override
	public void onClick(View v) {
		/*
		 * switch (v.getId()) { case R.id.rl_my_notice_teacher_all:
		 * v_my_notice_teacher_divider_all.setVisibility(View.VISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.INVISIBLE);
		 * type = Constant.STATUS_ALL; content(); break; case
		 * R.id.rl_my_notice_teacher_waitsend:
		 * v_my_notice_teacher_divider_all.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.VISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.INVISIBLE);
		 * type = Constant.STATUS_TASK; content(); break; case
		 * R.id.rl_my_notice_teacher_beensent:
		 * v_my_notice_teacher_divider_all.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.VISIBLE);
		 * type = Constant.STATUS_SENT; content(); break;
		 * 
		 * }
		 */

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		context.unregisterReceiver(broadcastReceiver);
	}
}
