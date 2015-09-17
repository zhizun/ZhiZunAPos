package com.zizun.cs.biz.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.S_Sync_UploadDao;
import com.zizun.cs.biz.dao.TransactionLogDao;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.List;

public class TransactionLogDaoImpl
  extends BaseDaoImpl
  implements TransactionLogDao
{
  public static final String SELECT_TRANSACTIONLOGLISTBYTLOG_STATUS = "select * FROM TransactionLog WHERE TLog_Status = <TLog_Status>";
  S_Sync_UploadDao syncDao = new S_Sync_UploadDaoImpl();
  
  public static void updateTransactionLogType(SQLiteDatabase paramSQLiteDatabase, TransactionLog paramTransactionLog, int paramInt)
  {
    paramTransactionLog.setTLog_Status((byte)paramInt);
    paramTransactionLog.setUpdateEntitySync(UserManager.getInstance().getCurrentUser().getUser_ID());
    Object localObject = new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, paramTransactionLog);
    paramTransactionLog = ((SqlUtil)localObject).getUpdateForLocalBeforeSql();
    String str = ((SqlUtil)localObject).getSqlBuffer();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    paramSQLiteDatabase.execSQL(paramTransactionLog);
    paramSQLiteDatabase.execSQL(str, (Object[])localObject);
    paramSQLiteDatabase = new S_Sync_Upload(TransactionLog.class.getSimpleName(), (byte)1);
    new S_Sync_UploadDaoImpl().insert(paramSQLiteDatabase);
  }
  
  public List<TransactionLog> getTransactionLogListByTLOG_Status(byte paramByte)
  {
    Object localObject = "select * FROM TransactionLog WHERE TLog_Status = <TLog_Status>".replace("<TLog_Status>", paramByte);
    LogUtils.i((String)localObject);
    try
    {
      localObject = SQLiteUtil.getQueryList(getDatabase(), (String)localObject, TransactionLog.class);
      return (List<TransactionLog>)localObject;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return null;
  }
  
  public boolean insert(TransactionLog paramTransactionLog)
  {
    insertOrUpdateObject(paramTransactionLog);
    paramTransactionLog = new S_Sync_Upload(TransactionLog.class.getSimpleName());
    this.syncDao.insert(paramTransactionLog);
    return false;
  }
  
  public boolean insertList(List<TransactionLog> paramList)
  {
    insertObjectList(paramList);
    paramList = new S_Sync_Upload(TransactionLog.class.getSimpleName());
    this.syncDao.insert(paramList);
    return false;
  }
}