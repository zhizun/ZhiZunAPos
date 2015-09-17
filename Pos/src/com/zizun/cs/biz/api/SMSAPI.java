package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.api.SMSFindPswdParam;
import com.zizun.cs.entities.api.SMSRegisterParam;

public class SMSAPI
  extends BaseAPI
{
  public static void sendFindPswdSMS(SMSFindPswdParam paramSMSFindPswdParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramSMSFindPswdParam = APIParamsUtil.getHttpParams(paramSMSFindPswdParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/user/findpwdsms", paramSMSFindPswdParam, paramRequestCallBack);
  }
  
  public static void sendRegisterSMS(SMSRegisterParam paramSMSRegisterParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramSMSRegisterParam = APIParamsUtil.getHttpParams(paramSMSRegisterParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/user/regsms", paramSMSRegisterParam, paramRequestCallBack);
  }
}