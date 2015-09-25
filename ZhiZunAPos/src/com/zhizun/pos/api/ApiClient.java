package com.zhizun.pos.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.ch.epw.bean.send.BabyinfoSend;
import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.bean.send.PersoninfoSend;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
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
import com.zhizun.pos.bean.CourseListViewBean;
import com.zhizun.pos.bean.CourseParticularsBean;
import com.zhizun.pos.bean.DynamicTeacherList;
import com.zhizun.pos.bean.FavorList;
import com.zhizun.pos.bean.FriendsCommentBean;
import com.zhizun.pos.bean.FriendsHelpfulBase;
import com.zhizun.pos.bean.HomeworkNewcommentsDetail;
import com.zhizun.pos.bean.HomeworkTeacherList;
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
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRoleList;
import com.zhizun.pos.bean.ValidCodeResult;
import com.zhizun.pos.bean.ValidUserResult;
import com.zhizun.pos.bean.VoiceDetail;
import com.zhizun.pos.bean.VoiceList;
import com.zhizun.pos.main.bean.GetCodeBean;

/**
 * API客户端接口：用于访问网络数据
 * 
 * @author yangzhenwei
 * 
 */
public class ApiClient {

	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";

	private final static int TIMEOUT_CONNECTION = 15000;
	private final static int TIMEOUT_SOCKET = 15000;
	private final static int RETRY_TIME = 3;
	private static final String TAG = "com.ch.epw.api.ApiClient";

	private static String appUserAgent;
	private static String appCookie;

	public static void cleanCookie() {
		appCookie = "";
	}

	private static String getCookie(AppContext appContext) {
		if (appCookie == null || appCookie == "") {
			appCookie = appContext.getProperty("cookie");
		}
		Log.i(TAG, "cookie=" + appCookie);
		return appCookie;
	}

	private static String getUserAgent(AppContext appContext) {
		if (appUserAgent == null || appUserAgent == "") {

			StringBuilder ua = new StringBuilder("epeiwang.com");
			ua.append('/' + appContext.getPackageInfo().versionName + '_'
					+ appContext.getPackageInfo().versionCode);// App版本
			ua.append("/Android");// 手机系统平台
			ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
			ua.append("/" + android.os.Build.MODEL); // 手机型号
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}

	private static DefaultHttpClient getHttpClient() {

		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION); // 设置连接超时
		HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET); // 设置请求超时

		// 设置 字符集
		HttpProtocolParams.setContentCharset(params, UTF_8);

