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
 * 		系统消息列表
 */

public class MySystemMessageList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MySystemMessageList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<MySystemMessageBean> mySystemMessageBean = new ArrayList<MySystemMessageBean>();

	/**
	 * @return
	 */
	public static MySystemMessageList parse(String _post) {
		MySystemMessageBean systemMessageBean = null;
		MySystemMessageList myMessageList = new MySystemMessageList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			myMessageList.setStatus(jsonObject.getString("status"));
			myMessageList.setTimestamp(jsonObject.getLong("timestamp"));
			myMessageList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
			tempJsonData = jsonObject.getJSONObject("data");
			myMessageList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			Log.i(TAG,"mySystemMessageBean.length()="+ tempJsonDatasArray.length());
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					systemMessageBean = new MySystemMessageBean();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					systemMessageBean.setTitle(tempJsonDatas
							.getString("title"));
					systemMessageBean.setSendTime(tempJsonDatas
							.getString("sendTime"));
					systemMessageBean.setRecieverId(tempJsonDatas
							.getString("recieverId"));
					systemMessageBean.setIsRead(tempJsonDatas
							.getString("isRead"));
					systemMessageBean.setContent(tempJsonDatas
							.getString("content"));
					myMessageList.getMySystemMessageBean().add(
							systemMessageBean);
					}
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

	public List<MySystemMessageBean> getMySystemMessageBean() {
		return mySystemMessageBean;
	}

	public void setMySystemMessageBean(List<MySystemMessageBean> mySystemMessageBean) {
		this.mySystemMessageBean = mySystemMessageBean;
	}

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

}
