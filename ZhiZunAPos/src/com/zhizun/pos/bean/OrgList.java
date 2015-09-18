package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 当前登录用户下面的班级 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class OrgList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	ArrayList<Org> orgList = new ArrayList<Org>();

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static OrgList parse(String _post) {

		Org org = null;

		OrgList orgList = new OrgList();
		JSONObject tempJsonData = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			JSONArray jsonArrayDataArray = jsonObject.getJSONArray("data");
			orgList.setStatus(jsonObject.getString("status"));
			orgList.setStatusMessage(jsonObject.getString("statusMessage"));
			for (int i = 0; i < jsonArrayDataArray.length(); i++) {
				tempJsonData = jsonArrayDataArray.optJSONObject(i);
				org = new Org();
				org.setClaId(tempJsonData.getString("claId"));
				org.setClaName(tempJsonData.getString("name"));
				org.setCheckState(false);
				orgList.getOrgList().add(org);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return orgList;
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

	public ArrayList<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(ArrayList<Org> orgList) {
		this.orgList = orgList;
	}

}
