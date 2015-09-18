package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 程序升级 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UpdateInfo extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String version;// 版本号
	private String note;// 升级内容
	private String lastUpdateTime;// 最后更新时间
	private String newFlag;// 是否有效
	private String upgradeId;// 升级ID
	private String clientType;// 客户端类型，0：Android；1：iOS
	private String isForceUpgrade;// 是否强制升级，0：否；1：是
	private String downloadUrl;// 客户端下载URL

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getUpgradeId() {
		return upgradeId;
	}

	public void setUpgradeId(String upgradeId) {
		this.upgradeId = upgradeId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getIsForceUpgrade() {
		return isForceUpgrade;
	}

	public void setIsForceUpgrade(String isForceUpgrade) {
		this.isForceUpgrade = isForceUpgrade;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

}
