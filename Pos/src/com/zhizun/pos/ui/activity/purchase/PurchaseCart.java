package com.zhizun.pos.ui.activity.purchase;

import com.zizun.cs.ui.entity.PurchaseGoods;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PurchaseCart
  implements Serializable
{
  private static final long serialVersionUID = -2834697450463524423L;
  private ArrayList<PurchaseGoods> allPurchaseGoods = new ArrayList();
  private double totalCount;
  private double totalMoney;
  
  public void addAmount(PurchaseGoods paramPurchaseGoods)
  {
    paramPurchaseGoods.setChooseAmount(paramPurchaseGoods.getChooseAmount() + 1.0D);
  }
  
  public void addPurchaseGoods(PurchaseGoods paramPurchaseGoods)
  {
    if (this.allPurchaseGoods.contains(paramPurchaseGoods))
    {
      Iterator localIterator = this.allPurchaseGoods.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return;
        }
        PurchaseGoods localPurchaseGoods = (PurchaseGoods)localIterator.next();
        if (localPurchaseGoods.getProduct_ID() == paramPurchaseGoods.getProduct_ID())
        {
          localPurchaseGoods.setChooseAmount(localPurchaseGoods.getChooseAmount() + 1.0D);
          localPurchaseGoods.setOnhand_Quantity(localPurchaseGoods.getOnhand_Quantity() + 1.0D);
        }
      }
    }
    paramPurchaseGoods.setChooseAmount(1.0D);
    this.allPurchaseGoods.add(paramPurchaseGoods);
  }
  
  public void clear(ArrayList<PurchaseGoods> paramArrayList)
  {
    this.allPurchaseGoods.removeAll(paramArrayList);
  }
  
  public ArrayList<PurchaseGoods> getAllPurchaseGoods()
  {
    return this.allPurchaseGoods;
  }
  
  public ArrayList<PurchaseGoods> getGoods()
  {
    return this.allPurchaseGoods;
  }
  
  public double getTotalCount()
  {
    this.totalCount = 0.0D;
    Iterator localIterator = this.allPurchaseGoods.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return this.totalCount;
      }
      PurchaseGoods localPurchaseGoods = (PurchaseGoods)localIterator.next();
      this.totalCount += localPurchaseGoods.getChooseAmount();
    }
  }
  
  public double getTotalMoney()
  {
    this.totalMoney = 0.0D;
    Iterator localIterator = this.allPurchaseGoods.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return this.totalMoney;
      }
      PurchaseGoods localPurchaseGoods = (PurchaseGoods)localIterator.next();
      this.totalMoney += localPurchaseGoods.getPurchase_Price() * localPurchaseGoods.getChooseAmount();
    }
  }
  
  public int hashCode()
  {
    if (this.allPurchaseGoods == null) {}
    for (int i = 0;; i = this.allPurchaseGoods.hashCode()) {
      return i + 31;
    }
  }
  
  public void setAllPurchaseGoods(ArrayList<PurchaseGoods> paramArrayList)
  {
    this.allPurchaseGoods = paramArrayList;
  }
  
  public void setGoods(ArrayList<PurchaseGoods> paramArrayList)
  {
    this.allPurchaseGoods = paramArrayList;
  }
  
  public void setTotalCount(double paramDouble)
  {
    this.totalCount = paramDouble;
  }
  
  public void setTotalMoney(double paramDouble)
  {
    this.totalMoney = paramDouble;
  }
  
  public void subAmount(PurchaseGoods paramPurchaseGoods)
  {
    paramPurchaseGoods.setChooseAmount(paramPurchaseGoods.getChooseAmount() - 1.0D);
  }
}