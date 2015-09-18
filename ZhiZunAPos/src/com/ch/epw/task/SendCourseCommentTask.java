package com.ch.epw.task;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CourseComment;
import com.zhizun.pos.bean.Rating;

public class SendCourseCommentTask extends BaseAsyncServiceTask {

	public SendCourseCommentTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(Object... params) {
		try {
			Map<String, String> mapData = new HashMap<String, String>();
			CourseComment courseComment = (CourseComment) params[0];
			mapData.put("commentId", courseComment.getCommentId());
			mapData.put("courId", courseComment.getCourId());
			mapData.put("courName", courseComment.getCourName());
			mapData.put("orgId", courseComment.getOrgId());
			mapData.put("orgName", courseComment.getOrgName());
			mapData.put("isAnonymous", courseComment.getIsAnonymous());
			mapData.put("range", courseComment.getRange());
			mapData.put("content", courseComment.getContent());
			mapData.put("marks", Rating.Helper
					.formatRatingAsPostData(courseComment.getRatingList()));
			mapData.put("photoPath", courseComment.getPhotoPath());
			
			return AppContext.getApp().sendCourseComment(mapData);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return null;
	}
}

