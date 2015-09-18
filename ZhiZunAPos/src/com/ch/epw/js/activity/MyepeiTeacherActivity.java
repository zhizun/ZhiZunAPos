package com.ch.epw.js.activity;

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

import com.ch.epw.jz.activity.BabyInfoListActivity;
import com.ch.epw.jz.activity.MyepeiMyClassActivity;
import com.ch.epw.task.SystemMessageTask;
import com.ch.epw.task.TaskCallBack;
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
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.widget.circularimage.CircularImage;

/**
 * 我的益培 教师端 创建人：李林中 创建日期：2014-12-15 上午10:08:08 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiTeacherActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	InviteCount inviteCount;
	CircularImage iv_myepei_common_title_pic;// 头像
	TextView tv_myepei_common_title_name;
	TextView tv_myepei_common_title_phone;

	RelativeLayout rl_myepei_techer_myinvitation;
	LinearLayout rl_myepei_common_myinfo_rolenum;// 角色
	RelativeLayout rl_myepei_techer_babyinfo;// 宝宝信息
	RelativeLayout rl_myepei_checkupdate;// 检查更新
	TextView tv_myepei_currentversion;// 版本号
	TextView tv_myepei_common_myinfo_rolenum;
	LinearLayout rl_myepei_common_myinfo_class;// 班级
	TextView tv_myepei_common_myinfo_classnum;
	TextView tv_myepei_invitecount;// 邀请数量
	BadgeView tv_myepei_invitecount_bv; // 邀请数量，提醒BadgeView对象
	RelativeLayout rl_myepei_myinfo;// 个人信息
	RelativeLayout rl_myepei_techer_prizedevent;//有奖活动
	RelativeLayout rl_myepei_techer_message;//我的消息
	PersonInfoDetail personInfoDetail;
//	UserRoleList userRoleList;
//	StudentClassList studentClassList;
	RelativeLayout rl_myepei_about;
	RelativeLayout rl_myepei_techer_myagent;
	private PersonInfoDetail person_info;
	private String role;
	private TextView tv_myemessage_invitecount;
	BadgeView tv_myepei_sysmsg_bv_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei);
		context = this;
		options = Options.getListOptions();
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		role=getIntent().getStringExtra("role");
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
				intent.putExtra("roles", "1");
				startActivity(intent);
				intoAnim();

			}
		});
		iv_myepei_common_title_pic = (CircularImage) this
				.findViewById(R.id.iv_myepei_common_title_pic);
		tv_myepei_common_title_phone = (TextView) findViewById(R.id.tv_myepei_common_title_phone);
		tv_myepei_common_title_name = (TextView) findViewById(R.id.tv_myepei_common_title_name);
		rl_myepei_techer_myinvitation = (RelativeLayout) this
				.findViewById(R.id.rl_myepei_techer_myinvitation);

		// 我的邀请
		rl_myepei_techer_myinvitation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						MyInvitationTeacherActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});
		rl_myepei_common_myinfo_rolenum = (LinearLayout) findViewById(R.id.rl_myepei_common_myinfo_rolenum);
		// rl_myepei_common_myinfo_rolenum.setOnClickListener(this);
		//我的邀请
		tv_myepei_invitecount = (TextView) findViewById(R.id.tv_myepei_invitecount);
		// 以tv_myepei_invitecount_bv带样式的显示代替tv_myepei_invitecount
		tv_myepei_invitecount_bv = new BadgeView(context);
		tv_myepei_invitecount_bv.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
		tv_myepei_invitecount_bv.setTargetView(tv_myepei_invitecount);
		tv_myepei_invitecount_bv.setLayoutParams(tv_myepei_invitecount
				.getLayoutParams());
		//我的消息红点提示
		tv_myemessage_invitecount=(TextView)findViewById(R.id.tv_myemessage_invitecount);
		tv_myepei_sysmsg_bv_num = new BadgeView(context);
		tv_myepei_sysmsg_bv_num.setBadgeGravity(Gravity.LEFT | Gravity.BOTTOM);
		tv_myepei_sysmsg_bv_num.setTargetView(tv_myemessage_invitecount);
		tv_myepei_sysmsg_bv_num.setLayoutParams(tv_myepei_invitecount
				.getLayoutParams());
		rl_myepei_common_myinfo_class = (LinearLayout) findViewById(R.id.rl_myepei_common_myinfo_class);
		// rl_myepei_common_myinfo_class.setOnClickListener(this);
		rl_myepei_myinfo = (RelativeLayout) findViewById(R.id.rl_myepei_myinfo);
		// rl_myepei_myinfo.setOnClickListener(this);
		tv_myepei_common_myinfo_rolenum = (TextView) findViewById(R.id.tv_myepei_common_myinfo_rolenum);
		tv_myepei_common_myinfo_classnum = (TextView) findViewById(R.id.tv_myepei_common_myinfo_classnum);
		rl_myepei_techer_babyinfo = (RelativeLayout) findViewById(R.id.rl_myepei_techer_babyinfo);
		rl_myepei_techer_babyinfo.setOnClickListener(this);
		rl_myepei_checkupdate = (RelativeLayout) findViewById(R.id.rl_myepei_checkupdate);
		rl_myepei_checkupdate.setOnClickListener(this);
		tv_myepei_currentversion = (TextView) findViewById(R.id.tv_myepei_currentversion);
		rl_myepei_about = (RelativeLayout) findViewById(R.id.rl_myepei_about);
		rl_myepei_about.setOnClickListener(this);
		rl_myepei_techer_prizedevent=(RelativeLayout) findViewById(R.id.rl_myepei_techer_prizedevent);
		rl_myepei_techer_prizedevent.setOnClickListener(this);
		rl_myepei_techer_myagent = (RelativeLayout) findViewById(R.id.rl_myepei_techer_myagent);
		rl_myepei_techer_myagent.setOnClickListener(this);
		rl_myepei_techer_message=(RelativeLayout) findViewById(R.id.rl_myepei_techer_message);
		rl_myepei_techer_message.setOnClickListener(this);
		initData(role);
	}

	private void initData(String role) {
		//获取当前角色，根据角色选择班级信息
		if (role==null||role.equals("")) {
			UserLogin userInfo=AppContext.getApp().getUserLoginSharedPre();
			role=userInfo.getUserInfo().getCurrentRole();
		}
		new GetPersonInfoDetailTask().execute(AppContext.getApp().getToken(),role);
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_myepei_common_myinfo_rolenum:
			if (null != person_info.getUserRoleList()) {
				Intent intent = new Intent(context,
						MyepeiSwitchRoleActivity.class);
				intent.putExtra("userRoleList", person_info.getUserRoleList());
				startActivity(intent);
				intoAnim();
			}
			break;
		case R.id.rl_myepei_common_myinfo_class:
			if (role.equals("0")) {//家长
				if (null != person_info.getStudentTeacherOrgClassList()) {
					Intent intentClass = new Intent(context,
							MyepeiMyClassActivity.class);
					intentClass.putExtra("studentTeacherOrgClassList", person_info.getStudentTeacherOrgClassList());
					startActivity(intentClass);
					intoAnim();
				}
			}else {//教师
				if (null != person_info.getStudentClassList()) {
					Intent intentClass = new Intent(context,
							MyepeiMyClassTeacherActivity.class);
					intentClass.putExtra("studentClassList", person_info.getStudentClassList());
					startActivity(intentClass);
					intoAnim();
				}
			}

			break;
		case R.id.rl_myepei_myinfo:
			if (null != personInfoDetail) {
				Intent intentPersonInfo = new Intent(context,
						MyepeiPersonInfoActivity.class);
				intentPersonInfo.putExtra("personInfoDetail", personInfoDetail);
				intentPersonInfo.putExtra("fromTag", 1);
				startActivity(intentPersonInfo);
				// startActivityForResult(intentPersonInfo,
				// Constant.REQUEST_COLDE);
				intoAnim();
			}

			break;
		case R.id.rl_myepei_checkupdate:
			CheckUpdate(myepeihandler);
			break;
		case R.id.rl_myepei_about:
			Intent intentAbout = new Intent(context, AboutActivity.class);
			intentAbout.putExtra("html", "/mobile/abuotEp.html");
			intentAbout.putExtra("titleName","关于益培");
			startActivity(intentAbout);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_myagent:
			Intent intentMyagent = new Intent(context,
					MyepeiTeacherAgentActivity.class);
			startActivity(intentMyagent);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_prizedevent:
			Intent intentPrizedEvent = new Intent(context,
					PrizedEventActivity.class);
			startActivity(intentPrizedEvent);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_message:
			Intent intentMessage= new Intent(context,
					MyMessageActivity.class);
			startActivity(intentMessage);
			intoAnim();
			break;
		case R.id.rl_myepei_techer_babyinfo:
			Intent intentBabyInfo = new Intent(context,
					BabyInfoListActivity.class);
			startActivity(intentBabyInfo);
			intoAnim();
			break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		role=AppContext.getApp().getRole();
		initData(role);
		//条用系统消息数量接口
		new SystemMessageTask(context, new TaskCallBack() {}).execute();
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
					person_info=result;
					tv_myepei_common_title_phone.setText(result.getPersonInfo()
							.getPhone());// 用户名
					tv_myepei_common_title_name.setText(result.getPersonInfo()
							.getName());// 姓名
					showPicture(result.getPersonInfo().getPhotoPath(),
							iv_myepei_common_title_pic, options);

					rl_myepei_myinfo
							.setOnClickListener(MyepeiTeacherActivity.this);
					 result.getUserRoleList();
					tv_myepei_common_myinfo_rolenum.setText(result.getUserRoleList().size()+ "");
					rl_myepei_common_myinfo_rolenum.setOnClickListener(MyepeiTeacherActivity.this);
					//获取当前角色，根据角色选择班级信息
					if (role==null||role.equals("")) {
						UserLogin userInfo=AppContext.getApp().getUserLoginSharedPre();
						role=userInfo.getUserInfo().getCurrentRole();
					}
					if (role.equals("0")) {
							rl_myepei_techer_babyinfo.setVisibility(View.VISIBLE);
							rl_myepei_techer_myagent.setVisibility(View.GONE);
							tv_myepei_common_myinfo_classnum.setText(result.getStudentTeacherOrgClassList().size()+"");
							if (result.getStudentTeacherOrgClassList().size() > 0) {
								rl_myepei_common_myinfo_class
									.setOnClickListener(MyepeiTeacherActivity.this);
							}
					}else {
							rl_myepei_techer_myagent.setVisibility(View.VISIBLE);
							rl_myepei_techer_babyinfo.setVisibility(View.GONE);
							tv_myepei_common_myinfo_classnum.setText(result
								.getStudentClassList().size() + "");
						if (result.getStudentClassList().size() > 0) {
								rl_myepei_common_myinfo_class
									.setOnClickListener(MyepeiTeacherActivity.this);
						}
					}
					//红色提示，邀请数量
					tv_myepei_invitecount_bv.setBadgeCount(Integer.parseInt(result.getInviteCount()));
					int messageNum=result.getMsgNum()+result.getFeedbackNum();
					//红色提示未读信息数量
					tv_myepei_sysmsg_bv_num.setBadgeCount(messageNum);
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

}
