package com.zizun.cs.biz.dao.impl;

import android.database.sqlite.SQLiteDatabase;
import com.zizun.cs.biz.dao.Invent_DetailDao;
import com.zizun.cs.biz.dao.S_Sync_UploadDao;
import com.zizun.cs.entities.Invent_Detail;
import com.zizun.cs.entities.Invent_Header;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.orm.SQLiteUtil;
import java.util.List;

public class Invent_DetailDaoImpl
  extends BaseDaoImpl
  implements Invent_DetailDao
{
  public static final String INVENT_DETAIL_SELECT = "SELECT ih.Invent_ID,ih.Invent_Number,ih.Invent_Date,ps.ProductSku_Name,id.Onhand_Disk,id.Onhand_Scan,id.Quantity_AdjustFROM Invent_Header AS ih INNER JOIN Invent_Detail AS id ON id.Merchant_ID = ih.Merchant_IDAND id.Invent_ID = ih.Invent_ID INNER JOIN ProductSku AS ps ON ps.Merchant_ID = id.Merchant_IDAND ps.ProductSku_ID = id.ProductSku_ID";
  S_Sync_UploadDao syncDao = new S_Sync_UploadDaoImpl();
  
  public List<Invent_Detail> GetAllInventDetails()
  {
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject2 = null;
    localSQLiteDatabase.beginTransaction();
    try
    {
      List localList = SQLiteUtil.getQueryList(localSQLiteDatabase, "SELECT ih.Invent_ID,ih.Invent_Number,ih.Invent_Date,ps.ProductSku_Name,id.Onhand_Disk,id.Onhand_Scan,id.Quantity_AdjustFROM Invent_Header AS ih INNER JOIN Invent_Detail AS id ON id.Merchant_ID = ih.Merchant_IDAND id.Invent_ID = ih.Invent_ID INNER JOIN ProductSku AS ps ON ps.Merchant_ID = id.Merchant_IDAND ps.ProductSku_ID = id.ProductSku_ID", Invent_Detail.class);
      localObject2 = localList;
      localObject3 = localList;
      localObject4 = localList;
      S_Sync_Upload localS_Sync_Upload = new S_Sync_Upload(Invent_Detail.class.getSimpleName());
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
      return (List<Invent_Detail>)localObject2;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return (List<Invent_Detail>)localObject3;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return (List<Invent_Detail>)localObject4;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
  
  public boolean Insert(Invent_Detail paramInvent_Detail)
  {
    insertOrUpdateObject(paramInvent_Detail);
    paramInvent_Detail = new S_Sync_Upload(Invent_Detail.class.getSimpleName());
    this.syncDao.insert(paramInvent_Detail);
    return false;
  }
  
  public boolean InsertList(List<Invent_Detail> paramList)
  {
    insertObjectList(paramList);
    paramList = new S_Sync_Upload(Invent_Header.class.getSimpleName());
    this.syncDao.insert(paramList);
    return false;
  }
}