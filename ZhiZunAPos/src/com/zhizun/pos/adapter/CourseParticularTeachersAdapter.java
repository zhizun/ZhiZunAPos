package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.CourseTeacherListBean;

public class CourseParticularTeachersAdapter extends MyBaseAdapter {
	private Context context;
	private List<CourseTeacherListBean> listItems;
	
	public CourseParticularTeachersAdapter(Context context,
			List<CourseTeacherListBean> list) {
		super();
		this.context = context;
		this.listItems = list;
		options = Options.getListOptions();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.activity_course_particular_listview,
					null);
			holder.im_teacher = (ImageView) convertView
					.findViewById(R.id.im_teacher);
			holder.tv_teacher_name = (TextView) convertView
					.findViewById(R.id.tv_teacher_name);
			holder.tv_teacher_content = (TextView) convertView
					.findViewById(R.id.tv_teacher_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CourseTeacherListBean CourseTeacher=listItems.get(position);
		String imgUrlString = "";
		if (null != CourseTeacher.getPhotoPath()&& !CourseTeacher.getPhotoPath().equals("static/theme/images/default_photo.png")) {
			imgUrlString=CourseTeacher.getPhotoPath();
		}else {
			holder.im_teacher.setImageDrawable(context.getResources().getDrawable(R.drawable.default_photo));
		}
			showPicture(imgUrlString, holder.im_teacher, options);
			holder.tv_teacher_name.setText(CourseTeacher.getName());
			holder.tv_teacher_content.setText(CourseTeacher.getIntroduction());
		return convertView;
	}
	static class ViewHolder{
		ImageView im_teacher;
		TextView tv_teacher_name;
		TextView tv_teacher_content;
	}

}
