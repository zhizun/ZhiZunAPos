package com.zizun.cs.biz.dao.trans.impl;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.biz.dao.trans.ProductGroupTrans;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.yunmendian.pos.app.StoreApplication;
import java.sql.Timestamp;

public class ProductGroupTranImpl
  extends BaseTransaction
  implements ProductGroupTrans
{
  private static final String SQL_DELETE_PRODUCT_GROUP = "UPDATE ProductGroup SET PG_Status = 3,Modify_DT = ?,Modify_ID = ?, Sync_Status = 1 WHERE PG_ID = ?";
  private static final String SQL_DELETE_PRODUCT_GROUP_SYNC = "UPDATE ProductGroup SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PG_ID = <PG_ID>";
  
  public ProductGroupTranImpl()
  {
    super(StoreApplication.getContext(), UserManager.getInstance().getCurrentUser().getMerchant_ID());
  }
  
  public boolean deleteProductGroup(ProductGroup paramProductGroup)
  {
    Timestamp localTimestamp = DateTimeUtil.getCurrentTime();
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      localSQLiteDatabase.rawQuery("UPDATE ProductGroup SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PG_ID = <PG_ID>".replace("<PG_ID>", String.valueOf(paramProductGroup.getPG_ID())), null);
      localSQLiteDatabase.execSQL("UPDATE ProductGroup SET PG_Status = 3,Modify_DT = ?,Modify_ID = ?, Sync_Status = 1 WHERE PG_ID = ?", new Object[] { localTimestamp, Integer.valueOf(UserManager.getInstance().getCurrentUser().getUser_ID()), Long.valueOf(paramProductGroup.getPG_ID()) });
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