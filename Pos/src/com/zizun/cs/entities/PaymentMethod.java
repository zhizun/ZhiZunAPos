package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;

@Table(name="PaymentMethod")
public class PaymentMethod
  extends EntitySync
{
  private static final long serialVersionUID = -2424980435617254813L;
  private int Merchant_ID;
  private String PaymentMethod_Code;
  @ID
  private long PaymentMethod_ID;
  private String PaymentMethod_Name;
  private int PaymentMethod_Order;
  private String PaymentMethod_Remark;
  private byte PaymentMethod_Status;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getPaymentMethod_Code()
  {
    return this.PaymentMethod_Code;
  }
  
  public long getPaymentMethod_ID()
  {
    return this.PaymentMethod_ID;
  }
  
  public String getPaymentMethod_Name()
  {
    return this.PaymentMethod_Name;
  }
  
  public int getPaymentMethod_Order()
  {
    return this.PaymentMethod_Order;
  }
  
  public String getPaymentMethod_Remark()
  {
    return this.PaymentMethod_Remark;
  }
  
  public byte getPaymentMethod_Status()
  {
    return this.PaymentMethod_Status;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setPaymentMethod_Code(String paramString)
  {
    this.PaymentMethod_Code = paramString;
  }
  
  public void setPaymentMethod_ID(long paramLong)
  {
    this.PaymentMethod_ID = paramLong;
  }
  
  public void setPaymentMethod_Name(String paramString)
  {
    this.PaymentMethod_Name = paramString;
  }
  
  public void setPaymentMethod_Order(int paramInt)
  {
    this.PaymentMethod_Order = paramInt;
  }
  
  public void setPaymentMethod_Remark(String paramString)
  {
    this.PaymentMethod_Remark = paramString;
  }
  
  public void setPaymentMethod_Status(byte paramByte)
  {
    this.PaymentMethod_Status = paramByte;
  }
}