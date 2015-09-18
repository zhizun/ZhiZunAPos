package com.zhizun.pos.activity;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ch.epw.task.StatCourseCounselNumTask;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.FriendsCallPhoneDialogAdapter;
import com.zhizun.pos.base.BaseActivity;

public class FriendsCallPhoneDialogActivity extends BaseActivity {
	private ListView ll_call_phone;
	List<String> phoneList;
	private String courId;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_friends_call_phone_dialog);
			phoneList=getIntent().getStringArrayListExtra("phoneList");
			courId=getIntent().getStringExtra("courId");
			ll_call_phone=(ListView) findViewById(R.id.ll_call_phone);
			ll_call_phone.setAdapter(new FriendsCallPhoneDialogAdapter(this,phoneList));
			ll_call_phone.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					if (courId != null&&!courId.equals("")) {
						new StatCourseCounselNumTask(FriendsCallPhoneDialogActivity.this, null).execute(courId);
					}
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
							+ phoneList.get(position)));
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});
		}
}
