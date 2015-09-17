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
import com.zizun.cs.entities.sync.SyncUpLoadParam;
import com.zizun.cs.entities.sync.SyncUpLoadResult;
import com.zizun.cs.entities.sync.UpLoadResultData;
import com.zhizun.pos.app.StoreApplication;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;

public class SyncUpAction
  implements IAction
{
  private static final int SYNC_UP_RESULT_TO_LOCAL_SUCCESS = 4;
  private static final int SYNC_UP_TO_SERVER = 1;
  private static final int SYNC_UP_TO_SERVER_FAIL = 3;
  private static final int SYNC_UP_TO_SERVER_SUCCESS = 2;
  private static SyncUpAction syncUpAction;
  private Context context;
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
          do
          {
            return false;
            if (SyncUpAction.this.upLoadParam != null)
            {
              SyncUpAction.this.syncUploadToServer();
              return false;
            }
            LogUtils.d("没有数据需要同步上传,本地同步结束");
            SyncUpAction.this.isSyncUp = false;
          } while (SyncUpAction.this.onSyncUpActionListener == null);
          SyncUpAction.this.onSyncUpActionListener.onSyncUpSuccess();
          return false;
          SyncUpAction.this.doSyncUploadComplete();
          return false;
          SyncUpAction.this.isSyncUp = false;
        } while (SyncUpAction.this.onSyncUpActionListener == null);
        SyncUpAction.this.onSyncUpActionListener.onSyncUpSuccess();
        return false;
        SyncUpAction.this.isSyncUp = false;
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
      } while (SyncUpAction.this.onSyncUpActionListener == null);
      SyncUpAction.this.onSyncUpActionListener.onSyncUpFail(paramAnonymousMessage);
      return false;
    }
  });
  private boolean isSyncUp = false;
  private int merchantID;
  private OnSyncUpActionListener onSyncUpActionListener = null;
  private SyncDaoImpl syncDao;
  private SyncManager syncManager;
  private SyncUpLoadParam upLoadParam;
  private SyncUpLoadResult upLoadResult;
  
  private SyncUpAction()
  {
    initData();
  }
  
  public static void clear()
  {
    syncUpAction = null;
  }
  
  private void doSyncUploadComplete()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        LogUtils.d("更新本地同步上传业务数据状态...");
        Timestamp localTimestamp = SyncUpAction.this.upLoadResult.Data.Return_Sync_DT;
        SyncUpAction.this.syncManager.SyncUploadComplete(SyncUpAction.this.syncDao, localTimestamp);
        LogUtils.d("更新本地同步上传业务数据状态完成...");
        SyncUpAction.this.handler.obtainMessage(4).sendToTarget();
      }
    });
  }
  
  public static SyncUpAction getInstance()
  {
    if (syncUpAction == null) {
      syncUpAction = new SyncUpAction();
    }
    return syncUpAction;
  }
  
  private void getSyncUpLoadParam()
  {
    LogUtils.d("初始化同步上传业务数据...");
    this.upLoadParam = this.syncManager.initSyncParamsFromDBOutUIThread(this.context, this.merchantID, this.syncDao);
    LogUtils.d("初始化同步上传业务数据完成...");
    this.handler.obtainMessage(1).sendToTarget();
  }
  
  private void initData()
  {
    this.context = StoreApplication.getContext();
    this.syncManager = ManagerFactory.getSyncManager();
    this.merchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.syncDao = new SyncDaoImpl(this.context, this.merchantID);
  }
  
  private void syncUploadToServer()
  {
    LogUtils.d("同步上传业务数据到服务器...");
    this.syncManager.doSyncUpLoad(this.upLoadParam, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LogUtils.d("同步上传业务数据到服务器失败...");
        SyncUpAction.this.handler.obtainMessage(3, paramAnonymousString).sendToTarget();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        SyncUpAction.this.upLoadResult = SyncUpAction.this.syncManager.getSyncUPResultFromJson((String)paramAnonymousResponseInfo.result);
        if (SyncUpAction.this.upLoadResult.Code == 200)
        {
          LogUtils.d("同步上传业务数据到服务器成功...");
          SyncUpAction.this.handler.obtainMessage(2).sendToTarget();
          return;
        }
        LogUtils.d("同步上传业务数据到服务器失败...");
        LogUtils.i(SyncUpAction.this.upLoadResult.Message);
        SyncUpAction.this.handler.obtainMessage(3, SyncUpAction.this.upLoadResult.Message).sendToTarget();
      }
    });
  }
  
  public void clearListener()
  {
    this.onSyncUpActionListener = null;
  }
  
  public void execute()
  {
    this.isSyncUp = true;
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        SyncUpAction.this.getSyncUpLoadParam();
      }
    });
  }
  
  public void executeInThread()
  {
    this.isSyncUp = true;
    getSyncUpLoadParam();
  }
  
  public boolean isSyncUp()
  {
    try
    {
      boolean bool = this.isSyncUp;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setOnSyncUpActionListener(OnSyncUpActionListener paramOnSyncUpActionListener)
  {
    this.onSyncUpActionListener = paramOnSyncUpActionListener;
  }
  
  public static abstract interface OnSyncUpActionListener
  {
    public abstract void onSyncUpFail(String paramString);
    
    public abstract void onSyncUpSuccess();
  }
}