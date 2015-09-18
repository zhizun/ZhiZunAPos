package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 页面刷新task的基类
 * 
 * @author lyc
 *
 */

public abstract class BaseRefeshTask extends AsyncTask<String, Void, BaseBean> implements IAsyncTaskWithCallBack{
	protected AppException e;
	protected Context context;
	protected TaskCallBack taskCallBack;
	
	// 刷新数据默认参数为 页码，每页加载条数
	// 如果该task有其它参数，并且task通过MyPullToRefreshListController调用，则需要通过addExtraParams将参数添加进来
	protected String [] taskExtraParams;

	public abstract void setContext(Context cxt);
	public abstract void setCallBack(TaskCallBack func);
	
	public BaseRefeshTask addExtraParams(String [] params){ taskExtraParams = params; return this;};
	

}
