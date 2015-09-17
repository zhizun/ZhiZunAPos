package com.zizun.cs.entities.biz;

import java.io.Serializable;

public class V_Parameter
  implements Serializable
{
  private static final long serialVersionUID = -7850503996824551710L;
  private String Module_Code;
  private int Module_ID;
  private String Module_Name;
  private String Parameter_Code;
  private String Parameter_Name;
  private String Parameter_Value;
  
  public String getModule_Code()
  {
    return this.Module_Code;
  }
  
  public int getModule_ID()
  {
    return this.Module_ID;
  }
  
  public String getModule_Name()
  {
    return this.Module_Name;
  }
  
  public String getParameter_Code()
  {
    return this.Parameter_Code;
  }
  
  public String getParameter_Name()
  {
    return this.Parameter_Name;
  }
  
  public String getParameter_Value()
  {
    return this.Parameter_Value;
  }
  
  public void setModule_Code(String paramString)
  {
    this.Module_Code = paramString;
  }
  
  public void setModule_ID(int paramInt)
  {
    this.Module_ID = paramInt;
  }
  
  public void setModule_Name(String paramString)
  {
    this.Module_Name = paramString;
  }
  
  public void setParameter_Code(String paramString)
  {
    this.Parameter_Code = paramString;
  }
  
  public void setParameter_Name(String paramString)
  {
    this.Parameter_Name = paramString;
  }
  
  public void setParameter_Value(String paramString)
  {
    this.Parameter_Value = paramString;
  }
}