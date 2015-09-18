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
 * 宝宝兴趣爱好 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class CatList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = CatList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private List<InterestPri> interestPriList = new ArrayList<InterestPri>();// 、兴趣爱好

	public String getCats() {
		StringBuffer sBuffer = new StringBuffer();
		for (InterestPri interest : interestPriList) {

			sBuffer.append(interest.getCatName());
			sBuffer.append(",");

		}
		String receivers = sBuffer.toString();
		if (receivers.endsWith(",")) {
			receivers = receivers.substring(0, receivers.length() - 1);
		}

		return receivers;
	}

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static CatList parse(String resultInfo) throws IOException,
			AppException {

		InterestPri interestPri = null;
		InterestSec interestSec = null;
		CatList catList = new CatList();

		JSONObject jsonObjectOrg;

		JSONObject jsonObjectInterests;
		JSONArray jsonArrayItemArray;
		JSONObject jsonObjectItem;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			catList.setStatus(jsonObject.getString("status"));
			catList.setStatusMessage(jsonObject.getString("statusMessage"));
			catList.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONArray jsonArrayInterestsArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArrayInterestsArray.length(); i++) {
				jsonObjectInterests = jsonArrayInterestsArray.optJSONObject(i);
				interestPri = new InterestPri();

				interestPri.setCatId(jsonObjectInterests.getString("catId"));
				interestPri
						.setCatName(jsonObjectInterests.getString("catName"));

				jsonArrayItemArray = jsonObjectInterests
						.getJSONArray("itemList");
				for (int j = 0; j < jsonArrayItemArray.length(); j++) {
					jsonObjectItem = jsonArrayItemArray.optJSONObject(j);
					interestSec = new InterestSec();

					interestSec.setItemId(jsonObjectItem.getString("itemId"));
					interestSec.setItemName(jsonObjectItem
							.getString("itemName"));
					interestSec.setIsSelected(false);
					interestPri.getInterestSecList().add(interestSec);
				}
				catList.getInterestPriList().add(interestPri);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return catList;
	}

	public List<InterestPri> getInterestPriList() {
		return interestPriList;
	}

	public void setInterestPriList(List<InterestPri> interestPriList) {
		this.interestPriList = interestPriList;
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
}
