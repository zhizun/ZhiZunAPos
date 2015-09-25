package com.zhizun.pos.main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.SpUtil;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.main.bean.GetCodeBean;
import com.zhizun.pos.main.task.LoginTask;
import com.zhizun.pos.main.task.ZhiZunGetCodeTask;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText et_phone_num;
	private EditText et_passond_num;
	private Button btn_login_submit;
	private ImageView image_tiyan;
	private ImageView image_registered;
	private TextView tv_forget_password;
	private Display d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_login);
		contenView();
		initDate();
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
	/**
	 * 获取设备信息，申请秘钥
	 */
	private void initDate() {
		// TODO Auto-generated method stub
		SharedPreferences sp=SpUtil.getInstance().getSharePerference(LoginActivity.this, "appToken");
		String token = sp.getString("token", "");//判断保存的是否有秘钥，如果没有申请秘钥
		if (token.equals("")) {
			new ZhiZunGetCodeTask(LoginActivity.this, new TaskCallBack() {
				@Override
				public void onTaskFinshed(BaseBean result) {
					GetCodeBean getCodeBean = (GetCodeBean) result;
					if (getCodeBean != null) {
						String status = getCodeBean.getStatus();
						if (status.equals("0")) {
							// 保存秘钥信息
							SpUtil.setStringSharedPerference(SpUtil.getInstance()
									.getSharePerference(LoginActivity.this, "appToken"), "token",
									getCodeBean.getMes());
						}
					}
				}
				
				@Override
				public void onTaskFailed() {
					// closeProgressDialog();
				}
				
			}).execute("863280022001832");
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login_submit://登录，跳到主页面
			String userName=et_phone_num.getText().toString().trim();
			String passoad=et_passond_num.getText().toString().trim();
			if (!userName.equals("") && userName!=null) {
				if (!passoad.equals("")&& passoad!=null) {
					new LoginTask(LoginActivity.this, new TaskCallBack() {
						@Override
						public void onTaskFinshed(BaseBean result) {
							GetCodeBean getCodeBean = (GetCodeBean) result;
							if (getCodeBean != null) {
								String status = getCodeBean.getStatus();
								if (status.equals("0")) {
									Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
									Intent intet=new Intent(LoginActivity.this,ZhiZunMainAcitivyt.class);
									startActivity(intet);
									finish();
								}else {
									Toast.makeText(LoginActivity.this, "登录失败，请稍后再试", Toast.LENGTH_LONG).show();
								}
							}
						}

						@Override
						public void onTaskFailed() {
							// closeProgressDialog();
						}

					}).execute(userName,passoad);
				}else {
					Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
				}
			}else {
				Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.image_tiyan://体验登录
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
	        tm.getDeviceId(); 
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
