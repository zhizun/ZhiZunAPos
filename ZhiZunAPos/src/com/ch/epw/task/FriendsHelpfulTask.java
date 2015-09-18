package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

public class FriendsHelpfulTask extends BaseAsyncTask {

	public FriendsHelpfulTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().queryFriendsHelpfulBase(params[0],params[1]);
		} catch (AppException e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}

}
