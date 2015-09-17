package com.zizun.cs.biz.sale;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.biz.enumType.DiscountType;
import com.zizun.cs.biz.enumType.LineType;
import com.zizun.cs.biz.enumType.SOType;
import com.zizun.cs.biz.enumType.SO_Status;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.biz.enumType.SyncStatus;
import com.zizun.cs.biz.enumType.TransType;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.entities.SD_Discount;
import com.zizun.cs.entities.SD_Line;
import com.zizun.cs.entities.SD_Payment;
import com.zizun.cs.entities.SD_Product;
import com.zizun.cs.entities.SO_Header;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SalesDocument
{
  private SD_Discount SD_Discount;
  private List<SD_Line> SD_LineList;
  private SD_Payment SD_Payment;
  private List<SD_Product> SD_ProductList;
  private Timestamp SO_Date;
  private SO_Header SO_Header;
  private String SO_Remark = "";
  private List<S_Sync_Upload> S_Sync_UploadList;
  private List<TransactionLog> TransactionLogList;
  private long currentPaymentMethodID;
  private long customerID;
  private double discountAmount = 0.0D;
  private double discountPercent = 1.0D;
  private DiscountType discountType;
  private ArrayList<V_ProductSkuOnSale> documentItems;
  private V_ProductSkuOnSale maxSalesDocumentItem;
  private double paid;
  private double payable;
  private SalesType salesType;
  private double shoppingCartTotalAmount;
  
  private double getCalPayable()
  {
    double d = 0.0D;
    Iterator localIterator = this.documentItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return NumUtil.getNum(d);
      }
      d += ((V_ProductSkuOnSale)localIterator.next()).getAmountFinal();
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
  
  private void initDocumentData(SalesShoppingCart paramSalesShoppingCart)
  {
    this.SO_Remark = paramSalesShoppingCart.getSO_Remark();
    this.salesType = paramSalesShoppingCart.getSalesType();
    this.shoppingCartTotalAmount = paramSalesShoppingCart.getTotalMoney();
    if (this.discountType == DiscountType.DISCOUNT_PERCENT) {
      this.discountAmount = (this.shoppingCartTotalAmount * this.discountPercent);
    }
    double d = getPayable();
    this.documentItems = new ArrayList();
    paramSalesShoppingCart = paramSalesShoppingCart.getSaleShoppingCartItemList().iterator();
    for (;;)
    {
      if (!paramSalesShoppingCart.hasNext())
      {
        if ((this.documentItems.size() > 0) && (this.discountAmount != 0.0D))
        {
          d = NumUtil.getNum(getCalPayable() - d);
          if (d != 0.0D)
          {
            Collections.sort(this.documentItems);
            this.maxSalesDocumentItem = ((V_ProductSkuOnSale)this.documentItems.get(0));
            this.maxSalesDocumentItem.setAmountFinal(NumUtil.getNum(this.maxSalesDocumentItem.getAmountFinal() - d));
            this.maxSalesDocumentItem.setPriceSold(NumUtil.getNum6(NumUtil.getNum(this.maxSalesDocumentItem.getAmountFinal()) / this.maxSalesDocumentItem.getQuantiy()));
            this.documentItems.remove(0);
            this.documentItems.add(this.maxSalesDocumentItem);
          }
        }
        return;
      }
      V_ProductSkuOnSale localV_ProductSkuOnSale = (V_ProductSkuOnSale)paramSalesShoppingCart.next();
      localV_ProductSkuOnSale.initDocumentItem(this.shoppingCartTotalAmount, this.discountAmount);
      this.documentItems.add(localV_ProductSkuOnSale);
    }
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
      Object localObject = (V_ProductSkuOnSale)localIterator.next();
      SD_Line localSD_Line = initSD_Line(this.SO_Header.getSO_ID(), LineType.PRODUCT, i);
      SD_Product localSD_Product = initSD_Product((V_ProductSkuOnSale)localObject, this.SO_Header.getSO_ID(), i);
      localObject = initTransactionLog((V_ProductSkuOnSale)localObject, this.salesType, localSD_Product.getProductSku_ID());
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
    this.SD_Payment.setPaymentMethod_ID(this.currentPaymentMethodID);
    this.SD_Payment.setAmount(this.paid);
    this.SD_Payment.setCreateEntitySync(((S_User)localObject).getUser_ID());
    localObject = initSD_Line(this.SO_Header.getSO_ID(), LineType.PAYMENT, paramInt);
    this.SD_LineList.add(localObject);
  }
  
  private SD_Product initSD_Product(V_ProductSkuOnSale paramV_ProductSkuOnSale, long paramLong, int paramInt)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    SD_Product localSD_Product = new SD_Product();
    localSD_Product.setMerchant_ID(localS_User.getMerchant_ID());
    localSD_Product.setSP_ID(IDUtil.getID());
    localSD_Product.setSO_ID(paramLong);
    localSD_Product.setLine_ID(paramInt);
    localSD_Product.setProductSku_ID(paramV_ProductSkuOnSale.getProductSku_ID());
    localSD_Product.setSD_Quantiy(paramV_ProductSkuOnSale.getQuantiy());
    localSD_Product.setSD_Cost(paramV_ProductSkuOnSale.getFixed_Cost());
    localSD_Product.setSD_PriceOrignial(paramV_ProductSkuOnSale.getPriceOrignial());
    localSD_Product.setSD_PricePromotional(paramV_ProductSkuOnSale.getPricePromotional());
    localSD_Product.setSD_PriceSold(paramV_ProductSkuOnSale.getPriceSold());
    localSD_Product.setCreateEntitySync(localS_User.getUser_ID());
    return localSD_Product;
  }
  
  private void initSO_Header(SO_Status paramSO_Status)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    Store localStore = UserManager.getInstance().getCurrentStore();
    this.SO_Header = new SO_Header();
    this.SO_Header.setMerchant_ID(localS_User.getMerchant_ID());
    this.SO_Header.setSO_ID(IDUtil.getID());
    if (this.salesType == SalesType.SALE)
    {
      this.SO_Header.setSO_Number("SX" + this.SO_Header.getSO_ID());
      this.SO_Header.setSO_VIPID(this.customerID);
      this.SO_Header.setSO_StoreID(localStore.getStore_ID());
      this.SO_Header.setSO_Date(this.SO_Date);
      this.SO_Header.setSO_Cashier(localS_User.getUser_ID());
      this.SO_Header.setSO_Salesman(localS_User.getUser_ID());
      if (this.salesType != SalesType.SALE) {
        break label316;
      }
      this.SO_Header.setSO_Type(SOType.SALE.getValue());
      label164:
      if (paramSO_Status != SO_Status.SALE_SAVE) {
        break label342;
      }
      this.SO_Header.setSO_Status((byte)0);
      label179:
      this.SO_Header.setSO_Remark(this.SO_Remark);
      this.SO_Header.setSO_ProductAmount(this.payable);
      this.SO_Header.setSO_PlanAmount(this.payable);
      this.SO_Header.setSO_ActualAmount(this.paid);
      if (this.payable - this.paid != 0.0D) {
        break label360;
      }
      this.SO_Header.setIS_Settle((byte)2);
    }
    for (;;)
    {
      this.SO_Header.setCreateEntitySync(localS_User.getUser_ID());
      this.S_Sync_UploadList.add(initS_Sync_Upload(SO_Header.class));
      return;
      if (this.salesType != SalesType.WHOLESALE) {
        break;
      }
      this.SO_Header.setSO_Number("WX" + this.SO_Header.getSO_ID());
      break;
      label316:
      if (this.salesType != SalesType.WHOLESALE) {
        break label164;
      }
      this.SO_Header.setSO_Type(SOType.WHOLESALE.getValue());
      break label164;
      label342:
      if (paramSO_Status != SO_Status.SALE_STOCK_OUT) {
        break label179;
      }
      this.SO_Header.setSO_Status((byte)1);
      break label179;
      label360:
      this.SO_Header.setIS_Settle((byte)1);
    }
  }
  
  private S_Sync_Upload initS_Sync_Upload(Class<?> paramClass)
  {
    S_Sync_Upload localS_Sync_Upload = new S_Sync_Upload();
    localS_Sync_Upload.setSync_Status(SyncStatus.SYNC_NEED.getValue());
    localS_Sync_Upload.setTable_Name(paramClass.getSimpleName());
    return localS_Sync_Upload;
  }
  
  private TransactionLog initTransactionLog(V_ProductSkuOnSale paramV_ProductSkuOnSale, SalesType paramSalesType, long paramLong)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    TransactionLog localTransactionLog = new TransactionLog();
    localTransactionLog.setMerchant_ID(localS_User.getMerchant_ID());
    localTransactionLog.setTLog_ID(IDUtil.getID());
    localTransactionLog.setTLog_AffectStock((byte)1);
    localTransactionLog.setTLog_TransactionId(this.SO_Header.getSO_ID());
    localTransactionLog.setTLog_TransactionNumber(this.SO_Header.getSO_Number());
    if (paramSalesType == SalesType.SALE) {
      localTransactionLog.setTLog_TransactionType(TransType.SALE.getValue());
    }
    for (;;)
    {
      localTransactionLog.setTLog_PostingDate(this.SO_Header.getSO_Date());
      localTransactionLog.setTLog_StoreID(this.SO_Header.getSO_StoreID());
      localTransactionLog.setTLog_ProductSku(paramLong);
      localTransactionLog.setTLog_StockQuantity(paramV_ProductSkuOnSale.getQuantiy());
      localTransactionLog.setTLog_CostPrice(paramV_ProductSkuOnSale.getFixed_Cost());
      localTransactionLog.setTLog_PriceOrignial(paramV_ProductSkuOnSale.getPriceOrignial());
      localTransactionLog.setTLog_PricePromotional(paramV_ProductSkuOnSale.getPricePromotional());
      localTransactionLog.setTLog_PriceSold(paramV_ProductSkuOnSale.getPriceSold());
      localTransactionLog.setTLog_IsLocal((byte)1);
      localTransactionLog.setTLog_Status((byte)1);
      localTransactionLog.setCreateEntitySync(localS_User.getUser_ID());
      return localTransactionLog;
      if (paramSalesType == SalesType.WHOLESALE) {
        localTransactionLog.setTLog_TransactionType(TransType.WHOLESALE.getValue());
      }
    }
  }
  
  public void clearDocument()
  {
    if (this.documentItems != null) {
      this.documentItems.clear();
    }
    this.SO_Header = null;
    this.SD_LineList = null;
    this.SD_ProductList = null;
    this.TransactionLogList = null;
    this.SD_Discount = null;
    this.SD_Payment = null;
    this.S_Sync_UploadList = null;
    this.maxSalesDocumentItem = null;
    this.salesType = SalesType.SALE;
    this.shoppingCartTotalAmount = 0.0D;
    this.discountAmount = 0.0D;
    this.discountPercent = 1.0D;
    this.payable = 0.0D;
    this.paid = 0.0D;
    this.discountType = null;
    this.currentPaymentMethodID = 0L;
    this.customerID = 0L;
  }
  
  public long getCurrentPaymentMethodID()
  {
    return this.currentPaymentMethodID;
  }
  
  public long getCustomerID()
  {
    return this.customerID;
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
  
  public V_ProductSkuOnSale getMaxSalesDocumentItem()
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
  
  public String getSO_Remark()
  {
    return this.SO_Remark;
  }
  
  public List<S_Sync_Upload> getS_Sync_UploadList()
  {
    return this.S_Sync_UploadList;
  }
  
  public ArrayList<V_ProductSkuOnSale> getSalesDocumentItemList()
  {
    return this.documentItems;
  }
  
  public SalesType getSalesType()
  {
    return this.salesType;
  }
  
  public double getShoppingCartTotalAmount()
  {
    return this.shoppingCartTotalAmount;
  }
  
  public List<TransactionLog> getTransactionLogList()
  {
    return this.TransactionLogList;
  }
  
  public void initSalesDocument(SalesShoppingCart paramSalesShoppingCart)
  {
    initDocumentData(paramSalesShoppingCart);
    this.S_Sync_UploadList = new ArrayList();
    initSO_Header(SO_Status.SALE_STOCK_OUT);
    initDocumentItemTables();
  }
  
  public void setCurrentPaymentMethodID(long paramLong)
  {
    this.currentPaymentMethodID = paramLong;
  }
  
  public void setCustomerID(long paramLong)
  {
    this.customerID = paramLong;
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
  
  public void setMaxSalesDocumentItem(V_ProductSkuOnSale paramV_ProductSkuOnSale)
  {
    this.maxSalesDocumentItem = paramV_ProductSkuOnSale;
  }
  
  public void setPaid(double paramDouble)
  {
    this.paid = paramDouble;
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
  
  public void setSO_Remark(String paramString)
  {
    this.SO_Remark = paramString;
  }
  
  public void setS_Sync_UploadList(List<S_Sync_Upload> paramList)
  {
    this.S_Sync_UploadList = paramList;
  }
  
  public void setSalesType(SalesType paramSalesType)
  {
    this.salesType = paramSalesType;
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