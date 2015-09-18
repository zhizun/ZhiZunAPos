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
import com.zhizun.pos.bean.ValidCodeResult;
import com.zhizun.pos.bean.ValidUserResult;

/**
 * 修改密码第二步创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ChangePassword2Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_next_submit;
	EditText et_change_passrword_new,
			et_change_passrword_new_confirm;
	Result result;
	String sign;
	ValidCodeResult validCodeResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_passrword_step2);
		context = this;
		Intent intent = getIntent();
		validCodeResult = (ValidCodeResult) intent
				.getSerializableExtra("validCodeResult");
		sign = validCodeResult.getSign();
		initView();
		AppManager.getAppManager().finishActivity(ChangePassword1Activity.class);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("修改密码");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_change_passrword_new = (EditText) this
				.findViewById(R.id.et_change_passrword_new);
		et_change_passrword_new_confirm = (EditText) this
				.findViewById(R.id.et_change_passrword_new_confirm);

		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newpwd = et_change_passrword_new.getText().toString()
						.trim();
				String newpwdconfirm = et_change_passrword_new_confirm
						.getText().toString().trim();
				if (null == newpwd || "".equals(newpwd)) {
					UIHelper.ToastMessage(context, "请输入新密码！", 0);
					return;
				}
				if (null == newpwdconfirm || "".equals(newpwdconfirm)) {
					UIHelper.ToastMessage(context, "请再次输入新密码！", 0);
					return;
				}
				if (!newpwd.equals(newpwdconfirm)) {
					UIHelper.ToastMessage(context, "两次输入的新密码不一样！", 0);
					return;
				}
				if (newpwdconfirm.length() < 6
						|| newpwdconfirm.length() > 16) {
					UIHelper.ToastMessage(context, "密码长度应为6-16个字符", 0);
					return;
				}
				showProgressDialog(context, "",
						getResources().getString(R.string.submit_ing));
				new UpdatePasswordTask().execute("", newpwd, newpwdconfirm,
						sign);
			}
		});

	}

	/**
	 * 修改密码 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class UpdatePasswordTask extends AsyncTask<String, Void, Result> {
		AppException e;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).update(params[0],
						params[1], params[2], params[3]);
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
							ChangePassword3Activity.class);
					startActivity(intent);
					finish();
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
