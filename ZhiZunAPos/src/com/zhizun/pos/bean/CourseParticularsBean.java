package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;
public class CourseParticularsBean extends BaseBean {

	/**
	 * 课程详情实体类
	 */
	private static final long serialVersionUID = 1L;

	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	private String courId;// 课程ID
	private String ownOrgId;// 机构ID
	private String photoPath;// 图片地址
	private String orgName;// 机构名字
	private String addr;// 机构地址
	private String name;// 课程名字
	private String courDesc;// 课程介绍
	private String arrange;//
	private String price;// 课程价格
	private String salesInfo;// 报名优惠
	private String olSalesInfo;// 报名优惠
	private String returnClassHour;// 是否退费
	private String signNum;// 报名人数
	private String viewNum;// 浏览次数
	private String phone;// 电话
	private String teachName;// 教师名字
	private String courseLength;// 课程长度
	private String commentNum;//评论数
	private List<TeacherInfo> courseTeacherListBean;
	private List<FriendsCommentListBean> courseCommentList;

	public static CourseParticularsBean parse(String _post) {
		CourseTeacherListBean courseTeacherListBean = null;
		CourseParticularsBean courseBeanList = new CourseParticularsBean();
		JSONObject tempJsonData;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			courseBeanList.setStatus(jsonObject.getString("status"));
			courseBeanList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				tempJsonData = jsonObject.getJSONObject("data");
				courseBeanList.setCourId(tempJsonData.getString("courId"));
				courseBeanList.setPhone(tempJsonData.getString("phone"));
				courseBeanList.setAddr(tempJsonData.getString("addr"));
				courseBeanList.setOrgName(tempJsonData.getString("orgName"));
				courseBeanList
						.setPhotoPath(tempJsonData.getString("photoPath"));
				courseBeanList.setOwnOrgId(tempJsonData.getString("ownOrgId"));
				courseBeanList
						.setTeachName(tempJsonData.getString("teachName"));
				courseBeanList.setCourDesc(tempJsonData.getString("courDesc"));
				courseBeanList.setArrange(tempJsonData.getString("arrange"));
				courseBeanList
						.setOlSalesInfo(tempJsonData.getString("olSalesInfo"));
				courseBeanList
				.setSalesInfo(tempJsonData.getString("salesInfo"));
				courseBeanList.setCourseLength(tempJsonData.optString("courseLength"));
				courseBeanList.setReturnClassHour(tempJsonData
						.getString("returnClassHour"));
				courseBeanList.setPrice(tempJsonData.getString("price"));
				courseBeanList.setViewNum(tempJsonData.getString("viewNum"));
				courseBeanList.setSignNum(tempJsonData.getString("signNum"));
				courseBeanList.setName(tempJsonData.getString("name"));
				courseBeanList.setCommentNum(tempJsonData.getString("commentNum"));

				if (!tempJsonData.isNull("teacherList")) {
					JSONArray tempJsonDatasArray = tempJsonData
							.getJSONArray("teacherList");
					courseBeanList
							.setCourseTeacherListBean(TeacherInfoListWrapper
									.parse(tempJsonDatasArray));
				}

				if (!tempJsonData.isNull("commentList")) {

					JSONArray commentJsonArray = tempJsonData
							.getJSONArray("commentList");
					courseBeanList.setCourseCommentList(FriendsCommentBean.parse(commentJsonArray));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (AppException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return courseBeanList;
	}

	public String getCourId() {
		return courId;
	}

	public void setCourId(String courId) {
		this.courId = courId;
	}

	public String getOwnOrgId() {
		return ownOrgId;
	}

	public void setOwnOrgId(String ownOrgId) {
		this.ownOrgId = ownOrgId;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourDesc() {
		return courDesc;
	}

	public void setCourDesc(String courDesc) {
		this.courDesc = courDesc;
	}

	public String getArrange() {
		return arrange;
	}

	public void setArrange(String arrange) {
		this.arrange = arrange;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSalesInfo() {
		return salesInfo;
	}

	public void setSalesInfo(String salesInfo) {
		this.salesInfo = salesInfo;
	}

	public String getOlSalesInfo() {
		return olSalesInfo;
	}

	public void setOlSalesInfo(String olSalesInfo) {
		this.olSalesInfo = olSalesInfo;
	}

	public String getReturnClassHour() {
		return returnClassHour;
	}

	public void setReturnClassHour(String returnClassHour) {
		this.returnClassHour = returnClassHour;
	}

	public String getSignNum() {
		return signNum;
	}

	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}

	public String getViewNum() {
		return viewNum;
	}

	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTeachName() {
		return teachName;
	}

	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}

	public List<TeacherInfo> getCourseTeacherListBean() {
		return courseTeacherListBean;
	}

	public void setCourseTeacherListBean(
			List<TeacherInfo> courseTeacherListBean) {
		this.courseTeacherListBean = courseTeacherListBean;
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

	public String getCourseLength() {
		return courseLength;
	}

	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
	}

	public List<FriendsCommentListBean> getCourseCommentList() {
		return courseCommentList;
	}

	public void setCourseCommentList(
			List<FriendsCommentListBean> courseCommentList) {
		this.courseCommentList = courseCommentList;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

}
