package com.zhizun.pos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

public class FriendsStartErrorActivityDialog extends BaseActivity implements OnClickListener {
	
	private Button bt_start;
	private Button bt_cancle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friends_start_error_dialog);
//		bt_start=(Button) findViewById(R.id.bt_start);
		bt_cancle=(Button) findViewById(R.id.bt_cancle);
//		bt_start.setOnClickListener(this);
		bt_cancle.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.bt_start:
//			Intent intent = new Intent();
//		    intent.putExtra("result", 1);
//		    setResult(1, intent);
//		    finish();
//			break;
		case R.id.bt_cancle:
			Intent inte = new Intent();
		    inte.putExtra("result", 2);
		    setResult(2, inte);
		    finish();
			break;

		}
		
	}
}
