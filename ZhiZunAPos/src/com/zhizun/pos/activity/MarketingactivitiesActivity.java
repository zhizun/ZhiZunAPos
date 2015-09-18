package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ch.epw.task.QueryMarketingHotListTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.LocationUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.OrgListViewAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CourseListItemList;
import com.zhizun.pos.bean.MarketingHot;
import com.zhizun.pos.bean.MarketingHotList;
import com.zhizun.pos.bean.UserLogin;

/**
 * 营销活动activity 创建人：李林中 创建日期：2014-12-15 上午9:47:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MarketingactivitiesActivity extends BaseActivity implements
		OnClickListener {
	protected static final String TAG = MarketingactivitiesActivity.class
			.getName();
	Context context;
	private ImageView[] imageViews = null;
	private ViewPager advPager = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	TitleBarView titleBarView;

	ListView ll_marketing_orglist;
	LinearLayout lv_course;
	TextView tv_loginAcitivyt;

	// 搜索入口
	LinearLayout ll_search_org;
	LinearLayout ll_search_cour;
	LinearLayout ll_search_act;
	private Dialog dialog;

	//正在定位中的提示信息
	private OnClickListener locationNotReadyListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			UIHelper.ToastMessage(context, R.string.info_location_not_ready);
		}
	};

	private void autoLocation(){
		LocationUtils.getLocation(new TaskCallBack() {
			@Override
			public void onLocated(BDLocation location){
				ll_search_org.setOnClickListener(MarketingactivitiesActivity.this);
				ll_search_cour.setOnClickListener(MarketingactivitiesActivity.this);
				ll_search_act.setOnClickListener(MarketingactivitiesActivity.this);
				lv_course.setOnClickListener(MarketingactivitiesActivity.this);
				if(LocationUtils.getLastKnownLocation() == null){
					UIHelper.ToastMessage(context, "无法获取您的位置信息");
				}
			};
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marketing_activities);
		context = this;
		
		initView();
		
		if(checkLocationAvailable()){
			autoLocation();
		}

		// 如果登录成功 则让标题栏 登陆 消失
		IntentFilter intentFilter = new IntentFilter(
						"com.ch.epw.REFRESH_INDEXACTIVITY");
		registerReceiver(broadcastReceiver, intentFilter);
		if (AppContext.getApp().getUserLoginSharedPre()!=null) {
			tv_loginAcitivyt.setVisibility(View.GONE);
		}
		// 判断是否为第一次使用 如果是第一次就弹出透明引导图
		SharedPreferences sef = this.getSharedPreferences("FirstUseInfo",
				Context.MODE_PRIVATE);
		Integer mainDialog = sef.getInt("marketingDialog", 0);
		if (null == mainDialog || mainDialog != 1) {
			Editor editor = sef.edit();
			editor.putInt("marketingDialog", 1);
			editor.commit();
			mainDialog();
		}
	}
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			tv_loginAcitivyt.setVisibility(View.GONE);
		}
	};
	private void initView() {
		lv_course = (LinearLayout) findViewById(R.id.lv_course);
		tv_loginAcitivyt = (TextView) findViewById(R.id.tv_loginAcitivyt);
		ll_marketing_orglist = (ListView) findViewById(R.id.ll_marketing_activities);

		ll_search_org = (LinearLayout) findViewById(R.id.ll_search_org);
		ll_search_cour = (LinearLayout) findViewById(R.id.ll_search_cour);
		ll_search_act = (LinearLayout) findViewById(R.id.ll_search_act);
		
		ll_search_org.setOnClickListener(locationNotReadyListener);
		ll_search_cour.setOnClickListener(locationNotReadyListener);
		ll_search_act.setOnClickListener(locationNotReadyListener);
		lv_course.setOnClickListener(locationNotReadyListener);

		tv_loginAcitivyt.setOnClickListener(this);
		advPager = (ViewPager) findViewById(R.id.adv_pager);

		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		new QueryMarketingHotListTask(context, new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				closeProgressDialog();
				MarketingHotList marketingHotList = (MarketingHotList) result;
				ArrayList<CourseListItemList> recommendList = (ArrayList<CourseListItemList>) marketingHotList
						.getCourseItemList();
				if (recommendList != null && recommendList.size() > 0) {
					ll_marketing_orglist.setDivider(getResources().getDrawable(R.drawable.itembg));
					ll_marketing_orglist.setDividerHeight(8);
					ll_marketing_orglist.setAdapter(new OrgListViewAdapter(
							context, recommendList));
					
					ll_marketing_orglist.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							CourseListItemList item = (CourseListItemList)parent.getAdapter().getItem(position);
							Intent intent = new Intent(
									context,
									OrgIntroDetailActivity.class);
							intent.putExtra("orgId",item.getOrgId());
							intent.putExtra("category",item.getCategory());
							startActivity(intent);
							intoAnim();
						}
					});
				}

				List<MarketingHot> listMarketingHot = marketingHotList
						.getMarketingHotList();
				if (listMarketingHot != null) {
					initViewPager(listMarketingHot);
				}
			}

			@Override
			public void onTaskFailed() {
				closeProgressDialog();
			}
		}).execute();
	}

	private void initViewPager(List<MarketingHot> listMarketingHot) {

		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		group.removeAllViews();
		// 这里存放的是广告背景
		List<View> advPics = new ArrayList<View>();
		for (final MarketingHot marketingHot : listMarketingHot) {
			ImageView img = new ImageView(this);
			ImageUtils.showImageAsCommonPic(img, marketingHot.getPicUrl());
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							PrizedEventIntroduceActivity.class);
					intent.putExtra("eventId", marketingHot.getEventId());
					startActivity(intent);
					intoAnim();

				}
			});
			advPics.add(img);
		}

		// 对imageviews进行填充
		imageViews = new ImageView[advPics.size()];
		// 小图标
		for (int i = 0; i < advPics.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
			lp.setMargins(5, 0, 5, 5);
			imageView.setLayoutParams(lp);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.marketing_point_select);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.marketing_point_normal);
			}
			group.addView(imageViews[i]);
		}

		advPager.setAdapter(new AdvAdapter(advPics));
		advPager.setOnPageChangeListener(new GuidePageChangeListener());
		advPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}

		}).start();
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
	}

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.marketing_point_select);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.marketing_point_normal);
				}
			}

		}

	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.lv_course://点击搜索框
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.setClass(MarketingactivitiesActivity.this,
					SearchHistoryActivity.class);
			startActivity(intent);
			break;

		case R.id.tv_loginAcitivyt://登陆按钮
			isLogin();
			break;

		case R.id.ll_search_org://搜索机构
			intent.setClass(MarketingactivitiesActivity.this,
					CourseListViewActivity.class);
			intent.putExtra("searchType", "1");
			startActivity(intent);
			intoAnim();
			break;

		case R.id.ll_search_cour://搜索课程
			intent.setClass(MarketingactivitiesActivity.this,
					CourseListViewActivity.class);
			intent.putExtra("searchType", "0");
			startActivity(intent);
			intoAnim();
			break;

		case R.id.ll_search_act://搜索活动
			intent.setClass(MarketingactivitiesActivity.this,
					SearchMarketActsActivity.class);
			startActivity(intent);
			intoAnim();
			break;

		default:
			break;
		}

	}
	private void mainDialog() {
		// 对话框样式
		dialog = new Dialog(this, R.style.Dialog_Fullscreen);
		// 添加对话框布局
		dialog.setContentView(R.layout.marketing_dialog);
		
		final ImageView iv = (ImageView) dialog
				.findViewById(R.id.imageView_main_guide);
		final ImageView iv2 = (ImageView) dialog
				.findViewById(R.id.imageView_main_guide_center);
		
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iv.setVisibility(View.GONE);
//				iv2.setVisibility(View.VISIBLE);
			}
		});
		
		iv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	private boolean isLogin() {
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			intoAnim();
			return false;
		}
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
	
	protected boolean checkLocationAvailable(){
		boolean isGpsOpen = LocationUtils.isGPSOpen(context);
		boolean isNetworkConnected = LocationUtils.isNetworkConnected(context);
		if(!isGpsOpen && !isNetworkConnected){
			UIHelper.ToastMessage(context, R.string.info_location_unavailable);
			return false;
		}
		return true;
	}
}
