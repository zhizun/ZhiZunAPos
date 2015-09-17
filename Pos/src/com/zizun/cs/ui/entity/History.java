package com.zizun.cs.ui.entity;

import com.zizun.cs.entities.PO_Detail;
import com.zizun.cs.entities.PO_Header;
import com.zizun.cs.entities.ProductSku;
import com.zizun.cs.entities.biz.Associate;

public class History
  extends BaseEntity
  implements Comparable<History>
{
  private static final long serialVersionUID = 8367900583566398510L;
  private double amount;
  private Associate associate;
  private String billNum;
  private String date;
  private double discount;
  private String goodsName;
  private double money;
  private float payable;
  private float payied;
  private String picture;
  private PO_Detail po_Detail;
  private PO_Header po_Header;
  private double price;
  private ProductSku productSku;
  
  public int compareTo(History paramHistory)
  {
    return this.date.compareTo(paramHistory.date);
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public Associate getAssociate()
  {
    return this.associate;
  }
  
  public String getBillNum()
  {
    return this.billNum;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public double getDiscount()
  {
    return this.discount;
  }
  
  public String getGoodsName()
  {
    return this.goodsName;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public float getPayable()
  {
    return this.payable;
  }
  
  public float getPayied()
  {
    return this.payied;
  }
  
  public String getPicture()
  {
    return this.picture;
  }
  
  public PO_Detail getPo_Detail()
  {
    return this.po_Detail;
  }
  
  public PO_Header getPo_Header()
  {
    return this.po_Header;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public ProductSku getProductSku()
  {
    return this.productSku;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public void setAssociate(Associate paramAssociate)
  {
    this.associate = paramAssociate;
  }
  
  public void setBillNum(String paramString)
  {
    this.billNum = paramString;
  }
  
  public void setDate(String paramString)
  {
    this.date = paramString;
  }
  
  public void setDiscount(double paramDouble)
  {
    this.discount = paramDouble;
  }
  
  public void setGoodsName(String paramString)
  {
    this.goodsName = paramString;
  }
  
  public void setMoney(double paramDouble)
  {
    this.money = paramDouble;
  }
  
  public void setPayable(float paramFloat)
  {
    this.payable = paramFloat;
  }
  
  public void setPayied(float paramFloat)
  {
    this.payied = paramFloat;
  }
  
  public void setPicture(String paramString)
  {
    this.picture = paramString;
  }
  
  public void setPo_Detail(PO_Detail paramPO_Detail)
  {
    this.po_Detail = paramPO_Detail;
  }
  
  public void setPo_Header(PO_Header paramPO_Header)
  {
    this.po_Header = paramPO_Header;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public void setProductSku(ProductSku paramProductSku)
  {
    this.productSku = paramProductSku;
  }
}