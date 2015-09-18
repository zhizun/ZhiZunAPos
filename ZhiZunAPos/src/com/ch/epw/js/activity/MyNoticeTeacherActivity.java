package com.ch.epw.js.activity;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.adapter.ListViewNoticeReceiveBoxAdapter;
import com.zhizun.pos.adapter.ListViewNoticeSendBoxAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.NoticeBox;
import com.zhizun.pos.bean.NoticeBoxList;

/**
 * 我的通知公告 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyNoticeTeacherActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = MyNoticeTeacherActivity.class.getName();
	Context context;
	NoticeBoxList noticeBoxList;
	ListViewNoticeReceiveBoxAdapter listViewNoticeReceiveBoxAdapter;
	ListViewNoticeSendBoxAdapter listViewNoticeSendBoxAdapter;
	Button btn_myinvitation_title_bar_receive, btn_myinvitation_title_bar_send;
	ImageView title_iv_left;
	ImageView title_iv_right;
	FrameLayout fl_area_left;
	FrameLayout fl_area_right;
	Integer dataCount;
	ArrayList<NoticeBox> list;
	// LinearLayout ll_my_notice_teacher_menu;
	// RelativeLayout rl_my_notice_teacher_all, rl_my_notice_teacher_waitsend,
	// rl_my_notice_teacher_beensent;
	// View v_my_notice_teacher_divider_all,
	// v_my_notice_teacher_divider_waitsend,
	// v_my_notice_teacher_divider_beensent;
	Integer REQUEST_COLDE = 1;// 请求码
	public static Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码

	Integer noticeBoxTag = 0;// 通知标志 0代表收件箱，1代表发件箱

	String status = "";// 0 未发送； 1： 已发送； 不传：所有

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_notice_teacher);
		context = this;
		initView();
		// 注册通知 刷新发件箱列表
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_SENDNOTICEBOX_LIST");
		registerReceiver(broadcastReceiver, filter);
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 如果发送按钮被选中 那么就模拟点击 发件箱里面的全部 否则就模拟点击发送按钮
			if (!btn_myinvitation_title_bar_send.isEnabled()) {
				// rl_my_notice_teacher_all.performClick();
				content();
			} else {
				btn_myinvitation_title_bar_send.performClick();
			}

		}
	};

	private void initView() {
		mPullListView = (PullToRefreshListView) findViewById(R.id.lv_my_notice_list);
		btn_myinvitation_title_bar_receive = (Button) findViewById(R.id.btn_myinvitation_title_bar_receive);
		btn_myinvitation_title_bar_receive
				.setText(R.string.text_noticebox_receive);
		btn_myinvitation_title_bar_send = (Button) findViewById(R.id.btn_myinvitation_title_bar_send);
		btn_myinvitation_title_bar_send.setText(R.string.text_noticebox_send);
		// ll_my_notice_teacher_menu = (LinearLayout)
		// findViewById(R.id.ll_my_notice_teacher_menu);
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
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);

		fl_area_left = (FrameLayout) findViewById(R.id.fl_area_left);
		fl_area_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		title_iv_right = (ImageView) findViewById(R.id.title_iv_right);
		title_iv_right.setImageResource(R.drawable.fadongtai);
		fl_area_right = (FrameLayout) findViewById(R.id.fl_area_right);
		fl_area_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SendNoticeActivity.class);
				startActivity(intent);
				intoAnim();
			}
		});
		btn_myinvitation_title_bar_receive
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (btn_myinvitation_title_bar_receive.isEnabled()) {
							btn_myinvitation_title_bar_receive
									.setEnabled(false);
							btn_myinvitation_title_bar_send.setEnabled(true);
							// ll_my_notice_teacher_menu.setVisibility(View.GONE);

							noticeBoxTag = 0;
							list = null;
							content();
						}
					}
				});
		btn_myinvitation_title_bar_send
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (btn_myinvitation_title_bar_send.isEnabled()) {
							btn_myinvitation_title_bar_send.setEnabled(false);
							btn_myinvitation_title_bar_receive.setEnabled(true);
							// ll_my_notice_teacher_menu
							// .setVisibility(View.VISIBLE);
							noticeBoxTag = 1;
							list = null;
							content();
						}
					}
				});
		btn_myinvitation_title_bar_receive.performClick();
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
		mListView.setOnItemClickListener(null);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				switch (noticeBoxTag) {
				case 0:// 收件箱
					new QueryInBoxListTask().execute(1 + "",
							Constant.LOADDATACOUNT + "");
					break;
				case 1:// 发件箱
					new QueryOutBoxListTask().execute(1 + "",
							Constant.LOADDATACOUNT + "", status);
					break;
				}

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

				switch (noticeBoxTag) {
				case 0:// 收件箱
					new QueryInBoxListTask().execute(mCurPage + "",
							Constant.LOADDATACOUNT + "");
					break;
				case 1:// 发件箱
					new QueryOutBoxListTask().execute(mCurPage + "",
							Constant.LOADDATACOUNT + "", status);
					break;
				}
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------

	}

	/**
	 * 获得发件箱列表 创建人：李林中 创建日期：2015-1-4 下午4:37:44 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class QueryOutBoxListTask extends
			AsyncTask<String, Void, NoticeBoxList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected NoticeBoxList doInBackground(String... params) {

			try {
				noticeBoxList = AppContext.getApp().queryOutBoxList(params[0],
						params[1], params[2]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				noticeBoxList = null;
			}
			return noticeBoxList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(NoticeBoxList result) {

			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(noticeBoxList.getDataCount());
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

										Intent intent_sendbox = new Intent(
												context,
												NoticeSendBoxDetailActivity.class);
										intent_sendbox.putExtra("noticeBox",
												list.get(position));
										intent_sendbox.putExtra("position",
												position);
										startActivityForResult(intent_sendbox,
												REQUEST_COLDE);
										intoAnim();

									}
								});
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getNoticeBoxList());
						listViewNoticeSendBoxAdapter.notifyDataSetChanged();
					} else {
						list = result.getNoticeBoxList();
						listViewNoticeSendBoxAdapter = new ListViewNoticeSendBoxAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView.setAdapter(listViewNoticeSendBoxAdapter);
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

	/**
	 * 获得收件箱列表 创建人：李林中 创建日期：2015-1-4 下午4:37:44 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class QueryInBoxListTask extends
			AsyncTask<String, Void, NoticeBoxList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected NoticeBoxList doInBackground(String... params) {

			try {
				noticeBoxList = AppContext.getApp().queryInBoxList(params[0],
						params[1]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				noticeBoxList = null;
			}
			return noticeBoxList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(NoticeBoxList result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(noticeBoxList.getDataCount());

					if (dataCount == 0) {
						mPullListView.setHasData(false);
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

										Intent intent_receivebox = new Intent(
												context,
												NoticeReceiveBoxDetailActivity.class);
										intent_receivebox.putExtra(
												"receiveBoxList", list);
										intent_receivebox.putExtra("position",
												position);
										list.get(position).setIsRead(
												Constant.NOTICE_READ);
										listViewNoticeReceiveBoxAdapter
												.notifyDataSetChanged();
										startActivity(intent_receivebox);
										intoAnim();

									}
								});
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getNoticeBoxList());
						listViewNoticeReceiveBoxAdapter.notifyDataSetChanged();
					} else {
						list = result.getNoticeBoxList();
						listViewNoticeReceiveBoxAdapter = new ListViewNoticeReceiveBoxAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView.setAdapter(listViewNoticeReceiveBoxAdapter);
					}

					mPullListView.onPullDownRefreshComplete();
					mPullListView.onPullUpRefreshComplete();
					mPullListView.setHasMoreData(hasMoreData);
					setLastUpdateTime();
				} else {
					if (result.getStatus().equals("1003")) {
						UIHelper.ToastMessage(context,
								result.getStatusMessage() + ",请重新登录！");
						Intent intent = new Intent(context, LoginActivity.class);
						startActivity(intent);
						MyNoticeTeacherActivity.this.finish();
						intoAnim();
						return;
					}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_COLDE && resultCode == RESULT_ACCEPT_COLDE) {
			list.remove(data.getIntExtra("position", 0));
			listViewNoticeSendBoxAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		/*
		 * switch (v.getId()) { case R.id.rl_my_notice_teacher_all:
		 * v_my_notice_teacher_divider_all.setVisibility(View.VISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.INVISIBLE);
		 * status = ""; content(); break; case
		 * R.id.rl_my_notice_teacher_waitsend:
		 * v_my_notice_teacher_divider_all.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.VISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.INVISIBLE);
		 * status = "0"; content(); break; case
		 * R.id.rl_my_notice_teacher_beensent:
		 * v_my_notice_teacher_divider_all.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_waitsend.setVisibility(View.INVISIBLE);
		 * v_my_notice_teacher_divider_beensent.setVisibility(View.VISIBLE);
		 * status = "1"; content(); break;
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
