package com.zhizun.pos.ui.activity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.geetest.sdk.GeetestLib;
import com.geetest.sdk.GtDialog;
import com.geetest.sdk.SdkInit;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zhizun.pos.ui.dialog.AgreementDialog;
import com.zhizun.pos.ui.dialog.SimpleConfirmDialog;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.activity.manager.SyncBaseDataManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.api.UserAPI;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.RegularUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.api.RegisterResult;
import com.zizun.cs.entities.api.SMSRegisterParam;
import com.zizun.cs.entities.api.UserNameCheckParam;
import com.zizun.cs.entities.sync.SyncDownLoadParam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RegistActivity extends BaseFragmentTitleTopBarActivity
		implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnClickListener {
	private GeetestLib geetestLib;
	private Button mBtnAuthCode;
	private Button mBtnOk;
	private CheckBox mCebAgreement;
	private String mCodeFromServer;
	private EditText mEdtAuthCode;
	private EditText mEdtPasswordk;
	private EditText mEdtPhone;
	private EditText mEdtReferrer;
	private SdkInit sdkInitData;

	private boolean checkInput() {
		String str1 = getEdtText(this.mEdtPhone);
		String str2 = getEdtText(this.mEdtPasswordk);
		String str3 = getEdtText(this.mEdtAuthCode);
		if ((!checkPhone(str1)) || (isTextNull(str2, pwd_not_null)) || (isTextNull(str3, authcode_not_null))) {
			return false;
		}
		if (!str3.equals(this.mCodeFromServer)) {
			ToastUtil.toastLong(this, auth_code_error);
			return false;
		}
		if (!this.mCebAgreement.isChecked()) {
			ToastUtil.toastLong(this, please_agree_agreement);
			return false;
		}
		return true;
	}

	private boolean checkPhone(String paramString) {
		if (isTextNull(paramString, phone_not_null)) {
			return false;
		}
		if (!RegularUtil.isMobileNum(paramString)) {
			ToastUtil.toastLong(this, phone_is_wrong);
			return false;
		}
		return true;
	}

	private void countDown() {
		new CountDownTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
	}

	private void doRegister() {
		if (!checkInput()) {
			return;
		}
		showWaitDialog(ResUtil.getString(wait_for_register));
		UserNameCheckParam localUserNameCheckParam = new UserNameCheckParam(getEdtText(this.mEdtPhone));
		LogUtils.i(JsonUtil.toJson(localUserNameCheckParam));
		ManagerFactory.getRegisterManager().checkUserName(localUserNameCheckParam, new RequestCallBack() {
			public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString) {
				LogUtils.i("注册失败=====" + paramAnonymousString);
				ToastUtil.toastLong(RegistActivity.this, check_network);
			}

			public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo) {
				LogUtils.i("检测用户名 ====== " + (String) paramAnonymousResponseInfo.result);
				paramAnonymousResponseInfo = ManagerFactory.getRegisterManager()
						.getUserNameCheckResultFromJson((String) paramAnonymousResponseInfo.result);
				if (paramAnonymousResponseInfo.Code == 200) {
					if (paramAnonymousResponseInfo.Data) {
					}
					for (int i = 0; i == 0; i = 1) {
						paramAnonymousResponseInfo = RegistActivity.this.getEdtText(RegistActivity.this.mEdtPhone);
						String str1 = RegistActivity.this.getEdtText(RegistActivity.this.mEdtPasswordk);
						String str2 = RegistActivity.this.getEdtText(RegistActivity.this.mEdtReferrer);
						ManagerFactory.getRegisterManager().doRegister(paramAnonymousResponseInfo, str1, str2,
								new RequestCallBack() {
							public void onFailure(HttpException paramAnonymous2HttpException,
									String paramAnonymous2String) {
								ToastUtil.toastLong(RegistActivity.this, check_network);
							}

							public void onSuccess(ResponseInfo<String> paramAnonymous2ResponseInfo) {
								LogUtils.i("注册====== " + (String) paramAnonymous2ResponseInfo.result);
								paramAnonymous2ResponseInfo = (RegisterResult) UserAPI.getAPIResultFromJson(
										(String) paramAnonymous2ResponseInfo.result, RegisterResult.class);
								if (paramAnonymous2ResponseInfo.Code == 200) {
									ToastUtil.toastLong(RegistActivity.this, register_success);
									new RegistActivity.HandleRegisterBackTask(RegistActivity.this, null)
											.executeOnExecutor(ExecutorUtils.getDBExecutor(),
													new RegisterResult[] { paramAnonymous2ResponseInfo });
									return;
								}
								RegistActivity.this.dismissWaitDialog();
								ToastUtil.toastLong(RegistActivity.this, paramAnonymous2ResponseInfo.Message);
							}
						});
						return;
					}
					RegistActivity.this.dismissWaitDialog();
					ToastUtil.toastLong(RegistActivity.this, phone_is_registered);
					return;
				}
				RegistActivity.this.dismissWaitDialog();
				ToastUtil.toastLong(RegistActivity.this, paramAnonymousResponseInfo.Message);
			}
		});
	}

	private void doSyncDownload() {
		SyncBaseDataManager localSyncBaseDataManager = SyncBaseDataManager.getInstance();
		Timestamp localTimestamp = localSyncBaseDataManager.getLastBaseDataDownloadTime();
		localSyncBaseDataManager.doSyncBaseDataDownload(
				new SyncDownLoadParam(UserManager.getInstance().getCurrentUser().getMerchant_ID(), localTimestamp),
				new RequestCallBack() {
					public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString) {
						RegistActivity.this.dismissWaitDialog();
						ToastUtil.toastLong(RegistActivity.this, check_network);
					}

					public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo) {
						new RegistActivity.SyncBackTask(RegistActivity.this, null).executeOnExecutor(
								ExecutorUtils.getDBExecutor(),
								new String[] { (String) paramAnonymousResponseInfo.result });
					}
				});
	}

	private void getAuthCode() {
		showWaitDialog();
		SMSRegisterParam localSMSRegisterParam = new SMSRegisterParam(getEdtText(this.mEdtPhone),
				getEdtText(this.mEdtReferrer));
		ManagerFactory.getRegisterManager().sendRegisterSMS(localSMSRegisterParam, new RequestCallBack() {
			public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString) {
				RegistActivity.this.dismissWaitDialog();
				LogUtil.logD("验证码发送失败=====" + paramAnonymousString);
				ToastUtil.toastLong(RegistActivity.this, check_network);
			}

			public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo) {
				RegistActivity.this.dismissWaitDialog();
				paramAnonymousResponseInfo = ManagerFactory.getRegisterManager()
						.getSendRegSMSResultFromJson((String) paramAnonymousResponseInfo.result);
				if (paramAnonymousResponseInfo.Code == 200) {
					RegistActivity.this.countDown();
					RegistActivity.this.mCodeFromServer = String.valueOf(paramAnonymousResponseInfo.Data);
					LogUtil.logD("验证码发送成功====" + paramAnonymousResponseInfo.Data);
					paramAnonymousResponseInfo = new SimpleConfirmDialog();
					paramAnonymousResponseInfo.setTitle(auth_code_sent);
					paramAnonymousResponseInfo.show(RegistActivity.this.getSupportFragmentManager(), "AuthCode");
					return;
				}
				LogUtil.logD("验证码发送失败=====" + paramAnonymousResponseInfo.Data);
				RegistActivity.this.dismissWaitDialog();
				ToastUtil.toastLong(RegistActivity.this, paramAnonymousResponseInfo.Message);
			}
		});
	}

	private void initView() {
		this.mEdtPhone = ((EditText) findViewById(activity_register_edtPhone));
		this.mEdtPasswordk = ((EditText) findViewById(activity_register_edtPassword));
		this.mEdtReferrer = ((EditText) findViewById(activity_register_edtReferrer));
		this.mEdtAuthCode = ((EditText) findViewById(activity_register_edtAuthCode));
		this.mBtnAuthCode = ((Button) findViewById(activity_register_btnAuthCode));
		this.mBtnOk = ((Button) findViewById(activity_register_btnOk));
		this.mCebAgreement = ((CheckBox) findViewById(activity_register_cebAgreeAgreement));
		findViewById(activity_register_txtAgreement).setOnClickListener(this);
		this.mBtnAuthCode.setOnClickListener(this);
		this.mBtnOk.setOnClickListener(this);
	}

	private boolean isTextNull(String paramString, int paramInt) {
		if ((paramString == null) || (paramString.equals(""))) {
			ToastUtil.toastLong(this, paramInt);
			return true;
		}
		return false;
	}

	public void addBody(ViewGroup paramViewGroup) {
		getLayoutInflater().inflate(activity_register, paramViewGroup);
		this.mTopBar.setTitle(register);
		this.mTopBar.setLeftButton(btn_back, this);
		this.mTopBar.setRightText(finish, this);
		initView();
		this.sdkInitData = new SdkInit();
		this.geetestLib = new GeetestLib();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case activity_register_cebAgreeAgreement:
		default:
		case activity_register_btnAuthCode:
			do {
				return;
			} while (!checkPhone(getEdtText(this.mEdtPhone)));
			this.geetestLib.setCaptchaId("00f297fc738ad1f393aead120217dc6a");
			new GtAppDlgTask().execute(new Void[0]);
			return;
		case activity_register_btnOk:
			doRegister();
			return;
		}
		new AgreementDialog().show(getSupportFragmentManager(), "agreement");
	}

	public void onTopBarLeftClick() {
		finish();
	}

	public void onTopBarRightClick() {
		doRegister();
	}

	public void openGtTest(SdkInit paramSdkInit) {
		paramSdkInit = GtDialog.newInstance(paramSdkInit);
		paramSdkInit.setGtListener(new GtDialog.GtListener() {
			public void closeGt() {
				LogUtil.logD("Close geetest windows");
			}

			public void gtResult(boolean paramAnonymousBoolean, String paramAnonymousString) {
				if (paramAnonymousBoolean) {
					RegistActivity.this.getAuthCode();
					return;
				}
				LogUtil.logD("client captcha failed:" + paramAnonymousString);
			}
		});
		paramSdkInit.show();
	}

	private class CountDownTask extends AsyncTask<Void, Integer, Void> {
		private int mDownTime = 60;

		private CountDownTask() {
		}

		protected Void doInBackground(Void... paramVarArgs) {
			for (;;) {
				if (this.mDownTime <= 0) {
					return null;
				}
				try {
					Thread.sleep(1000L);
					this.mDownTime -= 1;
					publishProgress(new Integer[] { Integer.valueOf(this.mDownTime) });
					int i = this.mDownTime;
					if (i == 0) {
						return null;
					}
				} catch (InterruptedException paramVarArgs) {
					paramVarArgs.printStackTrace();
				}
			}
		}

		protected void onPostExecute(Void paramVoid) {
			RegistActivity.this.mBtnAuthCode.setBackgroundResource(corners_blue_bg);
			RegistActivity.this.mBtnAuthCode.setText(auth_code);
			RegistActivity.this.mBtnAuthCode.setClickable(true);
			super.onPostExecute(paramVoid);
		}

		protected void onPreExecute() {
			RegistActivity.this.mBtnAuthCode.setClickable(false);
			RegistActivity.this.mBtnAuthCode.setBackgroundResource(corners_grey_bg);
			RegistActivity.this.mBtnAuthCode.setText(String.valueOf(60));
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer... paramVarArgs) {
			RegistActivity.this.mBtnAuthCode.setText(String.valueOf(paramVarArgs[0]));
			super.onProgressUpdate(paramVarArgs);
		}
	}

	class GtAppDlgTask extends AsyncTask<Void, Integer, Integer> {
		GtAppDlgTask() {
		}

		protected Integer doInBackground(Void... paramVarArgs) {
			return Integer.valueOf(RegistActivity.this.geetestLib.preProcess());
		}

		protected void onPostExecute(Integer paramInteger) {
			if (paramInteger.intValue() == 1) {
				RegistActivity.this.sdkInitData.setCaptcha_id("00f297fc738ad1f393aead120217dc6a");
				RegistActivity.this.sdkInitData.setChallenge_id(RegistActivity.this.geetestLib.getChallengeId());
				RegistActivity.this.sdkInitData.setContext(RegistActivity.this);
				RegistActivity.this.openGtTest(RegistActivity.this.sdkInitData);
			}
		}
	}

	private class HandleRegisterBackTask extends AsyncTask<RegisterResult, Void, Boolean> {
		private HandleRegisterBackTask() {
		}

		protected Boolean doInBackground(RegisterResult... paramVarArgs) {
			ManagerFactory.getRegisterManager().handleRegisterResult(paramVarArgs[0],
					RegistActivity.this.getEdtText(RegistActivity.this.mEdtPasswordk));
			publishProgress(new Void[0]);
			RegistActivity.this.doSyncDownload();
			return Boolean.valueOf(false);
		}

		protected void onProgressUpdate(Void... paramVarArgs) {
			RegistActivity.this.dismissWaitDialog();
			RegistActivity.this.showWaitDialog(ResUtil.getString(is_login));
			super.onProgressUpdate(paramVarArgs);
		}
	}

	private class SyncBackTask extends AsyncTask<String, Void, Boolean> {
		private SyncBackTask() {
		}

		protected Boolean doInBackground(String... paramVarArgs) {
			return Boolean.valueOf(SyncBaseDataManager.getInstance().handleSyncBaseDataDownBack(paramVarArgs[0]));
		}

		protected void onPostExecute(Boolean paramBoolean) {
			RegistActivity.this.dismissWaitDialog();
			Object localObject;
			if (paramBoolean.booleanValue()) {
				ToastUtil.toastLong(RegistActivity.this, login_success);
				LogUtil.logD("门店数量:=====" + UserManager.getInstance().getCurrentUserStoreList().size());
				if (UserManager.getInstance().getCurrentUserStoreList().size() > 1) {
					localObject = new Bundle();
					((Bundle) localObject).putSerializable("StoreList",
							(Serializable) UserManager.getInstance().getCurrentUserStoreList());
					Intent localIntent = new Intent(RegistActivity.this, StoreSelActivity.class);
					localIntent.putExtra("BundleStores", (Bundle) localObject);
					RegistActivity.this.startActivity(localIntent);
				}
			}
			for (;;) {
				super.onPostExecute(paramBoolean);
				return;
				if (UserManager.getInstance().getCurrentUserStoreList().size() == 1) {
					localObject = new Intent(RegistActivity.this, AD_HeaderActivity.class);
					((Intent) localObject).addFlags(268435456);
					RegistActivity.this.startActivity((Intent) localObject);
					RegistActivity.this.finish();
					continue;
					ToastUtil.toastLong(RegistActivity.this, login_failure);
				}
			}
		}
	}
}