package com.zhizun.pos.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
/**
 * 
 * @author 李林中
 * 2015-9-25 上午9:57:46 
 * 
 * 添加商品
 *
 */
public class AddCommodityActivity extends BaseActivity {
	private TitleBarView titleBarView;
	private String baer;
	private EditText et_commodity_bar_code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_commodity);
		baer=getIntent().getStringExtra("Code");
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zhizun_commodity);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.add_commodity);
		titleBarView.getIvLeft().setImageResource(R.drawable.fanhui_zhizun);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_commodity_bar_code=(EditText) findViewById(R.id.et_commodity_bar_code);
		if (baer!=null && !baer.equals("")) {
			et_commodity_bar_code.setText(baer);
		}
		
	}
}
