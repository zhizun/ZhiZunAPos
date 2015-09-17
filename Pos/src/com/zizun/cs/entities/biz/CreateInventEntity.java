package com.zizun.cs.entities.biz;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.Invent_Detail;
import com.zizun.cs.entities.Invent_Header;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.Invent;

public class CreateInventEntity
{
  private TransactionLog TLog;
  private Invent_Detail detail;
  private Invent_Header header;
  private Invent mInvent;
  
  public CreateInventEntity(Invent paramInvent)
  {
    this.mInvent = paramInvent;
    setInvent();
  }
  
  private void setInvent()
  {
    int i = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    long l = UserManager.getInstance().getCurrentStore().getStore_ID();
    int j = UserManager.getInstance().getCurrentUser().getUser_ID();
    this.header = new Invent_Header();
    this.header.setMerchant_ID(i);
    this.header.setInvent_ID(IDUtil.getID());
    this.header.setInvent_Number("TC" + this.header.getInvent_ID());
    this.header.setInvent_StoreID(l);
    String str = this.mInvent.getTime();
    if (this.mInvent.isIsCurtime()) {
      this.header.setInvent_Date(DateTimeUtil.getTimestampFromString(str));
    }
    for (;;)
    {
      this.header.setInvent_Staff(j);
      this.header.setInvent_Status((byte)1);
      this.header.setInvent_Type((byte)1);
      this.header.setCreateEntitySync(j);
      this.detail = new Invent_Detail();
      this.detail.setMerchant_ID(i);
      this.detail.setID_ID(IDUtil.getID());
      this.detail.setInvent_ID(this.header.getInvent_ID());
      this.detail.setSequence_ID(1);
      this.detail.setProductSku_ID(this.mInvent.getGoods().getSkuId());
      this.detail.setOnhand_Disk(this.mInvent.getGoods().getAmount());
      this.detail.setOnhand_Scan(this.mInvent.getOnhand_Scan());
      double d = this.detail.getOnhand_Scan() - this.detail.getOnhand_Disk();
      this.detail.setQuantity_Adjust(d);
      this.detail.setUnit_Price(this.mInvent.getGoods().getSellPrice());
      this.detail.setUnit_Cost(this.mInvent.getGoods().getCostPrice());
      this.detail.setCreateEntitySync(j);
      if ((d > 0.0D) || (d < 0.0D))
      {
        this.TLog = new TransactionLog();
        this.TLog.setMerchant_ID(i);
        this.TLog.setTLog_ID(IDUtil.getID());
        this.TLog.setTLog_AffectStock((byte)1);
        this.TLog.setTLog_TransactionId(this.header.getInvent_ID());
        this.TLog.setTLog_TransactionNumber(this.header.getInvent_Number());
        this.TLog.setTLog_TransactionType(7);
        this.TLog.setTLog_PostingDate(this.header.getInvent_Date());
        this.TLog.setTLog_StoreID(this.header.getInvent_StoreID());
        this.TLog.setTLog_ProductSku(this.detail.getProductSku_ID());
        this.TLog.setTLog_StockQuantity(d);
        this.TLog.setTLog_CostPrice(this.mInvent.getGoods().getCostPrice());
        this.TLog.setTLog_PriceOrignial(0.0D);
        this.TLog.setTLog_PricePromotional(0.0D);
        this.TLog.setTLog_PriceSold(this.mInvent.getGoods().getSellPrice());
        this.TLog.setTLog_IsLocal((byte)1);
        this.TLog.setTLog_Status((byte)1);
        this.TLog.setCreateEntitySync(j);
      }
      return;
      this.header.setInvent_Date(DateTimeUtil.getSimpleTimestampFromString(str));
    }
  }
  
  public Invent_Detail getDetail()
  {
    return this.detail;
  }
  
  public Invent_Header getHeader()
  {
    return this.header;
  }
  
  public TransactionLog getTLog()
  {
    return this.TLog;
  }
  
  public void setDetail(Invent_Detail paramInvent_Detail)
  {
    this.detail = paramInvent_Detail;
  }
  
  public void setHeader(Invent_Header paramInvent_Header)
  {
    this.header = paramInvent_Header;
  }
  
  public void setTLog(TransactionLog paramTransactionLog)
  {
    this.TLog = paramTransactionLog;
  }
}