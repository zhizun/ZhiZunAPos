package com.zhizun.pos.bean;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 我的消息
 * 		系统消息详情页--列表
 */

public class MySystemMessageDeatilContent extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MySystemMessageDeatilContent.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量
	private String title;//标题
	private String content;//消息内容
	private String sendTime;//发送时间
	private String isReplyAllowed;//是否允许回复
	private int replyNum;

	private String receiveId;
	private String messageId;
	
	/**
	 * 解析JSON得到系统消息列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static MySystemMessageDeatilContent parse(String _post) {
		MySystemMessageDeatilContent myMessageList = new MySystemMessageDeatilContent();
		JSONObject tempJsonData;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			myMessageList.setStatus(jsonObject.getString("status"));
			myMessageList.setTimestamp(jsonObject.getLong("timestamp"));
			myMessageList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			myMessageList.setTitle(tempJsonData.getString("title"));
			myMessageList.setContent(tempJsonData.getString("content"));
			myMessageList.setSendTime(tempJsonData.getString("sendTime"));
			myMessageList.setIsReplyAllowed(tempJsonData.getString("isReplyAllowed"));
			myMessageList.setReplyNum(tempJsonData.optInt("replyNum", 0));
			myMessageList.setReceiveId(tempJsonData.getString("receiveId"));
			myMessageList.setMessageId(tempJsonData.getString("msgId"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myMessageList;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getIsReplyAllowed() {
		return isReplyAllowed;
	}

	public void setIsReplyAllowed(String isReplyAllowed) {
		this.isReplyAllowed = isReplyAllowed;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	
}
