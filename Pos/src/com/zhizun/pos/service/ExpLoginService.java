package com.zhizun.pos.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.LoginManager;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.S_ModuleDaoImpl;
import com.zizun.cs.biz.dao.impl.StoreDaoImpl;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.LoginResult;
import com.zizun.cs.entities.api.LoginResult.LoginResultData;
import com.zhizun.pos.ui.activity.AD_HeaderActivity;
import com.zhizun.pos.ui.activity.StoreSelActivity;
import java.util.List;

public class ExpLoginService
  extends Service
{
  public static final int MERCHANT_ID = 1003136;
  private static final int START_ACTY_MAIN = 19;
  private static final int START_ACTY_STORELIST = 20;
  public static final int USER_ID = 1002384;
  public static final String USER_NAME = "400";
  public static final String USER_PASSWORD = "1";
  private static OnExpLoginListener onExpLoginListener;
  private Context context;
  private List<S_Module> currentModuleList;
  private Store currentStore;
  private S_User currentUser;
  private List<Store> currentUserStoreList;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Intent paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return false;
      case 20: 
        if (ExpLoginService.this.currentUserStoreList.size() > 1)
        {
          ExpLoginService.this.saveLoginInfo2SP();
          paramAnonymousMessage = new Intent(ExpLoginService.this.context, StoreSelActivity.class);
          paramAnonymousMessage.addFlags(268435456);
          ExpLoginService.this.startActivity(paramAnonymousMessage);
        }
        for (;;)
        {
          ExpLoginService.this.handler.obtainMessage(19).sendToTarget();
          return false;
          if (ExpLoginService.this.currentUserStoreList.size() == 1)
          {
            ExpLoginService.this.currentStore = ((Store)ExpLoginService.this.loginResult.Data.Store.get(0));
            LogUtils.i(ExpLoginService.this.currentStore.getStore_Name());
            UserManager.getInstance().setCurrentStore(ExpLoginService.this.currentStore);
            paramAnonymousMessage = new Intent(ExpLoginService.this.context, AD_HeaderActivity.class);
            paramAnonymousMessage.addFlags(268435456);
            ExpLoginService.this.startActivity(paramAnonymousMessage);
          }
        }
      }
      ExpLoginService.this.saveLoginInfo2SP();
      if (ExpLoginService.onExpLoginListener != null) {
        ExpLoginService.onExpLoginListener.onExpLoginSuccess();
      }
      ExpLoginService.onExpLoginListener = null;
      ExpLoginService.this.stopSelf();
      return false;
    }

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
  });
  private LoginManager loginManager;
  private LoginResult loginResult = null;
  
  public static void clearOnExpLoginListener()
  {
    if (onExpLoginListener != null) {
      onExpLoginListener = null;
    }
  }
  
  private void doLogin()
  {
    new AsyncTask()
    {
      protected LoginResult doInBackground(Void... paramAnonymousVarArgs)
      {
        if (!ExpLoginService.this.loginManager.isDbFileExist(ExpLoginService.this.currentUser.getMerchant_ID())) {
          ExpLoginService.this.loginManager.CopyDBToSDCardOutUIThread(ExpLoginService.this.currentUser.getMerchant_ID());
        }
        ExpLoginService.this.currentUserStoreList = ExpLoginService.this.loginManager.getAllStoreByUserOutUIThread(ExpLoginService.this.currentUser.getUser_ID(), new StoreDaoImpl(ExpLoginService.this.context, ExpLoginService.this.currentUser.getMerchant_ID()));
        ExpLoginService.this.currentModuleList = ExpLoginService.this.loginManager.getMoudleListByUserIDOutUIThread(ExpLoginService.this.currentUser.getUser_ID(), new S_ModuleDaoImpl(ExpLoginService.this.context, ExpLoginService.this.currentUser.getMerchant_ID()));
        ExpLoginService.this.loginResult = new LoginResult();
        paramAnonymousVarArgs = ExpLoginService.this.loginResult;
        LoginResult localLoginResult = ExpLoginService.this.loginResult;
        localLoginResult.getClass();
        paramAnonymousVarArgs.Data = new LoginResult.LoginResultData(localLoginResult);
        ExpLoginService.this.loginResult.Data.S_User = ExpLoginService.this.currentUser;
        ExpLoginService.this.loginResult.Data.Store = ExpLoginService.this.currentUserStoreList;
        ExpLoginService.this.loginResult.Data.S_Module = ExpLoginService.this.currentModuleList;
        return ExpLoginService.this.loginResult;
      }
      
      protected void onPostExecute(LoginResult paramAnonymousLoginResult)
      {
        ExpLoginService.this.handler.obtainMessage(20).sendToTarget();
        super.onPostExecute(paramAnonymousLoginResult);
      }
    }.executeOnExecutor(ExecutorUtils.EXECUTOR, new Void[0]);
  }
  
  private void initCurrentUser()
  {
    this.currentUser = new S_User();
    this.currentUser.setUser_ID(1002384);
    this.currentUser.setUser_Name("400");
    this.currentUser.setUser_Password("1");
    this.currentUser.setMerchant_ID(1003136);
  }
  
  private void initData()
  {
    this.context = this;
    ManagerFactory.reSet();
    this.loginManager = ManagerFactory.getLoginManager();
    UserManager.getInstance().setExperienceAccount(true);
    initCurrentUser();
  }
  
  private void saveLoginInfo2SP()
  {
    if (this.currentUser != null) {
      UserManager.getInstance().setCurrentUser(this.currentUser);
    }
    if (this.currentStore != null) {
      UserManager.getInstance().setCurrentStore(this.currentStore);
    }
    if (this.currentModuleList != null) {
      UserManager.getInstance().setCurrentModuleList(this.currentModuleList);
    }
    if (this.currentUserStoreList != null) {
      UserManager.getInstance().setCurrentUserStoreList(this.currentUserStoreList);
    }
    UserManager.getInstance().setLoginResult(this.loginResult);
  }
  
  public static void setOnExpLoginListener(OnExpLoginListener paramOnExpLoginListener)
  {
    onExpLoginListener = paramOnExpLoginListener;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    initData();
    doLogin();
    return 1;
  }
  
  public static int getStartActyMain() {
	return START_ACTY_MAIN;
}

public static int getStartActyStorelist() {
	return START_ACTY_STORELIST;
}

public static abstract interface OnExpLoginListener
  {
    public abstract void onExpLoginFail(String paramString);
    
    public abstract void onExpLoginSuccess();
  }
}