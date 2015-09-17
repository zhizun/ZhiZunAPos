package com.zizun.cs.biz.dao.impl;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.ProductGroupDao;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductGroupDaoImpl
  extends BaseDaoImpl
  implements ProductGroupDao
{
  private static final String SQL_SELECT_ALL_GROUP = "SELECT\t* FROM ProductGroup AS pg WHERE pg.Merchant_ID = ? AND pg.Parent_ID = ? AND pg.PG_Status = 1";
  private static final String SQL_SELECT_DEFAULT_GROUP_ID = "SELECT pg.PG_ID FROM ProductGroup AS pg WHERE pg.PG_Type = 1 limit 1";
  private static final String SQL_UPDATE_GROUP = "UPDATE ProductGroup SET PG_Name = ?,Modify_DT = ?,Modify_ID = ?,Sync_Status = 1 WHERE PG_ID = ?";
  private static final String SQL_UPDATE_GROUP_SYNC = "UPDATE ProductGroup SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PG_ID = <PG_ID>";
  
  private boolean createGroup(String paramString, long paramLong)
  {
    Object localObject1 = new ProductGroup();
    ((ProductGroup)localObject1).setCreateEntitySync(UserManager.getInstance().getCurrentUser().getUser_ID());
    ((ProductGroup)localObject1).setMerchant_ID(UserManager.getInstance().getCurrentUser().getMerchant_ID());
    ((ProductGroup)localObject1).setPG_ID(IDUtil.getID());
    ((ProductGroup)localObject1).setParent_ID(paramLong);
    ((ProductGroup)localObject1).setPG_Name(paramString);
    ((ProductGroup)localObject1).setPG_Type((byte)2);
    ((ProductGroup)localObject1).setPG_Status((byte)1);
    paramString = getDatabase();
    paramString.beginTransaction();
    try
    {
      localObject1 = new SqlUtil(SqlUtil.SqlType.INSERT, localObject1);
      paramString.execSQL(((SqlUtil)localObject1).getSqlBuffer().toString(), ((SqlUtil)localObject1).getParam().toArray());
      new S_Sync_UploadDaoImpl().insert(new S_Sync_Upload(ProductGroup.class.getSimpleName()));
      paramString.setTransactionSuccessful();
      return true;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      return false;
    }
    finally
    {
      paramString.endTransaction();
    }
  }
  
  private ArrayList<ProductGroup> getAllGroup(long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    int i = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    Cursor localCursor = getDatabase().rawQuery("SELECT\t* FROM ProductGroup AS pg WHERE pg.Merchant_ID = ? AND pg.Parent_ID = ? AND pg.PG_Status = 1", new String[] { String.valueOf(i), String.valueOf(paramLong) });
    for (;;)
    {
      if (!localCursor.moveToNext()) {
        return localArrayList;
      }
      ProductGroup localProductGroup = new ProductGroup();
      localProductGroup.setMerchant_ID(localCursor.getInt(localCursor.getColumnIndex("Merchant_ID")));
      localProductGroup.setPG_ID(localCursor.getLong(localCursor.getColumnIndex("PG_ID")));
      localProductGroup.setParent_ID(localCursor.getLong(localCursor.getColumnIndex("Parent_ID")));
      localProductGroup.setPG_Name(localCursor.getString(localCursor.getColumnIndex("PG_Name")));
      localProductGroup.setPG_Type((byte)localCursor.getInt(localCursor.getColumnIndex("PG_Type")));
      localArrayList.add(localProductGroup);
    }
  }
  
  public boolean createPrimaryGroup(String paramString)
  {
    return createGroup(paramString, 0L);
  }
  
  public boolean createSecondaryGroup(String paramString, long paramLong)
  {
    return createGroup(paramString, paramLong);
  }
  
  public ArrayList<ProductGroup> getAllPrimaryGroup()
  {
    return getAllGroup(0L);
  }
  
  public ArrayList<ProductGroup> getAllSecondaryGroup(long paramLong)
  {
    return getAllGroup(paramLong);
  }
  
  public long getDefaultGroupId()
  {
    Cursor localCursor = getDatabase().rawQuery("SELECT pg.PG_ID FROM ProductGroup AS pg WHERE pg.PG_Type = 1 limit 1", null);
    if (localCursor.moveToNext()) {
      return localCursor.getLong(localCursor.getColumnIndex("PG_ID"));
    }
    return 0L;
  }
  
  public boolean updateGroup(ProductGroup paramProductGroup)
  {
    Timestamp localTimestamp = DateTimeUtil.getCurrentTime();
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      localSQLiteDatabase.rawQuery("UPDATE ProductGroup SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PG_ID = <PG_ID>".replace("<PG_ID>", String.valueOf(paramProductGroup.getPG_ID())), null);
      localSQLiteDatabase.execSQL("UPDATE ProductGroup SET PG_Name = ?,Modify_DT = ?,Modify_ID = ?,Sync_Status = 1 WHERE PG_ID = ?", new Object[] { paramProductGroup.getPG_Name(), localTimestamp, Integer.valueOf(UserManager.getInstance().getCurrentUser().getUser_ID()), Long.valueOf(paramProductGroup.getPG_ID()) });
      new S_Sync_UploadDaoImpl().insert(new S_Sync_Upload(ProductGroup.class.getSimpleName()));
      localSQLiteDatabase.setTransactionSuccessful();
      LogUtils.i("产品分类数据表删除事务成功!");
      return true;
    }
    catch (SQLException paramProductGroup)
    {
      paramProductGroup.printStackTrace();
      return false;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
}