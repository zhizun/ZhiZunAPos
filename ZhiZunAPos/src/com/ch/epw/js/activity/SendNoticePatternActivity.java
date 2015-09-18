package com.ch.epw.js.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.DateTimePickDialogUtil;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

/**
 * 通知发送模式 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SendNoticePatternActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = SendNoticePatternActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;
	RelativeLayout rl_activity_fstz_selectsendpattern_ljfs,
			rl_activity_fstz_selectsendpattern_dsfs;
	CheckBox cb_activity_fstz_selectsendpattern_lifs_state,
			cb_activity_fstz_selectsendpattern_dsfs_state;
	TextView tv_activity_fstz_selectsendpattern_dsfs_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fstz_teacher_sendpattern);
		options = Options.getListOptions();
		context = this;
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_fstz_teacher_sendpattern);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_select_send_pattern);

		
		
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getRightTextView().setText("确定");
		titleBarView.setBarRightOnclickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = getIntent();
						if (cb_activity_fstz_selectsendpattern_lifs_state
								.isChecked()) {
							SendNoticePatternActivity.this.setResult(
									Constant.NOTICE_SENDPATTERN_IMMEDIATELY,
									intent);
						}
						if (cb_activity_fstz_selectsendpattern_dsfs_state
								.isChecked()) {
							String str = tv_activity_fstz_selectsendpattern_dsfs_time
									.getText().toString().trim();
							if (null == str || "".equals(str)) {
								UIHelper.ToastMessage(context,
										"定时时间不能为空！请点击选择！");
								return;
							}
							intent.putExtra("timing", str);
							SendNoticePatternActivity.this.setResult(
									Constant.NOTICE_SENDPATTERN_TIMING, intent);
						}
						SendNoticePatternActivity.this.finish();
						backAnim();
					}
				});

		rl_activity_fstz_selectsendpattern_ljfs = (RelativeLayout) findViewById(R.id.rl_activity_fstz_selectsendpattern_ljfs);
		rl_activity_fstz_selectsendpattern_dsfs = (RelativeLayout) findViewById(R.id.rl_activity_fstz_selectsendpattern_dsfs);
		cb_activity_fstz_selectsendpattern_lifs_state = (CheckBox) findViewById(R.id.cb_activity_fstz_selectsendpattern_lifs_state);
		cb_activity_fstz_selectsendpattern_dsfs_state = (CheckBox) findViewById(R.id.cb_activity_fstz_selectsendpattern_dsfs_state);
		tv_activity_fstz_selectsendpattern_dsfs_time = (TextView) findViewById(R.id.tv_activity_fstz_selectsendpattern_dsfs_time);

		cb_activity_fstz_selectsendpattern_lifs_state.setOnClickListener(this);
		cb_activity_fstz_selectsendpattern_dsfs_state.setOnClickListener(this);
		rl_activity_fstz_selectsendpattern_dsfs.setOnClickListener(this);
		rl_activity_fstz_selectsendpattern_ljfs.setOnClickListener(this);

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
		case R.id.cb_activity_fstz_selectsendpattern_lifs_state:
			cb_activity_fstz_selectsendpattern_dsfs_state.setChecked(false);
			cb_activity_fstz_selectsendpattern_lifs_state.setChecked(true);
			tv_activity_fstz_selectsendpattern_dsfs_time.setText("");
			break;
		case R.id.cb_activity_fstz_selectsendpattern_dsfs_state:
			cb_activity_fstz_selectsendpattern_dsfs_state.setChecked(true);
			cb_activity_fstz_selectsendpattern_lifs_state.setChecked(false);
			dateTimeDialog();
			break;
		case R.id.rl_activity_fstz_selectsendpattern_dsfs:
			cb_activity_fstz_selectsendpattern_dsfs_state.setChecked(true);
			cb_activity_fstz_selectsendpattern_lifs_state.setChecked(false);
			dateTimeDialog();
			break;
		case R.id.rl_activity_fstz_selectsendpattern_ljfs:
			cb_activity_fstz_selectsendpattern_dsfs_state.setChecked(false);
			cb_activity_fstz_selectsendpattern_lifs_state.setChecked(true);
			tv_activity_fstz_selectsendpattern_dsfs_time.setText("");
			break;
		}
	}

	private void dateTimeDialog() {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				SendNoticePatternActivity.this,
				tv_activity_fstz_selectsendpattern_dsfs_time.getText()
						.toString().trim());
		dateTimePicKDialog.dateTimePicKDialog(
				tv_activity_fstz_selectsendpattern_dsfs_time, Constant.DATETIME_PICK_YYYYMMDD, Constant.DATETIME_PICK_HHMMSS);
	}
}
