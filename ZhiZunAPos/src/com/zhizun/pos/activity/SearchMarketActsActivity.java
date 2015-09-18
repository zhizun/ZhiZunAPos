package com.zhizun.pos.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhizun.pos.R;
import com.zhizun.pos.activity.CourseListViewActivity.SearchParaEnum;
import com.zhizun.pos.adapter.ListViewMarketingAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.MarketingList;
import com.zhizun.pos.bean.MyepePrizeBean;
import com.ch.epw.helper.MyPullToRefreshListHelper;
import com.ch.epw.task.QueryCourseListTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

/**
 * 营销活动activity 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SearchMarketActsActivity extends BaseActivity {
	protected static final String TAG = SearchMarketActsActivity.class
			.getName();
	Context context;
	TitleBarView titleBarView;
	ArrayList<MyepePrizeBean> listItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_marketing_activities);
		context = this;
		initView();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void initView() {
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_search_acts);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_marketing_activities);
		listItems = new ArrayList<MyepePrizeBean>();

		// 将Activity、mPullListView、listItems绑定
		final MyPullToRefreshListHelper mvHelper = new MyPullToRefreshListHelper(
				context, mPullListView, listItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(25);
		mListView.setAdapter(new ListViewMarketingAdapter(context, listItems));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object item = mListView.getAdapter().getItem(position);
				if(item != null){
					MyepePrizeBean prize = (MyepePrizeBean)item;
					Intent intent = new Intent(context, PrizedEventIntroduceActivity.class);
					intent.putExtra("eventId", prize.getEventId());
					startActivity(intent);
					intoAnim();
				}
			}
		});

		TaskCallBack getDataCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				MarketingList marketList = (MarketingList) result;
				if (marketList != null && "0".equals(marketList.getStatus())) {
					mvHelper.setPageCount(marketList.getDataCount());
					if(Integer.parseInt(marketList.getDataCount()) > 0){
						listItems.addAll(marketList.getPrizeList());
					}
				}
				// 必须在每次task执行结束调用mvController.refreshEnd()
				mvHelper.refreshEnd();
			}
		};

		String[] searchParams = new String[SearchParaEnum.values().length];
		searchParams[SearchParaEnum.searchType.ordinal()]="2";	// searchType = 2 找活动
		searchParams[SearchParaEnum.sort.ordinal()] = "";
		mvHelper.setGetDataCallBack(QueryCourseListTask.class, searchParams,
				getDataCallBack);
		mvHelper.readyForRefresh();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
