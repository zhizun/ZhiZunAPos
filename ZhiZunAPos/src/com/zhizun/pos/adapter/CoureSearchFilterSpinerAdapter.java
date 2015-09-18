package com.zhizun.pos.adapter;

import com.zhizun.pos.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CoureSearchFilterSpinerAdapter extends CourseSpinerAdapter<String>{

	public  CoureSearchFilterSpinerAdapter(Context context){
		super(context);
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		 ViewHolder viewHolder;
    	 
	     if (convertView == null) {
	    	 convertView = mInflater.inflate(R.layout.course_filter_spiner_item, null);
	         viewHolder = new ViewHolder();
	         viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
	         convertView.setTag(viewHolder);
	     } else {
	         viewHolder = (ViewHolder) convertView.getTag();
	     }

	     
	     String item = (String) getItem(pos);
		 viewHolder.mTextView.setText(item);

	     return convertView;
	}
}
