package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 最新回复数量实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NewCommentsCount extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NewCommentsCount.class.getName();
	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private String count;// 最新回复数量

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static NewCommentsCount parse(String resultInfo) throws IOException,
			AppException {

		NewCommentsCount like = new NewCommentsCount();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			like.setStatus(jsonObject.getString("status"));
			like.setStatusMessage(jsonObject.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				like.setCount(jsonObject.getString("data"));
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return like;
	}

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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
