package com.zhizun.pos.bean;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;
	/**
	 * 有奖活动 json
	 */
public class MyepePrizedBeanList extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;//总数量
	private String mktType;
	public String getMktType() {
		return mktType;
	}

	public void setMktType(String mktType) {
		this.mktType = mktType;
	}

	ArrayList<MyepePrizeBean> prizeList=new ArrayList<MyepePrizeBean>();
	
	public static MyepePrizedBeanList parse(String _post){
		MyepePrizedBeanList prizeBeanList=new MyepePrizedBeanList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			prizeBeanList.setStatus(jsonObject.getString("status"));
			prizeBeanList.setStatusMessage(jsonObject.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				tempJsonData=jsonObject.getJSONObject("data");
				prizeBeanList.setDataCount(tempJsonData.getString("count"));
				JSONArray tempJsonDatasArray=tempJsonData.getJSONArray("datas");
				if (null != tempJsonDatasArray
						&& tempJsonDatasArray.length() > 0) {
					for (int i = 0; i < tempJsonDatasArray.length(); i++) {
						
						tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
						JSONObject tempJsonEvent = tempJsonDatasArray.optJSONObject(i).getJSONObject("event");
						MyepePrizeBean prizebean = PrizedBeanWrapper.parse(tempJsonEvent);
						
						prizebean.setUserValidShareNum(tempJsonDatas.getString("userValidShareNum"));
						prizebean.setUserRecommendNum(tempJsonDatas.getString("userRecommendNum"));
						prizebean.setUserPickNum(tempJsonDatas.getString("userPickNum"));
						prizebean.setUserShareNum(tempJsonDatas.getString("userShareNum"));
						
						prizeBeanList.getPrizeList().add(prizebean);
					}
				}
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

	public ArrayList<MyepePrizeBean> getPrizeList() {
		return prizeList;
	}

	public void setPrizeList(ArrayList<MyepePrizeBean> prizeList) {
		this.prizeList = prizeList;
	}

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

	
	
}
