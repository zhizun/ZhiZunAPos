package com.zhizun.pos.bean;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 验证用户接口操作结果实体类(通用) 创建人：李林中 创建日期：2014-12-17 下午5:29:29 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class ValidUserResult extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String status;// 0 成功
	private String statusMessage;// 错误原因

	private String username;// 用户名
	private String key;// key
	private String mobile;// 手机号

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static ValidUserResult parse(String resultInfo) throws IOException,
			AppException {

		ValidUserResult result = new ValidUserResult();
		JSONObject jsonObject;
		JSONObject jsonObjectData;
		try {
			jsonObject = new JSONObject(resultInfo);

			result.setStatus(jsonObject.getString("status"));
			result.setStatusMessage(jsonObject.getString("statusMessage"));
			jsonObjectData = jsonObject.getJSONObject("data");
			result.setKey(jsonObjectData.getString("key"));
			result.setMobile(jsonObjectData.getString("mobile"));
			result.setUsername(jsonObjectData.getString("username"));

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return result;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
