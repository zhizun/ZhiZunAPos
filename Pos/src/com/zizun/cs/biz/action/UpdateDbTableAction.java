package com.zizun.cs.biz.action;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.DBVersionManager;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.api.DBVersionGetSqlResult;
import com.zizun.cs.entities.api.DBVersionGetSqlResult.Reg_DBVersion;
import com.zizun.cs.entities.api.DBVersionGetSqlResult.getDBVersionSqlResultData;
import com.zhizun.pos.app.StoreApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class UpdateDbTableAction
{
  private static final String CREATE_DB_VERSION_TABLE = " Create  TABLE MAIN.[DB_Version]( [VersionNumber] int , Primary Key(VersionNumber)  )";
  private static final int GET_DBVERSION_SQL_FROM_SERVER = 1;
  private static final String INSERT_DEFULT_VERSIONNUMBER = " insert into db_version(VersionNumber)values(0)";
  private static final String SELECT_COUNT_DB_VERSION = " Select  count(*) as co  From MAIN.[sqlite_master] where type = 'table' and name = 'DB_Version'";
  private static final String SELECT_VERSIONNUMBER = " Select  VersionNumber  From DB_Version";
  private static final int UPDATE_DBVERSION_FAIL = 3;
  private static final int UPDATE_DBVERSION_SUCCESS = 2;
  private static final String UPDATE_VERSIONNUMBER = " UPDATE DB_VERSION  SET VersionNumber = [value]";
  private int VersionNumber = 0;
  private SQLiteDatabase db;
  Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        UpdateDbTableAction.this.getDBVersionSql();
        continue;
        if (UpdateDbTableAction.this.onUpdateDBVersionListener != null)
        {
          UpdateDbTableAction.this.onUpdateDBVersionListener.onUpdateDBVersionSuccess();
          continue;
          paramAnonymousMessage = (String)paramAnonymousMessage.obj;
          if (UpdateDbTableAction.this.onUpdateDBVersionListener != null) {
            UpdateDbTableAction.this.onUpdateDBVersionListener.onUpdateDBVersionFail(paramAnonymousMessage);
          }
        }
      }
    }
  });
  private int merchantID;
  private OnUpdateDBVersionListener onUpdateDBVersionListener = null;
  private List<String[]> sqlScriptList = null;
  
  public UpdateDbTableAction(int paramInt)
  {
    this.merchantID = paramInt;
    this.db = MyDbUtils.getDbUtils(StoreApplication.getContext(), this.merchantID).getDatabase();
  }
  
  private int getDBVersionNumber()
  {
    j = 0;
    int i = 0;
    localObject3 = null;
    localObject1 = null;
    for (;;)
    {
      try
      {
        localCursor = this.db.rawQuery(" Select  VersionNumber  From DB_Version", null);
        localObject1 = localCursor;
        j = i;
        localObject3 = localCursor;
        boolean bool = localCursor.moveToNext();
        if (bool) {
          continue;
        }
        k = i;
        if (localCursor != null)
        {
          localCursor.close();
          k = i;
        }
      }
      catch (SQLException localSQLException)
      {
        Cursor localCursor;
        localObject3 = localObject1;
        localSQLException.printStackTrace();
        int k = j;
        return j;
      }
      finally
      {
        if (localObject3 == null) {
          continue;
        }
        ((Cursor)localObject3).close();
      }
      return k;
      localObject1 = localCursor;
      j = i;
      localObject3 = localCursor;
      i = localCursor.getInt(localCursor.getColumnIndex("VersionNumber"));
    }
  }
  
  private void getDBVersionSql()
  {
    DBVersionManager.getDBVersionSqlFromAPI(this.VersionNumber, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        UpdateDbTableAction.this.handler.obtainMessage(3, paramAnonymousString).sendToTarget();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        LogUtils.i((String)paramAnonymousResponseInfo.result);
        paramAnonymousResponseInfo = DBVersionManager.getDBVersionGetSqlResultFromJsonString((String)paramAnonymousResponseInfo.result);
        if (200 == paramAnonymousResponseInfo.Code)
        {
          if (UpdateDbTableAction.this.initSqlList(paramAnonymousResponseInfo))
          {
            UpdateDbTableAction.this.updateSqlToDBVersionInThread();
            return;
          }
          UpdateDbTableAction.this.handler.obtainMessage(2).sendToTarget();
          return;
        }
        UpdateDbTableAction.this.handler.obtainMessage(3, paramAnonymousResponseInfo.Message).sendToTarget();
      }
    });
  }
  
  /* Error */
  private boolean initDBVersionTable()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   8: ldc 28
    //   10: aconst_null
    //   11: invokevirtual 135	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   14: astore 4
    //   16: iconst_m1
    //   17: istore_1
    //   18: aload 4
    //   20: astore_2
    //   21: aload 4
    //   23: astore_3
    //   24: aload 4
    //   26: invokeinterface 140 1 0
    //   31: ifne +51 -> 82
    //   34: iload_1
    //   35: ifgt +33 -> 68
    //   38: aload 4
    //   40: astore_2
    //   41: aload 4
    //   43: astore_3
    //   44: aload_0
    //   45: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   48: ldc 19
    //   50: invokevirtual 166	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   53: aload 4
    //   55: astore_2
    //   56: aload 4
    //   58: astore_3
    //   59: aload_0
    //   60: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   63: ldc 25
    //   65: invokevirtual 166	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   68: aload 4
    //   70: ifnull +10 -> 80
    //   73: aload 4
    //   75: invokeinterface 143 1 0
    //   80: iconst_1
    //   81: ireturn
    //   82: aload 4
    //   84: astore_2
    //   85: aload 4
    //   87: astore_3
    //   88: aload 4
    //   90: aload 4
    //   92: ldc -88
    //   94: invokeinterface 148 2 0
    //   99: invokeinterface 152 2 0
    //   104: istore_1
    //   105: goto -87 -> 18
    //   108: astore 4
    //   110: aload_2
    //   111: astore_3
    //   112: aload 4
    //   114: invokevirtual 155	android/database/SQLException:printStackTrace	()V
    //   117: aload_2
    //   118: ifnull +9 -> 127
    //   121: aload_2
    //   122: invokeinterface 143 1 0
    //   127: iconst_0
    //   128: ireturn
    //   129: astore_2
    //   130: aload_3
    //   131: ifnull +9 -> 140
    //   134: aload_3
    //   135: invokeinterface 143 1 0
    //   140: aload_2
    //   141: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	142	0	this	UpdateDbTableAction
    //   17	88	1	i	int
    //   3	119	2	localObject1	Object
    //   129	12	2	localObject2	Object
    //   1	134	3	localObject3	Object
    //   14	77	4	localCursor	Cursor
    //   108	5	4	localSQLException	SQLException
    // Exception table:
    //   from	to	target	type
    //   4	16	108	android/database/SQLException
    //   24	34	108	android/database/SQLException
    //   44	53	108	android/database/SQLException
    //   59	68	108	android/database/SQLException
    //   88	105	108	android/database/SQLException
    //   4	16	129	finally
    //   24	34	129	finally
    //   44	53	129	finally
    //   59	68	129	finally
    //   88	105	129	finally
    //   112	117	129	finally
  }
  
  private void initDbVersionNumber()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        UpdateDbTableAction.this.initDBVersionTable();
        UpdateDbTableAction.this.VersionNumber = UpdateDbTableAction.this.getDBVersionNumber();
        UpdateDbTableAction.this.handler.obtainMessage(1).sendToTarget();
      }
    });
  }
  
  private boolean initSqlList(DBVersionGetSqlResult paramDBVersionGetSqlResult)
  {
    paramDBVersionGetSqlResult = paramDBVersionGetSqlResult.Data;
    Object localObject = paramDBVersionGetSqlResult.getSqlData();
    if ((localObject != null) && (((List)localObject).size() > 0))
    {
      this.sqlScriptList = new ArrayList();
      localObject = ((List)localObject).iterator();
      for (;;)
      {
        if (!((Iterator)localObject).hasNext())
        {
          this.VersionNumber = paramDBVersionGetSqlResult.getVer();
          return true;
        }
        String[] arrayOfString = ((DBVersionGetSqlResult.Reg_DBVersion)((Iterator)localObject).next()).getSqlScript().split("\\;");
        this.sqlScriptList.add(arrayOfString);
      }
    }
    return false;
  }
  
  /* Error */
  private boolean updateSqlToDBVersion()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   4: invokevirtual 239	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   7: aload_0
    //   8: getfield 60	com/zizun/cs/biz/action/UpdateDbTableAction:sqlScriptList	Ljava/util/List;
    //   11: invokeinterface 206 1 0
    //   16: astore_3
    //   17: aload_3
    //   18: invokeinterface 211 1 0
    //   23: ifne +52 -> 75
    //   26: ldc 38
    //   28: ldc -15
    //   30: new 243	java/lang/StringBuilder
    //   33: dup
    //   34: aload_0
    //   35: getfield 58	com/zizun/cs/biz/action/UpdateDbTableAction:VersionNumber	I
    //   38: invokestatic 247	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   41: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   44: invokevirtual 252	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokevirtual 256	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   50: astore_3
    //   51: aload_0
    //   52: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   55: aload_3
    //   56: invokevirtual 166	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   59: aload_0
    //   60: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   63: invokevirtual 259	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   66: aload_0
    //   67: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   70: invokevirtual 262	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   73: iconst_1
    //   74: ireturn
    //   75: aload_3
    //   76: invokeinterface 218 1 0
    //   81: checkcast 264	[Ljava/lang/String;
    //   84: astore 4
    //   86: aload 4
    //   88: arraylength
    //   89: istore_2
    //   90: iconst_0
    //   91: istore_1
    //   92: iload_1
    //   93: iload_2
    //   94: if_icmpge -77 -> 17
    //   97: aload 4
    //   99: iload_1
    //   100: aaload
    //   101: astore 5
    //   103: aload 5
    //   105: invokevirtual 267	java/lang/String:trim	()Ljava/lang/String;
    //   108: invokestatic 273	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   111: ifne +12 -> 123
    //   114: aload_0
    //   115: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   118: aload 5
    //   120: invokevirtual 166	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   123: iload_1
    //   124: iconst_1
    //   125: iadd
    //   126: istore_1
    //   127: goto -35 -> 92
    //   130: astore_3
    //   131: aload_3
    //   132: invokevirtual 155	android/database/SQLException:printStackTrace	()V
    //   135: aload_0
    //   136: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   139: invokevirtual 262	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   142: iconst_0
    //   143: ireturn
    //   144: astore_3
    //   145: aload_0
    //   146: getfield 92	com/zizun/cs/biz/action/UpdateDbTableAction:db	Landroid/database/sqlite/SQLiteDatabase;
    //   149: invokevirtual 262	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   152: aload_3
    //   153: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	UpdateDbTableAction
    //   91	36	1	i	int
    //   89	6	2	j	int
    //   16	60	3	localObject1	Object
    //   130	2	3	localSQLException	SQLException
    //   144	9	3	localObject2	Object
    //   84	14	4	arrayOfString	String[]
    //   101	18	5	str	String
    // Exception table:
    //   from	to	target	type
    //   0	17	130	android/database/SQLException
    //   17	66	130	android/database/SQLException
    //   75	90	130	android/database/SQLException
    //   103	123	130	android/database/SQLException
    //   0	17	144	finally
    //   17	66	144	finally
    //   75	90	144	finally
    //   103	123	144	finally
    //   131	135	144	finally
  }
  
  private void updateSqlToDBVersionInThread()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        if (UpdateDbTableAction.this.updateSqlToDBVersion())
        {
          UpdateDbTableAction.this.handler.obtainMessage(2).sendToTarget();
          return;
        }
        UpdateDbTableAction.this.handler.obtainMessage(3, "本地数据库更新失败").sendToTarget();
      }
    });
  }
  
  public void execute()
  {
    initDbVersionNumber();
  }
  
  public void setOnUpdateDBVersionListener(OnUpdateDBVersionListener paramOnUpdateDBVersionListener)
  {
    this.onUpdateDBVersionListener = paramOnUpdateDBVersionListener;
  }
  
  public static abstract interface OnUpdateDBVersionListener
  {
    public abstract void onUpdateDBVersionFail(String paramString);
    
    public abstract void onUpdateDBVersionSuccess();
  }
}