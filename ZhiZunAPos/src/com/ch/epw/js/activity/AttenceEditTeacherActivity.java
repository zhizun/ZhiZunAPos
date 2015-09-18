package com.ch.epw.js.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.NoScrollListView;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewAttenceEditTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Attence;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Remarks;
import com.zhizun.pos.bean.Result;


/**
 * 考勤记录 教师端 创建人：李林中 创建日期：2014-12-15 上午10:11:13 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class AttenceEditTeacherActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = AttenceEditTeacherActivity.class
			.getName();
	private TitleBarView titleBarView;
	Context context;
	TextView tv_list_attence_js_item_classname;
	TextView tv_list_attence_js_item_attenceinfo;
//	ImageView iv_list_attence_js_item_xhks_reduce;
//	ImageView iv_list_attence_js_item_xhks_increase;
//	TextView tv_list_attence_js_item_xhks_count;
	TextView tv_list_attence_js_item_username_content;
	NoScrollListView nl_list_attence_js_item_attencelist;
	TextView tv_my_attence_teacher_texttime;
	ArrayList<Attence> attenceList;
	Attence attence;
	int position;
	Result result;
	ArrayList<AttenceDetail> attenceDetailList;

	ListViewAttenceEditTeacherAdapter listViewAttenceEditTeacherAdapter;
	protected static final int FLAG_SENDDYNAMIC_REQUESTCODE_STRING = 1;
	int textCount;// 消耗课时

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				int attencenum = msg.arg1;
				int absentnum = msg.arg2;

				attence.setAttendNum(attencenum + "");
				attence.setAbsentNum(absentnum + "");
				tv_list_attence_js_item_attenceinfo
						.setText("已到/未到:" + attence.getAttendNum() + "/"
								+ attence.getAbsentNum());
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kqjl_teacher_edit);
		context = AttenceEditTeacherActivity.this;
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		initView();
		Intent intent = getIntent();
		attenceList = (ArrayList<Attence>) intent
				.getSerializableExtra("listItems");
		position = intent.getIntExtra("position", 0);
		attence = attenceList.get(position);
		attenceDetailList = attenceList.get(position).getAttenceDetailList();
		initData();
		IntentFilter filter = new IntentFilter(Constant.REMARKS);
		registerReceiver(receiver, filter);

	}

	private void initData() {
		if (attence.getClaName().length()>10) {
			String claName=attence.getClaName().substring(0, 10)+"...";
			tv_list_attence_js_item_classname.setText(claName);
		}else {
			tv_list_attence_js_item_classname.setText(attence.getClaName());
		}
		tv_list_attence_js_item_attenceinfo.setText("已到/未到:"
				+ attence.getAttendNum() + "/" + attence.getAbsentNum());

//		tv_list_attence_js_item_xhks_count.setText(attence
//				.getExpendedChourNum());
		tv_list_attence_js_item_username_content.setText(attence.getUserName());
		tv_my_attence_teacher_texttime.setText(attence.getRecTime());
		if (attence.getAttenceDetailList() != null
				&& attence.getAttenceDetailList().size() > 0) {
			listViewAttenceEditTeacherAdapter = new ListViewAttenceEditTeacherAdapter(
					context, attence.getAttenceDetailList(), handler,
					Integer.parseInt(attence.getAttendNum()),
					Integer.parseInt(attence.getAbsentNum()));
			nl_list_attence_js_item_attencelist
					.setAdapter(listViewAttenceEditTeacherAdapter);
		} else {
			nl_list_attence_js_item_attencelist.setAdapter(null);
		}
		textCount = Integer.parseInt(attence.getExpendedChourNum());
		if (progress != null) {
			progress.dismiss();
		}
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zxdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText(R.string.text_text_today_attence);

		
		
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getRightTextView().setText(R.string.text_save);
		titleBarView.setBarRightOnclickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						progress = ProgressDialog.show(context, "",
								getResources().getString(R.string.submit_ing));
						JSONArray jsonArray = new JSONArray();
						JSONObject jsonObject = null;
						Remarks remarks=null;
						for (AttenceDetail attenceDetail : attenceDetailList) {
							try {
								jsonObject = new JSONObject();
								JSONArray remarksArray = new JSONArray();
								jsonObject.put("stuId",
										attenceDetail.getStuId());
								jsonObject.put("stuName",
										attenceDetail.getStuName());
								jsonObject.put("detailId",
										attenceDetail.getDetailId());
								jsonObject.put("status",
										attenceDetail.getStatus());
								JSONObject remarksJson =null;
								for (int i = 0; i < attenceDetail.getRemarks().size(); i++) {
									remarksJson= new JSONObject();
									remarks=attenceDetail.getRemarks().get(i);
									remarksJson.put("type",
											remarks.getTypt());
									remarksJson.put("note",
											remarks.getNote());
									remarksJson.put("recordTime",
											remarks.getRecordtime());
									remarksArray.put(remarksJson);
								}
								jsonObject.put("remarks",remarksArray);
								jsonArray.put(jsonObject);
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
						Log.i(TAG,
								"JSONArray.toString()=" + jsonArray.toString());

						new SaveAttenceTask().execute(AppContext.getApp()
								.getToken(), attence.getAttenceId(), attence
								.getClaId(), attence.getClaName(), attence
								.getExpendedChourNum(), jsonArray.toString());

					}
				});

		tv_my_attence_teacher_texttime = (TextView) findViewById(R.id.tv_my_attence_teacher_texttime);
		tv_list_attence_js_item_classname = (TextView) findViewById(R.id.tv_list_attence_js_item_classname);
		tv_list_attence_js_item_attenceinfo = (TextView) findViewById(R.id.tv_list_attence_js_item_attenceinfo);
//		iv_list_attence_js_item_xhks_reduce = (ImageView) findViewById(R.id.iv_list_attence_js_item_xhks_reduce);
//		iv_list_attence_js_item_xhks_increase = (ImageView) findViewById(R.id.iv_list_attence_js_item_xhks_increase);
//		tv_list_attence_js_item_xhks_count = (TextView) findViewById(R.id.tv_list_attence_js_item_xhks_count);
		tv_list_attence_js_item_username_content = (TextView) findViewById(R.id.tv_list_attence_js_item_username_content);
		nl_list_attence_js_item_attencelist = (NoScrollListView) findViewById(R.id.nl_list_attence_js_item_attencelist);
//		iv_list_attence_js_item_xhks_reduce.setOnClickListener(this);
//		iv_list_attence_js_item_xhks_increase.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	/**
	 * 布置作业 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class SaveAttenceTask extends AsyncTask<String, Void, Result> {
		AppException e;
		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().saveAttence(params[0], params[1],
						params[2], params[3], params[4], params[5]);
			} catch (AppException e) {
				this.e=e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "保存成功");
					// 发送广播通知 来刷新作业列表
					Intent intent = new Intent();
					intent.setAction("com.ch.epw.REFRESH_ATTENCE_LIST");
					sendBroadcast(intent);
					AttenceEditTeacherActivity.this.finish();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FLAG_SENDDYNAMIC_REQUESTCODE_STRING
				&& resultCode == FLAG_SENDDYNAMIC_REQUESTCODE_STRING) {
			// 进入自动刷新
			mPullListView.doPullRefreshing(true, 500);
		}
	}

	@Override
	public void onClick(View v) {

		/*switch (v.getId()) {
		case R.id.iv_list_attence_js_item_xhks_reduce:
			if (textCount >= 1) {
				textCount = textCount - 1;
			}
			tv_list_attence_js_item_xhks_count.setText("" + textCount);
			attence.setExpendedChourNum("" + textCount);
			Log.i(TAG, "textCount=" + textCount);
			break;
		case R.id.iv_list_attence_js_item_xhks_increase:
			textCount = textCount + 1;
			tv_list_attence_js_item_xhks_count.setText("" + textCount);
			attence.setExpendedChourNum("" + textCount);
			Log.i(TAG, "textCount=" + textCount);
			break;
		}*/
	}
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(Constant.REMARKS.equals(intent.getAction())){
				ArrayList<Remarks> remarks=(ArrayList<Remarks>) intent.getSerializableExtra(Constant.REMARKS);
				int po=intent.getIntExtra("position", 0);
				attence.getAttenceDetailList().get(po).setRemarks(remarks);
				listViewAttenceEditTeacherAdapter.notifyDataSetChanged();
			}
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}

}
