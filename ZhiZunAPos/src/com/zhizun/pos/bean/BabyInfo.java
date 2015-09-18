package com.zhizun.pos.bean;




import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 宝宝资料实体
 * 创建人：李林中
 * 创建日期：2014-12-4  上午11:56:10
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public class BabyInfo extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = BabyInfo.class.getName();
	
	private String name;// 缩略图
	private String childId;//	、大图
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}

	

	
}
