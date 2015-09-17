package org.apache.commons.lang3;

public class CharSetUtils
{
  public static int count(String paramString, String... paramVarArgs)
  {
    int k;
    if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs)))
    {
      k = 0;
      return k;
    }
    paramVarArgs = CharSet.getInstance(paramVarArgs);
    int i = 0;
    paramString = paramString.toCharArray();
    int m = paramString.length;
    int j = 0;
    for (;;)
    {
      k = i;
      if (j >= m) {
        break;
      }
      k = i;
      if (paramVarArgs.contains(paramString[j])) {
        k = i + 1;
      }
      j += 1;
      i = k;
    }
  }
  
  private static boolean deepEmpty(String[] paramArrayOfString)
  {
    if (paramArrayOfString != null)
    {
      int j = paramArrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (StringUtils.isNotEmpty(paramArrayOfString[i])) {
          return false;
        }
        i += 1;
      }
    }
    return true;
  }
  
  public static String delete(String paramString, String... paramVarArgs)
  {
    if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
      return paramString;
    }
    return modify(paramString, paramVarArgs, false);
  }
  
  public static String keep(String paramString, String... paramVarArgs)
  {
    if (paramString == null) {
      return null;
    }
    if ((paramString.length() == 0) || (deepEmpty(paramVarArgs))) {
      return "";
    }
    return modify(paramString, paramVarArgs, true);
  }
  
  private static String modify(String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    paramArrayOfString = CharSet.getInstance(paramArrayOfString);
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    paramString = paramString.toCharArray();
    int j = paramString.length;
    int i = 0;
    while (i < j)
    {
      if (paramArrayOfString.contains(paramString[i]) == paramBoolean) {
        localStringBuilder.append(paramString[i]);
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public static String squeeze(String paramString, String... paramVarArgs)
  {
    if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
      return paramString;
    }
    paramVarArgs = CharSet.getInstance(paramVarArgs);
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    paramString = paramString.toCharArray();
    int k = paramString.length;
    int j = 32;
    int i = 0;
    if (i < k)
    {
      char c = paramString[i];
      if ((c == j) && (i != 0) && (paramVarArgs.contains(c))) {}
      for (;;)
      {
        i += 1;
        break;
        localStringBuilder.append(c);
        j = c;
      }
    }
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\CharSetUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */