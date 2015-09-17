package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="Store")
public class Store
  extends EntitySync
{
  private static final long serialVersionUID = -2870620513152183937L;
  @Column(column="Merchant_ID")
  @ID
  private int Merchant_ID;
  @Column(column="Store_Address")
  private String Store_Address;
  @Column(column="Store_Code")
  private String Store_Code;
  @Column(column="Store_Email")
  private String Store_Email;
  @Column(column="Store_ID")
  @ID
  private long Store_ID;
  @Column(column="Store_Name")
  private String Store_Name;
  @Column(column="Store_Phone")
  private String Store_Phone;
  @Column(column="Store_Remark")
  private String Store_Remark;
  @Column(column="Store_Status")
  private byte Store_Status;
  private byte Store_Type;
  @Column(column="Store_Zip")
  private String Store_Zip;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getStore_Address()
  {
    return this.Store_Address;
  }
  
  public String getStore_Code()
  {
    return this.Store_Code;
  }
  
  public String getStore_Email()
  {
    return this.Store_Email;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public String getStore_Name()
  {
    return this.Store_Name;
  }
  
  public String getStore_Phone()
  {
    return this.Store_Phone;
  }
  
  public String getStore_Remark()
  {
    return this.Store_Remark;
  }
  
  public byte getStore_Status()
  {
    return this.Store_Status;
  }
  
  public byte getStore_Type()
  {
    return this.Store_Type;
  }
  
  public String getStore_Zip()
  {
    return this.Store_Zip;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setStore_Address(String paramString)
  {
    this.Store_Address = paramString;
  }
  
  public void setStore_Code(String paramString)
  {
    this.Store_Code = paramString;
  }
  
  public void setStore_Email(String paramString)
  {
    this.Store_Email = paramString;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
  
  public void setStore_Name(String paramString)
  {
    this.Store_Name = paramString;
  }
  
  public void setStore_Phone(String paramString)
  {
    this.Store_Phone = paramString;
  }
  
  public void setStore_Remark(String paramString)
  {
    this.Store_Remark = paramString;
  }
  
  public void setStore_Status(byte paramByte)
  {
    this.Store_Status = paramByte;
  }
  
  public void setStore_Type(byte paramByte)
  {
    this.Store_Type = paramByte;
  }
  
  public void setStore_Zip(String paramString)
  {
    this.Store_Zip = paramString;
  }
}