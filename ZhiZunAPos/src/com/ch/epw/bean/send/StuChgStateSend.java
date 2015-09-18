package com.ch.epw.bean.send;

import com.zhizun.pos.base.BaseBean;

/**
 * 发送转班 休学 毕业 退班 实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class StuChgStateSend extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String chosClaId;// 选择班级标识，即该学员所在班级
	private String stuId;// 学员标识
	private String stuName;// 学员姓名
	private String cngType;// 变动类型，1：休学；2：转班：3：退班；4：复学 5：毕业
	private String outClaId;// 退出班级标识，当变动类型为转班时必须
	private String outChour;// 转出课时，当变动类型为转班生效
	private String intoClaId;// 转入班级标识，当变动类型为转班时必须
	private String note;// 备注，仅当变动类型为转班时生效
	private String exitClaId;// 退出班级标识，仅当变动类型为退班时必须
	private String susDate;// 休学日期，仅当变动类型为休学时生效，格式为yyyy-mm-dd hh:mi:ss
	private String estResuDate;// 预计复学日期，仅当变动类型为休学时生效，格式为yyyy-mm-dd hh:mi:ss

	public StuChgStateSend(String chosClaId, String stuId, String stuName,
			String cngType, String outClaId, String outChour, String intoClaId,
			String note, String exitClaId, String susDate, String estResuDate) {
		super();
		this.chosClaId = chosClaId;
		this.stuId = stuId;
		this.stuName = stuName;
		this.cngType = cngType;
		this.outClaId = outClaId;
		this.outChour = outChour;
		this.intoClaId = intoClaId;
		this.note = note;
		this.exitClaId = exitClaId;
		this.susDate = susDate;
		this.estResuDate = estResuDate;
	}

	public String getChosClaId() {
		return chosClaId;
	}

	public void setChosClaId(String chosClaId) {
		this.chosClaId = chosClaId;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getCngType() {
		return cngType;
	}

	public void setCngType(String cngType) {
		this.cngType = cngType;
	}

	public String getOutClaId() {
		return outClaId;
	}

	public void setOutClaId(String outClaId) {
		this.outClaId = outClaId;
	}

	public String getOutChour() {
		return outChour;
	}

	public void setOutChour(String outChour) {
		this.outChour = outChour;
	}

	public String getIntoClaId() {
		return intoClaId;
	}

	public void setIntoClaId(String intoClaId) {
		this.intoClaId = intoClaId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getExitClaId() {
		return exitClaId;
	}

	public void setExitClaId(String exitClaId) {
		this.exitClaId = exitClaId;
	}

	public String getSusDate() {
		return susDate;
	}

	public void setSusDate(String susDate) {
		this.susDate = susDate;
	}

	public String getEstResuDate() {
		return estResuDate;
	}

	public void setEstResuDate(String estResuDate) {
		this.estResuDate = estResuDate;
	}

}
