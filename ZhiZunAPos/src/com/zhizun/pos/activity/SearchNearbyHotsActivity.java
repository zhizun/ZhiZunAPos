package com.zhizun.pos.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import com.zhizun.pos.R;
import com.zhizun.pos.fragment.nearby.CourseListFragment;
import com.zhizun.pos.fragment.nearby.OrgListFragment;
import com.ch.epw.view.TitleBarView;
/**
 * 附近页面
 * @author lilinzhong
 *
 * 2015-7-14上午10:27:11
 */
public class SearchNearbyHotsActivity extends FragmentActivity {
	
	public static String DEF_SEARCH_RADIUS = "100000";
	
	TitleBarView titleBarView;
	private RadioGroup radioGroup;
	private FragmentTabHost mTabHost;
	private final Class[] fragments = { CourseListFragment.class, OrgListFragment.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_nearby_hots);
		initView();
	}

	private void initView() {
		
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_nearby);
		
		radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiobutton_cour:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.radiobutton_org:
					mTabHost.setCurrentTab(1);
					break;
				default:
					break;
				}
			}
		});

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_content);
		int count = fragments.length;  
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容  
            TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");  
            // 将Tab按钮添加进Tab选项卡中  
            mTabHost.addTab(tabSpec, fragments[i], null);  
        }

        ((RadioButton)radioGroup.getChildAt(0)).toggle();
	}
}
