package com.ch.epw.jz.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.StudentTeacherOrgClass;
import com.zhizun.pos.bean.StudentTeacherOrgClassList;
import com.zhizun.pos.bean.TeacherInfo;
import com.zhizun.pos.bean.TeacherOrgClass;

/**
 * 我的邀请 发送邀请时候选择学生 创建人：李林中 创建日期：2014-12-15 上午10:09:11 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class MyepeiMyClassActivity extends BaseActivity {

	private TitleBarView titleBarView;
	Context context;
	ArrayList<TeacherOrgClass> teacherOrgClasseList;
	List<TeacherInfo> teacherInfoList;
	List<StudentTeacherOrgClass> studentTeacherOrgClasseList;

//	StudentTeacherOrgClassList studentTeacherOrgClassList;
	ExpandableAdapter expandableAdapter;
	LinearLayout ll_myepei_myclass_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myepei_myclass_parent);
		context = this;
		studentTeacherOrgClasseList = (List<StudentTeacherOrgClass>) getIntent()
				.getSerializableExtra("studentTeacherOrgClassList");
		initView();
		options = Options.getListOptions();
		initData();

	}

	private void initData() {
		for (int i = 0; i < studentTeacherOrgClasseList.size(); i++) {
			View llLayout = View.inflate(context,
					R.layout.list_myepei_myclass_parent_item, null);
			TextView tv_list_myepei_myclass_parent_item_stuname = (TextView) llLayout
					.findViewById(R.id.tv_list_myepei_myclass_parent_item_stuname);
			ExpandableListView el_list_myepei_myclass_parent_item_teacherlist = (ExpandableListView) llLayout
					.findViewById(R.id.el_list_myepei_myclass_parent_item_teacherlist);
			final StudentTeacherOrgClass studentTeacherOrgClass = studentTeacherOrgClasseList
					.get(i);
			tv_list_myepei_myclass_parent_item_stuname
					.setText(studentTeacherOrgClass.getChildName());
			teacherOrgClasseList = studentTeacherOrgClass
					.getTecheOrgClasseList();
			expandableAdapter = new ExpandableAdapter(context,
					teacherOrgClasseList);
			el_list_myepei_myclass_parent_item_teacherlist
					.setAdapter(expandableAdapter);
			el_list_myepei_myclass_parent_item_teacherlist.setDivider(null);
			// el_list_myepei_myclass_parent_item_teacherlist.setDivider(getResources().getDrawable(R.drawable.itembg));
			// el_list_myepei_myclass_parent_item_teacherlist.setChildDivider(getResources().getDrawable(R.drawable.left_drawer_count_bg));
			el_list_myepei_myclass_parent_item_teacherlist
					.setOnChildClickListener(new OnChildClickListener() {

						@Override
						public boolean onChildClick(ExpandableListView parent,
								View v, int groupPosition, int childPosition,
								long id) {
							ExpandableListAdapter adapter = parent
									.getExpandableListAdapter();
							// 得到班级中的学生实体
							TeacherInfo teacherInfo = (TeacherInfo) adapter
									.getChild(groupPosition, childPosition);
							Intent intent = new Intent(Intent.ACTION_DIAL, Uri
									.parse("tel:" + teacherInfo.getPhone()));
							startActivity(intent);
							return false;
						}
					});
			ll_myepei_myclass_item.addView(llLayout);
		}
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_myepei_techer);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_myclass);

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});

		ll_myepei_myclass_item = (LinearLayout) findViewById(R.id.ll_myepei_myclass_item);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	class ExpandableAdapter extends BaseExpandableListAdapter {
		Context context;
		ArrayList<TeacherOrgClass> classTeacherList;

		public ExpandableAdapter(Context context,
				ArrayList<TeacherOrgClass> orgTeaList) {
			this.context = context;
			this.classTeacherList = orgTeaList;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return classTeacherList.get(groupPosition).getTeacherInfoList()
					.get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return classTeacherList.get(groupPosition).getTeacherInfoList()
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

			final TeacherInfo teacherInfo = (TeacherInfo) getChild(
					groupPosition, childPosition);
			showPicture(teacherInfo.getPhotoPath(),
					iv_senddynamic_child_selectstudent_item_logo, options);
			tv_senddynamic_child_selectstudent_item_name.setText(teacherInfo
					.getName());
			if (teacherInfo.getPhone() != null
					&& !teacherInfo.getPhone().equals("")) {
				tv_senddynamic_child_selectstudent_item_phonenumber
						.setText(teacherInfo.getPhone());
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
			return classTeacherList.get(groupPosition);
		}

		public int getGroupCount() {
			return classTeacherList.size();
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

			final TeacherOrgClass sc = classTeacherList.get(groupPosition);
			tv_senddynamic_selectstudent_item_name.setText(sc.getOrgName()
					+ "    " + sc.getClaName());

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
