package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class CourseListViewBean extends BaseBean {

	/**
	 * 课程搜索实体类
	 */
	private static final long serialVersionUID = 1L;
	private String status; // 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String dataCount;// 总数量
	private String categories;
	ArrayList<CourseRegions> courseRegionsList=new ArrayList<CourseRegions>();
	List<SortTreeNode> cateNodeList;

	ArrayList<CourseListItemList> courseItemList;

	public static CourseListViewBean parse(String _post) {
		CourseListViewBean courseBeanList = new CourseListViewBean();
		JSONObject tempJsonData;
		try {
			JSONObject jsonObject = new JSONObject(_post);
			courseBeanList.setStatus(jsonObject.getString("status"));
			courseBeanList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				tempJsonData = jsonObject.getJSONObject("data");
				courseBeanList.setDataCount(tempJsonData.getString("count"));
				JSONArray tempJsonDatasArray = tempJsonData
						.getJSONArray("datas");
				try {
					courseBeanList.setCourseItemList((ArrayList<CourseListItemList>) CourseListViewBean
							.parse(tempJsonDatasArray));
					
					if (!jsonObject.isNull("metaData")) {
						tempJsonData = jsonObject.getJSONObject("metaData");
						// courseBeanList.setCategories(tempJsonData.optString("categories"));
						JSONArray cateJsonArray = tempJsonData
								.optJSONArray("categories");
						if (cateJsonArray != null) {
							courseBeanList.setCateNodeList(SortTreeNodeWrapper
									.parse(cateJsonArray, null));
						}

						JSONArray tempJsonMetaDatasArray = tempJsonData.getJSONArray("regions");
						if (null != tempJsonMetaDatasArray
								&& tempJsonMetaDatasArray.length() > 0) {
							for (int i = 0; i < tempJsonMetaDatasArray.length(); i++) {
								CourseRegions courseRegions = new CourseRegions();
								JSONObject courseRegionsList = tempJsonMetaDatasArray.optJSONObject(i);
								courseRegions.setCount(courseRegionsList.getString("count"));
								courseRegions.setValue(courseRegionsList.getString("value"));
								courseRegions.setKey(courseRegionsList.getString("key"));
								courseBeanList.getCourseRegionsList().add(courseRegions);
							}
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return courseBeanList;
	}

	public static List<CourseListItemList> parse(JSONArray jsonArray)
			throws IOException, AppException {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}

		ArrayList<CourseListItemList> courseItemList = new ArrayList<CourseListItemList>();

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject JsonCourseListItem = jsonArray.optJSONObject(i);
				CourseListItemList courseItemBean = new CourseListItemList();
				
				String refundable = JsonCourseListItem.optString("refundable");
				if(refundable == null || "".equals(refundable)){
					int returnClassHour = JsonCourseListItem.optInt("returnClassHour",-1);
					if (returnClassHour<0) {
						refundable="0";
					}else if(returnClassHour==0){
						refundable="1";
					}else {
						refundable="2";
					}
				}
				courseItemBean.setRefundable(refundable);
				String signNume=JsonCourseListItem.optString("signNum");
				if(signNume == null || "".equals(signNume)){
					signNume = "";
				}
				courseItemBean.setSignNum(signNume);
				
				String courDesc = JsonCourseListItem.optString("desc");
				if(courDesc == null || "".equals(courDesc)){
					courDesc = JsonCourseListItem.optString("courDesc");
				}
				courseItemBean.setDesc(courDesc);

				if (!JsonCourseListItem.isNull("orgPhotoRelList")) {
					JSONArray tempJsonOrgPhotoArray = JsonCourseListItem.getJSONArray("orgPhotoRelList");
					List<Photo> phoList = PhotoListWrapper.parse(tempJsonOrgPhotoArray);
					if(phoList != null && phoList.size() > 0){
						Photo pho = phoList.get(0);
						courseItemBean.setThumbPath(pho.getThumbPath() + pho.getThumbSaveName());
						courseItemBean.setPath(pho.getPath() + pho.getSaveName());
					}
				}else{
					courseItemBean.setThumbPath(JsonCourseListItem
							.optString("thumbPath"));
					courseItemBean.setPath(JsonCourseListItem.optString("path"));
				}
				
				courseItemBean
						.setCourId(JsonCourseListItem.optString("courId"));
				courseItemBean
				.setCommentNum(JsonCourseListItem.optString("commentNum"));

				courseItemBean.setPrice(JsonCourseListItem.optInt("price"));
				courseItemBean.setName(JsonCourseListItem.getString("name"));
				courseItemBean.setOlSalesInfo(JsonCourseListItem
						.optInt("olSalesInfo"));
				courseItemBean.setDistance(JsonCourseListItem
						.optString("distance"));

				courseItemBean.setCourCode(JsonCourseListItem
						.optString("courCode"));
				// 机构
				courseItemBean.setPhone(JsonCourseListItem
						.optString("phone"));
				courseItemBean.setWebsite(JsonCourseListItem
						.optString("website"));
				courseItemBean.setIsAuthen(JsonCourseListItem
						.optString("isAuthen"));
				courseItemBean.setViewNum(JsonCourseListItem
						.optString("viewNum"));
				courseItemBean.setContact(JsonCourseListItem
						.optString("contact"));
				String orgId = JsonCourseListItem.optString("orgId");
				if(orgId == null || "".equals(orgId)){
					orgId = JsonCourseListItem.optString("ownOrgId");
				}
				courseItemBean.setOrgId(orgId);
				
				courseItemBean.setCategory(JsonCourseListItem
						.optString("category"));
				courseItemBean.setMemLevel(JsonCourseListItem
						.optString("memLevel"));
				courseItemBean.setOrgDesc(JsonCourseListItem
						.optString("orgDesc"));
				courseItemBean.setOrgCode(JsonCourseListItem
						.optString("orgCode"));
				courseItemBean.setShortName(JsonCourseListItem
						.optString("shortName"));
				courseItemBean.setLogoPath(JsonCourseListItem
						.optString("logoPath"));
				if (!JsonCourseListItem.isNull("cat")) {
					JSONArray catJsonDatasArray = JsonCourseListItem
							.getJSONArray("cat");
					for (int j = 0; j < catJsonDatasArray.length(); j++) {
						CourseCatBean courseCatBean = new CourseCatBean();
						JSONObject catJsonArray = catJsonDatasArray.optJSONObject(j);
						courseCatBean.setCatPriId(catJsonArray.optString("catPriId"));
						courseCatBean.setCatSecName(catJsonArray.optString("catSecName"));
						courseCatBean.setCatPriName(catJsonArray.optString("catPriName"));
						courseCatBean.setCatSecId(catJsonArray.optString("catSecId"));
						courseItemBean.getCourseCatBeanList().add(courseCatBean);
					}
				}
				if (!JsonCourseListItem.isNull("org")) {
					JSONObject orgJsonObject = JsonCourseListItem
							.getJSONObject("org");
					CourseOrgBean courseOrgBean = new CourseOrgBean();
					courseOrgBean.setOrgId(orgJsonObject.optString("orgId"));
					courseOrgBean.setOrgName(orgJsonObject.optString("orgName"));
					courseItemBean.setCourseOrgBean(courseOrgBean);
					courseItemBean.setOrgId(courseOrgBean.getOrgId());
				} else {
					courseItemBean.setCourseOrgBean(null);
				}
				if (!JsonCourseListItem.isNull("addr")) {
					JSONObject adrrJsonObject = JsonCourseListItem
							.getJSONObject("addr");
					
					CourseAddrBean courseAddrBean = new CourseAddrBean();
					courseAddrBean.setAddress(adrrJsonObject
							.getString("address"));
					courseAddrBean
							.setCounty(adrrJsonObject.getString("county"));
					courseAddrBean.setProvince(adrrJsonObject
							.getString("province"));
					courseAddrBean.setCity(adrrJsonObject.getString("city"));
					courseAddrBean.setCountry(adrrJsonObject
							.getString("country"));
					courseAddrBean.setCountyName(adrrJsonObject
							.getString("countyName"));
					courseAddrBean.setCityName(adrrJsonObject
							.getString("cityName"));
					courseAddrBean.setProvinceName(adrrJsonObject
							.getString("provinceName"));
					courseAddrBean
							.setStreet(adrrJsonObject.getString("street"));
					JSONObject locationJsonObject = adrrJsonObject.getJSONObject("location");
					String lon=locationJsonObject.optString("lon");
					String lat=locationJsonObject.optString("lat");
					if (lon==null || lon.equals("")||lon.startsWith("0")||lat==null || lat.equals("")||lat.startsWith("0")) {
						courseItemBean.setDistance("");
					}
					courseItemBean.setCourseAddrBean(courseAddrBean);
				} else {
					courseItemBean.setCourseAddrBean(null);
				}

				courseItemList.add(courseItemBean);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return courseItemList;
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

	public ArrayList<CourseListItemList> getCourseItemList() {
		return courseItemList;
	}

	public void setCourseItemList(ArrayList<CourseListItemList> courseItemList) {
		this.courseItemList = courseItemList;
	}

	public ArrayList<CourseRegions> getCourseRegionsList() {
		return courseRegionsList;
	}


	public void setCourseRegionsList(ArrayList<CourseRegions> courseRegionsList) {
		this.courseRegionsList = courseRegionsList;
	}

	public List<SortTreeNode> getCateNodeList() {
		return cateNodeList;
	}

	public void setCateNodeList(List<SortTreeNode> cateNodeList) {
		this.cateNodeList = cateNodeList;
	}

/*	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}*/

	
	

}
