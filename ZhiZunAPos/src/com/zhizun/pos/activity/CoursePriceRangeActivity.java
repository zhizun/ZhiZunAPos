package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.base.BaseActivity;
/**
 * 输入搜索价格区间页面
 * @author lilinzhong
 *
 * 2015-6-23下午5:17:01
 */
public class CoursePriceRangeActivity extends BaseActivity implements OnClickListener {
	private Button bt_price;
	private EditText et_price_one,et_price_two;
	private TitleBarView titleBarView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_price_range);
		initView();
	}
	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_course);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.title_text_course_jiage);
		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		et_price_one=(EditText) findViewById(R.id.et_price_one);
		et_price_two=(EditText) findViewById(R.id.et_price_two);
		bt_price=(Button) findViewById(R.id.bt_price);
		bt_price.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_price:
			String priceValue=et_price_one.getText().toString();
			String priceTwoValue=et_price_two.getText().toString();
			if(!priceValue.equals("")
					&& !priceTwoValue.equals("")
					&&Integer.parseInt(priceValue)>=Integer.parseInt(priceTwoValue)){
				Toast.makeText(this, "最低价格不能高于最高价格", Toast.LENGTH_SHORT).show();
				return;
			}
			//关闭软键盘
			View view = getWindow().peekDecorView();
	        if (view != null) {
	            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
	        }
			
			Intent intent = new Intent();  
            intent.putExtra("priceLower", priceValue);  
            intent.putExtra("priceUpper", priceTwoValue);
            // 通过调用setResult方法返回结果给前一个activity。  
            setResult(1, intent);  
            //关闭当前activity  
            finish();
            
			break;
		default:
			break;
		}
	}

}
