package com.zhizun.pos.ui.activity.merchant;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.MerchantManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.action.merchant.LogOutAction;
import com.zizun.cs.biz.dao.impl.S_IndustryDaoImpl;
import com.zizun.cs.biz.dao.impl.S_MerchantDaoImpl;
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_Industry;
import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.S_Role;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.ImgResult;
import com.zizun.cs.entities.api.MerchantImgUploadParam;
import com.zhizun.pos.service.SyncService;
import com.zhizun.pos.service.SyncService.OnSyncServiceListener;
import com.zhizun.pos.ui.activity.BaseTitleTopBarActivity;
import com.zhizun.pos.ui.activity.CropImageActivity;
import com.zhizun.pos.ui.activity.GuideActivity;
import com.zhizun.pos.ui.activity.IndustrySelActivity2;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow.OnPhotoPopupWindowClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.xujie.NetUtil;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MerchantActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnClickListener, PhotoPopupWindow.OnPhotoPopupWindowClickListener
{
  private static final int CROP_IMAGE_REQUEST = 4;
  private static final int GET_INDUSTRY_REQUEST = 1;
  protected static boolean ISLOGOUT = false;
  private static final int PICK_PHOTO_REQUEST = 3;
  private static final int TAKE_PHOTO_REQUEST = 2;
  private Button bt_merchant_logout;
  private Context context;
  private EditText et_merchant_address;
  private EditText et_merchant_email;
  private EditText et_merchant_name;
  private EditText et_merchant_phone;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      MerchantActivity.this.finish();
    }
  };
  private String imgHeadPath;
  private String imgPhotoPath;
  private ImageView iv_merchant_image;
  private LinearLayout ll_merchant_industry;
  private S_Merchant merchant;
  private int merchantID;
  private PhotoPopupWindow photoPopupWindow;
  private S_Industry s_Industry;
  private S_MerchantDaoImpl s_MerchantDao;
  private TextView tv_merchant_industry;
  private TextView tv_merchant_mobile;
  private TextView tv_merchant_username;
  private ImageView wiget_topnavbar_imgRightBtn2;
  
  private void checkRoleType()
  {
    if (UserManager.getInstance().getS_Role().getRole_ID() != 1L)
    {
      this.iv_merchant_image.setClickable(false);
      this.et_merchant_name.setClickable(false);
      this.tv_merchant_industry.setClickable(false);
      this.tv_merchant_username.setClickable(false);
      this.tv_merchant_mobile.setClickable(false);
      this.et_merchant_address.setClickable(false);
      this.et_merchant_email.setClickable(false);
      this.et_merchant_phone.setClickable(false);
    }
  }
  
  private S_Industry getIndustry()
  {
    int i = this.merchant.getIndustry_ID();
    this.s_Industry = new S_IndustryDaoImpl().getIndustryById(i);
    return this.s_Industry;
  }
  
  private void initData()
  {
    this.context = this;
    this.merchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.s_MerchantDao = new S_MerchantDaoImpl();
    this.merchant = this.s_MerchantDao.getMerchant();
    if (this.merchant != null)
    {
      ImgUtil.showImg(this.iv_merchant_image, this.merchant.getMerchant_Image(), 2130837667, 4);
      Object localObject = this.et_merchant_name;
      if (this.merchant.getMerchant_Name() == null)
      {
        str = "";
        ((EditText)localObject).setText(str);
        localObject = this.tv_merchant_industry;
        if (getIndustry() != null) {
          break label236;
        }
        str = "";
        label102:
        ((TextView)localObject).setText(str);
        localObject = this.tv_merchant_username;
        if (UserManager.getInstance().getCurrentUser().getUser_Name() != null) {
          break label247;
        }
        str = "";
        label127:
        ((TextView)localObject).setText(str);
        localObject = this.tv_merchant_mobile;
        if (this.merchant.getMerchant_Mobile() != null) {
          break label260;
        }
        str = "";
        label150:
        ((TextView)localObject).setText(str);
        localObject = this.et_merchant_address;
        if (this.merchant.getMerchant_Address() != null) {
          break label271;
        }
        str = "";
        label173:
        ((EditText)localObject).setText(str);
        localObject = this.et_merchant_email;
        if (this.merchant.getMerchant_Email() != null) {
          break label282;
        }
        str = "";
        label196:
        ((EditText)localObject).setText(str);
        localObject = this.et_merchant_phone;
        if (this.merchant.getMerchant_Phone() != null) {
          break label293;
        }
      }
      label236:
      label247:
      label260:
      label271:
      label282:
      label293:
      for (String str = "";; str = this.merchant.getMerchant_Phone())
      {
        ((EditText)localObject).setText(str);
        return;
        str = this.merchant.getMerchant_Name();
        break;
        str = getIndustry().getIndustry_Name();
        break label102;
        str = UserManager.getInstance().getCurrentUser().getUser_Name();
        break label127;
        str = this.merchant.getMerchant_Mobile();
        break label150;
        str = this.merchant.getMerchant_Address();
        break label173;
        str = this.merchant.getMerchant_Email();
        break label196;
      }
    }
    ToastUtil.toastShort(this, "数据获取失败");
    finish();
  }
  
  private void initTitle(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903074, paramViewGroup);
    this.mTopBar.setTitle(2131165551);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837754, this);
  }
  
  private void initView(ViewGroup paramViewGroup)
  {
    initTitle(paramViewGroup);
    this.iv_merchant_image = ((ImageView)findViewById(2131362021));
    this.et_merchant_name = ((EditText)findViewById(2131362025));
    this.ll_merchant_industry = ((LinearLayout)findViewById(2131362026));
    this.tv_merchant_industry = ((TextView)findViewById(2131362029));
    this.tv_merchant_username = ((TextView)findViewById(2131362033));
    this.tv_merchant_mobile = ((TextView)findViewById(2131362037));
    this.et_merchant_address = ((EditText)findViewById(2131362041));
    this.et_merchant_email = ((EditText)findViewById(2131362045));
    this.et_merchant_phone = ((EditText)findViewById(2131362049));
    this.bt_merchant_logout = ((Button)findViewById(2131362050));
    this.wiget_topnavbar_imgRightBtn2 = ((ImageView)findViewById(2131362809));
    this.iv_merchant_image.setOnClickListener(this);
    this.ll_merchant_industry.setOnClickListener(this);
    this.bt_merchant_logout.setOnClickListener(this);
  }
  
  private void loginOut()
  {
    SyncService.clearOnSyncListener();
    SyncService.stopService(this.context);
    LogOutAction.logout(this.context, GuideActivity.class);
    finish();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    checkLogined();
    initView(paramViewGroup);
    initData();
  }
  
  public void cancel()
  {
    if ((this.photoPopupWindow != null) && (this.photoPopupWindow.isShowing())) {
      this.photoPopupWindow.dismiss();
    }
  }
  
  public void checkLogined()
  {
    if (UserManager.getInstance().getCurrentUser() == null)
    {
      ToastUtil.toastLong(this, "您还没有登录，请先登录");
      loginOut();
    }
  }
  
  public boolean checkSync()
  {
    List localList = new S_Sync_UploadDaoImpl().getS_Sync_UploadList();
    return (localList != null) && (localList.size() > 0);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1) {}
    switch (paramInt1)
    {
    default: 
    case 1: 
    case 2: 
    case 3: 
      do
      {
        do
        {
          do
          {
            return;
          } while (paramIntent == null);
          this.s_Industry = ((S_Industry)paramIntent.getSerializableExtra("s_Industry"));
          if (this.s_Industry == null)
          {
            Log.i(getClass().getSimpleName(), "行业没获取到");
            return;
          }
          this.tv_merchant_industry.setText(this.s_Industry.getIndustry_Name());
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
    }
    this.imgHeadPath = PreferencesUtil.getPreference(this).getString("path_image_upload", "");
    LogUtils.i(this.imgHeadPath);
    ImgUtil.showBig(this.iv_merchant_image, this.imgHeadPath);
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }
  
  public void onClick(View paramView)
  {
    this.wiget_topnavbar_imgRightBtn2.setVisibility(0);
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362021: 
      if (UserManager.getInstance().isExperienceAccount())
      {
        ToastUtil.toastLong(this, 2131165567);
        return;
      }
      this.photoPopupWindow = PhotoPopupWindow.create(this);
      this.photoPopupWindow.setOnPhotoPopupWindowClickListener(this);
      return;
    case 2131362026: 
      startActivityForResult(new Intent(this, IndustrySelActivity2.class), 1);
      return;
    }
    if (UserManager.getInstance().isExperienceAccount())
    {
      loginOut();
      return;
    }
    if (checkSync())
    {
      if (!NetUtil.checkNet(this.context)) {
        loginOut();
      }
      showSyncDialog();
      return;
    }
    UserManager.getInstance().setCurrentUser(null);
    ToastUtil.toastShort(this, "成功注销登陆");
    loginOut();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    ISLOGOUT = false;
    if (NetUtil.checkNet(this))
    {
      if (!TextUtils.isEmpty(this.imgHeadPath))
      {
        showWaitDialog("正在上传图片,请稍后...", true);
        this.mTopBar.getRightButton().setEnabled(false);
        ExecutorUtils.getDBExecutor().execute(new Runnable()
        {
          public void run()
          {
            String str = ImgUtil.bitmapToString(MerchantActivity.this.imgHeadPath);
            MerchantActivity.this.updateImg(str, MerchantActivity.this.merchant.getMerchant_ID());
          }
        });
        return;
      }
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          MerchantActivity.this.save();
        }
      });
      return;
    }
    ToastUtil.toastShort(this, "网络异常，请检查网络后再试");
  }
  
  public void pickPhoto()
  {
    PhotoPopupWindow.pickPhoto(this, 3);
  }
  
  public void save()
  {
    String str1 = this.et_merchant_name.getText().toString().trim();
    if (this.s_Industry != null)
    {
      int i = this.s_Industry.getIndustry_ID();
      this.merchant.setIndustry_ID((byte)i);
    }
    String str2 = this.et_merchant_address.getText().toString().trim();
    String str3 = this.et_merchant_email.getText().toString().trim();
    String str4 = this.et_merchant_phone.getText().toString().trim();
    this.merchant.setMerchant_Name(str1);
    this.merchant.setMerchant_Address(str2);
    this.merchant.setMerchant_Email(str3);
    this.merchant.setMerchant_Phone(str4);
    this.merchant.setUpdateEntitySync(UserManager.getInstance().getCurrentUser().getUser_ID());
    this.s_MerchantDao.update(getApplicationContext(), this.merchant);
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        ToastUtil.toastShort(MerchantActivity.this, "保存成功");
      }
    });
  }
  
  public void showSyncDialog()
  {
    ISLOGOUT = true;
    new AlertDialog.Builder(this).setTitle("提醒").setMessage("发现有未同步的数据，是否同步？").setPositiveButton("是", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        SyncService.sync(MerchantActivity.this.context, new SyncService.OnSyncServiceListener()
        {
          public void onSyncFail(String paramAnonymous2String)
          {
            LogUtils.i("同步失败");
            ToastUtil.toastShort(MerchantActivity.this.context, "同步失败");
            MerchantActivity.this.loginOut();
          }
          
          public void onSyncSuccess()
          {
            LogUtils.i("同步成功");
            ToastUtil.toastShort(MerchantActivity.this.context, "同步完成");
            MerchantActivity.this.loginOut();
          }
        });
      }
    }).setNegativeButton("否", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        MerchantActivity.this.loginOut();
      }
    }).show();
  }
  
  public void sync(Context paramContext, SyncService.OnSyncServiceListener paramOnSyncServiceListener)
  {
    SyncService.clearOnSyncListener();
    SyncService.setOnSyncServiceListener(paramOnSyncServiceListener);
    startService(new Intent(paramContext, SyncService.class));
  }
  
  public void takePhoto()
  {
    this.imgPhotoPath = PhotoPopupWindow.takePhoto(this, 2);
  }
  
  public void updateImg(String paramString, int paramInt)
  {
    MerchantManager.uploadImg(new MerchantImgUploadParam(paramString, paramInt), new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        LogUtils.i(paramAnonymousHttpException.getMessage());
        MerchantActivity.this.dismissWaitDialog();
        MerchantActivity.this.mTopBar.getRightButton().setEnabled(true);
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        LogUtils.i((String)paramAnonymousResponseInfo.result);
        paramAnonymousResponseInfo = MerchantManager.getUpLoadImgCUDResult((String)paramAnonymousResponseInfo.result);
        if (paramAnonymousResponseInfo != null) {
          MerchantActivity.this.merchant.setMerchant_Image(paramAnonymousResponseInfo.Data);
        }
        ExecutorUtils.getDBExecutor().execute(new Runnable()
        {
          public void run()
          {
            MerchantActivity.this.save();
          }
        });
        MerchantActivity.this.dismissWaitDialog();
        MerchantActivity.this.mTopBar.getRightButton().setEnabled(true);
      }
    });
  }
}