package com.zhizun.pos.util;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.define.Module_Code;
import com.zizun.cs.ui.entity.Module;
import java.util.ArrayList;

public class PermissionManager
{
  public static ArrayList<Module> getFunctionModule()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = UserManager.getInstance().getListModule();
    int i = 0;
    if (i >= localArrayList2.size()) {
      return localArrayList1;
    }
    Module localModule = (Module)localArrayList2.get(i);
    switch (localModule.getModule_Code())
    {
    }
    for (;;)
    {
      i += 1;
      break;
      localArrayList1.add(localModule);
    }
  }
  
  public static int getFunctionModuleIconId(Module_Code paramModule_Code)
  {
    return ResUtil.getDrawableId(String.format("ic_function_%d", new Object[] { Integer.valueOf(getFunctionModuleResPosition(paramModule_Code)) }));
  }
  
  private static int getFunctionModuleResPosition(Module_Code paramModule_Code)
  {
    switch (paramModule_Code)
    {
    case Vip: 
    default: 
      return -1;
    case Staff: 
      return 0;
    case Vendor: 
      return 1;
    case Stock: 
      return 2;
    case StockIn: 
      return 3;
    case StockOut: 
      return 4;
    case Store: 
      return 5;
    case Transfer: 
      return 6;
    }
    return 7;
  }
  
  public static int getHomeModuleIconId(Module_Code paramModule_Code)
  {
    return ResUtil.getDrawableId(String.format("ic_goods_%d", new Object[] { Integer.valueOf(getHomeModuleResPosition(paramModule_Code)) }));
  }
  
  private static int getHomeModuleResPosition(Module_Code paramModule_Code)
  {
    switch (paramModule_Code)
    {
    default: 
      return -1;
    case Invent: 
      return 0;
    case Balance: 
      return 1;
    case ProductGroup: 
      return 2;
    case Purchase: 
      return 3;
    case Report: 
      return 4;
    case Return: 
      return 5;
    case Role: 
      return 6;
    }
    return 7;
  }
  
  public static ArrayList<Module> getHomePageModule()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = UserManager.getInstance().getListModule();
    int i = 0;
    if (i >= localArrayList2.size()) {
      return localArrayList1;
    }
    Module localModule = (Module)localArrayList2.get(i);
    switch (localModule.getModule_Code())
    {
    }
    for (;;)
    {
      i += 1;
      break;
      localArrayList1.add(localModule);
    }
  }
}