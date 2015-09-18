package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

/**
 * 在校动态实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Homework extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String homeworkId;// 作业ID
	private String sendTime;// 发送时间
	private String taskTime;// 定时发送时间，发送模式为立即发送时，为空
	private String isSendMsg;// 是否发送短信，0：否，1：是
	private String sendMode;// 发送模式，0：立即发送，1：定时发送
	private String lastUpdateTime;// 最后更新时间
	private Integer receiverCount;// 接收者 数量
	private String userId;// 发送人ID
	private String userName;// 发送人姓名
	private String orgId;// 机构ID
	private String orgName;// 机构名称
	private String status;// 家庭作业状态，0：待发送，1：已发送
	private String content;// 作业内容
	private String userPhoto;// 作业内容
	private StudentClass studentClass=new StudentClass();
	private ArrayList<StudentClass> studentClassesList = new ArrayList<StudentClass>();	// 接收班级和学生
	private Integer commentCount;// 评论数量
	private Boolean currenUserFav;//是否收藏
	private Boolean currenUserLike;//是否赞过
	private List<Like> likeList=new ArrayList<Like>();//赞列表
	
	private List<Comments> commentsList = new ArrayList<Comments>();// 评论列表

	private Comments referComment;	//当前引用的评论
	private String  typeingComment;	//正在输入的评论内容
	private boolean  isFoucsOn = false;		//是否正在评论

	public void resetInputStatus(){
		setReferComment(null);
		setTypeingComment("");
		setFoucsOn(false);
	}
	
	public String getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(String isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}



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


	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}

	public List<Comments> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comments> commentsList) {
		this.commentsList = commentsList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getReceiverCount() {
		return receiverCount;
	}

	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}

	public Integer getCommentCount() {
		return commentsList==null?0:commentsList.size();
		//return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Boolean getCurrenUserFav() {
		return currenUserFav;
	}

	public void setCurrenUserFav(Boolean currenUserFav) {
		this.currenUserFav = currenUserFav;
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

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
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
