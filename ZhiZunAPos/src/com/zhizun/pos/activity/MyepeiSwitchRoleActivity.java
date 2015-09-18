package com.zhizun.pos.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewMyepeiSwitchRoleAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.UserRole;
import com.zhizun.pos.bean.UserRoleList;
import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.view.TitleBarView;

/**
 * 我的邀请家长端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiSwitchRoleActivity extends BaseActivity {

	protected static final String TAG = MyepeiSwitchRoleActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;
	UserRole userRole;
	UserRoleList userRoleList;
	ListViewMyepeiSwitchRoleAdapter listViewMyepeiSwitchRoleAdapter;
	Result result;
	Integer dataCountRoles;
	List<UserRole> list;
	Integer REQUEST_COLDE = 1;// 结果返回码
	Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码 接受邀请
	Integer RESULT_REFUSE_COLDE = 2;// 结果返回码 拒绝邀请
	ListView ll_my_myepei_switchrole_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_myepei_switchrole);
		context = this;
		list = (List<UserRole>) getIntent().getSerializableExtra(
				"userRoleList");
		initView();
		initData();

	}

	private void initData() {

		listViewMyepeiSwitchRoleAdapter = new ListViewMyepeiSwitchRoleAdapter(
				context,list);
		// 得到实际的ListView
		ll_my_myepei_switchrole_list
				.setAdapter(listViewMyepeiSwitchRoleAdapter);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_my_myepei_switchrole);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.text_switchrole);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		ll_my_myepei_switchrole_list = (ListView) findViewById(R.id.ll_my_myepei_switchrole_list);
		ll_my_myepei_switchrole_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						userRole = list.get(position);
						UserInfo userInfo = AppContext.getApp()
								.getUserLoginSharedPre().getUserInfo();
						if (userInfo.getCurrentOrgan() == null
								|| !userRole.getRoleId().equals(
										userInfo.getCurrentRole())
								|| !userRole.getOrgId().equals(
										userInfo.getCurrentOrgan().getOrgId())) {
							new SwitchRolesTask(userRole, context,
									new TaskCallBack() {
										@Override
										public void onTaskFinshed() {
											AppContext.getApp().saveRole(userRole.getRoleId());
											if (userRole
													.getRoleId()
													.equals(Constant.ORG_ROLE_TYPE_PARENT)) {
												Intent intent = new Intent(
														context,
														IndexActivity.class);
												intent.putExtra(
														"currentRoleTag",
														Constant.ORG_ROLE_TYPE_PARENT);
												startActivity(intent);
												MyepeiSwitchRoleActivity.this
														.finish();
												backAnim();

											} else {
												// 机构 或者老师
												Intent intent = new Intent(
														context,
														IndexActivity.class);
												intent.putExtra(
														"currentRoleTag",
														Constant.ORG_ROLE_TYPE_TEACHER);
												startActivity(intent);
												MyepeiSwitchRoleActivity.this
														.finish();
												backAnim();
											}
										}
									}).execute(AppContext.getApp().getToken(),
									userRole.getRoleId(), userRole.getOrgId());
						}
					}
				});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
