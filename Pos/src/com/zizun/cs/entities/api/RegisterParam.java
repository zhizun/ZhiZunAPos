package com.zizun.cs.entities.api;

public class RegisterParam
{
  public int Industry_ID;
  public String MobileID;
  public String Recommend_Info;
  public String Store_Name;
  public int Terminal_Type;
  public String Terminal_Version;
  public String User_Email;
  public String User_Mobile;
  public String User_Name;
  public String User_Password;
  
  public RegisterParam() {}
  
  public RegisterParam(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, String paramString6, int paramInt2, String paramString7, String paramString8)
  {
    this.User_Name = paramString1;
    this.User_Password = paramString2;
    this.User_Mobile = paramString3;
    this.Store_Name = paramString4;
    this.User_Email = paramString5;
    this.Terminal_Type = paramInt1;
    this.Terminal_Version = paramString6;
    this.Industry_ID = paramInt2;
    this.Recommend_Info = paramString7;
    this.MobileID = paramString8;
  }
  
  public int getIndustry_ID()
  {
    return this.Industry_ID;
  }
  
  public String getMobileID()
  {
    return this.MobileID;
  }
  
  public String getRecommend_Info()
  {
    return this.Recommend_Info;
  }
  
  public String getStore_Name()
  {
    return this.Store_Name;
  }
  
  public int getTerminal_Type()
  {
    return this.Terminal_Type;
  }
  
  public String getTerminal_Version()
  {
    return this.Terminal_Version;
  }
  
  public String getUser_Email()
  {
    return this.User_Email;
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
  
  public void setIndustry_ID(int paramInt)
  {
    this.Industry_ID = paramInt;
  }
  
  public void setMobileID(String paramString)
  {
    this.MobileID = paramString;
  }
  
  public void setRecommend_Info(String paramString)
  {
    this.Recommend_Info = paramString;
  }
  
  public void setStore_Name(String paramString)
  {
    this.Store_Name = paramString;
  }
  
  public void setTerminal_Type(int paramInt)
  {
    this.Terminal_Type = paramInt;
  }
  
  public void setTerminal_Version(String paramString)
  {
    this.Terminal_Version = paramString;
  }
  
  public void setUser_Email(String paramString)
  {
    this.User_Email = paramString;
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
}