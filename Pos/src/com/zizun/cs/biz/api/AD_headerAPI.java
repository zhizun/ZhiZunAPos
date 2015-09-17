package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

public class AD_headerAPI
  extends BaseAPI
{
  public static void CreateADheader(RequestCallBack<String> paramRequestCallBack, String paramString)
  {
    LogUtils.i("http://pos.com:8090/api/auxiliary/getappad?AH_Code=IMGAD");
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.yunmendian.com:8090/api/auxiliary/getappad?AH_Code=IMGAD&AH_VersionNumber=" + paramString, paramRequestCallBack);
  }
  
  public static void CreateADheaderBanner(RequestCallBack<String> paramRequestCallBack, String paramString)
  {
    LogUtils.i("http://pos.com:8090/api/auxiliary/getappad?AH_Code=BTNAD");
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.yunmendian.com:8090/api/auxiliary/getappad?AH_Code=BTNAD&AH_VersionNumber=" + paramString, paramRequestCallBack);
  }
  
  public static void CreateShareInfo(RequestCallBack<String> paramRequestCallBack)
  {
    LogUtils.i("http://pos.com:8090/api/auxiliary/getappad?AH_Code=APPShare");
    httpSend(HttpRequest.HttpMethod.GET, "http://pos.yunmendian.com:8090/api/auxiliary/getappad?AH_Code=APPShare", paramRequestCallBack);
  }
}