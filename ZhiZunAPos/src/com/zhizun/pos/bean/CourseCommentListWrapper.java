package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class CourseCommentListWrapper extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = OrgIntroWrapper.class.getName();

	private String status; // 0 成功
	private String statusMessage; // 错误原因
	private String dataCount;// 总数量

	List<CourseComment> courseCommentList;

	public static List parse(String resultInfo) throws IOException,
			AppException {

		return null;
	}

	public static List parse(JSONArray jsonArray) throws AppException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}

		List<CourseComment> courseCommentList = new ArrayList<CourseComment>();
		CourseComment courseComment = new CourseComment();
		courseComment.setUserName("山大王");
		courseComment
				.setContent("大王叫我来巡山，巡完南山巡北山哟。大王叫我来巡山，巡完南山巡北山哟。大王叫我来巡山，巡完南山巡北山哟。");
		courseComment.setCommentTime("2015-8-17");
		Rating rate = new Rating();
		rate.setRemarkItemId("0");
		rate.setRemarkItemName("综合评分");
		rate.setRating("3");
		courseComment.getRatingList().add(rate);
		courseCommentList.add(courseComment);
		return courseCommentList;
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

	public List<CourseComment> getCourseCommentList() {
		return courseCommentList;
	}

	public void setCourseCommentList(List<CourseComment> courseCommentList) {
		this.courseCommentList = courseCommentList;
	}
}