package com.zhizun.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.activity.manager.AD_headerManager;
import com.zizun.cs.activity.manager.AppManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.AD_HeaderParam;
import com.zizun.cs.entities.api.AD_HeaderResult;
import com.zhizun.pos.util.LogUtil;

public class AD_HeaderActivity
  extends BaseActivity
  implements View.OnClickListener
{
  public static final int MREQUSTCODE = 1;
  private boolean IsClick = false;
  private String ah_Image;
  private AD_HeaderActivity context;
  private ImageView mImgAD;
  private ImageView mImgJump;
  private WebView mWeb;
  private int merchant_ID;
  private AD_HeaderResult result;
  private int user_ID;
  
  private void getADheaderResult()
  {
    String str = AppManager.getInstance().getAppVersionName(this.context);
    AD_headerManager.GetAD_headerfromAPI(new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        ToastUtil.toastShort(AD_HeaderActivity.this, "网络获取失败！");
        AD_HeaderActivity.this.jumpMain();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        AD_HeaderActivity.this.result = AD_headerManager.getADheaderFromJsonResult((String)paramAnonymousResponseInfo.result);
        LogUtil.logD((String)paramAnonymousResponseInfo.result);
        if (AD_HeaderActivity.this.result.Code == 200)
        {
          AD_HeaderActivity.this.ah_Image = AD_HeaderActivity.this.result.Data.AH_Image;
          if (AD_HeaderActivity.this.result.Data.AH_IsShow == 1)
          {
            String str = AD_HeaderActivity.this.result.Data.AH_OtherUrl;
            if ((str == null) || (str.equals(""))) {
              ImgUtil.showBig(AD_HeaderActivity.this.mImgAD, AD_HeaderActivity.this.ah_Image);
            }
            for (;;)
            {
              AD_HeaderActivity.this.showTime();
              return;
              paramAnonymousResponseInfo = str;
              if (AD_HeaderActivity.this.result.Data.AH_IsParameter == 1) {
                paramAnonymousResponseInfo = str + "?u=" + AD_HeaderActivity.this.user_ID + "&m=" + AD_HeaderActivity.this.merchant_ID;
              }
              AD_HeaderActivity.this.mWeb.loadUrl(paramAnonymousResponseInfo);
            }
          }
          AD_HeaderActivity.this.jumpMain();
          return;
        }
        ToastUtil.toastShort(AD_HeaderActivity.this, "网络获取失败！");
        AD_HeaderActivity.this.jumpMain();
      }
    }, str);
  }
  
  private void hideSystemUI()
  {
    getWindow().getDecorView().setSystemUiVisibility(3078);
  }
  
  private void initBaseData()
  {
    this.context = this;
    this.user_ID = UserManager.getInstance().getCurrentUser().getUser_ID();
    this.merchant_ID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void initView()
  {
    this.mImgAD = ((ImageView)findViewById(2131361820));
    this.mImgAD.setOnClickListener(this);
    this.mWeb = ((WebView)findViewById(2131361819));
    this.mImgJump = ((ImageView)findViewById(2131361821));
    this.mImgJump.setOnClickListener(this);
    this.mWeb.setWebChromeClient(new WebChromeClient());
    this.mWeb.setWebViewClient(new WebViewClient());
    this.mWeb.getSettings().setJavaScriptEnabled(true);
    hideSystemUI();
  }
  
  private void jumpMain()
  {
    startActivity(new Intent(this.context, MainActivity.class));
    finish();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 1) {
      jumpMain();
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131361820: 
      this.IsClick = true;
      paramView = new Intent(this.context, AD_Header_WebActivity.class);
      paramView.putExtra("RESULT", this.result);
      startActivityForResult(paramView, 1);
      return;
    }
    this.IsClick = true;
    jumpMain();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    initView();
    initBaseData();
    getADheaderResult();
  }
  
  public void showTime()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        if (!AD_HeaderActivity.this.IsClick) {
          AD_HeaderActivity.this.jumpMain();
        }
      }
    }, Long.valueOf(this.result.Data.AH_ShowTime).longValue() * 1000L);
  }
}