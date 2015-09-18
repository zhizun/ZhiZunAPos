package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 最新回复实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NewComments extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NewComments.class.getName();

	private String newCommentContent;// 回复内容
	private String newCommentRefId;// 回复的动态ID
	private String newCommentUserAppe;// 评论人 名字和称谓
	private String newCommentID;// 评论ID
	private String newCommentUserPhoto;//
	private String newCommentTime;// 评论时间
	private String dynamicUserPhoto;// 动态默认的图片

	private DynamicTeacher dynamicTeacher=new DynamicTeacher();//动态实体


	public String getNewCommentContent() {
		return newCommentContent;
	}

	public void setNewCommentContent(String newCommentContent) {
		this.newCommentContent = newCommentContent;
	}

	public String getNewCommentRefId() {
		return newCommentRefId;
	}

	public void setNewCommentRefId(String newCommentRefId) {
		this.newCommentRefId = newCommentRefId;
	}

	public String getNewCommentUserAppe() {
		return newCommentUserAppe;
	}

	public void setNewCommentUserAppe(String newCommentUserAppe) {
		this.newCommentUserAppe = newCommentUserAppe;
	}

	public String getNewCommentID() {
		return newCommentID;
	}

	public void setNewCommentID(String newCommentID) {
		this.newCommentID = newCommentID;
	}

	public String getNewCommentUserPhoto() {
		return newCommentUserPhoto;
	}

	public void setNewCommentUserPhoto(String newCommentUserPhoto) {
		this.newCommentUserPhoto = newCommentUserPhoto;
	}

	public String getNewCommentTime() {
		return newCommentTime;
	}

	public void setNewCommentTime(String newCommentTime) {
		this.newCommentTime = newCommentTime;
	}

	public DynamicTeacher getDynamicTeacher() {
		return dynamicTeacher;
	}

	public void setDynamicTeacher(DynamicTeacher dynamicTeacher) {
		this.dynamicTeacher = dynamicTeacher;
	}

	public String getDynamicUserPhoto() {
		return dynamicUserPhoto;
	}

	public void setDynamicUserPhoto(String dynamicUserPhoto) {
		this.dynamicUserPhoto = dynamicUserPhoto;
	}


}
