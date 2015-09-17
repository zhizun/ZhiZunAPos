package com.zizun.cs.biz.dao.trans.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.trans.LoginTrans;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.LoginResult.LoginResultData;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.Iterator;
import java.util.List;

public class LoginTransaction
  extends BaseTransaction
  implements LoginTrans
{
  public LoginTransaction(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  private StringBuffer initSqlBufferForExist(String paramString, List<String> paramList, List<Object> paramList1)
  {
    paramString = new StringBuffer("Select * From " + paramString + " Where 1=1 ");
    int i = 0;
    for (;;)
    {
      if (i >= paramList.size())
      {
        LogUtils.i(paramString.toString());
        return paramString;
      }
      paramString.append(" and " + (String)paramList.get(i) + " = " + paramList1.get(i));
      i += 1;
    }
  }
  
  private void insertOrUpdateTable(SQLiteDatabase paramSQLiteDatabase, String paramString, Object paramObject)
  {
    SqlUtil localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramObject);
    if (!isTargetExist(paramSQLiteDatabase, initSqlBufferForExist(paramString, localSqlUtil.getIdNames(), localSqlUtil.getIdValus()).toString()))
    {
      paramSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
      return;
    }
    paramString = new SqlUtil(SqlUtil.SqlType.UPDATE, paramObject);
    paramSQLiteDatabase.execSQL(paramString.getSqlBuffer().toString(), paramString.getParam().toArray());
  }
  
  private boolean isTargetExist(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Object localObject = null;
    SQLiteDatabase localSQLiteDatabase = null;
    for (;;)
    {
      try
      {
        paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(paramString, null);
        localSQLiteDatabase = paramSQLiteDatabase;
        localObject = paramSQLiteDatabase;
        boolean bool = paramSQLiteDatabase.moveToNext();
        if (bool)
        {
          if (paramSQLiteDatabase != null) {
            paramSQLiteDatabase.close();
          }
          return true;
        }
      }
      catch (Exception paramSQLiteDatabase)
      {
        localObject = localSQLiteDatabase;
        paramSQLiteDatabase.printStackTrace();
        if (localSQLiteDatabase != null) {
          localSQLiteDatabase.close();
        }
        return false;
      }
      finally
      {
        if (localObject != null) {
          ((Cursor)localObject).close();
        }
      }
      if (paramSQLiteDatabase != null) {
        paramSQLiteDatabase.close();
      }
    }
  }
  
  public boolean doLoginTransaction(LoginResult.LoginResultData paramLoginResultData)
  {
    Object localObject1 = paramLoginResultData.S_Merchant;
    S_User localS_User = paramLoginResultData.S_User;
    List localList1 = paramLoginResultData.Store;
    List localList2 = paramLoginResultData.S_Module;
    paramLoginResultData = getDatabase();
    paramLoginResultData.beginTransaction();
    for (;;)
    {
      try
      {
        insertOrUpdateTable(paramLoginResultData, "S_Merchant", localObject1);
        insertOrUpdateTable(paramLoginResultData, "S_User", localS_User);
        localObject1 = localList1.iterator();
        if (((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject1 = localList2.iterator();
      }
      catch (SQLException localSQLException)
      {
        localSQLException.printStackTrace();
        return false;
        insertOrUpdateTable(paramLoginResultData, "S_Module", (S_Module)localSQLException.next());
        continue;
      }
      finally
      {
        paramLoginResultData.endTransaction();
      }
      if (((Iterator)localObject1).hasNext()) {
        continue;
      }
      paramLoginResultData.setTransactionSuccessful();
      paramLoginResultData.endTransaction();
      return true;
      insertOrUpdateTable(paramLoginResultData, "Store", (Store)((Iterator)localObject1).next());
    }
  }
}