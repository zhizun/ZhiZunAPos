package com.zhizun.pos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.js.activity.AttenceEditTeacherActivity;
import com.ch.epw.js.activity.AttenceSaveTeacherActivity;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.NoScrollGridView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Attence;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserInfo;

/**
 * 考勤 教师端 ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewAttenceTeacherAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewAttenceTeacherAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<Attence> listItems; // 数据集合
	private String dateString;

	public ListViewAttenceTeacherAdapter(Context context,
			List<Attence> listItems, String dateString) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.dateString = dateString;
		options = Options.getListOptions();
	}

	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_attence_js_item,
					null);

			holder.iv_list_attence_js_item_edit = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_edit);

			holder.iv_list_attence_js_item_foldoropen = (ImageView) convertView
					.findViewById(R.id.iv_list_attence_js_item_foldoropen);
			holder.ll_list_attence_js_item_content = (LinearLayout) convertView
					.findViewById(R.id.ll_list_attence_js_item_content);
			holder.ll_tv_list_attence_js_item_title_left = (LinearLayout) convertView
					.findViewById(R.id.ll_tv_list_attence_js_item_title_left);
			holder.ll_tv_list_attence_js_item_title_right = (LinearLayout) convertView
					.findViewById(R.id.ll_tv_list_attence_js_item_title_right);

			holder.tv_list_attence_js_item_classname = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_classname);
			holder.tv_list_attence_js_item_attenceinfo = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_attenceinfo);

			// holder.tv_list_attence_js_item_xhks_content = (TextView)
			// convertView
			// .findViewById(R.id.tv_list_attence_js_item_xhks_content);
			holder.tv_list_attence_js_item_attencetime_content = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_attencetime_content);

			holder.tv_list_attence_js_item_username_content = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_username_content);
			holder.ngv_list_attence_js_item_attencelist = (NoScrollGridView) convertView
					.findViewById(R.id.ngv_list_attence_js_item_attencelist);
			holder.tv_list_attence_js_item_xinzeng = (TextView) convertView
					.findViewById(R.id.tv_list_attence_js_item_xinzeng);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.resetContentAndListener();
		}

		Attence attence = listItems.get(position);

		if (null != attence.getClaName() && !attence.getClaName().equals("")) {
			holder.tv_list_attence_js_item_classname.setText(attence
					.getClaName());
		} else {
			holder.tv_list_attence_js_item_classname.setText("");
		}

		boolean isAttenceEditAble = false; // 考勤是否可编辑
		if (attence.getAttenceId() == null || attence.getAttenceId().equals("")) {
			holder.tv_list_attence_js_item_attenceinfo.setVisibility(View.GONE);
			holder.iv_list_attence_js_item_foldoropen.setVisibility(View.GONE);
			holder.tv_list_attence_js_item_xinzeng.setVisibility(View.VISIBLE);
			holder.iv_list_attence_js_item_edit.setVisibility(View.GONE);
		} else {
			isAttenceEditAble = true;
			holder.tv_list_attence_js_item_attenceinfo
					.setVisibility(View.VISIBLE);
			holder.iv_list_attence_js_item_foldoropen
					.setVisibility(View.VISIBLE);
			holder.tv_list_attence_js_item_xinzeng.setVisibility(View.GONE);

			// 超级管理员不只能查看考勤，不可对考勤编辑或者修改
			UserInfo userInfo = AppContext.getApp().getUserLoginSharedPre()
					.getUserInfo();
			if (!Constant.ORG_ROLE_TYPE_TEACHER.equals(userInfo
					.getCurrentRole())
					&& !userInfo.getUserId().equals(attence.getUserId())) {
				holder.iv_list_attence_js_item_edit.setVisibility(View.GONE);
			} else {
				holder.iv_list_attence_js_item_edit.setVisibility(View.VISIBLE);
			}
		}

		// 没有可考勤的学员或者进行删除过的考勤记录
		boolean detailListHasNoData = (attence.getAttenceDetailList() == null || attence
				.getAttenceDetailList().size() == 0);
		//没有考勤明细
		if(detailListHasNoData){
			holder.tv_list_attence_js_item_xinzeng.setText(R.string.attence_class_no_student);
			holder.tv_list_attence_js_item_xinzeng.setTextColor(Color.GRAY);
			return convertView;
		}else{
			//有明细的情况下，判断是否已删除
			if(attence.isDeleted()){
				holder.ngv_list_attence_js_item_attencelist.setAdapter(null);
				holder.ll_list_attence_js_item_content.setVisibility(View.GONE);
			}else{
				GridViewAttenceAdapter gridViewAttenceAdapter = new GridViewAttenceAdapter(
						context, attence.getAttenceDetailList());
				holder.ngv_list_attence_js_item_attencelist
						.setAdapter(gridViewAttenceAdapter);
			}
		}

		holder.tv_list_attence_js_item_attenceinfo.setText("已到/未到:"
				+ attence.getAttendNum() + "/" + attence.getAbsentNum());

		/*
		 * if (null != attence.getExpendedChourNum() &&
		 * !attence.getExpendedChourNum().equals("")) {
		 * holder.tv_list_attence_js_item_xhks_content.setText(attence
		 * .getExpendedChourNum()); } else {
		 * holder.tv_list_attence_js_item_xhks_content.setText(""); }
		 */

		if (null != attence.getRecTime() && !attence.getRecTime().equals("")) {
			holder.tv_list_attence_js_item_attencetime_content.setText(attence
					.getRecTime());
		} else {
			holder.tv_list_attence_js_item_attencetime_content.setText("");
		}
		if (null != attence.getUserName() && !attence.getUserName().equals("")) {
			holder.tv_list_attence_js_item_username_content.setText(attence
					.getUserName());

		} else {
			holder.tv_list_attence_js_item_username_content.setText("");
		}

		// 需要考勤，绑定相关事件
		// isAttenceEditAble为false时不能编辑、删除或查，只能新增
		if (!isAttenceEditAble) {
			holder.tv_list_attence_js_item_xinzeng
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									AttenceSaveTeacherActivity.class);
							intent.putExtra("listItems",
									(ArrayList<Attence>) listItems);
							intent.putExtra("position", position);
							intent.putExtra("dateString", dateString);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}
					});
		}
		// 否则可编辑、删除和查看
		else {
			holder.iv_list_attence_js_item_edit
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							showPopupWindow(v, position);
						}
					});

			holder.iv_list_attence_js_item_foldoropen
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (v.getTag().equals("open")) {
								holder.ll_list_attence_js_item_content
										.setVisibility(View.GONE);
								holder.iv_list_attence_js_item_foldoropen
										.setImageResource(R.drawable.fold);
								holder.iv_list_attence_js_item_foldoropen
										.setTag("fold");
							} else {
								holder.ll_list_attence_js_item_content
										.setVisibility(View.VISIBLE);
								holder.iv_list_attence_js_item_foldoropen
										.setImageResource(R.drawable.open);
								holder.iv_list_attence_js_item_foldoropen
										.setTag("open");
							}

						}
					});
		}

		holder.ll_tv_list_attence_js_item_title_left
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						holder.iv_list_attence_js_item_edit.performClick();

					}
				});
		holder.ll_tv_list_attence_js_item_title_right
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						holder.iv_list_attence_js_item_foldoropen
								.performClick();

					}
				});
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_attence_js_item_edit;// logo
		ImageView iv_list_attence_js_item_foldoropen;
		TextView tv_list_attence_js_item_classname;//
		TextView tv_list_attence_js_item_attenceinfo;//
		LinearLayout ll_list_attence_js_item_content;
		TextView tv_list_attence_js_item_attencetime_content;//
		TextView tv_list_attence_js_item_username_content;//
		NoScrollGridView ngv_list_attence_js_item_attencelist;
		LinearLayout ll_tv_list_attence_js_item_title_left;
		LinearLayout ll_tv_list_attence_js_item_title_right;
		TextView tv_list_attence_js_item_xinzeng;

		protected void resetContentAndListener() {
			if (tv_list_attence_js_item_xinzeng != null) {
				tv_list_attence_js_item_xinzeng
						.setText(R.string.attence_class_add_attence);
				tv_list_attence_js_item_xinzeng.setTextColor(Color.BLACK);
			}
			if (iv_list_attence_js_item_edit != null) {
				iv_list_attence_js_item_edit.setOnClickListener(null);
			}
			if (iv_list_attence_js_item_foldoropen != null) {
				iv_list_attence_js_item_foldoropen.setOnClickListener(null);
			}
			if (ll_tv_list_attence_js_item_title_left != null) {
				ll_tv_list_attence_js_item_title_left.setOnClickListener(null);
			}
			if (ll_tv_list_attence_js_item_title_right != null) {
				ll_tv_list_attence_js_item_title_right.setOnClickListener(null);
			}
			if (ngv_list_attence_js_item_attencelist != null) {
				ngv_list_attence_js_item_attencelist
						.setOnItemClickListener(null);
				ngv_list_attence_js_item_attencelist.setAdapter(null);
			}
		}
	}

	private void showPopupWindow(View view, final int position) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.list_popwindow_item, null);
		// 设置点击事件
		TextView tv_list_popwindow_edit = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_edit);
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
				Intent intent = new Intent(context,
						AttenceEditTeacherActivity.class);
				intent.putExtra("listItems", (ArrayList<Attence>) listItems);
				intent.putExtra("position", position);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});

		// 设置是否显示删除菜单:非创建考勤用户隐藏删除按钮
		UserInfo userInfo = AppContext.getApp().getUserLoginSharedPre()
				.getUserInfo();
		String loginUserId = userInfo.getUserId();
		String attUserId = listItems.get(position).getUserId();
		if (!loginUserId.equals(attUserId)) {
			tv_list_popwindow_delete.setVisibility(View.GONE);
		} else {
			tv_list_popwindow_delete.setVisibility(View.VISIBLE);
			tv_list_popwindow_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i(TAG, "删除");
					new DeleteAttenceTask(position)
							.execute(AppContext.getApp().getToken(), listItems
									.get(position).getAttenceId());
					popupWindow.dismiss();
				}
			});
		}
		// 设置好参数之后再show
		popupWindow.showAsDropDown(view, -55, 15);

	}

	/**
	 * 删除动态 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class DeleteAttenceTask extends AsyncTask<String, Void, Result> {
		int position;
		AppException e;

		public DeleteAttenceTask(int position) {
			this.position = position;
			Log.i(TAG, "position=" + position);
		}

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected Result doInBackground(String... params) {
			Result result = null;
			try {
				result = AppContext.getApp().delAttence(params[0], params[1]);
			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
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
					UIHelper.ToastMessage(context, "考勤已成功删除", Toast.LENGTH_LONG);
					listItems.get(position).setAttenceId("");
					listItems.get(position).setDeleted(true);
					ListViewAttenceTeacherAdapter.this.notifyDataSetChanged();
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
