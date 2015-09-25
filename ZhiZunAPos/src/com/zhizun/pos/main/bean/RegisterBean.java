package com.zhizun.pos.main.bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class RegisterBean extends BaseBean {

	/**
	 * 注册接口实体类
	 */
	private static final long serialVersionUID = 1L;
	

	private String status;//状态码
	private String mes;//状态信息，为0时，代表秘钥
	
	public static GetCodeBean parse(String resultInfo) throws IOException,
	AppException {
		
		GetCodeBean result = new GetCodeBean();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
		
			result.setStatus(jsonObject.getString("errorcode"));
			result.setMes(jsonObject.getString("errormsg"));
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		return result;
}
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	

}
