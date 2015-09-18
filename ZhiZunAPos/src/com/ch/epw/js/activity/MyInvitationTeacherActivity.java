package com.ch.epw.js.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.ch.epw.jz.activity.MyInvitationRquestParentActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.adapter.ListViewMyInvitationRecevieTeacherAdapter;
import com.zhizun.pos.adapter.ListViewMyInvitationSendTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.ReceiveMyInvitationList;
import com.zhizun.pos.bean.RecevieMyInvitation;

/**
 * 我的邀请教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationTeacherActivity extends BaseActivity {

	protected static final String TAG = MyInvitationTeacherActivity.class
			.getName();
	Context context;
	ReceiveMyInvitationList receiveMyInvitationList;
	ListViewMyInvitationRecevieTeacherAdapter listViewMyInvitationRecevieTeacherAdapter;
	ListViewMyInvitationSendTeacherAdapter listViewMyInvitationSendTeacherAdapter;
	Button btn_myinvitation_title_bar_receive, btn_myinvitation_title_bar_send;
	ImageView title_iv_left;
	ImageView title_iv_right;
	FrameLayout fl_area_left;
	Integer dataCountRecevieTeacher;
	List<RecevieMyInvitation> list;
	Integer REQUEST_COLDE = 1;// 结果返回码
	Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码 接受邀请
	Integer RESULT_REFUSE_COLDE = 2;// 结果返回码 拒绝邀请
	Integer inviteTag = 0;// 邀请和发送标志 0代表收到邀请，1代表发送的邀请

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invitation_teacher);
		context = this;

		// 注册通知 刷新列表
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_PARENT_INVITELIST");
		registerReceiver(broadcastReceiver, filter);

		initView();
	}

	private void initView() {
		mPullListView = (PullToRefreshListView) findViewById(R.id.lv_my_invitation_list);
		btn_myinvitation_title_bar_receive = (Button) findViewById(R.id.btn_myinvitation_title_bar_receive);
		btn_myinvitation_title_bar_send = (Button) findViewById(R.id.btn_myinvitation_title_bar_send);
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		fl_area_left = (FrameLayout) findViewById(R.id.fl_area_left);
		fl_area_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		title_iv_right = (ImageView) findViewById(R.id.title_iv_right);
		title_iv_right.setVisibility(View.GONE);
		btn_myinvitation_title_bar_receive
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (btn_myinvitation_title_bar_receive.isEnabled()) {
							btn_myinvitation_title_bar_receive
									.setEnabled(false);
							btn_myinvitation_title_bar_send.setEnabled(true);
							inviteTag = 0;
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
							inviteTag = 1;
							content();
						}
					}
				});
		btn_myinvitation_title_bar_receive.performClick();
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
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (inviteTag) {
				case 0:// 收到的邀请
					RecevieMyInvitation invite = list.get(position);
					Intent intent = null;
					// 邀请老师
					if (Constant.INVITE_TYPE_TO_TEACHER.equals(invite.getType())) {
						intent = new Intent(context,
								MyInvitationRquestTeacherActivity.class);
					}
					// 邀请家长
					else if (Constant.INVITE_TYPE_TO_PARENT.equals(invite
							.getType())) {
						intent = new Intent(context,
								MyInvitationRquestParentActivity.class);
					} else {
						return;
					}

					intent.putExtra("recevieMyInvitation", invite);
					startActivityForResult(intent, REQUEST_COLDE);
					intoAnim();
					break;
				case 1:// 发出的邀请
					if (list.get(position).getType().equals("0")) {// 家长
						Intent intentParent = new Intent(context,
								MyInvitationSendParentTeacherActivity.class);
						intentParent.putExtra("recevieMyInvitation",
								list.get(position));
						startActivity(intentParent);
						intoAnim();
					}
					if (list.get(position).getType().equals("1")) {// 老师
						Intent intentTeacher = new Intent(context,
								MyInvitationSendTeacherActivity.class);
						intentTeacher.putExtra("recevieMyInvitation",
								list.get(position));
						startActivity(intentTeacher);
						intoAnim();
					}
					break;

				}

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
				SharedPreferences sp = AppContext.getApp().getSharedPref(
						"appToken");
				String token = sp.getString("token", "");
				switch (inviteTag) {
				case 0:
					new GetUserInviteTask().execute(token, 1 + "",
							Constant.LOADDATACOUNT + "");
					break;
				case 1:
					new GetUserSendInviteTask().execute(token, 1 + "",
							Constant.LOADDATACOUNT + "");
					break;
				}

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				int page = (int) (Math.ceil(dataCountRecevieTeacher
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
				Log.i(TAG, "dynamicCount=" + dataCountRecevieTeacher);
				Log.i(TAG, "page=" + page);
				SharedPreferences sp = AppContext.getApp().getSharedPref(
						"appToken");
				String token = sp.getString("token", "");

				switch (inviteTag) {
				case 0:
					new GetUserInviteTask().execute(token, mCurPage + "",
							Constant.LOADDATACOUNT + "");
					break;
				case 1:
					new GetUserSendInviteTask().execute(token, mCurPage + "",
							Constant.LOADDATACOUNT + "");
					break;
				}
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------

	}

	private class GetUserInviteTask extends
			AsyncTask<String, Void, ReceiveMyInvitationList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected ReceiveMyInvitationList doInBackground(String... params) {

			try {
				receiveMyInvitationList = AppContext.getApp()
						.getReceiveMyInvitationList(params[0], params[1],
								params[2]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				receiveMyInvitationList = null;
			}
			return receiveMyInvitationList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(ReceiveMyInvitationList result) {

			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCountRecevieTeacher = Integer
							.parseInt(receiveMyInvitationList.getDataCount());
					if (list != null && list.size() > 0) {
						list.addAll(result.getRecevieMyInvitationList());
					} else {
						list = result.getRecevieMyInvitationList();
					}
					if (dataCountRecevieTeacher == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
						listViewMyInvitationRecevieTeacherAdapter = new ListViewMyInvitationRecevieTeacherAdapter(
								MyInvitationTeacherActivity.this, list);
						mListView
								.setAdapter(listViewMyInvitationRecevieTeacherAdapter);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					}else if (dataCountRecevieTeacher == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (list.size() <= Constant.LOADDATACOUNT) {
						listViewMyInvitationRecevieTeacherAdapter = new ListViewMyInvitationRecevieTeacherAdapter(
								MyInvitationTeacherActivity.this, list);
						// listViewMyInvitationRecevieTeacherAdapter = new
						// ListViewMyInvitationRecevieTeacherAdapter(
						// getApplicationContext(), list);
						// 得到实际的ListView
						mListView
								.setAdapter(listViewMyInvitationRecevieTeacherAdapter);
					} else {
						listViewMyInvitationRecevieTeacherAdapter
								.notifyDataSetChanged();
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

	private class GetUserSendInviteTask extends
			AsyncTask<String, Void, ReceiveMyInvitationList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected ReceiveMyInvitationList doInBackground(String... params) {

			try {
				receiveMyInvitationList = AppContext.getApp()
						.getSendReceiveMyInvitationList(params[0], params[1],
								params[2]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				receiveMyInvitationList = null;
			}
			return receiveMyInvitationList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(ReceiveMyInvitationList result) {

			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCountRecevieTeacher = Integer
							.parseInt(receiveMyInvitationList.getDataCount());
					if (list != null && list.size() > 0) {
						list.addAll(result.getRecevieMyInvitationList());
					} else {
						list = result.getRecevieMyInvitationList();
					}
					if (dataCountRecevieTeacher == 0) {
						mPullListView.setHasData(false);
						listViewMyInvitationSendTeacherAdapter = new ListViewMyInvitationSendTeacherAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView
								.setAdapter(listViewMyInvitationSendTeacherAdapter);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					}else if (dataCountRecevieTeacher == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (list.size() <= Constant.LOADDATACOUNT) {
						listViewMyInvitationSendTeacherAdapter = new ListViewMyInvitationSendTeacherAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView
								.setAdapter(listViewMyInvitationSendTeacherAdapter);
					} else {
						listViewMyInvitationSendTeacherAdapter
								.notifyDataSetChanged();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_COLDE && resultCode == RESULT_ACCEPT_COLDE) {
			content();
		}
		if (requestCode == REQUEST_COLDE && resultCode == RESULT_REFUSE_COLDE) {
			content();
		}
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			content();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		context.unregisterReceiver(broadcastReceiver);
	}
}
