package com.zhizun.pos.adapter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.ch.epw.bean.send.CommentsSend;
import com.ch.epw.js.activity.MyInvitationRquestTeacherActivity;
import com.ch.epw.jz.activity.MyInvitationParentActivity;
import com.ch.epw.task.AcceptInviteTask;
import com.ch.epw.task.GetUserChildsTask;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.SpUtil;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.utils.URLs;
import com.ch.epw.view.NoScrollListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhizun.pos.AppException;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Comments;
import com.zhizun.pos.bean.CommentsReply;
import com.zhizun.pos.bean.DynamicTeacher;
import com.zhizun.pos.bean.ReceiveMyInvitationList;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.Result;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 教师 我收到的邀请ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewMyInvitationRecevieTeacherAdapter extends MyBaseAdapter {

	public static final String TAG = "com.ch.epw.adapter.ListViewMyInvitationRecevieTeacherAdapter";
	private Context context;// 运行上下文
	private List<RecevieMyInvitation> listItems; // 数据集合
	Result result;
	TextView tView;
	Button btnButton;

	// private Callback mCallback;

	// /**
	// * 自定义接口，用于回调按钮点击事件到Activity
	// *
	// * @author Ivan Xu 2014-11-26
	// */
	// public interface Callback {
	// public void click(View v);
	// }

	public ListViewMyInvitationRecevieTeacherAdapter(Context context,
			List<RecevieMyInvitation> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
	}

	// public ListViewDynamicTeacherAdapter(Context context,
	// List<DynamicTeacher> listItems, Callback callback) {
	// super();
	// this.context = context;
	// this.listItems = listItems;
	// mCallback = callback;
	// }

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
					R.layout.list_my_invitation_receive_item, null);
			holder.iv_list_my_invitation_recevie_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_my_invitation_recevie_item_logo);
			holder.tv_list_my_invitation_parent_item_invitename = (TextView) convertView
					.findViewById(R.id.tv_list_my_invitation_parent_item_invitename);
			holder.btn_list_my_invitation_parent_item_invitestatus_recevie = (Button) convertView
					.findViewById(R.id.btn_list_my_invitation_parent_item_invitestatus_recevie);
			holder.tv_list_my_invitation_parent_item_inviteorgname = (TextView) convertView
					.findViewById(R.id.tv_list_my_invitation_parent_item_inviteorgname);
			holder.tv_list_my_invitation_parent_item_invitestatus = (TextView) convertView
					.findViewById(R.id.tv_list_my_invitation_parent_item_invitestatus);
			holder.tv_list_my_invitation_parent_item_time = (TextView) convertView
					.findViewById(R.id.tv_list_my_invitation_parent_item_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.resetContentAndListener();
		}

		final RecevieMyInvitation recevieMyInvitation = listItems.get(position);

		showPicture(recevieMyInvitation.getInviteUserPhoto(),
				holder.iv_list_my_invitation_recevie_item_logo, options);

		if (null != recevieMyInvitation.getInviteUserName()
				&& !recevieMyInvitation.getInviteUserName().equals("")) {
			holder.tv_list_my_invitation_parent_item_invitename
					.setText(recevieMyInvitation.getInviteUserName());
		}else {
			holder.tv_list_my_invitation_parent_item_invitename
			.setText("");
		}
		if (null != recevieMyInvitation.getInviteOrgName()
				&& !recevieMyInvitation.getInviteOrgName().equals("")) {
			holder.tv_list_my_invitation_parent_item_inviteorgname
					.setText(recevieMyInvitation.getInviteOrgName());
		}else {
			holder.tv_list_my_invitation_parent_item_inviteorgname
			.setText("");
		}
		if (null != recevieMyInvitation.getInviteTime()
				&& !recevieMyInvitation.getInviteTime().equals("")) {
			String str1 = recevieMyInvitation.getInviteTime().trim().split(" ")[0];
			String str2 = str1.split("年")[1];
			holder.tv_list_my_invitation_parent_item_time.setText(str2);
		}else {
			holder.tv_list_my_invitation_parent_item_time.setText("");
		}
		if (recevieMyInvitation.getInviteStatus() != null
				&& !recevieMyInvitation.getInviteStatus().equals("")) {
			if (recevieMyInvitation.getInviteStatus().equals(
					Constant.INVITESTATUS_UNTREATED)) {// 邀请状态 未处理
				holder.btn_list_my_invitation_parent_item_invitestatus_recevie
						.setVisibility(View.VISIBLE);
				holder.tv_list_my_invitation_parent_item_invitestatus
						.setVisibility(View.GONE);

			}
			if (recevieMyInvitation.getInviteStatus().equals(
					Constant.INVITESTATUS_ACCEPTED)) {// 邀请状态 已接受
				holder.btn_list_my_invitation_parent_item_invitestatus_recevie
						.setVisibility(View.GONE);
				holder.tv_list_my_invitation_parent_item_invitestatus
						.setVisibility(View.VISIBLE);
				holder.tv_list_my_invitation_parent_item_invitestatus
						.setText("已接受");
			}
			if (recevieMyInvitation.getInviteStatus().equals(
					Constant.INVITESTATUS_REFUSE)) {// 邀请状态 已拒绝
				holder.btn_list_my_invitation_parent_item_invitestatus_recevie
						.setVisibility(View.GONE);
				holder.tv_list_my_invitation_parent_item_invitestatus
						.setVisibility(View.VISIBLE);
				holder.tv_list_my_invitation_parent_item_invitestatus
						.setText("已拒绝");
			}
		}
		holder.btn_list_my_invitation_parent_item_invitestatus_recevie
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						tView = holder.tv_list_my_invitation_parent_item_invitestatus;
						btnButton = holder.btn_list_my_invitation_parent_item_invitestatus_recevie;
