package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 个人资料 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class PersonInfoDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	PersonInfo personInfo = new PersonInfo();
	private ArrayList<StudentTeacherOrgClass> studentTeacherOrgClassList;
	private ArrayList<StudentClass> studentClassList;
	private ArrayList<UserRole> userRoleList;
	private int msgNum;
	private int feedbackNum;
	private String inviteCount;
	/**
	 * 解析JSON
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static PersonInfoDetail parse(String _post,String roles) {

		PersonInfoDetail personInfoDetail = new PersonInfoDetail();
		PersonInfo personInfo = new PersonInfo();
		JSONObject tempJsonechoParameter;
		JSONObject tempJsonBmaddr;
		JSONObject tempJsonData;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			personInfoDetail.setStatus(jsonObject.getString("status"));
			personInfoDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			JSONObject personalInfoJson=tempJsonData.getJSONObject("personalInfo");
			tempJsonechoParameter = personalInfoJson.getJSONObject("ppv");

			personInfo.setName(tempJsonechoParameter.getString("name"));

			personInfo.setUserName(tempJsonechoParameter.getString("userName"));
			personInfo.setUserId(tempJsonechoParameter.getString("userId"));
			personInfo.setPhone(tempJsonechoParameter.getString("phone"));

			personInfo.setPhotoPath(tempJsonechoParameter
					.getString("photoPath"));

			personInfo.setSex(tempJsonechoParameter.getString("sex"));
			tempJsonBmaddr = tempJsonechoParameter.getJSONObject("bmAddr");

			personInfo.setLat(tempJsonBmaddr.getString("lat"));
			personInfo.setLng(tempJsonBmaddr.getString("lng"));
			personInfo.setAddrInfo(tempJsonechoParameter.getString("addrInfo"));
			personInfo.setAddr(tempJsonBmaddr.getString("addr"));
			personInfo.setProvince(tempJsonBmaddr.getString("province"));
			personInfo.setCity(tempJsonBmaddr.getString("city"));
			personInfo.setCounty(tempJsonBmaddr.getString("county"));

			personInfoDetail.setPersonInfo(personInfo);
			//接口修改为一个，直接调用之前方法
			JSONArray classAll=tempJsonData.getJSONArray("classList");
				if (roles.equals("0")) {
					//家长端列表
					ArrayList<StudentTeacherOrgClass>	studentClassList=StudentTeacherOrgClassList.parseJsonData(classAll);
					personInfoDetail.setStudentTeacherOrgClassList(studentClassList);
				}else {
					//教师端学生列表
					ArrayList<StudentClass> studentClassList=StudentClassList.parseJsonData(classAll);
					personInfoDetail.setStudentClassList(studentClassList);
				}
				JSONObject rolesJson=tempJsonData.getJSONObject("roles");
				JSONArray rolesArrayList=rolesJson.getJSONArray("userOrgan");
				ArrayList<UserRole> userRoleList =UserRoleList.postRoleList(rolesArrayList);
				personInfoDetail.setUserRoleList(userRoleList);
				personInfoDetail.setMsgNum(tempJsonData.getInt("msgNum"));
				personInfoDetail.setFeedbackNum(tempJsonData.getInt("feedbackNum"));
				personInfoDetail.setInviteCount(tempJsonData.getString("inviteCount"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return personInfoDetail;
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

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	

	public ArrayList<StudentTeacherOrgClass> getStudentTeacherOrgClassList() {
		return studentTeacherOrgClassList;
	}

	public void setStudentTeacherOrgClassList(
			ArrayList<StudentTeacherOrgClass> studentTeacherOrgClassList) {
		this.studentTeacherOrgClassList = studentTeacherOrgClassList;
	}



	public ArrayList<StudentClass> getStudentClassList() {
		return studentClassList;
	}

	public void setStudentClassList(ArrayList<StudentClass> studentClassList) {
		this.studentClassList = studentClassList;
	}

	public ArrayList<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(ArrayList<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public int getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}

	public int getFeedbackNum() {
		return feedbackNum;
	}

	public void setFeedbackNum(int feedbackNum) {
		this.feedbackNum = feedbackNum;
	}

	public String getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(String inviteCount) {
		this.inviteCount = inviteCount;
	}

}
