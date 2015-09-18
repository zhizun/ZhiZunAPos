package com.zhizun.pos.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.ch.epw.helper.MyPullToRefreshListHelper;
import com.ch.epw.task.FriendsCommeTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.LocationUtils;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsCommentAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.FriendsCommentBean;
import com.zhizun.pos.bean.FriendsCommentListBean;

/**
 * 我的评价
 * @author lilinzhong
 *
 * 2015-8-21上午9:47:47
 */
public class FriendsMyCommentActivity extends BaseActivity {

	public static final int REQUEST_START_COMMENT_EDIT = 1;
	private TitleBarView titleBarView;
	private ArrayList<FriendsCommentListBean> listItems;
	private MyPullToRefreshListHelper mvHelper;
	private double latitude;
	private double longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_my_comment);
		BDLocation location=LocationUtils.getLastKnownLocation();
		if (location!=null) {
			latitude=location.getLatitude();
			longitude=location.getLongitude();
		}
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_friends_mycomment);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_my_course_comment_view);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		mPullListView=(PullToRefreshListView)findViewById(R.id.ll_friends_mycomment);
		listItems = new ArrayList<FriendsCommentListBean>();
		// 将Activity、mPullListView、listItems绑定
		mvHelper = new MyPullToRefreshListHelper(
				this, mPullListView, listItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.drawable.itembg));
		mListView.setDividerHeight(20);
		mListView.setAdapter(new FriendsCommentAdapter(this, listItems,"1"));
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		final TaskCallBack getDataCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				FriendsCommentBean friendsCommentBean = (FriendsCommentBean) result;
				if (friendsCommentBean != null && "0".equals(friendsCommentBean.getStatus())) {
					mvHelper.setPageCount(friendsCommentBean.getCount());
					if (Integer.parseInt(friendsCommentBean.getCount()) > 0) {
						listItems.addAll(friendsCommentBean.getFriendsCommentListBean());
					}
				}
				// 必须在每次task执行结束调用mvController.refreshEnd()
				mvHelper.refreshEnd();
			}
		};
		mvHelper.setGetDataCallBack(FriendsCommeTask.class, new String[]{String.valueOf(latitude),String.valueOf(longitude),"1",""},
				getDataCallBack);
		mvHelper.readyForRefresh();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FriendsMyCommentActivity.REQUEST_START_COMMENT_EDIT
				&& resultCode == MyCourseCommentEditActivity.RESULT_SEND_COMMENT_FINISHED) {
			reloadContent();
		}
	}

	public void reloadContent() {
		mvHelper.manualForceRefresh();
		// 通知朋友圈刷新页面
		Intent intent = new Intent(
				FriendsCircleActivity.ACTION_RELOAD_COMMENT_LIST);
		sendBroadcast(intent);
	}
}
