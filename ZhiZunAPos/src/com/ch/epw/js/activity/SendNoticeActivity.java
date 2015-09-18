package com.ch.epw.js.activity;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.jz.activity.MyInvitationRquestSelectStudentActivity;
import com.ch.epw.jz.activity.SelectTeacherActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.DateUtil;
import com.ch.epw.utils.EmojiFilter;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.SelectReceiverDialog;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentInfo;
import com.zhizun.pos.bean.TeacherInfo;
import com.zhizun.pos.bean.TeacherOrg;
import com.zhizun.pos.bean.TeacherOrgClass;

/**
 * 发送通知 创建人：李林中 创建日期：2014-12-15 上午10:10:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SendNoticeActivity extends BaseActivity implements OnClickListener {

	protected static final String TAG = SendNoticeActivity.class.getName();
	Context context;
	private TitleBarView titleBarView;

	ArrayList<StudentClass> studentClasseList;
	ArrayList<TeacherOrgClass> teacherAndOrglist;
	String isSendMsg = Constant.TYPE_NO;// 是否发送短信标识 0不发送 1发送
	String sendMode = Constant.NOTICE_SENDPATTERN_IMMEDIATELY + "";// 0：立即发送；1：定时发送
	Result result;
	Dialog dialog;
	String taskTime;// 定时发送时间
	int RESULT_CODE_INSERTDYNAMIC = 1;

	EditText et_activity_fstz_teacher_content;
	Button btn_activity_fstz_teacher_noticetemplete;
	RelativeLayout rl_activity_fstz_teacher_receviers;
	// rl_activity_fstz_teacher_pattern;
	// rl_activity_fstz_teacher_sendsms;
	TextView tv_activity_fstz_teacher_receivers,
			tv_activity_fstz_teacher_receivers_count,
			tv_activity_fsdt_teacher_content_count;
	ImageView iv_activity_fstz_teacher_imglogo;

	// TextView tv_activity_fstz_teacher_text_pattern,
	// tv_activity_fstz_teacher_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		sInputFormat = getResources()
				.getString(R.string.input_text_count_limit);
		sFinalInput = String.format(sInputFormat, Rest_Length);
		setContentView(R.layout.activity_fstz_teacher);
		options = Options.getListOptions();
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_fstz_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_notice_send);
		titleBarView.setRightText(R.string.title_text_send);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		rl_activity_fstz_teacher_receviers = (RelativeLayout) findViewById(R.id.rl_activity_fstz_teacher_receviers);
		rl_activity_fstz_teacher_receviers
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog = new SelectReceiverDialog(
								context,
								R.style.MyDialog,
								new SelectReceiverDialog.LeaveMyDialogListener() {

									@Override
									public void onClick(View view) {
										switch (view.getId()) {
										case R.id.rl_activity_fstz_teacher_receviers_sendteacher:
											if (null != studentClasseList
													&& studentClasseList.size() > 0) {
												studentClasseList = null;
											}

											Intent intent = new Intent(
													context,
													SendNoticeSelectTeacherActivity.class);
											intent.putExtra(
													"teacherAndOrglist",
													teacherAndOrglist);
											startActivityForResult(
													intent,
													Constant.SEND_NOTICE_SELCET_TEACHER);
											intoAnim();
											dialog.dismiss();
											break;
										case R.id.rl_activity_fstz_teacher_receviers_sendstudent:
											if (null != teacherAndOrglist
													&& teacherAndOrglist.size() > 0) {
												teacherAndOrglist = null;
											}
											Intent intent_sendstudent = new Intent(
													context,
													MyInvitationSelectStudentActivity.class);

											intent_sendstudent.putExtra(
													"studentClasseList",
													studentClasseList);

											startActivityForResult(
													intent_sendstudent,
													Constant.SEND_NOTICE_SELCET_SUTDENT);
											intoAnim();
											dialog.dismiss();
											break;
										}
									}
								});
						dialog.show();

					}
				});
		tv_activity_fstz_teacher_receivers = (TextView) findViewById(R.id.tv_activity_fstz_teacher_receivers);
		tv_activity_fstz_teacher_receivers_count = (TextView) findViewById(R.id.tv_activity_fstz_teacher_receivers_count);

		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = et_activity_fstz_teacher_content.getText()
						.toString();
				if (null == content || content.equals("")) {
					UIHelper.ToastMessage(context, "内容不能为空");
					return;
				}
				if (content.length() > MAX_LENGTH) {
					UIHelper.ToastMessage(context, "内容不能大于"+MAX_LENGTH+"个字符");
					return;
				}
				StringBuffer sBuffer = new StringBuffer();
				String receivers = null;
				if (null != teacherAndOrglist && teacherAndOrglist.size() > 0) {
					for (TeacherOrgClass teacherOrg : teacherAndOrglist) {
						if (!teacherOrg.getReceivers().equals("")) {
							sBuffer.append(teacherOrg.getReceivers());
							sBuffer.append(",");
						}
					}
					receivers = sBuffer.toString();
					if (receivers.endsWith(",")) {
						receivers = receivers.substring(0,
								receivers.length() - 1);
					}
				}

				if (null != studentClasseList && studentClasseList.size() > 0) {
					for (StudentClass studentClass : studentClasseList) {
						if (null != studentClass.getReceivers()
								&& !studentClass.getReceivers().equals("")) {
							sBuffer.append(studentClass.getReceivers());
						}
					}
					receivers = sBuffer.toString();
					if (receivers.endsWith(",")) {
						receivers = receivers.substring(0,
								receivers.length() - 1);
					}
				}
				if (null == receivers || receivers.length() <= 0) {
					UIHelper.ToastMessage(context, "接收人不能为空");
					return;
				}
				Log.i(TAG, "receivers=" + receivers);
				if (sendMode.equals(Constant.NOTICE_SENDPATTERN_IMMEDIATELY
						+ "")) {
					taskTime = "";
				}
				Log.i(TAG, "sendmode=" + sendMode + ",taskTime=" + taskTime);
				showProgressDialog(context, "",
						getResources().getString(R.string.submit_ing));
				new SendNoticeTask().execute(AppContext.getApp().getToken(),
						EmojiFilter.filterEmoji(content), receivers, isSendMsg,
						sendMode, taskTime);
			}
		});
		et_activity_fstz_teacher_content = (EditText) findViewById(R.id.et_activity_fstz_teacher_content);
		et_activity_fstz_teacher_content
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						Rest_Length = et_activity_fstz_teacher_content
								.getText().toString().trim().length();

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						if (Rest_Length > MAX_LENGTH) {

							UIHelper.ToastMessage(context, "最多只能输入"+MAX_LENGTH+"个字");
							sFinalInput = String.format(sInputFormat,
									MAX_LENGTH);
							tv_activity_fsdt_teacher_content_count
									.setText(sFinalInput);
							et_activity_fstz_teacher_content
									.setText(et_activity_fstz_teacher_content
											.getText().toString().trim()
											.substring(0, MAX_LENGTH));
							et_activity_fstz_teacher_content
									.setSelection(et_activity_fstz_teacher_content
											.getText().toString().trim()
											.length());

						} else {
							sFinalInput = String.format(sInputFormat,
									Rest_Length);
							tv_activity_fsdt_teacher_content_count
									.setText(sFinalInput);
						}
					}
				});
		tv_activity_fsdt_teacher_content_count = (TextView) findViewById(R.id.tv_activity_fsdt_teacher_content_count);
		tv_activity_fsdt_teacher_content_count.setText(sFinalInput);
		// rl_activity_fstz_teacher_pattern = (RelativeLayout)
		// findViewById(R.id.rl_activity_fstz_teacher_pattern);
		// rl_activity_fstz_teacher_pattern.setOnClickListener(this);
		// rl_activity_fstz_teacher_sendsms = (RelativeLayout)
		// findViewById(R.id.rl_activity_fstz_teacher_sendsms);
		// rl_activity_fstz_teacher_sendsms.setOnClickListener(this);
		// iv_activity_fstz_teacher_imglogo = (ImageView)
		// findViewById(R.id.iv_activity_fstz_teacher_imglogo);
		btn_activity_fstz_teacher_noticetemplete = (Button) findViewById(R.id.btn_activity_fstz_teacher_noticetemplete);
		btn_activity_fstz_teacher_noticetemplete.setOnClickListener(this);
		// tv_activity_fstz_teacher_text_pattern = (TextView)
		// findViewById(R.id.tv_activity_fstz_teacher_text_pattern);
		// tv_activity_fstz_teacher_message = (TextView)
		// findViewById(R.id.tv_activity_fstz_teacher_message);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int count = 0;
		if (requestCode == Constant.SEND_NOTICE_SELCET_SUTDENT
				&& resultCode == Constant.SEND_NOTICE_SELCET_SUTDENT) {
			studentClasseList = (ArrayList<StudentClass>) data
					.getSerializableExtra("studentClasseList");
			StringBuffer sbBuffer = new StringBuffer();
			String nameListStringShort = "";
			if (null != studentClasseList && studentClasseList.size() > 0) {
				for (int i = 0; i < studentClasseList.size(); i++) {
					StudentClass sClass = studentClasseList.get(i);
					for (int j = 0; j < sClass.getStudentInfoList().size(); j++) {
						StudentInfo sInfo = sClass.getStudentInfoList().get(j);
						if (sInfo.getCheckState()) {
							if (sbBuffer.length() > 0) {
								sbBuffer.append(",");
							}
							sbBuffer.append(sInfo.getName());
							count++;
							if (count <= 2) {
								nameListStringShort = sbBuffer.toString();
							}
						}
					}
				}
			}

			if (count > 0) {
				if (!nameListStringShort.equals(sbBuffer.toString())) {
					nameListStringShort = nameListStringShort + "等";
				}
				tv_activity_fstz_teacher_receivers.setText(nameListStringShort);
				tv_activity_fstz_teacher_receivers_count.setText(" 共" + count
						+ "人");
			} else {
				tv_activity_fstz_teacher_receivers.setText("");
				tv_activity_fstz_teacher_receivers_count.setText("");
			}

		}
		if (requestCode == Constant.SEND_NOTICE_SELCET_TEACHER
				&& resultCode == Constant.SEND_NOTICE_SELCET_SUTDENT) {
			teacherAndOrglist = (ArrayList<TeacherOrgClass>) data
					.getSerializableExtra("teacherAndOrglist");
			StringBuffer sbBuffer = new StringBuffer();
			String nameListStringShort = "";
			if (null != teacherAndOrglist && teacherAndOrglist.size() > 0) {
				for (int i = 0; i < teacherAndOrglist.size(); i++) {
					TeacherOrgClass sClass = teacherAndOrglist.get(i);
					for (int j = 0; j < sClass.getTeacherInfoList().size(); j++) {
						TeacherInfo sInfo = sClass.getTeacherInfoList().get(j);
						if (sInfo.getCheckState()) {
							if (sbBuffer.length() > 0) {
								sbBuffer.append(",");
							}
							sbBuffer.append(sInfo.getName());
							count++;
							if (count <= 2) {
								nameListStringShort = sbBuffer.toString();
							}
						}
					}
				}
			}

			if (count > 0) {
				if (!nameListStringShort.equals(sbBuffer.toString())) {
					nameListStringShort = nameListStringShort + "等";
				}
				tv_activity_fstz_teacher_receivers.setText(nameListStringShort);
				tv_activity_fstz_teacher_receivers_count.setText(" 共" + count
						+ "人");
			} else {
				tv_activity_fstz_teacher_receivers.setText("");
				tv_activity_fstz_teacher_receivers_count.setText("");
			}
		}
		// if (requestCode == Constant.SEND_NOTICE_PARTTERN) {
		// if (resultCode == Constant.NOTICE_SENDPATTERN_IMMEDIATELY) {
		// tv_activity_fstz_teacher_text_pattern
		// .setText(R.string.title_text_sendnotice_immediately);
		// sendMode = Constant.NOTICE_SENDPATTERN_IMMEDIATELY + "";
		// }
		// if (resultCode == Constant.NOTICE_SENDPATTERN_TIMING) {
		// tv_activity_fstz_teacher_text_pattern.setText(data
		// .getStringExtra("timing") + " 发送");
		// sendMode = Constant.NOTICE_SENDPATTERN_TIMING + "";
		// taskTime = data.getStringExtra("timing").trim();
		// }
		// }

		if (requestCode == Constant.SEND_NOTICE_SELCET_TEMPLETE
				&& resultCode == Constant.SEND_NOTICE_SELCET_TEMPLETE) {
			// 把返回来的通知模版内容 赋值给输入框
			et_activity_fstz_teacher_content.setText(data
					.getStringExtra("noticeTempleteContent"));
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 模式
		// case R.id.rl_activity_fstz_teacher_pattern:
		// Intent intent = new Intent(SendNoticeActivity.this,
		// SendNoticePatternActivity.class);
		// startActivityForResult(intent, Constant.SEND_NOTICE_PARTTERN);
		// intoAnim();
		// break;
		// 发送短信
		// case R.id.rl_activity_fstz_teacher_sendsms:
		// if (iv_activity_fstz_teacher_imglogo.isEnabled()) {
		// iv_activity_fstz_teacher_imglogo.setEnabled(false);
		// tv_activity_fstz_teacher_message.setTextColor(getResources()
		// .getColor(R.color.darkblue_2));
		// isSendMsg = Constant.TYPE_YES;
		//
		// } else if (!iv_activity_fstz_teacher_imglogo.isEnabled()) {
		// iv_activity_fstz_teacher_imglogo.setEnabled(true);
		// tv_activity_fstz_teacher_message.setTextColor(getResources()
		// .getColor(R.color.black));
		// isSendMsg = Constant.TYPE_NO;
		// }
		// break;
		// 模版
		case R.id.btn_activity_fstz_teacher_noticetemplete:
			Intent intentTemplete = new Intent(SendNoticeActivity.this,
					NoticeTempleteActivity.class);
			startActivityForResult(intentTemplete,
					Constant.SEND_NOTICE_SELCET_TEMPLETE);
			intoAnim();
			break;
		}

	}

	/**
	 * 发送通知 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class SendNoticeTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().sendNotice(params[0], params[1],
						params[2], params[3], params[4], params[5]);
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
					UIHelper.ToastMessage(context, "您已成功发送通知");
					// 发送广播通知 来刷新发件箱列表
					Intent intent = new Intent();
					intent.setAction("com.ch.epw.REFRESH_SENDNOTICEBOX_LIST");
					sendBroadcast(intent);
					SendNoticeActivity.this.finish();
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
