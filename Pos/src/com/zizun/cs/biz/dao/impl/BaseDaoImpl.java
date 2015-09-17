package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import com.zhizun.pos.app.StoreApplication;
import java.util.Iterator;
import java.util.List;

public class BaseDaoImpl
{
  private int MerchantID = 0;
  private Context context;
  private SQLiteDatabase database = null;
  
  public BaseDaoImpl()
  {
    init();
  }
  
  public BaseDaoImpl(Context paramContext, int paramInt)
  {
    init(paramContext, paramInt);
  }
  
  private void init()
  {
    this.context = StoreApplication.getContext();
    this.MerchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
  }
  
  private void init(Context paramContext, int paramInt)
  {
    this.context = paramContext;
    this.MerchantID = paramInt;
  }
  
  public Context getContext()
  {
    return this.context;
  }
  
  protected SQLiteDatabase getDatabase()
  {
    try
    {
      LogUtils.d("database + " + this.MerchantID);
      this.database = MyDbUtils.getDataBase(this.context, this.MerchantID);
      SQLiteDatabase localSQLiteDatabase = this.database;
      return localSQLiteDatabase;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected DbUtils getDbUtils()
  {
    try
    {
      LogUtils.d("database + " + this.MerchantID);
      DbUtils localDbUtils = MyDbUtils.getDbUtils(this.context, this.MerchantID);
      return localDbUtils;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getMerchantID()
  {
    return this.MerchantID;
  }
  
  public long getStoreID()
  {
    return UserManager.getInstance().getCurrentStore().getStore_ID();
  }
  
  public int getUserID()
  {
    return UserManager.getInstance().getCurrentUser().getUser_ID();
  }
  
  protected void insertObject(Object paramObject)
  {
    Object localObject = new SqlUtil(SqlUtil.SqlType.INSERT, paramObject);
    paramObject = ((SqlUtil)localObject).getSqlBuffer().toString();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    LogUtil.logD("=================" + (String)paramObject + localObject.toString());
    getDatabase().execSQL((String)paramObject, (Object[])localObject);
  }
  
  protected void insertObjectList(List<?> paramList)
  {
    paramList = paramList.iterator();
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      insertObject(paramList.next());
    }
  }
  
  protected void insertOrUpdateObject(Object paramObject)
  {
    SQLiteUtil.insertOrUpdateTable(getDatabase(), paramObject);
  }
  
  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }
}