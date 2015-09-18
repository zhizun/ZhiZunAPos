package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class AttenceDetailParentList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = AttenceDetailParentList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private String dataCount;// 总考勤数量
	private List<AttenceDetailParent> attenceDetailParentList = new ArrayList<AttenceDetailParent>();

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static AttenceDetailParentList parse(String resultInfo)
			throws IOException, AppException {
		AttenceDetailParent attenceDetailParent = null;
		AttenceDetailParentList attenceDetailParentList = new AttenceDetailParentList();
		JSONObject tempJsonData;
		JSONObject jsonObjectData;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			attenceDetailParentList.setStatus(jsonObject.getString("status"));
			attenceDetailParentList.setTimestamp(jsonObject.getLong("timestamp"));
			attenceDetailParentList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			attenceDetailParentList.setDataCount(tempJsonData
					.getString("count"));

			JSONArray jsonArray = tempJsonData.getJSONArray("datas");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObjectData = jsonArray.optJSONObject(i);
				attenceDetailParent = new AttenceDetailParent();

				attenceDetailParent.setContent(jsonObjectData
						.getString("content"));
				attenceDetailParent.setRefId(jsonObjectData
						.getString("refId"));
				attenceDetailParent.setIsFav(jsonObjectData.getString("isFav"));
				attenceDetailParent.setOrgId(jsonObjectData
						.optString("orgId"));
				attenceDetailParent.setOrgName(jsonObjectData
						.getString("orgName"));
				attenceDetailParent.setRecordTime(jsonObjectData
						.getString("recordTime"));
				attenceDetailParent.setUserId(jsonObjectData
						.getString("userId"));
				attenceDetailParent.setUserName(jsonObjectData
						.getString("userName"));
				attenceDetailParent.setUserPhoto(jsonObjectData
						.getString("userPhoto"));

				attenceDetailParentList.getAttenceDetailParentList().add(
						attenceDetailParent);

			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return attenceDetailParentList;
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

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

	public List<AttenceDetailParent> getAttenceDetailParentList() {
		return attenceDetailParentList;
	}

	public void setAttenceDetailParentList(
			List<AttenceDetailParent> attenceDetailParentList) {
		this.attenceDetailParentList = attenceDetailParentList;
	}

}
