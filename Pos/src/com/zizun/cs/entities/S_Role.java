package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="S_Role")
public class S_Role
  extends EntitySync
{
  private static final long serialVersionUID = 7812995582056813179L;
  @Column(column="Merchant_ID")
  @ID
  private int Merchant_ID;
  @Column(column="Role_Description")
  private String Role_Description;
  @Column(column="Role_ID")
  @ID
  private long Role_ID;
  @Column(column="Role_Name")
  private String Role_Name;
  @Column(column="Role_Status")
  private byte Role_Status;
  @Column(column="Role_Type")
  private byte Role_Type;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getRole_Description()
  {
    return this.Role_Description;
  }
  
  public long getRole_ID()
  {
    return this.Role_ID;
  }
  
  public String getRole_Name()
  {
    return this.Role_Name;
  }
  
  public byte getRole_Status()
  {
    return this.Role_Status;
  }
  
  public byte getRole_Type()
  {
    return this.Role_Type;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setRole_Description(String paramString)
  {
    this.Role_Description = paramString;
  }
  
  public void setRole_ID(long paramLong)
  {
    this.Role_ID = paramLong;
  }
  
  public void setRole_Name(String paramString)
  {
    this.Role_Name = paramString;
  }
  
  public void setRole_Status(byte paramByte)
  {
    this.Role_Status = paramByte;
  }
  
  public void setRole_Type(byte paramByte)
  {
    this.Role_Type = paramByte;
  }
}