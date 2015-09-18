package com.ch.epw.view;

import java.util.List;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewRoleAdapter;
import com.zhizun.pos.adapter.ListViewSelectClassAdapter;
import com.zhizun.pos.bean.Org;
import com.zhizun.pos.bean.StudentInfo;
import com.zhizun.pos.bean.UserRole;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.drm.DrmStore.RightsStatus;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 毕业 退班 
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class GraduationQuitClassDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private Button btn_dialog_selectstudent_yes, btn_dialog_selectstudent_no;
	TextView tv_dialog_select_class_title_text;
	ImageView iv_dialog_select_class_title_img;

	private LeaveMyDialogListener listener;
	String content;
	int resId;

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public GraduationQuitClassDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public GraduationQuitClassDialog(Context context, int theme,
			String content, int resId, LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.content = content;
		this.resId = resId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_biyetuiban_class);
		btn_dialog_selectstudent_yes = (Button) findViewById(R.id.btn_dialog_selectstudent_yes);
		btn_dialog_selectstudent_no = (Button) findViewById(R.id.btn_dialog_selectstudent_no);
		tv_dialog_select_class_title_text = (TextView) findViewById(R.id.tv_dialog_select_class_title_text);
		tv_dialog_select_class_title_text.setText(content);
		iv_dialog_select_class_title_img = (ImageView) findViewById(R.id.iv_dialog_select_class_title_img);
		iv_dialog_select_class_title_img.setImageResource(resId);

		btn_dialog_selectstudent_yes.setOnClickListener(this);
		btn_dialog_selectstudent_no.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}

}