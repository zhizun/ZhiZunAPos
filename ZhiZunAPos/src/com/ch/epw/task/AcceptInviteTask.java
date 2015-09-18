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
 * 
 * ===================================================
 */
public class AcceptInviteTask extends AsyncTask<String, Void, Result> {
	AppException e;
	// 用于通过activity中调用
	private BaseActivity activity; // 启动该任务的activity
	private ProgressDialog progress = null; // 进度条

	// 用于通过adapter调用
	private Context context;

	public AcceptInviteTask(Activity activity) {
		this.activity = (BaseActivity) activity;
		this.progress = this.activity.getProgress();
		this.context = (Context) this.activity;
	}

	public AcceptInviteTask(Context context) {
		this.context = context;
	}

	@Override
	protected Result doInBackground(String... params) {
		Result result = null;

		try {
			result = AppContext.getApp().recevieInvite(params[0], params[1],
					params[2], params[3]);
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
				UIHelper.ToastMessage(context, "您已成功接受邀请");

				if (activity != null) {
					// 返回上一个页面
					Intent intent = activity.getIntent();
					intent.putExtra("inviteStatus", 1);
					activity.setResult(Constant.RESULT_ACCEPT_COLDE, intent);
					activity.finish();
					activity.backAnim();
				} else {
					// 不作页面跳转，刷新当前邀请列表
					Intent intent = new Intent();
					intent.setAction("com.ch.epw.REFRESH_PARENT_INVITELIST");
					context.sendBroadcast(intent);
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
