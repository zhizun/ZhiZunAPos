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
 * 搜索建议
 * 
 */

public class SuggestNameWrapper extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = SuggestNameWrapper.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因

	private List<SuggestName> suggestNameList = new ArrayList<SuggestName>();

	public static SuggestNameWrapper parse(String resultInfo) throws IOException,
			AppException {
		SuggestNameWrapper suggestNameList = new SuggestNameWrapper();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			suggestNameList.setStatus(jsonObject.getString("status"));
			suggestNameList.setStatusMessage(jsonObject
					.getString("statusMessage"));

			if (!jsonObject.isNull("data")) {
				JSONArray jsonArray = jsonObject.getJSONObject("data")
						.getJSONArray("datas");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonSuggestObject = jsonArray.optJSONObject(i);
					SuggestName suggest = new SuggestName();
					// json对象不不包括courId，表示结果为搜索机构
					if (jsonSuggestObject.isNull("courId")) {
						suggest.setSearchType("1");
						suggest.setOrgId(jsonSuggestObject.optString("orgId"));
						suggest.setOrgName(jsonSuggestObject.optString("name"));
						if (!jsonSuggestObject.isNull("logoPath")) {
							suggest.setLogoPath(jsonSuggestObject.optString("logoPath"));
						}else {
							suggest.setLogoPath(null);
						}
						suggest.setOrgDrr(jsonSuggestObject.getJSONObject("addr").getString("address"));
					} else {
						suggest.setSearchType("0");
						suggest.setCourId(jsonSuggestObject.optString("courId"));
						suggest.setCourName(jsonSuggestObject.optString("name"));
						JSONObject orgObject = jsonSuggestObject
								.optJSONObject("org");
						if (orgObject != null) {
							suggest.setOrgId(orgObject.optString("orgId"));
							suggest.setOrgName(orgObject.optString("orgName"));
						}
					}
					suggestNameList.getSuggestNameList().add(suggest);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return suggestNameList;
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

	public List<SuggestName> getSuggestNameList() {
		return suggestNameList;
	}

	public void setSuggestNameList(List<SuggestName> suggestNameList) {
		this.suggestNameList = suggestNameList;
	}

	public String[] getSuggestNames() {
		if (suggestNameList == null || suggestNameList.size() == 0) {
			return new String[] {};
		}

		String[] names = new String[suggestNameList.size()];
		for (int k = 0; k < suggestNameList.size(); k++) {
			SuggestName suggest = suggestNameList.get(k);
			// 搜索课程建议
			if ("0".equals(suggest.getSearchType())) {
				names[k] = suggest.getCourName();
			} else {
				names[k] = suggest.getOrgName();
			}
		}

		return names;
	}

}


