package com.zizun.cs.biz.refund.purchase;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.dao.PurchasesDao;
import com.zizun.cs.biz.dao.impl.PaymentMethodDaoImpl;
import com.zizun.cs.biz.dao.impl.PurchaseDaoImpl;
import com.zizun.cs.biz.dao.trans.SalesTrans;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.dao.RefundPurchaseDoucumentDao;
import com.zizun.cs.biz.refund.dao.impl.RefundPurchaseDoucumentDaoImpl;
import com.zizun.cs.biz.sale.SalesHistoryItem;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.app.StoreApplication;
import java.util.List;

public class RefundPurchaseManager
{
  private static RefundPurchaseManager mInstance;
  private static RefundableSheet refundableSheet = null;
  private static RefundPurchaseShoppingCart shoppingCart;
  private List<PaymentMethod> paymentMethodList;
  
  public static void clearData()
  {
    shoppingCart = null;
    refundableSheet = null;
  }
  
  public static RefundPurchaseManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new RefundPurchaseManager();
    }
    return mInstance;
  }
  
  public static RefundableSheet getRefundableSheet()
  {
    return refundableSheet;
  }
  
  public static RefundPurchaseShoppingCart getShoppingCart()
  {
    return shoppingCart;
  }
  
  public static boolean saveRefundPurchaseDocument(RefundPurchaseDocument paramRefundPurchaseDocument)
  {
    return new RefundPurchaseDoucumentDaoImpl().saveRefundPurchaseDocumentTransction(paramRefundPurchaseDocument);
  }
  
  public static void setRefundableSheet(RefundableSheet paramRefundableSheet)
  {
    refundableSheet = paramRefundableSheet;
  }
  
  public static void setShoppingCart(RefundPurchaseShoppingCart paramRefundPurchaseShoppingCart)
  {
    shoppingCart = paramRefundPurchaseShoppingCart;
  }
  
  public List<SalesHistoryItem> getAllSalesHistoryItemOutOfThread(SalesTrans paramSalesTrans)
  {
    return paramSalesTrans.getAllSalesHistoryItem();
  }
  
  public List<PaymentMethod> getPaymentMethodListOutUIThread()
  {
    if (this.paymentMethodList == null) {
      this.paymentMethodList = new PaymentMethodDaoImpl().getAllPaymentMethod();
    }
    return this.paymentMethodList;
  }
  
  public List<PurchaseGoods> getSalesHistoryItemDetailOutOfThread(SalesTrans paramSalesTrans, long paramLong)
  {
    return new PurchaseDaoImpl(StoreApplication.getContext(), UserManager.getInstance().getCurrentUser().getMerchant_ID()).getPurchaseGoodsList(paramLong);
  }
  
  public void setPaymentMethodList(List<PaymentMethod> paramList)
  {
    this.paymentMethodList = paramList;
  }
}