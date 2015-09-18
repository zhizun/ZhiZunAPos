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
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.ValidCodeResult;
import com.zhizun.pos.bean.ValidUserResult;

/**
 * 忘记密码第2步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ForgetPasswordStep2Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	TextView tv_forget_passrword_step2_title, tv_forget_passrword_timer;
	private Button btn_nexe_submit_gray, btn_next_submit;
	EditText et_forget_passrword_text;
	ValidUserResult validUserResult;
	ValidCodeResult validCodeResult;
	Result result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_passrword_step2);
		context = this;
		Intent intent = getIntent();
		validUserResult = (ValidUserResult) intent
				.getSerializableExtra("ValidUserResult");
		initView();
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
		tv_forget_passrword_step2_title.setText("验证短信已发送至您的手机："
				+ validUserResult.getMobile());

		btn_nexe_submit_gray = (Button) findViewById(R.id.btn_nexe_submit_gray);
		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String validText = et_forget_passrword_text.getText()
						.toString().trim();

				if (null == validText || "".equals(validText)) {
					UIHelper.ToastMessage(context, "验证码不能为空！", 0);
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));
				new ValidCodeTask().execute(validUserResult.getKey(), "mobile",
						validText);
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
				if (et_forget_passrword_text.getText().toString().length() > 0) {
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
				new SendCodeTask().execute(validUserResult.getKey(), "mobile");
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
	private class SendCodeTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).sendCode(params[0],
						params[1]);
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
					UIHelper.ToastMessage(context,
							"验证码已发送到" + validUserResult.getMobile());
				} else {
					timer.cancel();
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
	private class ValidCodeTask extends
			AsyncTask<String, Void, ValidCodeResult> {
		AppException e;

		protected ValidCodeResult doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				validCodeResult = ((AppContext) getApplication()).validCode(
						params[0], params[1], params[2]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return validCodeResult;
		}

		@Override
		protected void onPostExecute(ValidCodeResult result) {
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
							ForgetPasswordStep3Activity.class);
					intent.putExtra("ValidCodeResult", validCodeResult);
					startActivity(intent);
					intoAnim();
				} else {
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
