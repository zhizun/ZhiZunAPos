package com.zizun.cs.entities.api;

import java.util.List;

public class StoreStaffParameterBatchBindParam
{
  public List<StoreStaffParameterBindParam> BatchData;
  public int Merchant_ID;
  public String Module_Code;
  
  public StoreStaffParameterBatchBindParam(int paramInt, String paramString, List<StoreStaffParameterBindParam> paramList)
  {
    this.Merchant_ID = paramInt;
    this.Module_Code = paramString;
    this.BatchData = paramList;
  }
}