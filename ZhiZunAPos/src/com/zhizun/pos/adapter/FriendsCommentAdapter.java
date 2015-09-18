package com.zhizun.pos.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.ch.epw.task.DeleteCourseCommentTask;
import com.ch.epw.task.FriendsHelpfulTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.NoScrollGridView;
import com.ch.epw.view.NoScrollListView;
import com.zhizun.pos.R;
import com.zhizun.pos.activity.CourseDetailActivity;
import com.zhizun.pos.activity.FriendsInviteActivity;
import com.zhizun.pos.activity.FriendsMyCommentActivity;
import com.zhizun.pos.activity.ImageShowActivity;
import com.zhizun.pos.activity.MyCourseCommentEditActivity;
import com.zhizun.pos.activity.OrgIntroDetailActivity;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.FriendsCommentListBean;
import com.zhizun.pos.bean.FriendsHelpfulBase;
import com.zhizun.pos.bean.Photo;
import com.zhizun.pos.bean.Result;
import com.zhizun.pos.bean.UserLogin;
import com.zhizun.pos.widget.actionsheet.ActionSheet;

/**
 * 评价列表适配器
 * 
 * @author lilinzhong
 * 
 *         2015-8-24下午5:43:00
 */
public class FriendsCommentAdapter extends MyBaseAdapter {
	private Context context;
	private ArrayList<FriendsCommentListBean> listItems;
	private Set<Integer> addRating = new HashSet<Integer>();
	long lastClick ;
	/**
	 * activityTag 枚举值含义
	 * 
	 * 0 : 朋友圈评价;
	 * 1 : 我的评价;
	 * 2 : 附近评价;
	 * 3 : 课程评价（从课程详情页面进入）。
	 */
	private String activityTag;

	public FriendsCommentAdapter(Context context,
			ArrayList<FriendsCommentListBean> listItems, String activityTag) {
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
		this.activityTag = activityTag;
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
					R.layout.activity_friends_listview_item, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tv_comDate = (TextView) convertView
					.findViewById(R.id.tv_comDate);
			holder.tv_org_name = (TextView) convertView
					.findViewById(R.id.tv_org_name);
			holder.tv_distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			holder.tv_org_class_name = (TextView) convertView
					.findViewById(R.id.tv_org_class_name);
			holder.ll_markAvg = (NoScrollListView) convertView
					.findViewById(R.id.ll_markAvg);
			holder.friends_list_phone_item_imagelist = (NoScrollGridView) convertView
					.findViewById(R.id.friends_list_phone_item_imagelist);
			holder.tv_help = (TextView) convertView.findViewById(R.id.tv_help);
			holder.bt_thank = (Button) convertView.findViewById(R.id.bt_thank);
			holder.bt_share = (Button) convertView.findViewById(R.id.bt_share);
			holder.bt_edit = (Button) convertView.findViewById(R.id.bt_edit);
			holder.bt_delete = (Button) convertView.findViewById(R.id.bt_delete);

			holder.rl_my_comment_edit = (RelativeLayout) convertView
					.findViewById(R.id.rl_my_comment_edit);
			holder.rl_frie_felp = (RelativeLayout) convertView
					.findViewById(R.id.rl_frie_felp);
			holder.tv_help_name = (TextView) convertView
					.findViewById(R.id.tv_help_name);

			holder.tv_help_course = (TextView) convertView
					.findViewById(R.id.tv_help_course);

			// holder.im_markAvg = (ImageView) convertView
			// .findViewById(R.id.im_markAvg);
			holder.im_drr_image = (ImageView) convertView
					.findViewById(R.id.im_drr_image);

			holder.ll_org = (RelativeLayout) convertView
					.findViewById(R.id.ll_org);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final FriendsCommentListBean friendsCommentListBean = listItems
				.get(position);
		holder.tv_content.setText(friendsCommentListBean.getContent());
		holder.tv_comDate.setText(friendsCommentListBean.getComDate());
		// holder.tv_org_name.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
		// holder.tv_org_name.setText(friendsCommentListBean.getOrgName());
		if (friendsCommentListBean.getOrgId()!=null && !friendsCommentListBean.getOrgId().equals("")) {
			holder.tv_org_name.setText(Html.fromHtml("<u>"
					+ friendsCommentListBean.getOrgName() + "</u>"));
			holder.tv_org_name.setTextColor(context.getResources().getColor(R.color.huodong_text));
			holder.tv_org_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,OrgIntroDetailActivity.class);
					intent.putExtra("orgId", friendsCommentListBean.getOrgId());
					intent.putExtra("category", "1");//
					context.startActivity(intent);
					
				}
			});
		}else {
			holder.tv_org_name.setText(friendsCommentListBean.getOrgName());
			holder.tv_org_name.setTextColor(context.getResources().getColor(R.color.gray_font));
		}
		if (friendsCommentListBean.getCourId()!=null && !friendsCommentListBean.getCourId().equals("")) {
			holder.tv_org_class_name.setText(Html.fromHtml("<u>"
					+ friendsCommentListBean.getCourName() + "</u>"));
			holder.tv_org_class_name.setTextColor(context.getResources().getColor(R.color.huodong_text));
			holder.tv_org_class_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,CourseDetailActivity.class);
					intent.putExtra("courId", friendsCommentListBean.getCourId());
					intent.putExtra("ownOrgId", friendsCommentListBean.getOrgId());
					context.startActivity(intent);
					
				}
			});
		}else {
			holder.tv_org_class_name.setText(friendsCommentListBean.getCourName());
			holder.tv_org_class_name.setTextColor(context.getResources().getColor(R.color.gray_font));
		}
		// 距离
		String distStrDesc = "";
		if (null != friendsCommentListBean.getDistance()
				&& !friendsCommentListBean.getDistance().equals("")) {
			holder.im_drr_image.setVisibility(View.VISIBLE);
			try {
				BigDecimal disance = new BigDecimal(
						friendsCommentListBean.getDistance());
				if (disance.compareTo(BigDecimal.ONE) < 0) {
					distStrDesc = disance.movePointLeft(-3).intValue() + "m";
				} else {
					distStrDesc = disance.setScale(1, BigDecimal.ROUND_CEILING)
							+ "km";
				}
			} catch (NumberFormatException e) {
				distStrDesc = "";
				holder.im_drr_image.setVisibility(View.GONE);
			}
		} else {
			distStrDesc = "";
			holder.im_drr_image.setVisibility(View.GONE);
		}
		holder.tv_distance.setText(distStrDesc);
		if (friendsCommentListBean.getHelpfulName() != null) {
			holder.tv_help_name.setVisibility(View.VISIBLE);
			holder.rl_frie_felp.setVisibility(View.GONE);
			if (!friendsCommentListBean.getHelpfulNum().equals("0")) {
				holder.tv_help_name.setText(friendsCommentListBean
						.getHelpfulName()
						+ "等"
						+ friendsCommentListBean.getHelpfulNum() + "人认为有帮助");
			}
		} else {
			holder.tv_help_name.setVisibility(View.GONE);
			if (!activityTag.equals("2")) {
				holder.rl_frie_felp.setVisibility(View.VISIBLE);
				holder.tv_help_course.setVisibility(View.GONE);
				if (!friendsCommentListBean.getHelpfulNum().equals("0")) {
					holder.tv_help.setText("对"
							+ friendsCommentListBean.getHelpfulNum()
							+ "人找培训班有帮助");
				} else {
					holder.tv_help.setText("");
				}
				holder.bt_thank.setVisibility(View.VISIBLE);
				if (friendsCommentListBean.getIsHelpful().equals("0")) {
					holder.bt_thank.setText("感谢");
					friendsCommentListBean.setCurrenUserLike(true);
				} else if (friendsCommentListBean.getIsHelpful().equals("1")) {
					holder.bt_thank.setText("已感谢");
					friendsCommentListBean.setCurrenUserLike(false);
				}
			} else {
				holder.rl_frie_felp.setVisibility(View.GONE);
				holder.tv_help_course.setVisibility(View.VISIBLE);
				holder.tv_help_course.setText("对"
						+ friendsCommentListBean.getHelpfulNum() + "人找培训班有帮助");
			}
		}
		holder.bt_thank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (System.currentTimeMillis() - lastClick <= 1500)  
			    {  
			        return;  
			    } 
				lastClick = System.currentTimeMillis();  
				new FriendsHelpfulTask(context, new TaskCallBack() {// 感谢接口
							@Override
							public void onTaskFinshed(BaseBean result) {
								FriendsHelpfulBase friendsHelpfulBase = (FriendsHelpfulBase) result;
								if (friendsHelpfulBase != null) {
									
									String status = friendsHelpfulBase
											.getStatus();
									if (status.equals("0")) {
										int helpfuNum = Integer
												.valueOf(friendsCommentListBean
														.getHelpfulNum());
										if (friendsCommentListBean
												.getIsHelpful().equals("0")) {
											friendsCommentListBean.setHelpfulNum(String
													.valueOf(helpfuNum + 1));
										} else {
												friendsCommentListBean.setHelpfulNum(String
														.valueOf(helpfuNum - 1));
										}
										friendsCommentListBean
												.setIsHelpful(friendsHelpfulBase
														.getNewFlag());
										FriendsCommentAdapter.this
												.notifyDataSetChanged();
									}
								}
							}

							@Override
							public void onTaskFailed() {
								// closeProgressDialog();
							}

						}).execute(friendsCommentListBean.getCommentId(),
						friendsCommentListBean.getCurrenUserLike() ? "1" : "0");
			}
		});

		String loginUserId = null;
		UserLogin userLogin = AppContext.getApp().getUserLoginSharedPre();
		if (userLogin != null && userLogin.getUserInfo() != null) {
			loginUserId = userLogin.getUserInfo().getUserId();
		}

		if (activityTag.equals("1")
				|| friendsCommentListBean.getUserId().equals(loginUserId)) {
			holder.tv_name.setText("我");// 我的评价
		} else {
			// 评价列表显示名字，按优先级，getShowUserName--getUserShortName--getUserPhone
			// if (activityTag.equals("0")||activityTag.equals("2")) {
			if (friendsCommentListBean.getIsAnonymous().equals("0")) {// getIsAnonymous()
				if (friendsCommentListBean.getShowUserName() != null
						&& !friendsCommentListBean.getShowUserName().equals("")) {
					holder.tv_name.setText(friendsCommentListBean
							.getShowUserName());// 朋友圈显示名称
				} else if (friendsCommentListBean.getUserShortName() != null
						&& !friendsCommentListBean.getUserShortName()
								.equals("")) {
					holder.tv_name.setText(friendsCommentListBean
							.getUserShortName());
				} else {
					holder.tv_name.setText(friendsCommentListBean
							.getUserPhone());// 附近显示手机号
				}
			} else {
				holder.tv_name.setText("匿名用户");// 佚名
			}
		}

		// 如果用户未登陆， 或显示我的评价列表，或登陆用户就是发表评论用户， 隐藏 感谢 按钮
		if (!AppContext.getApp().isLogin() || activityTag.equals("1")
				|| friendsCommentListBean.getUserId().equals(loginUserId)) {
			holder.bt_thank.setVisibility(View.GONE);
		} else {
			holder.bt_thank.setVisibility(View.VISIBLE);
		}

		// 我的评价列表，显示分享按钮
		if (activityTag.equals("1")) {
			holder.rl_my_comment_edit.setVisibility(View.VISIBLE);

			// 删除
			holder.bt_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ActionSheet.OnActionSheetSelected OnActionSheetSelectedListener = new ActionSheet.OnActionSheetSelected() {
						@Override
						public void onClick(View view) {
							String commentId = friendsCommentListBean.getCommentId();
							new DeleteCourseCommentTask(context, new TaskCallBack() {
								@Override
								public void onTaskFinshed(BaseBean resultBean) {
									Result result = (Result) resultBean;
									if ("0".equals(result.getStatus())) {
										// 删除后如果只删除listItem元素，刷新adapter，会导致刷新问题。这里直接刷新页面
										if(context.getClass().isAssignableFrom(FriendsMyCommentActivity.class)){
											((FriendsMyCommentActivity)context).reloadContent();
										}
										
									} else {
										UIHelper.ToastMessage(context, "删除评价失败");
									}
								}
							}).execute(commentId);
						}
					};

					ActionSheet.showSheet(context,"","",OnActionSheetSelectedListener, null);
				}
			});

			// 编辑
			holder.bt_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							MyCourseCommentEditActivity.class);
					intent.putExtra("commentBean", friendsCommentListBean);
					((Activity) context)
							.startActivityForResult(
									intent,
									FriendsMyCommentActivity.REQUEST_START_COMMENT_EDIT);
				}

			});

			// 分享
			holder.bt_share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context, FriendsInviteActivity.class);
					intent.putExtra("course",
							friendsCommentListBean.getCourName());// 课程名
					intent.putExtra("org", friendsCommentListBean.getOrgName());// 机构名
					intent.putExtra("commentId", friendsCommentListBean.getCommentId());
					intent.putExtra("logoPath", friendsCommentListBean.getLogoPath());
					context.startActivity(intent);
				}
			});
		}

		// 课程评价列表，隐藏机构名称、课程名称
		if (activityTag.equals("3")) {
			holder.ll_org.setVisibility(View.GONE);
			holder.tv_org_class_name.setVisibility(View.GONE);

			// 不显示课程名称的时候，将评价分数改为靠左对齐
			LayoutParams lp = (LayoutParams) holder.ll_markAvg
					.getLayoutParams();
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		} else {
			holder.ll_org.setVisibility(View.VISIBLE);
			holder.tv_org_class_name.setVisibility(View.VISIBLE);
		}

		if (friendsCommentListBean.getRatingList() != null
				&& friendsCommentListBean.getRatingList().size() > 0) {// 显示星星
			List starList = friendsCommentListBean.getRatingList()
					.subList(0, 1);

			ListViewRatingAdapter ratingListAdapter = new ListViewRatingAdapter(
					context, R.layout.course_comment_star_item, starList);
			holder.ll_markAvg.setAdapter(ratingListAdapter);
		}
		if (friendsCommentListBean.getPicList() != null
				&& friendsCommentListBean.getPicList().size() > 0) {
			FriendsGridViewImagesAdapter listViewImagesAdapter = new FriendsGridViewImagesAdapter(
					context, friendsCommentListBean.getPicList());
			holder.friends_list_phone_item_imagelist
					.setVisibility(View.VISIBLE);
			holder.friends_list_phone_item_imagelist
					.setAdapter(listViewImagesAdapter);
		} else {
			holder.friends_list_phone_item_imagelist.setVisibility(View.GONE);
		}

		holder.friends_list_phone_item_imagelist
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ArrayList<String> imgsUrl = new ArrayList<String>();
						for (Photo photo : friendsCommentListBean.getPicList()) {
							imgsUrl.add(photo.getThumbPath() + "/"
									+ photo.getThumbSaveName());
						}
						Intent intent = new Intent();
						intent.putStringArrayListExtra("infos", imgsUrl);
						intent.putExtra("imgPosition", position);
						intent.setClass(context, ImageShowActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				});

		return convertView;
	}

	static class ViewHolder {
		TextView tv_name;// 朋友评论名字
		TextView tv_comDate;// 评论时间
		TextView tv_org_name;// 机构名字
		TextView tv_org_class_name;// 班名
		TextView tv_distance;// 距离
		NoScrollListView ll_markAvg;// 综合
		TextView tv_content;// 内容
		NoScrollGridView friends_list_phone_item_imagelist;// 图片
		TextView tv_help;// 对多少人有帮助
		Button bt_thank;// 感谢
		Button bt_share;// 分享
		Button bt_edit;// 编辑
		Button bt_delete;// 删除

		RelativeLayout rl_my_comment_edit;// 我的评价编辑和分享
		RelativeLayout rl_frie_felp;// 感谢和对多少人帮助
		TextView tv_help_name;// 我的评价中，所有帮助过的人名

		TextView tv_help_course;// 课程中显示的帮助人数

		// ImageView im_markAvg;// 折叠星星按钮
		ImageView im_drr_image;// 距离图标

		RelativeLayout ll_org;
	}
}
