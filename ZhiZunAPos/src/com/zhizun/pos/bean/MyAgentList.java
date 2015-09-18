package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zhizun.pos.base.BaseBean;

/**
 * 我的代理 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class MyAgentList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MyAgentList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因

	private List<AgentStatusProgress> agentStatusProgresseList = new ArrayList<AgentStatusProgress>();

	public String getStatus() {
		return status;
	}

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static MyAgentList parse(String _post) {
		AgentStatusProgress agentStatusProgres = null;
		Org omClass = null;
		List<Org> omClassList = null;
		MyAgentList agentList = new MyAgentList();
		JSONObject tempJsonData;
		JSONObject tempJsonStatusProgress;
		JSONObject tempJsonOmClass;

		JSONArray tempJsonDatasArray;
		JSONArray tempJsonOrgArray;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			agentList.setStatus(jsonObject.getString("status"));
			agentList.setStatusMessage(jsonObject.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			if (!tempJsonData.isNull("statusProgress")
					&& tempJsonData.has("statusProgress")) {
				tempJsonDatasArray = tempJsonData
						.getJSONArray("statusProgress");
				if (null != tempJsonDatasArray
						&& tempJsonDatasArray.length() > 0) {
					for (int i = 0; i < tempJsonDatasArray.length(); i++) {
						agentStatusProgres = new AgentStatusProgress();
						tempJsonStatusProgress = tempJsonDatasArray
								.optJSONObject(i);
						agentStatusProgres.setAgentTeaId(tempJsonStatusProgress
								.getString("agentTeaId"));
						agentStatusProgres
								.setAgentTeaName(tempJsonStatusProgress
										.getString("agentTeaName"));
						agentStatusProgres
								.setAgentUserId(tempJsonStatusProgress
										.getString("agentUserId"));
						agentStatusProgres
								.setClientTeaId(tempJsonStatusProgress
										.getString("clientTeaId"));
						agentStatusProgres
								.setClientTeaName(tempJsonStatusProgress
										.getString("clientTeaName"));
						agentStatusProgres
								.setClientUserId(tempJsonStatusProgress
										.getString("clientUserId"));
						agentStatusProgres
								.setDelegationId(tempJsonStatusProgress
										.getString("delegationId"));
						agentStatusProgres.setEndTime(tempJsonStatusProgress
								.getString("endTime"));
						agentStatusProgres.setInvalidTime(tempJsonStatusProgress
								.getString("invalidTime"));
						if (!tempJsonStatusProgress.isNull("omClassViewList")
								&& tempJsonStatusProgress
										.has("omClassViewList")) {
							tempJsonOrgArray = tempJsonStatusProgress
									.getJSONArray("omClassViewList");
							if (null != tempJsonOrgArray
									&& tempJsonOrgArray.length() > 0) {
								omClassList = new ArrayList<Org>();
								for (int j = 0; j < tempJsonOrgArray.length(); j++) {
									tempJsonOmClass = tempJsonOrgArray
											.optJSONObject(j);
									omClass = new Org();
									omClass.setClaId(tempJsonOmClass
											.getString("claId"));
									omClass.setClaName(tempJsonOmClass
											.getString("name"));
									omClassList.add(omClass);
								}
								agentStatusProgres.setOmClassList(omClassList);
							}
						}
						agentList.getAgentStatusProgresseList().add(
								agentStatusProgres);
					}

				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return agentList;
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

	public List<AgentStatusProgress> getAgentStatusProgresseList() {
		return agentStatusProgresseList;
	}

	public void setAgentStatusProgresseList(
			List<AgentStatusProgress> agentStatusProgresseList) {
		this.agentStatusProgresseList = agentStatusProgresseList;
	}

}
