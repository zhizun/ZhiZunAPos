package org.apache.commons.lang3.text;

import java.util.Formattable;
import java.util.Formatter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

public class FormattableUtils
{
  private static final String SIMPLEST_FORMAT = "%s";
  
  public static Formatter append(CharSequence paramCharSequence, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3)
  {
    return append(paramCharSequence, paramFormatter, paramInt1, paramInt2, paramInt3, ' ', null);
  }
  
  public static Formatter append(CharSequence paramCharSequence, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, char paramChar)
  {
    return append(paramCharSequence, paramFormatter, paramInt1, paramInt2, paramInt3, paramChar, null);
  }
  
  public static Formatter append(CharSequence paramCharSequence1, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, char paramChar, CharSequence paramCharSequence2)
  {
    boolean bool;
    StringBuilder localStringBuilder;
    if ((paramCharSequence2 == null) || (paramInt3 < 0) || (paramCharSequence2.length() <= paramInt3))
    {
      bool = true;
      Validate.isTrue(bool, "Specified ellipsis '%1$s' exceeds precision of %2$s", new Object[] { paramCharSequence2, Integer.valueOf(paramInt3) });
      localStringBuilder = new StringBuilder(paramCharSequence1);
      if ((paramInt3 >= 0) && (paramInt3 < paramCharSequence1.length()))
      {
        paramCharSequence2 = (CharSequence)ObjectUtils.defaultIfNull(paramCharSequence2, "");
        localStringBuilder.replace(paramInt3 - paramCharSequence2.length(), paramCharSequence1.length(), paramCharSequence2.toString());
      }
      if ((paramInt1 & 0x1) != 1) {
        break label166;
      }
      paramInt3 = 1;
      label124:
      paramInt1 = localStringBuilder.length();
      label130:
      if (paramInt1 >= paramInt2) {
        break label178;
      }
      if (paramInt3 == 0) {
        break label172;
      }
    }
    label166:
    label172:
    for (int i = paramInt1;; i = 0)
    {
      localStringBuilder.insert(i, paramChar);
      paramInt1 += 1;
      break label130;
      bool = false;
      break;
      paramInt3 = 0;
      break label124;
    }
    label178:
    paramFormatter.format(localStringBuilder.toString(), new Object[0]);
    return paramFormatter;
  }
  
  public static Formatter append(CharSequence paramCharSequence1, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence2)
  {
    return append(paramCharSequence1, paramFormatter, paramInt1, paramInt2, paramInt3, ' ', paramCharSequence2);
  }
  
  public static String toString(Formattable paramFormattable)
  {
    return String.format("%s", new Object[] { paramFormattable });
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\FormattableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */