package com.zhizun.pos.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 评论实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Comments extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.Comments";

	private String dynamicID;// 动态ID
	private String commentID;// 评论ID
	private String userId;// 评论人ID
	private String userAppe;// 评论人 名字和称谓
	private String commentContent;// 评论内容
	private String replyCommentID;// 回复 评论ID
	private String replyUserAppe;// 回复人的名字和称谓
	private String sf_read;// 是否已读
	private String replyUserID;// 回复 用户ID
	private String commentTime;// 评论时间
	private String userPhoto;// 用户头像
	private String refId;// 引用ID

	public String getDynamicID() {
		return dynamicID;
	}

	public void setDynamicID(String dynamicID) {
		this.dynamicID = dynamicID;
	}

	public String getCommentID() {
		return commentID;
	}

	public void setCommentID(String commentID) {
		this.commentID = commentID;
	}

	public String getUserAppe() {
		return userAppe == null? "" : userAppe;
	}

	public void setUserAppe(String userAppe) {
		this.userAppe = userAppe;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getReplyCommentID() {
		return replyCommentID;
	}

	public void setReplyCommentID(String replyCommentID) {
		this.replyCommentID = replyCommentID;
	}

	public String getReplyUserAppe() {
		return replyUserAppe;
	}

	public void setReplyUserAppe(String replyUserAppe) {
		this.replyUserAppe = replyUserAppe;
	}

	public String getSf_read() {
		return sf_read;
	}

	public void setSf_read(String sf_read) {
		this.sf_read = sf_read;
	}

	public String getReplyUserID() {
		return replyUserID;
	}

	public void setReplyUserID(String replyUserID) {
		this.replyUserID = replyUserID;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
