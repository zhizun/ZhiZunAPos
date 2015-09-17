package com.zizun.cs.biz.sale;

import java.io.Serializable;

public class SalesHistoryItemDetail
  implements Serializable
{
  private static final long serialVersionUID = -2717831454046313117L;
  private double Amount;
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private double SD_Cost;
  private double SD_PriceOrignial;
  private double SD_PricePromotional;
  private double SD_PriceSold;
  private double SD_Quantiy;
  
  public double getAmount()
  {
    return this.Amount;
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
  
  public double getSD_Cost()
  {
    return this.SD_Cost;
  }
  
  public double getSD_PriceOrignial()
  {
    return this.SD_PriceOrignial;
  }
  
  public double getSD_PricePromotional()
  {
    return this.SD_PricePromotional;
  }
  
  public double getSD_PriceSold()
  {
    return this.SD_PriceSold;
  }
  
  public double getSD_Quantiy()
  {
    return this.SD_Quantiy;
  }
  
  public void setAmount(double paramDouble)
  {
    this.Amount = paramDouble;
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
  
  public void setSD_Cost(double paramDouble)
  {
    this.SD_Cost = paramDouble;
  }
  
  public void setSD_PriceOrignial(double paramDouble)
  {
    this.SD_PriceOrignial = paramDouble;
  }
  
  public void setSD_PricePromotional(double paramDouble)
  {
    this.SD_PricePromotional = paramDouble;
  }
  
  public void setSD_PriceSold(double paramDouble)
  {
    this.SD_PriceSold = paramDouble;
  }
  
  public void setSD_Quantiy(double paramDouble)
  {
    this.SD_Quantiy = paramDouble;
  }
}