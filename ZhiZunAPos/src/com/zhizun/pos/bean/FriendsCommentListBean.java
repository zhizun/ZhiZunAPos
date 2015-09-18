package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

public class FriendsCommentListBean extends BaseBean {

	/**
	 * 朋友圈评论
	 */
	private static final long serialVersionUID = 1L;
	
	private String commentId;// 评论标识
	private String showUserName;// 朋友圈显示名称
	private String userShortName;// 系统用户名称
	private String userPhoto;// 头像
	private String userPhone;// 电话
	private String comDate;// 评论时间
	private String orgName;// 机构名称
	private String courName;// 课程名称
	private String distance;// 距离
	private String markAvg;// 综合评分
	private String markA;// 第一项评分
	private String markB;// 第二项评分
	private String markC;// 第三项评分
	private String markD;// 第四项评分
	private String markE;// 第五项评分
	private String content;// 评论内容
	private String helpfulNum;// 有多少用户觉得有用
	private String isHelpful;// 感谢，0：用户未感谢，1：用户已感谢；
	private String isAnonymous;
	private String userId; // 发表评论的userId
	private String orgId;// 机构ID
	private String courId;// 课程ID
	private String logoPath;// 机构图片
	
	private boolean CurrenUserLike;// 是否感谢过
	
	private String helpfulName;// 我的评论，已感谢用户
	
	List<Rating> ratingList=new ArrayList<Rating>();
	
	private List<Photo> picList = new ArrayList<Photo>();// 图片集合

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getShowUserName() {
		return showUserName;
	}

	public void setShowUserName(String showUserName) {
		this.showUserName = showUserName;
	}

	public String getUserShortName() {
		return userShortName;
	}

	public void setUserShortName(String userShortName) {
		this.userShortName = userShortName;
	}

	public String getComDate() {
		return comDate;
	}

	public void setComDate(String comDate) {
		this.comDate = comDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCourName() {
		return courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getMarkAvg() {
		return markAvg;
	}

	public void setMarkAvg(String markAvg) {
		this.markAvg = markAvg;
	}

	public String getMarkA() {
		return markA;
	}

	public void setMarkA(String markA) {
		this.markA = markA;
	}

	public String getMarkB() {
		return markB;
	}

	public void setMarkB(String markB) {
		this.markB = markB;
	}

	public String getMarkC() {
		return markC;
	}

	public void setMarkC(String markC) {
		this.markC = markC;
	}

	public String getMarkD() {
		return markD;
	}

	public void setMarkD(String markD) {
		this.markD = markD;
	}

	public String getMarkE() {
		return markE;
	}

	public void setMarkE(String markE) {
		this.markE = markE;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHelpfulNum() {
		return helpfulNum;
	}

	public void setHelpfulNum(String helpfulNum) {
		this.helpfulNum = helpfulNum;
	}

	public String getIsHelpful() {
		return isHelpful;
	}

	public void setIsHelpful(String isHelpful) {
		this.isHelpful = isHelpful;
	}

	public List<Photo> getPicList() {
		return picList;
	}

	public void setPicList(List<Photo> picList) {
		this.picList = picList;
	}

	public List<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}

	public String getHelpfulName() {
		return helpfulName;
	}

	public void setHelpfulName(String helpfulName) {
		this.helpfulName = helpfulName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public boolean getCurrenUserLike() {
		return CurrenUserLike;
	}

	public void setCurrenUserLike(boolean currenUserLike) {
		CurrenUserLike = currenUserLike;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getUserId() {
		return userId == null ? "" : userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCourId() {
		return courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	

}
