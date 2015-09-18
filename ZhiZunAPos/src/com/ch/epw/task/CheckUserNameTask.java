package com.ch.epw.task;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Result;

/**
 * 检查用户名是否重复
 */
public class CheckUserNameTask extends AsyncTask<String, Void, Result> {
	AppException e;
	private Context context;		//上下文context
	private TextView textView;				//设置用户名的view对象
	private Dialog dialog;			//编辑用户名对话框窗口
	private String userName;

	public CheckUserNameTask(Context context, TextView nameView, Dialog dialog){
		this.context = context;
		this.textView = nameView;
		this.dialog = dialog;
	}

	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		userName = params[0];
		try {
			result = AppContext.getApp().checkUserName(userName);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (null != result) {
			if (result.getStatus().equals("0")) {
				textView.setText(userName);
				if(dialog != null){
					dialog.dismiss();
				}
			} else {
				UIHelper.ToastMessage(context, "该用户名已被占用，请更换一个");
				//UIHelper.ToastMessage(context, result.getStatusMessage());
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
