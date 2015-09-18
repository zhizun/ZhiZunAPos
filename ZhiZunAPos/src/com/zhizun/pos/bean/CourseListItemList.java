package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

public class CourseListItemList extends BaseBean {

	/**
	 * 课程搜索列表，内容
	 */
	private static final long serialVersionUID = 1L;
	
	private String refundable;//是否支持随时退，0：不支持，1：支持，2：只能退还一部分；
	private String signNum;//报名数
	private int price;//价格
	private String name;//课程名称
	private String courId;//课程id
	private String desc;//课程描述
	private String thumbPath;//课程缩略图
	private String path;// 课程大图
	private int olSalesInfo;//优惠信息
	private String courCode;//课程编码
	private String distance;//距离
	private String commentNum;//课程评论数
	private List<CourseCatBean> courseCatBeanList = new ArrayList<CourseCatBean>();
	private CourseAddrBean courseAddrBean=new CourseAddrBean();
	private CourseOrgBean courseOrgBean=new CourseOrgBean();
	
	//机构
	private String phone;//机构联系方式
	private String website;//主页地址
	private String isAuthen;//是否认证，0：否，1：是
	private String viewNum;//浏览数
	private String contact;//机构联系人
	private String orgId;//机构ID
	private String category;//机构类别，0：幼儿园，1：机构
	private String memLevel;//会员级别
	private String orgDesc;//机构描述
	private String orgCode;
	private String shortName;//机构简称
	private String logoPath;//机构LOGO
	
	public String getRefundable() {
		return refundable;
	}
	public void setRefundable(String refundable) {
		this.refundable = refundable;
	}
	public String getSignNum() {
		return signNum;
	}
	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourId() {
		return courId;
	}
	public void setCourId(String courId) {
		this.courId = courId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getOlSalesInfo() {
		return olSalesInfo;
	}
	public void setOlSalesInfo(int olSalesInfo) {
		this.olSalesInfo = olSalesInfo;
	}
	public String getCourCode() {
		return courCode;
	}
	public void setCourCode(String courCode) {
		this.courCode = courCode;
	}
	
	public List<CourseCatBean> getCourseCatBeanList() {
		return courseCatBeanList;
	}
	public void setCourseCatBeanList(List<CourseCatBean> courseCatBeanList) {
		this.courseCatBeanList = courseCatBeanList;
	}
	public CourseAddrBean getCourseAddrBean() {
		return courseAddrBean;
	}
	public void setCourseAddrBean(CourseAddrBean courseAddrBean) {
		this.courseAddrBean = courseAddrBean;
	}
	public CourseOrgBean getCourseOrgBean() {
		return courseOrgBean;
	}
	public void setCourseOrgBean(CourseOrgBean courseOrgBean) {
		this.courseOrgBean = courseOrgBean;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	//机构
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getIsAuthen() {
		return isAuthen;
	}
	public void setIsAuthen(String isAuthen) {
		this.isAuthen = isAuthen;
	}
	public String getViewNum() {
		return viewNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMemLevel() {
		return memLevel;
	}
	public void setMemLevel(String memLevel) {
		this.memLevel = memLevel;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	
	
	

}
