package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;
import java.sql.Timestamp;

@Table(name="Invent_Header")
public class Invent_Header
  extends EntitySync
{
  private static final long serialVersionUID = 3349533198992859376L;
  private Timestamp Invent_Date;
  @ID
  private long Invent_ID;
  private String Invent_Number;
  private String Invent_Remark;
  private int Invent_Staff;
  private byte Invent_Status;
  private long Invent_StoreID;
  private byte Invent_Type;
  private int Merchant_ID;
  
  public Timestamp getInvent_Date()
  {
    return this.Invent_Date;
  }
  
  public long getInvent_ID()
  {
    return this.Invent_ID;
  }
  
  public String getInvent_Number()
  {
    return this.Invent_Number;
  }
  
  public String getInvent_Remark()
  {
    return this.Invent_Remark;
  }
  
  public int getInvent_Staff()
  {
    return this.Invent_Staff;
  }
  
  public byte getInvent_Status()
  {
    return this.Invent_Status;
  }
  
  public long getInvent_StoreID()
  {
    return this.Invent_StoreID;
  }
  
  public byte getInvent_Type()
  {
    return this.Invent_Type;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public void setInvent_Date(Timestamp paramTimestamp)
  {
    this.Invent_Date = paramTimestamp;
  }
  
  public void setInvent_ID(long paramLong)
  {
    this.Invent_ID = paramLong;
  }
  
  public void setInvent_Number(String paramString)
  {
    this.Invent_Number = paramString;
  }
  
  public void setInvent_Remark(String paramString)
  {
    this.Invent_Remark = paramString;
  }
  
  public void setInvent_Staff(int paramInt)
  {
    this.Invent_Staff = paramInt;
  }
  
  public void setInvent_Status(byte paramByte)
  {
    this.Invent_Status = paramByte;
  }
  
  public void setInvent_StoreID(long paramLong)
  {
    this.Invent_StoreID = paramLong;
  }
  
  public void setInvent_Type(byte paramByte)
  {
    this.Invent_Type = paramByte;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
}