package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class GetInputSuggestTask extends BaseAsyncTask {

	public GetInputSuggestTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().getInputSuggest(params[0], params[1],
					params[2]);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return null;
	}
}

