package com.zizun.cs.entities.biz;

import java.io.Serializable;

public class V_VoucherHeader
  implements Serializable
{
  private static final long serialVersionUID = 3397123932043751758L;
  private int Merchant_ID;
  private String RelatedName;
  private long Supplier_ID;
  private byte VD_TransType;
  private long VH_ID;
  private String VH_Number;
  private byte VH_PR;
  private byte VH_Status;
  private long VH_StoreID;
  private String VH_TransDate;
  private byte VH_Type;
  private long VIP_ID;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getRelatedName()
  {
    return this.RelatedName;
  }
  
  public long getSupplier_ID()
  {
    return this.Supplier_ID;
  }
  
  public byte getVD_TransType()
  {
    return this.VD_TransType;
  }
  
  public long getVH_ID()
  {
    return this.VH_ID;
  }
  
  public String getVH_Number()
  {
    return this.VH_Number;
  }
  
  public byte getVH_PR()
  {
    return this.VH_PR;
  }
  
  public byte getVH_Status()
  {
    return this.VH_Status;
  }
  
  public long getVH_StoreID()
  {
    return this.VH_StoreID;
  }
  
  public String getVH_TransDate()
  {
    return this.VH_TransDate;
  }
  
  public byte getVH_Type()
  {
    return this.VH_Type;
  }
  
  public long getVIP_ID()
  {
    return this.VIP_ID;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setRelatedName(String paramString)
  {
    this.RelatedName = paramString;
  }
  
  public void setSupplier_ID(long paramLong)
  {
    this.Supplier_ID = paramLong;
  }
  
  public void setVD_TransType(byte paramByte)
  {
    this.VD_TransType = paramByte;
  }
  
  public void setVH_ID(long paramLong)
  {
    this.VH_ID = paramLong;
  }
  
  public void setVH_Number(String paramString)
  {
    this.VH_Number = paramString;
  }
  
  public void setVH_PR(byte paramByte)
  {
    this.VH_PR = paramByte;
  }
  
  public void setVH_Status(byte paramByte)
  {
    this.VH_Status = paramByte;
  }
  
  public void setVH_StoreID(long paramLong)
  {
    this.VH_StoreID = paramLong;
  }
  
  public void setVH_TransDate(String paramString)
  {
    this.VH_TransDate = paramString;
  }
  
  public void setVH_Type(byte paramByte)
  {
    this.VH_Type = paramByte;
  }
  
  public void setVIP_ID(long paramLong)
  {
    this.VIP_ID = paramLong;
  }
}