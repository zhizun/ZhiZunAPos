package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Editable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.task.DeleteCommentTask;
import com.ch.epw.task.FavTask;
import com.ch.epw.task.LikeTask;
import com.ch.epw.task.SendCommentsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.DateUtil;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.SpUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.CommentsReply;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.LikeResult;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.Remark;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 在校点评家长ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewRemarkParentAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewRemarkParentAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<Remark> listItems; // 数据集合

	public ListViewRemarkParentAdapter(Context context, List<Remark> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
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
		return position;
	}

	protected void setFocusOn(Remark remark) {
		if (remark == null) {
			return;
		}
		remark.setFoucsOn(true);
		if (listItems.size() > 1) {
			for (Remark item : listItems) {
				if (!item.getRemarkId().equals(remark.getRemarkId())) {
					item.setFoucsOn(false);
				}
			}
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_zxdp_jz_item,
					null);

			holder.iv_list_common_title_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_common_title_logo);
			holder.tv_list_common_title_teachername = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_teachername);
			holder.tv_list_common_title_orgname = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_orgname);
			holder.tv_list_common_title_time = (TextView) convertView
					.findViewById(R.id.tv_list_common_title_time);
			holder.tv_list_zxdt_jz_item_content = (TextView) convertView
					.findViewById(R.id.tv_list_zxdt_jz_item_content);
			holder.ll_list_common_ssh_zan = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_zan);
			holder.ll_list_common_ssh_sc = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_sc);
			holder.ll_list_zxdt_jz_item_commentlist = (NoScrollListView) convertView
					.findViewById(R.id.ll_list_zxdt_jz_item_commentlist);
			holder.ll_list_common_ssh_fx = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_fx);
			holder.tv_list_common_ssh_commentcount = (TextView) convertView
					.findViewById(R.id.tv_list_common_ssh_commentcount);
			holder.et_list_common_pinglun_reply = (EditText) convertView
					.findViewById(R.id.et_list_zxdt_jz_item_comment_reply);
			holder.btn_list_zxdt_jz_item_comment_send = (Button) convertView
					.findViewById(R.id.btn_list_zxdt_jz_item_comment_send);
			holder.tv_list_common_ssh_sc = (TextView) convertView
					.findViewById(R.id.tv_list_common_ssh_sc);
			holder.ngv_list_zxdt_jz_item_imagelist = (NoScrollGridView) convertView
					.findViewById(R.id.ngv_list_zxdt_jz_item_imagelist);
			holder.tv_list_common_ssh_zan = (TextView) convertView
					.findViewById(R.id.tv_list_common_ssh_zan);
			holder.ll_list_commom_zan = (LinearLayout) convertView
					.findViewById(R.id.ll_list_commom_zan);
			holder.tv_list_common_zan = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan);
			holder.tv_list_common_zan_count = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan_count);
			holder.iv_list_common_ssh_zan = (ImageView) convertView
					.findViewById(R.id.iv_list_common_ssh_zan);// 赞图标
			holder.iv_list_common_ssh_sc = (ImageView) convertView
					.findViewById(R.id.iv_list_common_ssh_sc);
			holder.ll_list_zxdp_js_item_starlist = (NoScrollListView) convertView
					.findViewById(R.id.ll_list_zxdp_js_item_starlist);
			holder.ll_list_common_ssh_comment = (LinearLayout) convertView
					.findViewById(R.id.ll_list_common_ssh_comment);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.resetContentAndListener();

		final Remark remark = listItems.get(position);
		showPicture(remark.getUserPhoto(), holder.iv_list_common_title_logo,
				options);

		if (null != remark.getUserName() && !remark.getUserName().equals("")) {
			holder.tv_list_common_title_teachername.setText(remark
					.getUserName());
		} else {
			holder.tv_list_common_title_teachername.setText("");
		}
		if (null != remark.getOrgName() && !remark.getOrgName().equals("")) {
			holder.tv_list_common_title_orgname.setText("来自"
					+ remark.getOrgName());
		} else {
			holder.tv_list_common_title_orgname.setText("来自");
		}
		if (null != remark.getSendTime() && !remark.getSendTime().equals("")) {
			holder.tv_list_common_title_time.setText(DateUtil
					.getSimpleChineseDateTimeWithoutSec(remark.getSendTime(),
							"yyyy-MM-dd HH:mm:ss"));
		} else {
			holder.tv_list_common_title_time.setText("");
		}
		if (remark.getRatingList() != null && remark.getRatingList().size() > 0) {
			ListViewRatingAdapter listViewRatingAdapter = new ListViewRatingAdapter(
					context, remark.getRatingList());
			holder.ll_list_zxdp_js_item_starlist.setVisibility(View.VISIBLE);
			holder.ll_list_zxdp_js_item_starlist
					.setAdapter(listViewRatingAdapter);
		} else {
			holder.ll_list_zxdp_js_item_starlist.setVisibility(View.GONE);
//			holder.ll_list_zxdp_js_item_starlist.setAdapter(null);
		}
		if (null != remark.getContent() && !remark.getContent().equals("")) {
			holder.tv_list_zxdt_jz_item_content.setText(remark.getContent());
			holder.tv_list_zxdt_jz_item_content.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					BaseTools.copyText(context, holder.tv_list_zxdt_jz_item_content.getText().toString());
					return false;
				}
			});
		} else {
			holder.tv_list_zxdt_jz_item_content.setText("");
		}
		if (null != remark.getCommentCount()
				&& !remark.getCommentCount().toString().trim().equals("")) {
			holder.tv_list_common_ssh_commentcount.setText("（"
					+ remark.getCommentCount() + "）");
		} else {
			holder.tv_list_common_ssh_commentcount.setText("（0）");
		}
		if (remark.getCurrenUserFav()) {
			holder.tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_yes);
			holder.iv_list_common_ssh_sc
					.setImageResource(R.drawable.haveshoucang);
		} else {
			holder.tv_list_common_ssh_sc.setText(R.string.dynamic_favorite_no);
			holder.iv_list_common_ssh_sc.setImageResource(R.drawable.shoucang);
		}
		if (remark.getCurrenUserLike()) {
			holder.tv_list_common_ssh_zan.setText(R.string.text_islike_liked);
			holder.iv_list_common_ssh_zan.setImageResource(R.drawable.havelike);
		} else {
			holder.tv_list_common_ssh_zan.setText(R.string.text_islike_like);
			holder.iv_list_common_ssh_zan.setImageResource(R.drawable.like);
		}
		if (remark.getPhotoList() != null && remark.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, remark.getPhotoList());
			holder.ngv_list_zxdt_jz_item_imagelist.setVisibility(View.VISIBLE);
			holder.ngv_list_zxdt_jz_item_imagelist
					.setAdapter(listViewImagesAdapter);
		} else {
//			holder.ngv_list_zxdt_jz_item_imagelist.setAdapter(null);
			holder.ngv_list_zxdt_jz_item_imagelist.setVisibility(View.GONE);
		}
		holder.ngv_list_zxdt_jz_item_imagelist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ArrayList<String> imgsUrl = new ArrayList<String>();
						for (Photo photo : remark.getPhotoList()) {
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
		if (remark.getLikeList() != null && remark.getLikeList().size() > 0) {
			List<Like> list = remark.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				if (uiserInfo.getUserId().equals(like.getUserId())) {
					holder.tv_list_common_ssh_zan
							.setText(R.string.text_islike_liked);
					holder.iv_list_common_ssh_zan
							.setImageResource(R.drawable.havelike);
					remark.setCurrenUserLike(true);

				} else {
					holder.tv_list_common_ssh_zan
							.setText(R.string.text_islike_like);
					holder.iv_list_common_ssh_zan
							.setImageResource(R.drawable.like);
					remark.setCurrenUserLike(false);
				}
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

		holder.ll_list_common_ssh_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				holder.ll_list_common_ssh_zan.setClickable(false);
				new LikeTask(remark.getLikeList(), context, new TaskCallBack() {
					@Override
					public void onTaskFinshed() {
						remark.setCurrenUserLike(!remark.getCurrenUserLike());
						holder.ll_list_common_ssh_zan.setClickable(true);
						ListViewRemarkParentAdapter.this.notifyDataSetChanged();
					}
				}).execute(AppContext.getApp().getToken(),
						remark.getRemarkId(), Constant.COMMNETTYPE_ZXDP,
						remark.getCurrenUserLike() ? "1" : "0"); // 当前已赞，需取消(1)，否则不取消(0)
			}
		});
		holder.ll_list_common_ssh_fx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String imgUrlString = "";
				if (remark.getPhotoList().size() > 0) {
					imgUrlString = URLs.formatImgURL(remark.getPhotoList().get(0));
				}
				AppContext.getApp().showShare(context, 
						remark.getOrgId(),
						remark.getRemarkId(), 
						Constant.COMMNETTYPE_ZXDP,
						remark.getContent(),
						imgUrlString);
			}
		});
		if (remark.getCommentCount() > 0) {

			final ListViewCommentsAdapter commentsAdapter = new ListViewCommentsAdapter(
					context, remark.getCommentsList());
			holder.ll_list_zxdt_jz_item_commentlist.setAdapter(commentsAdapter);

			holder.ll_list_zxdt_jz_item_commentlist
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, final int position, long id) {

							String userId = AppContext.getApp()
									.getUserLoginSharedPre().getUserInfo()
									.getUserId();
							final Comments comment = (Comments) parent
									.getAdapter().getItem(position);
							String commentUser = comment.getUserId();
							if (userId != null && userId.equals(commentUser)) {
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
																			remark.getCommentsList()
																					.remove(comment);
																			ListViewRemarkParentAdapter.this
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
								remark.setReferComment(comment);
								holder.et_list_common_pinglun_reply
										.requestFocus();
								holder.et_list_common_pinglun_reply
										.setHint("回复 " + comment.getUserAppe());
								setFocusOn(remark);
							}
						}
					});

		} else {
			holder.ll_list_zxdt_jz_item_commentlist.setAdapter(null);
		}
		holder.ll_list_common_ssh_sc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				holder.ll_list_common_ssh_sc.setClickable(false);
				new FavTask(context, new TaskCallBack() {
					@Override
					public void onTaskFinshed() {
						remark.setCurrenUserFav(!remark.getCurrenUserFav());
						holder.ll_list_common_ssh_sc.setClickable(true);
						ListViewRemarkParentAdapter.this.notifyDataSetChanged();
					}
				}).execute(AppContext.getApp().getToken(),
						remark.getRemarkId(), Constant.COMMNETTYPE_ZXDP,
						remark.getCurrenUserFav() ? "1" : "0");
			}
		});

		// 点击评论按钮，清除评论对象
		holder.ll_list_common_ssh_comment
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						remark.resetInputStatus();
						setFocusOn(remark);
						holder.et_list_common_pinglun_reply.requestFocus();
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
					}
				});

		holder.btn_list_zxdt_jz_item_comment_send
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
						Comments referComment = remark.getReferComment(); // 引用的comment
						if (null == referComment) {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(remark.getRemarkId());
							commentsSend.setType(Constant.COMMNETTYPE_ZXDP);
							commentsSend.setReplyCommentId("");
							commentsSend.setTargetUserId(remark.getUserId());
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						} else {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(remark.getRemarkId());
							commentsSend.setReplyCommentId(referComment
									.getCommentID());
							commentsSend.setTargetUserId(remark
									.getUserId());
							commentsSend.setType(Constant.COMMNETTYPE_ZXDP);
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						}
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
						new SendCommentsTask(remark.getCommentsList(), context,
								new TaskCallBack() {
									@Override
									public void onTaskFinshed() {
										remark.resetInputStatus();
										ListViewRemarkParentAdapter.this
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
							setFocusOn(remark);
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
						if (remark.isFoucsOn()) {
							remark.setTypeingComment(typeingComment);
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
		if (remark.getReferComment() != null
				&& remark.getReferComment().getUserAppe() != null) {
			holder.et_list_common_pinglun_reply.setHint("回复 "
					+ remark.getReferComment().getUserAppe());
		}

		// 如果有保存的数据，恢复输入未提交的内容
		if (remark.getTypeingComment() != null
				&& !remark.getTypeingComment().equals("")) {
			holder.et_list_common_pinglun_reply.setText(remark
					.getTypeingComment());
		} else {
			holder.et_list_common_pinglun_reply.setText("");
			holder.et_list_common_pinglun_reply
					.setHint(R.string.text_comment_default_prompt);
		}

		// 如果是由于输入框输入法键盘导致的页面重绘，直接获取焦点；对于其他导致的重绘，重置各参数
		if (remark.isFoucsOn()) {
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
		ImageView iv_list_common_title_logo;// 图片logo
		TextView tv_list_common_title_teachername;// 老师名字
		TextView tv_list_common_title_orgname;// 机构名称
		TextView tv_list_common_title_time;// 发表时间
		TextView tv_list_zxdt_jz_item_content;// 内容
		LinearLayout ll_list_common_ssh_zan;// 赞
		TextView tv_list_common_ssh_zan;// 赞文字
		ImageView iv_list_common_ssh_zan;// 赞图标
		ImageView iv_list_common_ssh_sc;// 收藏图标
		LinearLayout ll_list_common_ssh_sc;// 收藏
		TextView tv_list_common_ssh_sc;// 收藏文字
		LinearLayout ll_list_common_ssh_fx;// 分享
		TextView tv_list_common_ssh_commentcount;// 评论数量
		NoScrollListView ll_list_zxdt_jz_item_commentlist;// 评论列表
		EditText et_list_common_pinglun_reply;// 回复；评论
		Button btn_list_zxdt_jz_item_comment_send;// 发送评论
		NoScrollGridView ngv_list_zxdt_jz_item_imagelist;// 图片列表
		LinearLayout ll_list_commom_zan;//
		LinearLayout ll_list_common_ssh_comment;//
		TextView tv_list_common_zan;
		TextView tv_list_common_zan_count;// 共多少人赞过

		NoScrollListView ll_list_zxdp_js_item_starlist;// 评分列表

		protected void resetContentAndListener() {
			if (tv_list_common_title_teachername != null) {
				tv_list_common_title_teachername.setText("");
			}
			if (tv_list_common_title_orgname != null) {
				tv_list_common_title_orgname.setText("");
			}
			if (tv_list_common_title_time != null) {
				tv_list_common_title_time.setText("");
			}
			if (tv_list_zxdt_jz_item_content != null) {
				tv_list_zxdt_jz_item_content.setText("");
			}
			if (tv_list_common_ssh_commentcount != null) {
				tv_list_common_ssh_commentcount.setText("");
			}
			if (ll_list_zxdt_jz_item_commentlist != null) {
				ll_list_zxdt_jz_item_commentlist.setOnItemClickListener(null);
				ll_list_zxdt_jz_item_commentlist.setAdapter(null);
			}
			if (ngv_list_zxdt_jz_item_imagelist != null) {
				ngv_list_zxdt_jz_item_imagelist.setOnItemClickListener(null);
				ngv_list_zxdt_jz_item_imagelist.setAdapter(null);
			}
			if (ll_list_zxdp_js_item_starlist != null) {
				ll_list_zxdp_js_item_starlist.setAdapter(null);
			}
			if (btn_list_zxdt_jz_item_comment_send != null) {
				btn_list_zxdt_jz_item_comment_send.setOnClickListener(null);
			}
		}

	}

}
