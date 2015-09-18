package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 异步task的基类
 * @author lyc
 *
 */

public abstract class BaseAsyncTask extends AsyncTask<String, Void, BaseBean>{
	protected AppException e;
	protected Context context;
	protected TaskCallBack taskCallBack;

	public BaseAsyncTask(Context ctx, TaskCallBack call){
		context = ctx;
		taskCallBack = call;
	}

	@Override
	protected void onPostExecute(BaseBean result) {
		super.onPostExecute(result);
		if (result != null) {
			if(taskCallBack != null){
				taskCallBack.onTaskFinshed(result);
			}
		} else {
			if (null != e && context != null) {
				e.makeToast(context);
			}
			if(taskCallBack != null){
				taskCallBack.onTaskFailed();
			}
			return;
		}
	}
}
