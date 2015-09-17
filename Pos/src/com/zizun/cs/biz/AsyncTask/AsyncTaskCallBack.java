package com.zizun.cs.biz.AsyncTask;

public abstract interface AsyncTaskCallBack<T>
{
  public abstract void afterExcute(T paramT);
}