package com.zizun.cs.entities.api;

public class ImgParam
{
  public String ImgCode;
  public int Merchant_ID;
  
  public ImgParam(int paramInt, String paramString)
  {
    this.Merchant_ID = paramInt;
    this.ImgCode = paramString;
  }
}