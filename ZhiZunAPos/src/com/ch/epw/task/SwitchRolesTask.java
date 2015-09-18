package com.ch.epw.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.activity.WelcomeActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

public class SwitchRolesTask extends AsyncTask<String, Void, Result> {
	AppException e;
	private Context context;
	private UserRole userRole;
	private TaskCallBack taskCallBack;

	public SwitchRolesTask(UserRole userRole, Context context,TaskCallBack taskCallBack){
		this.userRole = userRole;
		this.context = context;
		this.taskCallBack = taskCallBack;
	}
	
	@Override
	protected Result doInBackground(String... params) {
		Result result = null;
		try {
			result = AppContext.getApp().switchRole(params[0], params[1],
					params[2]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);

		if(BaseActivity.class.isAssignableFrom(context.getClass())){
			ProgressDialog progress = ((BaseActivity)context).getProgress();
			if(progress != null){
				progress.dismiss();
			}
		}
		if (result != null) {
			if (result.getStatus().equals("0")) {
				// 保存当前角色和机构ID 到本地
				UserLogin userLogin = AppContext.getApp()
						.getUserLoginSharedPre();
				userLogin.getUserInfo()
						.setCurrentRole(userRole.getRoleId());
				userLogin.getUserInfo().setCurrentOrgan(userRole);
				AppContext.getApp().saveLoginInfo(userLogin);
				
				//如果切换后的角色家长，则清除NavigationTeacherActivity
				if(Constant.ORG_ROLE_TYPE_PARENT.equals(userRole.getRoleId())){
					AppManager.getAppManager().finishActivity(NavigationTeacherActivity.class);
				}
				//否则，清除 MainActivity
				else{
					AppManager.getAppManager().finishActivity(MainActivity.class);
				}
				
				if(taskCallBack != null){
					taskCallBack.onTaskFinshed();
				}
			} else {
				UIHelper.ToastMessage(context, "登录已过期，请重新登录");
				Intent logIntent = new Intent(context,	LoginActivity.class);
				context.startActivity(logIntent);
				AppManager.getAppManager().finishActivity(WelcomeActivity.class);
				//((BaseActivity)context).intoAnim();
			}
		} else {
			if (null != e) {
				//处理loginActivity中的异常
				e.makeToast(context);
			}
			AppManager.getAppManager().finishActivity(WelcomeActivity.class);
			return;
		}
	}
}
