package com.zhizun.pos.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.ch.epw.jz.fragment.BanjishipuFragment;
import com.ch.epw.jz.fragment.JiaoxuejihuaFragment;
import com.ch.epw.jz.fragment.JiatingzuoyeFragment;
import com.ch.epw.jz.fragment.JiazhangxinshengFragment;
import com.ch.epw.jz.fragment.KaoqinjiluFragment;
import com.ch.epw.jz.fragment.TongzhigonggaoFragment;
import com.ch.epw.jz.fragment.WodeshoucangFragment;
import com.ch.epw.jz.fragment.ZaixiaodianpingFragment;
import com.ch.epw.jz.fragment.ZaixiaodongtaiFragment;
import com.ch.epw.task.GetNewCommentsCountTask;
import com.ch.epw.task.GetUnReadRecvNumTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.view.ColumnHorizontalScrollView;
import com.ch.epw.view.MyListView;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.view.MyListView.MyListViewFling;
import com.jauker.widget.BadgeView;
import com.umeng.analytics.MobclickAgent;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.NewsFragmentPagerAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.ChannelItem;
import com.zhizun.pos.bean.ChannelManage;
import com.zhizun.pos.bean.PushMsg;
import com.zhizun.pos.bean.UnReadRecvNum;
import com.zhizun.pos.bean.UnReadRecvNumList;

/**
 *  框架 创建人：李林中 创建日期：2014-12-15 上午9:48:12 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MainActivity extends FragmentActivity implements OnTouchListener,
		GestureDetector.OnGestureListener, OnItemClickListener,
		MyListViewFling {
	private Context mContext;
	/** 自定义右滑菜单 */
	private boolean hasMeasured = false;// 是否Measured.
	private LinearLayout layout_left;
	private LinearLayout layout_right;
	private MyListView lv_set;

	/** 每次自动展开/收缩的范围 */
	private int MAX_WIDTH = 0;
	/** 每次自动展开/收缩的速度 */
	private final static int SPEED = 30;

	private final static int sleep_time = 5;

	private GestureDetector mGestureDetector;// 手势
	private boolean isScrolling = false;
	private float mScrollX; // 滑块滑动距离
	private int window_width;// 屏幕的宽度

	private String TAG = "MainActivity";

	private View view = null;// 点击的view

	/** 头部标题栏 */
	private TitleBarView mTitleBarView;

	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	LinearLayout ll_more_columns;
	RelativeLayout rl_column;
	private ViewPager mViewPager;
	protected SimpleDateFormat mDateFormat2 = new SimpleDateFormat("yyyyMMdd");
	private ImageView button_more_columns;
	/** 用户选择的新闻分类列表 */
	private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 左阴影部分 */
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;
	/** 调整返回的RESULTCODE */
	public final static int CHANNELRESULT = 10;

	private int msgFromTag = 0;// 跳转到详情页的时候，这条消息是来自推送通知，还是来自登录/角色切换
								// 1表示来自推送通知，0表示来自登录/角色切换
	FrameLayout fl_common_zxhf;// 最新回复
	BadgeView badgeView;
	private Dialog dialog;

	private String activityCalledOnBack; // 当前activity退出时启动该activity，为空时则不调用
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.main2);
		mContext = this;
		mScreenWidth = BaseTools.getWindowsWidth(this);
		if (mScreenWidth > 320) {
			mItemWidth = mScreenWidth / 4;// 大屏除4， 一个Item宽度为屏幕的1/3
		} else {
			mItemWidth = mScreenWidth / 3;// 小屏除3， 一个Item宽度为屏幕的1/3
		}
		initView();
		Init2View();
		// 最新回复
		badgeView = new BadgeView(this);
		badgeView.setTargetView(fl_common_zxhf);
		badgeView.setBadgeGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		badgeView.setBadgeMargin(0, 0, 2, 0);
		// 加载最新回复个数
		new GetNewCommentsCountTask(mContext, badgeView).execute(AppContext
				.getApp().getToken(), "");

		msgFromTag = getIntent().getIntExtra("msgFromTag", 0);
		if (msgFromTag == 1) {
			PushMsg pushMsg = (PushMsg) getIntent().getSerializableExtra(
					"pushMsg");
			mViewPager.setCurrentItem(Integer.parseInt(pushMsg.getType()));
			selectTab(Integer.parseInt(pushMsg.getType()));
		}

		// 注册通知 刷新动态最新回复的小红点 减少一条
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_SETREAD_NEWCOMMENTCOUNT_LIST");
		registerReceiver(broadcastReceiver, filter);

		// 判断是否为第一次使用 如果是第一次就弹出透明引导图
				SharedPreferences sef = this.getSharedPreferences("FirstUseInfo",
						Context.MODE_PRIVATE);
				Integer mainDialog = sef.getInt("mainDialog", 0);
				if (null == mainDialog || mainDialog != 1) {
					Editor editor = sef.edit();
					editor.putInt("mainDialog", 1);
					editor.commit();
					mainDialog();
				}
				
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		// 设置退出时需要启动的activity
		activityCalledOnBack = getIntent().getStringExtra(
				"activityCalledOnBack");
	}

	
	@Override
	public void onBackPressed() {
		if (activityCalledOnBack != null) {
			AppManager.getAppManager().startActivity(MainActivity.this,
					activityCalledOnBack);
		}
		super.onBackPressed();
	}
	
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 全部已读 小红点消失
			badgeView.setBadgeCount(0);
		}
	};

	/***
	 * 初始化view 自定义左右滑动菜单
	 */
	void Init2View() {
		layout_left = (LinearLayout) findViewById(R.id.layout_left);
		layout_right = (LinearLayout) findViewById(R.id.layout_right);
		// iv_set = (ImageView) findViewById(R.id.iv_set);
		lv_set = (MyListView) findViewById(R.id.lv_set);
		lv_set.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
				R.id.tv_item, ChannelManage.getChannelItemNames()));
		// 点击监听
		lv_set.setOnItemClickListener(this);

		// listview item滑动监听
		lv_set.setMyListViewFling(this);

		layout_right.setOnTouchListener(this);
		layout_left.setOnTouchListener(this);
		// iv_set.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(this);
		// 禁用长按监听
		mGestureDetector.setIsLongpressEnabled(false);
		getMAX_WIDTH();
	}

	/** 初始化layout控件 */
	private void initView() {
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
		mTitleBarView = (TitleBarView) findViewById(R.id.activity_main_title_bar);
		// 标题栏
		mTitleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.GONE);
