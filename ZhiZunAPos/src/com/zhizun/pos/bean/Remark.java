package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

/**
 * 在校点评实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Remark extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String content;// 内容
	private String userName;// 发送人
	private String userId;// 发送人ID
	private String lastUpdateTime;// 最后更新时间
	private String orgId;// 机构ID
	private String orgName;// 机构名称
	private String remarkId;// 点评ID
	private String sendTime;// 发送时间
	private String userPhoto;// 用户头像
	private Boolean currenUserFav;// 是否收藏
	private Integer receiverCount;// 接收者 数量
	private String receivePerson;// 接收者
	private Boolean currenUserLike;//是否赞过
	private StudentClass studentClass = new StudentClass();// 接收班级和学生
	private ArrayList<StudentClass> studentClassesList = new ArrayList<StudentClass>();	// 接收班级和学生
	private List<Rating> ratingList = new ArrayList<Rating>();// 评分
	private List<Photo> photoList = new ArrayList<Photo>();// 图片列表
	private Integer commentCount;// 评论数量
	private List<Comments> commentsList = new ArrayList<Comments>();// 评论列表
	private List<Like> likeList=new ArrayList<Like>();//赞列表

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

	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Boolean getCurrenUserFav() {
		return currenUserFav;
	}

	public void setCurrenUserFav(Boolean currenUserFav) {
		this.currenUserFav = currenUserFav;
	}

	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}

	public List<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public Integer getCommentCount() {
		return commentsList == null ? 0 : commentsList.size();
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

	public Integer getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public Boolean getCurrenUserLike() {
		return currenUserLike;
	}

	public void setCurrenUserLike(Boolean currenUserLike) {
		this.currenUserLike = currenUserLike;
	}

	public List<Like> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<Like> likeList) {
		this.likeList = likeList;
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
