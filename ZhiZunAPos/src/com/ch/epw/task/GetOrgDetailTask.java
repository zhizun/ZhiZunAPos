package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.OrgInfo;

public class GetOrgDetailTask extends AsyncTask<String, Void, OrgInfo> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;

	public GetOrgDetailTask(Context context, TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected OrgInfo doInBackground(String... params) {
		OrgInfo result = null;
		try {
			result = AppContext.getApp().getOrgDetail(params[0]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(OrgInfo result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				if (taskCallBack != null) {
					taskCallBack.onTaskFinshed(result);
				}
			} else {
				// UIHelper.ToastMessage(context, result.getStatusMessage());
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