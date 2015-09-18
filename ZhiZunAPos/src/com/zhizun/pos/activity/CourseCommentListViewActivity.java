package com.zhizun.pos.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ch.epw.helper.MyPullToRefreshListHelper;
import com.ch.epw.task.QueryCourseCommentListTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsCommentAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.FriendsCommentBean;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.UserLogin;

/**
 * 课程评价列表
 *
 * ===================================================
 */
public class CourseCommentListViewActivity extends BaseActivity {
	protected static final String TAG = CourseCommentListViewActivity.class
			.getName();
	Context context;
	TitleBarView titleBarView;
	ArrayList<FriendsCommentListBean> listItems;
	String orgId;
	String courId;
	private String userId;

	String activityTag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_comment_activity);
		context = this;
		orgId = getIntent().getStringExtra("ownOrgId");
		courId = getIntent().getStringExtra("courId");

		activityTag = getIntent().getStringExtra("activityTag");
		if (activityTag == null) {
			activityTag = "2";
		}

		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin != null && userLogin.getUserInfo() != null) {
			userId = userLogin.getUserInfo().getUserId();
		}
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
		titleBarView.setTitleText(R.string.title_text_course_comment);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_course_comment_list);
		listItems = new ArrayList<FriendsCommentListBean>();

		// 将Activity、mPullListView、listItems绑定
		final MyPullToRefreshListHelper mvHelper = new MyPullToRefreshListHelper(
				context, mPullListView, listItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(20);
		mListView.setAdapter(new FriendsCommentAdapter(context, listItems, activityTag));

		TaskCallBack getDataCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				FriendsCommentBean commentList = (FriendsCommentBean) result;
				if (commentList != null && "0".equals(commentList.getStatus())) {
					mvHelper.setPageCount(commentList.getCount());
					if(Integer.parseInt(commentList.getCount()) > 0){
						listItems.addAll(commentList.getFriendsCommentListBean());
					}
				}
				// 必须在每次task执行结束调用mvController.refreshEnd()
				mvHelper.refreshEnd();
			}
		};

		mvHelper.setGetDataCallBack(QueryCourseCommentListTask.class, new String[]{orgId, courId,userId},
				getDataCallBack);
		mvHelper.readyForRefresh();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
