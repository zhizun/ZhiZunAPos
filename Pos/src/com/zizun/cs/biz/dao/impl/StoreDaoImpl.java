package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.StoreDao;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.entities.Store;
import com.zizun.cs.orm.SQLiteUtil;
import java.util.List;

public class StoreDaoImpl
  extends BaseDaoImpl
  implements StoreDao
{
  public static final String SELECT_STORE = "select * FROM Store WHERE Store_ID = <Store_ID>";
  
  public StoreDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public boolean delete(Store paramStore)
  {
    return false;
  }
  
  public List<Store> getAllStores(int paramInt)
  {
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject1 = null;
    try
    {
      List localList = SQLiteUtil.getQueryList(getDatabase(), "SELECT s.*  FROM StoreStaff AS ss INNER JOIN store AS s ON s.Merchant_ID = ss.Merchant_ID AND  s.Store_ID = ss.Store_ID INNER JOIN staff AS s1 ON s1.Merchant_ID = ss.Merchant_ID AND s1.Staff_ID = ss.Staff_ID\t WHERE ss.Staff_ID = <用户ID> AND ss.StoreStaff_Status = 1 AND s1.Staff_Status = 1 AND s.Store_Status =1\t".replace("<用户ID>", paramInt), Store.class);
      localObject1 = localList;
      localObject2 = localList;
      localObject3 = localList;
      localObject4 = localList;
      LogUtils.i(JsonUtil.toJson(localList));
      return localList;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return (List<Store>)localObject1;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return (List<Store>)localObject2;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return (List<Store>)localObject3;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return (List<Store>)localObject4;
  }
  
  public Store getStoreByStore_ID(long paramLong)
  {
    Object localObject1 = null;
    Object localObject2 = "select * FROM Store WHERE Store_ID = <Store_ID>".replace("<Store_ID>", paramLong);
    LogUtils.i((String)localObject2);
    try
    {
      localObject2 = SQLiteUtil.getQueryList(getDatabase(), (String)localObject2, Store.class);
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
    if (localObject1 != null) {
      return (Store)((List)localObject1).get(0);
    }
    return null;
  }
  
  public boolean insert(Store paramStore)
  {
    return false;
  }
  
  public boolean update(Store paramStore)
  {
    return false;
  }
}