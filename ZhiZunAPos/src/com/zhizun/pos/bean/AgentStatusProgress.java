package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 我的代理信息 实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class AgentStatusProgress extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = AgentStatusProgress.class.getName();

	private String endTime;// 截止日期
	private String delegationId;// 代理标识
	private String clientUserId;// 被代理人UserId
	private String clientTeaId;// 被代理人老师标识
	private String clientTeaName;// 被代理老师名称
	private String agentUserId;// 代理人UsrId
	private String agentTeaId;// 代理人老师标识
	private String agentTeaName;// 代理老师名称
	private String status;// 代理状态
	private String invalidTime;// 失效时间
	List<Org> omClassList = new ArrayList<Org>();// 代理班级信息

	// studentId@studentName@classId@className;
	public String getOmClass() {
		StringBuffer sBuffer = new StringBuffer();
		for (Org org : omClassList) {

			sBuffer.append(org.getClaName());
			sBuffer.append(" ");

		}
		String omClass = sBuffer.toString();
		return omClass;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(String delegationId) {
		this.delegationId = delegationId;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getClientTeaId() {
		return clientTeaId;
	}

	public void setClientTeaId(String clientTeaId) {
		this.clientTeaId = clientTeaId;
	}

	public String getClientTeaName() {
		return clientTeaName;
	}

	public void setClientTeaName(String clientTeaName) {
		this.clientTeaName = clientTeaName;
	}

	public String getAgentUserId() {
		return agentUserId;
	}

	public void setAgentUserId(String agentUserId) {
		this.agentUserId = agentUserId;
	}

	public String getAgentTeaId() {
		return agentTeaId;
	}

	public void setAgentTeaId(String agentTeaId) {
		this.agentTeaId = agentTeaId;
	}

	public String getAgentTeaName() {
		return agentTeaName;
	}

	public void setAgentTeaName(String agentTeaName) {
		this.agentTeaName = agentTeaName;
	}

	public List<Org> getOmClassList() {
		return omClassList;
	}

	public void setOmClassList(List<Org> omClassList) {
		this.omClassList = omClassList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

}
