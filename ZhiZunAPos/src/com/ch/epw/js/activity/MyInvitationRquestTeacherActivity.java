package com.ch.epw.js.activity;

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

import com.ch.epw.task.AcceptInviteTask;
import com.ch.epw.task.GetUserChildsTask;
import com.ch.epw.task.RefuseInviteTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.view.TitleBarView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;

/**
 * 我的邀请 邀请老师 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationRquestTeacherActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	ImageView iv_list_my_invitation_requestschool_item_logo,
			iv_my_invitation_requester_item_logo;
	TextView tv_list_my_invitation_requestschool_item_orgname,
			tv_list_my_invitation_requester_item_teachername;
	TextView tv_list_my_invitation_requestschool_item_orgaddress,
			tv_list_my_invitation_requester_item_phone;
	TextView tv_list_my_invitation_requester_item_content;
	RelativeLayout rl_my_invitation_bottom_bar_yes,
			rl_my_invitation_bottom_bar_no;
	LinearLayout ll_my_invitation_bottom_bar;
	protected ImageLoader imageLoader;
	DisplayImageOptions options;
	RecevieMyInvitation recevieMyInvitation;
	Result result;
	Context context;
	Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码
	Integer RESULT_REFUSE_COLDE = 2;// 结果返回码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invitation_requestteacher);
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
				iv_my_invitation_requester_item_logo, Options.getListOptions(R.drawable.default_photo));
		tv_list_my_invitation_requester_item_teachername
				.setText(recevieMyInvitation.getInviteUserName() + " 老师");
		tv_list_my_invitation_requester_item_phone.setText(recevieMyInvitation
				.getInviteUserPhone());
		tv_list_my_invitation_requester_item_content
				.setText(recevieMyInvitation.getInviteComment());
		if (recevieMyInvitation.getInviteStatus().equals("1")
				|| recevieMyInvitation.getInviteStatus().equals("2")) {
			ll_my_invitation_bottom_bar.setVisibility(View.GONE);
		}
		rl_my_invitation_bottom_bar_yes.setOnClickListener(this);
		rl_my_invitation_bottom_bar_no.setOnClickListener(this);

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myinvitation_requesteacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_requestteacher);

		
		
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
			
			//如果是邀请老师，直接调AcceptInviteTask
			if(Constant.INVITE_TYPE_TO_TEACHER.equals(recevieMyInvitation.getType())){
				new AcceptInviteTask(MyInvitationRquestTeacherActivity.this).execute(AppContext.getApp().getToken(),
						recevieMyInvitation.getOrgInviteId(), "", "");
			}
			//邀请家长，调用GetUserChildsTask
			else if(Constant.INVITE_TYPE_TO_PARENT.equals(recevieMyInvitation.getType())){
				new GetUserChildsTask(MyInvitationRquestTeacherActivity.this, recevieMyInvitation)
				.execute(AppContext.getApp().getToken());
			}

			break;
		case R.id.rl_my_invitation_bottom_bar_no:
			progress = ProgressDialog.show(context, "", "努力提交中...", true);
			new RefuseInviteTask(MyInvitationRquestTeacherActivity.this).execute(AppContext.getApp().getToken(),
					recevieMyInvitation.getOrgInviteId());
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
//					MyInvitationRquestTeacherActivity.this.setResult(
//							RESULT_ACCEPT_COLDE, intent);
//					MyInvitationRquestTeacherActivity.this.finish();
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
//					MyInvitationRquestTeacherActivity.this.setResult(
//							RESULT_REFUSE_COLDE, intent);
//					MyInvitationRquestTeacherActivity.this.finish();
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
