package com.zhizun.pos.bean;

import java.util.List;

import com.zhizun.pos.base.BaseBean;

public class OrgIntroBean extends BaseBean {

	String orgId;

	String orgName;

	String shortName;

	String logoPath;

	String orgDesc;

	String addrInfo;
	
	String phone;

	// 课程列表
	List<CourseListItemList> orgCourseList;

	// 机构环境图片列表
	List<Photo> envPicList;

	// 资质荣誉图片列表
	List<Photo> awardPicList;

	// 师资介绍图片列表
	List<TeacherInfo> orgTeacherList;

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getOrgDesc() {
		return orgDesc == null ? "" : orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public String getAddrInfo() {
		return addrInfo == null ? "" : addrInfo;
	}

	public void setAddrInfo(String addrInfo) {
		this.addrInfo = addrInfo;
	}

	public List<CourseListItemList> getOrgCourseList() {
		return orgCourseList;
	}

	public void setOrgCourseList(List<CourseListItemList> orgCourseList) {
		this.orgCourseList = orgCourseList;
	}

	public List<Photo> getEnvPicList() {
		return envPicList;
	}

	public void setEnvPicList(List<Photo> envPicList) {
		this.envPicList = envPicList;
	}

	public List<Photo> getAwardPicList() {
		return awardPicList;
	}

	public void setAwardPicList(List<Photo> awardPicList) {
		this.awardPicList = awardPicList;
	}

	public List<TeacherInfo> getOrgTeacherList() {
		return orgTeacherList;
	}

	public void setOrgTeacherList(List<TeacherInfo> orgTeacherList) {
		this.orgTeacherList = orgTeacherList;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
