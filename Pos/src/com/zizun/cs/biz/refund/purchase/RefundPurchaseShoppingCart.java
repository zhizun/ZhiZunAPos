package com.zizun.cs.biz.refund.purchase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class RefundPurchaseShoppingCart
  implements Serializable
{
  private static final long serialVersionUID = -5302265282475885054L;
  private ArrayList<RefundablePurchaseSheetItem> shoppingCartItems = new ArrayList();
  private double totalCount;
  private double totalMoney;
  
  public void addShoppingCartItem(RefundablePurchaseSheetItem paramRefundablePurchaseSheetItem)
  {
    if (this.shoppingCartItems.contains(paramRefundablePurchaseSheetItem))
    {
      ((RefundablePurchaseSheetItem)this.shoppingCartItems.get(this.shoppingCartItems.indexOf(paramRefundablePurchaseSheetItem))).increaseCount();
      return;
    }
    paramRefundablePurchaseSheetItem.setQuantiyPromotional(1.0D);
    this.shoppingCartItems.add(paramRefundablePurchaseSheetItem);
  }
  
  public void clearShoppingCart()
  {
    this.shoppingCartItems.clear();
    this.totalCount = 0.0D;
    this.totalMoney = 0.0D;
  }
  
  public void deleteShoppingCartItem(RefundablePurchaseSheetItem paramRefundablePurchaseSheetItem)
  {
    if (this.shoppingCartItems.contains(paramRefundablePurchaseSheetItem)) {
      this.shoppingCartItems.remove(paramRefundablePurchaseSheetItem);
    }
  }
  
  public ArrayList<RefundablePurchaseSheetItem> getShoppingCartItemList()
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
      RefundablePurchaseSheetItem localRefundablePurchaseSheetItem = (RefundablePurchaseSheetItem)localIterator.next();
      this.totalCount += localRefundablePurchaseSheetItem.getQuantiyPromotional();
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
      RefundablePurchaseSheetItem localRefundablePurchaseSheetItem = (RefundablePurchaseSheetItem)localIterator.next();
      this.totalMoney += localRefundablePurchaseSheetItem.getAmountPromotional();
    }
  }
  
  public void setShoppingCartItemList(ArrayList<RefundablePurchaseSheetItem> paramArrayList)
  {
    this.shoppingCartItems = paramArrayList;
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