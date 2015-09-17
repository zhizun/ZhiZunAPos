package com.zizun.cs.activity.manager;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.DBVersionAPI;
import com.zizun.cs.entities.api.DBVersionGetSqlResult;

public class DBVersionManager
{
  public static DBVersionGetSqlResult getDBVersionGetSqlResultFromJsonString(String paramString)
  {
    return (DBVersionGetSqlResult)DBVersionAPI.getAPIResultFromJson(paramString, DBVersionGetSqlResult.class);
  }
  
  public static void getDBVersionSqlFromAPI(int paramInt, RequestCallBack<String> paramRequestCallBack)
  {
    DBVersionAPI.getDBVersionSqlFromAPI(paramInt, paramRequestCallBack);
  }
}