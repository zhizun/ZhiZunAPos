package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 学生所属班级实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class StudentClassList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.ClassAndStudent";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	ArrayList<StudentClass> studentClassesList = new ArrayList<StudentClass>();

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static StudentClassList parse(String _post) {
		StudentClassList classAndStudent = new StudentClassList();

		try {
			JSONObject jsonObject = new JSONObject(_post);
			JSONArray jsonArrayClassArray = jsonObject.getJSONArray("data");
			classAndStudent.setStatus(jsonObject.getString("status"));
			classAndStudent.setStatusMessage(jsonObject
					.getString("statusMessage"));
			classAndStudent.setStudentClassesList(parseJsonData(jsonArrayClassArray));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return classAndStudent;
	}

	public static ArrayList<StudentClass> parseJsonData(JSONArray jsonArrayClassArray) throws JSONException {
		StudentClass studentClass;
		StudentInfo studentInfo;
		JSONObject tempJsonData;
		JSONObject tempJsonClass;
		JSONObject tempJsonStudent;
		ArrayList<StudentClass> classAndStudent = new ArrayList<StudentClass>();
		for (int i = 0; i < jsonArrayClassArray.length(); i++) {
			tempJsonData = jsonArrayClassArray.optJSONObject(i);
			tempJsonClass = tempJsonData.getJSONObject("myClass");
			studentClass = new StudentClass();
			studentClass.setClaId(tempJsonClass.getString("claId"));
			studentClass.setName(tempJsonClass.getString("name"));
			studentClass.setCheckState(false);
			JSONArray jsonArrayStudentArray = tempJsonData
					.getJSONArray("studentList");
			for (int j = 0; j < jsonArrayStudentArray.length(); j++) {
				tempJsonStudent = jsonArrayStudentArray.optJSONObject(j);
				studentInfo = new StudentInfo();
				studentInfo.setAge(tempJsonStudent.getString("age"));
				studentInfo.setBirthDate(tempJsonStudent
						.getString("birthDate"));
				studentInfo.setName(tempJsonStudent.getString("name"));
				studentInfo.setPhotoPath(tempJsonStudent
						.getString("photoPath"));
				studentInfo.setSex(tempJsonStudent.getString("sex"));
				studentInfo.setStuId(tempJsonStudent.getString("stuId"));
				studentInfo.setPhone(tempJsonStudent.getString("parentPhone"));
				studentInfo.setIsFamilyJoin(
						   tempJsonStudent.getString("familiyJoin") != null
						&&!tempJsonStudent.getString("familiyJoin").equals("")
						&&!tempJsonStudent.getString("familiyJoin").equals("0")
					);
				studentInfo.setCheckState(false);
				studentClass.getStudentInfoList().add(studentInfo);
			}
			classAndStudent.add(studentClass);
		}
		return classAndStudent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public ArrayList<StudentClass> getStudentClassesList() {
		return studentClassesList;
	}

	public void setStudentClassesList(ArrayList<StudentClass> studentClassesList) {
		this.studentClassesList = studentClassesList;
	}

}
