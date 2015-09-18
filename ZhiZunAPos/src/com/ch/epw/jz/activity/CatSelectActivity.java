package com.ch.epw.jz.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.GridViewCatAdapter;
import com.zhizun.pos.adapter.ListViewCatListAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.BabyInfoDetail;
import com.zhizun.pos.bean.CatList;
import com.zhizun.pos.bean.InterestPri;
import com.zhizun.pos.bean.InterestSec;

public class CatSelectActivity extends BaseActivity {
	private static final String TAG = CatSelectActivity.class.getName();
	// 主activity

	private TitleBarView titleBarView;
	ListView ll_myepei_babyinfo_catlist;
	ListViewCatListAdapter listViewCatListAdapter;
	CatList catList;
	ArrayList<InterestPri> listPri;
	List<InterestSec> listSec;
	Context context;

	GridView gv_myepei_babyinfo_catdetail;
	GridViewCatAdapter gridViewCatAdapter;
	BabyInfoDetail babyInfoDetail;

	ImageView title_iv_left;
	TextView title_tv_text;
	TextView title_tv_sure;
	int catsize = 0;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				int catnum = msg.arg1;

				title_tv_text.setText("（" + catnum + "/8）");
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei_babyinfo_catselect);
		context = this;
		babyInfoDetail = (BabyInfoDetail) getIntent().getSerializableExtra(
				"babyInfoDetail");
		initView();
	}

	private void initView() {

		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		title_tv_text = (TextView) findViewById(R.id.title_tv_text);
		title_tv_sure = (TextView) findViewById(R.id.title_tv_sure);

		title_iv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		title_tv_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				intent.putExtra("listPri", listPri);
				CatSelectActivity.this.setResult(
						Constant.REQUSTCONDE_BABYINFO_CATLIST, intent);
				CatSelectActivity.this.finish();
				backAnim();
			}
		});
		ll_myepei_babyinfo_catlist = (ListView) findViewById(R.id.ll_myepei_babyinfo_catlist);
		ll_myepei_babyinfo_catlist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						listSec = listPri.get(position).getInterestSecList();
						smoothScroollListView(position);
						listViewCatListAdapter.setViewBackGround(position);
						listViewCatListAdapter.notifyDataSetChanged();

					}
				});
		gv_myepei_babyinfo_catdetail = (GridView) findViewById(R.id.gv_myepei_babyinfo_catdetail);
		gv_myepei_babyinfo_catdetail
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						if (listSec.get(position).getIsSelected()) {

							catsize = catsize - 1;
							listSec.get(position).setIsSelected(false);
						} else {
							if (catsize > 7) {
								UIHelper.ToastMessage(context, "兴趣爱好最多选择8个");
								return;
							} else {
								catsize = catsize + 1;
							}
							listSec.get(position).setIsSelected(true);
						}

						Message msg = handler.obtainMessage();
						msg.what = 1;
						msg.arg1 = catsize;
						Log.i(TAG, "catNum=" + catsize);
						handler.sendMessage(msg);
						gridViewCatAdapter.notifyDataSetChanged();
					}
				});
		new GetCatListTask().execute(AppContext.getApp().getToken());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	/**
	 * 获得兴趣爱好分组列表 创建人：李林中 创建日期：2015-2-25 上午10:09:45 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetCatListTask extends AsyncTask<String, Void, CatList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected CatList doInBackground(String... params) {

			try {
				catList = AppContext.getApp().getCat(params[0]);

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e=e;
				e.printStackTrace();
				catList = null;
			}
			return catList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(CatList result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {
					listPri = (ArrayList<InterestPri>) result
							.getInterestPriList();
					for (InterestPri pri : listPri) {
						for (InterestPri pri2 : babyInfoDetail
								.getInterestPriList()) {
							if (pri.getCatId().equals(pri2.getCatId())) {
								for (InterestSec sec : pri.getInterestSecList()) {
									for (InterestSec sec2 : pri2
											.getInterestSecList()) {
										if (sec.getItemId().equals(
												sec2.getItemId())) {
											sec.setIsSelected(true);
										}
									}
								}
							}
						}
					}
					for (InterestPri interestPri : listPri) {
						for (InterestSec interestSec : interestPri
								.getInterestSecList()) {
							if (interestSec.getIsSelected()) {
								catsize = catsize + 1;
							}
						}
					}
					title_tv_text.setText("（" + catsize + "/8）");
					// 初始给第一个列表赋值
					listViewCatListAdapter = new ListViewCatListAdapter(
							context, listPri);
					ll_myepei_babyinfo_catlist
							.setAdapter(listViewCatListAdapter);
					listViewCatListAdapter.setViewBackGround(0);
					listViewCatListAdapter.notifyDataSetChanged();
					// 初始给第一个列表赋值
					listSec = listPri.get(0).getInterestSecList();
					gridViewCatAdapter = new GridViewCatAdapter(context,
							listSec);
					gv_myepei_babyinfo_catdetail.setAdapter(gridViewCatAdapter);
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
	 * listView scroll
	 * 
	 * @param position
	 */
	@SuppressLint("NewApi")
	public void smoothScroollListView(int position) {
		if (Build.VERSION.SDK_INT >= 21) {
			ll_myepei_babyinfo_catlist.setSelectionFromTop(position, 0);
		} else if (Build.VERSION.SDK_INT >= 11) {
			ll_myepei_babyinfo_catlist.smoothScrollToPositionFromTop(position,
					0, 500);
		} else if (Build.VERSION.SDK_INT >= 8) {
			int firstVisible = ll_myepei_babyinfo_catlist
					.getFirstVisiblePosition();
			int lastVisible = ll_myepei_babyinfo_catlist
					.getLastVisiblePosition();

			Log.i(TAG, " firstVisible " + firstVisible + " lastVisible "
					+ lastVisible + "  position " + position);

			if (position < firstVisible) {
				ll_myepei_babyinfo_catlist.smoothScrollToPosition(position);
			} else {
				if (firstVisible == 0) {
					ll_myepei_babyinfo_catlist.smoothScrollToPosition(position
							+ lastVisible - firstVisible);
				} else {
					ll_myepei_babyinfo_catlist.smoothScrollToPosition(position
							+ lastVisible - firstVisible - 1);
				}
			}
		} else {
			ll_myepei_babyinfo_catlist.setSelection(position);
		}

		gridViewCatAdapter = new GridViewCatAdapter(context, listSec);
		gv_myepei_babyinfo_catdetail.setAdapter(gridViewCatAdapter);
	}
}