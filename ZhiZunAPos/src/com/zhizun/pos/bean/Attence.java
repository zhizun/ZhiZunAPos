package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 考勤实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Attence extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private String userName;// 考勤人姓名
	private String userId;// 考勤人ID
	private String lastUpdateTime;// 最后更新时间

	private String expendedChourNum;// 消耗课时数

	private String claId;// 班级ID
	private String attendNum;// 已到人数
	private String absentNum;// 缺席人数
	private String recTime;	//记录考勤时间
	private String orgId;// 机构ID
	private String orgName;// 机构名称
	private String attenceId;// 考勤ID
	private String claName;// 班级名称
	
	private String attenceDate;	//考勤日期，与recTime不同的是，attenceDate可以是当天，也可以是补录考勤的日期
	private String attenceTime;	//考勤时间

	ArrayList<AttenceDetail> attenceDetailList = new ArrayList<AttenceDetail>();
	
	//考勤删除标识
	private boolean isDeleted = false;

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static Attence parse(String resultInfo) throws IOException,
			AppException {
		Attence attence = new Attence();
		AttenceDetail attenceDetail = null;

		JSONObject jsonObjectData;
		JSONObject jsonObjectAttenceDetail;
		JSONArray jsonArrayAttenceList;
		JSONArray jsonArrayRemarks;
		JSONObject jsonObjectType;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			attence.setStatus(jsonObject.getString("status"));
			attence.setStatusMessage(jsonObject.getString("statusMessage"));
			jsonObjectData = jsonObject.getJSONObject("data");

			attence.setAbsentNum(jsonObjectData.getString("absentNum"));
			attence.setAttendNum(jsonObjectData.getString("attendNum"));
			attence.setClaId(jsonObjectData.getString("claId"));
			attence.setExpendedChourNum(jsonObjectData
					.getString("expendedChourNum"));
			attence.setLastUpdateTime(jsonObjectData
					.getString("lastUpdateTime"));
			attence.setOrgId(jsonObjectData.getString("orgId"));
			attence.setOrgName(jsonObjectData.getString("orgName"));
			attence.setRecTime(jsonObjectData.getString("recTime"));
			attence.setUserId(jsonObjectData.getString("userId"));
			attence.setUserName(jsonObjectData.getString("userName"));
			attence.setClaName(jsonObjectData.getString("claName"));
			attence.setAttenceId(jsonObjectData.getString("attenceId"));
			attence.setAttenceDate(jsonObjectData.getString("attenceDate"));
			attence.setAttenceTime(jsonObjectData.getString("attenceTime"));
			jsonArrayAttenceList = jsonObjectData
					.getJSONArray("fsiAttenceDetailList");
			for (int j = 0; j < jsonArrayAttenceList.length(); j++) {
				jsonObjectAttenceDetail = jsonArrayAttenceList.optJSONObject(j);
				attenceDetail = new AttenceDetail();
				attenceDetail.setAttenceId(jsonObjectAttenceDetail
						.getString("attenceId"));
				attenceDetail.setAttenceTime(jsonObjectAttenceDetail
						.getString("attenceTime"));
				attenceDetail.setExpendedChourNum(jsonObjectAttenceDetail
						.getString("expendedChourNum"));
				attenceDetail.setDetailId(jsonObjectAttenceDetail
						.getString("detailId"));
				attenceDetail.setLastUpdateTime(jsonObjectAttenceDetail
						.getString("lastUpdateTime"));
				attenceDetail.setStatus(jsonObjectAttenceDetail
						.getString("status"));
				attenceDetail.setStuId(jsonObjectAttenceDetail
						.getString("stuId"));
				attenceDetail.setStuName(jsonObjectAttenceDetail
						.getString("stuName"));
				attenceDetail.setUserId(jsonObjectAttenceDetail
						.getString("userId"));
				attenceDetail.setUserName(jsonObjectAttenceDetail
						.getString("userName"));
				jsonArrayRemarks=jsonObjectAttenceDetail.getJSONArray("remarks");
				jsonObjectType=jsonArrayRemarks.optJSONObject(0);
				if (jsonObjectType!=null) {
				Remarks remarks=new Remarks();
				remarks.setTypt(jsonObjectType.getString("type"));
				remarks.setNote(jsonObjectType.getString("note"));
				remarks.setRecordtime(jsonObjectType.getString("recordTime"));
				attenceDetail.getRemarks().add(remarks);
				}
				attence.getAttenceDetailList().add(attenceDetail);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return attence;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getExpendedChourNum() {
		return expendedChourNum;
	}

	public void setExpendedChourNum(String expendedChourNum) {
		this.expendedChourNum = expendedChourNum;
	}

	public String getClaId() {
		return claId;
	}

	public void setClaId(String claId) {
		this.claId = claId;
	}

	public String getAttendNum() {
		return attendNum;
	}

	public void setAttendNum(String attendNum) {
		this.attendNum = attendNum;
	}

	public String getAbsentNum() {
		return absentNum;
	}

	public void setAbsentNum(String absentNum) {
		this.absentNum = absentNum;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
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

	

	public ArrayList<AttenceDetail> getAttenceDetailList() {
		return attenceDetailList;
	}

	public void setAttenceDetailList(ArrayList<AttenceDetail> attenceDetailList) {
		this.attenceDetailList = attenceDetailList;
	}

	public String getAttenceId() {
		return attenceId;
	}

	public void setAttenceId(String attenceId) {
		this.attenceId = attenceId;
	}

	public String getClaName() {
		return claName;
	}

	public void setClaName(String claName) {
		this.claName = claName;
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

	public String getAttenceDate() {
		return attenceDate;
	}

	public void setAttenceDate(String attenceDate) {
		this.attenceDate = attenceDate;
	}

	public String getAttenceTime() {
		return attenceTime;
	}

	public void setAttenceTime(String attenceTime) {
		this.attenceTime = attenceTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
