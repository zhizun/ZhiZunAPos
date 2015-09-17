package com.zizun.cs.biz.action.sales;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.zizun.cs.biz.action.IAction;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.enumType.DiscountType;
import com.zizun.cs.biz.sale.SalesDocument;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.biz.sale.SalesShoppingCart;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.PaymentMethod;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SaveSalesDocumentAction
  implements IAction
{
  private static final int SALES_DOCUMENT_SAVE_FAIL = 18;
  private static final int SALES_DOCUMENT_SAVE_SUCCESS = 17;
  private Timestamp SO_Date;
  private long currentPaymentMethodID;
  private long customerID;
  private double discountAmount = 0.0D;
  private double discountPercent = 1.0D;
  private DiscountType discountType;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        if (SaveSalesDocumentAction.this.onSaveSalesDocumentActionListener != null)
        {
          SaveSalesDocumentAction.this.onSaveSalesDocumentActionListener.onSaveSalesDocumentSuccess(SaveSalesDocumentAction.this.transID);
          continue;
          if (SaveSalesDocumentAction.this.onSaveSalesDocumentActionListener != null) {
            SaveSalesDocumentAction.this.onSaveSalesDocumentActionListener.onSaveSalesDocumentFail("保存失败");
          }
        }
      }
    }
  });
  private OnSaveSalesDocumentActionListener onSaveSalesDocumentActionListener = null;
  private double paid = 0.0D;
  private SalesDocument salesDocument;
  private SalesShoppingCart salesShoppingCart;
  private long transID = -1L;
  
  public SaveSalesDocumentAction(SalesShoppingCart paramSalesShoppingCart, long paramLong)
  {
    this.salesShoppingCart = paramSalesShoppingCart;
    this.discountType = DiscountType.DISCOUNT_AMOUNT;
    this.discountAmount = 0.0D;
    this.discountPercent = 1.0D;
    this.paid = paramSalesShoppingCart.getTotalMoney();
    this.currentPaymentMethodID = paramLong;
    this.SO_Date = DateTimeUtil.getCurrentTime();
  }
  
  public SaveSalesDocumentAction(SalesShoppingCart paramSalesShoppingCart, DiscountType paramDiscountType, double paramDouble1, double paramDouble2, double paramDouble3, long paramLong1, long paramLong2, Timestamp paramTimestamp)
  {
    this.salesShoppingCart = paramSalesShoppingCart;
    this.discountType = paramDiscountType;
    this.discountAmount = paramDouble1;
    this.discountPercent = paramDouble2;
    this.currentPaymentMethodID = paramLong1;
    this.customerID = paramLong2;
    this.SO_Date = paramTimestamp;
    this.paid = paramDouble3;
  }
  
  private void getPaymentMethod()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Object localObject = SalesManager.getInstance().getPaymentMethodDao();
        localObject = (PaymentMethod)SalesManager.getInstance().getPaymentMethodListOutUIThread((PaymentMethodDao)localObject).get(0);
      }
    });
  }
  
  public void execute()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        SaveSalesDocumentAction.this.salesDocument = new SalesDocument();
        SaveSalesDocumentAction.this.salesDocument.setDiscountType(SaveSalesDocumentAction.this.discountType);
        SaveSalesDocumentAction.this.salesDocument.setDiscountAmount(SaveSalesDocumentAction.this.discountAmount);
        SaveSalesDocumentAction.this.salesDocument.setDiscountPercent(SaveSalesDocumentAction.this.discountPercent);
        SaveSalesDocumentAction.this.salesDocument.setPaid(SaveSalesDocumentAction.this.paid);
        SaveSalesDocumentAction.this.salesDocument.setCurrentPaymentMethodID(SaveSalesDocumentAction.this.currentPaymentMethodID);
        SaveSalesDocumentAction.this.salesDocument.setCustomerID(SaveSalesDocumentAction.this.customerID);
        SaveSalesDocumentAction.this.salesDocument.setSO_Date(SaveSalesDocumentAction.this.SO_Date);
        SaveSalesDocumentAction.this.salesDocument.initSalesDocument(SaveSalesDocumentAction.this.salesShoppingCart);
        if (SaveSalesDocumentAction.this.salesDocument.getSO_Header() != null)
        {
          SaveSalesDocumentAction.this.transID = SalesManager.saveSalesDocument(SaveSalesDocumentAction.this.salesDocument);
          if (SaveSalesDocumentAction.this.transID > 0L) {
            SaveSalesDocumentAction.this.handler.obtainMessage(17).sendToTarget();
          }
        }
        else
        {
          return;
        }
        SaveSalesDocumentAction.this.handler.obtainMessage(18).sendToTarget();
      }
    });
  }
  
  public void setOnSaveSalesDocumentActionListener(OnSaveSalesDocumentActionListener paramOnSaveSalesDocumentActionListener)
  {
    this.onSaveSalesDocumentActionListener = paramOnSaveSalesDocumentActionListener;
  }
  
  public static abstract interface OnSaveSalesDocumentActionListener
  {
    public abstract void onSaveSalesDocumentFail(String paramString);
    
    public abstract void onSaveSalesDocumentSuccess(long paramLong);
  }
}