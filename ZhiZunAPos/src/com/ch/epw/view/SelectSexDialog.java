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
import android.widget.TextView;

public class SelectSexDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;

	private Button btn_dialog_selectstudent_yes, btn_dialog_selectstudent_no;
	private LeaveMyDialogListener listener;
	CheckBox cb_dialog_selectsex_man;
	CheckBox cb_dialog_selectsex_female;
	LinearLayout ll_dialog_selectsex_female;
	LinearLayout ll_dialog_selectsex_man;
	String checkedSex;// 已经选择的性别

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public SelectSexDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public SelectSexDialog(Context context, int theme, String checkedSex,
			LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.checkedSex = checkedSex;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_selectsex);
		btn_dialog_selectstudent_yes = (Button) findViewById(R.id.btn_dialog_selectstudent_yes);
		btn_dialog_selectstudent_no = (Button) findViewById(R.id.btn_dialog_selectstudent_no);
		cb_dialog_selectsex_female = (CheckBox) findViewById(R.id.cb_dialog_selectsex_female);
		cb_dialog_selectsex_man = (CheckBox) findViewById(R.id.cb_dialog_selectsex_man);

		ll_dialog_selectsex_female = (LinearLayout) findViewById(R.id.ll_dialog_selectsex_female);
		ll_dialog_selectsex_man = (LinearLayout) findViewById(R.id.ll_dialog_selectsex_man);
		if (checkedSex.equals(Constant.SEX_FEMALE)) {
			cb_dialog_selectsex_female.setChecked(true);
		} else {
			cb_dialog_selectsex_man.setChecked(true);
		}

		ll_dialog_selectsex_female
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cb_dialog_selectsex_female.setChecked(true);
						cb_dialog_selectsex_man.setChecked(false);
					}
				});
		ll_dialog_selectsex_man.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cb_dialog_selectsex_female.setChecked(false);
				cb_dialog_selectsex_man.setChecked(true);
			}
		});
		btn_dialog_selectstudent_yes.setOnClickListener(this);
		btn_dialog_selectstudent_no.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}
}