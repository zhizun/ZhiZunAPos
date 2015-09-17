package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;

@Table(name="PO_Detail")
public class PO_Detail
  extends EntitySync
{
  private static final long serialVersionUID = 8393047668609906587L;
  private int Merchant_ID;
  private double PD_CostPrice;
  @ID
  private long PD_ID;
  private String PD_LineRemark;
  private double PD_PurchasePrice;
  private double PD_Quantiy;
  private double PD_RetailPrice;
  private long PO_ID;
  private long ProductSku_ID;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getPD_CostPrice()
  {
    return this.PD_CostPrice;
  }
  
  public long getPD_ID()
  {
    return this.PD_ID;
  }
  
  public String getPD_LineRemark()
  {
    return this.PD_LineRemark;
  }
  
  public double getPD_PurchasePrice()
  {
    return this.PD_PurchasePrice;
  }
  
  public double getPD_Quantiy()
  {
    return this.PD_Quantiy;
  }
  
  public double getPD_RetailPrice()
  {
    return this.PD_RetailPrice;
  }
  
  public long getPO_ID()
  {
    return this.PO_ID;
  }
  
  public long getProductSku_ID()
  {
    return this.ProductSku_ID;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setPD_CostPrice(double paramDouble)
  {
    this.PD_CostPrice = paramDouble;
  }
  
  public void setPD_ID(long paramLong)
  {
    this.PD_ID = paramLong;
  }
  
  public void setPD_LineRemark(String paramString)
  {
    this.PD_LineRemark = paramString;
  }
  
  public void setPD_PurchasePrice(double paramDouble)
  {
    this.PD_PurchasePrice = paramDouble;
  }
  
  public void setPD_Quantiy(double paramDouble)
  {
    this.PD_Quantiy = paramDouble;
  }
  
  public void setPD_RetailPrice(double paramDouble)
  {
    this.PD_RetailPrice = paramDouble;
  }
  
  public void setPO_ID(long paramLong)
  {
    this.PO_ID = paramLong;
  }
  
  public void setProductSku_ID(long paramLong)
  {
    this.ProductSku_ID = paramLong;
  }
}