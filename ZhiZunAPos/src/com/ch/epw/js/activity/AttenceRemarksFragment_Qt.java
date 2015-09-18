package com.ch.epw.js.activity;


import java.util.ArrayList;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Remarks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AttenceRemarksFragment_Qt extends Fragment implements OnClickListener {
	private ArrayList<AttenceDetail> remarksList;
	private EditText et_qt;
	private Button bt_qt_deselect;
	private Button bt_qt_sure;
	private int position;
	AttenceDetail getRemarks;
	public final int MAX_LENGTH = 300;// 最大字符数
	public int Rest_Length = 0;// 剩余可输入字符数
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		System.out.println("MainTab2 onCreate");
		remarksList=(ArrayList<AttenceDetail>) getArguments().getSerializable("remarks");
		position=getArguments().getInt("position");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.attenceremarks_tab_qt, null);
		et_qt=(EditText) view.findViewById(R.id.et_qt);
		bt_qt_sure=(Button) view.findViewById(R.id.bt_qt_sure);
		bt_qt_deselect=(Button) view.findViewById(R.id.bt_qt_deselect);
		bt_qt_sure.setOnClickListener(this);
		bt_qt_deselect.setOnClickListener(this);
		getRemarks=remarksList.get(position);
		if (getRemarks.getRemarks().size()>0) {
			if (getRemarks.getRemarks().get(0).getTypt().equals(Constant.KQ_QT)) {
				et_qt.setText(getRemarks.getRemarks().get(0).getNote());
			}
		}
		et_qt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				Rest_Length = et_qt.getText().toString().trim().length();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {

			}
			@Override
			public void afterTextChanged(Editable s) {
				if (Rest_Length > MAX_LENGTH) {
					UIHelper.ToastMessage(getActivity(), "最多只能输入"+MAX_LENGTH+"个字");
					et_qt.setText(et_qt
							.getText().toString().trim()
							.substring(0, MAX_LENGTH));
					et_qt.setSelection(et_qt
							.getText().toString().trim()
							.length());
				}
			}
		});
		return view;
	}
	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_qt_sure:
			if (null!=et_qt.getText().toString() && !et_qt.getText().toString().equals("")) {
			ArrayList<Remarks> remList=new ArrayList<Remarks>();
			Remarks rem=new Remarks();
			rem.setNote(et_qt.getText().toString());
			rem.setTypt(Constant.KQ_QT);
			rem.setRecordtime("");
			remList.add(rem); 
			Intent intent = new Intent(Constant.REMARKS);
			intent.putExtra(Constant.REMARKS, remList);
			intent.putExtra("position", position);
			getActivity().sendBroadcast(intent);
			getActivity().finish();
			}else {
				Toast.makeText(getActivity(), "内容不能为空", 1).show();
			}
			break;
		case R.id.bt_qt_deselect:
			getActivity().finish();
			break;
		default:
			break;
		}
		
	}

}
