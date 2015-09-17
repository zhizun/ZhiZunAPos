package com.zizun.cs.biz.action;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.activity.manager.SyncManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.SyncDaoImpl;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.sync.SyncDownLoadParam;
import com.zizun.cs.entities.sync.SyncDownLoadResult;
import com.zhizun.pos.app.StoreApplication;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;

public class SyncDownAction
  implements IAction
{
  private static final int GET_SYNC_DOWN_DATA_FROM_SERVER = 1;
  private static final int GET_SYNC_DOWN_DATA_FROM_SERVER_FAIL = 3;
  private static final int GET_SYNC_DOWN_DATA_FROM_SERVER_SUCCESS = 2;
  private static final int SAVE_SYNC_DOWN_DATA_TO_LOCAL_FAIL = 5;
  private static final int SAVE_SYNC_DOWN_DATA_TO_LOCAL_SUCCESS = 4;
  private static SyncDownAction syncDownAction;
  private Context context;
  private SyncDownLoadResult downLoadResult;
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
          SyncDownAction.this.getSyncDownLoadDataFromServer();
          return false;
          SyncDownAction.this.saveSyncDownLoadData();
          return false;
          SyncDownAction.this.isSyncDown = false;
          paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        } while (SyncDownAction.this.onSyncDownActionListener == null);
        SyncDownAction.this.onSyncDownActionListener.onSyncDownFail(paramAnonymousMessage);
        return false;
        SyncDownAction.this.isSyncDown = false;
      } while (SyncDownAction.this.onSyncDownActionListener == null);
      SyncDownAction.this.onSyncDownActionListener.onSyncDownSuccess();
      return false;
    }
  });
  private boolean isSyncDown = false;
  private Timestamp lastDownloadTime;
  private int merchantID;
  private OnSyncDownActionListener onSyncDownActionListener = null;
  private SyncDaoImpl syncDao;
  private SyncManager syncManager;
  
  private SyncDownAction()
  {
    initData();
  }
  
  public static void clear()
  {
    syncDownAction = null;
  }
  
  public static SyncDownAction getInstance()
  {
    if (syncDownAction == null) {
      syncDownAction = new SyncDownAction();
    }
    return syncDownAction;
  }
  
  private void getLastDownloadTime()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        LogUtils.d("获取上次的同步下载时间...");
        SyncDownAction.this.lastDownloadTime = SyncDownAction.this.syncManager.getLastDownloadTime(SyncDownAction.this.syncDao);
        SyncDownAction.this.handler.obtainMessage(1).sendToTarget();
      }
    });
  }
  
  private void getSyncDownLoadDataFromServer()
  {
    LogUtils.d("从服务器获取下载数据...");
    SyncDownLoadParam localSyncDownLoadParam = new SyncDownLoadParam(this.merchantID, this.lastDownloadTime);
    this.syncManager.doSyncDownload(localSyncDownLoadParam, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LogUtils.d("从服务器获取下载数据失败...");
        SyncDownAction.this.handler.obtainMessage(3, paramAnonymousString).sendToTarget();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        SyncDownAction.this.downLoadResult = SyncDownAction.this.syncManager.getSyncDownResultFromJson((String)paramAnonymousResponseInfo.result);
        if (SyncDownAction.this.downLoadResult.Code == 200)
        {
          LogUtils.d("从服务器获取下载数据成功...");
          SyncDownAction.this.handler.obtainMessage(2).sendToTarget();
          return;
        }
        LogUtils.d("从服务器获取下载数据失败...");
        SyncDownAction.this.handler.obtainMessage(3, SyncDownAction.this.downLoadResult.Message).sendToTarget();
      }
    });
  }
  
  private void initData()
  {
    this.context = StoreApplication.getContext();
    this.syncManager = ManagerFactory.getSyncManager();
    this.merchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.syncDao = new SyncDaoImpl(this.context, this.merchantID);
  }
  
  private void saveSyncDownLoadData()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        LogUtils.d("更新下载数据到本地数据库...");
        SyncDownAction.this.syncManager.DownLoadSyncComplete(SyncDownAction.this.downLoadResult, SyncDownAction.this.syncManager.getSyncDao());
        LogUtils.d("更新下载数据到本地数据库完成...");
        SyncDownAction.this.handler.obtainMessage(4).sendToTarget();
      }
    });
  }
  
  public void clearListener()
  {
    this.onSyncDownActionListener = null;
  }
  
  public void execute()
  {
    this.isSyncDown = true;
    getLastDownloadTime();
  }
  
  public boolean isSyncDown()
  {
    try
    {
      boolean bool = this.isSyncDown;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setOnSyncDownActionListener(OnSyncDownActionListener paramOnSyncDownActionListener)
  {
    this.onSyncDownActionListener = paramOnSyncDownActionListener;
  }
  
  public static abstract interface OnSyncDownActionListener
  {
    public abstract void onSyncDownFail(String paramString);
    
    public abstract void onSyncDownSuccess();
  }
}