package com.zhizun.pos.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

public class MyPrizedParticipant extends BaseBean {
	/**
	 * 分享有奖 参与详情 参数
	 */
	private static final long serialVersionUID = 1L;
	private String status;//状态码
	private String statusMessage;//状态信息
	private String leastShareNum;//最少分享次数
	private String ValidCount;//有效分享次数
	private MktgEventAwardRec mktgEventAwardRec;
	private ArrayList<MyPrizedShareList> shareList=new ArrayList<MyPrizedShareList>();
	
	public static MyPrizedParticipant parse(String _post){
		MyPrizedParticipant prizeBeanList=new MyPrizedParticipant();
		MyPrizedShareList priceList=null;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			prizeBeanList.setStatus(jsonObject.getString("status"));
			jsonObject.getString("statusMessage");
			prizeBeanList.setStatusMessage(jsonObject.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				tempJsonData=jsonObject.getJSONObject("data");
				prizeBeanList.setValidCount(tempJsonData.getString("validCount"));
				prizeBeanList.setLeastShareNum(tempJsonData.getString("leastShareNum"));
					if (!tempJsonData.isNull("mktgEventAwardRec")) {
						MktgEventAwardRec mktg=new MktgEventAwardRec();
						JSONObject	mktgEvent=tempJsonData.getJSONObject("mktgEventAwardRec");
						mktg.setReceiverTime(mktgEvent.getString("receiverTime"));
						prizeBeanList.setMktgEventAwardRec(mktg);
					}
				JSONArray tempJsonDatasArray=tempJsonData.getJSONArray("shareList");
				if (null != tempJsonDatasArray
						&& tempJsonDatasArray.length() > 0) {
					for (int i = 0; i < tempJsonDatasArray.length(); i++) {
						priceList=new MyPrizedShareList();
						tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
						priceList.setRefId(tempJsonDatas.getString("refId"));
						priceList.setLastUpdateTime(tempJsonDatas.getString("lastUpdateTime"));
						priceList.setType(tempJsonDatas.getString("type"));
						priceList.setShareTime(tempJsonDatas.getString("shareTime"));
						priceList.setNewFlag(tempJsonDatas.getString("newFlag"));
						priceList.setIsValid(tempJsonDatas.getString("isValid"));
						priceList.setSmpType(tempJsonDatas.getString("smpType"));
						prizeBeanList.getShareList().add(priceList);
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

	public String getLeastShareNum() {
		return leastShareNum;
	}

	public void setLeastShareNum(String leastShareNum) {
		this.leastShareNum = leastShareNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValidCount() {
		return ValidCount;
	}
	public void setValidCount(String validCount) {
		ValidCount = validCount;
	}
	public ArrayList<MyPrizedShareList> getShareList() {
		return shareList;
	}

	public void setShareList(ArrayList<MyPrizedShareList> shareList) {
		this.shareList = shareList;
	}

	public MktgEventAwardRec getMktgEventAwardRec() {
		return mktgEventAwardRec;
	}

	public void setMktgEventAwardRec(MktgEventAwardRec mktgEventAwardRec) {
		this.mktgEventAwardRec = mktgEventAwardRec;
	}

}
