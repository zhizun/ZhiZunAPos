package com.zhizun.pos.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 立即报名实体类 创建人：李林中
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class CourseApplyBean extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.UserLogin";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public static CourseApplyBean parse(String resultInfo) {
		Log.i(TAG, "解析报名：" + resultInfo);
		CourseApplyBean courseApplyBean = new CourseApplyBean();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			courseApplyBean.setStatus(jsonObject.getString("status"));
			courseApplyBean.setStatusMessage(jsonObject.getString("statusMessage"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return courseApplyBean;
	}
}
