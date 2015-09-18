package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 朋友圈
 */
public class FriendsCommentBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String status;
	private String statusMessage;
	private String count;
	private List<FriendsCommentListBean> friendsCommentListBean = new ArrayList<FriendsCommentListBean>();

	public static FriendsCommentBean parse(String resultInfo) throws IOException,
			AppException {
		FriendsCommentBean friendsCommentBean = new FriendsCommentBean();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			friendsCommentBean.setStatus(jsonObject.getString("status"));
			friendsCommentBean.setStatusMessage(jsonObject.getString("statusMessage"));
			
			if (!jsonObject.isNull("data")) {
				JSONObject tempJsonData = jsonObject.getJSONObject("data");
				friendsCommentBean.setCount(tempJsonData.getString("count"));
				JSONArray commentListJsonArray = tempJsonData.getJSONArray("datas");
				friendsCommentBean
						.setFriendsCommentListBean(parse(commentListJsonArray));
				}

			} catch (JSONException e) {

			e.printStackTrace();
		}
		return friendsCommentBean;
	}

	public static List<FriendsCommentListBean> parse(
			JSONArray commentListJsonArray) throws JSONException {

		if (commentListJsonArray == null || commentListJsonArray.length() == 0) {
			return null;
		}

		List<FriendsCommentListBean> commentList = new ArrayList<FriendsCommentListBean>();
		for (int i = 0; i < commentListJsonArray.length(); i++) {
			JSONObject jsonObjectPic = commentListJsonArray.optJSONObject(i);
			FriendsCommentListBean friendsCommentListBean = new FriendsCommentListBean();
			friendsCommentListBean.setCommentId(jsonObjectPic
					.getString("commentId"));
			if (!jsonObjectPic.isNull("showUserName")) {
				friendsCommentListBean.setShowUserName(jsonObjectPic
						.getString("showUserName"));
			}else {
				friendsCommentListBean.setShowUserName(null);
			}
			if (!jsonObjectPic.isNull("userShortName")) {
				friendsCommentListBean.setUserShortName(jsonObjectPic
						.getString("userShortName"));
			}else {
				friendsCommentListBean.setUserShortName(null);
			}
			friendsCommentListBean.setUserPhoto(jsonObjectPic
					.getString("userPhoto"));
			if (!jsonObjectPic.isNull("userPhone")){
				friendsCommentListBean.setUserPhone(jsonObjectPic
						.getString("userPhone"));
			}else {
				friendsCommentListBean.setUserPhone(null);
			}
			friendsCommentListBean.setIsAnonymous(jsonObjectPic.getString("isAnonymous"));
			friendsCommentListBean.setComDate(jsonObjectPic
					.getString("comDate"));
			friendsCommentListBean.setOrgName(jsonObjectPic
					.getString("orgName"));
			friendsCommentListBean.setCourName(jsonObjectPic
					.getString("courName"));
			if (!jsonObjectPic.isNull("courId")) {
				friendsCommentListBean.setCourId(jsonObjectPic
						.getString("courId"));
			}else {
				friendsCommentListBean.setCourId(null);
			}
			if (!jsonObjectPic.isNull("orgId")) {
				friendsCommentListBean.setOrgId(jsonObjectPic
						.getString("orgId"));
			}else {
				friendsCommentListBean.setOrgId(null);
			}
			if (!jsonObjectPic.isNull("helpfulName")) {
				friendsCommentListBean.setHelpfulName(jsonObjectPic
						.optString("helpfulName"));
			}else {
				friendsCommentListBean.setHelpfulName(null);
			}
			
			if(!jsonObjectPic.isNull("org")){
				JSONObject orgObject = jsonObjectPic.getJSONObject("org");
				friendsCommentListBean.setLogoPath(orgObject.optString("logoPath"));
			}

			friendsCommentListBean.setRatingList(Rating.Helper
					.getCourseRatingFromJson(jsonObjectPic));
			friendsCommentListBean.setContent(jsonObjectPic
					.getString("content"));
			friendsCommentListBean.setHelpfulNum(jsonObjectPic
					.getString("helpfulNum"));
			friendsCommentListBean.setIsHelpful(jsonObjectPic
					.getString("isHelpful"));
			friendsCommentListBean.setDistance(jsonObjectPic
					.getString("distance"));
			friendsCommentListBean.setCurrenUserLike(false);
			friendsCommentListBean.setUserId(jsonObjectPic.getString("userId"));

			if (!jsonObjectPic.isNull("picList")) {
				try {
					friendsCommentListBean.setPicList(PhotoListWrapper
							.parse(jsonObjectPic.getJSONArray("picList")));
				} catch (AppException e) {
					e.printStackTrace();
				}
			}

			commentList.add(friendsCommentListBean);
		}

		return commentList;
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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<FriendsCommentListBean> getFriendsCommentListBean() {
		return friendsCommentListBean;
	}

	public void setFriendsCommentListBean(
			List<FriendsCommentListBean> friendsCommentListBean) {
		this.friendsCommentListBean = friendsCommentListBean;
	}

}
