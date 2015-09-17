package com.zizun.cs.entities.api;

public class StoreUpdateParam
{
  public int Merchant_ID;
  public int Modify_ID;
  public String Store_Address;
  public String Store_Code;
  public String Store_Email;
  public long Store_ID;
  public String Store_Name;
  public String Store_Phone;
  public String Store_Remark;
  public int Store_Status;
  public String Store_Zip;
  
  public StoreUpdateParam() {}
  
  public StoreUpdateParam(int paramInt1, long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, byte paramByte, int paramInt2, int paramInt3)
  {
    this.Merchant_ID = paramInt1;
    this.Store_ID = paramLong;
    this.Store_Code = paramString1;
    this.Store_Zip = paramString2;
    this.Store_Name = paramString3;
    this.Store_Address = paramString4;
    this.Store_Phone = paramString5;
    this.Store_Email = paramString6;
    this.Store_Remark = paramString7;
    this.Modify_ID = paramInt2;
    this.Store_Status = paramInt3;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public int getModify_ID()
  {
    return this.Modify_ID;
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
  
  public int getStore_Status()
  {
    return this.Store_Status;
  }
  
  public String getStore_Zip()
  {
    return this.Store_Zip;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setModify_ID(int paramInt)
  {
    this.Modify_ID = paramInt;
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
  
  public void setStore_Status(int paramInt)
  {
    this.Store_Status = paramInt;
  }
  
  public void setStore_Zip(String paramString)
  {
    this.Store_Zip = paramString;
  }
}