package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.S_UserDao;
import com.zizun.cs.common.utils.MD5Util;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.util.Arrays;
import java.util.List;

public class S_UserDaoImpl
  extends BaseDaoImpl
  implements S_UserDao
{
  public S_UserDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public boolean delete(S_User paramS_User)
  {
    return false;
  }
  
  public boolean insert(S_User paramS_User)
  {
    return false;
  }
  
  public S_User localLogin(LocalLoginParam paramLocalLoginParam)
  {
    Object localObject2 = MD5Util.GetMD5Code(paramLocalLoginParam.userPswd);
    Object localObject1 = null;
    paramLocalLoginParam = "select * from S_User where User_Name = '" + paramLocalLoginParam.userName + "' and User_Password = '" + (String)localObject2 + "'";
    try
    {
      localObject2 = SQLiteUtil.getQueryList(getDatabase(), paramLocalLoginParam, S_User.class);
      paramLocalLoginParam = (LocalLoginParam)localObject1;
      if (localObject2 != null)
      {
        paramLocalLoginParam = (LocalLoginParam)localObject1;
        if (((List)localObject2).size() != 0) {
          paramLocalLoginParam = (S_User)((List)localObject2).get(0);
        }
      }
      return paramLocalLoginParam;
    }
    catch (IllegalAccessException paramLocalLoginParam)
    {
      paramLocalLoginParam.printStackTrace();
      return null;
    }
    catch (InstantiationException paramLocalLoginParam)
    {
      paramLocalLoginParam.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException paramLocalLoginParam)
    {
      paramLocalLoginParam.printStackTrace();
    }
    return null;
  }
  
  public boolean update(S_User paramS_User)
  {
    Object localObject = new SqlUtil(SqlUtil.SqlType.UPDATE, paramS_User);
    paramS_User = ((SqlUtil)localObject).getSqlBuffer().toString();
    localObject = ((SqlUtil)localObject).getParam().toArray();
    LogUtils.i("sql = " + paramS_User);
    LogUtils.i("bindArgs = " + Arrays.asList((Object[])localObject).toString());
    try
    {
      getDatabase().execSQL(paramS_User, (Object[])localObject);
      return true;
    }
    catch (SQLException paramS_User)
    {
      paramS_User.printStackTrace();
    }
    return false;
  }
}