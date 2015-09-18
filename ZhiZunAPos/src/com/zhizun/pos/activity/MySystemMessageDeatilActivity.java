package com.zhizun.pos.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MySystemMessageDeatilContent;
/**
 * 系统详情页
 * @author lilinzhong
 *
 * 2015-7-23上午11:19:57
 */
public class MySystemMessageDeatilActivity extends BaseActivity implements OnClickListener {
	
	TitleBarView titleBarView;
	private TextView tv_system_message_title;
	private TextView tv_system_message_time;
	private WebView tv_message_content;
	private LinearLayout ll_bottom_send_message;
	private TextView tv_huifu;
	
	private String receiveId;
	private String messageId;
	MySystemMessageDeatilContent mySystemMessageDeatilListContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_message_detail);
        receiveId =getIntent().getStringExtra("receiveId");
        messageId =getIntent().getStringExtra("messageId");
		initView();
	}
	
	private void initView() {
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_message);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText("消息详情");
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		tv_system_message_title=(TextView) findViewById(R.id.tv_system_message_title);
		tv_system_message_time=(TextView)findViewById(R.id.tv_system_message_time);
		tv_message_content=(WebView) findViewById(R.id.tv_message_content);
		ll_bottom_send_message=(LinearLayout) findViewById(R.id.ll_bottom_send_message);
		ll_bottom_send_message.setOnClickListener(this);
		tv_huifu=(TextView) findViewById(R.id.tv_huifu);
		
		content();
	}
	
	private void content() {
		//先调用此方法获取详情页标题和内容
		new MySystemMessageDeatilContentTask().execute(receiveId, messageId);
	}


	/**
	 * 系统消息 详情页
	 *		获取详情页
	 */
	private class MySystemMessageDeatilContentTask extends
			AsyncTask<String, Void, MySystemMessageDeatilContent> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		@Override
		protected MySystemMessageDeatilContent doInBackground(String... params) {
			try {
				mySystemMessageDeatilListContent = AppContext.getApp().getMySystemMessageDeatil(params[0],params[1]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				params = null;
			}
			return mySystemMessageDeatilListContent;

		}
	
		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(final MySystemMessageDeatilContent result) {
	
			super.onPostExecute(result);
			if (result != null) {
				if (result.getStatus().equals("0")) {
					if (result.getIsReplyAllowed().equals("0")) {
						//不允许回复
						ll_bottom_send_message.setVisibility(View.GONE);
					}else {
						//允许回复
						ll_bottom_send_message.setVisibility(View.VISIBLE);
					}
					if (result.getReplyNum()>0) {
						tv_huifu.setText("查看回复");
					}else {
						tv_huifu.setText("我要回复");
					}
					tv_system_message_title.setText(result.getTitle());
					tv_system_message_time.setText(result.getSendTime());
					tv_message_content.loadDataWithBaseURL("about:blank", result.getContent(), "text/html", "utf-8", null);
					tv_message_content.getSettings().setUseWideViewPort(true);
					tv_message_content.getSettings().setLoadWithOverviewMode(true);//如果只加上这两行会让webView加载的图片小了点
					tv_message_content.setInitialScale(220);//解决上面的缩放过小问
				} else {
					UIHelper.ToastMessage(MySystemMessageDeatilActivity.this, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(MySystemMessageDeatilActivity.this);
				}
				return;
			}
	
		}
	}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode==0) {
		if (data!=null) {
			boolean hasRepky=data.getBooleanExtra("hasReply", false);
			if (hasRepky) {
				tv_huifu.setText("查看回复");
			}
		}
	}
	
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ll_bottom_send_message:

			if(mySystemMessageDeatilListContent != null && mySystemMessageDeatilListContent.getReceiveId() != null){
				Intent intent=new Intent(this,MySystemMessageDeatilListActivity.class);
				intent.putExtra("receiveId", mySystemMessageDeatilListContent.getReceiveId());
				startActivityForResult(intent, 0);
			}

			break;
		}
	}
	}






