package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.AgentStatusProgress;
import com.zhizun.pos.bean.InterestPri;

/**
 * 我的代理 ListViewAdapter
 */
public class ListViewAgentAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<AgentStatusProgress> listItems; // 数据集合

	public ListViewAgentAdapter(Context context,
			List<AgentStatusProgress> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;

	}

	public int getCount() {
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
			convertView = View.inflate(context, R.layout.list_myagent_item,
					null);

			holder.tv_myagent_content = (TextView) convertView
					.findViewById(R.id.tv_myagent_content);
			holder.tv_myagent_endtime = (TextView) convertView
					.findViewById(R.id.tv_myagent_endtime);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AgentStatusProgress agentStatusProgress = listItems.get(position);

		if (!TextUtils.isEmpty(agentStatusProgress.getAgentTeaName())) {
			if (null != agentStatusProgress.getOmClassList()
					&& agentStatusProgress.getOmClassList().size() > 0) {

				holder.tv_myagent_content.setText("您目前正在代理 "
						+ agentStatusProgress.getClientTeaName() + "老师 的班级事务管理："
						+ agentStatusProgress.getOmClass());
			} else {
				holder.tv_myagent_content.setText("您目前正在代理 "
						+ agentStatusProgress.getClientTeaName() + "老师 的班级事务管理");
			}

		} else {
			holder.tv_myagent_content.setText("");
		}

		if (!TextUtils.isEmpty(agentStatusProgress.getEndTime())) {

			holder.tv_myagent_endtime.setText(agentStatusProgress.getEndTime());

		} else {
			holder.tv_myagent_endtime.setText("");
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_myagent_content;
		TextView tv_myagent_endtime;
	}

}
