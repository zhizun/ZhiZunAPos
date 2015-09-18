package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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

import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.task.DeleteCommentTask;
import com.ch.epw.task.DeleteCommonFsiTask;
import com.ch.epw.task.SendCommentsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.SpUtil;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentInfo;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 教师获取我的动态ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewDynamicTeacherAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewDynamicTeacherAdapter.class
			.getName();
	private Context context; // 运行上下文
	private List<DynamicTeacher> listItems; // 数据集合

	public ListViewDynamicTeacherAdapter(Context context,
			List<DynamicTeacher> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
	}

	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	protected void setFocusOn(DynamicTeacher dynamic) {
		if (dynamic == null) {
			return;
		}
		dynamic.setFoucsOn(true);
		if (listItems.size() > 1) {
			for (DynamicTeacher dyn : listItems) {
				if (!dyn.getDynamicID().equals(dynamic.getDynamicID())) {
					dyn.setFoucsOn(false);
				}
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_zxdt_js_item,
					null);

			holder.tv_list_jtzy_js_item_title_sendtime = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_sendtime);// 发送时间
			holder.tv_list_jtzy_js_item_title_senduser = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_senduser);// 发送人
			// holder.tv_list_jtzy_js_item_title_sendsms = (TextView)
			// convertView
			// .findViewById(R.id.tv_list_jtzy_js_item_title_sendsms);// 是否发送短信
			holder.tv_list_jtzy_js_item_title_receivecount = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_receivecount);// 共有多少接收人
			holder.tv_list_jtzy_js_item_title_short = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_short);// 接收人

			holder.tv_list_jtzy_js_item_title_btnishow = (TextView) convertView
					.findViewById(R.id.tv_list_jtzy_js_item_title_btnishow);// 更多
			holder.tv_notice_receivebox_detail_title_receiversinfo = (TextView) convertView
					.findViewById(R.id.tv_notice_receivebox_detail_title_receiversinfo);// 接受人详细

			holder.tv_list_zxdt_js_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_zxdt_js_item_content);
			holder.tv_list_js_ssh_commentcount = (TextView) convertView
					.findViewById(R.id.tv_list_js_ssh_commentcount);
			holder.ll_list_zxdt_js_item_commentlist = (NoScrollListView) convertView
					.findViewById(R.id.ll_list_zxdt_js_item_commentlist);
			holder.et_list_common_pinglun_reply = (EditText) convertView
					.findViewById(R.id.et_list_common_pinglun_reply);
			holder.btn_list_common_pinglun_send = (Button) convertView
					.findViewById(R.id.btn_list_common_pinglun_send);
			holder.ll_list_zxdt_js_ssh_delete = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_delete);
			holder.ngv_list_zxdt_js_item_imagelist = (NoScrollGridView) convertView
					.findViewById(R.id.ngv_list_zxdt_js_item_imagelist);
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
		final DynamicTeacher dynamicTeacher = (DynamicTeacher) getItem(position);
		final String loginUserId = AppContext.getApp().getUserLoginSharedPre()
				.getUserInfo().getUserId();
		final String sendUserId = dynamicTeacher.getSendUserId();

		holder.tv_list_jtzy_js_item_title_sendtime.setText("发送时间："
				+ dynamicTeacher.getSendTime());
		holder.tv_list_jtzy_js_item_title_senduser.setText("发送人："
				+ dynamicTeacher.getSendUserName());

		holder.tv_list_jtzy_js_item_title_receivecount.setText("共"
				+ dynamicTeacher.getReceiverCount() + "人");
		// holder.tv_list_jtzy_js_item_title_sendsms.setVisibility(View.GONE);

		StringBuilder text = new StringBuilder();
		List<String> recvListForShort = new ArrayList<String>();
		ArrayList<StudentClass> recvList = dynamicTeacher
				.getStudentClassesList();
		for (StudentClass studentClass : recvList) {
			text.append("<font color='#818181'>" + studentClass.getName()
					+ "</font><br>");
			List<StudentInfo> stuList = studentClass.getStudentInfoList();
			for (StudentInfo studentInfo : stuList) {
				text.append(studentInfo.getName() + " ");
				if (recvListForShort.size() < Constant.FSI_RECVERS_FOR_SHORT_LEN) {
					recvListForShort.add(studentInfo.getName());
				}
			}
			text.append("<br>");
		}
		holder.tv_notice_receivebox_detail_title_receiversinfo.setText(Html
				.fromHtml(text.toString()));

		if (recvListForShort.size() > 0) {
			holder.tv_list_jtzy_js_item_title_short.setText(StringUtils
					.listToString(recvListForShort) + "...");
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

		if (null!=dynamicTeacher
				.getDynamicContent() && !dynamicTeacher
				.getDynamicContent().equals("")) {
			holder.tv_list_zxdt_js_item_content.setVisibility(View.VISIBLE);
			holder.tv_list_zxdt_js_item_content.setText(dynamicTeacher
					.getDynamicContent());
		}else {
			holder.tv_list_zxdt_js_item_content.setVisibility(View.GONE);
		}

		holder.tv_list_js_ssh_commentcount.setText("（"
				+ dynamicTeacher.getCommentCount() + "）");
		holder.tv_list_zxdt_js_item_content.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				BaseTools.copyText(context, holder.tv_list_zxdt_js_item_content.getText().toString());
				return false;
			}
		});
		if (dynamicTeacher.getPhotoList() != null
				&& dynamicTeacher.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, dynamicTeacher.getPhotoList());
			holder.ngv_list_zxdt_js_item_imagelist.setVisibility(View.VISIBLE);
			holder.ngv_list_zxdt_js_item_imagelist
					.setAdapter(listViewImagesAdapter);
		} else {
			// holder.ngv_list_zxdt_js_item_imagelist.setAdapter(null);
			holder.ngv_list_zxdt_js_item_imagelist.setVisibility(View.GONE);
		}
		holder.ngv_list_zxdt_js_item_imagelist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ArrayList<String> imgsUrl = new ArrayList<String>();
						for (Photo photo : dynamicTeacher.getPhotoList()) {
							imgsUrl.add(URLs.URL_IMG_API_HOST
									+ photo.getThumbPath() + "/"
									+ photo.getThumbSaveName());
						}
						Intent intent = new Intent();
						intent.putStringArrayListExtra("infos", imgsUrl);
						intent.putExtra("imgPosition", position);
						intent.setClass(context, ImageShowActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				});
		if (dynamicTeacher.getLikeList() != null
				&& dynamicTeacher.getLikeList().size() > 0) {
			List<Like> list = dynamicTeacher.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
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
		if (dynamicTeacher.getCommentCount() > 0) {

			final ListViewCommentsAdapter commentsAdapter = new ListViewCommentsAdapter(
					context, dynamicTeacher.getCommentsList());
			holder.ll_list_zxdt_js_item_commentlist.setAdapter(commentsAdapter);
			holder.ll_list_zxdt_js_item_commentlist
					.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, final int position, long id) {
							final Comments comment = (Comments) parent
									.getAdapter().getItem(position);
							String commentUser = comment.getUserId();
							// 如果发表评论的用户是登录用户本身，点击评论进行删除；否则进行回复
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
																			dynamicTeacher
																					.getCommentsList()
																					.remove(comment);
																			ListViewDynamicTeacherAdapter.this
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
								dynamicTeacher.setReferComment(comment);

								holder.et_list_common_pinglun_reply
										.requestFocus();
								holder.et_list_common_pinglun_reply
										.setHint("回复 " + comment.getUserAppe());
								setFocusOn(dynamicTeacher);
							}
						}
					});
		} else {
			holder.ll_list_zxdt_js_item_commentlist.setAdapter(null);
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
														Constant.COMMNETTYPE_ZXDT,
														context,
														new TaskCallBack() {
															@Override
															public void onTaskFinshed() {
																listItems
																		.remove(position);
																ListViewDynamicTeacherAdapter.this
																		.notifyDataSetChanged();
															}
														})
														.execute(
																AppContext
																		.getApp()
																		.getToken(),
																dynamicTeacher
																		.getDynamicID());
											}
										}
									}, null);

						}
					});
		} else {
			holder.ll_list_zxdt_js_ssh_delete.setVisibility(View.INVISIBLE);
		}

		// 点击评论按钮，清除评论对象
		holder.ll_list_zxdt_js_ssh_comment
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dynamicTeacher.resetInputStatus();
						setFocusOn(dynamicTeacher);
						holder.et_list_common_pinglun_reply.requestFocus();
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
					}
				});

		holder.ll_list_zxdt_js_ssh_share
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String imgUrlString = "";
						if (dynamicTeacher.getPhotoList().size() > 0) {
							imgUrlString = URLs.formatImgURL(dynamicTeacher.getPhotoList().get(0));
						}
						AppContext.getApp().showShare(context, 
								dynamicTeacher.getOrgId(),
								dynamicTeacher.getDynamicID(), 
								Constant.COMMNETTYPE_ZXDT,
								dynamicTeacher.getDynamicContent(),
								imgUrlString);
					}
				});

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
						Comments referComment = dynamicTeacher
								.getReferComment(); // 引用的comment
						if (null == referComment) {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(dynamicTeacher.getDynamicID());
							commentsSend.setType(Constant.COMMNETTYPE_ZXDT);
							commentsSend.setReplyCommentId("");
							commentsSend.setTargetUserId(dynamicTeacher
									.getSendUserId());
							SharedPreferences sp = SpUtil.getInstance()
									.getSharePerference(context, "appToken");
							String token = sp.getString("token", "");
							commentsSend.setToken(token);
						} else {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(dynamicTeacher.getDynamicID());
							commentsSend.setReplyCommentId(referComment
									.getCommentID());
							commentsSend.setTargetUserId(dynamicTeacher.getSendUserId());
							commentsSend.setType(Constant.COMMNETTYPE_ZXDT);
							SharedPreferences sp = SpUtil.getInstance()
									.getSharePerference(context, "appToken");
							String token = sp.getString("token", "");
							commentsSend.setToken(token);
						}
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
						new SendCommentsTask(dynamicTeacher.getCommentsList(),
								context, new TaskCallBack() {
									@Override
									public void onTaskFinshed() {
										dynamicTeacher.resetInputStatus();
										ListViewDynamicTeacherAdapter.this
												.notifyDataSetChanged();
									}
								}).execute(commentsSend);
						((InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(v.getWindowToken(), 0);
					}
				});

		// 获得焦点时保存当前位置
		holder.et_list_common_pinglun_reply
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							setFocusOn(dynamicTeacher);
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

						if (dynamicTeacher.isFoucsOn()) {
							dynamicTeacher.setTypeingComment(typeingComment);
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
		if (dynamicTeacher.getReferComment() != null
				&& dynamicTeacher.getReferComment().getUserAppe() != null) {
			holder.et_list_common_pinglun_reply.setHint("回复 "
					+ dynamicTeacher.getReferComment().getUserAppe());
		}

		// 如果有保存的数据，回复输入未提交的内容
		if (dynamicTeacher.getTypeingComment() != null
				&& !dynamicTeacher.getTypeingComment().equals("")) {
			holder.et_list_common_pinglun_reply.setText(dynamicTeacher
					.getTypeingComment());
		} else {
			holder.et_list_common_pinglun_reply.setText("");
			holder.et_list_common_pinglun_reply
					.setHint(R.string.text_comment_default_prompt);
		}

		// 如果是由于输入框输入法键盘导致的页面重绘，直接获取焦点；对于其他导致的重绘，重置各参数
		if (dynamicTeacher.isFoucsOn()) {
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

		TextView tv_list_zxdt_js_item_content;// 内容
		TextView tv_list_js_ssh_commentcount;// 评论数量
		NoScrollListView ll_list_zxdt_js_item_commentlist;// 评论列表
		EditText et_list_common_pinglun_reply;// 回复；评论
		Button btn_list_common_pinglun_send;// 发送评论

		LinearLayout ll_list_zxdt_js_ssh_delete;// 删除动态
		LinearLayout ll_list_zxdt_js_ssh_share;// 分享
		LinearLayout ll_list_zxdt_js_ssh_comment;// 评论
		NoScrollGridView ngv_list_zxdt_js_item_imagelist;

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
			if (ngv_list_zxdt_js_item_imagelist != null) {
				ngv_list_zxdt_js_item_imagelist.setOnItemClickListener(null);
				ngv_list_zxdt_js_item_imagelist.setAdapter(null);
			}
			if (ll_list_zxdt_js_item_commentlist != null) {
				ll_list_zxdt_js_item_commentlist.setOnItemClickListener(null);
				ll_list_zxdt_js_item_commentlist.setAdapter(null);
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
