package com.zhizun.pos.bean;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhizun.pos.base.BaseBean;

import android.util.Log;

/**
 * 评论回复实体
 * 创建人：李林中
 * 创建日期：2014-12-4  上午11:56:10
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public class CommentsReply extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.Comments";
	private String status;// 整型 错误代码 1 成功
	private String statusMessage;// 字符 错误原因 出错时必须，成功时可以为空
	private Comments comments;
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
	public Comments getComments() {
		return comments;
	}
	public void setComments(Comments comments) {
		this.comments = comments;
	}

	/**
	 * 解析JSON得到在校动态列表
	 * 
	 * @author 李林中 2014-12-4 下午1:39:11
	 * @param _post
	 * @return
	 */
	public static CommentsReply parse(String _post) {
		Comments  comments=new Comments();
		
		CommentsReply commentsReply = new CommentsReply();
		JSONObject tempJsonData;
		
		try {
			JSONObject jsonObject = new JSONObject(_post);
			commentsReply.setStatus(jsonObject.getString("status"));
			commentsReply.setStatusMessage(jsonObject
					.getString("statusMessage"));
			tempJsonData = jsonObject.getJSONObject("data");
			
			comments.setCommentID(tempJsonData.getString("commentId"));
			comments.setCommentContent(tempJsonData.getString("content"));
			comments.setReplyCommentID(tempJsonData.getString("replyCommentId"));
			comments.setReplyUserID(tempJsonData.getString("replyUserId"));
			comments.setSf_read(tempJsonData.getString("isRead"));
			comments.setCommentTime(tempJsonData.getString("commentTime"));
			comments.setUserAppe(tempJsonData.getString("userAppe"));
			comments.setUserId(tempJsonData.getString("userId"));
			comments.setReplyUserAppe(tempJsonData.getString("replyUserAppe"));
			commentsReply.setComments(comments);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return commentsReply;
	}
}
