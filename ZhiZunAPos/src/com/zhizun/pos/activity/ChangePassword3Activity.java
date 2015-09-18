package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ch.epw.jz.activity.SendVoiceParentActivity;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;

/**
 * 修改密码第3步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ChangePassword3Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_next_submit;
	TextView tv_forget_password_step4_title;
	Result result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_passrword_step4);
		context = this;

		initView();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		btn_next_submit.performClick();	//完成页面后退等同于点击完成事件
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("完成修改密码");

		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppContext.getApp().cleanUserLoginInfo();
				Intent intent = new Intent(ChangePassword3Activity.this,
						LoginActivity.class);
				startActivity(intent);
				ChangePassword3Activity.this.finish();
				intoAnim();
			}
		});
		tv_forget_password_step4_title = (TextView) findViewById(R.id.tv_forget_password_step4_title);
		tv_forget_password_step4_title.setText("您已成功修改密码");

	}

	/**
	 * 退出程序 创建人：李林中 创建日期：2015-1-6 上午10:14:53 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class LogOutTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			try {
				result = AppContext.getApp().logOut(params[0]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
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
					AppContext.getApp().cleanUserLoginInfo();
					AppManager.getAppManager().finishAllActivity();

					Intent intent_password = new Intent(context,
							LoginActivity.class);
					startActivity(intent_password);
					intoAnim();
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

}
