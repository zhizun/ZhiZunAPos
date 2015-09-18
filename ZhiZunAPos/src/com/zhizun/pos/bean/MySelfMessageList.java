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
 * 		我的消息列表
 */

public class MySelfMessageList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MySelfMessageList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量
	private String pageCount;//总共多少页

	List<MySelfMessageBean> mySelfMessageBean = new ArrayList<MySelfMessageBean>();

	/**
	 * 解析JSON得到系统消息列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static MySelfMessageList parse(String _post) {
		MySelfMessageBean mySelfMessageBean = null;
		MySelfMessageList myMessageList = new MySelfMessageList();
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
			myMessageList.setPageCount(tempJsonData.getString("pageCount"));
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

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

}