//		mTitleBarView.setBtnRight(R.drawable.myx2);
//		inviteMsgView = new BadgeView(mContext);
//		inviteMsgView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//		inviteMsgView.setTargetView(mTitleBarView.getBtnRight());
		// mTitleBarView.getIvLeft().setImageResource(R.drawable.touxiang);
		// mtitleBarView.setBarAreaLeftOnclickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(mContext, BabyInfoListActivity.class);
		// startActivity(intent);
		// overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		// }
		// });
//		mTitleBarView.setBarRightOnclickListener(this);

		ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) findViewById(R.id.rl_column);
		button_more_columns = (ImageView) findViewById(R.id.button_more_columns);
		button_more_columns.setOnTouchListener(this);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		shade_left = (ImageView) findViewById(R.id.shade_left);
		shade_right = (ImageView) findViewById(R.id.shade_right);
		fl_common_zxhf = (FrameLayout) findViewById(R.id.fl_common_zxhf);
		fl_common_zxhf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						NewCommonCommentsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});
		setChangelView();
	}

	/**
	 * 当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		showImage(Constant.POSTION_NUM);
		initFragment();
	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {
		// userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(
		// AppApplication.getApp().getSQLHelper()).getUserChannel());
		userChannelList = (ArrayList<ChannelItem>) ChannelManage.defaultUserChannels;

	}

	/**
	 * 初始化Column栏目项
	 * */
	@SuppressLint({ "ResourceAsColor", "NewApi" })
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = userChannelList.size();
		Log.i("TAG", "userChannelList.count=" + count);
		mColumnHorizontalScrollView.setParam(this, mScreenWidth,
				mRadioGroup_content, shade_left, shade_right, ll_more_columns,
				rl_column);
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			LinearLayout layout = new LinearLayout(this);
			TextView columnTextView = new TextView(this);
			ImageView bgImageView = new ImageView(this);
			bgImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.message_remind));
			bgImageView.setVisibility(View.GONE);
			bgImageView.setPadding(0, 15, 0, 0);
			columnTextView.setTextAppearance(this,
					R.style.top_category_scroll_view_item_text);
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setId(i);
			columnTextView.setSingleLine(true);
			columnTextView.setText(userChannelList.get(i).getName());
			columnTextView.setTextColor(getResources().getColor(
					R.color.title_dow));
			if (columnSelectIndex == i) {
				columnTextView.setSelected(true);
				columnTextView.setTextColor(Color.WHITE);
				mTitleBarView.setTitleText(userChannelList.get(i).getName());
			}
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						LinearLayout localLayout = (LinearLayout) mRadioGroup_content
								.getChildAt(i);
						View localView = localLayout.getChildAt(0);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewPager.setCurrentItem(i);
						}

					}
