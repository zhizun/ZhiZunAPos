package com.zhizun.pos.util;

import com.zhizun.pos.app.StoreApplication;

public class ResUtil {
	public static int getColor(int paramInt) {
		return StoreApplication.getContext().getResources().getColor(paramInt);
	}

	public static float getDimens(int paramInt) {
		return StoreApplication.getContext().getResources().getDimensionPixelSize(paramInt);
	}

	public static int getDrawableId(String paramString) {
		return StoreApplication.getContext().getResources().getIdentifier(paramString, "drawable",
				StoreApplication.getContext().getPackageName());
	}

	public static String getFormatString(int paramInt, Object paramObject) {
		return StoreApplication.getContext().getResources().getString(paramInt, new Object[] { paramObject });
	}

	public static String getString(int paramInt) {
		return StoreApplication.getContext().getResources().getString(paramInt);
	}

	public static String[] getStringArrays(int paramInt) {
		return StoreApplication.getContext().getResources().getStringArray(paramInt);
	}
}