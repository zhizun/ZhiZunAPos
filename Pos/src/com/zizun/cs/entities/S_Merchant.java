package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="S_Merchant")
public class S_Merchant
  extends EntitySync
{
  private static final long serialVersionUID = -623875966021085937L;
  @Column(column="Activity_Level")
  private int Activity_Level;
  @Column(column="AliPay_QR")
  private String AliPay_QR;
  @Column(column="Industry_ID")
  private byte Industry_ID;
  @Column(column="Merchant_Address")
  private String Merchant_Address;
  @Column(column="Merchant_Contact")
  private String Merchant_Contact;
  @Column(column="Merchant_Email")
  private String Merchant_Email;
  @Column(column="Merchant_ID")
  @ID
  private int Merchant_ID;
  @Column(column="Merchant_Image")
  private String Merchant_Image;
  @Column(column="Merchant_Mobile")
  private String Merchant_Mobile;
  @Column(column="Merchant_Name")
  private String Merchant_Name;
  @Column(column="Merchant_Phone")
  private String Merchant_Phone;
  @Column(column="Merchant_QQ")
  private String Merchant_QQ;
  @Column(column="Merchant_Remark")
  private String Merchant_Remark;
  @Column(column="Merchant_Status")
  private byte Merchant_Status;
  @Column(column="Recommend_ID")
  private int Recommend_ID;
  @Column(column="Recommend_Info")
  private String Recommend_Info;
  @Column(column="Remommend_Type")
  private byte Remommend_Type;
  @Column(column="User_ID")
  private int User_ID;
  @Column(column="WX_QR")
  private String WX_QR;
  
  public int getActivity_Level()
  {
    return this.Activity_Level;
  }
  
  public String getAliPay_QR()
  {
    return this.AliPay_QR;
  }
  
  public byte getIndustry_ID()
  {
    return this.Industry_ID;
  }
  
  public String getMerchant_Address()
  {
    return this.Merchant_Address;
  }
  
  public String getMerchant_Contact()
  {
    return this.Merchant_Contact;
  }
  
  public String getMerchant_Email()
  {
    return this.Merchant_Email;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getMerchant_Image()
  {
    return this.Merchant_Image;
  }
  
  public String getMerchant_Mobile()
  {
    return this.Merchant_Mobile;
  }
  
  public String getMerchant_Name()
  {
    return this.Merchant_Name;
  }
  
  public String getMerchant_Phone()
  {
    return this.Merchant_Phone;
  }
  
  public String getMerchant_QQ()
  {
    return this.Merchant_QQ;
  }
  
  public String getMerchant_Remark()
  {
    return this.Merchant_Remark;
  }
  
  public byte getMerchant_Status()
  {
    return this.Merchant_Status;
  }
  
  public int getRecommend_ID()
  {
    return this.Recommend_ID;
  }
  
  public String getRecommend_Info()
  {
    return this.Recommend_Info;
  }
  
  public byte getRemommend_Type()
  {
    return this.Remommend_Type;
  }
  
  public int getUser_ID()
  {
    return this.User_ID;
  }
  
  public String getWX_QR()
  {
    return this.WX_QR;
  }
  
  public void setActivity_Level(int paramInt)
  {
    this.Activity_Level = paramInt;
  }
  
  public void setAliPay_QR(String paramString)
  {
    this.AliPay_QR = paramString;
  }
  
  public void setIndustry_ID(byte paramByte)
  {
    this.Industry_ID = paramByte;
  }
  
  public void setMerchant_Address(String paramString)
  {
    this.Merchant_Address = paramString;
  }
  
  public void setMerchant_Contact(String paramString)
  {
    this.Merchant_Contact = paramString;
  }
  
  public void setMerchant_Email(String paramString)
  {
    this.Merchant_Email = paramString;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setMerchant_Image(String paramString)
  {
    this.Merchant_Image = paramString;
  }
  
  public void setMerchant_Mobile(String paramString)
  {
    this.Merchant_Mobile = paramString;
  }
  
  public void setMerchant_Name(String paramString)
  {
    this.Merchant_Name = paramString;
  }
  
  public void setMerchant_Phone(String paramString)
  {
    this.Merchant_Phone = paramString;
  }
  
  public void setMerchant_QQ(String paramString)
  {
    this.Merchant_QQ = paramString;
  }
  
  public void setMerchant_Remark(String paramString)
  {
    this.Merchant_Remark = paramString;
  }
  
  public void setMerchant_Status(byte paramByte)
  {
    this.Merchant_Status = paramByte;
  }
  
  public void setRecommend_ID(int paramInt)
  {
    this.Recommend_ID = paramInt;
  }
  
  public void setRecommend_Info(String paramString)
  {
    this.Recommend_Info = paramString;
  }
  
  public void setRemommend_Type(byte paramByte)
  {
    this.Remommend_Type = paramByte;
  }
  
  public void setUser_ID(int paramInt)
  {
    this.User_ID = paramInt;
  }
  
  public void setWX_QR(String paramString)
  {
    this.WX_QR = paramString;
  }
}