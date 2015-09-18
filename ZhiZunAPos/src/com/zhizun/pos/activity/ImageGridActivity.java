package com.zhizun.pos.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ImageGridAdapter;
import com.zhizun.pos.adapter.ImageGridAdapter.TextCallback;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.view.photograph.AlbumHelper;
import com.zhizun.pos.view.photograph.ImageItem;

public class ImageGridActivity extends BaseActivity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataList;
	List<String> drrList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;
	ImageView title_iv_left;
	int maxUploadCount = Constant.UPLOAD_PHOTO_COUNT;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);
		initView();
		bt = (Button) findViewById(R.id.bt);
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		title_iv_left.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doBack();
				finish(); // 2015-02-04 Renzhenwen

			}
		});
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}
				for (int i = 0; i < list.size(); i++) {
					if (BaseTools.bimp.getDrr().size() < maxUploadCount) {
						BaseTools.bimp.getDrr().add(list.get(i));
					}
				}
				finish();
			}

		});
		// 对获取到的相册图片列表进行排序，根据ID排序
		Collections.sort(dataList, new Comparator<ImageItem>() {
			@Override
			public int compare(ImageItem arg0, ImageItem arg1) {
				if (Integer.parseInt(arg0.getImageId()) > Integer.parseInt(arg1.getImageId())) {
					return -1;
				}else {
					return 1;
				}
			}

        });
	}

	// //2015-02-04 Renzhenwen
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // 按两下退出程序
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// Bimp.drr.clear();
	// Bimp.max = 0;
	// //super.onDestroy();
	// finish();
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// maxUploadCount参数传递路径: UploadImageHelper（GridViewUploadImagesAdapter） -> AlbumShowActivity -> ImageGridActivity（ImageGridAdapter）
		maxUploadCount = getIntent().getIntExtra("maxUploadCount",
				Constant.UPLOAD_PHOTO_COUNT);
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Toast.makeText(ImageGridActivity.this,
							"最多可以上传" + maxUploadCount + "张图片",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};

		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		adapter.setMaxUploadCount(maxUploadCount);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				bt.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }

				adapter.notifyDataSetChanged();
			}

		});

	}
}
