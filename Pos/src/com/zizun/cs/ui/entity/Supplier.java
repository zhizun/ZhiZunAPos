package com.zizun.cs.ui.entity;

public class Supplier
  extends BaseEntity
{
  private static final long serialVersionUID = -8466034658007384975L;
  private String company;
  private String name;
  private String phone;
  
  public String getCompany()
  {
    return this.company;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setCompany(String paramString)
  {
    this.company = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
}