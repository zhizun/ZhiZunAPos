package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 代理历史 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class AgentHistoryList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = AgentHistoryList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<AgentStatusProgress> agentStatusProgressList = new ArrayList<AgentStatusProgress>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static AgentHistoryList parse(String _post) {
		AgentStatusProgress agentStatusProgress = null;

		AgentHistoryList agentHistoryList = new AgentHistoryList();

		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			agentHistoryList.setStatus(jsonObject.getString("status"));
			agentHistoryList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			agentHistoryList.setTimestamp(jsonObject.getLong("timestamp"));
			tempJsonData = jsonObject.getJSONObject("data");
			if (tempJsonData == null || tempJsonData.length() == 0) {
				agentHistoryList.setDataCount("0");
				return agentHistoryList;
			}

			agentHistoryList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");

			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					agentStatusProgress = new AgentStatusProgress();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					agentStatusProgress.setAgentTeaId(tempJsonDatas
							.getString("agentTeaId"));
					agentStatusProgress.setAgentTeaName(tempJsonDatas
							.getString("agentTeaName"));
					agentStatusProgress.setAgentUserId(tempJsonDatas
							.getString("agentUserId"));
					agentStatusProgress.setClientTeaId(tempJsonDatas
							.getString("clientTeaId"));
					agentStatusProgress.setClientTeaName(tempJsonDatas
							.getString("clientTeaName"));
					agentStatusProgress.setClientUserId(tempJsonDatas
							.getString("clientUserId"));
					agentStatusProgress.setDelegationId(tempJsonDatas
							.getString("delegationId"));

					agentStatusProgress.setEndTime(tempJsonDatas
							.getString("endTime"));
					agentStatusProgress.setInvalidTime(tempJsonDatas
							.getString("invalidTime"));
					agentStatusProgress.setStatus(tempJsonDatas
							.getString("status"));
					agentHistoryList.getAgentStatusProgressList().add(
							agentStatusProgress);
				}
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return agentHistoryList;
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

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

	public List<AgentStatusProgress> getAgentStatusProgressList() {
		return agentStatusProgressList;
	}

	public void setAgentStatusProgressList(
			List<AgentStatusProgress> agentStatusProgressList) {
		this.agentStatusProgressList = agentStatusProgressList;
	}

}
