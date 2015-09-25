package com.zhizun.pos.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ch.epw.task.TaskCallBack;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.main.bean.GetCodeBean;
import com.zhizun.pos.main.task.RegisterTask;
/**
 * 
 * @author 李林中
 * 
 * 至尊建业
 * 		注册页面
 *
 */
public class RegisteredActivity extends BaseActivity {
	private TitleBarView titleBarView;
	private Button btn_zhuce_submit;
	private EditText register_username;
	private EditText register_passoad;
	private EditText register_pd;
	private CheckBox cb_is_anonymous;
	private EditText register_email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_registered);
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zhizun);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.registration_text);
		titleBarView.getIvLeft().setImageResource(R.drawable.fanhui_zhizun);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		register_username=(EditText) findViewById(R.id.register_username);//手机号
		register_passoad=(EditText) findViewById(R.id.register_passoad);//密码
		register_pd=(EditText) findViewById(R.id.register_pd);//验证码
		
		register_email=(EditText) findViewById(R.id.register_email);//邮箱
		
		btn_zhuce_submit=(Button) findViewById(R.id.btn_zhuce_submit);
		btn_zhuce_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName=register_username.getText().toString().trim();
				String passoad=register_passoad.getText().toString().trim();
				String register=register_pd.getText().toString().trim();
				String email=register_email.getText().toString().trim();
				
				if (userName!=null && !userName.equals("")) {
					if (userName.length()==11) {
						new RegisterTask(RegisteredActivity.this, new TaskCallBack() {
							@Override
							public void onTaskFinshed(BaseBean result) {
								GetCodeBean getCodeBean = (GetCodeBean) result;
								if (getCodeBean != null) {
									String status = getCodeBean.getStatus();
									if (status.equals("0")) {
										Toast.makeText(RegisteredActivity.this, "恭喜您注册成功！请登录", Toast.LENGTH_LONG).show();
									}
								}
							}

							@Override
							public void onTaskFailed() {
								// closeProgressDialog();
							}

						}).execute(userName,passoad,register,email);
					}else {
						Toast.makeText(RegisteredActivity.this, "请输入正确的手机号", 1).show();
					}
				}
				
			}
		});
		
		}
	
}
