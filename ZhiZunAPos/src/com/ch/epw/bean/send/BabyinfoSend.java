package com.ch.epw.bean.send;

import com.zhizun.pos.base.BaseBean;

/**
 * 发送宝宝资料实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class BabyinfoSend extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String userId;
	private String photoPath;// 头像
	private String nickName;// 乳名
	private String name;// 姓名
	private String sex;// 性别
	private String birthDate;// 出生日期
	private String interestPath;// 兴趣爱好
	private String childId;

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getInterestPath() {
		return interestPath;
	}

	public void setInterestPath(String interestPath) {
		this.interestPath = interestPath;
	}

	public BabyinfoSend(String userId, String photoPath, String nickName,
			String name, String sex, String birthDate, String interestPath,
			String childId) {
		super();
		this.userId = userId;
		this.photoPath = photoPath;
		this.nickName = nickName;
		this.name = name;
		this.sex = sex;
		this.birthDate = birthDate;
		this.interestPath = interestPath;
		this.childId = childId;
	}

	

}
