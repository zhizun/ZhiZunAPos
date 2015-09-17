package com.zizun.cs.activity.manager;

import android.text.TextUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.StoreStaffApi;
import com.zizun.cs.biz.dao.StoreStaffParameterDao;
import com.zizun.cs.biz.dao.impl.StoreStaffParameterDaoImpl;
import com.zizun.cs.entities.api.StoreStaffParameterBatchBindParam;
import com.zizun.cs.entities.api.StoreStaffParameterBatchBindResult;
import com.zizun.cs.entities.api.StoreStaffParameterBindParam;
import com.zizun.cs.entities.api.StoreStaffParameterBindResult;
import com.zizun.cs.entities.api.ViewStoreStaffParameterGetListResult;
import com.zizun.cs.entities.biz.V_Parameter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StoreStaffManager
{
  public static boolean IsAutoSync()
  {
    String str = (String)UserManager.getInstance().getCurrentParameterMap().get("SyncBackground");
    if (TextUtils.isEmpty(str)) {}
    while (!str.equals("Y")) {
      return false;
    }
    return true;
  }
  
  public static void batchBindStoreStaffParameter(StoreStaffParameterBatchBindParam paramStoreStaffParameterBatchBindParam, RequestCallBack<String> paramRequestCallBack)
  {
    StoreStaffApi.batchBindStoreStaffParameter(paramStoreStaffParameterBatchBindParam, paramRequestCallBack);
  }
  
  public static void bindStoreStaffParameter(StoreStaffParameterBindParam paramStoreStaffParameterBindParam, RequestCallBack<String> paramRequestCallBack)
  {
    StoreStaffApi.bindStoreStaffParameter(paramStoreStaffParameterBindParam, paramRequestCallBack);
  }
  
  public static List<V_Parameter> getAllStoreStaffParamerter()
  {
    return new StoreStaffParameterDaoImpl().getAllParameter();
  }
  
  private static String getParameterValueByCode(String paramString)
  {
    String str = null;
    Iterator localIterator = UserManager.getInstance().getCurrentStoreStaffParamerterList().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return str;
      }
      V_Parameter localV_Parameter = (V_Parameter)localIterator.next();
      if (localV_Parameter.getParameter_Code().equals(paramString)) {
        str = localV_Parameter.getParameter_Value();
      }
    }
  }
  
  public static StoreStaffParameterBatchBindResult getStoreStaffParameterBatchBindResult(String paramString)
  {
    return (StoreStaffParameterBatchBindResult)StoreStaffApi.getAPIResultFromJson(paramString, StoreStaffParameterBatchBindResult.class);
  }
  
  public static StoreStaffParameterBindResult getStoreStaffParameterBindResult(String paramString)
  {
    return (StoreStaffParameterBindResult)StoreStaffApi.getAPIResultFromJson(paramString, StoreStaffParameterBindResult.class);
  }
  
  public static void getStoreStaffParameterList(int paramInt, String paramString, RequestCallBack<String> paramRequestCallBack)
  {
    StoreStaffApi.getStoreStaffParameter(paramInt, paramString, paramRequestCallBack);
  }
  
  public static ViewStoreStaffParameterGetListResult getStoreStaffParameterListResult(String paramString)
  {
    return (ViewStoreStaffParameterGetListResult)StoreStaffApi.getAPIResultFromJson(paramString, ViewStoreStaffParameterGetListResult.class);
  }
}