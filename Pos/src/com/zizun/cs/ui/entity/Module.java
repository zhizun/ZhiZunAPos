package com.zizun.cs.ui.entity;

import com.zizun.cs.define.Module_Code;

public class Module
{
  private Module_Code Module_Code;
  private String Module_Name;
  private int Module_Order;
  
  public Module_Code getModule_Code()
  {
    return this.Module_Code;
  }
  
  public String getModule_Name()
  {
    return this.Module_Name;
  }
  
  public int getModule_Order()
  {
    return this.Module_Order;
  }
  
  public void setModule_Code(Module_Code paramModule_Code)
  {
    this.Module_Code = paramModule_Code;
  }
  
  public void setModule_Name(String paramString)
  {
    this.Module_Name = paramString;
  }
  
  public void setModule_Order(int paramInt)
  {
    this.Module_Order = paramInt;
  }
}
