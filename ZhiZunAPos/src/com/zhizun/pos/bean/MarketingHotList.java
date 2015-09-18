package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 获取首页推荐列表
 * 
 */

public class MarketingHotList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = MarketingHotList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因

	private List<MarketingHot> marketingHotList;

	private List<CourseListItemList> courseItemList;

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static MarketingHotList parse(String resultInfo) throws IOException,
			AppException {
		MarketingHotList marketingHotList = new MarketingHotList();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			marketingHotList.setStatus(jsonObject.getString("status"));
			marketingHotList.setStatusMessage(jsonObject
					.getString("statusMessage"));

			if (!jsonObject.isNull("data")) {
				JSONObject jsonDataObject = jsonObject.getJSONObject("data");
				if (!jsonDataObject.isNull("hots")) {
					marketingHotList.setMarketingHotList(parse(jsonDataObject
							.getJSONArray("hots")));
				}

				if (!jsonDataObject.isNull("orgRecommend")) {
					marketingHotList
							.setCourseItemList(CourseListViewBean
									.parse(jsonDataObject
											.getJSONArray("orgRecommend")));
				}
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return marketingHotList;
	}

	public static List<MarketingHot> parse(JSONArray jsonArray)
			throws AppException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}

		ArrayList<MarketingHot> hotList = new ArrayList<MarketingHot>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);
				MarketingHot marketingHot = new MarketingHot();
				marketingHot.setEventId(jsonObjectPic.getString("eventId"));
				marketingHot.setPicUrl(jsonObjectPic.getString("picUrl"));
				hotList.add(marketingHot);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return hotList;
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

	public List<MarketingHot> getMarketingHotList() {
		return marketingHotList;
	}

	public void setMarketingHotList(List<MarketingHot> marketingHotList) {
		this.marketingHotList = marketingHotList;
	}

	public List<CourseListItemList> getCourseItemList() {
		return courseItemList;
	}

	public void setCourseItemList(List<CourseListItemList> courseItemList) {
		this.courseItemList = courseItemList;
	}

}
