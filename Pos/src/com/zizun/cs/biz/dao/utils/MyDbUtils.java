package com.zizun.cs.biz.dao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.FileUtil;

public class MyDbUtils
{
  public static final String CUS_DB_NAME = "CSM_Client.db";
  public static final String DB_PATH = FileUtil.getRootFilePath("CloudStore");
  public static final String EXP_DB_NAME = "EXP_Client.db";
  private static SQLiteDatabase database = null;
  private static DbUtils dbUtils = null;
  
  public static void close()
  {
    if (dbUtils != null)
    {
      dbUtils.close();
      dbUtils = null;
      database = null;
    }
  }
  
  public static String getDBName()
  {
    if (!UserManager.getInstance().isExperienceAccount()) {
      return "CSM_Client.db";
    }
    return "EXP_Client.db";
  }
  
  public static SQLiteDatabase getDataBase(Context paramContext, int paramInt)
  {
    if (database == null)
    {
      database = getDbUtils(paramContext, paramInt).getDatabase();
      database.enableWriteAheadLogging();
      LogUtils.d("database + " + paramInt);
    }
    return database;
  }
  
  public static String getDbPath(int paramInt)
  {
    return DB_PATH + paramInt + "/" + getDBName();
  }
  
  public static DbUtils getDbUtils(Context paramContext, int paramInt)
  {
    try
    {
      if ((dbUtils != null) && (!dbUtils.getDatabase().isOpen())) {
        dbUtils = null;
      }
      dbUtils = DbUtils.create(paramContext, DB_PATH + paramInt + "/", getDBName());
      LogUtils.d("database + " + paramInt);
      dbUtils.configAllowTransaction(true);
      dbUtils.configDebug(true);
      paramContext = dbUtils;
      return paramContext;
    }
    finally {}
  }
}