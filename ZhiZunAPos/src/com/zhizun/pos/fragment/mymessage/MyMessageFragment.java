package com.zhizun.pos.fragment.mymessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ch.epw.task.SystemMessageTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.MySystemMessageDeatilListActivity;
import com.zhizun.pos.adapter.MyMessageListviewAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseFragment;
import com.zhizun.pos.bean.MySelfMessageBean;
import com.zhizun.pos.bean.MySelfMessageList;
import com.zhizun.pos.bean.MySendMessageBean;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的消息列表界面
 * @author lilinzhong
 *
 * 2015-7-21上午10:38:11
 */
public class MyMessageFragment extends BaseFragment {
	
	private Activity activity;
	private RadioButton radiobutton_cour;
	private List<MySelfMessageBean> list;
	private MySelfMessageList dynamicTeacherList;
	private MyMessageListviewAdapter dynamicParentAdapter;
	private EditText et_list_mymessage_send_text;
	private Button btn_list_my_message_send;
	private MySendMessageBean mySendMessageBean;
	Integer dataCount;
	private boolean getData=false;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.mymessage_listview_fragment, null);
			mPullListView = (PullToRefreshListView) view.findViewById(R.id.ll_my_message);
			et_list_mymessage_send_text=(EditText) view.findViewById(R.id.et_list_mymessage_send_text);
			btn_list_my_message_send=(Button) view.findViewById(R.id.btn_list_my_message_send);
			radiobutton_cour = (RadioButton) activity.findViewById(R.id.radiobutton_cour);
			btn_list_my_message_send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//隐藏输入法
					((InputMethodManager) activity
							.getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(et_list_mymessage_send_text.getWindowToken(), 0);

					if (null!=et_list_mymessage_send_text.getText().toString()&&!et_list_mymessage_send_text.getText().toString().equals("")) {
						new SendMessageTask().execute("",et_list_mymessage_send_text.getText().toString());
					}else {
						Toast.makeText(activity, "发送内容不能为空", Toast.LENGTH_LONG).show();
					}
				}
			});
			getData=false;
			list=null;
			contentList();
		return view;
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
							/ (double) Constant.LOADDATACOUNT));//总页码数
					
					mCurPage = mCurPage + 1;
					if (mCurPage > page) {//如果请求页码大于总页码数，结束下拉刷新
						mPullListView.onPullDownRefreshComplete();
						return;
					}
				}
				new MyMessageListTask().execute(mCurPage + "", Constant.LOADDATACOUNT
						+ "");
				getData=true;

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				list = null;
				mCurPage=1;//下拉加载页码数重置为1，
				hasMoreData = true;
				mPullListView.setHasData(true);
				new MyMessageListTask().execute(1 + "", Constant.LOADDATACOUNT
						+ "");
				
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);
		// 下拉刷新和上啦加载代码结束=------
	}
	/**
	 * 我的消息
	 */
	private class MyMessageListTask extends
			AsyncTask<String, Void, MySelfMessageList> {
		AppException e;
		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected MySelfMessageList doInBackground(String... params) {

			try {
				dynamicTeacherList = AppContext.getApp().getMySelfMessageList(
						params[0], params[1]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				dynamicTeacherList = null;
			}
			return dynamicTeacherList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(MySelfMessageList result) {

			super.onPostExecute(result);
			if (result != null) {

				if (result.getStatus().equals("0")) {
					dataCount = Integer.parseInt(result.getDataCount());
					if (dataCount == 0) {
						//无内容，设为false，就显示暂无内容图片
						mPullListView.setHasData(false);
						mListView.setDivider(null);
						mListView.setDividerHeight(0);

					} else if (dataCount == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
						mPullListView.setHasData(true);
					}

					List<MySelfMessageBean> newList=new ArrayList<MySelfMessageBean>();
					if (list != null && list.size() > 0) {
						list.addAll(result.getMySelfMessageBean());
						newList.addAll(list);
						Collections.reverse(newList);
						dynamicParentAdapter = new MyMessageListviewAdapter(
								activity, newList);
						// 得到实际的ListView
						mListView.setAdapter(dynamicParentAdapter);
						mListView.setSelection(0);//下拉家在历史消息，定位到顶部
					} else {
						list = result.getMySelfMessageBean();
						newList.addAll(list);
						Collections.reverse(newList);
						dynamicParentAdapter = new MyMessageListviewAdapter(
								activity, newList);
						// 得到实际的ListView
						mListView.setAdapter(dynamicParentAdapter);
						//定位到底部
						mListView.setSelection(dynamicParentAdapter.getCount() - 1);
					}
					mPullListView.onPullDownRefreshComplete();
					mPullListView.onPullUpRefreshComplete();
					mPullListView.setHasMoreData(hasMoreData);
					setLastUpdateTime();
				} else {
					UIHelper.ToastMessage(activity, result.getStatusMessage());
					return;
				}
			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(activity);
				}
				return;
			}

		}
	}
	private class SendMessageTask extends AsyncTask<String, Void, MySendMessageBean> {
		AppException e;

		protected MySendMessageBean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				mySendMessageBean = AppContext.getApp().sendMessage(
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
			if (null == result) {
				if (null != e) {
					e.makeToast(activity);
				}
				return;

			} else {
				if (result.getStatus().equals("0")) {
					Toast.makeText(activity,"发送成功，我们会尽快给您回复",
							Toast.LENGTH_SHORT).show();
					list=null;
					new MyMessageListTask().execute(1+"",
							Constant.LOADDATACOUNT + "");
					et_list_mymessage_send_text.setText("");
					return;
				}else {
					Toast.makeText(activity, "发送失败",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}

	}
}
