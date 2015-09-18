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

public class StudentClass extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = StudentClass.class.getName();

	private String claId;// 班级ID
	private String name;// 班级名称
	private Boolean checkState;// 是否选中
	ArrayList<StudentInfo> studentInfoList = new ArrayList<StudentInfo>();// 学生信息

	public String getReceivers() {
		StringBuffer sBuffer = new StringBuffer();
		for (StudentInfo studentInfo : studentInfoList) {
			if (studentInfo.getCheckState()) {
				sBuffer.append(studentInfo.getStuId() + "@"
						+ studentInfo.getName() + "@" + claId + "@" + name);
				sBuffer.append(",");
			}
		}
		String receivers = sBuffer.toString();
		return receivers;
	}

	// studentId@studentName@classId@className;

	// String receivers=
	public String getClaId() {
		return claId;
	}

	public void setClaId(String claId) {
		this.claId = claId;
	}

	public String getName() {
		return name == null ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StudentInfo> getStudentInfoList() {
		return studentInfoList;
	}

	public void setStudentInfoList(ArrayList<StudentInfo> studentInfoList) {
		this.studentInfoList = studentInfoList;
	}

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

}
