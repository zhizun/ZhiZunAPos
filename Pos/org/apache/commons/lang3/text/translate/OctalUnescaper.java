package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

public class OctalUnescaper
  extends CharSequenceTranslator
{
  private static int OCTAL_MAX = 377;
  
  public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
    throws IOException
  {
    if ((paramCharSequence.charAt(paramInt) == '\\') && (paramInt < paramCharSequence.length() - 1) && (Character.isDigit(paramCharSequence.charAt(paramInt + 1))))
    {
      int j = paramInt + 1;
      paramInt += 2;
      int i;
      do
      {
        i = paramInt;
        if (paramInt >= paramCharSequence.length()) {
          break;
        }
        i = paramInt;
        if (!Character.isDigit(paramCharSequence.charAt(paramInt))) {
          break;
        }
        i = paramInt + 1;
        paramInt = i;
      } while (Integer.parseInt(paramCharSequence.subSequence(j, i).toString(), 10) <= OCTAL_MAX);
      i -= 1;
      paramWriter.write(Integer.parseInt(paramCharSequence.subSequence(j, i).toString(), 8));
      return i + 1 - j;
    }
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\OctalUnescaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */