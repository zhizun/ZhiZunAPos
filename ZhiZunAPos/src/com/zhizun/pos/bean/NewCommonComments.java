package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

/**
 * 通用最新回复实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NewCommonComments extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NewCommonComments.class.getName();
	
	private String content;// 回复内容
	private String refId;// 回复的主题ID
	private String userId;//评论人ID
	private String userAppe;//评论人名字
	private String commentId;// 评论ID
	private String type;//
	private String commentTime;// 评论时间
	private String userPhoto;// 动态默认的图片
	private String refContent;// 动态默认的图片
	private List<Photo> photoList = new ArrayList<Photo>();// 图片列表
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public String getRefContent() {
		return refContent;
	}
	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}
	public List<Photo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}
	public String getUserAppe() {
		return userAppe;
	}
	public void setUserAppe(String userAppe) {
		this.userAppe = userAppe;
	}



}
