package com.zizun.cs.biz.sale;

import java.io.Serializable;
import java.sql.Timestamp;

public class V_TransactionHeader
  implements Serializable
{
  private static final long serialVersionUID = 7253764220434003188L;
  private long BusinessID;
  private String BusinessName;
  private double DiscountAmount;
  private double DiscountPercent;
  private String Merchant_Address;
  private int Merchant_ID;
  private String Merchant_Name;
  private String Merchant_Phone;
  private long Store_ID;
  private String Store_Name;
  private double TotalActualAmount;
  private double TotalPlanAmount;
  private double TotalQuantiy;
  private Timestamp TransDate;
  private long TransID;
  private String TransNumber;
  private long TransStaffID;
  private String TransStaffName;
  private byte TransStatus;
  private String TransType;
  private String TransTypeName;
  
  public long getBusinessID()
  {
    return this.BusinessID;
  }
  
  public String getBusinessName()
  {
    return this.BusinessName;
  }
  
  public double getDiscountAmount()
  {
    return this.DiscountAmount;
  }
  
  public double getDiscountPercent()
  {
    return this.DiscountPercent;
  }
  
  public String getMerchant_Address()
  {
    return this.Merchant_Address;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getMerchant_Name()
  {
    return this.Merchant_Name;
  }
  
  public String getMerchant_Phone()
  {
    return this.Merchant_Phone;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public String getStore_Name()
  {
    return this.Store_Name;
  }
  
  public double getTotalActualAmount()
  {
    return this.TotalActualAmount;
  }
  
  public double getTotalPlanAmount()
  {
    return this.TotalPlanAmount;
  }
  
  public double getTotalQuantiy()
  {
    return this.TotalQuantiy;
  }
  
  public Timestamp getTransDate()
  {
    return this.TransDate;
  }
  
  public long getTransID()
  {
    return this.TransID;
  }
  
  public String getTransNumber()
  {
    return this.TransNumber;
  }
  
  public long getTransStaffID()
  {
    return this.TransStaffID;
  }
  
  public String getTransStaffName()
  {
    return this.TransStaffName;
  }
  
  public byte getTransStatus()
  {
    return this.TransStatus;
  }
  
  public String getTransType()
  {
    return this.TransType;
  }
  
  public String getTransTypeName()
  {
    return this.TransTypeName;
  }
  
  public void setBusinessID(long paramLong)
  {
    this.BusinessID = paramLong;
  }
  
  public void setBusinessName(String paramString)
  {
    this.BusinessName = paramString;
  }
  
  public void setDiscountAmount(double paramDouble)
  {
    this.DiscountAmount = paramDouble;
  }
  
  public void setDiscountPercent(double paramDouble)
  {
    this.DiscountPercent = paramDouble;
  }
  
  public void setMerchant_Address(String paramString)
  {
    this.Merchant_Address = paramString;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setMerchant_Name(String paramString)
  {
    this.Merchant_Name = paramString;
  }
  
  public void setMerchant_Phone(String paramString)
  {
    this.Merchant_Phone = paramString;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setStore_Name(String paramString)
  {
    this.Store_Name = paramString;
  }
  
  public void setTotalActualAmount(double paramDouble)
  {
    this.TotalActualAmount = paramDouble;
  }
  
  public void setTotalPlanAmount(double paramDouble)
  {
    this.TotalPlanAmount = paramDouble;
  }
  
  public void setTotalQuantiy(double paramDouble)
  {
    this.TotalQuantiy = paramDouble;
  }
  
  public void setTransDate(Timestamp paramTimestamp)
  {
    this.TransDate = paramTimestamp;
  }
  
  public void setTransID(long paramLong)
  {
    this.TransID = paramLong;
  }
  
  public void setTransNumber(String paramString)
  {
    this.TransNumber = paramString;
  }
  
  public void setTransStaffID(long paramLong)
  {
    this.TransStaffID = paramLong;
  }
  
  public void setTransStaffName(String paramString)
  {
    this.TransStaffName = paramString;
  }
  
  public void setTransStatus(byte paramByte)
  {
    this.TransStatus = paramByte;
  }
  
  public void setTransType(String paramString)
  {
    this.TransType = paramString;
  }
  
  public void setTransTypeName(String paramString)
  {
    this.TransTypeName = paramString;
  }
}