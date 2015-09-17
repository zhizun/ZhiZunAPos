package org.apache.commons.lang3.concurrent;

public abstract class LazyInitializer<T>
  implements ConcurrentInitializer<T>
{
  private volatile T object;
  
  public T get()
    throws ConcurrentException
  {
    Object localObject1 = this.object;
    if (localObject1 == null) {
      try
      {
        Object localObject2 = this.object;
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = initialize();
          this.object = localObject1;
        }
        return (T)localObject1;
      }
      finally {}
    }
    return ?;
  }
  
  protected abstract T initialize()
    throws ConcurrentException;
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\LazyInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */