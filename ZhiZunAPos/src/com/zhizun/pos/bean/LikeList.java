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
 * 赞 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class LikeList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = LikeList.class.getName();

	private String status;// 0 成功
	private String statusMessage;// 错误原因
	private List<Like> likeList = new ArrayList<Like>();

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static LikeList parse(String resultInfo) throws IOException,
			AppException {
		Like like = null;
		LikeList likeList = new LikeList();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			likeList.setStatus(jsonObject.getString("status"));
			likeList.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObjectPic = jsonArray.optJSONObject(i);
				like = new Like();
				like.setIsCancel(jsonObjectPic.getString("isCancel"));
				like.setUserId(jsonObjectPic.getString("userId"));
				like.setUserAppe(jsonObjectPic.getString("userAppe"));
				likeList.getLikeList().add(like);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return likeList;
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

	public List<Like> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<Like> likeList) {
		this.likeList = likeList;
	}

}
