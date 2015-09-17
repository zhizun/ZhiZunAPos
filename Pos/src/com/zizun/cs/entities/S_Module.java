package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;

public class S_Module
  extends EntitySync
{
  private static final long serialVersionUID = 8210961062964709794L;
  private String Module_Code;
  @ID
  private int Module_ID;
  private String Module_Name;
  private int Module_Order;
  private byte Module_Status;
  private int Parent_ID;
  private byte Terminal_Type;
  
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
  
  public int getModule_Order()
  {
    return this.Module_Order;
  }
  
  public byte getModule_Status()
  {
    return this.Module_Status;
  }
  
  public int getParent_ID()
  {
    return this.Parent_ID;
  }
  
  public byte getTerminal_Type()
  {
    return this.Terminal_Type;
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
  
  public void setModule_Order(int paramInt)
  {
    this.Module_Order = paramInt;
  }
  
  public void setModule_Status(byte paramByte)
  {
    this.Module_Status = paramByte;
  }
  
  public void setParent_ID(int paramInt)
  {
    this.Parent_ID = paramInt;
  }
  
  public void setTerminal_Type(byte paramByte)
  {
    this.Terminal_Type = paramByte;
  }
}