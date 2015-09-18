package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class MySystemMessageBean extends BaseBean {

	/**
	 * 系统消息
	 */
	private static final long serialVersionUID = 1L;
	private String title;//消息标题
	private String sendTime;//消息发送时间
	private String recieverId;//接收标识
	private String isRead;//是否已读	0：未读 1：已读
	private String content;//内容
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getRecieverId() {
		return recieverId;
	}
	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
