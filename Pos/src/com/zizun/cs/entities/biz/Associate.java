package com.zizun.cs.entities.biz;

import java.io.Serializable;
import java.sql.Timestamp;

public class Associate
  implements Serializable
{
  private static final long serialVersionUID = 1295478383927575821L;
  private long Assciate_SupplierID;
  private long Assciate_VIPID;
  private long Associate_ID;
  private byte Associate_Status;
  private long Associate_SupplierGroupID;
  private long Associate_VIPGroupID;
  private Timestamp Associate_createDT;
  private int Associate_createID;
  private Timestamp Associate_syDT;
  private String Company;
  private byte IS_Merchant;
  private byte VIPType;
  private String address;
  private String code;
  private String email;
  private String fax;
  private boolean isChecked;
  private boolean isCustomer;
  private boolean isSupplier;
  private String mobile;
  private String name;
  private String phone;
  private String qq;
  private String weixin;
  private String zip;
  
  public String getAddress()
  {
    return this.address;
  }
  
  public long getAssciate_SupplierID()
  {
    return this.Assciate_SupplierID;
  }
  
  public long getAssciate_VIPID()
  {
    return this.Assciate_VIPID;
  }
  
  public long getAssociate_ID()
  {
    return this.Associate_ID;
  }
  
  public byte getAssociate_Status()
  {
    return this.Associate_Status;
  }
  
  public long getAssociate_SupplierGroupID()
  {
    return this.Associate_SupplierGroupID;
  }
  
  public long getAssociate_VIPGroupID()
  {
    return this.Associate_VIPGroupID;
  }
  
  public Timestamp getAssociate_createDT()
  {
    return this.Associate_createDT;
  }
  
  public int getAssociate_createID()
  {
    return this.Associate_createID;
  }
  
  public Timestamp getAssociate_syDT()
  {
    return this.Associate_syDT;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public String getCompany()
  {
    return this.Company;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public byte getIS_Merchant()
  {
    return this.IS_Merchant;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public String getQq()
  {
    return this.qq;
  }
  
  public byte getVIPType()
  {
    return this.VIPType;
  }
  
  public String getWeixin()
  {
    return this.weixin;
  }
  
  public String getZip()
  {
    return this.zip;
  }
  
  public boolean isChecked()
  {
    return this.isChecked;
  }
  
  public boolean isCustomer()
  {
    return this.isCustomer;
  }
  
  public boolean isSupplier()
  {
    return this.isSupplier;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString;
  }
  
  public void setAssciate_SupplierID(long paramLong)
  {
    this.Assciate_SupplierID = paramLong;
  }
  
  public void setAssciate_VIPID(long paramLong)
  {
    this.Assciate_VIPID = paramLong;
  }
  
  public void setAssociate_ID(long paramLong)
  {
    this.Associate_ID = paramLong;
  }
  
  public void setAssociate_Status(byte paramByte)
  {
    this.Associate_Status = paramByte;
  }
  
  public void setAssociate_SupplierGroupID(long paramLong)
  {
    this.Associate_SupplierGroupID = paramLong;
  }
  
  public void setAssociate_VIPGroupID(long paramLong)
  {
    this.Associate_VIPGroupID = paramLong;
  }
  
  public void setAssociate_createDT(Timestamp paramTimestamp)
  {
    this.Associate_createDT = paramTimestamp;
  }
  
  public void setAssociate_createID(int paramInt)
  {
    this.Associate_createID = paramInt;
  }
  
  public void setAssociate_syDT(Timestamp paramTimestamp)
  {
    this.Associate_syDT = paramTimestamp;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public void setCompany(String paramString)
  {
    this.Company = paramString;
  }
  
  public void setCustomer(boolean paramBoolean)
  {
    this.isCustomer = paramBoolean;
  }
  
  public void setEmail(String paramString)
  {
    this.email = paramString;
  }
  
  public void setFax(String paramString)
  {
    this.fax = paramString;
  }
  
  public void setIS_Merchant(byte paramByte)
  {
    this.IS_Merchant = paramByte;
  }
  
  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
  
  public void setQq(String paramString)
  {
    this.qq = paramString;
  }
  
  public void setSupplier(boolean paramBoolean)
  {
    this.isSupplier = paramBoolean;
  }
  
  public void setVIPType(byte paramByte)
  {
    this.VIPType = paramByte;
  }
  
  public void setWeixin(String paramString)
  {
    this.weixin = paramString;
  }
  
  public void setZip(String paramString)
  {
    this.zip = paramString;
  }
  
  public String toString()
  {
    return "Associate [Company=" + this.Company + ", name=" + this.name + ", phone=" + this.phone + ", mobile=" + this.mobile + ", code=" + this.code + ", address=" + this.address + ", fax=" + this.fax + ", qq=" + this.qq + ", email=" + this.email + ", Associate_Status=" + this.Associate_Status + ", VIPType=" + this.VIPType + ", Associate_createID=" + this.Associate_createID + ", Associate_createDT=" + this.Associate_createDT + ", Associate_syDT=" + this.Associate_syDT + ", Associate_ID=" + this.Associate_ID + ", isChecked=" + this.isChecked + ", isCustomer=" + this.isCustomer + ", isSupplier=" + this.isSupplier + ", Assciate_VIPID=" + this.Assciate_VIPID + ", Assciate_SupplierID=" + this.Assciate_SupplierID + ", weixin=" + this.weixin + ", zip=" + this.zip + ", Associate_VIPGroupID=" + this.Associate_VIPGroupID + ", Associate_SupplierGroupID=" + this.Associate_SupplierGroupID + ", IS_Merchant=" + this.IS_Merchant + "]";
  }
}