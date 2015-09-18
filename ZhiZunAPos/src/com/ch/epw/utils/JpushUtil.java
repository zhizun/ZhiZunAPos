package com.ch.epw.utils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;

public class JpushUtil {
	
	private static final String TAG = JpushUtil.class.getName();
	private static final int retryInterval = 60;
    public static final String PREFS_NAME = "JPUSH_EXAMPLE";
    public static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
    public static final String PREFS_START_TIME = "PREFS_START_TIME";
    public static final String PREFS_END_TIME = "PREFS_END_TIME";
    public static final String KEY_APP_KEY = "JPUSH_APPKEY";

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }
    
    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    // 取得AppKey
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {

        }
        return appKey;
    }
    
    // 取得版本号
    public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
	
    public static void showToast(final String toast, final Context context)
    {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
    }
    
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    
	public static String getImei(Context context, String imei) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			Log.e(JpushUtil.class.getSimpleName(), e.getMessage());
		}
		return imei;
	}
	
	public static void resetAliasAndTags(String alias){
		if (null == alias) {
			alias = "";
		}
		if (!TextUtils.isEmpty(alias) && !JpushUtil.isValidTagAndAlias(alias)) {
			Log.i(TAG, AppContext.getApp().getResources().getString(R.string.error_tag_gs_empty));
			return;
		}
		// 调用JPush API设置Alias
		jpushsetAliasHandler.sendMessage(jpushsetAliasHandler.obtainMessage(
				MSG_SET_ALIAS, alias));
	}

	// JPUSH通知别名
	private static final int MSG_SET_ALIAS = 1001;
	private final static Handler jpushsetAliasHandler = new Handler(){
		@Override
		public void handleMessage(final android.os.Message msg) {
			super.handleMessage(msg);
			final String alias = (String)(msg.obj == null ? "" :msg.obj);
			switch (msg.what) {
				case MSG_SET_ALIAS:
					Log.d(TAG, "Set alias in handler.");
					JPushInterface.setAliasAndTags(AppContext.getApp(), alias, Constant.PUSH_ALIAS_TAGS, new TagAliasCallback(){
						@Override
						public void gotResult(int code, String arg1, Set<String> arg2) {
							switch (code) {
								case 0:
									Log.i(TAG, "Set tag and alias = " + alias);
									if(alias.equals("")){	//注册的别名为空字符串，则停止jpush
										if(!JPushInterface.isPushStopped(AppContext.getApp())){
											JPushInterface.stopPush(AppContext.getApp());
											Log.i(TAG, "JPushInterface stoped");
										}
									}else{
										if(JPushInterface.isPushStopped(AppContext.getApp())){
											JPushInterface.resumePush(AppContext.getApp());
											Log.i(TAG, "JPushInterface resumed");
										}
									}
									break;
								case 6002:
									Log.i(TAG, "Failed to set alias and tags due to timeout. Try again after "+retryInterval+"s");
									if (JpushUtil
											.isConnected(AppContext.getApp())) {
										jpushsetAliasHandler.sendMessageDelayed(
												jpushsetAliasHandler
														.obtainMessage(
																MSG_SET_ALIAS,
																(String) msg.obj), 1000 * retryInterval);
									} else {
										Log.i(TAG, "No network");
									}
									break;
								default:
									Log.e(TAG, "Failed with errorCode = " + code);
									break;
							}
						}
					});
				break;
			default:
				Log.i(TAG, "Unhandled msg - " + msg.what);
			}
		}
	};
}
