package org.apache.commons.lang3.time;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class FormatCache<F extends Format>
{
  static final int NONE = -1;
  private final ConcurrentMap<MultipartKey, String> cDateTimeInstanceCache = new ConcurrentHashMap(7);
  private final ConcurrentMap<MultipartKey, F> cInstanceCache = new ConcurrentHashMap(7);
  
  protected abstract F createInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale);
  
  public F getDateTimeInstance(Integer paramInteger1, Integer paramInteger2, TimeZone paramTimeZone, Locale paramLocale)
  {
    localLocale = paramLocale;
    if (paramLocale == null) {
      localLocale = Locale.getDefault();
    }
    MultipartKey localMultipartKey = new MultipartKey(new Object[] { paramInteger1, paramInteger2, localLocale });
    String str = (String)this.cDateTimeInstanceCache.get(localMultipartKey);
    paramLocale = str;
    if ((str != null) || (paramInteger1 == null)) {}
    for (;;)
    {
      try
      {
        paramInteger1 = DateFormat.getTimeInstance(paramInteger2.intValue(), localLocale);
        paramLocale = ((SimpleDateFormat)paramInteger1).toPattern();
        paramInteger1 = (String)this.cDateTimeInstanceCache.putIfAbsent(localMultipartKey, paramLocale);
        if (paramInteger1 != null) {
          paramLocale = paramInteger1;
        }
        return getInstance(paramLocale, paramTimeZone, localLocale);
      }
      catch (ClassCastException paramInteger1)
      {
        throw new IllegalArgumentException("No date time pattern for locale: " + localLocale);
      }
      if (paramInteger2 == null) {
        paramInteger1 = DateFormat.getDateInstance(paramInteger1.intValue(), localLocale);
      } else {
        paramInteger1 = DateFormat.getDateTimeInstance(paramInteger1.intValue(), paramInteger2.intValue(), localLocale);
      }
    }
  }
  
  public F getInstance()
  {
    return getDateTimeInstance(Integer.valueOf(3), Integer.valueOf(3), TimeZone.getDefault(), Locale.getDefault());
  }
  
  public F getInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale)
  {
    if (paramString == null) {
      throw new NullPointerException("pattern must not be null");
    }
    TimeZone localTimeZone = paramTimeZone;
    if (paramTimeZone == null) {
      localTimeZone = TimeZone.getDefault();
    }
    Locale localLocale = paramLocale;
    if (paramLocale == null) {
      localLocale = Locale.getDefault();
    }
    MultipartKey localMultipartKey = new MultipartKey(new Object[] { paramString, localTimeZone, localLocale });
    paramLocale = (Format)this.cInstanceCache.get(localMultipartKey);
    paramTimeZone = paramLocale;
    if (paramLocale == null)
    {
      paramTimeZone = createInstance(paramString, localTimeZone, localLocale);
      paramString = (Format)this.cInstanceCache.putIfAbsent(localMultipartKey, paramTimeZone);
      if (paramString != null) {
        paramTimeZone = paramString;
      }
    }
    return paramTimeZone;
  }
  
  private static class MultipartKey
  {
    private int hashCode;
    private final Object[] keys;
    
    public MultipartKey(Object... paramVarArgs)
    {
      this.keys = paramVarArgs;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof MultipartKey)) {
        return false;
      }
      return Arrays.equals(this.keys, ((MultipartKey)paramObject).keys);
    }
    
    public int hashCode()
    {
      if (this.hashCode == 0)
      {
        int j = 0;
        Object[] arrayOfObject = this.keys;
        int m = arrayOfObject.length;
        int i = 0;
        while (i < m)
        {
          Object localObject = arrayOfObject[i];
          int k = j;
          if (localObject != null) {
            k = j * 7 + localObject.hashCode();
          }
          i += 1;
          j = k;
        }
        this.hashCode = j;
      }
      return this.hashCode;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\time\FormatCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */