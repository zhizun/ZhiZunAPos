package com.ch.epw.js.activity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.DateUtil;
import com.ch.epw.view.DateTimePickDialogUtil;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.AttenceDetail;
import com.zhizun.pos.bean.Remarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AttenceRemarksFragmentLate extends Fragment implements OnClickListener {
	private ArrayList<AttenceDetail> remarksList;
	private Button bt1_1;
	private Button bt1_2;
	private TextView textView;
	private int position;
	private String textDate;
	AttenceDetail getRemarks;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		System.out.println("MainTab2 onCreate");
		remarksList=(ArrayList<AttenceDetail>) getArguments().getSerializable("remarks");
		position=getArguments().getInt("position");
		textDate = DateUtil.getCurrentTime();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.attenceremarks_tab_late, null);
		bt1_1=(Button) view.findViewById(R.id.bt1_1);
		bt1_2=(Button) view.findViewById(R.id.bt1_2);
		textView=(TextView) view.findViewById(R.id.tv_list_attence_js_item_kqsj_count);
		textView.setOnClickListener(this);
		bt1_1.setOnClickListener(this);
		bt1_2.setOnClickListener(this);
		getRemarks=remarksList.get(position);
		if (getRemarks.getRemarks().size()>0 && getRemarks.getRemarks().get(0).getRecordtime()!=null && !getRemarks.getRemarks().get(0).getRecordtime().equals("")) {
			textView.setText(getRemarks.getRemarks().get(0).getRecordtime());
		}else {
			textView.setText("");
		}
		
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt1_1:
			String timeString=textView.getText().toString();
			if (!timeString.equals("")) {
				boolean tage=compareTime(timeString,textDate);
				if (tage) {
					ArrayList<Remarks> remarks=new ArrayList<Remarks>();
					Remarks rems=new Remarks();
					rems.setTypt(Constant.KQ_CD);
					rems.setNote("");
					rems.setRecordtime(timeString);
					remarks.add(rems);
					Intent intent = new Intent(Constant.REMARKS);
					intent.putExtra(Constant.REMARKS, remarks);
					intent.putExtra("position", position);
					getActivity().sendBroadcast(intent);
					getActivity().finish();
				}else {
					Toast.makeText(getActivity(), "时间大于今天", Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.bt1_2:
			getActivity().finish();
			break;
		case R.id.tv_list_attence_js_item_kqsj_count:
			dateTimeDialog();
			break;

		default:
			break;
		}
	}
	private boolean compareTime(String begin,String end){
		boolean flag=false;
		if(Long.valueOf(begin.replaceAll("[-\\s:]","")) < Long.valueOf(end.replaceAll("[-\\s:]", ""))){
			flag=true;
			}
		return flag;
	}
	private void dateTimeDialog() {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				getActivity(), textView
						.getText().toString().trim());
		dateTimePicKDialog.dateTimePicKDialog(textView,
				Constant.DATETIME_PICK_YYYYMMDD, Constant.DATETIME_PICK_HHMMSS);
	}

}
