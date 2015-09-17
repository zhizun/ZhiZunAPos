package com.zizun.cs.entities.sync;

import com.zizun.cs.entities.EntityBase;
import java.sql.Timestamp;
import java.util.List;

public class DownLoadResultData
  extends EntityBase
{
  private static final long serialVersionUID = 6697804620584087576L;
  public List<ParamTable> Return_Data;
  public Timestamp Return_Sync_DT;
}