package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class TeacherInfoListWrapper extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = TeacherInfoListWrapper.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因

	public static List parse(String resultInfo) throws IOException,
			AppException {

		return null;
	}

	public static List parse(JSONArray jsonArray) throws IOException,
			AppException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}

		List<TeacherInfo> teacherList = new ArrayList<TeacherInfo>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);

				TeacherInfo teacherInfo = new TeacherInfo();
				String teachName = jsonObjectPic.optString("name");
				if(teachName == null || "".equals(teachName)){
					teachName = jsonObjectPic.optString("teachName");
				}
				teacherInfo.setName(teachName);
				
				String teachPhotoPath = jsonObjectPic.optString("photoPath");
				if(teachPhotoPath == null || "".equals(teachPhotoPath)){
					teachPhotoPath = jsonObjectPic.getString("teachPhotoPath");
				}
				teacherInfo.setPhotoPath(teachPhotoPath);
				
				teacherInfo.setOwnOrgId(jsonObjectPic.getString("ownOrgId"));
				teacherInfo.setTeachId(jsonObjectPic.getString("teachId"));

				String isJoinOrg = jsonObjectPic.optString("isJoinOrg");
				teacherInfo.setIsJoinOrg(isJoinOrg);
				teacherInfo.setIsOrgJoin("1".equals(isJoinOrg));

				teacherInfo.setPost(jsonObjectPic.optString("post"));
				teacherInfo.setPhone(jsonObjectPic.optString("phone"));
				teacherInfo.setCheckState(false);
				
				String teachDesc = jsonObjectPic.optString("teachDesc");
				if(teachDesc == null || "".equals(teachDesc)){
					teachDesc = jsonObjectPic.optString("introduction");
				}
				teacherInfo.setTeachDesc(teachDesc);

				teacherList.add(teacherInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return teacherList;
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

}
