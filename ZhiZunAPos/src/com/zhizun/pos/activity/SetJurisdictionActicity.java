package com.zhizun.pos.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.TitleBarView;

public class SetJurisdictionActicity extends BaseActivity {
	Context context;
	private TitleBarView titleBarView;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_jurisdiction);
		context = this;
		initView();
		initDate();
	}
	private void initDate() {
//		webView.loadUrl(URLs.URL_API_HOST + "/mobile/abuotEp.html");
		webView.loadUrl(URLs.URL_API_HOST +"/enable_contact_access_andorid.html");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		backAnim();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_set_juris_detail);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText("访问通讯录");
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
