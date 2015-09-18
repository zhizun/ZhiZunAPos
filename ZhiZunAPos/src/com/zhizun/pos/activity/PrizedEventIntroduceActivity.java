package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MyepePrizeBean;
import com.zhizun.pos.bean.PrizedBeanWrapper;
import com.zhizun.pos.bean.UserLogin;

/**
 * 有奖活动 具体内容详情页
 * 
 * @author 李林中
 * 
 */
public class PrizedEventIntroduceActivity extends BaseActivity implements
		OnClickListener {
	private WebView prized_webview;
	private ImageView image_back;
	private LinearLayout ly_share;
	private LinearLayout ly_participation;
	private String eventId;
	UserLogin userLogin;
	Context context;
	MyepePrizeBean myepePrizedBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prized_introduce);
		context = this;
		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		Log.i(TAG, "eventId=" + eventId);
		prized_webview = (WebView) findViewById(R.id.prized_webview);
		image_back = (ImageView) findViewById(R.id.im_back);
		ly_share = (LinearLayout) findViewById(R.id.ly_share);
		ly_participation = (LinearLayout) findViewById(R.id.ly_participation);
		image_back.setOnClickListener(this);
		ly_share.setOnClickListener(this);
		ly_participation.setOnClickListener(this);
		initDate();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_back:
			doBack();
			break;
		case R.id.ly_share:
			if (isLogin()) {
				String shareType = "";
				int eventType = 0;

				if ((Constant.EVENT_TYPE_SHARE_PRIZE + "")
						.equals(myepePrizedBean.getType())) {
					eventType = Constant.EVENT_TYPE_SHARE_PRIZE;
					shareType = Constant.COMMNETTYPE_FXYJ;
				}
				if ((Constant.EVENT_TYPE_RECOMMEND_PRIZE + "")
						.equals(myepePrizedBean.getType())) {
					eventType = Constant.EVENT_TYPE_RECOMMEND_PRIZE;
					shareType = Constant.COMMNETTYPE_TJYJ;
				}
				if ((Constant.EVENT_TYPE_RECOMMEND_YHHD + "")
						.equals(myepePrizedBean.getType())) {
					eventType = Constant.EVENT_TYPE_RECOMMEND_YHHD;
					shareType = Constant.COMMNETTYPE_YHHD;
				}
				String eventPicUrl = myepePrizedBean.getEvtPicPath();
				// 判断内容是否为空，如果为空，使用标题作为分享出去的内容
				String shareText = null;
				if (null != myepePrizedBean.getContent()
						&& !myepePrizedBean.getContent().equals("")) {
					shareText = myepePrizedBean.getContent();
				} else {
					shareText = myepePrizedBean.getTitle();
				}
				AppContext.getApp().showShare(
						PrizedEventIntroduceActivity.this,
						myepePrizedBean.getCreateOrgid(),
						myepePrizedBean.getEventId(), shareType,
						// Constant.COMMNETTYPE_ZXDT,
						shareText, eventPicUrl, eventType,
						myepePrizedBean.getTitle());
			}

			break;
		case R.id.ly_participation:
			if (isLogin()) {
				if (null != myepePrizedBean
						&& null != myepePrizedBean.getType()
						&& !myepePrizedBean.getType().equals("")) {
					if (myepePrizedBean.getType().equals("0")) {
						Intent intent = new Intent(this,
								PrizedParticipationActivity.class);
						intent.putExtra("eventId", myepePrizedBean.getEventId());
						startActivity(intent);
					} else {
						Intent intent = new Intent(this,
								PrizedRecommendationActivity.class);
						intent.putExtra("type", myepePrizedBean.getType());
						intent.putExtra("eventId", myepePrizedBean.getEventId());
						intent.putExtra("introducerBonus",
								myepePrizedBean.getIntroducerBonus());
						intent.putExtra("perAwardNum",
								myepePrizedBean.getPerAwardNum());
						startActivity(intent);
					}
				}
			}
			break;
		}

	}

	private boolean isLogin() {
		userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (null == userLogin) {
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			intoAnim();
			return false;
		}
		return true;
	}

	private void initDate() {
		new EventDataTask().execute(eventId);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		backAnim();
	}

	/**
	 * 请求接口
	 */
	class EventDataTask extends AsyncTask<String, Void, PrizedBeanWrapper> {
		AppException e;

		@Override
		protected PrizedBeanWrapper doInBackground(String... params) {
			try {
				return AppContext.getApp().queryEvent(params[0]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				AppException.network(e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(PrizedBeanWrapper result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (null != result) {
				if (result.getStatus().equals("0")) {
					myepePrizedBean = result.getPrizeBean();
					if (myepePrizedBean != null) {

						prized_webview.loadUrl(URLs.URL_API_HOST + "/"
								+ myepePrizedBean.getEventUrl());
						// if (myepePrizedBean.getUserShareNum().equals("0")) {
						// ly_participation.setVisibility(View.GONE);
						// }
						prized_webview.setWebViewClient(new WebViewClient() {
							@Override
							public boolean shouldOverrideUrlLoading(
									WebView view, String url) {
								return false;
							}
						});
					}
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
