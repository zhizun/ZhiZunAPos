package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.NoticeBox;
import com.zhizun.pos.bean.NoticeSendBoxDetail;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentInfo;

/**
 * 通知发件箱list 详情 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NoticeSendBoxDetailActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = NoticeSendBoxDetailActivity.class
			.getName();
	Context context;
	ImageView title_iv_left;
	ImageView title_iv_right_last;
	ImageView title_iv_right_next;
	Result result;
	NoticeSendBoxDetail noticeSendBoxDetail;

	TextView tv_notice_receivebox_detail_title_receivetime;
	TextView tv_notice_receivebox_detail_title_sender;
	TextView tv_notice_receivebox_detail_title_content;
	RelativeLayout rl_my_noticereceivebox_detail_bottom_bar_share;
	RelativeLayout rl_my_noticereceivebox_detail_sendnotice;
	RelativeLayout rl_my_noticereceivebox_detail_deletenotice;
	NoticeBox noticeBox;
	int position;

	TextView tv_notice_receivebox_detail_title_sendsms;
	TextView tv_notice_receivebox_detail_title_short;
	TextView tv_notice_receivebox_detail_title_btnishow;
	TextView tv_notice_receivebox_detail_title_receiversinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_sendnoticebox_detail);
		context = this;
		progress = ProgressDialog.show(context, "",
				getResources().getString(R.string.load_ing));
		Intent intent = getIntent();
		noticeBox = (NoticeBox) intent.getSerializableExtra("noticeBox");
		position = intent.getIntExtra("position", 0);
		initView();
		new GetNoticeDetailTask().execute(AppContext.getApp().getToken(),
				noticeBox.getNoticeId());
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
		rl_my_noticereceivebox_detail_deletenotice = (RelativeLayout) findViewById(R.id.rl_my_noticereceivebox_detail_deletenotice);
		rl_my_noticereceivebox_detail_deletenotice.setOnClickListener(this);
		tv_notice_receivebox_detail_title_sendsms = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_sendsms);
		tv_notice_receivebox_detail_title_short = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_short);
		tv_notice_receivebox_detail_title_btnishow = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_btnishow);
		tv_notice_receivebox_detail_title_receiversinfo = (TextView) findViewById(R.id.tv_notice_receivebox_detail_title_receiversinfo);

		tv_notice_receivebox_detail_title_btnishow.setText(R.string.show_more);
		tv_notice_receivebox_detail_title_btnishow.setOnClickListener(this);
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
			NoticeSendBoxDetailActivity.this.finish();
			intoAnim();
			break;
		case R.id.rl_my_noticereceivebox_detail_deletenotice:
			new DelSentNoticeTask().execute(AppContext.getApp().getToken(),
					noticeBox.getNoticeId());
			break;
		case R.id.tv_notice_receivebox_detail_title_btnishow:
			if (tv_notice_receivebox_detail_title_btnishow.getText().toString()
					.equals(getResources().getString(R.string.show_more))) {
				tv_notice_receivebox_detail_title_btnishow
						.setText(R.string.hide_more);
				tv_notice_receivebox_detail_title_receiversinfo
						.setVisibility(View.VISIBLE);
				tv_notice_receivebox_detail_title_short
						.setVisibility(View.GONE);

			} else {
				tv_notice_receivebox_detail_title_btnishow
						.setText(R.string.show_more);
				tv_notice_receivebox_detail_title_receiversinfo
						.setVisibility(View.GONE);
				tv_notice_receivebox_detail_title_short
						.setVisibility(View.VISIBLE);
			}

			break;

		}

	}

	/**
	 * 删除发送通知 创建人：李林中 创建日期：2015-1-6 上午10:14:53 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class DelSentNoticeTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			try {
				result = AppContext.getApp()
						.delSentNotice(params[0], params[1]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
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
					UIHelper.ToastMessage(context, "通知删除成功");
					Intent intent = getIntent();
					intent.putExtra("position", position);
					NoticeSendBoxDetailActivity.this
							.setResult(
									MyNoticeTeacherActivity.RESULT_ACCEPT_COLDE,
									intent);
					NoticeSendBoxDetailActivity.this.finish();
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

	/**
	 * 得到通知的详情 创建人：李林中 创建日期：2015-1-6 上午10:14:53 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetNoticeDetailTask extends
			AsyncTask<String, Void, NoticeSendBoxDetail> {
		AppException e;

		@Override
		protected NoticeSendBoxDetail doInBackground(String... params) {
			try {
				noticeSendBoxDetail = AppContext.getApp().getNoticeDetail(
						params[0], params[1]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return noticeSendBoxDetail;
		}

		@Override
		protected void onPostExecute(NoticeSendBoxDetail result) {

			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (result != null) {
				if (result.getStatus().equals("0")) {

					tv_notice_receivebox_detail_title_receivetime
							.setText("发送时间：" + result.getSendTime());
					tv_notice_receivebox_detail_title_sender.setText("发送人："
							+ result.getUserName());
					// tv_notice_receivebox_detail_title_sendsms.setText("共："+result.getContent()+"人");
					if (result.getIsSendMsg().equals(Constant.TYPE_YES)) {
						tv_notice_receivebox_detail_title_sendsms
								.setVisibility(View.VISIBLE);
					} else {
						tv_notice_receivebox_detail_title_sendsms
								.setVisibility(View.GONE);
					}
					StringBuilder text = new StringBuilder();
					List<String> name = new ArrayList<String>();
					ArrayList<StudentClass> list = result
							.getStudentClassesList();
					for (StudentClass studentClass : list) {
						text.append("<font color='#818181'>"
								+ studentClass.getName() + "</font><br>");
						List<StudentInfo> studentInfoList = studentClass
								.getStudentInfoList();
						for (StudentInfo studentInfo : studentInfoList) {
							text.append(studentInfo.getName() + " ");
							if (name.size() < 2) {
								name.add(studentInfo.getName());
							}
						}
						text.append("<br>");
					}

					tv_notice_receivebox_detail_title_short.setText(StringUtils
							.listToString(name) + "...");

					tv_notice_receivebox_detail_title_receiversinfo
							.setText(Html.fromHtml(text.toString()));

					tv_notice_receivebox_detail_title_content.setText("    "
							+ result.getContent());
					tv_notice_receivebox_detail_title_content.setOnLongClickListener(new OnLongClickListener() {
						@Override
						public boolean onLongClick(View arg0) {
							BaseTools.copyText(context, tv_notice_receivebox_detail_title_content.getText().toString());
							return false;
						}
					});
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
