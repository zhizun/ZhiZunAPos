package com.zizun.cs.biz.action;

public class SyncUpAndDownAction
  implements IAction, SyncUpAction.OnSyncUpActionListener, SyncDownAction.OnSyncDownActionListener
{
  private static SyncUpAndDownAction syncUpAndDownAction;
  private boolean isOnSync = false;
  private OnSyncUpAndDownActionListener onSyncUpAndDownActionListener = null;
  private SyncDownAction syncDownAction;
  private SyncUpAction syncUpAction;
  
  private SyncUpAndDownAction()
  {
    initData();
  }
  
  public static void clear()
  {
    syncUpAndDownAction = null;
  }
  
  public static SyncUpAndDownAction getInstance()
  {
    if (syncUpAndDownAction == null) {
      syncUpAndDownAction = new SyncUpAndDownAction();
    }
    return syncUpAndDownAction;
  }
  
  private void initData()
  {
    this.syncUpAction = SyncUpAction.getInstance();
    this.syncUpAction.setOnSyncUpActionListener(this);
    this.syncDownAction = SyncDownAction.getInstance();
    this.syncDownAction.setOnSyncDownActionListener(this);
  }
  
  public void clearListener()
  {
    this.syncUpAction.clearListener();
    this.syncDownAction.clearListener();
    this.onSyncUpAndDownActionListener = null;
  }
  
  public void clearOnSyncListener()
  {
    if (this.onSyncUpAndDownActionListener != null) {
      this.onSyncUpAndDownActionListener = null;
    }
  }
  
  public void execute()
  {
    this.isOnSync = true;
    this.syncUpAction.execute();
  }
  
  public boolean isOnSync()
  {
    try
    {
      boolean bool = this.isOnSync;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onSyncDownFail(String paramString)
  {
    this.isOnSync = false;
    if (this.onSyncUpAndDownActionListener != null) {
      this.onSyncUpAndDownActionListener.onSyncUpAndDownFail(paramString);
    }
  }
  
  public void onSyncDownSuccess()
  {
    this.isOnSync = false;
    if (this.onSyncUpAndDownActionListener != null) {
      this.onSyncUpAndDownActionListener.onSyncUpAndDownSuccess();
    }
  }
  
  public void onSyncUpFail(String paramString)
  {
    this.isOnSync = false;
    if (this.onSyncUpAndDownActionListener != null) {
      this.onSyncUpAndDownActionListener.onSyncUpAndDownFail(paramString);
    }
  }
  
  public void onSyncUpSuccess()
  {
    this.syncDownAction.execute();
  }
  
  public void setOnSyncUpAndDownActionListener(OnSyncUpAndDownActionListener paramOnSyncUpAndDownActionListener)
  {
    this.syncUpAction.setOnSyncUpActionListener(this);
    this.syncDownAction.setOnSyncDownActionListener(this);
    this.onSyncUpAndDownActionListener = paramOnSyncUpAndDownActionListener;
  }
  
  public static abstract interface OnSyncUpAndDownActionListener
  {
    public abstract void onSyncUpAndDownFail(String paramString);
    
    public abstract void onSyncUpAndDownSuccess();
  }
}