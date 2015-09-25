package com.zhizun.pos.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
/**
 * 
 * @author 李林中
 * 2015-9-25 上午9:30:46 
 * 开单页面
 * 
 *
 */
public class ZhiZunSellprodsActivity extends BaseActivity implements OnClickListener {
	private TitleBarView titleBarView;
	private CheckBox cb_is_preview;//票据预览
	private EditText et_data;//日期
	private EditText et_client;//客户
	private RelativeLayout rl_add_commodity;//添加商品
	private ImageView image_saoma;//扫描
	private CheckBox cb_is_lingshou;//销售类型
	private EditText et_zhekou;//折扣额
	private Button btn_login_submit;//收钱
	private TextView tv_shifujine;//实付金额
	private TextView tv_yingfujine;//应付金额
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_zhizun_selprods);
		initView();
		
		
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_zhizun_main_menu);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.VISIBLE);
		titleBarView.setTitleText(R.string.sellprods);
		titleBarView.getIvLeft().setImageResource(R.drawable.fanhui_zhizun);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.setBtnRight(R.drawable.fanhui_zhizun);
		titleBarView.setBtnRightOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		cb_is_preview=(CheckBox) findViewById(R.id.cb_is_preview);
		et_data=(EditText) findViewById(R.id.et_data);
		et_client=(EditText) findViewById(R.id.et_client);
		rl_add_commodity=(RelativeLayout) findViewById(R.id.rl_add_commodity);
		image_saoma=(ImageView) findViewById(R.id.image_saoma);
		cb_is_lingshou=(CheckBox) findViewById(R.id.cb_is_lingshou);
		et_zhekou=(EditText) findViewById(R.id.et_zhekou);
		btn_login_submit=(Button) findViewById(R.id.btn_login_submit);
		tv_shifujine=(TextView) findViewById(R.id.tv_shifujine);
		tv_yingfujine=(TextView) findViewById(R.id.tv_yingfujine);
		
		rl_add_commodity.setOnClickListener(this);
		image_saoma.setOnClickListener(this);
		btn_login_submit.setOnClickListener(this);
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_saoma://扫描
			
			break;

		case R.id.rl_add_commodity://添加商品
			Intent intent=new Intent(ZhiZunSellprodsActivity.this,AddCommodityActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btn_login_submit://收钱
			String dateString=et_data.getText().toString();
			String clientText=et_client.getText().toString();
			String zhekou=et_zhekou.getText().toString();
			if (cb_is_preview.isChecked()) {//判断是否票据预览
				Intent inten=new Intent(ZhiZunSellprodsActivity.this,BillPreviewActivity.class);
				startActivity(inten);
			}else {
				
			}
			break;
		}
	}
}
