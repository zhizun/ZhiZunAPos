package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 我的消息
 * 		系统消息详情页--列表
 */

public class MySystemMessageDeatilList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MySystemMessageDeatilList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量
	private String title;//标题
	private String content;//消息内容
	private String sendTime;//发送时间
	private String isReplyAllowed;//是否允许回复

	List<MySelfMessageBean> mySelfMessageBean = new ArrayList<MySelfMessageBean>();

	/**
	 * 解析JSON得到系统消息列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static MySystemMessageDeatilList parse(String _post) {
		MySelfMessageBean mySelfMessageBean = null;
		MySystemMessageDeatilList myMessageList = new MySystemMessageDeatilList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			myMessageList.setStatus(jsonObject.getString("status"));
			myMessageList.setTimestamp(jsonObject.getLong("timestamp"));
			myMessageList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			myMessageList.setDataCount(tempJsonData.getString("count"));
//			myMessageList.setTitle(tempJsonData.getString("title"));
//			myMessageList.setContent(tempJsonData.getString("content"));
//			myMessageList.setSendTime(tempJsonData.getString("sendTime"));
//			myMessageList.setIsReplyAllowed(tempJsonData.getString("isReplyAllowed"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			Log.i(TAG,"mySystemMessageBean.length()="+ tempJsonDatasArray.length());
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					mySelfMessageBean = new MySelfMessageBean();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					mySelfMessageBean.setSysReplyContent(tempJsonDatas
							.getString("sysReplyContent"));
					mySelfMessageBean.setSendTime(tempJsonDatas
							.getString("sendTime"));
					mySelfMessageBean.setSysReplyTime(tempJsonDatas
							.getString("sysReplyTime"));
					mySelfMessageBean.setReplyId(tempJsonDatas
							.getString("replyId"));
					mySelfMessageBean.setContent(tempJsonDatas
							.getString("content"));
					myMessageList.getMySelfMessageBean().add(
							mySelfMessageBean);
				}
			}

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

	public List<MySelfMessageBean> getMySelfMessageBean() {
		return mySelfMessageBean;
	}

	public void setMySelfMessageBean(List<MySelfMessageBean> mySelfMessageBean) {
		this.mySelfMessageBean = mySelfMessageBean;
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

}
