package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.yumendian.cs.activity.manager.AppManager;
import com.yumendian.cs.activity.manager.LoginManager;
import com.yumendian.cs.activity.manager.ManagerFactory;
import com.yumendian.cs.activity.manager.PswdManager;
import com.yumendian.cs.activity.manager.StoreStaffManager;
import com.yumendian.cs.activity.manager.SyncManager;
import com.yumendian.cs.activity.manager.UserManager;
import com.yumendian.cs.activity.manager.VersionManager;
import com.yumendian.cs.biz.action.UpdateStoreStaffParameterAction;
import com.yumendian.cs.biz.action.UpdateStoreStaffParameterAction.OnBindStoreStaffParameterListener;
import com.yumendian.cs.common.utils.DateTimeUtil;
import com.yumendian.cs.common.utils.ExecutorUtils;
import com.yumendian.cs.common.utils.ResUtil;
import com.yumendian.cs.common.utils.ToastUtil;
import com.yumendian.cs.entities.api.VersionNameParam;
import com.yumendian.cs.entities.api.VersionNameResult;
import com.yumendian.cs.ui.entity.Setting;
import com.yunmendian.pos.service.SyncService;
import com.yunmendian.pos.ui.adapter.SimpleTextAdapter;
import com.yunmendian.pos.ui.dialog.PickTimeDialog;
import com.yunmendian.pos.ui.dialog.PickTimeDialog.onDatePickListener;
import com.yunmendian.pos.ui.dialog.SimpleChooseDialog;
import com.yunmendian.pos.ui.dialog.SimpleChooseDialog.OnChooseListener;
import com.yunmendian.pos.ui.dialog.SimpleEditNumberDialog;
import com.yunmendian.pos.ui.dialog.SimpleEditNumberDialog.EditNumberFinishListener;
import com.yunmendian.pos.ui.dialog.SimpleListDialog;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.yunmendian.pos.ui.widget.TitleTopBar;
import com.yunmendian.pos.util.DataUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class SettingMessgeActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, UpdateStoreStaffParameterAction.OnBindStoreStaffParameterListener
{
  private static final int GET_LASTDOWNLOADTIME_COMPLETE = 1;
  private SettingMessgeActivity context;
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
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        SettingMessgeActivity.this.mTxtSyncTime.setText(paramAnonymousMessage);
        SettingMessgeActivity.this.dismissWaitDialog();
      }
    }
  });
  private LoginManager loginManager;
  private ToggleButton mTbtnIsAutoSync;
  private ToggleButton mTbtnIsNegativeRemind;
  private ToggleButton mTbtnIsNegativeStockSale;
  private TextView mTxtAlerm;
  private TextView mTxtPrint;
  private TextView mTxtSyncTime;
  private PswdManager pswdManager;
  private Map<String, String> storeStaffParameterMap;
  private SyncManager syncManager;
  private VersionNameResult versionResult = null;
  
  private void getLatestVersionFromServer()
  {
    VersionManager.GetVersionfromAPI(new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        ToastUtil.toastLong(SettingMessgeActivity.this.context, "网络连接失败！");
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        SettingMessgeActivity.this.versionResult = VersionManager.getVersionFromJsonResult((String)paramAnonymousResponseInfo.result);
        LogUtils.i((String)paramAnonymousResponseInfo.result);
        if (SettingMessgeActivity.this.versionResult.Code == 200) {
          if (SettingMessgeActivity.this.versionResult.Data.VersionNumber.equals(AppManager.getInstance().getAppVersionName(SettingMessgeActivity.this))) {
            ToastUtil.toastLong(SettingMessgeActivity.this.context, "已是最新版本了");
          }
        }
        while (SettingMessgeActivity.this.versionResult.Code != 500)
        {
          return;
          paramAnonymousResponseInfo = new SimpleChooseDialog();
          paramAnonymousResponseInfo.setTitle(2131165321);
          paramAnonymousResponseInfo.setButtonName(2131165323);
          paramAnonymousResponseInfo.setCancelable(false);
          paramAnonymousResponseInfo.setOnConfirmListener(new SimpleChooseDialog.OnChooseListener()
          {
            public void onConfirm()
            {
              SettingMessgeActivity.this.updateApp();
            }
          });
          paramAnonymousResponseInfo.show(SettingMessgeActivity.this.getSupportFragmentManager(), "update");
          return;
        }
        ToastUtil.toastLong(SettingMessgeActivity.this.context, SettingMessgeActivity.this.versionResult.Message);
      }
    });
  }
  
  private void initData()
  {
    this.context = this;
    this.loginManager = ManagerFactory.getLoginManager();
    this.pswdManager = ManagerFactory.getPswdManager();
    this.syncManager = ManagerFactory.getSyncManager();
    showWaitDialog();
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        String str = DateTimeUtil.getTimeStrForElevenLen(SettingMessgeActivity.this.syncManager.getLastDownloadTime());
        SettingMessgeActivity.this.handler.obtainMessage(1, str).sendToTarget();
      }
    });
    this.storeStaffParameterMap = new HashMap();
  }
  
  private void initIsAutoSync()
  {
    boolean bool = StoreStaffManager.IsAutoSync();
    this.mTbtnIsAutoSync.setChecked(bool);
    this.mTbtnIsAutoSync.setOnCheckedChangeListener(this);
    this.mTbtnIsAutoSync.setOnCheckedChangeListener(this);
  }
  
  private void updateApp()
  {
    if (this.versionResult.Data.AndroidUrl != null) {
      startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(this.versionResult.Data.AndroidUrl)), ResUtil.getString(2131165324)));
    }
  }
  
  private void updateAutoSyncSetting(boolean paramBoolean)
  {
    UserManager.getInstance().getSetting().setAutoSync(paramBoolean);
    UserManager.getInstance().saveSetting();
    if (paramBoolean)
    {
      this.storeStaffParameterMap.put("SyncBackground", "Y");
      UserManager.getInstance().setCurrentParameterMap(this.storeStaffParameterMap);
      SyncService.sync(this.context, null);
      return;
    }
    this.storeStaffParameterMap.put("SyncBackground", "N");
    UserManager.getInstance().setCurrentParameterMap(this.storeStaffParameterMap);
    SyncService.stopService(this.context);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903092, paramViewGroup);
    this.mTopBar.setTitle(2131165571);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTbtnIsNegativeStockSale = ((ToggleButton)findViewById(2131362137));
    this.mTbtnIsAutoSync = ((ToggleButton)findViewById(2131362146));
    this.mTxtAlerm = ((TextView)findViewById(2131362141));
    this.mTxtPrint = ((TextView)findViewById(2131362148));
    this.mTxtSyncTime = ((TextView)findViewById(2131362144));
    findViewById(2131362149).setOnClickListener(this);
    findViewById(2131362155).setOnClickListener(this);
    findViewById(2131362135).setOnClickListener(this);
    findViewById(2131362139).setOnClickListener(this);
    findViewById(2131362147).setOnClickListener(this);
    findViewById(2131362143).setOnClickListener(this);
    findViewById(2131362152).setOnClickListener(this);
    this.mTxtAlerm.setText(DataUtil.getFormatString(UserManager.getInstance().getSetting().getStockQtyAlert()));
    this.mTbtnIsNegativeStockSale.setChecked(UserManager.getInstance().getSetting().isNegativeStockSale());
    this.mTbtnIsNegativeStockSale.setOnCheckedChangeListener(this);
    initIsAutoSync();
    initData();
  }
  
  public void onBindFail(String paramString)
  {
    dismissWaitDialog();
    ToastUtil.toastLong(this.context, paramString);
  }
  
  public void onBindSuccess()
  {
    dismissWaitDialog();
    finish();
  }
  
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    switch (paramCompoundButton.getId())
    {
    default: 
      return;
    case 2131362137: 
      UserManager.getInstance().getSetting().setNegativeStockSale(paramBoolean);
      UserManager.getInstance().saveSetting();
      return;
    }
    updateAutoSyncSetting(paramBoolean);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362135: 
      if (UserManager.getInstance().isExperienceAccount())
      {
        ToastUtil.toastLong(this.context, "体验账号不能修改密码!");
        return;
      }
      startActivity(new Intent(this, UpdateActivity.class));
      return;
    case 2131362149: 
      getLatestVersionFromServer();
      return;
    case 2131362155: 
      startActivity(new Intent(this, AboutActivity.class));
      return;
    case 2131362139: 
      paramView = new SimpleEditNumberDialog();
      paramView.setTitle(2131165730);
      paramView.setOnEditFinishDialog(new SimpleEditNumberDialog.EditNumberFinishListener()
      {
        public void onFinish(String paramAnonymousString)
        {
          SettingMessgeActivity.this.mTxtAlerm.setText(paramAnonymousString);
          UserManager.getInstance().getSetting().setStockQtyAlert(Double.valueOf(paramAnonymousString).doubleValue());
          UserManager.getInstance().saveSetting();
        }
      });
      paramView.show(getSupportFragmentManager(), "stockalerm");
      return;
    case 2131362147: 
      paramView = new SimpleListDialog();
      paramView.setTitle(2131165731);
      paramView.setListAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296269)));
      paramView.setItemClickListener(new PrintItemClickListener(null));
      paramView.show(getSupportFragmentManager(), "printDialog");
      return;
    case 2131362143: 
      paramView = new PickTimeDialog();
      paramView.setOnTimePickListener(new PickTimeDialog.onDatePickListener()
      {
        public void onPickFinish(final String paramAnonymousString)
        {
          SettingMessgeActivity.this.mTxtSyncTime.setText(paramAnonymousString);
          ExecutorUtils.getDBExecutor().execute(new Runnable()
          {
            public void run()
            {
              SettingMessgeActivity.this.syncManager.updateDownloadTime(DateTimeUtil.getSimpleTimestampFromString(paramAnonymousString));
            }
          });
        }
      });
      paramView.show(getSupportFragmentManager(), "PickTimeDialog");
      return;
    }
    startActivity(new Intent(this.context, ActivityRemind.class));
  }
  
  public void onTopBarLeftClick()
  {
    if ((this.storeStaffParameterMap != null) && (this.storeStaffParameterMap.size() > 0))
    {
      showWaitDialog("正在保存设置");
      UpdateStoreStaffParameterAction localUpdateStoreStaffParameterAction = new UpdateStoreStaffParameterAction("User", this.storeStaffParameterMap);
      localUpdateStoreStaffParameterAction.setOnBindStoreStaffParameterListener(this);
      localUpdateStoreStaffParameterAction.execute();
      return;
    }
    finish();
  }
  
  private class PrintItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private PrintItemClickListener() {}
    
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      SettingMessgeActivity.this.mTxtPrint.setText((CharSequence)((Adapter)paramAdapterView.getAdapter()).getItem(paramInt));
      UserManager.getInstance().getSetting().setPrintModelType(paramInt);
      UserManager.getInstance().saveSetting();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\activity\SettingMessgeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */