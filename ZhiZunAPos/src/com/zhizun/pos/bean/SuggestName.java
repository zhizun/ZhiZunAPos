package com.zhizun.pos.bean;


/**
 * 搜索建议
 * 
 */

public class SuggestName {

	private String orgId;

	private String orgName;

	private String courId;

	private String courName;
	
	private String orgDrr;
	
	private String logoPath;
	
	/**
	 * 搜索类型，表明结果是针对机构还是课程名称的搜索建议 取值同接口查询的searchType定义 0 : 搜索课程 1 : 搜索机构
	 * 
	 * note: 在对接口结果解析时候根据是否有courId区分搜索结果类型
	 */
	private String searchType;

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

	public String getCourId() {
		return courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public String getCourName() {
		return courName;
	}

	public void setCourName(String courName) {
		this.courName = courName;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getOrgDrr() {
		return orgDrr;
	}

	public void setOrgDrr(String orgDrr) {
		this.orgDrr = orgDrr;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	
}


