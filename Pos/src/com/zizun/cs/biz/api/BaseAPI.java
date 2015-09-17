package com.zizun.cs.biz.api;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;

public class BaseAPI
{
  public static <T> T getAPIResultFromJson(String paramString, Class<T> paramClass)
  {
    return (T)APIParamsUtil.getHttpResultFromJson(paramString, paramClass);
  }
  
  public static void httpSend(HttpRequest.HttpMethod paramHttpMethod, String paramString, RequestParams paramRequestParams, RequestCallBack<String> paramRequestCallBack)
  {
    HttpUtils localHttpUtils = new HttpUtils();
    APIParamsUtil.setHttpParamsAES(paramRequestParams, paramHttpMethod, paramString);
    localHttpUtils.send(paramHttpMethod, paramString, paramRequestParams, paramRequestCallBack);
  }
  
  public static void httpSend(HttpRequest.HttpMethod paramHttpMethod, String paramString, RequestCallBack<String> paramRequestCallBack)
  {
    HttpUtils.sHttpCache.clear();
    HttpUtils localHttpUtils = new HttpUtils();
    RequestParams localRequestParams = new RequestParams("utf-8");
    APIParamsUtil.setHttpParamsAES(localRequestParams, paramHttpMethod, paramString);
    localHttpUtils.send(paramHttpMethod, paramString, localRequestParams, paramRequestCallBack);
  }
}