package com.zizun.cs.biz.sale;

import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.common.utils.NumUtil;
import java.io.Serializable;

public class V_ProductSkuOnSale
  implements Serializable, Comparable<V_ProductSkuOnSale>
{
  private static final long serialVersionUID = 3505791811647416869L;
  private double Fixed_Cost;
  private int Merchant_ID;
  private double Onhand_Quantity;
  private long PG_ID;
  private String PG_Name;
  private byte PSS_Status;
  private String ProductSku_BarCode;
  private String ProductSku_Code;
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private String ProductSku_Spec;
  private byte ProductSku_Status;
  private String ProductSku_Unit;
  private long Product_ID;
  private byte Product_Status;
  private String PruductSku_Description;
  private double Purchase_Price;
  private double Qty_Order;
  private double Qty_Retail;
  private double Qty_Wholesale;
  private double Retail_Price;
  private long Store_ID;
  private double WholeSale_Price;
  private double amountFinal;
  private double amountPromotional;
  private double priceOrignial;
  private double pricePromotional;
  private double priceSold;
  private double quantiy;
  
  public int compareTo(V_ProductSkuOnSale paramV_ProductSkuOnSale)
  {
    if (this.amountFinal > paramV_ProductSkuOnSale.amountFinal) {
      return -1;
    }
    if (this.amountFinal == paramV_ProductSkuOnSale.amountFinal) {
      return 0;
    }
    return 1;
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
      paramObject = (V_ProductSkuOnSale)paramObject;
    } while (getProductSku_ID() == ((V_ProductSkuOnSale)paramObject).getProductSku_ID());
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
  
  public double getFixed_Cost()
  {
    return this.Fixed_Cost;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getOnhand_Quantity()
  {
    return this.Onhand_Quantity;
  }
  
  public long getPG_ID()
  {
    return this.PG_ID;
  }
  
  public String getPG_Name()
  {
    return this.PG_Name;
  }
  
  public byte getPSS_Status()
  {
    return this.PSS_Status;
  }
  
  public double getPriceOrignial()
  {
    return this.priceOrignial;
  }
  
  public double getPricePromotional()
  {
    return this.pricePromotional;
  }
  
  public double getPriceSold()
  {
    return this.priceSold;
  }
  
  public String getProductSku_BarCode()
  {
    return this.ProductSku_BarCode;
  }
  
  public String getProductSku_Code()
  {
    return this.ProductSku_Code;
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
  
  public String getProductSku_Spec()
  {
    return this.ProductSku_Spec;
  }
  
  public byte getProductSku_Status()
  {
    return this.ProductSku_Status;
  }
  
  public String getProductSku_Unit()
  {
    return this.ProductSku_Unit;
  }
  
  public long getProduct_ID()
  {
    return this.Product_ID;
  }
  
  public byte getProduct_Status()
  {
    return this.Product_Status;
  }
  
  public String getPruductSku_Description()
  {
    return this.PruductSku_Description;
  }
  
  public double getPurchase_Price()
  {
    return this.Purchase_Price;
  }
  
  public double getQty_Order()
  {
    return this.Qty_Order;
  }
  
  public double getQty_Retail()
  {
    return this.Qty_Retail;
  }
  
  public double getQty_Wholesale()
  {
    return this.Qty_Wholesale;
  }
  
  public double getQuantiy()
  {
    return this.quantiy;
  }
  
  public double getRetail_Price()
  {
    return this.Retail_Price;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public double getWholeSale_Price()
  {
    return this.WholeSale_Price;
  }
  
  public void increaseCount()
  {
    this.quantiy += 1.0D;
    setQuantiy(this.quantiy);
  }
  
  public void initDocumentItem(double paramDouble1, double paramDouble2)
  {
    if (paramDouble2 != 0.0D)
    {
      paramDouble1 = (paramDouble1 - paramDouble2) / paramDouble1;
      this.priceSold = NumUtil.getNum6((this.amountPromotional - (this.amountPromotional - this.amountPromotional * paramDouble1)) / this.quantiy);
      this.amountFinal = NumUtil.getNum(this.priceSold * this.quantiy);
      return;
    }
    this.priceSold = this.pricePromotional;
    this.amountFinal = this.amountPromotional;
  }
  
  public void initShoppingCartItem(SalesType paramSalesType)
  {
    if (paramSalesType == SalesType.SALE)
    {
      this.priceOrignial = this.Retail_Price;
      this.pricePromotional = this.Retail_Price;
    }
    for (;;)
    {
      this.amountPromotional = (this.pricePromotional * this.quantiy);
      return;
      if (paramSalesType == SalesType.WHOLESALE)
      {
        this.priceOrignial = this.WholeSale_Price;
        this.pricePromotional = this.WholeSale_Price;
      }
    }
  }
  
  public void reduceCount()
  {
    if (this.quantiy > 0.0D)
    {
      this.quantiy -= 1.0D;
      setQuantiy(this.quantiy);
    }
  }
  
  public void setAmountFinal(double paramDouble)
  {
    this.amountFinal = paramDouble;
  }
  
  public void setAmountPromotional(double paramDouble)
  {
    this.amountPromotional = paramDouble;
    this.amountPromotional = (this.pricePromotional * this.quantiy);
  }
  
  public void setFixed_Cost(double paramDouble)
  {
    this.Fixed_Cost = paramDouble;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setOnhand_Quantity(double paramDouble)
  {
    this.Onhand_Quantity = paramDouble;
  }
  
  public void setPG_ID(long paramLong)
  {
    this.PG_ID = paramLong;
  }
  
  public void setPG_Name(String paramString)
  {
    this.PG_Name = paramString;
  }
  
  public void setPSS_Status(byte paramByte)
  {
    this.PSS_Status = paramByte;
  }
  
  public void setPricePromotional(double paramDouble)
  {
    this.pricePromotional = paramDouble;
  }
  
  public void setPriceSold(double paramDouble)
  {
    this.priceSold = paramDouble;
  }
  
  public void setProductSku_BarCode(String paramString)
  {
    this.ProductSku_BarCode = paramString;
  }
  
  public void setProductSku_Code(String paramString)
  {
    this.ProductSku_Code = paramString;
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
  
  public void setProductSku_Spec(String paramString)
  {
    this.ProductSku_Spec = paramString;
  }
  
  public void setProductSku_Status(byte paramByte)
  {
    this.ProductSku_Status = paramByte;
  }
  
  public void setProductSku_Unit(String paramString)
  {
    this.ProductSku_Unit = paramString;
  }
  
  public void setProduct_ID(long paramLong)
  {
    this.Product_ID = paramLong;
  }
  
  public void setProduct_Status(byte paramByte)
  {
    this.Product_Status = paramByte;
  }
  
  public void setPruductSku_Description(String paramString)
  {
    this.PruductSku_Description = paramString;
  }
  
  public void setPurchase_Price(double paramDouble)
  {
    this.Purchase_Price = paramDouble;
  }
  
  public void setQty_Order(double paramDouble)
  {
    this.Qty_Order = paramDouble;
  }
  
  public void setQty_Retail(double paramDouble)
  {
    this.Qty_Retail = paramDouble;
  }
  
  public void setQty_Wholesale(double paramDouble)
  {
    this.Qty_Wholesale = paramDouble;
  }
  
  public void setQuantiy(double paramDouble)
  {
    this.quantiy = paramDouble;
    this.amountPromotional = (this.pricePromotional * this.quantiy);
    this.amountFinal = (this.priceSold * this.quantiy);
  }
  
  public void setRetail_Price(double paramDouble)
  {
    this.Retail_Price = paramDouble;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setWholeSale_Price(double paramDouble)
  {
    this.WholeSale_Price = paramDouble;
  }
}