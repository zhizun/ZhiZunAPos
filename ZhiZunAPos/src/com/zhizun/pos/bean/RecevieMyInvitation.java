package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 邀请实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class RecevieMyInvitation extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.RecevieMyInvitation";
	private String invitedUserPhone;// 被邀请人电话
	private String inviteOrgName;// 邀请机构名称
	private String inviteOrgShortName;// 邀请机构短名称
	private String inviteOrgAddress;	// 邀请机构地址
	private String invitedUserName;		// 被邀请人姓名
	private String invitedUserPhoto; 	//被邀请人头像
	private String inviteUserName;		// 邀请人名字
	private String inviteUserPhoto; 	// 邀请人头像
	private String inviteUserPhone;// 邀请人电话
	private String inviteStatus;// 邀请状态 0初始未处理 1已接受 2已拒绝
	private String inviteComment;// 邀请内容
	private String inviteTime;// 邀请时间
	private String type;// 类型 0邀请家长 1 邀请老师
	private String logoPath;// 图片logo地址
	private String orgInviteId;// 邀请ID
	private String orgId;//邀请机构ID

	// 学生信息



	private String stuId;// 学生id
	private String stuName;// 学生姓名
	private String stuSex;// 学生性别
	private String stuPhotoPath;// 图片路径
	private String stuBirthDate;// 生日
	private String stuPhone;// 电话
	private String stuOwnOrgId;// 所属机构ID
	private String stuAge;// 年龄
	private String stuOwnOrgName;// 所属机构名称
	private String stuParentPhone;// 父母电话
	// 班级信息
	List<StudentClass> lisStudentClasses = new ArrayList<StudentClass>();

	public String getInvitedUserPhone() {
		return invitedUserPhone;
	}

	public void setInvitedUserPhone(String invitedUserPhone) {
		this.invitedUserPhone = invitedUserPhone;
	}

	public String getInviteOrgName() {
		return inviteOrgName;
	}

	public void setInviteOrgName(String inviteOrgName) {
		this.inviteOrgName = inviteOrgName;
	}

	public String getInviteOrgShortName() {
		return inviteOrgShortName;
	}

	public void setInviteOrgShortName(String inviteOrgShortName) {
		this.inviteOrgShortName = inviteOrgShortName;
	}

	public String getInvitedUserName() {
		return invitedUserName;
	}

	public void setInvitedUserName(String invitedUserName) {
		this.invitedUserName = invitedUserName;
	}

	public String getInviteUserPhone() {
		return inviteUserPhone;
	}

	public void setInviteUserPhone(String inviteUserPhone) {
		this.inviteUserPhone = inviteUserPhone;
	}

	public String getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(String inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public String getInviteComment() {
		return inviteComment;
	}

	public void setInviteComment(String inviteComment) {
		this.inviteComment = inviteComment;
	}

	public String getInviteTime() {
		return inviteTime;
	}

	public void setInviteTime(String inviteTime) {
		this.inviteTime = inviteTime;
	}

	public String getInviteOrgAddress() {
		return inviteOrgAddress;
	}

	public void setInviteOrgAddress(String inviteOrgAddress) {
		this.inviteOrgAddress = inviteOrgAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getInviteUserName() {
		return inviteUserName;
	}

	public void setInviteUserName(String inviteUserName) {
		this.inviteUserName = inviteUserName;
	}

	public String getOrgInviteId() {
		return orgInviteId;
	}

	public void setOrgInviteId(String orgInviteId) {
		this.orgInviteId = orgInviteId;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

	public String getStuPhotoPath() {
		return stuPhotoPath;
	}

	public void setStuPhotoPath(String stuPhotoPath) {
		this.stuPhotoPath = stuPhotoPath;
	}

	public String getStuBirthDate() {
		return stuBirthDate;
	}

	public void setStuBirthDate(String stuBirthDate) {
		this.stuBirthDate = stuBirthDate;
	}

	public String getStuPhone() {
		return stuPhone;
	}

	public void setStuPhone(String stuPhone) {
		this.stuPhone = stuPhone;
	}

	public String getStuOwnOrgId() {
		return stuOwnOrgId;
	}

	public void setStuOwnOrgId(String stuOwnOrgId) {
		this.stuOwnOrgId = stuOwnOrgId;
	}

	public String getStuAge() {
		return stuAge;
	}

	public void setStuAge(String stuAge) {
		this.stuAge = stuAge;
	}

	public String getStuOwnOrgName() {
		return stuOwnOrgName;
	}

	public void setStuOwnOrgName(String stuOwnOrgName) {
		this.stuOwnOrgName = stuOwnOrgName;
	}

	public String getStuParentPhone() {
		return stuParentPhone;
	}

	public void setStuParentPhone(String stuParentPhone) {
		this.stuParentPhone = stuParentPhone;
	}

	public List<StudentClass> getLisStudentClasses() {
		return lisStudentClasses;
	}

	public void setLisStudentClasses(List<StudentClass> lisStudentClasses) {
		this.lisStudentClasses = lisStudentClasses;
	}
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getInvitedUserPhoto() {
		return invitedUserPhoto;
	}

	public void setInvitedUserPhoto(String invitedUserPhoto) {
		this.invitedUserPhoto = invitedUserPhoto;
	}

	public String getInviteUserPhoto() {
		return inviteUserPhoto;
	}

	public void setInviteUserPhoto(String inviteUserPhoto) {
		this.inviteUserPhoto = inviteUserPhoto;
	}
}
