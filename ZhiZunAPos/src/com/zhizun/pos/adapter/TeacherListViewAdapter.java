package com.zhizun.pos.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.ImageUtils;
import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.TeacherInfo;

public class TeacherListViewAdapter extends MyBaseAdapter {
	private Context context;// 运行上下文
	private List<TeacherInfo> listItems; // 数据集合
	Set<Integer> unfoldDescSet = new HashSet<Integer>();

	public TeacherListViewAdapter(Context context, List<TeacherInfo> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.list_org_intro_teacher_item, null);
			holder.iv_teacher_photo = (ImageView) convertView.findViewById(R.id.iv_teacher_photo);
			holder.tv_teacher_name = (TextView) convertView.findViewById(R.id.tv_teacher_name);
			holder.tv_teacher_desc = (TextView) convertView.findViewById(R.id.tv_teacher_desc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TeacherInfo teacherInfo = listItems.get(position);
		ImageUtils.showImageCourse(holder.iv_teacher_photo, teacherInfo.getPhotoPath());
		//showPicture(teacherInfo.getPhotoPath(), holder.iv_teacher_photo, options);
		
		if(teacherInfo.getName() != null){
			if (teacherInfo.getName().contains("老师")
					|| teacherInfo.getName().contains("校长")) {
				holder.tv_teacher_name.setText(teacherInfo.getName());
			}else {
				holder.tv_teacher_name.setText(teacherInfo.getName() + "老师");
			}
		}else{
			holder.tv_teacher_name.setText("");
		}
		
		if (unfoldDescSet.contains(position)) {
			holder.tv_teacher_desc.setMaxLines(1000);
		} else {
			holder.tv_teacher_desc.setMaxLines(2);
		}

		if(teacherInfo.getTeachDesc() != null){
			holder.tv_teacher_desc.setText(teacherInfo.getTeachDesc());
			
		}else{
			holder.tv_teacher_desc.setText("");
		}

		holder.tv_teacher_desc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				unfoldDescSet.add(position);
				TeacherListViewAdapter.this.notifyDataSetChanged();
			}
		});

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_teacher_photo;
		TextView tv_teacher_name;
		TextView tv_teacher_desc;
	}

}
