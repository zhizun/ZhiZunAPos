package com.geetest.sdk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GtDialog
  extends Dialog
{
  private static GtDialog mDialog;
  private GtListener gtListener;
  SdkInit initData = new SdkInit();
  private WebView webView;
  
  private GtDialog(Context paramContext)
  {
    super(paramContext);
  }
  
  private GtDialog(SdkInit paramSdkInit)
  {
    super(paramSdkInit.getContext());
    this.initData = paramSdkInit;
  }
  
  public static GtDialog newInstance(SdkInit paramSdkInit)
  {
    if (mDialog == null) {
      mDialog = new GtDialog(paramSdkInit);
    }
    return mDialog;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.webView = new WebView(getContext());
    setContentView(this.webView);
    this.webView.getLayoutParams().height = (DimenTool.getHeightPx(getContext()) / 2);
    this.webView.getLayoutParams().width = -1;
    this.webView.setLayoutParams(this.webView.getLayoutParams());
    paramBundle = this.webView.getSettings();
    paramBundle.setLoadWithOverviewMode(true);
    paramBundle.setUseWideViewPort(true);
    paramBundle.setJavaScriptEnabled(true);
    this.webView.addJavascriptInterface(new Scripte(), "android");
    paramBundle = ClientInfo.build(getContext()).toJsonString();
    paramBundle = "http://static.geetest.com/static/appweb/android-index.html?gt=" + this.initData.getCaptcha_id() + "&product=embed" + "&challenge=" + this.initData.getChallenge_id() + "&mobileInfo=" + paramBundle;
    GeetestLib.log_v(paramBundle);
    this.webView.loadUrl(paramBundle);
    GeetestLib.log_v(paramBundle);
    this.webView.setWebChromeClient(new WebChromeClient()
    {
      public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
      {
        super.onProgressChanged(paramAnonymousWebView, paramAnonymousInt);
        if (paramAnonymousInt == 100)
        {
          GtDialog.this.webView.getLayoutParams().height = -1;
          GtDialog.this.webView.getLayoutParams().width = -1;
          GtDialog.this.webView.setLayoutParams(GtDialog.this.webView.getLayoutParams());
        }
      }
    });
    this.webView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
      }
    });
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    mDialog = null;
  }
  
  public void setGtListener(GtListener paramGtListener)
  {
    this.gtListener = paramGtListener;
  }
  
  public static abstract interface GtListener
  {
    public abstract void closeGt();
    
    public abstract void gtResult(boolean paramBoolean, String paramString);
  }
  
  public class Scripte
  {
    public Scripte() {}
    
    @JavascriptInterface
    public void gtCallBack(String paramString1, String paramString2, String paramString3)
    {
      GeetestLib.log_v("gtCallBack");
      GeetestLib.log_v("code:" + paramString1.toString());
      GeetestLib.log_v("result:" + paramString2.toString());
      GeetestLib.log_v("message:client result" + paramString3.toString());
      try
      {
        if (Integer.parseInt(paramString1) == 1)
        {
          GtDialog.this.dismiss();
          if (GtDialog.this.gtListener != null) {
            GtDialog.this.gtListener.gtResult(true, paramString2);
          }
        }
        else if (GtDialog.this.gtListener != null)
        {
          GtDialog.this.gtListener.gtResult(false, paramString2);
          return;
        }
      }
      catch (NumberFormatException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    
    @JavascriptInterface
    public void gtCloseWindow()
    {
      GeetestLib.log_v("gtCloseWindow");
      GtDialog.this.dismiss();
      if (GtDialog.this.gtListener != null) {
        GtDialog.this.gtListener.closeGt();
      }
    }
  }
}