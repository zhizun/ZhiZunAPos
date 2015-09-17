package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.S_LoginDao;
import com.zizun.cs.entities.S_Login;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.Arrays;
import java.util.List;

public class S_LoginDaoImpl
  extends BaseDaoImpl
  implements S_LoginDao
{
  public S_LoginDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public boolean insert(S_Login paramS_Login)
  {
    Object localObject = new SqlUtil(SqlUtil.SqlType.INSERT, paramS_Login);
    paramS_Login = ((SqlUtil)localObject).getSqlBuffer().toString();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    LogUtils.i("sql = " + paramS_Login);
    LogUtils.i("bindArgs = " + Arrays.asList((Object[])localObject).toString());
    try
    {
      getDatabase().execSQL(paramS_Login, (Object[])localObject);
      return true;
    }
    catch (SQLException paramS_Login)
    {
      paramS_Login.printStackTrace();
    }
    return false;
  }
}