package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class CourseRegions extends BaseBean {

	/**
	 * 选择区域列表实体类
	 */
	private static final long serialVersionUID = 1L;
	private String count;//
	private String value;//区名
	private String key;//
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
