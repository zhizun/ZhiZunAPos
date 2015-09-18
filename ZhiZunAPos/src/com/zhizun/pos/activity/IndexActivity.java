package com.zhizun.pos.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RemoteViews;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.SystemMessageTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.FileUtils;
import com.ch.epw.utils.JpushUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.SelectRoleDialog;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.InviteCount;
import com.zhizun.pos.bean.SystemMessageNumBean;
import com.zhizun.pos.bean.UpdateInfoDetail;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

/**
 * 界面切换activity 创建人：李林中 创建日期：2014-4-14 上午12:58:06 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class IndexActivity extends TabActivity {
	protected static final String TAG = "IndexActivity";
	UserLogin userLogin;
	Context context;
	// 检查更新新版本参数
	NotificationManager nm = null;
	Notification notify = null;
	private boolean isUping = false;
	private final int NOTIFY_PROGRESS = 354;
	private final int UPDATE_PROGRESS = 3;
	private final int UPDATE_NO = 1;
	private final int UPDATE_YES = 2;
	protected SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyyMMdd");
	UpdateInfoDetail updateInfoDetail;
	private String role;
	InviteCount inviteCount;

	// 定义TabHost对象
	private TabHost tabHost;

	// 定义RadioGroup对象
	private RadioGroup radioGroup;
	private int pageIndex = 0;// tabhost里面的索引
	private String currentRoleTag;// 当前角色
									// -1代表无角色
	Boolean isJoinOrg = true;// 是否加入机构
	private Class[] currentTabClassArray = Constant.mLoginTabClassArray;
	private ImageView tv_message;
	
	int messageNum = 0;
	int feedbackNum = 0;
	int inviteNum = 0;

	protected Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == Constant.IS_JOIN_ORG_NO) {
				isJoinOrg = false;
			} else {
				isJoinOrg = true;
			}
			switch (msg.what) {
			case Constant.ORG_ROLE_PARENT:
				currentRoleTag = Constant.ORG_ROLE_TYPE_PARENT;
				initData();
				break;
			case Constant.ORG_ROLE_TEACHERORORG:
				currentRoleTag = Constant.ORG_ROLE_TYPE_TEACHER;
				initData();
				break;
			}

		}

	};
	private Handler update_handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			final Bundle bundle = msg.getData();
			switch (msg.what) {
			case UPDATE_YES:
				// 对话框询问用户是否更新
				Builder builder = new AlertDialog.Builder(IndexActivity.this);
				builder.setTitle(R.string.update_dlg_title);
				// 是否需要强制升级
				if (bundle.getBoolean("isForceUpgrade")) {// 强制升级
					builder.setCancelable(false);
					builder.setMessage(getResources().getString(
							R.string.update_notofy_content_force_pre)
							+ bundle.getString("updateContent"));
					builder.setNeutralButton(R.string.update_cancel,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {// 退出程序
									AppContext.getApp().cleanUserLoginInfo();
									AppManager.getAppManager().AppExit(
											getApplicationContext());
									dialog.dismiss();
								}
							});

				} else {
					builder.setCancelable(true);
					builder.setMessage(bundle.getString("updateContent"));
					builder.setNeutralButton(R.string.update_cancel,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
				}
				builder.setPositiveButton(R.string.update_start,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 设置通知消息
								nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
								// 创建布局
								RemoteViews contentView = new RemoteViews(
										getPackageName(), R.layout.down_notify);
								// 创建通知
								notify = new Notification();
								Intent intent = new Intent();
								PendingIntent contentIntent = PendingIntent
										.getActivity(IndexActivity.this, 0,
												intent, 0);
								notify.contentIntent = contentIntent;
								notify.contentView = contentView;
								notify.tickerText = "开始更新";
								notify.icon = R.drawable.ic_launcher;
								nm.notify(NOTIFY_PROGRESS, notify);
								// 启动一个下载线程
								new Thread(new ThreadDownApk(bundle
										.getString("updateAddress"), bundle
										.getString("updateVersion"))).start();
								dialog.dismiss();
							}
						});

				Dialog dialog = builder.create();
				dialog.show();
				break;

			case UPDATE_PROGRESS:
				notify.contentView.setProgressBar(R.id.update_progress, 100,
						msg.arg1, false);
				notify.contentView.setTextViewText(R.id.update_show, msg.arg1
						+ "%");
				nm.notify(NOTIFY_PROGRESS, notify);
				break;
			}// switch
		}

	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);
		context = this;
		CheckUpdate();// 检查更新
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		tv_message=(ImageView) findViewById(R.id.tv_message);
		currentRoleTag = getIntent().getStringExtra("currentRoleTag");
		if (TextUtils.isEmpty(currentRoleTag)) {
			currentRoleTag = Constant.ORG_ROLE_TYPE_NOLOGIN;
		}
		pageIndex = getIntent().getIntExtra("pageIndex", 0);

		//先以未登陆角色获得首页
		initData();
		
		//再根据保存的用户信息重置首页数据
		initLogin();

		IntentFilter intentFilter = new IntentFilter(
				"com.ch.epw.REFRESH_INDEXACTIVITY");
		registerReceiver(broadcastReceiver, intentFilter);
		//注册广播，是否要的“我的”显示红点
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.system.Message");
		registerReceiver(messageReceiver, filter);

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			currentRoleTag = intent.getStringExtra("currentRoleTag");
			isJoinOrg = intent.getBooleanExtra("isJoinOrg", true);
			if (TextUtils.isEmpty(currentRoleTag)) {
				currentRoleTag = Constant.ORG_ROLE_TYPE_NOROLE;
				initLogin();
			} else {
				pageIndex = intent.getIntExtra("pageIndex", 0);
				initData();
			}
		}
	};
	BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			messageNum = intent.getIntExtra("messageNum", messageNum);
			feedbackNum = intent.getIntExtra("feedbackNum", feedbackNum);
			inviteNum = intent.getIntExtra("inviteNum", inviteNum);
			int messageNumAll=messageNum + feedbackNum + inviteNum;
			if (messageNumAll <= 0) {
				tv_message.setVisibility(View.GONE);
			}else {
				tv_message.setVisibility(View.VISIBLE);
			}
		}
	};

	TaskCallBack showMyepeiTipsCallBack = new TaskCallBack(){
		@Override
		public void onTaskFinshed(BaseBean result) {
			boolean isShowTips = false;
			Intent intent=new Intent();
			intent.setAction("com.ch.epw.system.Message");
			if(result.getClass().isAssignableFrom(InviteCount.class)){
				InviteCount inviteCount = (InviteCount)result;
				if(inviteCount.getStatus().equals("0")){
					intent.putExtra("inviteNum", Integer.parseInt(inviteCount.getCount()));
					isShowTips = true;
				}
			}else if(result.getClass().isAssignableFrom(SystemMessageNumBean.class)){
				SystemMessageNumBean messageNumBean = (SystemMessageNumBean)result;
				if(messageNumBean.getStatus().equals("0")){
					intent.putExtra("messageNum", messageNumBean.getMsgNum());
					intent.putExtra("feedbackNum", messageNumBean.getFeedbackNum());
					isShowTips = true;
				}
			}
			if(isShowTips){
				context.sendBroadcast(intent);
			}
		};
	};
	
	private void checkMyepeiNewTips(){
		new SystemMessageTask(context, showMyepeiTipsCallBack ).execute();
		new com.ch.epw.task.GetInviteCountTask(context, showMyepeiTipsCallBack ).execute(AppContext.getApp().getToken());
	}

	private void initLogin() {
		userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
			return;
		}

		registerAlias();
		if (null == userLogin.getUserInfo().getCurrentRole()
				|| userLogin.getUserInfo().getCurrentRole().equals("")) {
			if (null == userLogin.getUserInfo().getUserRoleList()
					|| userLogin.getUserInfo().getUserRoleList().size() <= 0) {
				new GetInviteCountTask().execute(AppContext.getApp()
						.getToken());
			} else {
				SelectRoleDialog selectRoleDialog = new SelectRoleDialog(
						context, R.layout.dialog_selecter_role,
						R.style.MyDialog, userLogin.getUserInfo()
								.getUserRoleList(), IndexActivity.this,
						handler);
				selectRoleDialog.show();
			}
		} else {
			final UserRole userRole = userLogin.getUserInfo()
					.getCurrentOrgan();
			new SwitchRolesTask(userRole, context, new TaskCallBack() {
				@Override
				public void onTaskFinshed() {
					if (TextUtils.isEmpty(userRole.getOrgId())) {
						isJoinOrg = false;
					}
					if (userRole.getRoleId().equals(
							Constant.ORG_ROLE_TYPE_PARENT)) {
						currentRoleTag = Constant.ORG_ROLE_TYPE_PARENT;
					} else {
						currentRoleTag = Constant.ORG_ROLE_TYPE_TEACHER;
					}
					initData();
				}
			}).execute(AppContext.getApp().getToken(),
					userRole.getRoleId(), userRole.getOrgId());
		}
	}

	private void registerAlias() {
		// 判断本地是否保存有是否允许接收通知状态值
		final SharedPreferences spPreferences = context.getSharedPreferences(
				userLogin.getUserInfo().getUserId(), Context.MODE_PRIVATE);
		String jpushAlias = "";
		String pushSwitchStatus = Constant.MSG_PUSH_STATUS_YES;
		if(spPreferences != null){
			pushSwitchStatus = spPreferences.getString(Constant.MSG_PUSH_STATUS, Constant.MSG_PUSH_STATUS_YES);
		}
		if(Constant.MSG_PUSH_STATUS_YES.equals(pushSwitchStatus)){
			jpushAlias = userLogin.getUserInfo().getUserId();
		}
		JpushUtil.resetAliasAndTags(jpushAlias);
	}

	/**
	 * 初始化组件
	 */
	private void initData() {
		if (Constant.ORG_ROLE_TYPE_NOROLE.equals(currentRoleTag)) {
			currentTabClassArray = Constant.mLoginTabClassArray;
		} else if (Constant.ORG_ROLE_TYPE_PARENT.equals(currentRoleTag)) {// 家长
			currentTabClassArray = Constant.mParentTabClassArray;
			role="0";
		} else if (Constant.ORG_ROLE_TYPE_TEACHER.equals(currentRoleTag)) {// 老师
																			// 机构
																			// 管理员等
			currentTabClassArray = Constant.mTeacherTabClassArray;
			role="1";
		}
		
		// 实例化TabHost，得到TabHost对象
		tabHost = getTabHost();
		tabHost.clearAllTabs();
		// 得到Activity的个数
		int count = currentTabClassArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = tabHost.newTabSpec(Constant.mTextviewArray[i])
					.setIndicator(Constant.mTextviewArray[i])
					.setContent(getTabItemIntent(i));
			// 将Tab按钮添加进Tab选项卡中
			tabHost.addTab(tabSpec);
		}
		// 给radioGroup设置监听事件
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiobutton_sy:
					pageIndex = 0;
					break;
				case R.id.radiobutton_hd:
					if (Constant.ORG_ROLE_TYPE_NOLOGIN.equals(currentRoleTag)) {
						Intent intent_login_hd = new Intent(context,
								LoginActivity.class);
						intent_login_hd.putExtra("pageIndex", 1);
						startActivityForResult(intent_login_hd,
								Constant.REQUSTCONDE_INDEXTO_LOGIN);
						intoAnim();
						return;
					} else if (Constant.ORG_ROLE_TYPE_NOROLE
							.equals(currentRoleTag)) {
						UIHelper.ToastMessage(context, "您还没有加入任何机构");
						return;
					} else {
						pageIndex = 2;
					}
					break;
				case R.id.radiobutton_wd:
					if (Constant.ORG_ROLE_TYPE_NOLOGIN.equals(currentRoleTag)) {
						Intent intent_login_hd = new Intent(context,
								LoginActivity.class);
						intent_login_hd.putExtra("pageIndex", 3);
						startActivityForResult(intent_login_hd,
								Constant.REQUSTCONDE_INDEXTO_LOGIN);
						intoAnim();
						return;
					} else if (Constant.ORG_ROLE_TYPE_NOROLE
							.equals(currentRoleTag)) {
						UIHelper.ToastMessage(context, "您还没有加入任何机构");
						return;
					} else {
						pageIndex = 3;
					}
					break;
				}
				toggleCurrentTab(pageIndex);
			}
		});
		Log.i(TAG, "pageIndex=" + pageIndex);

		if (!Constant.ORG_ROLE_TYPE_NOLOGIN.equals(currentRoleTag)
				&& !Constant.ORG_ROLE_TYPE_NOROLE.equals(currentRoleTag)) {
			((RadioButton) radioGroup.getChildAt(pageIndex)).setChecked(true);
			toggleCurrentTab(pageIndex);
		} else {
			((RadioButton) radioGroup.getChildAt(pageIndex)).toggle();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (Constant.REQUSTCONDE_INDEXTO_LOGIN == resultCode
				&& Constant.REQUSTCONDE_INDEXTO_LOGIN == requestCode) {
			currentRoleTag = data.getStringExtra("currentRoleTag");
			pageIndex = data.getIntExtra("pageIndex", 0);
			initData();
		}
	}

	/**
	 * 给Tab选项卡设置内容（每个内容都是一个Activity）
	 */
	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, currentTabClassArray[index]);
		intent.putExtra("role", role);
		return intent;
	}

	/**
	 * 点击时候 设置radiobutton的图片状态
	 */
	private void toggleCurrentTab(int index) {
		tabHost.setCurrentTabByTag(Constant.mTextviewArray[index]);
		Intent intent = new Intent("com.ch.epw.HIDE_LEFTIMG_MYEPEI");
		sendBroadcast(intent);
		
		//用户以任意角色登陆，刷新我的益培红点提示
		if(!Constant.ORG_ROLE_TYPE_NOROLE.equals(currentRoleTag)
			&& !Constant.ORG_ROLE_TYPE_NOLOGIN.equals(currentRoleTag)
			&& pageIndex != 3 //不是点击 我的 按钮
		){
			checkMyepeiNewTips();
		}
	}

	// 返回动画
	public void backAnim() {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	// 前进动画
	public void intoAnim() {
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	private long mExitTime;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				AppManager.getAppManager().AppExit(context);
			}
			return true;
		}
		
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 检查更新
	 */
	public void CheckUpdate() {
		new Thread(new CheckUpThread()).start();
	}

	/**
	 * 
	 * @author 李林中 负责网络通信检查APK更新版本
	 */
	class CheckUpThread implements Runnable {
		@Override
		public void run() {
			Message msg = Message.obtain();
			Bundle bundle = new Bundle();
			int version;
			// 获取APK包信息 和版本信息
			PackageInfo pi = AppContext.getApp().getPackageInfo();
			if (pi != null) {
				version = pi.versionCode;
				Log.i(TAG, "versoncode=" + pi.versionCode + ",versionname="
						+ pi.versionName + ",packagename=" + pi.packageName
						+ ",applicationInfo.packageName"
						+ pi.applicationInfo.packageName
						+ ",applicationInfo.name" + pi.applicationInfo.name);
				// 检查最新版本
				try {
					updateInfoDetail = AppContext.getApp().getClientVersion(
							Constant.UPDATE_CLIENTTYPE_ANDROID);
					// 1、首先判断是否需要升级
					if (null != updateInfoDetail.getUpdateInfo()
							&& null != updateInfoDetail.getUpdateInfo()
									.getVersion()
							&& !updateInfoDetail.getUpdateInfo().getVersion()
									.equals("")
							&& version < Integer.parseInt(updateInfoDetail
									.getUpdateInfo().getVersion())) {
						// 需要更新

						// 2、其次判断是否需要强制升级
						if (updateInfoDetail.getUpdateInfo()
								.getIsForceUpgrade()
								.equals(Constant.UPDATE_ISFORCEUPGRADE_YES)) {
							bundle.putBoolean("isForceUpgrade", true);
						} else {
							bundle.putBoolean("isForceUpgrade", false);
						}
						bundle.putString("updateAddress", updateInfoDetail
								.getUpdateInfo().getDownloadUrl());
						bundle.putString("updateContent", updateInfoDetail
								.getUpdateInfo().getNote());
						bundle.putString("updateVersion", updateInfoDetail
								.getUpdateInfo().getVersion());
						msg.what = UPDATE_YES;
						msg.setData(bundle);
					} else {
						// 不需要更新
						msg.what = UPDATE_NO;
					}
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			update_handler.sendMessage(msg);
		}
	}

	/**
	 * APK下载线程，负责下载需要更新的APK 下载完成会提醒用户安装
	 * 
	 * @author
	 * 
	 */
	class ThreadDownApk implements Runnable {
		String updateAddress;
		String updateVersion;

		public ThreadDownApk(String addressString, String updateVersion) {
			super();
			this.updateAddress = addressString;
			this.updateVersion = updateVersion;
		}

		@Override
		public void run() {
			// 检查是否正在更新
			if (isUping) {
				return;
			}
			isUping = true;

			// 得到文件对象
			File file = FileUtils.createRealFile("epeiwang", "epeiwang"
					+ updateVersion + "_" + mDateFormat2.format(new Date())
					+ ".apk"); // 创建文件
			if (file != null) {
				try {
					URL url = new URL(updateAddress);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// 获取文件大小
					int size = conn.getContentLength();
					System.out.println("-- 文件大小 -->" + size);
					// 获取输入流
					InputStream in = conn.getInputStream();
					// 创建文件输出流
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int length = 0, progress = 0;
					float count = 0;
					while ((length = in.read(buf)) != -1) {
						int cur = 0;

						// 写出到文件
						fos.write(buf, 0, length);
						count += length;

						// 更新进度条 （当前下载数据 / 文件大小）* 100 得到下载百分比
						if (count < size) {
							cur = Math.round((count / size) * 100);

						} else {
							progress = 100;
						}
						if (cur > progress) {
							progress = cur;
							Message msg = Message.obtain();
							msg.what = UPDATE_PROGRESS;
							msg.arg1 = progress;
							update_handler.sendMessage(msg);
						}
					}// while
					fos.close();
					in.close();

					// 下载完成开始安装
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file),
							"application/vnd.android.package-archive");
					startActivity(intent);

					// 更新标记
					isUping = false;
				} catch (Exception e) {
					e.printStackTrace();
				}// try

			}// if
		}// run
	}// class

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
			if (null != result) {
				if (result.getStatus().equals("0")) {
					if (!TextUtils.isEmpty(result.getCount())
							&& Integer.parseInt(result.getCount()) > 0) {

						currentRoleTag = Constant.ORG_ROLE_TYPE_NOROLE;
						isJoinOrg = false;
						initData();

						Intent intent = new Intent(context,
								MyInvitationActivity.class);
						startActivity(intent);

						intoAnim();
					} else {
						currentRoleTag = Constant.ORG_ROLE_TYPE_NOROLE;
						isJoinOrg = false;
						initData();
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
		unregisterReceiver(messageReceiver);
	}
}
