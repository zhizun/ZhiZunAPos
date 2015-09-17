package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;
import java.sql.Timestamp;

@Table(name="TransactionLog")
public class TransactionLog
  extends EntitySync
{
  private static final long serialVersionUID = 8005247521753278606L;
  @ID
  private int Merchant_ID;
  private byte TLog_AffectStock;
  private double TLog_CostPrice;
  @ID
  private long TLog_ID;
  private byte TLog_IsLocal;
  private Timestamp TLog_PostingDate;
  private double TLog_PriceOrignial;
  private double TLog_PricePromotional;
  private double TLog_PriceSold;
  private long TLog_ProductSku;
  private byte TLog_Status;
  private double TLog_StockQuantity;
  private long TLog_StoreID;
  private long TLog_TransactionId;
  private String TLog_TransactionNumber;
  private int TLog_TransactionType;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public byte getTLog_AffectStock()
  {
    return this.TLog_AffectStock;
  }
  
  public double getTLog_CostPrice()
  {
    return this.TLog_CostPrice;
  }
  
  public long getTLog_ID()
  {
    return this.TLog_ID;
  }
  
  public byte getTLog_IsLocal()
  {
    return this.TLog_IsLocal;
  }
  
  public Timestamp getTLog_PostingDate()
  {
    return this.TLog_PostingDate;
  }
  
  public double getTLog_PriceOrignial()
  {
    return this.TLog_PriceOrignial;
  }
  
  public double getTLog_PricePromotional()
  {
    return this.TLog_PricePromotional;
  }
  
  public double getTLog_PriceSold()
  {
    return this.TLog_PriceSold;
  }
  
  public long getTLog_ProductSku()
  {
    return this.TLog_ProductSku;
  }
  
  public byte getTLog_Status()
  {
    return this.TLog_Status;
  }
  
  public double getTLog_StockQuantity()
  {
    return this.TLog_StockQuantity;
  }
  
  public long getTLog_StoreID()
  {
    return this.TLog_StoreID;
  }
  
  public long getTLog_TransactionId()
  {
    return this.TLog_TransactionId;
  }
  
  public String getTLog_TransactionNumber()
  {
    return this.TLog_TransactionNumber;
  }
  
  public int getTLog_TransactionType()
  {
    return this.TLog_TransactionType;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setTLog_AffectStock(byte paramByte)
  {
    this.TLog_AffectStock = paramByte;
  }
  
  public void setTLog_CostPrice(double paramDouble)
  {
    this.TLog_CostPrice = paramDouble;
  }
  
  public void setTLog_ID(long paramLong)
  {
    this.TLog_ID = paramLong;
  }
  
  public void setTLog_IsLocal(byte paramByte)
  {
    this.TLog_IsLocal = paramByte;
  }
  
  public void setTLog_PostingDate(Timestamp paramTimestamp)
  {
    this.TLog_PostingDate = paramTimestamp;
  }
  
  public void setTLog_PriceOrignial(double paramDouble)
  {
    this.TLog_PriceOrignial = paramDouble;
  }
  
  public void setTLog_PricePromotional(double paramDouble)
  {
    this.TLog_PricePromotional = paramDouble;
  }
  
  public void setTLog_PriceSold(double paramDouble)
  {
    this.TLog_PriceSold = paramDouble;
  }
  
  public void setTLog_ProductSku(long paramLong)
  {
    this.TLog_ProductSku = paramLong;
  }
  
  public void setTLog_Status(byte paramByte)
  {
    this.TLog_Status = paramByte;
  }
  
  public void setTLog_StockQuantity(double paramDouble)
  {
    this.TLog_StockQuantity = paramDouble;
  }
  
  public void setTLog_StoreID(long paramLong)
  {
    this.TLog_StoreID = paramLong;
  }
  
  public void setTLog_TransactionId(long paramLong)
  {
    this.TLog_TransactionId = paramLong;
  }
  
  public void setTLog_TransactionNumber(String paramString)
  {
    this.TLog_TransactionNumber = paramString;
  }
  
  public void setTLog_TransactionType(int paramInt)
  {
    this.TLog_TransactionType = paramInt;
  }
}