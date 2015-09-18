package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 推送通知 实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class PushMsg extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = PushMsg.class.getName();

	private String refId;
	private String orgId;
	private String role;
	private String type;
	private String commentType;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static PushMsg parse(String resultInfo) throws IOException,
			AppException {
		PushMsg pushMsg = null;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			pushMsg = new PushMsg();
			pushMsg.setRefId(jsonObject.getString("refId"));
			pushMsg.setType(jsonObject.getString("type"));
			pushMsg.setOrgId(jsonObject.getString("orgId"));
			pushMsg.setRole(jsonObject.getString("role"));
			pushMsg.setCommentType(jsonObject.optString("commentType"));
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return pushMsg;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

}
