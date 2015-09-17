package com.zhizun.pos.util;

import android.util.Log;

public class LogUtil
{
  private static boolean mIsLog = true;
  
  public static void logD(String paramString)
  {
    if (mIsLog) {
      Log.d("CloudStoreNew", paramString);
    }
  }
}