package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 学生所属班级实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Org extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = Org.class.getName();

	private String orgId;// 机构ID
	private String name;// 机构名称
	private String shortName;// 机构名称
	private String claId;// 班级ID
	private String claName;// 班级名称
	private String logoPath;// 机构logo
	private Boolean checkState;// 是否选中

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getClaId() {
		return claId;
	}

	public void setClaId(String claId) {
		this.claId = claId;
	}

	public String getClaName() {
		return claName;
	}

	public void setClaName(String claName) {
		this.claName = claName;
	}



	

}
