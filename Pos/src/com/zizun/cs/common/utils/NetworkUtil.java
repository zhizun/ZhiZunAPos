package com.zizun.cs.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class NetworkUtil
{
  public static boolean checkNetworkConnection(Context paramContext)
  {
    boolean bool = false;
    Object localObject = (ConnectivityManager)paramContext.getSystemService("connectivity");
    paramContext = ((ConnectivityManager)localObject).getNetworkInfo(1);
    localObject = ((ConnectivityManager)localObject).getNetworkInfo(0);
    if ((paramContext.isAvailable()) || (((NetworkInfo)localObject).isAvailable())) {
      bool = true;
    }
    return bool;
  }
  
  public static String doPost(List<NameValuePair> paramList, String paramString)
  {
    String str = "";
    Object localObject1 = str;
    Object localObject2 = str;
    Object localObject3 = str;
    localObject4 = str;
    try
    {
      Object localObject5 = new HttpPost(paramString);
      if (paramList != null)
      {
        localObject1 = str;
        localObject2 = str;
        localObject3 = str;
        localObject4 = str;
        ((HttpPost)localObject5).setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
      }
      localObject1 = str;
      localObject2 = str;
      localObject3 = str;
      localObject4 = str;
      paramList = new DefaultHttpClient();
      localObject1 = str;
      localObject2 = str;
      localObject3 = str;
      localObject4 = str;
      paramList.getParams().setParameter("http.connection.timeout", Integer.valueOf(6000));
      localObject1 = str;
      localObject2 = str;
      localObject3 = str;
      localObject4 = str;
      paramList.getParams().setParameter("http.socket.timeout", Integer.valueOf(6000));
      paramString = str;
      localObject1 = str;
      localObject2 = str;
      localObject3 = str;
      localObject4 = str;
      try
      {
        localObject5 = paramList.execute((HttpUriRequest)localObject5);
        paramString = str;
        localObject1 = str;
        localObject2 = str;
        localObject3 = str;
        localObject4 = str;
        Log.i("mid2222", ((HttpResponse)localObject5).getStatusLine().getStatusCode());
        paramList = str;
        paramString = str;
        localObject1 = str;
        localObject2 = str;
        localObject3 = str;
        localObject4 = str;
        if (((HttpResponse)localObject5).getStatusLine().getStatusCode() == 200)
        {
          paramString = str;
          localObject1 = str;
          localObject2 = str;
          localObject3 = str;
          localObject4 = str;
          paramList = EntityUtils.toString(((HttpResponse)localObject5).getEntity(), "UTF-8");
          paramString = paramList;
          localObject1 = paramList;
          localObject2 = paramList;
          localObject3 = paramList;
          localObject4 = paramList;
          Log.i("mid2222", "result: " + paramList);
        }
        return paramList;
      }
      catch (ConnectTimeoutException paramList)
      {
        return paramString;
      }
      return (String)localObject4;
    }
    catch (UnsupportedEncodingException paramList)
    {
      return (String)localObject1;
    }
    catch (ClientProtocolException paramList)
    {
      return (String)localObject2;
    }
    catch (ParseException paramList)
    {
      return (String)localObject3;
    }
    catch (IOException paramList) {}
  }
  
  public static boolean hasNetwork(Context paramContext)
  {
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (paramContext != null) && (paramContext.isConnectedOrConnecting());
  }
  
  public static boolean isWifiConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
      if (paramContext != null) {
        return paramContext.isAvailable();
      }
    }
    return false;
  }
}