package com.zizun.cs.common.utils;

import android.util.Log;

public class LogUtil
{
  public static boolean mIsLog = false;
  
  public static void logD(String paramString)
  {
    if (mIsLog) {
      Log.d("CloudStoreNew", paramString);
    }
  }
}