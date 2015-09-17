package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.ObjectUtils;

public class ConstantInitializer<T>
  implements ConcurrentInitializer<T>
{
  private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
  private final T object;
  
  public ConstantInitializer(T paramT)
  {
    this.object = paramT;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ConstantInitializer)) {
      return false;
    }
    paramObject = (ConstantInitializer)paramObject;
    return ObjectUtils.equals(getObject(), ((ConstantInitializer)paramObject).getObject());
  }
  
  public T get()
    throws ConcurrentException
  {
    return (T)getObject();
  }
  
  public final T getObject()
  {
    return (T)this.object;
  }
  
  public int hashCode()
  {
    if (getObject() != null) {
      return getObject().hashCode();
    }
    return 0;
  }
  
  public String toString()
  {
    return String.format("ConstantInitializer@%d [ object = %s ]", new Object[] { Integer.valueOf(System.identityHashCode(this)), String.valueOf(getObject()) });
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\ConstantInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */