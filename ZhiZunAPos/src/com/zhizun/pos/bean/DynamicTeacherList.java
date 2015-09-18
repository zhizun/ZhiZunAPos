package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 在校动态 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class DynamicTeacherList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = DynamicTeacherList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<DynamicTeacher> dynamicTeacherList = new ArrayList<DynamicTeacher>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static DynamicTeacherList parse(String _post) {
		Comments comments = null;
		DynamicTeacher dynamicTeacher = null;
		Photo photo = null;
		StudentInfo studentInfo = null;
		Like like = null;
		DynamicTeacherList dynamicTeacherList = new DynamicTeacherList();
		JSONObject tempJsonRecvList;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonComments;
		JSONObject temJsonObjectPhoto;
		JSONObject temJsonObjectLike;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			dynamicTeacherList.setStatus(jsonObject.getString("status"));
			dynamicTeacherList.setTimestamp(jsonObject.getLong("timestamp"));
			dynamicTeacherList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			dynamicTeacherList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			Log.i(TAG,
					"tempJsonDatasArray.length()="
							+ tempJsonDatasArray.length());
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					dynamicTeacher = new DynamicTeacher();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					dynamicTeacher.setDynamicID(tempJsonDatas
							.getString("dynamicId"));
					dynamicTeacher.setSendUserName(tempJsonDatas
							.getString("userName"));
					dynamicTeacher.setSendUserId(tempJsonDatas
							.getString("userId"));
					dynamicTeacher.setSendTime(tempJsonDatas
							.getString("sendTime"));
					dynamicTeacher.setDynamicContent(tempJsonDatas
							.getString("content"));
					dynamicTeacher.setUserPhoto(tempJsonDatas
							.getString("userPhoto"));
					dynamicTeacher.setOrgId(tempJsonDatas.getString("orgId"));
					dynamicTeacher.setOrgName(tempJsonDatas
							.getString("orgName"));
					dynamicTeacher.setCurrenUserFav(tempJsonDatas
							.getBoolean("currenUserFav"));
					dynamicTeacher.setCurrenUserLike(false);

					JSONArray jsonRecvListArray = tempJsonDatas
							.getJSONArray("recvList");
					if (null != jsonRecvListArray
							&& jsonRecvListArray.length() > 0) {
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
							for(StudentClass sc : dynamicTeacher.getStudentClassesList()){
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
								dynamicTeacher.getStudentClassesList().add(studentClass);
							}
							studentClass.getStudentInfoList().add(studentInfo);
						}
						//dynamicTeacher.setStudentClass(studentClass);
						dynamicTeacher.setReceiverCount(jsonRecvListArray.length());
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
							dynamicTeacher.getPhotoList().add(photo);
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

							dynamicTeacher.getLikeList().add(like);
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
							comments.setDynamicID(tempJsonDatas
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
							comments.setUserPhoto(tempJsonComments
									.getString("userPhoto"));
							dynamicTeacher.getCommentsList().add(comments);
						}
						dynamicTeacher.setCommentCount(jsonCommentsArray
								.length());
					}
					dynamicTeacherList.getDynamicTeacherList().add(
							dynamicTeacher);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dynamicTeacherList;
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

	public List<DynamicTeacher> getDynamicTeacherList() {
		return dynamicTeacherList;
	}

	public void setDynamicTeacherList(List<DynamicTeacher> dynamicTeacherList) {
		this.dynamicTeacherList = dynamicTeacherList;
	}

	public String getDataCount() {
		return dataCount;
	}

	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}

}
