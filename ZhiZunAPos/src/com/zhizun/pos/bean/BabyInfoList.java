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

import android.util.Log;

/**
 * 宝宝资料 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class BabyInfoList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = BabyInfoList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private List<BabyInfo> babyInfoList = new ArrayList<BabyInfo>();

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static BabyInfoList parse(String resultInfo) throws IOException,
			AppException {
		BabyInfo babyInfo = null;
		BabyInfoList pictureList = new BabyInfoList();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			pictureList.setStatus(jsonObject.getString("status"));
			pictureList.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);
				babyInfo = new BabyInfo();
				babyInfo.setChildId(jsonObjectPic.getString("childId"));
				babyInfo.setName(jsonObjectPic.getString("name"));
				pictureList.getBabyInfoList().add(babyInfo);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return pictureList;
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

	public List<BabyInfo> getBabyInfoList() {
		return babyInfoList;
	}

	public void setBabyInfoList(List<BabyInfo> babyInfoList) {
		this.babyInfoList = babyInfoList;
	}

}
