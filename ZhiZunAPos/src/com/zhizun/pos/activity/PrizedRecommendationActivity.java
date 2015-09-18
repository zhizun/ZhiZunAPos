package com.zhizun.pos.activity;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
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
import com.zhizun.pos.adapter.ListViewPrizedRecommendAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MyPrizedRecommendationBean;
import com.zhizun.pos.bean.MyRecommendationPicker;
import com.zhizun.pos.bean.MyepePrizeBean;
import com.zhizun.pos.bean.RecommendationData;
/**
 * 推荐有奖 参与详情列表
 * @author lilinzhong
 *
 */
public class PrizedRecommendationActivity extends BaseActivity {
	private TitleBarView titleBarView;
	Integer dataCount;
	private String eventId;
	private MyPrizedRecommendationBean particList;
	private Context context;
	private ArrayList<RecommendationData> list;
	private ListViewPrizedRecommendAdapter listViewPrizedRecommendAdapter;
	private TextView tv_text;
	private String introducerBonus;
	private Intent inten;
	private LinearLayout lb_title;
	private LinearLayout lb_youhui;
	private View view_f;
	private String erAwardNum;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prized_recommendation);
		context = this;
		inten=getIntent();
		eventId =inten.getStringExtra("eventId");
		type =inten.getStringExtra("type");
		introducerBonus =inten.getStringExtra("introducerBonus");
		erAwardNum =inten.getStringExtra("perAwardNum");
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
		lb_title=(LinearLayout) findViewById(R.id.lb_title);
		lb_youhui=(LinearLayout) findViewById(R.id.lb_youhui);
		view_f=findViewById(R.id.view_f);
		
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
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						if (null!=particList&&!particList.equals("")&&type.equals(Constant.RECOMMEND_AWARD)) {
							ArrayList<MyRecommendationPicker> myRecommendationPicker=particList.getRecommendationDataList().get(position).getPickerList();
							if (null!=myRecommendationPicker && !myRecommendationPicker.equals("")) {
								Intent intent =new Intent(PrizedRecommendationActivity.this,RecommendationPickerListActivity.class);
								intent.putExtra("pickerList",myRecommendationPicker);
								startActivity(intent);
							}
						}
						
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
						new QueryOutParticListTask().execute(eventId,Constant.LOADDATACOUNT + "",mCurPage+"");

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
							new QueryOutParticListTask().execute(eventId,Constant.LOADDATACOUNT + "",mCurPage+"");
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
			AsyncTask<String, Void, MyPrizedRecommendationBean> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected MyPrizedRecommendationBean doInBackground(String... params) {

			try {
				particList = AppContext.getApp().queryRecommendation(params[0], params[1], params[2]);

			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				particList = null;
			}
			return particList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(MyPrizedRecommendationBean result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = particList.getRecommendationDataList().size();
					if (dataCount == 0) {
						mPullListView.setHasData(false);
					} else if (dataCount == 1) {
						if (type.equals(Constant.RECOMMEND_AWARD)) {
							mListView.setDivider(null);
							mListView.setDividerHeight(0);
						}
					}
					if (dataCount>0) {
						if (type.equals(Constant.RECOMMEND_AWARD)) {
							lb_title.setVisibility(View.VISIBLE);
							view_f.setVisibility(View.VISIBLE);
						}else if(particList.getRecommendationDataList().get(0).getPickerList().size()==0) {
							mPullListView.setHasData(false);
							lb_youhui.setVisibility(View.GONE);
							view_f.setVisibility(View.GONE);
						}else{
							lb_youhui.setVisibility(View.VISIBLE);
							view_f.setVisibility(View.VISIBLE);
						}
					}
					if (list != null && list.size() > 0) {
						list.addAll(result.getRecommendationDataList());
						listViewPrizedRecommendAdapter.notifyDataSetChanged();
					} else {
						list = result.getRecommendationDataList();
						
						listViewPrizedRecommendAdapter = new ListViewPrizedRecommendAdapter(
								getApplicationContext(), list,type);
						// 得到实际的ListView
						mListView.setAdapter(listViewPrizedRecommendAdapter);
					}
					int userRecommendNum=0;
					try {
						userRecommendNum = Integer.valueOf(particList.getUserRecommendNum());
					} catch (Exception e) {
//						e.printStackTrace();
					}
					int perAwardNum=1;
					try {
					perAwardNum = Integer.valueOf(erAwardNum);
					} catch (NumberFormatException e) {
//					e.printStackTrace();
				}
					if (type.equals(Constant.RECOMMEND_AWARD)) {
						if (userRecommendNum==0) {			
							tv_text.setText("您还没有获得任何奖励");
						} else{	
							if (perAwardNum==0) {
								tv_text.setText("已获得："+userRecommendNum+"*"+introducerBonus);
							}else {
								int gotPrize = (userRecommendNum < perAwardNum) ? userRecommendNum :perAwardNum;			
								tv_text.setText("已获得："+gotPrize+"*"+introducerBonus);
							}
						}
					}else {
						tv_text.setVisibility(View.GONE);
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
						PrizedRecommendationActivity.this.finish();
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