//						new AcceptInviteTask().execute(AppContext.getApp()
//								.getToken(), recevieMyInvitation
//								.getOrgInviteId(), "", "");
						//如果是邀请老师，直接调AcceptInviteTask
						if(Constant.INVITE_TYPE_TO_TEACHER.equals(recevieMyInvitation.getType())){
							new AcceptInviteTask(context).execute(AppContext.getApp().getToken(),
									recevieMyInvitation.getOrgInviteId(), "", "");
						}
						//邀请家长，调用GetUserChildsTask
						else if(Constant.INVITE_TYPE_TO_PARENT.equals(recevieMyInvitation.getType())){
							new GetUserChildsTask( context, recevieMyInvitation, false)
							.execute(AppContext.getApp().getToken());
						}
					}
				});

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_my_invitation_recevie_item_logo;// logo
		TextView tv_list_my_invitation_parent_item_invitename;// 邀请人姓名
		Button btn_list_my_invitation_parent_item_invitestatus_recevie;// 接受按钮
		TextView tv_list_my_invitation_parent_item_inviteorgname;// 邀请机构名称
		TextView tv_list_my_invitation_parent_item_invitestatus;// 接受状态
		TextView tv_list_my_invitation_parent_item_time;// 时间
		
		protected void resetContentAndListener(){
			if(iv_list_my_invitation_recevie_item_logo!=null){
				iv_list_my_invitation_recevie_item_logo.setImageResource(R.drawable.default_photo);
			}
			if(tv_list_my_invitation_parent_item_invitename!=null){
				tv_list_my_invitation_parent_item_invitename.setText("");
			}
			if(tv_list_my_invitation_parent_item_inviteorgname!=null){
				tv_list_my_invitation_parent_item_inviteorgname.setText("");
			}
			if(tv_list_my_invitation_parent_item_invitestatus!=null){
				tv_list_my_invitation_parent_item_invitestatus.setText("");
			}
			if(tv_list_my_invitation_parent_item_time!=null){
				tv_list_my_invitation_parent_item_time.setText("");
			}
		}
	}

//	/**
//	 * 接受邀请 创建人：李林中 创建日期：2014-12-17 下午7:40:16 作用： 修改
//	 * =================================================== 修改人 修改日期 原因(描述)
//	 * ===================================================
//	 */
//	class AcceptInviteTask extends AsyncTask<String, Void, Result> {
//
//		@Override
//		protected Result doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				result = AppContext.getApp().recevieInvite(params[0],
//						params[1], params[2], params[3]);
//			} catch (AppException e) {
//				e.printStackTrace();
//				result = null;
//			}
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(Result result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//
//			if (null != result) {
//				if (result.getStatus().equals("0")) {
//					UIHelper.ToastMessage(context, "谢谢您，您已接成功受邀请");
//					btnButton.setVisibility(View.GONE);
//					tView.setVisibility(View.VISIBLE);
//					tView.setText("已接受");
//				} else {
//					UIHelper.ToastMessage(context, result.getStatusMessage());
//					return;
//				}
//			} else {
//				//UIHelper.ToastMessage(context, R.string.socket_exception_error);
//				return;
//			}
//		}
//	}
	// //响应按钮点击事件,调用子定义接口，并传入View
	// @Override
	// public void onClick(View v) {
	// mCallback.click(v);
	// }

}
