package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 在校动态实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Voice extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String content;// 内容
	private String orgName;// 机构名称
	private String userName;// 发送人
	private String userId;// 发送人ID
	private String userAppe;//发送人称谓
	private String userDesc;//发送人描述，目前描述为宝宝班级信息
	private String lastUpdateTime;// 动态ID
	private String sendTime;// 发送时间
	private String orgId;// 机构ID
	private Boolean currenUserLike;// 是否赞过
	private Boolean currenUserFav;// 是否收藏
	private String photopath;// 发送人的头像 教师端用
	private String voice_id;// 动态ID
	private String teaName;// 发送给某机构 某老师 教师端用
	
	private List<Photo> photoList = new ArrayList<Photo>();// 图片列表
	private Integer commentCount;// 评论数量
	private List<Comments> commentsList = new ArrayList<Comments>();// 评论列表
	private List<Like> likeList = new ArrayList<Like>();// 赞列表

	private Comments referComment;	//当前引用的评论
	private String  typeingComment;	//正在输入的评论内容
	private boolean  isFoucsOn = false;		//是否正在评论

	public void resetInputStatus(){
		setReferComment(null);
		setTypeingComment("");
		setFoucsOn(false);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Boolean getCurrenUserLike() {
		return currenUserLike;
	}

	public void setCurrenUserLike(Boolean currenUserLike) {
		this.currenUserLike = currenUserLike;
	}

	public Boolean getCurrenUserFav() {
		return currenUserFav;
	}

	public void setCurrenUserFav(Boolean currenUserFav) {
		this.currenUserFav = currenUserFav;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getVoice_id() {
		return voice_id;
	}

	public void setVoice_id(String voice_id) {
		this.voice_id = voice_id;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public Integer getCommentCount() {
		return commentsList==null? 0 : commentsList.size();
		//return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public List<Comments> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comments> commentsList) {
		this.commentsList = commentsList;
	}

	public List<Like> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<Like> likeList) {
		this.likeList = likeList;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getUserAppe() {
		return userAppe;
	}

	public void setUserAppe(String userAppe) {
		this.userAppe = userAppe;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Comments getReferComment() {
		return referComment;
	}

	public void setReferComment(Comments referComment) {
		this.referComment = referComment;
	}

	public String getTypeingComment() {
		return typeingComment;
	}

	public void setTypeingComment(String typeingComment) {
		this.typeingComment = typeingComment;
	}

	public boolean isFoucsOn() {
		return isFoucsOn;
	}

	public void setFoucsOn(boolean isFoucsOn) {
		this.isFoucsOn = isFoucsOn;
	}

}
