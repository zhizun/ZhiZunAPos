package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class SystemMessageTask extends BaseAsyncTask {

	public SystemMessageTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().systemnum();
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return null;
	}
}

