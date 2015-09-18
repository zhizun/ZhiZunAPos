package com.zhizun.pos.activity;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.epw.utils.ImageUtils;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ImagePagerAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.view.imageshow.ImageShowViewPager;

/*
 * 图片展示
 */
public class ImageShowActivity extends BaseActivity {
	/** 图片展示 */
	private ImageShowViewPager image_pager;
	private TextView page_number;
	/** 图片下载按钮 */
	private ImageView download;
	/** 图片列表 */
	private ArrayList<String> imgsUrl;
	/** PagerAdapter */
	private ImagePagerAdapter mAdapter;
	/** 当前点击图片的索引 */
	private int imgPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_imageshow);
		initView();
		initData();
		initViewPager();
	}

	private void initData() {
		imgsUrl = getIntent().getStringArrayListExtra("infos");
		imgPosition = getIntent().getIntExtra("imgPosition", 0);
		page_number.setText((imgPosition + 1) + "/" + imgsUrl.size());
	}

	private void initView() {
		image_pager = (ImageShowViewPager) findViewById(R.id.image_pager);
		page_number = (TextView) findViewById(R.id.page_number);
		download = (ImageView) findViewById(R.id.download);
		image_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				imgPosition=arg0;
				page_number.setText((arg0 + 1) + "/" + imgsUrl.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		download.setVisibility(View.VISIBLE);
		download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String stare=ImageUtils.saveImageToGallery(ImageShowActivity.this, imageLoader.loadImageSync(imgsUrl.get(imgPosition)));
				if (!stare.equals("")) {
				Toast.makeText(ImageShowActivity.this, "保存路径"+Environment.getExternalStorageDirectory()+stare, Toast.LENGTH_SHORT).show();	
				}else {
				Toast.makeText(ImageShowActivity.this, "保存失败", Toast.LENGTH_SHORT).show();	
				}
			}
		});

	}
	private void initViewPager() {
		if (imgsUrl != null && imgsUrl.size() != 0) {
			mAdapter = new ImagePagerAdapter(ImageShowActivity.this, imgsUrl,
					ImageShowActivity.this);
			image_pager.setAdapter(mAdapter);
			image_pager.setCurrentItem(imgPosition);
		}
	}
}
