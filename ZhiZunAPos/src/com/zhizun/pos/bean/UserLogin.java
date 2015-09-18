package com.zhizun.pos.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 登录用户实体类 创建人：李林中 创建日期：2014-12-1 下午12:18:48 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UserLogin extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.UserLogin";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String token;
	private UserInfo userInfo;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * 解析JSON格式的结果 封装，并返回为UserLogin实体
	 * 
	 * @param resultInfo
	 *            jSON格式的结果
	 * @return UserLogin实体
	 */
	public static UserLogin parse(String resultInfo) {
		Log.i(TAG, "Userlogin里面解析json字符串为：" + resultInfo);
		UserLogin userLogin = new UserLogin();
		UserInfo userInfo = new UserInfo();
		UserRole userRole;
		JSONObject tempJsonData;
		JSONObject tempJsonUser;
		JSONObject jsonUserRole;
		JSONObject jsonCurrentOrgn;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			userLogin.setStatus(jsonObject.getString("status"));
			userLogin.setStatusMessage(jsonObject.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			userLogin.setToken(tempJsonData.getString("token"));
			tempJsonUser = tempJsonData.getJSONObject("user");
			userInfo.setUserName(tempJsonUser.getString("userName"));
			userInfo.setUserId(tempJsonUser.getString("userId"));
			userInfo.setPhone(tempJsonUser.getString("phone"));
			userInfo.setEmail(tempJsonUser.getString("email"));
			if (!tempJsonUser.isNull("currentRole")) {
				userInfo.setCurrentRole(tempJsonUser.getString("currentRole"));
			}
			
			if (!tempJsonUser.isNull("currentOrgan")) {
				jsonCurrentOrgn = tempJsonUser.getJSONObject("currentOrgan");
				userInfo.getCurrentOrgan().setImagePath(jsonCurrentOrgn.getString("imagePath"));
				userInfo.getCurrentOrgan().setOrgId(jsonCurrentOrgn.getString("orgId"));
				userInfo.getCurrentOrgan().setOrgName(jsonCurrentOrgn.getString("orgName"));
				userInfo.getCurrentOrgan().setRoleId(jsonCurrentOrgn.getString("roleId"));
				userInfo.getCurrentOrgan().setRoleName(jsonCurrentOrgn.getString("roleName"));
			}
			if (!tempJsonUser.isNull("userOrgan")) {
				JSONArray jsonArray = tempJsonUser.getJSONArray("userOrgan");
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonUserRole = jsonArray.optJSONObject(i);
					userRole = new UserRole();
					userRole.setOrgId(jsonUserRole.getString("orgId"));
					userRole.setOrgName(jsonUserRole.getString("orgName"));
					userRole.setRoleId(jsonUserRole.getString("roleId"));
					userRole.setRoleName(jsonUserRole.getString("roleName"));
					userInfo.getUserRoleList().add(userRole);
				}
			}
			
			userLogin.setUserInfo(userInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userLogin;
	}

	// /**
	// * 解析JSON格式的结果 封装，并返回为UserLogin实体
	// * @param resultInfo jSON格式的结果
	// * @return UserLogin实体
	// */
	// public static UserLogin parse(String resultInfo) {
	// Log.i(TAG, "Userlogin里面解析json字符串为："+resultInfo);
	// UserLogin userLogin = new UserLogin();
	// try {
	// JSONObject jsonObject = new JSONObject(resultInfo);
	// userLogin.setResult(jsonObject.getInt("Result"));
	// userLogin.setMsg(jsonObject.getString("Msg"));
	// userLogin.setPwd(jsonObject.getString("Pwd"));
	// userLogin.setTelNo(jsonObject.getString("TelNo"));
	// userLogin.setUserID(jsonObject.getInt("UserID"));
	// userLogin.setUserName(jsonObject.getString("UserName"));
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return userLogin;
	// }
}
