package com.zhizun.pos.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.yumendian.cs.activity.manager.ManagerFactory;
import com.yumendian.cs.activity.manager.MerchantManager;
import com.yumendian.cs.activity.manager.PaymentManager;
import com.yumendian.cs.activity.manager.SyncManager;
import com.yumendian.cs.activity.manager.UserManager;
import com.yumendian.cs.biz.dao.impl.S_MerchantDaoImpl;
import com.yumendian.cs.biz.dao.impl.SyncDaoImpl;
import com.yumendian.cs.common.utils.ExecutorUtils;
import com.yumendian.cs.common.utils.ImgUtil;
import com.yumendian.cs.common.utils.PreferencesUtil;
import com.yumendian.cs.common.utils.ToastUtil;
import com.yumendian.cs.entities.S_Merchant;
import com.yumendian.cs.entities.S_User;
import com.yumendian.cs.entities.api.ImgParam;
import com.yumendian.cs.entities.api.ImgResult;
import com.yumendian.cs.entities.sync.SyncUpLoadParam;
import com.yumendian.cs.entities.sync.SyncUpLoadResult;
import com.yunmendian.pos.ui.popupwindow.PhotoPopupWindow;
import com.yunmendian.pos.ui.popupwindow.PhotoPopupWindow.OnPhotoPopupWindowClickListener;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.yunmendian.pos.ui.widget.RadioTopBar;
import com.yunmendian.pos.ui.widget.RadioTopBar.OnTopBarOptionChangeListener;
import com.yunmendian.pos.xujie.NetUtil;
import java.io.File;
import java.util.concurrent.ExecutorService;

public class PaymentActivity
  extends BaseRadioTopBarFragmentActivity
  implements BaseTopBar.OnTopBarLeftClickListener, RadioTopBar.OnTopBarOptionChangeListener, View.OnClickListener, PhotoPopupWindow.OnPhotoPopupWindowClickListener
{
  private static final int CROP_IMAGE_REQUEST = 4;
  private static final int PICK_PHOTO_REQUEST = 3;
  private static final int SYNC_DOWNLOAD = 18;
  private static final int SYNC_DOWNLOAD_FAIL = 20;
  private static final int SYNC_DOWNLOAD_SUCCESS = 19;
  private static final int SYNC_UPLOAD = 17;
  private static final int TAKE_PHOTO_REQUEST = 2;
  private Bitmap aliPayCache;
  private Button btn_code;
  private CheckBox cb_dim_code;
  private Context context;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        PaymentActivity.this.syncUpload();
        continue;
        ToastUtil.toastLong(PaymentActivity.this.context, "同步完成");
      }
    }
  });
  private String imgHeadPath;
  private String imgPhotoPath;
  private ImageView imvWeixin1;
  private ImageView imvWeixin2;
  private ImageView imvWeixin3;
  private ImageView imvWeixin4;
  private ImageView imvWeixin5;
  private ImageView imvZhifubao1;
  private ImageView imvZhifubao2;
  private ImageView imvZhifubao3;
  private ImageView imvZhifubao4;
  private ImageView imvZhifubao5;
  private ImageView imvZhifubao6;
  private ImageView imvZhifubao7;
  private boolean isZhiFuBao = true;
  private ImageView iv_dim_code;
  private S_Merchant merchant;
  private int merchantID;
  private PhotoPopupWindow photoPopupWindow;
  private S_MerchantDaoImpl s_MerchantDao;
  private SyncDaoImpl syncDao;
  private SyncManager syncManager;
  private TextView tv_desc;
  private SyncUpLoadParam upLoadParam;
  private SyncUpLoadResult upLoadResult;
  private Bitmap weixinCache;
  
  private void loadBitmap()
  {
    if (this.isZhiFuBao) {}
    for (String str = this.merchant.getAliPay_QR();; str = this.merchant.getWX_QR())
    {
      ImgUtil.showBigImg(this.iv_dim_code, str, 2130837804, 2);
      return;
    }
  }
  
  private void syncUpload()
  {
    this.syncManager.doSyncUpLoad(this.upLoadParam, new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString) {}
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        PaymentActivity.this.upLoadResult = PaymentActivity.this.syncManager.getSyncUPResultFromJson((String)paramAnonymousResponseInfo.result);
        if (PaymentActivity.this.upLoadResult.Code == 200)
        {
          LogUtils.i("已成功同步到服务器");
          return;
        }
        LogUtils.i(PaymentActivity.this.upLoadResult.Message);
        ToastUtil.toastLong(PaymentActivity.this, "网络异常，请检查网络连接后重试");
      }
    });
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    this.context = this;
    getLayoutInflater().inflate(2130903077, paramViewGroup);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setOnOptionChangeListener(this);
    this.mTopBar.setRightOptionText(2131165514);
    this.mTopBar.setLeftOptionText(2131165710);
    this.tv_desc = ((TextView)findViewById(2131361919));
    this.iv_dim_code = ((ImageView)findViewById(2131362066));
    this.iv_dim_code.setImageResource(2130837804);
    this.iv_dim_code.setOnClickListener(this);
    this.imvZhifubao1 = ((ImageView)findViewById(2131362068));
    this.imvZhifubao2 = ((ImageView)findViewById(2131362069));
    this.imvZhifubao3 = ((ImageView)findViewById(2131362070));
    this.imvZhifubao4 = ((ImageView)findViewById(2131362071));
    this.imvZhifubao5 = ((ImageView)findViewById(2131362072));
    this.imvZhifubao6 = ((ImageView)findViewById(2131362073));
    this.imvZhifubao7 = ((ImageView)findViewById(2131362074));
    this.imvWeixin1 = ((ImageView)findViewById(2131362075));
    this.imvWeixin2 = ((ImageView)findViewById(2131362076));
    this.imvWeixin3 = ((ImageView)findViewById(2131362077));
    this.imvWeixin4 = ((ImageView)findViewById(2131362078));
    this.imvWeixin5 = ((ImageView)findViewById(2131362079));
    this.btn_code = ((Button)findViewById(2131362067));
    this.btn_code.setOnClickListener(this);
    this.s_MerchantDao = new S_MerchantDaoImpl();
    this.merchant = this.s_MerchantDao.getMerchant();
    this.btn_code.setVisibility(4);
    loadBitmap();
    this.syncManager = ManagerFactory.getSyncManager();
    this.merchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.syncDao = new SyncDaoImpl(this.context, this.merchantID);
  }
  
  public void cancel()
  {
    if ((this.photoPopupWindow != null) && (this.photoPopupWindow.isShowing())) {
      this.photoPopupWindow.dismiss();
    }
    this.btn_code.setVisibility(4);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1) {
      switch (paramInt1)
      {
      }
    }
    do
    {
      do
      {
        do
        {
          return;
        } while (!new File(this.imgPhotoPath).exists());
        LogUtils.i("拍照  : " + this.imgPhotoPath);
        paramIntent = new Intent(this, CropImageActivity.class);
        paramIntent.putExtra(String.class.getSimpleName(), this.imgPhotoPath);
        startActivityForResult(paramIntent, 4);
        return;
        this.imgPhotoPath = PhotoPopupWindow.getPickPhotoResult(this, paramIntent);
      } while (TextUtils.isEmpty(this.imgPhotoPath));
      LogUtils.i("相册  : " + this.imgPhotoPath);
      paramIntent = new Intent(this, CropImageActivity.class);
      paramIntent.putExtra(String.class.getSimpleName(), this.imgPhotoPath);
      startActivityForResult(paramIntent, 4);
      return;
      this.imgHeadPath = PreferencesUtil.getPreference(this).getString("path_image_upload", "");
      LogUtils.i(this.imgHeadPath);
    } while (TextUtils.isEmpty(this.imgHeadPath));
    ImgUtil.showBig(this.iv_dim_code, this.imgHeadPath);
    this.btn_code.setVisibility(0);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362066: 
      if (UserManager.getInstance().isExperienceAccount())
      {
        ToastUtil.toastLong(this, 2131165567);
        return;
      }
      this.photoPopupWindow = PhotoPopupWindow.create(this);
      this.photoPopupWindow.setOnPhotoPopupWindowClickListener(this);
      return;
    }
    if (UserManager.getInstance().isExperienceAccount())
    {
      ToastUtil.toastLong(this, 2131165567);
      return;
    }
    if (NetUtil.checkNet(this))
    {
      showWaitDialog();
      this.btn_code.setEnabled(false);
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          String str = ImgUtil.bitmapToString(PaymentActivity.this.imgHeadPath);
          PaymentActivity.this.updateImg(str, UserManager.getInstance().getCurrentUser().getMerchant_ID());
        }
      });
      return;
    }
    ToastUtil.toastShort(this, "网络异常，请检查网络后再试");
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarLeftOptionSelect()
  {
    this.imvZhifubao1.setVisibility(0);
    this.imvZhifubao2.setVisibility(0);
    this.imvZhifubao3.setVisibility(0);
    this.imvZhifubao4.setVisibility(0);
    this.imvZhifubao5.setVisibility(0);
    this.imvZhifubao6.setVisibility(0);
    this.imvZhifubao7.setVisibility(0);
    this.imvWeixin1.setVisibility(8);
    this.imvWeixin2.setVisibility(8);
    this.imvWeixin3.setVisibility(8);
    this.imvWeixin4.setVisibility(8);
    this.imvWeixin5.setVisibility(8);
    this.isZhiFuBao = true;
    this.tv_desc.setText(getResources().getString(2131165708));
    loadBitmap();
  }
  
  public void onTopBarRightOptionSelect()
  {
    this.imvZhifubao1.setVisibility(8);
    this.imvZhifubao2.setVisibility(8);
    this.imvZhifubao3.setVisibility(8);
    this.imvZhifubao4.setVisibility(8);
    this.imvZhifubao5.setVisibility(8);
    this.imvZhifubao6.setVisibility(8);
    this.imvZhifubao7.setVisibility(8);
    this.imvWeixin1.setVisibility(0);
    this.imvWeixin2.setVisibility(0);
    this.imvWeixin3.setVisibility(0);
    this.imvWeixin4.setVisibility(0);
    this.imvWeixin5.setVisibility(0);
    this.tv_desc.setText(getResources().getString(2131165709));
    this.isZhiFuBao = false;
    loadBitmap();
  }
  
  public void pickPhoto()
  {
    PhotoPopupWindow.pickPhoto(this, 3);
  }
  
  public void takePhoto()
  {
    this.imgPhotoPath = PhotoPopupWindow.takePhoto(this, 2);
  }
  
  public void updateImg(String paramString, int paramInt)
  {
    if (this.isZhiFuBao)
    {
      PaymentManager.uploadAliPayImg(new ImgParam(paramInt, paramString), new RequestCallBack()
      {
        public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
        {
          PaymentActivity.this.dismissWaitDialog();
          LogUtils.i("网络异常，请检查网络连接后重试");
          ToastUtil.toastShort(PaymentActivity.this, "保存失败，请检查网络");
          PaymentActivity.this.btn_code.setEnabled(true);
        }
        
        public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
        {
          PaymentActivity.this.dismissWaitDialog();
          paramAnonymousResponseInfo = MerchantManager.getUpLoadImgCUDResult((String)paramAnonymousResponseInfo.result);
          if (paramAnonymousResponseInfo != null) {
            PaymentActivity.this.merchant.setAliPay_QR(paramAnonymousResponseInfo.Data);
          }
          PaymentActivity.this.merchant.setUpdateEntitySync(UserManager.getInstance().getCurrentUser().getUser_ID());
          PaymentActivity.this.s_MerchantDao.update(PaymentActivity.this.getApplicationContext(), PaymentActivity.this.merchant);
          ToastUtil.toastShort(PaymentActivity.this, "保存成功");
          PaymentActivity.this.btn_code.setEnabled(true);
        }
      });
      return;
    }
    PaymentManager.uploadWeiXinImg(new ImgParam(paramInt, paramString), new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        PaymentActivity.this.dismissWaitDialog();
        LogUtils.i(paramAnonymousHttpException.getMessage());
        ToastUtil.toastShort(PaymentActivity.this, "保存失败，请检查网络");
        PaymentActivity.this.btn_code.setEnabled(true);
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        PaymentActivity.this.dismissWaitDialog();
        paramAnonymousResponseInfo = MerchantManager.getUpLoadImgCUDResult((String)paramAnonymousResponseInfo.result);
        if (paramAnonymousResponseInfo != null) {
          PaymentActivity.this.merchant.setWX_QR(paramAnonymousResponseInfo.Data);
        }
        PaymentActivity.this.merchant.setUpdateEntitySync(UserManager.getInstance().getCurrentUser().getUser_ID());
        PaymentActivity.this.s_MerchantDao.update(PaymentActivity.this.getApplicationContext(), PaymentActivity.this.merchant);
        ToastUtil.toastShort(PaymentActivity.this, "保存成功");
        PaymentActivity.this.btn_code.setEnabled(true);
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\activity\PaymentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */