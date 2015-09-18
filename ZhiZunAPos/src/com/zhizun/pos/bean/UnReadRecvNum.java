package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 教师端未读数量提示 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UnReadRecvNum extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String type;//
	private String count;//
	private String userId;//

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
