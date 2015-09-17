package com.zizun.cs.biz.refund.sales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class RefundSalesShoppingCart
  implements Serializable
{
  private static final long serialVersionUID = -5302265282475885054L;
  private ArrayList<RefundableSalesSheetItem> shoppingCartItems = new ArrayList();
  private double totalCount;
  private double totalMoney;
  
  public void SetSaleShoppingCartItemList(ArrayList<RefundableSalesSheetItem> paramArrayList)
  {
    this.shoppingCartItems = paramArrayList;
  }
  
  public void addShoppingCartItem(RefundableSalesSheetItem paramRefundableSalesSheetItem)
  {
    if (this.shoppingCartItems.contains(paramRefundableSalesSheetItem))
    {
      ((RefundableSalesSheetItem)this.shoppingCartItems.get(this.shoppingCartItems.indexOf(paramRefundableSalesSheetItem))).increaseCount();
      return;
    }
    paramRefundableSalesSheetItem.setQuantiyPromotional(1.0D);
    this.shoppingCartItems.add(paramRefundableSalesSheetItem);
  }
  
  public void clearShoppingCart()
  {
    this.shoppingCartItems.clear();
    this.totalCount = 0.0D;
    this.totalMoney = 0.0D;
  }
  
  public void deleteShoppingCartItem(RefundableSalesSheetItem paramRefundableSalesSheetItem)
  {
    if (this.shoppingCartItems.contains(paramRefundableSalesSheetItem)) {
      this.shoppingCartItems.remove(paramRefundableSalesSheetItem);
    }
  }
  
  public ArrayList<RefundableSalesSheetItem> getSaleShoppingCartItemList()
  {
    return this.shoppingCartItems;
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
      RefundableSalesSheetItem localRefundableSalesSheetItem = (RefundableSalesSheetItem)localIterator.next();
      this.totalCount += localRefundableSalesSheetItem.getQuantiyPromotional();
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
      RefundableSalesSheetItem localRefundableSalesSheetItem = (RefundableSalesSheetItem)localIterator.next();
      this.totalMoney += localRefundableSalesSheetItem.getAmountPromotional();
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