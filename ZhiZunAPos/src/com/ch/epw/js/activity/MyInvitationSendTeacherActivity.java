package com.ch.epw.js.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.view.TitleBarView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.RecevieMyInvitation;

/**
 * 我的邀请 邀请教师 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationSendTeacherActivity extends BaseActivity  {

	private TitleBarView titleBarView;
	ImageView iv_my_invitation_teacherinfo_item_logo;
	TextView tv_my_invitation_teacherinfo_invitestatus;
	TextView tv_list_my_invitation_teacherinfo_item_teachername,
	tv_my_invitation_teacherinfo_item_phone;

	
	RecevieMyInvitation recevieMyInvitation;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invitation_teacherinfo);
		context = this;
		initView();
		Intent intent = getIntent();
		recevieMyInvitation = (RecevieMyInvitation) intent
				.getSerializableExtra("recevieMyInvitation");
		imageLoader = ImageLoader.getInstance();
		options = Options.getListOptions();
		showPicture(recevieMyInvitation.getInvitedUserPhoto(),
				iv_my_invitation_teacherinfo_item_logo, options);
		if (Constant.INVITESTATUS_UNTREATED.equals(recevieMyInvitation.getInviteStatus())) {
			tv_my_invitation_teacherinfo_invitestatus.setText("未处理邀请");
		}else if (Constant.INVITESTATUS_ACCEPTED.equals(recevieMyInvitation.getInviteStatus())) {
			tv_my_invitation_teacherinfo_invitestatus.setText("已接受邀请");
		}else if (Constant.INVITESTATUS_REFUSE.equals(recevieMyInvitation.getInviteStatus())) {
			tv_my_invitation_teacherinfo_invitestatus.setText("已拒绝邀请");
		}
		if(recevieMyInvitation.getInvitedUserPhone()!=null){
		tv_my_invitation_teacherinfo_item_phone.setText(recevieMyInvitation
				.getInvitedUserPhone());
		}
		if(recevieMyInvitation.getInvitedUserName()!=null){
			tv_list_my_invitation_teacherinfo_item_teachername.setText(recevieMyInvitation
				.getInvitedUserName() + " 老师");
		}
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_my_invitation_teacherinfo);
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
		iv_my_invitation_teacherinfo_item_logo = (ImageView) findViewById(R.id.iv_my_invitation_teacherinfo_item_logo);
		tv_my_invitation_teacherinfo_invitestatus = (TextView) findViewById(R.id.tv_my_invitation_teacherinfo_invitestatus);
		tv_my_invitation_teacherinfo_item_phone = (TextView) findViewById(R.id.tv_my_invitation_teacherinfo_item_phone);
		tv_list_my_invitation_teacherinfo_item_teachername = (TextView) findViewById(R.id.tv_list_my_invitation_teacherinfo_item_teachername);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}


}
