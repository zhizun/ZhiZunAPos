package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

public class Favor {
	/**
	 * 发送人ID
	 */
	String userId;
	/**
	 * 发送人姓名
	 */
	String userName;
	/**
	 * 发送人头像
	 */
	String photopath;
	/**
	 * 引用动态ID
	 */
	String refId;
	/**
	 * 动态类型
	 */
	String type;
	/**
	 * 内容
	 */
	String content;
	/**
	 * 收藏时间
	 */
	String favTime;
	
	String orgId;
	
	String orgName;
	
	List photoList = new ArrayList<Photo>();
	
	List ratingList = new ArrayList<Rating>();

	public String getUserId() {
		return userId;
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
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
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
	public String getFavTime() {
		return favTime;
	}
	public void setFavTime(String favTime) {
		this.favTime = favTime;
	}
	public List getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List photoList) {
		this.photoList = photoList;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List getRatingList() {
		return ratingList;
	}
	public void setRatingList(List ratingList) {
		this.ratingList = ratingList;
	}

}
