package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.StudentClass;
import com.zhizun.pos.bean.StudentClassList;
import com.zhizun.pos.bean.StudentInfo;

/**
 * 我的班级 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiMyClassTeacherActivity extends BaseActivity {

	private TitleBarView titleBarView;
	Context context;
	ExpandableListView el_myinvitation_senddynamic_selectstudent;
	ArrayList<StudentClass> studentClassesList;
	List<StudentInfo> studentInfoList;
	StudentClassList studentClassList;
	ExpandableAdapter expandableAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinvitation_senddynamic_selectstudent);
		context = this;
		studentClassesList = (ArrayList<StudentClass>) getIntent().getSerializableExtra(
				"studentClassList");
		initView();
		options = Options.getListOptions();
		initData();

	}

	private void initData() {
//		studentClassesList = studentClassList.getStudentClassesList();
		expandableAdapter = new ExpandableAdapter(context);
		// 得到实际的ListView
		el_myinvitation_senddynamic_selectstudent.setAdapter(expandableAdapter);
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_myinvitation_senddynamic_selectstudent);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_myclass);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		titleBarView.setRightText(R.string.text_title_change);
		titleBarView.getRightTextView().setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								ChangeClassSelectStudentActivity.class);
						intent.putExtra("studentClassesList",
								studentClassesList);
						startActivityForResult(intent,
								Constant.REQUSTCONDE_SELECTCLASS);
						intoAnim();
					}
				});
		el_myinvitation_senddynamic_selectstudent = (ExpandableListView) findViewById(R.id.el_myinvitation_senddynamic_selectstudent);
		// el_myinvitation_senddynamic_selectstudent.setDivider(getResources().getDrawable(R.drawable.itembg));
		el_myinvitation_senddynamic_selectstudent.setDivider(null);
		el_myinvitation_senddynamic_selectstudent
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// 得到某个班级
						StudentClass studentClass = studentClassesList
								.get(groupPosition);
						// 得到班级中的学生实体
						StudentInfo studentInfo = studentClass
								.getStudentInfoList().get(childPosition);
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri
								.parse("tel:" + studentInfo.getPhone()));
						startActivity(intent);
						return false;
					}
				});
	}

	// 获得学生列表
	private void getStudentList() {
		showProgressDialog(context, "",
				getResources().getString(R.string.load_ing));
		new GetStudentAndClassTask().execute(AppContext.getApp().getToken());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (Constant.REQUSTCONDE_SELECTCLASS == requestCode
				&& Constant.REQUSTCONDE_SELECTCLASS == resultCode) {
			getStudentList();
		}
	}

	/**
	 * 获取学生和班级 创建人：李林中 创建日期：2014-12-9 上午10:42:07 作用： 修改
	 * =================================================== 修改人 修改日期 原因(描述)
	 * ===================================================
	 */
	private class GetStudentAndClassTask extends
			AsyncTask<String, Void, StudentClassList> {
		AppException e;

		// 执行这个方法 此方法重新开启一个来执行网络接口读数据的
		// 操作，将得到的结果List<Preferential>，传送给onPostExecute方法
		@Override
		protected StudentClassList doInBackground(String... params) {

			try {
				studentClassList = AppContext.getApp().getStudentClassList(
						params[0]);

			} catch (AppException e) {
				// TODO Auto-generated catch block
				this.e = e;
				e.printStackTrace();
				studentClassList = null;
			}
			return studentClassList;
		}

		// 这个方法可以执行UI线程里操作界面的东西，类似于new handler里面的东东
		@Override
		protected void onPostExecute(StudentClassList result) {

			super.onPostExecute(result);
			closeProgressDialog();
			if (result != null) {

				if (result.getStatus().equals("0")) {
					initData();
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
			return studentClassesList.get(groupPosition).getStudentInfoList()
					.get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return studentClassesList.get(groupPosition).getStudentInfoList()
					.size();
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = View.inflate(context,
					R.layout.myepei_myclass_child_selectstudent_item, null);
			ImageView iv_senddynamic_child_selectstudent_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_child_selectstudent_item_logo);
			TextView tv_senddynamic_child_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_child_selectstudent_item_name);

			TextView tv_senddynamic_child_selectstudent_item_phonenumber = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_child_selectstudent_item_phonenumber);

			ImageView iv_senddynamic_child_selectstudent_item_phone = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_child_selectstudent_item_phone);
			final StudentInfo studentInfo = studentClassesList
					.get(groupPosition).getStudentInfoList().get(childPosition);
			showPicture(studentInfo.getPhotoPath(),
					iv_senddynamic_child_selectstudent_item_logo, options);
			tv_senddynamic_child_selectstudent_item_name.setText(studentInfo
					.getName());
			if (studentInfo.getPhone() != null
					&& !studentInfo.getPhone().equals("")) {
				tv_senddynamic_child_selectstudent_item_phonenumber
						.setText(studentInfo.getPhone());
				iv_senddynamic_child_selectstudent_item_phone
						.setVisibility(View.VISIBLE);
			} else {
				tv_senddynamic_child_selectstudent_item_phonenumber.setText("");
				iv_senddynamic_child_selectstudent_item_phone
						.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		// group method stub
		public Object getGroup(int groupPosition) {
			return studentClassesList.get(groupPosition);
		}

		public int getGroupCount() {
			return studentClassesList.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context,
						R.layout.myepei_myclass_selectstudent_item, null);
			}
			TextView tv_senddynamic_selectstudent_item_name = (TextView) convertView
					.findViewById(R.id.tv_senddynamic_selectstudent_item_name);
			ImageView iv_senddynamic_selectstudent_item_arrow = (ImageView) convertView
					.findViewById(R.id.iv_senddynamic_selectstudent_item_arrow);

			final StudentClass sc = studentClassesList.get(groupPosition);
			tv_senddynamic_selectstudent_item_name.setText(sc.getName());

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

}
