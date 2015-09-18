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

public class TeacherOrg extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = TeacherOrg.class.getName();

	private String orgId;// 班级ID
	private String name;// 班级名称
	private String shortName;// 班级名称
	private Boolean checkState;// 是否选中
	ArrayList<TeacherInfo> teacherInfoList = new ArrayList<TeacherInfo>();// 学生信息

	// studentId@studentName@classId@className;
	public String getReceivers() {
		StringBuffer sBuffer = new StringBuffer();
		for (TeacherInfo teacherInfo : teacherInfoList) {
			if (teacherInfo.getCheckState()) {
				sBuffer.append(teacherInfo.getTeachId() + "@"
						+ teacherInfo.getName() + "@" + orgId + "@" + name);
				sBuffer.append(",");
			}
		}
		String receivers = sBuffer.toString();
		return receivers;
	}

	public String getOrgId() {
		return orgId;
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

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	public ArrayList<TeacherInfo> getTeacherInfoList() {
		return teacherInfoList;
	}

	public void setTeacherInfoList(ArrayList<TeacherInfo> teacherInfoList) {
		this.teacherInfoList = teacherInfoList;
	}

}
