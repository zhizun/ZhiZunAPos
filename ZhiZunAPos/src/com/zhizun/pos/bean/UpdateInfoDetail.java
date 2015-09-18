package com.zhizun.pos.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 程序升级 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UpdateInfoDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	UpdateInfo updateInfo = new UpdateInfo();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static UpdateInfoDetail parse(String _post) {

		UpdateInfoDetail updateInfoDetail = null;
		UpdateInfo updateInfo;
		JSONObject tempJsonechoParameter;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			updateInfoDetail = new UpdateInfoDetail();
			updateInfoDetail.setStatus(jsonObject.getString("status"));
			updateInfoDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));

			tempJsonechoParameter = jsonObject.getJSONObject("data");
			if (null != tempJsonechoParameter) {
				updateInfo = new UpdateInfo();
				updateInfo.setClientType(tempJsonechoParameter
						.getString("clientType"));
				updateInfo.setDownloadUrl(tempJsonechoParameter
						.getString("downloadUrl"));
				updateInfo.setIsForceUpgrade(tempJsonechoParameter
						.getString("isForceUpgrade"));
				updateInfo.setLastUpdateTime(tempJsonechoParameter
						.getString("lastUpdateTime"));
				updateInfo.setNewFlag(tempJsonechoParameter.getString("newFlag"));
				updateInfo.setNote(tempJsonechoParameter.getString("note"));
				updateInfo.setUpgradeId(tempJsonechoParameter
						.getString("upgradeId"));
				updateInfo.setVersion(tempJsonechoParameter.getString("version"));
				updateInfoDetail.setUpdateInfo(updateInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return updateInfoDetail;
	}

	public UpdateInfo getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(UpdateInfo updateInfo) {
		this.updateInfo = updateInfo;
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
