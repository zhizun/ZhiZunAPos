package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.S_Sync_UploadDao;
import com.zizun.cs.biz.dao.VIPDao;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.VIP;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VIPDaoImpl
  extends BaseDaoImpl
  implements VIPDao
{
  public static final String VIP_FUZZY_SELECT = "SELECT * FROM VIP  WHERE VIP_Name like '%<客户名称>%'  AND VIP_Status=1  OR VIP_Contact like '%<联系人姓名>%'  AND VIP_Status=1  ORDER BY VIP_ID DESC";
  private S_Sync_UploadDao syncDao;
  
  public VIPDaoImpl() {}
  
  public VIPDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.syncDao = new S_Sync_UploadDaoImpl();
  }
  
  public List<VIP> getAllVips(int paramInt)
  {
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), "SELECT * FROM VIP WHERE VIP_STATUS=1 ORDER BY VIP_ID DESC", VIP.class);
      return localList;
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
  
  public ArrayList<VIP> getFuzzyVIP(String paramString)
  {
    paramString = "SELECT * FROM VIP  WHERE VIP_Name like '%<客户名称>%'  AND VIP_Status=1  OR VIP_Contact like '%<联系人姓名>%'  AND VIP_Status=1  ORDER BY VIP_ID DESC".replace("<客户名称>", paramString).replace("<联系人姓名>", paramString);
    try
    {
      paramString = (ArrayList)SQLiteUtil.getQueryList(getDatabase(), paramString, VIP.class);
      return paramString;
    }
    catch (IllegalAccessException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (InstantiationException paramString)
    {
      for (;;)
      {
        paramString.printStackTrace();
      }
    }
    catch (ClassNotFoundException paramString)
    {
      for (;;)
      {
        paramString.printStackTrace();
      }
    }
  }
  
  public List<VIP> getListVips()
  {
    Object localObject = getDatabase();
    try
    {
      localObject = SQLiteUtil.getQueryList((SQLiteDatabase)localObject, "SELECT * FROM VIP WHERE VIP_STATUS=1 ORDER BY VIP_ID DESC", VIP.class);
      return (List<VIP>)localObject;
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
  
  public VIP getSingleVip(int paramInt1, int paramInt2)
  {
    Object localObject2 = null;
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), "SELECT * FROM VIP WHERE VIP_STATUS=1 ORDER BY VIP_ID DESC", VIP.class);
      Object localObject1 = localObject2;
      if (localList != null)
      {
        localObject1 = localObject2;
        if (localList.size() != 0) {
          localObject1 = (VIP)localList.get(0);
        }
      }
      return (VIP)localObject1;
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
  
  public VIP getVIPByID(long paramLong)
  {
    Object localObject2 = null;
    Object localObject1 = "select * from VIP where VIP_ID = <VIP_ID>".replace("<VIP_ID>", paramLong);
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), (String)localObject1, VIP.class);
      localObject1 = localObject2;
      if (localList != null)
      {
        localObject1 = localObject2;
        if (localList.size() > 0) {
          localObject1 = (VIP)localList.get(0);
        }
      }
      return (VIP)localObject1;
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
  
  public VIP getVipName(String paramString)
  {
    Object localObject = null;
    paramString = "SELECT * FROM VIP AS v WHERE v.VIP_Name = <联系人> AND v.VIP_Status = 1".replace("<联系人>", "'" + paramString + "'");
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), paramString, VIP.class);
      paramString = (String)localObject;
      if (localList != null)
      {
        paramString = (String)localObject;
        if (localList.size() > 0) {
          paramString = (VIP)localList.get(0);
        }
      }
      return paramString;
    }
    catch (IllegalAccessException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (InstantiationException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public VIP getVipNameCancle(String paramString)
  {
    Object localObject = null;
    paramString = "SELECT * FROM VIP AS v WHERE v.VIP_Name = <联系人>\t AND v.VIP_Status = 2".replace("<联系人>", "'" + paramString + "'");
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), paramString, VIP.class);
      paramString = (String)localObject;
      if (localList != null)
      {
        paramString = (String)localObject;
        if (localList.size() > 0) {
          paramString = (VIP)localList.get(0);
        }
      }
      return paramString;
    }
    catch (IllegalAccessException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (InstantiationException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public boolean insert(VIP paramVIP)
  {
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    Object localObject = new SqlUtil(SqlUtil.SqlType.INSERT, paramVIP);
    paramVIP = ((SqlUtil)localObject).getSqlBuffer().toString();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    LogUtils.i("sql = " + paramVIP);
    LogUtils.i("bindArgs = " + Arrays.asList((Object[])localObject).toString());
    try
    {
      localSQLiteDatabase.execSQL(paramVIP, (Object[])localObject);
      paramVIP = new S_Sync_Upload(VIP.class.getSimpleName());
      this.syncDao.insert(paramVIP);
      localSQLiteDatabase.setTransactionSuccessful();
      return true;
    }
    catch (Exception paramVIP)
    {
      paramVIP.printStackTrace();
      return false;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
  
  /* Error */
  public boolean insertList(List<VIP> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 37	com/zizun/cs/biz/dao/impl/VIPDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore_2
    //   5: aload_2
    //   6: invokevirtual 127	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   9: aload_1
    //   10: invokeinterface 197 1 0
    //   15: astore_1
    //   16: aload_1
    //   17: invokeinterface 203 1 0
    //   22: ifne +37 -> 59
    //   25: new 173	com/zizun/cs/entities/S_Sync_Upload
    //   28: dup
    //   29: ldc 41
    //   31: invokevirtual 178	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   34: invokespecial 179	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   37: astore_1
    //   38: aload_0
    //   39: getfield 25	com/zizun/cs/biz/dao/impl/VIPDaoImpl:syncDao	Lcom/zizun/cs/biz/dao/S_Sync_UploadDao;
    //   42: aload_1
    //   43: invokeinterface 184 2 0
    //   48: pop
    //   49: aload_2
    //   50: invokevirtual 187	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   53: aload_2
    //   54: invokevirtual 190	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   57: iconst_1
    //   58: ireturn
    //   59: aload_1
    //   60: invokeinterface 207 1 0
    //   65: checkcast 41	com/zizun/cs/entities/VIP
    //   68: astore_3
    //   69: aload_0
    //   70: invokevirtual 37	com/zizun/cs/biz/dao/impl/VIPDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   73: ldc 41
    //   75: invokevirtual 178	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   78: aload_3
    //   79: invokestatic 211	com/zizun/cs/orm/SQLiteUtil:insertOrUpdateTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/Object;)V
    //   82: goto -66 -> 16
    //   85: astore_1
    //   86: aload_1
    //   87: invokevirtual 191	java/lang/Exception:printStackTrace	()V
    //   90: aload_2
    //   91: invokevirtual 190	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   94: iconst_0
    //   95: ireturn
    //   96: astore_1
    //   97: aload_2
    //   98: invokevirtual 190	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   101: aload_1
    //   102: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	103	0	this	VIPDaoImpl
    //   0	103	1	paramList	List<VIP>
    //   4	94	2	localSQLiteDatabase	SQLiteDatabase
    //   68	11	3	localVIP	VIP
    // Exception table:
    //   from	to	target	type
    //   5	16	85	java/lang/Exception
    //   16	53	85	java/lang/Exception
    //   59	82	85	java/lang/Exception
    //   5	16	96	finally
    //   16	53	96	finally
    //   59	82	96	finally
    //   86	90	96	finally
  }
  
  public boolean update(VIP paramVIP)
  {
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    Object localObject = new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, paramVIP);
    paramVIP = ((SqlUtil)localObject).getUpdateForLocalBeforeSql();
    String str = ((SqlUtil)localObject).getSqlBuffer().toString();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    LogUtils.i("sql = " + str);
    LogUtils.i("bindArgs = " + Arrays.asList((Object[])localObject).toString());
    try
    {
      localSQLiteDatabase.execSQL(paramVIP);
      localSQLiteDatabase.execSQL(str, (Object[])localObject);
      paramVIP = new S_Sync_Upload(VIP.class.getSimpleName());
      this.syncDao.insert(paramVIP);
      localSQLiteDatabase.setTransactionSuccessful();
      return true;
    }
    catch (Exception paramVIP)
    {
      paramVIP.printStackTrace();
      return false;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
}