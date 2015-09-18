package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.activity.SuggestInputWindow.OnSuggestionCheckedListener;
import com.zhizun.pos.adapter.ListViewRatingAdapter;
import com.zhizun.pos.adapter.ListViewRatingAdapter.OnRatingItemChangedListener;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CourseComment;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.Rating;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.SuggestName;
import com.zhizun.pos.bean.UserLogin;
import com.ch.epw.helper.UploadImageHelper;
import com.ch.epw.helper.UploadImageHelper.OnImageUploadFinshedListener;
import com.ch.epw.task.SendCourseCommentTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.umeng.analytics.MobclickAgent;

/**
 * 我要评价
 *
 * ===================================================
 */
public class MyCourseCommentEditActivity extends BaseActivity implements
		OnImageUploadFinshedListener {
	protected static final String TAG = MyCourseCommentEditActivity.class
			.getName();

	public static final int RESULT_SEND_COMMENT_FINISHED = 1;
	public static final int REQUEST_START_LOGIN_ACTIVITY = 1;

	Context context;
	TitleBarView titleBarView;

	LinearLayout ll_comment_org_name;
	LinearLayout ll_comment_course_name;
	TextView tv_comment_org_name;
	TextView tv_comment_course_name;
	EditText et_comment_text;
	ListView ll_course_comment_starlist;
	GridView gv_pic_gridlist;
	TextView tv_comment_send;
	CheckBox cb_is_anonymous;
	CheckBox cb_is_friends_shead;

	// 从intent获取到的机构、课程相关
	String orgId;
	String orgName;
	String courId;
	String courName;
	FriendsCommentListBean commentBean;
	boolean isUpdateMode = false; // 是否编辑模式（默认新增评论，值为false）

	UploadImageHelper imageHelper;

	// 所有预设的评价项列表
	List<Rating> listRatingAllItems =  Rating.Helper.getCourseRatingFromJson(null);
	List<Rating> listRatingItems = new ArrayList<Rating>(); // 当前显示的评价项列表
	
	private String logoPath="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_course_comment_edit_activity);
		context = this;

		// 禁止自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Intent intent = getIntent();
		orgId = intent.getStringExtra("ownOrgId");
		orgName = intent.getStringExtra("orgName");
		courId = intent.getStringExtra("courId");
		courName = intent.getStringExtra("courName");
		commentBean = (FriendsCommentListBean) intent.getSerializableExtra("commentBean");
		if (commentBean != null) {
			isUpdateMode = true;
			orgId = commentBean.getOrgId();
			orgName = commentBean.getOrgName();
			courId = commentBean.getCourId();
			courName = commentBean.getCourName();
		}

		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin == null) {
			Intent intent_login = new Intent(context, LoginActivity.class);
			startActivityForResult(intent_login, REQUEST_START_LOGIN_ACTIVITY);
			intoAnim();
		}else{
			initView();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MyCourseCommentEditActivity.REQUEST_START_LOGIN_ACTIVITY
				&& resultCode == LoginActivity.RESULT_USER_LOGIN_FINISHED) {
			initView();
		} else {
			MyCourseCommentEditActivity.this.finish();
		}
	}

	OnClickListener clickToShowSuggestionWindow = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String searchType = "0";
			String popupTitleName = "";
			String suggestInputHint = "";
			TextView caller = null;
			switch (v.getId()) {
			case R.id.tv_comment_course_name:
				searchType = "0";
				caller = tv_comment_course_name;
				popupTitleName = "所学课程";
				suggestInputHint = "请输入课程名称";
				break;
			case R.id.tv_comment_org_name:
				searchType = "1";
				caller = tv_comment_org_name;
				popupTitleName = "培训机构";
				suggestInputHint = "请输入培训机构名称";
				break;
			}

			if (caller != null) {
				final SuggestInputWindow suggestWindow = new SuggestInputWindow(
						context, searchType, caller.getText().toString(), orgId);
				suggestWindow.showAsDropDown(titleBarView);
				// 修改弹出框下的标题文字
				titleBarView.setTitleText(popupTitleName);
				// 设置输入文本框提示信息
				suggestWindow.setSuggestTextHint(suggestInputHint);
				suggestWindow
					.setOnSuggestionCheckedListener(new OnSuggestionCheckedListener() {

						@Override
						public void OnSuggestionChecked(SuggestName suggest) {
							if (suggest != null) {
									// 输入建议返回的是课程
								if ("0".equals(suggest.getSearchType())) {
									if (!TextUtils.isEmpty(suggest.getCourName())
											&& !suggest.getCourName().equals(courName)) {
										courId = suggest.getCourId();
										courName = suggest.getCourName();
										tv_comment_course_name.setText(courName);
									}
								}
									// 输入建议返回的是机构,设置机构名称，并显示课程输入框
								else {
									if (!TextUtils.isEmpty(suggest.getOrgName())
											&& !suggest.getOrgName().equals(orgName)) {
										orgId = suggest.getOrgId();
										orgName = suggest.getOrgName();
										tv_comment_org_name.setText(orgName);
										// orgName发生变化，设置课程相关内容为空
										courId = "";
										courName = "";
										tv_comment_course_name.setText("");
										logoPath=suggest.getLogoPath();
										ll_comment_course_name.setVisibility(View.VISIBLE);
										tv_comment_course_name.setOnClickListener(clickToShowSuggestionWindow);
									}
								}
									// 恢复页面标题
								titleBarView.setTitleText(R.string.title_text_my_course_comment_edit);
							}
						}
					});
			}
		}
	};

	private void initView() {
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_my_course_comment_edit);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ll_comment_org_name = (LinearLayout) findViewById(R.id.ll_comment_org_name);
		ll_comment_course_name = (LinearLayout) findViewById(R.id.ll_comment_course_name);
		tv_comment_org_name = (TextView) findViewById(R.id.tv_comment_org_name);
		tv_comment_course_name = (TextView) findViewById(R.id.tv_comment_course_name);
		cb_is_anonymous = (CheckBox) findViewById(R.id.cb_is_anonymous);
		cb_is_friends_shead = (CheckBox) findViewById(R.id.cb_is_friends_shead);
		et_comment_text = (EditText) findViewById(R.id.et_comment_text);
		et_comment_text.addTextChangedListener(new MaxLengthWatcher(context,
				200, et_comment_text));

		et_comment_text.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					((InputMethodManager) context
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});

		ll_course_comment_starlist = (ListView) findViewById(R.id.ll_course_comment_starlist);
		// 初始化点击发布按钮事件响应
		tv_comment_send = (TextView) findViewById(R.id.tv_comment_send);
		tv_comment_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getWrapCourseComment() != null) {
					imageHelper
							.setOnImageUploadFinshedListener(MyCourseCommentEditActivity.this);
					imageHelper.upLoadImageAsync();
				}
			}
		});

		// 初始化上传图片布局及事件响应
		gv_pic_gridlist = (GridView) findViewById(R.id.gv_pic_gridlist);
		imageHelper = new UploadImageHelper(context, gv_pic_gridlist, 3);
		// 为图片列表增加点击时获得焦点，触发et_comment_text失去焦点并隐藏软键盘
		gv_pic_gridlist.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
				return false;
			}
		});

		if (isUpdateMode) {
			ll_comment_org_name.setVisibility(View.VISIBLE);
			ll_comment_course_name.setVisibility(View.VISIBLE);
			tv_comment_org_name.setText(commentBean.getOrgName());
			tv_comment_course_name.setText(commentBean.getCourName());
			listRatingItems.addAll(commentBean.getRatingList());
			et_comment_text.setText(commentBean.getContent());
			et_comment_text.setSelection(commentBean.getContent().length());
			cb_is_anonymous
					.setChecked("1".equals(commentBean.getIsAnonymous()));
			tv_comment_send.setText("保存");
			tv_comment_org_name.setOnClickListener(clickToShowSuggestionWindow);
			tv_comment_course_name
					.setOnClickListener(clickToShowSuggestionWindow);
			imageHelper.loadImageForUpdate(commentBean.getPicList());
		} else {
			// 默认情况下，培训机构和所学课程输入框都是隐藏的
			if (TextUtils.isEmpty(orgId)) {
				ll_comment_org_name.setVisibility(View.VISIBLE);
				tv_comment_org_name
						.setOnClickListener(clickToShowSuggestionWindow);
			}
			// 初始化评价列表布局及事件响应
			listRatingItems.addAll(listRatingAllItems.subList(0, 1));
		}

		final ListViewRatingAdapter ratingListAdapter = new ListViewRatingAdapter(
				context, R.layout.course_comment_star_item_clickable,
				listRatingItems);
		ll_course_comment_starlist.setAdapter(ratingListAdapter);

		ratingListAdapter
				.setOnRatingItemChangedListener(new OnRatingItemChangedListener() {

					@Override
					public void OnRatingItemChanged(Rating rating) {
						if (ratingListAdapter.getCount() < Rating.Helper.COURSE_RATING_ITEM_NUM) {
							listRatingItems.clear();
							listRatingItems.addAll(listRatingAllItems);
							ratingListAdapter.notifyDataSetChanged();
						}
					}
				});

	}

	private CourseComment getWrapCourseComment() {
		CourseComment courseComment = new CourseComment();
		courseComment.setOrgId(orgId);
		courseComment.setOrgName(orgName);
		courseComment.setCourId(courId);
		courseComment.setCourName(courName);

		if (isUpdateMode) {
			courseComment.setCommentId(commentBean.getCommentId());
		}

		if (TextUtils.isEmpty(orgName)) {
			UIHelper.ToastMessage(context, "培训机构不能为空");
			return null;
		}

		if (TextUtils.isEmpty(courName)) {
			UIHelper.ToastMessage(context, "所学课程不能为空");
			return null;
		}

		if (TextUtils.isEmpty(listRatingItems.get(0).getRating())
				|| listRatingItems.get(0).getRating().startsWith("0")) {
			UIHelper.ToastMessage(context, "综合评分不能为空");
			return null;
		}

		courseComment.setIsAnonymous(cb_is_anonymous.isChecked() ? "1" : "0");
		courseComment.setRatingList(listRatingItems);
		courseComment.setContent(et_comment_text.getText().toString());
		if (TextUtils.isEmpty(courseComment.getContent())) {
			UIHelper.ToastMessage(context, "评价内容不能为空");
			return null;
		}

		courseComment.setPhotoPath(imageHelper.getPicturePostFormat());

		return courseComment;
	}

	protected void onRestart() {
		super.onRestart();
		imageHelper.refreshAndLoadImage();
	}

	@Override
	public void onBackPressed() {
		this.finish();
		imageHelper.resetContents();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		super.onBackPressed();
	}

	@Override
	public void onImageUploadFinshed() {
		CourseComment courseComment = getWrapCourseComment();
		if (courseComment != null) {
				
			new SendCourseCommentTask(context, new TaskCallBack() {
				@Override
				public void onTaskFinshed(BaseBean result) {
					Result res = (Result) result;
					if (res.getStatus().equals("0")) {
						MyCourseCommentEditActivity.this
								.setResult(MyCourseCommentEditActivity.RESULT_SEND_COMMENT_FINISHED);
						MyCourseCommentEditActivity.this.finish();
						if (cb_is_friends_shead.isChecked()) {
							// 发表评价勾选分享
							Intent intent=new Intent(MyCourseCommentEditActivity.this,FriendsInviteActivity.class);
							intent.putExtra("logoPath", logoPath);
							intent.putExtra("course", courName);// 课程名
							intent.putExtra("org", orgName);// 机构名
							intent.putExtra("commentId", res.getCommentId());
							startActivity(intent);
							imageHelper.resetContents();
						} else {
							onBackPressed();
						}
						UIHelper.ToastMessage(context, "评价发表成功");
					} else {
						UIHelper.ToastMessage(context, "评价发表失败");
					}
				}
			}).execute(courseComment);
		}
	}
}

class MaxLengthWatcher implements TextWatcher {
	private int maxLen = 0;
	private EditText editText;
	Context context;

	public MaxLengthWatcher(Context context, int maxLen, EditText editText) {
		this.maxLen = maxLen;
		this.editText = editText;
		this.context = context;
	}

	public void afterTextChanged(Editable arg0) {

	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		Editable editable = editText.getText();
		int len = editable.length();

		if (len > maxLen) {
			UIHelper.ToastMessage(context, "最多输入" + maxLen + "个字符");
			int selEndIndex = Selection.getSelectionEnd(editable);
			String str = editable.toString();
			// 截取新字符串
			String newStr = str.substring(0, maxLen);
			editText.setText(newStr);
			editable = editText.getText();

			// 新字符串的长度
			int newLen = editable.length();
			// 旧光标位置超过字符串长度
			if (selEndIndex > newLen) {
				selEndIndex = editable.length();
			}
			// 设置新光标所在的位置
			Selection.setSelection(editable, selEndIndex);
		}
	}
}
