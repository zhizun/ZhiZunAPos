package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.task.DeleteCommentTask;
import com.ch.epw.task.DeleteCommonFsiTask;
import com.ch.epw.task.SendCommentsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.Homework;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentInfo;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 在校点评 教师端ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewHomeworkTeacherAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewHomeworkTeacherAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<Homework> listItems; // 数据集合

	public ListViewHomeworkTeacherAdapter(Context context,
			List<Homework> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
	}

	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	protected void setFocusOn(Homework homework) {
		if (homework == null) {
			return;
		}
		homework.setFoucsOn(true);
		if (listItems.size() > 1) {
			for (Homework item : listItems) {
				if (!homework.getHomeworkId().equals(item.getHomeworkId())) {
					item.setFoucsOn(false);
				}
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_jtzy_js_item,
					null);

			holder.tv_list_jtzy_js_item_title_sendtime = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_sendtime);
			holder.tv_list_jtzy_js_item_title_senduser = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_senduser);

			// holder.tv_list_jtzy_js_item_title_sendsms = (TextView)
			// convertView
			// .findViewById(R.id.tv_list_jtzy_js_item_title_sendsms);

			holder.tv_notice_receivebox_detail_title_receiversinfo = (TextView) convertView
					.findViewById(R.id.tv_notice_receivebox_detail_title_receiversinfo);
			holder.tv_list_jtzy_js_item_title_btnishow = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_btnishow);

			holder.ll_list_jtzy_js_item_commentlist = (NoScrollListView) convertView
					.findViewById(R.id.ll_list_jtzy_js_item_commentlist);

			holder.tv_list_jtzy_js_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_content);
			holder.tv_list_jtzy_js_item_title_receivecount = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_receivecount);
			holder.tv_list_jtzy_js_item_title_short = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_short);

			holder.tv_list_js_ssh_commentcount = (TextView) convertView
					.findViewById(R.id.tv_list_js_ssh_commentcount);

			holder.et_list_common_pinglun_reply = (EditText) convertView
					.findViewById(R.id.et_list_common_pinglun_reply);
			holder.btn_list_common_pinglun_send = (Button) convertView
					.findViewById(R.id.btn_list_common_pinglun_send);
			holder.ll_list_zxdt_js_ssh_delete = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_delete);
			holder.ll_list_zxdt_js_ssh_share = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_share);
			holder.ll_list_zxdt_js_ssh_comment = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_comment);
			holder.ll_list_commom_zan = (LinearLayout) convertView
					.findViewById(R.id.ll_list_commom_zan);
			holder.tv_list_common_zan = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan);
			holder.tv_list_common_zan_count = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.resetContentAndListener();

		final Homework homework = listItems.get(position);
		final String loginUserId = AppContext.getApp().getUserLoginSharedPre()
				.getUserInfo().getUserId();
		final String sendUserId = homework.getUserId();

		if (homework.getStatus().equals(Constant.NOTICE_SEND_STATUS_WAIT)) {// 待发送
			holder.tv_list_jtzy_js_item_title_sendtime.setText("计划发送时间："
					+ homework.getTaskTime());
		} else if (homework.getStatus()
				.equals(Constant.NOTICE_SEND_STATUS_SENT)) {// 已发送
			holder.tv_list_jtzy_js_item_title_sendtime.setText("发送时间："
					+ homework.getSendTime());
		} else {
			holder.tv_list_jtzy_js_item_title_sendtime.setText("发送时间：");
		}

		if (homework.getUserName() != null
				&& !homework.getUserName().equals("")) {
			holder.tv_list_jtzy_js_item_title_senduser.setText("发送人："
					+ homework.getUserName());
		}

		if (null != homework.getContent() && !homework.getContent().equals("")) {
			holder.tv_list_jtzy_js_item_content.setText(homework.getContent());
			holder.tv_list_jtzy_js_item_content.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					BaseTools.copyText(context, holder.tv_list_jtzy_js_item_content.getText().toString());
					return false;
				}
			});
		} else {
			holder.tv_list_jtzy_js_item_content.setText(homework.getContent());
		}

		holder.tv_list_jtzy_js_item_title_receivecount.setText("共"
				+ homework.getReceiverCount() + "人");

		// if (homework.getIsSendMsg().equals(Constant.TYPE_YES)) {
		// holder.tv_list_jtzy_js_item_title_sendsms
		// .setVisibility(View.VISIBLE);
		// } else {
		// holder.tv_list_jtzy_js_item_title_sendsms.setVisibility(View.GONE);
		// }

		StringBuilder text = new StringBuilder();
		List<String> recvListForShort = new ArrayList<String>();
		ArrayList<StudentClass> recvList = homework.getStudentClassesList();
		for (StudentClass studentClass : recvList) {
			text.append("<font color='#818181'>" + studentClass.getName()
					+ "</font><br>");
			List<StudentInfo> stuList = studentClass.getStudentInfoList();
			for (StudentInfo studentInfo : stuList) {
				text.append(studentInfo.getName() + " ");
			}
			if (recvListForShort.size() < Constant.FSI_RECVERS_FOR_SHORT_LEN) {
				recvListForShort.add(studentClass.getName());
			}
			text.append("<br>");
		}
		holder.tv_notice_receivebox_detail_title_receiversinfo.setText(Html
				.fromHtml(text.toString()));

		if (recvListForShort.size() > 0) {
			holder.tv_list_jtzy_js_item_title_short.setText(StringUtils
					.listToString(recvListForShort));
		} else {
			holder.tv_list_jtzy_js_item_title_short.setText("");
		}

		holder.tv_list_jtzy_js_item_title_btnishow
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (holder.tv_list_jtzy_js_item_title_btnishow
								.getText()
								.toString()
								.equals(context.getResources().getString(
										R.string.show_more))) {
							holder.tv_list_jtzy_js_item_title_btnishow
									.setText(R.string.hide_more);
							holder.tv_notice_receivebox_detail_title_receiversinfo
									.setVisibility(View.VISIBLE);
							holder.tv_list_jtzy_js_item_title_short
									.setVisibility(View.GONE);

						} else {
							holder.tv_list_jtzy_js_item_title_btnishow
									.setText(R.string.show_more);
							holder.tv_notice_receivebox_detail_title_receiversinfo
									.setVisibility(View.GONE);
							holder.tv_list_jtzy_js_item_title_short
									.setVisibility(View.VISIBLE);
						}

					}
				});
		if (null != homework.getCommentCount()
				&& !homework.getCommentCount().toString().trim().equals("")) {
			holder.tv_list_js_ssh_commentcount.setText("（"
					+ homework.getCommentCount() + "）");
		}

		if (homework.getCommentCount() > 0) {
			final ListViewCommentsAdapter commentsAdapter = new ListViewCommentsAdapter(
					context, homework.getCommentsList());
			holder.ll_list_jtzy_js_item_commentlist.setAdapter(commentsAdapter);

			holder.ll_list_jtzy_js_item_commentlist
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, final int position, long id) {
							final Comments comment = (Comments) parent
									.getAdapter().getItem(position);
							String commentUser = comment.getUserId();
							if (loginUserId != null
									&& loginUserId.equals(commentUser)) {
								ActionSheet
										.showSheet(
												context,"","",
												new ActionSheet.OnActionSheetSelected() {
													@Override
													public void onClick(
															View view) {
														if (view.getId() == R.id.actionsheet_content) {
															new DeleteCommentTask(
																	context,
																	new TaskCallBack() {
																		@Override
																		public void onTaskFinshed() {
																			homework.getCommentsList()
																					.remove(comment);
																			ListViewHomeworkTeacherAdapter.this
																					.notifyDataSetChanged();
																		}
																	})
																	.execute(
																			AppContext
																					.getApp()
																					.getToken(),
																			comment.getCommentID());
														}
													}
												}, null);
							} else {
								homework.setReferComment(comment);
								holder.et_list_common_pinglun_reply
										.requestFocus();
								holder.et_list_common_pinglun_reply
										.setHint("回复 " + comment.getUserAppe());
								setFocusOn(homework);
							}
						}
					});
		} else {
			holder.ll_list_jtzy_js_item_commentlist.setAdapter(null);
		}

		// 根据登录用户是否是发表点评用户确定是否显示删除按钮
		if (loginUserId != null && loginUserId.equals(sendUserId)) {
			holder.ll_list_zxdt_js_ssh_delete.setVisibility(View.VISIBLE);
			holder.ll_list_zxdt_js_ssh_delete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							ActionSheet.showSheet(context,"","",
									new ActionSheet.OnActionSheetSelected() {
										@Override
										public void onClick(View view) {
											if (view.getId() == R.id.actionsheet_content) {
												new DeleteCommonFsiTask(
														Constant.COMMNETTYPE_JTZY,
														context,
														new TaskCallBack() {
															@Override
															public void onTaskFinshed() {
																listItems
																		.remove(position);
																ListViewHomeworkTeacherAdapter.this
																		.notifyDataSetChanged();
															}
														})
														.execute(
																AppContext
																		.getApp()
																		.getToken(),
																homework.getHomeworkId());
											}
										}
									}, null);

						}
					});
		} else {
			holder.ll_list_zxdt_js_ssh_delete.setVisibility(View.INVISIBLE);
		}

		holder.ll_list_zxdt_js_ssh_share
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						AppContext.getApp().showShare(context, 
								homework.getOrgId(),
								homework.getHomeworkId(), 
								Constant.COMMNETTYPE_JTZY,
								homework.getContent(),
								null);
					}
				});
		// 点击评论按钮，清除评论对象
		holder.ll_list_zxdt_js_ssh_comment
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						homework.resetInputStatus();
						setFocusOn(homework);
						holder.et_list_common_pinglun_reply.requestFocus();
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
					}
				});
		if (homework.getLikeList() != null && homework.getLikeList().size() > 0) {
			List<Like> list = homework.getLikeList();

			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				sBuffer.append(like.getUserAppe());
				sBuffer.append(",");
			}
			String str = sBuffer.toString();
			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1);
			}
			holder.ll_list_commom_zan.setVisibility(View.VISIBLE);
			holder.tv_list_common_zan.setText(str);
			holder.tv_list_common_zan_count.setText(" 共" + list.size() + "人");
		} else {
			holder.ll_list_commom_zan.setVisibility(View.GONE);
		}
		holder.btn_list_common_pinglun_send
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						String contentString = holder.et_list_common_pinglun_reply
								.getText().toString().trim();

						if (null == contentString || contentString.equals("")) {
							UIHelper.ToastMessage(context, "发送的内容不能为空！");
							return;
						}

						CommentsSend commentsSend = new CommentsSend();
						Comments referComment = homework.getReferComment(); // 引用的comment
						if (null == referComment) {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(homework.getHomeworkId());
							commentsSend.setType(Constant.COMMNETTYPE_JTZY);
							commentsSend.setReplyCommentId("");
							commentsSend.setTargetUserId(homework.getUserId());
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						} else {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(homework.getHomeworkId());
							commentsSend.setReplyCommentId(referComment
									.getCommentID());
							commentsSend.setTargetUserId(homework
									.getUserId());
							commentsSend.setType(Constant.COMMNETTYPE_JTZY);
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						}
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
						new SendCommentsTask(homework.getCommentsList(),
								context, new TaskCallBack() {
									@Override
									public void onTaskFinshed() {
										homework.resetInputStatus();
										ListViewHomeworkTeacherAdapter.this
												.notifyDataSetChanged();
									}
								}).execute(commentsSend);
						((InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(
										holder.et_list_common_pinglun_reply
												.getWindowToken(), 0);
					}
				});

		// 获得焦点时保存当前位置
		holder.et_list_common_pinglun_reply
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							setFocusOn(homework);
						}
						return false;
					}
				});

		// 输入完成时，保存当前的输入内容
		holder.et_list_common_pinglun_reply
				.addTextChangedListener(new TextWatcher() {
					@Override
					public void afterTextChanged(Editable s) {
						String typeingComment = holder.et_list_common_pinglun_reply
								.getText().toString();
						if (typeingComment.length() > Constant.COMMENT_MAX_LEN) {
							typeingComment = typeingComment.substring(0,
									Constant.COMMENT_MAX_LEN);
							UIHelper.ToastMessage(context, "输入的内容不能大于"
									+ Constant.COMMENT_MAX_LEN + "个字符");
							holder.et_list_common_pinglun_reply
									.setText(typeingComment);
						}
						if (homework.isFoucsOn()) {
							homework.setTypeingComment(typeingComment);
						}
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					}

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}
				});

		// 如果当前动态动态正引用一条评论回复
		if (homework.getReferComment() != null
				&& homework.getReferComment().getUserAppe() != null) {
			holder.et_list_common_pinglun_reply.setHint("回复 "
					+ homework.getReferComment().getUserAppe());
		}

		// 如果有保存的数据，回复输入未提交的内容
		if (homework.getTypeingComment() != null
				&& !homework.getTypeingComment().equals("")) {
			holder.et_list_common_pinglun_reply.setText(homework
					.getTypeingComment());
		} else {
			holder.et_list_common_pinglun_reply.setText("");
			holder.et_list_common_pinglun_reply
					.setHint(R.string.text_comment_default_prompt);
		}

		// 如果是由于输入框输入法键盘导致的页面重绘，直接获取焦点；对于其他导致的重绘，重置各参数
		if (homework.isFoucsOn()) {
			// 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
			holder.et_list_common_pinglun_reply.requestFocus();
			if (holder.et_list_common_pinglun_reply.getText().length() > 0) {
				holder.et_list_common_pinglun_reply
						.setSelection(holder.et_list_common_pinglun_reply
								.getText().length());
			}
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_jtzy_js_item_title_sendtime;// 发送时间
		TextView tv_list_jtzy_js_item_title_senduser;// 发送人
		// TextView tv_list_jtzy_js_item_title_sendsms;// 是否发送短信
		TextView tv_list_jtzy_js_item_title_receivecount;// 共有多少接收人
		TextView tv_list_jtzy_js_item_title_short;// 接收人

		TextView tv_list_jtzy_js_item_title_btnishow;// 更多
		TextView tv_notice_receivebox_detail_title_receiversinfo;// 接受人详细

		TextView tv_list_jtzy_js_item_content;// 作业内容
		TextView tv_list_js_ssh_commentcount;// 评论数量
		LinearLayout ll_list_zxdt_js_ssh_delete;// 删除动态
		LinearLayout ll_list_zxdt_js_ssh_share;// 分享
		LinearLayout ll_list_zxdt_js_ssh_comment;// 评论

		NoScrollListView ll_list_jtzy_js_item_commentlist;// 评论列表
		EditText et_list_common_pinglun_reply;// 回复；评论
		Button btn_list_common_pinglun_send;// 发送评论

		LinearLayout ll_list_commom_zan;//
		TextView tv_list_common_zan;
		TextView tv_list_common_zan_count;// 共多少人赞过

		protected void resetContentAndListener() {
			if (tv_list_jtzy_js_item_title_sendtime != null) {
				tv_list_jtzy_js_item_title_sendtime.setText("");
			}
			if (tv_list_jtzy_js_item_title_senduser != null) {
				tv_list_jtzy_js_item_title_senduser.setText("");
			}
			// if (tv_list_jtzy_js_item_title_sendsms != null) {
			// tv_list_jtzy_js_item_title_sendsms.setText("");
			// }
			if (tv_list_jtzy_js_item_title_receivecount != null) {
				tv_list_jtzy_js_item_title_receivecount.setText("");
			}
			if (tv_list_jtzy_js_item_title_short != null) {
				tv_list_jtzy_js_item_title_short.setText("");
			}
			if (tv_list_jtzy_js_item_title_btnishow != null) {
				tv_list_jtzy_js_item_title_btnishow.setOnClickListener(null);
			}
			if (ll_list_jtzy_js_item_commentlist != null) {
				ll_list_jtzy_js_item_commentlist.setOnItemClickListener(null);
				ll_list_jtzy_js_item_commentlist.setAdapter(null);
			}
			if (ll_list_zxdt_js_ssh_delete != null) {
				ll_list_zxdt_js_ssh_delete.setOnClickListener(null);
			}
			if (ll_list_zxdt_js_ssh_share != null) {
				ll_list_zxdt_js_ssh_share.setOnClickListener(null);
			}
			if (btn_list_common_pinglun_send != null) {
				btn_list_common_pinglun_send.setOnClickListener(null);
			}
		}
	}

}