		return new DefaultHttpClient(params);
	}

	private static HttpGet getHttpGet(String url, String cookie,
			String userAgent) {
		HttpGet httpGet = new HttpGet(url);
		// 设置 请求超时时间
		// httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.addHeader("Host", URLs.EPEIWANG_URL);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("User-Agent", userAgent);
		httpGet.addHeader("Cookie", cookie);
		return httpGet;
	}

	private static HttpPost getHttpPost(String url, String cookie,
			String userAgent) {

		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader("Host", URLs.EPEIWANG_URL);
//		httpPost.addHeader("Connection", "Keep-Alive");
//		httpPost.addHeader("User-Agent", userAgent);
//		httpPost.addHeader("Cookie", cookie);
		Log.i(TAG, "url=" + url);
		return httpPost;
	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	private static String http_get(AppContext appContext, String url)
			throws AppException {
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String responseBody = "";
		try {
			httpClient = getHttpClient();
			httpGet = getHttpGet(url, cookie, userAgent);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw AppException.http(statusCode);
			}
			responseBody = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			throw AppException.network(e);
		} finally {
			// 释放连接
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}

		return responseBody;
	}

	// /**
	// * 公用post方法 以流的方式传递参数
	// *
	// * @param url
	// * @param params
	// * @param files
	// * @throws AppException
	// */
	// private static String _post(AppContext appContext, String url,
	// Map<String, Object> params) throws AppException {
	// String cookie = getCookie(appContext);
	// String userAgent = getUserAgent(appContext);
	// JSONObject json = null;
	// DefaultHttpClient httpClient = null;
	// HttpPost httpPost = null;
	// // map转换成json对象
	// if (params != null) {
	// json = new JSONObject(params);
	// Log.i(TAG, "map转成josn对象为：" + json.toString());
	// }
	//
	// String responseBody = "";
	// try {
	// httpClient = getHttpClient();
	// httpPost = getHttpPost(url, cookie, userAgent);
	// if (null != json) {
	// httpPost.setEntity(new StringEntity(json.toString(), "UTF-8"));
	// }
	// HttpResponse response = httpClient.execute(httpPost);
	// int statusCode = response.getStatusLine().getStatusCode();
	// if (statusCode != HttpStatus.SC_OK) {
	// throw AppException.http(statusCode);
	// } else if (statusCode == HttpStatus.SC_OK) {
	// CookieStore mCookieStore = httpClient.getCookieStore();
	// List<Cookie> cookies = mCookieStore.getCookies();
	//
	// String tmpcookies = "";
	// for (Cookie ck : cookies) {
	// tmpcookies += ck.getValue() + ";";
	// }
	// Log.i(TAG, "tmpcookies=" + tmpcookies);
	// // 保存cookie
	// if (appContext != null && tmpcookies != "") {
	// appContext.setProperty("cookie", tmpcookies);
	// appCookie = tmpcookies;
	// }
	// }
	// responseBody = EntityUtils.toString(response.getEntity());
	//
	// } catch (IOException e) {
	// // 发生网络异常
	// e.printStackTrace();
	// throw AppException.network(e);
	// } finally {
	// // 释放连接
	// httpClient.getConnectionManager().shutdown();
	// httpClient = null;
	// }
	//
	// return responseBody;
	// }

	/**
	 * 公用post方法 模拟form提交
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private static String _post(AppContext appContext, String url,
			Map<String, Object> params) throws AppException {
		Log.i(TAG, String.valueOf(params));
		
//		String cookie = getCookie(appContext);
		SharedPreferences sp = appContext.getSharedPref("appToken");
		String token = sp.getString("token", "");
		if (!token.equals("")) {
			params.put("hashcode", token);
		}
//		String userAgent = getUserAgent(appContext);
		DefaultHttpClient httpClient = null;
		HttpPost httpPost = null;
		List<NameValuePair> formParams = null;
		if (params != null) {
			formParams = new ArrayList<NameValuePair>(); // 创建参数队列
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue().toString().trim()));

			}
		}

		String responseBody = "";
		try {
			httpClient = getHttpClient();
			if (formParams != null) {
//				httpPost = getHttpPost(url, cookie, userAgent);
				httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			}
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			Log.i(TAG, "statusCode" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode == 401) {
					responseBody = EntityUtils.toString(response.getEntity());
					AppContext.getApp().handleTokenExpired(responseBody);
					Log.i(TAG, "responseBody=" + responseBody);
					return null;
				} else {
					throw AppException.http(statusCode);
				}

			} else if (statusCode == HttpStatus.SC_OK) {
				CookieStore mCookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = mCookieStore.getCookies();
				Log.i(TAG, "cookies.size()=" + cookies.size());
				String tmpcookies = "";
				if (!cookies.isEmpty()) {
					for (Cookie ck : cookies) {
						tmpcookies += ck.getName() + "=" + ck.getValue() + ";";
					}
				}
				Log.i(TAG, "tmpcookies=" + tmpcookies);
				// 保存cookie
				appContext.setProperty("cookie", tmpcookies);
				appCookie = tmpcookies;

			}
			responseBody = EntityUtils.toString(response.getEntity());
			Log.i(TAG, "responseBody=" + responseBody);
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			throw AppException.network(e);

		} finally {
			// 释放连接
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}

		return responseBody;
	}

	/**
	 * 获取网络图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url) throws AppException {
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		Bitmap bitmap = null;

		try {
			httpClient = getHttpClient();
			httpGet = getHttpGet(url, null, null);
			Log.i(TAG, "url=" + url);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			Log.i(TAG, "statusCode=" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				throw AppException.http(statusCode);
			}
			InputStream inStream = new ByteArrayInputStream(
					EntityUtils.toByteArray(response.getEntity()));
			bitmap = BitmapFactory.decodeStream(inStream);
			inStream.close();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
			throw AppException.network(e);
		} finally {
			// 释放连接
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}

		return bitmap;
	}

	/**
	 * 获取登录信息
	 * 
	 * @author 李林中 2014-12-1 下午12:57:32
	 * @param appContext
	 * @param j_password
	 * @param j_username
	 * @return
	 * @throws AppException
	 */

	public static UserLogin loginVerify(AppContext appContext,
			String j_username, String j_password,String j_loginType) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("j_loginType", j_loginType);
		map.put("j_username", j_username);
		map.put("j_password", j_password);
		try {
			UserLogin userLogin = UserLogin.parse(_post(appContext,
					URLs.GETLOGINVERIFY_URL, map));
			if (userLogin != null && userLogin.getUserInfo() != null) {
				UserInfo user = userLogin.getUserInfo();
				user.setLoginName(j_username);
				user.setLoginPseudoCode(Constant.USER_PWD_PSEUDO_CODE
						.substring(0, j_password.length()));
			}
			return userLogin;
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 立即报名信息
	 * 
	 * @author 李林中
	 * @param appContext
	 * @param j_password
	 * @param j_username
	 * @return
	 * @throws AppException
	 */

	public static CourseApplyBean courseApply(AppContext appContext,
			String phone, String userId, String courseId, String message,
			String ownOrgId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("userId", userId);
		map.put("courseId", courseId);
		map.put("message", message);
		map.put("ownOrgId", ownOrgId);
		try {
			CourseApplyBean courseApplyBean = CourseApplyBean.parse(_post(
					appContext, URLs.COURSE__APPLY_URL, map));
			return courseApplyBean;
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 我的消息，回复系统消息
	 * 
	 * @param appContext
	 * @param phone
	 * @param userId
	 * @param courseId
	 * @param message
	 * @param ownOrgId
	 * @return
	 * @throws AppException
	 */
	public static MySendMessageBean sendMessage(AppContext appContext,
			String recieveId, String content) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!recieveId.equals("")) {
			map.put("recieveId", recieveId);
		}
		map.put("content", content);
		try {
			MySendMessageBean courseApplyBean = MySendMessageBean.parse(_post(
					appContext, URLs.SEND__MESSAGE_URL, map));
			return courseApplyBean;
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得我的在校动态 家长
	 * 
	 * @author 李林中 2014-12-4 下午5:11:12
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static DynamicTeacherList getMyDynamic(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return DynamicTeacherList.parse(_post(appContext,
					URLs.GETMYDYNAMIC_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得我的在校动态 老师
	 * 
	 * @author 李林中 2014-12-4 下午5:11:12
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static DynamicTeacherList getTeacherMyDynamic(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return DynamicTeacherList.parse(_post(appContext,
					URLs.GETTEACHERDYNAMIC_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 回复动态或评论
	 * 
	 * @author 李林中 2014-12-9 下午12:18:17
	 * @param appContext
	 * @param commentsSend
	 * @return
	 * @throws AppException
	 */
	public static CommentsReply replyComments(AppContext appContext,
			CommentsSend commentsSend) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", commentsSend.getToken());
		map.put("refId", commentsSend.getRefId());
		map.put("type", commentsSend.getType());
		map.put("content", commentsSend.getContent());
		map.put("replyCommentId", commentsSend.getReplyCommentId());
		map.put("targetUserId", commentsSend.getTargetUserId());

		try {
			return CommentsReply.parse(_post(appContext,
					URLs.REPLYCOMMENTS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除动态
	 * 
	 * @author 李林中 2014-12-20 下午9:05:12
	 * @param appContext
	 * @param token
	 * @param dynamicId
	 * @return
	 * @throws AppException
	 */
	public static Result deleteDynamic(AppContext appContext, String token,
			String dynamicId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("dynamicId", dynamicId);
		try {
			return Result.parse(_post(appContext, URLs.DELETEDYNAMIC_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除评论
	 * 
	 * @author 李林中 2014-12-20 下午9:05:12
	 * @param appContext
	 * @param token
	 * @param commnetId
	 * @return
	 * @throws AppException
	 */
	public static Result deleteComment(AppContext appContext, String token,
			String commentId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("commentId", commentId);
		try {
			return Result
					.parse(_post(appContext, URLs.DELETECOMMENTS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得班级和班级学生
	 * 
	 * @author 李林中 2014-12-21 下午6:33:09
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */
	public static StudentClassList getStudentClassList(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);

		try {
			return StudentClassList.parse(_post(appContext,
					URLs.GETUSERCLASSANDSTUDENT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得我收到的邀请
	 * 
	 * @author 李林中 2014-12-17 下午3:07:41
	 * @param appContext
	 * @param token
	 * @param _page_
	 * @param _page_count_
	 * @return
	 * @throws AppException
	 */
	public static ReceiveMyInvitationList getReceiveMyInvitationList(
			AppContext appContext, String token, String _page_,
			String _page_count_) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("_page_", _page_);
		map.put("_page_count_", _page_count_);

		try {
			return ReceiveMyInvitationList.parse(_post(appContext,
					URLs.GETUSERINVITE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得我发出的邀请
	 * 
	 * @author 李林中 2014-12-17 下午3:07:41
	 * @param appContext
	 * @param token
	 * @param _page_
	 * @param _page_count_
	 * @return
	 * @throws AppException
	 */
	public static ReceiveMyInvitationList getSendReceiveMyInvitationList(
			AppContext appContext, String token, String _page_,
			String _page_count_) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("_page_", _page_);
		map.put("_page_count_", _page_count_);

		try {
			return ReceiveMyInvitationList.parse(_post(appContext,
					URLs.GETUSERSENDINVITE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 接受邀请
	 * 
	 * @author 李林中 2014-12-17 下午5:44:25
	 * @param appContext
	 * @param token
	 * @param orgInviteId
	 * @param childId
	 * @param stuId
	 * @return
	 * @throws AppException
	 */
	public static Result recevieInvite(AppContext appContext, String token,
			String orgInviteId, String childId, String stuId)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("orgInviteId", orgInviteId);
		map.put("childId", childId);
		map.put("stuId", stuId);
		try {
			return Result.parse(_post(appContext, URLs.ACCEPTINVITE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 拒绝邀请
	 * 
	 * @author 李林中 2014-12-17 下午5:47:06
	 * @param appContext
	 * @param token
	 * @param orgInviteId
	 * @return
	 * @throws AppException
	 */
	public static Result refuseInvite(AppContext appContext, String token,
			String orgInviteId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("orgInviteId", orgInviteId);

		try {
			return Result.parse(_post(appContext, URLs.REJECT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 收藏动态
	 * 
	 * @author 李林中 2014-12-22 下午7:46:30
	 * @param appContext
	 * @param token
	 * @param refId
	 * @param type
	 * @param isCancle
	 * @return
	 * @throws AppException
	 */

	public static Result fav(AppContext appContext, String token, String refId,
			String type, String isCancle) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("refId", refId);
		map.put("type", type);
		map.put("isCancle", isCancle);

		try {
			return Result.parse(_post(appContext, URLs.FAV_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 赞
	 * 
	 * @author 李林中 2014-12-24 下午2:54:08
	 * @param appContext
	 * @param token
	 * @param refId
	 * @param isCancle
	 * @return
	 * @throws AppException
	 */

	public static LikeResult like(AppContext appContext, String token,
			String refId, String type, String isCancle) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("refId", refId);
		map.put("type", type);
		map.put("isCancle", isCancle);

		try {
			return LikeResult.parse(_post(appContext, URLs.LIKE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 系统消息数
	 */
	public static SystemMessageNumBean systemnum(AppContext appContext) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			return SystemMessageNumBean.parse(_post(appContext, URLs.SYSTEM__MESSAGE_GETUNREADMSGNUM_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 发送动态
	 * 
	 * @author 李林中 2014-12-23 下午4:36:41
	 * @param appContext
	 * @param token
	 * @param content
	 * @param receivers
	 * @param pictures
	 * @return
	 * @throws AppException
	 */

	public static Result insertdynamic(AppContext appContext, String token,
			String content, String receivers, String pictures)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("content", content);
		map.put("receivers", receivers);
		map.put("pictures", pictures);

		try {
			return Result.parse(_post(appContext, URLs.INSERTDYNAMIC_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取当前用户的宝宝
	 * 
	 * @author 李林中 2014-12-18 下午8:09:11
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */
	public static UserChildInfoList getUserChildInfoList(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);

		try {
			return UserChildInfoList.parse(_post(appContext,
					URLs.GETUSERCHILDS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取当前用户角色
	 * 
	 * @author 李林中 2014-12-18 下午8:09:11
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */
	public static UserRoleList getUserRoleList(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);

		try {
			return UserRoleList
					.parse(_post(appContext, URLs.GETROLES_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 切换角色
	 * 
	 * @author 李林中 2014-12-25 下午9:29:43
	 * @param appContext
	 * @param token
	 * @param role
	 * @param organId
	 * @return
	 * @throws AppException
	 */

	public static Result switchRole(AppContext appContext, String token,
			String role, String organId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("role", role);
		map.put("organId", organId);
		try {
			return Result.parse(_post(appContext, URLs.SWITCHROLE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 得到最新回复
	 * 
	 * @author 李林中 2014-12-26 下午5:03:29
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */
	public static NewCommentsList getDynamicNewComments(AppContext appContext,
			String token) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return NewCommentsList.parse(_post(appContext,
					URLs.GETDYNAMICNEWCOMMENTS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得历史回复
	 * 
	 * @author 李林中 2014-12-17 下午3:07:41
	 * @param appContext
	 * @param token
	 * @param _page_
	 * @param _page_count_
	 * @return
	 * @throws AppException
	 */
	public static OldCommentsList getDynamicOldComments(AppContext appContext,
			String token, String _page_, String _page_count_)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("_page_", _page_);
		map.put("_page_count_", _page_count_);

		try {
			return OldCommentsList.parse(_post(appContext,
					URLs.GETDYNAMICOLDCOMMENTS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得通知模版标签列表
	 * 
	 * @author 李林中 2014-12-30 上午11:03:15
	 * @param appContext
	 * @return
	 * @throws AppException
	 */
	public static String getTplTagList(AppContext appContext)
			throws AppException {

		try {
			return _post(appContext, URLs.GETTPLTAGLIST_URL, null);
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得通知模板列表
	 * 
	 * @author 李林中 2014-12-30 上午11:26:50
	 * @param appContext
	 * @param page
	 * @param page_count
	 * @param tag
	 * @return
	 * @throws AppException
	 */

	public static NoticeTempleteList queryNoticeTplList(AppContext appContext,
			String page, String page_count, String tag) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", page_count);
		map.put("tag", tag);
		try {
			return NoticeTempleteList.parse(_post(appContext,
					URLs.QUERYNOTICETPLLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除通知模板
	 * 
	 * @author 李林中 2014-12-30 上午11:31:35
	 * @param appContext
	 * @param token
	 * @param templateId
	 * @return
	 * @throws AppException
	 */
	public static Result delNoticeTpl(AppContext appContext, String token,
			String templateId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("templateId", templateId);

		try {
			return Result.parse(_post(appContext, URLs.DELNOTICETPL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 发送通知
	 * 
	 * @author 李林中 2014-12-31 下午5:20:47
	 * @param appContext
	 * @param token
	 * @param content
	 * @param receivers
	 * @param isSendMsg
	 * @param sendMode
	 * @param tasktime
	 * @return
	 * @throws AppException
	 */

	public static Result sendNotice(AppContext appContext, String token,
			String content, String receivers, String isSendMsg,
			String sendMode, String taskTime) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("content", content);
		map.put("receivers", receivers);
		map.put("isSendMsg", isSendMsg);
		map.put("sendMode", sendMode);
		map.put("taskTime", taskTime);

		try {
			return Result.parse(_post(appContext, URLs.SENDNOTICE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得通知发送的收件箱列表
	 * 
	 * @author 李林中 2015-1-4 下午4:22:08
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static NoticeBoxList queryInBoxList(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return NoticeBoxList.parse(_post(appContext,
					URLs.QUERYINBOXLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得通知发送的发件箱列表
	 * 
	 * @author 李林中 2015-1-4 下午4:23:21
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static NoticeBoxList queryOutBoxList(AppContext appContext,
			String page, String pageCount, String status) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("status", status);

		try {
			return NoticeBoxList.parse(_post(appContext,
					URLs.QUERYOUTBOXLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取有奖活动 分享有奖
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static MyepePrizedBeanList queryShareList(AppContext appContext,
			String page, String pageCount, String mktgtype) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("mktgtype", mktgtype);
		try {

			MyepePrizedBeanList prizeBeans = MyepePrizedBeanList.parse(_post(
					appContext, URLs.PRIZED_URL, map));

			prizeBeans.setMktType(mktgtype);
			return prizeBeans;

		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 活动详情页面 请求接口
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static PrizedBeanWrapper queryEvent(AppContext appContext,
			String eventId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventId", eventId);
		try {
			return PrizedBeanWrapper.parse(_post(appContext, URLs.EVENTID_URL,
					map));

		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 营销活动首页 列表
	 * 
	 * @author 李林中 2015-5-12 下午4:15:05
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static MarketingList queryMarketingList(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return MarketingList.parse(_post(appContext,
					URLs.PRIZED_INDEX_MARKETING_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 课程搜索列表
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public static BaseBean queryCourseList(AppContext appContext, String page,
			String pageCount, String keyword, String lat, String lon,
			String sort, String filter, String searchType) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("keyword", keyword == null ? "" : keyword);
		map.put("lat", lat == null ? "" : lat);
		map.put("lon", lon == null ? "" : lon);
		map.put("sort", sort == null ? "" : sort);
		map.put("filter", filter == null ? "" : filter);
		map.put("searchType", searchType == null ? "" : searchType);
		try {
			String result = _post(appContext, URLs.COURSE_URL, map);
			if (searchType != null) {
				if (searchType.equals("2")) { // 找活动，按照活动列表进行json解析
					return MarketingList.parse(result);
				} else { // 否则，按照课程/机构列表进行解析
					return CourseListViewBean.parse(result);
				}
			}
			return null;
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 课程详情
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public static CourseParticularsBean queryCourseParticulars(
			AppContext appContext, String courId, String ownOrgId,String userId)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courId", courId);
		map.put("ownOrgId", ownOrgId);
		map.put("userId", userId == null ? "" : userId);
		try {
			return CourseParticularsBean.parse(_post(appContext,
					URLs.COURSE__DETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 营销 热门活动
	 * 
	 * @author 李林中 2015-5-13 下午5:51:08
	 * @param appContext
	 * @return
	 * @throws AppException
	 */

	public static MarketingHotList queryMarketingHotList(AppContext appContext)
			throws AppException {
		try {
			return MarketingHotList.parse(_post(appContext,
					URLs.PRIZED_INDEX_HOT_URL, null));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取有奖活动 分享有奖 参与详情
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static MyPrizedParticipant queryParticList(AppContext appContext,
			String eventId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventId", eventId);
		try {
			return MyPrizedParticipant.parse(_post(appContext,
					URLs.PRIZED_PARTICIPATION_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取有奖活动 推荐有奖 参与详情
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static MyPrizedRecommendationBean queryRecommendList(
			AppContext appContext, String eventId, String pageCount, String page)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("eventId", eventId);
		try {
			return MyPrizedRecommendationBean.parse(_post(appContext,
					URLs.PRIZED_RECOMMEND_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 设置通知为已读
	 * 
	 * @author 李林中 2015-1-5 下午6:39:11
	 * @param appContext
	 * @param token
	 * @param noticeId
	 * @return
	 * @throws AppException
	 */

	public static Result setNoticeRead(AppContext appContext, String token,
			String noticeId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("noticeId", noticeId);
		try {
			return Result.parse(_post(appContext, URLs.SETNOTICEREAD_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除发送通知
	 * 
	 * @author 李林中 2015-1-5 下午6:39:11
	 * @param appContext
	 * @param token
	 * @param noticeId
	 * @return
	 * @throws AppException
	 */

	public static Result delSentNotice(AppContext appContext, String token,
			String noticeId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("noticeId", noticeId);
		try {
			return Result.parse(_post(appContext, URLs.DELSENTNOTICE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 查看已发通知明细
	 * 
	 * @author 李林中 2015-1-6 下午4:43:45
	 * @param appContext
	 * @param token
	 * @param noticeId
	 * @return
	 * @throws AppException
	 */
	public static NoticeSendBoxDetail getNoticeDetail(AppContext appContext,
			String token, String noticeId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("noticeId", noticeId);
		try {
			return NoticeSendBoxDetail.parse(_post(appContext,
					URLs.GETNOTICEDETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 退出登录
	 * 
	 * @author 李林中 2015-1-7 下午4:41:09
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static Result logOut(AppContext appContext, String token)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return Result.parse(_post(appContext, URLs.LOGOUT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除家庭作业
	 * 
	 * @author 李林中 2015-1-9 上午10:48:31
	 * @param appContext
	 * @param token
	 * @param homeworkId
	 * @return
	 * @throws AppException
	 */

	public static Result deleteHomework(AppContext appContext, String token,
			String homeworkId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("homeworkId", homeworkId);
		try {
			return Result
					.parse(_post(appContext, URLs.DELETEHOMEWORK_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得家庭作业列表
	 * 
	 * @author 李林中 2015-1-9 上午11:26:46
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static HomeworkTeacherList getHomeworkList(AppContext appContext,
			String page, String pageCount, String type) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("type", type);

		try {
			return HomeworkTeacherList.parse(_post(appContext,
					URLs.GETHOMEWORKLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 布置作业
	 * 
	 * @author 李林中 2015-1-9 下午3:33:42
	 * @param appContext
	 * @param token
	 * @param content
	 * @param receivers
	 * @param isSendMsg
	 * @param sendMode
	 * @param taskTime
	 * @return
	 * @throws AppException
	 */
	public static Result assignHomework(AppContext appContext, String token,
			String content, String receivers, String isSendMsg,
			String sendMode, String taskTime) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("content", content);
		map.put("receivers", receivers);
		map.put("isSendMsg", isSendMsg);
		map.put("sendMode", sendMode);
		map.put("taskTime", taskTime);

		try {
			return Result
					.parse(_post(appContext, URLs.ASSIGNHOMEWORK_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得家庭作业列表 家长
	 * 
	 * @author 李林中 2015-1-9 上午11:26:46
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static HomeworkTeacherList getUserHomeworkList(
			AppContext appContext, String page, String pageCount)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return HomeworkTeacherList.parse(_post(appContext,
					URLs.GETUSERHOMEWORKLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复动态详情 教师端
	 * 
	 * @author 李林中 2015-1-10 下午2:39:37
	 * @param appContext
	 * @param refId
	 * @return
	 * @throws AppException
	 */

	public static NewCommentsDetail getFsiDynamicAndComments(
			AppContext appContext, String refId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("refId", refId);

		try {
			return NewCommentsDetail.parse(_post(appContext,
					URLs.GETFSIDYNAMICANDCOMMENTS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复动态详情 家长端
	 * 
	 * @author 李林中 2015-1-13 下午7:55:43
	 * @param appContext
	 * @param type
	 * @param refId
	 * @param commentId
	 * @return
	 * @throws AppException
	 */

	public static NewCommentsDetail getFsiDataDetail(AppContext appContext,
			String type, String refId, String commentId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("refId", refId);
		map.put("commentId", commentId);
		try {
			return NewCommentsDetail.parse(_post(appContext,
					URLs.GETFSIDATADETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复家庭作业详情 家长端
	 * 
	 * @author 李林中 2015-1-13 下午7:55:43
	 * @param appContext
	 * @param type
	 * @param refId
	 * @param commentId
	 * @return
	 * @throws AppException
	 */

	public static HomeworkNewcommentsDetail getHomeworkFsiDataDetail(
			AppContext appContext, String type, String refId, String commentId)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("refId", refId);
		map.put("commentId", commentId);
		try {
			return HomeworkNewcommentsDetail.parse(_post(appContext,
					URLs.GETFSIDATADETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得邀请数量
	 * 
	 * @author 李林中 2015-1-10 下午4:41:19
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static InviteCount getInviteCount(AppContext appContext, String token)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);

		try {
			return InviteCount.parse(_post(appContext, URLs.GETINVITECOUNT_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复数量
	 * 
	 * @author 李林中 2015-1-10 下午4:41:19
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static NewCommentsCount getNewCommnetsCount(AppContext appContext,
			String token, String type) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("type", type);

		try {
			return NewCommentsCount.parse(_post(appContext,
					URLs.GETLATESTCOMMENTREPLYNUM_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得通用最新回复列表
	 * 
	 * @author 李林中 2015-1-13 下午5:01:06
	 * @param appContext
	 * @param token
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static NewCommonCommentsList getLatestCommentReplyList(
			AppContext appContext, String token, String page, String pageCount,
			String type) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("type", type);
		try {
			return NewCommonCommentsList.parse(_post(appContext,
					URLs.GETLATESTCOMMENTREPLYLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得考勤列表 教师端
	 * 
	 * @author 李林中 2015-1-14 下午9:13:04
	 * @param appContext
	 * @param token
	 * @param chkDay
	 * @return
	 * @throws AppException
	 */

	public static AttenceList getAttenceListTeacherByDay(AppContext appContext,
			String token, String chkDay) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("chkDay", chkDay);
		try {
			return AttenceList.parse(_post(appContext,
					URLs.GETATTENCELISTTEACHERBYDAY_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除考勤记录
	 * 
	 * @author 李林中 2015-1-9 上午10:48:31
	 * @param appContext
	 * @param token
	 * @param attenceId
	 * @return
	 * @throws AppException
	 */

	public static Result delAttence(AppContext appContext, String token,
			String attenceId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("attenceId", attenceId);
		try {
			return Result.parse(_post(appContext, URLs.DELATTENCE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 编辑考勤记录
	 * 
	 * @author 李林中 2015-1-16 下午12:08:09
	 * @param appContext
	 * @param token
	 * @param attenceId
	 * @param claId
	 * @param claName
	 * @param expendedChourNum
	 * @param attenceDetail
	 * @return
	 * @throws AppException
	 */

	public static Result saveAttence(AppContext appContext, String token,
			String attenceId, String claId, String claName,
			String expendedChourNum, String attenceDetail) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("attenceId", attenceId);
		map.put("claId", claId);
		map.put("claName", claName);
		map.put("expendedChourNum", expendedChourNum);
		map.put("attenceDetail", attenceDetail);
		try {
			return Result.parse(_post(appContext, URLs.SAVEATTENCE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 增加考勤记录
	 * 
	 * @author 李林中 2015-1-17 下午8:35:45
	 * @param appContext
	 * @param token
	 * @param attenceId
	 * @param claId
	 * @param claName
	 * @param expendedChourNum
	 * @param attenceDetail
	 * @param attenceDate
	 * @param attenceTime
	 * @return
	 * @throws AppException
	 */

	public static Result saveAttence(AppContext appContext, String token,
			String attenceId, String claId, String claName,
			String expendedChourNum, String attenceDetail, String attenceDate,
			String attenceTime) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("attenceId", attenceId);
		map.put("claId", claId);
		map.put("claName", claName);
		map.put("expendedChourNum", expendedChourNum);
		map.put("attenceDetail", attenceDetail);
		map.put("attenceDate", attenceDate);
		map.put("attenceTime", attenceTime);

		try {
			return Result.parse(_post(appContext, URLs.SAVEATTENCE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 家长查看考勤列表
	 * 
	 * @author 李林中 2015-1-17 上午11:53:01
	 * @param appContext
	 * @param token
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public static AttenceDetailParentList getAttenceListParent(
			AppContext appContext, String token, String page, String pageCount)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return AttenceDetailParentList.parse(_post(appContext,
					URLs.GETATTENCELISTPARENT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 查看考勤明细 家长端
	 * 
	 * @author 李林中 2015-1-17 下午6:04:11
	 * @param appContext
	 * @param token
	 * @param chkDay
	 * @param chkClaId
	 * @param orgId
	 * @return
	 * @throws AppException
	 */
	public static Attence getAttenceDetail(AppContext appContext, String token,
			String chkDay, String chkClaId, String orgId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("chkDay", chkDay);
		map.put("chkClaId", chkClaId);
		map.put("orgId", orgId);
		try {
			return Attence.parse(_post(appContext, URLs.GETATTENCEDETAIL_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除在校点评
	 * 
	 * @author 李林中 2015-1-20 上午10:25:33
	 * @param appContext
	 * @param token
	 * @param remarkId
	 * @return
	 * @throws AppException
	 */

	public static Result deleteRemark(AppContext appContext, String token,
			String remarkId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("remarkId", remarkId);
		try {
			return Result.parse(_post(appContext, URLs.DELETEREMARK_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取点评列表 教师端
	 * 
	 * @author 李林中 2015-1-21 上午11:07:16
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static RemarkList getRemarkList(AppContext appContext, String page,
			String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return RemarkList.parse(_post(appContext, URLs.GETREMARKLIST_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 发送在校点评
	 * 
	 * @author 李林中 2015-1-22 下午1:30:49
	 * @param appContext
	 * @param token
	 * @param content
	 * @param receivers
	 * @param pictures
	 * @param rates
	 * @return
	 * @throws AppException
	 */

	public static Result insertRemark(AppContext appContext, String token,
			String content, String receivers, String pictures, String rates)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("content", content);
		map.put("receivers", receivers);
		map.put("pictures", pictures);
		map.put("rates", rates);
		try {
			return Result.parse(_post(appContext, URLs.PUBLISH_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 在校点评模板列表
	 * 
	 * @author 李林中 2015-1-22 下午2:46:24
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static RemarkTeplateList getRatingTplList(AppContext appContext,
			String token) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return RemarkTeplateList.parse(_post(appContext,
					URLs.GETRATINGTPLLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取点评列表 家长端
	 * 
	 * @author 李林中 2015-1-21 上午11:07:16
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static RemarkList getUserRemarkList(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return RemarkList.parse(_post(appContext,
					URLs.GETUSERREMARKLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复 在校点评详情 家长端
	 * 
	 * @author 李林中 2015-1-13 下午7:55:43
	 * @param appContext
	 * @param type
	 * @param refId
	 * @param commentId
	 * @return
	 * @throws AppException
	 */

	public static RemarkNewCommentsDetail getRemarkFsiDataDetail(
			AppContext appContext, String type, String refId, String commentId)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("refId", refId);
		map.put("commentId", commentId);
		try {
			return RemarkNewCommentsDetail.parse(_post(appContext,
					URLs.GETFSIDATADETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得最新回复 家长心声 教师端
	 * 
	 * @author 李林中 2015-1-13 下午7:55:43
	 * @param appContext
	 * @param type
	 * @param refId
	 * @param commentId
	 * @return
	 * @throws AppException
	 */

	public static VoiceDetail getVoiceFsiDataDetail(AppContext appContext,
			String type, String refId, String commentId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("refId", refId);
		map.put("commentId", commentId);
		try {
			return VoiceDetail.parse(_post(appContext,
					URLs.GETFSIDATADETAIL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 调用不同类型的数据接口，获得通用的动态明细
	 * 
	 * @param appContext
	 * @param type
	 * @param refId
	 * @param commentId
	 * @return
	 * @throws AppException
	 */
	public static BaseBean getFsiBeanDataDetail1(AppContext appContext,
			String type, String refId, String commentId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("refId", refId);
		map.put("commentId", commentId);
		if (type == null) {
			return null;
		}
		try {
			String resultInfo = _post(appContext, URLs.GETFSIDATADETAIL_URL,
					map);
			if (Constant.COMMNETTYPE_ZXDT.equals(type)) {

				return DynamicTeacherList.parse(resultInfo);
			} else if (Constant.COMMNETTYPE_TZGG.equals(type)) {

				return NoticeBoxList.parse(resultInfo);
			} else if (Constant.COMMNETTYPE_JTZY.equals(type)) {

				return HomeworkTeacherList.parse(resultInfo);
			} else if (Constant.COMMNETTYPE_KQJL.equals(type)) {

				return AttenceDetailParentList.parse(resultInfo);
			} else if (Constant.COMMNETTYPE_ZXDP.equals(type)) {

				return RemarkList.parse(resultInfo);
			} else if (Constant.COMMNETTYPE_JZXS.equals(type)) {

				return VoiceList.parse(resultInfo);
			}
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
		return null;
	}

	/**
	 * 获得家长心声列表 教师端
	 * 
	 * @author 李林中 2015-1-9 上午11:26:46
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static VoiceList getVoiceTeacherList(AppContext appContext,
			String page, String pageCount, String type) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		map.put("type", type);

		try {
			return VoiceList.parse(_post(appContext,
					URLs.GETVOICETEACHERLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得家长心声列表 家长端
	 * 
	 * @author 李林中 2015-1-9 上午11:26:46
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */

	public static VoiceList getVoiceList(AppContext appContext, String page,
			String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return VoiceList
					.parse(_post(appContext, URLs.GETVOICELIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除家长心声
	 * 
	 * @author 李林中 2015-1-28 上午11:32:37
	 * @param appContext
	 * @param token
	 * @param voice_id
	 * @return
	 * @throws AppException
	 */

	public static Result deletevoice(AppContext appContext, String token,
			String voice_id) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("voice_id", voice_id);
		try {
			return Result.parse(_post(appContext, URLs.DELETEVOICE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 找回密码-验证用户
	 * 
	 * @author 李林中 2015-1-28 上午11:32:37
	 * @param appContext
	 * @param token
	 * @param voice_id
	 * @return
	 * @throws AppException
	 */

	public static ValidUserResult validUser(AppContext appContext,
			String username) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("username", username);
		try {
			return ValidUserResult.parse(_post(appContext, URLs.VALIDUSER_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 找回密码-发送验证码
	 * 
	 * @author 李林中 2015-1-28 下午5:25:09
	 * @param appContext
	 * @param token
	 * @param key
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static Result sendCode(AppContext appContext, String key, String type)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("type", type);
		try {
			return Result.parse(_post(appContext, URLs.SENDCODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 找回密码-校验验证码
	 * 
	 * @author 李林中 2015-1-28 下午5:37:20
	 * @param appContext
	 * @param token
	 * @param key
	 * @param type
	 * @param code
	 * @return
	 * @throws AppException
	 */

	public static ValidCodeResult validCode(AppContext appContext, String key,
			String type, String code) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		map.put("type", type);
		map.put("code", code);
		try {
			return ValidCodeResult.parse(_post(appContext, URLs.VALIDCODE_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 找回密码-重设密码
	 * 
	 * @author 李林中 2015-1-28 下午5:39:53
	 * @param appContext
	 * @param token
	 * @param sign
	 * @param newPassword
	 * @return
	 * @throws AppException
	 */

	public static Result resetPwd(AppContext appContext, String sign,
			String newPassword) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sign", sign);
		map.put("newPassword", newPassword);
		try {
			return Result.parse(_post(appContext, URLs.RESETPWD_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 新增一条家长心声
	 * 
	 * @author 李林中 2015-1-29 上午11:31:19
	 * @param appContext
	 * @param content
	 * @param pictures
	 * @param receivers
	 * @return
	 * @throws AppException
	 */

	public static Result insertvoice(AppContext appContext, String content,
			String pictures, String receivers) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("pictures", pictures);
		map.put("receivers", receivers);
		try {
			return Result.parse(_post(appContext, URLs.INSERTVOICE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取机构和教师列表
	 * 
	 * @author 李林中 2015-1-29 下午5:15:42
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static TeacherOrgList getUserToTeachers(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return TeacherOrgList.parse(_post(appContext,
					URLs.GETUSERTOTEACHERS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取机构下面的班级和教师列表
	 * 
	 * @author 李林中 2015-4-10 下午3:28:52
	 * @param appContext
	 * @param token
	 * @param joinOrg
	 * @return
	 * @throws AppException
	 */

	public static TeacherOrgClassList getUserClassAndTeacher(
			AppContext appContext, Boolean joinOrg) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinOrg", joinOrg);
		try {
			return TeacherOrgClassList.parse(_post(appContext,
					URLs.GETUSERCLASSANDTEACHER_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取代理老师列表
	 * 
	 * @author 李林中 2015-4-10 下午3:28:52
	 * @param appContext
	 * @param token
	 * @param joinOrg
	 * @return
	 * @throws AppException
	 */

	public static TeacherOrgClassList getDelegationClassAndTeacher(
			AppContext appContext, Boolean joinOrg) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinOrg", joinOrg);
		try {
			return TeacherOrgClassList.parse(_post(appContext,
					URLs.GETDELEGATIONCLASSANDTEACHER_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 注册 获取验证码
	 * 
	 * @author 李林中 2015-1-30 下午3:24:47
	 * @param appContext
	 * @param mobile
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static Result sendSmsCode(AppContext appContext, String mobile,
			String type) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("type", type);
		try {
			return Result.parse(_post(appContext, URLs.SENDSMSCODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 注册 校验验证码
	 * 
	 * @author 李林中 2015-1-30 下午3:54:14
	 * @param appContext
	 * @param mobile
	 * @param type
	 * @param authCode
	 * @return
	 * @throws AppException
	 */

	public static Result validateSmsCode(AppContext appContext, String mobile,
			String type, String authCode) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("type", type);
		map.put("authCode", authCode);
		try {
			return Result
					.parse(_post(appContext, URLs.VALIDATESMSCODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 注册
	 * 
	 * @author 李林中 2015-1-30 下午4:08:40
	 * @param appContext
	 * @param phone
	 * @param authCode
	 * @param userName
	 * @param pwd
	 * @param confirmPwd
	 * @return
	 * @throws AppException
	 */

	public static Result register(AppContext appContext, String phone,
			String authCode, String userName, String pwd, String confirmPwd)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("authCode", authCode);
		map.put("userName", userName);
		map.put("pwd", pwd);
		map.put("confirmPwd", confirmPwd);
		try {
			return Result.parse(_post(appContext, URLs.REGISTER_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 绑定手机
	 * 
	 * @author 李林中 2015-2-1 下午4:22:12
	 * @param appContext
	 * @param password
	 * @param mobile
	 * @param authCode
	 * @return
	 * @throws AppException
	 */

	public static Result bind(AppContext appContext, String password,
			String mobile, String authCode) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", password);
		map.put("authCode", authCode);
		map.put("mobile", mobile);

		try {
			return Result.parse(_post(appContext, URLs.BIND_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 绑定 获取验证码
	 * 
	 * @author 李林中 2015-1-30 下午3:24:47
	 * @param appContext
	 * @param mobile
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static Result sendSmsCodeBind(AppContext appContext, String mobile,
			String type) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("type", type);
		try {
			return Result.parse(_post(appContext, URLs.SENDSMSCODE_BIND_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static Result sendSmsCodeLogin(AppContext appContext, String mobile,
			String type) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("type", type);
		try {
			return Result.parse(_post(appContext, URLs.SENDSMSCODE_LOGIN_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 校验密码
	 * 
	 * @author 李林中 2015-2-2 上午11:21:28
	 * @param appContext
	 * @param password
	 * @param userId
	 * @return
	 * @throws AppException
	 */

	public static Result checkPass(AppContext appContext, String password,
			String userId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", password);
		map.put("userId", userId);
		try {
			return Result.parse(_post(appContext, URLs.CHECKPASS_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 绑定 校验验证码
	 * 
	 * @author 李林中 2015-1-30 下午3:54:14
	 * @param appContext
	 * @param mobile
	 * @param type
	 * @param authCode
	 * @return
	 * @throws AppException
	 */

	public static Result validateSmsBindCode(AppContext appContext,
			String mobile, String type, String authCode) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("type", type);
		map.put("authCode", authCode);
		try {
			return Result.parse(_post(appContext,
					URLs.VALIDATESMSCODE_BIND_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 重新绑定手机号
	 * 
	 * @author 李林中 2015-2-2 下午7:52:08
	 * @param appContext
	 * @param mobile
	 * @param authCode
	 * @param sign
	 * @return
	 * @throws AppException
	 */

	public static Result rebind(AppContext appContext, String mobile,
			String authCode, String sign) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("sign", sign);
		map.put("authCode", authCode);
		try {
			return Result.parse(_post(appContext, URLs.REBIND_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得个人资料 家长
	 * 
	 * @author 李林中 2015-2-2 下午4:38:51
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static PersonInfoDetail getPersonList(AppContext appContext,
			String token,String roles) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return PersonInfoDetail.parse(_post(appContext,
					URLs.GETPERSONLIST_URL, map),roles);
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 验证用户
	 * 
	 * @author 李林中 2015-2-2 下午6:06:18
	 * @param appContext
	 * @param tag
	 * @param type
	 * @param checkCode
	 * @return
	 * @throws AppException
	 */

	public static ValidCodeResult validdata(AppContext appContext, String tag,
			String type, String checkCode) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("tag", tag);
		map.put("type", type);
		map.put("checkCode", checkCode);
		try {
			return ValidCodeResult.parse(_post(appContext, URLs.VALIDATE_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @author 李林中 2015-2-3 下午4:12:54
	 * @param appContext
	 * @param oldPwd
	 * @param password
	 * @param confirmPwd
	 * @param sign
	 * @return
	 * @throws AppException
	 */

	public static Result update(AppContext appContext, String oldPwd,
			String password, String confirmPwd, String sign)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldPwd", oldPwd);
		map.put("password", password);
		map.put("confirmPwd", confirmPwd);
		map.put("sign", sign);
		try {
			return Result.parse(_post(appContext, URLs.UPDATE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得学生所在的班级 机构 机构下面的老师
	 * 
	 * @author 李林中 2015-2-4 下午2:45:53
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static StudentTeacherOrgClassList getChildClassAndTeacher(
			AppContext appContext, String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return StudentTeacherOrgClassList.parse(_post(appContext,
					URLs.GETCHILDCLASSANDTEACHER_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得教师端 未读数量提示
	 * 
	 * @author 李林中 2015-2-6 上午11:07:30
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static UnReadRecvNumList getUnReadRecvNum(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return UnReadRecvNumList.parse(_post(appContext,
					URLs.GETUNREADRECVNUM_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 编辑个人信息
	 * 
	 * @author 李林中 2015-2-11 下午3:17:08
	 * @param appContext
	 * @param token
	 * @param personinfoSend
	 * @return
	 * @throws AppException
	 */

	public static Result updatePersonList(AppContext appContext,
			PersoninfoSend personinfoSend) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("addr", personinfoSend.getAddr());
		map.put("city", personinfoSend.getCity());
		map.put("county", personinfoSend.getCounty());
		map.put("lat", personinfoSend.getLat());
		map.put("lng", personinfoSend.getLng());
		map.put("name", personinfoSend.getName());
		map.put("photoPath", personinfoSend.getPhotoPath());
		map.put("province", personinfoSend.getProvince());
		map.put("sex", personinfoSend.getSex());
		map.put("userName", personinfoSend.getUserName());
		try {
			return Result.parse(_post(appContext, URLs.UPDATEPERSONLIST_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得宝宝信息列表
	 * 
	 * @author 李林中 2015-2-13 上午11:26:35
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static BabyInfoList getBabyNameList(AppContext appContext,
			String token) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		try {
			return BabyInfoList.parse(_post(appContext,
					URLs.GETBABYNAMELIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得宝宝明细
	 * 
	 * @author 李林中 2015-2-14 上午10:57:25
	 * @param appContext
	 * @param childId
	 * @param userId
	 * @param updatetype
	 * @return
	 * @throws AppException
	 */

	public static BabyInfoDetail getupdateBaby(AppContext appContext,
			String childId, String userId, String updatetype)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("childId", childId);
		map.put("userId", userId);
		map.put("updatetype", updatetype);
		try {
			return BabyInfoDetail.parse(_post(appContext,
					URLs.GETUPDATEBABY_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获得兴趣爱好列表
	 * 
	 * @author 李林中 2015-2-14 下午5:10:18
	 * @param appContext
	 * @param token
	 * @return
	 * @throws AppException
	 */

	public static CatList getCat(AppContext appContext, String token)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);

		try {
			return CatList.parse(_post(appContext, URLs.GETCAT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 编辑宝宝信息
	 * 
	 * @author 李林中 2015-2-11 下午3:17:08
	 * @param appContext
	 * @param token
	 * @param babyinfoSend
	 * @return
	 * @throws AppException
	 */

	public static Result updateBabyList(AppContext appContext,
			BabyinfoSend babyinfoSend) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", babyinfoSend.getUserId());
		map.put("photoPath", babyinfoSend.getPhotoPath());
		map.put("nickName", babyinfoSend.getNickName());
		map.put("name", babyinfoSend.getName());
		map.put("sex", babyinfoSend.getSex());
		map.put("birthDate", babyinfoSend.getBirthDate());
		map.put("interestPath", babyinfoSend.getInterestPath());
		map.put("childId", babyinfoSend.getChildId());

		try {
			return Result
					.parse(_post(appContext, URLs.UPDATEBABYLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 删除宝宝
	 * 
	 * @author 李林中 2015-1-9 上午10:48:31
	 * @param appContext
	 * @param token
	 * @param attenceId
	 * @return
	 * @throws AppException
	 */

	public static Result deleteBaby(AppContext appContext, String token,
			String childId, String userId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("childId", childId);
		map.put("userId", userId);
		try {
			return Result.parse(_post(appContext, URLs.DELETEBABY_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 宝宝退出机构
	 * 
	 * @author 李林中 2015-2-27 下午1:33:42
	 * @param appContext
	 * @param token
	 * @param childId
	 * @param orgId
	 * @return
	 * @throws AppException
	 */

	public static Result exitOrg(AppContext appContext, String token,
			String childId, String orgId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("childId", childId);
		map.put("orgId", orgId);
		try {
			return Result.parse(_post(appContext, URLs.EXITORG_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取客户端版本信息
	 * 
	 * @author 李林中 2015-3-10 下午4:45:16
	 * @param appContext
	 * @param type
	 * @return
	 * @throws AppException
	 */

	public static UpdateInfoDetail getClientVersion(AppContext appContext,
			String type) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);

		try {
			return UpdateInfoDetail.parse(_post(appContext,
					URLs.GETCLIENTVERSION_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 我的代理信息
	 * 
	 * @author 李林中 2015-4-20 下午5:20:14
	 * @param appContext
	 * @return
	 * @throws AppException
	 */

	public static MyAgentList getUserDelegation(AppContext appContext)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			return MyAgentList.parse(_post(appContext,
					URLs.GETUSERDELEGATION_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 修改个人资料-验证用户名是否重复
	 * 
	 * @author lyc 2015-3-12 下午2:02:53
	 * @param userName
	 * @return
	 * @throws AppException
	 */
	public static Result checkUserName(AppContext appContext, String userName)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		try {
			return Result.parse(_post(appContext, URLs.CHECKUSERNAME_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 分享接口
	 * 
	 * @param appContext
	 * @param refId
	 *            互动ID
	 * @param type
	 *            互动类型
	 * @param smpType
	 *            分享平台类型
	 * @param orgId
	 *            分享内容所属机构ID
	 * @param opt
	 *            分享操作标识
	 * @return
	 * @throws AppException
	 */
	public static ShareResult share(AppContext appContext, String refId,
			String type, String smpType, String orgId, String opt)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("refId", refId == null ? "" : refId);
		map.put("type", type == null ? "" : type);
		map.put("smpType", smpType == null ? "" : smpType);
		map.put("orgId", orgId == null ? "" : orgId);
		map.put("opt", opt == null ? "" : opt);
		try {
			return ShareResult.parse(_post(appContext, URLs.SHARE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static OrgInfo orgDetail(AppContext appContext, String orgId)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		try {
			return OrgInfo.parse(_post(appContext, URLs.ORGINFO_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 增加代理老师
	 * 
	 * @author 李林中 2015-4-21 下午2:37:53
	 * @param appContext
	 * @param agentUserId
	 * @param agentTeaId
	 * @param endTime
	 * @return
	 * @throws AppException
	 */

	public static Result saveDelegation(AppContext appContext,
			String agentUserId, String agentTeaId, String endTime)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentUserId", agentUserId);
		map.put("agentTeaId", agentTeaId);
		map.put("endTime", endTime);
		try {
			return Result
					.parse(_post(appContext, URLs.SAVEDELEGATION_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 取消代理
	 * 
	 * @author 李林中 2015-4-21 下午2:40:47
	 * @param appContext
	 * @param delegationId
	 * @return
	 * @throws AppException
	 */

	public static Result cancelDelegation(AppContext appContext,
			String delegationId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delegationId", delegationId);
		try {
			return Result.parse(_post(appContext, URLs.CANCELDELEGATION_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static FavorList getFavList(AppContext appContext, String page,
			String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return FavorList
					.parse(_post(appContext, URLs.GETFAVORLIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 我的代理信息 历史记录
	 * 
	 * @author 李林中 2015-4-21 上午11:49:40
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public static AgentHistoryList queryDelegation(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);

		try {
			return AgentHistoryList.parse(_post(appContext,
					URLs.QUERYDELEGATION_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 获取当前登录用户负责的班级
	 * 
	 * @author 李林中 2015-4-27 下午1:57:04
	 * @param appContext
	 * @return
	 * @throws AppException
	 */
	public static OrgList getOrgClasses(AppContext appContext)
			throws AppException {
		try {
			return OrgList
					.parse(_post(appContext, URLs.GETORGCLASSES_URL, null));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 我的系统消息
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @return
	 * @throws AppException
	 */
	public static MySystemMessageList getMessageList(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return MySystemMessageList.parse(_post(appContext,
					URLs.MYMESSAGE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 我的消息
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @returng
	 * @throws AppException
	 */
	public static MySelfMessageList getMySelfMessageList(AppContext appContext,
			String page, String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return MySelfMessageList.parse(_post(appContext,
					URLs.MY_MYMESSAGE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 系统消息详情页 列表
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @returng
	 * @throws AppException
	 */
	public static MySystemMessageDeatilList getMySystemMessageDeatiList(AppContext appContext,
			String receiveId,String page,String pageCount) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("recieverId", receiveId);
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return MySystemMessageDeatilList.parse(_post(appContext,
					URLs.MY_SYSTEM_DETAIL_LIST_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 系统消息详情页 --标题，内容，发送时间
	 * 
	 * @param appContext
	 * @param receiveId
	 * @param msgId
	 * @return
	 * @throws AppException
	 */
	public static MySystemMessageDeatilContent getMySystemMessageDeatil(AppContext appContext,
			String receiveId, String messageId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		String recvId = receiveId == null ? "" : receiveId;
		String msgId = messageId == null ? "" : messageId;
		try {
			String resultInfo = null;
			if (!"".equals(recvId)) { // receiveId不空，按热receiveId查询
				map.put("receiveId", recvId);
				resultInfo = _post(appContext, URLs.MY_SYSMSG_BY_RECVID_URL, map);
			} else { // 否则，按照messageId进行查询
				map.put("msgId", msgId);
				resultInfo = _post(appContext, URLs.MY_SYSMSG_BY_MSGID_URL, map);
			}
			
			return MySystemMessageDeatilContent.parse(resultInfo);
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 进行学员变动
	 * 
	 * @author 李林中 2015-4-27 下午7:11:29
	 * @param appContext
	 * @param stuChgList
	 * @return
	 * @throws AppException
	 */
	public static Result saveStuChgState(AppContext appContext,
			String stuChgList) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("stuChgList", stuChgList);
		try {
			return Result
					.parse(_post(appContext, URLs.SAVESTUCHGSTATE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static ShareResult cancelShare(AppContext appContext, String shareId)
			throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shareId", shareId == null ? "" : shareId);
		try {
			return ShareResult.parse(_post(appContext, URLs.CANCEL_SHARE_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static OrgIntroWrapper getOrgIntroDetail(AppContext appContext,
			String orgId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId == null ? "" : orgId);
		try {
			return OrgIntroWrapper.parse(_post(appContext, URLs.ORG_INTRO_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 上传通讯录
	 * 
	 * @param appContext
	 * @param orgId
	 * @return
	 * @throws AppException
	 */
	public static CircleofFriendsBean synPhoneContacts(AppContext appContext,
			String isAllow,String contacts) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAllow", isAllow == null ? "" : isAllow);
		map.put("contacts", contacts == null ? "" : contacts);
		try {
			return CircleofFriendsBean.parse(_post(appContext, URLs.UPLOADER_PHONE_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 朋友圈评论列表
	 * 
	 * @param appContext
	 * @param page
	 * @param pageCount
	 * @param lat
	 * @param lon
	 * @return
	 * @throws AppException
	 */
	public static FriendsCommentBean queryFriendsCommentList(AppContext appContext, String page,
			String pageCount,String lat,String lon,String tag,String userId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_page_", page);
		map.put("_page_count_", "5");
		map.put("lat", lat);
		map.put("lon", lon);
		map.put("userId", userId);
		try {
			if (tag.equals("0")) {// 用来区别朋友圈，附近，和我的评论
				return FriendsCommentBean.parse(_post(appContext,
						URLs.FRIENDS_COMMENT_URL, map));
			} else if (tag.equals("6")) {// 附近
				return FriendsCommentBean.parse(_post(appContext,
						URLs.MY_FRIENDS_FUJIN_URL, map));
			} else {// 我的评论
				return FriendsCommentBean.parse(_post(appContext,
						URLs.MY_FRIENDS_COMMENT_URL, map));
			}
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	/**
	 * 感谢
	 * 
	 * @param appContext
	 * @param commentId
	 * @param newFlag
	 * @return
	 * @throws AppException
	 */
	public static FriendsHelpfulBase queryFriendsHelpfulBase(AppContext appContext, String commentId,
			String newFlag) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentId", commentId);
		map.put("newFlag", newFlag);
		try {
			return FriendsHelpfulBase.parse(_post(appContext,
					URLs.FRIENDS_HELPFUL_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static SuggestNameWrapper getInputSuggest(AppContext appContext,
			String searchType, String keyword, String orgId)
			throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("searchType", searchType == null ? "" : searchType);
		map.put("keyword", keyword == null ? "" : keyword);
		map.put("include", "name|courId|orgId|org|addr.address|logoPath");
		if (!TextUtils.isEmpty(orgId)) {
			map.put("filter", "orgId:" + orgId);
		}

		try {
			return SuggestNameWrapper.parse(_post(appContext,
					URLs.GET_SUGGEST_NAME_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static Result StatCourseCounselNum(AppContext appContext,
			String courId) throws AppException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courId", courId == null ? "" : courId);
		try {
			return Result.parse(_post(appContext, URLs.ADD_COUESE_COUNSEL_NUM_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	
	public static Result sendCourseComment(AppContext appContext,
			Map<String, String> mapData) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (mapData != null) {
			map.putAll(mapData);
			try {
				return Result.parse(_post(appContext,
						URLs.SEND_COUESE_COMMENT_URL, map));
			} catch (Exception e) {
				if (e instanceof AppException)
					throw (AppException) e;
				throw AppException.network(e);
			}
		}
		return null;
	}

	/**
	 * 根据课程ID查询课程下的评价
	 * 
	 * @param appContext
	 * @param orgId
	 * @param courId
	 * @return
	 */
	public static FriendsCommentBean QueryCourseComment(AppContext appContext,
			String page, String pageCount,
			String orgId, String courId,String userId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId == null ? "" : orgId);
		map.put("courId", courId == null ? "" : courId);
		map.put("userId", userId == null ? "" : userId);
		map.put("_page_", page);
		map.put("_page_count_", pageCount);
		try {
			return FriendsCommentBean.parse(_post(appContext,
					URLs.GET_COURSE_COMMENT_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static SuggestNameWrapper getSuggestCourse(AppContext appContext,
			String orgId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", "0");
		map.put("filter", "orgId:" + orgId);
		map.put("include", "name|courId|orgId|org|addr.address");
		map.put("_page_", 1);
		map.put("_page_count_", 10);
		try {
			return SuggestNameWrapper.parse(_post(appContext, URLs.COURSE_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	public static BaseBean deleteCourseComment(AppContext appContext,
			String commentId) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentId", commentId == null ? "" : commentId);
		try {
			return Result.parse(_post(appContext, URLs.DELETE_COUESE_COMMENT_URL,
					map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	/**
	 * 建业至尊
	 * 			申请秘钥
	 * @param appContext
	 * @param commentId
	 * @param newFlag
	 * @return
	 * @throws AppException
	 */
	public static GetCodeBean queryZhiZunGetCodeBase(AppContext appContext, String s_no) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("s_no", s_no);
		map.put("act", "getcode");
		try {
			return GetCodeBean.parse(_post(appContext,
					URLs.ZHI_ZUN_GET_CODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	/**
	 * 注册接口
	 * @param appContext
	 * @param s_no
	 * @return
	 * @throws AppException
	 */
	public static GetCodeBean queryRegisterBase(AppContext appContext, String userName,String password,String register,String email) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", userName);
		map.put("password", password);
		map.put("email", email);
		map.put("act", "register");//接口
		try {
			return GetCodeBean.parse(_post(appContext,
					URLs.ZHI_ZUN_GET_CODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}
	/**
	 * 登录接口
	 * @param appContext
	 * @param s_no
	 * @return
	 * @throws AppException
	 */
	public static GetCodeBean queryLoginBase(AppContext appContext, String userName,String password) throws AppException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", userName);
		map.put("password", password);
		map.put("act", "login");//接口
		try {
			return GetCodeBean.parse(_post(appContext,
					URLs.ZHI_ZUN_GET_CODE_URL, map));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

}
