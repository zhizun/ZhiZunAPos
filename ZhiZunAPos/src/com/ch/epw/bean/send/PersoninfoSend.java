package com.ch.epw.bean.send;

import com.zhizun.pos.base.BaseBean;

/**
 * 发送个人资料实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class PersoninfoSend extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String photoPath;// 头像
	private String userName;// 会员名
	private String name;// 姓名
	private String sex;// 性别
	private String addr;// 详细地址
	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String lat;// 纬度
	private String lng;// 经度

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public PersoninfoSend(String photoPath, String userName, String name,
			String sex, String addr, String province, String city,
			String county, String lat, String lng) {
		super();
		this.photoPath = photoPath;
		this.userName = userName;
		this.name = name;
		this.sex = sex;
		this.addr = addr;
		this.province = province;
		this.city = city;
		this.county = county;
		this.lat = lat;
		this.lng = lng;
	}

}
