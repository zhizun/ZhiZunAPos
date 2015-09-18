package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class PhoneContactBean extends BaseBean {

	/**
	 * 手机联系人实体类
	 */
	private static final long serialVersionUID = 1L;
	
	private int contactId; //id 
	private String desplayName;//姓名  
    private String phoneNum; // 电话号码
	private String sortLetters;  //显示数据拼音的首字母
	private boolean checkTag;//是否选中
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getDesplayName() {
		return desplayName;
	}
	public void setDesplayName(String desplayName) {
		this.desplayName = desplayName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public boolean isCheckTag() {
		return checkTag;
	}
	public void setCheckTag(boolean checkTag) {
		this.checkTag = checkTag;
	}
    
	
    

}
