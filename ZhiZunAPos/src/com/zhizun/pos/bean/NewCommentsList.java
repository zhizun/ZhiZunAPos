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

public class NewCommentsList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacherList";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	List<NewComments> newCommentsList = new ArrayList<NewComments>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NewCommentsList parse(String _post) {
		NewComments newComments = null;
		Photo photo = null;
		Like like = null;
		Comments comments = null;
		NewCommentsList newCommentsList = new NewCommentsList();

		JSONObject tempJsonData = null;
		JSONObject temJsonObjectLike = null;
		JSONObject temJsonObjectPhoto = null;
		JSONObject tempJsonComments = null;
		JSONObject tempJsonechoParameter;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			newCommentsList.setStatus(jsonObject.getString("status"));
			newCommentsList.setTimestamp(jsonObject.getLong("timestamp"));
			newCommentsList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			JSONArray tempJsonDataArray = jsonObject.getJSONArray("data");
			JSONArray tempJsonechoParameterArray = jsonObject
					.getJSONArray("echoParameter");
			if (null != tempJsonDataArray && tempJsonDataArray.length() > 0) {
				for (int i = 0; i < tempJsonDataArray.length(); i++) {
					newComments = new NewComments();
					tempJsonData = tempJsonDataArray.optJSONObject(i);

					newComments.setNewCommentContent(tempJsonData
							.getString("content"));
					newComments.setNewCommentID(tempJsonData
							.getString("commentId"));
					newComments.setNewCommentRefId(tempJsonData
							.getString("refId"));
					newComments.setNewCommentTime(tempJsonData
							.getString("commentTime"));
					newComments.setNewCommentUserAppe(tempJsonData
							.getString("userAppe"));
					newComments.setNewCommentUserPhoto(tempJsonData
							.getString("userPhoto"));

					for (int j = 0; j < tempJsonechoParameterArray.length(); j++) {
						tempJsonechoParameter = tempJsonechoParameterArray
								.optJSONObject(j);
						if (tempJsonData.getString("refId").equals(
								tempJsonechoParameter.getString("dynamicId"))) {
							newComments
									.setDynamicUserPhoto(tempJsonechoParameter
											.getString("userPhoto"));
							newComments.getDynamicTeacher().setDynamicContent(
									tempJsonechoParameter.getString("content"));
							newComments.getDynamicTeacher()
									.setSendUserName(
											tempJsonechoParameter
													.getString("userName"));
							newComments.getDynamicTeacher().setSendUserId(
									tempJsonechoParameter.getString("userId"));
							newComments.getDynamicTeacher().setOrgId(
									tempJsonechoParameter.getString("orgId"));
							newComments.getDynamicTeacher().setOrgName(
									tempJsonechoParameter.getString("orgName"));
							newComments.getDynamicTeacher().setDynamicID(
									tempJsonechoParameter
											.getString("dynamicId"));
							newComments.getDynamicTeacher()
									.setSendTime(
											tempJsonechoParameter
													.getString("sendTime"));
							newComments.getDynamicTeacher().setCurrenUserFav(
									tempJsonechoParameter
											.getBoolean("currenUserFav"));
							if (!tempJsonechoParameter.isNull("photoList")) {
								JSONArray jsonPhotoArray = tempJsonechoParameter
										.getJSONArray("photoList");

								if (null != jsonPhotoArray
										&& jsonPhotoArray.length() > 0) {
									for (int x = 0; x < jsonPhotoArray.length(); x++) {
										temJsonObjectPhoto = jsonPhotoArray
												.optJSONObject(x);
										photo = new Photo();
										photo.setPath(temJsonObjectPhoto
												.getString("path"));
										photo.setPhotoId(temJsonObjectPhoto
												.getString("photoId"));
										photo.setSaveName(temJsonObjectPhoto
												.getString("saveName"));
										photo.setThumbPath(temJsonObjectPhoto
												.getString("thumbPath"));
										photo.setThumbSaveName(temJsonObjectPhoto
												.getString("thumbSaveName"));
										newComments.getDynamicTeacher()
												.getPhotoList().add(photo);
									}
								}
							}
							if (!tempJsonechoParameter.isNull("fsiLikeList")) {
								JSONArray jsonLikeArray = tempJsonechoParameter
										.getJSONArray("fsiLikeList");
								if (null != jsonLikeArray
										&& jsonLikeArray.length() > 0) {
									for (int y = 0; y < jsonLikeArray.length(); y++) {
										temJsonObjectLike = jsonLikeArray
												.optJSONObject(y);
										like = new Like();
										like.setIsCancel(temJsonObjectLike
												.getString("isCancel"));
										like.setUserId(temJsonObjectLike
												.getString("userId"));
										like.setUserAppe(temJsonObjectLike
												.getString("userAppe"));

										newComments.getDynamicTeacher()
												.getLikeList().add(like);
									}
								}
							}
							if (!tempJsonechoParameter.isNull("commentList")) {
								JSONArray jsonCommentsArray = tempJsonechoParameter
										.getJSONArray("commentList");

								if (null != jsonCommentsArray
										&& jsonCommentsArray.length() > 0) {
									for (int k = 0; k < jsonCommentsArray
											.length(); k++) {
										tempJsonComments = jsonCommentsArray
												.optJSONObject(k);
										comments = new Comments();
										comments.setDynamicID(tempJsonechoParameter
												.getString("dynamicId"));

										comments.setUserAppe(tempJsonComments
												.getString("userAppe"));
										comments.setReplyUserAppe(tempJsonComments
												.getString("replyUserAppe"));
										comments.setCommentContent(tempJsonComments
												.getString("content"));
										comments.setCommentID(tempJsonComments
												.getString("commentId"));
										comments.setCommentTime(tempJsonComments
												.getString("commentTime"));
										comments.setReplyUserID(tempJsonComments
												.getString("replyUserId"));
										comments.setReplyCommentID(tempJsonComments
												.getString("replyCommentId"));
										comments.setSf_read(tempJsonComments
												.getString("isRead"));
										comments.setUserId(tempJsonComments
												.getString("userId"));
										newComments.getDynamicTeacher()
												.getCommentsList()
												.add(comments);
									}
								}

							}

						}
						newCommentsList.getNewCommentsList().add(newComments);

					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newCommentsList;
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

	public List<NewComments> getNewCommentsList() {
		return newCommentsList;
	}

	public void setNewCommentsList(List<NewComments> newCommentsList) {
		this.newCommentsList = newCommentsList;
	}

}
