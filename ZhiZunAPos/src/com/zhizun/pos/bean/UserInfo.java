package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 用户信息实体类 创建人：李林中 创建日期：2014-12-1 下午12:18:48 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UserInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.UserInfo";

	private String userName;//
	private String userId;//
	private String email;
	private String phone;
	private String currentRole; 	//当前角色 角色类型 0：家长，1：老师，2：机构管理员，3：机构超级管理员
	private String loginName;		//最近一次登录使用的账号，可能是 userName email phone
	private String loginPseudoCode;	//登录时使用的密码，保存的是伪密码
	private UserRole currentOrgan = new UserRole();// 当前角色信息
	private List<UserRole> userRoleList = new ArrayList<UserRole>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public UserRole getCurrentOrgan() {
		return currentOrgan;
	}

	public void setCurrentOrgan(UserRole currentOrgan) {
		this.currentOrgan = currentOrgan;
	}

	public String getLoginName() {
		return loginName == null ? "" : loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPseudoCode() {
		return loginPseudoCode == null ? "" : loginPseudoCode;
	}

	public void setLoginPseudoCode(String loginPseudoCode) {
		this.loginPseudoCode = loginPseudoCode;
	}

}
