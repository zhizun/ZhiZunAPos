package com.zhizun.pos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.yumendian.cs.activity.manager.ShareLogManager;
import com.yumendian.cs.activity.manager.UserManager;
import com.yumendian.cs.entities.S_User;
import com.yumendian.cs.entities.api.AD_HeaderParam;
import com.yumendian.cs.entities.api.AD_HeaderResult;
import com.yumendian.cs.entities.api.ShareLogCreateParam;
import com.yumendian.cs.entities.api.ShareLogResult;
import com.yunmendian.pos.ui.activity.sales.SalesProuductSelActivity;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.yunmendian.pos.ui.widget.TitleTopBar;

@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class AD_Header_WebActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnClickListener
{
  public static final String LOGID = "logid";
  public static final int MREQUSTCODE = 1;
  private String BackURL;
  private String Browser_BackURL;
  private String aH_URL;
  private AD_Header_WebActivity context;
  private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
  private ImageView mImgWeiChat;
  private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
  private ImageView mShare;
  private RelativeLayout mShareQQ;
  private RelativeLayout mShareQzone;
  private RelativeLayout mShareSina;
  private RelativeLayout mShareTencentweibo;
  private RelativeLayout mShareWechat;
  private RelativeLayout mShareWechatmoments;
  private WebView mWeb;
  private int merchant_ID;
  PopupWindow popup;
  private AD_HeaderResult result;
  private WebSettings setting;
  private int user_ID;
  private View view;
  private WebViewClient webViewClient = new WebViewClient()
  {
    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      int i = paramAnonymousString.indexOf("#Sell");
      int j = paramAnonymousString.indexOf("#Share");
      if (i > -1)
      {
        String str = paramAnonymousString.substring(paramAnonymousString.indexOf("-") + 1, paramAnonymousString.length());
        LogUtils.d("logId----------" + str);
        Intent localIntent = new Intent(AD_Header_WebActivity.this.getApplication(), SalesProuductSelActivity.class);
        localIntent.putExtra("logid", str);
        AD_Header_WebActivity.this.startActivity(localIntent);
        AD_Header_WebActivity.this.finish();
      }
      for (;;)
      {
        LogUtils.i("shouldOverrideUrlLoading..." + paramAnonymousString);
        super.shouldOverrideUrlLoading(paramAnonymousWebView, paramAnonymousString);
        return false;
        if (j > -1) {
          AD_Header_WebActivity.this.showWindow();
        }
      }
    }
  };
  
  private void addQQQZonePlatform()
  {
    Object localObject = new UMQQSsoHandler(this.context, "1104749490", "FzI9ZUurJHsvjlHE");
    ((UMQQSsoHandler)localObject).setTitle(this.result.Data.AH_Title);
    ((UMQQSsoHandler)localObject).setTargetUrl(this.Browser_BackURL);
    ((UMQQSsoHandler)localObject).addToSocialSDK();
    localObject = new QZoneSsoHandler(this.context, "1104749490", "FzI9ZUurJHsvjlHE");
    ((QZoneSsoHandler)localObject).setTargetUrl(this.Browser_BackURL);
    ((QZoneSsoHandler)localObject).addToSocialSDK();
  }
  
  private void addWXPlatform()
  {
    new UMWXHandler(this.context, "wxbd37d26e32a1aecb", "62f9c2b66d892869be437a47ad63cc4d").addToSocialSDK();
    Object localObject = new WeiXinShareContent();
    ((WeiXinShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((WeiXinShareContent)localObject).setShareImage(new UMImage(this.context, this.result.Data.AH_Thumbnail));
    ((WeiXinShareContent)localObject).setTargetUrl(this.BackURL);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new UMWXHandler(this.context, "wxbd37d26e32a1aecb", "62f9c2b66d892869be437a47ad63cc4d");
    ((UMWXHandler)localObject).setTargetUrl(this.BackURL);
    ((UMWXHandler)localObject).setTitle(this.result.Data.AH_Title);
    ((UMWXHandler)localObject).setToCircle(true);
    ((UMWXHandler)localObject).addToSocialSDK();
  }
  
  private void configPlatforms()
  {
    this.mController.getConfig().setSsoHandler(new SinaSsoHandler());
    this.mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    addQQQZonePlatform();
    addWXPlatform();
  }
  
  private void initBaseData()
  {
    this.context = this;
    this.user_ID = UserManager.getInstance().getCurrentUser().getUser_ID();
    this.merchant_ID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.result = ((AD_HeaderResult)getIntent().getSerializableExtra("RESULT"));
  }
  
  private void link()
  {
    this.aH_URL = this.result.Data.AH_URL;
    if (this.result.Data.AH_IsParameter == 1)
    {
      this.aH_URL = (this.result.Data.AH_URL + "?u=" + this.user_ID + "&m=" + this.merchant_ID);
      this.mWeb.setWebChromeClient(new WebChromeClient());
      this.mWeb.setWebViewClient(new WebViewClient());
      this.mWeb.getSettings().setJavaScriptEnabled(true);
      this.mWeb.setLayerType(1, null);
      this.mWeb.loadUrl(this.aH_URL);
      this.mWeb.loadUrl("javascript:window.location.reload( true )");
      if (this.result.Data.AH_IsParameter != 1) {
        break label346;
      }
      this.BackURL = (this.result.Data.AH_BackURL + "?u=" + this.user_ID + "&m=" + this.merchant_ID);
      this.Browser_BackURL = (this.result.Data.AH_Browser_BackURL + "?u=" + this.user_ID + "&m=" + this.merchant_ID);
    }
    for (;;)
    {
      this.mWeb.setWebChromeClient(new WebChromeClient());
      this.mWeb.setWebViewClient(this.webViewClient);
      this.setting = this.mWeb.getSettings();
      this.setting.setJavaScriptEnabled(true);
      return;
      if (this.result.Data.AH_IsParameter != 2) {
        break;
      }
      this.aH_URL = this.result.Data.AH_URL;
      break;
      label346:
      if (this.result.Data.AH_IsParameter == 2)
      {
        this.BackURL = this.result.Data.AH_BackURL;
        this.Browser_BackURL = this.result.Data.AH_Browser_BackURL;
      }
    }
  }
  
  private void performShare(SHARE_MEDIA paramSHARE_MEDIA)
  {
    this.mController.postShare(this.context, paramSHARE_MEDIA, new SocializeListeners.SnsPostListener()
    {
      public void onComplete(SHARE_MEDIA paramAnonymousSHARE_MEDIA, int paramAnonymousInt, SocializeEntity paramAnonymousSocializeEntity)
      {
        int i = 0;
        paramAnonymousSHARE_MEDIA.toString();
        if (paramAnonymousSHARE_MEDIA.equals(SHARE_MEDIA.WEIXIN))
        {
          i = 1;
          if (paramAnonymousInt != 200) {
            break label145;
          }
          paramAnonymousSHARE_MEDIA = "分享成功";
          AD_Header_WebActivity.this.showTime();
          paramAnonymousSocializeEntity = new ShareLogCreateParam(AD_Header_WebActivity.this.user_ID, AD_Header_WebActivity.this.merchant_ID, i, 1);
          AD_Header_WebActivity.this.sharelLog(paramAnonymousSocializeEntity);
        }
        for (;;)
        {
          Toast.makeText(AD_Header_WebActivity.this.context, paramAnonymousSHARE_MEDIA, 0).show();
          AD_Header_WebActivity.this.popup.dismiss();
          return;
          if (paramAnonymousSHARE_MEDIA.equals(SHARE_MEDIA.WEIXIN_CIRCLE))
          {
            i = 2;
            break;
          }
          if (paramAnonymousSHARE_MEDIA.equals(SHARE_MEDIA.QQ))
          {
            i = 3;
            break;
          }
          if (!paramAnonymousSHARE_MEDIA.equals(SHARE_MEDIA.QZONE)) {
            break;
          }
          i = 4;
          break;
          label145:
          paramAnonymousSHARE_MEDIA = "分享失败";
          AD_Header_WebActivity.this.showTime();
        }
      }
      
      public void onStart() {}
    });
  }
  
  private void setShareContent()
  {
    this.mController.getConfig().setSsoHandler(new SinaSsoHandler());
    this.mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    new QZoneSsoHandler(this.context, "1104749490", "FzI9ZUurJHsvjlHE").addToSocialSDK();
    this.mController.setShareContent(this.result.Data.AH_Desc);
    UMImage localUMImage = new UMImage(this.context, this.result.Data.AH_Thumbnail);
    Object localObject = new WeiXinShareContent();
    ((WeiXinShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((WeiXinShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((WeiXinShareContent)localObject).setTargetUrl(this.BackURL);
    ((WeiXinShareContent)localObject).setShareMedia(localUMImage);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new CircleShareContent();
    ((CircleShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((CircleShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((CircleShareContent)localObject).setShareMedia(localUMImage);
    ((CircleShareContent)localObject).setTargetUrl(this.BackURL);
    this.mController.setShareMedia((UMediaObject)localObject);
    localUMImage = new UMImage(this.context, this.result.Data.AH_Thumbnail);
    localUMImage.setTargetUrl(this.Browser_BackURL);
    localObject = new QZoneShareContent();
    ((QZoneShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((QZoneShareContent)localObject).setTargetUrl(this.Browser_BackURL);
    ((QZoneShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((QZoneShareContent)localObject).setShareMedia(localUMImage);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new QQShareContent();
    ((QQShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((QQShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((QQShareContent)localObject).setShareMedia(localUMImage);
    ((QQShareContent)localObject).setTargetUrl(this.Browser_BackURL);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new TencentWbShareContent();
    ((TencentWbShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((TencentWbShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((TencentWbShareContent)localObject).setShareImage(localUMImage);
    ((TencentWbShareContent)localObject).setTargetUrl(this.BackURL);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new SinaShareContent();
    ((SinaShareContent)localObject).setShareContent(this.result.Data.AH_Desc);
    ((SinaShareContent)localObject).setTitle(this.result.Data.AH_Title);
    ((SinaShareContent)localObject).setShareImage(localUMImage);
    ((SinaShareContent)localObject).setTargetUrl(this.BackURL);
    this.mController.setShareMedia((UMediaObject)localObject);
  }
  
  private void sharelLog(ShareLogCreateParam paramShareLogCreateParam)
  {
    ShareLogManager.createMarketShareLog(paramShareLogCreateParam, new RequestCallBack()
    {
      private ShareLogResult logCreateResult;
      
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LogUtils.d("网络获取失败！");
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        this.logCreateResult = ShareLogManager.getShareLogCreateResult((String)paramAnonymousResponseInfo.result);
        if (this.logCreateResult.Code == 200)
        {
          LogUtils.d("分享日志增加成功！");
          return;
        }
        LogUtils.d("分享日志增加失败！");
      }
    });
  }
  
  private void showWindow()
  {
    if (this.popup == null)
    {
      this.view = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903180, null);
      this.mShareQQ = ((RelativeLayout)this.view.findViewById(2131362579));
      this.mShareWechat = ((RelativeLayout)this.view.findViewById(2131362573));
      this.mShareQzone = ((RelativeLayout)this.view.findViewById(2131362576));
      this.mShareWechatmoments = ((RelativeLayout)this.view.findViewById(2131362570));
      this.mShareQQ.setOnClickListener(this);
      this.mShareWechat.setOnClickListener(this);
      this.mShareQzone.setOnClickListener(this);
      this.mShareWechatmoments.setOnClickListener(this);
      this.popup = new PopupWindow(this.view, -1, -2);
      this.mImgWeiChat = ((ImageView)this.view.findViewById(2131362574));
      this.popup.setFocusable(true);
      this.popup.setOutsideTouchable(true);
      this.popup.setOnDismissListener(new PopupWindow.OnDismissListener()
      {
        public void onDismiss() {}
      });
      this.popup.setBackgroundDrawable(new BitmapDrawable());
      this.popup.setTouchInterceptor(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          if (paramAnonymousMotionEvent.getAction() == 4)
          {
            AD_Header_WebActivity.this.popup.dismiss();
            return true;
          }
          return false;
        }
      });
    }
    if (!this.popup.isShowing()) {
      this.popup.showAtLocation(this.mTopBar, 81, 0, 0);
    }
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903044, paramViewGroup);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837751, this);
    this.mWeb = ((WebView)paramViewGroup.findViewById(2131361822));
    initBaseData();
    link();
    configPlatforms();
    setShareContent();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    UMSsoHandler localUMSsoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(paramInt1);
    if (localUMSsoHandler != null) {
      localUMSsoHandler.authorizeCallBack(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362579: 
      performShare(SHARE_MEDIA.QQ);
      return;
    case 2131362573: 
      performShare(SHARE_MEDIA.WEIXIN);
      return;
    case 2131362576: 
      performShare(SHARE_MEDIA.QZONE);
      return;
    }
    performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    showWindow();
  }
  
  public void showTime()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        AD_Header_WebActivity.this.mWeb.reload();
      }
    }, 2000L);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\activity\AD_Header_WebActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */