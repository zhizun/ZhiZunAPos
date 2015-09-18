package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class QueryCourseListTask extends BaseRefeshTask {
	
	@Override
	public void setContext(Context cxt) {
		context = cxt;
	}

	@Override
	public void setCallBack(TaskCallBack func) {
		taskCallBack = func;
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			if(params.length >= 8){
				return AppContext.getApp().queryCourseList(
						params[0], params[1], params[2], params[3],params[4],params[5],params[6],params[7]);
			}else{
				if(taskExtraParams != null && taskExtraParams.length == 6){
					return AppContext.getApp().queryCourseList(
							params[0], params[1], taskExtraParams[0], taskExtraParams[1], taskExtraParams[2], taskExtraParams[3],taskExtraParams[4],taskExtraParams[5]);
				}else{
					return AppContext.getApp().queryCourseList(
							params[0], params[1], "", "0","0", "","","");
				}
			}
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(BaseBean result) {
		super.onPostExecute(result);
		if (result != null) {
			if(taskCallBack != null){
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
