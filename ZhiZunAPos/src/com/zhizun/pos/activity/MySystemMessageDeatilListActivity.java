package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.zhizun.pos.R;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.adapter.MyMessageListviewAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MySelfMessageBean;
import com.zhizun.pos.bean.MySendMessageBean;
import com.zhizun.pos.bean.MySystemMessageDeatilList;
/**
 * 系统回复列表
 * @author lilinzhong
 *
 * 2015-7-28下午4:09:06
 */
public class MySystemMessageDeatilListActivity extends BaseActivity implements OnClickListener {
	TitleBarView titleBarView;
	private String receiveId;
	private EditText et_send_message_content;
	private Button btn_send_system_message;
	private MySendMessageBean mySendMessageBean;
	private MySystemMessageDeatilList mySystemMessageDeatilList;
	private List<MySelfMessageBean> list;
	private MyMessageListviewAdapter dynamicParentAdapter;
	private int dataCount;
	private boolean getData=false;
	private boolean hasReply=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_system_message_deatil_list);
		receiveId=getIntent().getStringExtra("receiveId");
		initView();
		contentList();
	}

	private void initView() {
		// 设置titleBar的按钮、标题及按钮响应事件
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_message);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText("回复详情");
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.putExtra("hasReply", hasReply);
				setResult(0, intent);
				finish();
			}
		});

		mPullListView=(PullToRefreshListView)findViewById(R.id.ll_my_system_message_listview);
		et_send_message_content=(EditText) findViewById(R.id.et_send_message_content);
		btn_send_system_message=(Button) findViewById(R.id.btn_send_system_message);
		btn_send_system_message.setOnClickListener(this);
		// 禁止自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	private void contentList() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件
		Log.i("tag", "mPullListView=" + mPullListView);
		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		mListView.setDivider(null);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				//下拉刷新
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (getData) {
					int page = (int) (Math.ceil(dataCount
							/ (double) Constant.LOADDATACOUNT));
					
					mCurPage = mCurPage + 1;
					Log.i(TAG, "mCurPage=" + mCurPage);
					if (mCurPage > page) {
						mCurPage = page;
						mPullListView.setHasMoreData(false);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					} 
				}
				new MySystemMessageListTask().execute(receiveId,mCurPage + "",
						Constant.LOADDATACOUNT + "");
				getData=true;
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				hasMoreData = true;
				mPullListView.setHasData(true);
				new MySystemMessageListTask().execute(receiveId,"1",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
		// 下拉刷新和上啦加载代码结束=------
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_send_system_message:
			//隐藏输入法
			((InputMethodManager) MySystemMessageDeatilListActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(et_send_message_content.getWindowToken(), 0);

			String messageContent=et_send_message_content.getText().toString();
			if (null!=messageContent && !messageContent.equals("")) {
				new SendMessageTask().execute(receiveId,messageContent);
			}else {
				Toast.makeText(this, "发送内容不能为空", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}
	
	/**
	 * 我的消息
	 * 		--获取列表接口
	 */
	private class MySystemMessageListTask extends
			AsyncTask<String, Void, MySystemMessageDeatilList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		@Override
		protected MySystemMessageDeatilList doInBackground(String... params) {
			try {
				mySystemMessageDeatilList = AppContext.getApp().getMySystemMessageDeatiList(
						params[0],params[1],params[2]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				params = null;
			}
			return mySystemMessageDeatilList;
		}
	
		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(final MySystemMessageDeatilList result) {
	
			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = Integer.valueOf(result.getDataCount());
					if (dataCount == 0) {
						mPullListView.setHasData(false);
						mListView.setDivider(null);
						mListView.setDividerHeight(0);

					} else if (dataCount == 1) {
						mPullListView.setHasData(true);
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}

					List<MySelfMessageBean> newList=new ArrayList<MySelfMessageBean>();
					if (list != null && list.size() > 0) {
						list.addAll(result.getMySelfMessageBean());
						newList.addAll(list);
						Collections.reverse(newList);
						dynamicParentAdapter = new MyMessageListviewAdapter(
								MySystemMessageDeatilListActivity.this, newList);
						// 得到实际的ListView
						mListView.setAdapter(dynamicParentAdapter);
						mListView.setSelection(0);//下拉加载历史消息，定位到顶部
					} else {
						list = result.getMySelfMessageBean();
						newList.addAll(list);
						Collections.reverse(newList);
						dynamicParentAdapter = new MyMessageListviewAdapter(
								MySystemMessageDeatilListActivity.this, newList);
						// 得到实际的ListView
						mListView.setAdapter(dynamicParentAdapter);
						//定位到底部
						mListView.setSelection(dynamicParentAdapter.getCount() - 1);
					}

				} else {
					UIHelper.ToastMessage(MySystemMessageDeatilListActivity.this, result.getStatusMessage());
					return;
				}
			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(MySystemMessageDeatilListActivity.this);
				}
				return;
			}

			mPullListView.onPullDownRefreshComplete();
			mPullListView.onPullUpRefreshComplete();
			mPullListView.setHasMoreData(hasMoreData);
			setLastUpdateTime();
	
		}
	}
	private class SendMessageTask extends AsyncTask<String, Void, MySendMessageBean> {
	AppException e;

	protected MySendMessageBean doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			mySendMessageBean = ((AppContext) getApplication()).sendMessage(
					params[0], params[1]);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			this.e = e;
			e.printStackTrace();
			AppException.network(e);
		}
		return mySendMessageBean;
	}

	@Override
	protected void onPostExecute(MySendMessageBean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		closeProgressDialog();
		if (null == result) {
			if (null != e) {
				e.makeToast(MySystemMessageDeatilListActivity.this);
			}
			return;

		} else {
			if (result.getStatus().equals("0")) {
				Toast.makeText(MySystemMessageDeatilListActivity.this,"发送成功，我们会尽快给您回复",
						Toast.LENGTH_SHORT).show();
				et_send_message_content.setText("");
				list=null;
				hasReply=true;
				new MySystemMessageListTask().execute(receiveId,"1",
						Constant.LOADDATACOUNT + "");
				return;
			}else {
				Toast.makeText(MySystemMessageDeatilListActivity.this, "发送失败，请稍后再试",
						Toast.LENGTH_SHORT).show();
				return;
			}
		}
	}

	}
	@Override 
    public void onBackPressed() { 
        Intent intent=new Intent();
		intent.putExtra("hasReply", hasReply);
		setResult(0, intent);
		super.onBackPressed(); 
    }
}