//					Toast.makeText(getApplicationContext(),
//							userChannelList.get(v.getId()).getName(),
//							Toast.LENGTH_SHORT).show();
				}
			});

			// mRadioGroup_content.addView(columnTextView, i, params);
			layout.addView(columnTextView, textParams);
			layout.addView(bgImageView, textParams);
			mRadioGroup_content.addView(layout, i, params);

		}
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	@SuppressLint("ResourceAsColor")
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			LinearLayout checkView = (LinearLayout) mRadioGroup_content
					.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		boolean ischeck;
		// 判断是否选中
		for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
			LinearLayout checkLayout = (LinearLayout) mRadioGroup_content
					.getChildAt(j);
			TextView checkView = (TextView) checkLayout.getChildAt(0);
			ImageView checkImage = (ImageView) checkLayout.getChildAt(1);
			if (j == tab_postion) {
				showImage(tab_postion);
				ischeck = true;
				mTitleBarView.setTitleText(userChannelList.get(j).getName());
				checkView.setTextColor(Color.WHITE);
				checkImage.setVisibility(View.GONE);
			} else {
				ischeck = false;
				checkView.setTextColor(getResources().getColor(
						R.color.title_dow));
			}
			checkView.setSelected(ischeck);

		}
	}

	private void showImage(final int postion) {
		GetUnReadRecvNumTask getUnReadRecvNumTask = new GetUnReadRecvNumTask(
				MainActivity.this, new TaskCallBack() {

					@Override
					public void onTaskFinshed(BaseBean result) {
						List<UnReadRecvNum> unReadRecvNumList = ((UnReadRecvNumList) result)
								.getUnReadRecvNumList();
						if (unReadRecvNumList.size() != 0) {
							for (int i = 0; i < unReadRecvNumList.size(); i++) {
								UnReadRecvNum num = unReadRecvNumList.get(i);
								int itemType = Integer.parseInt(num.getType());
								for (int j = 0; j < ChannelManage.defaultUserChannels
										.size(); j++) {
									ChannelItem ch = ChannelManage.defaultUserChannels
											.get(j);
									if (itemType == ch.getId()) {
										int itemID = ch.getOrderId();
										LinearLayout checkLayout = (LinearLayout) mRadioGroup_content
												.getChildAt(itemID);
										ImageView checkImage = (ImageView) checkLayout
												.getChildAt(Constant.FSI_TYPE_TZGG);
										checkImage.setVisibility(View.VISIBLE);
									}
									;
								}
							}
							LinearLayout checkLayout = (LinearLayout) mRadioGroup_content
									.getChildAt(postion);
							ImageView checkImage = (ImageView) checkLayout
									.getChildAt(Constant.FSI_TYPE_TZGG);
							checkImage.setVisibility(View.GONE);
						}

					}
				});
		getUnReadRecvNumTask.execute(AppContext.getApp().getToken());

	}

	/**
	 * 初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();// 清空
		int count = userChannelList.size();
		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			data.putString("text", userChannelList.get(i).getName());
			int id = userChannelList.get(i).getId();
			data.putInt("id", id);
			switch (id) {
			case Constant.FSI_TYPE_ZXDT:
				ZaixiaodongtaiFragment zxdtfragment = new ZaixiaodongtaiFragment();
				zxdtfragment.setArguments(data);
				fragments.add(zxdtfragment);
				break;

			case Constant.FSI_TYPE_TZGG:
				TongzhigonggaoFragment tzggfragment = new TongzhigonggaoFragment();
				tzggfragment.setArguments(data);
				fragments.add(tzggfragment);

				break;
			case Constant.FSI_TYPE_JTZY:
				JiatingzuoyeFragment jtzyfragment = new JiatingzuoyeFragment();
				jtzyfragment.setArguments(data);
				fragments.add(jtzyfragment);

				break;
			case Constant.FSI_TYPE_KQJL:
				KaoqinjiluFragment kqjlfragment = new KaoqinjiluFragment();
				kqjlfragment.setArguments(data);
				fragments.add(kqjlfragment);
				break;
			case Constant.FSI_TYPE_ZXDP:
				ZaixiaodianpingFragment zxdpfragment = new ZaixiaodianpingFragment();
				zxdpfragment.setArguments(data);
				fragments.add(zxdpfragment);
				break;
			case Constant.FSI_TYPE_JXJH:
				JiaoxuejihuaFragment jxjhfragment = new JiaoxuejihuaFragment();
				jxjhfragment.setArguments(data);
				fragments.add(jxjhfragment);
				break;
			case Constant.FSI_TYPE_BJSP:
				BanjishipuFragment bjspfragment = new BanjishipuFragment();
				bjspfragment.setArguments(data);
				fragments.add(bjspfragment);
				break;
			case Constant.FSI_TYPE_JZXS:
				JiazhangxinshengFragment jzxsfragment = new JiazhangxinshengFragment();
				jzxsfragment.setArguments(data);
				fragments.add(jzxsfragment);
				break;
			case Constant.FSI_TYPE_WDSC:
				WodeshoucangFragment wdscfragment = new WodeshoucangFragment();
				wdscfragment.setArguments(data);
				fragments.add(wdscfragment);
				break;
			default:
				break;
			}
		}
		NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		// mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			selectTab(position);
			new GetNewCommentsCountTask(mContext, badgeView).execute(AppContext
					.getApp().getToken(), "");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private long mExitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			if (layoutParams.leftMargin < 0) {
				new AsynMove().execute(SPEED);
				return false;
			}
//			else {
//				if ((System.currentTimeMillis() - mExitTime) > 2000) {
//					Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//					mExitTime = System.currentTimeMillis();
//				} else {
//					AppManager.getAppManager().AppExit(mContext);
//				}
//			}
//			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case CHANNELREQUEST:
			if (resultCode == CHANNELRESULT) {
				setChangelView();
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/***
	 * listview 正在滑动时执行.
	 */
	void doScrolling(float distanceX) {
		isScrolling = true;
		mScrollX += distanceX;// distanceX:向左为正，右为负

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
				.getLayoutParams();
		layoutParams.leftMargin -= mScrollX;
		layoutParams_1.leftMargin = window_width + layoutParams.leftMargin;
		if (layoutParams.leftMargin >= 0) {
			isScrolling = false;// 拖过头了不需要再执行AsynMove了
			layoutParams.leftMargin = 0;
			layoutParams_1.leftMargin = window_width;

		} else if (layoutParams.leftMargin <= -MAX_WIDTH) {
			// 拖过头了不需要再执行AsynMove了
			isScrolling = false;
			layoutParams.leftMargin = -MAX_WIDTH;
			layoutParams_1.leftMargin = window_width - MAX_WIDTH;
		}
		Log.v(TAG, "layoutParams.leftMargin=" + layoutParams.leftMargin
				+ ",layoutParams_1.leftMargin =" + layoutParams_1.leftMargin);

		layout_left.setLayoutParams(layoutParams);
		layout_right.setLayoutParams(layoutParams_1);
	}

	/***
	 * 获取移动距离 移动的距离其实就是layout_left的宽度
	 */
	void getMAX_WIDTH() {
		ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
		// 获取控件宽度
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth();
					MAX_WIDTH = layout_right.getWidth();
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
							.getLayoutParams();
					RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
							.getLayoutParams();
					ViewGroup.LayoutParams layoutParams_2 = lv_set
							.getLayoutParams();
					// 注意： 设置layout_left的宽度。防止被在移动的时候控件被挤压
					layoutParams.width = window_width;
					layout_left.setLayoutParams(layoutParams);

					// 设置layout_right的初始位置.
					layoutParams_1.leftMargin = window_width;
					layout_right.setLayoutParams(layoutParams_1);
					// 注意：设置lv_set的宽度防止被在移动的时候控件被挤压
					layoutParams_2.width = MAX_WIDTH;
					lv_set.setLayoutParams(layoutParams_2);

					Log.v(TAG, "MAX_WIDTH=" + MAX_WIDTH + "width="
							+ window_width);
					hasMeasured = true;
				}
				return true;
			}
		});

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		view = v;// 记录点击的控件

		// 松开的时候要判断，如果不到半屏幕位子则缩回去，
		if (MotionEvent.ACTION_UP == event.getAction() && isScrolling == true) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 缩回去
			if (layoutParams.leftMargin < -window_width / 2) {
				new AsynMove().execute(-SPEED);
			} else {
				new AsynMove().execute(SPEED);
			}
		}

		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {

		int position = lv_set.pointToPosition((int) e.getX(), (int) e.getY());
		if (position != ListView.INVALID_POSITION) {
			View child = lv_set.getChildAt(position
					- lv_set.getFirstVisiblePosition());
			if (child != null)
				child.setPressed(true);
		}

		mScrollX = 0;
		isScrolling = false;
		// 将之改为true，才会传递给onSingleTapUp,不然事件不会向下传递.
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	/***
	 * 点击松开执行
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// 点击的不是layout_left
		if (view != null && view == button_more_columns) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 左移动
			if (layoutParams.leftMargin >= 0) {
				new AsynMove().execute(-SPEED);
				lv_set.setSelection(0);// 设置为首位.
			} else {
				// 右移动
				new AsynMove().execute(SPEED);
			}
		} else if (view != null && view == layout_left) {
			RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			if (layoutParams.leftMargin < 0) {
				// 说明layout_left处于移动最左端状态，这个时候如果点击layout_left应该直接所以原有状态.(更人性化)
				// 右移动
				new AsynMove().execute(SPEED);
			}
		}

		return true;
	}

	/***
	 * 滑动监听 就是一个点移动到另外一个点. distanceX=后面点x-前面点x，如果大于0，说明后面点在前面点的右边及向右滑动
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// 执行滑动.
		doScrolling(distanceX);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0)// 整除
				times = MAX_WIDTH / Math.abs(params[0]);
			else
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// 有余数

			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * update UI
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
					.getLayoutParams();
			// 右移动
			if (values[0] > 0) {
				layoutParams.leftMargin = Math.min(layoutParams.leftMargin
						+ values[0], 0);
				layoutParams_1.leftMargin = Math.min(layoutParams_1.leftMargin
						+ values[0], window_width);
				Log.v(TAG, "layout_left右" + layoutParams.leftMargin
						+ ",layout_right右" + layoutParams_1.leftMargin);
			} else {
				// 左移动
				layoutParams.leftMargin = Math.max(layoutParams.leftMargin
						+ values[0], -MAX_WIDTH);
				layoutParams_1.leftMargin = Math.max(layoutParams_1.leftMargin
						+ values[0], window_width - MAX_WIDTH);
				Log.v(TAG, "layout_left左" + layoutParams.leftMargin
						+ ",layout_right左" + layoutParams_1.leftMargin);
			}
			layout_right.setLayoutParams(layoutParams_1);
			layout_left.setLayoutParams(layoutParams);

		}

	}

	@Override
	public void doFlingLeft(float distanceX) {
		// 执行滑动.
		doScrolling(distanceX);

	}

	@Override
	public void doFlingRight(float distanceX) {
		// 执行滑动.
		doScrolling(distanceX);

	}

	@Override
	public void doFlingOver(MotionEvent event) {
		// 松开的时候要判断，如果不到半屏幕位子则缩回去，
		if (MotionEvent.ACTION_UP == event.getAction() && isScrolling == true) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 缩回去
			if (layoutParams.leftMargin < -window_width / 2) {
				new AsynMove().execute(-SPEED);
			} else {
				new AsynMove().execute(SPEED);
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		// 只要没有滑动则都属于点击
		if (layoutParams.leftMargin == -MAX_WIDTH) {
			if (layoutParams.leftMargin < 0) {
				new AsynMove().execute(SPEED);
			}
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if (v == mTitleBarView.getBtnRight()
//				|| v == mTitleBarView.getRightTextView()
//				|| v == mTitleBarView.getRightClickableArea()) {
//			if (inviteMsgView != null && inviteMsgView.getBadgeCount() > 0) {
//				inviteMsgView.setBadgeCount(0);
//			}
//			Intent intent = new Intent(mContext, MyepeiTeacherActivity.class);
//			intent.putExtra("role", "0");
//			startActivity(intent);
//			this.overridePendingTransition(R.anim.slide_in_right,
//					R.anim.slide_out_left);
//		}
//	}
	private void mainDialog() {
		// 对话框样式
		dialog = new Dialog(this, R.style.Dialog_Fullscreen);
		// 添加对话框布局
		dialog.setContentView(R.layout.main_dialog);
		final ImageView iv2 = (ImageView) dialog
				.findViewById(R.id.imageView_main_splash);
		iv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(mContext);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(mContext);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

}
