package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 考勤 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class AttenceList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = AttenceList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private boolean isMaxDay = false;	//可 供编辑考勤的最大日期
	private boolean isMinDay = false;	//可供编辑考勤的最小日期（暂未实现）
	private ArrayList<Attence> attenceList = new ArrayList<Attence>();

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static AttenceList parse(String resultInfo) throws IOException,
			AppException {
		Attence attence = null;
		AttenceDetail attenceDetail = null;
		AttenceList attenceList = new AttenceList();
		JSONObject jsonObjectData;
		JSONObject jsonObjectAttenceDetail;
		JSONArray jsonArrayAttenceList;
		JSONArray jsonArrayRemarks;
		JSONObject jsonObjectType;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			attenceList.setStatus(jsonObject.getString("status"));
			attenceList.setStatusMessage(jsonObject.getString("statusMessage"));
			attenceList.setTimestamp(jsonObject.getLong("timestamp"));
			JSONObject metaData = jsonObject.optJSONObject("metaData");
			if(metaData!=null){
				String maxDayFlag = metaData.optString("isMaxDay");
				attenceList.setMaxDay(maxDayFlag!=null&&maxDayFlag.equals("true"));
				String minDayFlag = metaData.optString("isMinDay");
				attenceList.setMinDay(minDayFlag!=null&&minDayFlag.equals("true"));
			}
			
			
			JSONArray jsonArray = jsonObject.optJSONArray("data");
			if(jsonArray!=null&&jsonArray.length()>0){
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObjectData = jsonArray.optJSONObject(i);
					attence = new Attence();
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
					jsonArrayAttenceList = jsonObjectData
							.getJSONArray("fsiAttenceDetailList");
					for (int j = 0; j < jsonArrayAttenceList.length(); j++) {
						jsonObjectAttenceDetail = jsonArrayAttenceList
								.optJSONObject(j);
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
					attenceList.getAttenceList().add(attence);
	
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return attenceList;
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


	public ArrayList<Attence> getAttenceList() {
		return attenceList;
	}

	public void setAttenceList(ArrayList<Attence> attenceList) {
		this.attenceList = attenceList;
	}

	public boolean isMaxDay() {
		return isMaxDay;
	}

	public void setMaxDay(boolean isMaxDay) {
		this.isMaxDay = isMaxDay;
	}

	public boolean isMinDay() {
		return isMinDay;
	}

	public void setMinDay(boolean isMinDay) {
		this.isMinDay = isMinDay;
	}

}
