package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.sync.DownLoadResultData;
import com.zizun.cs.entities.sync.ParamTable;
import java.sql.Timestamp;
import java.util.List;

public abstract interface SyncDao
{
  public static final String SELECT_BASEDATA_DOWNLOADTIME_SSP_ID = "SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastSubDownloadTime'";
  public static final String SELECT_DOWNLOADTIME_SSP_ID = "SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastDownloadTime'";
  public static final String SELECT_LAST_BASEDATA_DOWNLOADTIME = "SELECT sssp.Parameter_Value FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastSubDownloadTime'";
  public static final String SELECT_LAST_DOWNLOADTIME = "SELECT sssp.Parameter_Value FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastDownloadTime'";
  public static final String SELECT_LAST_UPLOADTIME = "SELECT sssp.Parameter_Value FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastUploadTime'";
  public static final String SELECT_UPLOADTIME_SSP_ID = "SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastUploadTime'";
  public static final String UPDATE_DOWN_LOADTIME = "UPDATE S_StoreStaff_Parameter SET Parameter_Value = ? WHERE SSP_ID = <>";
  public static final String UPDATE_UP_LOADTIME = " UPDATE S_StoreStaff_Parameter SET Parameter_Value = ? WHERE SSP_ID = <>";
  
  public abstract Timestamp getLastBaseDataDownloadTime();
  
  public abstract Timestamp getLastDownloadTime();
  
  public abstract Timestamp getLastUploadTime();
  
  public abstract List<ParamTable> initSyncUploadData();
  
  public abstract boolean updateBaseDataDownloadTime(Timestamp paramTimestamp);
  
  public abstract boolean updateDownloadTime(Timestamp paramTimestamp);
  
  public abstract boolean updateSyncDownLoadDataToLocal(DownLoadResultData paramDownLoadResultData);
  
  public abstract boolean updateSyncUploadCompleteState();
  
  public abstract boolean updateUploadTime(Timestamp paramTimestamp);
}