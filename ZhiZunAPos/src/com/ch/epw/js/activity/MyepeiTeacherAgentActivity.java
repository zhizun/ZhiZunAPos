package com.ch.epw.js.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.DateTimePickDialogUtil;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewAgentAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MyAgentList;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.TeacherInfo;
import com.zhizun.pos.bean.TeacherOrgClass;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 我的代理 教师端 创建人：李林中 创建日期：2014-12-15 上午10:08:08 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiTeacherAgentActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	TextView tv_myagent_content;
	TextView tv_myagent_endtime;
	Button btn_myagent_cancelagent;
	ListView lv_myagent_list;
	MyAgentList myAgentList;
	LinearLayout ll_myagent_selectagent;
	RelativeLayout rl_myagent_agented;

	RelativeLayout rl_myagent_selectagent_selectteacher,
			rl_myagent_selectagent_endtime, rl_myagent_selectagent_history;
	TextView tv_myagent_selectagent_agentteacher,
			tv_myagent_selectagent_endtime;

	ListViewAgentAdapter listViewAgentAdapter;
	ArrayList<TeacherOrgClass> teacherAndOrglist;
	Result result;
	TeacherInfo teacherInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myagent);
		context = this;
		// showProgressDialog(context, "",
		// getResources().getString(R.string.load_ing));
		initView();
		initData();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_myagent);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.getIvLeft().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		tv_myagent_content = (TextView) findViewById(R.id.tv_myagent_content);
		tv_myagent_endtime = (TextView) findViewById(R.id.tv_myagent_endtime);
		btn_myagent_cancelagent = (Button) findViewById(R.id.btn_myagent_cancelagent);
		btn_myagent_cancelagent.setOnClickListener(this);
		lv_myagent_list = (ListView) findViewById(R.id.lv_myagent_list);
		ll_myagent_selectagent = (LinearLayout) findViewById(R.id.ll_myagent_selectagent);
		rl_myagent_agented = (RelativeLayout) findViewById(R.id.rl_myagent_agented);

		rl_myagent_selectagent_selectteacher = (RelativeLayout) findViewById(R.id.rl_myagent_selectagent_selectteacher);
		rl_myagent_selectagent_endtime = (RelativeLayout) findViewById(R.id.rl_myagent_selectagent_endtime);
		rl_myagent_selectagent_selectteacher.setOnClickListener(this);
		rl_myagent_selectagent_endtime.setOnClickListener(this);
		tv_myagent_selectagent_agentteacher = (TextView) findViewById(R.id.tv_myagent_selectagent_agentteacher);
		tv_myagent_selectagent_endtime = (TextView) findViewById(R.id.tv_myagent_selectagent_endtime);
		rl_myagent_selectagent_history = (RelativeLayout) findViewById(R.id.rl_myagent_selectagent_history);
		rl_myagent_selectagent_history.setOnClickListener(this);
	}

	private void initData() {
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		new GetUserDelegationTask().execute();

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
		switch (v.getId()) {
		case R.id.rl_myagent_selectagent_selectteacher:
			Intent intentMyagent = new Intent(context,
					SingleSelectTeacherActivity.class);
			intentMyagent.putExtra("teacherAndOrglist", teacherAndOrglist);
			startActivityForResult(intentMyagent,
					Constant.MYAGENT_SELCET_TEACHER);

			intoAnim();
			break;
		case R.id.rl_myagent_selectagent_endtime:
			dateTimeDialog();
			break;
		case R.id.btn_myagent_cancelagent:

			ActionSheet.showSheet(context, "确定", "返回",
					new ActionSheet.OnActionSheetSelected() {
						@Override
						public void onClick(View view) {
							if (view.getId() == R.id.actionsheet_content) {
								if (null != myAgentList
										&& myAgentList
												.getAgentStatusProgresseList()
												.size() > 0) {
									new CancelDelegationTask()
											.execute(myAgentList
													.getAgentStatusProgresseList()
													.get(0).getDelegationId());
								} else {
									UIHelper.ToastMessage(context,
											"找不到您要取消的代理信息");
								}
							}
						}
					}, null);

			break;
		case R.id.rl_myagent_selectagent_history:
			Intent intent_History = new Intent(context,
					MyepeiTeacherAgentHistoryActivity.class);
			startActivity(intent_History);
			intoAnim();
			break;
		}
	}

	private void dateTimeDialog() {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				MyepeiTeacherAgentActivity.this, tv_myagent_selectagent_endtime
						.getText().toString().trim());
		dateTimePicKDialog.dateTimePicKDialog(tv_myagent_selectagent_endtime,
				Constant.DATETIME_PICK_YYYYMMDD, Constant.DATETIME_PICK_HHMMSS);
	}

	/**
	 * 获取我的委托信息 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetUserDelegationTask extends
			AsyncTask<Void, Void, MyAgentList> {
		AppException e;

		protected MyAgentList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				myAgentList = ((AppContext) getApplication())
						.getUserDelegation();
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppException.network(e);
			}
			return myAgentList;
		}

		@Override
		protected void onPostExecute(MyAgentList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != progress) {
				progress.dismiss();
			}
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					if (null == result.getAgentStatusProgresseList()
							|| result.getAgentStatusProgresseList().size() == 0) {
						rl_myagent_agented.setVisibility(View.GONE);
						lv_myagent_list.setVisibility(View.GONE);
						ll_myagent_selectagent.setVisibility(View.VISIBLE);
						tv_myagent_selectagent_agentteacher.setText("");
						tv_myagent_selectagent_endtime.setText("");
						titleBarView.setRightText(getResources().getString(
								R.string.sure));
						titleBarView.getRightTextView().setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (TextUtils
												.isEmpty(tv_myagent_selectagent_agentteacher
														.getText().toString()
														.trim())) {
											UIHelper.ToastMessage(context,
													"请选择代课老师");
											return;
										}
										if (TextUtils
												.isEmpty(tv_myagent_selectagent_endtime
														.getText().toString()
														.trim())) {
											UIHelper.ToastMessage(context,
													"请选择截止日期");
											return;
										}
										if (null != teacherInfo) {

											// agentUserId, agentTeaId, endTime
											showProgressDialog(
													context,
													"",
													getResources()
															.getString(
																	R.string.submit_ing));
											new SaveDelegationTask().execute(
													teacherInfo.getUserId(),
													teacherInfo.getTeachId(),
													tv_myagent_selectagent_endtime
															.getText()
															.toString().trim());
										} else {
											UIHelper.ToastMessage(context,
													"请选择代课老师");
											return;
										}
									}
								});
					} else if (null != result.getAgentStatusProgresseList()
							|| result.getAgentStatusProgresseList().size() > 0) {

						titleBarView.setRightText(getResources().getString(
								R.string.text_history));
						titleBarView.getRightTextView().setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent intent_History = new Intent(
												context,
												MyepeiTeacherAgentHistoryActivity.class);
										startActivity(intent_History);
										intoAnim();
									}
								});

						if (AppContext
								.getApp()
								.getUserLoginSharedPre()
								.getUserInfo()
								.getUserId()
								.equals(result.getAgentStatusProgresseList()
										.get(0).getClientUserId())) {// 我是被代理人
							rl_myagent_agented.setVisibility(View.VISIBLE);
							lv_myagent_list.setVisibility(View.GONE);
							ll_myagent_selectagent.setVisibility(View.GONE);
							tv_myagent_content.setText("您目前正在委托 "
									+ result.getAgentStatusProgresseList()
											.get(0).getAgentTeaName()
									+ "老师 代理班级事务管理");
							tv_myagent_endtime.setText(result
									.getAgentStatusProgresseList().get(0)
									.getEndTime());

						}
						if (AppContext
								.getApp()
								.getUserLoginSharedPre()
								.getUserInfo()
								.getUserId()
								.equals(result.getAgentStatusProgresseList()
										.get(0).getAgentUserId())) {// 我是代理人
							rl_myagent_agented.setVisibility(View.GONE);
							lv_myagent_list.setVisibility(View.VISIBLE);
							ll_myagent_selectagent.setVisibility(View.GONE);
							listViewAgentAdapter = new ListViewAgentAdapter(
									context,
									result.getAgentStatusProgresseList());
							lv_myagent_list.setAdapter(listViewAgentAdapter);
						}
					}
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.MYAGENT_SELCET_TEACHER
				&& resultCode == Constant.SEND_NOTICE_SELCET_SUTDENT) {
			teacherAndOrglist = (ArrayList<TeacherOrgClass>) data
					.getSerializableExtra("teacherAndOrglist");
			StringBuffer sbBuffer = new StringBuffer();
			String nameListStringShort = "";
			if (null != teacherAndOrglist && teacherAndOrglist.size() > 0) {
				TeacherOrgClass sClass = null;

				for (int i = 0; i < teacherAndOrglist.size(); i++) {
					sClass = teacherAndOrglist.get(i);
					for (int j = 0; j < sClass.getTeacherInfoList().size(); j++) {
						if (sClass.getTeacherInfoList().get(j).getCheckState()) {
							teacherInfo = sClass.getTeacherInfoList().get(j);
						}
					}
				}
				if (null != teacherInfo) {

					if (teacherInfo.getCheckState()) {
						tv_myagent_selectagent_agentteacher.setText(teacherInfo
								.getName());
					} else {
						tv_myagent_selectagent_agentteacher.setText("");
					}
				}

			}
		}
	}

	/**
	 * 发送代理 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class SaveDelegationTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().saveDelegation(params[0],
						params[1], params[2]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "您已成功设置代理老师");
					new GetUserDelegationTask().execute();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}

	/**
	 * 取消代理代理 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class CancelDelegationTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().cancelDelegation(params[0]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "您已成功取消代理设置");
					new GetUserDelegationTask().execute();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
