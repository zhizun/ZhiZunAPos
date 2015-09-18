package com.zhizun.pos.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.CourseApplyBean;
import com.zhizun.pos.bean.UserLogin;

public class CourseApply extends BaseActivity implements OnClickListener {
	private TitleBarView titleBarView;
	private Button bt_finsh,bt_suer_app;
	private SharedPreferences mySharedPreferences;
	private String username;
	private TextView tv_username;
	private CourseApplyBean courseApply;
	private String courseId;
	private EditText et_course_content;
	private String ownOrgId;
	Display d ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_course_apply);
		WindowManager m = getWindowManager();    
	    d = m.getDefaultDisplay();  //为获取屏幕宽、高    
	    LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值    
	    p.height = (int) (d.getHeight() * 0.6);   //高度设置为屏幕的1.0   
	    p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.8 
	    mySharedPreferences = getSharedPreferences("UserNameSave",
				LocationActivity.MODE_PRIVATE);
		if (null != mySharedPreferences.getString("name", "")
				&& !mySharedPreferences.getString("name", "").equals("")) {
			username=mySharedPreferences.getString("name", "");
		}
		Intent intent=getIntent();
		courseId=intent.getStringExtra("courId");
		ownOrgId=getIntent().getStringExtra("ownOrgId");
		initView();
	}
	private void initView() {
		
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_prized_partic);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.look_course_apply);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		
		titleBarView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,d.getHeight()/15));
		bt_suer_app=(Button) findViewById(R.id.bt_suer_app);
		bt_finsh=(Button) findViewById(R.id.bt_finsh);
		tv_username=(TextView) findViewById(R.id.tv_username);
		tv_username.setHeight(d.getHeight()/15);
		et_course_content=(EditText) findViewById(R.id.et_course_content);
		bt_finsh.setOnClickListener(this);
		bt_suer_app.setOnClickListener(this);
		tv_username.setText(username);
		}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_finsh:
			CourseApply.this.finish();
			break;
		case R.id.bt_suer_app://报名确定提交
			String text=et_course_content.getText().toString();
			UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
			new CourseApplyTask().execute(username, userLogin.getUserInfo().getUserId(),courseId,text,ownOrgId);
			finish();
			break;
		}
		
	}
	private class CourseApplyTask extends AsyncTask<String, Void, CourseApplyBean> {
		AppException e;

		protected CourseApplyBean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				courseApply = ((AppContext) getApplication()).courseApply(
						params[0], params[1],params[2],params[3],params[4]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return courseApply;
		}

		@Override
		protected void onPostExecute(CourseApplyBean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null == result) {
				if (null != e) {
					e.makeToast(CourseApply.this);
				}
				return;

			} else {
				if (result.getStatus().equals("0")) {
					Toast.makeText(CourseApply.this, R.string.course_success,
							Toast.LENGTH_SHORT).show();
					return;
				}else {
					Toast.makeText(CourseApply.this, R.string.course_error,
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}

	}

}
