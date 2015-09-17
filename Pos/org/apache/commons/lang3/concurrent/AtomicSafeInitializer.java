package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicSafeInitializer<T>
  implements ConcurrentInitializer<T>
{
  private final AtomicReference<AtomicSafeInitializer<T>> factory = new AtomicReference();
  private final AtomicReference<T> reference = new AtomicReference();
  
  public final T get()
    throws ConcurrentException
  {
    Object localObject;
    for (;;)
    {
      localObject = this.reference.get();
      if (localObject != null) {
        break;
      }
      if (this.factory.compareAndSet(null, this)) {
        this.reference.set(initialize());
      }
    }
    return (T)localObject;
  }
  
  protected abstract T initialize()
    throws ConcurrentException;
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\AtomicSafeInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */