package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.base.BaseBean;

/**
 * 评分实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class Rating extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = Rating.class.getName();
	
    private String remarkItemId;
	private String rating;
	private String remarkItemName;
	public String getRemarkItemId() {
		return remarkItemId;
	}
	public void setRemarkItemId(String remarkItemId) {
		this.remarkItemId = remarkItemId;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRemarkItemName() {
		return remarkItemName;
	}
	public void setRemarkItemName(String remarkItemName) {
		this.remarkItemName = remarkItemName;
	}
	

	public static class Helper {
		
		public static final int COURSE_RATING_ITEM_NUM = 4;

		public static List<Rating> getCourseRatingFromJson(JSONObject ratingObject){
			List<Rating> ratingList = new ArrayList<Rating>();
			String[][] defRatingSettings = Constant.courseRatingOptionSettings;
			for (int k = 0; k < defRatingSettings.length; k++) {
				String[] tempSetting = defRatingSettings[k];
				Rating rating = new Rating();
				rating.setRemarkItemId(tempSetting[0]);
				rating.setRemarkItemName(tempSetting[1]);
				rating.setRating(tempSetting[2]);
				if (ratingObject != null) {
					rating.setRating(ratingObject.optString(
							rating.getRemarkItemId(), rating.getRating()));
				}
				ratingList.add(rating);
			}

			return ratingList;
		}

		public static String formatRatingAsPostData(List<Rating> ratingList) {
			StringBuffer strBuff = new StringBuffer();
			for (int k = 0; k < Helper.COURSE_RATING_ITEM_NUM; k++) {
				String rating = "";
				if (ratingList.size() >= k + 1) {
					rating = ratingList.get(k).getRating();
					if (rating.contains(".")) {
						rating = String.valueOf(Math.round(Double
								.parseDouble(rating)));
					}
				}
				strBuff.append(rating).append(",");
			}
			return strBuff.deleteCharAt(strBuff.length() - 1).toString();
		}
	}
}

