package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.ProductSku;
import com.zizun.cs.ui.entity.History;
import com.zizun.cs.ui.entity.PurchaseGoods;
import java.util.ArrayList;

public abstract interface PurchasesDao
{
  public abstract ArrayList<History> getAllHistory(long paramLong, int paramInt1, int paramInt2);
  
  public abstract ArrayList<ProductSku> getProductInStorage(long paramLong, int paramInt1, int paramInt2)
    throws IllegalAccessException, InstantiationException, ClassNotFoundException;
  
  public abstract History getPurchaseBillbyPo_Id(long paramLong);
  
  public abstract PurchaseGoods getPurchaseGoodsByBarCode(String paramString);
  
  public abstract ArrayList<PurchaseGoods> getPurchaseGoodsByLikeCondtion(long paramLong, String paramString);
  
  public abstract ArrayList<PurchaseGoods> getPurchaseGoodsByPG_ID(long paramLong, int paramInt1, int paramInt2);
  
  public abstract ArrayList<PurchaseGoods> getPurchaseGoodsInStorage(long paramLong, int paramInt1, int paramInt2);
  
  public abstract ArrayList<PurchaseGoods> getPurchaseGoodsList(long paramLong);
  
  public abstract boolean updateBillStatus(long paramLong);
}