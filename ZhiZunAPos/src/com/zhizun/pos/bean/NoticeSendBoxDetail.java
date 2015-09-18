package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 通知通知发送详情 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NoticeSendBoxDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String content;// 内容
	private String userId;// 发送人ID
	private String userName;// 发送人姓名
	private String lastUpdateTime;// 最后更新时间
	private String orgId;// 机构ID
	private String orgName;// 机构名称
	private String sendTime;// 发送时间
	private String isSendMsg;// 是否发送短信，0：否，1：是
	private String sendMode;// 发送模式，0：立即发送；1：定时发送
	private String taskTime;// 定时发送时间
	private String noticeId;// 通知ID

	private String recvNameList;// 接收人
	ArrayList<StudentClass> studentClassesList = new ArrayList<StudentClass>();// 接受人班级
																				// 名字

	/**
	 * 解析JSON得到 通知收发件箱列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NoticeSendBoxDetail parse(String _post) {
		NoticeSendBoxDetail noticeSendBoxDetail = new NoticeSendBoxDetail();
		StudentClass studentClass = null;
		StudentInfo studentInfo = null;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			noticeSendBoxDetail.setStatus(jsonObject.getString("status"));
			noticeSendBoxDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			noticeSendBoxDetail.setContent(tempJsonData.getString("content"));
			noticeSendBoxDetail.setIsSendMsg(tempJsonData
					.getString("isSendMsg"));
			noticeSendBoxDetail.setLastUpdateTime(tempJsonData
					.getString("lastUpdateTime"));
			noticeSendBoxDetail.setNoticeId(tempJsonData.getString("noticeId"));
			noticeSendBoxDetail.setOrgId(tempJsonData.getString("orgId"));
			noticeSendBoxDetail.setOrgName(tempJsonData.getString("orgName"));
			noticeSendBoxDetail.setRecvNameList(tempJsonData
					.getString("recvNameList"));
			noticeSendBoxDetail.setSendMode(tempJsonData.getString("sendMode"));
			noticeSendBoxDetail.setSendTime(tempJsonData.getString("sendTime"));

			noticeSendBoxDetail.setTaskTime(tempJsonData.getString("taskTime"));
			noticeSendBoxDetail.setUserId(tempJsonData.getString("userId"));
			noticeSendBoxDetail.setUserName(tempJsonData.getString("userName"));

			tempJsonDatas = tempJsonData.getJSONObject("recvGroupMap");
			String stuforClass = "";
			String students = "";
			Iterator<?> it = tempJsonDatas.keys();
			while (it.hasNext()) {// 遍历tempJsonDatas
				stuforClass = (String) it.next().toString();
				studentClass = new StudentClass();
				studentClass.setName(stuforClass);
				students = tempJsonDatas.getString(stuforClass);
				String[] strings = students.split(",");
				for (String str : strings) {
					studentInfo = new StudentInfo();
					studentInfo.setName(str);
					studentClass.getStudentInfoList().add(studentInfo);
				}
				noticeSendBoxDetail.getStudentClassesList().add(studentClass);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noticeSendBoxDetail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(String isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getRecvNameList() {
		return recvNameList;
	}

	public void setRecvNameList(String recvNameList) {
		this.recvNameList = recvNameList;
	}

	public ArrayList<StudentClass> getStudentClassesList() {
		return studentClassesList;
	}

	public void setStudentClassesList(ArrayList<StudentClass> studentClassesList) {
		this.studentClassesList = studentClassesList;
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
