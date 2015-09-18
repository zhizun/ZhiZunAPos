package com.zhizun.pos.bean;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import com.zhizun.pos.AppException;
import com.zhizun.pos.base.BaseBean;

/**
 * 接口操作结果实体类(通用)
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class Result extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String status;// 0 成功
	private String statusMessage;// 错误原因
	
	private String commentId;

	/**
	 * @param resultInfo
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public static Result parse(String resultInfo) throws IOException,
			AppException {

		Result result = new Result();
		try {
			JSONObject jsonObject = new JSONObject(resultInfo);

			result.setStatus(jsonObject.getString("status"));
			result.setStatusMessage(jsonObject.getString("statusMessage"));
			if (!jsonObject.isNull("data")) {
				JSONObject jsData=jsonObject.getJSONObject("data");
				result.setCommentId(jsData.getString("commentId"));
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return result;
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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
