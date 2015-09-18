package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class OrgInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = OrgInfo.class.getName();
	
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	
	private String orgId;
	private String orgName;
	private String shortName;
	private String phone;
	private String addrInfo;
	private String logoPath;

	public static OrgInfo parse(String resultInfo) throws IOException,
	AppException {
		
		OrgInfo orgInfo = new OrgInfo();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			orgInfo.setStatus(jsonObject.getString("status"));
			orgInfo.setStatusMessage(jsonObject
					.getString("statusMessage"));

			JSONObject jsonObjectData = jsonObject.optJSONObject("data");
			if(jsonObjectData != null){
				orgInfo.setOrgId(jsonObjectData.getString("orgId"));
				orgInfo.setOrgName(jsonObjectData.getString("name"));
				orgInfo.setShortName(jsonObjectData.getString("shortName"));
				orgInfo.setPhone(jsonObjectData.getString("phone"));
				orgInfo.setLogoPath(jsonObjectData.getString("logoPath"));
				
				JSONObject jsonObjectDataAddr = jsonObject.optJSONObject("bmAddr");
				if(jsonObjectDataAddr != null){
					orgInfo.setAddrInfo(jsonObjectDataAddr.getString("addrInfo"));
				}
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return orgInfo;
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddrInfo() {
		return addrInfo;
	}
	public void setAddrInfo(String addrInfo) {
		this.addrInfo = addrInfo;
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
	
	
}
