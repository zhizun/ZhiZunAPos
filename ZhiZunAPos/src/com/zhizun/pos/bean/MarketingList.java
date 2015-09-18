package com.zhizun.pos.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 首页 有奖活动 json
 */
public class MarketingList extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总数量

	List<MyepePrizeBean> prizeList;

	public static MarketingList parse(String _post) {
		MarketingList prizeBeanList = new MarketingList();
		try {
			JSONObject jsonObject = new JSONObject(_post);
			prizeBeanList.setStatus(jsonObject.getString("status"));
			prizeBeanList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				JSONObject jsonDataObject = jsonObject.getJSONObject("data");
				prizeBeanList.setDataCount(jsonDataObject.getString("count"));
				JSONArray tempJsonDatasArray = jsonDataObject
						.getJSONArray("datas");
				prizeBeanList.setPrizeList(PrizedBeanWrapper
						.parse(tempJsonDatasArray));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return prizeBeanList;
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

	public List<MyepePrizeBean> getPrizeList() {
		return prizeList;
	}

	public void setPrizeList(List<MyepePrizeBean> prizeList) {
		this.prizeList = prizeList;
	}

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

}
