package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

public abstract class CodePointTranslator
  extends CharSequenceTranslator
{
  public final int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
    throws IOException
  {
    if (translate(Character.codePointAt(paramCharSequence, paramInt), paramWriter)) {
      return 1;
    }
    return 0;
  }
  
  public abstract boolean translate(int paramInt, Writer paramWriter)
    throws IOException;
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\CodePointTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */