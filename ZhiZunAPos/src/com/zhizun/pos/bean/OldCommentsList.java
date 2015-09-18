package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ch.epw.utils.URLs;
import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 历史回复 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class OldCommentsList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = OldCommentsList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<NewComments> oldCommentsList = new ArrayList<NewComments>();

	/**
	 * 解析JSON得到回复历史
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static OldCommentsList parse(String _post) {
		NewComments newComments = null;
		Photo photo = null;
		Like like = null;
		Comments comments = null;
		OldCommentsList oldCommentsList = new OldCommentsList();
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonechoParameter;
		JSONObject temJsonObjectLike = null;
		JSONObject temJsonObjectPhoto = null;
		JSONObject tempJsonComments = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			oldCommentsList.setStatus(jsonObject.getString("status"));
			oldCommentsList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			oldCommentsList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			JSONArray tempJsonechoParameterArray = jsonObject
					.getJSONArray("echoParameter");

			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					newComments = new NewComments();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					newComments.setNewCommentContent(tempJsonDatas
							.getString("content"));
					newComments.setNewCommentID(tempJsonDatas
							.getString("commentId"));
					newComments.setNewCommentRefId(tempJsonDatas
							.getString("refId"));
					newComments.setNewCommentTime(tempJsonDatas
							.getString("commentTime"));
					newComments.setNewCommentUserAppe(tempJsonDatas
							.getString("userAppe"));
					newComments.setNewCommentUserPhoto( tempJsonDatas.getString("userPhoto"));

					for (int j = 0; j < tempJsonechoParameterArray.length(); j++) {
						tempJsonechoParameter = tempJsonechoParameterArray
								.optJSONObject(j);
						if (tempJsonDatas.getString("refId").equals(
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

					}
					oldCommentsList.getOldCommentsList().add(newComments);

				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return oldCommentsList;
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

	public List<NewComments> getOldCommentsList() {
		return oldCommentsList;
	}

	public void setOldCommentsList(List<NewComments> oldCommentsList) {
		this.oldCommentsList = oldCommentsList;
	}

}
