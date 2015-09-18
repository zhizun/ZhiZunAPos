package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.AgentStatusProgress;
import com.zhizun.pos.bean.InterestPri;

/**
 * 我的代理历史记录 ListViewAdapter
 */
public class ListViewAgentHistoryAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<AgentStatusProgress> listItems; // 数据集合

	public ListViewAgentHistoryAdapter(Context context,
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
			convertView = View.inflate(context,
					R.layout.list_myagent_history_item, null);

			holder.tv_list_myagent_history_item_teachernamefirst = (TextView) convertView
					.findViewById(R.id.tv_list_myagent_history_item_teachernamefirst);
			holder.tv_list_myagent_history_item_teachernamesecond = (TextView) convertView
					.findViewById(R.id.tv_list_myagent_history_item_teachernamesecond);
			holder.tv_list_my_invitation_parent_item_endtime = (TextView) convertView
					.findViewById(R.id.tv_list_my_invitation_parent_item_endtime);
			holder.tv_list_myagent_history_item_status = (TextView) convertView
					.findViewById(R.id.tv_list_myagent_history_item_status);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AgentStatusProgress agentStatusProgress = listItems.get(position);

		if (AppContext.getApp().getUserLoginSharedPre().getUserInfo()
				.getUserId().equals(agentStatusProgress.getClientUserId())) {// 我是被代理人
			holder.tv_list_myagent_history_item_teachernamefirst
					.setText(agentStatusProgress.getAgentTeaName());
			holder.tv_list_myagent_history_item_teachernamefirst
					.setTextColor(context.getResources().getColorStateList(
							R.color.gray_font));
			holder.tv_list_myagent_history_item_teachernamesecond
					.setText("代理您");
		} else if (AppContext.getApp().getUserLoginSharedPre().getUserInfo()
				.getUserId().equals(agentStatusProgress.getAgentUserId())) {// 我是代理人
			holder.tv_list_myagent_history_item_teachernamesecond
					.setText(agentStatusProgress.getClientTeaName());
			holder.tv_list_myagent_history_item_teachernamefirst.setText("您代理");
			holder.tv_list_myagent_history_item_teachernamefirst
					.setTextColor(context.getResources().getColorStateList(
							R.color.gray_font));
		} else {
			holder.tv_list_myagent_history_item_teachernamesecond.setText("");
			holder.tv_list_myagent_history_item_teachernamefirst.setText("");
		}

		if (Constant.MYAGENT_AGENTING.equals(agentStatusProgress.getStatus())) {
			holder.tv_list_myagent_history_item_status.setText("进行中");
		} else if (Constant.MYAGENT_CANCEL.equals(agentStatusProgress
				.getStatus())) {
			holder.tv_list_myagent_history_item_status.setText("已取消");
		} else if (Constant.MYAGENT_END.equals(agentStatusProgress.getStatus())) {
			holder.tv_list_myagent_history_item_status.setText("已结束");
		} else {
			holder.tv_list_myagent_history_item_status.setText("");
		}

		if (Constant.MYAGENT_AGENTING.equals(agentStatusProgress.getStatus())) {// 进行中
			if (!TextUtils.isEmpty(agentStatusProgress.getEndTime())) {

				holder.tv_list_my_invitation_parent_item_endtime
						.setText(agentStatusProgress.getEndTime());
			} else {
				holder.tv_list_my_invitation_parent_item_endtime.setText("");
			}
		} else {
			if (!TextUtils.isEmpty(agentStatusProgress.getInvalidTime())) {

				holder.tv_list_my_invitation_parent_item_endtime
						.setText(agentStatusProgress.getInvalidTime());

			} else {
				holder.tv_list_my_invitation_parent_item_endtime.setText("");
			}
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_myagent_history_item_teachernamefirst;
		TextView tv_list_myagent_history_item_teachernamesecond;
		TextView tv_list_my_invitation_parent_item_endtime;
		TextView tv_list_myagent_history_item_status;
	}

}
