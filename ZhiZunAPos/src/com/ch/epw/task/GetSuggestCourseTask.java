package com.ch.epw.task;

import android.content.Context;

import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

/**
 * 查询课程建议
 * 
 * 调用search接口，返回的是输入课程建议名称列表
 * 与GetInputSuggestTask不同的是，该接口不传入关键字名称，通过orgId获得机构下的所有课程列表
 * 
 * @author lyc
 *
 */
public class GetSuggestCourseTask extends BaseAsyncTask {

	public GetSuggestCourseTask(Context ctx, TaskCallBack call) {
		super(ctx, call);
	}

	@Override
	protected BaseBean doInBackground(String... params) {
		try {
			return AppContext.getApp().getSuggestCourse(params[0]);
		} catch (AppException e) {
			this.e=e;
			e.printStackTrace();
		}
		return null;
	}
}

