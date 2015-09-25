package com.zhizun.pos.main;

import com.zhizun.pos.R;
import com.zhizun.pos.activity.MenuPopWindow;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.main.listview.MyAdapter;
import com.zhizun.pos.main.listview.MyListView;
import com.zhizun.pos.main.listview.SubAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SellersFragmentActivity extends BaseActivity implements
		OnClickListener {

	private MyListView listView;
	private MyListView subListView;
	private MyAdapter myAdapter;
	private SubAdapter subAdapter;
	private ImageView image_fanhui;
	private TextView title_type;
	private Button bt_Billing;
	private Button collect_money;
	private ImageView im_xinjianshangpin;
	private ImageView im_saomiao;

	String cities[][] = new String[][] {
			new String[] { "全部美食", "本帮江浙菜", "川菜", "粤菜", "湘菜", "东北菜", "台湾菜",
					"新疆/清真", "素菜", "火锅", "自助餐", "小吃快餐", "日本", "韩国料理", "东南亚菜",
					"西餐", "面包甜点", "其他" },
			new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺",
					"公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆",
					"桌面游戏", "更多休闲娱乐" },
			new String[] { "全部购物", "综合商场", "服饰鞋包", "运动户外", "珠宝饰品", "化妆品",
					"数码家电", "亲子购物", "家居建材", "书店", "书店", "眼镜店", "特色集市",
					"更多购物场所", "食品茶酒", "超市/便利店", "药店" },
			new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺",
					"公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆",
					"桌面游戏", "更多休闲娱乐" },
			new String[] { "全", "咖啡厅", "酒吧", "茶馆", "KTV", "游乐游艺", "公园",
					"景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏",
					"更多休闲娱乐" }, };
	String foods[] = new String[] { "进货排行", "默认产品分类", "文具用品", "生活用品", "服饰" ,};
	private LinearLayout ll_menu_xiala;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.activity_zhizun_sellers_listview);
		init();
		myAdapter = new MyAdapter(getApplicationContext(), foods);
		listView.setAdapter(myAdapter);

		selectDefult();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				final int location = position;
				listView.setSelector(new ColorDrawable(Color.WHITE));
				myAdapter.setSelectedPosition(position);
				myAdapter.notifyDataSetInvalidated();
				subAdapter = new SubAdapter(getApplicationContext(), cities,
						position);
				subListView.setAdapter(subAdapter);
				subListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								cities[location][position], Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		});

	}

	private void init() {
		listView = (MyListView) findViewById(R.id.listView);
		subListView = (MyListView) findViewById(R.id.subListView);
		image_fanhui = (ImageView) findViewById(R.id.image_fanhui);
		image_fanhui.setOnClickListener(this);
		ll_menu_xiala = (LinearLayout) findViewById(R.id.ll_menu_xiala);
		ll_menu_xiala.setOnClickListener(this);
		title_type = (TextView) findViewById(R.id.title_type);
		bt_Billing=(Button) findViewById(R.id.bt_Billing);
		bt_Billing.setOnClickListener(this);
		collect_money=(Button) findViewById(R.id.collect_money);
		collect_money.setOnClickListener(this);
		im_xinjianshangpin=(ImageView) findViewById(R.id.im_xinjianshangpin);
		im_xinjianshangpin.setOnClickListener(this);
		im_saomiao=(ImageView) findViewById(R.id.im_saomiao);
		im_saomiao.setOnClickListener(this);
	}

	private void selectDefult() {
		final int location = 0;
		myAdapter.setSelectedPosition(0);
		myAdapter.notifyDataSetInvalidated();
		subAdapter = new SubAdapter(getApplicationContext(), cities, 0);
		subListView.setAdapter(subAdapter);
		subListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						cities[location][position], Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.image_fanhui://后退
			finish();
			break;
		case R.id.ll_menu_xiala://标题栏零售和批发切换
			showPopupWindow(ll_menu_xiala);
			break;
		case R.id.bt_Billing://开单
			Intent intent=new Intent(this,ZhiZunSellprodsActivity.class);
			startActivity(intent);
			break;
		case R.id.collect_money://收钱
			Intent inten=new Intent(this,BillPreviewActivity.class);
			startActivity(inten);
			break;
		case R.id.im_xinjianshangpin://新建商品
			Intent intents=new Intent(this,AddCommodityActivity.class);
			startActivity(intents);
			break;
		case R.id.im_saomiao://扫描
			Intent intentsaom=new Intent(this,CameraSaoMiaoActivity.class);
			startActivity(intentsaom);
			break;
		}
	}

	private void showPopupWindow(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.list_popwindow_item, null);
		// 设置点击事件
		TextView tv_list_popwindow_edit = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_edit);
		TextView tv_list_popwindow_delete = (TextView) contentView
				.findViewById(R.id.tv_list_popwindow_delete);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		tv_list_popwindow_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "批发");
				title_type.setText("批发");
				intoAnim();
				popupWindow.dismiss();
			}
		});
		tv_list_popwindow_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "零售");
				title_type.setText("零售");
				intoAnim();
				popupWindow.dismiss();
			}
		});
		// 设置好参数之后再show
		popupWindow.showAsDropDown(view, -55, 15);

	}

}
