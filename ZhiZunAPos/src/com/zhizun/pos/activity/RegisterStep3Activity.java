package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.ValidCodeResult;

/**
 * 注册第3步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class RegisterStep3Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_next_submit;
	EditText et_forget_passrword_text, et_forget_passrword_text_confirm;
	TextView tv_forget_password_step3_title;
	Result result;
	String phone;
	String smsCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_passrword_step3);
		context = this;
		Intent intent = getIntent();
		phone = (String) intent.getStringExtra("phone");
		smsCode = (String) intent.getStringExtra("smsCode");
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("设置密码");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_forget_passrword_text = (EditText) this
				.findViewById(R.id.et_forget_passrword_text);
		et_forget_passrword_text_confirm = (EditText) findViewById(R.id.et_forget_passrword_text_confirm);

		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String password = et_forget_passrword_text.getText().toString()
						.trim();
				String password_confirm = et_forget_passrword_text_confirm
						.getText().toString().trim();
				if (null == password || "".equals(password)) {
					UIHelper.ToastMessage(context, "密码不能为空！", 0);
					return;
				}
				if (null == password_confirm || "".equals(password_confirm)) {
					UIHelper.ToastMessage(context, "确认密码不能为空！", 0);
					return;
				}

				if (!password.equals(password_confirm)) {
					UIHelper.ToastMessage(context, "两次输入的密码不一样！", 0);
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));
				new RegisterTask().execute(phone, smsCode, "", password,
						password_confirm);
			}
		});
		tv_forget_password_step3_title = (TextView) findViewById(R.id.tv_forget_password_step3_title);
		tv_forget_password_step3_title.setText("请设置您的密码：");
	}

	/**
	 * 验证验证码 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class RegisterTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).register(params[0],
						params[1], params[2], params[3], params[4]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					Intent intent = new Intent(context,
							RegisterStep4Activity.class);
					startActivity(intent);
					intoAnim();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
