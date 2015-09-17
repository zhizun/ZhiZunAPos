package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="S_User")
public class S_User
  extends EntitySync
{
  private static final long serialVersionUID = -594698069051192162L;
  @Column(column="Merchant_ID")
  private int Merchant_ID;
  @Column(column="User_Email")
  private String User_Email;
  @Id
  @NoAutoIncrement
  @ID
  private int User_ID;
  @Column(column="User_Mobile")
  private String User_Mobile;
  @Column(column="User_Name")
  private String User_Name;
  @Column(column="User_Password")
  private String User_Password;
  @Column(column="User_Status")
  private byte User_Status;
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public String getUser_Email()
  {
    return this.User_Email;
  }
  
  public int getUser_ID()
  {
    return this.User_ID;
  }
  
  public String getUser_Mobile()
  {
    return this.User_Mobile;
  }
  
  public String getUser_Name()
  {
    return this.User_Name;
  }
  
  public String getUser_Password()
  {
    return this.User_Password;
  }
  
  public byte getUser_Status()
  {
    return this.User_Status;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setUser_Email(String paramString)
  {
    this.User_Email = paramString;
  }
  
  public void setUser_ID(int paramInt)
  {
    this.User_ID = paramInt;
  }
  
  public void setUser_Mobile(String paramString)
  {
    this.User_Mobile = paramString;
  }
  
  public void setUser_Name(String paramString)
  {
    this.User_Name = paramString;
  }
  
  public void setUser_Password(String paramString)
  {
    this.User_Password = paramString;
  }
  
  public void setUser_Status(byte paramByte)
  {
    this.User_Status = paramByte;
  }
}