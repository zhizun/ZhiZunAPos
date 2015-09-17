package org.apache.commons.lang3.concurrent;

public abstract interface ConcurrentInitializer<T>
{
  public abstract T get()
    throws ConcurrentException;
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\ConcurrentInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */