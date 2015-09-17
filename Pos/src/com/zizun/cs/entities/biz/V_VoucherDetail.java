package com.zizun.cs.entities.biz;

import java.io.Serializable;

public class V_VoucherDetail
  implements Serializable
{
  private static final long serialVersionUID = -7582184296485136780L;
  private int Merchant_ID;
  private String TransNumber;
  private byte TransType;
  private double VD_TransAmount;
  private long VD_TransID;
  private long VH_ID;
  private long VH_StoreID;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getTransNumber()
  {
    return this.TransNumber;
  }
  
  public byte getTransType()
  {
    return this.TransType;
  }
  
  public double getVD_TransAmount()
  {
    return this.VD_TransAmount;
  }
  
  public long getVD_TransID()
  {
    return this.VD_TransID;
  }
  
  public long getVH_ID()
  {
    return this.VH_ID;
  }
  
  public long getVH_StoreID()
  {
    return this.VH_StoreID;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setTransNumber(String paramString)
  {
    this.TransNumber = paramString;
  }
  
  public void setTransType(byte paramByte)
  {
    this.TransType = paramByte;
  }
  
  public void setVD_TransAmount(double paramDouble)
  {
    this.VD_TransAmount = paramDouble;
  }
  
  public void setVD_TransID(long paramLong)
  {
    this.VD_TransID = paramLong;
  }
  
  public void setVH_ID(long paramLong)
  {
    this.VH_ID = paramLong;
  }
  
  public void setVH_StoreID(long paramLong)
  {
    this.VH_StoreID = paramLong;
  }
}