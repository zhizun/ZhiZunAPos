package com.geetest.sdk;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GeetestLib
{
  private final String api_url = "http://api.geetest.com";
  private final String baseUrl = "api.geetest.com";
  private String captchaId = "";
  private String challengeId = "";
  private final int defaultIsMobile = 0;
  private final int defaultMobileWidth = 260;
  private final String https_api_url = "https://api.geetest.com";
  private Boolean isHttps = Boolean.valueOf(false);
  private int isMobile = 0;
  private String picId = "";
  private String privateKey = "";
  private String productType = "embed";
  private final String sdkLang = "android_sdk";
  private String submitBtnId = "submit-button";
  private final String verName = "2.15.4.13.1";
  
  public GeetestLib() {}
  
  public GeetestLib(String paramString)
  {
    this.privateKey = paramString;
  }
  
  private boolean checkResultByPrivate(String paramString1, String paramString2)
  {
    return paramString2.equals(md5Encode(this.privateKey + "geetest" + paramString1));
  }
  
  private String dealResponseResult(InputStream paramInputStream)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      try
      {
        i = paramInputStream.read(arrayOfByte);
        if (i != -1) {
          continue;
        }
      }
      catch (IOException paramInputStream)
      {
        int i;
        paramInputStream.printStackTrace();
        continue;
      }
      return new String(localByteArrayOutputStream.toByteArray());
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
  }
  
  private String fixEncoding(String paramString)
    throws UnsupportedEncodingException
  {
    return URLEncoder.encode(new String(paramString.getBytes("UTF-8")), "UTF-8");
  }
  
  private StringBuffer getRequestData(Map<String, String> paramMap, String paramString)
  {
    localStringBuffer = new StringBuffer();
    try
    {
      paramMap = paramMap.entrySet().iterator();
      for (;;)
      {
        if (!paramMap.hasNext())
        {
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          return localStringBuffer;
        }
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        localStringBuffer.append((String)localEntry.getKey()).append("=").append(URLEncoder.encode((String)localEntry.getValue(), paramString)).append("&");
      }
      return localStringBuffer;
    }
    catch (Exception paramMap)
    {
      paramMap.printStackTrace();
    }
  }
  
  public static void gtlog(String paramString)
  {
    System.out.println("gtlog: " + paramString);
  }
  
  public static void log_v(String paramString)
  {
    Log.v("geetest", paramString);
  }
  
  private boolean objIsEmpty(Object paramObject)
  {
    return paramObject == null;
  }
  
  private String postValidate(String paramString1, String paramString2, String paramString3, int paramInt)
    throws Exception
  {
    String str = "error";
    Socket localSocket = new Socket(InetAddress.getByName(paramString1), paramInt);
    BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream(), "UTF8"));
    localBufferedWriter.write("POST " + paramString2 + " HTTP/1.0\r\n");
    localBufferedWriter.write("Host: " + paramString1 + "\r\n");
    localBufferedWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
    localBufferedWriter.write("Content-Length: " + paramString3.length() + "\r\n");
    localBufferedWriter.write("\r\n");
    localBufferedWriter.write(paramString3);
    localBufferedWriter.flush();
    paramString3 = new BufferedReader(new InputStreamReader(localSocket.getInputStream(), "UTF-8"));
    for (paramString1 = str;; paramString1 = paramString2)
    {
      paramString2 = paramString3.readLine();
      if (paramString2 == null)
      {
        localBufferedWriter.close();
        paramString3.close();
        localSocket.close();
        return paramString1;
      }
      System.out.println(paramString2);
    }
  }
  
  /* Error */
  private String readContentFromGet(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: new 308	java/net/URL
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 309	java/net/URL:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: new 158	java/lang/StringBuffer
    //   12: dup
    //   13: invokespecial 159	java/lang/StringBuffer:<init>	()V
    //   16: astore_1
    //   17: aload_3
    //   18: invokevirtual 313	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   21: checkcast 315	java/net/HttpURLConnection
    //   24: astore_3
    //   25: aload_3
    //   26: sipush 1000
    //   29: invokevirtual 319	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   32: aload_3
    //   33: sipush 1000
    //   36: invokevirtual 322	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   39: aload_3
    //   40: invokevirtual 325	java/net/HttpURLConnection:connect	()V
    //   43: sipush 1024
    //   46: newarray <illegal type>
    //   48: astore 4
    //   50: aload_3
    //   51: invokevirtual 326	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   54: astore 5
    //   56: new 328	java/io/BufferedInputStream
    //   59: dup
    //   60: aload 5
    //   62: invokespecial 331	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   65: pop
    //   66: aload 5
    //   68: aload 4
    //   70: invokevirtual 122	java/io/InputStream:read	([B)I
    //   73: istore_2
    //   74: iload_2
    //   75: iconst_m1
    //   76: if_icmpne +17 -> 93
    //   79: aload 5
    //   81: invokevirtual 332	java/io/InputStream:close	()V
    //   84: aload_3
    //   85: invokevirtual 335	java/net/HttpURLConnection:disconnect	()V
    //   88: aload_1
    //   89: invokevirtual 336	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   92: areturn
    //   93: aload_1
    //   94: new 86	java/lang/String
    //   97: dup
    //   98: aload 4
    //   100: iconst_0
    //   101: iload_2
    //   102: ldc -115
    //   104: invokespecial 339	java/lang/String:<init>	([BIILjava/lang/String;)V
    //   107: invokevirtual 197	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   110: pop
    //   111: goto -45 -> 66
    //   114: astore_3
    //   115: aload_1
    //   116: invokevirtual 336	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   119: areturn
    //   120: astore_3
    //   121: aload_3
    //   122: invokevirtual 205	java/lang/Exception:printStackTrace	()V
    //   125: aload_3
    //   126: invokevirtual 342	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   129: invokestatic 344	com/geetest/sdk/GeetestLib:log_v	(Ljava/lang/String;)V
    //   132: goto -17 -> 115
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	this	GeetestLib
    //   0	135	1	paramString	String
    //   73	29	2	i	int
    //   8	77	3	localObject	Object
    //   114	1	3	localEOFException	java.io.EOFException
    //   120	6	3	localException	Exception
    //   48	51	4	arrayOfByte	byte[]
    //   54	26	5	localInputStream	InputStream
    // Exception table:
    //   from	to	target	type
    //   25	66	114	java/io/EOFException
    //   66	74	114	java/io/EOFException
    //   79	93	114	java/io/EOFException
    //   93	111	114	java/io/EOFException
    //   25	66	120	java/lang/Exception
    //   66	74	120	java/lang/Exception
    //   79	93	120	java/lang/Exception
    //   93	111	120	java/lang/Exception
  }
  
  private boolean validate(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString2.length() > 0) && (checkResultByPrivate(paramString1, paramString2)))
    {
      paramString2 = "seccode=" + paramString3;
      paramString1 = "";
      try
      {
        paramString2 = postValidate("api.geetest.com", "/validate.php", paramString2, 80);
        paramString1 = paramString2;
        gtlog(paramString2);
        paramString1 = paramString2;
      }
      catch (Exception paramString2)
      {
        for (;;)
        {
          paramString2.printStackTrace();
        }
      }
      gtlog("md5: " + md5Encode(paramString3));
      if (paramString1.equals(md5Encode(paramString3))) {
        return true;
      }
    }
    return false;
  }
  
  public String getCaptchaId()
  {
    return this.captchaId;
  }
  
  public String getChallengeId()
  {
    return this.challengeId;
  }
  
  public String getGtFrontSource()
  {
    if (this.isHttps.booleanValue())
    {
      str = "https://api.geetest.com";
      str = String.format("<script type=\"text/javascript\" src=\"%s/get.php?gt=%s&challenge=%s", new Object[] { str, this.captchaId, this.challengeId });
      if (!this.productType.equals("popup")) {
        break label124;
      }
    }
    label124:
    for (String str = str + String.format("&product=%s&popupbtnid=%s", new Object[] { this.productType, this.submitBtnId });; str = str + String.format("&product=%s", new Object[] { this.productType }))
    {
      return str + "\"></script>";
      str = "http://api.geetest.com";
      break;
    }
  }
  
  public int getGtServerStatus()
  {
    try
    {
      if (readContentFromGet("http://api.geetest.com/check_status.php").equals("ok")) {
        return 1;
      }
      System.out.println("gServer is Down");
      return 0;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public Boolean getIsHttps()
  {
    return this.isHttps;
  }
  
  public int getIsMobile()
  {
    return this.isMobile;
  }
  
  public String getPicId()
  {
    return this.picId;
  }
  
  public String getPrivateKey()
  {
    return this.privateKey;
  }
  
  public String getProductType()
  {
    return this.productType;
  }
  
  public int getRandomNum()
  {
    return (int)(Math.random() * 100.0D);
  }
  
  public String getSubmitBtnId()
  {
    return this.submitBtnId;
  }
  
  public String getVerName()
  {
    return "2.15.4.13.1";
  }
  
  public String getVersionInfo()
  {
    return "2.15.4.13.1";
  }
  
  public String md5Encode(String paramString)
  {
    String str = new String();
    for (;;)
    {
      int i;
      try
      {
        localObject = MessageDigest.getInstance("MD5");
        ((MessageDigest)localObject).update(paramString.getBytes());
        paramString = ((MessageDigest)localObject).digest();
        localObject = new StringBuffer("");
        i = 0;
        if (i < paramString.length) {
          break label97;
        }
        return ((StringBuffer)localObject).toString();
      }
      catch (NoSuchAlgorithmException paramString)
      {
        Object localObject;
        paramString.printStackTrace();
        return str;
      }
      if (j < 16) {
        ((StringBuffer)localObject).append("0");
      }
      ((StringBuffer)localObject).append(Integer.toHexString(j));
      i += 1;
      continue;
      label97:
      int k = paramString[i];
      int j = k;
      if (k < 0) {
        j = k + 256;
      }
    }
  }
  
  public int preProcess()
  {
    int i = 1;
    if (registerChallenge() != 1) {
      i = 0;
    }
    return i;
  }
  
  public int registerChallenge()
  {
    try
    {
      String str = readContentFromGet("http://api.geetest.com/register.php?gt=" + this.captchaId);
      log_v("register:" + str);
      if (32 == str.length())
      {
        this.challengeId = str;
        return 1;
      }
      System.out.println("gServer register challenge failed");
      return 0;
    }
    catch (Exception localException)
    {
      gtlog("exception:register api:" + localException.getMessage());
    }
    return 0;
  }
  
  public void setCaptchaId(String paramString)
  {
    this.captchaId = paramString;
  }
  
  public void setChallengeId(String paramString)
  {
    this.challengeId = paramString;
  }
  
  public void setIsHttps(Boolean paramBoolean)
  {
    this.isHttps = paramBoolean;
  }
  
  public void setIsMobile(int paramInt)
  {
    this.isMobile = paramInt;
  }
  
  public void setPicId(String paramString)
  {
    this.picId = paramString;
  }
  
  public void setPrivateKey(String paramString)
  {
    this.privateKey = paramString;
  }
  
  public void setProductType(String paramString)
  {
    this.productType = paramString;
  }
  
  public void setSubmitBtnId(String paramString)
  {
    this.submitBtnId = paramString;
  }
  
  public String submitPostData(String paramString1, Map<String, String> paramMap, String paramString2)
  {
    paramMap = getRequestData(paramMap, paramString2).toString().getBytes();
    try
    {
      paramString1 = (HttpURLConnection)new URL(paramString1).openConnection();
      paramString1.setConnectTimeout(3000);
      paramString1.setDoInput(true);
      paramString1.setDoOutput(true);
      paramString1.setRequestMethod("POST");
      paramString1.setUseCaches(false);
      paramString1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      paramString1.setRequestProperty("Content-Length", String.valueOf(paramMap.length));
      paramString1.getOutputStream().write(paramMap);
      if (paramString1.getResponseCode() == 200)
      {
        paramString1 = dealResponseResult(paramString1.getInputStream());
        return paramString1;
      }
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
    }
    return "";
  }
}