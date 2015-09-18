package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.bean.send.StuChgStateSend;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.DateTimePickDialogUtil;
import com.ch.epw.view.DropOutDialog;
import com.ch.epw.view.GraduationQuitClassDialog;
import com.ch.epw.view.SelectClassDialog;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Org;
import com.zhizun.pos.bean.OrgList;
import com.zhizun.pos.bean.Remarks;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentClassList;
import com.zhizun.pos.bean.StudentInfo;

/**
 * 班级变更 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ChangeClassSelectStudentActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	ExpandableListView el_myinvitation_senddynamic_selectstudent;
	ArrayList<StudentClass> studentClassesList;
	List<StudentInfo> studentInfoList;
	StudentClassList studentClassList;
	ExpandableAdapter expandableAdapter;
	View ll_no_items_listed;
	OrgList orgList;
	RelativeLayout rl_changeclass_bottom_bar_changeclass;// 转班
	RelativeLayout rl_changeclass_bottom_bar_quitschool;// 休学
	RelativeLayout rl_changeclass_bottom_bar_graduate;// 毕业
	RelativeLayout rl_changeclass_bottom_bar_quitclass;// 退班
	Dialog dialog;
	Result result;
	String cngType;
	String intoClaId;
	String note;
	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changeclass_selectstudent);
		context = this;
		initView();
		options = Options.getListOptions();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		ArrayList<StudentClass> list = (ArrayList<StudentClass>) getIntent()
				.getSerializableExtra("studentClasseList");
		if (null == list) {
			getStudentList();
		} else {
			studentClassesList = list;
			expandableAdapter = new ExpandableAdapter(context);
			// 得到实际的ListView
			el_myinvitation_senddynamic_selectstudent
					.setAdapter(expandableAdapter);
		}

	}

	// 获得学生列表
	private void getStudentList() {
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		new GetStudentAndClassTask().execute(AppContext.getApp().getToken());
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_myinvitation_senddynamic_selectstudent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_myclass);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		el_myinvitation_senddynamic_selectstudent = (ExpandableListView) findViewById(R.id.el_myinvitation_senddynamic_selectstudent);
		el_myinvitation_senddynamic_selectstudent
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// 得到某个班级
						StudentClass studentClass = studentClassesList
								.get(groupPosition);
						// 得到班级中的学生实体
						StudentInfo studentInfo = studentClass
								.getStudentInfoList().get(childPosition);

						// if (!studentInfo.getIsFamilyJoin()) {
						// UIHelper.ToastMessage(
						// context,
						// studentInfo.getName()
						// + getResources()
						// .getString(
						// R.string.text_info_no_family_join));
						// studentClass.setCheckState(false);
						// return false;
						// }

						// 判断学生是否选中，如果选中 则反选
						if (studentInfo.getCheckState()) {// 选中了学生
							studentInfo.setCheckState(false);
							// 如果班级 也就是父list也选中了 那么反选，因为少选一项 就意味着不是全选
							if (studentClass.getCheckState()) {
								studentClass.setCheckState(false);
							}
						} else {// 没选中学生

							studentInfo.setCheckState(true);// 选中
							boolean isAllSelected = true;// 定义一个是否全选 设为true
							// 遍历这个班级里面的学生列表，如果有一个学生没有选中 那么就代表没全选 跳出循环
							for (int i = 0; i < studentClass
									.getStudentInfoList().size(); i++) {
								StudentInfo sti = studentClass
										.getStudentInfoList().get(i);
								if (!sti.getCheckState()) {
									isAllSelected = false;
									break;
								}
							}
							// 如果全选那么 班级也选中
							if (isAllSelected) {
								studentClass.setCheckState(true);
							}
						}

						expandableAdapter.notifyDataSetChanged();
						return false;
					}
				});
		// 没有数据显示
		ll_no_items_listed = (View) findViewById(R.id.ll_no_items_listed);
		rl_changeclass_bottom_bar_changeclass = (RelativeLayout) findViewById(R.id.rl_changeclass_bottom_bar_changeclass);// 转班
		rl_changeclass_bottom_bar_changeclass.setOnClickListener(this);
		rl_changeclass_bottom_bar_quitschool = (RelativeLayout) findViewById(R.id.rl_changeclass_bottom_bar_quitschool);// 休学
		rl_changeclass_bottom_bar_quitschool.setOnClickListener(this);
		rl_changeclass_bottom_bar_graduate = (RelativeLayout) findViewById(R.id.rl_changeclass_bottom_bar_graduate);// 毕业
		rl_changeclass_bottom_bar_graduate.setOnClickListener(this);
		rl_changeclass_bottom_bar_quitclass = (RelativeLayout) findViewById(R.id.rl_changeclass_bottom_bar_quitclass);// 退班
		rl_changeclass_bottom_bar_quitclass.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	class ExpandableAdapter extends BaseExpandableListAdapter {
		Context context;

		public ExpandableAdapter(Context context) {
			this.context = context;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return studentClassesList.get(groupPosition).getStudentInfoList()
					.get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return studentClassesList.get(groupPosition).getStudentInfoList()
					.size();
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = View.inflate(context,
					R.layout.changeclass_selectstudent_item, null);

			ImageView iv_senddynamic_child_selectstudent_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_child_selectstudent_item_logo);
			TextView tv_senddynamic_child_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_child_selectstudent_item_name);
			CheckBox cb_senddynamic_child_selectstudent_item_state = (CheckBox) convertView
					.findViewById(R.id.cb_senddynamic_child_selectstudent_item_state);

			final StudentInfo studentInfo = studentClassesList
					.get(groupPosition).getStudentInfoList().get(childPosition);
			showPicture(studentInfo.getPhotoPath(),
					iv_senddynamic_child_selectstudent_item_logo, options);
			tv_senddynamic_child_selectstudent_item_name.setText(studentInfo
					.getName());

			cb_senddynamic_child_selectstudent_item_state
					.setChecked(studentInfo.getCheckState());

			return convertView;
		}

		// group method stub
		public Object getGroup(int groupPosition) {
			return studentClassesList.get(groupPosition);
		}

		public int getGroupCount() {
			return studentClassesList.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.senddynamic_selectstudent_item, null);
			}
			TextView tv_senddynamic_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_selectstudent_item_name);
			ImageView iv_senddynamic_selectstudent_item_arrow = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_selectstudent_item_arrow);
			CheckBox cb_senddynamic_selectstudent_item_state = (CheckBox) convertView
					.findViewById(R.id.cb_senddynamic_selectstudent_item_state);

			final StudentClass sc = studentClassesList.get(groupPosition);
			tv_senddynamic_selectstudent_item_name.setText(sc.getName());
			cb_senddynamic_selectstudent_item_state
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							sc.setCheckState(!sc.getCheckState());
							for (int i = 0; i < sc.getStudentInfoList().size(); i++) {
								StudentInfo sti = sc.getStudentInfoList()
										.get(i);
								sti.setCheckState(sc.getCheckState());
							}
							expandableAdapter.notifyDataSetChanged();
						}

					});
			cb_senddynamic_selectstudent_item_state
					.setChecked(studentClassesList.get(groupPosition)
							.getCheckState());

			// 判断isExpanded就可以控制是按下还是关闭，同时更换图片
			if (isExpanded) {
				iv_senddynamic_selectstudent_item_arrow
						.setBackgroundResource(R.drawable.arrow_down);
			} else {
				iv_senddynamic_selectstudent_item_arrow
						.setBackgroundResource(R.drawable.arrow_right);
			}

			return convertView;
		}

		public boolean hasStableIds() {
			return false;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	/**
	 * 获取学生和班级 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetStudentAndClassTask extends
			AsyncTask<String, Void, StudentClassList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected StudentClassList doInBackground(String... params) {

			try {
				studentClassList = AppContext.getApp().getStudentClassList(
						params[0]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				studentClassList = null;
			}
			return studentClassList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(StudentClassList result) {
			closeProgressDialog();
			super.onPostExecute(result);

			if (result != null) {

				if (result.getStatus().equals("0")) {
					studentClassesList = studentClassList
							.getStudentClassesList();
					if (studentClassesList == null
							|| studentClassesList.size() == 0) {
						ll_no_items_listed.setVisibility(View.VISIBLE);
					} else {
						ll_no_items_listed.setVisibility(View.GONE);
					}
					expandableAdapter = new ExpandableAdapter(context);
					// 得到实际的ListView
					el_myinvitation_senddynamic_selectstudent
							.setAdapter(expandableAdapter);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_changeclass_bottom_bar_changeclass:

			if (hasSelectStudent()) {
				showProgressDialog(context, "",
						getResources().getString(R.string.load_ing));
				new GetUserClassTask().execute();
			} else {
				UIHelper.ToastMessage(context, "请选择要转班的学生");
				return;
			}

			break;

		case R.id.rl_changeclass_bottom_bar_quitschool:
			if (!hasSelectStudent()) {
				UIHelper.ToastMessage(context, "请选择要休学的学生");
				return;
			} else {
				cngType = Constant.STUDENT_CHANGE_TYPE_XX;
				dialog = new DropOutDialog(context, R.style.MyDialog,
						new DropOutDialog.LeaveMyDialogListener() {

							@Override
							public void onClick(View view) {
								switch (view.getId()) {
								case R.id.btn_dialog_selectstudent_yes:
									showProgressDialog(
											context,
											"",
											getResources().getString(
													R.string.load_ing));
									String susDate = ((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_starttime_text))
											.getText().toString().trim();
									String estResuDate = ((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_endtime_text))
											.getText().toString().trim();
									if (TextUtils.isEmpty(susDate)) {
										UIHelper.ToastMessage(context,
												"请选择休学日期");
										return;
									}
									if (TextUtils.isEmpty(estResuDate)) {
										UIHelper.ToastMessage(context,
												"请选择预计复学日期");
										return;
									}
									new SaveStuChgStateTask()
											.execute(getSendJsonArray(cngType,
													estResuDate, susDate, "",
													"", ""));
									dialog.dismiss();
									break;
								case R.id.btn_dialog_selectstudent_no:
									dialog.dismiss();
									break;
								case R.id.tv_dialog_xiuxue_class_starttime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_starttime_text));
									break;
								case R.id.tv_dialog_xiuxue_class_endtime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_endtime_text));
									break;
								}
							}

						});
				dialog.show();
			}

			break;
		case R.id.rl_changeclass_bottom_bar_graduate:
			if (!hasSelectStudent()) {
				UIHelper.ToastMessage(context, "请选择要毕业的学生");
				return;
			} else {
				cngType = Constant.STUDENT_CHANGE_TYPE_BY;
				dialog = new GraduationQuitClassDialog(context,
						R.style.MyDialog, "确定要毕业了吗", R.drawable.biye_dialog,
						new GraduationQuitClassDialog.LeaveMyDialogListener() {

							@Override
							public void onClick(View view) {
								switch (view.getId()) {
								case R.id.btn_dialog_selectstudent_yes:
									showProgressDialog(
											context,
											"",
											getResources().getString(
													R.string.load_ing));

									new SaveStuChgStateTask()
											.execute(getSendJsonArray(cngType,
													"", "", "", "", ""));
									dialog.dismiss();
									break;
								case R.id.btn_dialog_selectstudent_no:
									dialog.dismiss();
									break;
								case R.id.tv_dialog_xiuxue_class_starttime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_starttime_text));
									break;
								case R.id.tv_dialog_xiuxue_class_endtime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_endtime_text));
									break;
								}
							}

						});
				dialog.show();
			}
			break;
		case R.id.rl_changeclass_bottom_bar_quitclass:
			if (!hasSelectStudent()) {
				UIHelper.ToastMessage(context, "请选择要退班的学生");
				return;
			} else {
				cngType = Constant.STUDENT_CHANGE_TYPE_TB;
				dialog = new GraduationQuitClassDialog(context,
						R.style.MyDialog, "确定要退班了吗", R.drawable.tuiban_dialog,
						new GraduationQuitClassDialog.LeaveMyDialogListener() {

							@Override
							public void onClick(View view) {
								switch (view.getId()) {
								case R.id.btn_dialog_selectstudent_yes:
									showProgressDialog(
											context,
											"",
											getResources().getString(
													R.string.load_ing));

									new SaveStuChgStateTask()
											.execute(getSendJsonArray(cngType,
													"", "", "", "", ""));
									dialog.dismiss();
									break;
								case R.id.btn_dialog_selectstudent_no:
									dialog.dismiss();
									break;
								case R.id.tv_dialog_xiuxue_class_starttime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_starttime_text));
									break;
								case R.id.tv_dialog_xiuxue_class_endtime_text:
									dateTimeDialog((TextView) dialog
											.findViewById(R.id.tv_dialog_xiuxue_class_endtime_text));
									break;
								}
							}

						});
				dialog.show();
			}
			break;

		}
	}

	// 选择时间
	private void dateTimeDialog(TextView tView) {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				ChangeClassSelectStudentActivity.this, tView.getText()
						.toString().trim());
		dateTimePicKDialog.dateTimePicKDialog(tView,
				Constant.DATETIME_PICK_YYYYMMDD, Constant.DATETIME_PICK_HHMMSS);
	}

	// 判断是否选择了学生
	private Boolean hasSelectStudent() {
		Boolean hasSelectStudent = false;
		for (StudentClass sClass : studentClassesList) {
			for (StudentInfo studentInfo : sClass.getStudentInfoList()) {
				if (studentInfo.getCheckState()) {
					hasSelectStudent = true;
					break;
				}
			}
		}
		return hasSelectStudent;
	}

	/**
	 * 获取当前登录用户的班级 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetUserClassTask extends AsyncTask<Void, Void, OrgList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected OrgList doInBackground(Void... params) {

			try {
				orgList = AppContext.getApp().getOrgClasses();

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				orgList = null;
			}
			return orgList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(final OrgList result) {

			super.onPostExecute(result);
			closeProgressDialog();
			if (result != null) {

				if (result.getStatus().equals("0")) {
					if (null != result.getOrgList()
							&& result.getOrgList().size() > 0) {
						dialog = new SelectClassDialog(context,
								R.style.MyDialog, result.getOrgList(), 140,
								new SelectClassDialog.LeaveMyDialogListener() {

									@Override
									public void onClick(View view) {
										switch (view.getId()) {
										case R.id.btn_dialog_selectstudent_yes:
											Boolean hasSelectClass = false;
											for (Org org : result.getOrgList()) {
												if (org.getCheckState()) {
													hasSelectClass = true;
													intoClaId = org.getClaId();
													break;
												}
											}
											if (!hasSelectClass) {
												UIHelper.ToastMessage(context,
														"请选择要转入的班级");
												return;
											} else {
												cngType = Constant.STUDENT_CHANGE_TYPE_ZB;
												EditText tv_note = (EditText) dialog
														.findViewById(R.id.et_dialog_selecter_class_note);
												note = tv_note.getText()
														.toString().trim();
												if (TextUtils.isEmpty(note)) {
													note = "";
												}
												showProgressDialog(
														context,
														"",
														getResources()
																.getString(
																		R.string.load_ing));
												new SaveStuChgStateTask()
														.execute(getSendJsonArray(
																cngType, "",
																"", intoClaId,
																"", note));
												dialog.dismiss();
											}

											break;
										case R.id.btn_dialog_selectstudent_no:
											dialog.dismiss();
											break;

										}
									}

								});
						dialog.show();
					} else {
						UIHelper.ToastMessage(context, "该用户下没有班级");
						return;
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

	/**
	 * 获得发送jsonarray字符串
	 * 
	 * @author 李林中 2015-4-28 上午11:29:07
	 * @param cngType
	 * @param estResuDate
	 * @param susDate
	 * @param intoClaId
	 * @param outChour
	 * @param note
	 * @return
	 */
	private String getSendJsonArray(String cngType, String estResuDate,
			String susDate, String intoClaId, String outChour, String note) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (StudentClass sClass : studentClassesList) {
			for (StudentInfo studentInfo : sClass.getStudentInfoList()) {
				jsonObject = new JSONObject();
				if (studentInfo.getCheckState()) {
					try {
						jsonObject.put("chosClaId", sClass.getClaId());
						jsonObject.put("cngType", cngType);

						jsonObject.put("estResuDate", estResuDate);

						jsonObject.put("intoClaId", intoClaId);

						jsonObject.put("exitClaId", sClass.getClaId());

						jsonObject.put("note", note);
						jsonObject.put("outChour", outChour);
						jsonObject.put("outClaId", sClass.getClaId());
						jsonObject.put("stuId", studentInfo.getStuId());
						jsonObject.put("stuName", studentInfo.getName());
						jsonObject.put("susDate", susDate);
						jsonArray.put(jsonObject);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return jsonArray.toString();
	}

	/**
	 * 学员变动 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class SaveStuChgStateTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().saveStuChgState(params[0]);
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
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {
					if (Constant.STUDENT_CHANGE_TYPE_ZB.equals(cngType)) {
						UIHelper.ToastMessage(context, "转班成功");

					} else if (Constant.STUDENT_CHANGE_TYPE_TB.equals(cngType)) {
						UIHelper.ToastMessage(context, "退班成功");
					} else if (Constant.STUDENT_CHANGE_TYPE_XX.equals(cngType)) {
						UIHelper.ToastMessage(context, "休学成功");
					} else if (Constant.STUDENT_CHANGE_TYPE_BY.equals(cngType)) {
						UIHelper.ToastMessage(context, "毕业了");
					}
					ChangeClassSelectStudentActivity.this
							.setResult(Constant.REQUSTCONDE_SELECTCLASS);
					ChangeClassSelectStudentActivity.this.finish();
					backAnim();
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
