package com.zizun.cs.biz.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import com.zizun.cs.biz.dao.Invent_HeaderDao;
import com.zizun.cs.biz.dao.S_Sync_UploadDao;
import com.zizun.cs.entities.Invent_Header;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.orm.SQLiteUtil;
import java.util.List;

public class Invent_HeaderDaoImpl
  extends BaseDaoImpl
  implements Invent_HeaderDao
{
  S_Sync_UploadDao syncDao = new S_Sync_UploadDaoImpl();
  
  public List<Invent_Header> GetListInventHeaders()
  {
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject2 = null;
    localSQLiteDatabase.beginTransaction();
    try
    {
      List localList = SQLiteUtil.getQueryList(localSQLiteDatabase, "SELECT * FROM Invent_Header", Invent_Header.class);
      localObject2 = localList;
      localObject3 = localList;
      localObject4 = localList;
      S_Sync_Upload localS_Sync_Upload = new S_Sync_Upload(Invent_Header.class.getSimpleName());
      localObject2 = localList;
      localObject3 = localList;
      localObject4 = localList;
      this.syncDao.insert(localS_Sync_Upload);
      localObject2 = localList;
      localObject3 = localList;
      localObject4 = localList;
      localSQLiteDatabase.setTransactionSuccessful();
      return localList;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return (List<Invent_Header>)localObject2;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return (List<Invent_Header>)localObject3;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return (List<Invent_Header>)localObject4;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
  
  public boolean Insert(Invent_Header paramInvent_Header)
  {
    insertOrUpdateObject(paramInvent_Header);
    paramInvent_Header = new S_Sync_Upload(Invent_Header.class.getSimpleName());
    this.syncDao.insert(paramInvent_Header);
    return false;
  }
  
  public boolean InsertList(List<Invent_Header> paramList)
  {
    insertObjectList(paramList);
    paramList = new S_Sync_Upload(Invent_Header.class.getSimpleName());
    this.syncDao.insert(paramList);
    return false;
  }
}