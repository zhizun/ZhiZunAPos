package com.zhizun.pos.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.AD_headerManager;
import com.zizun.cs.activity.manager.AppManager;
import com.zizun.cs.activity.manager.LoginManager;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.activity.manager.StoreStaffManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.action.BaseSyncAction;
import com.zizun.cs.biz.action.BaseSyncAction.OnBaseSyncActionListener;
import com.zizun.cs.biz.action.UpdateDbTableAction;
import com.zizun.cs.biz.action.UpdateDbTableAction.OnUpdateDBVersionListener;
import com.zizun.cs.biz.dao.impl.S_LoginDaoImpl;
import com.zizun.cs.biz.dao.impl.S_ModuleDaoImpl;
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.biz.dao.impl.S_UserDaoImpl;
import com.zizun.cs.biz.dao.impl.StoreDaoImpl;
import com.zizun.cs.biz.dao.trans.impl.LoginTransaction;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_Login;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.AD_HeaderParam;
import com.zizun.cs.entities.api.AD_HeaderResult;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zizun.cs.entities.api.LoginParam;
import com.zizun.cs.entities.api.LoginResult;
import com.zizun.cs.entities.api.LoginResult.LoginResultData;
import com.zizun.cs.entities.biz.V_Parameter;
import com.zhizun.pos.ui.activity.AD_HeaderActivity;
import com.zhizun.pos.ui.activity.MainActivity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class LoginService
  extends Service
  implements BaseSyncAction.OnBaseSyncActionListener, UpdateDbTableAction.OnUpdateDBVersionListener
{
  private static final int GET_GETALLSTORE_COMPLETE = 2;
  private static final int START_ACTY_AD_HEADER = 18;
  private static final int START_ACTY_MAIN = 19;
  private static final int START_ACTY_STORELIST = 20;
  private static final int START_SYNC_BACKGROUND = 21;
  private static OnLoginListener onLoginListener;
  private Context context;
  private List<S_Module> currentModuleList;
  private Store currentStore;
  private List<V_Parameter> currentStoreStaffParamerterList;
  private S_User currentUser;
  private List<Store> currentUserStoreList;
  private Handler handler = new Handler(new Handler.Callback()
  {
    @SuppressWarnings("unchecked")
	public boolean handleMessage(Intent paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        LoginService.this.getAllStoreList();
        continue;
        LoginService.this.currentUserStoreList = ((List<Store>)paramAnonymousMessage.obj);
        if ((LoginService.this.currentUserStoreList == null) || (LoginService.this.currentUserStoreList.size() == 0))
        {
          if (LoginService.onLoginListener != null) {
            LoginService.onLoginListener.onLoginFail("该账号没有绑定门店");
          }
        }
        else
        {
          LoginService.this.initCurrentStore();
          continue;
          LoginService.this.saveLoginInfo2SP();
          LoginService.this.loadSyncBackground();
          if (LoginService.onLoginListener != null) {
            LoginService.onLoginListener.onLoginSuccess();
          }
          LoginService.this.jumpMain();
          LoginService.this.stopSelf();
          continue;
          LoginService.this.saveLoginInfo2SP();
          LoginService.this.loadSyncBackground();
          if (LoginService.onLoginListener != null) {
            LoginService.onLoginListener.onLoginSuccess();
          }
          LoginService.this.jumpADHeader();
          LoginService.this.stopSelf();
          continue;
          LogUtil.logD("自动后台同步开启......");
          paramAnonymousMessage = new Intent(LoginService.this.context, SyncService.class);
          paramAnonymousMessage.putExtra("FIRST_EXECUTOR_TIME_KEY", 3);
          LoginService.this.context.startService(paramAnonymousMessage);
        }
      }
    }

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
  });
  private LocalLoginParam localLoginParam;
  private LoginManager loginManager;
  private PreferencesUtil loginMapPreferencesUtil;
  private LoginParam loginParam;
  private LoginResult loginResult = null;
  private String userName;
  private String userPassword;
  
  public static void clearOnLoginListener()
  {
    if (onLoginListener != null) {
      onLoginListener = null;
    }
  }
  
  private void doLogin()
  {
    if (!initUserInputInfo()) {
      return;
    }
    if (this.loginManager.checkNetwork())
    {
      serverLogin();
      return;
    }
    localLogin();
  }
  
  private void doSyncDownload()
  {
    BaseSyncAction.clear();
    BaseSyncAction localBaseSyncAction = BaseSyncAction.getInstance();
    localBaseSyncAction.setOnSyncActionListener(this);
    localBaseSyncAction.execute();
  }
  
  private void getAllStoreList()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        List localList = LoginService.this.loginManager.getAllStoreByUserOutUIThread(LoginService.this.currentUser.getUser_ID(), new StoreDaoImpl(LoginService.this.context, LoginService.this.currentUser.getMerchant_ID()));
        LoginService.this.handler.obtainMessage(2, localList).sendToTarget();
      }
    });
  }
  
  private Map<String, String> initCurrentParameterMap()
  {
    Iterator localIterator = null;
    Object localObject = localIterator;
    if (this.currentStoreStaffParamerterList != null)
    {
      localObject = localIterator;
      if (this.currentStoreStaffParamerterList.size() > 0)
      {
        localObject = new HashMap();
        localIterator = this.currentStoreStaffParamerterList.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return (Map<String, String>)localObject;
      }
      V_Parameter localV_Parameter = (V_Parameter)localIterator.next();
      ((Map)localObject).put(localV_Parameter.getParameter_Code(), localV_Parameter.getParameter_Value());
    }
  }
  
  private void initCurrentStore()
  {
    this.currentStore = ((Store)this.currentUserStoreList.get(0));
    LogUtils.i(this.currentStore.getStore_Name());
    UserManager.getInstance().setCurrentStore(this.currentStore);
    String str = AppManager.getInstance().getAppVersionName(this.context);
    AD_headerManager.GetAD_headerfromAPI(new RequestCallBack()
    {
      private AD_HeaderResult result;
      
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LoginService.this.handler.obtainMessage(19).sendToTarget();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        this.result = AD_headerManager.getADheaderFromJsonResult((String)paramAnonymousResponseInfo.result);
        LogUtil.logD((String)paramAnonymousResponseInfo.result);
        if (this.result.Code == 200)
        {
          if (this.result.Data.AH_IsShow == 1)
          {
            LoginService.this.handler.obtainMessage(18).sendToTarget();
            return;
          }
          LoginService.this.handler.obtainMessage(19).sendToTarget();
          return;
        }
        if (this.result.Data.AH_IsShow == 1) {
          ToastUtil.toastShort(LoginService.this.context, "网络获取失败！");
        }
        LoginService.this.handler.obtainMessage(19).sendToTarget();
      }
    }, str);
  }
  
  private void initData()
  {
    this.context = this;
    ManagerFactory.reSet();
    this.loginManager = ManagerFactory.getLoginManager();
    this.loginMapPreferencesUtil = new PreferencesUtil(this.context, "NAME_LOGIN");
  }
  
  private LoginParam initLoginParam()
  {
    return new LoginParam(this.userName, this.userPassword, 3, "");
  }
  
  private boolean initUserInputInfo()
  {
    if ((this.userName == null) || (TextUtils.isEmpty(this.userName))) {
      if (onLoginListener != null) {
        onLoginListener.onLoginFail("用户名为空");
      }
    }
    do
    {
      return false;
      if ((this.userPassword != null) && (!TextUtils.isEmpty(this.userPassword))) {
        break;
      }
    } while (onLoginListener == null);
    onLoginListener.onLoginFail("密码为空");
    return false;
    return true;
  }
  
  private void initUserManager()
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
    if (this.currentStoreStaffParamerterList != null)
    {
      UserManager.getInstance().setCurrentStoreStaffParamerterList(this.currentStoreStaffParamerterList);
      Map localMap = initCurrentParameterMap();
      UserManager.getInstance().setCurrentParameterMap(localMap);
    }
    UserManager.getInstance().setLoginResult(this.loginResult);
  }
  
  private void jumpADHeader()
  {
    Intent localIntent = new Intent(this.context, AD_HeaderActivity.class);
    localIntent.addFlags(268435456);
    startActivity(localIntent);
  }
  
  private void jumpMain()
  {
    Intent localIntent = new Intent(this.context, MainActivity.class);
    localIntent.addFlags(268435456);
    startActivity(localIntent);
  }
  
  private void loadSyncBackground()
  {
    Object localObject = UserManager.getInstance().getCurrentParameterMap();
    int j = 0;
    localObject = (String)((Map)localObject).get("SyncBackground");
    int i = j;
    if (localObject != null)
    {
      i = j;
      if (((String)localObject).equals("Y")) {
        i = 1;
      }
    }
    if (i != 0)
    {
      if (UserManager.getInstance().isExperienceAccount()) {
        ToastUtil.toastLong(this.context, "体验账号不能同步数据!");
      }
    }
    else {
      return;
    }
    this.handler.obtainMessage(21).sendToTarget();
  }
  
  private void localLogin()
  {
    this.localLoginParam = ((LocalLoginParam)this.loginMapPreferencesUtil.getObject(this.userName, LocalLoginParam.class));
    if (this.localLoginParam == null)
    {
      if (onLoginListener != null) {
        onLoginListener.onLoginFail("请在线登录!");
      }
      return;
    }
    this.localLoginParam.userName = this.userName;
    this.localLoginParam.userPswd = this.userPassword;
    new AsyncTask()
    {
      protected LoginResult doInBackground(Void... paramAnonymousVarArgs)
      {
        LoginService.this.currentUser = LoginService.this.loginManager.localLoginOutUIThread(new S_UserDaoImpl(LoginService.this.context, LoginService.this.localLoginParam.merchantID), LoginService.this.localLoginParam);
        LoginService.this.currentModuleList = LoginService.this.loginManager.getMoudleListByUserIDOutUIThread(LoginService.this.currentUser.getUser_ID(), new S_ModuleDaoImpl(LoginService.this.context, LoginService.this.currentUser.getMerchant_ID()));
        LoginService.this.loginResult = new LoginResult();
        paramAnonymousVarArgs = LoginService.this.loginResult;
        LoginResult localLoginResult = LoginService.this.loginResult;
        localLoginResult.getClass();
        paramAnonymousVarArgs.Data = new LoginResult.LoginResultData(localLoginResult);
        LoginService.this.loginResult.Data.S_User = LoginService.this.currentUser;
        LoginService.this.loginResult.Data.Store = LoginService.this.currentUserStoreList;
        LoginService.this.loginResult.Data.S_Module = LoginService.this.currentModuleList;
        LoginService.this.saveLocalLoginHistory();
        return LoginService.this.loginResult;
      }
      
      protected void onPostExecute(LoginResult paramAnonymousLoginResult)
      {
        LoginService.this.handler.obtainMessage(20).sendToTarget();
        super.onPostExecute(paramAnonymousLoginResult);
      }
    }.executeOnExecutor(ExecutorUtils.EXECUTOR, new Void[0]);
  }
  
  private void saveLocalLoginHistory()
  {
    LogUtils.d("保存本地登录历史");
    Object localObject = AppManager.getInstance().getAppVersionName(this.context);
    localObject = new S_Login(this.currentUser.getMerchant_ID(), IDUtil.getID(), this.currentUser.getUser_ID(), DateTimeUtil.getCurrentTime(), (byte)3, (String)localObject);
    ((S_Login)localObject).setCreateEntitySync(this.currentUser.getUser_ID());
    this.loginManager.insertS_LoginOutUIThread(new S_LoginDaoImpl(this.context, this.currentUser.getMerchant_ID()), (S_Login)localObject);
    new S_Sync_UploadDaoImpl().insert(new S_Sync_Upload("S_Login"));
  }
  
  private void saveLoginInfo2SP()
  {
    this.loginManager.saveLocalLoginParamToPreference(this.localLoginParam);
    initUserManager();
  }
  
  private void serverLogin()
  {
    this.loginParam = initLoginParam();
    LogUtil.logD(JsonUtil.toJson(this.loginParam));
    this.loginManager.login(this.loginParam, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        if (LoginService.onLoginListener != null) {
          LoginService.onLoginListener.onLoginFail(paramAnonymousString);
        }
        paramAnonymousHttpException.printStackTrace();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        LogUtil.logD((String)paramAnonymousResponseInfo.result);
        LoginService.this.loginResult = LoginService.this.loginManager.getLoginResultFromJson((String)paramAnonymousResponseInfo.result);
        if ((LoginService.this.loginResult != null) && (LoginService.this.loginResult.Code == 200))
        {
          LoginService.this.currentUser = LoginService.this.loginResult.Data.S_User;
          LoginService.this.currentModuleList = LoginService.this.loginResult.Data.S_Module;
          if (!LoginService.this.loginManager.isDbFileExist(LoginService.this.currentUser.getMerchant_ID())) {
            LoginService.this.loginManager.CopyDBToSDCardOutUIThread(LoginService.this.currentUser.getMerchant_ID());
          }
          LoginService.this.localLoginParam = new LocalLoginParam(LoginService.this.loginResult.Data.S_User.getMerchant_ID(), LoginService.this.loginParam.User_Name, LoginService.this.loginParam.User_Password);
          LoginService.this.saveLoginInfo2SP();
          LoginService.this.updateDBVersion(LoginService.this.loginResult.Data.S_User.getMerchant_ID());
        }
        while ((LoginService.this.loginResult.Code != 500) || (LoginService.onLoginListener == null)) {
          return;
        }
        LoginService.onLoginListener.onLoginFail(LoginService.this.loginResult.Message);
      }
    });
  }
  
  public static void setOnLoginListener(OnLoginListener paramOnLoginListener)
  {
    onLoginListener = paramOnLoginListener;
  }
  
  private void updateDBVersion(int paramInt)
  {
    UpdateDbTableAction localUpdateDbTableAction = new UpdateDbTableAction(paramInt);
    localUpdateDbTableAction.setOnUpdateDBVersionListener(this);
    localUpdateDbTableAction.execute();
  }
  
  public void onBaseSyncFail(String paramString)
  {
    if (onLoginListener != null) {
      onLoginListener.onBaseSyncFail(paramString);
    }
  }
  
  public void onBaseSyncSuccess()
  {
    if (onLoginListener != null) {
      onLoginListener.onBaseSyncSuccess();
    }
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        LoginService.this.loginManager.saveLoginResultOnLineOutUIThread(LoginService.this.loginResult.Data, new LoginTransaction(LoginService.this.context, LoginService.this.loginResult.Data.S_User.getMerchant_ID()));
        LoginService.this.currentStoreStaffParamerterList = StoreStaffManager.getAllStoreStaffParamerter();
        LoginService.this.handler.obtainMessage(20).sendToTarget();
      }
    });
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    initData();
    if (paramIntent != null)
    {
      paramIntent = paramIntent.getExtras();
      this.userName = paramIntent.getString("userName", "");
      this.userPassword = paramIntent.getString("userPassword", "");
    }
    doLogin();
    return 1;
  }
  
  public void onUpdateDBVersionFail(String paramString)
  {
    if (onLoginListener != null) {
      onLoginListener.onLoginFail(paramString);
    }
  }
  
  public void onUpdateDBVersionSuccess()
  {
    doSyncDownload();
  }
  
  public static int getGetGetallstoreComplete() {
	return GET_GETALLSTORE_COMPLETE;
}

public static int getStartActyAdHeader() {
	return START_ACTY_AD_HEADER;
}

public static int getStartActyMain() {
	return START_ACTY_MAIN;
}

public static int getStartActyStorelist() {
	return START_ACTY_STORELIST;
}

public static int getStartSyncBackground() {
	return START_SYNC_BACKGROUND;
}

public static abstract interface OnLoginListener
  {
    public abstract void onBaseSyncFail(String paramString);
    
    public abstract void onBaseSyncSuccess();
    
    public abstract void onLoginFail(String paramString);
    
    public abstract void onLoginSuccess();
  }
}