package com.ch.epw.js.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ch.epw.task.GetNewCommentsCountTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.NewCommonCommentsActivity;
import com.zhizun.pos.adapter.ListViewCommentsAdapter;
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.DynamicTeacherList;

/**
 * 在校动态 教师端 创建人：李林中 创建日期：2014-12-15 上午10:11:13 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class InSchoolDynamicTeacherActivity extends BaseActivity {

	protected static final String TAG = InSchoolDynamicTeacherActivity.class
			.getName();
	private TitleBarView titleBarView;
	Context context;
	Integer dynamicCount;// 在校动态数量 共用字段
	DynamicTeacherList dynamicTeacherList;
	List<DynamicTeacher> list;
	DynamicTeacher dynamicTeacher;
	ListViewDynamicTeacherAdapter dynamicTeacherAdapter;
	ListViewCommentsAdapter commentsAdapter;
	protected static final int FLAG_SENDDYNAMIC_REQUESTCODE_STRING = 1;
	FrameLayout fl_common_zxhf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zxdt_teacher);
		context = this;

		initView();
		new GetNewCommentsCountTask(context, newReplyMsgBadgeView).execute(
				AppContext.getApp().getToken(), Constant.COMMNETTYPE_ZXDT);
		content();
	}

	@Override
	protected void onResume() {
		super.onResume();
		newReplyMsgBadgeView.setTargetView(fl_common_zxhf);
		new GetNewCommentsCountTask(context, newReplyMsgBadgeView).execute(
				AppContext.getApp().getToken(), Constant.COMMNETTYPE_ZXDT);
	}

	private void content() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件

		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		// listview每行间隔
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(25);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new DynamicListTask().execute(1 + "", Constant.LOADDATACOUNT
						+ "");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				int page = (int) (Math.ceil(dynamicCount
						/ (double) Constant.LOADDATACOUNT));

				mCurPage = mCurPage + 1;
				Log.i(TAG, "mCurPage=" + mCurPage);
				if (mCurPage > page) {
					mCurPage = page;
					hasMoreData = false;
					mPullListView.setHasMoreData(hasMoreData);
					return;
				}

				Log.i(TAG, "mCurPage2=" + mCurPage);
				Log.i(TAG, "dynamicCount=" + dynamicCount);
				Log.i(TAG, "page=" + page);
				new DynamicListTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zxdt_teacher);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.in_school_dynamic);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getBtnRight().setBackgroundResource(R.drawable.fadongtai);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent fsdtIntent = new Intent(context,
						SendDynamicActivity.class);
				startActivityForResult(fsdtIntent,
						FLAG_SENDDYNAMIC_REQUESTCODE_STRING);
				intoAnim();

			}
		});
		// 下拉刷新控件
		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_activity_zxdt_teacher);
		fl_common_zxhf = (FrameLayout) findViewById(R.id.fl_common_zxhf);
		fl_common_zxhf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						NewCommonCommentsActivity.class);
				intent.putExtra("type", Constant.COMMNETTYPE_ZXDT);
				startActivity(intent);
				intoAnim();
			}
		});
		newReplyMsgBadgeView = new BadgeView(context);
		newReplyMsgBadgeView.setBadgeGravity(Gravity.RIGHT
				| Gravity.CENTER_VERTICAL);
		newReplyMsgBadgeView.setBadgeMargin(0, 0, 2, 0);
		newReplyMsgBadgeView.setTargetView(fl_common_zxhf);

		// 没有数据显示
		// ll_no_items_listed = (LinearLayout)
		// findViewById(R.id.ll_no_items_listed);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	/**
	 * 获取动态 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class DynamicListTask extends
			AsyncTask<String, Void, DynamicTeacherList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected DynamicTeacherList doInBackground(String... params) {

			try {
				dynamicTeacherList = AppContext.getApp().getTeacherMyDynamic(
						params[0], params[1]);

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				dynamicTeacherList = null;
			}
			return dynamicTeacherList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(DynamicTeacherList result) {

			super.onPostExecute(result);

			if (result != null) {

				if (result.getStatus().equals("0")) {
					dynamicCount = Integer.parseInt(dynamicTeacherList
							.getDataCount());
					if (list != null && list.size() > 0) {

						list.addAll(result.getDynamicTeacherList());
					} else {
						list = result.getDynamicTeacherList();
					}
					if (dynamicCount == 0) {
						mPullListView.setHasData(false);
						// listview每行间隔
						mListView.setDividerHeight(0);

						dynamicTeacherAdapter = new ListViewDynamicTeacherAdapter(
								InSchoolDynamicTeacherActivity.this, list);
						// 得到实际的ListView
						mListView.setAdapter(dynamicTeacherAdapter);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					} else if (dynamicCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (list.size() <= Constant.LOADDATACOUNT) {
						dynamicTeacherAdapter = new ListViewDynamicTeacherAdapter(
								InSchoolDynamicTeacherActivity.this, list);
						// 得到实际的ListView
						mListView.setAdapter(dynamicTeacherAdapter);
					} else {
						dynamicTeacherAdapter.notifyDataSetChanged();
					}

				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
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

}
