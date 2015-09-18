package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class GetOrgIntroDetailTask extends BaseAsyncTask {
	
	public GetOrgIntroDetailTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().getOrgIntroDetail(params[0]);
		} catch (AppException e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}
}
