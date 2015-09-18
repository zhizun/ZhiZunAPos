package com.ch.epw.jz.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.adapter.GridViewImagesAdapter;
import com.zhizun.pos.adapter.ListViewNewCommentsListAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.Remark;
import com.zhizun.pos.bean.RemarkNewCommentsDetail;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;

/**
 * 在校点评 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class RemarkNewCommentsDetailActivity extends BaseActivity {

	protected static final String TAG = RemarkNewCommentsDetailActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;

	Comments comments = null;
	NoScrollListView nlist;
	List<Comments> listcoComments;
	LikeResult likeResult;
	Remark remark;
	RemarkNewCommentsDetail remarkNewCommentsDetail;
	NewCommonComments newCommonComments;
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
	NoScrollGridView ngv_list_zxdt_jz_item_imagelist;// 图片列表
	LinearLayout ll_list_commom_zan;//
	TextView tv_list_common_zan;
	TextView tv_list_common_zan_count;// 共多少人赞过

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_comment_detail);
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
			new GetRemarkFsiDataDetailTask().execute(type, refId, "");

		} else {// 接收的是最新回复列表过来的
			newCommonComments = (NewCommonComments) getIntent()
					.getSerializableExtra("newCommonComments");
			new GetRemarkFsiDataDetailTask().execute(
					newCommonComments.getType(), newCommonComments.getRefId(),
					newCommonComments.getCommentId());
		}

	}

	private void initData() {

		showPicture(remark.getUserPhoto(), iv_list_common_title_logo, options);

		if (null != remark.getUserName() && !remark.getUserName().equals("")) {
			tv_list_common_title_teachername.setText(remark.getUserName());
		}
		if (null != remark.getOrgName() && !remark.getOrgName().equals("")) {
			tv_list_common_title_orgname.setText("来自" + remark.getOrgName());
		} else {
			tv_list_common_title_orgname.setText("来自");
		}
		if (null != remark.getSendTime() && !remark.getSendTime().equals("")) {
			tv_list_common_title_time.setText("发送时间：" + remark.getSendTime());
		} else {
			tv_list_common_title_time.setText("发送时间：");
		}
		if (null != remark.getContent() && !remark.getContent().equals("")) {
			tv_list_zxdt_jz_item_content.setText(remark.getContent());
		}
		if (null != remark.getCommentCount()
				&& !remark.getCommentCount().toString().trim().equals("")) {
			tv_list_common_ssh_commentcount.setText("（"
					+ remark.getCommentCount() + "）");
		}
		if (remark.getCurrenUserFav()) {
			tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_yes);
			iv_list_common_ssh_sc.setImageResource(R.drawable.haveshoucang);
		} else {
			tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_no);
			iv_list_common_ssh_sc.setImageResource(R.drawable.shoucang);
		}
		if (remark.getPhotoList() != null && remark.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, remark.getPhotoList());
			ngv_list_zxdt_jz_item_imagelist.setAdapter(listViewImagesAdapter);
		}
		ngv_list_zxdt_jz_item_imagelist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ArrayList<String> imgsUrl = new ArrayList<String>();
						for (Photo photo : remark.getPhotoList()) {
							imgsUrl.add(URLs.URL_IMG_API_HOST
									+ photo.getThumbPath() + "/"
									+ photo.getThumbSaveName());
						}
						Intent intent = new Intent();
						intent.putStringArrayListExtra("infos", imgsUrl);
						intent.putExtra("imgPosition", position);
						intent.setClass(context, ImageShowActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				});
		Log.i(TAG, "remark.getLikeList() =" + remark.getLikeList().size());
		if (remark.getLikeList() != null && remark.getLikeList().size() > 0) {

			List<Like> list = remark.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				if (uiserInfo.getUserId().equals(like.getUserId())) {
					tv_list_common_ssh_zan.setText(R.string.text_islike_liked);
					iv_list_common_ssh_zan
							.setImageResource(R.drawable.havelike);
					remark.setCurrenUserLike(true);

				} else {
					tv_list_common_ssh_zan.setText(R.string.text_islike_like);
					iv_list_common_ssh_zan.setImageResource(R.drawable.like);
					remark.setCurrenUserLike(false);
				}
				sBuffer.append(like.getUserAppe());
				sBuffer.append(",");
			}
			String str = sBuffer.toString();
			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1);
			}

			ll_list_commom_zan.setVisibility(View.VISIBLE);
			tv_list_common_zan.setText(str);
			tv_list_common_zan_count.setText(" 共" + list.size() + "人");
		} else {
			ll_list_commom_zan.setVisibility(View.GONE);
		}
		ll_list_common_ssh_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String isCancel = "0";
				if (remark.getCurrenUserLike()) {
					isCancel = "1";
				} else {
					isCancel = "0";
				}
				new LikeTask(remark.getCurrenUserLike()).execute(AppContext
						.getApp().getToken(), remark.getRemarkId(),
						Constant.COMMNETTYPE_ZXDP, isCancel);

			}
		});
		ll_list_common_ssh_sc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (remark.getCurrenUserFav()) {
					new FavTask(remark.getCurrenUserFav()).execute(AppContext
							.getApp().getToken(), remark.getRemarkId(),
							Constant.COMMNETTYPE_ZXDP, Constant.TYPE_YES);

				} else {
					new FavTask(remark.getCurrenUserFav()).execute(AppContext
							.getApp().getToken(), remark.getRemarkId(),
							Constant.COMMNETTYPE_ZXDP, Constant.TYPE_NO);

				}
			}
		});
		ll_list_common_ssh_fx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppContext.getApp().showShare(context,
						newCommonComments.getRefContent(), null);

				String contentString = newCommonComments.getContent();
				String imgUrlString = "";
				if (newCommonComments.getPhotoList().size() > 0) {
					imgUrlString = URLs.formatImgURL(newCommonComments
							.getPhotoList().get(0).getThumbPath()
							+ "/"
							+ newCommonComments.getPhotoList().get(0)
									.getThumbSaveName());
				}

				AppContext.getApp().showShare(context, contentString,
						imgUrlString);
			}
		});
		if (remark.getCommentsList() != null
				&& remark.getCommentsList().size() > 0) {
			ListViewNewCommentsListAdapter commentsAdapter = new ListViewNewCommentsListAdapter(
					context, remark.getCommentsList());
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
						remark.setCurrenUserLike(false);
						List<Like> list = remark.getLikeList();
						Log.i(TAG, "list.size=" + list.size());
						if (list.size() == 1) {
							remark.getLikeList().clear();
							ll_list_commom_zan.setVisibility(View.GONE);
							tv_list_common_zan.setText("");
						} else {
							UserInfo userInfo = AppContext.getApp()
									.getUserLoginSharedPre().getUserInfo();
							StringBuffer sBuffer = new StringBuffer();
							for (Like like : list) {

								if (userInfo.getUserId().equals(
										like.getUserId())) {
									remark.getLikeList().remove(like);

								}
							}
							StringBuffer sBuffer2 = new StringBuffer();
							for (Like like : remark.getLikeList()) {
								sBuffer.append(like.getUserAppe());
								sBuffer.append(",");
							}
							String str = sBuffer2.toString();
							if (str.endsWith(",")) {
								str = str.substring(0, str.length() - 1);
							}
							tv_list_common_zan.setText(str);
							tv_list_common_zan_count.setText(" 共"
									+ remark.getLikeList().size() + "人");

						}
					} else {
						UIHelper.ToastMessage(context, "您已赞成功");
						iv_list_common_ssh_zan
								.setImageResource(R.drawable.havelike);
						remark.setCurrenUserLike(true);
						tv_list_common_ssh_zan
								.setText(R.string.text_islike_liked);
						Like like = new Like();
						like.setIsCancel(result.getIsCancel());
						like.setUserAppe(result.getUserAppe());
						like.setUserId(result.getUserId());
						remark.getLikeList().add(like);
						ll_list_commom_zan.setVisibility(View.VISIBLE);
						tv_list_common_zan.setText(tv_list_common_zan.getText()
								.toString() + "," + result.getUserAppe());
						tv_list_common_zan_count.setText(" 共"
								+ remark.getLikeList().size() + "人");
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
				this.e = e;
				// TODO Auto-generated catch block
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
						remark.setCurrenUserFav(false);
					} else {
						UIHelper.ToastMessage(context, "收藏成功");
						iv_list_common_ssh_sc
								.setImageResource(R.drawable.haveshoucang);
						remark.setCurrenUserFav(true);
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
	 * 获得点评详情 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetRemarkFsiDataDetailTask extends
			AsyncTask<String, Void, RemarkNewCommentsDetail> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected RemarkNewCommentsDetail doInBackground(String... params) {
			try {
				remarkNewCommentsDetail = AppContext
						.getApp()
						.getRemarkFsiDataDetail(params[0], params[1], params[2]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return remarkNewCommentsDetail;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(RemarkNewCommentsDetail result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {
					remark = result.getRemark();

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
		ngv_list_zxdt_jz_item_imagelist = (NoScrollGridView) this
				.findViewById(R.id.ngv_list_zxdt_jz_item_imagelist);
		tv_list_common_ssh_zan = (TextView) this
				.findViewById(R.id.tv_list_common_ssh_zan);
		ll_list_commom_zan = (LinearLayout) this
				.findViewById(R.id.ll_list_commom_zan);
		tv_list_common_zan = (TextView) this
				.findViewById(R.id.tv_list_common_zan);
		tv_list_common_zan_count = (TextView) this
				.findViewById(R.id.tv_list_common_zan_count);
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
