package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.CourseSpinerPopWindow;
import com.zhizun.pos.adapter.CourseSpinerAdapter.IOnItemSelectListener;
import com.zhizun.pos.base.BaseActivity;
import com.ch.epw.utils.SpUtil;

/**
 * 
 * @author 李林中 课程搜索历史界面
 */
public class SearchHistoryActivity extends BaseActivity implements
		IOnItemSelectListener, OnClickListener {
	private final int historyListLen = 5;
	private final String spSearchListHistoryTag = "spSearchListHistory";
	private TextView mTView;
	private List<String> nameList = new ArrayList<String>();
	private CourseSpinerPopWindow mSpinerPopWindow;
	SharedPreferences sp;

	private LinearLayout ll_search_switch;
	private FrameLayout fl_right_nagv;
	private FrameLayout fl_btn_search;
	private ImageButton btn_search;
	private EditText et_keyWord;
	private Button bt_clear_history;

	private ListView lv_course_history;
	private ArrayList<String> keyWordArrayList = new ArrayList<String>(historyListLen);

	String searchType;
	int serchTagPos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		searchType = getIntent().getStringExtra("searchType");
		if (searchType == null || searchType.equals("")) {
			searchType = "0";
		}
		if (searchType.equals("0") || searchType.equals("1")) {
			serchTagPos = Integer.parseInt(searchType);
		}

		sp = SpUtil.getInstance().getSharePerference(this, "course");
		String courseString = sp.getString(spSearchListHistoryTag, "");
		if (!courseString.equals("")) {
			List<String> resultList = Arrays.asList(courseString.split(","));
			keyWordArrayList.addAll(resultList);
		}
		initView();
		String[] names = getResources().getStringArray(R.array.hero_name);
		for (int i = 0; i < names.length; i++) {
			nameList.add(names[i]);
		}
		mTView.setText(names[serchTagPos]);
		mSpinerPopWindow = new CourseSpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
	}

	private void initView() {
		mTView = (TextView) findViewById(R.id.tv_search_type);
		ll_search_switch = (LinearLayout) findViewById(R.id.ll_search_switch);
		fl_right_nagv = (FrameLayout) findViewById(R.id.fl_right_nagv);
		et_keyWord = (EditText) findViewById(R.id.et_keyword);
		fl_btn_search = (FrameLayout) findViewById(R.id.fl_btn_search);
		btn_search = (ImageButton) findViewById(R.id.btn_search);
		bt_clear_history = (Button) findViewById(R.id.bt_clear_history);
		lv_course_history = (ListView) findViewById(R.id.lv_course_history);

		if (keyWordArrayList.size() > 0) {
			ArrayList<String> showList = new ArrayList<String>(keyWordArrayList);
			//显示时倒序
			Collections.reverse(showList);
			String[] keyWordList = (String[]) showList.toArray(new String[showList.size()]);
			lv_course_history.setAdapter(new ArrayAdapter<String>(this,
					R.layout.key_word_list_text, keyWordList));
			lv_course_history.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Object item = parent.getAdapter().getItem(position);
					if(item != null){
						String keyWord = (String)item;
						et_keyWord.setText(keyWord);
						SearchHistoryActivity.this.onClick(btn_search);
					}
				}
				
			});
		} else {
			bt_clear_history.setVisibility(View.GONE);
		}

		ll_search_switch.setOnClickListener(this);
		((TextView) ((ViewGroup) fl_right_nagv).getChildAt(0)).setText("搜索");
		fl_right_nagv.setOnClickListener(this);
		fl_btn_search.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		bt_clear_history.setOnClickListener(this);
	}

	private void setHero(int pos) {
		if (pos >= 0 && pos <= nameList.size()) {
			String value = nameList.get(pos);
			mTView.setText(value);
			searchType = String.valueOf(pos);
		}
	}

	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(ll_search_switch.getWidth());
		mSpinerPopWindow.showAsDropDown(ll_search_switch);
	}

	@Override
	public void onItemClick(int pos) {
		setHero(pos);
	}

	private void refreshSearchListHistory(){
		String keyWord = et_keyWord.getText().toString();
		if(keyWord != null && !keyWord.equals("")){
			//先将保存过的关键字删除（如果有）
			keyWordArrayList.remove(keyWord);
			//已保存的关键字超过 historyListLen 个，清除列表头
			if(keyWordArrayList.size() >= historyListLen){
				keyWordArrayList.remove(0);
			}
			//将关键字加入列表
			keyWordArrayList.add(keyWord);

			String keyWorString = keyWordArrayList.toString();
			keyWorString = keyWorString.replaceAll("[\\[\\]\\s]", "");
			SpUtil.setStringSharedPerference(sp, spSearchListHistoryTag,
					keyWorString);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_search_switch:
			showSpinWindow();
			break;

		case R.id.fl_right_nagv:
		case R.id.btn_search:
		case R.id.fl_btn_search:
			//刷新搜索历史
			refreshSearchListHistory();
			String keyWord = et_keyWord.getText().toString();
			Intent intent = new Intent();
			intent.setClass(SearchHistoryActivity.this,
					CourseListViewActivity.class);
			intent.putExtra("keyword", keyWord);
			intent.putExtra("searchType", searchType);
			startActivity(intent);
			intoAnim();
			finish();
			break;
		case R.id.bt_clear_history:
			SpUtil.setStringSharedPerference(sp, spSearchListHistoryTag, "");
			keyWordArrayList.clear();
			String[] nr = new String[] {};
			lv_course_history.setAdapter(new ArrayAdapter<String>(this,
					R.layout.key_word_list_text, nr));
			bt_clear_history.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// keyWordList.clear();
	}
}
