package com.zhizun.pos.bean;




import com.zhizun.pos.base.BaseBean;

/**
 * 学生实体
 * 创建人：李林中
 * 创建日期：2014-12-4  上午11:56:10
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public class StudentInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = StudentInfo.class.getName();
    private String name;// 姓名
	private String sex;//	性别
	private String photoPath;//	图片路径
	private String birthDate;//生日
	private String stuId;//学生ID
	private String age;
	private String phone;
	private Boolean checkState;//是否选中
	private Boolean isFamilyJoin;	//家庭成员是否加入机构
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
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public Boolean getCheckState() {
		return checkState;
	}
	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getIsFamilyJoin() {
		return isFamilyJoin;
	}
	public void setIsFamilyJoin(Boolean isFamilyJoin) {
		this.isFamilyJoin = isFamilyJoin;
	}
	

}
