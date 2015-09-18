package com.zhizun.pos.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

public class MyPrizedRecommendationBean extends BaseBean {
	/**
	 * 推荐有奖	 参与详情 参数
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private String statusMessage;//状态信息
//	private MyPrizedRecommendationList shareList;
	private String userRecommendNum;
	private ArrayList<RecommendationData> recommendationDataList=new ArrayList<RecommendationData>();
	public static MyPrizedRecommendationBean parse(String _post){
		MyPrizedRecommendationBean recommendationBean=new MyPrizedRecommendationBean();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			recommendationBean.setStatus(jsonObject.getString("status"));
			recommendationBean.setStatusMessage(jsonObject.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				tempJsonData=jsonObject.getJSONObject("data");
				JSONArray tempJsonDatasArray=tempJsonData.getJSONArray("datas");
				if (null!=tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
					for (int i = 0; i < tempJsonDatasArray.length(); i++) {
						RecommendationData recommendationData=new RecommendationData();
						MyRecommendationShare shareList=new MyRecommendationShare();
						tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
						String userRecommendNum=tempJsonDatas.getString("userRecommendNum");
						recommendationBean.setUserRecommendNum(userRecommendNum);
						JSONObject	share=tempJsonDatas.getJSONObject("share");
						shareList.setSmpType(share.getString("smpType"));
						shareList.setShareTime(share.getString("shareTime"));
						JSONArray	recommenList= tempJsonDatas.getJSONArray("receives");
						int verifiedCount = 0;
						if (null!=recommenList && recommenList.length() > 0) {
							for (int j = 0; j < recommenList.length(); j++) {
								MyRecommendationPicker recommendationPicker=new MyRecommendationPicker();
								JSONObject recommenListArray = recommenList.optJSONObject(j);
								String num=recommenListArray.getString("isVerify");
								recommendationPicker.setReferralPhone(recommenListArray.getString("referralPhone"));
								recommendationPicker.setXcode(recommenListArray.getString("xcode"));
								recommendationPicker.setIsVerify(num);
								recommendationPicker.setReferralTime(recommenListArray.getString("referralTime"));
								if (num.equals("1")) {
									verifiedCount++;
								}
								recommendationData.getPickerList().add(recommendationPicker);
							}
						}
						recommendationData.setShareList(shareList);
						recommendationData.setVerifiedCount(verifiedCount);
						recommendationBean.getRecommendationDataList().add(recommendationData);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return recommendationBean;
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
	
	public ArrayList<RecommendationData> getRecommendationDataList() {
		return recommendationDataList;
	}
	/*public void setReceivesDatas(ArrayList<RecommendationData> receivesDatas) {
		this.receivesDatas = receivesDatas;
	}*/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserRecommendNum() {
		return userRecommendNum;
	}
	public void setUserRecommendNum(String userRecommendNum) {
		this.userRecommendNum = userRecommendNum;
	}
	
}
