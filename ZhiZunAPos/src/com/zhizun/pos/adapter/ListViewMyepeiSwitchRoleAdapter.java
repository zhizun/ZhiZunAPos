package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.StringUtils;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.UserInfo;
import com.zhizun.pos.bean.UserRole;

/**
 * 教师 我收到的邀请ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewMyepeiSwitchRoleAdapter extends MyBaseAdapter {

	public static final String TAG = "com.ch.epw.adapter.ListViewMyInvitationRecevieTeacherAdapter";
	private Context context;// 运行上下文
	private List<UserRole> listItems; // 数据集合

	public ListViewMyepeiSwitchRoleAdapter(Context context,
			List<UserRole> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.my_epei_switchrole_item, null);

			holder.iv_my_epei_switchrole_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_my_epei_switchrole_item_logo);

			holder.tv_my_epei_switchrole_item_orgname = (TextView) convertView
					.findViewById(R.id.tv_my_epei_switchrole_item_orgname);

			holder.tv_my_epei_switchrole_item_parentname = (TextView) convertView
					.findViewById(R.id.tv_my_epei_switchrole_item_parentname);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		UserRole userRole = listItems.get(position);
		showPicture(userRole.getImagePath(),
				holder.iv_my_epei_switchrole_item_logo, options);
		UserInfo userInfo = AppContext.getApp().getUserLoginSharedPre().getUserInfo();
		if( userRole.getRoleId().equals(userInfo.getCurrentRole())
		 &&	userInfo.getCurrentOrgan()!=null 
		 && userRole.getOrgId().equals(userInfo.getCurrentOrgan().getOrgId())
		){
			convertView.setBackgroundColor(Color.LTGRAY);
		}else{
			convertView.setBackgroundColor(Color.WHITE);
		}
		// 家长角色：角色名称（上） + 宝宝姓名（下）
		if (Constant.ORG_ROLE_TYPE_PARENT.equals(userRole.getRoleId())) {
			if (userRole.getRoleName() != null
					&& !userRole.getRoleName().equals("")) {
				String stuName = userRole.getRoleName().replace("家长", "");
				holder.tv_my_epei_switchrole_item_parentname.setText("宝宝家长");
				holder.tv_my_epei_switchrole_item_orgname.setText(stuName);
			}
		}
		// 其他角色：机构名称（上）+ 角色名称（下）
		else {
			String orgName = userRole.getOrgName() == null ? "" : userRole
					.getOrgName();
			String roleName = userRole.getRoleName() == null ? "" : userRole
					.getRoleName();
			holder.tv_my_epei_switchrole_item_parentname.setText(orgName);
			holder.tv_my_epei_switchrole_item_orgname.setText(roleName);
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_my_epei_switchrole_item_logo;// logo
		TextView tv_my_epei_switchrole_item_orgname;// 机构名字
		TextView tv_my_epei_switchrole_item_parentname;// 父母名字

	}

}
