package org.apache.commons.lang3.text;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

public class ExtendedMessageFormat
  extends MessageFormat
{
  private static final String DUMMY_PATTERN = "";
  private static final char END_FE = '}';
  private static final String ESCAPED_QUOTE = "''";
  private static final int HASH_SEED = 31;
  private static final char QUOTE = '\'';
  private static final char START_FE = '{';
  private static final char START_FMT = ',';
  private static final long serialVersionUID = -2362048321261811743L;
  private final Map<String, ? extends FormatFactory> registry;
  private String toPattern;
  
  public ExtendedMessageFormat(String paramString)
  {
    this(paramString, Locale.getDefault());
  }
  
  public ExtendedMessageFormat(String paramString, Locale paramLocale)
  {
    this(paramString, paramLocale, null);
  }
  
  public ExtendedMessageFormat(String paramString, Locale paramLocale, Map<String, ? extends FormatFactory> paramMap)
  {
    super("");
    setLocale(paramLocale);
    this.registry = paramMap;
    applyPattern(paramString);
  }
  
  public ExtendedMessageFormat(String paramString, Map<String, ? extends FormatFactory> paramMap)
  {
    this(paramString, Locale.getDefault(), paramMap);
  }
  
  private StringBuilder appendQuotedString(String paramString, ParsePosition paramParsePosition, StringBuilder paramStringBuilder, boolean paramBoolean)
  {
    int k = paramParsePosition.getIndex();
    char[] arrayOfChar = paramString.toCharArray();
    if ((paramBoolean) && (arrayOfChar[k] == '\''))
    {
      next(paramParsePosition);
      if (paramStringBuilder != null) {}
    }
    int j;
    do
    {
      return null;
      return paramStringBuilder.append('\'');
      j = k;
      int i = paramParsePosition.getIndex();
      if (i >= paramString.length()) {
        break;
      }
      if ((paramBoolean) && (paramString.substring(i).startsWith("''")))
      {
        paramStringBuilder.append(arrayOfChar, j, paramParsePosition.getIndex() - j).append('\'');
        paramParsePosition.setIndex("''".length() + i);
        j = paramParsePosition.getIndex();
      }
      for (;;)
      {
        i += 1;
        break;
        switch (arrayOfChar[paramParsePosition.getIndex()])
        {
        default: 
          next(paramParsePosition);
        }
      }
      next(paramParsePosition);
    } while (paramStringBuilder == null);
    return paramStringBuilder.append(arrayOfChar, j, paramParsePosition.getIndex() - j);
    throw new IllegalArgumentException("Unterminated quoted string at position " + k);
  }
  
  private boolean containsElements(Collection<?> paramCollection)
  {
    if ((paramCollection == null) || (paramCollection.isEmpty())) {}
    do
    {
      while (!paramCollection.hasNext())
      {
        return false;
        paramCollection = paramCollection.iterator();
      }
    } while (paramCollection.next() == null);
    return true;
  }
  
  private Format getFormat(String paramString)
  {
    if (this.registry != null)
    {
      String str2 = paramString;
      String str1 = null;
      int i = paramString.indexOf(',');
      if (i > 0)
      {
        str2 = paramString.substring(0, i).trim();
        str1 = paramString.substring(i + 1).trim();
      }
      paramString = (FormatFactory)this.registry.get(str2);
      if (paramString != null) {
        return paramString.getFormat(str2, str1, getLocale());
      }
    }
    return null;
  }
  
  private void getQuotedString(String paramString, ParsePosition paramParsePosition, boolean paramBoolean)
  {
    appendQuotedString(paramString, paramParsePosition, null, paramBoolean);
  }
  
  private String insertFormats(String paramString, ArrayList<String> paramArrayList)
  {
    if (!containsElements(paramArrayList)) {
      return paramString;
    }
    StringBuilder localStringBuilder = new StringBuilder(paramString.length() * 2);
    ParsePosition localParsePosition = new ParsePosition(0);
    int j = -1;
    int i = 0;
    if (localParsePosition.getIndex() < paramString.length())
    {
      char c = paramString.charAt(localParsePosition.getIndex());
      switch (c)
      {
      }
      for (;;)
      {
        localStringBuilder.append(c);
        next(localParsePosition);
        break;
        appendQuotedString(paramString, localParsePosition, localStringBuilder, false);
        break;
        int k = i + 1;
        i = k;
        if (k != 1) {
          break;
        }
        int m = j + 1;
        localStringBuilder.append('{').append(readArgumentIndex(paramString, next(localParsePosition)));
        String str = (String)paramArrayList.get(m);
        i = k;
        j = m;
        if (str == null) {
          break;
        }
        localStringBuilder.append(',').append(str);
        i = k;
        j = m;
        break;
        i -= 1;
      }
    }
    return localStringBuilder.toString();
  }
  
  private ParsePosition next(ParsePosition paramParsePosition)
  {
    paramParsePosition.setIndex(paramParsePosition.getIndex() + 1);
    return paramParsePosition;
  }
  
  private String parseFormatDescription(String paramString, ParsePosition paramParsePosition)
  {
    int k = paramParsePosition.getIndex();
    seekNonWs(paramString, paramParsePosition);
    int m = paramParsePosition.getIndex();
    int i = 1;
    if (paramParsePosition.getIndex() < paramString.length())
    {
      switch (paramString.charAt(paramParsePosition.getIndex()))
      {
      }
      for (;;)
      {
        next(paramParsePosition);
        break;
        i += 1;
        continue;
        int j = i - 1;
        i = j;
        if (j == 0)
        {
          return paramString.substring(m, paramParsePosition.getIndex());
          getQuotedString(paramString, paramParsePosition, false);
        }
      }
    }
    throw new IllegalArgumentException("Unterminated format element at position " + k);
  }
  
  private int readArgumentIndex(String paramString, ParsePosition paramParsePosition)
  {
    int j = paramParsePosition.getIndex();
    seekNonWs(paramString, paramParsePosition);
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while ((i == 0) && (paramParsePosition.getIndex() < paramString.length()))
    {
      char c2 = paramString.charAt(paramParsePosition.getIndex());
      char c1 = c2;
      if (Character.isWhitespace(c2))
      {
        seekNonWs(paramString, paramParsePosition);
        c2 = paramString.charAt(paramParsePosition.getIndex());
        c1 = c2;
        if (c2 != ',')
        {
          c1 = c2;
          if (c2 != '}')
          {
            i = 1;
            next(paramParsePosition);
            continue;
          }
        }
      }
      if (((c1 == ',') || (c1 == '}')) && (localStringBuffer.length() > 0)) {
        try
        {
          i = Integer.parseInt(localStringBuffer.toString());
          return i;
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      if (!Character.isDigit(c1)) {}
      for (i = 1;; i = 0)
      {
        localStringBuffer.append(c1);
        break;
      }
    }
    if (i != 0) {
      throw new IllegalArgumentException("Invalid format argument index at position " + j + ": " + paramString.substring(j, paramParsePosition.getIndex()));
    }
    throw new IllegalArgumentException("Unterminated format element at position " + j);
  }
  
  private void seekNonWs(String paramString, ParsePosition paramParsePosition)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i;
    do
    {
      i = StrMatcher.splitMatcher().isMatch(arrayOfChar, paramParsePosition.getIndex());
      paramParsePosition.setIndex(paramParsePosition.getIndex() + i);
    } while ((i > 0) && (paramParsePosition.getIndex() < paramString.length()));
  }
  
  public final void applyPattern(String paramString)
  {
    if (this.registry == null)
    {
      super.applyPattern(paramString);
      this.toPattern = super.toPattern();
    }
    ArrayList localArrayList1;
    Object localObject2;
    label314:
    label383:
    do
    {
      return;
      localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      StringBuilder localStringBuilder = new StringBuilder(paramString.length());
      ParsePosition localParsePosition = new ParsePosition(0);
      char[] arrayOfChar = paramString.toCharArray();
      i = 0;
      while (localParsePosition.getIndex() < paramString.length()) {
        switch (arrayOfChar[localParsePosition.getIndex()])
        {
        default: 
          localStringBuilder.append(arrayOfChar[localParsePosition.getIndex()]);
          next(localParsePosition);
          break;
        case '\'': 
          appendQuotedString(paramString, localParsePosition, localStringBuilder, true);
          break;
        case '{': 
          i += 1;
          seekNonWs(paramString, localParsePosition);
          int j = localParsePosition.getIndex();
          int k = readArgumentIndex(paramString, next(localParsePosition));
          localStringBuilder.append('{').append(k);
          seekNonWs(paramString, localParsePosition);
          localObject2 = null;
          localObject1 = null;
          if (arrayOfChar[localParsePosition.getIndex()] == ',')
          {
            String str = parseFormatDescription(paramString, next(localParsePosition));
            Format localFormat = getFormat(str);
            localObject2 = localFormat;
            localObject1 = str;
            if (localFormat == null)
            {
              localStringBuilder.append(',').append(str);
              localObject1 = str;
              localObject2 = localFormat;
            }
          }
          localArrayList1.add(localObject2);
          if (localObject2 == null) {
            localObject1 = null;
          }
          localArrayList2.add(localObject1);
          if (localArrayList1.size() == i)
          {
            bool = true;
            Validate.isTrue(bool);
            if (localArrayList2.size() != i) {
              break label383;
            }
          }
          for (boolean bool = true;; bool = false)
          {
            Validate.isTrue(bool);
            if (arrayOfChar[localParsePosition.getIndex()] == '}') {
              break;
            }
            throw new IllegalArgumentException("Unreadable format element at position " + j);
            bool = false;
            break label314;
          }
        }
      }
      super.applyPattern(localStringBuilder.toString());
      this.toPattern = insertFormats(super.toPattern(), localArrayList2);
    } while (!containsElements(localArrayList1));
    paramString = getFormats();
    int i = 0;
    Object localObject1 = localArrayList1.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Format)((Iterator)localObject1).next();
      if (localObject2 != null) {
        paramString[i] = localObject2;
      }
      i += 1;
    }
    super.setFormats(paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!super.equals(paramObject)) {
        return false;
      }
      if (ObjectUtils.notEqual(getClass(), paramObject.getClass())) {
        return false;
      }
      paramObject = (ExtendedMessageFormat)paramObject;
      if (ObjectUtils.notEqual(this.toPattern, ((ExtendedMessageFormat)paramObject).toPattern)) {
        return false;
      }
    } while (!ObjectUtils.notEqual(this.registry, ((ExtendedMessageFormat)paramObject).registry));
    return false;
  }
  
  public int hashCode()
  {
    return (super.hashCode() * 31 + ObjectUtils.hashCode(this.registry)) * 31 + ObjectUtils.hashCode(this.toPattern);
  }
  
  public void setFormat(int paramInt, Format paramFormat)
  {
    throw new UnsupportedOperationException();
  }
  
  public void setFormatByArgumentIndex(int paramInt, Format paramFormat)
  {
    throw new UnsupportedOperationException();
  }
  
  public void setFormats(Format[] paramArrayOfFormat)
  {
    throw new UnsupportedOperationException();
  }
  
  public void setFormatsByArgumentIndex(Format[] paramArrayOfFormat)
  {
    throw new UnsupportedOperationException();
  }
  
  public String toPattern()
  {
    return this.toPattern;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\ExtendedMessageFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */