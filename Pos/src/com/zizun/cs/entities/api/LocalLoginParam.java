package com.zizun.cs.entities.api;

import com.zizun.cs.entities.EntityBase;

public class LocalLoginParam
  extends EntityBase
{
  private static final long serialVersionUID = -2999591734332216951L;
  public int merchantID;
  public String userName;
  public String userPswd;
  
  public LocalLoginParam(int paramInt, String paramString1, String paramString2)
  {
    this.merchantID = paramInt;
    this.userName = paramString1;
    this.userPswd = paramString2;
  }
}