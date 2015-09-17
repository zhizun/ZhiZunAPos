package com.lidroid.xutils.http;

import android.text.TextUtils;
import com.lidroid.xutils.cache.LruMemoryCache;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import java.util.concurrent.ConcurrentHashMap;

public class HttpCache
{
  private static final int DEFAULT_CACHE_SIZE = 102400;
  private static final long DEFAULT_EXPIRY_TIME = 60000L;
  private static long defaultExpiryTime = 60000L;
  private static final ConcurrentHashMap<String, Boolean> httpMethod_enabled_map = new ConcurrentHashMap(10);
  private int cacheSize = 102400;
  private final LruMemoryCache<String, String> mMemoryCache;
  
  static
  {
    httpMethod_enabled_map.put(HttpRequest.HttpMethod.GET.toString(), Boolean.valueOf(true));
  }
  
  public HttpCache()
  {
    this(102400, 60000L);
  }
  
  public HttpCache(int paramInt, long paramLong)
  {
    this.cacheSize = paramInt;
    defaultExpiryTime = paramLong;
    this.mMemoryCache = new LruMemoryCache(this.cacheSize)
    {
      protected int sizeOf(String paramAnonymousString1, String paramAnonymousString2)
      {
        if (paramAnonymousString2 == null) {
          return 0;
        }
        return paramAnonymousString2.length();
      }
    };
  }
  
  public static long getDefaultExpiryTime()
  {
    return defaultExpiryTime;
  }
  
  public static void setDefaultExpiryTime(long paramLong)
  {
    defaultExpiryTime = paramLong;
  }
  
  public void clear()
  {
    this.mMemoryCache.evictAll();
  }
  
  public String get(String paramString)
  {
    if (paramString != null) {
      return (String)this.mMemoryCache.get(paramString);
    }
    return null;
  }
  
  public boolean isEnabled(HttpRequest.HttpMethod paramHttpMethod)
  {
    if (paramHttpMethod == null) {}
    do
    {
      return false;
      paramHttpMethod = (Boolean)httpMethod_enabled_map.get(paramHttpMethod.toString());
    } while (paramHttpMethod == null);
    return paramHttpMethod.booleanValue();
  }
  
  public boolean isEnabled(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {}
    do
    {
      return false;
      paramString = (Boolean)httpMethod_enabled_map.get(paramString.toUpperCase());
    } while (paramString == null);
    return paramString.booleanValue();
  }
  
  public void put(String paramString1, String paramString2)
  {
    put(paramString1, paramString2, defaultExpiryTime);
  }
  
  public void put(String paramString1, String paramString2, long paramLong)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramLong < 1L)) {
      return;
    }
    this.mMemoryCache.put(paramString1, paramString2, System.currentTimeMillis() + paramLong);
  }
  
  public void setCacheSize(int paramInt)
  {
    this.mMemoryCache.setMaxSize(paramInt);
  }
  
  public void setEnabled(HttpRequest.HttpMethod paramHttpMethod, boolean paramBoolean)
  {
    httpMethod_enabled_map.put(paramHttpMethod.toString(), Boolean.valueOf(paramBoolean));
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\lidroid\xutils\http\HttpCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */