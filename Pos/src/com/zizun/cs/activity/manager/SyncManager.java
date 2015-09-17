package com.zizun.cs.activity.manager;

import android.content.Context;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.SyncAPI;
import com.zizun.cs.biz.dao.SyncDao;
import com.zizun.cs.biz.dao.impl.SyncDaoImpl;
import com.zizun.cs.entities.sync.DownLoadResultData;
import com.zizun.cs.entities.sync.SyncDownLoadParam;
import com.zizun.cs.entities.sync.SyncDownLoadResult;
import com.zizun.cs.entities.sync.SyncUpLoadParam;
import com.zizun.cs.entities.sync.SyncUpLoadResult;
import java.sql.Timestamp;

public class SyncManager
{
  private SyncDao syncDao;
  
  private boolean LocalUpdateDownLoadSyncOutUIThread(SyncDownLoadResult paramSyncDownLoadResult, SyncDao paramSyncDao)
  {
    return paramSyncDao.updateSyncDownLoadDataToLocal(paramSyncDownLoadResult.Data);
  }
  
  private SyncUpLoadParam initSyncParamsFromDB(Context paramContext, int paramInt, SyncDao paramSyncDao)
  {
    paramContext = null;
    paramSyncDao = paramSyncDao.initSyncUploadData();
    if (paramSyncDao != null) {
      paramContext = new SyncUpLoadParam(paramInt, paramSyncDao);
    }
    return paramContext;
  }
  
  private boolean updateDownloadTime(SyncDao paramSyncDao, Timestamp paramTimestamp)
  {
    return paramSyncDao.updateDownloadTime(paramTimestamp);
  }
  
  private boolean updateSyncUploadCompleteState(SyncDao paramSyncDao)
  {
    return paramSyncDao.updateSyncUploadCompleteState();
  }
  
  public boolean DownLoadSyncComplete(SyncDownLoadResult paramSyncDownLoadResult, SyncDao paramSyncDao)
  {
    try
    {
      LocalUpdateDownLoadSyncOutUIThread(paramSyncDownLoadResult, paramSyncDao);
      updateDownloadTime(paramSyncDao, paramSyncDownLoadResult.Data.Return_Sync_DT);
      return true;
    }
    catch (Exception paramSyncDownLoadResult)
    {
      paramSyncDownLoadResult.printStackTrace();
    }
    return false;
  }
  
  public boolean SyncUploadComplete(SyncDao paramSyncDao, Timestamp paramTimestamp)
  {
    try
    {
      updateUploadTime(paramSyncDao, paramTimestamp);
      updateSyncUploadCompleteState(paramSyncDao);
      return true;
    }
    catch (Exception paramSyncDao)
    {
      paramSyncDao.printStackTrace();
    }
    return false;
  }
  
  public void doSyncDownload(SyncDownLoadParam paramSyncDownLoadParam, RequestCallBack<String> paramRequestCallBack)
  {
    SyncAPI.doSyncDownLoad(paramSyncDownLoadParam, paramRequestCallBack);
  }
  
  public void doSyncUpLoad(SyncUpLoadParam paramSyncUpLoadParam, RequestCallBack<String> paramRequestCallBack)
  {
    SyncAPI.doSyncUpLoad(paramSyncUpLoadParam, paramRequestCallBack);
  }
  
  public Timestamp getLastDownloadTime()
  {
    return getLastDownloadTime(getSyncDao());
  }
  
  public Timestamp getLastDownloadTime(SyncDao paramSyncDao)
  {
    return paramSyncDao.getLastDownloadTime();
  }
  
  public Timestamp getLastUploadTime(SyncDao paramSyncDao)
  {
    return paramSyncDao.getLastUploadTime();
  }
  
  public SyncDao getSyncDao()
  {
    if (this.syncDao == null) {
      this.syncDao = new SyncDaoImpl();
    }
    return this.syncDao;
  }
  
  public SyncDownLoadResult getSyncDownResultFromJson(String paramString)
  {
    return SyncAPI.getSyncDownResultFromJson(paramString);
  }
  
  public SyncUpLoadResult getSyncUPResultFromJson(String paramString)
  {
    return SyncAPI.getSyncUpResultFromJson(paramString);
  }
  
  public SyncUpLoadParam initSyncParamsFromDBOutUIThread(Context paramContext, int paramInt, SyncDao paramSyncDao)
  {
    return initSyncParamsFromDB(paramContext, paramInt, paramSyncDao);
  }
  
  public boolean updateDownloadTime(Timestamp paramTimestamp)
  {
    return updateDownloadTime(getSyncDao(), paramTimestamp);
  }
  
  public boolean updateUploadTime(SyncDao paramSyncDao, Timestamp paramTimestamp)
  {
    return paramSyncDao.updateUploadTime(paramTimestamp);
  }
}