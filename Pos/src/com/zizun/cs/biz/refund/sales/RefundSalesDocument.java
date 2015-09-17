package com.zizun.cs.biz.refund.sales;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.biz.enumType.DiscountType;
import com.zizun.cs.biz.enumType.LineType;
import com.zizun.cs.biz.enumType.SOType;
import com.zizun.cs.biz.enumType.SO_Status;
import com.zizun.cs.biz.enumType.SyncStatus;
import com.zizun.cs.biz.enumType.TransType;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.entities.SD_Discount;
import com.zizun.cs.entities.SD_Line;
import com.zizun.cs.entities.SD_Payment;
import com.zizun.cs.entities.SD_Product;
import com.zizun.cs.entities.SO_Header;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.entities.biz.Associate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RefundSalesDocument
{
  private SD_Discount SD_Discount;
  private List<SD_Line> SD_LineList;
  private SD_Payment SD_Payment;
  private List<SD_Product> SD_ProductList;
  private Timestamp SO_Date;
  private SO_Header SO_Header;
  private List<S_Sync_Upload> S_Sync_UploadList;
  private List<TransactionLog> TransactionLogList;
  private PaymentMethod currentPaymentMethod;
  private Associate customer;
  private double discountAmount;
  private double discountPercent;
  private DiscountType discountType;
  private ArrayList<RefundableSalesSheetItem> documentItems = new ArrayList();
  private RefundableSalesSheetItem maxSalesDocumentItem;
  private double paid;
  private double payable;
  private RefundableSheet refundableSheet;
  private double shoppingCartTotalAmount;
  
  public RefundSalesDocument() {}
  
  public RefundSalesDocument(RefundSalesShoppingCart paramRefundSalesShoppingCart)
  {
    initRefundSalesShoppingCart(paramRefundSalesShoppingCart);
  }
  
  private double getCalPayable()
  {
    double d = 0.0D;
    Iterator localIterator = this.documentItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return NumUtil.getNum(d);
      }
      d += ((RefundableSalesSheetItem)localIterator.next()).getAmountFinal();
    }
  }
  
  private void initDiscount(int paramInt)
  {
    Object localObject = UserManager.getInstance().getCurrentUser();
    this.SD_Discount = new SD_Discount();
    this.SD_Discount.setMerchant_ID(((S_User)localObject).getMerchant_ID());
    this.SD_Discount.setSD_ID(IDUtil.getID());
    this.SD_Discount.setSO_ID(this.SO_Header.getSO_ID());
    this.SD_Discount.setLine_ID(paramInt);
    this.SD_Discount.setIS_Global((byte)1);
    this.SD_Discount.setDiscount_Type(this.discountType.getValue());
    this.SD_Discount.setDiscount_Amount(this.discountAmount);
    this.SD_Discount.setDiscount_Percent(this.discountPercent);
    this.SD_Discount.setCreateEntitySync(((S_User)localObject).getUser_ID());
    localObject = initSD_Line(this.SO_Header.getSO_ID(), LineType.DISCOUNT, paramInt);
    this.SD_LineList.add(localObject);
  }
  
  private void initDocumentItemTables()
  {
    int i = 1;
    this.SD_LineList = new ArrayList();
    this.SD_ProductList = new ArrayList();
    this.TransactionLogList = new ArrayList();
    Iterator localIterator = this.documentItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.S_Sync_UploadList.add(initS_Sync_Upload(SD_Line.class));
        this.S_Sync_UploadList.add(initS_Sync_Upload(SD_Product.class));
        this.S_Sync_UploadList.add(initS_Sync_Upload(TransactionLog.class));
        initDiscount(i);
        this.S_Sync_UploadList.add(initS_Sync_Upload(SD_Discount.class));
        initSD_Payment(i + 1);
        this.S_Sync_UploadList.add(initS_Sync_Upload(SD_Payment.class));
        return;
      }
      Object localObject = (RefundableSalesSheetItem)localIterator.next();
      SD_Line localSD_Line = initSD_Line(this.SO_Header.getSO_ID(), LineType.PRODUCT, i);
      SD_Product localSD_Product = initSD_Product((RefundableSalesSheetItem)localObject, this.SO_Header.getSO_ID(), i);
      localObject = initTransactionLog((RefundableSalesSheetItem)localObject, this.refundableSheet.getTransType(), localSD_Product.getProductSku_ID());
      this.SD_LineList.add(localSD_Line);
      this.SD_ProductList.add(localSD_Product);
      this.TransactionLogList.add(localObject);
      i += 1;
    }
  }
  
  private SD_Line initSD_Line(long paramLong, LineType paramLineType, int paramInt)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    SD_Line localSD_Line = new SD_Line();
    localSD_Line.setMerchant_ID(localS_User.getMerchant_ID());
    localSD_Line.setSL_ID(IDUtil.getID());
    localSD_Line.setSO_ID(paramLong);
    localSD_Line.setLine_ID(paramInt);
    localSD_Line.setLine_Type((byte)paramLineType.getValue());
    localSD_Line.setCreateEntitySync(localS_User.getUser_ID());
    return localSD_Line;
  }
  
  private void initSD_Payment(int paramInt)
  {
    Object localObject = UserManager.getInstance().getCurrentUser();
    this.SD_Payment = new SD_Payment();
    this.SD_Payment.setMerchant_ID(((S_User)localObject).getMerchant_ID());
    this.SD_Payment.setSP_ID(IDUtil.getID());
    this.SD_Payment.setSO_ID(this.SO_Header.getSO_ID());
    this.SD_Payment.setLine_ID(paramInt);
    this.SD_Payment.setPaymentMethod_ID(this.currentPaymentMethod.getPaymentMethod_ID());
    this.SD_Payment.setAmount(this.paid);
    this.SD_Payment.setCreateEntitySync(((S_User)localObject).getUser_ID());
    localObject = initSD_Line(this.SO_Header.getSO_ID(), LineType.PAYMENT, paramInt);
    this.SD_LineList.add(localObject);
  }
  
  private SD_Product initSD_Product(RefundableSalesSheetItem paramRefundableSalesSheetItem, long paramLong, int paramInt)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    SD_Product localSD_Product = new SD_Product();
    localSD_Product.setMerchant_ID(localS_User.getMerchant_ID());
    localSD_Product.setSP_ID(IDUtil.getID());
    localSD_Product.setSO_ID(paramLong);
    localSD_Product.setLine_ID(paramInt);
    localSD_Product.setProductSku_ID(paramRefundableSalesSheetItem.getProductSku_ID());
    localSD_Product.setSD_Quantiy(paramRefundableSalesSheetItem.getQuantiyPromotional());
    localSD_Product.setSD_Cost(paramRefundableSalesSheetItem.getOriginalCost());
    localSD_Product.setSD_PriceOrignial(paramRefundableSalesSheetItem.getOriginalPrice());
    localSD_Product.setSD_PricePromotional(paramRefundableSalesSheetItem.getPricePromotional());
    localSD_Product.setSD_PriceSold(paramRefundableSalesSheetItem.getPriceSold());
    localSD_Product.setCreateEntitySync(localS_User.getUser_ID());
    return localSD_Product;
  }
  
  private S_Sync_Upload initS_Sync_Upload(Class<?> paramClass)
  {
    S_Sync_Upload localS_Sync_Upload = new S_Sync_Upload();
    localS_Sync_Upload.setSync_Status(SyncStatus.SYNC_NEED.getValue());
    localS_Sync_Upload.setTable_Name(paramClass.getSimpleName());
    return localS_Sync_Upload;
  }
  
  private TransactionLog initTransactionLog(RefundableSalesSheetItem paramRefundableSalesSheetItem, String paramString, long paramLong)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    TransactionLog localTransactionLog = new TransactionLog();
    localTransactionLog.setMerchant_ID(localS_User.getMerchant_ID());
    localTransactionLog.setTLog_ID(IDUtil.getID());
    localTransactionLog.setTLog_AffectStock((byte)1);
    localTransactionLog.setTLog_TransactionId(this.SO_Header.getSO_ID());
    localTransactionLog.setTLog_TransactionNumber(this.SO_Header.getSO_Number());
    if (paramString.equals("SX")) {
      localTransactionLog.setTLog_TransactionType(TransType.SALE_RETURN.getValue());
    }
    for (;;)
    {
      localTransactionLog.setTLog_PostingDate(this.SO_Header.getSO_Date());
      localTransactionLog.setTLog_StoreID(this.SO_Header.getSO_StoreID());
      localTransactionLog.setTLog_ProductSku(paramLong);
      localTransactionLog.setTLog_StockQuantity(paramRefundableSalesSheetItem.getQuantiyPromotional());
      localTransactionLog.setTLog_CostPrice(paramRefundableSalesSheetItem.getOriginalCost());
      localTransactionLog.setTLog_PriceOrignial(paramRefundableSalesSheetItem.getOriginalPrice());
      localTransactionLog.setTLog_PricePromotional(paramRefundableSalesSheetItem.getPricePromotional());
      localTransactionLog.setTLog_PriceSold(paramRefundableSalesSheetItem.getPriceSold());
      localTransactionLog.setTLog_IsLocal((byte)1);
      localTransactionLog.setTLog_Status((byte)1);
      localTransactionLog.setCreateEntitySync(localS_User.getUser_ID());
      return localTransactionLog;
      if (paramString.equals("WX")) {
        localTransactionLog.setTLog_TransactionType(TransType.WHOLESALE_RETURN.getValue());
      }
    }
  }
  
  public void clearDocument()
  {
    this.SO_Header = null;
    this.SD_LineList = null;
    this.SD_ProductList = null;
    this.TransactionLogList = null;
    this.SD_Discount = null;
    this.SD_Payment = null;
    this.S_Sync_UploadList = null;
    this.maxSalesDocumentItem = null;
    this.shoppingCartTotalAmount = 0.0D;
    this.discountAmount = 0.0D;
    this.discountPercent = 1.0D;
    this.payable = 0.0D;
    this.paid = 0.0D;
    this.discountType = null;
    this.currentPaymentMethod = null;
    this.customer = null;
  }
  
  public PaymentMethod getCurrentPaymentMethod()
  {
    return this.currentPaymentMethod;
  }
  
  public Associate getCustomer()
  {
    return this.customer;
  }
  
  public double getDiscountAmount()
  {
    return this.discountAmount;
  }
  
  public double getDiscountPercent()
  {
    return this.discountPercent;
  }
  
  public DiscountType getDiscountType()
  {
    return this.discountType;
  }
  
  public RefundableSalesSheetItem getMaxSalesDocumentItem()
  {
    return this.maxSalesDocumentItem;
  }
  
  public double getPaid()
  {
    return this.paid;
  }
  
  public double getPayable()
  {
    this.payable = (this.shoppingCartTotalAmount - this.discountAmount);
    return this.payable;
  }
  
  public RefundableSheet getRefundableSheet()
  {
    return this.refundableSheet;
  }
  
  public SD_Discount getSD_Discount()
  {
    return this.SD_Discount;
  }
  
  public List<SD_Line> getSD_LineList()
  {
    return this.SD_LineList;
  }
  
  public SD_Payment getSD_Payment()
  {
    return this.SD_Payment;
  }
  
  public List<SD_Product> getSD_ProductList()
  {
    return this.SD_ProductList;
  }
  
  public Timestamp getSO_Date()
  {
    return this.SO_Date;
  }
  
  public SO_Header getSO_Header()
  {
    return this.SO_Header;
  }
  
  public List<S_Sync_Upload> getS_Sync_UploadList()
  {
    return this.S_Sync_UploadList;
  }
  
  public ArrayList<RefundableSalesSheetItem> getSalesDocumentItemList()
  {
    return this.documentItems;
  }
  
  public double getShoppingCartTotalAmount()
  {
    return this.shoppingCartTotalAmount;
  }
  
  public List<TransactionLog> getTransactionLogList()
  {
    return this.TransactionLogList;
  }
  
  public void initRefundSalesShoppingCart(RefundSalesShoppingCart paramRefundSalesShoppingCart)
  {
    this.documentItems.clear();
    this.shoppingCartTotalAmount = paramRefundSalesShoppingCart.getTotalMoney();
    if (this.discountType == DiscountType.DISCOUNT_PERCENT) {
      this.discountAmount = (this.shoppingCartTotalAmount * this.discountPercent);
    }
    double d = getPayable();
    this.documentItems = new ArrayList();
    paramRefundSalesShoppingCart = paramRefundSalesShoppingCart.getSaleShoppingCartItemList().iterator();
    for (;;)
    {
      if (!paramRefundSalesShoppingCart.hasNext())
      {
        if ((this.documentItems.size() > 0) && (this.discountAmount != 0.0D))
        {
          d = NumUtil.getNum(getCalPayable() - d);
          if (d != 0.0D)
          {
            Collections.sort(this.documentItems);
            this.maxSalesDocumentItem = ((RefundableSalesSheetItem)this.documentItems.get(0));
            this.maxSalesDocumentItem.setAmountFinal(NumUtil.getNum(this.maxSalesDocumentItem.getAmountFinal() - d));
            this.maxSalesDocumentItem.setPriceSold(NumUtil.getNum6(NumUtil.getNum(this.maxSalesDocumentItem.getAmountFinal()) / this.maxSalesDocumentItem.getQuantiyPromotional()));
            this.documentItems.remove(0);
            this.documentItems.add(this.maxSalesDocumentItem);
          }
        }
        return;
      }
      RefundableSalesSheetItem localRefundableSalesSheetItem = (RefundableSalesSheetItem)paramRefundSalesShoppingCart.next();
      localRefundableSalesSheetItem.initDocumentItem(d, this.discountAmount);
      this.documentItems.add(localRefundableSalesSheetItem);
    }
  }
  
  public void initSO_Header(SO_Status paramSO_Status)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    Store localStore = UserManager.getInstance().getCurrentStore();
    this.SO_Header = new SO_Header();
    this.SO_Header.setMerchant_ID(localS_User.getMerchant_ID());
    this.SO_Header.setSO_ID(IDUtil.getID());
    if (this.refundableSheet.getTransType().equals("SX"))
    {
      this.SO_Header.setSO_Number("SR" + this.SO_Header.getSO_ID());
      this.SO_Header.setSO_ReturnID(this.refundableSheet.getTransID());
      if (this.customer == null) {
        break label347;
      }
      this.SO_Header.setSO_VIPID(getCustomer().getAssciate_VIPID());
      label127:
      this.SO_Header.setSO_StoreID(localStore.getStore_ID());
      this.SO_Header.setSO_Date(this.SO_Date);
      this.SO_Header.setSO_Cashier(localS_User.getUser_ID());
      this.SO_Header.setSO_Salesman(localS_User.getUser_ID());
      if (!this.refundableSheet.getTransType().equals("SX")) {
        break label358;
      }
      this.SO_Header.setSO_Type(SOType.SALE_RETURN_WITH_RECEIPT.getValue());
      label200:
      if (paramSO_Status != SO_Status.SALE_SAVE) {
        break label390;
      }
      this.SO_Header.setSO_Status((byte)0);
      label215:
      this.SO_Header.setSO_ProductAmount(this.payable);
      this.SO_Header.setSO_PlanAmount(this.payable);
      this.SO_Header.setSO_ActualAmount(this.paid);
      if (this.payable - this.paid != 0.0D) {
        break label408;
      }
      this.SO_Header.setIS_Settle((byte)2);
    }
    for (;;)
    {
      this.SO_Header.setCreateEntitySync(localS_User.getUser_ID());
      this.S_Sync_UploadList.add(initS_Sync_Upload(SO_Header.class));
      return;
      if (!this.refundableSheet.getTransType().equals("WX")) {
        break;
      }
      this.SO_Header.setSO_Number("WR" + this.SO_Header.getSO_ID());
      break;
      label347:
      this.SO_Header.setSO_VIPID(0L);
      break label127;
      label358:
      if (!this.refundableSheet.getTransType().equals("WX")) {
        break label200;
      }
      this.SO_Header.setSO_Type(SOType.WHOLESALE_RETURN_WITH_RECEIPT.getValue());
      break label200;
      label390:
      if (paramSO_Status != SO_Status.SALE_STOCK_OUT) {
        break label215;
      }
      this.SO_Header.setSO_Status((byte)1);
      break label215;
      label408:
      this.SO_Header.setIS_Settle((byte)1);
    }
  }
  
  public void initSalesDocument()
  {
    this.S_Sync_UploadList = new ArrayList();
    initSO_Header(SO_Status.SALE_STOCK_OUT);
    initDocumentItemTables();
  }
  
  public void setCurrentPaymentMethod(PaymentMethod paramPaymentMethod)
  {
    this.currentPaymentMethod = paramPaymentMethod;
  }
  
  public void setCustomer(Associate paramAssociate)
  {
    this.customer = paramAssociate;
  }
  
  public void setDiscountAmount(double paramDouble)
  {
    this.discountAmount = paramDouble;
  }
  
  public void setDiscountPercent(double paramDouble)
  {
    this.discountPercent = paramDouble;
  }
  
  public void setDiscountType(DiscountType paramDiscountType)
  {
    this.discountType = paramDiscountType;
  }
  
  public void setMaxSalesDocumentItem(RefundableSalesSheetItem paramRefundableSalesSheetItem)
  {
    this.maxSalesDocumentItem = paramRefundableSalesSheetItem;
  }
  
  public void setPaid(double paramDouble)
  {
    this.paid = paramDouble;
  }
  
  public void setRefundableSheet(RefundableSheet paramRefundableSheet)
  {
    this.refundableSheet = paramRefundableSheet;
  }
  
  public void setSD_Discount(SD_Discount paramSD_Discount)
  {
    this.SD_Discount = paramSD_Discount;
  }
  
  public void setSD_LineList(List<SD_Line> paramList)
  {
    this.SD_LineList = paramList;
  }
  
  public void setSD_Payment(SD_Payment paramSD_Payment)
  {
    this.SD_Payment = paramSD_Payment;
  }
  
  public void setSD_ProductList(List<SD_Product> paramList)
  {
    this.SD_ProductList = paramList;
  }
  
  public void setSO_Date(Timestamp paramTimestamp)
  {
    this.SO_Date = paramTimestamp;
  }
  
  public void setSO_Header(SO_Header paramSO_Header)
  {
    this.SO_Header = paramSO_Header;
  }
  
  public void setS_Sync_UploadList(List<S_Sync_Upload> paramList)
  {
    this.S_Sync_UploadList = paramList;
  }
  
  public void setShoppingCartTotalAmount(double paramDouble)
  {
    this.shoppingCartTotalAmount = paramDouble;
  }
  
  public void setTransactionLogList(List<TransactionLog> paramList)
  {
    this.TransactionLogList = paramList;
  }
}