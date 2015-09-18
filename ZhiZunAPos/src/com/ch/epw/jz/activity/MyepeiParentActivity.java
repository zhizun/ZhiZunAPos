package com.ch.epw.jz.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.AboutActivity;
import com.zhizun.pos.activity.MyMessageActivity;
import com.zhizun.pos.activity.MyepeiPersonInfoActivity;
import com.zhizun.pos.activity.MyepeiSwitchRoleActivity;
import com.zhizun.pos.activity.PrizedEventActivity;
import com.zhizun.pos.activity.SettingsActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.InviteCount;
import com.zhizun.pos.bean.PersonInfoDetail;
import com.zhizun.pos.bean.StudentTeacherOrgClassList;
import com.zhizun.pos.bean.UserRoleList;
import com.zhizun.pos.widget.circularimage.CircularImage;

/**
 * 我的益培家长端 创建人：李林中 创建日期：2014-12-15 上午10:08:08 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiParentActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	InviteCount inviteCount;
	CircularImage iv_myepei_common_title_pic;
	RelativeLayout rl_myepei_techer_myinvitation;
	LinearLayout rl_myepei_common_myinfo_rolenum;// 角色
	TextView tv_myepei_common_myinfo_rolenum;
	TextView tv_myepei_invitecount; // 邀请数量
	BadgeView tv_myepei_invitecount_bv; // 邀请数量，提醒BadgeView对象
	LinearLayout rl_myepei_common_myinfo_class;// 班级
	RelativeLayout rl_myepei_checkupdate;// 检查更新
	TextView tv_myepei_currentversion;// 版本号
	TextView tv_myepei_common_myinfo_classnum;
	TextView tv_myepei_common_title_name;
	TextView tv_myepei_common_title_phone;
	RelativeLayout rl_myepei_myinfo;// 个人信息
	PersonInfoDetail personInfoDetail;
	UserRoleList userRoleList;
	StudentTeacherOrgClassList studentTeacherOrgClassList;
	RelativeLayout rl_myepei_techer_babyinfo;
	RelativeLayout rl_myepei_about;
	RelativeLayout rl_myepei_techer_message;
	
	RelativeLayout rl_myepei_techer_myagent;
	private RelativeLayout rl_myepei_techer_prizedevent;
	private PersonInfoDetail person_info;
	private TextView tv_myemessage_invitecount;
	private String role="0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei);
		context = this;
		options = Options.getListOptions();
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		initView();
		// 当点击我的时候 隐藏左边返回按钮
				IntentFilter intentFilter = new IntentFilter(
						"com.ch.epw.HIDE_LEFTIMG_MYEPEI");
				registerReceiver(broadcastReceiver, intentFilter);
	}
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			titleBarView.getIvLeft().setVisibility(View.GONE);
			titleBarView.setBarLeftOnclickListener(null);
		}
	};
	@SuppressLint("ResourceAsColor")
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_myepei);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		titleBarView.getBtnRight().setBackgroundResource(R.drawable.bg_setting);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SettingsActivity.class);
				startActivity(intent);
				intoAnim();
			}
		});
		iv_myepei_common_title_pic = (CircularImage) this
				.findViewById(R.id.iv_myepei_common_title_pic);
		tv_myepei_common_title_phone = (TextView) findViewById(R.id.tv_myepei_common_title_phone);
		tv_myepei_common_title_name = (TextView) findViewById(R.id.tv_myepei_common_title_name);
		iv_myepei_common_title_pic.setImageResource(R.drawable.default_photo);
		rl_myepei_techer_myinvitation = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_techer_myinvitation);

		// 我的邀请
		rl_myepei_techer_myinvitation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						MyInvitationParentActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});
		rl_myepei_common_myinfo_rolenum = (LinearLayout) findViewById(R.id.rl_myepei_common_myinfo_rolenum);
		// rl_myepei_common_myinfo_rolenum.setOnClickListener(this);
		tv_myepei_invitecount = (TextView) findViewById(R.id.tv_myepei_invitecount);
		tv_myemessage_invitecount=(TextView)findViewById(R.id.tv_myemessage_invitecount);
		// 以tv_myepei_invitecount_bv带样式的显示代替tv_myepei_invitecount
		tv_myepei_invitecount_bv = new BadgeView(context);
		tv_myepei_invitecount_bv.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
		tv_myepei_invitecount_bv.setTargetView(tv_myepei_invitecount);
		tv_myepei_invitecount_bv.setLayoutParams(tv_myepei_invitecount
				.getLayoutParams());

		rl_myepei_common_myinfo_class = (LinearLayout) findViewById(R.id.rl_myepei_common_myinfo_class);
		// rl_myepei_common_myinfo_class.setOnClickListener(this);
		rl_myepei_myinfo = (RelativeLayout) findViewById(R.id.rl_myepei_myinfo);
		// rl_myepei_myinfo.setOnClickListener(this);
		tv_myepei_common_myinfo_rolenum = (TextView) findViewById(R.id.tv_myepei_common_myinfo_rolenum);
		tv_myepei_common_myinfo_classnum = (TextView) findViewById(R.id.tv_myepei_common_myinfo_classnum);
		rl_myepei_techer_babyinfo = (RelativeLayout) findViewById(R.id.rl_myepei_techer_babyinfo);
		rl_myepei_techer_babyinfo.setOnClickListener(this);
		rl_myepei_techer_prizedevent = (RelativeLayout) findViewById(R.id.rl_myepei_techer_prizedevent);
		rl_myepei_techer_prizedevent.setOnClickListener(this);
		rl_myepei_checkupdate = (RelativeLayout) findViewById(R.id.rl_myepei_checkupdate);
		rl_myepei_checkupdate.setOnClickListener(this);
		tv_myepei_currentversion = (TextView) findViewById(R.id.tv_myepei_currentversion);
		rl_myepei_about = (RelativeLayout) findViewById(R.id.rl_myepei_about);
		rl_myepei_about.setOnClickListener(this);
		rl_myepei_techer_message=(RelativeLayout) findViewById(R.id.rl_myepei_techer_message);
		rl_myepei_techer_message.setOnClickListener(this);
		rl_myepei_techer_myagent=(RelativeLayout) findViewById(R.id.rl_myepei_techer_myagent);
		rl_myepei_techer_myagent.setVisibility(View.GONE);
		initData(role);
				//注册广播，判断角色
//		IntentFilter filter = new IntentFilter(
//						"com.ch.epw.system.myepei");
//		registerReceiver(messageReceiver, filter);
	}

	private void initData(String role) {
		//获得邀请数量
//		new GetInviteCountTask(context, tv_myepei_invitecount_bv)
//				.execute(AppContext.getApp().getToken());
	//改成一个接口，总的个人资料	第二个参数用来判断教师家长角色	0：家长	//获取个人资料，
		new GetPersonInfoDetailTask().execute(AppContext.getApp().getToken(),role);
		// 加载角色信息
//		new GetRolesTask().execute(AppContext.getApp().getToken());
		// 加载班级信息
//		new GetChildClassAndTeacherTask().execute(AppContext.getApp()
//				.getToken());

		// 加载版本信息
		tv_myepei_currentversion.setText("当前版本 "
				+ AppContext.getApp().getPackageInfo().versionName);
		closeProgressDialog();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_myepei_common_myinfo_rolenum:
			if (null != person_info.getUserRoleList()) {
				Intent intent = new Intent(context,
						MyepeiSwitchRoleActivity.class);
				intent.putExtra("userRoleList", person_info.getUserRoleList());
				startActivityForResult(intent, 0);
//				startActivity(intent);
				intoAnim();
			}
			break;
		case R.id.rl_myepei_common_myinfo_class:
			if (null != person_info.getStudentTeacherOrgClassList()) {
				Intent intentClass = new Intent(context,
						MyepeiMyClassActivity.class);
				intentClass.putExtra("studentTeacherOrgClassList",
						person_info.getStudentTeacherOrgClassList());
				startActivity(intentClass);
				intoAnim();
			}

			break;
		case R.id.rl_myepei_myinfo:
			if (null != personInfoDetail) {
				Intent intentPersonInfo = new Intent(context,
						MyepeiPersonInfoActivity.class);
				intentPersonInfo.putExtra("personInfoDetail", personInfoDetail);
				intentPersonInfo.putExtra("fromTag", 0);
				startActivity(intentPersonInfo);
				// startActivityForResult(intentPersonInfo,
				// Constant.REQUEST_COLDE);
				intoAnim();
			}

			break;
		case R.id.rl_myepei_techer_babyinfo:
			Intent intentBabyInfo = new Intent(context,
					BabyInfoListActivity.class);
			startActivity(intentBabyInfo);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_prizedevent:
			Intent intentPrized = new Intent(context,
					PrizedEventActivity.class);
			startActivity(intentPrized);
			intoAnim();
			break;
			
		case R.id.rl_myepei_checkupdate:
			CheckUpdate(myepeihandler);
			break;
		case R.id.rl_myepei_about:
			Intent intentAbout = new Intent(context,
					AboutActivity.class);
			intentAbout.putExtra("html", "/mobile/abuotEp.html");
			intentAbout.putExtra("titleName", "关于益培");
			startActivity(intentAbout);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_message:
			Intent in=new Intent(MyepeiParentActivity.this,MyMessageActivity.class);
			startActivity(in);
			intoAnim();
			break;
		}
	}
	BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			role=intent.getStringExtra("userRole");
			
		}
	};
	@Override
	protected void onResume() {
		super.onResume();
		initData(role);
	}

	/**
	 * 获取个人资料 创建人：李林中 创建日期：2015-1-28 下午5:46:11 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetPersonInfoDetailTask extends
			AsyncTask<String, Void, PersonInfoDetail> {

		AppException e;

		protected PersonInfoDetail doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				personInfoDetail = ((AppContext) getApplication())
						.getPersonList(params[0],params[1]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				AppException.network(e);
			}
			return personInfoDetail;
		}

		@Override
		protected void onPostExecute(PersonInfoDetail result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null == result) {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			} else {
				if (result.getStatus().equals("0")) {
					person_info=result;
					tv_myepei_common_title_phone.setText(result.getPersonInfo()
							.getPhone());// 用户名
					tv_myepei_common_title_name.setText(result.getPersonInfo()
							.getName());// 姓名
					showPicture(result.getPersonInfo().getPhotoPath(),
							iv_myepei_common_title_pic, options);

					rl_myepei_myinfo
							.setOnClickListener(MyepeiParentActivity.this);
					
					//角色信息
					tv_myepei_common_myinfo_rolenum.setText(result
							.getUserRoleList().size() + "");
					rl_myepei_common_myinfo_rolenum
							.setOnClickListener(MyepeiParentActivity.this);
					//班级数量
					int classCount=result.getStudentTeacherOrgClassList().size();
					tv_myepei_common_myinfo_classnum.setText(classCount + "");
					if (classCount > 0) {
						rl_myepei_common_myinfo_class
								.setOnClickListener(MyepeiParentActivity.this);
					}
					//红色提示，邀请数量
					tv_myepei_invitecount_bv.setBadgeCount(Integer.parseInt(result.getInviteCount()));
					int messageNum=result.getMsgNum()+result.getFeedbackNum();
					//红色提示未读信息数量
					if (messageNum>0) {
						BadgeView tv_myepei_invitecount_message = new BadgeView(context);
						tv_myepei_invitecount_message.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
						tv_myepei_invitecount_message.setTargetView(tv_myemessage_invitecount);
						tv_myepei_invitecount_message.setLayoutParams(tv_myepei_invitecount
								.getLayoutParams());
						tv_myepei_invitecount_message.setBadgeCount(messageNum);
					}
				} else {

					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	/**
	 * 获得角色信息 创建人：李林中 创建日期：2015-3-4 上午11:45:36 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
//	private class GetRolesTask extends AsyncTask<String, Void, UserRoleList> {
//		AppException e;
//
//		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
//		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
//		@Override
//		protected UserRoleList doInBackground(String... params) {
//
//			try {
//				userRoleList = AppContext.getApp().getUserRoleList(params[0]);
//
//			} catch (AppException e) {
//				this.e = e;
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				userRoleList = null;
//			}
//			return userRoleList;
//		}
//
//		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
//		@Override
//		protected void onPostExecute(UserRoleList result) {
//
//			super.onPostExecute(result);
//
//			if (result != null) {
//				if (result.getStatus().equals("0")) {
//					tv_myepei_common_myinfo_rolenum.setText(userRoleList
//							.getUserRoleList().size() + "");
//					rl_myepei_common_myinfo_rolenum
//							.setOnClickListener(MyepeiParentActivity.this);
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//
//			} else {
//				if (null != e) {
//					e.makeToast(context);
//				}
//				return;
//			}
//		}
//
//	}

	/**
	 * 获得学生和班级 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
//	private class GetChildClassAndTeacherTask extends
//			AsyncTask<String, Void, StudentTeacherOrgClassList> {
//		AppException e;
//
//		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
//		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
//		@Override
//		protected StudentTeacherOrgClassList doInBackground(String... params) {
//
//			try {
//				studentTeacherOrgClassList = AppContext.getApp()
//						.getChildClassAndTeacher(params[0]);
//
//			} catch (AppException e) {
//				this.e = e;
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return studentTeacherOrgClassList;
//		}
//
//		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
//		@Override
//		protected void onPostExecute(StudentTeacherOrgClassList result) {
//
//			super.onPostExecute(result);
//
//			if (result != null) {
//
//				if (result.getStatus().equals("0")) {
//					// 班级数量
//					int classCount = 0;
//					for (StudentTeacherOrgClass stoc : result
//							.getStudentTeacherOrgClasseList()) {
//						classCount += stoc.getTecheOrgClasseList().size();
//					}
//
//					tv_myepei_common_myinfo_classnum.setText(classCount + "");
//
//					if (classCount > 0) {
//						rl_myepei_common_myinfo_class
//								.setOnClickListener(MyepeiParentActivity.this);
//					}
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//			} else {
//				if (null != e) {
//					e.makeToast(context);
//				}
//				return;
//			}
//
//		}
//
//	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
}
