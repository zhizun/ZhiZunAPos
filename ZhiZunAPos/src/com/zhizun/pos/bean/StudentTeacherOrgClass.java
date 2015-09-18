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

public class StudentTeacherOrgClass extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = StudentTeacherOrgClass.class.getName();

	private String childId;//
	private String childName;//

	ArrayList<TeacherOrgClass> techeOrgClasseList = new ArrayList<TeacherOrgClass>();//

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public ArrayList<TeacherOrgClass> getTecheOrgClasseList() {
		return techeOrgClasseList;
	}

	public void setTecheOrgClasseList(ArrayList<TeacherOrgClass> techeOrgClasseList) {
		this.techeOrgClasseList = techeOrgClasseList;
	}

	
}
