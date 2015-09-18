package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class MySelfMessageBean extends BaseBean {

	/**
	 * 我的消息
	 */
	private static final long serialVersionUID = 1L;
	private String sendTime;//消息发送时间
	private String sysReplyContent;//系统回复内容
	private String sysReplyTime;//系统回复时间
	private String replyId;//回复标识
	private String content;//内容
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSysReplyContent() {
		return sysReplyContent;
	}
	public void setSysReplyContent(String sysReplyContent) {
		this.sysReplyContent = sysReplyContent;
	}
	public String getSysReplyTime() {
		return sysReplyTime;
	}
	public void setSysReplyTime(String sysReplyTime) {
		this.sysReplyTime = sysReplyTime;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
