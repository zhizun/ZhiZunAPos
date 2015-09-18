package com.zhizun.pos.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText et_phone_num;
	private EditText et_passond_num;
	private Button btn_login_submit;
	private ImageView image_tiyan;
	private ImageView image_registered;
	private TextView tv_forget_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_login);
		contenView();
	}
	private void contenView(){
		et_phone_num=(EditText) findViewById(R.id.et_phone_num);
		et_passond_num=(EditText) findViewById(R.id.et_passond_num);
		
		btn_login_submit=(Button) findViewById(R.id.btn_login_submit);
		
		image_tiyan=(ImageView) findViewById(R.id.image_tiyan);
		image_registered=(ImageView) findViewById(R.id.image_registered);
		
		tv_forget_password=(TextView) findViewById(R.id.tv_forget_password);
		
		btn_login_submit.setOnClickListener(this);
		image_tiyan.setOnClickListener(this);
		image_registered.setOnClickListener(this);
		tv_forget_password.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login_submit://登录
			
			break;
		case R.id.image_tiyan://体验登录
			
			break;
		case R.id.image_registered://注册
			Intent intent=new Intent(this,RegisteredActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_forget_password://忘记密码
			
			break;

		}
		
	}

}
