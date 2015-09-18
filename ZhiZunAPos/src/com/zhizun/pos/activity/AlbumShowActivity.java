package com.zhizun.pos.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.R;
import com.zhizun.pos.adapter.ImageBucketAdapter;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.view.photograph.AlbumHelper;
import com.zhizun.pos.view.photograph.ImageBucket;

public class AlbumShowActivity extends BaseActivity {
	// ArrayList<Entity> dataList;//用来装载数据源的列表
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	ImageView title_iv_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// /**
		// * 这里，我们假设已经从网络或者本地解析好了数据，所以直接在这里模拟了10个实体类，直接装进列表中
		// */
		// dataList = new ArrayList<Entity>();
		// for(int i=-0;i<10;i++){
		// Entity entity = new Entity(R.drawable.picture, false);
		// dataList.add(entity);
		// }
		dataList = helper.getImagesBucketList(false);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		title_iv_left = (ImageView) findViewById(R.id.title_iv_left);
		title_iv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
				AlbumShowActivity.this.finish();
			}
		});

		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(AlbumShowActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(AlbumShowActivity.this,
						ImageGridActivity.class);
				// maxUploadCount参数传递路径: UploadImageHelper（GridViewUploadImagesAdapter） -> AlbumShowActivity -> ImageGridActivity（ImageGridAdapter）
				int maxUploadCount = getIntent().getIntExtra("maxUploadCount",
						Constant.UPLOAD_PHOTO_COUNT);
				intent.putExtra("maxUploadCount", maxUploadCount);
				intent.putExtra(AlbumShowActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivity(intent);
				finish();
			}

		});
	}
}
