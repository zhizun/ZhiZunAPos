package com.ch.epw.jz.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.task.DeleteCommentTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewBabyInfoListAdapter;
import com.zhizun.pos.adapter.ListViewDynamicParentAdapter;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.BabyInfo;
import com.zhizun.pos.bean.BabyInfoDetail;
import com.zhizun.pos.bean.BabyInfoList;
import com.zhizun.pos.bean.InterestPri;
import com.zhizun.pos.bean.InterestSec;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 宝宝资料列表 创建人：李林中 创建日期：2014-12-15 上午10:10:50 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class BabyInfoListActivity extends BaseActivity implements
		OnClickListener {

	protected static final String TAG = BabyInfoListActivity.class.getName();

	Context context;

	BabyInfoList babyInfoList;
	BabyInfoDetail babyInfoDetail;

	List<BabyInfo> list;

	ListViewBabyInfoListAdapter listViewBabyInfoListAdapter;

	ListView listView;

	ImageView title_iv_left;
	ImageView title_iv_more;
	RelativeLayout rl_title_center;

	ImageView iv_myepei_personinfo_pic;
	TextView tv_myepei_babyinfo_name;
	TextView tv_myepei_babyinfo_age;
	ImageView iv_myepei_babyinfo_sex;
	RelativeLayout rl_myepei_babyinfo;
	RelativeLayout rl_myepei_personinfo_babyinfo_count;
	TextView tv_myepei_babyinfo_nickname;
	TextView tv_myepei_babyinfo_birthdate;
	TextView tv_myepei_babyinfo_cats;
	TextView tv_myepei_babyinfo_count;
	int current_position;// 当前列表位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.myepei_babyinfo);
		options = Options.getListOptions();

		initView();
		// 加载宝宝信息列表
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		new GetBabyListTask().execute(AppContext.getApp().getToken());
	}

	private void initView() {
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		title_iv_more = (ImageView) findViewById(R.id.title_iv_more);
		rl_title_center = (RelativeLayout) findViewById(R.id.rl_title_center);
		title_iv_left.setOnClickListener(this);
		title_iv_more.setOnClickListener(this);
		rl_title_center.setOnClickListener(this);

		iv_myepei_personinfo_pic = (ImageView) findViewById(R.id.iv_myepei_personinfo_pic);
		tv_myepei_babyinfo_name = (TextView) findViewById(R.id.tv_myepei_babyinfo_name);
		tv_myepei_babyinfo_age = (TextView) findViewById(R.id.tv_myepei_babyinfo_age);
		iv_myepei_babyinfo_sex = (ImageView) findViewById(R.id.iv_myepei_babyinfo_sex);
		rl_myepei_babyinfo = (RelativeLayout) findViewById(R.id.rl_myepei_babyinfo);
		tv_myepei_babyinfo_nickname = (TextView) findViewById(R.id.tv_myepei_babyinfo_nickname);
		tv_myepei_babyinfo_birthdate = (TextView) findViewById(R.id.tv_myepei_babyinfo_birthdate);
		tv_myepei_babyinfo_cats = (TextView) findViewById(R.id.tv_myepei_babyinfo_cats);
		tv_myepei_babyinfo_count = (TextView) findViewById(R.id.tv_myepei_babyinfo_count);
		rl_myepei_babyinfo.setOnClickListener(this);
		rl_myepei_personinfo_babyinfo_count = (RelativeLayout) findViewById(R.id.rl_myepei_personinfo_babyinfo_count);
		rl_myepei_personinfo_babyinfo_count.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUSTCONDE_BABYINFO_CATLIST
				&& resultCode == Constant.REQUSTCONDE_BABYINFO_CATLIST) {
			// 加载宝宝信息列表
			if (current_position >= 0 && current_position <= list.size() - 1) {
				new GetupdateBabyTask().execute(list.get(current_position)
						.getChildId(), AppContext.getApp()
						.getUserLoginSharedPre().getUserInfo().getUserId(), "");
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_iv_left:
			doBack();
			break;
		case R.id.title_iv_more:
			showPopupWindow(title_iv_more, current_position);
			break;
		case R.id.rl_title_center:
			if (null != list && list.size() > 0) {
				showPopupWindow(rl_title_center);
			} else {
				UIHelper.ToastMessage(context, "您还没有添加宝宝信息");
				// BabyInfoListActivity.this.finish();
				// backAnim();
				return;
			}

			break;
		case R.id.rl_myepei_babyinfo:
			if (babyInfoDetail != null) {
				Intent intent_babyinfodetail = new Intent(context,
						BabyInfoDetailActivity.class);
				intent_babyinfodetail
						.putExtra("babyInfoDetail", babyInfoDetail);
				startActivityForResult(intent_babyinfodetail,
						Constant.REQUSTCONDE_BABYINFO_CATLIST);
				intoAnim();
			}
			break;
		case R.id.rl_myepei_personinfo_babyinfo_count:
			if (babyInfoDetail != null && babyInfoDetail.getOrgCount() != null
					&& babyInfoDetail.getOrgCount() > 0) {
				Intent intent_babyinfoorg = new Intent(context,
						BabyinfoOrgListActivity.class);
				intent_babyinfoorg.putExtra("babyInfoDetail", babyInfoDetail);
				startActivityForResult(intent_babyinfoorg,
						Constant.REQUSTCONDE_BABYINFO_CATLIST);
				intoAnim();
			} else {
				UIHelper.ToastMessage(context, "该宝宝未加入任何教育机构");
			}
			break;
		default:
			break;
		}

	}

	private void showPopupWindow(View view, final int position) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.list_popwindow_item, null);
		// 设置点击事件
		TextView tv_list_popwindow_edit = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_edit);
		tv_list_popwindow_edit.setVisibility(View.GONE);
		TextView tv_list_popwindow_delete = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_delete);

		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		tv_list_popwindow_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "编辑");
				Intent intent_babyinfodetail = new Intent(context,
						BabyInfoDetailActivity.class);
				intent_babyinfodetail
						.putExtra("babyInfoDetail", babyInfoDetail);
				startActivityForResult(intent_babyinfodetail,
						Constant.REQUSTCONDE_BABYINFO_CATLIST);
				intoAnim();
				popupWindow.dismiss();
			}
		});
		tv_list_popwindow_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "删除");
				if (null != list && list.size() > 0) {
					ActionSheet.showSheet(context, "", "",
							new ActionSheet.OnActionSheetSelected() {
								@Override
								public void onClick(View view) {
									if (view.getId() == R.id.actionsheet_content) {
										new deleteBabyTask()
												.execute(
														AppContext.getApp()
																.getToken(),
														list.get(position)
																.getChildId(),
														AppContext
																.getApp()
																.getUserLoginSharedPre()
																.getUserInfo()
																.getUserId());
									}
								}
							}, null);
					popupWindow.dismiss();
				} else {
					UIHelper.ToastMessage(context, "您还没有添加宝宝信息");
					popupWindow.dismiss();
					return;
				}

				
			}
		});
		// 设置好参数之后再show
		popupWindow.showAsDropDown(view, -55, 15);

	}

	// 我的资料弹出pop
	private void showPopupWindow(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.list_popwindow_babyinfo_item, null);
		listView = (ListView) contentView
				.findViewById(R.id.list_popwindow_templatelist);
		listViewBabyInfoListAdapter = new ListViewBabyInfoListAdapter(context,
				list);
		listView.setAdapter(listViewBabyInfoListAdapter);
		// 屏幕适配
		int width = 200;
		int mScreenWidth = BaseTools.getWindowsWidth(this);
		if (mScreenWidth > 720) {
			width = 400;
		}

		final PopupWindow popupWindow = new PopupWindow(contentView, width,
				LayoutParams.WRAP_CONTENT, true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				current_position = position;
				new GetupdateBabyTask().execute(
						list.get(position).getChildId(), AppContext.getApp()
								.getUserLoginSharedPre().getUserInfo()
								.getUserId(), "");
				popupWindow.dismiss();
			}
		});
		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);

		// 设置好参数之后再show
		popupWindow.showAsDropDown(view, 0, 0);

	}

	/**
	 * 获取宝宝信息列表 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetBabyListTask extends AsyncTask<String, Void, BabyInfoList> {
		AppException e;

		@Override
		protected BabyInfoList doInBackground(String... params) {
			babyInfoList = null;
			try {
				babyInfoList = AppContext.getApp().getBabyNameList(params[0]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
			}
			return babyInfoList;
		}

		@Override
		protected void onPostExecute(BabyInfoList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {
					list = result.getBabyInfoList();
					if (list == null || list.size() == 0) {
						UIHelper.ToastMessage(context, "您还没有添加宝宝信息");
						// BabyInfoListActivity.this.finish();
						// backAnim();
						return;
					}
					showProgressDialog(context, "",
							getResources().getString(R.string.load_ing));
					current_position = 0;
					// 加载第一个宝宝的默认信息
					new GetupdateBabyTask().execute(list.get(0).getChildId(),
							AppContext.getApp().getUserLoginSharedPre()
									.getUserInfo().getUserId(), "");
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

	/**
	 * 删除宝宝 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class deleteBabyTask extends AsyncTask<String, Void, Result> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected Result doInBackground(String... params) {
			Result result = null;
			try {
				result = AppContext.getApp().deleteBaby(params[0], params[1],
						params[2]);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
			}
			return result;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(Result result) {

			super.onPostExecute(result);

			if (result != null) {
				if (result.getStatus().equals("0")) {
					UIHelper.ToastMessage(context, "删除成功", Toast.LENGTH_SHORT);
					// 加载宝宝信息列表
					showProgressDialog(context, "",
							getResources().getString(R.string.load_ing));
					new GetBabyListTask().execute(AppContext.getApp()
							.getToken());
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

	/**
	 * 获取宝宝详细信息 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	class GetupdateBabyTask extends AsyncTask<String, Void, BabyInfoDetail> {
		AppException e;

		@Override
		protected BabyInfoDetail doInBackground(String... params) {
			babyInfoDetail = null;
			try {
				babyInfoDetail = AppContext.getApp().getupdateBaby(params[0],
						params[1], params[2]);
			} catch (AppException e) {
				this.e = e;
				e.printStackTrace();
			}
			return babyInfoDetail;
		}

		@Override
		protected void onPostExecute(BabyInfoDetail result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeProgressDialog();
			if (null != result) {
				if (result.getStatus().equals("0")) {
					showPicture(result.getPhotoPath(),
							iv_myepei_personinfo_pic, options);
					tv_myepei_babyinfo_name.setText(result.getName());
					tv_myepei_babyinfo_age.setText(TextUtils.isEmpty(result
							.getAge())||result.getAge().equals("0")?"":result.getAge() + "岁");
					if (result.getSex().equals("0")) {
						iv_myepei_babyinfo_sex
								.setImageResource(R.drawable.sex_female);
					}
					if (result.getSex().equals("1")) {
						iv_myepei_babyinfo_sex
								.setImageResource(R.drawable.sex_man);
					}
					tv_myepei_babyinfo_nickname.setText(result.getNickName());
					tv_myepei_babyinfo_birthdate.setText(result.getBirthDate());
					StringBuffer buffString = new StringBuffer();
					for (InterestPri interestPri : babyInfoDetail
							.getInterestPriList()) {
						for (InterestSec interestSec : interestPri
								.getInterestSecList()) {

							buffString.append(interestSec.getItemName() + ",");

						}
					}
					String buffStr = buffString.toString();
					if (buffStr.endsWith(",")) {
						buffStr = buffStr.substring(0, buffStr.length() - 1);
					}
					tv_myepei_babyinfo_cats.setText(buffStr);
					if (result.getOrgCount() != null
							&& result.getOrgCount() > 0) {
						tv_myepei_babyinfo_count.setText("（"
								+ result.getOrgCount() + "）");
					} else {
						tv_myepei_babyinfo_count.setText("");
					}
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

}
