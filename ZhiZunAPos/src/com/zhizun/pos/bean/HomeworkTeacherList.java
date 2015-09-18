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

public class HomeworkTeacherList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = HomeworkTeacherList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<Homework> homeworkTeacherList = new ArrayList<Homework>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static HomeworkTeacherList parse(String _post) {
		Comments comments = null;
		Homework homework = null;
		StudentInfo studentInfo = null;
		Like like = null;
		HomeworkTeacherList homeworkTeacherList = new HomeworkTeacherList();
		JSONObject tempJsonRecvList;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonComments;
		JSONObject temJsonObjectLike;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			homeworkTeacherList.setStatus(jsonObject.getString("status"));
			homeworkTeacherList.setTimestamp(jsonObject.getLong("timestamp"));
			homeworkTeacherList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			homeworkTeacherList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			Log.i(TAG,
					"tempJsonDatasArray.length()="
							+ tempJsonDatasArray.length());
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					homework = new Homework();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					homework.setContent(tempJsonDatas.getString("content"));
					homework.setHomeworkId(tempJsonDatas
							.getString("homeworkId"));
					homework.setIsSendMsg(tempJsonDatas.getString("isSendMsg"));
					homework.setLastUpdateTime(tempJsonDatas
							.getString("lastUpdateTime"));
					homework.setOrgId(tempJsonDatas.getString("orgId"));
					homework.setOrgName(tempJsonDatas.getString("orgName"));
					homework.setSendMode(tempJsonDatas.getString("sendMode"));
					homework.setSendTime(tempJsonDatas.getString("sendTime"));
					homework.setStatus(tempJsonDatas.getString("status"));
					homework.setTaskTime(tempJsonDatas.getString("taskTime"));
					homework.setUserId(tempJsonDatas.getString("userId"));
					homework.setUserName(tempJsonDatas.getString("userName"));
					homework.setUserPhoto(tempJsonDatas.getString("userPhoto"));
					homework.setCurrenUserFav(tempJsonDatas
							.getBoolean("currenUserFav"));
					homework.setCurrenUserLike(false);
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
							for(StudentClass sc : homework.getStudentClassesList()){
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
								homework.getStudentClassesList().add(studentClass);
							}
							studentClass.getStudentInfoList().add(studentInfo);
						}
						homework.setReceiverCount(jsonRecvListArray.length());
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

							homework.getLikeList().add(like);
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
							comments.setRefId(tempJsonComments
									.getString("refId"));
							homework.getCommentsList().add(comments);
						}
						homework.setCommentCount(jsonCommentsArray.length());
					}
					homeworkTeacherList.getHomeworkTeacherList().add(homework);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return homeworkTeacherList;
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

	public List<Homework> getHomeworkTeacherList() {
		return homeworkTeacherList;
	}

	public void setHomeworkTeacherList(List<Homework> homeworkTeacherList) {
		this.homeworkTeacherList = homeworkTeacherList;
	}

}
