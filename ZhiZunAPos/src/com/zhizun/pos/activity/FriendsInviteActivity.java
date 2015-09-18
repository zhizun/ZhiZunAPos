package com.zhizun.pos.activity;

import static cn.sharesdk.framework.utils.R.getStringRes;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.ch.epw.task.ShareTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.ShareResult;

public class FriendsInviteActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	private RelativeLayout rl_myfriends_weixin;
	private RelativeLayout rl_myfriends_weixinfriends;
	private RelativeLayout rl_myfriends_qq;
	private RelativeLayout rl_myfriends_qz;
	 private RelativeLayout rl_myfriends_phone;
	private String imageUrl;
	private String platName;
	private String logoPath;
	private String course;
	private String contentText;
	private String titleText;
	private String commentId;
	String url;
	String smpType;
	private String org;

	// 用于接收来自分享过程中回调函数的消息
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			UIHelper.ToastMessage(FriendsInviteActivity.this, (String) msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_nvite);
		Intent in = getIntent();
		logoPath = in.getStringExtra("logoPath");
		course = in.getStringExtra("course");// 0：发表评论勾选，1：邀请好友使用
		commentId = in.getStringExtra("commentId");
		org = in.getStringExtra("org");// 机构名字
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_friends_invite);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_my_share_friends);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		rl_myfriends_weixin = (RelativeLayout) findViewById(R.id.rl_myfriends_weixin);
		rl_myfriends_weixinfriends = (RelativeLayout) findViewById(R.id.rl_myfriends_weixinfriends);
		rl_myfriends_qq = (RelativeLayout) findViewById(R.id.rl_myfriends_qq);
		rl_myfriends_qz = (RelativeLayout) findViewById(R.id.rl_myfriends_qz);
		 rl_myfriends_phone=(RelativeLayout)findViewById(R.id.rl_myfriends_phone);

		rl_myfriends_weixin.setOnClickListener(this);
		rl_myfriends_weixinfriends.setOnClickListener(this);
		rl_myfriends_qq.setOnClickListener(this);
		rl_myfriends_qz.setOnClickListener(this);
		 rl_myfriends_phone.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_myfriends_weixin:
			platName = Wechat.NAME;
			smpType = "0";
			getshareTask();
			break;
		case R.id.rl_myfriends_weixinfriends:
			platName = WechatMoments.NAME;
			smpType = "1";
			getshareTask();
			break;
		case R.id.rl_myfriends_qq:
			platName = QQ.NAME;
			smpType = "3";
			getshareTask();
			break;
		case R.id.rl_myfriends_qz:
			platName = QZone.NAME;
			smpType = "4";
			getshareTask();
			break;
		 case R.id.rl_myfriends_phone:
		 Intent intent=new Intent(this,FriendsContactsListViewActivity.class);
		 startActivity(intent);
		 break;
		}

	}

	public void getshareTask() {
		// 调用shareTask通过指定平台的方式分享
		if (!course.equals("1")) {
			new ShareTask(this, new TaskCallBack() {
				@Override
				public void onTaskFinshed(BaseBean result) {

					final ShareResult shareResult = (ShareResult) result;
					url = URLs.URL_SHARE_PRE + shareResult.getShareId();
					contentText = "我对" + org + "的" + course + "做了评价，快来看看吧";
					titleText = "";
					shareContent();
				}
			}).execute(commentId, Constant.COMMNETTYPE_COUR, smpType, "", null);
		} else {
			url = Constant.courseDownladApp;
			contentText = "益培网上有很多熟人评价，能帮助你更好的找到合适的培训班，快来试试吧！";
			titleText = "装益培网了吗？";
			shareContent();
		}
	}

	public void shareContent() {
		ShareParams sp = new ShareParams();
		Platform plat = ShareSDK.getPlatform(FriendsInviteActivity.this,
				platName);// 平台名字
		if (logoPath == null || logoPath.equals("")) {
			// imageUrl =
			// "http://192.168.1.10:8090/epeiwang/static/theme/images/share_epw_logo.png";
			imageUrl = URLs.PROCOTOL + URLs.EPEIWANG_URL + Constant.imageLog;
		} else {
			imageUrl = ImageUtils.getRealImageUri(logoPath);
		}

		String sitrUrl = URLs.PROCOTOL + URLs.EPEIWANG_URL;
		if (WechatMoments.NAME.equals(platName)) {
			titleText = contentText;
			sitrUrl = url;
		}

		sp.setText(contentText);
		sp.setSite(this.getResources().getString(R.string.app_name));
		sp.setShareType(plat.SHARE_WEBPAGE);
		sp.setSiteUrl(sitrUrl);
		sp.setImageUrl(imageUrl);
		sp.setTitle(titleText);
		sp.setTitleUrl(url);
		sp.setUrl(url);
		plat.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onCancel(Platform arg0, int arg1) {
				Message msg = Message.obtain();
				msg.obj = "已取消分享";
				handler.sendMessage(msg);
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				Message msg = Message.obtain();
				msg.obj = "分享成功";
				handler.sendMessage(msg);
			}

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {

				String expName = arg2.getClass().getSimpleName();
				String msgString = "分享失败，请稍后再试";
				if ("WechatClientNotExistException".equals(expName)
						|| "WechatTimelineNotSupportedException"
								.equals(expName)
						|| "WechatFavoriteNotSupportedException"
								.equals(expName)) {
					int resId = getStringRes(FriendsInviteActivity.this,
							"wechat_client_inavailable");
					if (resId > 0) {
						msgString = FriendsInviteActivity.this.getString(resId);
					}
				}

				Message msg = Message.obtain();
				msg.obj = msgString;
				handler.sendMessage(msg);

			}
		});

		// 执行图文分享
		plat.share(sp);

	}
}
