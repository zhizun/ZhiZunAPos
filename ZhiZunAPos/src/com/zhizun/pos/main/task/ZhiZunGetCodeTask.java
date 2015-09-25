package com.zhizun.pos.main.task;

import android.content.Context;

import com.ch.epw.task.BaseAsyncTask;
import com.ch.epw.task.TaskCallBack;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
/**
 * 请求秘钥
 * @author 李林中
 *
 */
public class ZhiZunGetCodeTask extends BaseAsyncTask {

	public ZhiZunGetCodeTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().queryZhiZunGetCodeBase(params[0]);
		} catch (AppException e) {
			e.printStackTrace();
			this.e = e;
		}
		return null;
	}

}
