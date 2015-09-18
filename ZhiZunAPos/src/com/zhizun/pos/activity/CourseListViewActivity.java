package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ch.epw.helper.MyPullToRefreshListHelper;
import com.ch.epw.task.QueryCourseListTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.LocationUtils;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.MenuPopWindow.OnMenuItemCheckedListener;
import com.zhizun.pos.activity.SortConditionWindow.OnSortItemCheckedListener;
import com.zhizun.pos.adapter.CourseListViewAdapter;
import com.zhizun.pos.adapter.CourseSpinerPopWindow;
import com.zhizun.pos.adapter.OrgListViewAdapter;
import com.zhizun.pos.adapter.CourseSpinerAdapter.IOnItemSelectListener;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.CourseListItemList;
import com.zhizun.pos.bean.CourseListViewBean;
import com.zhizun.pos.bean.CourseRegions;
import com.zhizun.pos.bean.SortTreeNode;
import com.zhizun.pos.bean.SortTreeNodeWrapper;

/**
 * 搜索课程显示列表
 * 
 * @author lilinzhong
 *
 *         2015-6-17上午11:42:14
 */
public class CourseListViewActivity extends BaseActivity implements
		IOnItemSelectListener, OnClickListener,
		OnSortItemCheckedListener, OnMenuItemCheckedListener {
	private Context context;
	private TextView mTView;
	private List<String> nameList = new ArrayList<String>();
	private RelativeLayout rl_search_bar;

	private FrameLayout fl_left_return;
	private FrameLayout fl_right_nagv;

	private RadioGroup course_contidon;
	private RadioButton rb_sort; // 排序按钮。显示下拉排序时要显示在此按钮下，需要初始化
	private RadioButton rb_filter_category; // 分类筛选
	private RadioButton rb_filter_area; // 区域筛选

	private ArrayList<CourseListItemList> listItems;
	private ArrayList<CourseRegions> metaDataList;
	private EditText et_keyword;
	private String keyWord;
	private String searchType;
	private LinearLayout ll_search_switch;

	private CourseSpinerPopWindow mSpinerPopWindow;
	private SortConditionWindow sortAreaWindow;
	private SortConditionWindow sortCategoryWindow;
	private MenuPopWindow orderMenuWindow;

	private static MyPullToRefreshListHelper mvHelper;
	// 调用QueryCourseListTask时传递的参数列表
	String[] searchParams = new String[SearchParaEnum.values().length];
	// 查询删选条件
	Map<String, String> filterMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_listview);
		context = this;
		keyWord = getIntent().getStringExtra("keyword");
		if (keyWord == null) {
			keyWord = "";
		}
		searchType = getIntent().getStringExtra("searchType");
		if (searchType == null) {
			searchType = "0";
		}
		if (!searchType.equals("0") && !searchType.equals("1")) {
			searchType = "0";
		}

		String[] names = getResources().getStringArray(R.array.hero_name);
		for (int i = 0; i < names.length; i++) {
			nameList.add(names[i]);
			mSpinerPopWindow = new CourseSpinerPopWindow(this);
			mSpinerPopWindow.refreshData(nameList, 0);
			mSpinerPopWindow.setItemListener(this);
		}

		// 禁止自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initView();
	}

	private void initView() {
		mTView = (TextView) findViewById(R.id.tv_search_type);
		rl_search_bar = (RelativeLayout) findViewById(R.id.rl_search_bar);
		et_keyword = (EditText) findViewById(R.id.et_keyword);
		fl_left_return = (FrameLayout) findViewById(R.id.fl_left_return);
		fl_right_nagv = (FrameLayout) findViewById(R.id.fl_right_nagv);
		ll_search_switch=(LinearLayout) findViewById(R.id.ll_search_switch);
		if (null != keyWord && !keyWord.equals("")) {
			et_keyword.setText(keyWord);
		}
		course_contidon = (RadioGroup) findViewById(R.id.course_contidon);
		rb_filter_category = (RadioButton) findViewById(R.id.rb_filter_category);
		rb_filter_area = (RadioButton) findViewById(R.id.rb_filter_area);
		rb_sort = (RadioButton) findViewById(R.id.rb_sort);

		// RadioGroup上的setOnCheckedChangeListener只能监听到组中RadioButton状态变化的操作，无法实现两次连续点击同一个按钮
		// 需要在每个RadioButton上增加onClickListener
		rb_filter_category.setOnClickListener(this);
		rb_filter_area.setOnClickListener(this);
		rb_sort.setOnClickListener(this);

		setHero(Integer.parseInt(searchType));

		orderMenuWindow = new MenuPopWindow(context,
				SortTreeNodeWrapper
						.parse(Constant.coureListOrderOptionSettings));
		orderMenuWindow.setOnMenuItemCheckedListener(this);

		et_keyword.setCursorVisible(false);
		et_keyword.setFocusable(false);
		et_keyword.setOnClickListener(this);
		rl_search_bar.setOnClickListener(this);
		fl_left_return.setVisibility(View.VISIBLE);
		fl_left_return.setOnClickListener(this);
		fl_right_nagv.setVisibility(View.GONE);

		listItems = new ArrayList<CourseListItemList>();
		mPullListView = (PullToRefreshListView) this
				.findViewById(R.id.ll_marketing_activities);
		// 将Activity、mPullListView、listItems绑定
		mvHelper = new MyPullToRefreshListHelper(context, mPullListView,
				listItems);
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(null);
		mListView.setDividerHeight(8);
		if (searchType.equals("0")) { // 搜课程
			mListView.setAdapter(new CourseListViewAdapter(context, listItems));
		} else if (searchType.equals("1")) { // 搜机构
			mListView.setAdapter(new OrgListViewAdapter(context, listItems));
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object item = mListView.getAdapter().getItem(position);
				if (item != null) {
					CourseListItemList courseItem = (CourseListItemList) item;
					if (searchType.equals("0")) {
						Intent intent = new Intent(context,
								CourseDetailActivity.class);
						intent.putExtra("courId", courseItem.getCourId());
						intent.putExtra("ownOrgId", courseItem
								.getCourseOrgBean().getOrgId());
						startActivity(intent);
						intoAnim();
					} else {
						Intent intent = new Intent(context,
								OrgIntroDetailActivity.class);
						intent.putExtra("orgId", courseItem.getOrgId());
						intent.putExtra("category", courseItem.getCategory());
						startActivity(intent);
						intoAnim();
					}
				}
			}
		});

		TaskCallBack getDataCallBack = new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean result) {
				CourseListViewBean marketList = (CourseListViewBean) result;
				if (marketList != null && "0".equals(marketList.getStatus())) {
					mvHelper.setPageCount(marketList.getDataCount());
					if (Integer.parseInt(marketList.getDataCount()) > 0) {
						listItems.addAll(marketList.getCourseItemList());
						metaDataList = marketList.getCourseRegionsList();

						if (sortAreaWindow == null) {
							sortAreaWindow = new SortConditionWindow<ListView>(
									CourseListViewActivity.this,
									R.layout.sort_area_window_layout,
									SortTreeNodeWrapper.parse(metaDataList));
							sortAreaWindow
									.setOnSortItemCheckedListener(CourseListViewActivity.this);
						}

						if (sortCategoryWindow == null
								&& marketList.getCateNodeList() != null) {
							sortCategoryWindow = new SortConditionWindow<GridView>(
									CourseListViewActivity.this,
									R.layout.sort_category_window_layout,
									marketList.getCateNodeList());
							sortCategoryWindow
									.setOnSortItemCheckedListener(CourseListViewActivity.this);
						}
					}
				}
				// 必须在每次task执行结束调用mvController.refreshEnd()
				mvHelper.refreshEnd();
			}
		};

		
		searchParams[SearchParaEnum.keyWord.ordinal()] = keyWord;
		searchParams[SearchParaEnum.searchType.ordinal()] = searchType;
		BDLocation location = LocationUtils.getLastKnownLocation();
		if(location != null){
			searchParams[SearchParaEnum.lat.ordinal()] = String.valueOf(location.getLatitude());
			searchParams[SearchParaEnum.lng.ordinal()] = String.valueOf(location.getLongitude());
		}
		// 默认筛选条件为3公里内
		searchParams[SearchParaEnum.filter.ordinal()] = "distance:0,3000";
		rb_filter_area.setChecked(true);
		// 默认排序距离优先
		searchParams[SearchParaEnum.sort.ordinal()] = "distance:1";

		mvHelper.setGetDataCallBack(QueryCourseListTask.class, searchParams,
				getDataCallBack);
		mvHelper.readyForRefresh();
	}

	private void setHero(int pos) {
		if (pos >= 0 && pos <= nameList.size()) {
			String value = nameList.get(pos);
			if (value.equals("课程")) {
				searchType = "0";
			} else {
				searchType = "1";
			}
			mTView.setText(value);
		}
	}

	private void showSortCategoryWindow() {
		if (sortCategoryWindow != null) {
			sortCategoryWindow.showAsDropDown(course_contidon);
		}
		hidePopWindow(sortAreaWindow, orderMenuWindow);
	}

	private void showSortAreaWindow() {
		if (sortAreaWindow != null) {
			sortAreaWindow.showAsDropDown(course_contidon);
		}
		hidePopWindow(sortCategoryWindow, orderMenuWindow);
	}

	private void showSpinWindow() {
		if (mSpinerPopWindow != null) {
			mSpinerPopWindow.setWidth(ll_search_switch.getWidth());
			mSpinerPopWindow.showAsDropDown(ll_search_switch);
		}
	}

	private void showOrderMenuWindow() {
		if (orderMenuWindow != null) {
			orderMenuWindow.showAsDropDown(rb_sort);
		}
		hidePopWindow(sortCategoryWindow, sortAreaWindow);
	}

	private void hidePopWindow(PopupWindow... popwins) {
		if (popwins != null) {
			for (int k = 0; k < popwins.length; k++) {
				PopupWindow win = popwins[k];
				if (win != null && win.isShowing()) {
					win.dismiss();
				}
			}
		}
	}

	private String getFormatedFilter(Map<String, String> filterMap) {
		StringBuffer filterBuffer = new StringBuffer();
		for (String key : filterMap.keySet()) {
			filterBuffer.append(key).append(":").append(filterMap.get(key))
					.append("|");
		}
		return filterBuffer.toString();
	}

	@Override
	public void onItemClick(int pos) {
		setHero(pos);
	}

	@Override
	public void onBackPressed() {
		if ((sortCategoryWindow != null && sortCategoryWindow.isShowing())
				|| (sortAreaWindow != null && sortAreaWindow.isShowing())
				|| (orderMenuWindow != null && orderMenuWindow.isShowing())) {
			hidePopWindow(sortCategoryWindow, sortAreaWindow, orderMenuWindow);
			return;
		}

		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_search_bar:
		case R.id.ll_search_switch:
			showSpinWindow();
			break;
		case R.id.et_keyword:
			Intent intent = new Intent();
			intent.setClass(this, SearchHistoryActivity.class);
			intent.putExtra("searchType", searchType);
			startActivity(intent);
			finish();
			intoAnim();
			break;
		case R.id.fl_left_return:
			onBackPressed();
			break;
		case R.id.rb_sort:
			showOrderMenuWindow();
			break;
		case R.id.rb_filter_category:
			showSortCategoryWindow();
			break;
		case R.id.rb_filter_area:
			showSortAreaWindow();
			break;
		default:
			break;
		}
	}


	public static enum SearchParaEnum {
		keyWord, lat, lng, sort, filter, searchType
	}

	@Override
	public void onSortItemChecked(SortTreeNode treeNode) {
		hidePopWindow(sortCategoryWindow, sortAreaWindow);
		if (treeNode == null) {
			filterMap.clear();
			rb_filter_area.setChecked(false);
			rb_filter_category.setChecked(false);
		} else {
			// 按照附近距离过滤
			if (Constant.SORT_NODE_TYPE_NEAR_CODE.equals(treeNode.getType())) {
				BDLocation location = LocationUtils.getLastKnownLocation();
				if (location != null) {
					searchParams[SearchParaEnum.lat.ordinal()] = String
							.valueOf(location.getLatitude());
					searchParams[SearchParaEnum.lng.ordinal()] = String
							.valueOf(location.getLongitude());
					// 距离与区县条件互斥，先清除addr.county条件
					filterMap.remove("addr.county");
					String filerVal = "0," + treeNode.getItemId();
					filterMap.put("distance", filerVal);
					// searchParams[SearchParaEnum.sort.ordinal()] = "distance:1";
				}
			}
			// 按照区县代码过滤
			else if (Constant.SORT_NODE_TYPE_COUNTY_CODE.equals(treeNode
					.getType())) {
				// 距离与区县条件互斥，先清除distance条件
				filterMap.remove("distance");
				filterMap.put("addr.county", treeNode.getItemId());
			}
			// 按课程分类过滤
			else if (Constant.SORT_NODE_TYPE_CATEGORY_CODE.equals(treeNode
					.getType())) {
				// 如果parentNode不为空，表示节点为二级分类（目前暂时不使用一级分类当条件 2015年8月21日09:23:53）
				if (treeNode.getParentNode() != null) {
					filterMap.put("cat.catSecId", treeNode.getItemId());
				} else {

				}
			}
		}

		searchParams[SearchParaEnum.filter.ordinal()] = getFormatedFilter(filterMap);
		mvHelper.manualForceRefresh();
	}

	@Override
	public void OnMenuItemChecked(SortTreeNode treeNode) {
		orderMenuWindow.dismiss();
		// order为负，表示倒序；order为正，表示为正序
		String order = "-1";
		String orderName = treeNode.getItemId();
		// 以距离排序时，正序排列
		if ("distance".equals(orderName)) {
			order = "1";
		}
		String orderOption = orderName + ":" + order;
		searchParams[SearchParaEnum.sort.ordinal()] = orderOption;
		mvHelper.manualForceRefresh();
	}

}
