package com.zhizun.pos.ui.popupwindow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.lidroid.xutils.util.LogUtils;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zizun.cs.common.utils.FileUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class PhotoPopupWindow extends CustomPopupWindow implements View.OnClickListener {
	private static PhotoPopupWindow photoPopupWindow;
	private OnPhotoPopupWindowClickListener clickListener;

	private PhotoPopupWindow(Context paramContext, View paramView) {
		super(paramContext, paramView, -1);
	}

	public static PhotoPopupWindow create(Context paramContext) {
		View localView = LayoutInflater.from(paramContext).inflate(2130903152, null);
		localView.setBackgroundColor(Color.argb(200, 0, 0, 0));
		Button localButton1 = (Button) localView.findViewById(2131362468);
		Button localButton2 = (Button) localView.findViewById(2131362469);
		Button localButton3 = (Button) localView.findViewById(2131362470);
		photoPopupWindow = new PhotoPopupWindow(paramContext, localView);
		photoPopupWindow.setAnimationStyle(2131230795);
		photoPopupWindow.showMenu(localView, 80, 0, 0);
		localButton1.setOnClickListener(photoPopupWindow);
		localButton2.setOnClickListener(photoPopupWindow);
		localButton3.setOnClickListener(photoPopupWindow);
		return photoPopupWindow;
	}

	public static String getPickPhotoResult(Context paramContext, Intent paramIntent) {
		if (paramIntent == null) {
			return null;
		}
		Object localObject = null;
		paramIntent = paramIntent.getData();
		paramIntent = paramContext.getContentResolver().query(paramIntent, new String[] { "_data" }, null, null, null);
		paramContext = (Context) localObject;
		if (paramIntent != null) {
			paramIntent.moveToNext();
			paramContext = paramIntent.getString(paramIntent.getColumnIndex("_data"));
		}
		return paramContext;
	}

	public static void pickPhoto(Activity paramActivity, int paramInt) {
		Intent localIntent = new Intent();
		localIntent.setAction("android.intent.action.PICK");
		localIntent.setType("image/*");
		paramActivity.startActivityForResult(localIntent, paramInt);
	}

	public static String takePhoto(Activity paramActivity, int paramInt) {
		Object localObject = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		localObject = FileUtil.getRootFilePath("Image") + ((SimpleDateFormat) localObject).format(new Date()) + ".jpg";
		File localFile = new File((String) localObject);
		if (!localFile.getParentFile().exists()) {
			localFile.getParentFile().mkdirs();
		}
		Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
		localIntent.putExtra("output", Uri.fromFile(localFile));
		paramActivity.startActivityForResult(localIntent, paramInt);
		return (String) localObject;
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		}
		for (;;) {
			if ((photoPopupWindow != null) && (photoPopupWindow.isShowing())) {
				photoPopupWindow.dismiss();
			}
			return;
			LogUtils.i("拍照");
			if (this.clickListener != null) {
				this.clickListener.takePhoto();
				continue;
				LogUtils.i("相册");
				if (this.clickListener != null) {
					this.clickListener.pickPhoto();
					continue;
					LogUtils.i("取消");
					if (this.clickListener != null) {
						this.clickListener.cancel();
					}
				}
			}
		}
	}

	public void setOnPhotoPopupWindowClickListener(
			OnPhotoPopupWindowClickListener paramOnPhotoPopupWindowClickListener) {
		this.clickListener = paramOnPhotoPopupWindowClickListener;
	}

	public static abstract interface OnPhotoPopupWindowClickListener {
		public abstract void cancel();

		public abstract void pickPhoto();

		public abstract void takePhoto();
	}
}