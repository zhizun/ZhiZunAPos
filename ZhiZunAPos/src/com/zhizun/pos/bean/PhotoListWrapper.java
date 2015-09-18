package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class PhotoListWrapper extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = PhotoListWrapper.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因

	public static List parse(String resultInfo) throws IOException,
			AppException {

		return null;
	}

	public static List parse(JSONArray jsonArray) throws AppException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}

		List<Photo> photoList = new ArrayList<Photo>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);
				Photo photo = new Photo();
				photo.setPath(jsonObjectPic.getString("path"));
				photo.setPhotoId(jsonObjectPic.getString("photoId"));
				photo.setSaveName(jsonObjectPic.getString("saveName"));
				photo.setThumbPath(jsonObjectPic.getString("thumbPath"));
				photo.setThumbSaveName(jsonObjectPic.getString("thumbSaveName"));
				photoList.add(photo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return photoList;
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
