package com.ch.epw.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ch.epw.task.TaskCallBack;
import com.zhizun.pos.app.AppContext;

/**
 * 使用百度地图SDK获取位置Utils *
 * 
 * 直接调用getLocation(TaskCallBack callfun)，传入回调对象callfun，再定位成功后调用回调函数中传入的方法
 * 使用过程中需要根据实际的应用场景自行选择, 或者从getLocation()获取最近一次定位信息
 * 
 * @author lyc
 *
 */
public class LocationUtils {

	private static BDLocation lastKnownLocation = null;

	private static class BDLocationListenerImpl implements BDLocationListener {
		TaskCallBack afterLocationCallBack;
		LocationClient locClient;

		BDLocationListenerImpl(final LocationClient locClient,
				final TaskCallBack callBack) {
			this.afterLocationCallBack = callBack;
			this.locClient = locClient;
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 定位结束，如果客户端定位前未开启，则停止定位
			if (locClient.isStarted()) {
				locClient.stop();
			}

			if (location == null) {
				if (afterLocationCallBack != null) {
					afterLocationCallBack.onTaskFailed();
				}
				return;
			}

			// 每调用一次设置下最近的定位结果是否可用
			if (location.getLocType() == BDLocation.TypeNetWorkLocation
					|| location.getLocType() == BDLocation.TypeGpsLocation) {

				lastKnownLocation = location;
			} else {
				lastKnownLocation = null;
			}

			if (afterLocationCallBack != null) {
				afterLocationCallBack.onLocated(location);
			}
		}
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static boolean isGPSOpen(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	public static BDLocation getLastKnownLocation() {
		return lastKnownLocation;
	}

	public static void getLocation(TaskCallBack callfun) {
		LocationClient mLocationClient = new LocationClient(AppContext.getApp());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型为bd09ll
		option.setProdName("com.ch.epeiwang"); // 设置产品线名称
		option.setScanSpan(5000); // 定时定位，每隔5秒钟定位一次
		mLocationClient.setLocOption(option);

		BDLocationListener locListener = new BDLocationListenerImpl(
				mLocationClient, callfun);
		mLocationClient.registerLocationListener(locListener);
		mLocationClient.start();
	}
	
}
