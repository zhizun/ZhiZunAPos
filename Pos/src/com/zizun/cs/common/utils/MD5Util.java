package com.zizun.cs.common.utils;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
  private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
  
  public static String GetMD5Code(String paramString)
  {
    Object localObject2 = null;
    try
    {
      localObject1 = new String(paramString);
      paramString.printStackTrace();
    }
    catch (NoSuchAlgorithmException paramString)
    {
      try
      {
        paramString = byteToString(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));
        return paramString;
      }
      catch (NoSuchAlgorithmException paramString)
      {
        Object localObject1;
        for (;;) {}
      }
      paramString = paramString;
      localObject1 = localObject2;
    }
    return (String)localObject1;
  }
  
  private static String byteToArrayString(byte paramByte)
  {
    byte b = paramByte;
    paramByte = b;
    if (b < 0) {
      paramByte = b + 256;
    }
    b = paramByte / 16;
    return strDigits[b] + strDigits[(paramByte % 16)];
  }
  
  private static String byteToNum(byte paramByte)
  {
    System.out.println("iRet1=" + paramByte);
    int i = paramByte;
    if (paramByte < 0) {
      i = paramByte + 256;
    }
    return String.valueOf(i);
  }
  
  private static String byteToString(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfByte.length) {
        return localStringBuffer.toString();
      }
      localStringBuffer.append(byteToArrayString(paramArrayOfByte[i]));
      i += 1;
    }
  }
  
  public static String getEncryPassword(String paramString)
  {
    char[] arrayOfChar = new char[16];
    char[] tmp8_6 = arrayOfChar;
    tmp8_6[0] = 48;
    char[] tmp13_8 = tmp8_6;
    tmp13_8[1] = 49;
    char[] tmp18_13 = tmp13_8;
    tmp18_13[2] = 50;
    char[] tmp23_18 = tmp18_13;
    tmp23_18[3] = 51;
    char[] tmp28_23 = tmp23_18;
    tmp28_23[4] = 52;
    char[] tmp33_28 = tmp28_23;
    tmp33_28[5] = 53;
    char[] tmp38_33 = tmp33_28;
    tmp38_33[6] = 54;
    char[] tmp44_38 = tmp38_33;
    tmp44_38[7] = 55;
    char[] tmp50_44 = tmp44_38;
    tmp50_44[8] = 56;
    char[] tmp56_50 = tmp50_44;
    tmp56_50[9] = 57;
    char[] tmp62_56 = tmp56_50;
    tmp62_56[10] = 65;
    char[] tmp68_62 = tmp62_56;
    tmp68_62[11] = 66;
    char[] tmp74_68 = tmp68_62;
    tmp74_68[12] = 67;
    char[] tmp80_74 = tmp74_68;
    tmp80_74[13] = 68;
    char[] tmp86_80 = tmp80_74;
    tmp86_80[14] = 69;
    char[] tmp92_86 = tmp86_80;
    tmp92_86[15] = 70;
    tmp92_86;
    try
    {
      byte[] arrayOfByte = paramString.getBytes();
      Object localObject = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject).update(arrayOfByte);
      arrayOfByte = ((MessageDigest)localObject).digest();
      int k = arrayOfByte.length;
      localObject = new char[k * 2];
      int i = 0;
      int j = 0;
      for (;;)
      {
        if (i >= k) {
          return new String((char[])localObject);
        }
        int m = arrayOfByte[i];
        int n = j + 1;
        localObject[j] = arrayOfChar[(m >>> 4 & 0xF)];
        j = n + 1;
        localObject[n] = arrayOfChar[(m & 0xF)];
        i += 1;
      }
      return paramString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
  }
}