package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicInitializer<T>
  implements ConcurrentInitializer<T>
{
  private final AtomicReference<T> reference = new AtomicReference();
  
  public T get()
    throws ConcurrentException
  {
    Object localObject2 = this.reference.get();
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject2 = initialize();
      localObject1 = localObject2;
      if (!this.reference.compareAndSet(null, localObject2)) {
        localObject1 = this.reference.get();
      }
    }
    return (T)localObject1;
  }
  
  protected abstract T initialize()
    throws ConcurrentException;
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\AtomicInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */