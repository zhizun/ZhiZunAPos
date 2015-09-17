package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.ArrayUtils;

public class AggregateTranslator
  extends CharSequenceTranslator
{
  private final CharSequenceTranslator[] translators;
  
  public AggregateTranslator(CharSequenceTranslator... paramVarArgs)
  {
    this.translators = ((CharSequenceTranslator[])ArrayUtils.clone(paramVarArgs));
  }
  
  public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
    throws IOException
  {
    CharSequenceTranslator[] arrayOfCharSequenceTranslator = this.translators;
    int j = arrayOfCharSequenceTranslator.length;
    int i = 0;
    while (i < j)
    {
      int k = arrayOfCharSequenceTranslator[i].translate(paramCharSequence, paramInt, paramWriter);
      if (k != 0) {
        return k;
      }
      i += 1;
    }
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\AggregateTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */