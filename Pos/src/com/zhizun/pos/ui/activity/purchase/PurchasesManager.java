package com.zhizun.pos.ui.activity.purchase;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.PurchasesDao;
import com.zizun.cs.biz.dao.impl.PurchaseDaoImpl;
import com.zizun.cs.biz.dao.trans.PurchaseTrans;
import com.zizun.cs.entities.PO_Detail;
import com.zizun.cs.entities.PO_Header;
import com.zizun.cs.entities.ProductSku;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.ui.entity.History;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.app.StoreApplication;
import java.util.ArrayList;

public class PurchasesManager
{
  private static PurchaseCart purchaseCart = null;
  private PurchaseTrans purchaseTrans;
  private PurchasesDao purchasesDao;
  
  public PurchasesManager() {}
  
  public PurchasesManager(PurchasesDao paramPurchasesDao)
  {
    this.purchasesDao = paramPurchasesDao;
  }
  
  public PurchasesManager(PurchasesDao paramPurchasesDao, PurchaseTrans paramPurchaseTrans)
  {
    this.purchasesDao = paramPurchasesDao;
    this.purchaseTrans = paramPurchaseTrans;
  }
  
  public static void clearData()
  {
    purchaseCart = null;
  }
  
  public static PurchaseCart getPurchaseCart()
  {
    if (purchaseCart == null) {
      purchaseCart = new PurchaseCart();
    }
    return purchaseCart;
  }
  
  public static void setPurchaseCart(PurchaseCart paramPurchaseCart)
  {
    purchaseCart = paramPurchaseCart;
  }
  
  public boolean deleteBillTrans(History paramHistory)
  {
    return this.purchaseTrans.deleteBillTrans(paramHistory, getPurchaseGoodsList(paramHistory.getPo_Header().getPO_ID()));
  }
  
  public boolean doPurchaseTran(PO_Header paramPO_Header, PO_Detail[] paramArrayOfPO_Detail, TransactionLog[] paramArrayOfTransactionLog)
  {
    return this.purchaseTrans.purchaseTrans(paramPO_Header, paramArrayOfPO_Detail, paramArrayOfTransactionLog);
  }
  
  public ArrayList<History> getAllHistory(long paramLong, int paramInt1, int paramInt2)
  {
    return this.purchasesDao.getAllHistory(paramLong, paramInt1, paramInt2);
  }
  
  public ArrayList<ProductSku> getProductInStorage(long paramLong, int paramInt1, int paramInt2)
    throws IllegalAccessException, InstantiationException, ClassNotFoundException
  {
    return this.purchasesDao.getProductInStorage(paramLong, paramInt1, paramInt2);
  }
  
  public History getPurchaseByPo_Id(long paramLong)
  {
    return this.purchasesDao.getPurchaseBillbyPo_Id(paramLong);
  }
  
  public PurchaseGoods getPurchaseGoodByBarcode(String paramString)
  {
    return new PurchaseDaoImpl(StoreApplication.getContext(), UserManager.getInstance().getCurrentUser().getMerchant_ID()).getPurchaseGoodsByBarCode(paramString);
  }
  
  public PurchaseGoods getPurchaseGoodsByBarCode(String paramString)
  {
    return this.purchasesDao.getPurchaseGoodsByBarCode(paramString);
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsByLikeCondtion(long paramLong, String paramString)
  {
    return this.purchasesDao.getPurchaseGoodsByLikeCondtion(paramLong, paramString);
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsByPG_ID(long paramLong, int paramInt1, int paramInt2)
  {
    return this.purchasesDao.getPurchaseGoodsByPG_ID(paramLong, paramInt1, paramInt2);
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsInStorage(long paramLong, int paramInt1, int paramInt2)
  {
    return this.purchasesDao.getPurchaseGoodsInStorage(paramLong, paramInt1, paramInt2);
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsList(long paramLong)
  {
    return this.purchasesDao.getPurchaseGoodsList(paramLong);
  }
  
  public boolean hasDeletePurchaseReturnOutUIThread(long paramLong)
  {
    return this.purchaseTrans.hasPurchaseReturn(paramLong);
  }
  
  public boolean hasDeletePurchaseVoucherOutUIThread(long paramLong)
  {
    return this.purchaseTrans.hasPurchaseVoucher(paramLong);
  }
  
  public boolean updateBillStatus(long paramLong)
  {
    return this.purchasesDao.updateBillStatus(paramLong);
  }
}