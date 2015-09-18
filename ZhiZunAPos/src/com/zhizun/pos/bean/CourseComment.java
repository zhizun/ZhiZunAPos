package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

public class CourseComment {

	/**
	 * 评论ID
	 */
	String commentId;

	/**
	 * 用户ID
	 */
	String userId;
	
	/**
	 * 用户名称
	 */
	String userName;
	
	/**
	 * 用户头像
	 */
	String userPhoto;

	String orgId;

	String orgName;

	String courId;

	String courName;

	/**
	 * 是否匿名评价（0-不匿名,1-匿名）
	 */
	String isAnonymous;

	/**
	 * 评价可见范围（0-公开,1-朋友圈）
	 */
	String range;

	/**
	 * 评论内容
	 */
	String content;
	
	/**
	 * 评论时间
	 */
	String commentTime;
	
	String photoPath;

	/**
	 * 评分列表
	 */
	List<Rating> ratingList = new ArrayList<Rating>();

	/**
	 * 评价时候上传图片列表
	 */
	List<Photo> photoList = new ArrayList<Photo>();

	public String getCommentId() {
		return commentId == null ? "" : commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId == null ? "" : userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getContent() {
		return content == null ? "" : content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentTime() {
		return commentTime == null ? "" : commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public List<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}

	public String getOrgId() {
		return orgId == null ? "" : orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName == null ? "" : orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCourId() {
		return courId == null ? "" : courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public String getCourName() {
		return courName == null ? "" : courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}

	public String getIsAnonymous() {
		return isAnonymous == null ? "0" : isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getRange() {
		return range == null ? "0" : range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
}
