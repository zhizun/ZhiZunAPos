package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class ShareResult extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = OrgInfo.class.getName();

	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	private String orgId;
	private String orgName;
	private String logoPath;
	private String shareId;

	public static ShareResult parse(String resultInfo) throws IOException,
			AppException {

		ShareResult shareResult = new ShareResult();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			shareResult.setStatus(jsonObject.getString("status"));
			shareResult.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONObject jsonObjectData = jsonObject.optJSONObject("data");
			if (jsonObjectData != null) {
				shareResult.setOrgId(jsonObjectData.getString("orgId"));
				shareResult.setOrgName(jsonObjectData.getString("orgName"));
				shareResult.setLogoPath(jsonObjectData.getString("logoPath"));
				shareResult.setShareId(jsonObjectData.getString("shareId"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return shareResult;
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

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
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

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

}