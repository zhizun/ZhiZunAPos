package com.zhizun.pos.activity;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.zhizun.pos.R;
import com.ch.epw.js.activity.InSchoolDynamicTeacherActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.MyDialog;
import com.ch.epw.view.TitleBarView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase;
import com.ch.epw.widget.pulltorefresh.PullToRefreshListView;
import com.ch.epw.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.zhizun.pos.AppException;
import com.zhizun.pos.adapter.ListViewDynamicTeacherAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.ReceiveMyInvitationList;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserChildInfoList;

/**
 * 我的邀请家长端 创建人：李林中 创建日期：2014-12-15 上午10:09:44 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationActivity extends BaseActivity {

	protected static final String TAG = MyInvitationActivity.class.getName();
	Context context;
	private TitleBarView titleBarView;
	ReceiveMyInvitationList receiveMyInvitationList;
	ListViewMyInvitationRecevieParentAdapter listViewMyInvitationRecevieParentAdapter;
	UserChildInfoList userChildInfoList;
	Result result;
	MyDialog dialog;
	Integer dataCountRecevieTeacher;
	RecevieMyInvitation recevie;
	List<RecevieMyInvitation> list;
	Integer REQUEST_COLDE = 1;// 结果返回码
	Integer RESULT_ACCEPT_COLDE = 1;// 结果返回码 接受邀请
	Integer RESULT_REFUSE_COLDE = 2;// 结果返回码 拒绝邀请

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invitation_parent);
		context = this;
		// 注册通知 刷新列表
		IntentFilter filter = new IntentFilter(
				"com.ch.epw.REFRESH_PARENT_INVITELIST");
		registerReceiver(broadcastReceiver, filter);

		initView();
		content();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_invitation_parentinfo);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.VISIBLE);

		titleBarView.setTitleText(R.string.title_text_myinvitation);

		mPullListView = (PullToRefreshListView) findViewById(R.id.lv_my_invitation_list);

	}

	private void content() {
		// 下拉刷新和上啦加载代码开始=------
		// 下拉刷新控件

		// 上拉加载不可用
		mPullListView.setPullLoadEnabled(false);
		// 滚动到底自动加载可用
		mPullListView.setScrollLoadEnabled(true);
		// 得到真正的listview
		mListView = mPullListView.getRefreshableView();
		

		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				list = null;
				mCurPage = 1;
				hasMoreData = true;
				mPullListView.setHasData(true);
				SharedPreferences sp = AppContext.getApp().getSharedPref(
						"appToken");
				String token = sp.getString("token", "");
				new GetUserInviteTask().execute(token, 1 + "",
						Constant.LOADDATACOUNT + "");

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				int page = (int) (Math.ceil(dataCountRecevieTeacher
						/ (double) Constant.LOADDATACOUNT));

				mCurPage = mCurPage + 1;
				Log.i(TAG, "mCurPage=" + mCurPage);
				if (mCurPage > page) {
					mCurPage = page;
					hasMoreData = false;
					mPullListView.setHasMoreData(hasMoreData);
					return;
				}

				Log.i(TAG, "mCurPage2=" + mCurPage);
				Log.i(TAG, "dynamicCount=" + dataCountRecevieTeacher);
				Log.i(TAG, "page=" + page);
				SharedPreferences sp = AppContext.getApp().getSharedPref(
						"appToken");
				String token = sp.getString("token", "");

				new GetUserInviteTask().execute(token, mCurPage + "",
						Constant.LOADDATACOUNT + "");
			}
		});
		setLastUpdateTime();
		// 进入自动刷新
		mPullListView.doPullRefreshing(true, 500);

		// 下拉刷新和上啦加载代码结束=------

	}

	private class GetUserInviteTask extends
			AsyncTask<String, Void, ReceiveMyInvitationList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected ReceiveMyInvitationList doInBackground(String... params) {

			try {
				receiveMyInvitationList = AppContext.getApp()
						.getReceiveMyInvitationList(params[0], params[1],
								params[2]);

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				receiveMyInvitationList = null;
			}
			return receiveMyInvitationList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(ReceiveMyInvitationList result) {

			super.onPostExecute(result);
			if (null != progress) {
				progress.dismiss();
			}
			if (result != null) {
				if (result.getStatus().equals("0")) {
					dataCountRecevieTeacher = Integer.parseInt(result
							.getDataCount());

					if (list != null && list.size() > 0) {
						list.addAll(result.getRecevieMyInvitationList());
					} else {
						list = result.getRecevieMyInvitationList();
					}

					if (dataCountRecevieTeacher == 0) {
						mPullListView.setHasData(false);
						mListView.setDividerHeight(0);
						listViewMyInvitationRecevieParentAdapter = new ListViewMyInvitationRecevieParentAdapter(
								context, list);
						// 得到实际的ListView
						mListView
								.setAdapter(listViewMyInvitationRecevieParentAdapter);
						mPullListView.onPullDownRefreshComplete();
						mPullListView.onPullUpRefreshComplete();
						return;
					}else if (dataCountRecevieTeacher == 1) {
						mListView.setDivider(null);
						mListView.setDividerHeight(0);
					}
					if (dataCountRecevieTeacher>0) {
						mListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								Intent intent = new Intent(context,
										MyInvitationRquestActivity.class);
								intent.putExtra("recevieMyInvitation", list.get(position));
								startActivityForResult(intent, REQUEST_COLDE);
								intoAnim();
							}
						});
					}
					if (list.size() <= Constant.LOADDATACOUNT) {
						listViewMyInvitationRecevieParentAdapter = new ListViewMyInvitationRecevieParentAdapter(
								context, list);
						// 得到实际的ListView
						mListView
								.setAdapter(listViewMyInvitationRecevieParentAdapter);
					} else {
						listViewMyInvitationRecevieParentAdapter
								.notifyDataSetChanged();
					}
					mPullListView.onPullDownRefreshComplete();
					mPullListView.onPullUpRefreshComplete();
					mPullListView.setHasMoreData(hasMoreData);
					setLastUpdateTime();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}

			} else {
				mPullListView.onPullDownRefreshComplete();
				mPullListView.onPullUpRefreshComplete();
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_COLDE && resultCode == RESULT_ACCEPT_COLDE) {
			content();
		}
		if (requestCode == REQUEST_COLDE && resultCode == RESULT_REFUSE_COLDE) {
			content();
		}
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			content();
		}
	};

	/**
	 * 家长 我收到的邀请ListViewAdapter
	 * 
	 * @param <index2>
	 */
	class ListViewMyInvitationRecevieParentAdapter extends MyBaseAdapter {

		private Context context;// 运行上下文
		private List<RecevieMyInvitation> listItems; // 数据集合
		Result result;
		TextView tView;
		Button btnButton;

		public ListViewMyInvitationRecevieParentAdapter(Context context,
				List<RecevieMyInvitation> listItems) {
			super();
			this.context = context;
			this.listItems = listItems;
			options = Options.getListOptions();
		}

		public int getCount() {
			return listItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = View
						.inflate(
								context,
								R.layout.list_activity_my_invitation_receive_item,
								null);
				holder.iv_list_my_invitation_recevie_item_logo = (ImageView) convertView
						.findViewById(R.id.iv_list_my_invitation_recevie_item_logo);
				holder.tv_list_my_invitation_parent_item_invitename = (TextView) convertView
						.findViewById(R.id.tv_list_my_invitation_parent_item_invitename);

				holder.tv_list_my_invitation_parent_item_inviteorgname = (TextView) convertView
						.findViewById(R.id.tv_list_my_invitation_parent_item_inviteorgname);

				holder.tv_list_my_invitation_parent_item_time = (TextView) convertView
						.findViewById(R.id.tv_list_my_invitation_parent_item_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final RecevieMyInvitation recevieMyInvitation = listItems
					.get(position);

			showPicture(recevieMyInvitation.getInviteUserPhoto(),
					holder.iv_list_my_invitation_recevie_item_logo, options);

			if (null != recevieMyInvitation.getInviteUserName()
					&& !recevieMyInvitation.getInviteUserName().equals("")) {
				holder.tv_list_my_invitation_parent_item_invitename
						.setText(recevieMyInvitation.getInviteUserName()
								+ " 老师");
			}
			if (null != recevieMyInvitation.getInviteOrgName()
					&& !recevieMyInvitation.getInviteOrgName().equals("")) {
				holder.tv_list_my_invitation_parent_item_inviteorgname
						.setText(recevieMyInvitation.getInviteOrgName());
			}
			if (null != recevieMyInvitation.getInviteTime()
					&& !recevieMyInvitation.getInviteTime().equals("")) {
				String str1 = recevieMyInvitation.getInviteTime().trim()
						.split(" ")[0];
				String str2 = str1.split("年")[1];
				holder.tv_list_my_invitation_parent_item_time.setText(str2);
			}

			return convertView;
		}

		class ViewHolder {
			ImageView iv_list_my_invitation_recevie_item_logo;// logo
			TextView tv_list_my_invitation_parent_item_invitename;// 邀请人姓名
			TextView tv_list_my_invitation_parent_item_inviteorgname;// 邀请机构名称
			TextView tv_list_my_invitation_parent_item_time;// 时间
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		context.unregisterReceiver(broadcastReceiver);
	}
}
