package com.zhizun.pos.bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class FriendsHelpfulBase extends BaseBean {

	/**
	 * 朋友圈，感谢
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String statusMessage;

	private String id;
	private String userId;
	private String newFlag;
	private String userName;
	private String commentId;
	private String lastUpdateTime;
	private String remark;
	
	
	
	public static FriendsHelpfulBase parse(String resultInfo) throws IOException,
	AppException {
		FriendsHelpfulBase friendsHelpfulBase=new FriendsHelpfulBase();
		
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			friendsHelpfulBase.setStatus(jsonObject.getString("status"));
			friendsHelpfulBase.setStatusMessage(jsonObject.getString("statusMessage"));
			
			if (!jsonObject.isNull("data")) {
				JSONObject tempJsonData = jsonObject.getJSONObject("data");
				friendsHelpfulBase.setId(tempJsonData.getString("id"));
				friendsHelpfulBase.setCommentId(tempJsonData.getString("commentId"));
				friendsHelpfulBase.setUserId(tempJsonData.getString("userId"));
				friendsHelpfulBase.setNewFlag(tempJsonData.getString("newFlag"));
				friendsHelpfulBase.setUserName(tempJsonData.getString("userName"));
				friendsHelpfulBase.setLastUpdateTime(tempJsonData.getString("lastUpdateTime"));
				friendsHelpfulBase.setRemark(tempJsonData.getString("remark"));
				
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friendsHelpfulBase;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
