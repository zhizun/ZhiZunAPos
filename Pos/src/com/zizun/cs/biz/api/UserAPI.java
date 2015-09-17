package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.entities.Reg_AppActive;
import com.zizun.cs.entities.api.LoginParam;
import com.zizun.cs.entities.api.PswdChangeParam;
import com.zizun.cs.entities.api.RegisterParam;
import com.zizun.cs.entities.api.UserNameCheckParam;

public class UserAPI
  extends BaseAPI
{
  public static void CheckUserName(UserNameCheckParam paramUserNameCheckParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramUserNameCheckParam = APIParamsUtil.getHttpParams(paramUserNameCheckParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.yunmendian.com:8090/api/user/isexist", paramUserNameCheckParam, paramRequestCallBack);
  }
  
  public static void activeApp(Reg_AppActive paramReg_AppActive, RequestCallBack<String> paramRequestCallBack)
  {
    paramReg_AppActive = APIParamsUtil.getHttpParams(paramReg_AppActive);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.yunmendian.com:8090/api/auxiliary/appactive", paramReg_AppActive, paramRequestCallBack);
  }
  
  public static void changePswd(PswdChangeParam paramPswdChangeParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramPswdChangeParam = APIParamsUtil.getHttpParams(paramPswdChangeParam);
    if (paramPswdChangeParam != null) {
      httpSend(HttpRequest.HttpMethod.POST, "http://pos.yunmendian.com:8090/api/user/updatepwd", paramPswdChangeParam, paramRequestCallBack);
    }
  }
  
  public static void login(LoginParam paramLoginParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramLoginParam = APIParamsUtil.getHttpParams(paramLoginParam);
    LogUtils.i(JsonUtil.toJson(paramLoginParam));
    if (paramLoginParam != null) {
      httpSend(HttpRequest.HttpMethod.POST, "http://pos.yunmendian.com:8090/api/user/login", paramLoginParam, paramRequestCallBack);
    }
  }
  
  public static void register(RegisterParam paramRegisterParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramRegisterParam = APIParamsUtil.getHttpParams(paramRegisterParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.yunmendian.com:8090/api/user/registerv1", paramRegisterParam, paramRequestCallBack);
  }
}