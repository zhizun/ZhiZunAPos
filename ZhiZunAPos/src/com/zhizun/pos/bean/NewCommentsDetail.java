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

public class NewCommentsDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacherList";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private DynamicTeacher dynamicTeacher = new DynamicTeacher();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static NewCommentsDetail parse(String _post) {
		DynamicTeacher dynamicTeacher = new DynamicTeacher();
		Photo photo = null;
		Like like = null;
		Comments comments = null;
		NewCommentsDetail newCommentsDetail = new NewCommentsDetail();

		JSONObject tempJsonData = null;
		JSONObject temJsonObjectLike = null;
		JSONObject temJsonObjectPhoto = null;
		JSONObject tempJsonComments = null;
		JSONObject tempJsonechoParameter;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			newCommentsDetail.setStatus(jsonObject.getString("status"));
			newCommentsDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonechoParameter = jsonObject.getJSONObject("data");

			dynamicTeacher.setUserPhoto(tempJsonechoParameter
					.getString("userPhoto"));
			dynamicTeacher.setDynamicContent(tempJsonechoParameter
					.getString("content"));
			dynamicTeacher.setSendUserName(tempJsonechoParameter
					.getString("userName"));
			dynamicTeacher.setSendUserId(tempJsonechoParameter
					.getString("userId"));
			dynamicTeacher.setOrgId(tempJsonechoParameter.getString("orgId"));
			dynamicTeacher.setOrgName(tempJsonechoParameter
					.getString("orgName"));
			dynamicTeacher.setDynamicID(tempJsonechoParameter
					.getString("dynamicId"));
			dynamicTeacher.setSendTime(tempJsonechoParameter
					.getString("sendTime"));
			dynamicTeacher.setCurrenUserFav(tempJsonechoParameter
					.getBoolean("currenUserFav"));
			dynamicTeacher.setCurrenUserLike(false);

			if (!tempJsonechoParameter.isNull("photoList")) {
				JSONArray jsonPhotoArray = tempJsonechoParameter
						.getJSONArray("photoList");

				if (null != jsonPhotoArray && jsonPhotoArray.length() > 0) {
					for (int x = 0; x < jsonPhotoArray.length(); x++) {
						temJsonObjectPhoto = jsonPhotoArray.optJSONObject(x);
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
						dynamicTeacher.getPhotoList().add(photo);
					}
				}
			}
			if (!tempJsonechoParameter.isNull("fsiLikeList")) {
				JSONArray jsonLikeArray = tempJsonechoParameter
						.getJSONArray("fsiLikeList");
				if (null != jsonLikeArray && jsonLikeArray.length() > 0) {
					for (int y = 0; y < jsonLikeArray.length(); y++) {
						temJsonObjectLike = jsonLikeArray.optJSONObject(y);
						like = new Like();
						like.setIsCancel(temJsonObjectLike
								.getString("isCancel"));
						like.setUserId(temJsonObjectLike.getString("userId"));
						like.setUserAppe(temJsonObjectLike
								.getString("userAppe"));

						dynamicTeacher.getLikeList().add(like);
					}
				}
			}
			if (!tempJsonechoParameter.isNull("commentList")) {
				JSONArray jsonCommentsArray = tempJsonechoParameter
						.getJSONArray("commentList");

				if (null != jsonCommentsArray && jsonCommentsArray.length() > 0) {
					for (int k = 0; k < jsonCommentsArray.length(); k++) {
						tempJsonComments = jsonCommentsArray.optJSONObject(k);
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
						comments.setUserId(tempJsonComments.getString("userId"));
						dynamicTeacher.getCommentsList().add(comments);
					}
					dynamicTeacher.setCommentCount(jsonCommentsArray
							.length());
				}

			}

			newCommentsDetail.setDynamicTeacher(dynamicTeacher);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newCommentsDetail;
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

	public DynamicTeacher getDynamicTeacher() {
		return dynamicTeacher;
	}

	public void setDynamicTeacher(DynamicTeacher dynamicTeacher) {
		this.dynamicTeacher = dynamicTeacher;
	}

}
