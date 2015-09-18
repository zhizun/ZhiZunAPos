package com.zhizun.pos.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 最新回复 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class VoiceDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacherList";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private Voice voice = new Voice();

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static VoiceDetail parse(String _post) {
		Voice voice = new Voice();
		Photo photo = null;
		Like like = null;
		Comments comments = null;
		VoiceDetail voiceDetail = new VoiceDetail();

		JSONObject tempJsonData = null;
		JSONObject temJsonObjectLike = null;
		JSONObject temJsonObjectPhoto = null;
		JSONObject tempJsonComments = null;
		JSONObject tempJsonechoParameter;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			voiceDetail.setStatus(jsonObject.getString("status"));
			voiceDetail.setStatusMessage(jsonObject.getString("statusMessage"));
			tempJsonechoParameter = jsonObject.getJSONObject("data");

			voice.setPhotopath(tempJsonechoParameter.getString("photopath"));
			voice.setContent(tempJsonechoParameter.getString("content"));
			voice.setUserName(tempJsonechoParameter.getString("userName"));
			voice.setUserId(tempJsonechoParameter.getString("userId"));
			voice.setOrgId(tempJsonechoParameter.getString("orgId"));
			voice.setOrgName(tempJsonechoParameter.getString("orgName"));
			voice.setVoice_id(tempJsonechoParameter.getString("voice_id"));
			voice.setSendTime(tempJsonechoParameter.getString("sendTime"));
			voice.setCurrenUserFav(tempJsonechoParameter
					.getBoolean("currenUserFav"));
			voice.setCurrenUserLike(false);

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
						voice.getPhotoList().add(photo);
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

						voice.getLikeList().add(like);
					}
				}
			}
			if (!tempJsonechoParameter.isNull("commentList")) {
				JSONArray jsonCommentsArray = tempJsonechoParameter
						.getJSONArray("commentList");
				voice.setCommentCount(jsonCommentsArray.length());
				if (null != jsonCommentsArray && jsonCommentsArray.length() > 0) {
					for (int k = 0; k < jsonCommentsArray.length(); k++) {
						tempJsonComments = jsonCommentsArray.optJSONObject(k);
						comments = new Comments();

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

						voice.getCommentsList().add(comments);
					}
					voice.setCommentCount(jsonCommentsArray.length());
				}

			}

			voiceDetail.setVoice(voice);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return voiceDetail;
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
