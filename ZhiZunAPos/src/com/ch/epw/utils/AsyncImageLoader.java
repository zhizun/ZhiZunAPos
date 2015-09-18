package com.ch.epw.utils;
//package com.epei.common;
//
//import java.lang.ref.SoftReference;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.widget.ImageView;
//
///**
// * 图片异步加载类
// * 
// * @author Leslie.Fang
// * @company EnwaySoft
// * 
// */
//public class AsyncImageLoader {
//	// 最大线程数
//	private static final int MAX_THREAD_NUM = 10;
//	private Map<String, SoftReference<Bitmap>> imageCaches = null;
//	private FileUtil fileUtil;
//	// 线程池
//	private ExecutorService threadPools = null;
//
//	public AsyncImageLoader(Context context) {
//		imageCaches = new HashMap<String, SoftReference<Bitmap>>();
//		fileUtil = new FileUtil(context);
//	}
//
//	public Bitmap loadImage(final ImageView imageView, final String imageUrl,
//			final ImageDownloadCallBack imageDownloadCallBack) {
//		final String filename = imageUrl
//				.substring(imageUrl.lastIndexOf("/") + 1);
//		final String filepath = fileUtil.getAbsolutePath() + "/" + filename;
//
//		// 先从软引用中找
//		if (imageCaches.containsKey(imageUrl)) {
//			SoftReference<Bitmap> reference = imageCaches.get(imageUrl);
//			Bitmap bitmap = reference.get();
//
//			// 软引用中的 Bitmap 对象可能随时被回收
//			// 如果软引用中的 Bitmap 已被回收，则从文件中找
//			if (bitmap != null) {
//				Log.i("aaaa", "cache exists " + filename);
//
//				return bitmap;
//			}
//		}
//
//		// 从文件中找
//		if (fileUtil.isBitmapExists(filename)) {
//			Log.i("aaaa", "file exists " + filename);
//			Bitmap bitmap = BitmapFactory.decodeFile(filepath);
//
//			// 重新加入到内存软引用中
//			imageCaches.put(imageUrl, new SoftReference<Bitmap>(bitmap));
//
//			return bitmap;
//		}
//
//		// 软引用和文件中都没有再从网络下载
//		if (imageUrl != null && !imageUrl.equals("")) {
//			if (threadPools == null) {
//				threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
//			}
//
//			final Handler handler = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					if (msg.what == 111 && imageDownloadCallBack != null) {
//						Bitmap bitmap = (Bitmap) msg.obj;
//						imageDownloadCallBack.onImageDownloaded(imageView,
//								bitmap);
//					}
//				}
//			};
//
//			Thread thread = new Thread() {
//				@Override
//				public void run() {
//					Log.i("aaaa", Thread.currentThread().getName()
//							+ " is running");
//					
//					Bitmap bitmap = null;
//					try {
//						bitmap = ApiClient.getNetBitmap(imageUrl);
//					} catch (AppException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					// 图片下载成功重新缓存并执行回调刷新界面
//					if (bitmap != null) {
//						// 加入到软引用中
//						imageCaches.put(imageUrl, new SoftReference<Bitmap>(
//								bitmap));
//						// 缓存到文件系统
//						fileUtil.saveBitmap(filepath, bitmap);
//
//						Message msg = new Message();
//						msg.what = 111;
//						msg.obj = bitmap;
//						handler.sendMessage(msg);
//					}
//				}
//			};
//
//			threadPools.execute(thread);
//		}
//
//		return null;
//	}
//
//	public void shutDownThreadPool() {
//		if (threadPools != null) {
//			threadPools.shutdown();
//			threadPools = null;
//		}
//	}
//
//	/**
//	 * 图片下载完成回调接口
//	 * 
//	 */
//	public interface ImageDownloadCallBack {
//		void onImageDownloaded(ImageView imageView, Bitmap bitmap);
//	}
//}
