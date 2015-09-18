package com.zhizun.pos.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;

public class FriendsStartUsingActivityDialog extends BaseActivity implements OnClickListener {
	
	private Button bt_start;
	private Button bt_cancle;
	private SharedPreferences mySharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friends_start_using_dialog);
		bt_start=(Button) findViewById(R.id.bt_start);
		bt_cancle=(Button) findViewById(R.id.bt_cancle);
		bt_start.setOnClickListener(this);
		bt_cancle.setOnClickListener(this);
		mySharedPreferences = getSharedPreferences("UserSelect",
				LocationActivity.MODE_PRIVATE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_start:
			Intent intent = new Intent();
		    intent.putExtra("result", 1);
		    setResult(1, intent);
		    finish();
			break;
		case R.id.bt_cancle:
			Intent inte = new Intent();
		    inte.putExtra("result", 2);
		    setResult(2, inte);
		    finish();
			break;

		}
		
	}
}
