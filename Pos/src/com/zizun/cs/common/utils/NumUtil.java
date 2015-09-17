package com.zizun.cs.common.utils;

import android.text.TextUtils;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumUtil
{
  public static String getDecimal(double paramDouble)
  {
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
    localNumberFormat.setMaximumFractionDigits(2);
    return localNumberFormat.format(paramDouble);
  }
  
  public static String getDecimal2(String paramString)
  {
    double d = getDouble(paramString);
    paramString = NumberFormat.getNumberInstance();
    paramString.setMaximumFractionDigits(2);
    return paramString.format(d);
  }
  
  public static double getDouble(String paramString)
  {
    try
    {
      double d = Double.valueOf(paramString).doubleValue();
      return d;
    }
    catch (NumberFormatException paramString)
    {
      paramString.printStackTrace();
    }
    return 0.0D;
  }
  
  public static String getInputMsg(String paramString)
  {
    String str = paramString;
    if (paramString.contains(","))
    {
      str = paramString;
      if (paramString != null) {
        str = paramString.replaceAll(",", "");
      }
    }
    return str;
  }
  
  public static double getNum(double paramDouble)
  {
    return new BigDecimal(paramDouble).setScale(2, 4).doubleValue();
  }
  
  public static double getNum6(double paramDouble)
  {
    return new BigDecimal(paramDouble).setScale(6, 4).doubleValue();
  }
  
  public static String getPercent(double paramDouble)
  {
    NumberFormat localNumberFormat = NumberFormat.getPercentInstance();
    localNumberFormat.setMaximumIntegerDigits(3);
    localNumberFormat.setMaximumFractionDigits(2);
    return localNumberFormat.format(paramDouble);
  }
  
  public static double getPrice(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return 0.0D;
    }
    String str1 = paramString;
    String str2 = str1;
    try
    {
      if (str1.contains(","))
      {
        str2 = str1;
        if (paramString != null) {
          str2 = str1.replaceAll(",", "");
        }
      }
      return Double.parseDouble(str2);
    }
    catch (Exception paramString) {}
    return 0.0D;
  }
}