package com.ch.epw.js.activity;


import java.util.ArrayList;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Remarks;

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

public class AttenceRemarksFragment_Qj extends Fragment implements OnClickListener {
	private ArrayList<AttenceDetail> remarksList;
	private Button bt_qj_sure;
	private Button bt_qj_deselect;
	private EditText et_qj;
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
		View view = inflater.inflate(R.layout.attenceremarks_tab_qj, null);
		et_qj=(EditText) view.findViewById(R.id.et_qj);
		bt_qj_sure=(Button) view.findViewById(R.id.bt_qj_sure);
		bt_qj_deselect=(Button) view.findViewById(R.id.bt_qj_deselect);
		bt_qj_sure.setOnClickListener(this);
		bt_qj_deselect.setOnClickListener(this);
		getRemarks=remarksList.get(position);
		if (getRemarks.getRemarks().size()>0) {
			if (getRemarks.getRemarks().get(0).getTypt().equals(Constant.KQ_QJ)) {
				et_qj.setText(getRemarks.getRemarks().get(0).getNote());
			}
		}
		et_qj.addTextChangedListener(new TextWatcher() {


			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				Rest_Length = et_qj.getText().toString().trim().length();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (Rest_Length > MAX_LENGTH) {
					UIHelper.ToastMessage(getActivity(), "最多只能输入"+MAX_LENGTH+"个字");
					et_qj.setText(et_qj
							.getText().toString().trim()
							.substring(0, MAX_LENGTH));
					et_qj.setSelection(et_qj
							.getText().toString().trim()
							.length());
				}

			}
		});
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_qj_sure:
			if (null!=et_qj.getText().toString() && !et_qj.getText().toString().equals("")) {
			ArrayList<Remarks> remList=new ArrayList<Remarks>();
			Remarks rem=new Remarks();
			rem.setNote(et_qj.getText().toString());
			rem.setTypt(Constant.KQ_QJ);
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
		case R.id.bt_qj_deselect:
			getActivity().finish();
			break;

		default:
			break;
		}
		
	}

}
