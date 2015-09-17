package com.zizun.cs.entities.biz;

import com.zizun.cs.ui.entity.BaseEntity;

public class V_HeaderForPay
  extends BaseEntity
{
  private static final long serialVersionUID = -7731127461933647150L;
  private double ActualAmount;
  private int Merchant_ID;
  private double PlanAmount;
  private String RelatedName;
  private double RemainAmount;
  private long Store_ID;
  private long Supplier_ID;
  private long TransID;
  private String TransNumber;
  private String TransType;
  private long VIP_ID;
  
  public double getActualAmount()
  {
    return this.ActualAmount;
  }
  
  public long getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getPlanAmount()
  {
    return this.PlanAmount;
  }
  
  public String getRelatedName()
  {
    return this.RelatedName;
  }
  
  public double getRemainAmount()
  {
    return this.RemainAmount;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public long getSupplier_ID()
  {
    return this.Supplier_ID;
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
  
  public void setActualAmount(double paramDouble)
  {
    this.ActualAmount = paramDouble;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setPlanAmount(double paramDouble)
  {
    this.PlanAmount = paramDouble;
  }
  
  public void setRelatedName(String paramString)
  {
    this.RelatedName = paramString;
  }
  
  public void setRemainAmount(double paramDouble)
  {
    this.RemainAmount = paramDouble;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setSupplier_ID(long paramLong)
  {
    this.Supplier_ID = paramLong;
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