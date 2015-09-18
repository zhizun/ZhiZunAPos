package com.ch.epw.view;


import com.zhizun.pos.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyInputDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private String title;
	private String content;
	private TextView tv_dialog_myinput_title;
	private Button btn_dialog_selectstudent_yes, btn_dialog_selectstudent_no;
	private LeaveMyDialogListener listener;
	private EditText et_dialog_myinput_content;
	
	int maxInputLen = -1;	//最长输入长度

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public MyInputDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyInputDialog(Context context, int theme, String title,
			String content, LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.title = title;
		this.content = content;
	}
	//编辑框带最大长度限制的构造函数
	public MyInputDialog(Context context, int theme, String title,
			String content, int maxInputLen, LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.title = title;
		this.content = content;
		this.maxInputLen = maxInputLen;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_myinput);
		btn_dialog_selectstudent_yes = (Button) findViewById(R.id.btn_dialog_selectstudent_yes);
		btn_dialog_selectstudent_no = (Button) findViewById(R.id.btn_dialog_selectstudent_no);
		tv_dialog_myinput_title = (TextView) findViewById(R.id.tv_dialog_myinput_title);
		tv_dialog_myinput_title.setText(title);
		et_dialog_myinput_content = (EditText) findViewById(R.id.et_dialog_myinput_content);
		et_dialog_myinput_content.setText(content);
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

	public int getMaxInputLen() {
		return maxInputLen;
	}

	protected void setMaxInputLen(int maxInputLen) {
		this.maxInputLen = maxInputLen;
		if(this.maxInputLen > 0 && et_dialog_myinput_content != null){
			InputFilter[] filters = {new InputFilter.LengthFilter(this.maxInputLen)};
			et_dialog_myinput_content.setFilters(filters);
		}
	}
}