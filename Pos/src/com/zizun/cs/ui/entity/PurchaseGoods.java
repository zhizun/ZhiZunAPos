package com.zizun.cs.ui.entity;

public class PurchaseGoods
  extends BaseEntity
{
  private static final long serialVersionUID = 3930551173218859692L;
  private double Fixed_Cost;
  private double Onhand_Quantity;
  private long PG_ID;
  private String PG_Name;
  private String ProductSku_BarCode;
  private String ProductSku_Code;
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private String ProductSku_Spec;
  private String ProductSku_Unit;
  private long Product_ID;
  private String PruductSku_Description;
  private double Purchase_Price;
  private double Qty_Order;
  private double Retail_Price;
  private double SubTotal;
  private double WholeSale_Price;
  private double chooseAmount;
  
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
      paramObject = (PurchaseGoods)paramObject;
    } while (this.ProductSku_ID == ((PurchaseGoods)paramObject).ProductSku_ID);
    return false;
  }
  
  public String getBarCode()
  {
    return this.ProductSku_BarCode;
  }
  
  public double getChooseAmount()
  {
    return this.chooseAmount;
  }
  
  public double getFixed_Cost()
  {
    return this.Fixed_Cost;
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
  
  public String getProductSku_Unit()
  {
    return this.ProductSku_Unit;
  }
  
  public long getProduct_ID()
  {
    return this.Product_ID;
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
  
  public double getRetail_Price()
  {
    return this.Retail_Price;
  }
  
  public double getSubTotal()
  {
    return this.SubTotal;
  }
  
  public double getWholeSale_Price()
  {
    return this.WholeSale_Price;
  }
  
  public void setBarCode(String paramString)
  {
    this.ProductSku_BarCode = paramString;
  }
  
  public void setChooseAmount(double paramDouble)
  {
    this.chooseAmount = paramDouble;
  }
  
  public void setFixed_Cost(double paramDouble)
  {
    this.Fixed_Cost = paramDouble;
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
  
  public void setProductSku_Unit(String paramString)
  {
    this.ProductSku_Unit = paramString;
  }
  
  public void setProduct_ID(long paramLong)
  {
    this.Product_ID = paramLong;
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
  
  public void setRetail_Price(double paramDouble)
  {
    this.Retail_Price = paramDouble;
  }
  
  public void setSubTotal(double paramDouble)
  {
    this.SubTotal = paramDouble;
  }
  
  public void setWholeSale_Price(double paramDouble)
  {
    this.WholeSale_Price = paramDouble;
  }
}