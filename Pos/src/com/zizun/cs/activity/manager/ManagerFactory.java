package com.zizun.cs.activity.manager;

import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.action.BaseSyncAction;
import com.zizun.cs.biz.action.SyncDownAction;
import com.zizun.cs.biz.action.SyncUpAction;
import com.zizun.cs.biz.action.SyncUpAndDownAction;
import com.zizun.cs.biz.dao.AccountsDao;
import com.zizun.cs.biz.dao.ProductDao;
import com.zizun.cs.biz.dao.S_ModuleDao;
import com.zizun.cs.biz.dao.StatisticsDetailListDao;
import com.zizun.cs.biz.dao.impl.AccountsDaoImpl;
import com.zizun.cs.biz.dao.impl.ProductDaoImpl;
import com.zizun.cs.biz.dao.impl.S_ModuleDaoImpl;
import com.zizun.cs.biz.dao.impl.StatisticsDetailListDaoImpl;
import com.zizun.cs.biz.dao.trans.RegisterTrans;
import com.zizun.cs.biz.dao.trans.impl.RegisterTransaction;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zhizun.pos.app.StoreApplication;

public class ManagerFactory
{
  private static LoadingManager loadingManager;
  private static LoginManager loginManager;
  private static PswdManager pswdManager;
  private static RegisterManager registerManager;
  private static SyncManager syncManager;
  
  public static AccountsDao getAccountsDao(String paramString)
  {
    return AccountsDaoImpl.getInstance(paramString);
  }
  
  public static LoadingManager getLoadingManager()
  {
    if (loadingManager == null) {}
    try
    {
      if (loadingManager == null) {
        loadingManager = new LoadingManager();
      }
      return loadingManager;
    }
    finally {}
  }
  
  public static LoginManager getLoginManager()
  {
    if (loginManager == null) {}
    try
    {
      if (loginManager == null) {
        loginManager = new LoginManager(StoreApplication.getContext());
      }
      return loginManager;
    }
    finally {}
  }
  
  public static ProductDao getProductDao()
  {
    return ProductDaoImpl.getInstance();
  }
  
  public static PswdManager getPswdManager()
  {
    if (pswdManager == null) {}
    try
    {
      if (pswdManager == null) {
        pswdManager = new PswdManager(StoreApplication.getContext());
      }
      return pswdManager;
    }
    finally {}
  }
  
  public static RegisterManager getRegisterManager()
  {
    if (registerManager == null) {}
    try
    {
      if (registerManager == null) {
        registerManager = new RegisterManager();
      }
      return registerManager;
    }
    finally {}
  }
  
  public static RegisterTrans getRegisterTrans()
  {
    return RegisterTransaction.getInstance();
  }
  
  public static S_ModuleDao getS_ModuleDao()
  {
    return S_ModuleDaoImpl.getInstance();
  }
  
  public static StatisticsDetailListDao getStatisticsDetailListDao()
  {
    return StatisticsDetailListDaoImpl.getInstance();
  }
  
  public static SyncManager getSyncManager()
  {
    if (syncManager == null) {}
    try
    {
      if (syncManager == null) {
        syncManager = new SyncManager();
      }
      return syncManager;
    }
    finally {}
  }
  
  public static void reSet()
  {
    LogUtils.i("ManagerFactory重置数据库重�?");
    MyDbUtils.close();
    SyncBaseDataManager.clear();
    RegisterTransaction.clear();
    S_ModuleDaoImpl.clear();
    ProductDaoImpl.clear();
    UserManager.clear();
    SyncUpAction.clear();
    SyncDownAction.clear();
    BaseSyncAction.clear();
    SyncUpAndDownAction.clear();
    StatisticsDetailListDaoImpl.clear();
    pswdManager = null;
    loadingManager = null;
    loginManager = null;
    registerManager = null;
    syncManager = null;
  }
}