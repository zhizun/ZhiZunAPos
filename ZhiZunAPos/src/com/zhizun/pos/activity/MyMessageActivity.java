package com.zhizun.pos.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

import com.zhizun.pos.R;
import com.ch.epw.task.SystemMessageTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.fragment.mymessage.MyMessageFragment;
import com.zhizun.pos.fragment.mymessage.SystemMessageListFragment;

/**
 * 我的消息
 * @author lilinzhong
 *
 * 2015-7-14上午10:27:11
 */
public class MyMessageActivity extends FragmentActivity {
	
	TitleBarView titleBarView;
	private RadioGroup radioGroup;
	private FragmentTabHost mTabHost;
	private final Class[] fragments = { SystemMessageListFragment.class,MyMessageFragment.class};
	private ImageView tv_message;
	private int pageIndex;
	String activityCalledOnBack;	//推送消息时如果携带该参数，则跳转到对应的activity
	private ImageView tv_my_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_message);
		Intent intent = getIntent();
		pageIndex = intent.getIntExtra("pageIndex", 0);
		activityCalledOnBack = intent.getStringExtra("activityCalledOnBack");

		initView();
		//注册广播，是否要显示new
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.system.Message");
		registerReceiver(messageReceiver, filter);
		//条用系统消息数量接口
		new SystemMessageTask(this, new TaskCallBack() {}).execute();
	}
	BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int messageNum=intent.getIntExtra("messageNum",-1);
			int feedbackNumm=intent.getIntExtra("feedbackNum",-1);
			if (messageNum > 0) {
				tv_message.setVisibility(View.VISIBLE);
			}else {
				tv_message.setVisibility(View.GONE);
			}
			if (feedbackNumm>0) {
				tv_my_message.setVisibility(View.VISIBLE);
			}else {
				tv_my_message.setVisibility(View.GONE);
			}
		}
	};

	private void initView() {
		
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_message);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_mymessage);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
					tv_my_message.setVisibility(View.GONE);
					break;
				}
			}
		});
		
		tv_message=(ImageView) findViewById(R.id.tv_message);
		tv_my_message=(ImageView)findViewById(R.id.tv_my_message);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_content);
		int count = fragments.length;  
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容  
            TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");  
            // 将Tab按钮添加进Tab选项卡中  
            mTabHost.addTab(tabSpec, fragments[i], null);  
        }

        ((RadioButton)radioGroup.getChildAt(pageIndex)).toggle();
	}

	@Override
	public void onBackPressed() {
		if (activityCalledOnBack != null && !"".equals(activityCalledOnBack)) {
			AppManager.getAppManager().startActivity(MyMessageActivity.this, activityCalledOnBack);
		}
		MyMessageActivity.this.finish();
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(messageReceiver);
		super.onDestroy();
	}
}
