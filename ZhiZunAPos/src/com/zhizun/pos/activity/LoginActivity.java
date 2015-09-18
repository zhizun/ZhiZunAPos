package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.JpushUtil;
import com.ch.epw.utils.SpUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.SelectRoleDialog;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.InviteCount;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

/**
 * 登录activity 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class LoginActivity extends BaseActivity {
	protected static final String TAG = LoginActivity.class.getName();
	public static final int RESULT_USER_LOGIN_FINISHED = 1;
	private TitleBarView titleBarView;
	private Context context;
	private Button btn_login_submit;
	EditText et_login_password, et_login_username;
	UserLogin userLogin;
	UserInfo userInfo;
	TextView iv_login_forgetpassword;
	InviteCount inviteCount;
	SharedPreferences mySharedPreferences;
	Boolean isJoinOrg = true;
	private String loginWay="message";
	protected Handler handler_role = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == Constant.IS_JOIN_ORG_NO) {
				isJoinOrg = false;
			} else {
				isJoinOrg = true;
			}
			switch (msg.what) {
			case Constant.ORG_ROLE_PARENT:
				setActivityResultTo(Constant.ORG_ROLE_TYPE_PARENT, isJoinOrg);
				break;
			case Constant.ORG_ROLE_TEACHERORORG:
				setActivityResultTo(Constant.ORG_ROLE_TYPE_TEACHER, isJoinOrg);
				break;
			}
		}

	};
	private TextView iv_login_phonepassword;
	private LinearLayout ll_user_login;
	private LinearLayout ll_phone_login;
	private TextView iv_login_accountpassword;
	private TextView tv_forget_passrword_timer;
	private EditText phone_login_username;
	private EditText phone_login_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		context = this;
		initView();
		mySharedPreferences = getSharedPreferences("UserNameSave",
				LocationActivity.MODE_PRIVATE);
		String loginPhone = mySharedPreferences.getString("name", "");
		if (null != loginPhone && !loginPhone.equals("")) {
			et_login_username.setText(loginPhone);
			phone_login_username.setText(loginPhone);
		}
		// 设置保存的用户名和伪密码
		userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin != null && userLogin.getUserInfo() != null) {
			userInfo = userLogin.getUserInfo();
			et_login_username.setText(userInfo.getLoginName());
			et_login_password.setText(userInfo.getLoginPseudoCode());
		}
	}

	private void initView() {
		titleBarView = (TitleBarView) this.findViewById(R.id.title_bar_login);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);
		titleBarView.setTitleText(R.string.user_login);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		phone_login_username=(EditText) findViewById(R.id.phone_login_username);
		phone_login_password=(EditText)findViewById(R.id.phone_login_password);
		// 暂时屏蔽掉手机端注册按钮 2015/3/13 20:10:15 liyingchun
		// 李林中 20150506放开
		titleBarView.setRightText(R.string.registration_text);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, RegisterStep1Activity.class);
				startActivity(intent);
				intoAnim();
			}
		});
		ll_user_login = (LinearLayout) findViewById(R.id.ll_user_login);// 账号登录
		ll_phone_login = (LinearLayout) findViewById(R.id.ll_phone_login);// 短信登陆
		et_login_password = (EditText) this
				.findViewById(R.id.et_login_password);

		et_login_password.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DEL) {
					et_login_password.setText("");
				}
				return false;
			}
		});
		et_login_username = (EditText) this
				.findViewById(R.id.et_login_username);
		btn_login_submit = (Button) this.findViewById(R.id.btn_login_submit);
		btn_login_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loginWay.equals("basic")) {
				
				String username = et_login_username.getText().toString().trim();

				String password = et_login_password.getText().toString().trim();
				if (null == username || "".equals(username)) {
						UIHelper.ToastMessage(context, "用户名不能为空！", 0);
					return;
				}
				if (null == password || "".equals(password)) {
						UIHelper.ToastMessage(context, "密码不能为空！", 0);
					return;
				}

				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));

					// 如果是保存的伪密码，直接登录
				if (userInfo != null && userInfo.getCurrentOrgan() != null
						&& userInfo.getLoginPseudoCode().equals(password)
						&& userInfo.getLoginName().equals(username)) {
					final UserRole userRole = userInfo.getCurrentOrgan();

					if (TextUtils.isEmpty(userRole.getOrgId())) {
						isJoinOrg = false;
					} else {
						isJoinOrg = true;
					}
					new SwitchRolesTask(userRole, context, new TaskCallBack() {
						@Override
						public void onTaskFinshed() {
							if (userRole.getRoleId().equals(
									Constant.ORG_ROLE_TYPE_PARENT)) {

								setActivityResultTo(
										Constant.ORG_ROLE_TYPE_PARENT,
										isJoinOrg);
							} else {
											// 机构 或者老师
								setActivityResultTo(
										Constant.ORG_ROLE_TYPE_TEACHER,
										isJoinOrg);
							}
						}
					}).execute(AppContext.getApp().getToken(),
							userRole.getRoleId(), userRole.getOrgId());
				} else {
					new GetUserLoginTask().execute(username, password,loginWay);
						// SharedPreferences.Editor editor = mySharedPreferences
						// .edit();
						// editor.putString("name", username);
						// editor.commit();
				}
					// 每次提交后清空用户密码
				et_login_password.setText("");
				
			}else {
				if (phone_login_password.getText().toString()!=null&&!phone_login_password.getText().toString().equals("")) {
					if (phone_login_password.getText().toString().length()==6) {
						new GetUserLoginTask().execute(phone_login_username.getText().toString(), phone_login_password.getText().toString(),loginWay);
					}else {
							Toast.makeText(LoginActivity.this, "请正确输入验证码", 1)
									.show();
					}
				}
				
			}
			}
		});
		iv_login_forgetpassword = (TextView) findViewById(R.id.iv_login_forgetpassword);
		iv_login_forgetpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// showPopupWindow(iv_login_forgetpassword);
				Intent intent = new Intent(context,
						ForgetPasswordStep1Activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		// 切换到使用短信登陆 "使用账号登录"
		iv_login_phonepassword=(TextView)findViewById(R.id.iv_login_phonepassword);
		iv_login_phonepassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginWay="basic";
				ll_user_login.setVisibility(View.VISIBLE);// 账号输入框
				iv_login_forgetpassword.setVisibility(View.VISIBLE);// 忘记密码
				ll_phone_login.setVisibility(View.GONE);// 手机输入框
				iv_login_phonepassword.setVisibility(View.GONE);// 使用账号登录
				iv_login_accountpassword.setVisibility(View.VISIBLE);// 使用手机登陆
			}
		});
		// 切换到使用账号登录 "使用手机登陆"
		iv_login_accountpassword=(TextView)findViewById(R.id.iv_login_accountpassword);
		iv_login_accountpassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginWay="message";
				ll_user_login.setVisibility(View.GONE);
				ll_phone_login.setVisibility(View.VISIBLE);
				iv_login_forgetpassword.setVisibility(View.GONE);
				iv_login_phonepassword.setVisibility(View.VISIBLE);
				iv_login_accountpassword.setVisibility(View.GONE);
			}
		});
		tv_forget_passrword_timer = (TextView) findViewById(R.id.tv_forget_passrword_timer);
		tv_forget_passrword_timer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!phone_login_username.getText().toString().equals("")&&phone_login_username.getText().toString()!=null) {
					if(phone_login_username.getText().toString().length()!=11){
						Toast.makeText(LoginActivity.this, "请正确输入手机号", 1)
								.show();
					}else {
						tv_forget_passrword_timer.setEnabled(false);
						timer.start();
						new SendSmsCodeBindTask().execute(phone_login_username.getText().toString(),
								Constant.VALIDTYPE_LOGIN);
					}
				}else {
					Toast.makeText(LoginActivity.this, "手机号不能为空", 1).show();
				}
				
			}
		});
		tv_forget_passrword_timer.setEnabled(true);
//		timer.start();
	}

	/**
	 * 发送手机验证码 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class SendSmsCodeBindTask extends AsyncTask<String, Void, Result> {
		AppException e;
		private Result result;

		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				result = ((AppContext) getApplication()).sendSmsCodeLogin(
						params[0], params[1]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.e = e;
				AppException.network(e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "验证码已发送到您的手机"
							+ phone_login_username.getText().toString());
				} else {
					timer.cancel();
					tv_forget_passrword_timer.setEnabled(true);
					tv_forget_passrword_timer.setText("重新发送");
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}
	
	/**
	 * 倒计时 60秒
	 */
	private CountDownTimer timer = new CountDownTimer(
			Constant.SMS_SEND_INTERVAL, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			tv_forget_passrword_timer.setText("重新发送（"
					+ (millisUntilFinished / 1000) + "）");
		}

		@Override
		public void onFinish() {
			tv_forget_passrword_timer.setEnabled(true);
			tv_forget_passrword_timer.setText("重新发送");
		}
	};
	private void showPopupWindow(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.list_popwindow_findpassword_item, null);
		// 设置点击事件
		TextView tv_list_popwindow_edit = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_edit);

		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		tv_list_popwindow_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						ForgetPasswordStep1Activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});

		int[] arrayOfInt = new int[2];
		// 获取点击按钮的坐标
		view.getLocationOnScreen(arrayOfInt);
		int x = arrayOfInt[0];
		int y = arrayOfInt[1];
		// 设置好参数之后再show 从右边弹出
		// 设置popwindow出现和消失动画
		popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
		popupWindow.showAtLocation(view, 0, x, y - 15);

	}

	/**
	 * 获得个人登陆信息
	 * 
	 * @ClassName: GetUserLoginTask
	 * @Description: TODO(这里用一句话描述这个类的作用)
	 * @author yangzhenwei
	 * @date 2014-7-1 上午2:09:46
	 * 
	 */
	private class GetUserLoginTask extends AsyncTask<String, Void, UserLogin> {
		AppException e;

		protected UserLogin doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				userLogin = ((AppContext) getApplication()).loginVerify(
						params[0], params[1],params[2]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return userLogin;
		}

		@Override
		protected void onPostExecute(UserLogin result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;

			} else {

				if (!result.getStatus().equals("0")) {
					// Toast.makeText(context, R.string.login_text_fail,
					// Toast.LENGTH_SHORT).show();
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
				// 判断本地是否保存有是否允许接收通知状态值
				final SharedPreferences spPreferences = context
						.getSharedPreferences(userLogin.getUserInfo()
								.getUserId(), Context.MODE_PRIVATE);

				// 如果允许推送消息，则设置jpushAlias为用户ID，否则设置为空，表示不接受推送
				String jpushAlias = "";
				if (null == spPreferences) {
					jpushAlias = userLogin.getUserInfo().getUserId();
				} else {
					if (spPreferences.getString(Constant.MSG_PUSH_STATUS,
							Constant.MSG_PUSH_STATUS_YES).equals(
							Constant.MSG_PUSH_STATUS_YES)) {
						jpushAlias = userLogin.getUserInfo().getUserId();
					}
				}
				JpushUtil.resetAliasAndTags(jpushAlias);

				// 保存登录配置信息
				AppContext.getApp().saveLoginInfo(result);
				SpUtil.setStringSharedPerference(SpUtil.getInstance()
						.getSharePerference(context, "appToken"), "token",
						result.getToken());
				Toast.makeText(context, R.string.login_text_success,
						Toast.LENGTH_SHORT).show();

				// 用户手机号码
				String userPhone = userLogin.getUserInfo().getPhone();
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putString("name", userPhone == null ? "" : userPhone);
				editor.commit();

				// 取出上回登录时候的角色
				String role = result.getUserInfo().getCurrentRole();
				if (null == role || role.equals("")) {
					if (null == result.getUserInfo().getUserRoleList()
							|| result.getUserInfo().getUserRoleList().size() <= 0) {
						new GetInviteCountTask().execute(AppContext.getApp()
								.getToken());

					} else {
						SelectRoleDialog selectRoleDialog = new SelectRoleDialog(
								context, R.layout.dialog_selecter_role,
								R.style.MyDialog, result.getUserInfo()
										.getUserRoleList(), LoginActivity.this,
								handler_role);
						selectRoleDialog.show();
					}

				} else {

					if (TextUtils.isEmpty(result.getUserInfo()
							.getCurrentOrgan().getOrgId())) {
						isJoinOrg = false;
					} else {
						isJoinOrg = true;
					}
					if (role.equals("0")) {
						// Intent intent = new Intent(context,
						// MainActivity.class);
						// startActivity(intent);
						// LoginActivity.this.finish();
						// intoAnim();

						setActivityResultTo(Constant.ORG_ROLE_TYPE_PARENT,
								isJoinOrg);
					} else {
						// 机构 或者老师
						// Intent intent = new Intent(context,
						// NavigationTeacherActivity.class);
						// startActivity(intent);
						// LoginActivity.this.finish();
						// intoAnim();

						setActivityResultTo(Constant.ORG_ROLE_TYPE_TEACHER,
								isJoinOrg);
					}
				}

			}
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		// AppManager.getAppManager().AppExit(context);
		backAnim();
	}

	private void setActivityResultTo(String param, Boolean bl) {

		Intent intent = new Intent();
		intent.putExtra("currentRoleTag", param);
		intent.putExtra("pageIndex", getIntent().getIntExtra("pageIndex", 0));
		intent.putExtra("isJoinOrg", bl);
		intent.setAction("com.ch.epw.REFRESH_INDEXACTIVITY");
		sendBroadcast(intent);
		setResult(RESULT_USER_LOGIN_FINISHED);
		LoginActivity.this.finish();
		backAnim();

	}

	/**
	 * 获取邀请数量 创建人：李林中 创建日期：2015-5-6 下午12:03:43 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetInviteCountTask extends AsyncTask<String, Void, InviteCount> {
		AppException e;

		@Override
		protected InviteCount doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				inviteCount = AppContext.getApp().getInviteCount(params[0]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
				inviteCount = null;
			}
			return inviteCount;
		}

		@Override
		protected void onPostExecute(InviteCount result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (null != result) {
				if (result.getStatus().equals("0")) {
					if (!TextUtils.isEmpty(result.getCount())
							&& Integer.parseInt(result.getCount()) > 0) {
						Intent intent_broadcast = new Intent();
						intent_broadcast.putExtra("currentRoleTag", Constant.ORG_ROLE_TYPE_NOROLE);
						intent_broadcast.putExtra("pageIndex", getIntent().getIntExtra("pageIndex", 0));
						intent_broadcast.putExtra("isJoinOrg", false);
						intent_broadcast.setAction("com.ch.epw.REFRESH_INDEXACTIVITY");
						sendBroadcast(intent_broadcast);
						
						Intent intent = new Intent(context,
								MyInvitationActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
						intoAnim();
					} else {
						setActivityResultTo(Constant.ORG_ROLE_TYPE_NOROLE,
								false);// 无角色
					}
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}
}
