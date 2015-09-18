package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.bean.send.CommentsSend;
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
import com.zhizun.pos.adapter.ListViewCommentsAdapter;
import com.zhizun.pos.adapter.ListViewNewCommentsListAdapter;
import com.zhizun.pos.adapter.ListViewVoiceTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.CommentsReply;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.NewComments;
import com.zhizun.pos.bean.NewCommentsDetail;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.Voice;
import com.zhizun.pos.bean.VoiceDetail;

/**
 * 家长心声 最新回复 详情 教师端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class VoiceNewCommentsDetailActivity extends BaseActivity {

	protected static final String TAG = VoiceNewCommentsDetailActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;
	NewCommonComments newCommonComments;
	Comments comments = null;
	CommentsReply commentsReply;
	NoScrollListView nlist;
	List<Comments> listcoComments;
	LikeResult likeResult;
	Voice voice;
	VoiceDetail voiceDetail;
	int index;

	ImageView iv_list_common_title_logo;// 图片logo
	TextView tv_list_common_title_teachername;// 老师名字
	TextView tv_list_common_title_orgname;// 机构名称
	TextView tv_list_common_title_time;// 发表时间
	TextView tv_list_zxdt_jz_item_content;// 内容
	LinearLayout ll_list_zxdt_js_ssh_zan;// 赞
	TextView tv_list_zxdt_js_shh_zan;// 赞文字
	ImageView iv_list_zxdt_js_shh_zan_logo;// 赞图标

	LinearLayout ll_list_zxdt_js_ssh_share;// 分享
	TextView tv_list_js_ssh_commentcount;// 评论数量
	NoScrollListView ll_list_zxdt_jz_item_commentlist;// 评论列表
	EditText et_list_zxdt_jz_item_comment_reply;// 回复；评论
	Button btn_list_zxdt_jz_item_comment_send;// 发送评论
	NoScrollGridView ngv_list_zxdt_jz_item_imagelist;// 图片列表
	LinearLayout ll_list_commom_zan;//
	TextView tv_list_common_zan;
	TextView tv_list_common_zan_count;// 共多少人赞过

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jzxs_js_detail);
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

		showPicture(voice.getPhotopath(), iv_list_common_title_logo, options);

		// if (null != voice.getUserName() && !voice.getUserName().equals("")) {
		// tv_list_common_title_teachername
		// .setText(voice.getUserName());
		// } else {
		// tv_list_common_title_teachername.setText("");
		// }
		tv_list_common_title_teachername.setText(voice.getUserAppe());
		// if (null != voice.getOrgName() && !voice.getOrgName().equals("")) {
		// tv_list_common_title_orgname.setText("来自"
		// + voice.getOrgName());
		// } else {
		// tv_list_common_title_orgname.setText("来自");
		// }
		if (voice.getUserDesc() != null && !voice.getUserAppe().equals("")) {
			tv_list_common_title_orgname.setText("(" + voice.getUserDesc()
					+ ")");
		}
		if (null != voice.getSendTime() && !voice.getSendTime().equals("")) {
			tv_list_common_title_time.setText("发送时间：" + voice.getSendTime());
		} else {
			tv_list_common_title_time.setText("发送时间：");
		}
		if (null != voice.getContent() && !voice.getContent().equals("")) {
			tv_list_zxdt_jz_item_content.setVisibility(View.VISIBLE);
			tv_list_zxdt_jz_item_content.setText(voice.getContent());
		} else {
			tv_list_zxdt_jz_item_content.setVisibility(View.GONE);
		}
		if (null != voice.getCommentCount()
				&& !voice.getCommentCount().toString().trim().equals("")) {
			tv_list_js_ssh_commentcount.setText("（" + voice.getCommentCount()
					+ "）");
		} else {
			tv_list_js_ssh_commentcount.setText("（0）");
		}

		if (voice.getCurrenUserLike()) {
			tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_liked);
			iv_list_zxdt_js_shh_zan_logo.setImageResource(R.drawable.havelike);
		} else {
			tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_like);
			iv_list_zxdt_js_shh_zan_logo.setImageResource(R.drawable.like);
		}
		if (voice.getPhotoList() != null && voice.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, voice.getPhotoList());
			ngv_list_zxdt_jz_item_imagelist.setAdapter(listViewImagesAdapter);
		} else {
			ngv_list_zxdt_jz_item_imagelist.setAdapter(null);
		}
		ngv_list_zxdt_jz_item_imagelist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ArrayList<String> imgsUrl = new ArrayList<String>();
						for (Photo photo : voice.getPhotoList()) {
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
		Log.i(TAG, "dynamicTeacher.getLikeList() ="
				+ voice.getLikeList().size());
		if (voice.getLikeList() != null && voice.getLikeList().size() > 0) {
			List<Like> list = voice.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				if (uiserInfo.getUserId().equals(like.getUserId())) {
					tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_liked);
					iv_list_zxdt_js_shh_zan_logo
							.setImageResource(R.drawable.havelike);
					voice.setCurrenUserLike(true);

				} else {
					tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_like);
					iv_list_zxdt_js_shh_zan_logo
							.setImageResource(R.drawable.like);
					voice.setCurrenUserLike(false);
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

		ll_list_zxdt_js_ssh_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String isCancel = "0";
				if (voice.getCurrenUserLike()) {
					isCancel = "1";
				} else {
					isCancel = "0";
				}
				new LikeTask(voice.getCurrenUserLike()).execute(AppContext
						.getApp().getToken(), voice.getVoice_id(),
						Constant.COMMNETTYPE_JZXS + "", isCancel);
			}
		});
		ll_list_zxdt_js_ssh_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String contentString = voiceDetail.getVoice().getContent();
				String imgUrlString = "";
				if (voiceDetail.getVoice().getPhotoList().size() > 0) {
					imgUrlString = URLs.formatImgURL(voiceDetail.getVoice()
							.getPhotoList().get(0).getThumbPath()
							+ "/"
							+ voiceDetail.getVoice().getPhotoList().get(0)
									.getThumbSaveName());
				}

				AppContext.getApp().showShare(context, contentString,
						imgUrlString);
			}
		});
		if (voice.getCommentsList() != null
				&& voice.getCommentsList().size() > 0) {

			final ListViewCommentsAdapter commentsAdapter = new ListViewCommentsAdapter(
					context, voice.getCommentsList());
			ll_list_zxdt_jz_item_commentlist.setAdapter(commentsAdapter);

			ll_list_zxdt_jz_item_commentlist
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							TextView tView = (TextView) view
									.findViewById(R.id.tv_list_common_pinglun_sendname);

							// tView.setTag(list.get(position));
							// mCallback.click(tView);
							et_list_zxdt_jz_item_comment_reply.requestFocus();
							et_list_zxdt_jz_item_comment_reply.setHint("回复 "
									+ voice.getCommentsList().get(position)
											.getUserAppe());
							comments = voice.getCommentsList().get(position);

						}
					});

		} else {
			ll_list_zxdt_jz_item_commentlist.setAdapter(null);
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
						voice.setCurrenUserLike(false);
						List<Like> list = voice.getLikeList();
						Log.i(TAG, "list.size=" + list.size());
						if (list.size() == 1) {
							voice.getLikeList().clear();
						} else {
							UserInfo userInfo = AppContext.getApp()
									.getUserLoginSharedPre().getUserInfo();
							for (Like like : list) {
								if (userInfo.getUserId().equals(
										like.getUserId())) {
									voice.getLikeList().remove(like);
								}
							}
						}
					} else {
						UIHelper.ToastMessage(context, "您已赞成功");
						voice.setCurrenUserLike(true);
						Like like = new Like();
						like.setIsCancel(result.getIsCancel());
						like.setUserAppe(result.getUserAppe());
						like.setUserId(result.getUserId());
						voice.getLikeList().add(like);
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
	 * 获得心声详情 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetFsiDataDetailTask extends
			AsyncTask<String, Void, VoiceDetail> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected VoiceDetail doInBackground(String... params) {
			try {
				voiceDetail = AppContext.getApp().getVoiceFsiDataDetail(
						params[0], params[1], params[2]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return voiceDetail;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(VoiceDetail result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {
					voice = result.getVoice();
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
		ll_list_zxdt_js_ssh_zan = (LinearLayout) this
				.findViewById(R.id.ll_list_zxdt_js_ssh_zan);
		ll_list_zxdt_jz_item_commentlist = (NoScrollListView) this
				.findViewById(R.id.ll_list_zxdt_jz_item_commentlist);
		ll_list_zxdt_js_ssh_share = (LinearLayout) this
				.findViewById(R.id.ll_list_zxdt_js_ssh_share);
		tv_list_zxdt_js_shh_zan = (TextView) this
				.findViewById(R.id.tv_list_zxdt_js_shh_zan);
		et_list_zxdt_jz_item_comment_reply = (EditText) this
				.findViewById(R.id.et_list_zxdt_jz_item_comment_reply);
		btn_list_zxdt_jz_item_comment_send = (Button) this
				.findViewById(R.id.btn_list_zxdt_jz_item_comment_send);
		tv_list_js_ssh_commentcount = (TextView) this
				.findViewById(R.id.tv_list_js_ssh_commentcount);
		ngv_list_zxdt_jz_item_imagelist = (NoScrollGridView) this
				.findViewById(R.id.ngv_list_zxdt_jz_item_imagelist);
		ll_list_commom_zan = (LinearLayout) this
				.findViewById(R.id.ll_list_commom_zan);
		tv_list_common_zan = (TextView) this
				.findViewById(R.id.tv_list_common_zan);
		tv_list_common_zan_count = (TextView) this
				.findViewById(R.id.tv_list_common_zan_count);
		iv_list_zxdt_js_shh_zan_logo = (ImageView) this
				.findViewById(R.id.iv_list_zxdt_js_shh_zan_logo);// 赞图标
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
