package com.zizun.cs.biz.refund.purchase;

import java.io.Serializable;

public class RefundablePurchaseSheetItem
  implements Serializable, Comparable<RefundablePurchaseSheetItem>
{
  private static final long serialVersionUID = 7592064617902844252L;
  private double OriginalCost;
  private double OriginalPrice;
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private double Remain_Quantity;
  private double amountFinal;
  private double amountPromotional;
  private double pricePromotional;
  private double priceSold;
  private double quantiyPromotional;
  
  public int compareTo(RefundablePurchaseSheetItem paramRefundablePurchaseSheetItem)
  {
    if (this.pricePromotional > paramRefundablePurchaseSheetItem.pricePromotional) {
      return 1;
    }
    if (this.pricePromotional == paramRefundablePurchaseSheetItem.pricePromotional) {
      return 0;
    }
    return -1;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (RefundablePurchaseSheetItem)paramObject;
    } while (getProductSku_ID() == ((RefundablePurchaseSheetItem)paramObject).getProductSku_ID());
    return false;
  }
  
  public double getAmountFinal()
  {
    return this.amountFinal;
  }
  
  public double getAmountPromotional()
  {
    return this.amountPromotional;
  }
  
  public double getOriginalCost()
  {
    return this.OriginalCost;
  }
  
  public double getOriginalPrice()
  {
    return this.OriginalPrice;
  }
  
  public double getPricePromotional()
  {
    return this.pricePromotional;
  }
  
  public double getPriceSold()
  {
    return this.priceSold;
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
  
  public double getQuantiyPromotional()
  {
    return this.quantiyPromotional;
  }
  
  public double getRemain_Quantity()
  {
    return this.Remain_Quantity;
  }
  
  public void increaseCount()
  {
    if (this.quantiyPromotional < this.Remain_Quantity)
    {
      this.quantiyPromotional += 1.0D;
      setQuantiyPromotional(this.quantiyPromotional);
    }
  }
  
  public void initDocumentItem()
  {
    this.priceSold = this.pricePromotional;
    this.amountFinal = this.amountPromotional;
  }
  
  public void initShoppingCartItem()
  {
    this.pricePromotional = this.OriginalPrice;
    this.quantiyPromotional = this.Remain_Quantity;
    this.amountPromotional = (this.pricePromotional * this.Remain_Quantity);
  }
  
  public void reduceCount()
  {
    if (this.quantiyPromotional > 0.0D)
    {
      this.quantiyPromotional -= 1.0D;
      setQuantiyPromotional(this.quantiyPromotional);
    }
  }
  
  public void setAmountFinal(double paramDouble)
  {
    this.amountFinal = paramDouble;
  }
  
  public void setAmountPromotional(double paramDouble)
  {
    this.amountPromotional = paramDouble;
  }
  
  public void setOriginalCost(double paramDouble)
  {
    this.OriginalCost = paramDouble;
  }
  
  public void setOriginalPrice(double paramDouble)
  {
    this.OriginalPrice = paramDouble;
  }
  
  public void setPricePromotional(double paramDouble)
  {
    this.pricePromotional = paramDouble;
  }
  
  public void setPriceSold(double paramDouble)
  {
    this.priceSold = paramDouble;
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
  
  public void setQuantiyPromotional(double paramDouble)
  {
    this.quantiyPromotional = paramDouble;
    this.amountPromotional = (this.pricePromotional * this.quantiyPromotional);
    this.amountFinal = (this.priceSold * this.quantiyPromotional);
  }
  
  public void setRemain_Quantity(double paramDouble)
  {
    this.Remain_Quantity = paramDouble;
  }
}