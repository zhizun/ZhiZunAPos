package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.sync.SyncDownLoadParam;
import com.zizun.cs.entities.sync.SyncDownLoadResult;

public class SyncBaseDataAPI
  extends BaseAPI
{
  public static void doSyncBaseDataDownLoad(SyncDownLoadParam paramSyncDownLoadParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramSyncDownLoadParam = APIParamsUtil.getHttpParams(paramSyncDownLoadParam);
    if (paramSyncDownLoadParam != null) {
      httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/sync/syncbasedown", paramSyncDownLoadParam, paramRequestCallBack);
    }
  }
  
  public static SyncDownLoadResult getSyncBaseDataDownResultFromJson(String paramString)
  {
    return (SyncDownLoadResult)APIParamsUtil.getHttpResultFromJson(paramString, SyncDownLoadResult.class);
  }
}