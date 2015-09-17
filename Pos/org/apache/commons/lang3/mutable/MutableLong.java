package org.apache.commons.lang3.mutable;

public class MutableLong
  extends Number
  implements Comparable<MutableLong>, Mutable<Number>
{
  private static final long serialVersionUID = 62986528375L;
  private long value;
  
  public MutableLong() {}
  
  public MutableLong(long paramLong)
  {
    this.value = paramLong;
  }
  
  public MutableLong(Number paramNumber)
  {
    this.value = paramNumber.longValue();
  }
  
  public MutableLong(String paramString)
    throws NumberFormatException
  {
    this.value = Long.parseLong(paramString);
  }
  
  public void add(long paramLong)
  {
    this.value += paramLong;
  }
  
  public void add(Number paramNumber)
  {
    this.value += paramNumber.longValue();
  }
  
  public int compareTo(MutableLong paramMutableLong)
  {
    long l = paramMutableLong.value;
    if (this.value < l) {
      return -1;
    }
    if (this.value == l) {
      return 0;
    }
    return 1;
  }
  
  public void decrement()
  {
    this.value -= 1L;
  }
  
  public double doubleValue()
  {
    return this.value;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((paramObject instanceof MutableLong))
    {
      bool1 = bool2;
      if (this.value == ((MutableLong)paramObject).longValue()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public float floatValue()
  {
    return (float)this.value;
  }
  
  public Long getValue()
  {
    return Long.valueOf(this.value);
  }
  
  public int hashCode()
  {
    return (int)(this.value ^ this.value >>> 32);
  }
  
  public void increment()
  {
    this.value += 1L;
  }
  
  public int intValue()
  {
    return (int)this.value;
  }
  
  public long longValue()
  {
    return this.value;
  }
  
  public void setValue(long paramLong)
  {
    this.value = paramLong;
  }
  
  public void setValue(Number paramNumber)
  {
    this.value = paramNumber.longValue();
  }
  
  public void subtract(long paramLong)
  {
    this.value -= paramLong;
  }
  
  public void subtract(Number paramNumber)
  {
    this.value -= paramNumber.longValue();
  }
  
  public Long toLong()
  {
    return Long.valueOf(longValue());
  }
  
  public String toString()
  {
    return String.valueOf(this.value);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\mutable\MutableLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */