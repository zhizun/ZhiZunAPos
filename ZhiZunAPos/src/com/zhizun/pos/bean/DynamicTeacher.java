package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

/**
 * 在校动态实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class DynamicTeacher extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String dynamicID;// 动态ID
	private String sendTime;// 发送时间
	private String sendUserName;// 发送人
	private String sendUserId;// 发送人ID
	private String receivePerson;// 接收者
	private Integer receiverCount;// 接收者 数量

	private String dynamicContent;// 内容
	private String userPhoto;// 发送人的头像 教师端用
	private String orgId;// 机构ID
	private String orgName;// 机构名称
	private Boolean currenUserFav;// 是否收藏
	private Boolean currenUserLike;// 是否赞过

	private StudentClass studentClass = new StudentClass();
	private ArrayList<StudentClass> studentClassesList = new ArrayList<StudentClass>();	// 接收班级和学生
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

	public String getDynamicID() {
		return dynamicID;
	}

	public void setDynamicID(String dynamicID) {
		this.dynamicID = dynamicID;
	}

	public String getSendTime() {
		return sendTime == null?"":sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendUserName() {
		return sendUserName == null?"":sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getDynamicContent() {
		return dynamicContent == null ? "" : dynamicContent;
	}

	public void setDynamicContent(String dynamicContent) {
		this.dynamicContent = dynamicContent;
	}

	public Integer getCommentCount() {
		return commentsList == null ? 0 : commentsList.size();
		// return commentCount;
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

	public Integer getReceiverCount() {
		return receiverCount == null ? 0 : receiverCount;
	}

	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
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

	public Boolean getCurrenUserFav() {
		return currenUserFav;
	}

	public void setCurrenUserFav(Boolean currenUserFav) {
		this.currenUserFav = currenUserFav;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public List<Like> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<Like> likeList) {
		this.likeList = likeList;
	}

	public Boolean getCurrenUserLike() {
		return currenUserLike;
	}

	public void setCurrenUserLike(Boolean currenUserLike) {
		this.currenUserLike = currenUserLike;
	}

	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
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

	public ArrayList<StudentClass> getStudentClassesList() {
		return studentClassesList;
	}

	public void setStudentClassesList(ArrayList<StudentClass> studentClassesList) {
		this.studentClassesList = studentClassesList;
	}

}
