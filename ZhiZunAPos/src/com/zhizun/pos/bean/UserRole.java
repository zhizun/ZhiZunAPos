package com.zhizun.pos.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 用户角色实体类 创建人：李林中 创建日期：2014-12-1 下午12:18:48 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UserRole extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = UserRole.class.getName();

	private String roleId;// 角色类型 0：家长，1：老师，2：机构管理员，3：机构超级管理员
	private String roleName;// 角色名字
	private String orgId;// 机构ID
	private String orgName;// 机构名字
	private String imagePath;//图片

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
