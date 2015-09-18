package com.ch.epw.task;
import android.content.Context;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
/**
 * 请求朋友圈评价列表
 * @author lilinzhong
 *
 * 2015-8-20下午2:09:20
 */
public class FriendsCommeTask extends BaseRefeshTask{

	@Override
	public void setContext(Context cxt) {
		// TODO Auto-generated method stub
		context=cxt;
	}

	@Override
	public void setCallBack(TaskCallBack func) {
		// TODO Auto-generated method stub
		taskCallBack = func;
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {			
			return AppContext.getApp().queryFriendsCommentList(
							params[0],params[1],taskExtraParams[0],taskExtraParams[1],taskExtraParams[2],taskExtraParams[3]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	protected void onPostExecute(BaseBean result) {
		super.onPostExecute(result);
		if (result != null) {
			if(taskCallBack != null){
				taskCallBack.onTaskFinshed(result);
			}
		} else {
			if (null != e) {
				e.makeToast(context);
			}
			return;
		}
	}
}
