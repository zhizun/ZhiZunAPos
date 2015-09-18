package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Result;

public class FavTask extends AsyncTask<String, Void, Result> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;
	private	String cancelState;// 取消收藏状态，执行任务时的第4个参数
	
	public FavTask(Context context,	TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		cancelState = params[3];
		try {
			result = AppContext.getApp().fav(params[0], params[1],
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
		if (result != null) {
			if (result.getStatus().equals("0")) {
				if ("1".equals(cancelState)) {		//需要取消收藏
					UIHelper.ToastMessage(context, "取消收藏");
				} else {
					UIHelper.ToastMessage(context, "已收藏");
				}

				if( taskCallBack != null ){
					taskCallBack.onTaskFinshed();
				}
			} else {
				//UIHelper.ToastMessage(context, result.getStatusMessage());
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