package com.zhizun.pos.xujie;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetUtil
{
  public static boolean checkNet(Context paramContext)
  {
    boolean bool1 = isWIFIConnection(paramContext);
    boolean bool2 = isMOBILEConnection(paramContext);
    return (bool1) || (bool2);
  }
  
  private static boolean isMOBILEConnection(Context paramContext)
  {
    boolean bool = false;
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0);
    if (paramContext != null) {
      bool = paramContext.isConnected();
    }
    return bool;
  }
  
  private static boolean isWIFIConnection(Context paramContext)
  {
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
    if (paramContext != null) {
      return paramContext.isConnected();
    }
    return false;
  }
  
  private static void readAPN(Context paramContext)
  {
    Uri localUri = Uri.parse("content://telephony/carriers/preferapn");
    paramContext = paramContext.getContentResolver().query(localUri, null, null, null, null);
    if ((paramContext != null) && (paramContext.moveToFirst()))
    {
      GlobalParams.PROXY = paramContext.getString(paramContext.getColumnIndex("proxy"));
      GlobalParams.PORT = paramContext.getInt(paramContext.getColumnIndex("port"));
    }
  }
}