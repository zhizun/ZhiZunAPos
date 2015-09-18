package com.ch.epw.utils;

import android.graphics.Bitmap;
import android.view.View;

public class ButtonClickUtil {
    	
	 private static long lastClickTime;  
	 /**
	  * 防止按钮连续点击
	  * @return
	  */
	    public static boolean isFastDoubleClick() {  
	        long time = System.currentTimeMillis();  
	        long timeD = time - lastClickTime;  
	        if ( 0 < timeD && timeD < 1000) {     
	            return true;     
	        }     
	        lastClickTime = time;     
	        return false;     
	    }  
}

//按钮点击时： 
//   
//public void onClick(View v) {  
//    if (Utils.isFastDoubleClick()) {  
//        return;  
//    }  
//} 
