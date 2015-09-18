package com.ch.epw.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;

/**
 * 接受邀请 
 */
public class RefuseInviteTask extends AsyncTask<String, Void, Result> {

	private BaseActivity activity; // 启动该任务的activity
	ProgressDialog progress = null; // 进度条
	AppException e;
	private Context context;

	public RefuseInviteTask(Activity activity) {
		this.activity = (BaseActivity) activity;
		this.progress = this.activity.getProgress();
		this.context = (Context) this.activity;
	}

	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		try {
			result = AppContext.getApp().refuseInvite(params[0], params[1]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (progress != null) {
			progress.dismiss();
		}
		if (null != result) {
			if (result.getStatus().equals("0")) {
				UIHelper.ToastMessage(activity, "您已拒绝邀请");

				Intent intent = activity.getIntent();
				intent.putExtra("inviteStatus", 1);
				activity.setResult(Constant.RESULT_REFUSE_COLDE, intent);
				activity.finish();
				activity.backAnim();
			} else {
				UIHelper.ToastMessage(activity, result.getStatusMessage());
				return;
			}
		} else {
			if (null != e) {
				e.makeToast(activity);
			}
			return;
		}
	}
}
