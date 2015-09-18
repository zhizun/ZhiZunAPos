package com.ch.epw.js.activity;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ch.epw.utils.DateUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewAttenceTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.AttenceList;

/**
 * 考勤记录 教师端 创建人：李林中 创建日期：2014-12-15 上午10:11:13 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class AttenceTeacherActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = AttenceTeacherActivity.class.getName();
	private TitleBarView titleBarView;
	Context context;
	AttenceList attenceList;
	ListView ll_activity_kqjl_teacher;
	ImageView iv_my_attence_teacher_left;
	ImageView iv_my_attence_teacher_right;
	TextView tv_my_attence_teacher_texttime;
	TextView tv_my_attence_teacher_textweek;
	ListViewAttenceTeacherAdapter listViewAttenceTeacherAdapter;
	protected static final int FLAG_SENDDYNAMIC_REQUESTCODE_STRING = 1;
	String textDate;// 日期
	String textWeek;// 星期几
	
	//没有班级时，显示列表为空提示信息
	View ll_no_items_listed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kqjl_teacher);
		context = this;
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		initView();
		// textDate = DateUtil.format(DateUtil.StringToDate(DateUtil
		// .getFromatCurrentDate("yyyy-MM-dd")), "yyyy-MM-dd");
		textDate = DateUtil.getyyyyMMddCurrentTime();
		textWeek = DateUtil.getWeekDay(textDate, "yyyy-MM-dd" );
		tv_my_attence_teacher_texttime.setText(textDate);
		tv_my_attence_teacher_textweek.setText(textWeek);
		new AttenceListTask().execute(AppContext.getApp().getToken(), textDate);
		// 注册通知 刷新列表
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_ATTENCE_LIST");
		registerReceiver(broadcastReceiver, filter);
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			new AttenceListTask().execute(AppContext.getApp().getToken(),
					textDate);
		}
	};

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zxdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);

		titleBarView.setTitleText(R.string.text_text_attence);

		
		
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		ll_activity_kqjl_teacher = (ListView) findViewById(R.id.ll_activity_kqjl_teacher);
		iv_my_attence_teacher_left = (ImageView) findViewById(R.id.iv_my_attence_teacher_left);
		iv_my_attence_teacher_right = (ImageView) findViewById(R.id.iv_my_attence_teacher_right);
		iv_my_attence_teacher_left.setOnClickListener(this);
		iv_my_attence_teacher_right.setOnClickListener(this);
		tv_my_attence_teacher_texttime = (TextView) findViewById(R.id.tv_my_attence_teacher_texttime);
		tv_my_attence_teacher_textweek = (TextView) findViewById(R.id.tv_my_attence_teacher_textweek);

		//没有数据显示
		ll_no_items_listed = findViewById(R.id.ll_no_items_listed);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	/**
	 * 获取考勤列表 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class AttenceListTask extends AsyncTask<String, Void, AttenceList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected AttenceList doInBackground(String... params) {

			try {
				attenceList = AppContext.getApp().getAttenceListTeacherByDay(
						params[0], params[1]);

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e=e;
				e.printStackTrace();
				attenceList = null;
			}
			return attenceList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(AttenceList result) {

			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (result != null) {
				if (!result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
				//获得服务器时间戳，并转化为日期格式
				String sysDate = "";
				if(result.getTimestamp() > 0){
					sysDate = DateUtil.Date2String(new Date(result.getTimestamp()), "yyyy-MM-dd");
				}
				//如果传入参数时间比系统日期大，以服务器时间进行校正
				if(!sysDate.equals("") && sysDate.compareTo(textDate) < 0){
					textDate = sysDate;
					result.setMaxDay(true);
				}
				textWeek = DateUtil.getWeekDay(textDate, "yyyy-MM-dd");
				tv_my_attence_teacher_texttime.setText(textDate);
				tv_my_attence_teacher_textweek.setText(textWeek);
				// 控制是否显示切换日期按钮
				if (result.isMaxDay()) {
					iv_my_attence_teacher_right
							.setVisibility(View.INVISIBLE);
				} else {
					iv_my_attence_teacher_right.setVisibility(View.VISIBLE);
				}
				if (result.isMinDay()) {
					iv_my_attence_teacher_left
							.setVisibility(View.INVISIBLE);
				} else {
					iv_my_attence_teacher_left.setVisibility(View.VISIBLE);
				}
				
				if (result.getAttenceList()!=null&&result.getAttenceList().size()>0) {
					listViewAttenceTeacherAdapter = new ListViewAttenceTeacherAdapter(
							getApplicationContext(), result.getAttenceList(),
							textDate);
					// 得到实际的ListView
					ll_activity_kqjl_teacher
							.setAdapter(listViewAttenceTeacherAdapter);
					ll_no_items_listed.setVisibility(View.GONE);
				}else{
					//显示没有可以考勤的班级
					ll_no_items_listed.setVisibility(View.VISIBLE);
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

		switch (v.getId()) {
		case R.id.iv_my_attence_teacher_left:
			showProgressDialog(context, "",
					getResources().getString(R.string.load_ing));
			textDate = DateUtil.format(
					DateUtil.getDateAfter(
							DateUtil.strToDate(textDate, "yyyy-MM-dd"), -1),
					"yyyy-MM-dd");
			new AttenceListTask().execute(AppContext.getApp().getToken(),
					textDate);
			break;
		case R.id.iv_my_attence_teacher_right:
			showProgressDialog(context, "",
					getResources().getString(R.string.load_ing));
			textDate = DateUtil.format(
					DateUtil.getDateAfter(
							DateUtil.strToDate(textDate, "yyyy-MM-dd"), 1),
					"yyyy-MM-dd");
			new AttenceListTask().execute(AppContext.getApp().getToken(),
					textDate);
			break;

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		context.unregisterReceiver(broadcastReceiver);
	}
}
