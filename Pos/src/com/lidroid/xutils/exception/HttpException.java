package com.lidroid.xutils.exception;

public class HttpException
  extends BaseException
{
  private static final long serialVersionUID = 1L;
  private int exceptionCode;
  
  public HttpException() {}
  
  public HttpException(int paramInt)
  {
    this.exceptionCode = paramInt;
  }
  
  public HttpException(int paramInt, String paramString)
  {
    super(paramString);
    this.exceptionCode = paramInt;
  }
  
  public HttpException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.exceptionCode = paramInt;
  }
  
  public HttpException(int paramInt, Throwable paramThrowable)
  {
    super(paramThrowable);
    this.exceptionCode = paramInt;
  }
  
  public HttpException(String paramString)
  {
    super(paramString);
  }
  
  public HttpException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public HttpException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public int getExceptionCode()
  {
    return this.exceptionCode;
  }
}