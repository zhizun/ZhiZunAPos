package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 收到的邀请 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class ReceiveMyInvitationList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.DynamicTeacher";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private String token;
	private String dataCount;// 邀请数量
	List<RecevieMyInvitation> recevieMyInvitationList = new ArrayList<RecevieMyInvitation>();

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static ReceiveMyInvitationList parse(String _post) {
		RecevieMyInvitation recevieMyInvitation = null;
		ReceiveMyInvitationList recevieMyInvitationList = new ReceiveMyInvitationList();
		StudentClass studentClass = null;
		JSONObject tempJsonData;
		JSONObject tempJsonDatas;
		JSONObject tempJsonInviteOrg;
		JSONObject tempJsonStudentInfo;
		JSONObject tempJsonstuClass;
		JSONObject tempJsonbmAddr;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			recevieMyInvitationList.setStatus(jsonObject.getString("status"));
			recevieMyInvitationList.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			recevieMyInvitationList.setDataCount(tempJsonData
					.getString("count"));
			JSONArray tempJsonDatasArray = tempJsonData.getJSONArray("datas");
			if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
				for (int i = 0; i < tempJsonDatasArray.length(); i++) {
					recevieMyInvitation = new RecevieMyInvitation();
					tempJsonDatas = tempJsonDatasArray.optJSONObject(i);
					recevieMyInvitation.setInviteComment(tempJsonDatas
							.getString("inviteComment"));
					recevieMyInvitation.setInvitedUserName(tempJsonDatas
							.getString("invitedUserName"));
					recevieMyInvitation.setInvitedUserPhone(tempJsonDatas
							.getString("invitedUserPhone"));
					recevieMyInvitation.setInviteStatus(tempJsonDatas
							.getString("inviteStatus"));
					recevieMyInvitation.setInviteTime(tempJsonDatas
							.getString("inviteTime"));
					recevieMyInvitation.setInviteUserName(tempJsonDatas
							.getString("inviteUserName"));
					recevieMyInvitation.setInviteUserPhone(tempJsonDatas
							.getString("inviteUserPhone"));
					recevieMyInvitation.setOrgInviteId(tempJsonDatas
							.getString("orgInviteId"));
					recevieMyInvitation.setInviteUserPhoto(tempJsonDatas
							.getString("inviteUserPhoto"));
					recevieMyInvitation.setInvitedUserPhoto(tempJsonDatas
							.getString("invitedUserPhoto"));
					recevieMyInvitation
							.setType(tempJsonDatas.getString("type"));
					tempJsonInviteOrg = tempJsonDatas
							.getJSONObject("inviteOrg");
					recevieMyInvitation.setInviteOrgName(tempJsonInviteOrg
							.getString("name"));
					recevieMyInvitation.setInviteOrgShortName(tempJsonInviteOrg
							.getString("shortName"));
					tempJsonbmAddr = tempJsonInviteOrg.getJSONObject("bmAddr");
					recevieMyInvitation.setInviteOrgAddress(tempJsonbmAddr
							.getString("addrInfo")
							+ tempJsonbmAddr.getString("addr"));
					recevieMyInvitation.setLogoPath(tempJsonInviteOrg
							.getString("logoPath"));
					recevieMyInvitation.setOrgId(tempJsonInviteOrg
							.getString("orgId"));

					if (!tempJsonDatas.isNull("studentInfo")) {
						tempJsonStudentInfo = tempJsonDatas
								.getJSONObject("studentInfo");
						recevieMyInvitation.setStuId(tempJsonStudentInfo
								.getString("stuId"));
						recevieMyInvitation.setStuName(tempJsonStudentInfo
								.getString("name"));
						recevieMyInvitation.setStuSex(tempJsonStudentInfo
								.getString("sex"));
						recevieMyInvitation.setStuPhotoPath(tempJsonStudentInfo
								.getString("photoPath"));
						recevieMyInvitation.setStuBirthDate(tempJsonStudentInfo
								.getString("birthDate"));
						recevieMyInvitation.setStuPhone(tempJsonStudentInfo
								.getString("phone"));
						recevieMyInvitation.setStuOwnOrgId(tempJsonStudentInfo
								.getString("ownOrgId"));
						recevieMyInvitation.setStuAge(tempJsonStudentInfo
								.getString("age"));
						recevieMyInvitation
								.setStuOwnOrgName(tempJsonStudentInfo
										.getString("ownOrgName"));
						recevieMyInvitation
								.setStuParentPhone(tempJsonStudentInfo
										.getString("parentPhone"));
					}

					// 所属班级
					if (!tempJsonDatas.isNull("stuClass")) {
						JSONArray tempJsonstuClassArray = tempJsonDatas
								.getJSONArray("stuClass");
						if (null != tempJsonstuClassArray
								&& tempJsonstuClassArray.length() > 0) {
							for (int j = 0; j < tempJsonstuClassArray.length(); j++) {
								studentClass = new StudentClass();
								tempJsonstuClass = tempJsonstuClassArray
										.optJSONObject(j);
								studentClass.setClaId(tempJsonstuClass
										.getString("claId"));
								studentClass.setName(tempJsonstuClass
										.getString("name"));
								recevieMyInvitation.getLisStudentClasses().add(
										studentClass);
							}

						}
					}

					recevieMyInvitationList.getRecevieMyInvitationList().add(
							recevieMyInvitation);

				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return recevieMyInvitationList;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<RecevieMyInvitation> getRecevieMyInvitationList() {
		return recevieMyInvitationList;
	}

	public void setRecevieMyInvitationList(
			List<RecevieMyInvitation> recevieMyInvitationList) {
		this.recevieMyInvitationList = recevieMyInvitationList;
	}

}
