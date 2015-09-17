package org.apache.commons.lang3.mutable;

import java.io.Serializable;

public class MutableBoolean
  implements Mutable<Boolean>, Serializable, Comparable<MutableBoolean>
{
  private static final long serialVersionUID = -4830728138360036487L;
  private boolean value;
  
  public MutableBoolean() {}
  
  public MutableBoolean(Boolean paramBoolean)
  {
    this.value = paramBoolean.booleanValue();
  }
  
  public MutableBoolean(boolean paramBoolean)
  {
    this.value = paramBoolean;
  }
  
  public boolean booleanValue()
  {
    return this.value;
  }
  
  public int compareTo(MutableBoolean paramMutableBoolean)
  {
    boolean bool = paramMutableBoolean.value;
    if (this.value == bool) {
      return 0;
    }
    if (this.value) {
      return 1;
    }
    return -1;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((paramObject instanceof MutableBoolean))
    {
      bool1 = bool2;
      if (this.value == ((MutableBoolean)paramObject).booleanValue()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public Boolean getValue()
  {
    return Boolean.valueOf(this.value);
  }
  
  public int hashCode()
  {
    if (this.value) {
      return Boolean.TRUE.hashCode();
    }
    return Boolean.FALSE.hashCode();
  }
  
  public boolean isFalse()
  {
    return !this.value;
  }
  
  public boolean isTrue()
  {
    return this.value == true;
  }
  
  public void setValue(Boolean paramBoolean)
  {
    this.value = paramBoolean.booleanValue();
  }
  
  public void setValue(boolean paramBoolean)
  {
    this.value = paramBoolean;
  }
  
  public Boolean toBoolean()
  {
    return Boolean.valueOf(booleanValue());
  }
  
  public String toString()
  {
    return String.valueOf(this.value);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\mutable\MutableBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */