package com.zhizun.pos.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

/**
 * 修改绑定手机第5步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ChangeBindPhone5Activity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	private Button btn_next_submit;
	TextView tv_forget_password_step4_title;
	String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_passrword_step4);
		context = this;
		phone = getIntent().getStringExtra("phone");
		initView();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		btn_next_submit.performClick();	//完成页面后退等同于点击完成事件
	}
	
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText("完成修改绑定");

		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				AppManager.getAppManager().finishActivity(
//						ChangeBindPhone4Activity.class);
//				AppManager.getAppManager().finishActivity(
//						ChangeBindPhone3Activity.class);
//				AppManager.getAppManager().finishActivity(
//						ChangeBindPhone2Activity.class);
//				AppManager.getAppManager().finishActivity(
//						ChangeBindPhone1Activity.class);
//				AppManager.getAppManager().finishActivity(
//						BindPhoneActivity.class);
				AppManager.getAppManager().finishActivity(
						ChangeBindPhone4Activity.class);
				AppManager.getAppManager().finishActivity(
						SettingsActivity.class);
				ChangeBindPhone5Activity.this.finish();
				backAnim();
			}
		});
		tv_forget_password_step4_title = (TextView) findViewById(R.id.tv_forget_password_step4_title);
		tv_forget_password_step4_title.setText("您已成功修改绑定手机号");

	}

}
