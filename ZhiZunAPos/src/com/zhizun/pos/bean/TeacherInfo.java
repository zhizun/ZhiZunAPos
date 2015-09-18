package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 教师实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class TeacherInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String name;// 姓名
	private String photoPath;// 图片路径
	private String teachId;// 教师ID
	private String ownOrgId;// 所属机构
	private Boolean checkState;// 是否选中
	private String post;// 称谓 0：校长；1：老师；2：园长；3：副园长；4：班主任。
	private String phone;
	private String userId;
	private String isJoinOrg;//是否加入机构
	private Boolean isOrgJoin;	//教师是否加入机构
	private String teachDesc;	//师资介绍

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getTeachId() {
		return teachId;
	}

	public void setTeachId(String teachId) {
		this.teachId = teachId;
	}

	public String getOwnOrgId() {
		return ownOrgId;
	}

	public void setOwnOrgId(String ownOrgId) {
		this.ownOrgId = ownOrgId;
	}

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsJoinOrg() {
		return isJoinOrg;
	}

	public void setIsJoinOrg(String isJoinOrg) {
		this.isJoinOrg = isJoinOrg;
	}

	public Boolean getIsOrgJoin() {
		return isOrgJoin;
	}

	public void setIsOrgJoin(Boolean isOrgJoin) {
		this.isOrgJoin = isOrgJoin;
	}

	public String getTeachDesc() {
		return teachDesc;
	}

	public void setTeachDesc(String teachDesc) {
		this.teachDesc = teachDesc;
	}

}
