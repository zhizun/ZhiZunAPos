package com.zizun.cs.biz.api.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.common.utils.MD5Util;
import java.io.UnsupportedEncodingException;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;

public class APIParamsUtil
{
  private static final String key = "10003";
  private static final String keySercret = "50dX9mUk3DC6En47w11ExS28v4UF3ILB";
  
  private static String getAuthorization(HttpRequest.HttpMethod paramHttpMethod, String paramString1, String paramString2)
  {
    paramHttpMethod = getContentString(paramHttpMethod, paramString1, paramString2);
    LogUtils.i(paramHttpMethod);
    paramHttpMethod = MD5Util.GetMD5Code(paramHttpMethod);
    return Base64.encodeToString(("10003:" + paramHttpMethod).getBytes(), 0).replace("\n", "");
  }
  
  private static String getContentString(HttpRequest.HttpMethod paramHttpMethod, String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramHttpMethod.toString());
    LogUtils.i(paramHttpMethod.toString());
    localStringBuilder.append("\n");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("\n");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("\n");
    localStringBuilder.append("10003");
    localStringBuilder.append("\n");
    localStringBuilder.append("50dX9mUk3DC6En47w11ExS28v4UF3ILB");
    return localStringBuilder.toString();
  }
  
  public static RequestParams getHttpParams(Object paramObject)
  {
    RequestParams localRequestParams = new RequestParams();
    if (paramObject == null) {
      return null;
    }
    localRequestParams.setContentType("application/json");
    paramObject = JsonUtil.toJson(paramObject);
    try
    {
      localRequestParams.setBodyEntity(new StringEntity((String)paramObject, "UTF-8"));
      LogUtils.i((String)paramObject);
      return localRequestParams;
    }
    catch (UnsupportedEncodingException paramObject)
    {
      ((UnsupportedEncodingException)paramObject).printStackTrace();
      return null;
    }
    catch (ParseException paramObject)
    {
      ((ParseException)paramObject).printStackTrace();
    }
    return null;
  }
  
  public static <T> T getHttpResultFromJson(String paramString, Class<T> paramClass)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return (T)JsonUtil.fromJson(paramString, paramClass);
  }
  
  public static void setHttpParamsAES(RequestParams paramRequestParams, HttpRequest.HttpMethod paramHttpMethod, String paramString)
  {
    String str = DateTimeUtil.getRequestTime();
    paramRequestParams.addHeader("Authorization", getAuthorization(paramHttpMethod, paramString, str));
    paramRequestParams.addHeader("RequestTime", str);
    LogUtils.i(JsonUtil.toJson(paramRequestParams));
  }
}