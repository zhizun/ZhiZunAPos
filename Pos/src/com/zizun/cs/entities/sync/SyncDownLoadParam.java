package com.zizun.cs.entities.sync;

import com.zizun.cs.entities.EntityBase;
import java.sql.Timestamp;

public class SyncDownLoadParam
  extends EntityBase
{
  private static final long serialVersionUID = 1356164592955183780L;
  public int Merchant_ID;
  public Timestamp Sync_DT;
  
  public SyncDownLoadParam(int paramInt, Timestamp paramTimestamp)
  {
    this.Merchant_ID = paramInt;
    this.Sync_DT = paramTimestamp;
  }
}