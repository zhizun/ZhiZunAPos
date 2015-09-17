package com.zizun.cs.biz.api;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpMethod;
import com.zizun.cs.biz.api.utils.APIParamsUtil;
import com.zizun.cs.entities.api.ImgParam;
import com.zizun.cs.entities.api.ImgProductParam;

public class ImgAPI
  extends BaseAPI
{
  public static void uploadAlipayQrImg(ImgParam paramImgParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramImgParam = APIParamsUtil.getHttpParams(paramImgParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/merchant/merchantalipayqr", paramImgParam, paramRequestCallBack);
  }
  
  public static void uploadMerchantImg(ImgParam paramImgParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramImgParam = APIParamsUtil.getHttpParams(paramImgParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/merchant/merchantimg", paramImgParam, paramRequestCallBack);
  }
  
  public static void uploadProductImg(ImgProductParam paramImgProductParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramImgProductParam = APIParamsUtil.getHttpParams(paramImgProductParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/product/productimg", paramImgProductParam, paramRequestCallBack);
  }
  
  public static void uploadWxQrImg(ImgParam paramImgParam, RequestCallBack<String> paramRequestCallBack)
  {
    paramImgParam = APIParamsUtil.getHttpParams(paramImgParam);
    httpSend(HttpRequest.HttpMethod.POST, "http://pos.com:8090/api/merchant/merchantwxqr", paramImgParam, paramRequestCallBack);
  }
}