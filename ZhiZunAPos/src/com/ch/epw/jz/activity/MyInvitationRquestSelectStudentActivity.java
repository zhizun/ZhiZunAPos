package com.ch.epw.jz.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserChildInfo;

/**
 * 我的邀请 选择学生 创建人：李林中 创建日期：2014-12-15 上午10:34:41 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyInvitationRquestSelectStudentActivity extends BaseActivity {

	private TitleBarView titleBarView;

	private ListView nl_my_invitation_selectstudent_childlist;

	private Map<Integer, Boolean> isSelected;

	private List<String> beSelectedData = new ArrayList<String>();

	ListViewUserChildInfoAdapter adapter;
	RecevieMyInvitation recevieMyInvitation;
	List<UserChildInfo> listuChildInfo;
	TextView tv_my_invitation_selectstudent_title;
	Button btn_my_invitation_selectstudent_submit;
	String orgclass;// 机构 班级信息
	Result result;
	Context context;
	int currentPosition;// listview的当前位置
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				currentPosition = msg.arg1;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.my_invitation_selectstudent);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		listuChildInfo = (List<UserChildInfo>) bundle
				.getSerializable("userChildInfoList");
		Log.i(TAG, "listuChildInfo.size=" + listuChildInfo.size());
		orgclass = bundle.getString("orgclass");
		recevieMyInvitation = (RecevieMyInvitation) bundle
				.getSerializable("recevieMyInvitation");

		initView();

		initList();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_my_invitation_selectstudent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);

		titleBarView.setTitleText(R.string.title_text_selectstudent);
		titleBarView.setRightText(R.string.title_text_addstudent);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		nl_my_invitation_selectstudent_childlist = (ListView) findViewById(R.id.nl_my_invitation_selectstudent_childlist);
		btn_my_invitation_selectstudent_submit = (Button) findViewById(R.id.btn_my_invitation_selectstudent_submit);
		tv_my_invitation_selectstudent_title = (TextView) findViewById(R.id.tv_my_invitation_selectstudent_title);
		tv_my_invitation_selectstudent_title.setText("请确认在" + orgclass
				+ "上课的宝宝");
		btn_my_invitation_selectstudent_submit
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null == beSelectedData
								|| beSelectedData.size() <= 0) {
							UIHelper.ToastMessage(context, "请选择一个宝宝");
							return;
						}
						Log.i(TAG, "currentPosition=" + currentPosition);

						if (currentPosition == listuChildInfo.size()) {
							// 最后一项
							new AcceptInviteTask().execute(AppContext.getApp()
									.getToken(), recevieMyInvitation
									.getOrgInviteId(), "", recevieMyInvitation
									.getStuId());
						} else {
							new AcceptInviteTask().execute(AppContext.getApp()
									.getToken(), recevieMyInvitation
									.getOrgInviteId(), beSelectedData.get(0),
									recevieMyInvitation.getStuId());
						}

					}
				});
	}

	/**
	 * 接受邀请 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class AcceptInviteTask extends AsyncTask<String, Void, Result> {
		AppException e;

		@Override
		protected Result doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				result = AppContext.getApp().recevieInvite(params[0],
						params[1], params[2], params[3]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
				result = null;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Result result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress != null) {
				progress.dismiss();
			}
			if (null != result) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "您已接成功受邀请");
					// 发送广播通知 来刷新我的邀请列表
					Intent intent = new Intent();
					intent.setAction("com.ch.epw.REFRESH_PARENT_INVITELIST");
					sendBroadcast(intent);
					MyInvitationRquestSelectStudentActivity.this.finish();
					backAnim();
				} else {
					UIHelper.ToastMessage(context, result.getStatusMessage());
					return;
				}
			} else {
				if (null != e) {
					e.makeToast(context);
				}
				return;
			}
		}
	}

	void initList() {

		if (listuChildInfo == null || listuChildInfo.size() == 0) {
			return;
		}
		if (isSelected != null) {
			isSelected = null;
		}
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < listuChildInfo.size() + 1; i++) {
			isSelected.put(i, false);
		}
		// 清除已经选择的项
		if (beSelectedData.size() > 0) {
			beSelectedData.clear();
		}
		adapter = new ListViewUserChildInfoAdapter(this, listuChildInfo,
				handler);
		nl_my_invitation_selectstudent_childlist.setAdapter(adapter);
		nl_my_invitation_selectstudent_childlist
				.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		nl_my_invitation_selectstudent_childlist
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Log.i("map", listuChildInfo.get(position).toString());
					}
				});

	}

	/**
	 * 当前用户的宝宝信息ListViewAdapter
	 */
	class ListViewUserChildInfoAdapter extends MyBaseAdapter {

		private Context context;// 运行上下文
		private List<UserChildInfo> listItems; // 数据集合
		Handler handler;

		public ListViewUserChildInfoAdapter(Context context,
				List<UserChildInfo> listItems, Handler handler) {
			super();
			this.context = context;
			this.listItems = listItems;
			this.handler = handler;
			options = Options.getListOptions();
		}

		public int getCount() {
			return (listItems.size() + 1);
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
			ViewHolder holder = null;

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = View.inflate(context,
						R.layout.my_invitation_selectstudent_item, null);

				holder.iv_myinvitation_selectstudent_item_logo = (ImageView) convertView
						.findViewById(R.id.iv_myinvitation_selectstudent_item_logo);
				holder.cb_my_invitation_selectstudent_item_state = (CheckBox) convertView
						.findViewById(R.id.cb_my_invitation_selectstudent_item_state);
				holder.tv_myinvitation_selectstudent_item_age = (TextView) convertView
						.findViewById(R.id.tv_myinvitation_selectstudent_item_age);
				holder.iv_myinvitation_selectstudent_item_sex = (ImageView) convertView
						.findViewById(R.id.iv_myinvitation_selectstudent_item_sex);
				holder.iv_myinvitation_selectstudent_item_name = (TextView) convertView
						.findViewById(R.id.iv_myinvitation_selectstudent_item_name);
				holder.ll_my_invitation_selectstudent_item = (LinearLayout) convertView
						.findViewById(R.id.ll_my_invitation_selectstudent_item);
				holder.rl_myinvitation_selectstudent_item_age = (RelativeLayout) convertView
						.findViewById(R.id.rl_myinvitation_selectstudent_item_age);
				holder.rl_myinvitation_selectstudent_item_name = (RelativeLayout) convertView
						.findViewById(R.id.rl_myinvitation_selectstudent_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == listItems.size()) {

				holder.rl_myinvitation_selectstudent_item_age
						.setVisibility(View.GONE);
				// android:layout_centerVertical="true"

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.CENTER_VERTICAL);

				holder.rl_myinvitation_selectstudent_item_name
						.setLayoutParams(params);
				holder.iv_myinvitation_selectstudent_item_name
						.setText("没有这个宝宝，帮我创建");
				holder.ll_my_invitation_selectstudent_item
						.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {

								Log.i(TAG, "当前点击项的position=" + position);
								Message msg = Message.obtain();
								msg.arg1 = position;
								msg.what = 1;
								handler.sendMessage(msg);
								// 当前点击的CB
								boolean cu = !isSelected.get(position);
								// 先将所有的置为FALSE
								for (Integer p : isSelected.keySet()) {
									isSelected.put(p, false);
								}
								// 再将当前选择CB的实际状态
								isSelected.put(position, cu);
								ListViewUserChildInfoAdapter.this
										.notifyDataSetChanged();
								beSelectedData.clear();
								if (cu) {
									beSelectedData.add(recevieMyInvitation
											.getStuId());
								}

							}
						});
			} else {

				UserChildInfo userChildInfo = listItems.get(position);
				holder.rl_myinvitation_selectstudent_item_age
						.setVisibility(View.VISIBLE);

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				holder.rl_myinvitation_selectstudent_item_name
						.setLayoutParams(params);
				holder.ll_my_invitation_selectstudent_item
						.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Log.i(TAG, "当前点击项的position=" + position);
								Message msg = Message.obtain();
								msg.arg1 = position;
								msg.what = 1;
								handler.sendMessage(msg);
								// 当前点击的CB
								boolean cu = !isSelected.get(position);
								// 先将所有的置为FALSE
								for (Integer p : isSelected.keySet()) {
									isSelected.put(p, false);
								}
								// 再将当前选择CB的实际状态
								isSelected.put(position, cu);
								ListViewUserChildInfoAdapter.this
										.notifyDataSetChanged();
								beSelectedData.clear();
								if (cu) {
									beSelectedData.add(listItems.get(position)
											.getChildId());
								}

							}
						});

				showPicture(userChildInfo.getPhotoPath(),
						holder.iv_myinvitation_selectstudent_item_logo, options);
				holder.iv_myinvitation_selectstudent_item_name
						.setText(userChildInfo.getName());
				holder.tv_myinvitation_selectstudent_item_age
						.setText(TextUtils.isEmpty(userChildInfo
								.getAge())||userChildInfo.getAge().equals("0")?"":userChildInfo.getAge() + "岁");
				if (userChildInfo.getSex().equals("1")) {
					holder.iv_myinvitation_selectstudent_item_sex
							.setImageResource(R.drawable.sex_man);
				}
				if (userChildInfo.getSex().equals("0")) {
					holder.iv_myinvitation_selectstudent_item_sex
							.setImageResource(R.drawable.sex_female);
				}

			}
			holder.cb_my_invitation_selectstudent_item_state
					.setChecked(isSelected.get(position));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_myinvitation_selectstudent_item_logo;
			CheckBox cb_my_invitation_selectstudent_item_state;
			TextView tv_myinvitation_selectstudent_item_age;
			ImageView iv_myinvitation_selectstudent_item_sex;
			TextView iv_myinvitation_selectstudent_item_name;
			LinearLayout ll_my_invitation_selectstudent_item;
			RelativeLayout rl_myinvitation_selectstudent_item_age;
			RelativeLayout rl_myinvitation_selectstudent_item_name;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
