package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 宝宝信息 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UserChildInfoList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacher";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	List<UserChildInfo> userChildInfoList = new ArrayList<UserChildInfo>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static UserChildInfoList parse(String _post) {

		UserChildInfo userChildInfo = null;
		UserChildInfoList userChildInfoList = new UserChildInfoList();
		JSONObject tempJsonData = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			userChildInfoList.setStatus(jsonObject.getString("status"));
			userChildInfoList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("childs");
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					userChildInfo = new UserChildInfo();
					tempJsonData = tempJsonDatasArray.optJSONObject(i);
					userChildInfo.setBirthDate(tempJsonData
							.getString("birthDate"));
					userChildInfo.setChildId(tempJsonData.getString("childId"));
					userChildInfo.setName(tempJsonData.getString("name"));
					userChildInfo.setPhotoPath(tempJsonData
							.getString("photoPath"));
					userChildInfo.setSex(tempJsonData.getString("sex"));
					userChildInfo.setAge(tempJsonData.getString("age"));

					userChildInfoList.getUserChildInfoList().add(userChildInfo);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userChildInfoList;
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

	public List<UserChildInfo> getUserChildInfoList() {
		return userChildInfoList;
	}

	public void setUserChildInfoList(List<UserChildInfo> userChildInfoList) {
		this.userChildInfoList = userChildInfoList;
	}

}
