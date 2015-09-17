package com.zizun.cs.biz.dao.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.biz.dao.impl.SyncDaoImpl;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.sync.SyncUpLoadParam;
import com.zizun.cs.entities.sync.SyncUpLoadResult;
import com.zizun.cs.entities.sync.UpLoadResultData;
import java.sql.Timestamp;
import java.util.List;

public class SyncTools
{
  private static final int SYNC_DOWNLOAD = 18;
  private static final int SYNC_DOWNLOAD_FAIL = 20;
  private static final int SYNC_DOWNLOAD_SUCCESS = 19;
  private static final int SYNC_UPLOAD = 17;
  private Context context;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        SyncTools.this.syncUpload();
        continue;
        ToastUtil.toastLong(SyncTools.this.context, "同步完成");
      }
    }
  });
  private LastDo lastDo;
  private int merchantID;
  private SyncDaoImpl syncDao;
  private SyncManager syncManager;
  private SyncUpLoadParam upLoadParam;
  private SyncUpLoadResult upLoadResult;
  
  private SyncTools(Context paramContext)
  {
    this.context = paramContext;
    this.merchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
  }
  
  private void doSyncUploadComplete()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        Timestamp localTimestamp = SyncTools.this.upLoadResult.Data.Return_Sync_DT;
        SyncTools.this.syncManager.SyncUploadComplete(SyncTools.this.syncDao, localTimestamp);
        LogUtils.i("执行同步完成");
        SyncTools.this.lastDo.doInLast();
      }
    }).start();
  }
  
  public static SyncTools getInstance(Context paramContext)
  {
    return new SyncTools(paramContext);
  }
  
  private static boolean needSync()
  {
    List localList = new S_Sync_UploadDaoImpl().getS_Sync_UploadList();
    return (localList != null) && (localList.size() > 0);
  }
  
  private void showSyncDialog(final Context paramContext)
  {
    new AlertDialog.Builder(paramContext).setTitle("提醒").setMessage("发现有未同步的数据，是否同步？").setPositiveButton("是", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        new Thread(new Runnable()
        {
          public void run()
          {
            SyncTools.this.upLoadParam = SyncTools.this.syncManager.initSyncParamsFromDBOutUIThread(this.val$context, SyncTools.this.merchantID, SyncTools.this.syncDao);
            SyncTools.this.handler.obtainMessage(17).sendToTarget();
          }
        }).start();
      }
    }).setNegativeButton("否", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        SyncTools.this.lastDo.doInLast();
      }
    }).show();
  }
  
  private void syncUpload()
  {
    this.syncManager.doSyncUpLoad(this.upLoadParam, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LogUtils.i(SyncTools.this.upLoadResult.Message);
        ToastUtil.toastLong(SyncTools.this.context, "同步失败,请检查网络后再试");
        SyncTools.this.lastDo.doInLast();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        SyncTools.this.upLoadResult = SyncTools.this.syncManager.getSyncUPResultFromJson((String)paramAnonymousResponseInfo.result);
        if (SyncTools.this.upLoadResult.Code == 200)
        {
          SyncTools.this.doSyncUploadComplete();
          return;
        }
        LogUtils.i(SyncTools.this.upLoadResult.Message);
        ToastUtil.toastLong(SyncTools.this.context, "同步失败,请检查网络后再试");
        SyncTools.this.lastDo.doInLast();
      }
    });
  }
  
  public void checkSync(LastDo paramLastDo)
  {
    this.lastDo = paramLastDo;
    if (needSync())
    {
      this.syncManager = ManagerFactory.getSyncManager();
      this.syncDao = new SyncDaoImpl(this.context, this.merchantID);
      showSyncDialog(this.context);
      return;
    }
    paramLastDo.doInLast();
  }
  
  public static abstract interface LastDo
  {
    public abstract void doInLast();
  }
}