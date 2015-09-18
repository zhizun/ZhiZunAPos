package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 宝宝信息 教师端 创建人：李林中 创建日期：2014-12-4 上午11:47:16 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class UserRoleList extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = UserRoleList.class.getName();
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空

	List<UserRole> userRoleList = new ArrayList<UserRole>();

	/**
	 * 解析JSON得到角色列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static UserRoleList parse(String _post) {

		UserRole userRole = null;
		UserRoleList userRoleList = new UserRoleList();
		JSONObject tempJsonUserOrgan = null;

		try {
			JSONObject jsonObject = new JSONObject(_post);
			userRoleList.setStatus(jsonObject.getString("status"));
			userRoleList
					.setStatusMessage(jsonObject.getString("statusMessage"));

			JSONObject tempJsonData = jsonObject.getJSONObject("data");

			JSONArray tempJsonDatasArray = tempJsonData
					.getJSONArray("userOrgan");
			userRoleList.setUserRoleList(postRoleList(tempJsonDatasArray));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userRoleList;
	}

	public static ArrayList<UserRole> postRoleList(JSONArray tempJsonDatasArray) throws JSONException {
		UserRole userRole;
		JSONObject tempJsonUserOrgan;
		ArrayList<UserRole> userRoleListNew=new ArrayList<UserRole>();
		if (null != tempJsonDatasArray && tempJsonDatasArray.length() > 0) {
			for (int i = 0; i < tempJsonDatasArray.length(); i++) {
				userRole = new UserRole();
				tempJsonUserOrgan = tempJsonDatasArray.optJSONObject(i);
				userRole.setImagePath(tempJsonUserOrgan
						.getString("imagePath"));
				userRole.setOrgId(tempJsonUserOrgan.getString("orgId"));
				userRole.setOrgName(tempJsonUserOrgan.getString("orgName"));
				userRole.setRoleId(tempJsonUserOrgan.getString("roleId"));
				userRole.setRoleName(tempJsonUserOrgan
						.getString("roleName"));
				userRoleListNew.add(userRole);
			}
		}
		return userRoleListNew;
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

	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

}
