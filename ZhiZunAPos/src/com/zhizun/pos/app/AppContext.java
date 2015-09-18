package com.zhizun.pos.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.baidu.mapapi.SDKInitializer;
import com.ch.epw.bean.send.BabyinfoSend;
import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.bean.send.PersoninfoSend;
import com.ch.epw.db.SQLHelper;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.JpushUtil;
import com.ch.epw.utils.PullHostConfigParser;
import com.ch.epw.utils.ShareUtils;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhizun.pos.AppConfig;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.api.ApiClient;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.AgentHistoryList;
import com.zhizun.pos.bean.Attence;
import com.zhizun.pos.bean.AttenceDetailParentList;
import com.zhizun.pos.bean.AttenceList;
import com.zhizun.pos.bean.BabyInfoDetail;
import com.zhizun.pos.bean.BabyInfoList;
import com.zhizun.pos.bean.CatList;
import com.zhizun.pos.bean.CircleofFriendsBean;
import com.zhizun.pos.bean.CommentsReply;
import com.zhizun.pos.bean.CourseApplyBean;
import com.zhizun.pos.bean.CourseParticularsBean;
import com.zhizun.pos.bean.DynamicTeacherList;
import com.zhizun.pos.bean.FavorList;
import com.zhizun.pos.bean.FriendsCommentBean;
import com.zhizun.pos.bean.FriendsHelpfulBase;
import com.zhizun.pos.bean.HomeworkNewcommentsDetail;
import com.zhizun.pos.bean.HomeworkTeacherList;
import com.zhizun.pos.bean.HostConfig;
import com.zhizun.pos.bean.InviteCount;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.MarketingHotList;
import com.zhizun.pos.bean.MarketingList;
import com.zhizun.pos.bean.MyAgentList;
import com.zhizun.pos.bean.MyPrizedParticipant;
import com.zhizun.pos.bean.MyPrizedRecommendationBean;
import com.zhizun.pos.bean.MySelfMessageList;
import com.zhizun.pos.bean.MySendMessageBean;
import com.zhizun.pos.bean.MySystemMessageDeatilContent;
import com.zhizun.pos.bean.MySystemMessageDeatilList;
import com.zhizun.pos.bean.MySystemMessageList;
import com.zhizun.pos.bean.MyepePrizedBeanList;
import com.zhizun.pos.bean.NewCommentsCount;
import com.zhizun.pos.bean.NewCommentsDetail;
import com.zhizun.pos.bean.NewCommentsList;
import com.zhizun.pos.bean.NewCommonCommentsList;
import com.zhizun.pos.bean.NoticeBoxList;
import com.zhizun.pos.bean.NoticeSendBoxDetail;
import com.zhizun.pos.bean.NoticeTempleteList;
import com.zhizun.pos.bean.OldCommentsList;
import com.zhizun.pos.bean.OrgInfo;
import com.zhizun.pos.bean.OrgIntroWrapper;
import com.zhizun.pos.bean.OrgList;
import com.zhizun.pos.bean.PersonInfoDetail;
import com.zhizun.pos.bean.PrizedBeanWrapper;
import com.zhizun.pos.bean.ReceiveMyInvitationList;
import com.zhizun.pos.bean.RemarkList;
import com.zhizun.pos.bean.RemarkNewCommentsDetail;
import com.zhizun.pos.bean.RemarkTeplateList;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.ShareResult;
import com.zhizun.pos.bean.StudentClassList;
import com.zhizun.pos.bean.StudentTeacherOrgClassList;
import com.zhizun.pos.bean.SuggestNameWrapper;
import com.zhizun.pos.bean.SystemMessageNumBean;
import com.zhizun.pos.bean.TeacherOrgClassList;
import com.zhizun.pos.bean.TeacherOrgList;
import com.zhizun.pos.bean.UnReadRecvNumList;
import com.zhizun.pos.bean.UpdateInfoDetail;
import com.zhizun.pos.bean.UserChildInfoList;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRoleList;
import com.zhizun.pos.bean.ValidCodeResult;
import com.zhizun.pos.bean.ValidUserResult;
import com.zhizun.pos.bean.VoiceDetail;
import com.zhizun.pos.bean.VoiceList;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * ===================================================
 */
public class AppContext extends Application {

	public String mData;
	public TextView mTv;
	private static AppContext mAppApplication;
	private SQLHelper sqlHelper;
	public boolean m_bKeyRight = true;
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	public static final int PAGE_SIZE = 20;// 默认分页大小
	private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间
	private static final int SECRETKEYVER = 0;// 缓存失效时间
	public static final String TAG = "com.ch.epw.AppContext";
	private boolean login = false; // 登录状态
	public HostConfig hostConfig;
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();
	private String saveImagePath;// 保存图片路径

