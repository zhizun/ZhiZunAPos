package com.zhizun.pos.util;

import java.text.NumberFormat;

public class DataUtil
{
  public static String getFormatString(double paramDouble)
  {
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
    localNumberFormat.setMaximumFractionDigits(2);
    return localNumberFormat.format(paramDouble);
  }
}