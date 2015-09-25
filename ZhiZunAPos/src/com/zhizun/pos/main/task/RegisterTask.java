package com.zhizun.pos.main.task;

import android.content.Context;

import com.ch.epw.task.BaseAsyncTask;
import com.ch.epw.task.TaskCallBack;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
/**
 * 注册接口
 * @author 李林中
 *
 */
public class RegisterTask extends BaseAsyncTask {

	public RegisterTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().queryRegisterBase(params[0],params[1],params[2],params[3]);
		} catch (AppException e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}

}
