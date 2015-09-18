package com.ch.epw.js.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.NoticeBox;
import com.zhizun.pos.bean.Result;

/**
 * 通知收件箱list 详情 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NoticeReceiveBoxDetailActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = NoticeReceiveBoxDetailActivity.class
			.getName();
	Context context;
	ImageView title_iv_left;
	ImageView title_iv_right_last;
	ImageView title_iv_right_next;
	Result result;

	TextView tv_notice_receivebox_detail_title_receivetime;
	TextView tv_notice_receivebox_detail_title_sender;
	TextView tv_notice_receivebox_detail_title_content;
	RelativeLayout rl_my_noticereceivebox_detail_bottom_bar_share;
	RelativeLayout rl_my_noticereceivebox_detail_sendnotice;
	ArrayList<NoticeBox> noticeBoxList;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_receivebox_detail);
		context = this;

		Intent intent = getIntent();
		noticeBoxList = (ArrayList<NoticeBox>) intent
				.getSerializableExtra("receiveBoxList");
		position = intent.getIntExtra("position", 0);
		initView();
		if (noticeBoxList.get(position).getIsRead()
				.equals(Constant.NOTICE_UNREAD)) {
			progress = ProgressDialog.show(context, "", getResources()
					.getString(R.string.load_ing));
			new SetNoticeReadTask().execute(AppContext.getApp().getToken(),
					noticeBoxList.get(position).getNoticeId());
		} else {
			if (noticeBoxList.get(position).getSendMode()
					.equals(Constant.NOTICE_SENDPATTERN_IMMEDIATELY + "")) {
				tv_notice_receivebox_detail_title_receivetime.setText("接收时间："
						+ noticeBoxList.get(position).getSendTime());
			} else {
				tv_notice_receivebox_detail_title_receivetime.setText("接收时间："
						+ noticeBoxList.get(position).getTaskTime());
			}
			tv_notice_receivebox_detail_title_sender.setText("发送人："
					+ noticeBoxList.get(position).getUserName());
			tv_notice_receivebox_detail_title_content.setText("    "
					+ noticeBoxList.get(position).getContent());
			tv_notice_receivebox_detail_title_content.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					BaseTools.copyText(context, tv_notice_receivebox_detail_title_content.getText().toString());
					return false;
				}
			});
		}
	}

	private void initView() {
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		title_iv_left.setOnClickListener(this);
		title_iv_right_last = (ImageView) findViewById(R.id.title_iv_right_last);
		title_iv_right_last.setOnClickListener(this);
		title_iv_right_next = (ImageView) findViewById(R.id.title_iv_right_next);
		title_iv_right_next.setOnClickListener(this);
		tv_notice_receivebox_detail_title_receivetime = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_receivetime);

		tv_notice_receivebox_detail_title_sender = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_sender);

		tv_notice_receivebox_detail_title_content = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_content);

		rl_my_noticereceivebox_detail_bottom_bar_share = (RelativeLayout) findViewById(R.id.rl_my_noticereceivebox_detail_bottom_bar_share);
		rl_my_noticereceivebox_detail_bottom_bar_share.setOnClickListener(this);
		rl_my_noticereceivebox_detail_sendnotice = (RelativeLayout) findViewById(R.id.rl_my_noticereceivebox_detail_sendnotice);
		rl_my_noticereceivebox_detail_sendnotice.setOnClickListener(this);
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
		case R.id.title_iv_left:
			doBack();
			break;
		case R.id.title_iv_right_last:

			break;
		case R.id.title_iv_right_next:

			break;
		case R.id.rl_my_noticereceivebox_detail_bottom_bar_share:
			NoticeBox noticeBox = noticeBoxList.get(position);
			AppContext.getApp().showShare(context, 
				noticeBox.getOrgId(),
				noticeBox.getNoticeId(), 
				Constant.COMMNETTYPE_TZGG,
				noticeBox.getContent(),
				null);
			break;
		case R.id.rl_my_noticereceivebox_detail_sendnotice:
			Intent intent = new Intent(context, SendNoticeActivity.class);
			startActivity(intent);
			NoticeReceiveBoxDetailActivity.this.finish();
			intoAnim();
			break;
		}

	}

	class SetNoticeReadTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			try {
				result = AppContext.getApp()
						.setNoticeRead(params[0], params[1]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {

			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (result != null) {
				if (result.getStatus().equals("0")) {
					if (noticeBoxList
							.get(position)
							.getSendMode()
							.equals(Constant.NOTICE_SENDPATTERN_IMMEDIATELY
									+ "")) {
						tv_notice_receivebox_detail_title_receivetime
								.setText("接收时间："
										+ noticeBoxList.get(position)
												.getSendTime());
					} else {
						tv_notice_receivebox_detail_title_receivetime
								.setText("接收时间："
										+ noticeBoxList.get(position)
												.getTaskTime());
					}
					tv_notice_receivebox_detail_title_sender.setText("发送人："
							+ noticeBoxList.get(position).getUserName());
					tv_notice_receivebox_detail_title_content.setText("    "
							+ noticeBoxList.get(position).getContent());

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
