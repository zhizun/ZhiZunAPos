package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.api.WXReceiptParam;
import com.zizun.cs.entities.api.WXReceiptResult;

public class WXReceiptAPI
  extends BaseAPI
{
  public static WXReceiptResult getResultFromJson(String paramString)
  {
    return (WXReceiptResult)BaseAPI.getAPIResultFromJson(paramString, WXReceiptResult.class);
  }
  
  public static void sendWXReceipt(WXReceiptParam paramWXReceiptParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramWXReceiptParam = APIParamsUtil.getHttpParams(paramWXReceiptParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://wx.yunmendian.com/wxapi/app/sendmsg", paramWXReceiptParam, paramRequestCallBack);
  }
}