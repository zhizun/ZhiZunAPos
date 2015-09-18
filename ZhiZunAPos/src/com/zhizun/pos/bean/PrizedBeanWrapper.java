package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ch.epw.utils.URLs;
import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class PrizedBeanWrapper extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = PrizedBeanWrapper.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因

	private MyepePrizeBean prizeBean;

	public static PrizedBeanWrapper parse(String _post) {
		PrizedBeanWrapper prizeBeanWrapper = new PrizedBeanWrapper();

		MyepePrizeBean prize = null;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			prizeBeanWrapper.setStatus(jsonObject.getString("status"));
			prizeBeanWrapper.setStatusMessage(jsonObject
					.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				prizeBeanWrapper.setPrizeBean(parse(jsonObject
						.getJSONObject("data")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return prizeBeanWrapper;
	}

	public static MyepePrizeBean parse(JSONObject jsonEvent){

		MyepePrizeBean prizebean = new MyepePrizeBean();
		try {
			prizebean.setType(jsonEvent.getString("type"));
			prizebean.setContent(jsonEvent.getString("content"));
			prizebean.setTitle(jsonEvent.getString("title"));
			prizebean.setStartTime(jsonEvent.optString("startTime"));
			prizebean.setStatus(jsonEvent.getString("status"));
			prizebean.setEventId(jsonEvent.getString("eventId"));

			String eventUrl = jsonEvent.optString("eventUrl");
			if ((eventUrl == null || eventUrl.equals(""))
					&& prizebean.getEventId() != null) {
				eventUrl = "mktgEventController.view?method=getevent&eventId="
						+ prizebean.getEventId();
			}
			prizebean.setEventUrl(eventUrl);

			prizebean.setCreateOrgid(jsonEvent.getString("createOrgId"));
			prizebean.setCreateOrgName(jsonEvent.getString("createOrgName"));
			prizebean
					.setIntroducerBonus(jsonEvent.getString("introducerBonus"));
			prizebean.setReferralBonus(jsonEvent.getString("referralBonus"));
			prizebean.setPerAwardNum(jsonEvent.getString("perAwardNum"));

			String path = jsonEvent.optString("path");
			String thumbPath = jsonEvent.optString("thumbPath");
			if(!jsonEvent.isNull("mktgPicList")){
				List<Photo> mktgPicList = PhotoListWrapper.parse(jsonEvent.getJSONArray("mktgPicList"));
				if(mktgPicList != null && mktgPicList.size() > 0 ){
					Photo pho = mktgPicList.get(0);
					path = pho.getPath() + pho.getSaveName();
					thumbPath = pho.getThumbPath() + pho.getThumbSaveName();
				}
			}

			if (path != null && !path.equals("") && !path.startsWith(URLs.PROCOTOL)) {
				path = URLs.URL_IMG_API_HOST + path;
			}
			if (thumbPath != null && !thumbPath.equals("")
					&& !thumbPath.startsWith(URLs.PROCOTOL)) {
				thumbPath = URLs.URL_IMG_API_HOST + thumbPath;
			}
			prizebean.setEvtPicPath(path);
			prizebean.setEvtThumbPicPath(thumbPath);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (AppException e) {
			e.printStackTrace();
		}
		return prizebean;
	}

	public static List<MyepePrizeBean> parse(JSONArray jsonArray) {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		List<MyepePrizeBean> prizeList = new ArrayList<MyepePrizeBean>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				MyepePrizeBean prizebean = parse(jsonArray.getJSONObject(i));
				prizeList.add(prizebean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return prizeList;
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

	public MyepePrizeBean getPrizeBean() {
		return prizeBean;
	}

	public void setPrizeBean(MyepePrizeBean prizeBean) {
		this.prizeBean = prizeBean;
	}

}
