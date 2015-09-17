package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StrSubstitutor
{
  public static final char DEFAULT_ESCAPE = '$';
  public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
  public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
  private boolean enableSubstitutionInVariables;
  private char escapeChar;
  private StrMatcher prefixMatcher;
  private StrMatcher suffixMatcher;
  private StrLookup<?> variableResolver;
  
  public StrSubstitutor()
  {
    this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
  }
  
  public <V> StrSubstitutor(Map<String, V> paramMap)
  {
    this(StrLookup.mapLookup(paramMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
  }
  
  public <V> StrSubstitutor(Map<String, V> paramMap, String paramString1, String paramString2)
  {
    this(StrLookup.mapLookup(paramMap), paramString1, paramString2, '$');
  }
  
  public <V> StrSubstitutor(Map<String, V> paramMap, String paramString1, String paramString2, char paramChar)
  {
    this(StrLookup.mapLookup(paramMap), paramString1, paramString2, paramChar);
  }
  
  public StrSubstitutor(StrLookup<?> paramStrLookup)
  {
    this(paramStrLookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
  }
  
  public StrSubstitutor(StrLookup<?> paramStrLookup, String paramString1, String paramString2, char paramChar)
  {
    setVariableResolver(paramStrLookup);
    setVariablePrefix(paramString1);
    setVariableSuffix(paramString2);
    setEscapeChar(paramChar);
  }
  
  public StrSubstitutor(StrLookup<?> paramStrLookup, StrMatcher paramStrMatcher1, StrMatcher paramStrMatcher2, char paramChar)
  {
    setVariableResolver(paramStrLookup);
    setVariablePrefixMatcher(paramStrMatcher1);
    setVariableSuffixMatcher(paramStrMatcher2);
    setEscapeChar(paramChar);
  }
  
  private void checkCyclicSubstitution(String paramString, List<String> paramList)
  {
    if (!paramList.contains(paramString)) {
      return;
    }
    paramString = new StrBuilder(256);
    paramString.append("Infinite loop in property interpolation of ");
    paramString.append((String)paramList.remove(0));
    paramString.append(": ");
    paramString.appendWithSeparators(paramList, "->");
    throw new IllegalStateException(paramString.toString());
  }
  
  public static <V> String replace(Object paramObject, Map<String, V> paramMap)
  {
    return new StrSubstitutor(paramMap).replace(paramObject);
  }
  
  public static <V> String replace(Object paramObject, Map<String, V> paramMap, String paramString1, String paramString2)
  {
    return new StrSubstitutor(paramMap, paramString1, paramString2).replace(paramObject);
  }
  
  public static String replace(Object paramObject, Properties paramProperties)
  {
    if (paramProperties == null) {
      return paramObject.toString();
    }
    HashMap localHashMap = new HashMap();
    Enumeration localEnumeration = paramProperties.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      localHashMap.put(str, paramProperties.getProperty(str));
    }
    return replace(paramObject, localHashMap);
  }
  
  public static String replaceSystemProperties(Object paramObject)
  {
    return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(paramObject);
  }
  
  private int substitute(StrBuilder paramStrBuilder, int paramInt1, int paramInt2, List<String> paramList)
  {
    StrMatcher localStrMatcher1 = getVariablePrefixMatcher();
    StrMatcher localStrMatcher2 = getVariableSuffixMatcher();
    int i5 = getEscapeChar();
    int i2;
    int n;
    int m;
    Object localObject2;
    int k;
    int j;
    Object localObject1;
    if (paramList == null)
    {
      i2 = 1;
      n = 0;
      m = 0;
      localObject2 = paramStrBuilder.buffer;
      k = paramInt1 + paramInt2;
      j = paramInt1;
      localObject1 = paramList;
      paramList = (List<String>)localObject2;
    }
    int i3;
    int i4;
    for (;;)
    {
      i3 = j;
      if (i3 >= k) {
        break label526;
      }
      i4 = localStrMatcher1.isMatch(paramList, i3, paramInt1, k);
      if (i4 == 0)
      {
        j = i3 + 1;
        continue;
        i2 = 0;
        break;
      }
      if ((i3 <= paramInt1) || (paramList[(i3 - 1)] != i5)) {
        break label154;
      }
      paramStrBuilder.deleteCharAt(i3 - 1);
      paramList = paramStrBuilder.buffer;
      m -= 1;
      n = 1;
      k -= 1;
      j = i3;
    }
    label154:
    int i = i3 + i4;
    int i1 = 0;
    for (;;)
    {
      j = i;
      if (i >= k) {
        break;
      }
      if (isEnableSubstitutionInVariables())
      {
        j = localStrMatcher1.isMatch(paramList, i, paramInt1, k);
        if (j != 0)
        {
          i1 += 1;
          i += j;
          continue;
        }
      }
      j = localStrMatcher2.isMatch(paramList, i, paramInt1, k);
      if (j == 0)
      {
        i += 1;
      }
      else
      {
        if (i1 == 0)
        {
          localObject2 = new String(paramList, i3 + i4, i - i3 - i4);
          Object localObject3 = localObject2;
          if (isEnableSubstitutionInVariables())
          {
            localObject2 = new StrBuilder((String)localObject2);
            substitute((StrBuilder)localObject2, 0, ((StrBuilder)localObject2).length());
            localObject3 = ((StrBuilder)localObject2).toString();
          }
          i4 = i + j;
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = new ArrayList();
            ((List)localObject2).add(new String(paramList, paramInt1, paramInt2));
          }
          checkCyclicSubstitution((String)localObject3, (List)localObject2);
          ((List)localObject2).add(localObject3);
          localObject1 = resolveVariable((String)localObject3, paramStrBuilder, i3, i4);
          i1 = k;
          i = m;
          j = i4;
          if (localObject1 != null)
          {
            i = ((String)localObject1).length();
            paramStrBuilder.replace(i3, i4, (String)localObject1);
            n = 1;
            i = substitute(paramStrBuilder, i3, i, (List)localObject2) + i - (i4 - i3);
            j = i4 + i;
            i1 = k + i;
            i = m + i;
            paramList = paramStrBuilder.buffer;
          }
          ((List)localObject2).remove(((List)localObject2).size() - 1);
          k = i1;
          m = i;
          localObject1 = localObject2;
          break;
        }
        i1 -= 1;
        i += j;
      }
    }
    label526:
    if (i2 != 0)
    {
      if (n != 0) {
        return 1;
      }
      return 0;
    }
    return m;
  }
  
  public char getEscapeChar()
  {
    return this.escapeChar;
  }
  
  public StrMatcher getVariablePrefixMatcher()
  {
    return this.prefixMatcher;
  }
  
  public StrLookup<?> getVariableResolver()
  {
    return this.variableResolver;
  }
  
  public StrMatcher getVariableSuffixMatcher()
  {
    return this.suffixMatcher;
  }
  
  public boolean isEnableSubstitutionInVariables()
  {
    return this.enableSubstitutionInVariables;
  }
  
  public String replace(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    paramObject = new StrBuilder().append(paramObject);
    substitute((StrBuilder)paramObject, 0, ((StrBuilder)paramObject).length());
    return ((StrBuilder)paramObject).toString();
  }
  
  public String replace(String paramString)
  {
    String str;
    if (paramString == null) {
      str = null;
    }
    StrBuilder localStrBuilder;
    do
    {
      return str;
      localStrBuilder = new StrBuilder(paramString);
      str = paramString;
    } while (!substitute(localStrBuilder, 0, paramString.length()));
    return localStrBuilder.toString();
  }
  
  public String replace(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == null) {
      return null;
    }
    StrBuilder localStrBuilder = new StrBuilder(paramInt2).append(paramString, paramInt1, paramInt2);
    if (!substitute(localStrBuilder, 0, paramInt2)) {
      return paramString.substring(paramInt1, paramInt1 + paramInt2);
    }
    return localStrBuilder.toString();
  }
  
  public String replace(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer == null) {
      return null;
    }
    paramStringBuffer = new StrBuilder(paramStringBuffer.length()).append(paramStringBuffer);
    substitute(paramStringBuffer, 0, paramStringBuffer.length());
    return paramStringBuffer.toString();
  }
  
  public String replace(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
  {
    if (paramStringBuffer == null) {
      return null;
    }
    paramStringBuffer = new StrBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
    substitute(paramStringBuffer, 0, paramInt2);
    return paramStringBuffer.toString();
  }
  
  public String replace(StrBuilder paramStrBuilder)
  {
    if (paramStrBuilder == null) {
      return null;
    }
    paramStrBuilder = new StrBuilder(paramStrBuilder.length()).append(paramStrBuilder);
    substitute(paramStrBuilder, 0, paramStrBuilder.length());
    return paramStrBuilder.toString();
  }
  
  public String replace(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
  {
    if (paramStrBuilder == null) {
      return null;
    }
    paramStrBuilder = new StrBuilder(paramInt2).append(paramStrBuilder, paramInt1, paramInt2);
    substitute(paramStrBuilder, 0, paramInt2);
    return paramStrBuilder.toString();
  }
  
  public String replace(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null) {
      return null;
    }
    StrBuilder localStrBuilder = new StrBuilder(paramArrayOfChar.length).append(paramArrayOfChar);
    substitute(localStrBuilder, 0, paramArrayOfChar.length);
    return localStrBuilder.toString();
  }
  
  public String replace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramArrayOfChar == null) {
      return null;
    }
    paramArrayOfChar = new StrBuilder(paramInt2).append(paramArrayOfChar, paramInt1, paramInt2);
    substitute(paramArrayOfChar, 0, paramInt2);
    return paramArrayOfChar.toString();
  }
  
  public boolean replaceIn(StringBuffer paramStringBuffer)
  {
    if (paramStringBuffer == null) {
      return false;
    }
    return replaceIn(paramStringBuffer, 0, paramStringBuffer.length());
  }
  
  public boolean replaceIn(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
  {
    if (paramStringBuffer == null) {}
    StrBuilder localStrBuilder;
    do
    {
      return false;
      localStrBuilder = new StrBuilder(paramInt2).append(paramStringBuffer, paramInt1, paramInt2);
    } while (!substitute(localStrBuilder, 0, paramInt2));
    paramStringBuffer.replace(paramInt1, paramInt1 + paramInt2, localStrBuilder.toString());
    return true;
  }
  
  public boolean replaceIn(StrBuilder paramStrBuilder)
  {
    if (paramStrBuilder == null) {
      return false;
    }
    return substitute(paramStrBuilder, 0, paramStrBuilder.length());
  }
  
  public boolean replaceIn(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
  {
    if (paramStrBuilder == null) {
      return false;
    }
    return substitute(paramStrBuilder, paramInt1, paramInt2);
  }
  
  protected String resolveVariable(String paramString, StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
  {
    paramStrBuilder = getVariableResolver();
    if (paramStrBuilder == null) {
      return null;
    }
    return paramStrBuilder.lookup(paramString);
  }
  
  public void setEnableSubstitutionInVariables(boolean paramBoolean)
  {
    this.enableSubstitutionInVariables = paramBoolean;
  }
  
  public void setEscapeChar(char paramChar)
  {
    this.escapeChar = paramChar;
  }
  
  public StrSubstitutor setVariablePrefix(char paramChar)
  {
    return setVariablePrefixMatcher(StrMatcher.charMatcher(paramChar));
  }
  
  public StrSubstitutor setVariablePrefix(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Variable prefix must not be null!");
    }
    return setVariablePrefixMatcher(StrMatcher.stringMatcher(paramString));
  }
  
  public StrSubstitutor setVariablePrefixMatcher(StrMatcher paramStrMatcher)
  {
    if (paramStrMatcher == null) {
      throw new IllegalArgumentException("Variable prefix matcher must not be null!");
    }
    this.prefixMatcher = paramStrMatcher;
    return this;
  }
  
  public void setVariableResolver(StrLookup<?> paramStrLookup)
  {
    this.variableResolver = paramStrLookup;
  }
  
  public StrSubstitutor setVariableSuffix(char paramChar)
  {
    return setVariableSuffixMatcher(StrMatcher.charMatcher(paramChar));
  }
  
  public StrSubstitutor setVariableSuffix(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Variable suffix must not be null!");
    }
    return setVariableSuffixMatcher(StrMatcher.stringMatcher(paramString));
  }
  
  public StrSubstitutor setVariableSuffixMatcher(StrMatcher paramStrMatcher)
  {
    if (paramStrMatcher == null) {
      throw new IllegalArgumentException("Variable suffix matcher must not be null!");
    }
    this.suffixMatcher = paramStrMatcher;
    return this;
  }
  
  protected boolean substitute(StrBuilder paramStrBuilder, int paramInt1, int paramInt2)
  {
    return substitute(paramStrBuilder, paramInt1, paramInt2, null) > 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\StrSubstitutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */