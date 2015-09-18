package com.ch.epw.task;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

import android.content.Context;

public class FriendsTask extends BaseAsyncTask {

	public FriendsTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		// TODO Auto-generated method stub
				try {
					return AppContext.getApp().getPhoneDetail(params[0],params[1]);
				} catch (AppException e) {
					e.printStackTrace();
					this.e = e;
				}
				return null;
	}


}
