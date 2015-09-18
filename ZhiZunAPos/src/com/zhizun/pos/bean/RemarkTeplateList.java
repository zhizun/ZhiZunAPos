package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 最新回复 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class RemarkTeplateList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacherList";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	List<RemarkTeplate> remarkTeplateList = new ArrayList<RemarkTeplate>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static RemarkTeplateList parse(String _post) {
		RemarkTeplate remarkTeplate = null;
		RemarkTeplateList remarkTeplateList = new RemarkTeplateList();

		JSONObject tempJsonData = null;
		JSONArray tempJsonDataArray = null;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			remarkTeplateList.setStatus(jsonObject.getString("status"));
			remarkTeplateList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonDataArray = jsonObject.getJSONArray("data");

			if (null != tempJsonDataArray && tempJsonDataArray.length() > 0) {
				for (int i = 0; i < tempJsonDataArray.length(); i++) {
					remarkTeplate = new RemarkTeplate();
					tempJsonData = tempJsonDataArray.optJSONObject(i);

					remarkTeplate.setItem(tempJsonData.getString("item"));
					remarkTeplate.setRemarkItemId(tempJsonData
							.getString("remarkItemId"));
					remarkTeplateList.getRemarkTeplateList().add(remarkTeplate);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return remarkTeplateList;
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

	public List<RemarkTeplate> getRemarkTeplateList() {
		return remarkTeplateList;
	}

	public void setRemarkTeplateList(List<RemarkTeplate> remarkTeplateList) {
		this.remarkTeplateList = remarkTeplateList;
	}

}
