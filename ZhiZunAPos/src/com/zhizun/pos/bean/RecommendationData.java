package com.zhizun.pos.bean;

import java.util.ArrayList;

import com.zhizun.pos.base.BaseBean;

public class RecommendationData extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MyRecommendationShare myRecommendation;
	private ArrayList<MyRecommendationPicker> receivesList=new ArrayList<MyRecommendationPicker>();
	private int verifiedCount;
	public MyRecommendationShare getRecommendation() {
		return myRecommendation;
	}
	public void setShareList(MyRecommendationShare myRecommendation) {
		this.myRecommendation = myRecommendation;
	}
	public ArrayList<MyRecommendationPicker> getPickerList() {
		return receivesList;
	}
	public void setReceivesList(
			ArrayList<MyRecommendationPicker> receivesList) {
		this.receivesList = receivesList;
	}
	public int getVerifiedCount() {
		return verifiedCount;
	}
	public void setVerifiedCount(int verifiedCount) {
		this.verifiedCount = verifiedCount;
	}

}
