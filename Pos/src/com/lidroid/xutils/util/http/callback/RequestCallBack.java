package com.lidroid.xutils.util.http.callback;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public abstract class RequestCallBack<T>
{
  private static final int DEFAULT_RATE = 1000;
  private static final int MIN_RATE = 200;
  private int rate;
  private String requestUrl;
  protected Object userTag;
  
  public RequestCallBack()
  {
    this.rate = 1000;
  }
  
  public RequestCallBack(int paramInt)
  {
    this.rate = paramInt;
  }
  
  public RequestCallBack(int paramInt, Object paramObject)
  {
    this.rate = paramInt;
    this.userTag = paramObject;
  }
  
  public RequestCallBack(Object paramObject)
  {
    this.rate = 1000;
    this.userTag = paramObject;
  }
  
  public final int getRate()
  {
    if (this.rate < 200) {
      return 200;
    }
    return this.rate;
  }
  
  public final String getRequestUrl()
  {
    return this.requestUrl;
  }
  
  public Object getUserTag()
  {
    return this.userTag;
  }
  
  public void onCancelled() {}
  
  public abstract void onFailure(HttpException paramHttpException, String paramString);
  
  public void onLoading(long paramLong1, long paramLong2, boolean paramBoolean) {}
  
  public void onStart() {}
  
  public abstract void onSuccess(ResponseInfo<T> paramResponseInfo);
  
  public final void setRate(int paramInt)
  {
    this.rate = paramInt;
  }
  
  public final void setRequestUrl(String paramString)
  {
    this.requestUrl = paramString;
  }
  
  public void setUserTag(Object paramObject)
  {
    this.userTag = paramObject;
  }
}