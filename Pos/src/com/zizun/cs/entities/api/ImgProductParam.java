package com.zizun.cs.entities.api;

public class ImgProductParam
{
  public String ImgCode;
  public int Merchant_ID;
  public long Product_ID;
  
  public ImgProductParam(int paramInt, long paramLong, String paramString)
  {
    this.Merchant_ID = paramInt;
    this.Product_ID = paramLong;
    this.ImgCode = paramString;
  }
}