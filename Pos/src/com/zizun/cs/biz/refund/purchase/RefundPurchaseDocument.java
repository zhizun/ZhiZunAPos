package com.zizun.cs.biz.refund.purchase;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.biz.enumType.RTVType;
import com.zizun.cs.biz.enumType.SyncStatus;
import com.zizun.cs.biz.enumType.TransType;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.entities.RTV_Detail;
import com.zizun.cs.entities.RTV_Header;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.entities.biz.Associate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RefundPurchaseDocument
{
  private List<RTV_Detail> RTV_DetailList;
  private RTV_Header RTV_Header;
  private Timestamp SO_Date;
  private List<S_Sync_Upload> S_Sync_UploadList;
  private List<TransactionLog> TransactionLogList;
  private ArrayList<RefundablePurchaseSheetItem> documentItems = new ArrayList();
  private double paid;
  private double payable;
  private RefundableSheet refundableSheet;
  private RTVType rtvType;
  private double shoppingCartTotalAmount;
  private Associate supplier;
  
  public RefundPurchaseDocument() {}
  
  public RefundPurchaseDocument(RefundPurchaseShoppingCart paramRefundPurchaseShoppingCart)
  {
    initRefundPurchaseShoppingCart(paramRefundPurchaseShoppingCart);
  }
  
  private void initDocumentItemTables()
  {
    this.RTV_DetailList = new ArrayList();
    this.TransactionLogList = new ArrayList();
    Iterator localIterator = this.documentItems.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.S_Sync_UploadList.add(initS_Sync_Upload(RTV_Detail.class));
        this.S_Sync_UploadList.add(initS_Sync_Upload(TransactionLog.class));
        return;
      }
      Object localObject = (RefundablePurchaseSheetItem)localIterator.next();
      RTV_Detail localRTV_Detail = initRTV_Detail((RefundablePurchaseSheetItem)localObject);
      localObject = initTransactionLog((RefundablePurchaseSheetItem)localObject, this.refundableSheet.getTransType(), ((RefundablePurchaseSheetItem)localObject).getProductSku_ID());
      this.RTV_DetailList.add(localRTV_Detail);
      this.TransactionLogList.add(localObject);
    }
  }
  
  private RTV_Detail initRTV_Detail(RefundablePurchaseSheetItem paramRefundablePurchaseSheetItem)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    RTV_Detail localRTV_Detail = new RTV_Detail();
    localRTV_Detail.setMerchant_ID(localS_User.getMerchant_ID());
    localRTV_Detail.setRD_ID(IDUtil.getID());
    localRTV_Detail.setRTV_ID(this.RTV_Header.getRTV_ID());
    localRTV_Detail.setProductSku_ID(paramRefundablePurchaseSheetItem.getProductSku_ID());
    localRTV_Detail.setRD_Quantiy(paramRefundablePurchaseSheetItem.getQuantiyPromotional());
    localRTV_Detail.setRD_PurchasePrice(paramRefundablePurchaseSheetItem.getPricePromotional());
    localRTV_Detail.setRD_CostPrice(paramRefundablePurchaseSheetItem.getOriginalCost());
    localRTV_Detail.setRD_RetailPrice(paramRefundablePurchaseSheetItem.getOriginalPrice());
    localRTV_Detail.setCreateEntitySync(localS_User.getUser_ID());
    return localRTV_Detail;
  }
  
  private void initRTV_Header()
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    Store localStore = UserManager.getInstance().getCurrentStore();
    this.RTV_Header = new RTV_Header();
    this.RTV_Header.setMerchant_ID(localS_User.getMerchant_ID());
    this.RTV_Header.setRTV_ID(IDUtil.getID());
    this.RTV_Header.setRTV_Number("PR" + this.RTV_Header.getRTV_ID());
    this.RTV_Header.setRTV_POID(this.refundableSheet.getTransID());
    if (this.supplier != null) {
      this.RTV_Header.setRTV_SupplierID(getSupplier().getAssciate_SupplierID());
    }
    this.RTV_Header.setRTV_StoreID(localStore.getStore_ID());
    this.RTV_Header.setRTV_Date(this.SO_Date);
    this.RTV_Header.setRTV_Staff(localS_User.getUser_ID());
    this.RTV_Header.setRTV_Status(this.rtvType.getValue());
    this.RTV_Header.setRTV_ProductAmount(this.payable);
    this.RTV_Header.setRTV_PlanAmount(this.payable);
    this.RTV_Header.setRTV_ActualAmount(this.paid);
    if (this.payable - this.paid == 0.0D) {
      this.RTV_Header.setIS_Settle((byte)2);
    }
    for (;;)
    {
      this.RTV_Header.setCreateEntitySync(localS_User.getUser_ID());
      this.S_Sync_UploadList.add(initS_Sync_Upload(RTV_Header.class));
      return;
      this.RTV_Header.setIS_Settle((byte)1);
    }
  }
  
  private S_Sync_Upload initS_Sync_Upload(Class<?> paramClass)
  {
    S_Sync_Upload localS_Sync_Upload = new S_Sync_Upload();
    localS_Sync_Upload.setSync_Status(SyncStatus.SYNC_NEED.getValue());
    localS_Sync_Upload.setTable_Name(paramClass.getSimpleName());
    return localS_Sync_Upload;
  }
  
  private TransactionLog initTransactionLog(RefundablePurchaseSheetItem paramRefundablePurchaseSheetItem, String paramString, long paramLong)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    TransactionLog localTransactionLog = new TransactionLog();
    localTransactionLog.setMerchant_ID(localS_User.getMerchant_ID());
    localTransactionLog.setTLog_ID(IDUtil.getID());
    localTransactionLog.setTLog_AffectStock((byte)1);
    localTransactionLog.setTLog_TransactionId(this.RTV_Header.getRTV_ID());
    localTransactionLog.setTLog_TransactionNumber(this.RTV_Header.getRTV_Number());
    if (paramString.equals("PX")) {
      localTransactionLog.setTLog_TransactionType(TransType.PURCHASE_RETURN.getValue());
    }
    localTransactionLog.setTLog_PostingDate(this.RTV_Header.getRTV_Date());
    localTransactionLog.setTLog_StoreID(this.RTV_Header.getRTV_StoreID());
    localTransactionLog.setTLog_ProductSku(paramLong);
    localTransactionLog.setTLog_StockQuantity(paramRefundablePurchaseSheetItem.getQuantiyPromotional());
    localTransactionLog.setTLog_CostPrice(paramRefundablePurchaseSheetItem.getOriginalCost());
    localTransactionLog.setTLog_PriceOrignial(0.0D);
    localTransactionLog.setTLog_PricePromotional(0.0D);
    localTransactionLog.setTLog_PriceSold(paramRefundablePurchaseSheetItem.getPriceSold());
    localTransactionLog.setTLog_IsLocal((byte)1);
    localTransactionLog.setTLog_Status((byte)1);
    localTransactionLog.setCreateEntitySync(localS_User.getUser_ID());
    return localTransactionLog;
  }
  
  public void clearDocument()
  {
    this.RTV_Header = null;
    this.RTV_DetailList = null;
    this.TransactionLogList = null;
    this.S_Sync_UploadList = null;
    this.payable = 0.0D;
    this.paid = 0.0D;
    this.supplier = null;
  }
  
  public double getPaid()
  {
    return this.paid;
  }
  
  public double getPayable()
  {
    this.payable = (this.shoppingCartTotalAmount - 0.0D);
    return this.payable;
  }
  
  public ArrayList<RefundablePurchaseSheetItem> getPurchaseDocumentItemList()
  {
    return this.documentItems;
  }
  
  public List<RTV_Detail> getRTV_DetailList()
  {
    return this.RTV_DetailList;
  }
  
  public RTV_Header getRTV_Header()
  {
    return this.RTV_Header;
  }
  
  public RefundableSheet getRefundableSheet()
  {
    return this.refundableSheet;
  }
  
  public RTVType getRtvType()
  {
    return this.rtvType;
  }
  
  public Timestamp getSO_Date()
  {
    return this.SO_Date;
  }
  
  public List<S_Sync_Upload> getS_Sync_UploadList()
  {
    return this.S_Sync_UploadList;
  }
  
  public Associate getSupplier()
  {
    return this.supplier;
  }
  
  public List<TransactionLog> getTransactionLogList()
  {
    return this.TransactionLogList;
  }
  
  public void initPurchaseDocument()
  {
    this.S_Sync_UploadList = new ArrayList();
    initRTV_Header();
    initDocumentItemTables();
  }
  
  public void initRefundPurchaseShoppingCart(RefundPurchaseShoppingCart paramRefundPurchaseShoppingCart)
  {
    this.documentItems.clear();
    this.shoppingCartTotalAmount = paramRefundPurchaseShoppingCart.getTotalMoney();
    getPayable();
    this.documentItems = new ArrayList();
    paramRefundPurchaseShoppingCart = paramRefundPurchaseShoppingCart.getShoppingCartItemList().iterator();
    for (;;)
    {
      if (!paramRefundPurchaseShoppingCart.hasNext()) {
        return;
      }
      RefundablePurchaseSheetItem localRefundablePurchaseSheetItem = (RefundablePurchaseSheetItem)paramRefundPurchaseShoppingCart.next();
      localRefundablePurchaseSheetItem.initDocumentItem();
      this.documentItems.add(localRefundablePurchaseSheetItem);
    }
  }
  
  public void setPaid(double paramDouble)
  {
    this.paid = paramDouble;
  }
  
  public void setRTV_DetailList(List<RTV_Detail> paramList)
  {
    this.RTV_DetailList = paramList;
  }
  
  public void setRTV_Header(RTV_Header paramRTV_Header)
  {
    this.RTV_Header = paramRTV_Header;
  }
  
  public void setRefundableSheet(RefundableSheet paramRefundableSheet)
  {
    this.refundableSheet = paramRefundableSheet;
  }
  
  public void setRtvType(RTVType paramRTVType)
  {
    this.rtvType = paramRTVType;
  }
  
  public void setSO_Date(Timestamp paramTimestamp)
  {
    this.SO_Date = paramTimestamp;
  }
  
  public void setS_Sync_UploadList(List<S_Sync_Upload> paramList)
  {
    this.S_Sync_UploadList = paramList;
  }
  
  public void setShoppingCart(RefundPurchaseShoppingCart paramRefundPurchaseShoppingCart)
  {
    initRefundPurchaseShoppingCart(paramRefundPurchaseShoppingCart);
  }
  
  public void setSupplier(Associate paramAssociate)
  {
    this.supplier = paramAssociate;
  }
  
  public void setTransactionLogList(List<TransactionLog> paramList)
  {
    this.TransactionLogList = paramList;
  }
}