package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class QueryCourseCommentListTask extends BaseRefeshTask {

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
			if (params.length < 4) {
				return AppContext.getApp().QueryCourseComment(params[0],
						params[1], taskExtraParams[0], taskExtraParams[1],taskExtraParams[2]);
			} else {
				return AppContext.getApp().QueryCourseComment(params[0],
						params[1], params[2], params[3],params[4]);
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
			if (taskCallBack != null) {
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
