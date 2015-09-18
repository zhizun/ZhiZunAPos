package com.ch.epw.jz.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
/**
 * 家长注册
 * 创建人：李林中
 * 创建日期：2014-12-15  上午10:39:28
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class RegistrationParentActivity extends BaseActivity {

	private TitleBarView titleBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_jiazhang);
		initView();
	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_registration_jiazhang);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.registration_jiazhang_text);
		titleBarView.setLeftText(R.string.title_text_cancel);
		titleBarView.getIvLeft().getLayoutParams().height=40;
		titleBarView.getIvLeft().getLayoutParams().width=40;
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doBack();
			}
		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
