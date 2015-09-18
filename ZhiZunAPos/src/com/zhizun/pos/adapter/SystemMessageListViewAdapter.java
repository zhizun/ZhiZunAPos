package com.zhizun.pos.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ch.epw.utils.HtmlRegexpUtils;
import com.ch.epw.utils.Options;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.MySystemMessageBean;

public class SystemMessageListViewAdapter extends MyBaseAdapter {
	private Context context;// 运行上下文
	private List<MySystemMessageBean> listItems; // 数据集合

	public SystemMessageListViewAdapter(Context context,
			List<MySystemMessageBean> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
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
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.system_massage_listview_itme,
					null);
			holder.tv_message_content = (TextView) convertView
					.findViewById(R.id.tv_message_content);
			holder.tv_message_name = (TextView) convertView
					.findViewById(R.id.tv_message_name);
			holder.tv_badgeview=(TextView) convertView
					.findViewById(R.id.tv_badgeview);
			holder.tv_message_time=(TextView) convertView.findViewById(R.id.tv_message_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final MySystemMessageBean mySystemMessageBean = listItems.get(position);

		holder.tv_message_content.setText(mySystemMessageBean.getTitle());
		if (mySystemMessageBean.getTitle()!=null&&!mySystemMessageBean.getTitle().equals("")) {
//			holder.tv_message_name.setText(courseListItemList.getCourseOrgBean().getOrgName());
			holder.tv_message_name.setText(HtmlRegexpUtils.filterHtml(mySystemMessageBean.getContent()));
		}else {
			holder.tv_message_name.setVisibility(View.GONE);
		}
		if (mySystemMessageBean.getIsRead()!=null && !mySystemMessageBean.getIsRead().equals("")) {
			BadgeView badgeView = new BadgeView(context);  
			badgeView.setTargetView(holder.tv_badgeview);  
			if (mySystemMessageBean.getIsRead().equals("0")) {
				badgeView.setBackground(5, Color.parseColor("#007AFE"));  
			}else {
				badgeView.setBackground(5, Color.WHITE);  
			}
			badgeView.setText("");
			/*if (mySystemMessageBean.getIsRead().equals("0")) {
				holder.tv_badgeview.setVisibility(View.VISIBLE);
				BadgeView badgeView = new BadgeView(context);  
				badgeView.setTargetView(holder.tv_badgeview);  
				badgeView.setBackground(5, Color.parseColor("#007AFE"));  
				badgeView.setText("");
			}else {
				holder.tv_badgeview.setVisibility(View.GONE);
			}*/
		}
		if (mySystemMessageBean.getSendTime()!=null&&!mySystemMessageBean.getSendTime().equals("")) {
			holder.tv_message_time.setText(mySystemMessageBean.getSendTime());
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tv_message_content;
		TextView tv_message_name;
		TextView tv_badgeview;
		TextView tv_message_time;
	}
}
