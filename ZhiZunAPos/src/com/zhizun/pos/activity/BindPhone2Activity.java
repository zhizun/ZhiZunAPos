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

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.ValidUserResult;

/**
 * 绑定手机第2步创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class BindPhone2Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_nexe_submit_gray, btn_next_submit;
	EditText et_forget_passrword_text;
	Result result;
	String phone, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone2);
		context = this;
		Intent intent = getIntent();
		password = intent.getStringExtra("password");
		initView();
		AppManager.getAppManager().finishActivity(BindPhone1Activity.class);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("验证手机号码");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_forget_passrword_text = (EditText) this
				.findViewById(R.id.et_forget_passrword_text);
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
				if (et_forget_passrword_text.getText().toString().length() == 11) {
					btn_nexe_submit_gray.setVisibility(View.GONE);
					btn_next_submit.setVisibility(View.VISIBLE);
				} else {
					btn_nexe_submit_gray.setVisibility(View.VISIBLE);
					btn_next_submit.setVisibility(View.GONE);
				}

			}
		});
		btn_nexe_submit_gray = (Button) findViewById(R.id.btn_nexe_submit_gray);
		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				phone = et_forget_passrword_text.getText().toString().trim();

				if (null == phone || "".equals(phone)) {
					UIHelper.ToastMessage(context, "手机号码不能为空！", 0);
					return;
				}
				if (!StringUtils.isMobile(phone)) {
					UIHelper.ToastMessage(context, "请输入正确的手机号！", 0);
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));
				new SendSmsCodeBindTask().execute(phone,
						Constant.VALIDTYPE_BIND);
			}
		});

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
					Intent intent = new Intent(context,
							BindPhone3Activity.class);
					intent.putExtra("phone", phone);
					intent.putExtra("password", password);
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
