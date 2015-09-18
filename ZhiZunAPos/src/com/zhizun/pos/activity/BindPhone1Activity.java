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
 * 绑定手机第1步创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class BindPhone1Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_nexe_submit_gray, btn_next_submit;
	EditText et_bind_phone_password;

	Result result;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone1);
		context = this;
		initView();
		AppManager.getAppManager().finishActivity(BindPhoneActivity.class);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("输入登录密码");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_bind_phone_password = (EditText) this
				.findViewById(R.id.et_bind_phone_password);
		et_bind_phone_password.addTextChangedListener(new TextWatcher() {

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
				if (et_bind_phone_password.getText().toString().length() > 0) {
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

				password = et_bind_phone_password.getText().toString().trim();

				if (null == password || "".equals(password)) {
					UIHelper.ToastMessage(context, "密码不能为空！", 0);
					return;
				}

				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));
				new CheckPassTask().execute(password, "");
			}
		});

	}

	/**
	 * 验证用户 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class CheckPassTask extends AsyncTask<String, Void, Result> {
		AppException e;
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).checkPass(params[0],
						params[1]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e=e;
				e.printStackTrace();
				AppException.network(e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
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
							BindPhone2Activity.class);
					intent.putExtra("password", password);
					startActivity(intent);
					intoAnim();
				} else {
					UIHelper.ToastMessage(context, R.string.login_text_fail);
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
