package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.PersonInfoDetail;

/**
 * 忘记密码第2步 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class BindPhoneActivity extends BaseActivity {

	private TitleBarView titleBarView;
	private Context context;
	TextView tv_bind_phone_content;
	private Button btn_next_submit;
	PersonInfoDetail personInfoDetail;
	Boolean isBindPhone;// 是否已经绑定手机号 true是 false 否
	String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone);
		context = this;
		Intent intent = getIntent();
		isBindPhone = intent.getBooleanExtra("isBindPhone", false);
		phone = intent.getStringExtra("phone");
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_forgetpassword);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);

		titleBarView.setTitleText("管理手机号");

		
		
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		btn_next_submit = (Button) this.findViewById(R.id.btn_next_submit);
		btn_next_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBindPhone) {
					Intent intent = new Intent(context,
							ChangeBindPhone2Activity.class);
					intent.putExtra("phone", phone);
					startActivity(intent);
					intoAnim();

				} else {
					Intent intent = new Intent(context,
							BindPhone1Activity.class);
					startActivity(intent);
					intoAnim();
				}
			}
		});
		tv_bind_phone_content = (TextView) findViewById(R.id.tv_bind_phone_content);

		if (isBindPhone) {
			tv_bind_phone_content.setText("您当前绑定的手机号为：" + phone);
			btn_next_submit.setText("修改绑定手机号");
		} else {
			tv_bind_phone_content.setText("您当前还未绑定手机号，请绑定");
			btn_next_submit.setText("绑定手机号");
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
