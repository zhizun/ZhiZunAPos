package com.zizun.cs.activity.manager;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.SyncBaseDataAPI;
import com.zizun.cs.biz.dao.SyncDao;
import com.zizun.cs.biz.dao.impl.SyncDaoImpl;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.sync.DownLoadResultData;
import com.zizun.cs.entities.sync.SyncDownLoadParam;
import com.zizun.cs.entities.sync.SyncDownLoadResult;
import com.zhizun.pos.app.StoreApplication;
import java.sql.Timestamp;

public class SyncBaseDataManager
{
  private static SyncBaseDataManager mInstance;
  private SyncDao syncDao;
  
  private SyncBaseDataManager()
  {
    int i = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.syncDao = new SyncDaoImpl(StoreApplication.getContext(), i);
  }
  
  private boolean LocalUpdateDownLoadSyncOutUIThread(SyncDownLoadResult paramSyncDownLoadResult, SyncDao paramSyncDao)
  {
    return paramSyncDao.updateSyncDownLoadDataToLocal(paramSyncDownLoadResult.Data);
  }
  
  public static void clear()
  {
    if (mInstance != null) {
      mInstance = null;
    }
  }
  
  public static SyncBaseDataManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new SyncBaseDataManager();
    }
    return mInstance;
  }
  
  private boolean updateBaseDataDownloadTime(Timestamp paramTimestamp)
  {
    return this.syncDao.updateBaseDataDownloadTime(paramTimestamp);
  }
  
  public boolean DownLoadSyncBaseDataComplete(SyncDownLoadResult paramSyncDownLoadResult)
  {
    try
    {
      LocalUpdateDownLoadSyncOutUIThread(paramSyncDownLoadResult, this.syncDao);
      updateBaseDataDownloadTime(paramSyncDownLoadResult.Data.Return_Sync_DT);
      return true;
    }
    catch (Exception paramSyncDownLoadResult)
    {
      paramSyncDownLoadResult.printStackTrace();
    }
    return false;
  }
  
  public void doSyncBaseDataDownload(SyncDownLoadParam paramSyncDownLoadParam, RequestCallBack<String> paramRequestCallBack)
  {
    SyncBaseDataAPI.doSyncBaseDataDownLoad(paramSyncDownLoadParam, paramRequestCallBack);
  }
  
  public Timestamp getLastBaseDataDownloadTime()
  {
    return this.syncDao.getLastBaseDataDownloadTime();
  }
  
  public SyncDownLoadResult getSyncBaseDataDownResultFromJson(String paramString)
  {
    return SyncBaseDataAPI.getSyncBaseDataDownResultFromJson(paramString);
  }
  
  public boolean handleSyncBaseDataDownBack(String paramString)
  {
    paramString = SyncBaseDataAPI.getSyncBaseDataDownResultFromJson(paramString);
    if (paramString.Code == 200) {
      return DownLoadSyncBaseDataComplete(paramString);
    }
    return false;
  }
}