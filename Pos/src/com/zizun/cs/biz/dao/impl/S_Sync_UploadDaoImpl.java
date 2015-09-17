package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.S_Sync_UploadDao;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import com.yunmendian.pos.app.StoreApplication;
import java.util.List;

public class S_Sync_UploadDaoImpl
  extends BaseDaoImpl
  implements S_Sync_UploadDao
{
  public static final String SELECT_S_SYNC_UPLOAD = "select * FROM S_Sync_Upload";
  
  public S_Sync_UploadDaoImpl()
  {
    super(StoreApplication.getContext(), UserManager.getInstance().getCurrentUser().getMerchant_ID());
  }
  
  public S_Sync_UploadDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public List<S_Sync_Upload> getS_Sync_UploadList()
  {
    LogUtils.i("select * FROM S_Sync_Upload");
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), "select * FROM S_Sync_Upload", S_Sync_Upload.class);
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
  
  public boolean insert(S_Sync_Upload paramS_Sync_Upload)
  {
    paramS_Sync_Upload = new SqlUtil(SqlUtil.SqlType.INSERT, paramS_Sync_Upload);
    StringBuffer localStringBuffer = SQLiteUtil.initSqlBufferForExist("S_Sync_Upload", paramS_Sync_Upload.getIdNames(), paramS_Sync_Upload.getIdValus());
    if (!SQLiteUtil.isTargetExist(getDatabase(), localStringBuffer.toString())) {
      getDatabase().execSQL(paramS_Sync_Upload.getSqlBuffer().toString(), paramS_Sync_Upload.getParam().toArray());
    }
    return true;
  }
}