package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 图片实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Photo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = Photo.class.getName();
	private String photoId;// 图片ID
	private String thumbPath;// 缩略图路径
	private String path;// 、大图路径
	private String thumbSaveName;// 缩略图保存名字
	private String saveName;// 大图保存名字

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getThumbSaveName() {
		return thumbSaveName;
	}

	public void setThumbSaveName(String thumbSaveName) {
		this.thumbSaveName = thumbSaveName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
