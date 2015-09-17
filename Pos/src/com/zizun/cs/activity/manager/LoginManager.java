package com.zizun.cs.activity.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.AsyncTask.AsyncTaskCallBack;
import com.zizun.cs.biz.api.StoreAPI;
import com.zizun.cs.biz.api.UserAPI;
import com.zizun.cs.biz.dao.S_LoginDao;
import com.zizun.cs.biz.dao.S_ModuleDao;
import com.zizun.cs.biz.dao.S_UserDao;
import com.zizun.cs.biz.dao.StoreDao;
import com.zizun.cs.biz.dao.trans.LoginTrans;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.FileUtil;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.common.utils.NetworkUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.entities.S_Login;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zizun.cs.entities.api.LoginParam;
import com.zizun.cs.entities.api.LoginResult;
import com.zizun.cs.entities.api.LoginResult.LoginResultData;
import com.zizun.cs.entities.api.StoreGetResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginManager
{
  private Context context = null;
  private PreferencesUtil loginMapPreferencesUtil = null;
  private PreferencesUtil preferencesUtil = null;
  
  public LoginManager(Context paramContext)
  {
    this.context = paramContext;
    this.loginMapPreferencesUtil = new PreferencesUtil(paramContext, "NAME_LOGIN");
  }
  
  public static void closeExistDatabase() {}
  
  public void CopyDBToSDCardOutUIThread(int paramInt)
  {
    try
    {
      FileUtil.CopyDbToSdcard(this.context, MyDbUtils.getDBName(), MyDbUtils.DB_PATH + paramInt + "/");
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public boolean checkNetwork()
  {
    return NetworkUtil.hasNetwork(this.context);
  }
  
  public List<LocalLoginParam> deleteLoginInfoInPreference(LocalLoginParam paramLocalLoginParam)
  {
    SharedPreferences.Editor localEditor = this.loginMapPreferencesUtil.getPreference().edit();
    localEditor.remove(paramLocalLoginParam.userName);
    localEditor.commit();
    paramLocalLoginParam = getLoginInfoList();
    LogUtils.i(JsonUtil.toJson(paramLocalLoginParam));
    return paramLocalLoginParam;
  }
  
  public void doLocalGetAllStoreTask(int paramInt, StoreDao paramStoreDao, AsyncTaskCallBack<List<Store>> paramAsyncTaskCallBack)
  {
    new localGetAllStoreTask(paramInt, paramStoreDao, paramAsyncTaskCallBack).executeOnExecutor(ExecutorUtils.EXECUTOR, new Void[0]);
  }
  
  public List<Store> getAllStoreByUserOutUIThread(int paramInt, StoreDao paramStoreDao)
  {
    return paramStoreDao.getAllStores(paramInt);
  }
  
  public List<LocalLoginParam> getLoginInfoList()
  {
    new HashMap();
    Object localObject = this.loginMapPreferencesUtil.getPreference().getAll();
    if (localObject == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    localObject = ((Map)localObject).keySet().iterator();
    for (;;)
    {
      if (!((Iterator)localObject).hasNext())
      {
        LogUtils.i(JsonUtil.toJson(localArrayList));
        return localArrayList;
      }
      String str = (String)((Iterator)localObject).next();
      localArrayList.add((LocalLoginParam)this.loginMapPreferencesUtil.getObject(str, LocalLoginParam.class));
    }
  }
  
  public LoginResult getLoginResultFromJson(String paramString)
  {
    return (LoginResult)UserAPI.getAPIResultFromJson(paramString, LoginResult.class);
  }
  
  public List<S_Module> getMoudleListByUserIDOutUIThread(int paramInt, S_ModuleDao paramS_ModuleDao)
  {
    return paramS_ModuleDao.getAllModuleByUserID(paramInt);
  }
  
  public StoreGetResult getStoreListByMerchantIDFromJson(String paramString)
  {
    return (StoreGetResult)JsonUtil.fromJson(paramString, StoreGetResult.class);
  }
  
  public void getStoresByMerchantID(int paramInt, RequestCallBack<String> paramRequestCallBack)
  {
    StoreAPI.getStoreFromAPI(paramInt, paramRequestCallBack);
  }
  
  public boolean insertS_LoginOutUIThread(S_LoginDao paramS_LoginDao, S_Login paramS_Login)
  {
    return paramS_LoginDao.insert(paramS_Login);
  }
  
  public boolean isDbFileExist(int paramInt)
  {
    return FileUtil.isFileExist(MyDbUtils.getDbPath(paramInt));
  }
  
  public S_User localLoginOutUIThread(S_UserDao paramS_UserDao, LocalLoginParam paramLocalLoginParam)
  {
    return paramS_UserDao.localLogin(paramLocalLoginParam);
  }
  
  public void login(LoginParam paramLoginParam, RequestCallBack<String> paramRequestCallBack)
  {
    UserAPI.login(paramLoginParam, paramRequestCallBack);
  }
  
  public List<LocalLoginParam> saveLocalLoginParamToPreference(LocalLoginParam paramLocalLoginParam)
  {
    this.loginMapPreferencesUtil.saveObject(paramLocalLoginParam.userName, paramLocalLoginParam);
    paramLocalLoginParam = getLoginInfoList();
    LogUtils.i(JsonUtil.toJson(paramLocalLoginParam));
    return paramLocalLoginParam;
  }
  
  public boolean saveLoginResultOnLineOutUIThread(LoginResult.LoginResultData paramLoginResultData, LoginTrans paramLoginTrans)
  {
    return paramLoginTrans.doLoginTransaction(paramLoginResultData);
  }
  
  public void saveLoginResultToPreference(LoginResult paramLoginResult)
  {
    this.preferencesUtil.saveObject("LOGIN_RESULT", paramLoginResult);
    LogUtils.i(JsonUtil.toJson(paramLoginResult));
  }
  
  private class localGetAllStoreTask
    extends AsyncTask<Void, Void, List<Store>>
  {
    private int UserID;
    private AsyncTaskCallBack<List<Store>> asyncTaskCallBack;
    private StoreDao storeDao;
    
    public localGetAllStoreTask(StoreDao paramStoreDao, AsyncTaskCallBack<List<Store>> paramAsyncTaskCallBack)
    {
      this.storeDao = paramAsyncTaskCallBack;
      this.UserID = paramStoreDao;
      AsyncTaskCallBack localAsyncTaskCallBack;
      this.asyncTaskCallBack = localAsyncTaskCallBack;
    }
    
    protected List<Store> doInBackground(Void... paramVarArgs)
    {
      return this.storeDao.getAllStores(this.UserID);
    }
    
    protected void onPostExecute(List<Store> paramList)
    {
      this.asyncTaskCallBack.afterExcute(paramList);
    }
  }
}