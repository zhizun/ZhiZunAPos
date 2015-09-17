package com.zizun.cs.biz.dao.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class IDUtil
{
  private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
  
  public static long getID()
  {
    try
    {
      int i = getRandomNum();
      long l1 = Long.parseLong(df.format(Long.valueOf(System.currentTimeMillis())));
      long l2 = i;
      return 100L * l1 + l2;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static long getID(Timestamp paramTimestamp)
  {
    int i = getRandomNum();
    return 100L * Long.parseLong(df.format(paramTimestamp)) + i;
  }
  
  public static String getIDString()
  {
    return String.valueOf(getID());
  }
  
  private static int getRandomNum()
  {
    return (int)(Math.random() * 100.0D);
  }
}