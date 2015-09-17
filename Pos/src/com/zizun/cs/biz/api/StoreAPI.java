package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.api.StoreCreateParam;
import com.zizun.cs.entities.api.StoreDeleteParam;
import com.zizun.cs.entities.api.StoreUpdateParam;

public class StoreAPI
  extends BaseAPI
{
  public static void createStoreFromAPI(StoreCreateParam paramStoreCreateParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramStoreCreateParam = APIParamsUtil.getHttpParams(paramStoreCreateParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/store/create", paramStoreCreateParam, paramRequestCallBack);
  }
  
  public static void deleteStoreFromAPI(StoreDeleteParam paramStoreDeleteParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramStoreDeleteParam = APIParamsUtil.getHttpParams(paramStoreDeleteParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/store/delete", paramStoreDeleteParam, paramRequestCallBack);
  }
  
  public static void getStoreFromAPI(int paramInt, RequestCallBack<String> paramRequestCallBack)
  {
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.com:8090/api/store/getstore?Merchant_ID=" + paramInt, paramRequestCallBack);
  }
  
  public static void updateStoreFromAPI(StoreUpdateParam paramStoreUpdateParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramStoreUpdateParam = APIParamsUtil.getHttpParams(paramStoreUpdateParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/store/update", paramStoreUpdateParam, paramRequestCallBack);
  }
}