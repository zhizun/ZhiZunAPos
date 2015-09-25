package com.zhizun.pos.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
/**
 * 
 * @author 李林中
 * 2015-9-23 上午10:22:12 
 *
 *	票据预览界面
 */
public class BillPreviewActivity extends BaseActivity {
	private TitleBarView titleBarView;
	private TextView tv_bill_num;//订单号
	private TextView text_date;//时间
	private TextView text_drr;//地址
	private TextView text_phone;//电话
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_bill_preview);
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zhizun_preview);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.zhizun_preview);
		titleBarView.getIvLeft().setImageResource(R.drawable.fanhui_zhizun);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		tv_bill_num=(TextView) findViewById(R.id.tv_bill_num);
		text_date=(TextView) findViewById(R.id.text_date);
		text_drr=(TextView) findViewById(R.id.text_drr);
		text_phone=(TextView) findViewById(R.id.text_phone);
	}
}





