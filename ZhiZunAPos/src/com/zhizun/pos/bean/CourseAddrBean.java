package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class CourseAddrBean extends BaseBean {

	/**
	 * 课程搜索列表，机构信息
	 */
	private static final long serialVersionUID = 1L;
	
	private String countyName;//所在区
	private String county;
	private String province;
	private String address;//机构地址信息
	private String city;
	private String country;
	private String street;
	private String cityName;//所在城市
	private String provinceName;//所在省
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	

}
