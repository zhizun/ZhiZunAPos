package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.activity.manager.ProductManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.ProductDao;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.ImgProductParam;
import com.zizun.cs.entities.api.ImgResult;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsStatus;
import com.zizun.cs.ui.entity.Setting;
import com.zhizun.pos.ui.activity.scan.BusinessType;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.dialog.ProductImportAlartDialog;
import com.zhizun.pos.ui.dialog.ProductImportAlartDialog.OnAlartEditListener;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow.OnPhotoPopupWindowClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.TextViewUtil;
import com.zhizun.pos.xujie.NetUtil;
import java.io.File;
import java.util.concurrent.ExecutorService;

public class CreateGoodsActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, PhotoPopupWindow.OnPhotoPopupWindowClickListener
{
  private static final int CROP_IMAGE_REQUEST = 4;
  private static final int PICK_PHOTO_REQUEST = 3;
  private static final int TAKE_PHOTO_REQUEST = 2;
  private final int HANDLER_CHECK_GOODS_BARCODE_EXIST = 3;
  private final int HANDLER_CHECK_GOODS_FINISH = 4;
  private final int HANDLER_CHECK_GOODS_NAME_EXIST = 2;
  private final int HANDLER_CREATE_GOODS_FAILURE = 1;
  private final int HANDLER_CREATE_GOODS_SUCCEED = 0;
  private final int INTENT_REQUEST_CODE_BARCODE = 1;
  private final int INTENT_REQUEST_CODE_GROUP = 0;
  private String imgHeadPath;
  private String imgPhotoPath;
  private EditText mEdtCode;
  private EditText mEdtCostPrice;
  private EditText mEdtCount;
  private EditText mEdtDescribe;
  private EditText mEdtName;
  private EditText mEdtNumber;
  private EditText mEdtSalePrice;
  private EditText mEdtSize;
  private EditText mEdtStockPrice;
  private EditText mEdtUnit;
  private EditText mEdtWolesalePrice;
  private GoodsStatus mGoodsStatus;
  private ProductGroup mGroup;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (TextUtils.isEmpty(CreateGoodsActivity.this.imgHeadPath))
      {
        CreateGoodsActivity.this.dismissWaitDialog();
        CreateGoodsActivity.this.mLinSave.setEnabled(true);
      }
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        super.handleMessage(paramAnonymousMessage);
        return;
        ToastUtil.toastShort(CreateGoodsActivity.this, 2131165311);
        CreateGoodsActivity.this.setResult(-1);
        CreateGoodsActivity.this.finish();
        continue;
        ToastUtil.toastShort(CreateGoodsActivity.this, 2131165312);
        continue;
        CreateGoodsActivity.this.dismissWaitDialog();
        CreateGoodsActivity.this.mLinSave.setEnabled(true);
        ToastUtil.toastLong(CreateGoodsActivity.this, 2131165416);
        continue;
        CreateGoodsActivity.this.dismissWaitDialog();
        CreateGoodsActivity.this.mLinSave.setEnabled(true);
        ToastUtil.toastLong(CreateGoodsActivity.this, 2131165417);
        continue;
        CreateGoodsActivity.this.uploadGoodimg(CreateGoodsActivity.this.mNewGoods);
      }
    }
  };
  private ImageView mImgGoodPicture;
  private Intent mIntent;
  private LinearLayout mLinHideItem;
  private LinearLayout mLinSave;
  private Goods mNewGoods;
  private CustomPopupWindow mPopStatu;
  private TextView mTxtCategroy;
  private TextView mTxtShowMore;
  private TextView mTxtStatus;
  private PhotoPopupWindow photoPopupWindow;
  private String productImg = "";
  
  private Goods getGoods()
  {
    String str1 = getEdtText(this.mEdtName);
    String str2 = getEdtText(this.mEdtCostPrice);
    String str3 = getEdtText(this.mEdtStockPrice);
    String str4 = getEdtText(this.mEdtWolesalePrice);
    String str5 = getEdtText(this.mEdtSalePrice);
    String str6 = getEdtText(this.mEdtCount);
    String str7 = getEdtText(this.mEdtCode);
    if ((isTextNull(str1, 2131165411)) || (isTextNull(str3, 2131165413)) || (isTextNull(str5, 2131165415))) {
      return null;
    }
    Goods localGoods = new Goods();
    localGoods.setSkuId(IDUtil.getID());
    localGoods.setName(str1);
    localGoods.setCode(str7);
    localGoods.setNumber(getEdtText(this.mEdtNumber));
    if (this.mGroup != null) {
      localGoods.setGroup(this.mGroup);
    }
    localGoods.setUnit(getEdtText(this.mEdtUnit));
    localGoods.setSize(getEdtText(this.mEdtSize));
    localGoods.setStockPrice(Double.valueOf(str3).doubleValue());
    localGoods.setSellPrice(Double.valueOf(str5).doubleValue());
    if (!TextUtils.isEmpty(str2))
    {
      localGoods.setCostPrice(Double.valueOf(str2).doubleValue());
      if (TextUtils.isEmpty(str4)) {
        break label309;
      }
      localGoods.setWholesalePrice(Double.valueOf(str4).doubleValue());
      label250:
      if (TextUtils.isEmpty(str6)) {
        break label322;
      }
      localGoods.setAmount(Double.valueOf(str6).doubleValue());
    }
    for (;;)
    {
      localGoods.setStatus(this.mGoodsStatus);
      localGoods.setDescribe(getEdtText(this.mEdtDescribe));
      return localGoods;
      localGoods.setCostPrice(localGoods.getStockPrice());
      break;
      label309:
      localGoods.setWholesalePrice(localGoods.getSellPrice());
      break label250;
      label322:
      localGoods.setAmount(0.0D);
    }
  }
  
  private void getIntentData()
  {
    String str = getIntent().getStringExtra(String.class.getSimpleName());
    if (!TextUtils.isEmpty(str)) {
      this.mEdtCode.setText(str);
    }
  }
  
  private void init()
  {
    getIntentData();
    this.mGoodsStatus = new GoodsStatus();
    this.mGoodsStatus.setId((byte)1);
    if (UserManager.getInstance().getSetting().isProductImportAlat()) {
      showImportAlat();
    }
  }
  
  private boolean isTextNull(String paramString, int paramInt)
  {
    if ((paramString == null) || (paramString.equals("")))
    {
      ToastUtil.toastShort(this, paramInt);
      return true;
    }
    return false;
  }
  
  private void pickStatu()
  {
    ListView localListView = (ListView)getLayoutInflater().inflate(2130903178, null);
    localListView.setBackgroundResource(2130837563);
    localListView.setAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296259)));
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        CreateGoodsActivity.this.mGoodsStatus.setId((byte)(paramAnonymousInt + 1));
        CreateGoodsActivity.this.mTxtStatus.setText(CreateGoodsActivity.this.mGoodsStatus.getName());
        if ((CreateGoodsActivity.this.mPopStatu != null) && (CreateGoodsActivity.this.mPopStatu.isShowing())) {
          CreateGoodsActivity.this.mPopStatu.dismiss();
        }
      }
    });
    this.mPopStatu = new CustomPopupWindow(this, localListView, (int)ResUtil.getDimens(2131099679));
    this.mPopStatu.showMenu(this.mTxtStatus, 0, 1);
  }
  
  private void save()
  {
    this.mLinSave.setEnabled(false);
    showWaitDialog();
    this.mNewGoods = getGoods();
    if (this.mNewGoods != null)
    {
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          Message localMessage = CreateGoodsActivity.this.mHandler.obtainMessage();
          if (ManagerFactory.getProductDao().isProductNameExist(CreateGoodsActivity.this.mNewGoods.getName())) {
            localMessage.what = 2;
          }
          for (;;)
          {
            CreateGoodsActivity.this.mHandler.sendMessage(localMessage);
            return;
            if ((!TextUtils.isEmpty(CreateGoodsActivity.this.mNewGoods.getCode())) && (ManagerFactory.getProductDao().isProductBarCodeExist(CreateGoodsActivity.this.mNewGoods.getCode()))) {
              localMessage.what = 3;
            } else {
              localMessage.what = 4;
            }
          }
        }
      });
      return;
    }
    dismissWaitDialog();
    this.mLinSave.setEnabled(true);
  }
  
  private void showImportAlat()
  {
    ProductImportAlartDialog localProductImportAlartDialog = new ProductImportAlartDialog();
    localProductImportAlartDialog.setOnAlartListener(new ProductImportAlartDialog.OnAlartEditListener()
    {
      public void onAlart()
      {
        UserManager.getInstance().getSetting().setProductImportAlat(false);
        UserManager.getInstance().saveSetting();
      }
      
      public void onConfire()
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://webpos.zhizun.com/"));
        CreateGoodsActivity.this.startActivity(Intent.createChooser(localIntent, ResUtil.getString(2131165325)));
      }
    });
    localProductImportAlartDialog.show(getSupportFragmentManager(), "import_alart");
  }
  
  private void uploadGoodimg(final Goods paramGoods)
  {
    if (NetUtil.checkNet(this))
    {
      if (TextUtils.isEmpty(this.imgHeadPath))
      {
        insertGoods(paramGoods);
        return;
      }
      new Thread()
      {
        public void run()
        {
          String str = ImgUtil.bitmapToString(CreateGoodsActivity.this.imgHeadPath);
          CreateGoodsActivity.this.updateImg(str, UserManager.getInstance().getCurrentUser().getMerchant_ID(), paramGoods.getSkuId(), paramGoods);
          super.run();
        }
      }.start();
      return;
    }
    insertGoods(paramGoods);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903050, paramViewGroup);
    this.mTopBar.setTitle(2131165394);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTxtCategroy = ((TextView)paramViewGroup.findViewById(2131361900));
    this.mTxtStatus = ((TextView)paramViewGroup.findViewById(2131361907));
    this.mEdtName = ((EditText)paramViewGroup.findViewById(2131361890));
    this.mEdtCode = ((EditText)paramViewGroup.findViewById(2131361892));
    this.mEdtNumber = ((EditText)paramViewGroup.findViewById(2131361893));
    this.mEdtUnit = ((EditText)paramViewGroup.findViewById(2131361905));
    this.mEdtSize = ((EditText)paramViewGroup.findViewById(2131361906));
    this.mEdtCostPrice = ((EditText)paramViewGroup.findViewById(2131361903));
    this.mEdtStockPrice = ((EditText)paramViewGroup.findViewById(2131361894));
    this.mEdtWolesalePrice = ((EditText)paramViewGroup.findViewById(2131361904));
    this.mEdtSalePrice = ((EditText)paramViewGroup.findViewById(2131361895));
    this.mEdtDescribe = ((EditText)paramViewGroup.findViewById(2131361908));
    this.mEdtCount = ((EditText)paramViewGroup.findViewById(2131361896));
    this.mTxtShowMore = ((TextView)paramViewGroup.findViewById(2131361901));
    this.mLinHideItem = ((LinearLayout)paramViewGroup.findViewById(2131361902));
    this.mImgGoodPicture = ((ImageView)findViewById(2131361898));
    this.mLinSave = ((LinearLayout)findViewById(2131361909));
    findViewById(2131361891).setOnClickListener(this);
    findViewById(2131361897).setOnClickListener(this);
    this.mLinSave.setOnClickListener(this);
    this.mTxtCategroy.setOnClickListener(this);
    this.mTxtStatus.setOnClickListener(this);
    this.mTxtShowMore.setOnClickListener(this);
    TextViewUtil.addInputLimitTwoDecimal(this.mEdtStockPrice);
    TextViewUtil.addInputLimitTwoDecimal(this.mEdtSalePrice);
    TextViewUtil.addInputLimitTwoDecimal(this.mEdtCostPrice);
    TextViewUtil.addInputLimitTwoDecimal(this.mEdtWolesalePrice);
    init();
  }
  
  public void cancel()
  {
    if ((this.photoPopupWindow != null) && (this.photoPopupWindow.isShowing())) {
      this.photoPopupWindow.dismiss();
    }
  }
  
  public void insertGoods(final Goods paramGoods)
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        boolean bool = ProductManager.createProduct(paramGoods);
        Message localMessage = CreateGoodsActivity.this.mHandler.obtainMessage();
        if (bool) {}
        for (localMessage.what = 0;; localMessage.what = 1)
        {
          CreateGoodsActivity.this.mHandler.sendMessage(localMessage);
          return;
        }
      }
    });
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if (paramInt2 == -1)
      {
        this.mGroup = ((ProductGroup)paramIntent.getExtras().getSerializable(ProductGroup.class.getSimpleName()));
        this.mTxtCategroy.setText(this.mGroup.getPG_Name());
        continue;
        if (paramIntent != null)
        {
          Object localObject = paramIntent.getStringExtra(String.class.getSimpleName());
          this.mEdtCode.setText((CharSequence)localObject);
          continue;
          if (new File(this.imgPhotoPath).exists())
          {
            LogUtils.i("拍照  : " + this.imgPhotoPath);
            localObject = new Intent(this, CropImageActivity.class);
            ((Intent)localObject).putExtra(String.class.getSimpleName(), this.imgPhotoPath);
            startActivityForResult((Intent)localObject, 4);
            continue;
            this.imgPhotoPath = PhotoPopupWindow.getPickPhotoResult(this, paramIntent);
            if (!TextUtils.isEmpty(this.imgPhotoPath))
            {
              LogUtils.i("相册  : " + this.imgPhotoPath);
              localObject = new Intent(this, CropImageActivity.class);
              ((Intent)localObject).putExtra(String.class.getSimpleName(), this.imgPhotoPath);
              startActivityForResult((Intent)localObject, 4);
              continue;
              this.imgHeadPath = PreferencesUtil.getPreference(this).getString("path_image_upload", "");
              LogUtils.i(this.imgHeadPath);
              if (!TextUtils.isEmpty(this.imgHeadPath)) {
                ImgUtil.show(this.mImgGoodPicture, this.imgHeadPath);
              }
            }
          }
        }
      }
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131361891: 
      this.mIntent = new Intent(this, CaptureActivity.class);
      this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.PRODUCT);
      this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
      startActivityForResult(this.mIntent, 1);
      return;
    case 2131361897: 
      if (UserManager.getInstance().isExperienceAccount())
      {
        ToastUtil.toastLong(this, 2131165567);
        return;
      }
      this.photoPopupWindow = PhotoPopupWindow.create(this);
      this.photoPopupWindow.setOnPhotoPopupWindowClickListener(this);
      return;
    case 2131361900: 
      this.mIntent = new Intent(this, GroupActivity.class);
      this.mIntent.putExtra("requestCode", 6);
      startActivityForResult(this.mIntent, 0);
      return;
    case 2131361907: 
      pickStatu();
      return;
    case 2131361909: 
      save();
      return;
    }
    if (this.mLinHideItem.getVisibility() == 8)
    {
      this.mLinHideItem.setVisibility(0);
      this.mTxtShowMore.setText(2131165410);
      return;
    }
    this.mLinHideItem.setVisibility(8);
    this.mTxtShowMore.setText(2131165409);
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void pickPhoto()
  {
    PhotoPopupWindow.pickPhoto(this, 3);
  }
  
  public void takePhoto()
  {
    this.imgPhotoPath = PhotoPopupWindow.takePhoto(this, 2);
  }
  
  public void updateImg(String paramString, int paramInt, long paramLong, final Goods paramGoods)
  {
    ProductManager.updateProductImg(new ImgProductParam(paramInt, paramLong, paramString), new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        CreateGoodsActivity.this.dismissWaitDialog();
        CreateGoodsActivity.this.mLinSave.setEnabled(true);
        LogUtils.i(paramAnonymousHttpException.getMessage());
        ToastUtil.toastShort(CreateGoodsActivity.this, "图片上传失败");
        CreateGoodsActivity.this.productImg = "";
        paramGoods.setPicture(CreateGoodsActivity.this.productImg);
        CreateGoodsActivity.this.insertGoods(paramGoods);
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        CreateGoodsActivity.this.dismissWaitDialog();
        CreateGoodsActivity.this.mLinSave.setEnabled(true);
        LogUtils.i((String)paramAnonymousResponseInfo.result);
        paramAnonymousResponseInfo = ProductManager.getUpLoadImgCUDResult((String)paramAnonymousResponseInfo.result);
        if (paramAnonymousResponseInfo != null) {
          CreateGoodsActivity.this.productImg = paramAnonymousResponseInfo.Data;
        }
        paramGoods.setPicture(CreateGoodsActivity.this.productImg);
        CreateGoodsActivity.this.insertGoods(paramGoods);
      }
    });
  }
}