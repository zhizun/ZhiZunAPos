package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

public class Remarks extends BaseBean{
	private String typt;//考勤类型0，迟到  1，请假   2.其他
	private String note;//内容
	private String recordtime;//时间
	public String getTypt() {
		return typt;
	}
	public void setTypt(String typt) {
		this.typt = typt;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}
	
	

}
