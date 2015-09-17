package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;
import java.sql.Timestamp;

@Table(name="Reg_AppActive")
public class Reg_AppActive
  extends EntitySync
{
  private static final long serialVersionUID = -501574078825782057L;
  private Timestamp ActiveDate;
  @ID
  private byte AppID;
  private int AppMarket;
  @ID
  private String AppVersion;
  @ID
  private String MobileID;
  private String MobileStyle;
  @ID
  private byte MobileSystem;
  @ID
  private String SystemVersion;
  
  public Timestamp getActiveDate()
  {
    return this.ActiveDate;
  }
  
  public byte getAppID()
  {
    return this.AppID;
  }
  
  public int getAppMarket()
  {
    return this.AppMarket;
  }
  
  public String getAppVersion()
  {
    return this.AppVersion;
  }
  
  public String getMobileID()
  {
    return this.MobileID;
  }
  
  public String getMobileStyle()
  {
    return this.MobileStyle;
  }
  
  public byte getMobileSystem()
  {
    return this.MobileSystem;
  }
  
  public String getSystemVersion()
  {
    return this.SystemVersion;
  }
  
  public void setActiveDate(Timestamp paramTimestamp)
  {
    this.ActiveDate = paramTimestamp;
  }
  
  public void setAppID(byte paramByte)
  {
    this.AppID = paramByte;
  }
  
  public void setAppMarket(int paramInt)
  {
    this.AppMarket = paramInt;
  }
  
  public void setAppVersion(String paramString)
  {
    this.AppVersion = paramString;
  }
  
  public void setMobileID(String paramString)
  {
    this.MobileID = paramString;
  }
  
  public void setMobileStyle(String paramString)
  {
    this.MobileStyle = paramString;
  }
  
  public void setMobileSystem(byte paramByte)
  {
    this.MobileSystem = paramByte;
  }
  
  public void setSystemVersion(String paramString)
  {
    this.SystemVersion = paramString;
  }
}