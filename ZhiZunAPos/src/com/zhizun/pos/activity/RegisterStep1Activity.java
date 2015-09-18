package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.ValidUserResult;

/**
 * 注册第1步创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class RegisterStep1Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_nexe_submit_gray, btn_next_submit;
	EditText et_forget_passrword_text;
	CheckBox cb_register_setp1_state;
	String phone;
	Result result;
	private TextView tv_register_setp1_xieyi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_step1);
		context = this;
		initView();
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
		cb_register_setp1_state = (CheckBox) findViewById(R.id.cb_register_setp1_state);

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
				if (et_forget_passrword_text.getText().toString().length() == 11
						&& cb_register_setp1_state.isChecked()) {
					btn_nexe_submit_gray.setVisibility(View.GONE);
					btn_next_submit.setVisibility(View.VISIBLE);
				} else {
					btn_nexe_submit_gray.setVisibility(View.VISIBLE);
					btn_next_submit.setVisibility(View.GONE);
				}

			}
		});
		cb_register_setp1_state
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (et_forget_passrword_text.getText().toString()
								.length() == 11
								&& cb_register_setp1_state.isChecked()) {
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

				String et_phone = et_forget_passrword_text.getText().toString()
						.trim();

				if (null == et_phone || "".equals(et_phone)) {
					UIHelper.ToastMessage(context, "手机号码不能为空！", 0);
					return;
				}
				if (!StringUtils.isMobile(et_phone)) {
					UIHelper.ToastMessage(context, "请输入正确的手机号！", 0);
					return;
				}
				if (!cb_register_setp1_state.isChecked()) {
					UIHelper.ToastMessage(context, "请同意益培网协议", 0);
					return;
				}
				if (TextUtils.isEmpty(phone) || !phone.equals(et_phone)) {
					phone = et_phone;
					showProgressDialog(context, "",
							getResources().getString(R.string.submit_ing));
					new SendCodeTask().execute(phone, Constant.VALIDTYPE_REG);
				} else {
					Intent intent = new Intent(context,
							RegisterStep2Activity.class);
					intent.putExtra("phone", phone);
					startActivity(intent);
					intoAnim();

				}

			}
		});
		tv_register_setp1_xieyi = (TextView) findViewById(R.id.tv_register_setp1_xieyi);
		tv_register_setp1_xieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterStep1Activity.this,
						AboutActivity.class);
				intent.putExtra("html", "/mobile/service.html");
				intent.putExtra("titleName", "益培网服务协议");
				startActivity(intent);
				intoAnim();
			}
		});

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
				result = ((AppContext) getApplication()).sendSmsCode(params[0],
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
					Intent intent = new Intent(context,
							RegisterStep2Activity.class);
					intent.putExtra("phone", phone);
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
