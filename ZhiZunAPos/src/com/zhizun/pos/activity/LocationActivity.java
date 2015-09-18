package com.zhizun.pos.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.ch.epw.utils.BaseTools;
import com.ch.epw.utils.ButtonClickUtil;
import com.ch.epw.utils.Constant;
import com.ch.epw.utils.UIHelper;
import com.ch.epw.view.TitleBarView;
import com.zhizun.pos.R;
import com.zhizun.pos.app.AppContext;
import com.zhizun.pos.base.BaseActivity;
import com.zhizun.pos.bean.PersonInfo;

public class LocationActivity extends BaseActivity implements
		OnGetGeoCoderResultListener {
	protected static final String TAG = LocationActivity.class.getName();
	private TitleBarView titleBarView;
	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	Context context;
	// 定位相关
	LocationClient mLocClient;
	BDLocation locationAll;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	boolean isFirstLoc = true;// 是否首次定位
	Button btn_activity_overlay_submit, btn_activity_overlay_confirm;
	EditText et_activity_overlay_addr;
	TextView tv_activity_overlay_addr;
	ImageView iv_activity_overlay_location;
	TextView tvTextView;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.point_start);

	// 地址编码和反编码相关
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	PersonInfo personInfo;
	int mScreenWidth;// 屏幕宽度
	int distance = -80;// 弹出框距离大头针的图标的距离
	private InfoWindow mInfoWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overlay);
		context = this;
		tvTextView = new TextView(context);
		tvTextView.setBackgroundResource(R.drawable.location_tips);
		tvTextView.setTextColor(Color.WHITE);
		tvTextView.setTextSize(16);
		tvTextView.setText("长按图标移动标注地址");
		mScreenWidth = BaseTools.getWindowsWidth(this);
		if (mScreenWidth > 720 && mScreenWidth <= 1080) {
			distance = -120;
		} else if (mScreenWidth > 1080) {
			distance = -160;
		}
		personInfo = (PersonInfo) getIntent()
				.getSerializableExtra("personInfo");
		initView();
		// 定位相关
		mCurrentMode = LocationMode.NORMAL;

		// 地图相关
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mMapView.showZoomControls(false);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		initData();
	}

	private void initData() {
		if (personInfo != null && !personInfo.getLat().equals("")
				&& !personInfo.getLng().equals("")) {
			// et_activity_overlay_addr.setText(personInfo.getAddress());
			tv_activity_overlay_addr.setText(personInfo.getAddrInfo());
			locationAll = new BDLocation();
			locationAll.setLatitude(Float.parseFloat(personInfo.getLat()));
			locationAll.setLongitude(Float.parseFloat(personInfo.getLng()));
			LatLng ll = new LatLng(Float.parseFloat(personInfo.getLat()),
					Float.parseFloat(personInfo.getLng()));
			mInfoWindow = new InfoWindow(tvTextView, ll, distance);
			mBaiduMap.showInfoWindow(mInfoWindow);
			initOverlay();
		} else {
			mLocClient.start();
		}
		iv_activity_overlay_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearOverlay(null);
				mLocClient.start();
			}
		});
		btn_activity_overlay_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//防止按钮被多次点击
				if (ButtonClickUtil.isFastDoubleClick()) { 
					return;  
			    }
				Log.i(TAG, "personInfo.getCity()=" + personInfo.getCity());
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(LocationActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				if (et_activity_overlay_addr.getText().toString().trim()
						.equals("")) {
					UIHelper.ToastMessage(context, "地址不能为空", Toast.LENGTH_SHORT);
					return;
				}
				btn_activity_overlay_submit.setClickable(false);
				String cityString = personInfo.getCity();
				if (null == cityString || cityString.equals("")) {
					cityString = Constant.INITCITY;
				}
				mSearch.geocode(new GeoCodeOption().city(cityString).address(
						et_activity_overlay_addr.getText().toString().trim()));
			}
		});

	}

	private void initView() {
		titleBarView = (TitleBarView) this
				.findViewById(R.id.title_bar_activity_overlay);
		titleBarView.setCommonTitle(View.GONE, View.GONE, View.VISIBLE,
				View.VISIBLE, View.VISIBLE, View.GONE);

		titleBarView.setTitleText("地图标注");

		titleBarView.getIvLeft().setImageResource(R.drawable.arrow_left);
		titleBarView.setBarLeftOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doBack();
			}
		});
		titleBarView.setRightText(R.string.sure);
		titleBarView.setBarRightOnclickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(tv_activity_overlay_addr.getText()
						.toString().trim())) {
					UIHelper.ToastMessage(context, "地址不存在，请在地图上进行标注");
					return;
				}
				Intent intent = getIntent();
				intent.putExtra("personInfo", personInfo);
				LocationActivity.this.setResult(
						Constant.REQUSTCONDE_PERSONINFO_ADDRESS, intent);
				LocationActivity.this.finish();
				backAnim();
			}
		});
		et_activity_overlay_addr = (EditText) findViewById(R.id.et_activity_overlay_addr);

		btn_activity_overlay_submit = (Button) findViewById(R.id.btn_activity_overlay_submit);
		tv_activity_overlay_addr = (TextView) findViewById(R.id.tv_activity_overlay_addr);
		iv_activity_overlay_location = (ImageView) findViewById(R.id.iv_activity_overlay_location);

	}

	public void initOverlay() {
		btn_activity_overlay_submit.setClickable(true);
		// add marker overlay
		LatLng llA = new LatLng(locationAll.getLatitude(),
				locationAll.getLongitude());

		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
				.zIndex(9).draggable(true);
		mBaiduMap.addOverlay(ooA);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(llA));
		
		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			public void onMarkerDrag(Marker marker) {
				mBaiduMap.hideInfoWindow();
			}

			public void onMarkerDragEnd(Marker marker) {
				LatLng ptCenter = new LatLng(marker.getPosition().latitude,
						marker.getPosition().longitude);
				// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(ptCenter));
				LatLng ll = marker.getPosition();
				mInfoWindow = new InfoWindow(tvTextView, ll, distance);
				mBaiduMap.showInfoWindow(mInfoWindow);
				et_activity_overlay_addr.setText("");
				// Toast.makeText(
				// LocationActivity.this,
				// "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
				// + marker.getPosition().longitude,
				// Toast.LENGTH_LONG).show();
			}

			public void onMarkerDragStart(Marker marker) {
			}
		});
	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			isNetworkConnected();//检查网络连接
			if (!TextUtils.isEmpty(et_activity_overlay_addr.getText()
					.toString().trim())) {
				et_activity_overlay_addr.setText("");
			}
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;

			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			LatLng ptCenter = new LatLng(location.getLatitude(),
					location.getLongitude());
			// 反Geo搜索
			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(ptCenter));
			locationAll = location;
			initOverlay();
			Log.i(TAG, "location.getAddrStr()=" + location.getAddrStr());
			Log.i(TAG, "location.getCity()=" + location.getCity());
			Log.i(TAG, "location.getLatitude()=" + location.getLatitude());
			// tv_activity_overlay_addr.setText(location.getAddrStr());
			if (personInfo == null) {
				personInfo = new PersonInfo();
			}
			personInfo.setCity(location.getCity());
			personInfo.setProvince(location.getProvince());
			personInfo.setCounty(location.getDistrict());
			personInfo.setLat(location.getLatitude() + "");
			personInfo.setLng(location.getLongitude() + "");
			personInfo.setAddrInfo(location.getAddrStr());
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());

			mInfoWindow = new InfoWindow(tvTextView, ll, distance);
			mBaiduMap.showInfoWindow(mInfoWindow);
			mLocClient.stop();

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay();
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		// 退出时销毁定位
		mLocClient.stop();
		mSearch.destroy();
		super.onDestroy();
		// 回收 bitmap 资源
		bdA.recycle();

	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(LocationActivity.this, "地址不存在，请在地图上进行标注",
					Toast.LENGTH_LONG).show();
			tv_activity_overlay_addr.setText("");
			btn_activity_overlay_submit.setClickable(true);
			return;
		}
		locationAll = new BDLocation();
		locationAll.setLatitude(result.getLocation().latitude);
		locationAll.setLongitude(result.getLocation().longitude);
		mBaiduMap.clear();
		initOverlay();
		if (personInfo == null) {
			personInfo = new PersonInfo();
		}
		personInfo.setLat(result.getLocation().latitude + "");
		personInfo.setLng(result.getLocation().longitude + "");

		LatLng ll = result.getLocation();

		// 反Geo搜索
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));

		mInfoWindow = new InfoWindow(tvTextView, ll, distance);
		mBaiduMap.showInfoWindow(mInfoWindow);

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(LocationActivity.this, "地址不存在，请在地图上进行标注",
					Toast.LENGTH_LONG).show();
			tv_activity_overlay_addr.setText("");
			return;
		}

		if (personInfo == null) {
			personInfo = new PersonInfo();
		}
		personInfo.setCity(result.getAddressDetail().city);
		personInfo.setProvince(result.getAddressDetail().province);
		personInfo.setCounty(result.getAddressDetail().district);
		personInfo.setLat(result.getLocation().latitude + "");
		personInfo.setLng(result.getLocation().longitude + "");
		String searchString = et_activity_overlay_addr.getText().toString()
				.trim();
		if (TextUtils.isEmpty(searchString)) {
			personInfo.setAddr(result.getAddressDetail().street
					+ result.getAddressDetail().streetNumber);
			personInfo.setAddrInfo(result.getAddress());
			tv_activity_overlay_addr.setText(result.getAddress());
			Toast.makeText(LocationActivity.this, result.getAddress(),
					Toast.LENGTH_LONG).show();
		} else {

			personInfo.setAddr(searchString);
			personInfo.setAddrInfo(searchString);
			tv_activity_overlay_addr.setText(searchString);
			Toast.makeText(LocationActivity.this, searchString,
					Toast.LENGTH_LONG).show();
		}

	}

	private void isNetworkConnected() {
		if (!AppContext.getApp().isNetworkConnected()) {
			Toast.makeText(LocationActivity.this, "请检查您的网络",
					Toast.LENGTH_LONG).show();
			return;
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	
}