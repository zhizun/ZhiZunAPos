package com.zhizun.pos.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.StoreStaffManager;
import com.zizun.cs.biz.action.SyncUpAction;
import com.zizun.cs.biz.action.SyncUpAction.OnSyncUpActionListener;
import com.zizun.cs.biz.action.SyncUpAndDownAction;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.NetworkUtil;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SyncService
  extends Service
  implements SyncUpAction.OnSyncUpActionListener
{
  public static final Integer DEFAULT_WATCH_TIME = Integer.valueOf(3);
  public static Integer FIRST_EXECUTOR_TIME = Integer.valueOf(0);
  public static final String FIRST_EXECUTOR_TIME_KEY = "FIRST_EXECUTOR_TIME_KEY";
  private static OnSyncServiceListener onSyncServiceListener = null;
  private static ScheduledExecutorService scheduledExecutorService = null;
  private Runnable syncRunnable = new Runnable()
  {
    public void run()
    {
      if (!NetworkUtil.hasNetwork(SyncService.this)) {
        LogUtils.d("无网络，本次同步上传不执行");
      }
      while (SyncService.this.isSyncNow()) {
        return;
      }
      SyncService.this.syncUpAction.executeInThread();
    }
  };
  private SyncUpAction syncUpAction;
  private SyncUpAndDownAction syncUpAndDownAction;
  private boolean syncbackground = true;
  
  public static void clearOnSyncListener()
  {
    if (onSyncServiceListener != null) {
      onSyncServiceListener = null;
    }
  }
  
  private String getComponentName(Intent paramIntent)
  {
    String str2 = "";
    String str1 = str2;
    if (paramIntent != null)
    {
      str1 = str2;
      if (paramIntent.getComponent() != null) {
        str1 = paramIntent.getComponent().getClassName();
      }
    }
    return str1;
  }
  
  private boolean isSyncNow()
  {
    boolean bool = this.syncUpAndDownAction.isOnSync();
    return (this.syncUpAction.isSyncUp()) || (bool);
  }
  
  public static void setOnSyncServiceListener(OnSyncServiceListener paramOnSyncServiceListener)
  {
    onSyncServiceListener = paramOnSyncServiceListener;
  }
  
  private static void stopExecutorTimer()
  {
    if ((scheduledExecutorService != null) && (!scheduledExecutorService.isTerminated()))
    {
      LogUtils.d("关闭定长线程池...");
      scheduledExecutorService.shutdown();
    }
  }
  
  public static void stopService(Context paramContext)
  {
    stopExecutorTimer();
    paramContext.stopService(new Intent(paramContext, SyncService.class));
  }
  
  public static void sync(Context paramContext, OnSyncServiceListener paramOnSyncServiceListener)
  {
    clearOnSyncListener();
    setOnSyncServiceListener(paramOnSyncServiceListener);
    paramContext.startService(new Intent(paramContext, SyncService.class));
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    scheduledExecutorService = ExecutorUtils.getSyncScheduledExecutor();
    this.syncbackground = StoreStaffManager.IsAutoSync();
    super.onCreate();
  }
  
  public void onDestroy()
  {
    Log.i("Service生命周期", "onDestroy被回调");
    stopExecutorTimer();
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Log.i("Service生命周期", "onStartCommand被回调[启动该服务的组件的ID:" + paramInt2 + ",启动该服务的组件是:" + getComponentName(paramIntent) + "]");
    if (paramIntent != null)
    {
      paramIntent = paramIntent.getExtras();
      if (paramIntent != null)
      {
        FIRST_EXECUTOR_TIME = Integer.valueOf(paramIntent.getInt("FIRST_EXECUTOR_TIME_KEY", 0));
        this.syncUpAction = SyncUpAction.getInstance();
        this.syncUpAndDownAction = SyncUpAndDownAction.getInstance();
        this.syncUpAction.setOnSyncUpActionListener(this);
        if (!this.syncbackground) {
          break label127;
        }
        scheduledExecutorService.scheduleAtFixedRate(this.syncRunnable, FIRST_EXECUTOR_TIME.intValue(), DEFAULT_WATCH_TIME.intValue(), TimeUnit.SECONDS);
      }
    }
    for (;;)
    {
      return 1;
      label127:
      this.syncUpAction.execute();
    }
  }
  
  public void onSyncUpFail(String paramString)
  {
    if (onSyncServiceListener != null) {
      onSyncServiceListener.onSyncFail(paramString);
    }
  }
  
  public void onSyncUpSuccess()
  {
    if (onSyncServiceListener != null) {
      onSyncServiceListener.onSyncSuccess();
    }
  }
  
  public static abstract interface OnSyncServiceListener
  {
    public abstract void onSyncFail(String paramString);
    
    public abstract void onSyncSuccess();
  }
}