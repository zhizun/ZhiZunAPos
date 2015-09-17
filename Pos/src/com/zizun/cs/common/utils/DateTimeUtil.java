package com.zizun.cs.common.utils;

import com.lidroid.xutils.util.LogUtils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil
{
  public static Timestamp getCurrentTime()
  {
    return new Timestamp(System.currentTimeMillis());
  }
  
  public static String getRequestTime()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    LogUtils.i(localSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
    return localSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
  }
  
  public static String getSimpleTimeString()
  {
    return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
  }
  
  public static Timestamp getSimpleTimestampFromString(String paramString)
  {
    return getTimestampFromString(paramString, "yyyy-MM-dd");
  }
  
  public static Timestamp getTimeFromString(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    try
    {
      paramString = new Timestamp(localSimpleDateFormat.parse(paramString).getTime());
      return paramString;
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static int getTimeInt(Timestamp paramTimestamp)
  {
    return Integer.parseInt(new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date(paramTimestamp.getTime())));
  }
  
  public static String getTimeStrForElevenLen(Timestamp paramTimestamp)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(paramTimestamp.getTime()));
  }
  
  public static String getTimeString()
  {
    return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
  }
  
  public static String getTimeString(Timestamp paramTimestamp)
  {
    return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date(paramTimestamp.getTime()));
  }
  
  public static Timestamp getTimestampFromString(String paramString)
  {
    return getTimestampFromString(paramString, "yyyy-MM-dd HH:mm:ss.SSS");
  }
  
  public static Timestamp getTimestampFromString(String paramString1, String paramString2)
  {
    paramString2 = new SimpleDateFormat(paramString2, Locale.getDefault());
    try
    {
      paramString1 = new Timestamp(paramString2.parse(paramString1).getTime());
      return paramString1;
    }
    catch (ParseException paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
}