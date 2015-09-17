package com.zizun.cs.entities.api;

public class StoreDeleteParam
{
  public int Merchant_ID;
  public int Modify_ID;
  public long Store_ID;
  
  public StoreDeleteParam(int paramInt1, long paramLong, int paramInt2)
  {
    this.Merchant_ID = paramInt1;
    this.Store_ID = paramLong;
    this.Modify_ID = paramInt2;
  }
}