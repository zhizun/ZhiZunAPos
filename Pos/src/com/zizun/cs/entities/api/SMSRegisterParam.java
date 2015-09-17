package com.zizun.cs.entities.api;

public class SMSRegisterParam
{
  public String Recommend_Info;
  public String User_Mobile;
  
  public SMSRegisterParam(String paramString1, String paramString2)
  {
    this.User_Mobile = paramString1;
    this.Recommend_Info = paramString2;
    if (this.Recommend_Info == null) {
      this.Recommend_Info = "";
    }
  }
}