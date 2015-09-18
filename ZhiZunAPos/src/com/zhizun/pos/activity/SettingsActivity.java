package com.zhizun.pos.activity;

import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.JpushUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.SwitchButton;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.PersonInfoDetail;
import com.zhizun.pos.bean.Result;

/**
 * 设置 创建人：李林中 创建日期：2014-12-15 上午9:51:04 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SettingsActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = SettingsActivity.class.getName();
	Button btn_setting_logout;
	private TitleBarView titleBarView;
	TextView tv_setting_phone;
	RelativeLayout rl_setting_bindphone;
	RelativeLayout rl_setting_password_manager;
	Result result;
	Context context;
	String phone;
	PersonInfoDetail personInfoDetail;
	Boolean isBindPhone;// 是否已经绑定手机号 true是 false 否
	SwitchButton sb_setting_receviemsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.setting);
		phone = "";
		String roles=getIntent().getStringExtra("roles");
		initView();
		new GetPersonInfoDetailTask().execute(AppContext.getApp().getToken(),roles);
	}

	private void initView() {
		titleBarView = (TitleBarView) this.findViewById(R.id.title_bar_setting);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_settings);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		btn_setting_logout = (Button) findViewById(R.id.btn_setting_logout);
		btn_setting_logout.setOnClickListener(this);
		tv_setting_phone = (TextView) findViewById(R.id.tv_setting_phone);
		rl_setting_bindphone = (RelativeLayout) findViewById(R.id.rl_setting_bindphone);
		// rl_setting_bindphone.setOnClickListener(this);
		rl_setting_password_manager = (RelativeLayout) findViewById(R.id.rl_setting_password_manager);
		// rl_setting_password_manager.setOnClickListener(this);
		sb_setting_receviemsg = (SwitchButton) findViewById(R.id.sb_setting_receviemsg);
		sb_setting_receviemsg
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						String jpushAlias = "";
						Log.e(TAG, "isChecked=" + isChecked);
						// isChecked是切换之后的状态
						if (isChecked) {
							// 1打开jpush服务
							if (JPushInterface.isPushStopped(context)) {
								JPushInterface.resumePush(context);
							}
							// 2将SharedPreferences里面的状态改为接收
							SharedPreferences spPreferences = context
									.getSharedPreferences(AppContext.getApp()
											.getUserLoginSharedPre()
											.getUserInfo().getUserId(),
											Context.MODE_PRIVATE);
							Editor et = spPreferences.edit();
							et.putString(Constant.MSG_PUSH_STATUS,
									Constant.MSG_PUSH_STATUS_YES);
							et.commit();
							
							jpushAlias = AppContext.getApp()
									.getUserLoginSharedPre().getUserInfo()
									.getUserId();
						} else {
							// 1、将SharedPreferences里面的状态改为不接收
							SharedPreferences spPreferences = context
									.getSharedPreferences(AppContext.getApp()
											.getUserLoginSharedPre()
											.getUserInfo().getUserId(),
											Context.MODE_PRIVATE);
							Editor et = spPreferences.edit();
							et.putString(Constant.MSG_PUSH_STATUS,
									Constant.MSG_PUSH_STATUS_NO);
							et.commit();
						}
						JpushUtil.resetAliasAndTags(jpushAlias);
					}
				});

		// 判断本地是否保存有是否允许接收通知状态值
		final SharedPreferences sp = context.getSharedPreferences(AppContext
				.getApp().getUserLoginSharedPre().getUserInfo().getUserId(),
				Context.MODE_PRIVATE);
		// 初始化 选择按钮状态
		if (null == sp
				|| sp.getString(Constant.MSG_PUSH_STATUS,
						Constant.MSG_PUSH_STATUS_YES).equals(
						Constant.MSG_PUSH_STATUS_YES)) {// 没有
														// 说明允许接收推送通知
			sb_setting_receviemsg.setChecked(true);
		} else {
			sb_setting_receviemsg.setChecked(false);
		}

	}

	private void initData() {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setting_logout:
			Exit();
			break;
		case R.id.rl_setting_bindphone:
			Intent intent_bindphone = new Intent(context,
					BindPhoneActivity.class);
			intent_bindphone.putExtra("isBindPhone", isBindPhone);
			intent_bindphone.putExtra("phone", phone);
			startActivity(intent_bindphone);
			intoAnim();
			break;
		case R.id.rl_setting_password_manager:
			if (isBindPhone) {
				Intent intent_password = new Intent(context,
						ChangePassword1Activity.class);
				intent_password.putExtra("phone", phone);
				startActivity(intent_password);
				intoAnim();
			} else {
				UIHelper.ToastMessage(context, "请先绑定手机号！");
				Intent intent_password = new Intent(context,
						BindPhone1Activity.class);
				startActivity(intent_password);
				intoAnim();
			}

			break;

		}
	}

	/**
	 * 获取个人资料 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetPersonInfoDetailTask extends
			AsyncTask<String, Void, PersonInfoDetail> {
		AppException e;

		protected PersonInfoDetail doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				personInfoDetail = ((AppContext) getApplication())
						.getPersonList(params[0],params[1]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return personInfoDetail;
		}

		@Override
		protected void onPostExecute(PersonInfoDetail result) {
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
					if (null == result.getPersonInfo().getPhone()
							|| result.getPersonInfo().getPhone().equals("")) {

						isBindPhone = false;
					} else {
						phone = result.getPersonInfo().getPhone();
						isBindPhone = true;
					}
					rl_setting_bindphone
							.setOnClickListener(SettingsActivity.this);
					rl_setting_password_manager
							.setOnClickListener(SettingsActivity.this);
				} else {

					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	/**
	 * 退出程序 创建人：李林中 创建日期：2015-1-6 上午10:14:53 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class LogOutTask extends AsyncTask<String, Void, Result> {

		@Override
		protected Result doInBackground(String... params) {
			try {
				result = AppContext.getApp().logOut(params[0]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				closeProgressDialog();

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {

			super.onPostExecute(result);
			closeProgressDialog();
			// if (result != null) {
			// if (result.getStatus().equals("0")) {
			// 退出时不对接口返回值进行校验。
			AppContext.getApp().cleanUserLoginInfo();
			AppManager.getAppManager().AppExit(context);
			// } else {
			// UIHelper.ToastMessage(context, result.getStatusMessage());
			// return;
			// }
			// } else {
			// //UIHelper.ToastMessage(context,
			// R.string.socket_exception_error);
			// return;
			// }
		}

	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	private void Exit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						// 没有网络 直接退出
						if (!AppContext.getApp().isNetworkConnected()) {

							AppContext.getApp().cleanUserLoginInfo();
							AppManager.getAppManager().AppExit(context);
						} else {
							// 退出

							showProgressDialog(context, "", "正在退出程序...");
							new LogOutTask().execute(AppContext.getApp()
									.getToken());
						}

					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}
}
