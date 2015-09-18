package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 在校点评 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class RemarkList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = RemarkList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<Remark> remarkList = new ArrayList<Remark>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static RemarkList parse(String _post) {
		Comments comments = null;
		Remark remark = null;
		Photo photo = null;
		Rating rating = null;
		Like like = null;
		StudentInfo studentInfo = null;
		RemarkList remarkList = new RemarkList();
		JSONObject tempJsonRecvList;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonComments;
		JSONObject temJsonObjectPhoto;
		JSONObject temJsonObjectLike;
		JSONObject temJsonObjectRating;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			remarkList.setStatus(jsonObject.getString("status"));
			remarkList.setStatusMessage(jsonObject.getString("statusMessage"));
			remarkList.setTimestamp(jsonObject.getLong("timestamp"));
			tempJsonData = jsonObject.getJSONObject("data");
			remarkList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");

			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					remark = new Remark();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					remark.setContent(tempJsonDatas.getString("content"));
					remark.setCurrenUserFav(tempJsonDatas
							.getBoolean("currenUserFav"));
					remark.setCurrenUserLike(false);
					remark.setLastUpdateTime(tempJsonDatas
							.getString("lastUpdateTime"));
					remark.setOrgId(tempJsonDatas.getString("orgId"));
					remark.setOrgName(tempJsonDatas.getString("orgName"));
					remark.setUserPhoto(tempJsonDatas.getString("userPhoto"));
					remark.setRemarkId(tempJsonDatas.getString("remarkId"));
					remark.setSendTime(tempJsonDatas.getString("sendTime"));
					remark.setUserId(tempJsonDatas.getString("userId"));
					remark.setUserName(tempJsonDatas.getString("userName"));

					JSONArray jsonRecvListArray = tempJsonDatas
							.getJSONArray("recvList");
					if (null != jsonRecvListArray
							&& jsonRecvListArray.length() > 0) {
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < jsonRecvListArray.length(); j++) {
							tempJsonRecvList = jsonRecvListArray
									.optJSONObject(j);
							
							studentInfo = new StudentInfo();
							studentInfo.setName(tempJsonRecvList
									.getString("stuName"));
							String stuClaId = tempJsonRecvList.getString("stuClaId");
							String stuClaName = tempJsonRecvList.getString("stuClaName");
							StudentClass studentClass = null;
							//找到已保存的班级列表
							for(StudentClass sc : remark.getStudentClassesList()){
								if(sc.getClaId()!=null&&sc.getClaId().equals(stuClaId)){
									studentClass = sc;
									break;
								}
							}
							//找不到班级，生成新的对象
							if(studentClass == null){
								studentClass = new StudentClass();
								studentClass.setClaId(stuClaId);
								studentClass.setName(stuClaName);
								remark.getStudentClassesList().add(studentClass);
							}
							studentClass.getStudentInfoList().add(studentInfo);
						}
						remark.setReceiverCount(jsonRecvListArray.length());
					}

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
							remark.getPhotoList().add(photo);
						}
					}
					JSONArray jsonRatingArray = tempJsonDatas
							.getJSONArray("remarkList");

					if (null != jsonRatingArray && jsonRatingArray.length() > 0) {
						for (int m = 0; m < jsonRatingArray.length(); m++) {
							temJsonObjectRating = jsonRatingArray
									.optJSONObject(m);
							rating = new Rating();
							rating.setRating(temJsonObjectRating
									.getString("rating"));
							rating.setRemarkItemId(temJsonObjectRating
									.getString("remarkItemId"));
							rating.setRemarkItemName(temJsonObjectRating
									.getString("remarkItemName"));
							remark.getRatingList().add(rating);
						}
					}
					JSONArray jsonLikeArray = tempJsonDatas
							.getJSONArray("fsiLikeList");
					if (null != jsonLikeArray && jsonLikeArray.length() > 0) {
						for (int y = 0; y < jsonLikeArray.length(); y++) {
							temJsonObjectLike = jsonLikeArray.optJSONObject(y);
							like = new Like();
							like.setIsCancel(temJsonObjectLike
									.getString("isCancel"));
							like.setUserId(temJsonObjectLike
									.getString("userId"));
							like.setUserAppe(temJsonObjectLike
									.getString("userAppe"));

							remark.getLikeList().add(like);
						}
					}
					JSONArray jsonCommentsArray = tempJsonDatas
							.getJSONArray("commentList");

					if (null != jsonCommentsArray
							&& jsonCommentsArray.length() > 0) {
						for (int k = 0; k < jsonCommentsArray.length(); k++) {
							tempJsonComments = jsonCommentsArray
									.optJSONObject(k);
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
							comments.setUserId(tempJsonComments
									.getString("userId"));
							comments.setUserPhoto(tempJsonComments
									.getString("userPhoto"));
							remark.getCommentsList().add(comments);
						}
						remark.setCommentCount(jsonCommentsArray.length());
					}
					remarkList.getRemarkList().add(remark);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return remarkList;
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

	public List<Remark> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<Remark> remarkList) {
		this.remarkList = remarkList;
	}

}
