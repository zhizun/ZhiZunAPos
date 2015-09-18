package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ch.epw.task.StatCourseCounselNumTask;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.NoScrollListView;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsCommentAdapter;
import com.zhizun.pos.adapter.TeacherListViewAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.CourseParticularsBean;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.TeacherInfo;
import com.zhizun.pos.bean.UserLogin;

/**
 * 课程详情页
 * 
 * @author lilinzhong
 *
 *         2015-6-29下午2:31:13
 */
public class CourseDetailActivity extends BaseActivity implements
		OnClickListener {
	
	public static final int REQUEST_START_COMMENT_EDIT = 1;
	
	private TitleBarView titleBarView;
	private LinearLayout ll_course_tel;
	private LinearLayout ll_course_apply;
	Context context;
	private CourseParticularsBean courseParticulars;
	String courId;
	String ownOrgId;
	private TextView tv_course_orgName, tv_course_addr, tv_look_course,
			tv_course_name, tv_course_coucesc, tv_course_arrange,
			tv_course_price, tv_salesinfo, tv_returnclasshour,
			tv_course_signnum_txet, tv_viewnum_text;
	private ImageView im_part;
	private NoScrollListView lv_course_teachers;
	private TeacherListViewAdapter adapter;
	private TextView tv_olsalesinfo,courseLength;

	TextView tv_course_no_comment_placeholder; // 无课程评价时默认提示消息
	LinearLayout ll_course_comment_list_container; // 放置课程评价列表布局
	LinearLayout ll_course_new_comment; // 我要评价
	ListView lv_course_comment_list; // 课程评价列表
	Button bt_course_comment_show_more; // 更多评价按钮
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = CourseDetailActivity.this;
		setContentView(R.layout.activity_course_particulars);
		Intent intent = getIntent();
		courId = intent.getStringExtra("courId");
		ownOrgId = intent.getStringExtra("ownOrgId");
		initView();
		content();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_course);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_course_particulars);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		ll_course_tel = (LinearLayout) findViewById(R.id.ll_course_tel);
		ll_course_apply = (LinearLayout) findViewById(R.id.ll_course_apply);
		tv_course_orgName = (TextView) findViewById(R.id.tv_course_orgName);
		tv_course_addr = (TextView) findViewById(R.id.tv_course_addr);
		tv_look_course = (TextView) findViewById(R.id.tv_look_course);
		tv_course_name = (TextView) findViewById(R.id.tv_course_name);
		tv_course_coucesc = (TextView) findViewById(R.id.tv_course_coucesc);
		tv_course_arrange = (TextView) findViewById(R.id.tv_course_arrange);
		tv_course_price = (TextView) findViewById(R.id.tv_course_price);
		tv_salesinfo = (TextView) findViewById(R.id.tv_salesinfo);
		tv_olsalesinfo = (TextView) findViewById(R.id.tv_olsalesinfo);
		tv_returnclasshour = (TextView) findViewById(R.id.tv_returnclasshour);
		tv_course_signnum_txet = (TextView) findViewById(R.id.tv_course_signnum_txet);
		tv_viewnum_text = (TextView) findViewById(R.id.tv_viewnum_text);
		courseLength=(TextView) findViewById(R.id.courseLength);
		im_part = (ImageView) findViewById(R.id.im_part);
		lv_course_teachers = (NoScrollListView) findViewById(R.id.lv_course_teachers);
		ll_course_tel.setOnClickListener(this);
		ll_course_apply.setOnClickListener(this);
		tv_look_course.setOnClickListener(this);

		tv_course_no_comment_placeholder = (TextView) findViewById(R.id.tv_course_no_comment_placeholder);
		ll_course_comment_list_container = (LinearLayout) findViewById(R.id.ll_course_comment_list_container);
		ll_course_new_comment = (LinearLayout) findViewById(R.id.ll_course_new_comment);
		lv_course_comment_list = (ListView) findViewById(R.id.lv_course_comment_list);
		bt_course_comment_show_more = (Button) findViewById(R.id.bt_course_comment_show_more);
		ll_course_new_comment.setOnClickListener(this);
		bt_course_comment_show_more.setOnClickListener(this);
	}

	/**
	 * 获取课程详情
	 */
	private void content() {
		if (courId != null && !courId.equals("") && ownOrgId != null
				&& !ownOrgId.equals("")) {
			UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
			if (userLogin != null && userLogin.getUserInfo() != null) {
				userId= userLogin.getUserInfo().getUserId();
			}
			new QueryCourseParticularsTask().execute(courId, ownOrgId,userId);
		}
	}

	/**
	 * 获得课程搜索列表 Task
	 */
	private class QueryCourseParticularsTask extends
			AsyncTask<String, Void, CourseParticularsBean> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected CourseParticularsBean doInBackground(String... params) {

			try {
				courseParticulars = AppContext.getApp().queryCourseParticulars(
						params[0], params[1],params[2]);

			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				courseParticulars = null;
			}
			return courseParticulars;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(CourseParticularsBean result) {

			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					List<TeacherInfo> teachList = result.getCourseTeacherListBean();
					if (teachList != null && teachList.size() > 0) {
						adapter = new TeacherListViewAdapter(context, result.getCourseTeacherListBean());
						lv_course_teachers.setAdapter(adapter);
					}
					ImageUtils.showImageAsLogo(im_part, result.getPhotoPath());
					// showPicture(result.getPhotoPath(), im_part, options);
					tv_course_orgName.setText(result.getOrgName());
					if (result.getAddr() != null) {
						tv_course_addr.setText("地址:" + result.getAddr());
					}
					if (result.getPrice()==null||result.getPrice().equals("")) {
						tv_course_price.setText("");
					}else if (result.getPrice().equals("-1")) {
						tv_course_price.setText("暂无价格");
					}else {
						tv_course_price.setText("¥" + result.getPrice());
					}
					tv_course_name.setText(result.getName());
					tv_course_coucesc.setText(result.getCourDesc());
					tv_course_arrange.setText(result.getArrange());
					if (result.getOlSalesInfo()!=null && !result.getOlSalesInfo().equals("")) {
						tv_olsalesinfo.setText("网上报名优惠"
								+ result.getOlSalesInfo() + "%");
					}
					tv_salesinfo.setText(result.getSalesInfo());
					tv_course_signnum_txet.setText(result.getSignNum());
					courseLength.setText(result.getCourseLength());
					tv_viewnum_text.setText(result.getViewNum());
					if (result.getReturnClassHour()!=null) {
						if (result.getReturnClassHour().equals("-1")) {
							tv_returnclasshour.setText("不可退");
						} else if (result.getReturnClassHour().equals("0")) {
							tv_returnclasshour.setText("随时可退"); 
						} else {
							tv_returnclasshour.setText(result.getReturnClassHour()
 + "节课后可退");
						}
					}
					// tv_returnclasshour.setText(result.getReturnClassHour());
					if (result.getCourseCommentList() != null
							&& result.getCourseCommentList().size() > 0) {
						List<FriendsCommentListBean> courCommentShowList = result.getCourseCommentList();
						if(courCommentShowList.size() > 5){
							courCommentShowList = courCommentShowList.subList(0, 5);
						}
						if(Integer.valueOf(result.getCommentNum())>5){
							bt_course_comment_show_more.setVisibility(View.VISIBLE);
						}else {
							bt_course_comment_show_more.setVisibility(View.GONE);
						}
						FriendsCommentAdapter courseCommentAdapter = new FriendsCommentAdapter(
								CourseDetailActivity.this,
								(ArrayList<FriendsCommentListBean>) courCommentShowList, "3");
						lv_course_comment_list.setAdapter(courseCommentAdapter);
						ll_course_comment_list_container.setVisibility(View.VISIBLE);
						tv_course_no_comment_placeholder.setVisibility(View.GONE);
					} else {
						ll_course_comment_list_container
								.setVisibility(View.GONE);
						tv_course_no_comment_placeholder
								.setVisibility(View.VISIBLE);
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

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.tv_look_course:
			intent = new Intent(this, OrgIntroDetailActivity.class);
			intent.putExtra("orgId", ownOrgId);
			intent.putExtra("category", "1");
			startActivity(intent);
			break;
		case R.id.ll_course_tel:
			ArrayList<String> listPhone=new ArrayList<String>();
			// 统计电话咨询数
			String[] phone=courseParticulars.getPhone().split(",");
			if (phone.length>1) {
			for (int i = 0; i < phone.length; i++) {
				listPhone.add(phone[i]);
					}
				System.out.println(phone);
				Intent intet=new Intent(this,FriendsCallPhoneDialogActivity.class);
				intet.putStringArrayListExtra("phoneList",listPhone);
				intet.putExtra("courId", courId);
				startActivity(intet);
			}else {
				if (courId != null) {
					new StatCourseCounselNumTask(context, null).execute(courId);
				}
				intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ courseParticulars.getPhone()));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			break;
		case R.id.ll_course_apply:
			if (isLogin()) {
				intent = new Intent(this, CourseApply.class);
				intent.putExtra("courId", courId);
				intent.putExtra("ownOrgId", ownOrgId);
				startActivity(intent);
			}
			break;

		case R.id.ll_course_new_comment:

			intent = new Intent(this, MyCourseCommentEditActivity.class);
			intent.putExtra("courId", courId);
			intent.putExtra("ownOrgId", ownOrgId);
			if (courseParticulars != null) {
				intent.putExtra("courName", courseParticulars.getName());
				intent.putExtra("orgName", courseParticulars.getOrgName());
			}
			startActivityForResult(intent,
					CourseDetailActivity.REQUEST_START_COMMENT_EDIT);
			break;

		case R.id.bt_course_comment_show_more:
			intent = new Intent(this, CourseCommentListViewActivity.class);
			intent.putExtra("courId", courId);
			intent.putExtra("ownOrgId", ownOrgId);
			intent.putExtra("activityTag", "3");
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CourseDetailActivity.REQUEST_START_COMMENT_EDIT
				&& resultCode == MyCourseCommentEditActivity.RESULT_SEND_COMMENT_FINISHED) {
			content();
		}
	}

	private boolean isLogin() {
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			return false;
		}
		return true;
	}
}
