package com.zizun.cs.biz.dao.impl;

import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.StoreStaffParameterDao;
import com.zizun.cs.entities.biz.V_Parameter;
import com.zizun.cs.orm.SQLiteUtil;
import java.util.List;

public class StoreStaffParameterDaoImpl
  extends BaseDaoImpl
  implements StoreStaffParameterDao
{
  private static final String GET_ALL_PARAMETER = " SELECT * FROM V_Parameter";
  
  public List<V_Parameter> getAllParameter()
  {
    LogUtils.i(" SELECT * FROM V_Parameter");
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), " SELECT * FROM V_Parameter", V_Parameter.class);
      return localList;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
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
}