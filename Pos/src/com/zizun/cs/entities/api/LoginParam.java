package com.zizun.cs.entities.api;

public class LoginParam
{
  public int Terminal_Type;
  public String Terminal_Version;
  public String User_Name;
  public String User_Password;
  
  public LoginParam(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    this.User_Name = paramString1;
    this.User_Password = paramString2;
    this.Terminal_Type = paramInt;
    this.Terminal_Version = paramString3;
  }
}