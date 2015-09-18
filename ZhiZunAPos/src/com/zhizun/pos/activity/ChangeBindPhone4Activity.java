package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;

/**
 * 修改绑定手机第4步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ChangeBindPhone4Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	TextView tv_forget_passrword_step2_title, tv_forget_passrword_timer;
	private Button btn_nexe_submit_gray, btn_next_submit;
	EditText et_forget_passrword_text;

	Result result;
	String sign, phone;
	String validText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone3);
		context = this;
		Intent intent = getIntent();
		sign = intent.getStringExtra("sign");
		phone = intent.getStringExtra("phone");
		initView();
		AppManager.getAppManager().finishActivity(ChangeBindPhone3Activity.class);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("验证身份");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_forget_passrword_text = (EditText) this
				.findViewById(R.id.et_forget_passrword_text);
		tv_forget_passrword_step2_title = (TextView) findViewById(R.id.tv_forget_passrword_step2_title);
		tv_forget_passrword_step2_title.setText("验证短信已发送至您的手机：" + phone);

		btn_nexe_submit_gray = (Button) findViewById(R.id.btn_nexe_submit_gray);
		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				validText = et_forget_passrword_text.getText().toString()
						.trim();

				if (null == validText || "".equals(validText)) {
					UIHelper.ToastMessage(context, "验证码不能为空！", 0);
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.submit_ing));
				new ValidSmsCodeBindTask().execute(phone,
						Constant.VALIDTYPE_REBIND, validText);
			}
		});
		et_forget_passrword_text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (et_forget_passrword_text.getText().toString().length() == Constant.SMS_VALID_CODE_LEN) {
					btn_nexe_submit_gray.setVisibility(View.GONE);
					btn_next_submit.setVisibility(View.VISIBLE);
				} else {
					btn_nexe_submit_gray.setVisibility(View.VISIBLE);
					btn_next_submit.setVisibility(View.GONE);
				}

			}
		});
		tv_forget_passrword_timer = (TextView) findViewById(R.id.tv_forget_passrword_timer);
		tv_forget_passrword_timer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_forget_passrword_timer.setEnabled(false);
				timer.start();
				new SendSmsCodeBindTask().execute(phone,
						Constant.VALIDTYPE_REBIND);
			}
		});
		tv_forget_passrword_timer.setEnabled(false);
		timer.start();
	}

	/**
	 * 发送手机验证码 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class SendSmsCodeBindTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).sendSmsCodeBind(
						params[0], params[1]);
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
			if (null != progress) {
				progress.dismiss();
			}
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "验证码已发送到您的手机" + phone);
				} else {
					timer.cancel();
					tv_forget_passrword_timer.setEnabled(true);
					tv_forget_passrword_timer.setText("重新发送");
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	/**
	 * 验证验证码 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class ValidSmsCodeBindTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).validateSmsBindCode(
						params[0], params[1], params[2]);
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
					new ReBindPhoneTask().execute(phone, validText, sign);
				} else {
					timer.cancel();
					tv_forget_passrword_timer.setEnabled(true);
					tv_forget_passrword_timer.setText("重新发送");
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	/**
	 * 更换绑定手机 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class ReBindPhoneTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).rebind(params[0],
						params[1], params[2]);
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
			if (null != progress) {
				progress.dismiss();
			}
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					Intent intent = new Intent(context,
							ChangeBindPhone5Activity.class);
					startActivity(intent);
					intoAnim();

				} else {
					timer.cancel();
					tv_forget_passrword_timer.setEnabled(true);
					tv_forget_passrword_timer.setText("获取验证码");
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	/**
	 * 倒计时 60秒
	 */
	private CountDownTimer timer = new CountDownTimer(
			Constant.SMS_SEND_INTERVAL, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			tv_forget_passrword_timer.setText("重新发送（"
					+ (millisUntilFinished / 1000) + "）");
		}

		@Override
		public void onFinish() {
			tv_forget_passrword_timer.setEnabled(true);
			tv_forget_passrword_timer.setText("重新发送");
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
