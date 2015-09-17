package com.zizun.cs.common.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil
{
  public static void toastLong(Context paramContext, int paramInt)
  {
    Toast.makeText(paramContext, paramInt, 1).show();
  }
  
  public static void toastLong(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 1).show();
  }
  
  public static void toastShort(Context paramContext, int paramInt)
  {
    Toast.makeText(paramContext, paramInt, 0).show();
  }
  
  public static void toastShort(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 0).show();
  }
}