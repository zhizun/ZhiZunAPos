package com.zizun.cs.biz.sale;

import java.io.Serializable;

public class V_TransactionDetail
  implements Serializable
{
  private static final long serialVersionUID = -6055501629864565642L;
  private int Merchant_ID;
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private double TransAmount;
  private long TransDetailID;
  private long TransID;
  private double TransPrice;
  private double TransQty;
  private String TransType;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public long getProductSku_ID()
  {
    return this.ProductSku_ID;
  }
  
  public String getProductSku_Image()
  {
    return this.ProductSku_Image;
  }
  
  public String getProductSku_Name()
  {
    return this.ProductSku_Name;
  }
  
  public double getTransAmount()
  {
    return this.TransAmount;
  }
  
  public long getTransDetailID()
  {
    return this.TransDetailID;
  }
  
  public long getTransID()
  {
    return this.TransID;
  }
  
  public double getTransPrice()
  {
    return this.TransPrice;
  }
  
  public double getTransQty()
  {
    return this.TransQty;
  }
  
  public String getTransType()
  {
    return this.TransType;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setProductSku_ID(long paramLong)
  {
    this.ProductSku_ID = paramLong;
  }
  
  public void setProductSku_Image(String paramString)
  {
    this.ProductSku_Image = paramString;
  }
  
  public void setProductSku_Name(String paramString)
  {
    this.ProductSku_Name = paramString;
  }
  
  public void setTransAmount(double paramDouble)
  {
    this.TransAmount = paramDouble;
  }
  
  public void setTransDetailID(long paramLong)
  {
    this.TransDetailID = paramLong;
  }
  
  public void setTransID(long paramLong)
  {
    this.TransID = paramLong;
  }
  
  public void setTransPrice(double paramDouble)
  {
    this.TransPrice = paramDouble;
  }
  
  public void setTransQty(double paramDouble)
  {
    this.TransQty = paramDouble;
  }
  
  public void setTransType(String paramString)
  {
    this.TransType = paramString;
  }
}