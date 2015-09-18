package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class GetFsiDataDetailTask extends AsyncTask<String, Void, BaseBean> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;
	
	public GetFsiDataDetailTask(Context context, TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().getFsiBeanDataDetail1(
					params[0], params[1], params[2]);
		} catch (AppException e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}

	// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
	@Override
	protected void onPostExecute(BaseBean result) {

		super.onPostExecute(result);

		if (result != null) {
			if(taskCallBack!=null){
				taskCallBack.onTaskFinshed(result);
			}
		} else {
			if (null != e) {
				e.makeToast(context);
			}
			return;
		}
	}
}
