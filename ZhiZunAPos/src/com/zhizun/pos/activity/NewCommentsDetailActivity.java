package com.zhizun.pos.activity;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewDynamicParentAdapter;
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.adapter.ListViewHomeworkParentAdapter;
import com.zhizun.pos.adapter.ListViewHomeworkTeacherAdapter;
import com.zhizun.pos.adapter.ListViewRemarkParentAdapter;
import com.zhizun.pos.adapter.ListViewRemarkTeacherAdapter;
import com.zhizun.pos.adapter.ListViewVoiceParentAdapter;
import com.zhizun.pos.adapter.ListViewVoiceTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.DynamicTeacherList;
import com.zhizun.pos.bean.HomeworkTeacherList;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.RemarkList;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.VoiceList;
import com.ch.epw.js.activity.DynamicNewCommentsDetailActivity;
import com.ch.epw.task.GetFsiDataDetailTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;

public class NewCommentsDetailActivity extends BaseActivity {

	protected static final String TAG = DynamicNewCommentsDetailActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;
	private ListView mListView;
	
	private String fsiType;
	private String refId;
	private String commentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_comment_detail);
		context = this;
		initView();
		
		//触发事件来源
		msgFromTag = getIntent().getIntExtra("msgFromTag", 0);
		// 接收的是推送过来的
		if (msgFromTag == 1) {
			PushMsg pushMsg = (PushMsg) getIntent().getSerializableExtra(
					"pushMsg");
			if(pushMsg != null){
				fsiType = pushMsg.getType();
				refId = pushMsg.getRefId();
				commentId = "";
			}
		} else {// 接收的是最新回复列表过来的
			NewCommonComments newCommonComments = (NewCommonComments) getIntent()
					.getSerializableExtra("newCommonComments");
			if( newCommonComments != null ){
				fsiType = newCommonComments.getType();
				refId = newCommonComments.getRefId();
				commentId = newCommonComments.getCommentId();
			}
		}
		mPullListView.doPullRefreshing(true, 500);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		backAnim();
	}

	private void initView(){
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_new_comment_detail);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.latest_reply_detail);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		
		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_activity_new_comment_detail);
		mListView = mPullListView.getRefreshableView();
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new GetFsiDataDetailTask(context,loadDataDetail).execute(fsiType,refId,commentId);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}
		});
		mPullListView.setPullLoadEnabled(false);
		setLastUpdateTime();
	}
	
	private void showParentDetail(BaseBean result) {
		//区分不同的业务类型，获取相关列表的adapter
		String type = fsiType;
		List resultDataList = null;
		String errStatus = "-1";
		if(Constant.COMMNETTYPE_ZXDT.equals(type)){
			DynamicTeacherList destTypeData = (DynamicTeacherList)result;
			resultDataList = destTypeData.getDynamicTeacherList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewDynamicParentAdapter(context, resultDataList));
			}
/*		}else if(Constant.COMMNETTYPE_TZGG.equals(type)){
			resultDataList = ((NoticeBoxList)result).getNoticeBoxList();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewNoticeReceiveBoxParentAdapter(context, resultDataList));
			}*/
		}else if(Constant.COMMNETTYPE_JTZY.equals(type)){
			HomeworkTeacherList destTypeData = (HomeworkTeacherList)result;
			resultDataList = destTypeData.getHomeworkTeacherList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewHomeworkParentAdapter(context, resultDataList));
			}
/*		}else if(Constant.COMMNETTYPE_KQJL.equals(type)){
			resultDataList = ((AttenceDetailParentList)result).getAttenceDetailParentList();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewAttenceParentAdapter(context, resultDataList));
			}*/
		}else if(Constant.COMMNETTYPE_ZXDP.equals(type)){
			RemarkList destTypeData = (RemarkList)result;
			resultDataList = destTypeData.getRemarkList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewRemarkParentAdapter(context, resultDataList));
			}
		}else if(Constant.COMMNETTYPE_JZXS.equals(type)){
			VoiceList destTypeData = (VoiceList)result;
			resultDataList = destTypeData.getVoiceList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewVoiceParentAdapter(context, resultDataList));
			}
		}
	}
	
	private void showTeacherDetail(BaseBean result) {
		//区分不同的业务类型，获取相关列表的adapter
		String type = fsiType;
		List resultDataList = null;
		String errStatus = "-1";
		if(Constant.COMMNETTYPE_ZXDT.equals(type)){
			DynamicTeacherList destTypeData = (DynamicTeacherList)result;
			resultDataList = destTypeData.getDynamicTeacherList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewDynamicTeacherAdapter(context, resultDataList));
			}
		}else if(Constant.COMMNETTYPE_JTZY.equals(type)){
			HomeworkTeacherList destTypeData = (HomeworkTeacherList)result;
			resultDataList = destTypeData.getHomeworkTeacherList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewHomeworkTeacherAdapter(context, resultDataList));
			}
		}else if(Constant.COMMNETTYPE_ZXDP.equals(type)){
			RemarkList destTypeData = (RemarkList)result;
			resultDataList = destTypeData.getRemarkList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewRemarkTeacherAdapter(context, resultDataList));
			}
		}else if(Constant.COMMNETTYPE_JZXS.equals(type)){
			VoiceList destTypeData = (VoiceList)result;
			resultDataList = destTypeData.getVoiceList();
			errStatus = destTypeData.getStatus();
			if(resultDataList!=null){
				mListView.setAdapter(new ListViewVoiceTeacherAdapter(context, resultDataList));
			}
		}
	}
	
	private TaskCallBack loadDataDetail = new TaskCallBack(){

		@Override
		public void onTaskFinshed() {
			
		}

		@Override
		public void onTaskFinshed(BaseBean result) {
			UserInfo userIno = AppContext.getApp().getUserLoginSharedPre()
			.getUserInfo();
			
			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(true);		//设置为true，避免显示更新到底的提示
			setLastUpdateTime();
			try{
				//家长端详情
				if(Constant.ORG_ROLE_TYPE_PARENT.equals(userIno.getCurrentRole())){
					showParentDetail(result);
				}
				//老师端详情
				else{
					showTeacherDetail(result);
				}
			}catch(Exception e){
				UIHelper.ToastMessage(context, R.string.app_run_code_error);
			}
		}
	};
}
