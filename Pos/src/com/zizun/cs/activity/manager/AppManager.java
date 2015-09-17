package com.zizun.cs.activity.manager;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zhizun.pos.app.StoreApplication;
import com.zizun.cs.biz.api.UserAPI;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.entities.Reg_AppActive;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class AppManager {
	private static AppManager mInstance;

	public static AppManager getInstance() {
		if (mInstance == null) {
			mInstance = new AppManager();
		}
		return mInstance;
	}

	private Reg_AppActive getReg_AppActiveEntity(int paramInt) {
		String str1 = ((TelephonyManager) StoreApplication.getContext().getSystemService("phone")).getDeviceId();
		String str2 = Build.MODEL;
		String str3 = Build.VERSION.RELEASE;
		String str4 = getAppVersionName(StoreApplication.getContext());
		Reg_AppActive localReg_AppActive = new Reg_AppActive();
		localReg_AppActive.setMobileID(str1);
		localReg_AppActive.setMobileSystem((byte) 2);
		localReg_AppActive.setSystemVersion(str3);
		localReg_AppActive.setAppID((byte) 1);
		localReg_AppActive.setAppVersion(str4);
		localReg_AppActive.setAppMarket(paramInt);
		localReg_AppActive.setMobileStyle(str2);
		localReg_AppActive.setActiveDate(DateTimeUtil.getCurrentTime());
		return localReg_AppActive;
	}

	public void ActivieApp(int paramInt) {
		if (!PreferencesUtil.getPreference(StoreApplication.getContext()).getString("App_Active_Version", "")
				.equals(getAppVersionName(StoreApplication.getContext()))) {
			UserAPI.activeApp(getReg_AppActiveEntity(paramInt), new ActiveCallBack(null));
		}
	}

	public String getAppVersionName(Context paramContext) {
		Object localObject = "";
		try {
			paramContext = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(),
					0).versionName;
			localObject = paramContext;
			boolean bool = TextUtils.isEmpty(paramContext);
			if (bool) {
				return "";
			}
		} catch (Exception paramContext) {
			paramContext.printStackTrace();
			paramContext = (Context) localObject;
		}
		return paramContext;
	}

	public boolean isFirstInstall() {
		return PreferencesUtil.getPreference(StoreApplication.getContext()).getBoolean("IS_FIRST_INSTALL", true);
	}

	public void setNotFirstInstall() {
		new PreferencesUtil(StoreApplication.getContext()).getEditor().putBoolean("IS_FIRST_INSTALL", false).commit();
	}

	private class ActiveCallBack extends RequestCallBack<String> {
		private ActiveCallBack() {
		}

		public void onFailure(HttpException paramHttpException, String paramString) {
			LogUtil.logD("激活:" + paramString);
		}

		public void onSuccess(ResponseInfo<String> paramResponseInfo) {
			LogUtil.logD("激活:" + (String) paramResponseInfo.result);
			new PreferencesUtil(StoreApplication.getContext()).getEditor()
					.putString("App_Active_Version", AppManager.this.getAppVersionName(StoreApplication.getContext()))
					.commit();
		}
	}
}