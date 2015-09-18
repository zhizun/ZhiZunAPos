package com.ch.epw.jz.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.js.activity.MyInvitationRquestTeacherActivity;
import com.ch.epw.task.AcceptInviteTask;
import com.ch.epw.task.GetUserChildsTask;
import com.ch.epw.task.RefuseInviteTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.MyDialog;
import com.ch.epw.view.TitleBarView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserChildInfoList;

/**
 * 我的邀请 邀请教师 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationRquestParentActivity extends BaseActivity implements
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
	RelativeLayout rl_my_invitation_bottom_bar_yes,
			rl_my_invitation_bottom_bar_no;
	LinearLayout ll_my_invitation_bottom_bar;
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
		setContentView(R.layout.my_invitation_requestparent);
		context = this;
		initView();
		Intent intent = getIntent();
		recevieMyInvitation = (RecevieMyInvitation) intent
				.getSerializableExtra("recevieMyInvitation");
		imageLoader = ImageLoader.getInstance();
		options = Options.getListOptions();
		//显示邀请机构图片
		showPicture(recevieMyInvitation.getLogoPath(),
				iv_list_my_invitation_requestschool_item_logo, Options.getListOptions(R.drawable.default_logo));
		tv_list_my_invitation_requestschool_item_orgname
				.setText(recevieMyInvitation.getInviteOrgName());
		tv_list_my_invitation_requestschool_item_orgaddress
				.setText(recevieMyInvitation.getInviteOrgAddress());

		//显示邀请人图片
		showPicture(recevieMyInvitation.getInviteUserPhoto(),
				iv_my_invitation_requester_item_logo, options);
		tv_list_my_invitation_requester_item_teachername
				.setText(recevieMyInvitation.getInviteUserName());
		tv_list_my_invitation_requester_item_phone.setText(recevieMyInvitation
				.getInviteUserPhone());
		if (!recevieMyInvitation.getInviteComment().equals("") 
				&& null!=recevieMyInvitation.getInviteComment()) {
			tv_list_my_invitation_requester_item_content
			.setText(recevieMyInvitation.getInviteComment());
		}else {
			tv_list_my_invitation_requester_item_content.setVisibility(View.GONE);
		}

		// 学员信息
		if (null != recevieMyInvitation.getStuName()
				&& !recevieMyInvitation.getStuName().equals("")) {
			tv_my_invitation_requeststudentinfo_item_name
					.setText(recevieMyInvitation.getStuName());
		}
		showPicture(recevieMyInvitation.getStuPhotoPath(),
				iv_my_invitation_requeststudentinfo_item_logo, options);

		if (null != recevieMyInvitation.getStuAge()
				&& !recevieMyInvitation.getStuAge().equals("")) {
			tv_my_invitation_requeststudentinfo_item_age
					.setText(recevieMyInvitation.getStuAge());
		}
		tv_my_invitation_requeststudentinfo_item_age
				.setText(recevieMyInvitation.getStuAge());
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
			tv_my_invitation_requeststudentinfo_item_classname.setText(sBuffer
					.toString());
		}

		if (recevieMyInvitation.getInviteStatus().equals("1")
				|| recevieMyInvitation.getInviteStatus().equals("2")) {
			ll_my_invitation_bottom_bar.setVisibility(View.GONE);
		}

		rl_my_invitation_bottom_bar_yes.setOnClickListener(this);
		rl_my_invitation_bottom_bar_no.setOnClickListener(this);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myinvitation_requestparent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_requestparent);

		
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
		rl_my_invitation_bottom_bar_yes = (RelativeLayout) findViewById(R.id.rl_my_invitation_bottom_bar_yes);
		rl_my_invitation_bottom_bar_no = (RelativeLayout) findViewById(R.id.rl_my_invitation_bottom_bar_no);

		iv_my_invitation_requeststudentinfo_item_logo = (ImageView) findViewById(R.id.iv_my_invitation_requeststudentinfo_item_logo);
		tv_my_invitation_requeststudentinfo_item_name = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_name);
		tv_my_invitation_requeststudentinfo_item_classname = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_classname);
		tv_my_invitation_requeststudentinfo_item_age = (TextView) findViewById(R.id.tv_my_invitation_requeststudentinfo_item_age);
		iv_my_invitation_requeststudentinfo_item_sex = (ImageView) findViewById(R.id.iv_my_invitation_requeststudentinfo_item_sex);
		ll_my_invitation_bottom_bar = (LinearLayout) findViewById(R.id.ll_my_invitation_bottom_bar);
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
		case R.id.rl_my_invitation_bottom_bar_yes:
			progress = ProgressDialog.show(context, "", "努力提交中...", true);
			new GetUserChildsTask(MyInvitationRquestParentActivity.this, recevieMyInvitation)
				.execute(AppContext.getApp().getToken());
			break;
		case R.id.rl_my_invitation_bottom_bar_no:
			progress = ProgressDialog.show(context, "", "努力提交中...", true);
			new RefuseInviteTask(MyInvitationRquestParentActivity.this)
				.execute(AppContext.getApp().getToken(),recevieMyInvitation.getOrgInviteId());
			break;
		}
	}

//	/**
//	 * 接受邀请 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
//	 * =================================================== 修改人 修改日期 原因(描述)
//	 * ===================================================
//	 */
//	class AcceptInviteTask extends AsyncTask<String, Void, Result> {
//
//		@Override
//		protected Result doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				result = AppContext.getApp().recevieInvite(params[0],
//						params[1], params[2], params[3]);
//			} catch (AppException e) {
//				e.printStackTrace();
//				result = null;
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(Result result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (progress != null) {
//				progress.dismiss();
//			}
//			if (null != result) {
//				if (result.getStatus().equals("0")) {
//					UIHelper.ToastMessage(context, "谢谢您，您已接成功受邀请");
//					Intent intent = getIntent();
//					intent.putExtra("inviteStatus", 1);
//					MyInvitationRquestParentActivity.this.setResult(
//							RESULT_ACCEPT_COLDE, intent);
//					MyInvitationRquestParentActivity.this.finish();
//					backAnim();
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//			} else {
//				//UIHelper.ToastMessage(context, R.string.socket_exception_error);
//				return;
//			}
//		}
//	}
//
//	/**
//	 * 获取宝宝信息 家长端 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
//	 * =================================================== 修改人 修改日期 原因(描述)
//	 * ===================================================
//	 */
//	class GetUserChildsTask extends AsyncTask<String, Void, UserChildInfoList> {
//
//		@Override
//		protected UserChildInfoList doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				userChildInfoList = AppContext.getApp().getUserChildInfoList(
//						params[0]);
//			} catch (AppException e) {
//				e.printStackTrace();
//				userChildInfoList = null;
//			}
//			return userChildInfoList;
//		}
//
//		@Override
//		protected void onPostExecute(UserChildInfoList result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (progress != null) {
//				progress.dismiss();
//			}
//
//			if (null != result) {
//				if (result.getStatus().equals("0")) {
//					if (result.getUserChildInfoList().size() <= 0) {
//						dialog = new MyDialog(context, R.style.MyDialog,
//								"未发现名为" + recevieMyInvitation.getStuName()
//										+ "的宝宝信息，是否创建？",
//								new MyDialog.LeaveMyDialogListener() {
//
//									@Override
//									public void onClick(View view) {
//										switch (view.getId()) {
//										case R.id.btn_dialog_selectstudent_yes:
//											new AcceptInviteTask().execute(
//													AppContext.getApp()
//															.getToken(),
//													recevieMyInvitation
//															.getOrgInviteId(),
//													"", recevieMyInvitation
//															.getStuId());
//											dialog.dismiss();
//											break;
//										case R.id.btn_dialog_selectstudent_no:
//											dialog.dismiss();
//											break;
//										}
//
//									}
//								});
//						dialog.show();
//					} else {
//						Intent intent = new Intent(context,
//								MyInvitationRquestSelectStudentActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("userChildInfoList",
//								userChildInfoList);
//						bundle.putString("orgclass",
//								recevieMyInvitation.getInviteOrgName() + " "
//										+ sBuffer);
//						bundle.putSerializable("recevieMyInvitation",
//								recevieMyInvitation);
//						intent.putExtras(bundle);
//						startActivity(intent);
//						MyInvitationRquestParentActivity.this.finish();
//						intoAnim();
//					}
//
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//			} else {
//				//UIHelper.ToastMessage(context, R.string.socket_exception_error);
//				return;
//			}
//		}
//	}
//
//	/**
//	 * 拒绝邀请 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
//	 * =================================================== 修改人 修改日期 原因(描述)
//	 * ===================================================
//	 */
//	class RefuseInviteTask extends AsyncTask<String, Void, Result> {
//
//		@Override
//		protected Result doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//				result = AppContext.getApp().refuseInvite(params[0], params[1]);
//			} catch (AppException e) {
//				e.printStackTrace();
//				result = null;
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(Result result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (progress != null) {
//				progress.dismiss();
//			}
//			if (null != result) {
//				if (result.getStatus().equals("0")) {
//					UIHelper.ToastMessage(context, "谢谢您，您已接拒绝邀请");
//					Intent intent = getIntent();
//					intent.putExtra("inviteStatus", 1);
//					MyInvitationRquestParentActivity.this.setResult(
//							RESULT_REFUSE_COLDE, intent);
//					MyInvitationRquestParentActivity.this.finish();
//					backAnim();
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//			} else {
//				//UIHelper.ToastMessage(context, R.string.socket_exception_error);
//				return;
//			}
//		}
//	}

}
