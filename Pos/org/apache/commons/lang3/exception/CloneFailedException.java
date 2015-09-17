package org.apache.commons.lang3.exception;

public class CloneFailedException
  extends RuntimeException
{
  private static final long serialVersionUID = 20091223L;
  
  public CloneFailedException(String paramString)
  {
    super(paramString);
  }
  
  public CloneFailedException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public CloneFailedException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\exception\CloneFailedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */