package com.ch.epw.js.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ch.epw.task.GetUnReadRecvNumTask;
import com.ch.epw.task.TaskCallBack;
import com.ch.epw.utils.Constant;
import com.ch.epw.view.TitleBarView;
import com.jauker.widget.BadgeView;
import com.zhizun.pos.AppManager;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.base.BaseBean;
import com.zhizun.pos.bean.UnReadRecvNum;
import com.zhizun.pos.bean.UnReadRecvNumList;

/**
 * 教师端 导航页 创建人：李林中 创建日期：2014-12-15 上午10:10:27 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */
public class NavigationTeacherActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView titleBarView;
	Context context;
	LinearLayout rl_activity_navigation_teacher_zxdt;
	LinearLayout rl_activity_navigation_teacher_tzgg;
	LinearLayout rl_activity_navigation_teacher_jtzy;
	LinearLayout rl_activity_navigation_teacher_kqjl;
	LinearLayout rl_activity_navigation_teacher_zxdp;
	LinearLayout rl_activity_navigation_teacher_jzxs;
	ImageView iv_zxdt_js;
	ImageView iv_tzgg_js;
	ImageView iv_jtzy_js;
	ImageView iv_kqjl_js;
	ImageView iv_zxdp_js;
	ImageView iv_jzxs_js;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_teacher);
		context = this;
		initView();
		// 刷新页面提醒信息，包括栏目未读数量和邀请数量显示
		refreshAllTips();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 刷新页面提醒信息，包括栏目未读数量和邀请数量显示
		refreshAllTips();
	}

	private void initUnreadTips(UnReadRecvNumList unReadRecvNumList) {
		List<UnReadRecvNum> unReadItemList = unReadRecvNumList
				.getUnReadRecvNumList();
		if (unReadItemList != null) {
			for (UnReadRecvNum uNum : unReadItemList) {
				BadgeView bv = null;
				switch (Integer.parseInt(uNum.getType())) {
				case Constant.FSI_TYPE_ZXDT:
					bv = getBadgeViewByTag(iv_zxdt_js);
					break;
				case Constant.FSI_TYPE_TZGG:
					bv = getBadgeViewByTag(iv_tzgg_js);
					break;
				case Constant.FSI_TYPE_JTZY:
					bv = getBadgeViewByTag(iv_jtzy_js);
					break;
				case Constant.FSI_TYPE_KQJL:
					bv = getBadgeViewByTag(iv_kqjl_js);
					break;
				case Constant.FSI_TYPE_ZXDP:
					bv = getBadgeViewByTag(iv_zxdp_js);
					break;
				case Constant.FSI_TYPE_JZXS:
					bv = getBadgeViewByTag(iv_jzxs_js);
					break;
				default:
					break;
				}

				if (bv != null && uNum.getCount() != null
						&& !"".equals(uNum.getCount())) {
					bv.setBadgeCount(Integer.parseInt(uNum.getCount()));
				}
			}
		}
	}

	private BadgeView getBadgeViewByTag(ImageView v) {
		BadgeView bv = (BadgeView) v.getTag();
		if (bv == null) {
			bv = new BadgeView(context);
			bv.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
			v.setTag(bv);
			bv.setTargetView(v);
		}
		return bv;
	}

	/**
	 * 刷新页面提醒信息，包括栏目未读数量和邀请数量显示
	 */
	private void refreshAllTips() {
		// 刷新未读数量提示
		new GetUnReadRecvNumTask(context, new TaskCallBack() {
			@Override
			public void onTaskFinshed(BaseBean resultList) {
				initUnreadTips((UnReadRecvNumList) resultList);
			}
		}).execute(AppContext.getApp().getToken());
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_navigation);

		// 标题栏
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.GONE,
				View.VISIBLE, View.GONE, View.GONE);
//		titleBarView.setBtnRight(R.drawable.myx2);
		titleBarView.setTitleText(R.string.title_text_jxhd);

		// 跳转到我的益培
//		titleBarView.setBarRightOnclickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (inviteMsgView != null && inviteMsgView.getBadgeCount() > 0) {
//					inviteMsgView.setBadgeCount(0);
//				}
//				Intent intent = new Intent(context, MyepeiParentActivity.class);
//				startActivity(intent);
//				intoAnim();
//			}
//		});
		rl_activity_navigation_teacher_zxdt = (LinearLayout) this
				.findViewById(R.id.rl_activity_navigation_teacher_zxdt);
		rl_activity_navigation_teacher_zxdt.setOnClickListener(this);
		rl_activity_navigation_teacher_tzgg = (LinearLayout) findViewById(R.id.rl_activity_navigation_teacher_tzgg);
		rl_activity_navigation_teacher_tzgg.setOnClickListener(this);
		rl_activity_navigation_teacher_jtzy = (LinearLayout) findViewById(R.id.rl_activity_navigation_teacher_jtzy);
		rl_activity_navigation_teacher_jtzy.setOnClickListener(this);
		rl_activity_navigation_teacher_kqjl = (LinearLayout) findViewById(R.id.rl_activity_navigation_teacher_kqjl);
		rl_activity_navigation_teacher_kqjl.setOnClickListener(this);
		rl_activity_navigation_teacher_zxdp = (LinearLayout) findViewById(R.id.rl_activity_navigation_teacher_zxdp);
		rl_activity_navigation_teacher_zxdp.setOnClickListener(this);
		rl_activity_navigation_teacher_jzxs = (LinearLayout) findViewById(R.id.rl_activity_navigation_teacher_jzxs);
		rl_activity_navigation_teacher_jzxs.setOnClickListener(this);
		iv_zxdt_js = (ImageView) findViewById(R.id.iv_zxdt_js);
		iv_tzgg_js = (ImageView) findViewById(R.id.iv_tzgg_js);
		iv_kqjl_js = (ImageView) findViewById(R.id.iv_kqjl_js);
		iv_jtzy_js = (ImageView) findViewById(R.id.iv_jtzy_js);
		iv_zxdp_js = (ImageView) findViewById(R.id.iv_zxdp_js);
		iv_jzxs_js = (ImageView) findViewById(R.id.iv_jzxs_js);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_activity_navigation_teacher_zxdt:
			if (iv_zxdt_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_zxdt_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_zxdt = new Intent(context,
					InSchoolDynamicTeacherActivity.class);
			startActivity(intent_zxdt);
			intoAnim();
			break;
		case R.id.rl_activity_navigation_teacher_tzgg:
			if (iv_tzgg_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_tzgg_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_tzgg = new Intent(context,
					MyNoticeTeacherActivity.class);
			startActivity(intent_tzgg);
			intoAnim();
			break;
		case R.id.rl_activity_navigation_teacher_jtzy:
			if (iv_jtzy_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_jtzy_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_jtzy = new Intent(context,
					MyHomeworkTeacherActivity.class);
			startActivity(intent_jtzy);
			intoAnim();
			break;
		case R.id.rl_activity_navigation_teacher_kqjl:
			if (iv_kqjl_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_kqjl_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_kqjl = new Intent(context,
					AttenceTeacherActivity.class);
			startActivity(intent_kqjl);
			intoAnim();
			break;
		case R.id.rl_activity_navigation_teacher_zxdp:
			if (iv_zxdp_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_zxdp_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_zxdp = new Intent(context,
					InSchoolCommentTeacherActivity.class);
			startActivity(intent_zxdp);
			intoAnim();
			break;
		case R.id.rl_activity_navigation_teacher_jzxs:
			if (iv_jzxs_js.getTag() != null) {
				BadgeView bv = (BadgeView) iv_jzxs_js.getTag();
				bv.setBadgeCount(0);
			}
			Intent intent_jzxs = new Intent(context, VoiceTeacherActivity.class);
			startActivity(intent_jzxs);
			intoAnim();
			break;
		default:
			break;
		}
	}

	private long mExitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				AppManager.getAppManager().AppExit(context);
			}

			return true;
		}
		// 拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
