package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="ProductGroup")
public class ProductGroup
  extends EntitySync
{
  public static final int PRODUCT_GROUP_TYPE_CUSTOMER = 2;
  public static final int PRODUCT_GROUP_TYPE_SYSTEM = 1;
  private static final long serialVersionUID = 5074823624587078399L;
  private int Merchant_ID;
  @ID
  private long PG_ID;
  private String PG_Name;
  private byte PG_Status;
  private byte PG_Type;
  private long Parent_ID;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public long getPG_ID()
  {
    return this.PG_ID;
  }
  
  public String getPG_Name()
  {
    return this.PG_Name;
  }
  
  public byte getPG_Status()
  {
    return this.PG_Status;
  }
  
  public byte getPG_Type()
  {
    return this.PG_Type;
  }
  
  public long getParent_ID()
  {
    return this.Parent_ID;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setPG_ID(long paramLong)
  {
    this.PG_ID = paramLong;
  }
  
  public void setPG_Name(String paramString)
  {
    this.PG_Name = paramString;
  }
  
  public void setPG_Status(byte paramByte)
  {
    this.PG_Status = paramByte;
  }
  
  public void setPG_Type(byte paramByte)
  {
    this.PG_Type = paramByte;
  }
  
  public void setParent_ID(long paramLong)
  {
    this.Parent_ID = paramLong;
  }
}