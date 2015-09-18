package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 家长心声 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class VoiceList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = VoiceList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总动态数量

	List<Voice> voiceList = new ArrayList<Voice>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static VoiceList parse(String _post) {
		Comments comments = null;
		Voice voice = null;
		Photo photo = null;
		Like like = null;
		VoiceList voiceList = new VoiceList();
		JSONObject tempJsonRecvList;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonComments;
		JSONObject temJsonObjectPhoto;
		JSONObject temJsonObjectLike;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			voiceList.setStatus(jsonObject.getString("status"));
			voiceList.setStatusMessage(jsonObject.getString("statusMessage"));
			voiceList.setTimestamp(jsonObject.getLong("timestamp"));
			tempJsonData = jsonObject.getJSONObject("data");
			if(tempJsonData==null||tempJsonData.length()==0){
				voiceList.setDataCount("0");
				return voiceList;
			}
			//家终端与老师端接口不同，区别在于是否存在pageList节点
			if(tempJsonData.optJSONObject("pageList")!=null){
				tempJsonData = tempJsonData.optJSONObject("pageList");
			}

			voiceList.setDataCount(tempJsonData.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");

			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					voice = new Voice();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					voice.setContent(tempJsonDatas.getString("content"));
					voice.setLastUpdateTime(tempJsonDatas
							.getString("lastUpdateTime"));
					voice.setPhotopath(tempJsonDatas.getString("photopath"));
					voice.setSendTime(tempJsonDatas.getString("sendTime"));
					voice.setVoice_id(tempJsonDatas.getString("voice_id"));
					voice.setOrgId(tempJsonDatas.getString("orgId"));
					voice.setOrgName(tempJsonDatas.getString("orgName"));
					voice.setCurrenUserFav(tempJsonDatas
							.getBoolean("currenUserFav"));
					voice.setCurrenUserLike(false);
					voice.setUserId(tempJsonDatas.getString("userId"));
					voice.setUserName(tempJsonDatas.getString("userName"));

					JSONArray jsonRecvListArray = tempJsonDatas
							.getJSONArray("recvList");
					if (null != jsonRecvListArray
							&& jsonRecvListArray.length() > 0) {
						for(int t=0; t < jsonRecvListArray.length(); t++){
							tempJsonRecvList = jsonRecvListArray.optJSONObject(t);
							//家长端有该字段，老师端无此字段，通过optString获取字段值
							String teaName = tempJsonRecvList.optString("teaName");
							if(teaName!=null&&!teaName.equals("")){
								voice.setTeaName(teaName);
								break;
							}

							//从接收人列表中找出实际的发送人及发送人称谓
							String sendUserId = tempJsonRecvList.getString("userId");
							if(sendUserId!=null&&sendUserId.equals(voice.getUserId())){
								voice.setUserAppe(tempJsonRecvList.optString("stuName")+tempJsonRecvList.optString("appe"));
								voice.setUserDesc(tempJsonRecvList.optString("claName"));
								break;
							}
						}
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
							voice.getPhotoList().add(photo);
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

							voice.getLikeList().add(like);
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
							voice.getCommentsList().add(comments);
						}
						voice.setCommentCount(jsonCommentsArray.length());
					}
					voiceList.getVoiceList().add(voice);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return voiceList;
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

	public List<Voice> getVoiceList() {
		return voiceList;
	}

	public void setVoiceList(List<Voice> voiceList) {
		this.voiceList = voiceList;
	}

}
