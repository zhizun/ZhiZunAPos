package com.zizun.cs.biz.refund.sales;

import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.dao.impl.PaymentMethodDaoImpl;
import com.zizun.cs.biz.dao.trans.SalesTrans;
import com.zizun.cs.biz.dao.trans.impl.SalesTransaction;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.dao.RefundSalesDoucumentDao;
import com.zizun.cs.biz.refund.dao.impl.RefundSalesDoucumentDaoImpl;
import com.zizun.cs.biz.sale.SalesHistoryItem;
import com.zizun.cs.biz.sale.SalesHistoryItemDetail;
import com.zizun.cs.entities.PaymentMethod;
import java.util.List;

public class RefundSalesManager
{
  private static RefundSalesManager mInstance;
  private static RefundSalesShoppingCart refundSalesShoppingCart;
  private static RefundableSheet refundableSheet = null;
  private List<PaymentMethod> paymentMethodList;
  
  public static void clearData()
  {
    refundSalesShoppingCart = null;
    refundableSheet = null;
  }
  
  public static RefundSalesManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new RefundSalesManager();
    }
    return mInstance;
  }
  
  public static RefundableSheet getRefundableSheet()
  {
    return refundableSheet;
  }
  
  public static RefundSalesShoppingCart getShoppingCart()
  {
    return refundSalesShoppingCart;
  }
  
  public static boolean saveRefundSalesDocument(RefundSalesDocument paramRefundSalesDocument)
  {
    return new RefundSalesDoucumentDaoImpl().saveRefundSalesDocumentTransction(paramRefundSalesDocument);
  }
  
  public static void setRefundableSheet(RefundableSheet paramRefundableSheet)
  {
    refundableSheet = paramRefundableSheet;
  }
  
  public static void setShoppingCart(RefundSalesShoppingCart paramRefundSalesShoppingCart)
  {
    refundSalesShoppingCart = paramRefundSalesShoppingCart;
  }
  
  public List<SalesHistoryItem> getAllSalesHistoryItemOutOfThread()
  {
    return new SalesTransaction().getAllSalesHistoryItem();
  }
  
  public List<PaymentMethod> getPaymentMethodListOutUIThread()
  {
    if (this.paymentMethodList == null) {
      this.paymentMethodList = new PaymentMethodDaoImpl().getAllPaymentMethod();
    }
    return this.paymentMethodList;
  }
  
  public List<SalesHistoryItemDetail> getSalesHistoryItemDetailOutOfThread(long paramLong)
  {
    return new SalesTransaction().getSalesHistoryItemDetailByID(paramLong);
  }
  
  public void setPaymentMethodList(List<PaymentMethod> paramList)
  {
    this.paymentMethodList = paramList;
  }
}