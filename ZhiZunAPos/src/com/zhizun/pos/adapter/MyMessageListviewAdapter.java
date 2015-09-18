package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.utils.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.MySelfMessageBean;
/**
 * 我的消息列表适配器
 * @author lilinzhong
 *
 * 2015-7-21上午11:06:05
 */
public class MyMessageListviewAdapter extends BaseAdapter {
	
	private Context context;
	private List<MySelfMessageBean> listItems;
	private DisplayImageOptions options;

	public MyMessageListviewAdapter(Context context,
			List<MySelfMessageBean> listItems) {
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
			convertView = View.inflate(context, R.layout.my_message_listview_item,
					null);
			holder.rl_my_system_message_layout=(RelativeLayout) convertView.findViewById(R.id.rl_my_system_message_layout);
			holder.my_system_message_content = (TextView) convertView
					.findViewById(R.id.my_system_message_content);
			holder.my_system_message_time = (TextView) convertView
					.findViewById(R.id.my_system_message_time);
			
			holder.rl_my_message_layout=(RelativeLayout) convertView.findViewById(R.id.rl_my_message_layout);
			holder.my_message_content=(TextView) convertView.findViewById(R.id.my_message_content);
			holder.my_message_time=(TextView) convertView.findViewById(R.id.my_message_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MySelfMessageBean mySelfMessageBean = listItems.get(position);
		
			holder.my_message_content.setText("我："+mySelfMessageBean.getContent());
			holder.my_message_time.setText(mySelfMessageBean.getSendTime());
		if (mySelfMessageBean.getSysReplyContent()!=null && !mySelfMessageBean.getSysReplyContent().equals("")) {
				holder.rl_my_system_message_layout.setVisibility(View.VISIBLE);
				holder.my_system_message_content.setText("益培网："+mySelfMessageBean.getSysReplyContent());
				holder.my_system_message_time.setText(mySelfMessageBean.getSysReplyTime());
		}else {
				holder.rl_my_system_message_layout.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class ViewHolder {
		RelativeLayout rl_my_system_message_layout;
		TextView my_system_message_content;
		TextView my_system_message_time;
		
		RelativeLayout rl_my_message_layout;
		TextView my_message_content;
		TextView my_message_time;
	}

}
