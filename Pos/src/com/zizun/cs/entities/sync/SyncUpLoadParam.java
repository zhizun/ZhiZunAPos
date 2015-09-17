package com.zizun.cs.entities.sync;

import java.util.List;

public class SyncUpLoadParam
{
  public List<ParamTable> Data;
  public int Merchant_ID;
  
  public SyncUpLoadParam(int paramInt, List<ParamTable> paramList)
  {
    this.Merchant_ID = paramInt;
    this.Data = paramList;
  }
}