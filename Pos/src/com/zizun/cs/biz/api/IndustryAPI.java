package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;

public class IndustryAPI
  extends BaseAPI
{
  public static void getIndustryFromAPI(RequestCallBack<String> paramRequestCallBack)
  {
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.yunmendian.com:8090/api/user/getindustry", paramRequestCallBack);
  }
}