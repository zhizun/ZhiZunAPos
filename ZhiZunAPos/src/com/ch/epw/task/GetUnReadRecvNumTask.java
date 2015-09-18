package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.UnReadRecvNumList;

public class GetUnReadRecvNumTask extends
		AsyncTask<String, Void, UnReadRecvNumList> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;

	public GetUnReadRecvNumTask(Context context, TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected UnReadRecvNumList doInBackground(String... params) {
		UnReadRecvNumList unReadRecvNumList = null;
		try {
			unReadRecvNumList = AppContext.getApp().getUnReadRecvNum(params[0]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return unReadRecvNumList;
	}

	@Override
	protected void onPostExecute(UnReadRecvNumList result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				if(taskCallBack != null){
					taskCallBack.onTaskFinshed(result);
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
