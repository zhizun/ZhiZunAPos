package com.zhizun.pos.base;

import com.ch.epw.utils.URLs;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
 * adapter基类
 * 创建人：李林中
 * 创建日期：2014-12-15  上午10:04:04
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */

public abstract class MyBaseAdapter extends BaseAdapter  {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;
	
	/**
	 * 通过displayImage显示图片，增加了默认图片的显示及异常处理
	 * @param uri
	 * @param imageView
	 * @param options
	 */
	protected void showPicture(String uri, ImageView imageView, DisplayImageOptions options){
		if(uri == null || uri.equals("") || imageView == null){
			return;
		}
		//如果是系统默认图片不处理
//		if(ImageUtils.isSysDefault(uri) && imageView.getDrawable() != null){
//			return;
//		}
		if(!uri.startsWith("http://")&&!uri.startsWith("https://")){
			uri = URLs.URL_IMG_API_HOST + uri;
		}
		imageLoader.displayImage(uri, imageView, options);
	}
}
