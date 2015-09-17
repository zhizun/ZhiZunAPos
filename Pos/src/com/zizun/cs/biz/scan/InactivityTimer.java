package com.zizun.cs.biz.scan;

import android.app.Activity;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class InactivityTimer
{
  private static final int INACTIVITY_DELAY_SECONDS = 3600;
  private final Activity activity;
  private ScheduledFuture<?> inactivityFuture = null;
  private final ScheduledExecutorService inactivityTimer = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory(null));
  
  public InactivityTimer(Activity paramActivity)
  {
    this.activity = paramActivity;
    onActivity();
  }
  
  private void cancel()
  {
    if (this.inactivityFuture != null)
    {
      this.inactivityFuture.cancel(true);
      this.inactivityFuture = null;
    }
  }
  
  public void onActivity()
  {
    cancel();
    this.inactivityFuture = this.inactivityTimer.schedule(new FinishListener(this.activity), 3600L, TimeUnit.SECONDS);
  }
  
  public void shutdown()
  {
    cancel();
    this.inactivityTimer.shutdown();
  }
  
  private static final class DaemonThreadFactory
    implements ThreadFactory
  {
    public Thread newThread(Runnable paramRunnable)
    {
      paramRunnable = new Thread(paramRunnable);
      paramRunnable.setDaemon(true);
      return paramRunnable;
    }
  }
}