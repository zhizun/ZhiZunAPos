package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ch.epw.utils.URLs;
import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 图片实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class PictureList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = PictureList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private List<Picture> pictureList = new ArrayList<Picture>();

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static PictureList parse(String resultInfo) throws IOException,
			AppException {
		Picture picture = null;
		PictureList pictureList = new PictureList();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			pictureList.setStatus(jsonObject.getString("status"));
			pictureList.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);
				picture = new Picture();
				picture.setPicPath(jsonObjectPic.getString("picPath"));
				picture.setThumbPath(jsonObjectPic.getString("thumbPath"));
				pictureList.getPictureList().add(picture);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return pictureList;
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

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

}
