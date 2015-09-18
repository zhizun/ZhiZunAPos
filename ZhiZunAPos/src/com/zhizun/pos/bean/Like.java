package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 赞实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Like extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = Like.class.getName();
	private String userAppe;// 点赞的人
	private String userId;// 点赞人ID
	private String isCancel;// 是否已赞，0已赞 1没赞

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getUserAppe() {
		return userAppe;
	}

	public void setUserAppe(String userAppe) {
		this.userAppe = userAppe;
	}



}
