package org.apache.commons.lang3.mutable;

import java.io.Serializable;

public class MutableObject<T>
  implements Mutable<T>, Serializable
{
  private static final long serialVersionUID = 86241875189L;
  private T value;
  
  public MutableObject() {}
  
  public MutableObject(T paramT)
  {
    this.value = paramT;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    do
    {
      return false;
      if (this == paramObject) {
        return true;
      }
    } while (getClass() != paramObject.getClass());
    paramObject = (MutableObject)paramObject;
    return this.value.equals(((MutableObject)paramObject).value);
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
  
  public int hashCode()
  {
    if (this.value == null) {
      return 0;
    }
    return this.value.hashCode();
  }
  
  public void setValue(T paramT)
  {
    this.value = paramT;
  }
  
  public String toString()
  {
    if (this.value == null) {
      return "null";
    }
    return this.value.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\mutable\MutableObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */