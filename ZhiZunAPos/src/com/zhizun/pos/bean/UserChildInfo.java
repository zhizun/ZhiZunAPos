package com.zhizun.pos.bean;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 宝宝实体
 * 创建人：李林中
 * 创建日期：2014-12-4  上午11:56:10
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public class UserChildInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.UserChildInfo";
	
	
	
    private String name;// 宝宝姓名
	private String sex;//	性别
	private String photoPath;//	图片路径
	private String birthDate;//生日
	private String childId;//孩子ID
	private String age;
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
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	

}
