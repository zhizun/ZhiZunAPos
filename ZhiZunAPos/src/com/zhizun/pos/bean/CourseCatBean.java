package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class CourseCatBean extends BaseBean {

	/**
	 * 课程搜索列表    Cat 课程
	 */
	private static final long serialVersionUID = 1L;
	
	private String catPriId;
	private String catSecName;
	private String catPriName;
	private String catSecId;
	public String getCatPriId() {
		return catPriId;
	}
	public void setCatPriId(String catPriId) {
		this.catPriId = catPriId;
	}
	public String getCatSecName() {
		return catSecName;
	}
	public void setCatSecName(String catSecName) {
		this.catSecName = catSecName;
	}
	public String getCatPriName() {
		return catPriName;
	}
	public void setCatPriName(String catPriName) {
		this.catPriName = catPriName;
	}
	public String getCatSecId() {
		return catSecId;
	}
	public void setCatSecId(String catSecId) {
		this.catSecId = catSecId;
	}
	
	

}
