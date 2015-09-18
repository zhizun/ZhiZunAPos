package com.zhizun.pos.activity;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.CourseCountyAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.CourseRegions;
/**
 * 区域列表
 * @author lilinzhong
 *
 * 2015-7-9上午9:44:40
 */
public class CourseCountyListViewActivity extends BaseActivity {
	private ListView lv_county;
	ArrayList<CourseRegions> list;
	private TitleBarView titleBarView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_county);
		list=(ArrayList<CourseRegions>) getIntent().getSerializableExtra("list");
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_mycourse);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_course_m);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		lv_county=(ListView) findViewById(R.id.lv_county);
		CourseCountyAdapter adapter=new CourseCountyAdapter(this, list);
		lv_county.setAdapter(adapter);
		lv_county.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
	            intent.putExtra("regionBean", list.get(position));
	            // 通过调用setResult方法返回结果给前一个activity。  
	            setResult(2, intent); 
	            finish();
			}
		});
	}
}
