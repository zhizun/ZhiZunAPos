package com.ch.epw.jz.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.TeacherInfo;
import com.zhizun.pos.bean.TeacherOrg;
import com.zhizun.pos.bean.TeacherOrgList;

/**
 * 发表心声的时候选择机构和教师 单选 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class SingleSelectTeacherActivity extends BaseActivity {

	private TitleBarView titleBarView;
	Context context;
	ExpandableListView el_myinvitation_senddynamic_selectstudent;
	ArrayList<TeacherOrg> teacherAndOrglist;
	List<TeacherInfo> teacherInfoList;
	TeacherOrgList teacherOrgList;
	ExpandableAdapter expandableAdapter;
	LinearLayout ll_no_items_listed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinvitation_senddynamic_selectstudent);
		context = this;
		initView();
		options = Options.getListOptions();
		ArrayList<TeacherOrg> list = (ArrayList<TeacherOrg>) getIntent()
				.getSerializableExtra("teacherAndOrglist");
		if (null == list) {
			new GetStudentAndClassTask()
					.execute(AppContext.getApp().getToken());
		} else {
			if (list.size() <= 0) {
				ll_no_items_listed.setVisibility(View.VISIBLE);
				return;
			} else {
				ll_no_items_listed.setVisibility(View.GONE);
				teacherAndOrglist = list;
				expandableAdapter = new ExpandableAdapter(context);
				// 得到实际的ListView
				el_myinvitation_senddynamic_selectstudent
						.setAdapter(expandableAdapter);
			}

		}

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_myinvitation_senddynamic_selectstudent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_choose_receiver);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.getRightTextView().setText("确定");
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				intent.putExtra("teacherAndOrglist", teacherAndOrglist);
				SingleSelectTeacherActivity.this.setResult(
						Constant.SEND_NOTICE_SELCET_SUTDENT, intent);
				SingleSelectTeacherActivity.this.finish();
				backAnim();
			}
		});
		el_myinvitation_senddynamic_selectstudent = (ExpandableListView) findViewById(R.id.el_myinvitation_senddynamic_selectstudent);
		el_myinvitation_senddynamic_selectstudent
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// 得到某个班级
						TeacherOrg teacherOrg = teacherAndOrglist
								.get(groupPosition);
						// 得到班级中的学生实体
						TeacherInfo teacherInfo = teacherOrg
								.getTeacherInfoList().get(childPosition);
						// 判断学生是否选中，如果选中 则反选
						if (teacherInfo.getCheckState()) {// 选中了学生
							teacherInfo.setCheckState(false);
							// 如果班级 也就是父list也选中了 那么反选，因为少选一项 就意味着不是全选
							if (teacherOrg.getCheckState()) {
								teacherOrg.setCheckState(false);
							}
						} else {// 没选中学生

							teacherInfo.setCheckState(true);// 选中
							boolean isAllSelected = true;// 定义一个是否全选 设为true
							// 遍历这个班级里面的学生列表，如果有一个学生没有选中 那么就代表没全选 跳出循环
							for (int i = 0; i < teacherOrg.getTeacherInfoList()
									.size(); i++) {
								TeacherInfo sti = teacherOrg
										.getTeacherInfoList().get(i);
								if (!sti.getCheckState()) {
									isAllSelected = false;
									break;
								}
							}
							// 如果全选那么 班级也选中
							if (isAllSelected) {
								teacherOrg.setCheckState(true);
							}

							// 如果本项选择了 那么其他项不要选择
							if (teacherAndOrglist.get(groupPosition)
									.getTeacherInfoList().get(childPosition)
									.getCheckState()) {
								for (int i = 0; i < teacherAndOrglist.size(); i++) {
									if (i != groupPosition) {
										teacherAndOrglist.get(i).setCheckState(
												false);
										for (int j = 0; j < teacherAndOrglist
												.get(i).getTeacherInfoList()
												.size(); j++) {
											teacherAndOrglist.get(i)
													.getTeacherInfoList()
													.get(j)
													.setCheckState(false);
										}
									}
								}

							}

						}
						expandableAdapter.notifyDataSetChanged();
						return false;
					}
				});
		ll_no_items_listed = (LinearLayout) this
				.findViewById(R.id.ll_no_items_listed);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	class ExpandableAdapter extends BaseExpandableListAdapter {
		Context context;

		public ExpandableAdapter(Context context) {
			this.context = context;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return teacherAndOrglist.get(groupPosition).getTeacherInfoList()
					.get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return teacherAndOrglist.get(groupPosition).getTeacherInfoList()
					.size();
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			convertView = View.inflate(context,
					R.layout.selectteacer_child_item, null);

			ImageView iv_senddynamic_child_selectstudent_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_child_selectstudent_item_logo);
			TextView tv_senddynamic_child_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_child_selectstudent_item_name);
			CheckBox cb_senddynamic_child_selectstudent_item_state = (CheckBox) convertView
					.findViewById(R.id.cb_senddynamic_child_selectstudent_item_state);
			TextView tv_senddynamic_child_selectstudent_item_age = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_child_selectstudent_item_age);

			final TeacherInfo teacherInfo = teacherAndOrglist
					.get(groupPosition).getTeacherInfoList().get(childPosition);
			showPicture(teacherInfo.getPhotoPath(),
					iv_senddynamic_child_selectstudent_item_logo, options);
			tv_senddynamic_child_selectstudent_item_name.setText(teacherInfo
					.getName());
			tv_senddynamic_child_selectstudent_item_age.setText(teacherInfo.getPost());

			cb_senddynamic_child_selectstudent_item_state
					.setChecked(teacherInfo.getCheckState());
			return convertView;
		}

		// group method stub
		public Object getGroup(int groupPosition) {
			return teacherAndOrglist.get(groupPosition);
		}

		public int getGroupCount() {
			return teacherAndOrglist.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(final int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.senddynamic_selectstudent_item, null);
			}
			TextView tv_senddynamic_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_selectstudent_item_name);
			ImageView iv_senddynamic_selectstudent_item_arrow = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_selectstudent_item_arrow);
			CheckBox cb_senddynamic_selectstudent_item_state = (CheckBox) convertView
					.findViewById(R.id.cb_senddynamic_selectstudent_item_state);

			final TeacherOrg sc = teacherAndOrglist.get(groupPosition);
			tv_senddynamic_selectstudent_item_name.setText(sc.getName());
			cb_senddynamic_selectstudent_item_state
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							sc.setCheckState(!sc.getCheckState());
							for (int i = 0; i < sc.getTeacherInfoList().size(); i++) {
								TeacherInfo sti = sc.getTeacherInfoList()
										.get(i);
								sti.setCheckState(sc.getCheckState());
							}

							// 如果本项选择了 那么其他项不要选择
							if (teacherAndOrglist.get(groupPosition)
									.getCheckState()) {
								for (int i = 0; i < teacherAndOrglist.size(); i++) {
									if (i != groupPosition) {
										teacherAndOrglist.get(i).setCheckState(
												false);
										for (int j = 0; j < teacherAndOrglist
												.get(i).getTeacherInfoList()
												.size(); j++) {
											teacherAndOrglist.get(i)
													.getTeacherInfoList()
													.get(j)
													.setCheckState(false);
										}
									}
								}

							}

							expandableAdapter.notifyDataSetChanged();
						}

					});
			cb_senddynamic_selectstudent_item_state
					.setChecked(teacherAndOrglist.get(groupPosition)
							.getCheckState());

			// 判断isExpanded就可以控制是按下还是关闭，同时更换图片
			if (isExpanded) {
				iv_senddynamic_selectstudent_item_arrow
						.setBackgroundResource(R.drawable.arrow_down);
			} else {
				iv_senddynamic_selectstudent_item_arrow
						.setBackgroundResource(R.drawable.arrow_right);
			}

			return convertView;
		}

		public boolean hasStableIds() {
			return false;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	/**
	 * 获取动态 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetStudentAndClassTask extends
			AsyncTask<String, Void, TeacherOrgList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected TeacherOrgList doInBackground(String... params) {

			try {
				teacherOrgList = AppContext.getApp().getUserToTeachers(
						params[0]);

			} catch (AppException e) {
				this.e = e;
				// TODO Auto-generated catch block
				e.printStackTrace();
				teacherOrgList = null;
			}
			return teacherOrgList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(TeacherOrgList result) {

			super.onPostExecute(result);
			closeProgressDialog();
			if (result != null) {

				if (result.getStatus().equals("0")) {
					if (result.getTeacherOrgList().size() <= 0) {
						ll_no_items_listed.setVisibility(View.VISIBLE);
						return;
					} else {
						ll_no_items_listed.setVisibility(View.GONE);
						teacherAndOrglist = result.getTeacherOrgList();
						expandableAdapter = new ExpandableAdapter(context);
						// 得到实际的ListView
						el_myinvitation_senddynamic_selectstudent
								.setAdapter(expandableAdapter);
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
