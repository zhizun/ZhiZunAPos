package com.ch.epw.utils;

import static cn.sharesdk.framework.utils.R.getStringRes;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.ch.epw.task.CancelShareTask;
import com.ch.epw.task.ShareTask;
import com.ch.epw.task.TaskCallBack;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.ShareResult;

public class ShareUtils {

	private static Context context;
	private static String orgId;
	private static String refId;
	private static String type;
	private static String content;
	private static String imageUrl;
	private static String shareTitle;

	private static int[] customLogoList = {
			// R.drawable.logo_sinaweibo,
			R.drawable.logo_wechat, R.drawable.logo_wechatmoments,
			R.drawable.logo_wechatfavorite, R.drawable.logo_qq,
			R.drawable.logo_qzone };

	private static int[] customLogo_Share_Prize_List = {
			// 分享有奖
			// R.drawable.logo_sinaweibo,
			R.drawable.logo_wechatmoments, R.drawable.logo_qzone };
	// 用于接收来自分享过程中回调函数的消息
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			UIHelper.ToastMessage(context, (String) msg.obj);
		}
	};

	private ShareUtils() {

	}

	protected static class CustomOnClickListener implements OnClickListener {
		private String smpType;
		private String platName;

		public CustomOnClickListener(int resourceId) {
			switch (resourceId) {
			case R.drawable.logo_sinaweibo:
				smpType = Constant.SMP_TYPE_WEIBO_SINA;
				platName = SinaWeibo.NAME;
				break;
			case R.drawable.logo_wechat:
				smpType = Constant.SMP_TYPE_WECHAT;
				platName = Wechat.NAME;
				break;
			case R.drawable.logo_wechatmoments:
				smpType = Constant.SMP_TYPE_WECHAT_MOMENTS;
				platName = WechatMoments.NAME;
				break;
			case R.drawable.logo_wechatfavorite:
				smpType = Constant.SMP_TYPE_WECHAT_FAVORITE;
				platName = WechatFavorite.NAME;
				break;
			case R.drawable.logo_qq:
				smpType = Constant.SMP_TYPE_QQ;
				platName = QQ.NAME;
				break;
			case R.drawable.logo_qzone:
				smpType = Constant.SMP_TYPE_QQ_ZONE;
				platName = QZone.NAME;
				break;
			}
		}

		@Override
		public void onClick(View v) {
			// 调用shareTask通过指定平台的方式分享
			new ShareTask(context, new TaskCallBack() {
				@Override
				public void onTaskFinshed(BaseBean result) {

					final ShareResult shareResult = (ShareResult) result;
					String url = URLs.URL_SHARE_PRE + shareResult.getShareId();

					// final OnekeyShare oks = new OnekeyShare();
					ShareParams sp = new ShareParams();
					Platform plat = ShareSDK.getPlatform(context, platName);
					// 关闭sso授权
					// oks.disableSSOWhenAuthorize();
					// 分享时Notification的图标和文字
					// oks.setNotification(R.drawable.ic_launcher,
					// context.getResources().getString(R.string.app_name));

					// text是分享文本，所有平台都需要这个字段
					if (Constant.COMMNETTYPE_KQJL.equals(type)) {
						content = content.replace(
								"<b style='font-size:14px;'>", "").replace(
								"</b>", "");
					}
					sp.setText(content);

					// 如果传入图片为空，则设置为机构默认图像
					if (imageUrl == null || imageUrl.equals("")) {
						imageUrl = shareResult.getLogoPath() == null ? ""
								: shareResult.getLogoPath();
					}

					// 如果imageUrl是服务器默认图片，则添加http前缀
					if (ImageUtils.isSysDefault(imageUrl)) {
						imageUrl = URLs.URL_API_HOST + "/" + imageUrl;
					}

					// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
					// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
					// url仅在微信（包括好友和朋友圈）中使用
					// oks.setUrl("http://www.epeiwang.com");
					// comment是我对这条分享的评论，仅在人人网和QQ空间使用
					// oks.setComment("我是测试评论文本");
					// site是分享此内容的网站名称，仅在QQ空间使用
					sp.setSite(context.getResources().getString(
							R.string.app_name));
					sp.setSiteUrl(URLs.PROCOTOL + URLs.EPEIWANG_URL);

					// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
					if (shareResult.getOrgName() != null
							&& !shareResult.getOrgName().equals("")
							&& !shareResult.getOrgName().equals("null")) {
						sp.setTitle("来自" + shareResult.getOrgName() + "的内容分享");
					} else {
						sp.setTitle(context.getResources().getString(
								R.string.title_text_share));
					}
					sp.setTitleUrl(url);

					sp.setUrl(url);

					// 通知公告和考勤记录或者没有分享图片内容的都只直接分享文字，不再生成分享链接
					if (!Constant.COMMNETTYPE_KQJL.equals(type)
							&& !Constant.COMMNETTYPE_TZGG.equals(type)
							&& !"".equals(imageUrl) && !"null".equals(imageUrl)) {
						sp.setImageUrl(imageUrl);
						sp.setShareType(plat.SHARE_WEBPAGE);
					} else {
						sp.setShareType(plat.SHARE_TEXT);
					}

					// 微信相关的分享平台下 图文分享时，修改title为从内容截取
					if (sp.getShareType() == plat.SHARE_WEBPAGE) {
						// 微信和微信收藏带标题显示，截取20个字符
						if (Wechat.NAME.equals(platName)
								|| WechatFavorite.NAME.equals(platName)) {
							if (TextUtils.isEmpty(shareTitle)) {
								sp.setTitle(content.length() > 20 ? content
										.substring(0, 20) : content);
							} else {
								sp.setTitle(shareTitle.length() > 20 ? shareTitle
										.substring(0, 20) : shareTitle);
							}

						}
						// 微信朋友圈不带标题显示，标题内容当做摘要显示，截取100个字，多余自动省略显示
						else if (WechatMoments.NAME.equals(platName)) {
							if (TextUtils.isEmpty(shareTitle)) {
								sp.setTitle(content.length() > 100 ? content
										.substring(0, 100) : content);
							} else {
								sp.setTitle(shareTitle.length() > 100 ? shareTitle
										.substring(0, 100) : shareTitle);
							}

						}
					}

					plat.setPlatformActionListener(new PlatformActionListener() {

						@Override
						public void onCancel(Platform arg0, int arg1) {
							new CancelShareTask(context, null).execute(shareResult.getShareId());
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
						public void onError(Platform arg0, int arg1,
								Throwable arg2) {

							String expName = arg2.getClass().getSimpleName();
							String msgString = "分享失败，请稍后再试";
							if ("WechatClientNotExistException".equals(expName)
									|| "WechatTimelineNotSupportedException"
											.equals(expName)
									|| "WechatFavoriteNotSupportedException"
											.equals(expName)) {
								int resId = getStringRes(context,
										"wechat_client_inavailable");
								if (resId > 0) {
									msgString = context.getString(resId);
								}
							}

							new CancelShareTask(context, null).execute(shareResult.getShareId());
							Message msg = Message.obtain();
							msg.obj = msgString;
							handler.sendMessage(msg);

						}
					});

					// 执行图文分享
					plat.share(sp);
				}
			}).execute(refId, type, smpType, orgId, null);
		}
	}

	private static OnekeyShare getOKSInstance(Context context) {
		OnekeyShare oks = new OnekeyShare();

		for (int k = 0; k < customLogoList.length; k++) {
			String label = "分享";
			int resourceId = customLogoList[k];
			switch (resourceId) {
			case R.drawable.logo_sinaweibo:
				label = context.getResources().getString(R.string.sinaweibo);
				break;
			case R.drawable.logo_wechat:
				label = context.getResources().getString(R.string.wechat);
				break;
			case R.drawable.logo_wechatmoments:
				label = context.getResources()
						.getString(R.string.wechatmoments);
				break;
			case R.drawable.logo_wechatfavorite:
				label = context.getResources().getString(
						R.string.wechatfavorite);
				break;
			case R.drawable.logo_qq:
				label = context.getResources().getString(R.string.qq);
				break;
			case R.drawable.logo_qzone:
				label = context.getResources().getString(R.string.qzone);
				break;
			}
			// 构造一个图标
			Bitmap logo = BitmapFactory.decodeResource(context.getResources(),
					resourceId);
			oks.setCustomerLogo(logo, logo, label, new CustomOnClickListener(
					resourceId));
		}

		return oks;
	}

	/**
	 * 
	 * @param context
	 * @param shareLogoList
	 *            九宫格列表
	 * @return
	 */
	private static OnekeyShare getOKSInstance(Context context,
			int[] shareLogoList) {
		OnekeyShare oks = new OnekeyShare();

		for (int k = 0; k < shareLogoList.length; k++) {
			String label = "分享";
			int resourceId = shareLogoList[k];
			switch (resourceId) {
			case R.drawable.logo_sinaweibo:
				label = context.getResources().getString(R.string.sinaweibo);
				break;
			case R.drawable.logo_wechat:
				label = context.getResources().getString(R.string.wechat);
				break;
			case R.drawable.logo_wechatmoments:
				label = context.getResources()
						.getString(R.string.wechatmoments);
				break;
			case R.drawable.logo_wechatfavorite:
				label = context.getResources().getString(
						R.string.wechatfavorite);
				break;
			case R.drawable.logo_qq:
				label = context.getResources().getString(R.string.qq);
				break;
			case R.drawable.logo_qzone:
				label = context.getResources().getString(R.string.qzone);
				break;
			}
			// 构造一个图标
			Bitmap logo = BitmapFactory.decodeResource(context.getResources(),
					resourceId);
			oks.setCustomerLogo(logo, logo, label, new CustomOnClickListener(
					resourceId));
		}

		return oks;
	}

	/**
	 * 分享
	 * 
	 * @param context
	 *            android context
	 * @param userId
	 *            用户ID
	 * @param refId
	 *            分享内容ID
	 * @param type
	 *            分享内容互动类型
	 * @param content
	 *            分享正文
	 * @param imageUrl分享内容图片URL
	 * 
	 * @param shareType
	 *            分享类型0 普通的分享，1分享有奖
	 */

	public static void share(Context _context, String _orgId, String _refId,
			String _type, String _content, String _imageUrl) {
		context = _context;
		orgId = _orgId;
		refId = _refId;
		type = _type;
		content = _content == null ? "" : _content;
		imageUrl = _imageUrl;
		shareTitle = "";
		OnekeyShare oks = getOKSInstance(context);
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher, context.getResources()
				.getString(R.string.app_name));
		oks.show(context);
	}

	/**
	 * 分享
	 * 
	 * @param context
	 *            android context
	 * @param userId
	 *            用户ID
	 * @param refId
	 *            分享内容ID
	 * @param type
	 *            分享内容互动类型
	 * @param content
	 *            分享正文
	 * @param imageUrl分享内容图片URL
	 * 
	 * @param eventType
	 *            分享类型0 分享有奖，1推荐有奖
	 * 
	 * @param title
	 *            标题
	 */

	public static void share(Context _context, String _orgId, String _refId,
			String _type, String _content, String _imageUrl, int eventType,
			String title) {
		context = _context;
		orgId = _orgId;
		refId = _refId;
		type = _type;
		content = _content == null ? "" : _content;
		imageUrl = _imageUrl;
		shareTitle = title;
		OnekeyShare oks = null;
		switch (eventType) {
		case Constant.EVENT_TYPE_SHARE_PRIZE:// 分享有奖
			oks = getOKSInstance(context, customLogo_Share_Prize_List);
			break;
		case Constant.EVENT_TYPE_RECOMMEND_PRIZE:// 推荐有奖
			oks = getOKSInstance(context, customLogoList);
			break;
		case Constant.EVENT_TYPE_RECOMMEND_YHHD:// 优惠活动
			oks = getOKSInstance(context, customLogoList);
			break;
		}
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher, context.getResources()
				.getString(R.string.app_name));
		oks.show(context);
	}
}
