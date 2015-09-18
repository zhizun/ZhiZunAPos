package com.zhizun.pos.activity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.adapter.ListViewPrizedParticAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MyPrizedParticipant;
import com.zhizun.pos.bean.MyPrizedShareList;
/**
 * 分享有奖	参与详情列表
 * @author lilinzhong
 *
 */
public class PrizedParticipationActivity extends BaseActivity {
	private TitleBarView titleBarView;
	Integer dataCount;
	private String eventId;
	private MyPrizedParticipant particList;
	private Context context;
	private ArrayList<MyPrizedShareList> list;
	private ListViewPrizedParticAdapter listViewPrizedParticAdapter;
	private TextView tv_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prized_partic);
		context = this;
		eventId =getIntent().getStringExtra("eventId");
		initView();
		content();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_prized_partic);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_prized_partic);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		tv_text=(TextView) findViewById(R.id.tv_text);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		mPullListView=(PullToRefreshListView) findViewById(R.id.lv_prized_partic_list);
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
				mListView.setOnItemClickListener(null);
				mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						list = null;
						mCurPage = 1;
						hasMoreData = true;
						mPullListView.setHasData(true);
							new QueryOutParticListTask().execute(eventId);

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						int page = (int) (Math.ceil(dataCount
								/ (double) Constant.LOADDATACOUNT));

						mCurPage = mCurPage + 1;
						Log.i(TAG, "mCurPage=" + mCurPage);
						if (mCurPage > page) {
							mCurPage = page;
							hasMoreData = false;
							mPullListView.setHasMoreData(hasMoreData);
							return;
						}
							new QueryOutParticListTask().execute(eventId);
					}
				});
				setLastUpdateTime();
				// 进入自动刷新
				mPullListView.doPullRefreshing(true, 500);
				// 下拉刷新和上啦加载代码结束=------
	}
	/**
	 * 获得分享列表
	 */
	private class QueryOutParticListTask extends
			AsyncTask<String, Void, MyPrizedParticipant> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected MyPrizedParticipant doInBackground(String... params) {

			try {
				particList = AppContext.getApp().queryPartic(params[0]);

			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				particList = null;
			}
			return particList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(MyPrizedParticipant result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = particList.getShareList().size();
					if (dataCount == 0) {
						mPullListView.setHasData(false);
					} else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getShareList());
						listViewPrizedParticAdapter.notifyDataSetChanged();
					} else {
						list = result.getShareList();
						
						listViewPrizedParticAdapter = new ListViewPrizedParticAdapter(
								getApplicationContext(), list);
						// 得到实际的ListView
						mListView.setAdapter(listViewPrizedParticAdapter);
					}
					if (null!=particList.getMktgEventAwardRec()) {
						tv_text.setText("您已经在"+particList.getMktgEventAwardRec().getReceiverTime()+"领取奖品");
					}else {
						if (null!=particList.getValidCount()&&!particList.getValidCount().equals("")) {
							int validShareCount = Integer.valueOf(particList.getValidCount());
							if (null!=particList.getLeastShareNum()&&!particList.getLeastShareNum().equals("")) {
								int atLeastShareNum = Integer.valueOf(particList.getLeastShareNum());
								if (validShareCount==0) {
									tv_text.setText("您还没有成功分享，继续分享达到"+atLeastShareNum+"次就可以领取奖品啦！");
								} else if(validShareCount < atLeastShareNum){
									tv_text.setText("您已经成功分享了"+validShareCount
											+"次，继续分享达到"+atLeastShareNum+"次就可以领取奖品啦！");
								}else {
									tv_text.setText("恭喜您，已经达到了分享次数，可以到机构领取奖品啦！");
								}
							}
						}
					}
					mPullListView.onPullDownRefreshComplete();
					mPullListView.onPullUpRefreshComplete();
					mPullListView.setHasMoreData(hasMoreData);
					setLastUpdateTime();
				} else {
					if (result.getStatus().equals("1003")) {
						UIHelper.ToastMessage(context,
								result.getStatusMessage() + ",请重新登录！");
						Intent intent = new Intent(context, LoginActivity.class);
						startActivity(intent);
						PrizedParticipationActivity.this.finish();
						intoAnim();
						return;
					}
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
		}
	}
}
