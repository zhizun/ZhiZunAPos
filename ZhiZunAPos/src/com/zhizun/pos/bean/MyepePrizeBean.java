package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 有奖活动 序列化
 */
public class MyepePrizeBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String type;
	private String content;// 内容
	private String title;// 标题
	private String createOrgid;// 发起机构标识
	private String createOrgName;// 发起机构名称
	private String createUserid;// 发起人标识
	private String startTime;	// 活动开始日期

	private String userRecommendNum;
	private String introducerBonus;// 介绍人优惠
	private String perAwardNum;// 每人最多领取奖励次数

	private String userPickNum;
	private String referralBonus;// 领取人优惠
	private String perReceiveNum;// 每人最多领取优惠次数

	private String evtPicPath; // 活动介绍图片路径
	private String evtThumbPicPath; // 活动介绍缩略图路径

	private String userValidShareNum;// 有效分享数
	private String status; // 状态 0,已发布 1，已过期 2，已撤销
	private String userShareNum; // 分享数

	private String eventId;
	private String eventUrl;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateOrgid() {
		return createOrgid;
	}

	public void setCreateOrgid(String createOrgid) {
		this.createOrgid = createOrgid;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getIntroducerBonus() {
		return introducerBonus;
	}

	public void setIntroducerBonus(String introducerBonus) {
		this.introducerBonus = introducerBonus;
	}

	public String getReferralBonus() {
		return referralBonus;
	}

	public void setReferralBonus(String referralBonus) {
		this.referralBonus = referralBonus;
	}

	public String getPerReceiveNum() {
		return perReceiveNum;
	}

	public void setPerReceiveNum(String perReceiveNum) {
		this.perReceiveNum = perReceiveNum;
	}

	public String getPerAwardNum() {
		return perAwardNum;
	}

	public void setPerAwardNum(String perAwardNum) {
		this.perAwardNum = perAwardNum;
	}

	public String getUserValidShareNum() {
		return userValidShareNum;
	}

	public void setUserValidShareNum(String validShareNum) {
		this.userValidShareNum = validShareNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserShareNum() {
		return userShareNum;
	}

	public void setUserShareNum(String shareNum) {
		this.userShareNum = shareNum;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public String getUserRecommendNum() {
		return userRecommendNum;
	}

	public void setUserRecommendNum(String userRecommendNum) {
		this.userRecommendNum = userRecommendNum;
	}

	public String getUserPickNum() {
		return userPickNum;
	}

	public void setUserPickNum(String userPickNum) {
		this.userPickNum = userPickNum;
	}

	public String getEvtPicPath() {
		return evtPicPath;
	}

	public void setEvtPicPath(String evtPicPath) {
		this.evtPicPath = evtPicPath;
	}

	public String getEvtThumbPicPath() {
		return evtThumbPicPath;
	}

	public void setEvtThumbPicPath(String evtThumbPicPath) {
		this.evtThumbPicPath = evtThumbPicPath;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
