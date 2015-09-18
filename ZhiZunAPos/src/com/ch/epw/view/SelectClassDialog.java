package com.ch.epw.view;

import java.util.List;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewRoleAdapter;
import com.zhizun.pos.adapter.ListViewSelectClassAdapter;
import com.zhizun.pos.bean.Org;
import com.zhizun.pos.bean.StudentInfo;
import com.zhizun.pos.bean.UserRole;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.drm.DrmStore.RightsStatus;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SelectClassDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private Button btn_dialog_selectstudent_yes, btn_dialog_selectstudent_no;
	private LeaveMyDialogListener listener;
	ListView ll_dialog_selecter_class_list;
	ListViewSelectClassAdapter listViewSelectClassAdapter;
	List<Org> orgList;
	EditText et_dialog_selecter_class_note;

	int maxInputLen = -1; // 最长输入长度

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public SelectClassDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public SelectClassDialog(Context context, int theme, List<Org> orgList,
			int maxInputLen, LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.orgList = orgList;
		this.maxInputLen = maxInputLen;
	}

	// 编辑框带最大长度限制的构造函数
	public SelectClassDialog(Context context, int theme, String title,
			String content, int maxInputLen, LeaveMyDialogListener listener) {
		super(context, theme);
		this.context = context;
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_selecter_class);
	
		btn_dialog_selectstudent_yes = (Button) findViewById(R.id.btn_dialog_selectstudent_yes);
		btn_dialog_selectstudent_no = (Button) findViewById(R.id.btn_dialog_selectstudent_no);
		et_dialog_selecter_class_note = (EditText) findViewById(R.id.et_dialog_selecter_class_note);
		ll_dialog_selecter_class_list = (ListView) findViewById(R.id.ll_dialog_selecter_class_list);
		listViewSelectClassAdapter = new ListViewSelectClassAdapter(context,
				orgList);
		ll_dialog_selecter_class_list.setAdapter(listViewSelectClassAdapter);
		ll_dialog_selecter_class_list
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// 判断班级是否选中，如果选中 则反选
						for (int i = 0; i < orgList.size(); i++) {
							if (i == position) {
								if (orgList.get(i).getCheckState()) {// 选中了班级
									orgList.get(i).setCheckState(false);
								} else {// 没选中
									orgList.get(i).setCheckState(true);// 选中
								}
							} else {
								orgList.get(i).setCheckState(false);
							}
						}
						listViewSelectClassAdapter.notifyDataSetChanged();
					}
				});
		
		btn_dialog_selectstudent_yes.setOnClickListener(this);
		btn_dialog_selectstudent_no.setOnClickListener(this);
		//如果设置了输入框的最大输入长度
				if(maxInputLen > 0){
					setMaxInputLen(maxInputLen);
				}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}

	public ListViewSelectClassAdapter getListViewSelectClassAdapter() {
		return listViewSelectClassAdapter;
	}

	public void setListViewSelectClassAdapter(
			ListViewSelectClassAdapter listViewSelectClassAdapter) {
		this.listViewSelectClassAdapter = listViewSelectClassAdapter;
	}

	public int getMaxInputLen() {
		return maxInputLen;
	}

	protected void setMaxInputLen(int maxInputLen) {
		this.maxInputLen = maxInputLen;
		if (this.maxInputLen > 0 && et_dialog_selecter_class_note != null) {
			InputFilter[] filters = { new InputFilter.LengthFilter(
					this.maxInputLen) };
			et_dialog_selecter_class_note.setFilters(filters);
		}
	}
}