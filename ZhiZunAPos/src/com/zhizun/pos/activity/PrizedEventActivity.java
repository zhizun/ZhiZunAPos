package com.zhizun.pos.activity;

import java.util.ArrayList;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.adapter.ListViewPrizedAdapter;
import com.zhizun.pos.adapter.ListViewRecommendationAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MyepePrizeBean;
import com.zhizun.pos.bean.MyepePrizedBeanList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 有奖活动	分享有奖，推荐有奖
 * @author 李林中
 *
 */
public class PrizedEventActivity extends BaseActivity {
	private TitleBarView titleBarView;
	private Button btn_prizedevent_title_bar_receive;
	private Button btn_prizedevent_title_bar_send;
	private Button btn_prizedevent_title_bar_preferential;
	Integer noticeBoxTag = 0;// 通知标志 0代表分享有奖，1代表推荐有奖
	private ArrayList<MyepePrizeBean> list;
	Integer dataCount;
	String status = "";// 0 未发送； 1： 已发送； 不传：所有
	private MyepePrizedBeanList prizedList;
	Context context;
	private MyBaseAdapter listViewPrizedAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_js_prizedevent);
		context = this;
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_prized_event);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_prized_event);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		mPullListView=(PullToRefreshListView) findViewById(R.id.lv_prized_event_list);
		btn_prizedevent_title_bar_receive=(Button) findViewById(R.id.btn_prizedevent_title_bar_receive);
		btn_prizedevent_title_bar_send=(Button) findViewById(R.id.btn_prizedevent_title_bar_send);
		btn_prizedevent_title_bar_preferential=(Button) findViewById(R.id.btn_prizedevent_title_bar_preferential);
		
		btn_prizedevent_title_bar_receive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btn_prizedevent_title_bar_receive.isEnabled()) {
					btn_prizedevent_title_bar_receive
							.setEnabled(false);
					btn_prizedevent_title_bar_send.setEnabled(true);
					btn_prizedevent_title_bar_preferential.setEnabled(true);
					noticeBoxTag = 0;
					list = null;
					content();
				}
			}
		});
		btn_prizedevent_title_bar_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (btn_prizedevent_title_bar_send.isEnabled()) {
					btn_prizedevent_title_bar_send.setEnabled(false);
					btn_prizedevent_title_bar_receive.setEnabled(true);
					btn_prizedevent_title_bar_preferential.setEnabled(true);
					// ll_my_notice_teacher_menu
					// .setVisibility(View.VISIBLE);
					noticeBoxTag = 1;
					list = null;
					content();
				}
			}
		});
		btn_prizedevent_title_bar_preferential.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (btn_prizedevent_title_bar_preferential.isEnabled()) {
					btn_prizedevent_title_bar_preferential.setEnabled(false);
					btn_prizedevent_title_bar_receive.setEnabled(true);
					btn_prizedevent_title_bar_send.setEnabled(true);
					noticeBoxTag = 2;
					list = null;
					content();
				}
			}
		});
		btn_prizedevent_title_bar_receive.performClick();//默认点击
		content();
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
						switch (noticeBoxTag) {
						case 0:// 分享
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "",Constant.SHARE_AWARD);
							break;
						case 1:// 推荐
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "", Constant.RECOMMEND_AWARD);
							break;
						case 2:// 优惠活动
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "", Constant.PRIZED_PREFERENTIAL);
							break;
						}

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

						switch (noticeBoxTag) {
						case 0:// 分享
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "",Constant.SHARE_AWARD);
							break;
						case 1:// 推荐
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "",Constant.RECOMMEND_AWARD);
							break;
						case 2:// 优惠活动
							new QueryOutShareListTask().execute(mCurPage + "",
									Constant.LOADDATACOUNT + "",Constant.PRIZED_PREFERENTIAL);
							break;
						}
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
	private class QueryOutShareListTask extends
			AsyncTask<String, Void, MyepePrizedBeanList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected MyepePrizedBeanList doInBackground(String... params) {

			try {
				prizedList = AppContext.getApp().queryShareList(params[0],
						params[1],params[2]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				prizedList = null;
			}
			return prizedList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(MyepePrizedBeanList result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(prizedList.getDataCount());
					if (dataCount==0) {
						mPullListView.setHasData(false);
					}
					if (dataCount>0) {
						mListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(
									AdapterView<?> parent, View view,
									int position, long id) {
								if (list.size()>0) {
									Intent intent_receivebox = new Intent(
											context,
											PrizedEventIntroduceActivity.class);
									intent_receivebox.putExtra(
											"eventId", list.get(position).getEventId());
									startActivity(intent_receivebox);
									intoAnim();
								}

							}
						});
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getPrizeList());
						listViewPrizedAdapter.notifyDataSetChanged();
					} else {
						list = result.getPrizeList();
						if(result.getMktType().equals("0")){
							listViewPrizedAdapter = new ListViewPrizedAdapter(
									getApplicationContext(), list);
						} else{
							listViewPrizedAdapter = new ListViewRecommendationAdapter(
									getApplicationContext(), list,result.getMktType());
						}
						// 得到实际的ListView
						mListView.setAdapter(listViewPrizedAdapter);
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
						PrizedEventActivity.this.finish();
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




