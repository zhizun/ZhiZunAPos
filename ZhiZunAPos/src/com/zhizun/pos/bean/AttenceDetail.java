package com.zhizun.pos.bean;

import java.util.ArrayList;

import com.zhizun.pos.base.BaseBean;

/**
 * 家庭作业实体 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class AttenceDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 考勤状态 0未到 1已到
	private String userName;// 考勤人姓名
	private String userId;// 考勤人ID
	private String lastUpdateTime;// 最后更新时间
	private String expendedChourNum;// 消耗课时数
	private String attenceId;// 考勤ID
	private String stuId;// 学生ID
	private String detailId;// 内容ID
	private String stuName;//学生姓名
	private String attenceTime;//考勤时间
	private ArrayList<Remarks> remarks=new ArrayList<Remarks>();
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getExpendedChourNum() {
		return expendedChourNum;
	}
	public void setExpendedChourNum(String expendedChourNum) {
		this.expendedChourNum = expendedChourNum;
	}
	public String getAttenceId() {
		return attenceId;
	}
	public void setAttenceId(String attenceId) {
		this.attenceId = attenceId;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getAttenceTime() {
		return attenceTime;
	}
	public void setAttenceTime(String attenceTime) {
		this.attenceTime = attenceTime;
	}
	public ArrayList<Remarks> getRemarks() {
		return remarks;
	}
	public void setRemarks(ArrayList<Remarks> remarks) {
		this.remarks = remarks;
	}
	


}
