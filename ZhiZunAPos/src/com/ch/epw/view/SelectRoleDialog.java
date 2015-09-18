package com.ch.epw.view;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.activity.MyepeiSwitchRoleActivity;
import com.zhizun.pos.adapter.ListViewRoleAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

public class SelectRoleDialog extends Dialog {
	private Context context;
	private int contentViewID;
	ListView ll_dialog_selecter_role_list;
	Activity activity;
	private List<UserRole> userRoleList;
	private ListViewRoleAdapter roleAdapter;
	Result result;
	UserLogin userLogin;
	UserRole userRole;
	Handler handler;
	int isJoinOrg;

	public SelectRoleDialog(Context context) {
		super(context);

	}

	public SelectRoleDialog(Context context, int resourceID, int themeID,
			List<UserRole> userRoleList, Activity activity, Handler handler) {
		super(context, themeID);
		this.context = context;
		this.contentViewID = resourceID;
		this.userRoleList = userRoleList;
		this.activity = activity;
		this.handler = handler;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(contentViewID);
		ll_dialog_selecter_role_list = (ListView) findViewById(R.id.ll_dialog_selecter_role_list);
		roleAdapter = new ListViewRoleAdapter(context, userRoleList);
		ll_dialog_selecter_role_list.setAdapter(roleAdapter);
		ll_dialog_selecter_role_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						userRole = userRoleList.get(position);
						if (TextUtils.isEmpty(userRole.getOrgId())) {
							isJoinOrg=Constant.IS_JOIN_ORG_NO;
						}else {
							isJoinOrg=Constant.IS_JOIN_ORG_YES;
						}
						new SwitchRolesTask(userRole, context,
								new TaskCallBack() {
									@Override
									public void onTaskFinshed() {
										if (userRole.getRoleId().equals(
												Constant.ORG_ROLE_TYPE_PARENT)) {
											// Intent intent = new
											// Intent(context,
											// MainActivity.class);
											// context.startActivity(intent);
											Message msg = handler
													.obtainMessage();
											msg.what = Constant.ORG_ROLE_PARENT;
											msg.arg1=isJoinOrg;
											handler.sendMessage(msg);
											SelectRoleDialog.this.dismiss();

										} else {
											// 机构 或者老师
											Message msg = handler
													.obtainMessage();
											msg.what = Constant.ORG_ROLE_TEACHERORORG;
											msg.arg1=isJoinOrg;
											handler.sendMessage(msg);
											SelectRoleDialog.this.dismiss();
										}

									}
								}).execute(AppContext.getApp().getToken(),
								userRole.getRoleId(), userRole.getOrgId());
					}
				});
	}

}
