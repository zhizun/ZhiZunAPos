package com.zizun.cs.entities.api;

public class PswdChangeParam
{
  public String User_Name;
  public String User_Password;
  
  public PswdChangeParam(String paramString1, String paramString2)
  {
    this.User_Name = paramString1;
    this.User_Password = paramString2;
  }
}