package com.zhizun.pos.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhizun.pos.R;
import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.ch.epw.jz.activity.MyInvitationRquestSelectStudentActivity;
import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.MyDialog;
import com.ch.epw.view.TitleBarView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserChildInfoList;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

/**
 * 我的邀请 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationRquestActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	ImageView iv_list_my_invitation_requestschool_item_logo,
			iv_my_invitation_requester_item_logo,
			iv_my_invitation_requeststudentinfo_item_logo,
			iv_my_invitation_requeststudentinfo_item_sex;
	TextView tv_list_my_invitation_requestschool_item_orgname,
			tv_list_my_invitation_requester_item_teachername,
			tv_my_invitation_requeststudentinfo_item_name;
	TextView tv_list_my_invitation_requestschool_item_orgaddress,
			tv_list_my_invitation_requester_item_phone,
			tv_my_invitation_requeststudentinfo_item_classname;
	TextView tv_list_my_invitation_requester_item_content,
			tv_my_invitation_requeststudentinfo_item_age;
	LinearLayout ll_my_invitation_bottom_bar,
			ll_my_invitation_requeststudentinfo_item;
	TextView tv_my_invitation_studentinfo_title;
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	RecevieMyInvitation recevieMyInvitation;
	UserChildInfoList userChildInfoList;
	Result result;
	Context context;
	Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码
	Integer RESULT_REFUSE_COLDE = 2;// 结果返回码
	MyDialog dialog;
	StringBuffer sBuffer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_invitation_request);
		context = this;
		initView();
		Intent intent = getIntent();
		recevieMyInvitation = (RecevieMyInvitation) intent
				.getSerializableExtra("recevieMyInvitation");
		imageLoader = ImageLoader.getInstance();
		options = Options.getListOptions();
		showPicture(recevieMyInvitation.getLogoPath(),
				iv_list_my_invitation_requestschool_item_logo, options);
		tv_list_my_invitation_requestschool_item_orgname
				.setText(recevieMyInvitation.getInviteOrgName());
		tv_list_my_invitation_requestschool_item_orgaddress
				.setText(recevieMyInvitation.getInviteOrgAddress());

		showPicture(recevieMyInvitation.getInviteUserPhoto(),
				iv_my_invitation_requester_item_logo, options);
		tv_list_my_invitation_requester_item_teachername
				.setText(recevieMyInvitation.getInviteUserName());
		tv_list_my_invitation_requester_item_phone.setText(recevieMyInvitation
				.getInviteUserPhone());
		tv_list_my_invitation_requester_item_content
				.setText(recevieMyInvitation.getInviteComment());

		// 学员信息
		tv_my_invitation_studentinfo_title = (TextView) findViewById(R.id.tv_my_invitation_studentinfo_title);
		ll_my_invitation_requeststudentinfo_item = (LinearLayout) findViewById(R.id.ll_my_invitation_requeststudentinfo_item);
		if (recevieMyInvitation.getType().equals("1")) {
			tv_my_invitation_studentinfo_title.setVisibility(View.GONE);
			ll_my_invitation_requeststudentinfo_item.setVisibility(View.GONE);
		} else {
			if (null != recevieMyInvitation.getStuName()
					&& !recevieMyInvitation.getStuName().equals("")) {
				tv_my_invitation_requeststudentinfo_item_name
						.setText(recevieMyInvitation.getStuName());
			} else {
				tv_my_invitation_requeststudentinfo_item_name.setText("");
			}
			showPicture(recevieMyInvitation.getStuPhotoPath(),
					iv_my_invitation_requeststudentinfo_item_logo, options);

			if (null != recevieMyInvitation.getStuAge()
					&& !recevieMyInvitation.getStuAge().equals("")) {
				tv_my_invitation_requeststudentinfo_item_age
						.setText(recevieMyInvitation.getStuAge());
			} else {
				tv_my_invitation_requeststudentinfo_item_age.setText("");
			}

			if (null != recevieMyInvitation.getStuSex()
					&& !recevieMyInvitation.getStuSex().equals("")) {
				if (recevieMyInvitation.getStuSex().equals("0")) {
					iv_my_invitation_requeststudentinfo_item_sex
							.setImageResource(R.drawable.sex_female);
				}
				if (recevieMyInvitation.getStuSex().equals("1")) {
					iv_my_invitation_requeststudentinfo_item_sex
							.setImageResource(R.drawable.sex_man);
				}
			}

			if (null != recevieMyInvitation.getLisStudentClasses()
					&& recevieMyInvitation.getLisStudentClasses().size() > 0) {
				sBuffer = new StringBuffer();
				for (int i = 0; i < recevieMyInvitation.getLisStudentClasses()
						.size(); i++) {
					sBuffer.append(recevieMyInvitation.getLisStudentClasses()
							.get(i).getName());
					if (i < recevieMyInvitation.getLisStudentClasses().size() - 1) {
						sBuffer.append(",");
					}
				}
				tv_my_invitation_requeststudentinfo_item_classname
						.setText(sBuffer.toString());
			}

		}

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myinvitation_requestparent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_requestdetail);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		iv_list_my_invitation_requestschool_item_logo = (ImageView) findViewById(R.id.iv_list_my_invitation_requestschool_item_logo);
		tv_list_my_invitation_requestschool_item_orgname = (TextView) findViewById(R.id.tv_list_my_invitation_requestschool_item_orgname);
		tv_list_my_invitation_requestschool_item_orgaddress = (TextView) findViewById(R.id.tv_list_my_invitation_requestschool_item_orgaddress);
		iv_my_invitation_requester_item_logo = (ImageView) findViewById(R.id.iv_my_invitation_requester_item_logo);
		tv_list_my_invitation_requester_item_teachername = (TextView) findViewById(R.id.tv_list_my_invitation_requester_item_teachername);
		tv_list_my_invitation_requester_item_phone = (TextView) findViewById(R.id.tv_list_my_invitation_requester_item_phone);
		tv_list_my_invitation_requester_item_content = (TextView) findViewById(R.id.tv_list_my_invitation_requester_item_content);

		iv_my_invitation_requeststudentinfo_item_logo = (ImageView) findViewById(R.id.iv_my_invitation_requeststudentinfo_item_logo);
		tv_my_invitation_requeststudentinfo_item_name = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_name);
		tv_my_invitation_requeststudentinfo_item_classname = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_classname);
		tv_my_invitation_requeststudentinfo_item_age = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_age);
		iv_my_invitation_requeststudentinfo_item_sex = (ImageView) findViewById(R.id.iv_my_invitation_requeststudentinfo_item_sex);
		ll_my_invitation_bottom_bar = (LinearLayout) findViewById(R.id.ll_my_invitation_bottom_bar);
		ll_my_invitation_bottom_bar.setOnClickListener(this);
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
		case R.id.ll_my_invitation_bottom_bar:
			showProgressDialog(context, "",
					getResources().getString(R.string.submit_ing));
			if (recevieMyInvitation.getType().equals("0")) {// 家长
				new GetUserChildsTask().execute(AppContext.getApp().getToken());
			} else {
				new AcceptInviteTask().execute(AppContext.getApp().getToken(),
						recevieMyInvitation.getOrgInviteId(), "", "");
			}

			break;
		}

	}

	/**
	 * 接受邀请 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class AcceptInviteTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().recevieInvite(params[0],
						params[1], params[2], params[3]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "您已成功接受邀请，为了账号安全，请在设置中修改密码",
							Toast.LENGTH_LONG);
					// 生成角色数据
					UserRole userRole = new UserRole();
					// 邀请家长
					if (Constant.INVITE_TYPE_TO_PARENT
							.equals(recevieMyInvitation.getType())) {
						userRole.setRoleId(Constant.ORG_ROLE_TYPE_PARENT);
					} else if (Constant.INVITE_TYPE_TO_TEACHER
							.equals(recevieMyInvitation.getType())) {
						userRole.setRoleId(Constant.ORG_ROLE_TYPE_TEACHER);
					}
					userRole.setOrgId(recevieMyInvitation.getOrgId());
					userRole.setOrgName(recevieMyInvitation.getInviteOrgName());

					new SwitchRolesTask(userRole, context, new TaskCallBack() {
						@Override
						public void onTaskFinshed() {
							// 跳转到相应的页面去
							//Intent intent;
							if (recevieMyInvitation.getType().equals(
									Constant.INVITE_TYPE_TO_PARENT)) {// 家长
//								intent = new Intent(context, MainActivity.class);
								setActivityResultTo(Constant.ORG_ROLE_TYPE_PARENT);
							} else {
//								intent = new Intent(context,
//										NavigationTeacherActivity.class);
								setActivityResultTo(Constant.ORG_ROLE_TYPE_TEACHER);
							}
							
						}
					}).execute(AppContext.getApp().getToken(),
							userRole.getRoleId(), userRole.getOrgId());
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

	private void setActivityResultTo(String param) {

		Intent intent = new Intent();
		intent.putExtra("currentRoleTag", param);
		intent.setAction("com.ch.epw.REFRESH_INDEXACTIVITY");
		sendBroadcast(intent);
		AppManager.getAppManager().finishActivity(MyInvitationActivity.class);
		MyInvitationRquestActivity.this.finish();
		backAnim();
	}

	/**
	 * 获取宝宝信息 家长端 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetUserChildsTask extends AsyncTask<String, Void, UserChildInfoList> {
		AppException e;

		@Override
		protected UserChildInfoList doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				userChildInfoList = AppContext.getApp().getUserChildInfoList(
						params[0]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				userChildInfoList = null;
			}
			return userChildInfoList;
		}

		@Override
		protected void onPostExecute(UserChildInfoList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}

			if (null != result) {
				if (result.getStatus().equals("0")) {
					if (result.getUserChildInfoList().size() <= 0) {
						dialog = new MyDialog(context, R.style.MyDialog,
								"未发现名为" + recevieMyInvitation.getStuName()
										+ "的宝宝信息，是否创建？",
								new MyDialog.LeaveMyDialogListener() {

									@Override
									public void onClick(View view) {
										switch (view.getId()) {
										case R.id.btn_dialog_selectstudent_yes:
											new AcceptInviteTask().execute(
													AppContext.getApp()
															.getToken(),
													recevieMyInvitation
															.getOrgInviteId(),
													"", recevieMyInvitation
															.getStuId());
											dialog.dismiss();
											break;
										case R.id.btn_dialog_selectstudent_no:
											dialog.dismiss();
											break;
										}

									}
								});
						dialog.show();
					} else {
						Intent intent = new Intent(context,
								MyInvitationRquestSelectStudentActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("userChildInfoList",
								userChildInfoList);
						bundle.putString("orgclass",
								recevieMyInvitation.getInviteOrgName() + " "
										+ sBuffer);
						bundle.putSerializable("recevieMyInvitation",
								recevieMyInvitation);
						intent.putExtras(bundle);
						startActivity(intent);
						MyInvitationRquestActivity.this.finish();
						intoAnim();
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
