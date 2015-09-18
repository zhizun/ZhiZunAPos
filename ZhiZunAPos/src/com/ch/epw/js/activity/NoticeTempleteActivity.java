package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.ColumnHorizontalScrollView;
import com.ch.epw.view.MyListView;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.view.MyListView.MyListViewFling;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewNoticeTempleteAdapter;
import com.zhizun.pos.adapter.ListViewNoticeTempleteAdapter.Callback;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.ChannelItem;
import com.zhizun.pos.bean.NoticeTemplete;
import com.zhizun.pos.bean.NoticeTempleteList;
import com.zhizun.pos.bean.Result;

/**
 * 通知模板创建人：李林中 创建日期：2014-12-15 上午9:48:12 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NoticeTempleteActivity extends BaseActivity implements
		OnTouchListener, GestureDetector.OnGestureListener,
		OnItemClickListener, MyListViewFling, OnClickListener, Callback {

	private Context context;
	String jsonString;
	Result result;
	NoticeTempleteList noticeTempleteList;
	ListViewNoticeTempleteAdapter listViewNoticeTempleteAdapter;
	List<NoticeTemplete> list;
	Integer dataCount;
	Integer noticeTagIndex;// 标签索引
	String noticeTag;
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

	private String TAG = "jj";

	private View view = null;// 点击的view

	/** 头部标题栏 */
	private TitleBarView mTitleBarView;

	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	LinearLayout ll_more_columns;
	RelativeLayout rl_column;
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

	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;
	/** 调整返回的RESULTCODE */
	public final static int CHANNELRESULT = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_templete);
		context = this;
		mScreenWidth = BaseTools.getWindowsWidth(this);
		mItemWidth = mScreenWidth / 4;// 一个Item宽度为屏幕的1/7
		initView();
		Init2View();
		// initSlidingMenu();
	}

	/***
	 * 初始化view 自定义左右滑动菜单
	 */
	void Init2View() {
		layout_left = (LinearLayout) findViewById(R.id.layout_left);
		layout_right = (LinearLayout) findViewById(R.id.layout_right);
		// iv_set = (ImageView) findViewById(R.id.iv_set);
		lv_set = (MyListView) findViewById(R.id.lv_set);

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
		mTitleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		mTitleBarView.setTitleText(R.string.notice_templete);

		mTitleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		mTitleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) findViewById(R.id.rl_column);
		button_more_columns = (ImageView) findViewById(R.id.button_more_columns);
		button_more_columns.setOnTouchListener(this);
		shade_left = (ImageView) findViewById(R.id.shade_left);
		shade_right = (ImageView) findViewById(R.id.shade_right);
		mPullListView = (PullToRefreshListView) findViewById(R.id.prl_notice_templete);
		setChangelView();
	}

	private void content() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件

		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new GetNoticeTempleteTask().execute(1 + "",
						Constant.LOADDATACOUNT + "", noticeTag);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				int page = (int) (Math.ceil(dataCount
						/ (double) Constant.LOADDATACOUNT));

				mCurPage = mCurPage + 1;
				Log.i(TAG, "mCurPage=" + mCurPage);
				if (mCurPage > page) {
					mCurPage = page;
					hasMoreData = false;
					mPullListView.setHasMoreData(hasMoreData);
					return;
				}

				Log.i(TAG, "mCurPage2=" + mCurPage);
				Log.i(TAG, "dataCount=" + dataCount);
				Log.i(TAG, "page=" + page);
				new GetNoticeTempleteTask().execute(mCurPage + "",
						Constant.LOADDATACOUNT + "", noticeTag);
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------
	}

	/**
	 * 当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		new AcceptInviteTask().execute();
	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {

		try {

			if (null != jsonString && !jsonString.equals("")) {
				JSONObject jsonObject = new JSONObject(jsonString);
				if (jsonObject.getString("status").equals("0")) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						String item = jsonArray.getString(i);
						Log.i(TAG, "item" + i + "=" + item);
						ChannelItem channelItem = new ChannelItem(i + 1, item,
								i + 1, 0);
						userChannelList.add(channelItem);
					}
					// 给标签栏赋值
					initTabColumn();
					// 给侧边栏赋值
					initSideColumn();

				} else {
					UIHelper.ToastMessage(context,
							jsonObject.getString("statusMessage"));
					return;
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initSideColumn() {
		String items[] = new String[userChannelList.size()];
		for (int i = 0; i < userChannelList.size(); i++) {
			items[i] = userChannelList.get(i).getName();
		}
		if (null != items) {
			lv_set.setAdapter(new ArrayAdapter<String>(this, R.layout.item,
					R.id.tv_item, items));
		}
	}

	/**
	 * 初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = userChannelList.size();
		Log.i("TAG", "userChannelList.count=" + count);
		mColumnHorizontalScrollView.setParam(this, mScreenWidth,
				mRadioGroup_content, shade_left, shade_right, ll_more_columns,
				rl_column);
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			// TextView localTextView = (TextView)
			// mInflater.inflate(R.layout.column_radio_item, null);
			// TextView columnTextView = new TextView(this);
			View columnTextView = View.inflate(this,
					R.layout.layout_common_text_titile, null);
			TextView tv_layout_common_text_title = (TextView) columnTextView
					.findViewById(R.id.tv_layout_common_text_title);
			final View v_layout_common_text_divider = columnTextView
					.findViewById(R.id.v_layout_common_text_divider);
			// columnTextView.setTextAppearance(this,
			// R.style.top_category_scroll_view_item_text);
			// localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			// columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setId(i);
			tv_layout_common_text_title.setText(userChannelList.get(i)
					.getName());
			tv_layout_common_text_title.setSingleLine();

			// columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
			if (columnSelectIndex == i) {
				v_layout_common_text_divider.setVisibility(View.VISIBLE);
				noticeTag = userChannelList.get(i).getName();
				noticeTagIndex = i;
			}
			// 加载listview
			content();
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						View localView = mRadioGroup_content.getChildAt(i);
						if (localView != v)
							localView.findViewById(
									R.id.v_layout_common_text_divider)
									.setVisibility(View.INVISIBLE);
						else {
							localView.findViewById(
									R.id.v_layout_common_text_divider)
									.setVisibility(View.VISIBLE);
						}
					}
					// 根据点击条目进行相应的切换操作
					Toast.makeText(getApplicationContext(),
							userChannelList.get(v.getId()).getName(),
							Toast.LENGTH_SHORT).show();
					noticeTag = userChannelList.get(v.getId()).getName();
					noticeTagIndex = v.getId();
					content();

				}
			});
			mRadioGroup_content.addView(columnTextView, i, params);
		}
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		// 判断是否选中
		for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
			View columnTextView = mRadioGroup_content.getChildAt(j);
			if (j == tab_postion) {
				columnTextView.findViewById(R.id.v_layout_common_text_divider)
						.setVisibility(View.VISIBLE);
				columnTextView.performClick();

			} else {
				columnTextView.findViewById(R.id.v_layout_common_text_divider)
						.setVisibility(View.INVISIBLE);
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			selectTab(position);
		}
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 获取模版标签 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class AcceptInviteTask extends AsyncTask<Void, Void, String> {
		AppException e;

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				jsonString = AppContext.getApp().getTplTagList();
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				jsonString = null;
			}
			return jsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (null != result) {
				initColumnData();

			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}

	/**
	 * 获取模版列表 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetNoticeTempleteTask extends
			AsyncTask<String, Void, NoticeTempleteList> {
		AppException e;

		@Override
		protected NoticeTempleteList doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				noticeTempleteList = AppContext.getApp().queryNoticeTplList(
						params[0], params[1], params[2]);

			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				noticeTempleteList = null;
			}
			return noticeTempleteList;
		}

		@Override
		protected void onPostExecute(NoticeTempleteList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(noticeTempleteList
							.getDataCount());
					if (list != null && list.size() > 0) {
						list.addAll(result.getNoticeTempleteList());
					} else {
						list = result.getNoticeTempleteList();
					}
					if (dataCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
						listViewNoticeTempleteAdapter = new ListViewNoticeTempleteAdapter(
								getApplicationContext(), list,
								NoticeTempleteActivity.this);
						// 得到实际的ListView
						mListView.setAdapter(listViewNoticeTempleteAdapter);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					}else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}

					if (list.size() <= Constant.LOADDATACOUNT) {
						listViewNoticeTempleteAdapter = new ListViewNoticeTempleteAdapter(
								getApplicationContext(), list,
								NoticeTempleteActivity.this);
						// 得到实际的ListView
						mListView.setAdapter(listViewNoticeTempleteAdapter);
					} else {
						listViewNoticeTempleteAdapter.notifyDataSetChanged();
					}
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void click(View v) {
		if (v.getId() == R.id.btn_list_notice_templete_send) {
			int position = (Integer) v.getTag();
			// 将内容传回发送页面
			Intent intent = getIntent();
			intent.putExtra("noticeTempleteContent", list.get(position)
					.getContent());
			NoticeTempleteActivity.this.setResult(
					Constant.SEND_NOTICE_SELCET_TEMPLETE, intent);
			NoticeTempleteActivity.this.finish();
			backAnim();
		}
		if (v.getId() == R.id.btn_list_notice_templete_delete) {
			int position = (Integer) v.getTag();
			// 删除通知
			new DelNoticeTempleteTask(position).execute(AppContext.getApp()
					.getToken(), list.get(position).getTemplateId());

		}

	}

	/**
	 * 删除通知模板 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class DelNoticeTempleteTask extends AsyncTask<String, Void, Result> {
		int position;
		AppException e;

		public DelNoticeTempleteTask(int position) {
			this.position = position;
			Log.i(TAG, "position=" + position);
		}

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().delNoticeTpl(params[0], params[1]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "您已成功删除该模板");
					list.remove(position);
					if (list.size() <= 0) {
						userChannelList.remove(noticeTagIndex);
						// 给标签栏赋值
						initTabColumn();
						// 给侧边栏赋值
						initSideColumn();
					} else {
						listViewNoticeTempleteAdapter.notifyDataSetChanged();
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
}
