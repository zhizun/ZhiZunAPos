package com.zizun.cs.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil
{
  private static String tel_reg = "^1[3-8][0-9]{9}$";
  
  public static String filterChinese(String paramString)
  {
    return paramString.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
  }
  
  public static boolean isAccountString(String paramString)
  {
    return Pattern.compile("^[A-Za-z0-9_]+$").matcher(paramString).matches();
  }
  
  public static boolean isEmail(String paramString)
  {
    return Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$").matcher(paramString).matches();
  }
  
  public static boolean isEmptSpace(String paramString)
  {
    return paramString.indexOf(" ") == -1;
  }
  
  public static boolean isMobileNum(String paramString)
  {
    return Pattern.compile(tel_reg).matcher(paramString).matches();
  }
  
  public static boolean isNumberDecimal(String paramString)
  {
    return Pattern.compile("^[-\\+]?\\d+(\\.\\d+)?$").matcher(paramString).matches();
  }
  
  public static boolean isPasswdString(String paramString)
  {
    return Pattern.compile("^[A-Za-z0-9_!#@$%^&*.~]{6,22}+$").matcher(paramString).matches();
  }
  
  public static String trimInnerSpaceStr(String paramString)
  {
    paramString = paramString.trim();
    if (!paramString.startsWith(" ")) {}
    for (;;)
    {
      if (!paramString.endsWith(" "))
      {
        return paramString;
        paramString = paramString.substring(1, paramString.length()).trim();
        break;
      }
      paramString = paramString.substring(0, paramString.length() - 1).trim();
    }
  }
}