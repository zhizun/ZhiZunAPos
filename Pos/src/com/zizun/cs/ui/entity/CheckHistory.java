package com.zizun.cs.ui.entity;

public class CheckHistory
  extends BaseEntity
{
  private static final long serialVersionUID = 2840298685789726261L;
  private double AccountAmount;
  private double amount;
  private String billNum;
  private double checkAmount;
  private String date;
  private String goodsName;
  private double num;
  private String picture;
  private double price;
  private String tradeType;
  
  public double getAccountAmount()
  {
    return this.AccountAmount;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public String getBillNum()
  {
    return this.billNum;
  }
  
  public double getCheckAmount()
  {
    return this.checkAmount;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public String getGoodsName()
  {
    return this.goodsName;
  }
  
  public double getNum()
  {
    return this.num;
  }
  
  public String getPicture()
  {
    return this.picture;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public String getTradeType()
  {
    return this.tradeType;
  }
  
  public void setAccountAmount(double paramDouble)
  {
    this.AccountAmount = paramDouble;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public void setBillNum(String paramString)
  {
    this.billNum = paramString;
  }
  
  public void setCheckAmount(double paramDouble)
  {
    this.checkAmount = paramDouble;
  }
  
  public void setDate(String paramString)
  {
    this.date = paramString;
  }
  
  public void setGoodsName(String paramString)
  {
    this.goodsName = paramString;
  }
  
  public void setNum(double paramDouble)
  {
    this.num = paramDouble;
  }
  
  public void setPicture(String paramString)
  {
    this.picture = paramString;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public void setTradeType(String paramString)
  {
    this.tradeType = paramString;
  }
}