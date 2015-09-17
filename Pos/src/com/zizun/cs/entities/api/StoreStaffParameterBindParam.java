package com.zizun.cs.entities.api;

public class StoreStaffParameterBindParam
{
  private int Create_ID;
  private int Merchant_ID;
  private int Modify_ID;
  private int Parameter_ID;
  private String Parameter_Value;
  private long Role_ID;
  private int Staff_ID;
  private long Store_ID;
  
  public StoreStaffParameterBindParam(int paramInt1, int paramInt2, long paramLong1, int paramInt3, long paramLong2, String paramString, int paramInt4, int paramInt5)
  {
    this.Merchant_ID = paramInt1;
    this.Parameter_ID = paramInt2;
    this.Store_ID = paramLong1;
    this.Staff_ID = paramInt3;
    this.Role_ID = paramLong2;
    this.Parameter_Value = paramString;
    this.Create_ID = paramInt4;
    this.Modify_ID = paramInt5;
  }
  
  public int getCreate_ID()
  {
    return this.Create_ID;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public int getModify_ID()
  {
    return this.Modify_ID;
  }
  
  public int getParameter_ID()
  {
    return this.Parameter_ID;
  }
  
  public String getParameter_Value()
  {
    return this.Parameter_Value;
  }
  
  public long getRole_ID()
  {
    return this.Role_ID;
  }
  
  public int getStaff_ID()
  {
    return this.Staff_ID;
  }
  
  public long getStore_ID()
  {
    return this.Store_ID;
  }
  
  public void setCreate_ID(int paramInt)
  {
    this.Create_ID = paramInt;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setModify_ID(int paramInt)
  {
    this.Modify_ID = paramInt;
  }
  
  public void setParameter_ID(int paramInt)
  {
    this.Parameter_ID = paramInt;
  }
  
  public void setParameter_Value(String paramString)
  {
    this.Parameter_Value = paramString;
  }
  
  public void setRole_ID(long paramLong)
  {
    this.Role_ID = paramLong;
  }
  
  public void setStaff_ID(int paramInt)
  {
    this.Staff_ID = paramInt;
  }
  
  public void setStore_ID(long paramLong)
  {
    this.Store_ID = paramLong;
  }
}