	private Handler unLoginHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				// UIHelper.ToastMessage(AppContext.this,
				// getString(R.string.msg_login_error));
				// UIHelper.showLoginDialog(AppContext.this);
			}
		}
	};

	@Override
	public void onCreate() {

		// 注册App异常崩溃处理器
		// Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler(this));
		super.onCreate();
		// 在使用 baidumapSDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);

		initImageLoader(getApplicationContext());
		mAppApplication = this;
		this.hostConfig = getHostCofigFromXml();
		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
	}

	/** 获取Application */
	public static AppContext getApp() {
		return mAppApplication;
	}

	/** 获取数据库Helper */
	public SQLHelper getSQLHelper() {
		if (sqlHelper == null)
			sqlHelper = new SQLHelper(mAppApplication);
		return sqlHelper;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (sqlHelper != null)
			sqlHelper.close();
		super.onTerminate();
		// 整体摧毁的时候调用这个方法
	}

	/** 初始化ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"epeiwang/Cache");// 获取到缓存的目录地址
		Log.d("cacheDir", cacheDir.getPath());
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).build();
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			Log.i(TAG, "extraInfo=" + extraInfo);
			if (!StringUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().contains("net")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 获取App唯一标识
	 * 
	 * @return
	 */
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if (StringUtils.isEmpty(uniqueID)) {
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}
	
	public HostConfig getHostCofigFromXml() {
		HostConfig hostConfig = new HostConfig();

		try {
			hostConfig = new PullHostConfigParser().parse(getResources()
					.getXml(R.xml.hostconfig));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostConfig;
	}

	public UserLogin getUserLoginSharedPre() {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
		String userInfo = preferences.getString("UserLogin", null);
		UserLogin userLogin = null;
		try {
			if (null != userInfo && !userInfo.equals("")) {
				userLogin = deSerialization(userInfo);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userLogin;
	}

	/**
	 * 用户是否登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * 用户注销
	 */
	public void Logout() {
		this.cleanUserLoginInfo();
		this.login = false;
	}

	/**
	 * 保存登录信息
	 * 
	 * @param context
	 * @param user
	 */
	public void saveLoginInfo(UserLogin userLogin) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		String userInfo = null;
		try {
			userInfo = serialize(userLogin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editor.putString("UserLogin", userInfo);
		editor.commit();
		this.login = true;
	}
	public void saveRole(String userRole) {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("UserRole", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		String userInfo = null;
		editor.putString("UserRole", userInfo);
		editor.commit();
		this.login = true;
	}
	public String getRole() {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("UserRole", Context.MODE_PRIVATE);
		String userInfo = preferences.getString("UserRole", null);
		return userInfo;
	}

	/**
	 * 序列化对象
	 * 
	 * @param person
	 * @return
	 * @throws IOException
	 */
	private String serialize(UserLogin userLogin) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(userLogin);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		Log.d("serial", "serialize str =" + serStr);
		return serStr;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private UserLogin deSerialization(String str) throws IOException,
			ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		UserLogin userLogin = (UserLogin) objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return userLogin;
	}

	/**
	 * 清除登录信息
	 * 
	 * @param context
	 */
	public void cleanUserLoginInfo() {
		SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("UserLogin", Context.MODE_PRIVATE);
		if (null != preferences) {
			Editor editor = preferences.edit();
			editor.clear();
			editor.commit();
			JpushUtil.resetAliasAndTags("");
		}
		this.login = false;
	}

	/**
	 * 获得我的在校动态 教师
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public DynamicTeacherList getMyDynamic(String page, String pageCount)
			throws AppException {
		return ApiClient.getMyDynamic(this, page, pageCount);
	}
	
	/**
	 * 我的消息 系统列表
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	
	public MySystemMessageList getMessageList(String page, String pageCount)
			throws AppException {
		return ApiClient.getMessageList(this, page, pageCount);
	}
	
	/**
	 * 我的消息 我的消息列表
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	
	public MySelfMessageList getMySelfMessageList(String page, String pageCount)
			throws AppException {
		return ApiClient.getMySelfMessageList(this, page, pageCount);
	}
	
	/**
	 * 我的消息 系统消息详情页 列表
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	
	public MySystemMessageDeatilList getMySystemMessageDeatiList(String receiveId,String page,String pageCount)
			throws AppException {
		return ApiClient.getMySystemMessageDeatiList(this,receiveId,page,pageCount);
	}
	
	/**
	 * 系统消息 详情页 标题内容发送时间
	 * 
	 * @param receiveId
	 * @param msgId
	 * @return
	 * @throws AppException
	 */
	public MySystemMessageDeatilContent getMySystemMessageDeatil(String receiveId,String messageId)
			throws AppException {
		return ApiClient.getMySystemMessageDeatil(this,receiveId, messageId);
	}
	
	/**
	 * 获取SharedPreferences
	 * 
	 * @return
	 */
	public SharedPreferences getSharedPref(String spName) {
		return getSharedPreferences(spName, MODE_PRIVATE);
	}

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public String getToken() {
		SharedPreferences sp = getSharedPref("appToken");
		return sp.getString("token", "");
	}

	
	/**
	 * 获取登录信息
	 * 
	 * @param appContext
	 * @param j_password
	 * @param j_username
	 * @return
	 * @throws AppException
	 */

	public UserLogin loginVerify(String j_username, String j_password,String j_loginType)
			throws AppException {
		return ApiClient.loginVerify(this, j_username, j_password,j_loginType);
	}

	/**
	 * 回复动态或评论
	 * 
	 * @param appContext
	 * @param commentsSend
	 * @return
	 * @throws AppException
	 */
	public CommentsReply replyComments(CommentsSend commentsSend)
			throws AppException {
		return ApiClient.replyComments(this, commentsSend);
	}

	/**
	 * 删除动态
	 * 
	 * @param appContext
	 * @param token
	 * @param dynamicId
	 * @return
	 * @throws AppException
	 */
	public Result deleteDynamic(String token, String dynamicId)
			throws AppException {
		return ApiClient.deleteDynamic(this, token, dynamicId);
	}

	/**
	 * 删除评论
	 * 
	 * @param appContext
	 * @param token
	 * @param commentId
	 * @return
	 * @throws AppException
	 */
	public Result deleteComment(String token, String commentId)
			throws AppException {
		return ApiClient.deleteComment(this, token, commentId);
	}

	/**
	 * 获得我的邀请
	 * 
	 * @param appContext
	 * @param token
	 * @param _page_
	 * @param _page_count_
	 * @return
	 * @throws AppException
	 */
	public ReceiveMyInvitationList getReceiveMyInvitationList(String token,
			String _page_, String _page_count_) throws AppException {
		return ApiClient.getReceiveMyInvitationList(this, token, _page_,
				_page_count_);
	}

	/**
	 * 获得我发出的邀请
	 * 
	 * @param appContext
	 * @param token
	 * @param _page_
	 * @param _page_count_
	 * @return
	 * @throws AppException
	 */
	public ReceiveMyInvitationList getSendReceiveMyInvitationList(String token,
			String _page_, String _page_count_) throws AppException {
		return ApiClient.getSendReceiveMyInvitationList(this, token, _page_,
				_page_count_);
	}

	/**
	 * 接受邀请
	 * 
	 * @param appContext
	 * @param token
	 * @param orgInviteId
	 * @param childId
	 * @param stuId
	 * @return
	 * @throws AppException
	 */
	public Result recevieInvite(String token, String orgInviteId,
			String childId, String stuId) throws AppException {
		return ApiClient
				.recevieInvite(this, token, orgInviteId, childId, stuId);
	}

	/**
	 * 拒绝邀请
	 * 
	 */
	public Result refuseInvite(String token, String orgInviteId)
			throws AppException {
		return ApiClient.refuseInvite(this, token, orgInviteId);
	}

	/**
	 * 获取当前用户的宝宝
	 * 
	 */
	public UserChildInfoList getUserChildInfoList(String token)
			throws AppException {
		return ApiClient.getUserChildInfoList(this, token);
	}

	/**
	 * 获得我的在校动态 老师
	 * 
	 */

	public DynamicTeacherList getTeacherMyDynamic(String page, String pageCount)
			throws AppException {
		return ApiClient.getTeacherMyDynamic(this, page, pageCount);
	}

	/**
	 * 获得班级和班级学生
	 * 
	 */
	public StudentClassList getStudentClassList(String token)
			throws AppException {
		return ApiClient.getStudentClassList(this, token);
	}

	/**
	 * 收藏动态
	 * 
	 */

	public Result fav(String token, String refId, String type, String isCancle)
			throws AppException {
		return ApiClient.fav(this, token, refId, type, isCancle);
	}

	/**
	 * 发送动态
	 * 
	 */

	public Result insertdynamic(String token, String content, String receivers,
			String pictures) throws AppException {
		return ApiClient.insertdynamic(this, token, content, receivers,
				pictures);
	}

	/**
	 * 赞
	 * 
	 */

	public LikeResult like(String token, String refId, String type,
			String isCancle) throws AppException {
		return ApiClient.like(this, token, refId, type, isCancle);
	}
	
	/**
	 * 未读消息
	 * 
	 * @param token
	 * @param refId
	 * @param type
	 * @param isCancle
	 * @return
	 * @throws AppException
	 */

	public SystemMessageNumBean systemnum() throws AppException {
		return ApiClient.systemnum(this);
	}

	/**
	 * 获取当前用户角色
	 * 
	 */
	public UserRoleList getUserRoleList(String token) throws AppException {
		return ApiClient.getUserRoleList(this, token);
	}

	/**
	 * 切换角色
	 * 
	 */

	public Result switchRole(String token, String role, String organId)
			throws AppException {
		return ApiClient.switchRole(this, token, role, organId);
	}

	/**
	 * 得到最新回复
	 * 
	 */
	public NewCommentsList getDynamicNewComments(String token)
			throws AppException {
		return ApiClient.getDynamicNewComments(this, token);
	}

	/**
	 * 获得历史回复
	 * 
	 */
	public OldCommentsList getDynamicOldComments(String token, String _page_,
			String _page_count_) throws AppException {
		return ApiClient.getDynamicOldComments(this, token, _page_,
				_page_count_);
	}

	/**
	 * 获得通知模版标签列表
	 * 
	 */
	public String getTplTagList() throws AppException {
		return ApiClient.getTplTagList(this);
	}

	/**
	 * 获得通知模板列表
	 * 
	 */

	public NoticeTempleteList queryNoticeTplList(String page,
			String page_count, String tag) throws AppException {
		return ApiClient.queryNoticeTplList(this, page, page_count, tag);
	}

	/**
	 * 删除通知模板
	 * 
	 */
	public Result delNoticeTpl(String token, String templateId)
			throws AppException {
		return ApiClient.delNoticeTpl(this, token, templateId);
	}

	/**
	 * 发送通知
	 * 
	 */

	public Result sendNotice(String token, String content, String receivers,
			String isSendMsg, String sendMode, String taskTime)
			throws AppException {
		return ApiClient.sendNotice(this, token, content, receivers, isSendMsg,
				sendMode, taskTime);
	}

	/**
	 * 获得通知发送的收件箱列表
	 * 
	 */

	public NoticeBoxList queryInBoxList(String page, String pageCount)
			throws AppException {
		return ApiClient.queryInBoxList(this, page, pageCount);
	}

	/**
	 * 获得通知发送的发件箱列表
	 * 
	 */

	public NoticeBoxList queryOutBoxList(String page, String pageCount,
			String status) throws AppException {
		return ApiClient.queryOutBoxList(this, page, pageCount, status);
	}

	/**
	 * @param page
	 * @param pageCount
	 * @param mktgtype
	 * @return
	 * @throws AppException
	 */

	public MyPrizedParticipant queryPartic(String eventId) throws AppException {
		return ApiClient.queryParticList(this, eventId);

	}

	/**
	 * 
	 * 有奖活动 推荐有奖参与活动详情
	 * 
	 * @param page
	 *            页码
	 * @param pageCount
	 *            每页条数（默认每页显示10条） =======
	 * 
	 *            有奖活动 推荐有奖参与活动详情
	 * @param page
	 *            页码
	 * @param pageCount
	 *            每页条数（默认每页显示10条） >>>>>>> .r7578
	 * @return
	 * @throws AppException
	 */

	public MyPrizedRecommendationBean queryRecommendation(String eventId,
			String pageCount, String page) throws AppException {
		return ApiClient.queryRecommendList(this, eventId, pageCount, page);

	}

	/**
	 * 
	 * @param page
	 * @param pageCount
	 * @param mktgtype
	 * @return
	 * @throws AppException
	 */

	public MyepePrizedBeanList queryShareList(String page, String pageCount,
			String mktgtype) throws AppException {
		return ApiClient.queryShareList(this, page, pageCount, mktgtype);

	}
	
	/**
	 * 有奖活动具体活动页面html请求
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public PrizedBeanWrapper queryEvent(String eventId) throws AppException {
		return ApiClient.queryEvent(this, eventId);

	}
	
	/**
	 * 设置通知为已读
	 * 
	 */

	public Result setNoticeRead(String token, String noticeId)
			throws AppException {
		return ApiClient.setNoticeRead(this, token, noticeId);
	}

	/**
	 * 删除发送通知
	 * 
	 */

	public Result delSentNotice(String token, String noticeId)
			throws AppException {
		return ApiClient.delSentNotice(this, token, noticeId);
	}

	/**
	 * token过期，跳转到登录界面
	 * 
	 */

	public void handleTokenExpired(String resultCode) throws IOException,
			AppException {
		Result result = Result.parse(resultCode);
		if (result.getStatus().equals(Constant.TOKEN_EXPIRED_CODE)) {
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	/**
	 * 查看已发通知明细
	 * 
	 */
	public NoticeSendBoxDetail getNoticeDetail(String token, String noticeId)
			throws AppException {
		return ApiClient.getNoticeDetail(this, token, noticeId);
	}

	/**
	 * 退出登录
	 * 
	 */

	public Result logOut(String token) throws AppException {
		return ApiClient.logOut(this, token);
	}

	/**
	 * 删除家庭作业
	 * 
	 */

	public Result deleteHomework(String token, String homeworkId)
			throws AppException {
		return ApiClient.deleteHomework(this, token, homeworkId);
	}

	/**
	 * 布置作业
	 * 
	 */
	public Result assignHomework(String token, String content,
			String receivers, String isSendMsg, String sendMode, String taskTime)
			throws AppException {
		return ApiClient.assignHomework(this, token, content, receivers,
				isSendMsg, sendMode, taskTime);
	}

	/**
	 * 获得家庭作业列表
	 * 
	 */

	public HomeworkTeacherList getHomeworkList(String page, String pageCount,
			String type) throws AppException {
		return ApiClient.getHomeworkList(this, page, pageCount, type);
	}

	/**
	 * 获得家庭作业列表 家长
	 * 
	 */

	public HomeworkTeacherList getUserHomeworkList(String page, String pageCount)
			throws AppException {
		return ApiClient.getUserHomeworkList(this, page, pageCount);
	}

	/**
	 * 获得动态详情
	 * 
	 */

	public NewCommentsDetail getFsiDynamicAndComments(String refId)
			throws AppException {
		return ApiClient.getFsiDynamicAndComments(this, refId);
	}

	/**
	 * 获得邀请数量
	 * 
	 */

	public InviteCount getInviteCount(String token) throws AppException {
		return ApiClient.getInviteCount(this, token);
	}

	/**
	 * 获得最新回复数量
	 */

	public NewCommentsCount getNewCommnetsCount(String token, String type)
			throws AppException {
		return ApiClient.getNewCommnetsCount(this, token, type);

	}

	/**
	 * 获得通用最新回复列表
	 * 
	 */

	public NewCommonCommentsList getLatestCommentReplyList(String token,
			String page, String pageCount, String type) throws AppException {
		return ApiClient.getLatestCommentReplyList(this, token, page,
				pageCount, type);
	}

	/**
	 * 获得最新回复动态详情 家长端
	 * 
	 */

	public NewCommentsDetail getFsiDataDetail(String type, String refId,
			String commentId) throws AppException {
		return ApiClient.getFsiDataDetail(this, type, refId, commentId);
	}

	/**
	 * 获得最新回复家庭作业详情 家长端
	 * 
	 */

	public HomeworkNewcommentsDetail getHomeworkFsiDataDetail(String type,
			String refId, String commentId) throws AppException {
		return ApiClient.getHomeworkFsiDataDetail(this, type, refId, commentId);
	}

	/**
	 * 获得考勤列表 教师端
	 * 
	 */

	public AttenceList getAttenceListTeacherByDay(String token, String chkDay)
			throws AppException {
		return ApiClient.getAttenceListTeacherByDay(this, token, chkDay);
	}

	/**
	 * 删除考勤记录
	 * 
	 */

	public Result delAttence(String token, String attenceId)
			throws AppException {
		return ApiClient.delAttence(this, token, attenceId);
	}

	/**
	 * 编辑/增加考勤记录
	 * 
	 */

	public Result saveAttence(String token, String attenceId, String claId,
			String claName, String expendedChourNum, String attenceDetail)
			throws AppException {
		return ApiClient.saveAttence(this, token, attenceId, claId, claName,
				expendedChourNum, attenceDetail);
	}

	/**
	 * 家长查看考勤列表
	 * 
	 */
	public AttenceDetailParentList getAttenceListParent(String token,
			String page, String pageCount) throws AppException {
		return ApiClient.getAttenceListParent(this, token, page, pageCount);
	}

	/**
	 * 查看考勤明细
	 * 
	 */
	public Attence getAttenceDetail(String token, String chkDay,
			String chkClaId, String orgId) throws AppException {
		return ApiClient.getAttenceDetail(this, token, chkDay, chkClaId, orgId);
	}

	/**
	 * 增加考勤记录
	 * 
	 */

	public Result saveAttence(String token, String attenceId, String claId,
			String claName, String expendedChourNum, String attenceDetail,
			String attenceDate, String attenceTime) throws AppException {
		return ApiClient.saveAttence(this, token, attenceId, claId, claName,
				expendedChourNum, attenceDetail, attenceDate, attenceTime);
	}

	/**
	 * 删除在校点评
	 * 
	 * @author 李林中 2015-1-20 上午10:25:33
	 */

	public Result deleteRemark(String token, String remarkId)
			throws AppException {
		return ApiClient.deleteRemark(this, token, remarkId);
	}

	/**
	 * 获取点评列表
	 * 
	 */

	public RemarkList getRemarkList(String page, String pageCount)
			throws AppException {
		return ApiClient.getRemarkList(this, page, pageCount);
	}

	/**
	 * 发送在校点评
	 * 
	 */

	public Result insertRemark(String token, String content, String receivers,
			String pictures, String rates) throws AppException {
		return ApiClient.insertRemark(this, token, content, receivers,
				pictures, rates);
	}

	/**
	 * 在校点评模板列表
	 * 
	 */

	public RemarkTeplateList getRatingTplList(String token) throws AppException {
		return ApiClient.getRatingTplList(this, token);
	}

	/**
	 * 获取点评列表 家长端
	 * 
	 */

	public RemarkList getUserRemarkList(String page, String pageCount)
			throws AppException {
		return ApiClient.getUserRemarkList(this, page, pageCount);
	}

	/**
	 * 获得最新回复 在校点评详情 家长端
	 * 
	 */

	public RemarkNewCommentsDetail getRemarkFsiDataDetail(String type,
			String refId, String commentId) throws AppException {
		return ApiClient.getRemarkFsiDataDetail(this, type, refId, commentId);
	}

	/**
	 * 获得最新回复 家长心声 教师端
	 * 
	 */

	public VoiceDetail getVoiceFsiDataDetail(String type, String refId,
			String commentId) throws AppException {
		return ApiClient.getVoiceFsiDataDetail(this, type, refId, commentId);
	}

	/**
	 * 获得家长心声列表 教师端
	 * 
	 */

	public VoiceList getVoiceTeacherList(String page, String pageCount,
			String type) throws AppException {
		return ApiClient.getVoiceTeacherList(this, page, pageCount, type);
	}

	/**
	 * 获得家长心声列表 家长端
	 * 
	 */

	public VoiceList getVoiceList(String page, String pageCount)
			throws AppException {
		return ApiClient.getVoiceList(this, page, pageCount);
	}

	/**
	 * 删除家长心声
	 * 
	 */

	public Result deletevoice(String token, String voice_id)
			throws AppException {
		return ApiClient.deletevoice(this, token, voice_id);
	}

	/**
	 * 找回密码-验证用户
	 * 
	 */

	public ValidUserResult validUser(String username) throws AppException {
		return ApiClient.validUser(this, username);
	}

	/**
	 * 找回密码-发送验证码
	 * 
	 */

	public Result sendCode(String key, String type) throws AppException {
		return ApiClient.sendCode(this, key, type);
	}

	/**
	 * 找回密码-校验验证码
	 * 
	 */

	public ValidCodeResult validCode(String key, String type, String code)
			throws AppException {
		return ApiClient.validCode(this, key, type, code);
	}

	/**
	 * 找回密码-重设密码
	 * 
	 */

	public Result resetPwd(String sign, String newPassword) throws AppException {
		return ApiClient.resetPwd(this, sign, newPassword);
	}

	/**
	 * 修改个人资料-验证用户名是否重复
	 * 
	 */
	public Result checkUserName(String userName) throws AppException {
		return ApiClient.checkUserName(this, userName);
	}

	/**
	 * 新增一条家长心声
	 * 
	 */

	public Result insertvoice(String content, String pictures, String receivers)
			throws AppException {
		return ApiClient.insertvoice(this, content, pictures, receivers);
	}

	/**
	 * 获取机构和教师列表
	 * 
	 */

	public TeacherOrgList getUserToTeachers(String token) throws AppException {
		return ApiClient.getUserToTeachers(this, token);
	}

	/**
	 * 注册 获取验证码
	 * 
	 */

	public Result sendSmsCode(String mobile, String type) throws AppException {
		return ApiClient.sendSmsCode(this, mobile, type);
	}

	/**
	 * 注册 校验验证码
	 * 
	 */

	public Result validateSmsCode(String mobile, String type, String authCode)
			throws AppException {
		return ApiClient.validateSmsCode(this, mobile, type, authCode);
	}

	/**
	 * 注册
	 * 
	 */

	public Result register(String phone, String authCode, String userName,
			String pwd, String confirmPwd) throws AppException {
		return ApiClient.register(this, phone, authCode, userName, pwd,
				confirmPwd);
	}

	/**
	 * 绑定手机
	 * 
	 */

	public Result bind(String password, String mobile, String authCode)
			throws AppException {
		return ApiClient.bind(this, password, mobile, authCode);
	}

	/**
	 * 绑定 获取验证码
	 * 
	 */

	public Result sendSmsCodeBind(String mobile, String type)
			throws AppException {
		return ApiClient.sendSmsCodeBind(this, mobile, type);
	}
	
	/**
	 * 短信登陆
	 * 
	 */
	public Result sendSmsCodeLogin(String mobile, String type)
			throws AppException {
		return ApiClient.sendSmsCodeLogin(this, mobile, type);
	}
	
	/**
	 * 校验密码
	 * 
	 */

	public Result checkPass(String password, String userId) throws AppException {
		return ApiClient.checkPass(this, password, userId);
	}

	/**
	 * 绑定 校验验证码
	 * 
	 */

	public Result validateSmsBindCode(String mobile, String type,
			String authCode) throws AppException {
		return ApiClient.validateSmsBindCode(this, mobile, type, authCode);
	}

	/**
	 * 获得个人资料 家长
	 * 
	 */

	public PersonInfoDetail getPersonList(String token,String roles) throws AppException {
		return ApiClient.getPersonList(this, token,roles);
	}

	/**
	 * 验证用户
	 * 
	 */

	public ValidCodeResult validdata(String tag, String type, String checkCode)
			throws AppException {
		return ApiClient.validdata(this, tag, type, checkCode);
	}

	/**
	 * 重新绑定手机号
	 * 
	 */

	public Result rebind(String mobile, String authCode, String sign)
			throws AppException {
		return ApiClient.rebind(this, mobile, authCode, sign);
	}

	/**
	 * 修改密码
	 * 
	 */

	public Result update(String oldPwd, String password, String confirmPwd,
			String sign) throws AppException {
		return ApiClient.update(this, oldPwd, password, confirmPwd, sign);
	}

	/**
	 * 获得学生所在的班级 机构 机构下面的老师
	 * 
	 */

	public StudentTeacherOrgClassList getChildClassAndTeacher(String token)
			throws AppException {
		return ApiClient.getChildClassAndTeacher(this, token);
	}

	/**
	 * 获得教师端 未读数量提示
	 * 
	 */

	public UnReadRecvNumList getUnReadRecvNum(String token) throws AppException {
		return ApiClient.getUnReadRecvNum(this, token);
	}

	/**
	 * 编辑个人信息
	 * 
	 */

	public Result updatePersonList(PersoninfoSend personinfoSend)
			throws AppException {
		return ApiClient.updatePersonList(this, personinfoSend);
	}

	/**
	 * 获得宝宝信息列表
	 * 
	 */

	public BabyInfoList getBabyNameList(String token) throws AppException {
		return ApiClient.getBabyNameList(this, token);
	}

	/**
	 * 获得宝宝明细
	 * 
	 */

	public BabyInfoDetail getupdateBaby(String childId, String userId,
			String updatetype) throws AppException {
		return ApiClient.getupdateBaby(this, childId, userId, updatetype);
	}

	/**
	 * 获得兴趣爱好列表
	 * 
	 */

	public CatList getCat(String token) throws AppException {
		return ApiClient.getCat(this, token);
	}

	/**
	 * 编辑宝宝信息
	 * 
	 */

	public Result updateBabyList(BabyinfoSend babyinfoSend) throws AppException {
		return ApiClient.updateBabyList(this, babyinfoSend);
	}

	/**
	 * 删除宝宝
	 * 
	 */

	public Result deleteBaby(String token, String childId, String userId)
			throws AppException {
		return ApiClient.deleteBaby(this, token, childId, userId);
	}

	/**
	 * 宝宝退出机构
	 * 
	 */

	public Result exitOrg(String token, String childId, String orgId)
			throws AppException {
		return ApiClient.exitOrg(this, token, childId, orgId);
	}

	/**
	 * 获取客户端版本信息
	 * 
	 */

	public UpdateInfoDetail getClientVersion(String type) throws AppException {
		return ApiClient.getClientVersion(this, type);
	}

	/**
	 * 我的代理信息
	 */

	public MyAgentList getUserDelegation() throws AppException {
		return ApiClient.getUserDelegation(this);
	}

	/**
	 * 注册通知别名
	 * 
	 */
	public void registeredAlias(Context context,
			final SharedPreferences spPreferences, String userId) {
		// 注册jpush别名
		Log.e(TAG, "登录注册别名userid=" + userId);
		JPushInterface.setAlias(context, userId, new TagAliasCallback() {

			@Override
			public void gotResult(int status, String alias, Set<String> tags) {
				Log.i(TAG, "通知别名注册状态=" + status);
				if (status == 0) {
					Editor et = spPreferences.edit();
					et.putString(Constant.MSG_PUSH_STATUS,
							Constant.MSG_PUSH_STATUS_YES);
					et.commit();
				}
			}
		});
	}

	/**
	 * 获取机构下面的班级和教师列表
	 */

	public TeacherOrgClassList getUserClassAndTeacher(Boolean joinOrg)
			throws AppException {
		return ApiClient.getUserClassAndTeacher(this, joinOrg);
	}

	/**
	 * 我的代理信息 历史记录
	 */
	public AgentHistoryList queryDelegation(String page, String pageCount)
			throws AppException {
		return ApiClient.queryDelegation(this, page, pageCount);
	}

	/**
	 * 增加代理老师
	 * 
	 */

	public Result saveDelegation(String agentUserId, String agentTeaId,
			String endTime) throws AppException {
		return ApiClient.saveDelegation(this, agentUserId, agentTeaId, endTime);
	}

	/**
	 * 取消代理
	 * 
	 */

	public Result cancelDelegation(String delegationId) throws AppException {
		return ApiClient.cancelDelegation(this, delegationId);
	}

	public void showShare(Context context, final String content,
			final String imageUrl) {
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher, context.getResources()
				.getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// oks.setTitle(context.getResources().getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://116.255.134.227/epeiwang");

		// text是分享文本，所有平台都需要这个字段
		oks.setText(content);
		oks.setImageUrl(imageUrl);

		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://www.epeiwang.com");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(context.getResources().getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://www.epeiwang.com");

		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

			@Override
			public void onShare(Platform platform, ShareParams paramsToShare) {
				if (QQ.NAME.equals(platform.getName())) {
					if (null != content && !content.equals("")
							&& null != imageUrl && !imageUrl.equals("")) {
						paramsToShare.setText(content);
						paramsToShare.setImageUrl("");

					}
				} else if (Wechat.NAME.equals(platform.getName())
						|| WechatMoments.NAME.equals(platform.getName())
						|| WechatFavorite.NAME.equals(platform.getName())) {
					if (null != content && !content.equals("")
							&& null != imageUrl && !imageUrl.equals("")) {
						paramsToShare.setShareType(platform.SHARE_TEXT);
					}

				}

			}
		});
		// 启动分享GUI
		oks.show(context);
	}

	/**
	 * 获取当前登录用户负责的班级
	 * 
	 */
	public OrgList getOrgClasses() throws AppException {
		return ApiClient.getOrgClasses(this);
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
	 */
	public void showShare(Context context, String orgId, String refId,
			String type, String content, String imageUrl) {

		try {
			ShareUtils.share(context, orgId, refId, type, content, imageUrl);
		} catch (Exception e) {
			UIHelper.ToastMessage(context, "分享失败，稍后再试吧");
			e.printStackTrace();
		}
	}

	public TeacherOrgClassList getDelegationClassAndTeacher(Boolean joinOrg)
			throws AppException {
		return ApiClient.getDelegationClassAndTeacher(this, joinOrg);
	}

	/**
	 * 进行学员变动
	 * 
	 */
	public Result saveStuChgState(String stuChgList) throws AppException {
		return ApiClient.saveStuChgState(this, stuChgList);
	}

	/**
	 * 分享 带分享类型参数
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
	 *            分享类型0 普通的分享，1分享有奖
	 * @param title
	 *            分享标题
	 */
	public void showShare(Context context, String orgId, String refId,
			String type, String content, String imageUrl, int eventType,String title) {

		try {
			ShareUtils.share(context, orgId, refId, type, content, imageUrl,
					eventType,title);
		} catch (Exception e) {
			UIHelper.ToastMessage(context, "分享失败，稍后再试吧");
			e.printStackTrace();
		}
	}

	/**
	 * 营销活动首页 列表
	 * 
	 */

	public MarketingList queryMarketingList(String page, String pageCount)
			throws AppException {
		return ApiClient.queryMarketingList(this, page, pageCount);
	}
	
	/**
	 * 课程详情
	 * 
	 * @param page
	 * @param pageCount
	 * @return ,
	 * @throws AppException
	 */
	public CourseParticularsBean queryCourseParticulars(String courId,String ownOrgId,String userId)
			throws AppException {
		return ApiClient.queryCourseParticulars(this,courId,ownOrgId,userId);
	}
	
	/**
	 * 
	 * 课程搜索列表
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public BaseBean queryCourseList(String page, String pageCount,String keyword,String lat,String log,String sort,String filter,String searchType)
			throws AppException {
		return ApiClient.queryCourseList(this, page, pageCount,keyword,lat,log,sort,filter,searchType);
	}
	
	/**
	 * 立即报名
	 * 
	 * @param phone
	 * @param userId
	 * @param courseId
	 * @return
	 * @throws AppException
	 */
	public CourseApplyBean courseApply(String phone, String userId,String courseId,String message,String ownOrgId)
			throws AppException {
		return ApiClient.courseApply(this, phone, userId, courseId,message,ownOrgId);
	}
	
	/**
	 * 我的消息，系统回复
	 * 
	 * @param phone
	 * @param userId
	 * @param courseId
	 * @return
	 * @throws AppException
	 */
	public MySendMessageBean sendMessage(String recieveId, String content)
			throws AppException {
		return ApiClient.sendMessage(this, recieveId, content);
	}
	
	/**
	 * 营销 热门活动
	 */

	public MarketingHotList queryMarketingHotList() throws AppException {
		return ApiClient.queryMarketingHotList(this);
	}

	public boolean containsProperty(String key) {
		Properties props = getProperties();
		return props.containsKey(key);
	}

	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return AppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	public String getProperty(String key) {
		return AppConfig.getAppConfig(this).get(key);
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

	public BaseBean getFsiBeanDataDetail1(String type, String refId,
			String commentId) throws AppException {
		return ApiClient.getFsiBeanDataDetail1(this, type, refId, commentId);
	}

	public ShareResult share(String refId, String type, String smpType,
			String orgId, String opt) throws AppException {
		return ApiClient.share(this, refId, type, smpType, orgId, opt);
	}

	public OrgInfo getOrgDetail(String orgId) throws AppException {
		return ApiClient.orgDetail(this, orgId);
	}

	public FavorList getFavList(String page, String pageCount)
			throws AppException {
		return ApiClient.getFavList(this, page, pageCount);
	}

	public ShareResult cancelShare(String shareId) throws AppException {
		return ApiClient.cancelShare(this, shareId);
	}
	
	public OrgIntroWrapper getOrgIntroDetail(String orgId) throws AppException {
		return ApiClient.getOrgIntroDetail(this, orgId);
	}
	
	/**
	 * 上传通讯录
	 * 
	 * @param isAllow
	 * @param contacts
	 * @return
	 * @throws AppException
	 */
	public CircleofFriendsBean getPhoneDetail(String isAllow,String contacts) throws AppException {
		return ApiClient.synPhoneContacts(this, isAllow,contacts);
	}
	
	/**
	 * 获取朋友圈评论列表
	 * 
	 * @param page
	 * @param pageCount
	 * @param contestId
	 * @param isDisplayPic
	 * @param userId
	 * @return
	 * @throws AppException
	 */
	public FriendsCommentBean queryFriendsCommentList(String page, String pageCount,String lat,String lon,String tag,String userId)
			throws AppException {
		return ApiClient.queryFriendsCommentList(this, page, pageCount,lat,lon,tag,userId);
	}
	
	/**
	 * 感谢
	 * 
	 * @param commentId
	 * @param newFlag
	 * @return
	 * @throws AppException
	 */
	public FriendsHelpfulBase queryFriendsHelpfulBase(String commentId,String newFlag)
			throws AppException {
		return ApiClient.queryFriendsHelpfulBase(this, commentId, newFlag);
	}

	/**
	 * 输入机构、课程建议
	 * 
	 * @param searchType
	 * @param keyword
	 * @return
	 * @throws AppException
	 */
	public SuggestNameWrapper getInputSuggest(String searchType,
			String keyword, String orgId)
			throws AppException {
		return ApiClient.getInputSuggest(this, searchType, keyword, orgId);
	}

	/**
	 * 课程咨询数+1
	 * 
	 * @param courId
	 * @return
	 */
	public Result StatCourseCounselNum(String courId) throws AppException {
		return ApiClient.StatCourseCounselNum(this, courId);
	}

	/**
	 * 发送课程评价
	 * 
	 * @param mapData
	 * @return
	 * @throws AppException
	 */
	public Result sendCourseComment(Map<String, String> mapData)
			throws AppException {
		return ApiClient.sendCourseComment(this, mapData);
	}

	public FriendsCommentBean QueryCourseComment(String page, String pageCount, String orgId, String courId,String userId)
			throws AppException {
		return ApiClient.QueryCourseComment(this,page, pageCount, orgId, courId,userId);
	}

	/**
	 * 输入课程建议
	 * 
	 * @param orgId
	 * @return
	 */
	public SuggestNameWrapper getSuggestCourse(String orgId)
			throws AppException {
		return ApiClient.getSuggestCourse(this, orgId);
	}

	public BaseBean deleteCourseComment(String commentId) throws AppException {
		return ApiClient.deleteCourseComment(this, commentId);
	}

}
