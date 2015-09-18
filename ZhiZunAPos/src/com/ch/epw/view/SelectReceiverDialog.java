package com.ch.epw.view;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectReceiverDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;

	private LeaveMyDialogListener listener;
	RelativeLayout rl_activity_fstz_teacher_receviers_sendteacher;
	RelativeLayout rl_activity_fstz_teacher_receviers_sendstudent;

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public SelectReceiverDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public SelectReceiverDialog(Context context, int theme,
			LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_fstz_teacher_selectreceiver);
		rl_activity_fstz_teacher_receviers_sendteacher = (RelativeLayout) findViewById(R.id.rl_activity_fstz_teacher_receviers_sendteacher);
		rl_activity_fstz_teacher_receviers_sendstudent = (RelativeLayout) findViewById(R.id.rl_activity_fstz_teacher_receviers_sendstudent);
		rl_activity_fstz_teacher_receviers_sendteacher.setOnClickListener(this);
		rl_activity_fstz_teacher_receviers_sendstudent.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}
}