package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;
import java.sql.Timestamp;

@Table(name="PO_Header")
public class PO_Header
  extends EntitySync
{
  private static final long serialVersionUID = 4059875986739369203L;
  private byte IS_Settle;
  private int Merchant_ID;
  private double PO_ActualAmount;
  private Timestamp PO_Date;
  @ID
  private long PO_ID;
  private String PO_Number;
  private double PO_OtherAmount;
  private double PO_PlanAmount;
  private double PO_ProductAmount;
  private String PO_Remark;
  private int PO_Staff;
  private byte PO_Status;
  private long PO_StoreID;
  private long PO_SupplierID;
  
  public byte getIS_Settle()
  {
    return this.IS_Settle;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getPO_ActualAmount()
  {
    return this.PO_ActualAmount;
  }
  
  public Timestamp getPO_Date()
  {
    return this.PO_Date;
  }
  
  public long getPO_ID()
  {
    return this.PO_ID;
  }
  
  public String getPO_Number()
  {
    return this.PO_Number;
  }
  
  public double getPO_OtherAmount()
  {
    return this.PO_OtherAmount;
  }
  
  public double getPO_PlanAmount()
  {
    return this.PO_PlanAmount;
  }
  
  public double getPO_ProductAmount()
  {
    return this.PO_ProductAmount;
  }
  
  public String getPO_Remark()
  {
    return this.PO_Remark;
  }
  
  public int getPO_Staff()
  {
    return this.PO_Staff;
  }
  
  public byte getPO_Status()
  {
    return this.PO_Status;
  }
  
  public long getPO_StoreID()
  {
    return this.PO_StoreID;
  }
  
  public long getPO_SupplierID()
  {
    return this.PO_SupplierID;
  }
  
  public void setIS_Settle(byte paramByte)
  {
    this.IS_Settle = paramByte;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setPO_ActualAmount(double paramDouble)
  {
    this.PO_ActualAmount = paramDouble;
  }
  
  public void setPO_Date(Timestamp paramTimestamp)
  {
    this.PO_Date = paramTimestamp;
  }
  
  public void setPO_ID(long paramLong)
  {
    this.PO_ID = paramLong;
  }
  
  public void setPO_Number(String paramString)
  {
    this.PO_Number = paramString;
  }
  
  public void setPO_OtherAmount(double paramDouble)
  {
    this.PO_OtherAmount = paramDouble;
  }
  
  public void setPO_PlanAmount(double paramDouble)
  {
    this.PO_PlanAmount = paramDouble;
  }
  
  public void setPO_ProductAmount(double paramDouble)
  {
    this.PO_ProductAmount = paramDouble;
  }
  
  public void setPO_Remark(String paramString)
  {
    this.PO_Remark = paramString;
  }
  
  public void setPO_Staff(int paramInt)
  {
    this.PO_Staff = paramInt;
  }
  
  public void setPO_Status(byte paramByte)
  {
    this.PO_Status = paramByte;
  }
  
  public void setPO_StoreID(long paramLong)
  {
    this.PO_StoreID = paramLong;
  }
  
  public void setPO_SupplierID(long paramLong)
  {
    this.PO_SupplierID = paramLong;
  }
}