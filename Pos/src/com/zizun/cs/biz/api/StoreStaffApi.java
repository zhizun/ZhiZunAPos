package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.api.StoreStaffParameterBatchBindParam;
import com.zizun.cs.entities.api.StoreStaffParameterBindParam;

public class StoreStaffApi
  extends BaseAPI
{
  public static void batchBindStoreStaffParameter(StoreStaffParameterBatchBindParam paramStoreStaffParameterBatchBindParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramStoreStaffParameterBatchBindParam = APIParamsUtil.getHttpParams(paramStoreStaffParameterBatchBindParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/store/batchbindstorestaffparameter", paramStoreStaffParameterBatchBindParam, paramRequestCallBack);
  }
  
  public static void bindStoreStaffParameter(StoreStaffParameterBindParam paramStoreStaffParameterBindParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramStoreStaffParameterBindParam = APIParamsUtil.getHttpParams(paramStoreStaffParameterBindParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/store/bindstorestaffparameter", paramStoreStaffParameterBindParam, paramRequestCallBack);
  }
  
  public static void getStoreStaffParameter(int paramInt, String paramString, RequestCallBack<String> paramRequestCallBack)
  {
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.com:8090/api/store/getstorestaffparameter?Merchant_ID=" + paramInt + "&Module_Code=" + paramString, paramRequestCallBack);
  }
}