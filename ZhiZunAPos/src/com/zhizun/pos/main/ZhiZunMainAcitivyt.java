package com.zhizun.pos.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.main.adapter.MainGridViewAdapter;

public class ZhiZunMainAcitivyt extends BaseActivity implements OnClickListener {
	
	private GridView gv_main_mu;
	private ImageView zhizun_main_saoma;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhizun_main);
		initView();
	}

	private void initView() {
		gv_main_mu=(GridView) findViewById(R.id.gv_main_mu);
		gv_main_mu.setAdapter(new MainGridViewAdapter(this, Constant.titles, Constant.imgs));
		gv_main_mu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent intent=new Intent(ZhiZunMainAcitivyt.this,SellersFragmentActivity.class);
					startActivity(intent);
					break;
//				case 1:
//					Intent intent1=new Intent(ZhiZunMainAcitivyt.this,ZhiZunSellprodsActivity.class);
//					startActivity(intent1);
//					break;
//				case 2:
//					Intent intent2=new Intent(ZhiZunMainAcitivyt.this,ZhiZunSellprodsActivity.class);
//					startActivity(intent2);
//					break;

				}
			}
		});
		zhizun_main_saoma=(ImageView) findViewById(R.id.zhizun_main_saoma);
		zhizun_main_saoma.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.zhizun_main_saoma:
			Intent intent=new Intent(this,CameraSaoMiaoActivity.class);
			startActivity(intent);
			break;

		}
		
	}

}
