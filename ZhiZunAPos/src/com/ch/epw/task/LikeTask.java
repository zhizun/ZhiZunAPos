package com.ch.epw.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.UserInfo;

public class LikeTask extends AsyncTask<String, Void, LikeResult> {
	AppException e;
	private List<Like> likeList;
	private Context context;
	private TaskCallBack taskCallBack;
	private	String cancelState;// 取消赞状态，执行任务时的第4个参数
	
	public LikeTask(List likeList, Context context,	TaskCallBack callBackFunc) {
		this.likeList = likeList;
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected LikeResult doInBackground(String... params) {
		LikeResult likeResult = null;
		cancelState = params[3];
		try {
			likeResult = AppContext.getApp().like(params[0], params[1],
					params[2], params[3]);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return likeResult;
	}


	@Override
	protected void onPostExecute(LikeResult result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				if ("1".equals(cancelState)) {		//需要取消赞
					UIHelper.ToastMessage(context, "已取消赞");
					if (likeList.size() == 1) {
						likeList.clear();
					} else {
						UserInfo userInfo = AppContext.getApp()
								.getUserLoginSharedPre().getUserInfo();
						for (Like like : likeList) {
							if (userInfo.getUserId().equals(like.getUserId())) {
								likeList.remove(like);
								break;
							}
						}
					}
				} else {
					UIHelper.ToastMessage(context, "已赞");
					Like like = new Like();
					like.setIsCancel(result.getIsCancel());
					like.setUserAppe(result.getUserAppe());
					like.setUserId(result.getUserId());
					likeList.add(like);
				}

				if( taskCallBack != null ){
					taskCallBack.onTaskFinshed();
				}
			} else {
				//UIHelper.ToastMessage(context, result.getStatusMessage());
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