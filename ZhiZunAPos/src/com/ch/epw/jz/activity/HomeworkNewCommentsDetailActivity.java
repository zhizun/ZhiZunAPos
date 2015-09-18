package com.ch.epw.jz.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.NoScrollListView;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.adapter.ListViewNewCommentsListAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.Homework;
import com.zhizun.pos.bean.HomeworkNewcommentsDetail;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;

/**
 * 我的邀请家长端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class HomeworkNewCommentsDetailActivity extends BaseActivity {

	protected static final String TAG = HomeworkNewCommentsDetailActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;

	Comments comments = null;
	NoScrollListView nlist;
	List<Comments> listcoComments;
	LikeResult likeResult;
	Homework homework;

	NewCommonComments newCommonComments;
	HomeworkNewcommentsDetail homeworkNewcommentsDetail;

	ImageView iv_list_common_title_logo;// 图片logo
	TextView tv_list_common_title_teachername;// 老师名字
	TextView tv_list_common_title_orgname;// 机构名称
	TextView tv_list_common_title_time;// 发表时间
	TextView tv_list_zxdt_jz_item_content;// 内容
	LinearLayout ll_list_common_ssh_zan;// 赞
	TextView tv_list_common_ssh_zan;// 赞文字
	ImageView iv_list_common_ssh_zan;// 赞图标
	ImageView iv_list_common_ssh_sc;// 收藏图标
	LinearLayout ll_list_common_ssh_sc;// 收藏
	TextView tv_list_common_ssh_sc;// 收藏文字

	LinearLayout ll_list_common_ssh_fx;// 分享
	TextView tv_list_common_ssh_commentcount;// 评论数量
	NoScrollListView ll_list_zxdt_jz_item_commentlist;// 评论列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_newcomment_detail);
		options = Options.getListOptions();
		context = this;
		msgFromTag = getIntent().getIntExtra("msgFromTag", 0);
		initView();

		// 接收的是推送过来的
		if (msgFromTag == 1) {
			PushMsg pushMsg = (PushMsg) getIntent().getSerializableExtra(
					"pushMsg");
			String type = pushMsg.getType();
			String refId = pushMsg.getRefId();
			new GetFsiDataDetailTask().execute(type, refId, "");

		} else {// 接收的是最新回复列表过来的
			newCommonComments = (NewCommonComments) getIntent()
					.getSerializableExtra("newCommonComments");
			new GetFsiDataDetailTask().execute(newCommonComments.getType(),
					newCommonComments.getRefId(),
					newCommonComments.getCommentId());
		}

	}

	private void initData() {

		showPicture(homework.getUserPhoto(), iv_list_common_title_logo, options);

		if (null != homework.getUserName()
				&& !homework.getUserName().equals("")) {
			tv_list_common_title_teachername.setText(homework.getUserName());
		}
		if (null != homework.getOrgName() && !homework.getOrgName().equals("")) {
			tv_list_common_title_orgname.setText("来自" + homework.getOrgName());
		}
		if (null != homework.getSendTime()
				&& !homework.getSendTime().equals("")) {
			tv_list_common_title_time.setText("发送时间：" + homework.getSendTime());
		}
		if (null != homework.getContent() && !homework.getContent().equals("")) {
			tv_list_zxdt_jz_item_content.setText(homework.getContent());
		}
		if (null != homework.getCommentCount()
				&& !homework.getCommentCount().toString().trim().equals("")) {
			tv_list_common_ssh_commentcount.setText("（"
					+ homework.getCommentCount() + "）");
		}
		if (homework.getCurrenUserFav()) {
			tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_yes);
			iv_list_common_ssh_sc.setImageResource(R.drawable.haveshoucang);
		} else {
			tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_no);
			iv_list_common_ssh_sc.setImageResource(R.drawable.shoucang);
		}

		Log.i(TAG, "dynamicTeacher.getLikeList() ="
				+ homework.getLikeList().size());
		if (homework.getLikeList() != null && homework.getLikeList().size() > 0) {

			List<Like> list = homework.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				if (uiserInfo.getUserId().equals(like.getUserId())) {
					tv_list_common_ssh_zan.setText(R.string.text_islike_liked);
					iv_list_common_ssh_zan
							.setImageResource(R.drawable.havelike);
					homework.setCurrenUserLike(true);

				} else {
					tv_list_common_ssh_zan.setText(R.string.text_islike_like);
					iv_list_common_ssh_zan.setImageResource(R.drawable.like);
					homework.setCurrenUserLike(false);
				}
				sBuffer.append(like.getUserAppe());
				sBuffer.append(",");
			}
			String str = sBuffer.toString();
			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1);
			}

		}
		ll_list_common_ssh_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String isCancel = "0";
				if (homework.getCurrenUserLike()) {
					isCancel = "1";
				} else {
					isCancel = "0";
				}
				new LikeTask(homework.getCurrenUserLike()).execute(AppContext
						.getApp().getToken(), homework.getHomeworkId(),
						Constant.COMMNETTYPE_JTZY, isCancel);

			}
		});
		ll_list_common_ssh_sc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (homework.getCurrenUserFav()) {
					new FavTask(homework.getCurrenUserFav()).execute(AppContext
							.getApp().getToken(), homework.getHomeworkId(),
							Constant.COMMNETTYPE_JTZY, Constant.TYPE_YES);

				} else {
					new FavTask(homework.getCurrenUserFav()).execute(AppContext
							.getApp().getToken(), homework.getHomeworkId(),
							Constant.COMMNETTYPE_JTZY, Constant.TYPE_NO);

				}
			}
		});
		ll_list_common_ssh_fx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppContext.getApp().showShare(context,
						newCommonComments.getRefContent(), null);
			}
		});
		if (homework.getCommentsList() != null
				&& homework.getCommentsList().size() > 0) {
			ListViewNewCommentsListAdapter commentsAdapter = new ListViewNewCommentsListAdapter(
					context, homework.getCommentsList());
			ll_list_zxdt_jz_item_commentlist.setAdapter(commentsAdapter);
		}
	}

	/**
	 * 赞 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class LikeTask extends AsyncTask<String, Void, LikeResult> {
		Boolean likeState;// 赞状态
		AppException e;

		public LikeTask(Boolean likeState) {
			this.likeState = likeState;
		}

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected LikeResult doInBackground(String... params) {

			try {
				likeResult = AppContext.getApp().like(params[0], params[1],
						params[2], params[3]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return likeResult;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(LikeResult result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {

					if (likeState) {
						UIHelper.ToastMessage(context, "已取消赞");
						tv_list_common_ssh_zan
								.setText(R.string.text_islike_like);
						iv_list_common_ssh_zan
								.setImageResource(R.drawable.like);
						homework.setCurrenUserLike(false);

					} else {
						UIHelper.ToastMessage(context, "您已赞成功");
						iv_list_common_ssh_zan
								.setImageResource(R.drawable.havelike);
						homework.setCurrenUserLike(true);
						tv_list_common_ssh_zan
								.setText(R.string.text_islike_liked);

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

	/**
	 * 收藏动态 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class FavTask extends AsyncTask<String, Void, Result> {
		Boolean favState;
		AppException e;

		public FavTask(Boolean favState) {
			this.favState = favState;
		}

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected Result doInBackground(String... params) {
			Result result = null;
			try {
				result = AppContext.getApp().fav(params[0], params[1],
						params[2], params[3]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
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

					if (favState) {
						UIHelper.ToastMessage(context, "已取消收藏");
						iv_list_common_ssh_sc
								.setImageResource(R.drawable.shoucang);
						tv_list_common_ssh_sc
								.setText(R.string.dynamic_favorite_no);
						homework.setCurrenUserFav(false);
					} else {
						UIHelper.ToastMessage(context, "收藏成功");
						iv_list_common_ssh_sc
								.setImageResource(R.drawable.haveshoucang);
						homework.setCurrenUserFav(true);
						tv_list_common_ssh_sc
								.setText(R.string.dynamic_favorite_yes);
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

	/**
	 * 获得家庭作业详情 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetFsiDataDetailTask extends
			AsyncTask<String, Void, HomeworkNewcommentsDetail> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected HomeworkNewcommentsDetail doInBackground(String... params) {
			try {
				homeworkNewcommentsDetail = AppContext.getApp()
						.getHomeworkFsiDataDetail(params[0], params[1],
								params[2]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return homeworkNewcommentsDetail;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(HomeworkNewcommentsDetail result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {
					homework = result.getHomework();
					initData();
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

	private void initView() {
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
		iv_list_common_title_logo = (ImageView) this
				.findViewById(R.id.iv_list_common_title_logo);
		tv_list_common_title_teachername = (TextView) this
				.findViewById(R.id.tv_list_common_title_teachername);
		tv_list_common_title_orgname = (TextView) this
				.findViewById(R.id.tv_list_common_title_orgname);
		tv_list_common_title_time = (TextView) this
				.findViewById(R.id.tv_list_common_title_time);
		tv_list_zxdt_jz_item_content = (TextView) this
				.findViewById(R.id.tv_list_zxdt_jz_item_content);
		ll_list_common_ssh_zan = (LinearLayout) this
				.findViewById(R.id.ll_list_common_ssh_zan);
		ll_list_common_ssh_sc = (LinearLayout) this
				.findViewById(R.id.ll_list_common_ssh_sc);
		ll_list_zxdt_jz_item_commentlist = (NoScrollListView) this
				.findViewById(R.id.ll_list_zxdt_jz_item_commentlist);
		ll_list_common_ssh_fx = (LinearLayout) this
				.findViewById(R.id.ll_list_common_ssh_fx);
		tv_list_common_ssh_commentcount = (TextView) this
				.findViewById(R.id.tv_list_common_ssh_commentcount);
		tv_list_common_ssh_sc = (TextView) this
				.findViewById(R.id.tv_list_common_ssh_sc);

		tv_list_common_ssh_zan = (TextView) this
				.findViewById(R.id.tv_list_common_ssh_zan);

		iv_list_common_ssh_zan = (ImageView) findViewById(R.id.iv_list_common_ssh_zan);// 赞图标
		iv_list_common_ssh_sc = (ImageView) findViewById(R.id.iv_list_common_ssh_sc);// 收藏图标

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
