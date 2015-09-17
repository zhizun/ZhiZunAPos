package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.S_MerchantDao;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.List;

public class S_MerchantDaoImpl
  extends BaseDaoImpl
  implements S_MerchantDao
{
  public static final String SELECT_S_MERCHANT = "select * FROM S_Merchant WHERE Merchant_ID = <Merchant_ID>";
  
  public boolean delete(S_Merchant paramS_Merchant)
  {
    return false;
  }
  
  public S_Merchant getMerchant()
  {
    int i = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    Object localObject1 = null;
    Object localObject2 = "select * FROM S_Merchant WHERE Merchant_ID = <Merchant_ID>".replace("<Merchant_ID>", i);
    LogUtils.i((String)localObject2);
    try
    {
      localObject2 = SQLiteUtil.getQueryList(getDatabase(), (String)localObject2, S_Merchant.class);
      localObject1 = localObject2;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        localIllegalAccessException.printStackTrace();
      }
    }
    catch (InstantiationException localInstantiationException)
    {
      for (;;)
      {
        localInstantiationException.printStackTrace();
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      for (;;)
      {
        localClassNotFoundException.printStackTrace();
      }
    }
    return (S_Merchant)((List)localObject1).get(0);
  }
  
  public boolean insert(S_Merchant paramS_Merchant)
  {
    return false;
  }
  
  public boolean update(Context paramContext, S_Merchant paramS_Merchant)
  {
    paramContext = MyDbUtils.getDbUtils(paramContext, UserManager.getInstance().getCurrentUser().getMerchant_ID()).getDatabase();
    paramContext.beginTransaction();
    try
    {
      Object localObject = new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, paramS_Merchant);
      paramS_Merchant = ((SqlUtil)localObject).getUpdateForLocalBeforeSql();
      String str = ((SqlUtil)localObject).getSqlBuffer();
      localObject = ((SqlUtil)localObject).getParam().toArray();
      paramContext.execSQL(paramS_Merchant);
      paramContext.execSQL(str, (Object[])localObject);
      paramS_Merchant = new S_Sync_Upload(S_Merchant.class.getSimpleName(), (byte)1);
      new S_Sync_UploadDaoImpl().insert(paramS_Merchant);
      paramContext.setTransactionSuccessful();
    }
    catch (SQLException paramS_Merchant)
    {
      for (;;)
      {
        paramS_Merchant.printStackTrace();
        paramContext.endTransaction();
      }
    }
    finally
    {
      paramContext.endTransaction();
    }
    return false;
  }
  
  public boolean update(S_Merchant paramS_Merchant)
  {
    return false;
  }
}
