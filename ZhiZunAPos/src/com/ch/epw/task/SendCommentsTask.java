package com.ch.epw.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.CommentsReply;

public class SendCommentsTask extends
		AsyncTask<CommentsSend, Void, CommentsReply> {
	AppException e;

	private List<Comments> commentList;
	private Context context;
	private TaskCallBack taskCallBack;

	public SendCommentsTask(List commentList, Context context,
			TaskCallBack callBackFunc) {
		this.commentList = commentList;
		this.context = context;
		this.taskCallBack = callBackFunc;
	}

	@Override
	protected CommentsReply doInBackground(CommentsSend... params) {
		CommentsReply commentsReply = null;
		try {
			commentsReply = AppContext.getApp().replyComments(params[0]);
		} catch (AppException e) {
			this.e = e;
			e.printStackTrace();
		}
		return commentsReply;
	}

	// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
	@Override
	protected void onPostExecute(CommentsReply result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getStatus().equals("0")) {
				Comments comment = new Comments();
				comment.setCommentID(result.getComments().getCommentID());
				comment.setUserId(result.getComments().getUserId());
				comment.setUserAppe(result.getComments().getUserAppe());
				comment.setCommentContent(result.getComments()
						.getCommentContent());
				comment.setReplyCommentID(result.getComments()
						.getReplyCommentID());
				comment.setReplyUserID(result.getComments().getReplyUserID());
				comment.setReplyUserAppe(result.getComments()
						.getReplyUserAppe());
				comment.setCommentTime(result.getComments().getCommentTime());
				if (commentList != null) {
					commentList.add(comment);
				}

				if (taskCallBack != null) {
					taskCallBack.onTaskFinshed();
				}
			} else {
				UIHelper.ToastMessage(context, result.getStatusMessage());
				return;
			}
		} else {
			if (null != e) {
				e.makeToast(context);
			}
			return;
		}
	}
}
