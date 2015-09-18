package com.zhizun.pos.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;

/**
 * 学生所属班级实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class TeacherOrgClassList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	ArrayList<TeacherOrgClass> teacherOrgClassList = new ArrayList<TeacherOrgClass>();

	public ArrayList<TeacherOrgClass> getTeacherOrgClassList() {
		return teacherOrgClassList;
	}

	public void setTeacherOrgClassList(
			ArrayList<TeacherOrgClass> teacherOrgClassList) {
		this.teacherOrgClassList = teacherOrgClassList;
	}

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static TeacherOrgClassList parse(String _post) {

		TeacherOrgClass teacherOrgClass = null;

		TeacherInfo teacherInfo = null;
		TeacherOrgClassList teacherOrgClassList = new TeacherOrgClassList();
		JSONObject tempJsonData = null;
		JSONObject tempJsonOrg = null;
		JSONObject tempJsonTeacher = null;
		JSONArray jsonArrayTeacher = null;
		JSONArray jsonArrayOrg = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			JSONArray jsonArrayDataArray = jsonObject.getJSONArray("data");
			teacherOrgClassList.setStatus(jsonObject.getString("status"));
			teacherOrgClassList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			for (int i = 0; i < jsonArrayDataArray.length(); i++) {
				tempJsonData = jsonArrayDataArray.optJSONObject(i);
				jsonArrayTeacher = tempJsonData.getJSONArray("teacherList");
				tempJsonOrg = tempJsonData.getJSONObject("myClass");
				teacherOrgClass = new TeacherOrgClass();
				teacherOrgClass.setClaId(tempJsonOrg.getString("claId"));
				teacherOrgClass.setClaName(tempJsonOrg.getString("name"));
				teacherOrgClass.setCheckState(false);

				for (int j = 0; j < jsonArrayTeacher.length(); j++) {
					tempJsonTeacher = jsonArrayTeacher.optJSONObject(j);
					teacherInfo = new TeacherInfo();
					if (AppContext.getApp().getUserLoginSharedPre()
							.getUserInfo().getUserId()
							.equals(tempJsonTeacher.getString("userId"))) {
						continue;
					}
					teacherInfo.setUserId(tempJsonTeacher.getString("userId"));
					teacherInfo.setName(tempJsonTeacher.getString("name"));
					teacherInfo.setPhotoPath(tempJsonTeacher
							.getString("photoPath"));
					teacherInfo.setOwnOrgId(tempJsonTeacher
							.getString("ownOrgId"));
					teacherInfo
							.setTeachId(tempJsonTeacher.getString("teachId"));
					teacherInfo
					.setIsJoinOrg(tempJsonTeacher.getString("isJoinOrg"));
					teacherInfo.setIsOrgJoin(
							tempJsonTeacher.getString("isJoinOrg") != null
							&&!tempJsonTeacher.getString("isJoinOrg").equals("")
							&&!tempJsonTeacher.getString("isJoinOrg").equals("0")
						);
					teacherInfo.setPost(tempJsonTeacher.getString("post"));
					teacherInfo.setPhone(tempJsonTeacher.getString("phone"));

					teacherInfo.setCheckState(false);
					teacherOrgClass.getTeacherInfoList().add(teacherInfo);
				}
				teacherOrgClassList.getTeacherOrgClassList().add(
						teacherOrgClass);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return teacherOrgClassList;
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

}
