package com.ch.epw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtil {
	private static SpUtil instance;
	static{
		instance=new SpUtil();
	}
	
	public static SpUtil getInstance(){
		if(instance==null){
			instance=new SpUtil();
		}
		return instance;
	}
	
	public SharedPreferences getSharePerference(Context context,String shareName){
		return context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
	}
	
	public static boolean isFirst(SharedPreferences sp){
		return sp.getBoolean("isFirst", false);
	}
	
	public static void setStringSharedPerference(SharedPreferences sp,String key,String value){
		Editor editor=sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void setBooleanSharedPerference(SharedPreferences sp,String key,boolean value){
		Editor editor=sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
