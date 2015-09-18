package com.ch.epw.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhizun.pos.R;

public class Options {

	public static DisplayImageOptions getListOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.default_photo)
				// // 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.default_photo)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.default_photo)
				.cacheInMemory(false)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(10))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		return options;
	}

	/**
	 * 自带圆角的显示options
	 * @param imageId
	 * @return
	 */
	public static DisplayImageOptions getListOptions(int imageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				.showImageOnLoading(imageId)
				// // 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(imageId)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(imageId).cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true).resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(10))// 是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		return options;
	}
	
	/**
	 * 不带圆角、带默认图片设置的options
	 * @param imageId
	 * @return
	 */
	public static DisplayImageOptions getShowNormalPictureWithDefaultImage(int imageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				.showImageOnLoading(imageId)
				// // 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(imageId)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(imageId).cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				.considerExifParams(true).resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.build();
		return options;
	}
}
