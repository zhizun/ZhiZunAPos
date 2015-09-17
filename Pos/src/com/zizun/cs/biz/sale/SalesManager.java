package com.zizun.cs.biz.sale;

import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.dao.impl.PaymentMethodDaoImpl;
import com.zizun.cs.biz.dao.trans.SalesTrans;
import com.zizun.cs.biz.dao.trans.impl.SalesTransaction;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.biz.refund.V_SOHeader;
import com.zizun.cs.biz.refund.dao.V_SOHeaderDao;
import com.zizun.cs.biz.refund.dao.impl.V_SOHeaderDaoImpl;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.entities.TransactionLog;
import java.util.ArrayList;
import java.util.List;

public class SalesManager
{
  private static SalesManager mInstance;
  private static SalesShoppingCart salesShoppingCart;
  private PaymentMethodDao paymentMethodDao;
  private List<PaymentMethod> paymentMethodList;
  
  public static void clear()
  {
    mInstance = null;
  }
  
  public static void clearData()
  {
    salesShoppingCart = null;
  }
  
  public static boolean deleteSalesHistoryOutUIThread(long paramLong, ArrayList<TransactionLog> paramArrayList)
  {
    return new SalesTransaction().deleteSalesHistory(paramLong, paramArrayList);
  }
  
  public static ArrayList<V_ProductSkuOnSale> getAllProduct(int paramInt1, int paramInt2)
  {
    return new V_ProductSkuOnSaleDaoImpl().getAllProductSkuOnSale(paramInt1, paramInt2);
  }
  
  public static ArrayList<V_ProductSkuOnSale> getAllProduct(int paramInt1, int paramInt2, String paramString, long paramLong, SalesType paramSalesType)
  {
    return new V_ProductSkuOnSaleDaoImpl().getAllProductSkuOnSale(paramInt1, paramInt2, paramString, paramLong, paramSalesType);
  }
  
  public static ArrayList<V_ProductSkuOnSale> getAllProduct(SalesType paramSalesType, String paramString, int paramInt1, int paramInt2)
  {
    return new V_ProductSkuOnSaleDaoImpl().getAllProductSkuOnSaleOrderBySalesType(paramSalesType, paramString, paramInt1, paramInt2);
  }
  
  public static List<SalesHistoryItem> getAllSalesHistoryItemOutOfThread()
  {
    return new SalesTransaction().getAllSalesHistoryItem();
  }
  
  public static List<SalesHistoryItem> getAllSalesHistoryItemOutOfThread(int paramInt1, int paramInt2)
  {
    return new SalesTransaction().getAllSalesHistoryItem(paramInt1, paramInt2);
  }
  
  public static SalesManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new SalesManager();
    }
    return mInstance;
  }
  
  public static List<SalesHistoryItemDetail> getSalesHistoryItemDetailOutOfThread(long paramLong)
  {
    return new SalesTransaction().getSalesHistoryItemDetailByID(paramLong);
  }
  
  public static V_SOHeader getSalesHistorySO_header(long paramLong)
  {
    return new V_SOHeaderDaoImpl().getV_SOHeaderBySO_ID(paramLong);
  }
  
  public static List<V_TransactionDetail> getV_TransactionDetailListOutUIThread(long paramLong)
  {
    return new V_TransactionDetailDaoImpl().getALlV_TransactionDetailByTransID(paramLong);
  }
  
  public static V_TransactionHeader getV_TransactionHeaderOutUIThread(long paramLong)
  {
    return new V_TransactionHeaderDaoImpl().getV_TransactionHeaderByTransID(paramLong);
  }
  
  public static boolean hasDeleteSalesReturnOutUIThread(long paramLong)
  {
    return new SalesTransaction().hasSalesReturn(paramLong);
  }
  
  public static boolean hasDeleteSalesVoucherOutUIThread(long paramLong)
  {
    return new SalesTransaction().hasSalesVoucher(paramLong);
  }
  
  public static long saveSalesDocument(SalesDocument paramSalesDocument)
  {
    return new SalesTransaction().saveSalesProductTransction(paramSalesDocument);
  }
  
  public static void setSalesShoppingCart(SalesShoppingCart paramSalesShoppingCart)
  {
    salesShoppingCart = paramSalesShoppingCart;
  }
  
  public PaymentMethodDao getPaymentMethodDao()
  {
    if (this.paymentMethodDao == null) {
      this.paymentMethodDao = new PaymentMethodDaoImpl();
    }
    return this.paymentMethodDao;
  }
  
  public List<PaymentMethod> getPaymentMethodListOutUIThread(PaymentMethodDao paramPaymentMethodDao)
  {
    if (this.paymentMethodList == null) {
      this.paymentMethodList = paramPaymentMethodDao.getAllPaymentMethod();
    }
    return this.paymentMethodList;
  }
  
  public SalesShoppingCart getSalesShoppingCart()
  {
    return salesShoppingCart;
  }
  
  public void setPaymentMethodList(List<PaymentMethod> paramList)
  {
    this.paymentMethodList = paramList;
  }
}