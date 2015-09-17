package com.zizun.cs.entities;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.zizun.cs.orm.annotation.ID;

@Table(name="S_Sync_Upload")
public class S_Sync_Upload
  extends EntityBase
{
  public static final byte SYNC_STATE_COMPLETE = 3;
  public static final byte SYNC_STATE_NEED = 1;
  public static final byte SYNC_STATE_ON = 2;
  private static final long serialVersionUID = 5503159269989247055L;
  @Column(column="Sync_Status")
  @ID
  private byte Sync_Status;
  @Column(column="Table_Name")
  @ID
  private String Table_Name;
  
  public S_Sync_Upload() {}
  
  public S_Sync_Upload(String paramString)
  {
    init(paramString, (byte)1);
  }
  
  public S_Sync_Upload(String paramString, byte paramByte)
  {
    init(paramString, paramByte);
  }
  
  private void init(String paramString, byte paramByte)
  {
    this.Table_Name = paramString;
    this.Sync_Status = paramByte;
  }
  
  public byte getSync_Status()
  {
    return this.Sync_Status;
  }
  
  public String getTable_Name()
  {
    return this.Table_Name;
  }
  
  public void setSync_Status(byte paramByte)
  {
    this.Sync_Status = paramByte;
  }
  
  public void setTable_Name(String paramString)
  {
    this.Table_Name = paramString;
  }
}