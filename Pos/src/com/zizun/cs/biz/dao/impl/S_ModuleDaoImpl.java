package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.S_ModuleDao;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.define.Module_Code;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.orm.SQLiteUtil;
import com.zizun.cs.ui.entity.Module;
import java.util.ArrayList;
import java.util.List;

public class S_ModuleDaoImpl
  extends BaseDaoImpl
  implements S_ModuleDao
{
  private static S_ModuleDaoImpl instance;
  private String SQL_SELECT_AVAILABLE_MODULE = "SELECT sm.Module_Code,sm.Module_Name,sm.Module_Order FROM StaffRole AS sr  INNER JOIN S_Role AS sr2 ON sr2.Role_ID = sr.Role_ID INNER JOIN S_RoleModule AS srm ON srm.Role_ID = sr2.Role_ID INNER JOIN S_Module AS sm ON sm.Module_ID = srm.Module_ID WHERE sr.Staff_ID = ? AND sr.StaffRole_Status = 1 AND sr2.Role_Status = 1 AND srm.RoleModule_Status =1 AND sm.Module_Status = 1 AND sm.Terminal_Type = 2 Order by sm.Module_Order";
  
  private S_ModuleDaoImpl() {}
  
  public S_ModuleDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public static void clear()
  {
    if (instance != null) {
      instance = null;
    }
  }
  
  public static S_ModuleDaoImpl getInstance()
  {
    if (instance == null) {
      instance = new S_ModuleDaoImpl();
    }
    return instance;
  }
  
  public ArrayList<S_Module> getAllModule()
    throws IllegalAccessException, InstantiationException, ClassNotFoundException
  {
    return (ArrayList)SQLiteUtil.getQueryList(getDatabase(), "select * from S_Module", S_Module.class);
  }
  
  public List<S_Module> getAllModuleByUserID(int paramInt)
  {
    Object localObject1 = new ArrayList();
    Object localObject2 = localObject1;
    Object localObject3 = localObject1;
    Object localObject4 = localObject1;
    Object localObject5 = localObject1;
    try
    {
      localObject1 = SQLiteUtil.getQueryList(getDatabase(), "SELECT sm.*  FROM StaffRole AS sr  INNER JOIN S_Role AS sr2 ON sr2.Role_ID = sr.Role_ID INNER JOIN S_RoleModule AS srm ON srm.Role_ID = sr2.Role_ID INNER JOIN S_Module AS sm ON sm.Module_ID = srm.Module_ID WHERE sr.Staff_ID = <用户ID> AND sr.StaffRole_Status = 1 AND sr2.Role_Status = 1 AND srm.RoleModule_Status =1 AND sm.Module_Status = 1 AND sm.Terminal_Type = 2".replace("<用户ID>", paramInt), S_Module.class);
      localObject2 = localObject1;
      localObject3 = localObject1;
      localObject4 = localObject1;
      localObject5 = localObject1;
      LogUtils.i(JsonUtil.toJson(localObject1));
      return (List<S_Module>)localObject1;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return (List<S_Module>)localObject2;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return (List<S_Module>)localObject3;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return (List<S_Module>)localObject4;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return (List<S_Module>)localObject5;
  }
  
  public ArrayList<Module> getAvailableModule()
  {
    localArrayList = new ArrayList();
    try
    {
      int i = UserManager.getInstance().getCurrentUser().getUser_ID();
      LogUtil.logD(this.SQL_SELECT_AVAILABLE_MODULE);
      Cursor localCursor = getDatabase().rawQuery(this.SQL_SELECT_AVAILABLE_MODULE, new String[] { String.valueOf(i) });
      for (;;)
      {
        if (!localCursor.moveToNext())
        {
          LogUtils.i(JsonUtil.toJson(localArrayList));
          return localArrayList;
        }
        Module localModule = new Module();
        localModule.setModule_Code(Module_Code.fromValue(localCursor.getString(localCursor.getColumnIndex("Module_Code"))));
        localModule.setModule_Name(localCursor.getString(localCursor.getColumnIndex("Module_Name")));
        localModule.setModule_Order(localCursor.getInt(localCursor.getColumnIndex("Module_Order")));
        localArrayList.add(localModule);
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}