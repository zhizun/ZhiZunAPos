package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.CourseSpinerConditionAdapter.ConditionItemSelectListener;

public class CourseSpinerConditionPopWindow extends PopupWindow implements OnItemClickListener, OnClickListener{

	private Context mContext;
	private ListView mListView;
	private CoureSearchFilterSpinerAdapter mAdapter;
	private ConditionItemSelectListener mItemSelectListener;
	private LinearLayout ll_confirm_bar;
	private Button bt_spiner_course;		//弹窗确定按钮
	private Button bt_spiner_course_reset;	//弹窗重置按钮
	
	
	public CourseSpinerConditionPopWindow(Context context)
	{
		super(context);
		
		mContext = context;
		init();
	}
	
	
	public void setItemListener(ConditionItemSelectListener listener){
		mItemSelectListener = listener;
	}

	
	private void init()
	{
		View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
		setContentView(view);		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
	
		
		mListView = (ListView) view.findViewById(R.id.listview);
		
		ll_confirm_bar=(LinearLayout) view.findViewById(R.id.ll_confirm_bar);
		ll_confirm_bar.setVisibility(View.VISIBLE);
		bt_spiner_course=(Button) view.findViewById(R.id.bt_spiner_course);
		bt_spiner_course_reset=(Button) view.findViewById(R.id.bt_spiner_course_reset);

		mAdapter = new CoureSearchFilterSpinerAdapter(mContext);	
		mListView.setAdapter(mAdapter);	
		mListView.setOnItemClickListener(this);
		bt_spiner_course.setOnClickListener(this);
		bt_spiner_course_reset.setOnClickListener(this);
	}
	
	
	public void refreshData(List<String> list, int selIndex)
	{
		if (list != null && selIndex  != -1)
		{
			mAdapter.refreshData(list, selIndex);
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null){
			mItemSelectListener.onConditionItemClick(pos);
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Constant.COURSE);
		String extraData = null;
		switch(v.getId()){
		case R.id.bt_spiner_course:
			extraData = "OK";
			break;
		case R.id.bt_spiner_course_reset:
			extraData = "RESET";
			break;
		}
		intent.putExtra(Constant.COURSE, extraData);
		mContext.sendBroadcast(intent);
	}


	
}
