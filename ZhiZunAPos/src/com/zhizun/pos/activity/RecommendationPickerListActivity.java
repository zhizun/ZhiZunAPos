package com.zhizun.pos.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ListViewRecommendPrizedListAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.MyRecommendationPicker;
/**
 * 推荐有奖	具体领取列表明细
 * @author
 *
 */
public class RecommendationPickerListActivity extends BaseActivity {
	private Button bt_picker;
	private TextView picker_time,picker,isDeal;
	private ListView picker_listview;
	private ListViewRecommendPrizedListAdapter listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recommendation_pickerlist);
		WindowManager m = getWindowManager();    
	    Display d = m.getDefaultDisplay();  //为获取屏幕宽、高    
	    LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值    
	    p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.5   
	    p.width = (int) (d.getWidth() * 0.95);    //宽度设置为屏幕的0.9
		ArrayList<MyRecommendationPicker> pickerList=(ArrayList<MyRecommendationPicker>) getIntent().getSerializableExtra("pickerList");
	
		picker_time=(TextView) findViewById(R.id.picker_time);
		picker=(TextView) findViewById(R.id.picker);
		isDeal=(TextView) findViewById(R.id.isdeal);
		picker_listview=(ListView) findViewById(R.id.picker_listview);
		
		bt_picker=(Button) findViewById(R.id.bt_picker);
		bt_picker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RecommendationPickerListActivity.this.finish();
			}
		});
		listview=new ListViewRecommendPrizedListAdapter(RecommendationPickerListActivity.this, pickerList);
		picker_listview.setAdapter(listview);
	}

}
