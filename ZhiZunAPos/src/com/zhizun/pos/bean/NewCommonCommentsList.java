package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 最新回复 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class NewCommonCommentsList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = NewCommonCommentsList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总数量
	List<NewCommonComments> newCommonCommentsList = new ArrayList<NewCommonComments>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NewCommonCommentsList parse(String _post) {
		NewCommonComments newCommentComments = null;
		Photo photo = null;

		NewCommonCommentsList newCommonCommentsList = new NewCommonCommentsList();

		JSONObject tempJsonData = null;
		JSONObject tempJsonDatas = null;
		JSONObject temJsonObjectPhoto = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			newCommonCommentsList.setStatus(jsonObject.getString("status"));
			newCommonCommentsList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			newCommonCommentsList.setTimestamp(jsonObject.getLong("timestamp"));
			tempJsonData = jsonObject.getJSONObject("data");
			newCommonCommentsList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					newCommentComments = new NewCommonComments();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					newCommentComments.setCommentId(tempJsonDatas
							.getString("commentId"));
					newCommentComments.setCommentTime(tempJsonDatas
							.getString("commentTime"));
					newCommentComments.setContent(tempJsonDatas
							.getString("content"));
					newCommentComments.setRefContent(tempJsonDatas
							.getString("refContent"));
					newCommentComments.setRefId(tempJsonDatas
							.getString("refId"));
					newCommentComments.setType(tempJsonDatas.getString("type"));
					newCommentComments.setUserId(tempJsonDatas
							.getString("userId"));
					 newCommentComments.setUserAppe(tempJsonDatas
					 .getString("userAppe"));
					newCommentComments.setUserPhoto(tempJsonDatas
							.getString("userPhoto"));
					JSONArray jsonPhotoArray = tempJsonDatas
							.getJSONArray("photoList");

					if (null != jsonPhotoArray && jsonPhotoArray.length() > 0) {
						for (int x = 0; x < jsonPhotoArray.length(); x++) {
							temJsonObjectPhoto = jsonPhotoArray
									.optJSONObject(x);
							photo = new Photo();
							photo.setPath(temJsonObjectPhoto.getString("path"));
							photo.setPhotoId(temJsonObjectPhoto
									.getString("photoId"));
							photo.setSaveName(temJsonObjectPhoto
									.getString("saveName"));
							photo.setThumbPath(temJsonObjectPhoto
									.getString("thumbPath"));
							photo.setThumbSaveName(temJsonObjectPhoto
									.getString("thumbSaveName"));
							newCommentComments.getPhotoList().add(photo);
						}
					}
					newCommonCommentsList.getNewCommonCommentsList().add(
							newCommentComments);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newCommonCommentsList;
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

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

	public List<NewCommonComments> getNewCommonCommentsList() {
		return newCommonCommentsList;
	}

	public void setNewCommonCommentsList(
			List<NewCommonComments> newCommonCommentsList) {
		this.newCommonCommentsList = newCommonCommentsList;
	}

}
