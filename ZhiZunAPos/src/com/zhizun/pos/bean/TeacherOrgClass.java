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
 * 教师所属机构 班级实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class TeacherOrgClass extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = TeacherOrgClass.class.getName();

	private String orgId;//
	private String orgName;//
	private String claId;//
	private String claName;//
	private Boolean checkState;// 是否选中
	ArrayList<TeacherInfo> teacherInfoList = new ArrayList<TeacherInfo>();// 学生信息
	// studentId@studentName@classId@className;
		public String getReceivers() {
			StringBuffer sBuffer = new StringBuffer();
			for (TeacherInfo teacherInfo : teacherInfoList) {
				if (teacherInfo.getCheckState()) {
					sBuffer.append(teacherInfo.getTeachId() + "@"
							+ teacherInfo.getName() + "@" + claId + "@" + claName);
					sBuffer.append(",");
				}
			}
			String receivers = sBuffer.toString();
			return receivers;
		}
	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public ArrayList<TeacherInfo> getTeacherInfoList() {
		return teacherInfoList;
	}

	public void setTeacherInfoList(ArrayList<TeacherInfo> teacherInfoList) {
		this.teacherInfoList = teacherInfoList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
