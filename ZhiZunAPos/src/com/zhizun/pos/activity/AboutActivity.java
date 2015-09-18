package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ListView;

import com.ch.epw.js.activity.DynamicNewCommentsDetailActivity;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

public class AboutActivity extends BaseActivity {

	protected static final String TAG = DynamicNewCommentsDetailActivity.class
			.getName();
	Context context;
	private TitleBarView titleBarView;
	private WebView webView;
	private String htmlUrl;
	private String titleText;
	private Intent inte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		context = this;
		inte=getIntent();
		htmlUrl=inte.getStringExtra("html");
		titleText=inte.getStringExtra("titleName");
		initView();
		initDate();

	}

	private void initDate() {
//		webView.loadUrl(URLs.URL_API_HOST + "/mobile/abuotEp.html");
		webView.loadUrl(URLs.URL_API_HOST + htmlUrl);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		backAnim();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_new_comment_detail);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(titleText);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		webView = (WebView) findViewById(R.id.webview);
	}
}
