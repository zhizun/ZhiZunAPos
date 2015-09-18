package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.activity.NewCommentsDetailActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;

public class DeleteCommonFsiTask extends AsyncTask<String, Void, Result> {
	AppException e;
	private Context context;
	private TaskCallBack taskCallBack;
	private	String type;	//动态类型
	private String typeDesc = "";
	public DeleteCommonFsiTask(String type, Context context, TaskCallBack callBackFunc) {
		this.type = type;
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		try {
			if(Constant.COMMNETTYPE_ZXDT.equals(type)){
				result = AppContext.getApp().deleteDynamic(params[0], params[1]);
				typeDesc = "动态";
			}else if(Constant.COMMNETTYPE_JTZY.equals(type)){
				result = AppContext.getApp().deleteHomework(params[0], params[1]);
				typeDesc = "作业";
			}else if(Constant.COMMNETTYPE_ZXDP.equals(type)){
				result = AppContext.getApp().deleteRemark(params[0], params[1]);
				typeDesc = "点评";
			}else if(Constant.COMMNETTYPE_JZXS.equals(type)){
				result = AppContext.getApp().deletevoice(params[0], params[1]);
				typeDesc = "心声";
			}
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}


	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				UIHelper.ToastMessage(context, typeDesc+"已删除");
				if( taskCallBack != null ){
					taskCallBack.onTaskFinshed();
				}

				//如果是从 启动的，删除后，回到最新回复列表页面
				if(context.getClass().isAssignableFrom(NewCommentsDetailActivity.class)){
					((BaseActivity)context).finish();
					((BaseActivity)context).backAnim();
				}
			} else {
				UIHelper.ToastMessage(context, result.getStatusMessage());
				return;
			}
		} else {
			if (null != e) {
				e.makeToast(context);
			}
			return;
		}
	}
}