package com.zizun.cs.biz.action;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.SyncBaseDataManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.sync.SyncDownLoadParam;
import com.zizun.cs.entities.sync.SyncDownLoadResult;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;

public class BaseSyncAction
{
  private static final int SAVE_SYNC_DOWNLOAD_FAIL = 19;
  private static final int SAVE_SYNC_DOWNLOAD_SUCESS = 18;
  private static BaseSyncAction baseSyncAction;
  private boolean StateOnSync = false;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      do
      {
        do
        {
          return false;
          BaseSyncAction.this.setStateOnSync(false);
        } while (BaseSyncAction.this.onBaseSyncActionListener == null);
        BaseSyncAction.this.onBaseSyncActionListener.onBaseSyncSuccess();
        return false;
        BaseSyncAction.this.setStateOnSync(false);
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
      } while (BaseSyncAction.this.onBaseSyncActionListener == null);
      BaseSyncAction.this.onBaseSyncActionListener.onBaseSyncFail(paramAnonymousMessage);
      return false;
    }
  });
  private OnBaseSyncActionListener onBaseSyncActionListener = null;
  private SyncBaseDataManager syncBaseDataManager;
  private SyncDownLoadResult syncDownLoadResult;
  
  private BaseSyncAction()
  {
    initData();
  }
  
  public static void clear()
  {
    baseSyncAction = null;
  }
  
  private void doSyncDownload()
  {
    Object localObject = this.syncBaseDataManager.getLastBaseDataDownloadTime();
    localObject = new SyncDownLoadParam(UserManager.getInstance().getCurrentUser().getMerchant_ID(), (Timestamp)localObject);
    this.syncBaseDataManager.doSyncBaseDataDownload((SyncDownLoadParam)localObject, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        BaseSyncAction.this.handler.obtainMessage(19, paramAnonymousString).sendToTarget();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        BaseSyncAction.this.syncDownLoadResult = BaseSyncAction.this.syncBaseDataManager.getSyncBaseDataDownResultFromJson((String)paramAnonymousResponseInfo.result);
        if (BaseSyncAction.this.syncDownLoadResult.Code == 200)
        {
          BaseSyncAction.this.saveSyncDownLoadData();
          return;
        }
        LogUtils.i(BaseSyncAction.this.syncDownLoadResult.Message);
        BaseSyncAction.this.handler.obtainMessage(19, BaseSyncAction.this.syncDownLoadResult.Message).sendToTarget();
      }
    });
  }
  
  public static BaseSyncAction getInstance()
  {
    if (baseSyncAction == null) {
      baseSyncAction = new BaseSyncAction();
    }
    return baseSyncAction;
  }
  
  private void initData()
  {
    this.syncBaseDataManager = SyncBaseDataManager.getInstance();
  }
  
  private void saveSyncDownLoadData()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        BaseSyncAction.this.syncBaseDataManager.DownLoadSyncBaseDataComplete(BaseSyncAction.this.syncDownLoadResult);
        BaseSyncAction.this.handler.obtainMessage(18).sendToTarget();
      }
    });
  }
  
  public void clearOnSyncListener()
  {
    if (this.onBaseSyncActionListener != null) {
      this.onBaseSyncActionListener = null;
    }
  }
  
  public void execute()
  {
    setStateOnSync(true);
    doSyncDownload();
  }
  
  public boolean isStateOnSync()
  {
    return this.StateOnSync;
  }
  
  public void setOnSyncActionListener(OnBaseSyncActionListener paramOnBaseSyncActionListener)
  {
    this.onBaseSyncActionListener = paramOnBaseSyncActionListener;
  }
  
  public void setStateOnSync(boolean paramBoolean)
  {
    this.StateOnSync = paramBoolean;
  }
  
  public static abstract interface OnBaseSyncActionListener
  {
    public abstract void onBaseSyncFail(String paramString);
    
    public abstract void onBaseSyncSuccess();
  }
}