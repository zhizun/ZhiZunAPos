package com.ch.epw.jz.activity;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewBabyOrgAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.BabyInfoDetail;
import com.zhizun.pos.bean.Org;
import com.zhizun.pos.bean.Result;

public class BabyinfoOrgListActivity extends BaseActivity {
	private static final String TAG = BabyinfoOrgListActivity.class.getName();

	private TitleBarView titleBarView;

	SwipeListView sl_lv_list;
	Context context;
	BabyInfoDetail babyInfoDetail;
	ListViewBabyOrgAdapter listViewBabyOrgAdapter;
	List<Org> listOrgs;
	private int initListOrgsCount = 0;	//进入界面时的机构数，用于区分是否删除过
	public static int deviceWidth;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei_babyinfo_orglist);
		context = this;
		babyInfoDetail = (BabyInfoDetail) getIntent().getSerializableExtra(
				"babyInfoDetail");
		listOrgs = babyInfoDetail.getOrgList();
		initListOrgsCount = listOrgs == null? 0 : listOrgs.size();
		deviceWidth = getDeviceWidth();
		initView();
		reload();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_babyinfo_orglist);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_babyorg);

		
		
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onBackPressed();
			}
		});
		sl_lv_list = (SwipeListView) findViewById(R.id.sl_lv_list);
		sl_lv_list
				.setSwipeListViewListener(new TestBaseSwipeListViewListener());
		listViewBabyOrgAdapter = new ListViewBabyOrgAdapter(context, listOrgs,
				sl_lv_list);
		sl_lv_list.setAdapter(listViewBabyOrgAdapter);
	}

	private int getDeviceWidth() {
		return getResources().getDisplayMetrics().widthPixels;
	}

	@Override
	public void onBackPressed() {
		forBabyOrgListActivityReturn();
	}
	
	private void forBabyOrgListActivityReturn(){
		if(initListOrgsCount != listOrgs.size()) {
			BabyinfoOrgListActivity.this.setResult(
					Constant.REQUSTCONDE_BABYINFO_CATLIST, getIntent());
		}
		BabyinfoOrgListActivity.this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	class TestBaseSwipeListViewListener extends BaseSwipeListViewListener {

		@Override
		public void onClickFrontView(int position) {
			super.onClickFrontView(position);
			Toast.makeText(getApplicationContext(),
					listOrgs.get(position).getName(), Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onDismiss(int[] reverseSortedPositions) {

			for (int position : reverseSortedPositions) {
				new exitOrgTask(position).execute(AppContext.getApp()
						.getToken(), babyInfoDetail.getChildId(),
						listOrgs.get(position).getOrgId());
			}

		}
	}

	/**
	 * 退出机构 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class exitOrgTask extends AsyncTask<String, Void, Result> {
		int position;
		AppException e;
		public exitOrgTask(int position) {
			super();
			this.position = position;
		}

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected Result doInBackground(String... params) {
			Result result = null;
			try {
				result = AppContext.getApp().exitOrg(params[0], params[1],
						params[2]);
			} catch (AppException e) {
				this.e=e;
				e.printStackTrace();
			}
			return result;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "已退出"+listOrgs.get(position).getName());
					listOrgs.remove(position);
					//判断剩余是否有机构
					if(listOrgs.size() > 0 ){
						listViewBabyOrgAdapter.notifyDataSetChanged();
					}else{
						forBabyOrgListActivityReturn();
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

	private void reload() {
		sl_lv_list.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		sl_lv_list.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
		// mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
		sl_lv_list.setOffsetLeft(deviceWidth * 3 / 4);
		// mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
		sl_lv_list.setAnimationTime(0);
		sl_lv_list.setSwipeOpenOnLongPress(false);
	}
}