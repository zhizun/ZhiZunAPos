package com.zizun.cs.ui.entity;

import com.zizun.cs.entities.ProductGroup;

public class Goods
  extends BaseEntity
{
  private static final long serialVersionUID = 2456575295319640777L;
  private String Number;
  private long PSP_ID;
  private double WholeSaleAmount;
  private double amount;
  private double chooseAmount;
  private String code;
  private double costPrice;
  private String describe;
  private ProductGroup group;
  private String name;
  private double newAmount;
  private double newCostPrice;
  private double newSellPrice;
  private GoodsStatus newStatus;
  private double newStockPrice;
  private double newWholesalePrice;
  private String picture;
  private int productSku_status;
  private int product_status;
  private double retailAmount;
  private double sellPrice;
  private String size;
  private long skuId;
  private GoodsStatus status;
  private double stockAmount;
  private double stockPrice;
  private double totalMoney;
  private String unit;
  private double wholesalePrice;
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public double getChooseAmount()
  {
    return this.chooseAmount;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public double getCostPrice()
  {
    return this.costPrice;
  }
  
  public String getDescribe()
  {
    return this.describe;
  }
  
  public ProductGroup getGroup()
  {
    return this.group;
  }
  
  public long getId()
  {
    return super.getId();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public double getNewAmount()
  {
    return this.newAmount;
  }
  
  public double getNewCostPrice()
  {
    return this.newCostPrice;
  }
  
  public double getNewSellPrice()
  {
    return this.newSellPrice;
  }
  
  public GoodsStatus getNewStatus()
  {
    return this.newStatus;
  }
  
  public double getNewStockPrice()
  {
    return this.newStockPrice;
  }
  
  public double getNewWholesalePrice()
  {
    return this.newWholesalePrice;
  }
  
  public String getNumber()
  {
    return this.Number;
  }
  
  public long getPSP_ID()
  {
    return this.PSP_ID;
  }
  
  public String getPicture()
  {
    return this.picture;
  }
  
  public int getProductSku_status()
  {
    return this.productSku_status;
  }
  
  public int getProduct_status()
  {
    return this.product_status;
  }
  
  public double getRetailAmount()
  {
    return this.retailAmount;
  }
  
  public double getSellPrice()
  {
    return this.sellPrice;
  }
  
  public String getSize()
  {
    return this.size;
  }
  
  public long getSkuId()
  {
    return this.skuId;
  }
  
  public GoodsStatus getStatus()
  {
    return this.status;
  }
  
  public double getStockAmount()
  {
    return this.stockAmount;
  }
  
  public double getStockPrice()
  {
    return this.stockPrice;
  }
  
  public double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public String getUnit()
  {
    return this.unit;
  }
  
  public double getWholeSaleAmount()
  {
    return this.WholeSaleAmount;
  }
  
  public double getWholesalePrice()
  {
    return this.wholesalePrice;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public void setChooseAmount(double paramDouble)
  {
    this.chooseAmount = paramDouble;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public void setCostPrice(double paramDouble)
  {
    this.costPrice = paramDouble;
  }
  
  public void setDescribe(String paramString)
  {
    this.describe = paramString;
  }
  
  public void setGroup(ProductGroup paramProductGroup)
  {
    this.group = paramProductGroup;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setNewAmount(double paramDouble)
  {
    this.newAmount = paramDouble;
  }
  
  public void setNewCostPrice(double paramDouble)
  {
    this.newCostPrice = paramDouble;
  }
  
  public void setNewSellPrice(double paramDouble)
  {
    this.newSellPrice = paramDouble;
  }
  
  public void setNewStatus(GoodsStatus paramGoodsStatus)
  {
    this.newStatus = paramGoodsStatus;
  }
  
  public void setNewStockPrice(double paramDouble)
  {
    this.newStockPrice = paramDouble;
  }
  
  public void setNewWholesalePrice(double paramDouble)
  {
    this.newWholesalePrice = paramDouble;
  }
  
  public void setNumber(String paramString)
  {
    this.Number = paramString;
  }
  
  public void setPSP_ID(long paramLong)
  {
    this.PSP_ID = paramLong;
  }
  
  public void setPicture(String paramString)
  {
    this.picture = paramString;
  }
  
  public void setProductSku_status(int paramInt)
  {
    this.productSku_status = paramInt;
  }
  
  public void setProduct_status(int paramInt)
  {
    this.product_status = paramInt;
  }
  
  public void setRetailAmount(double paramDouble)
  {
    this.retailAmount = paramDouble;
  }
  
  public void setSellPrice(double paramDouble)
  {
    this.sellPrice = paramDouble;
  }
  
  public void setSize(String paramString)
  {
    this.size = paramString;
  }
  
  public void setSkuId(long paramLong)
  {
    this.skuId = paramLong;
  }
  
  public void setStatus(GoodsStatus paramGoodsStatus)
  {
    this.status = paramGoodsStatus;
  }
  
  public void setStockAmount(double paramDouble)
  {
    this.stockAmount = paramDouble;
  }
  
  public void setStockPrice(double paramDouble)
  {
    this.stockPrice = paramDouble;
  }
  
  public void setTotalMoney(double paramDouble)
  {
    this.totalMoney = paramDouble;
  }
  
  public void setUnit(String paramString)
  {
    this.unit = paramString;
  }
  
  public void setWholeSaleAmount(double paramDouble)
  {
    this.WholeSaleAmount = paramDouble;
  }
  
  public void setWholesalePrice(double paramDouble)
  {
    this.wholesalePrice = paramDouble;
  }
}
