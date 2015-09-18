package com.ch.epw.receiver;

import java.io.IOException;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.ch.epw.js.activity.AttenceTeacherActivity;
import com.ch.epw.js.activity.InSchoolCommentTeacherActivity;
import com.ch.epw.js.activity.InSchoolDynamicTeacherActivity;
import com.ch.epw.js.activity.MyHomeworkTeacherActivity;
import com.ch.epw.js.activity.MyNoticeTeacherActivity;
import com.ch.epw.js.activity.NavigationTeacherActivity;
import com.ch.epw.js.activity.VoiceTeacherActivity;
import com.ch.epw.task.SwitchRolesTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.activity.IndexActivity;
import com.zhizun.pos.activity.LoginActivity;
import com.zhizun.pos.activity.MainActivity;
import com.zhizun.pos.activity.MyMessageActivity;
import com.zhizun.pos.activity.MySystemMessageDeatilActivity;
import com.zhizun.pos.activity.MySystemMessageDeatilListActivity;
import com.zhizun.pos.activity.NewCommentsDetailActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.bean.UserRole;

/**
 * 自定义接收器 消息推送
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	Result result;
	UserLogin userLogin;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 接收到推送下来的自定义消息: "
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			PushMsg pushMsg = null;
			try {
				pushMsg = PushMsg.parse(bundle
						.getString(JPushInterface.EXTRA_EXTRA));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (AppException e) {
				e.printStackTrace();
			}

			showPushMsg(pushMsg, context);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	private void showPushMsg(final PushMsg pushMsg, final Context context){
		Class clazzClass = LoginActivity.class;
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		// 如果用户未保存登录信息，跳转到登录页面
		if(userLogin == null || userLogin.getUserInfo() == null){
			Intent intent = new Intent(context, clazzClass);
			intent.putExtra("pushMsg", pushMsg);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
			return;
		}

		int pageIndex = 0;
		String receiveId = null;
		String messageId = null;
		UserInfo userInfo = userLogin.getUserInfo();
		String userRoleId = userInfo.getCurrentRole();
		String userOrgId = userInfo.getCurrentOrgan() == null ? "" :userInfo.getCurrentOrgan().getOrgId();
		boolean isAppRunning = false; // 判断当前应用是否已启动
		// 推送消息页面返回时需要打开的activity
		String activityCalledOnBack = "com.ch.epw.activity.WelcomeActivity";
		if(userRoleId.equals(Constant.ORG_ROLE_TYPE_PARENT)){
			activityCalledOnBack = IndexActivity.class.getName();
			isAppRunning = AppManager.getAppManager().isActivityRunning(MainActivity.class);
		}else{
			activityCalledOnBack = IndexActivity.class.getName();
			isAppRunning = AppManager.getAppManager().isActivityRunning(NavigationTeacherActivity.class);
		}

		if(pushMsg != null ){
			String pushRole = pushMsg.getRole()== null ? "":pushMsg.getRole();
			String pushOrgId = pushMsg.getOrgId()== null ? "":pushMsg.getOrgId();
			String pushType = pushMsg.getType()== null ? "":pushMsg.getType();
			String pushRefId = pushMsg.getRefId()== null ? "":pushMsg.getRefId();

			// 对系统消息和最新反馈判断处理，不区分角色，只根据推送消息类型判断
			if(pushType.equals(Constant.COMMNETTYPE_MSG) || pushType.equals(Constant.COMMNETTYPE_NEWMSG) ){
				// 回复提醒处理
				if(pushType.equals(Constant.COMMNETTYPE_MSG)){
					receiveId = pushRefId;
					// refId 为空，表示回复用户反馈内容
					if (pushRefId.equals("")) {
						clazzClass = MyMessageActivity.class;
						pageIndex = 1; // 在MyMessageActivity中的pageIndex
					} else { // 否则回复系统消息下用户的回复
						clazzClass = MySystemMessageDeatilListActivity.class;
					}
				}
				// 系统推送新的提醒
				else if (pushType.equals(Constant.COMMNETTYPE_NEWMSG)){
					messageId = pushRefId;
					clazzClass =MySystemMessageDeatilActivity.class;
				}
			}else{
				
				// 用户登录角色与推送角色不匹配
				if( !pushToSameRole(pushRole,pushOrgId,userRoleId,userOrgId)){
					// 如果用户已经登录，显示 提示信息
					if(isAppRunning){
						UIHelper.ToastMessage(context, "当前角色无法查看相关消息",
								Toast.LENGTH_LONG);
					}
					// 否则先切换到相应角色，再显示相关信息
					else{
						UserRole userRole = getDestUserOrgan(userInfo, pushRole, pushOrgId);
						if(userRole != null){
							new SwitchRolesTask(userRole, context, new TaskCallBack(){
								@Override
								public void onTaskFinshed(){
									showPushMsg(pushMsg, context);
								}
							}).execute(AppContext.getApp().getToken(),userRole.getRoleId(), userRole.getOrgId());
						}
					}
					return;
				}
				
				// 以下分别处理推送对象 家长、老师和未指定角色的情况（pushRole=*）
				if(pushRole.equals("*")){
					if( pushType.equals(Constant.COMMNETTYPE_ZXHF)){
						String commentType = pushMsg.getCommentType();
						if(commentType != null && !commentType.equals("")){
							clazzClass = NewCommentsDetailActivity.class;
							pushMsg.setType(commentType);
						}
					}
				}
				else if(pushRole.equals(Constant.ORG_ROLE_TYPE_PARENT)){
					clazzClass = IndexActivity.class;
					pageIndex = 2;
				}
				// 其他角色: 老师、机构管理员、超级管理员
				else {
					if(pushType.equals(Constant.COMMNETTYPE_ZXDT)){
						clazzClass = InSchoolDynamicTeacherActivity.class;
					}else if(pushType.equals(Constant.COMMNETTYPE_TZGG)){
						clazzClass = MyNoticeTeacherActivity.class;
					}else if(pushType.equals(Constant.COMMNETTYPE_JTZY)){
						clazzClass = MyHomeworkTeacherActivity.class;
					}else if(pushType.equals(Constant.COMMNETTYPE_KQJL)){
						clazzClass = AttenceTeacherActivity.class;
					}else if(pushType.equals(Constant.COMMNETTYPE_ZXDP)){
						clazzClass = InSchoolCommentTeacherActivity.class;
					}else if(pushType.equals(Constant.COMMNETTYPE_JZXS)){
						clazzClass = VoiceTeacherActivity.class;
					}
				}
			}
		}

		// 打开自定义的Activity
		Intent intent = null;
		boolean isParentIndexActivityRunning = AppManager.getAppManager().isActivityRunning(IndexActivity.class);
		if(userRoleId.equals(Constant.ORG_ROLE_TYPE_PARENT)
				&& clazzClass.equals(IndexActivity.class)
				&& isParentIndexActivityRunning){
			intent = new Intent();
			intent.putExtra("currentRoleTag", Constant.ORG_ROLE_TYPE_PARENT);
			intent.putExtra("pageIndex", pageIndex);
			intent.setAction("com.ch.epw.REFRESH_INDEXACTIVITY");
			AppContext.getApp().sendBroadcast(intent);
		}else{
			intent = new Intent(context, clazzClass);
			intent.putExtra("pushMsg", pushMsg);
			intent.putExtra("pageIndex", pageIndex);
			intent.putExtra("activityCalledOnBack", activityCalledOnBack);
			intent.putExtra("msgFromTag", 1);// 表示该跳转来自消息推送
			intent.putExtra("receiveId", receiveId);// 接收消息ID，只对系统消息回复推送有效
			intent.putExtra("messageId", messageId);// 系统消息ID，只对系统新消息提醒推送有效
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 根据roleId和orgId得到用户UserRole对象
	 */
	private UserRole getDestUserOrgan(UserInfo userInfo, String roleId, String orgId){
		List<UserRole> userRoleList = userInfo.getUserRoleList();
		if(userRoleList == null){
			return null;
		}
		for(UserRole ur : userRoleList){
			if(Constant.ORG_ROLE_TYPE_PARENT.equals(roleId) && roleId.equals(ur.getRoleId())){
				return ur;
			}else if(roleId.equals(ur.getRoleId()) && orgId.equals(ur.getOrgId())){
				return ur;
			}
		}
		return null;
	}

	/**
	 * 判断推送的角色与当前登录用户是否是同一个角色
	 * 
	 * @return
	 */
	private boolean pushToSameRole(String pushRole, String pushOrgId, String currRole, String currOrgId){
		// 进行null处理
		pushRole = pushRole == null ? "-" : pushRole;
		pushOrgId = pushOrgId == null ? "-" : pushOrgId;

		if(pushRole.equals("*")){
			return true;
		}else if(pushRole.equals(Constant.ORG_ROLE_TYPE_PARENT)){
			
			return currRole.equals(pushRole);
		}else{
			
			return pushRole.equals(currRole)&&pushOrgId.equals(currOrgId);
		}
	}
	
	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
}
