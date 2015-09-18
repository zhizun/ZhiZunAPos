package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 通知模版 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NoticeTempleteList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NoticeTempleteList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总数量

	List<NoticeTemplete> noticeTempleteList = new ArrayList<NoticeTemplete>();

	/**
	 * 解析JSON得到 通知模版列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NoticeTempleteList parse(String _post) {
		NoticeTemplete noticeTemplete = null;
		NoticeTempleteList noticeTempleteList = new NoticeTempleteList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			noticeTempleteList.setStatus(jsonObject.getString("status"));
			noticeTempleteList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			noticeTempleteList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					noticeTemplete = new NoticeTemplete();

					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);

					noticeTemplete.setContent(tempJsonDatas
							.getString("content"));
					noticeTemplete.setCrtTime(tempJsonDatas
							.getString("crtTime"));
					noticeTemplete.setIsSystem(tempJsonDatas
							.getString("isSystem"));

					noticeTemplete.setLastUpdateTime(tempJsonDatas
							.getString("lastUpdateTime"));
					noticeTemplete.setTag(tempJsonDatas.getString("tag"));
					noticeTemplete.setTemplateId(tempJsonDatas
							.getString("templateId"));
					noticeTemplete.setUserId(tempJsonDatas.getString("userId"));
					noticeTempleteList.getNoticeTempleteList().add(
							noticeTemplete);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noticeTempleteList;
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

	public List<NoticeTemplete> getNoticeTempleteList() {
		return noticeTempleteList;
	}

	public void setNoticeTempleteList(List<NoticeTemplete> noticeTempleteList) {
		this.noticeTempleteList = noticeTempleteList;
	}

}
