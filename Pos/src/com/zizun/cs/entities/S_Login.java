package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import java.sql.Timestamp;

@Table(name="S_Login")
public class S_Login
  extends EntitySync
{
  private static final long serialVersionUID = 4922759073931082197L;
  @Column(column="Login_DT")
  private Timestamp Login_DT;
  @Column(column="Login_ID")
  private long Login_ID;
  @Column(column="Merchant_ID")
  private int Merchant_ID;
  @Column(column="Terminal_Type")
  private byte Terminal_Type;
  @Column(column="Terminal_Version")
  private String Terminal_Version;
  @Column(column="User_ID")
  private int User_ID;
  
  public S_Login() {}
  
  public S_Login(int paramInt1, long paramLong, int paramInt2, Timestamp paramTimestamp, byte paramByte, String paramString)
  {
    this.Merchant_ID = paramInt1;
    this.Login_ID = paramLong;
    this.User_ID = paramInt2;
    this.Login_DT = paramTimestamp;
    this.Terminal_Type = paramByte;
    this.Terminal_Version = paramString;
  }
  
  public Timestamp getLogin_DT()
  {
    return this.Login_DT;
  }
  
  public long getLogin_ID()
  {
    return this.Login_ID;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public byte getTerminal_Type()
  {
    return this.Terminal_Type;
  }
  
  public String getTerminal_Version()
  {
    return this.Terminal_Version;
  }
  
  public int getUser_ID()
  {
    return this.User_ID;
  }
  
  public void setLogin_DT(Timestamp paramTimestamp)
  {
    this.Login_DT = paramTimestamp;
  }
  
  public void setLogin_ID(long paramLong)
  {
    this.Login_ID = paramLong;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setTerminal_Type(byte paramByte)
  {
    this.Terminal_Type = paramByte;
  }
  
  public void setTerminal_Version(String paramString)
  {
    this.Terminal_Version = paramString;
  }
  
  public void setUser_ID(int paramInt)
  {
    this.User_ID = paramInt;
  }
}