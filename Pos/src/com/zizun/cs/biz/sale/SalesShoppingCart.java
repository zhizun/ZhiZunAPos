package com.zizun.cs.biz.sale;

import com.zizun.cs.biz.enumType.SalesType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class SalesShoppingCart
  implements Serializable
{
  private static final long serialVersionUID = -5302265282475885054L;
  private String SO_Remark = "";
  private SalesType salesType;
  private ArrayList<V_ProductSkuOnSale> shoppingCartItems = new ArrayList();
  private double totalCount;
  private double totalMoney;
  
  public SalesShoppingCart(SalesType paramSalesType)
  {
    setSalesType(paramSalesType);
  }
  
  public void addShoppingCartItem(V_ProductSkuOnSale paramV_ProductSkuOnSale)
  {
    if (this.shoppingCartItems.contains(paramV_ProductSkuOnSale))
    {
      ((V_ProductSkuOnSale)this.shoppingCartItems.get(this.shoppingCartItems.indexOf(paramV_ProductSkuOnSale))).increaseCount();
      return;
    }
    paramV_ProductSkuOnSale.setQuantiy(1.0D);
    this.shoppingCartItems.add(paramV_ProductSkuOnSale);
  }
  
  public void clearShoppingCart()
  {
    this.shoppingCartItems.clear();
    this.totalCount = 0.0D;
    this.totalMoney = 0.0D;
    this.SO_Remark = "";
  }
  
  public void deleteShoppingCartItem(V_ProductSkuOnSale paramV_ProductSkuOnSale)
  {
    if (this.shoppingCartItems.contains(paramV_ProductSkuOnSale)) {
      this.shoppingCartItems.remove(paramV_ProductSkuOnSale);
    }
  }
  
  public String getSO_Remark()
  {
    return this.SO_Remark;
  }
  
  public ArrayList<V_ProductSkuOnSale> getSaleShoppingCartItemList()
  {
    return this.shoppingCartItems;
  }
  
  public SalesType getSalesType()
  {
    return this.salesType;
  }
  
  public double getTotalCount()
  {
    this.totalCount = 0.0D;
    Iterator localIterator = this.shoppingCartItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return this.totalCount;
      }
      V_ProductSkuOnSale localV_ProductSkuOnSale = (V_ProductSkuOnSale)localIterator.next();
      this.totalCount += localV_ProductSkuOnSale.getQuantiy();
    }
  }
  
  public double getTotalMoney()
  {
    this.totalMoney = 0.0D;
    Iterator localIterator = this.shoppingCartItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return this.totalMoney;
      }
      V_ProductSkuOnSale localV_ProductSkuOnSale = (V_ProductSkuOnSale)localIterator.next();
      this.totalMoney += localV_ProductSkuOnSale.getAmountPromotional();
    }
  }
  
  public void setSO_Remark(String paramString)
  {
    this.SO_Remark = paramString;
  }
  
  public void setSalesType(SalesType paramSalesType)
  {
    this.salesType = paramSalesType;
    Iterator localIterator = this.shoppingCartItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      ((V_ProductSkuOnSale)localIterator.next()).initShoppingCartItem(paramSalesType);
    }
  }
  
  public void setTotalCount(double paramDouble)
  {
    this.totalCount = paramDouble;
  }
  
  public void setTotalMoney(double paramDouble)
  {
    this.totalMoney = paramDouble;
  }
}