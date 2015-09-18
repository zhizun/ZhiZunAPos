package com.ch.epw.view;

import com.zhizun.pos.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private String content;
	private TextView tv_dialog_selectstudent_content;
	private Button btn_dialog_selectstudent_yes, btn_dialog_selectstudent_no;
	private LeaveMyDialogListener listener;

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyDialog(Context context, int theme,String content, LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.content=content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_selectstudent);
		btn_dialog_selectstudent_yes = (Button) findViewById(R.id.btn_dialog_selectstudent_yes);
		btn_dialog_selectstudent_no = (Button) findViewById(R.id.btn_dialog_selectstudent_no);
		tv_dialog_selectstudent_content = (TextView) findViewById(R.id.tv_dialog_selectstudent_content);
		tv_dialog_selectstudent_content.setText(content);
		btn_dialog_selectstudent_yes.setOnClickListener(this);
		btn_dialog_selectstudent_no.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}
}