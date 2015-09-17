package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.S_Module;
import com.zizun.cs.ui.entity.Module;
import java.util.ArrayList;
import java.util.List;

public abstract interface S_ModuleDao
{
  public static final String SELECT_ALL_MODULE = "select * from S_Module";
  public static final String SELECT_MODULES_BY_USERID = "SELECT sm.*  FROM StaffRole AS sr  INNER JOIN S_Role AS sr2 ON sr2.Role_ID = sr.Role_ID INNER JOIN S_RoleModule AS srm ON srm.Role_ID = sr2.Role_ID INNER JOIN S_Module AS sm ON sm.Module_ID = srm.Module_ID WHERE sr.Staff_ID = <用户ID> AND sr.StaffRole_Status = 1 AND sr2.Role_Status = 1 AND srm.RoleModule_Status =1 AND sm.Module_Status = 1 AND sm.Terminal_Type = 2";
  
  public abstract ArrayList<S_Module> getAllModule()
    throws IllegalAccessException, InstantiationException, ClassNotFoundException;
  
  public abstract List<S_Module> getAllModuleByUserID(int paramInt);
  
  public abstract ArrayList<Module> getAvailableModule();
}