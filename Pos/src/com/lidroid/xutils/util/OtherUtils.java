package com.lidroid.xutils.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;

public class OtherUtils
{
  private static final int STRING_BUFFER_LENGTH = 100;
  private static javax.net.ssl.SSLSocketFactory sslSocketFactory;
  
  public static long getAvailableSpace(File paramFile)
  {
    try
    {
      paramFile = new StatFs(paramFile.getPath());
      long l = paramFile.getBlockSize();
      int i = paramFile.getAvailableBlocks();
      return l * i;
    }
    catch (Throwable paramFile)
    {
      LogUtils.e(paramFile.getMessage(), paramFile);
    }
    return -1L;
  }
  
  public static StackTraceElement getCallerStackTraceElement()
  {
    return Thread.currentThread().getStackTrace()[4];
  }
  
  public static Charset getCharsetFromHttpRequest(HttpRequestBase paramHttpRequestBase)
  {
    if (paramHttpRequestBase == null) {}
    for (;;)
    {
      return null;
      Object localObject1 = null;
      Object localObject2 = paramHttpRequestBase.getFirstHeader("Content-Type");
      paramHttpRequestBase = (HttpRequestBase)localObject1;
      int i;
      label46:
      boolean bool2;
      boolean bool1;
      if (localObject2 != null)
      {
        paramHttpRequestBase = ((Header)localObject2).getElements();
        int j = paramHttpRequestBase.length;
        i = 0;
        if (i >= j) {
          paramHttpRequestBase = (HttpRequestBase)localObject1;
        }
      }
      else
      {
        bool2 = false;
        bool1 = bool2;
        if (TextUtils.isEmpty(paramHttpRequestBase)) {}
      }
      try
      {
        bool1 = Charset.isSupported(paramHttpRequestBase);
        if (!bool1) {
          continue;
        }
        return Charset.forName(paramHttpRequestBase);
        localObject2 = paramHttpRequestBase[i].getParameterByName("charset");
        if (localObject2 != null)
        {
          paramHttpRequestBase = ((NameValuePair)localObject2).getValue();
          break label46;
        }
        i += 1;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          bool1 = bool2;
        }
      }
    }
  }
  
  public static StackTraceElement getCurrentStackTraceElement()
  {
    return Thread.currentThread().getStackTrace()[3];
  }
  
  public static String getDiskCacheDir(Context paramContext, String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if ("mounted".equals(Environment.getExternalStorageState()))
    {
      File localFile = paramContext.getExternalCacheDir();
      localObject1 = localObject2;
      if (localFile != null) {
        localObject1 = localFile.getPath();
      }
    }
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      paramContext = paramContext.getCacheDir();
      localObject2 = localObject1;
      if (paramContext != null)
      {
        localObject2 = localObject1;
        if (paramContext.exists()) {
          localObject2 = paramContext.getPath();
        }
      }
    }
    return localObject2 + File.separator + paramString;
  }
  
  public static String getFileNameFromHttpResponse(HttpResponse paramHttpResponse)
  {
    if (paramHttpResponse == null) {}
    for (;;)
    {
      return null;
      paramHttpResponse = paramHttpResponse.getFirstHeader("Content-Disposition");
      if (paramHttpResponse != null)
      {
        paramHttpResponse = paramHttpResponse.getElements();
        int j = paramHttpResponse.length;
        int i = 0;
        while (i < j)
        {
          NameValuePair localNameValuePair = paramHttpResponse[i].getParameterByName("filename");
          if (localNameValuePair != null)
          {
            paramHttpResponse = localNameValuePair.getValue();
            return CharsetUtils.toCharset(paramHttpResponse, "UTF-8", paramHttpResponse.length());
          }
          i += 1;
        }
      }
    }
  }
  
  public static String getSubString(String paramString, int paramInt1, int paramInt2)
  {
    return new String(paramString.substring(paramInt1, paramInt2));
  }
  
  public static String getUserAgent(Context paramContext)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramContext != null) {}
    try
    {
      localObject1 = paramContext.getString(((Integer)Class.forName("com.android.internal.R$string").getDeclaredField("web_user_agent").get(null)).intValue());
      paramContext = (Context)localObject1;
      if (TextUtils.isEmpty((CharSequence)localObject1)) {
        paramContext = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
      }
      localObject2 = Locale.getDefault();
      localObject1 = new StringBuffer();
      String str = Build.VERSION.RELEASE;
      if (str.length() > 0)
      {
        ((StringBuffer)localObject1).append(str);
        ((StringBuffer)localObject1).append("; ");
        str = ((Locale)localObject2).getLanguage();
        if (str == null) {
          break label214;
        }
        ((StringBuffer)localObject1).append(str.toLowerCase());
        localObject2 = ((Locale)localObject2).getCountry();
        if (localObject2 != null)
        {
          ((StringBuffer)localObject1).append("-");
          ((StringBuffer)localObject1).append(((String)localObject2).toLowerCase());
        }
      }
      for (;;)
      {
        if ("REL".equals(Build.VERSION.CODENAME))
        {
          localObject2 = Build.MODEL;
          if (((String)localObject2).length() > 0)
          {
            ((StringBuffer)localObject1).append("; ");
            ((StringBuffer)localObject1).append((String)localObject2);
          }
        }
        localObject2 = Build.ID;
        if (((String)localObject2).length() > 0)
        {
          ((StringBuffer)localObject1).append(" Build/");
          ((StringBuffer)localObject1).append((String)localObject2);
        }
        return String.format(paramContext, new Object[] { localObject1, "Mobile " });
        ((StringBuffer)localObject1).append("1.0");
        break;
        label214:
        ((StringBuffer)localObject1).append("en");
      }
    }
    catch (Throwable paramContext)
    {
      for (;;)
      {
        localObject1 = localObject2;
      }
    }
  }
  
  public static boolean isSupportRange(HttpResponse paramHttpResponse)
  {
    if (paramHttpResponse == null) {}
    do
    {
      do
      {
        return false;
        Header localHeader = paramHttpResponse.getFirstHeader("Accept-Ranges");
        if (localHeader != null) {
          return "bytes".equals(localHeader.getValue());
        }
        paramHttpResponse = paramHttpResponse.getFirstHeader("Content-Range");
      } while (paramHttpResponse == null);
      paramHttpResponse = paramHttpResponse.getValue();
    } while ((paramHttpResponse == null) || (!paramHttpResponse.startsWith("bytes")));
    return true;
  }
  
  public static long sizeOfString(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    long l2;
    if (TextUtils.isEmpty(paramString1)) {
      l2 = 0L;
    }
    int k;
    long l1;
    int i;
    do
    {
      return l2;
      k = paramString1.length();
      if (k < 100) {
        return paramString1.getBytes(paramString2).length;
      }
      l1 = 0L;
      i = 0;
      l2 = l1;
    } while (i >= k);
    int j = i + 100;
    if (j < k) {}
    for (;;)
    {
      l1 += getSubString(paramString1, i, j).getBytes(paramString2).length;
      i += 100;
      break;
      j = k;
    }
  }
  
  public static void trustAllHttpsURLConnection()
  {
    X509TrustManager local1;
    if (sslSocketFactory == null) {
      local1 = new X509TrustManager()
      {
        public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) {}
        
        public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) {}
        
        public X509Certificate[] getAcceptedIssuers()
        {
          return null;
        }
      };
    }
    try
    {
      SSLContext localSSLContext = SSLContext.getInstance("TLS");
      localSSLContext.init(null, new TrustManager[] { local1 }, null);
      sslSocketFactory = localSSLContext.getSocketFactory();
      if (sslSocketFactory != null)
      {
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        LogUtils.e(localThrowable.getMessage(), localThrowable);
      }
    }
  }
}