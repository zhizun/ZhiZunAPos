package com.zhizun.pos.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

/**
 * 在校点评详情 家长端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class RemarkNewCommentsDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = RemarkNewCommentsDetail.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private Remark remark = new Remark();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static RemarkNewCommentsDetail parse(String _post) {
		Comments comments = null;
		Remark remark = new Remark();
		Photo photo = null;
		Rating rating = null;
		Like like = null;
		RemarkNewCommentsDetail remarkNewCommentsDetail = new RemarkNewCommentsDetail();
		
		JSONObject tempJsonDatas;
		JSONObject tempJsonComments;
		JSONObject temJsonObjectPhoto;
		JSONObject temJsonObjectLike;
		JSONObject temJsonObjectRating;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			remarkNewCommentsDetail.setStatus(jsonObject.getString("status"));
			remarkNewCommentsDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonDatas = jsonObject.getJSONObject("data");

			remark = new Remark();

			remark.setContent(tempJsonDatas.getString("content"));
			remark.setCurrenUserFav(tempJsonDatas.getBoolean("currenUserFav"));
			remark.setCurrenUserLike(false);
			remark.setLastUpdateTime(tempJsonDatas.getString("lastUpdateTime"));
			remark.setOrgId(tempJsonDatas.getString("orgId"));
			remark.setOrgName(tempJsonDatas.getString("orgName"));
			remark.setUserPhoto(tempJsonDatas.getString("userPhoto"));
			remark.setRemarkId(tempJsonDatas.getString("remarkId"));
			remark.setSendTime(tempJsonDatas.getString("sendTime"));
			remark.setUserId(tempJsonDatas.getString("userId"));
			remark.setUserName(tempJsonDatas.getString("userName"));

			JSONArray jsonPhotoArray = tempJsonDatas.getJSONArray("photoList");

			if (null != jsonPhotoArray && jsonPhotoArray.length() > 0) {
				for (int x = 0; x < jsonPhotoArray.length(); x++) {
					temJsonObjectPhoto = jsonPhotoArray.optJSONObject(x);
					photo = new Photo();
					photo.setPath(temJsonObjectPhoto.getString("path"));
					photo.setPhotoId(temJsonObjectPhoto.getString("photoId"));
					photo.setSaveName(temJsonObjectPhoto.getString("saveName"));
					photo.setThumbPath(temJsonObjectPhoto
							.getString("thumbPath"));
					photo.setThumbSaveName(temJsonObjectPhoto
							.getString("thumbSaveName"));
					remark.getPhotoList().add(photo);
				}
			}
			JSONArray jsonRatingArray = tempJsonDatas
					.getJSONArray("remarkList");

			if (null != jsonRatingArray && jsonRatingArray.length() > 0) {
				for (int m = 0; m < jsonRatingArray.length(); m++) {
					temJsonObjectRating = jsonRatingArray.optJSONObject(m);
					rating = new Rating();
					rating.setRating(temJsonObjectRating.getString("rating"));
					rating.setRemarkItemId(temJsonObjectRating
							.getString("remarkItemId"));
					rating.setRemarkItemName(temJsonObjectRating
							.getString("remarkItemName"));
					remark.getRatingList().add(rating);
				}
			}
			JSONArray jsonLikeArray = tempJsonDatas.getJSONArray("fsiLikeList");
			if (null != jsonLikeArray && jsonLikeArray.length() > 0) {
				for (int y = 0; y < jsonLikeArray.length(); y++) {
					temJsonObjectLike = jsonLikeArray.optJSONObject(y);
					like = new Like();
					like.setIsCancel(temJsonObjectLike.getString("isCancel"));
					like.setUserId(temJsonObjectLike.getString("userId"));
					like.setUserAppe(temJsonObjectLike.getString("userAppe"));

					remark.getLikeList().add(like);
				}
			}
			JSONArray jsonCommentsArray = tempJsonDatas
					.getJSONArray("commentList");

			if (null != jsonCommentsArray && jsonCommentsArray.length() > 0) {
				for (int k = 0; k < jsonCommentsArray.length(); k++) {
					tempJsonComments = jsonCommentsArray.optJSONObject(k);
					comments = new Comments();

					comments.setUserAppe(tempJsonComments.getString("userAppe"));
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
					comments.setSf_read(tempJsonComments.getString("isRead"));
					comments.setUserId(tempJsonComments.getString("userId"));
					comments.setUserPhoto(tempJsonComments
							.getString("userPhoto"));
					remark.getCommentsList().add(comments);
				}
				remark.setCommentCount(jsonCommentsArray.length());
			}
			remarkNewCommentsDetail.setRemark(remark);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return remarkNewCommentsDetail;
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

	public Remark getRemark() {
		return remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

}
