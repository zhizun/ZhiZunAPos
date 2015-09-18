package com.zhizun.pos.bean;




import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 图片实体
 * 创建人：李林中
 * 创建日期：2014-12-4  上午11:56:10
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public class Picture extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = Picture.class.getName();
	
	private String thumbPath;// 缩略图
	private String picPath;//	、大图

	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String toString(){
		return thumbPath+"@"+picPath;
	}

	
}
