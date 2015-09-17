package com.zizun.cs.activity.manager;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.WXReceiptAPI;
import com.zizun.cs.biz.dao.VIPDao;
import com.zizun.cs.biz.dao.impl.VIPDaoImpl;
import com.zizun.cs.entities.VIP;
import com.zizun.cs.entities.api.WXReceiptParam;
import com.zizun.cs.entities.api.WXReceiptResult;

public class ReceiptManager
{
  public static WXReceiptResult getResultFromJson(String paramString)
  {
    return (WXReceiptResult)WXReceiptAPI.getAPIResultFromJson(paramString, WXReceiptResult.class);
  }
  
  public static VIP getVIPByVIPIDOutUIThread(long paramLong)
  {
    return new VIPDaoImpl().getVIPByID(paramLong);
  }
  
  public static void sendWXReceipt(WXReceiptParam paramWXReceiptParam, RequestCallBack<String> paramRequestCallBack)
  {
    WXReceiptAPI.sendWXReceipt(paramWXReceiptParam, paramRequestCallBack);
  }
  
  public static boolean updateVIPOutUIThread(VIP paramVIP)
  {
    return new VIPDaoImpl().update(paramVIP);
  }
}