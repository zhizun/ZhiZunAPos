package org.apache.commons.lang3.text;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class WordUtils
{
  public static String capitalize(String paramString)
  {
    return capitalize(paramString, null);
  }
  
  public static String capitalize(String paramString, char... paramVarArgs)
  {
    if (paramVarArgs == null) {}
    for (int i = -1; (StringUtils.isEmpty(paramString)) || (i == 0); i = paramVarArgs.length) {
      return paramString;
    }
    paramString = paramString.toCharArray();
    int k = 1;
    int j = 0;
    if (j < paramString.length)
    {
      char c = paramString[j];
      if (isDelimiter(c, paramVarArgs)) {
        i = 1;
      }
      for (;;)
      {
        j += 1;
        k = i;
        break;
        i = k;
        if (k != 0)
        {
          paramString[j] = Character.toTitleCase(c);
          i = 0;
        }
      }
    }
    return new String(paramString);
  }
  
  public static String capitalizeFully(String paramString)
  {
    return capitalizeFully(paramString, null);
  }
  
  public static String capitalizeFully(String paramString, char... paramVarArgs)
  {
    if (paramVarArgs == null) {}
    for (int i = -1; (StringUtils.isEmpty(paramString)) || (i == 0); i = paramVarArgs.length) {
      return paramString;
    }
    return capitalize(paramString.toLowerCase(), paramVarArgs);
  }
  
  public static String initials(String paramString)
  {
    return initials(paramString, null);
  }
  
  public static String initials(String paramString, char... paramVarArgs)
  {
    if (StringUtils.isEmpty(paramString)) {
      return paramString;
    }
    if ((paramVarArgs != null) && (paramVarArgs.length == 0)) {
      return "";
    }
    int n = paramString.length();
    char[] arrayOfChar = new char[n / 2 + 1];
    int j = 1;
    int k = 0;
    int i = 0;
    char c;
    if (k < n)
    {
      c = paramString.charAt(k);
      if (isDelimiter(c, paramVarArgs)) {
        j = 1;
      }
    }
    for (;;)
    {
      k += 1;
      break;
      if (j != 0)
      {
        j = i + 1;
        arrayOfChar[i] = c;
        int m = 0;
        i = j;
        j = m;
        continue;
        return new String(arrayOfChar, 0, i);
      }
    }
  }
  
  private static boolean isDelimiter(char paramChar, char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null) {
      return Character.isWhitespace(paramChar);
    }
    int j = paramArrayOfChar.length;
    int i = 0;
    while (i < j)
    {
      if (paramChar == paramArrayOfChar[i]) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static String swapCase(String paramString)
  {
    if (StringUtils.isEmpty(paramString)) {
      return paramString;
    }
    paramString = paramString.toCharArray();
    boolean bool = true;
    int i = 0;
    if (i < paramString.length)
    {
      char c = paramString[i];
      if (Character.isUpperCase(c))
      {
        paramString[i] = Character.toLowerCase(c);
        bool = false;
      }
      for (;;)
      {
        i += 1;
        break;
        if (Character.isTitleCase(c))
        {
          paramString[i] = Character.toLowerCase(c);
          bool = false;
        }
        else if (Character.isLowerCase(c))
        {
          if (bool)
          {
            paramString[i] = Character.toTitleCase(c);
            bool = false;
          }
          else
          {
            paramString[i] = Character.toUpperCase(c);
          }
        }
        else
        {
          bool = Character.isWhitespace(c);
        }
      }
    }
    return new String(paramString);
  }
  
  public static String uncapitalize(String paramString)
  {
    return uncapitalize(paramString, null);
  }
  
  public static String uncapitalize(String paramString, char... paramVarArgs)
  {
    if (paramVarArgs == null) {}
    for (int i = -1; (StringUtils.isEmpty(paramString)) || (i == 0); i = paramVarArgs.length) {
      return paramString;
    }
    paramString = paramString.toCharArray();
    int k = 1;
    int j = 0;
    if (j < paramString.length)
    {
      char c = paramString[j];
      if (isDelimiter(c, paramVarArgs)) {
        i = 1;
      }
      for (;;)
      {
        j += 1;
        k = i;
        break;
        i = k;
        if (k != 0)
        {
          paramString[j] = Character.toLowerCase(c);
          i = 0;
        }
      }
    }
    return new String(paramString);
  }
  
  public static String wrap(String paramString, int paramInt)
  {
    return wrap(paramString, paramInt, null, false);
  }
  
  public static String wrap(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    if (paramString1 == null) {
      return null;
    }
    String str = paramString2;
    if (paramString2 == null) {
      str = SystemUtils.LINE_SEPARATOR;
    }
    int i = paramInt;
    if (paramInt < 1) {
      i = 1;
    }
    int j = paramString1.length();
    paramInt = 0;
    paramString2 = new StringBuilder(j + 32);
    while (j - paramInt > i) {
      if (paramString1.charAt(paramInt) == ' ')
      {
        paramInt += 1;
      }
      else
      {
        int k = paramString1.lastIndexOf(' ', i + paramInt);
        if (k >= paramInt)
        {
          paramString2.append(paramString1.substring(paramInt, k));
          paramString2.append(str);
          paramInt = k + 1;
        }
        else if (paramBoolean)
        {
          paramString2.append(paramString1.substring(paramInt, i + paramInt));
          paramString2.append(str);
          paramInt += i;
        }
        else
        {
          k = paramString1.indexOf(' ', i + paramInt);
          if (k >= 0)
          {
            paramString2.append(paramString1.substring(paramInt, k));
            paramString2.append(str);
            paramInt = k + 1;
          }
          else
          {
            paramString2.append(paramString1.substring(paramInt));
            paramInt = j;
          }
        }
      }
    }
    paramString2.append(paramString1.substring(paramInt));
    return paramString2.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\WordUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */