package com.zhizun.pos.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
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
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zizun.cs.activity.manager.AD_headerManager;
import com.zizun.cs.activity.manager.AppManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.AD_HeaderParam;
import com.zizun.cs.entities.api.AD_HeaderResult;
import com.zhizun.pos.ui.activity.AD_Header_WebActivity;
import com.zhizun.pos.ui.activity.FunctionPageActivity;
import com.zhizun.pos.ui.activity.PaymentActivity;
import com.zhizun.pos.ui.activity.SettingMessgeActivity;
import com.zhizun.pos.ui.activity.merchant.MerchantActivity;
import com.zhizun.pos.ui.adapter.PersonalAdapter;
import com.zhizun.pos.ui.widget.TitleTopBar;

public class PersonalFragment
  extends BaseTitleTopBarFragment
  implements AdapterView.OnItemClickListener, View.OnClickListener
{
  public static final int BUNNERCODE = 2;
  public static final int MREQUSTCODE = 1;
  private String ah_Image;
  private BitmapUtils bitmapUtils;
  private FragmentActivity context;
  private UMImage localImage;
  private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
  private ImageView mImgADheader;
  private Intent mIntent;
  private ListView mLsvItem;
  private int merchant_ID;
  private AD_HeaderResult result;
  private String shareContent;
  private AD_HeaderResult shareResult;
  private String shareTitle;
  private String shareUrl;
  private String url;
  private int user_ID;
  
  private void addQQQZonePlatform()
  {
    Object localObject = new UMQQSsoHandler(this.context, "1104749490", "FzI9ZUurJHsvjlHE");
    ((UMQQSsoHandler)localObject).setTargetUrl(this.shareUrl);
    ((UMQQSsoHandler)localObject).addToSocialSDK();
    localObject = new QZoneSsoHandler(this.context, "1104749490", "FzI9ZUurJHsvjlHE");
    ((QZoneSsoHandler)localObject).setTargetUrl(this.shareUrl);
    ((QZoneSsoHandler)localObject).addToSocialSDK();
  }
  
  private void addSharePlatform()
  {
    new UMWXHandler(getActivity(), "wxbd37d26e32a1aecb", "62f9c2b66d892869be437a47ad63cc4d").addToSocialSDK();
    UMWXHandler localUMWXHandler = new UMWXHandler(getActivity(), "wxbd37d26e32a1aecb", "62f9c2b66d892869be437a47ad63cc4d");
    localUMWXHandler.setToCircle(true);
    localUMWXHandler.addToSocialSDK();
    this.mController.getConfig().setSsoHandler(new SinaSsoHandler());
    this.mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    addQQQZonePlatform();
  }
  
  private void getADheaderResult()
  {
    String str = AppManager.getInstance().getAppVersionName(this.context);
    AD_headerManager.GetAD_headerBannerfromAPI(new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        ToastUtil.toastShort(PersonalFragment.this.getActivity(), "网络获取失败");
        PersonalFragment.this.mImgADheader.setVisibility(8);
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        PersonalFragment.this.result = AD_headerManager.getADheaderBannerFromJsonResult((String)paramAnonymousResponseInfo.result);
        LogUtil.logD((String)paramAnonymousResponseInfo.result);
        if (PersonalFragment.this.result.Code == 200)
        {
          PersonalFragment.this.ah_Image = PersonalFragment.this.result.Data.AH_Image;
          LogUtil.logD(PersonalFragment.this.result.Data.AH_ShowTime);
          if ((PersonalFragment.this.result.Data.AH_IsShow == 1) && (!TextUtils.isEmpty(PersonalFragment.this.ah_Image)))
          {
            ImgUtil.showBig(PersonalFragment.this.mImgADheader, PersonalFragment.this.ah_Image);
            PersonalFragment.this.showTime();
          }
        }
        while (PersonalFragment.this.result.Code != 500) {
          for (;;)
          {
            return;
            PersonalFragment.this.mImgADheader.setVisibility(8);
          }
        }
        ToastUtil.toastShort(PersonalFragment.this.getActivity(), "网络获取失败");
        PersonalFragment.this.mImgADheader.setVisibility(8);
      }
    }, str);
  }
  
  private void initBaseData()
  {
    this.context = getActivity();
    this.user_ID = UserManager.getInstance().getCurrentUser().getUser_ID();
    this.merchant_ID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
  }
  
  private void setShareContent()
  {
    this.mController.setShareContent("至尊一体化收银，简单又方便");
    Object localObject = new WeiXinShareContent();
    ((WeiXinShareContent)localObject).setShareContent(this.shareContent);
    ((WeiXinShareContent)localObject).setTitle(this.shareTitle);
    ((WeiXinShareContent)localObject).setTargetUrl(this.shareUrl);
    ((WeiXinShareContent)localObject).setShareMedia(this.localImage);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new CircleShareContent();
    ((CircleShareContent)localObject).setShareContent(this.shareContent);
    ((CircleShareContent)localObject).setTitle(this.shareTitle);
    ((CircleShareContent)localObject).setShareMedia(this.localImage);
    ((CircleShareContent)localObject).setTargetUrl(this.shareUrl);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new QZoneShareContent();
    ((QZoneShareContent)localObject).setShareContent(this.shareContent);
    ((QZoneShareContent)localObject).setTargetUrl(this.shareUrl);
    ((QZoneShareContent)localObject).setTitle(this.shareTitle);
    ((QZoneShareContent)localObject).setShareMedia(this.localImage);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new QQShareContent();
    ((QQShareContent)localObject).setShareContent(this.shareContent);
    ((QQShareContent)localObject).setTitle(this.shareTitle);
    ((QQShareContent)localObject).setShareMedia(this.localImage);
    ((QQShareContent)localObject).setTargetUrl(this.shareUrl);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new TencentWbShareContent();
    ((TencentWbShareContent)localObject).setShareContent(this.shareContent);
    ((TencentWbShareContent)localObject).setShareMedia(this.localImage);
    ((TencentWbShareContent)localObject).setTitle(this.shareTitle);
    ((TencentWbShareContent)localObject).setTargetUrl(this.shareUrl);
    this.mController.setShareMedia((UMediaObject)localObject);
    localObject = new SinaShareContent();
    ((SinaShareContent)localObject).setShareContent(this.shareContent);
    ((SinaShareContent)localObject).setShareMedia(this.localImage);
    ((SinaShareContent)localObject).setTitle(this.shareTitle);
    ((SinaShareContent)localObject).setTargetUrl(this.shareUrl);
    this.mController.setShareMedia((UMediaObject)localObject);
  }
  
  private void share()
  {
    AD_headerManager.GetShareInfofromAPI(new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        ToastUtil.toastShort(PersonalFragment.this.getActivity(), 2131165320);
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        PersonalFragment.this.shareResult = AD_headerManager.getADheaderFromJsonResult((String)paramAnonymousResponseInfo.result);
        LogUtil.logD("分享" + (String)paramAnonymousResponseInfo.result);
        if (PersonalFragment.this.shareResult.Code == 200)
        {
          PersonalFragment.this.ah_Image = PersonalFragment.this.shareResult.Data.AH_Image;
          PersonalFragment.this.bitmapUtils = new BitmapUtils(PersonalFragment.this.getActivity());
          PersonalFragment.this.localImage = new UMImage(PersonalFragment.this.getActivity(), PersonalFragment.this.shareResult.Data.AH_Thumbnail);
          PersonalFragment.this.localImage.setTargetUrl(PersonalFragment.this.shareResult.Data.AH_URL);
          PersonalFragment.this.shareContent = PersonalFragment.this.shareResult.Data.AH_Desc;
          PersonalFragment.this.shareTitle = PersonalFragment.this.shareResult.Data.AH_Title;
          PersonalFragment.this.shareUrl = PersonalFragment.this.shareResult.Data.AH_URL;
          PersonalFragment.this.setShareContent();
          PersonalFragment.this.addSharePlatform();
          PersonalFragment.this.mController.getConfig().setPlatforms(new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE });
          PersonalFragment.this.mController.openShare(PersonalFragment.this.getActivity(), false);
          com.umeng.socialize.common.SocializeConstants.SHOW_ERROR_CODE = false;
          return;
        }
        ToastUtil.toastShort(PersonalFragment.this.getActivity(), PersonalFragment.this.shareResult.Message);
      }
    });
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getActivity().getLayoutInflater().inflate(2130903179, paramViewGroup);
    this.mTopBar.setTitle(2131165294);
    this.mLsvItem = ((ListView)paramViewGroup.findViewById(2131362568));
    this.mImgADheader = ((ImageView)paramViewGroup.findViewById(2131362567));
    this.mImgADheader.setOnClickListener(this);
    this.mLsvItem.setAdapter(new PersonalAdapter());
    this.mLsvItem.setOnItemClickListener(this);
    initBaseData();
    getADheaderResult();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    paramView = new Intent(this.context, AD_Header_WebActivity.class);
    paramView.putExtra("RESULT", this.result);
    startActivityForResult(paramView, 1);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 0: 
      this.mIntent = new Intent(getActivity(), MerchantActivity.class);
      startActivity(this.mIntent);
      return;
    case 1: 
      startActivity(new Intent(getActivity(), PaymentActivity.class));
      return;
    case 2: 
      this.mIntent = new Intent(getActivity(), FunctionPageActivity.class);
      startActivity(this.mIntent);
      return;
    case 3: 
      startActivity(new Intent(getActivity(), SettingMessgeActivity.class));
      return;
    }
    share();
  }
  
  public void showTime()
  {
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        PersonalFragment.this.mImgADheader.setVisibility(8);
      }
    }, Long.valueOf(this.result.Data.AH_ShowTime).longValue() * 1000L);
  }
}