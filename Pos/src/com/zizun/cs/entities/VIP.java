package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import java.io.Serializable;

public class VIP
  extends EntitySync
  implements Serializable
{
  private static final long serialVersionUID = -7502547470856908330L;
  private byte IS_MerchantVIP;
  private int Merchant_ID;
  private long Supplier_ID;
  private long VIPGroup_ID;
  private String VIP_Address;
  private String VIP_Code;
  private String VIP_Contact;
  private String VIP_Email;
  private String VIP_FAX;
  @ID
  private long VIP_ID;
  private String VIP_Mobile;
  private String VIP_Name;
  private String VIP_Phone;
  private String VIP_QQ;
  private String VIP_Remark;
  private byte VIP_Status;
  private byte VIP_Type;
  private String VIP_WX;
  private String VIP_Zip;
  
  public byte getIS_MerchantVIP()
  {
    return this.IS_MerchantVIP;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public long getSupplier_ID()
  {
    return this.Supplier_ID;
  }
  
  public long getVIPGroup_ID()
  {
    return this.VIPGroup_ID;
  }
  
  public String getVIP_Address()
  {
    return this.VIP_Address;
  }
  
  public String getVIP_Code()
  {
    return this.VIP_Code;
  }
  
  public String getVIP_Contact()
  {
    return this.VIP_Contact;
  }
  
  public String getVIP_Email()
  {
    return this.VIP_Email;
  }
  
  public String getVIP_FAX()
  {
    return this.VIP_FAX;
  }
  
  public long getVIP_ID()
  {
    return this.VIP_ID;
  }
  
  public String getVIP_Mobile()
  {
    return this.VIP_Mobile;
  }
  
  public String getVIP_Name()
  {
    return this.VIP_Name;
  }
  
  public String getVIP_Phone()
  {
    return this.VIP_Phone;
  }
  
  public String getVIP_QQ()
  {
    return this.VIP_QQ;
  }
  
  public String getVIP_Remark()
  {
    return this.VIP_Remark;
  }
  
  public byte getVIP_Status()
  {
    return this.VIP_Status;
  }
  
  public byte getVIP_Type()
  {
    return this.VIP_Type;
  }
  
  public String getVIP_WX()
  {
    return this.VIP_WX;
  }
  
  public String getVIP_Zip()
  {
    return this.VIP_Zip;
  }
  
  public void setIS_MerchantVIP(byte paramByte)
  {
    this.IS_MerchantVIP = paramByte;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setSupplier_ID(long paramLong)
  {
    this.Supplier_ID = paramLong;
  }
  
  public void setVIPGroup_ID(long paramLong)
  {
    this.VIPGroup_ID = paramLong;
  }
  
  public void setVIP_Address(String paramString)
  {
    this.VIP_Address = paramString;
  }
  
  public void setVIP_Code(String paramString)
  {
    this.VIP_Code = paramString;
  }
  
  public void setVIP_Contact(String paramString)
  {
    this.VIP_Contact = paramString;
  }
  
  public void setVIP_Email(String paramString)
  {
    this.VIP_Email = paramString;
  }
  
  public void setVIP_FAX(String paramString)
  {
    this.VIP_FAX = paramString;
  }
  
  public void setVIP_ID(long paramLong)
  {
    this.VIP_ID = paramLong;
  }
  
  public void setVIP_Mobile(String paramString)
  {
    this.VIP_Mobile = paramString;
  }
  
  public void setVIP_Name(String paramString)
  {
    this.VIP_Name = paramString;
  }
  
  public void setVIP_Phone(String paramString)
  {
    this.VIP_Phone = paramString;
  }
  
  public void setVIP_QQ(String paramString)
  {
    this.VIP_QQ = paramString;
  }
  
  public void setVIP_Remark(String paramString)
  {
    this.VIP_Remark = paramString;
  }
  
  public void setVIP_Status(byte paramByte)
  {
    this.VIP_Status = paramByte;
  }
  
  public void setVIP_Type(byte paramByte)
  {
    this.VIP_Type = paramByte;
  }
  
  public void setVIP_WX(String paramString)
  {
    this.VIP_WX = paramString;
  }
  
  public void setVIP_Zip(String paramString)
  {
    this.VIP_Zip = paramString;
  }
  
  public String toString()
  {
    return "VIP [Merchant_ID=" + this.Merchant_ID + ", VIP_ID=" + this.VIP_ID + ", VIPGroup_ID=" + this.VIPGroup_ID + ", VIP_Code=" + this.VIP_Code + ", VIP_Name=" + this.VIP_Name + ", VIP_Phone=" + this.VIP_Phone + ", VIP_Contact=" + this.VIP_Contact + ", VIP_Mobile=" + this.VIP_Mobile + ", VIP_FAX=" + this.VIP_FAX + ", VIP_Email=" + this.VIP_Email + ", VIP_Zip=" + this.VIP_Zip + ", VIP_Address=" + this.VIP_Address + ", VIP_QQ=" + this.VIP_QQ + ", VIP_Remark=" + this.VIP_Remark + ", IS_MerchantVIP=" + this.IS_MerchantVIP + ", Supplier_ID=" + this.Supplier_ID + ", VIP_Status=" + this.VIP_Status + "]";
  }
}