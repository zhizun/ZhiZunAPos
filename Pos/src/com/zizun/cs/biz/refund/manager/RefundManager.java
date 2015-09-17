package com.zizun.cs.biz.refund.manager;

import com.zizun.cs.biz.dao.trans.RefundTrans;
import com.zizun.cs.biz.dao.trans.impl.RefundTransaction;
import com.zizun.cs.biz.refund.RefundHistorySheet;
import com.zizun.cs.biz.refund.RefundPurchaseHistoryItem;
import com.zizun.cs.biz.refund.RefundSalesHistoryItem;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.V_RTVHeader;
import com.zizun.cs.biz.refund.V_SOHeader;
import com.zizun.cs.biz.refund.dao.RefundHistoryItemDao;
import com.zizun.cs.biz.refund.dao.RefundPurchaseHistoryItemDao;
import com.zizun.cs.biz.refund.dao.RefundSalesHistoryItemDao;
import com.zizun.cs.biz.refund.dao.RefundablePurchaseSheetItemDao;
import com.zizun.cs.biz.refund.dao.RefundableSalesSheetItemDao;
import com.zizun.cs.biz.refund.dao.RefundableSheetDao;
import com.zizun.cs.biz.refund.dao.V_RTVHeaderDao;
import com.zizun.cs.biz.refund.dao.V_SOHeaderDao;
import com.zizun.cs.biz.refund.dao.impl.RefundHistoryItemDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.RefundPurchaseHistoryItemDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.RefundSalesHistoryItemDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.RefundablePurchaseSheetItemDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.RefundableSalesSheetItemDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.RefundableSheetDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.V_RTVHeaderDaoImpl;
import com.zizun.cs.biz.refund.dao.impl.V_SOHeaderDaoImpl;
import com.zizun.cs.biz.refund.purchase.RefundablePurchaseSheetItem;
import com.zizun.cs.biz.refund.sales.RefundSalesManager;
import com.zizun.cs.biz.refund.sales.RefundableSalesSheetItem;
import com.zizun.cs.entities.TransactionLog;
import java.util.ArrayList;

public class RefundManager
{
  public static boolean deleteRefundPurchaseHistory(long paramLong, ArrayList<TransactionLog> paramArrayList)
  {
    return new RefundTransaction().deleteRefundPurchaseHistory(paramLong, paramArrayList);
  }
  
  public static boolean deleteRefundSalesHistory(long paramLong, ArrayList<TransactionLog> paramArrayList)
  {
    return new RefundTransaction().deleteRefundSalesHistory(paramLong, paramArrayList);
  }
  
  public static ArrayList<RefundHistorySheet> getAllRefundHistoryItemOutUIThread(int paramInt1, int paramInt2)
  {
    return (ArrayList)new RefundHistoryItemDaoImpl().getAllRefundHistoryItems(paramInt1, paramInt2);
  }
  
  public static ArrayList<RefundPurchaseHistoryItem> getAllRefundPurchaseHistoryItemOutUIThread(long paramLong)
  {
    return (ArrayList)new RefundPurchaseHistoryItemDaoImpl().getAllRefundPurchaseHistoryItem(paramLong);
  }
  
  public static ArrayList<RefundSalesHistoryItem> getAllRefundSalesHistoryItemOutUIThread(long paramLong)
  {
    return (ArrayList)new RefundSalesHistoryItemDaoImpl().getAllRefundSalesHistoryItem(paramLong);
  }
  
  public static ArrayList<RefundablePurchaseSheetItem> getAllRefundablePurchaseSheetItemsOutUIThread(long paramLong)
  {
    return (ArrayList)new RefundablePurchaseSheetItemDaoImpl().getAllRefundablePurchaseSheetItems(paramLong);
  }
  
  public static ArrayList<RefundableSalesSheetItem> getAllRefundableSalesSheetItemsOutUIThread(long paramLong)
  {
    return (ArrayList)new RefundableSalesSheetItemDaoImpl().getAllRefundableSalesSheetItems(paramLong);
  }
  
  public static ArrayList<RefundableSheet> getAllRefundableSheetsOutUIThread(int paramInt1, int paramInt2)
  {
    return (ArrayList)new RefundableSheetDaoImpl().getAllRefundableSheets(paramInt1, paramInt2);
  }
  
  public static RefundSalesManager getRefundSalesManager()
  {
    return RefundSalesManager.getInstance();
  }
  
  public static V_RTVHeader getV_RTVHeaderOutUIThread(long paramLong)
  {
    return new V_RTVHeaderDaoImpl().getV_RTVHeaderByRTV_ID(paramLong);
  }
  
  public static V_SOHeader getV_SOHeaderOutUIThread(long paramLong)
  {
    return new V_SOHeaderDaoImpl().getV_SOHeaderBySO_ID(paramLong);
  }
  
  public static boolean hasDeleteRefundPurchaseVoucherOutUIThread(long paramLong)
  {
    return new RefundTransaction().hasPurchaseRefundVoucher(paramLong);
  }
  
  public static boolean hasDeleteRefundSalesVoucherOutUIThread(long paramLong)
  {
    return new RefundTransaction().hasSalesRefundVoucher(paramLong);
  }
}