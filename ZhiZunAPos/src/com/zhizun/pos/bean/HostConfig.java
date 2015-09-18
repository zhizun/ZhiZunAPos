package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 赞实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class HostConfig extends BaseBean {
	private static final long serialVersionUID = 1L;
	
	public static String epeiwang_server;//
	public static String epeiwang_img_server;//
	public static String epeiwang_url;//
	public static String getEpeiwang_server() {
		return epeiwang_server;
	}
	public static void setEpeiwang_server(String epeiwang_server) {
		HostConfig.epeiwang_server = epeiwang_server;
	}
	public static String getEpeiwang_img_server() {
		return epeiwang_img_server;
	}
	public static void setEpeiwang_img_server(String epeiwang_img_server) {
		HostConfig.epeiwang_img_server = epeiwang_img_server;
	}
	public static String getEpeiwang_url() {
		return epeiwang_url;
	}
	public static void setEpeiwang_url(String epeiwang_url) {
		HostConfig.epeiwang_url = epeiwang_url;
	}




}
