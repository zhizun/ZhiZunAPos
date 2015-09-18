package com.ch.epw.task;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.utils.UIHelper;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.NewCommentsCount;

/**
 * 获得最新回复数量 
 * 创建日期：2014-12-17 下午7:40:16 
 * 作用： 修改
 * =================================================== 
 * 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class GetNewCommentsCountTask extends
		AsyncTask<String, Void, NewCommentsCount> {
	AppException e;
	BadgeView newMsgBadgeView;
	Context context;
	public GetNewCommentsCountTask(Context context, BadgeView bv){
		this.newMsgBadgeView =  bv;
		this.context = context;
	}

	@Override
	protected NewCommentsCount doInBackground(String... params) {
		NewCommentsCount newCommentsCount = null;
		try {
			newCommentsCount = AppContext.getApp().getNewCommnetsCount(
					params[0], params[1]);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return newCommentsCount;
	}

	@Override
	protected void onPostExecute(NewCommentsCount result) {
		super.onPostExecute(result);
		if (null != result) {
			if (result.getStatus().equals("0")) {
				if(result.getCount() != null){
					newMsgBadgeView.setBadgeCount(Integer.parseInt(result.getCount()));
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
