package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;

import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.ch.epw.utils.UIHelper;
import com.umeng.analytics.AnalyticsConfig;

/**
 * 启动splash界面 创建人：李林中 
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class WelcomeActivity extends BaseActivity {
	protected static final String TAG = "WelcomeActivity";
	private Context context;
	private ImageView mImageView;
	Result result;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnalyticsConfig.enableEncrypt(false);

		View view = View.inflate(WelcomeActivity.this,
				R.layout.activity_welcome, null);
		setContentView(view);
		context = this;
		ShareSDK.initSDK(this);
		// 渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		// 初始化登录
		// ((AppContext)getApplication()).initLoginInfo();

		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});

	}

	private void findView() {
		mImageView = (ImageView) findViewById(R.id.iv_welcome);
	}

	/**
	 * 跳转到...
	 */
	private void redirectTo() {

		// 判断是否有网络 如果没有网络 则给出提示 并退出应用程序
		if (!AppContext.getApp().isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			WelcomeActivity.this.finish();
			return;
		}

		// 判断是否为第一次使用 如果是第一次但则跳转到引导页

		SharedPreferences sef = this.getSharedPreferences("FirstUseInfo",
				Context.MODE_PRIVATE);
		Integer isUseing = sef.getInt("isUseing", 0);
		if (null == isUseing || isUseing != 1) {
			Editor editor = sef.edit();
			editor.putInt("isUseing", 1);
			editor.commit();
			Intent intent = new Intent(this, GuideActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
			intoAnim();

		} else {
			Intent intent = new Intent(this, IndexActivity.class);
			startActivity(intent);
			WelcomeActivity.this.finish();
			intoAnim();
		}
	}

}
