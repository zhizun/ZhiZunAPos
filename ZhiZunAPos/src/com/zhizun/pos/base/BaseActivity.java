package com.zhizun.pos.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import cn.jpush.android.api.JPushInterface;

import com.ch.epw.listener.BackGestureListener;
import com.ch.epw.task.FriendsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.FileUtils;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.FriendsCircleActivity;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.CircleofFriendsBean;
import com.zhizun.pos.bean.PhoneContactBean;
import com.zhizun.pos.bean.UpdateInfoDetail;
import com.zhizun.pos.bean.UserLogin;

public class BaseActivity extends Activity {

	// 下拉刷新相关参数
	protected ListView mListView;

	protected PullToRefreshListView mPullListView;
	private static final String[] PHONES_PROJECTION = new String[] {
		Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	protected int mCurPage = 1;// 当前页面
	protected SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	protected SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyyMMdd");
	protected boolean hasMoreData = true;
	protected BadgeView newReplyMsgBadgeView;
	// 图片缓存插件
	protected ImageLoader imageLoader;
	protected DisplayImageOptions options;

	protected int msgFromTag = 0;// 跳转到详情页的时候，这条消息是来自推送通知，还是来自最新回复列表
									// 1表示来自推送通知，0表示来自最新回复列表

	// 压缩后的图片 保存路径设置
	public static final String FILE_SDCARD = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "epeiwang"
			+ File.separator;

	public static final File FILE_LOCAL = new File(FILE_SDCARD, "epwimage");
	public static final String TAG = BaseActivity.class.getName();
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	private ArrayList<PhoneContactBean> phoneContactList = new ArrayList<PhoneContactBean>();
	/** 手势监听 */
	GestureDetector mGestureDetector;
	/** 是否需要监听手势关闭功能 */
	private boolean mNeedBackGesture = false;
	protected ProgressDialog progress = null;// 进度条

	// 最大输入字符数 限制参数
	public final int MAX_LENGTH = 500;// 最大字符数
	public int Rest_Length = 0;// 剩余可输入字符数
	public String sInputFormat;

	public String sFinalInput;;
	// 检查更新新版本参数
	protected NotificationManager nm = null;
	protected Notification notify = null;
	protected boolean isUping = false;
	protected final int NOTIFY_PROGRESS = 354;

	public ProgressDialog getProgress() {
		return progress;
	}

	public void setProgress(ProgressDialog progress) {
		this.progress = progress;
	}

	protected final int UPDATE_PROGRESS = 3;
	protected final int UPDATE_NO = 1;
	protected final int UPDATE_YES = 2;
	UpdateInfoDetail updateInfoDetail;

	private String activityCalledOnBack; // 当前activity退出时启动该activity，为空时则不调用
	protected Handler myepeihandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			final Bundle bundle = msg.getData();
			switch (msg.what) {
			case UPDATE_NO:
				UIHelper.ToastMessage(BaseActivity.this, R.string.noupdate);
				break;
			case UPDATE_YES:
				// 对话框询问用户是否更新
				Builder builder = new AlertDialog.Builder(BaseActivity.this);
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
										.getActivity(BaseActivity.this, 0,
												intent, 0);
								notify.contentIntent = contentIntent;
								notify.contentView = contentView;
								notify.tickerText = "开始更新";
								notify.icon = R.drawable.ic_launcher;
								nm.notify(NOTIFY_PROGRESS, notify);
								// 启动一个下载线程
								new Thread(new ThreadDownApk(bundle
										.getString("updateAddress"))).start();
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
	protected Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			final Bundle bundle = msg.getData();
			switch (msg.what) {
			case UPDATE_YES:
				// 对话框询问用户是否更新
				Builder builder = new AlertDialog.Builder(BaseActivity.this);
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
										.getActivity(BaseActivity.this, 0,
												intent, 0);
								notify.contentIntent = contentIntent;
								notify.contentView = contentView;
								notify.tickerText = "开始更新";
								notify.icon = R.drawable.ic_launcher;
								nm.notify(NOTIFY_PROGRESS, notify);
								// 启动一个下载线程
								new Thread(new ThreadDownApk(bundle
										.getString("updateAddress"))).start();
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imageLoader = ImageLoader.getInstance();

		initGestureDetector();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		// 设置退出时需要启动的activity
		activityCalledOnBack = getIntent().getStringExtra(
				"activityCalledOnBack");
	}

	@Override
	public void onBackPressed() {
		if (activityCalledOnBack != null) {
			AppManager.getAppManager().startActivity(BaseActivity.this,
					activityCalledOnBack);
		}
		closeProgressDialog();
		super.onBackPressed();
	}

	private void initGestureDetector() {
		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getApplicationContext(),
					new BackGestureListener(this));
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mNeedBackGesture) {
			return mGestureDetector.onTouchEvent(ev)
					|| super.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	/*
	 * 设置是否进行手势监听
	 */
	public void setNeedBackGesture(boolean mNeedBackGesture) {
		this.mNeedBackGesture = mNeedBackGesture;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	/*
	 * 返回
	 */
	public void doBack() {
		onBackPressed();
	}

	// 返回动画
	public void backAnim() {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	// 前进动画
	public void intoAnim() {
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	// 下拉加载下拉刷新要用到
	// 最后一次刷新时间
	protected void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		mPullListView.setLastUpdatedLabel(text);
	}

	protected String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

	// 弹出进度条
	protected void showProgressDialog(Context context, String titleString,
			String msg) {
		progress = ProgressDialog.show(context, titleString, msg);
	}

	// 关闭进度条
	protected void closeProgressDialog() {
		if (progress != null) {
			progress.dismiss();
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
//		new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
						// TODO Auto-generated method stub
						SharedPreferences	mySharedPreferences = getSharedPreferences("UserSelect",FriendsCircleActivity.MODE_PRIVATE);
						String osAllowReadContacts =mySharedPreferences.getString("osAllowReadContacts", "-1");
						if (!osAllowReadContacts.equals("-1")) {
							//上传通讯录
							boolean s=readSystemContacts();
							if (s) {//读取到了
//								uploadPhoneContacts();
								SharedPreferences.Editor editor = mySharedPreferences
										.edit();
								editor.putString("osAllowReadContacts", "1");
								editor.commit();
							}else {//没读到通讯录
								SharedPreferences.Editor editor = mySharedPreferences
										.edit();
								editor.putString("osAllowReadContacts", "0");
								editor.commit();
							}
						}
//					}
//				}).start();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
		MobclickAgent.onPageStart("SplashScreen"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPageEnd("SplashScreen"); // 保证 onPageEnd 在onPause
													// 之前调用,因为 onPause 中会保存信息
		MobclickAgent.onPause(this);
	}

	/**
	 * 通过displayImage显示图片，增加了默认图片的显示及异常处理
	 * 
	 * @param uri
	 * @param imageView
	 * @param options
	 */
	protected void showPicture(String uri, ImageView imageView,
			DisplayImageOptions options) {
		if (uri == null || uri.equals("") || imageView == null) {
			return;
		}
		// 如果是系统默认图片不处理
		if (ImageUtils.isSysDefault(uri)) {
			// imageView有可能未清空，无法使用null判断
			// if(imageView.getDrawable() == null){
			imageView.setImageDrawable(options
					.getImageForEmptyUri(getResources()));
			// }
			return;
		}
		if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
			uri = URLs.URL_IMG_API_HOST + uri;
		}
		imageLoader.displayImage(uri, imageView, options);
	}

	/**
	 * 检查更新
	 */
	public void CheckUpdate(Handler handler) {
		new Thread(new CheckUpThread(handler)).start();
	}

	/**
	 * 
	 * @author 李林中 负责网络通信检查APK更新版本
	 */
	class CheckUpThread implements Runnable {
		Handler updateHandler;

		public CheckUpThread(Handler handler) {
			super();
			this.updateHandler = handler;
		}

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
			updateHandler.sendMessage(msg);
		}
	}

	/**
	 * APK下载线程，负责下载需要更新的APK 下载完成会提醒用户安装
	 * 
	 * @author
	 * 
	 */
	protected class ThreadDownApk implements Runnable {
		String updateAddress;

		public ThreadDownApk(String addressString) {
			super();
			this.updateAddress = addressString;
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
					+ mDateFormat2.format(new Date()) + ".apk"); // 创建文件
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
							handler.sendMessage(msg);
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
	 * 获取手机联系人
	 */
	private boolean readSystemContacts() {
		boolean isAllow = false;
		ContentResolver resolver = this.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				isAllow = true;
				PhoneContactBean phoneContact = new PhoneContactBean();
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// 得到联系人ID
//				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// 得到联系人头像ID
				// Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				phoneContact.setPhoneNum(StringUtils.StringFilter(phoneNumber));
//				phoneContact.setPhoneNum(phoneNumber);
				phoneContact.setDesplayName(contactName);
				phoneContactList.add(phoneContact);
			}
			phoneCursor.close();
		}
		return isAllow;
	}

	/**
	 * 上传通讯录
	 */
	private void uploadPhoneContacts() {
		StringBuffer json = new StringBuffer("[");
		if (phoneContactList != null && phoneContactList.size() > 0 ) {
			for (int i = 0; i < phoneContactList.size(); i++) {
				PhoneContactBean phoneContact = phoneContactList.get(i);
				json.append("{\"phoneNumber\":\""
						+ phoneContact.getPhoneNum() + "\",\"name\":\""
						+ phoneContact.getDesplayName() + "\"}");
				if (i != phoneContactList.size() - 1) {
					json.append(",");
				}
				System.out.println("姓名："+phoneContact.getDesplayName());
				System.out.println("电话："+phoneContact.getPhoneNum());
			}
		}
		json.append("]");
		if (isLogin()) {
			uploadPhoneContacts(true,json.toString());
		}
	}

	public void uploadPhoneContacts(final boolean userAllowReadContacts,String json) {
		new FriendsTask(this, new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				CircleofFriendsBean circleofFriendsBean = (CircleofFriendsBean) result;
				if (circleofFriendsBean != null) {
					if (circleofFriendsBean.getStatus()!=null) {
						if (circleofFriendsBean.getStatus().equals("0")) {// 同步成功,
							
						}else if(circleofFriendsBean.getStatus().equals("1003")){//登录过期
							
						}
					}
				}
			}

		}).execute(userAllowReadContacts ? "1" :"0", json);
	}
	private boolean isLogin() {
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
//			Intent intent = new Intent(this, LoginActivity.class);
//			this.startActivity(intent);
			return false;
		}
		return true;
	}
}
