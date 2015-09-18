package com.zhizun.pos.bean;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;


/**
 * 教师所属机构 班级实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class StudentTeacherOrgClassList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	ArrayList<StudentTeacherOrgClass> studentTeacherOrgClasseList = new ArrayList<StudentTeacherOrgClass>();

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static StudentTeacherOrgClassList parse(String _post) {

		StudentTeacherOrgClassList studentTeacherOrgClasseList = new StudentTeacherOrgClassList();

		try {
			JSONObject jsonObject = new JSONObject(_post);
			studentTeacherOrgClasseList.setStatus(jsonObject
					.getString("status"));
			studentTeacherOrgClasseList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			JSONArray jsonArrayDataArray = jsonObject.getJSONArray("data");

			ArrayList<StudentTeacherOrgClass> jsonStudent	=parseJsonData(jsonArrayDataArray);
			studentTeacherOrgClasseList.setStudentTeacherOrgClasseList(jsonStudent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return studentTeacherOrgClasseList;
	}

	public static ArrayList<StudentTeacherOrgClass> parseJsonData(
			JSONArray jsonArrayDataArray) throws JSONException {
		TeacherOrgClass teacherOrgClass;
		TeacherInfo teacherInfo;
		StudentTeacherOrgClass studentTeacherOrgClass;
		JSONObject tempJsonData;
		JSONObject tempJsonTeacherInfo;
		JSONObject tempJsonTeacherOrgClass;
		JSONArray jsonArrayTeacherOrgClass;
		JSONArray jsonArrayTeacherInfo;
		ArrayList<StudentTeacherOrgClass> studentTeacherOrgs = new ArrayList<StudentTeacherOrgClass>();
		for (int i = 0; i < jsonArrayDataArray.length(); i++) {
			tempJsonData = jsonArrayDataArray.optJSONObject(i);
			studentTeacherOrgClass = new StudentTeacherOrgClass();
			studentTeacherOrgClass.setChildId(tempJsonData
					.getString("childId"));
			studentTeacherOrgClass.setChildName(tempJsonData
					.getString("childName"));
			jsonArrayTeacherOrgClass = tempJsonData
					.getJSONArray("myClassList");
			for (int k = 0; k < jsonArrayTeacherOrgClass.length(); k++) {
				tempJsonTeacherOrgClass = jsonArrayTeacherOrgClass
						.optJSONObject(k);
				teacherOrgClass = new TeacherOrgClass();
				teacherOrgClass.setClaId(tempJsonTeacherOrgClass
						.getString("claId"));
				teacherOrgClass.setClaName(tempJsonTeacherOrgClass
						.getString("claName"));
				teacherOrgClass.setOrgId(tempJsonTeacherOrgClass
						.getString("orgId"));
				teacherOrgClass.setOrgName(tempJsonTeacherOrgClass
						.getString("orgName"));
				jsonArrayTeacherInfo = tempJsonTeacherOrgClass.getJSONArray("teaList");
				for (int j = 0; j < jsonArrayTeacherInfo.length(); j++) {
					tempJsonTeacherInfo = jsonArrayTeacherInfo
							.optJSONObject(j);
					teacherInfo = new TeacherInfo();
					teacherInfo.setName(tempJsonTeacherInfo
							.getString("name"));
					teacherInfo.setPhotoPath(tempJsonTeacherInfo
							.getString("photoPath"));
					teacherInfo.setOwnOrgId(tempJsonTeacherInfo
							.getString("ownOrgId"));
					teacherInfo.setTeachId(tempJsonTeacherInfo
							.getString("teachId"));
					teacherInfo.setIsJoinOrg(tempJsonTeacherInfo
							.getString("isJoinOrg"));
					teacherInfo.setIsOrgJoin(
							tempJsonTeacherInfo.getString("isJoinOrg") != null
							&&!tempJsonTeacherInfo.getString("isJoinOrg").equals("")
							&&!tempJsonTeacherInfo.getString("isJoinOrg").equals("0")
						);
					teacherInfo.setPost(tempJsonTeacherInfo
							.getString("post"));
					teacherInfo.setPhone(tempJsonTeacherInfo
							.getString("phone"));
					teacherOrgClass.getTeacherInfoList().add(teacherInfo);
				}
				studentTeacherOrgClass.getTecheOrgClasseList().add(
						teacherOrgClass);
			}
			studentTeacherOrgs.add(studentTeacherOrgClass);
		}
		return studentTeacherOrgs;
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

	public ArrayList<StudentTeacherOrgClass> getStudentTeacherOrgClasseList() {
		return studentTeacherOrgClasseList;
	}

	public void setStudentTeacherOrgClasseList(
			ArrayList<StudentTeacherOrgClass> studentTeacherOrgClasseList) {
		this.studentTeacherOrgClasseList = studentTeacherOrgClasseList;
	}

}
