package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;
/**
 * 上传手机联系人
 * @author lilinzhong
 *
 * 2015-8-19下午4:01:02
 */
public class CircleofFriendsBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = CircleofFriendsBean.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因

	public static CircleofFriendsBean parse(String resultInfo) throws IOException,
			AppException {
		CircleofFriendsBean orgIntroWrapper = new CircleofFriendsBean();

		try {
			if (resultInfo!=null) {
			JSONObject jsonObject = new JSONObject(resultInfo);
			
			orgIntroWrapper.setStatus(jsonObject.getString("status"));
			orgIntroWrapper.setStatusMessage(jsonObject
					.getString("statusMessage"));
			orgIntroWrapper.setTimestamp(jsonObject.getLong("timestamp"));

			if (!jsonObject.isNull("data")) {
//				JSONObject jsonDataObject = jsonObject.getJSONObject("data");
//				orgIntroBean.setOrgId(jsonDataObject.getString("orgId"));
//				orgIntroBean.setOrgName(jsonDataObject.getString("name"));
//				orgIntroBean.setShortName(jsonDataObject.getString("shortName"));
//				orgIntroBean.setLogoPath(jsonDataObject.getString("logoPath"));
//				orgIntroBean.setOrgDesc(jsonDataObject.getString("orgDesc"));
//				orgIntroBean.setPhone(jsonDataObject.optString("phone"));
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

}
