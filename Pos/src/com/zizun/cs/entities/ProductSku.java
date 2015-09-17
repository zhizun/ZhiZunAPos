package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;

@Table(name="ProductSku")
public class ProductSku
  extends EntitySync
{
  private static final long serialVersionUID = 8294772668001327719L;
  private double Average_Cost;
  private double Fixed_Cost;
  @ID
  private int Merchant_ID;
  private String ProductSku_BarCode;
  private String ProductSku_Code;
  @ID
  private long ProductSku_ID;
  private String ProductSku_Image;
  private String ProductSku_Name;
  private String ProductSku_Remark;
  private String ProductSku_Spec;
  private byte ProductSku_Status;
  private String ProductSku_Unit;
  @ID
  private long Product_ID;
  private String PruductSku_Description;
  private double Purchase_Price;
  private double Retail_Price;
  private double WholeSale_Price;
  
  public double getAverage_Cost()
  {
    return this.Average_Cost;
  }
  
  public double getFixed_Cost()
  {
    return this.Fixed_Cost;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
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
  
  public String getProductSku_Remark()
  {
    return this.ProductSku_Remark;
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
  
  public String getPruductSku_Description()
  {
    return this.PruductSku_Description;
  }
  
  public double getPurchase_Price()
  {
    return this.Purchase_Price;
  }
  
  public double getRetail_Price()
  {
    return this.Retail_Price;
  }
  
  public double getWholeSale_Price()
  {
    return this.WholeSale_Price;
  }
  
  public void setAverage_Cost(double paramDouble)
  {
    this.Average_Cost = paramDouble;
  }
  
  public void setFixed_Cost(double paramDouble)
  {
    this.Fixed_Cost = paramDouble;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
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
  
  public void setProductSku_Remark(String paramString)
  {
    this.ProductSku_Remark = paramString;
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
  
  public void setPruductSku_Description(String paramString)
  {
    this.PruductSku_Description = paramString;
  }
  
  public void setPurchase_Price(double paramDouble)
  {
    this.Purchase_Price = paramDouble;
  }
  
  public void setRetail_Price(double paramDouble)
  {
    this.Retail_Price = paramDouble;
  }
  
  public void setWholeSale_Price(double paramDouble)
  {
    this.WholeSale_Price = paramDouble;
  }
}