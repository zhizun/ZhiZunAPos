package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.CourseListViewAdapter;
import com.zhizun.pos.adapter.GridViewImagesAdapter;
import com.zhizun.pos.adapter.TeacherListViewAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CourseListItemList;
import com.zhizun.pos.bean.OrgIntroBean;
import com.zhizun.pos.bean.OrgIntroWrapper;
import com.zhizun.pos.bean.Photo;
import com.ch.epw.task.GetOrgIntroDetailTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.ch.epw.view.StickyScrollView;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.view.StickyScrollView.OnScrollListener;
/**
 * 机构详情
 * 2015-7-6上午11:30:58
 */
public class OrgIntroDetailActivity extends BaseActivity implements
		OnScrollListener {

	private TitleBarView titleBarView;
	private Context context;
	private String orgId;

	LinearLayout ll_orginfo_container;
	ImageView iv_org_logo;
	TextView tv_orgname;
	TextView tv_orgaddress;
	
	TextView ll_envpiclist_title;
	TextView ll_awardpiclist_title;
	TextView ll_teacherlist_title;

	NoScrollListView nsl_courselist;
	NoScrollGridView ngv_envpiclist;
	NoScrollGridView ngv_awardpiclist;
	NoScrollListView nsl_teacherlist;

	LinearLayout ll_courselist_container;
	LinearLayout ll_envpiclist_container;
	LinearLayout ll_awardpiclist_container;
	LinearLayout ll_teacherlist_container;
	RelativeLayout rl_org_intro_call;
	StickyScrollView myScrollView;
	LinearLayout ll_top_sticky;
	private String category;

	CourseListViewAdapter courseListViewAdapter = null;
	GridViewImagesAdapter awardpiclistViewImagesAdapter = null;
	GridViewImagesAdapter envpiclistViewImagesAdapter = null;
	TeacherListViewAdapter teacherListViewAdapter = null;
	private LinearLayout ll_org_introduce;
	private TextView ngv_org_introduce_content;
	private TextView ll_org_introduce_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orgintro_detail);
		this.context = this;
		Intent intent = getIntent();
		orgId = intent.getStringExtra("orgId");//机构ID
		category=intent.getStringExtra("category");//区分机构还是幼儿园
		initView();
	}

	protected void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_org_intro);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);
		if (category.equals("0")) {
			titleBarView.setTitleText(R.string.kindergarten_introDetail_title);
		}else {
			titleBarView.setTitleText(R.string.org_introDetail_title);
		}
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		ll_orginfo_container = (LinearLayout) findViewById(R.id.ll_orginfo_container);
		iv_org_logo = (ImageView) findViewById(R.id.iv_org_logo);
		tv_orgname = (TextView) findViewById(R.id.tv_orgname);
		tv_orgaddress = (TextView) findViewById(R.id.tv_orgaddress);
		nsl_courselist = (NoScrollListView) findViewById(R.id.nsl_courselist);
		ngv_envpiclist = (NoScrollGridView) findViewById(R.id.ngv_envpiclist);
		ngv_awardpiclist = (NoScrollGridView) findViewById(R.id.ngv_awardpiclist);
		nsl_teacherlist = (NoScrollListView) findViewById(R.id.nsl_teacherlist);

		ll_courselist_container = (LinearLayout) findViewById(R.id.ll_courselist_container);
		ll_org_introduce=(LinearLayout) findViewById(R.id.ll_org_introduce);
		ll_envpiclist_container = (LinearLayout) findViewById(R.id.ll_envpiclist_container);
		ll_awardpiclist_container = (LinearLayout) findViewById(R.id.ll_awardpiclist_container);
		ll_teacherlist_container = (LinearLayout) findViewById(R.id.ll_teacherlist_container);
		rl_org_intro_call = (RelativeLayout) findViewById(R.id.rl_org_intro_call);
		
		ll_org_introduce_title=(TextView)findViewById(R.id.ll_org_introduce_title);
		ngv_org_introduce_content=(TextView)findViewById(R.id.ngv_org_introduce_content);
		ll_envpiclist_title=(TextView) findViewById(R.id.ll_envpiclist_title);
		ll_awardpiclist_title=(TextView) findViewById(R.id.ll_awardpiclist_title);
		ll_teacherlist_title=(TextView) findViewById(R.id.ll_teacherlist_title);

		myScrollView = (StickyScrollView) findViewById(R.id.scrollView);
		myScrollView.setOnScrollListener(this);
		
		ll_top_sticky = (LinearLayout)findViewById(R.id.ll_top_sticky);

		findViewById(R.id.parent_layout).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 这一步很重要，使得悬浮菜单与显示的菜单名称重合
						onScroll(myScrollView.getScrollY());

					}
				});

		
		if (orgId != null && !orgId.equals("")) {
			showProgressDialog(context, "",
					getResources().getString(R.string.load_ing));
			new GetOrgIntroDetailTask(context, new TaskCallBack() {

				@Override
				public void onTaskFinshed(BaseBean result) {
					closeProgressDialog();
					OrgIntroWrapper orgIntroWrapper = (OrgIntroWrapper) result;
					if (orgIntroWrapper != null
							&& "0".equals(orgIntroWrapper.getStatus())) {
						final OrgIntroBean orgIntroDetail = orgIntroWrapper
								.getOrgIntroBean();

						ImageUtils.showImageAsLogo(iv_org_logo, orgIntroDetail.getLogoPath());
						tv_orgname.setText(orgIntroDetail.getOrgName());

						String addrInfo = orgIntroDetail.getAddrInfo();
						if (addrInfo != null && !"".equals(addrInfo)) {
							tv_orgaddress.setText("地址：" + addrInfo);
						} else {
							tv_orgaddress.setText("");
						}
						// 全部课程
						if (orgIntroDetail.getOrgCourseList() != null
								&& orgIntroDetail.getOrgCourseList().size() > 0) {
							courseListViewAdapter = new CourseListViewAdapter(
									context,(ArrayList<CourseListItemList>) orgIntroDetail.getOrgCourseList());
							nsl_courselist.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									Object item = nsl_courselist.getAdapter().getItem(position);
									if (item != null) {
										CourseListItemList course = (CourseListItemList) item;

										Intent intent = new Intent(context, CourseDetailActivity.class);
										intent.putExtra("courId",   course.getCourId());
										String orgId = course.getOrgId();
										if (course.getCourseOrgBean() != null
												&& course.getCourseOrgBean().getOrgId() != null
												&& !"".equals(course.getCourseOrgBean().getOrgId())) {
											orgId = course.getCourseOrgBean().getOrgId();
										}
										intent.putExtra("ownOrgId", orgId);
										startActivity(intent);
										intoAnim();
									}
								}
							});
							
							ll_courselist_container.setVisibility(View.VISIBLE);
						}
						
						// 机构介绍
						if (orgIntroDetail.getOrgDesc() != null
								&& !orgIntroDetail.getOrgDesc().equals("")) {
							ngv_org_introduce_content.setText(orgIntroDetail.getOrgDesc());
							ll_org_introduce.setVisibility(View.VISIBLE);
							if (category.equals("0")) {
								ll_org_introduce_title.setText("园所介绍");
							}
						}

						// 机构环境
						if (orgIntroDetail.getEnvPicList() != null
								&& orgIntroDetail.getEnvPicList().size() > 0) {
							envpiclistViewImagesAdapter = new GridViewImagesAdapter(
									context, orgIntroDetail.getEnvPicList());
							ngv_envpiclist
									.setOnItemClickListener(getNewInstanceOfOnItemClickListener(orgIntroDetail
											.getEnvPicList()));
							ll_envpiclist_container.setVisibility(View.VISIBLE);
							if (category.equals("0")) {
								ll_envpiclist_title.setText("园所环境");
							}
						}

						// 荣誉展示
						if (orgIntroDetail.getAwardPicList() != null
								&& orgIntroDetail.getAwardPicList().size() > 0) {
							awardpiclistViewImagesAdapter = new GridViewImagesAdapter(
									context, orgIntroDetail.getAwardPicList());
							ngv_awardpiclist
									.setOnItemClickListener(getNewInstanceOfOnItemClickListener(orgIntroDetail
											.getAwardPicList()));
							ll_awardpiclist_container
									.setVisibility(View.VISIBLE);
//							if (category.equals("0")) {
//								ll_awardpiclist_title.setText("荣誉展示");
//							}
						}

						// 名师团队
						if (orgIntroDetail.getOrgTeacherList() != null
								&& orgIntroDetail.getOrgTeacherList().size() > 0) {
							teacherListViewAdapter = new TeacherListViewAdapter(
									context, orgIntroDetail.getOrgTeacherList());
							ll_teacherlist_container.setVisibility(View.VISIBLE);
							if (category.equals("0")) {
								ll_teacherlist_title.setText("师资介绍");
							}
						}

						// 电话咨询
						if (orgIntroDetail.getPhone() == null
								|| "".equals(orgIntroDetail.getPhone())) {
							rl_org_intro_call.setVisibility(View.INVISIBLE);
						} else {
							rl_org_intro_call
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											ArrayList<String> listPhone=new ArrayList<String>();
											String[] phone=orgIntroDetail.getPhone().split(",");
											if (phone.length>1) {
											for (int i = 0; i < phone.length; i++) {
												listPhone.add(phone[i]);
													}
												Intent intet=new Intent(OrgIntroDetailActivity.this,FriendsCallPhoneDialogActivity.class);
												intet.putStringArrayListExtra("phoneList",listPhone);
												intet.putExtra("courId", "");
												startActivity(intet);
											}else {
												Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
														+ orgIntroDetail.getPhone()));
												intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												startActivity(intent);
											}
//											Intent intent = new Intent(Intent.ACTION_DIAL, Uri
//													.parse("tel:" + orgIntroDetail.getPhone()));
//											startActivity(intent);
										}
									});
						}
					}
				}

				@Override
				public void onTaskFailed() {
					closeProgressDialog();
				}
			}).execute(orgId);
		}
	}

	protected OnItemClickListener getNewInstanceOfOnItemClickListener(
			final List<Photo> photoList) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ArrayList<String> imgsUrl = new ArrayList<String>();
				for (Photo photo : photoList) {
					imgsUrl.add(URLs.URL_IMG_API_HOST + photo.getThumbPath()
							+ "/" + photo.getThumbSaveName());
				}
				Intent intent = new Intent();
				intent.putStringArrayListExtra("infos", imgsUrl);
				intent.putExtra("imgPosition", position);
				intent.setClass(context, ImageShowActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		};
	}

	@Override
	public void onScroll(int scrollY) {
		View currView = myScrollView.getCurrRelatedView();
		if(currView != null){
			String tagText = (String) ((TextView)((ViewGroup)currView).getChildAt(0)).getText();
			((TextView)(ll_top_sticky.getChildAt(0))).setText(tagText);
			int mTopLayout2ParentTop = Math.max(scrollY, currView.getTop());  
			ll_top_sticky.layout(0, mTopLayout2ParentTop, ll_top_sticky.getWidth(), mTopLayout2ParentTop + ll_top_sticky.getHeight());
			ll_top_sticky.setVisibility(View.VISIBLE);
		}

		
		if(ll_courselist_container.getTop() - scrollY < myScrollView.getBottom() && courseListViewAdapter != null
				&& nsl_courselist.getAdapter() == null
		){
			nsl_courselist.setAdapter(courseListViewAdapter);
			return;
		}
		
		if(ll_envpiclist_container.getTop() - scrollY < myScrollView.getBottom() && envpiclistViewImagesAdapter != null
				&& ngv_envpiclist.getAdapter() == null
		){
			ngv_envpiclist.setAdapter(envpiclistViewImagesAdapter);
			return;
		}
		
		if(ll_awardpiclist_container.getTop() - scrollY < myScrollView.getBottom() && awardpiclistViewImagesAdapter != null
				&& ngv_awardpiclist.getAdapter() == null
		){
			ngv_awardpiclist.setAdapter(awardpiclistViewImagesAdapter);
			return;
		}
		
		if(ll_teacherlist_container.getTop() - scrollY < myScrollView.getBottom() && teacherListViewAdapter != null
				&& nsl_teacherlist.getAdapter() == null
		){
			nsl_teacherlist.setAdapter(teacherListViewAdapter);
			return;
		}
		
	}
	

}
