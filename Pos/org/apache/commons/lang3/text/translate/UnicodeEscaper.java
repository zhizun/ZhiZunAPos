package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

public class UnicodeEscaper
  extends CodePointTranslator
{
  private final int above;
  private final int below;
  private final boolean between;
  
  public UnicodeEscaper()
  {
    this(0, Integer.MAX_VALUE, true);
  }
  
  private UnicodeEscaper(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.below = paramInt1;
    this.above = paramInt2;
    this.between = paramBoolean;
  }
  
  public static UnicodeEscaper above(int paramInt)
  {
    return outsideOf(0, paramInt);
  }
  
  public static UnicodeEscaper below(int paramInt)
  {
    return outsideOf(paramInt, Integer.MAX_VALUE);
  }
  
  public static UnicodeEscaper between(int paramInt1, int paramInt2)
  {
    return new UnicodeEscaper(paramInt1, paramInt2, true);
  }
  
  public static UnicodeEscaper outsideOf(int paramInt1, int paramInt2)
  {
    return new UnicodeEscaper(paramInt1, paramInt2, false);
  }
  
  public boolean translate(int paramInt, Writer paramWriter)
    throws IOException
  {
    if (this.between)
    {
      if ((paramInt >= this.below) && (paramInt <= this.above)) {}
    }
    else {
      while ((paramInt >= this.below) && (paramInt <= this.above)) {
        return false;
      }
    }
    if (paramInt > 65535) {
      paramWriter.write("\\u" + hex(paramInt));
    }
    for (;;)
    {
      return true;
      if (paramInt > 4095) {
        paramWriter.write("\\u" + hex(paramInt));
      } else if (paramInt > 255) {
        paramWriter.write("\\u0" + hex(paramInt));
      } else if (paramInt > 15) {
        paramWriter.write("\\u00" + hex(paramInt));
      } else {
        paramWriter.write("\\u000" + hex(paramInt));
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\UnicodeEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */