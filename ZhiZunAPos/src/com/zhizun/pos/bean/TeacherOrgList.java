package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

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

public class TeacherOrgList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	ArrayList<TeacherOrg> teacherOrgList = new ArrayList<TeacherOrg>();

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static TeacherOrgList parse(String _post) {

		TeacherOrg teacherOrg = null;
		TeacherInfo teacherInfo = null;
		TeacherOrgList teacherOrgList = new TeacherOrgList();
		JSONObject tempJsonData = null;
		JSONObject tempJsonOrg = null;
		JSONObject tempJsonTeacher = null;
		JSONArray jsonArrayTeacher = null;
		JSONArray jsonArrayOrg = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			JSONArray jsonArrayDataArray = jsonObject.getJSONArray("data");
			teacherOrgList.setStatus(jsonObject.getString("status"));
			teacherOrgList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			for (int i = 0; i < jsonArrayDataArray.length(); i++) {
				tempJsonData = jsonArrayDataArray.optJSONObject(i);
				jsonArrayTeacher = tempJsonData.getJSONArray("teacherList");
				tempJsonOrg = tempJsonData.getJSONObject("myClass");
				teacherOrg = new TeacherOrg();
				teacherOrg.setOrgId(tempJsonOrg.getString("orgId"));
				teacherOrg.setName(tempJsonOrg.getString("name"));
				teacherOrg.setCheckState(false);
				teacherOrg.setShortName(tempJsonOrg.getString("shortName"));

				for (int j = 0; j < jsonArrayTeacher.length(); j++) {
					tempJsonTeacher = jsonArrayTeacher.optJSONObject(j);
					teacherInfo = new TeacherInfo();
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
					teacherOrg.getTeacherInfoList().add(teacherInfo);
				}
				teacherOrgList.getTeacherOrgList().add(teacherOrg);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return teacherOrgList;
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

	public ArrayList<TeacherOrg> getTeacherOrgList() {
		return teacherOrgList;
	}

	public void setTeacherOrgList(ArrayList<TeacherOrg> teacherOrgList) {
		this.teacherOrgList = teacherOrgList;
	}

}
