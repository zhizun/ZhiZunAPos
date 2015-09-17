package com.lidroid.xutils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import com.lidroid.xutils.bitmap.BitmapCacheListener;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.BitmapGlobalConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.bitmap.core.AsyncDrawable;
import com.lidroid.xutils.bitmap.core.BitmapCache;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.lidroid.xutils.bitmap.download.Downloader;
import com.lidroid.xutils.cache.FileNameGenerator;
import com.lidroid.xutils.task.PriorityAsyncTask;
import com.lidroid.xutils.task.PriorityExecutor;
import com.lidroid.xutils.task.TaskHandler;
import java.io.File;
import java.lang.ref.WeakReference;

public class BitmapUtils
  implements TaskHandler
{
  private boolean cancelAllTask = false;
  private Context context;
  private BitmapDisplayConfig defaultDisplayConfig;
  private BitmapGlobalConfig globalConfig;
  private boolean pauseTask = false;
  private final Object pauseTaskLock = new Object();
  
  public BitmapUtils(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BitmapUtils(Context paramContext, String paramString)
  {
    if (paramContext == null) {
      throw new IllegalArgumentException("context may not be null");
    }
    this.context = paramContext.getApplicationContext();
    this.globalConfig = BitmapGlobalConfig.getInstance(this.context, paramString);
    this.defaultDisplayConfig = new BitmapDisplayConfig();
  }
  
  public BitmapUtils(Context paramContext, String paramString, float paramFloat)
  {
    this(paramContext, paramString);
    this.globalConfig.setMemCacheSizePercent(paramFloat);
  }
  
  public BitmapUtils(Context paramContext, String paramString, float paramFloat, int paramInt)
  {
    this(paramContext, paramString);
    this.globalConfig.setMemCacheSizePercent(paramFloat);
    this.globalConfig.setDiskCacheSize(paramInt);
  }
  
  public BitmapUtils(Context paramContext, String paramString, int paramInt)
  {
    this(paramContext, paramString);
    this.globalConfig.setMemoryCacheSize(paramInt);
  }
  
  public BitmapUtils(Context paramContext, String paramString, int paramInt1, int paramInt2)
  {
    this(paramContext, paramString);
    this.globalConfig.setMemoryCacheSize(paramInt1);
    this.globalConfig.setDiskCacheSize(paramInt2);
  }
  
  private static <T extends View> boolean bitmapLoadTaskExist(T paramT, String paramString, BitmapLoadCallBack<T> paramBitmapLoadCallBack)
  {
    boolean bool = true;
    paramT = getBitmapTaskFromContainer(paramT, paramBitmapLoadCallBack);
    if (paramT != null)
    {
      paramBitmapLoadCallBack = paramT.uri;
      if ((TextUtils.isEmpty(paramBitmapLoadCallBack)) || (!paramBitmapLoadCallBack.equals(paramString))) {
        paramT.cancel(true);
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  private static <T extends View> BitmapLoadTask<T> getBitmapTaskFromContainer(T paramT, BitmapLoadCallBack<T> paramBitmapLoadCallBack)
  {
    if (paramT != null)
    {
      paramT = paramBitmapLoadCallBack.getDrawable(paramT);
      if ((paramT instanceof AsyncDrawable)) {
        return ((AsyncDrawable)paramT).getBitmapWorkerTask();
      }
    }
    return null;
  }
  
  public void cancel()
  {
    this.pauseTask = true;
    this.cancelAllTask = true;
    synchronized (this.pauseTaskLock)
    {
      this.pauseTaskLock.notifyAll();
      return;
    }
  }
  
  public void clearCache()
  {
    this.globalConfig.clearCache();
  }
  
  public void clearCache(String paramString)
  {
    this.globalConfig.clearCache(paramString);
  }
  
  public void clearDiskCache()
  {
    this.globalConfig.clearDiskCache();
  }
  
  public void clearDiskCache(String paramString)
  {
    this.globalConfig.clearDiskCache(paramString);
  }
  
  public void clearMemoryCache()
  {
    this.globalConfig.clearMemoryCache();
  }
  
  public void clearMemoryCache(String paramString)
  {
    this.globalConfig.clearMemoryCache(paramString);
  }
  
  public void closeCache()
  {
    this.globalConfig.closeCache();
  }
  
  public BitmapUtils configBitmapCacheListener(BitmapCacheListener paramBitmapCacheListener)
  {
    this.globalConfig.setBitmapCacheListener(paramBitmapCacheListener);
    return this;
  }
  
  public BitmapUtils configDefaultAutoRotation(boolean paramBoolean)
  {
    this.defaultDisplayConfig.setAutoRotation(paramBoolean);
    return this;
  }
  
  public BitmapUtils configDefaultBitmapConfig(Bitmap.Config paramConfig)
  {
    this.defaultDisplayConfig.setBitmapConfig(paramConfig);
    return this;
  }
  
  public BitmapUtils configDefaultBitmapMaxSize(int paramInt1, int paramInt2)
  {
    this.defaultDisplayConfig.setBitmapMaxSize(new BitmapSize(paramInt1, paramInt2));
    return this;
  }
  
  public BitmapUtils configDefaultBitmapMaxSize(BitmapSize paramBitmapSize)
  {
    this.defaultDisplayConfig.setBitmapMaxSize(paramBitmapSize);
    return this;
  }
  
  public BitmapUtils configDefaultCacheExpiry(long paramLong)
  {
    this.globalConfig.setDefaultCacheExpiry(paramLong);
    return this;
  }
  
  public BitmapUtils configDefaultConnectTimeout(int paramInt)
  {
    this.globalConfig.setDefaultConnectTimeout(paramInt);
    return this;
  }
  
  public BitmapUtils configDefaultDisplayConfig(BitmapDisplayConfig paramBitmapDisplayConfig)
  {
    this.defaultDisplayConfig = paramBitmapDisplayConfig;
    return this;
  }
  
  public BitmapUtils configDefaultImageLoadAnimation(Animation paramAnimation)
  {
    this.defaultDisplayConfig.setAnimation(paramAnimation);
    return this;
  }
  
  public BitmapUtils configDefaultLoadFailedImage(int paramInt)
  {
    this.defaultDisplayConfig.setLoadFailedDrawable(this.context.getResources().getDrawable(paramInt));
    return this;
  }
  
  public BitmapUtils configDefaultLoadFailedImage(Bitmap paramBitmap)
  {
    this.defaultDisplayConfig.setLoadFailedDrawable(new BitmapDrawable(this.context.getResources(), paramBitmap));
    return this;
  }
  
  public BitmapUtils configDefaultLoadFailedImage(Drawable paramDrawable)
  {
    this.defaultDisplayConfig.setLoadFailedDrawable(paramDrawable);
    return this;
  }
  
  public BitmapUtils configDefaultLoadingImage(int paramInt)
  {
    this.defaultDisplayConfig.setLoadingDrawable(this.context.getResources().getDrawable(paramInt));
    return this;
  }
  
  public BitmapUtils configDefaultLoadingImage(Bitmap paramBitmap)
  {
    this.defaultDisplayConfig.setLoadingDrawable(new BitmapDrawable(this.context.getResources(), paramBitmap));
    return this;
  }
  
  public BitmapUtils configDefaultLoadingImage(Drawable paramDrawable)
  {
    this.defaultDisplayConfig.setLoadingDrawable(paramDrawable);
    return this;
  }
  
  public BitmapUtils configDefaultReadTimeout(int paramInt)
  {
    this.globalConfig.setDefaultReadTimeout(paramInt);
    return this;
  }
  
  public BitmapUtils configDefaultShowOriginal(boolean paramBoolean)
  {
    this.defaultDisplayConfig.setShowOriginal(paramBoolean);
    return this;
  }
  
  public BitmapUtils configDiskCacheEnabled(boolean paramBoolean)
  {
    this.globalConfig.setDiskCacheEnabled(paramBoolean);
    return this;
  }
  
  public BitmapUtils configDiskCacheFileNameGenerator(FileNameGenerator paramFileNameGenerator)
  {
    this.globalConfig.setFileNameGenerator(paramFileNameGenerator);
    return this;
  }
  
  public BitmapUtils configDownloader(Downloader paramDownloader)
  {
    this.globalConfig.setDownloader(paramDownloader);
    return this;
  }
  
  public BitmapUtils configMemoryCacheEnabled(boolean paramBoolean)
  {
    this.globalConfig.setMemoryCacheEnabled(paramBoolean);
    return this;
  }
  
  public BitmapUtils configThreadPoolSize(int paramInt)
  {
    this.globalConfig.setThreadPoolSize(paramInt);
    return this;
  }
  
  public <T extends View> void display(T paramT, String paramString)
  {
    display(paramT, paramString, null, null);
  }
  
  public <T extends View> void display(T paramT, String paramString, BitmapDisplayConfig paramBitmapDisplayConfig)
  {
    display(paramT, paramString, paramBitmapDisplayConfig, null);
  }
  
  public <T extends View> void display(T paramT, String paramString, BitmapDisplayConfig paramBitmapDisplayConfig, BitmapLoadCallBack<T> paramBitmapLoadCallBack)
  {
    if (paramT == null) {}
    Object localObject;
    do
    {
      return;
      localObject = paramBitmapLoadCallBack;
      if (paramBitmapLoadCallBack == null) {
        localObject = new DefaultBitmapLoadCallBack();
      }
      if (paramBitmapDisplayConfig != null)
      {
        paramBitmapLoadCallBack = paramBitmapDisplayConfig;
        if (paramBitmapDisplayConfig != this.defaultDisplayConfig) {}
      }
      else
      {
        paramBitmapLoadCallBack = this.defaultDisplayConfig.cloneNew();
      }
      paramBitmapDisplayConfig = paramBitmapLoadCallBack.getBitmapMaxSize();
      paramBitmapLoadCallBack.setBitmapMaxSize(BitmapCommonUtils.optimizeMaxSizeByView(paramT, paramBitmapDisplayConfig.getWidth(), paramBitmapDisplayConfig.getHeight()));
      paramT.clearAnimation();
      if (TextUtils.isEmpty(paramString))
      {
        ((BitmapLoadCallBack)localObject).onLoadFailed(paramT, paramString, paramBitmapLoadCallBack.getLoadFailedDrawable());
        return;
      }
      ((BitmapLoadCallBack)localObject).onPreLoad(paramT, paramString, paramBitmapLoadCallBack);
      paramBitmapDisplayConfig = this.globalConfig.getBitmapCache().getBitmapFromMemCache(paramString, paramBitmapLoadCallBack);
      if (paramBitmapDisplayConfig != null)
      {
        ((BitmapLoadCallBack)localObject).onLoadStarted(paramT, paramString, paramBitmapLoadCallBack);
        ((BitmapLoadCallBack)localObject).onLoadCompleted(paramT, paramString, paramBitmapDisplayConfig, paramBitmapLoadCallBack, BitmapLoadFrom.MEMORY_CACHE);
        return;
      }
    } while (bitmapLoadTaskExist(paramT, paramString, (BitmapLoadCallBack)localObject));
    BitmapLoadTask localBitmapLoadTask = new BitmapLoadTask(paramT, paramString, paramBitmapLoadCallBack, (BitmapLoadCallBack)localObject);
    paramBitmapDisplayConfig = this.globalConfig.getBitmapLoadExecutor();
    paramString = getBitmapFileFromDiskCache(paramString);
    if ((paramString != null) && (paramString.exists())) {}
    for (int i = 1;; i = 0)
    {
      paramString = paramBitmapDisplayConfig;
      if (i != 0)
      {
        paramString = paramBitmapDisplayConfig;
        if (paramBitmapDisplayConfig.isBusy()) {
          paramString = this.globalConfig.getDiskCacheExecutor();
        }
      }
      ((BitmapLoadCallBack)localObject).setDrawable(paramT, new AsyncDrawable(paramBitmapLoadCallBack.getLoadingDrawable(), localBitmapLoadTask));
      localBitmapLoadTask.setPriority(paramBitmapLoadCallBack.getPriority());
      localBitmapLoadTask.executeOnExecutor(paramString, new Object[0]);
      return;
    }
  }
  
  public <T extends View> void display(T paramT, String paramString, BitmapLoadCallBack<T> paramBitmapLoadCallBack)
  {
    display(paramT, paramString, null, paramBitmapLoadCallBack);
  }
  
  public void flushCache()
  {
    this.globalConfig.flushCache();
  }
  
  public File getBitmapFileFromDiskCache(String paramString)
  {
    return this.globalConfig.getBitmapCache().getBitmapFileFromDiskCache(paramString);
  }
  
  public Bitmap getBitmapFromMemCache(String paramString, BitmapDisplayConfig paramBitmapDisplayConfig)
  {
    BitmapDisplayConfig localBitmapDisplayConfig = paramBitmapDisplayConfig;
    if (paramBitmapDisplayConfig == null) {
      localBitmapDisplayConfig = this.defaultDisplayConfig;
    }
    return this.globalConfig.getBitmapCache().getBitmapFromMemCache(paramString, localBitmapDisplayConfig);
  }
  
  public boolean isCancelled()
  {
    return this.cancelAllTask;
  }
  
  public boolean isPaused()
  {
    return this.pauseTask;
  }
  
  public void pause()
  {
    this.pauseTask = true;
    flushCache();
  }
  
  public void resume()
  {
    this.pauseTask = false;
    synchronized (this.pauseTaskLock)
    {
      this.pauseTaskLock.notifyAll();
      return;
    }
  }
  
  public boolean supportCancel()
  {
    return true;
  }
  
  public boolean supportPause()
  {
    return true;
  }
  
  public boolean supportResume()
  {
    return true;
  }
  
  public class BitmapLoadTask<T extends View>
    extends PriorityAsyncTask<Object, Object, Bitmap>
  {
    private static final int PROGRESS_LOADING = 1;
    private static final int PROGRESS_LOAD_STARTED = 0;
    private final BitmapLoadCallBack<T> callBack;
    private final WeakReference<T> containerReference;
    private final BitmapDisplayConfig displayConfig;
    private BitmapLoadFrom from = BitmapLoadFrom.DISK_CACHE;
    private final String uri;
    
    public BitmapLoadTask(String paramString, BitmapDisplayConfig paramBitmapDisplayConfig, BitmapLoadCallBack<T> paramBitmapLoadCallBack)
    {
      BitmapLoadCallBack localBitmapLoadCallBack;
      if ((paramString == null) || (paramBitmapDisplayConfig == null) || (paramBitmapLoadCallBack == null) || (localBitmapLoadCallBack == null)) {
        throw new IllegalArgumentException("args may not be null");
      }
      this.containerReference = new WeakReference(paramString);
      this.callBack = localBitmapLoadCallBack;
      this.uri = paramBitmapDisplayConfig;
      this.displayConfig = paramBitmapLoadCallBack;
    }
    
    protected Bitmap doInBackground(Object... arg1)
    {
      for (;;)
      {
        boolean bool;
        synchronized (BitmapUtils.this.pauseTaskLock)
        {
          if ((!BitmapUtils.this.pauseTask) || (isCancelled()))
          {
            Object localObject1 = null;
            ??? = (Object[])localObject1;
            if (!isCancelled())
            {
              ??? = (Object[])localObject1;
              if (getTargetContainer() != null)
              {
                publishProgress(new Object[] { Integer.valueOf(0) });
                ??? = BitmapUtils.this.globalConfig.getBitmapCache().getBitmapFromDiskCache(this.uri, this.displayConfig);
              }
            }
            localObject1 = ???;
            if (??? == null)
            {
              localObject1 = ???;
              if (!isCancelled())
              {
                localObject1 = ???;
                if (getTargetContainer() != null)
                {
                  localObject1 = BitmapUtils.this.globalConfig.getBitmapCache().downloadBitmap(this.uri, this.displayConfig, this);
                  this.from = BitmapLoadFrom.URI;
                }
              }
            }
            return (Bitmap)localObject1;
          }
        }
      }
    }
    
    public T getTargetContainer()
    {
      View localView = (View)this.containerReference.get();
      if (this == BitmapUtils.getBitmapTaskFromContainer(localView, this.callBack)) {
        return localView;
      }
      return null;
    }
    
    protected void onCancelled(Bitmap arg1)
    {
      synchronized (BitmapUtils.this.pauseTaskLock)
      {
        BitmapUtils.this.pauseTaskLock.notifyAll();
        return;
      }
    }
    
    protected void onPostExecute(Bitmap paramBitmap)
    {
      View localView = getTargetContainer();
      if (localView != null)
      {
        if (paramBitmap != null) {
          this.callBack.onLoadCompleted(localView, this.uri, paramBitmap, this.displayConfig, this.from);
        }
      }
      else {
        return;
      }
      this.callBack.onLoadFailed(localView, this.uri, this.displayConfig.getLoadFailedDrawable());
    }
    
    protected void onProgressUpdate(Object... paramVarArgs)
    {
      if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {}
      View localView;
      do
      {
        do
        {
          return;
          localView = getTargetContainer();
        } while (localView == null);
        switch (((Integer)paramVarArgs[0]).intValue())
        {
        default: 
          return;
        case 0: 
          this.callBack.onLoadStarted(localView, this.uri, this.displayConfig);
          return;
        }
      } while (paramVarArgs.length != 3);
      this.callBack.onLoading(localView, this.uri, this.displayConfig, ((Long)paramVarArgs[1]).longValue(), ((Long)paramVarArgs[2]).longValue());
    }
    
    public void updateProgress(long paramLong1, long paramLong2)
    {
      publishProgress(new Object[] { Integer.valueOf(1), Long.valueOf(paramLong1), Long.valueOf(paramLong2) });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\lidroid\xutils\BitmapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */