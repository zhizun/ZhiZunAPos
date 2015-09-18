package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
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
import com.ch.epw.task.LikeTask;
import com.ch.epw.task.SendCommentsTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.Like;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.Voice;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 家长心声 教师端ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewVoiceTeacherAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewVoiceTeacherAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<Voice> listItems; // 数据集合

	public ListViewVoiceTeacherAdapter(Context context, List<Voice> listItems) {
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
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	protected void setFocusOn(Voice voice){
		if(voice == null){
			return;
		}
		voice.setFoucsOn(true);
		if(listItems.size() > 1){
			for(Voice item : listItems){
				if(!item.getVoice_id().equals(voice.getVoice_id())){
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
			convertView = View.inflate(context, R.layout.list_jzxs_js_item,
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
			holder.ll_list_zxdt_js_ssh_zan = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_zan);
			holder.ll_list_zxdt_jz_item_commentlist = (NoScrollListView) convertView
					.findViewById(R.id.ll_list_zxdt_jz_item_commentlist);
			holder.ll_list_zxdt_js_ssh_share = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_share);
			holder.tv_list_zxdt_js_shh_zan = (TextView) convertView
					.findViewById(R.id.tv_list_zxdt_js_shh_zan);
			holder.et_list_common_pinglun_reply = (EditText) convertView
					.findViewById(R.id.et_list_zxdt_jz_item_comment_reply);
			holder.btn_list_zxdt_jz_item_comment_send = (Button) convertView
					.findViewById(R.id.btn_list_zxdt_jz_item_comment_send);
			holder.tv_list_js_ssh_commentcount = (TextView) convertView
					.findViewById(R.id.tv_list_js_ssh_commentcount);
			holder.ngv_list_zxdt_jz_item_imagelist = (NoScrollGridView) convertView
					.findViewById(R.id.ngv_list_zxdt_jz_item_imagelist);
			holder.ll_list_commom_zan = (LinearLayout) convertView
					.findViewById(R.id.ll_list_commom_zan);
			holder.tv_list_common_zan = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan);
			holder.tv_list_common_zan_count = (TextView) convertView
					.findViewById(R.id.tv_list_common_zan_count);
			holder.iv_list_zxdt_js_shh_zan_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_zxdt_js_shh_zan_logo);// 赞图标
			holder.ll_list_zxdt_js_ssh_comment = (LinearLayout) convertView
					.findViewById(R.id.ll_list_zxdt_js_ssh_comment);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.resetContentAndListener();

		final Voice voice = listItems.get(position);
		final String loginUserId = AppContext.getApp().getUserLoginSharedPre().getUserInfo().getUserId();
		final String sendUserId = voice.getUserId();

		showPicture(voice.getPhotopath(),
				holder.iv_list_common_title_logo, options);

//		if (null != voice.getUserName() && !voice.getUserName().equals("")) {
//			holder.tv_list_common_title_teachername
//					.setText(voice.getUserName());
//		} else {
//			holder.tv_list_common_title_teachername.setText("");
//		}
		holder.tv_list_common_title_teachername.setText(voice.getUserAppe());
//		if (null != voice.getOrgName() && !voice.getOrgName().equals("")) {
//			holder.tv_list_common_title_orgname.setText("来自"
//					+ voice.getOrgName());
//		} else {
//			holder.tv_list_common_title_orgname.setText("来自");
//		}
		if(voice.getUserDesc()!=null&&!voice.getUserAppe().equals("")){
			holder.tv_list_common_title_orgname.setText("("+voice.getUserDesc()+")");
		}
		if (null != voice.getSendTime() && !voice.getSendTime().equals("")) {
			holder.tv_list_common_title_time.setText("发送时间："
					+ voice.getSendTime());
		} else {
			holder.tv_list_common_title_time.setText("发送时间：");
		}
		if (null != voice.getContent() && !voice.getContent().equals("")) {
			holder.tv_list_zxdt_jz_item_content.setVisibility(View.VISIBLE);
			holder.tv_list_zxdt_jz_item_content.setText(voice.getContent());
			holder.tv_list_zxdt_jz_item_content.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					BaseTools.copyText(context, holder.tv_list_zxdt_jz_item_content.getText().toString());
					return false;
				}
			});
		} else {
//			holder.tv_list_zxdt_jz_item_content.setText("");
			holder.tv_list_zxdt_jz_item_content.setVisibility(View.GONE);
		}
		if (null != voice.getCommentCount()
				&& !voice.getCommentCount().toString().trim().equals("")) {
			holder.tv_list_js_ssh_commentcount.setText("（"
					+ voice.getCommentCount() + "）");
		} else {
			holder.tv_list_js_ssh_commentcount.setText("（0）");
		}

		if (voice.getCurrenUserLike()) {
			holder.tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_liked);
			holder.iv_list_zxdt_js_shh_zan_logo
					.setImageResource(R.drawable.havelike);
		} else {
			holder.tv_list_zxdt_js_shh_zan.setText(R.string.text_islike_like);
			holder.iv_list_zxdt_js_shh_zan_logo
					.setImageResource(R.drawable.like);
		}
		if (voice.getPhotoList() != null && voice.getPhotoList().size() > 0) {
			GridViewImagesAdapter listViewImagesAdapter = new GridViewImagesAdapter(
					context, voice.getPhotoList());
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
						for (Photo photo : voice.getPhotoList()) {
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
		Log.i(TAG, "dynamicTeacher.getLikeList() ="
				+ voice.getLikeList().size());
		if (voice.getLikeList() != null && voice.getLikeList().size() > 0) {
			List<Like> list = voice.getLikeList();
			UserInfo uiserInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			StringBuffer sBuffer = new StringBuffer();
			for (Like like : list) {
				if (uiserInfo.getUserId().equals(like.getUserId())) {
					holder.tv_list_zxdt_js_shh_zan
							.setText(R.string.text_islike_liked);
					holder.iv_list_zxdt_js_shh_zan_logo
							.setImageResource(R.drawable.havelike);
					voice.setCurrenUserLike(true);
				} else {
					holder.tv_list_zxdt_js_shh_zan
							.setText(R.string.text_islike_like);
					holder.iv_list_zxdt_js_shh_zan_logo
							.setImageResource(R.drawable.like);
					voice.setCurrenUserLike(false);
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

		holder.ll_list_zxdt_js_ssh_zan
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//禁用点赞图标
						holder.ll_list_zxdt_js_ssh_zan.setClickable(false);
						new LikeTask(voice.getLikeList(),context,new TaskCallBack(){
							@Override
							public void onTaskFinshed() {
								voice.setCurrenUserLike(!voice.getCurrenUserLike());
								holder.ll_list_zxdt_js_ssh_zan.setClickable(true);
								ListViewVoiceTeacherAdapter.this.notifyDataSetChanged();
							}
						}).execute(AppContext.getApp().getToken(), 
								voice.getVoice_id(),
								Constant.COMMNETTYPE_JZXS, 
								voice.getCurrenUserLike()?"1":"0");	//当前已赞，需取消(1)，否则不取消(0)
					}
				});
		holder.ll_list_zxdt_js_ssh_share
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String imgUrlString = "";
						if (voice.getPhotoList().size() > 0) {
							imgUrlString = URLs.formatImgURL(voice.getPhotoList().get(0));
						}
						AppContext.getApp().showShare(context, 
								voice.getOrgId(),
								voice.getVoice_id(), 
								Constant.COMMNETTYPE_JZXS,
								voice.getContent(),
								imgUrlString);
					}
				});
		//点击评论按钮，清除评论对象
		holder.ll_list_zxdt_js_ssh_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				voice.resetInputStatus();
				setFocusOn(voice);
				holder.et_list_common_pinglun_reply.requestFocus();
				holder.et_list_common_pinglun_reply.setText("");
				holder.et_list_common_pinglun_reply.setHint(R.string.text_comment_default_prompt);
			}
		});
		
		if (voice.getCommentCount() > 0) {
			final ListViewCommentsAdapter commentsAdapter = new ListViewCommentsAdapter(
					context, voice.getCommentsList());
			holder.ll_list_zxdt_jz_item_commentlist.setAdapter(commentsAdapter);

			holder.ll_list_zxdt_jz_item_commentlist
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, final int position, long id) {
							final Comments comment = (Comments)parent.getAdapter().getItem(position);
							String commentUser = comment.getUserId();
							if(loginUserId!=null&&loginUserId.equals(commentUser)){
								ActionSheet.showSheet(context, "","",new ActionSheet.OnActionSheetSelected() {
									@Override
									public void onClick(View view) {
										if (view.getId() == R.id.actionsheet_content) {
											new DeleteCommentTask(context, 
													new TaskCallBack(){
														@Override
														public void onTaskFinshed() {
															voice.getCommentsList().remove(comment);
															ListViewVoiceTeacherAdapter.this.notifyDataSetChanged();
														}
												}).execute(AppContext.getApp().getToken(),
																comment.getCommentID());
										}
									}}, null);
							}else{
								voice.setReferComment(comment);
								holder.et_list_common_pinglun_reply.requestFocus();
								holder.et_list_common_pinglun_reply
										.setHint("回复 "	+ comment.getUserAppe());
								setFocusOn(voice);
							}
						}
					});

		} else {
			holder.ll_list_zxdt_jz_item_commentlist.setAdapter(null);
		}

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
						Comments referComment = voice.getReferComment();	//引用的comment
						if (null == referComment) {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(voice.getVoice_id());
							commentsSend.setType(Constant.COMMNETTYPE_JZXS);
							commentsSend.setReplyCommentId("");
							commentsSend.setTargetUserId(voice.getUserId());
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						} else {
							commentsSend.setContent(contentString);
							commentsSend.setRefId(voice.getVoice_id());
							commentsSend.setReplyCommentId(referComment.getCommentID());
							commentsSend.setTargetUserId(voice.getUserId());
							commentsSend.setType(Constant.COMMNETTYPE_JZXS);
							commentsSend.setToken(AppContext.getApp()
									.getToken());
						}
						holder.et_list_common_pinglun_reply.setText("");
						holder.et_list_common_pinglun_reply
								.setHint(R.string.text_comment_default_prompt);
						new SendCommentsTask(voice.getCommentsList(),context,new TaskCallBack(){
							@Override
							public void onTaskFinshed() {
								voice.resetInputStatus();
								ListViewVoiceTeacherAdapter.this.notifyDataSetChanged();
							}
						}).execute(commentsSend);
						((InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(
										holder.et_list_common_pinglun_reply
												.getWindowToken(), 0);
					}
				});
		//获得焦点时保存当前位置
		holder.et_list_common_pinglun_reply.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					setFocusOn(voice);
				}
				return false;
			}
		});

		//输入完成时，保存当前的输入内容
		holder.et_list_common_pinglun_reply.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String typeingComment = holder.et_list_common_pinglun_reply.getText().toString();
				if( typeingComment.length() > Constant.COMMENT_MAX_LEN) {
					typeingComment = typeingComment.substring(0,Constant.COMMENT_MAX_LEN);
					UIHelper.ToastMessage(context, "输入的内容不能大于"+Constant.COMMENT_MAX_LEN+"个字符");
					holder.et_list_common_pinglun_reply.setText(typeingComment);
				}
				if( voice.isFoucsOn() ) {
					voice.setTypeingComment(typeingComment);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
		});

		//如果当前动态动态正引用一条评论回复
		if(voice.getReferComment() != null
		&& voice.getReferComment().getUserAppe()!=null){
			holder.et_list_common_pinglun_reply
			.setHint("回复 "
					+ voice.getReferComment().getUserAppe());
		}

		//如果有保存的数据，回复输入未提交的内容
		if(voice.getTypeingComment()!=null && 
		 !voice.getTypeingComment().equals("")){
			holder.et_list_common_pinglun_reply.setText(voice.getTypeingComment());
		}else{
			holder.et_list_common_pinglun_reply.setText("");
			holder.et_list_common_pinglun_reply.setHint(R.string.text_comment_default_prompt);
		}
		
		//如果是由于输入框输入法键盘导致的页面重绘，直接获取焦点；对于其他导致的重绘，重置各参数
		if( voice.isFoucsOn()) {
			// 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
			holder.et_list_common_pinglun_reply.requestFocus();
			if(holder.et_list_common_pinglun_reply.getText().length() > 0){
				holder.et_list_common_pinglun_reply
					.setSelection(holder.et_list_common_pinglun_reply.getText()
							.length());
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
		LinearLayout ll_list_zxdt_js_ssh_zan;// 赞
		TextView tv_list_zxdt_js_shh_zan;// 赞文字
		ImageView iv_list_zxdt_js_shh_zan_logo;// 赞图标

		LinearLayout ll_list_zxdt_js_ssh_share;// 分享
		TextView tv_list_js_ssh_commentcount;// 评论数量
		NoScrollListView ll_list_zxdt_jz_item_commentlist;// 评论列表
		EditText et_list_common_pinglun_reply;// 回复；评论
		Button btn_list_zxdt_jz_item_comment_send;// 发送评论
		NoScrollGridView ngv_list_zxdt_jz_item_imagelist;// 图片列表
		LinearLayout ll_list_commom_zan;//
		TextView tv_list_common_zan;
		TextView tv_list_common_zan_count;// 共多少人赞过
		LinearLayout ll_list_zxdt_js_ssh_comment;//

		protected void resetContentAndListener(){
			if(tv_list_common_title_time!=null){
				tv_list_common_title_time.setText("");
			}
			if(tv_list_common_title_teachername!=null){
				tv_list_common_title_teachername.setText("");
			}
			if(tv_list_js_ssh_commentcount!=null){
				tv_list_js_ssh_commentcount.setText("");
			}
			if(ll_list_zxdt_jz_item_commentlist != null){
				ll_list_zxdt_jz_item_commentlist.setOnItemClickListener(null);
				ll_list_zxdt_jz_item_commentlist.setAdapter(null);
			}
			if(ngv_list_zxdt_jz_item_imagelist != null){
				ngv_list_zxdt_jz_item_imagelist.setOnItemClickListener(null);
				ngv_list_zxdt_jz_item_imagelist.setAdapter(null);
			}
			if(tv_list_common_zan != null){
				tv_list_common_zan.setOnClickListener(null);
			}
			if(tv_list_common_zan_count != null){
				tv_list_common_zan_count.setOnClickListener(null);
			}
			if(ll_list_zxdt_js_ssh_share != null){
				ll_list_zxdt_js_ssh_share.setOnClickListener(null);
			}
			if(btn_list_zxdt_jz_item_comment_send != null){
				btn_list_zxdt_jz_item_comment_send.setOnClickListener(null);
			}
		}
	}


}
