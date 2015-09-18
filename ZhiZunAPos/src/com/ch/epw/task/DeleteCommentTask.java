package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Result;

/**
 * 删除评论 创建人：lyc 创建日期：2015-3-2 上午10:56:07 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class DeleteCommentTask extends AsyncTask<String, Void, Result> {
	Context context;
	AppException e;
	private TaskCallBack taskCallBack;
	
	public DeleteCommentTask(Context context, TaskCallBack callBackFunc) {
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
	// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		try {
			result = AppContext.getApp().deleteComment(params[0], params[1]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}

	// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				UIHelper.ToastMessage(context, "评论已成功删除", Toast.LENGTH_LONG);
				if(taskCallBack!=null){
					taskCallBack.onTaskFinshed();
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
