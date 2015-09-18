package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class OrgIntroWrapper extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = OrgIntroWrapper.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因
	private OrgIntroBean orgIntroBean = new OrgIntroBean();

	public static OrgIntroWrapper parse(String resultInfo) throws IOException,
			AppException {
		OrgIntroWrapper orgIntroWrapper = new OrgIntroWrapper();
		OrgIntroBean orgIntroBean = orgIntroWrapper.getOrgIntroBean();

		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			orgIntroWrapper.setStatus(jsonObject.getString("status"));
			orgIntroWrapper.setStatusMessage(jsonObject
					.getString("statusMessage"));
			orgIntroWrapper.setTimestamp(jsonObject.getLong("timestamp"));

			if (!jsonObject.isNull("data")) {
				JSONObject jsonDataObject = jsonObject.getJSONObject("data");
				orgIntroBean.setOrgId(jsonDataObject.getString("orgId"));
				orgIntroBean.setOrgName(jsonDataObject.getString("name"));
				orgIntroBean.setShortName(jsonDataObject.getString("shortName"));
				orgIntroBean.setLogoPath(jsonDataObject.getString("logoPath"));
				orgIntroBean.setOrgDesc(jsonDataObject.getString("orgDesc"));
				orgIntroBean.setPhone(jsonDataObject.optString("phone"));
				
				if(!jsonDataObject.isNull("bmAddr")){
					JSONObject jsonObjectDataAddr = jsonDataObject.getJSONObject("bmAddr");
					orgIntroBean.setAddrInfo(jsonObjectDataAddr.getString("addr"));
				}
				
				if(!jsonDataObject.isNull("orgCourseList")){
					JSONArray jsonArray = jsonDataObject.getJSONArray("orgCourseList");
					orgIntroBean.setOrgCourseList(CourseListViewBean.parse(jsonArray));
				}
				
				if(!jsonDataObject.isNull("envPicList")){
					JSONArray jsonArray = jsonDataObject.getJSONArray("envPicList");
					orgIntroBean.setEnvPicList(PhotoListWrapper.parse(jsonArray));
				}

				if(!jsonDataObject.isNull("awardPicList")){
					JSONArray jsonArray = jsonDataObject.getJSONArray("awardPicList");
					orgIntroBean.setAwardPicList(PhotoListWrapper.parse(jsonArray));
				}

				if(!jsonDataObject.isNull("orgTeacherList")){
					JSONArray jsonArray = jsonDataObject.getJSONArray("orgTeacherList");
					orgIntroBean.setOrgTeacherList(TeacherInfoListWrapper.parse(jsonArray));
				}
			}

		}catch (JSONException e) {
			e.printStackTrace();
		}

		return orgIntroWrapper;
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

	public OrgIntroBean getOrgIntroBean() {
		return orgIntroBean;
	}

	public void setOrgIntroBean(OrgIntroBean orgIntroBean) {
		this.orgIntroBean = orgIntroBean;
	}

}
