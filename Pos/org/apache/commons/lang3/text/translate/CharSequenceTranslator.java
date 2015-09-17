package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

public abstract class CharSequenceTranslator
{
  public static String hex(int paramInt)
  {
    return Integer.toHexString(paramInt).toUpperCase(Locale.ENGLISH);
  }
  
  public abstract int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
    throws IOException;
  
  public final String translate(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {
      return null;
    }
    try
    {
      StringWriter localStringWriter = new StringWriter(paramCharSequence.length() * 2);
      translate(paramCharSequence, localStringWriter);
      paramCharSequence = localStringWriter.toString();
      return paramCharSequence;
    }
    catch (IOException paramCharSequence)
    {
      throw new RuntimeException(paramCharSequence);
    }
  }
  
  public final void translate(CharSequence paramCharSequence, Writer paramWriter)
    throws IOException
  {
    if (paramWriter == null) {
      throw new IllegalArgumentException("The Writer must not be null");
    }
    if (paramCharSequence == null) {
      return;
    }
    int i;
    int n;
    for (;;)
    {
      i = 0;
      int m = paramCharSequence.length();
      while (i < m)
      {
        n = translate(paramCharSequence, i, paramWriter);
        if (n != 0) {
          break label74;
        }
        char[] arrayOfChar = Character.toChars(Character.codePointAt(paramCharSequence, i));
        paramWriter.write(arrayOfChar);
        i += arrayOfChar.length;
      }
    }
    label74:
    int k = 0;
    int j = i;
    for (;;)
    {
      i = j;
      if (k >= n) {
        break;
      }
      j += Character.charCount(Character.codePointAt(paramCharSequence, j));
      k += 1;
    }
  }
  
  public final CharSequenceTranslator with(CharSequenceTranslator... paramVarArgs)
  {
    CharSequenceTranslator[] arrayOfCharSequenceTranslator = new CharSequenceTranslator[paramVarArgs.length + 1];
    arrayOfCharSequenceTranslator[0] = this;
    System.arraycopy(paramVarArgs, 0, arrayOfCharSequenceTranslator, 1, paramVarArgs.length);
    return new AggregateTranslator(arrayOfCharSequenceTranslator);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\CharSequenceTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */