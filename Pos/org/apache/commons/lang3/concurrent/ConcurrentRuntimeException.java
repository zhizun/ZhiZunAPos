package org.apache.commons.lang3.concurrent;

public class ConcurrentRuntimeException
  extends RuntimeException
{
  private static final long serialVersionUID = -6582182735562919670L;
  
  protected ConcurrentRuntimeException() {}
  
  public ConcurrentRuntimeException(String paramString, Throwable paramThrowable)
  {
    super(paramString, ConcurrentUtils.checkedException(paramThrowable));
  }
  
  public ConcurrentRuntimeException(Throwable paramThrowable)
  {
    super(ConcurrentUtils.checkedException(paramThrowable));
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\concurrent\ConcurrentRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */