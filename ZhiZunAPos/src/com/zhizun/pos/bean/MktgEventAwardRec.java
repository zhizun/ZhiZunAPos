package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;
	/**
	 * 领奖人信息
	 */
public class MktgEventAwardRec extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String receiverTime;//领奖时间
	public String getReceiverTime() {
		return receiverTime;
	}
	public void setReceiverTime(String receiverTime) {
		this.receiverTime = receiverTime;
	}
	
}
