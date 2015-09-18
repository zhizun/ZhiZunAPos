package com.zhizun.pos.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

public class FavorList extends BaseBean{

	private static final long serialVersionUID = 1L;
	private static final String TAG = BabyInfoDetail.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	private int dataPage;		//当前页码
	private int pageCount;		//总页数

	private List<Favor> favList = new ArrayList<Favor>() ;
	
	public static FavorList parse(String resultInfo) throws IOException,
	AppException {
		FavorList favList = new FavorList();
		
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			favList.setStatus(jsonObject.getString("status"));
			favList.setStatusMessage(jsonObject.getString("statusMessage"));
			favList.setTimestamp(jsonObject.getLong("timestamp"));
			JSONObject jsonDataObject = jsonObject.optJSONObject("data");
			if(jsonDataObject== null ){
				return favList;
			}
			favList.setDataPage(jsonDataObject.getInt("pageCount"));
			favList.setPageCount(jsonDataObject.getInt("pageCount"));
			
			JSONArray jsonDatasArray = jsonDataObject.getJSONArray("datas");
			if( jsonDatasArray != null && jsonDatasArray.length() > 0){
				for(int k = 0; k < jsonDatasArray.length(); k++){
					JSONObject jsonFavor = jsonDatasArray.getJSONObject(k);
					Favor favor = new Favor();
					favor.setUserId(jsonFavor.getString("userId"));
					favor.setUserName(jsonFavor.getString("userName"));
					favor.setRefId(jsonFavor.getString("refId"));
					favor.setType(jsonFavor.getString("type"));
					favor.setContent(jsonFavor.getString("content"));
					favor.setPhotopath(jsonFavor.getString("photopath"));
					favor.setFavTime(jsonFavor.getString("lastUpdateTime"));
					favor.setOrgId(jsonFavor.getString("orgId"));
					favor.setOrgName(jsonFavor.getString("orgName"));

					JSONArray jsonPhotoListArray = jsonFavor.optJSONArray("photoList");
					if(jsonPhotoListArray != null && jsonPhotoListArray.length() > 0){
						for(int j = 0; j < jsonPhotoListArray.length(); j++ ){
							JSONObject jsonObjectPhoto = jsonPhotoListArray.optJSONObject(j);
							Photo photo = new Photo();
							photo.setPath(jsonObjectPhoto.getString("path"));
							photo.setPhotoId(jsonObjectPhoto.getString("photoId"));
							photo.setSaveName(jsonObjectPhoto.getString("saveName"));
							photo.setThumbPath(jsonObjectPhoto.getString("thumbPath"));
							photo.setThumbSaveName(jsonObjectPhoto.getString("thumbSaveName"));
							favor.getPhotoList().add(photo);
						}
					}
					
					JSONArray jsonRatingListArray = jsonFavor.optJSONArray("remarkList");
					if(jsonRatingListArray != null && jsonRatingListArray.length() > 0){
						for(int j = 0; j < jsonRatingListArray.length(); j++ ){
							JSONObject jsonObjectRating = jsonRatingListArray.optJSONObject(j);
							Rating rating = new Rating();
							rating.setRemarkItemId(jsonObjectRating.getString("remarkItemId"));
							rating.setRemarkItemName(jsonObjectRating.getString("remarkItemName"));
							rating.setRating(jsonObjectRating.getString("rating"));
							favor.getRatingList().add(rating);
						}
					}
					
					favList.getFavList().add(favor);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return favList;
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

	public List<Favor> getFavList() {
		return favList;
	}

	public void setFavList(List<Favor> favList) {
		this.favList = favList;
	}

	public int getDataPage() {
		return dataPage;
	}

	public void setDataPage(int dataPage) {
		this.dataPage = dataPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	
}
