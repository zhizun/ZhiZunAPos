package com.ch.epw.task;

import java.io.Serializable;

import com.ch.epw.jz.activity.MyInvitationRquestSelectStudentActivity;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.MyDialog;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.UserChildInfoList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GetUserChildsTask extends
		AsyncTask<String, Void, UserChildInfoList> {
	AppException e;
	private BaseActivity activity; // 启动该任务的activity
	ProgressDialog progress = null; // 进度条
	RecevieMyInvitation recevieMyInvitation;
	MyDialog dialog;

	// 用于通过adapter调用
	private Context context;

	public GetUserChildsTask(Activity activity, RecevieMyInvitation nvitation) {
		this.activity = (BaseActivity) activity;
		this.progress = this.activity.getProgress();
		this.context = (Context) this.activity;
		this.recevieMyInvitation = nvitation;
	}

	public GetUserChildsTask(Context context, RecevieMyInvitation nvitation,
			boolean autoBack) {
		this.context = context;
		this.recevieMyInvitation = nvitation;
	}

	@Override
	protected UserChildInfoList doInBackground(String... params) {
		UserChildInfoList userChildInfoList = null;
		try {
			userChildInfoList = AppContext.getApp().getUserChildInfoList(
					params[0]);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return userChildInfoList;
	}

	@Override
	protected void onPostExecute(UserChildInfoList result) {
		super.onPostExecute(result);
		if (progress != null) {
			progress.dismiss();
		}

		if (null != result) {
			if (result.getStatus().equals("0")) {
				Log.i("TAG", "getUserChildInfoList().size()="
						+ result.getUserChildInfoList().size());
				if (result.getUserChildInfoList().size() <= 0) {
					dialog = new MyDialog(context, R.style.MyDialog, "未发现名为"
							+ recevieMyInvitation.getStuName() + "的宝宝信息，是否创建？",
							new MyDialog.LeaveMyDialogListener() {
								@Override
								public void onClick(View view) {
									switch (view.getId()) {
									case R.id.btn_dialog_selectstudent_yes:
										AcceptInviteTask task = null;
										if (activity != null) {
											task = new AcceptInviteTask(
													activity);
										} else {
											task = new AcceptInviteTask(context);
										}
										task.execute(AppContext.getApp()
												.getToken(),
												recevieMyInvitation
														.getOrgInviteId(), "",
												recevieMyInvitation.getStuId());
										dialog.dismiss();
										break;
									case R.id.btn_dialog_selectstudent_no:
										dialog.dismiss();
										break;
									}
								}
							});
					dialog.show();
				} else {
					StringBuffer sBuffer = new StringBuffer();
					if (null != recevieMyInvitation.getLisStudentClasses()
							&& recevieMyInvitation.getLisStudentClasses()
									.size() > 0) {
						sBuffer = new StringBuffer();
						for (int i = 0; i < recevieMyInvitation
								.getLisStudentClasses().size(); i++) {
							sBuffer.append(recevieMyInvitation
									.getLisStudentClasses().get(i).getName());
							if (i < recevieMyInvitation.getLisStudentClasses()
									.size() - 1) {
								sBuffer.append(",");
							}
						}
					}

					Intent intent = new Intent(context,
							MyInvitationRquestSelectStudentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("userChildInfoList",
							(Serializable) result.getUserChildInfoList());
					bundle.putString("orgclass",
							recevieMyInvitation.getInviteOrgName() + " "
									+ sBuffer);
					bundle.putSerializable("recevieMyInvitation",
							recevieMyInvitation);
					intent.putExtras(bundle);
					context.startActivity(intent);
					if (activity != null) {
						activity.finish();
						activity.intoAnim();
					}
				}
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
