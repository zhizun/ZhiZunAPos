package com.ch.epw.bean.send;

import com.ch.epw.utils.EmojiFilter;
import com.zhizun.pos.base.BaseBean;

/**
 * 发送评论实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class CommentsSend extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = "com.ch.epw.bean.Comments";

	private String token;// 字符 用户token 必需
	private String refId;// 需要评论的动态ID
	private String type;// 评论的动态类型，0：在校动态，1：通知公告，2：家庭作业，3：考勤记录，4：在校点评，5：教学计划，6：班级食谱，7：家长心声
	private String content;// 评论内容
	private String replyCommentId;// 回复的评论ID，不为空则为回复某条评论
	private String targetUserId;// 需要评论的动态的发布人ID

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = EmojiFilter.filterEmoji(content);
	}

	public String getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

}
