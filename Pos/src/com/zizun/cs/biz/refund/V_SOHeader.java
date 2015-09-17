package com.zizun.cs.biz.refund;

import java.io.Serializable;
import java.sql.Timestamp;

public class V_SOHeader
  implements Serializable
{
  private static final long serialVersionUID = -284778568559059271L;
  private double Discount_Amount;
  private double Discount_Percent;
  private int Discount_Type;
  private byte IS_Settle;
  private long Merchant_ID;
  private double SO_ActualAmount;
  private Timestamp SO_Date;
  private long SO_ID;
  private String SO_Number;
  private double SO_PlanAmount;
  private byte SO_Status;
  private byte SO_Type;
  private long Staff_ID;
  private String Staff_Name;
  private long Store_ID;
  private String Store_Name;
  private double TotalQuantiy;
  private String VIP_Contact;
  private long VIP_ID;
  private String VIP_Name;
  
  public double getDiscount_Amount()
  {
    return this.Discount_Amount;
  }
  
  public double getDiscount_Percent()
  {
    return this.Discount_Percent;
  }
  
  public int getDiscount_Type()
  {
    return this.Discount_Type;
  }
  
  public byte getIS_Settle()
  {
    return this.IS_Settle;
  }
  
  public long getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getSO_ActualAmount()
  {
    return this.SO_ActualAmount;
  }
  
  public Timestamp getSO_Date()
  {
    return this.SO_Date;
  }
  
  public long getSO_ID()
  {
    return this.SO_ID;
  }
  
  public String getSO_Number()
  {
    return this.SO_Number;
  }
  
  public double getSO_PlanAmount()
  {
    return this.SO_PlanAmount;
  }
  
  public byte getSO_Status()
  {
    return this.SO_Status;
  }
  
  public byte getSO_Type()
  {
    return this.SO_Type;
  }
  
  public long getStaff_ID()
  {
    return this.Staff_ID;
  }
  
  public String getStaff_Name()
  {
    return this.Staff_Name;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public String getStore_Name()
  {
    return this.Store_Name;
  }
  
  public double getTotalQuantiy()
  {
    return this.TotalQuantiy;
  }
  
  public String getVIP_Contact()
  {
    return this.VIP_Contact;
  }
  
  public long getVIP_ID()
  {
    return this.VIP_ID;
  }
  
  public String getVIP_Name()
  {
    return this.VIP_Name;
  }
  
  public void setDiscount_Amount(double paramDouble)
  {
    this.Discount_Amount = paramDouble;
  }
  
  public void setDiscount_Percent(double paramDouble)
  {
    this.Discount_Percent = paramDouble;
  }
  
  public void setDiscount_Type(int paramInt)
  {
    this.Discount_Type = paramInt;
  }
  
  public void setIS_Settle(byte paramByte)
  {
    this.IS_Settle = paramByte;
  }
  
  public void setMerchant_ID(long paramLong)
  {
    this.Merchant_ID = paramLong;
  }
  
  public void setSO_ActualAmount(double paramDouble)
  {
    this.SO_ActualAmount = paramDouble;
  }
  
  public void setSO_Date(Timestamp paramTimestamp)
  {
    this.SO_Date = paramTimestamp;
  }
  
  public void setSO_ID(long paramLong)
  {
    this.SO_ID = paramLong;
  }
  
  public void setSO_Number(String paramString)
  {
    this.SO_Number = paramString;
  }
  
  public void setSO_PlanAmount(double paramDouble)
  {
    this.SO_PlanAmount = paramDouble;
  }
  
  public void setSO_Status(byte paramByte)
  {
    this.SO_Status = paramByte;
  }
  
  public void setSO_Type(byte paramByte)
  {
    this.SO_Type = paramByte;
  }
  
  public void setStaff_ID(long paramLong)
  {
    this.Staff_ID = paramLong;
  }
  
  public void setStaff_Name(String paramString)
  {
    this.Staff_Name = paramString;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setStore_Name(String paramString)
  {
    this.Store_Name = paramString;
  }
  
  public void setTotalQuantiy(double paramDouble)
  {
    this.TotalQuantiy = paramDouble;
  }
  
  public void setVIP_Contact(String paramString)
  {
    this.VIP_Contact = paramString;
  }
  
  public void setVIP_ID(long paramLong)
  {
    this.VIP_ID = paramLong;
  }
  
  public void setVIP_Name(String paramString)
  {
    this.VIP_Name = paramString;
  }
}