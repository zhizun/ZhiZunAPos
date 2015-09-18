package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class CourseTeacherListBean extends BaseBean {

	/**
	 * 课程详情，老师列表
	 */
	private static final long serialVersionUID = 1L;
	private String photoPath;//照片地址
	private String introduction;//老师介绍
	private String name;//老师名字
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
