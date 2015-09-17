package com.zizun.cs.common.utils;

import com.lidroid.xutils.util.LogUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtils
{
  public static final Executor EXECUTOR;
  private static final int corePoolSize = 60;
  private static final int keepAliveTime = 10;
  private static final int maximumPoolSize = 80;
  private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
  private static final ExecutorService singleThreadExecutor;
  private static final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue(80);
  
  static
  {
    EXECUTOR = new ThreadPoolExecutor(60, 80, 10L, TimeUnit.SECONDS, workQueue);
    singleThreadExecutor = Executors.newSingleThreadExecutor();
  }
  
  public static ExecutorService getDBExecutor()
  {
    return singleThreadExecutor;
  }
  
  public static ScheduledExecutorService getSyncScheduledExecutor()
  {
    if ((scheduledThreadPool == null) || (scheduledThreadPool.isShutdown()))
    {
      LogUtils.d("开启新的定长线程池...");
      scheduledThreadPool = Executors.newScheduledThreadPool(5);
    }
    return scheduledThreadPool;
  }
}