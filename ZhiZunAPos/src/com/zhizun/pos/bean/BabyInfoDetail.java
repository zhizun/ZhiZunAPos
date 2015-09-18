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
 * 宝宝资料详细实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class BabyInfoDetail extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = BabyInfoDetail.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String photoPath;// 宝宝头像
	private String name;// 、姓名
	private String nickName;// 、乳名
	private String sex;// 、性别
	private String birthDate;// 、出生日期
	private String age;// 、年龄
	private String childId;

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	private Integer orgCount;// 、所属机构数量

	List<Org> orgList = new ArrayList<Org>();
	private List<InterestPri> interestPriList = new ArrayList<InterestPri>();// 、兴趣爱好

	

	/**
	 * @author 李林中
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static BabyInfoDetail parse(String resultInfo) throws IOException,
			AppException {
		Org org = null;
		InterestPri interestPri = null;
		InterestSec interestSec = null;
		BabyInfoDetail babyInfoDetail = new BabyInfoDetail();

		JSONObject jsonObjectOrg;
		JSONObject jsonObjectBmChild;
		JSONObject jsonObjectInterests;
		JSONArray jsonArrayItemArray;
		JSONObject jsonObjectItem;
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);
			babyInfoDetail.setStatus(jsonObject.getString("status"));
			babyInfoDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));
			babyInfoDetail.setStatusMessage(jsonObject
					.getString("statusMessage"));

			JSONObject jsonObjectData = jsonObject.getJSONObject("data");

			jsonObjectBmChild = jsonObjectData.getJSONObject("bmChild");

			babyInfoDetail.setBirthDate(jsonObjectBmChild
					.getString("birthDate"));
			babyInfoDetail.setChildId(jsonObjectBmChild.getString("childId"));
			babyInfoDetail.setName(jsonObjectBmChild.getString("name"));
			babyInfoDetail.setNickName(jsonObjectBmChild.getString("nickName"));
			babyInfoDetail.setPhotoPath(jsonObjectBmChild
					.getString("photoPath"));
			babyInfoDetail.setSex(jsonObjectBmChild.getString("sex"));
			babyInfoDetail.setAge(jsonObjectBmChild.getString("age"));
			JSONArray jsonArrayOrgArray = jsonObjectData
					.getJSONArray("omOrgList");
			babyInfoDetail.setOrgCount(jsonArrayOrgArray.length());

			for (int i = 0; i < jsonArrayOrgArray.length(); i++) {
				jsonObjectOrg = jsonArrayOrgArray.optJSONObject(i);
				org = new Org();
				org.setClaId(jsonObjectOrg.getString("claId"));
				org.setClaName(jsonObjectOrg.getString("claName"));
				org.setLogoPath(jsonObjectOrg.getString("logoPath"));
				org.setName(jsonObjectOrg.getString("name"));
				org.setOrgId(jsonObjectOrg.getString("orgId"));
				org.setShortName(jsonObjectOrg.getString("shortName"));
				babyInfoDetail.getOrgList().add(org);
			}

			JSONArray jsonArrayInterestsArray = jsonObjectData
					.getJSONArray("childInterests");
			for (int i = 0; i < jsonArrayInterestsArray.length(); i++) {
				jsonObjectInterests = jsonArrayInterestsArray.optJSONObject(i);
				interestPri = new InterestPri();

				interestPri.setCatId(jsonObjectInterests.getString("catId"));
				interestPri
						.setCatName(jsonObjectInterests.getString("catName"));

				jsonArrayItemArray = jsonObjectInterests
						.getJSONArray("itemList");
				for (int j = 0; j < jsonArrayItemArray.length(); j++) {
					jsonObjectItem = jsonArrayItemArray.optJSONObject(j);
					interestSec = new InterestSec();

					interestSec.setItemId(jsonObjectItem.getString("itemId"));
					interestSec.setItemName(jsonObjectItem
							.getString("itemName"));
					interestPri.getInterestSecList().add(interestSec);
				}
				babyInfoDetail.getInterestPriList().add(interestPri);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return babyInfoDetail;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(Integer orgCount) {
		this.orgCount = orgCount;
	}

	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public List<InterestPri> getInterestPriList() {
		return interestPriList;
	}

	public void setInterestPriList(List<InterestPri> interestPriList) {
		this.interestPriList = interestPriList;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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
}
