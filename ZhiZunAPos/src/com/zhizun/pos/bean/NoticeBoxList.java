package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 通知收件箱 发件箱 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NoticeBoxList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NoticeBoxList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总数量

	ArrayList<NoticeBox> noticeBoxList = new ArrayList<NoticeBox>();

	/**
	 * 解析JSON得到 通知收发件箱列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NoticeBoxList parse(String _post) {
		NoticeBox noticeBox = null;
		NoticeBoxList noticeBoxList = new NoticeBoxList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			noticeBoxList.setStatus(jsonObject.getString("status"));
			noticeBoxList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			noticeBoxList.setTimestamp(jsonObject.getLong("timestamp"));
			if (!jsonObject.isNull("data")) {
				tempJsonData = jsonObject.getJSONObject("data");
				noticeBoxList.setDataCount(tempJsonData.getString("count"));
				JSONArray tempJsonDatasArray = tempJsonData
						.getJSONArray("datas");
				if (null != tempJsonDatasArray
						&& tempJsonDatasArray.length() > 0) {
					for (int i = 0; i < tempJsonDatasArray.length(); i++) {
						noticeBox = new NoticeBox();

						tempJsonDatas = tempJsonDatasArray.optJSONObject(i);

						noticeBox
								.setContent(tempJsonDatas.getString("content"));
						if (tempJsonDatas.has("isRead")
								&& !tempJsonDatas.isNull("isRead")) {
							noticeBox.setIsRead(tempJsonDatas
									.getString("isRead"));
						}
						if (tempJsonDatas.has("readTime")
								&& !tempJsonDatas.isNull("readTime")) {
							noticeBox.setReadTime(tempJsonDatas
									.getString("readTime"));
						}
						noticeBox.setIsSendMsg(tempJsonDatas
								.getString("isSendMsg"));
						noticeBox.setLastUpdateTime(tempJsonDatas
								.getString("lastUpdateTime"));
						noticeBox.setNoticeId(tempJsonDatas
								.getString("noticeId"));
						noticeBox.setOrgId(tempJsonDatas.getString("orgId"));
						noticeBox
								.setOrgName(tempJsonDatas.getString("orgName"));
						noticeBox.setSendMode(tempJsonDatas
								.getString("sendMode"));
						noticeBox.setSendTime(tempJsonDatas
								.getString("sendTime"));
						noticeBox.setTaskTime(tempJsonDatas
								.getString("taskTime"));
						noticeBox.setUserId(tempJsonDatas.getString("userId"));
						noticeBox.setUserName(tempJsonDatas
								.getString("userName"));
						noticeBox.setStatus(tempJsonDatas.getString("status"));
						if (!tempJsonDatas.isNull("userPhoto")) {
							noticeBox.setUserPhoto(tempJsonDatas
									.getString("userPhoto"));
						}
						noticeBox.setIsFav(tempJsonDatas.getString("isFav"));
						noticeBoxList.getNoticeBoxList().add(noticeBox);
					}
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noticeBoxList;
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

	public ArrayList<NoticeBox> getNoticeBoxList() {
		return noticeBoxList;
	}

	public void setNoticeBoxList(ArrayList<NoticeBox> noticeBoxList) {
		this.noticeBoxList = noticeBoxList;
	}

}
