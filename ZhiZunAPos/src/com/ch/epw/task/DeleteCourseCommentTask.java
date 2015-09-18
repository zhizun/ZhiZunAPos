package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class DeleteCourseCommentTask extends BaseAsyncServiceTask {

	public DeleteCourseCommentTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(Object... params) {
		try {
			String commentId = (String) params[0];
			return AppContext.getApp().deleteCourseComment(commentId);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return null;
	}
}

