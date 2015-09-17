package com.zizun.cs.biz.refund;

import java.io.Serializable;
import java.sql.Timestamp;

public class RefundableSheet
  implements Serializable
{
  private static final long serialVersionUID = -2316950156226657083L;
  private int Merchant_ID;
  private String RelatedContact;
  private String RelatedName;
  private long Store_ID;
  private long Supplier_ID;
  private Timestamp TransDate;
  private long TransID;
  private String TransNumber;
  private String TransType;
  private long VIP_ID;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getRelatedContact()
  {
    return this.RelatedContact;
  }
  
  public String getRelatedName()
  {
    return this.RelatedName;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public long getSupplier_ID()
  {
    return this.Supplier_ID;
  }
  
  public Timestamp getTransDate()
  {
    return this.TransDate;
  }
  
  public long getTransID()
  {
    return this.TransID;
  }
  
  public String getTransNumber()
  {
    return this.TransNumber;
  }
  
  public String getTransType()
  {
    return this.TransType;
  }
  
  public long getVIP_ID()
  {
    return this.VIP_ID;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setRelatedContact(String paramString)
  {
    this.RelatedContact = paramString;
  }
  
  public void setRelatedName(String paramString)
  {
    this.RelatedName = paramString;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setSupplier_ID(long paramLong)
  {
    this.Supplier_ID = paramLong;
  }
  
  public void setTransDate(Timestamp paramTimestamp)
  {
    this.TransDate = paramTimestamp;
  }
  
  public void setTransID(long paramLong)
  {
    this.TransID = paramLong;
  }
  
  public void setTransNumber(String paramString)
  {
    this.TransNumber = paramString;
  }
  
  public void setTransType(String paramString)
  {
    this.TransType = paramString;
  }
  
  public void setVIP_ID(long paramLong)
  {
    this.VIP_ID = paramLong;
  }
}