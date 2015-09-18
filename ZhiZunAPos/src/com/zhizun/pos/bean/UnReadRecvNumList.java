package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 教师端未读数量提示 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UnReadRecvNumList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	List<UnReadRecvNum> unReadRecvNumList = new ArrayList<UnReadRecvNum>();

	/**
	 * 解析JSON得到列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static UnReadRecvNumList parse(String _post) {

		UnReadRecvNum unReadRecvNum = null;
		UnReadRecvNumList unReadRecvNumList = new UnReadRecvNumList();
		JSONObject tempJsonData = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			unReadRecvNumList.setStatus(jsonObject.getString("status"));
			unReadRecvNumList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			JSONArray tempJsonDatasArray = jsonObject.getJSONArray("data");
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					unReadRecvNum = new UnReadRecvNum();
					tempJsonData = tempJsonDatasArray.optJSONObject(i);
					unReadRecvNum.setCount(tempJsonData.getString("count"));
					unReadRecvNum.setType(tempJsonData.getString("type"));
					unReadRecvNum.setUserId(tempJsonData.getString("userId"));

					unReadRecvNumList.getUnReadRecvNumList().add(unReadRecvNum);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return unReadRecvNumList;
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

	public List<UnReadRecvNum> getUnReadRecvNumList() {
		return unReadRecvNumList;
	}

	public void setUnReadRecvNumList(List<UnReadRecvNum> unReadRecvNumList) {
		this.unReadRecvNumList = unReadRecvNumList;
	}

}
