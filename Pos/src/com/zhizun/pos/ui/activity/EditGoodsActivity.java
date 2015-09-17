package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.ImgProductParam;
import com.zizun.cs.entities.api.ImgResult;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsStatus;
import com.zhizun.pos.ui.activity.purchase.PurchaseActivity;
import com.zhizun.pos.ui.activity.scan.BusinessType;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow;
import com.zhizun.pos.ui.popupwindow.PhotoPopupWindow.OnPhotoPopupWindowClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import com.zhizun.pos.util.TextViewUtil;
import com.zhizun.pos.xujie.NetUtil;
import java.io.File;
import java.util.concurrent.ExecutorService;

public class EditGoodsActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, PhotoPopupWindow.OnPhotoPopupWindowClickListener
{
  private static final int CROP_IMAGE_REQUEST = 5;
  private static final int PICK_PHOTO_REQUEST = 4;
  private static final int TAKE_PHOTO_REQUEST = 3;
  private final int HANDLER_CHECK_GOODS_BARCODE_EXIST = 3;
  private final int HANDLER_CHECK_GOODS_FINISH = 4;
  private final int HANDLER_CHECK_GOODS_NAME_EXIST = 2;
  private final int HANDLER_CREATE_GOODS_FAILURE = 1;
  private final int HANDLER_CREATE_GOODS_SUCCEED = 0;
  private final int INTENT_REQUEST_CODE_BARCODE = 1;
  private final int INTENT_REQUEST_CODE_GROUP = 0;
  private final int INTENT_REQUEST_CODE_STOCK = 2;
  private String imgHeadPath;
  private String imgPhotoPath;
  private boolean isImgChange = false;
  private EditText mEdtAmount;
  private EditText mEdtCode;
  private EditText mEdtCostPrice;
  private EditText mEdtDescribe;
  private EditText mEdtName;
  private EditText mEdtNumber;
  private EditText mEdtSalePrice;
  private EditText mEdtSize;
  private EditText mEdtStockPrice;
  private EditText mEdtUnit;
  private EditText mEdtWolesalePrice;
  private ImageView mGoodPicture;
  private Goods mGoods;
  private GoodsStatus mGoodsNewStatus;
  private ProductGroup mGroup;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (TextUtils.isEmpty(EditGoodsActivity.this.imgHeadPath))
      {
        EditGoodsActivity.this.dismissWaitDialog();
        EditGoodsActivity.this.mLinSave.setEnabled(true);
      }
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        super.handleMessage(paramAnonymousMessage);
        return;
        ToastUtil.toastLong(EditGoodsActivity.this, 2131165313);
        EditGoodsActivity.this.setResult(-1);
        EditGoodsActivity.this.finish();
        continue;
        ToastUtil.toastLong(EditGoodsActivity.this, 2131165314);
        continue;
        EditGoodsActivity.this.dismissWaitDialog();
        EditGoodsActivity.this.mLinSave.setEnabled(true);
        ToastUtil.toastLong(EditGoodsActivity.this, 2131165416);
        continue;
        EditGoodsActivity.this.dismissWaitDialog();
        EditGoodsActivity.this.mLinSave.setEnabled(true);
        ToastUtil.toastLong(EditGoodsActivity.this, 2131165417);
        continue;
        EditGoodsActivity.this.uploadGoodimg();
      }
    }
  };
  private Intent mIntent;
  private LinearLayout mLinHideItem;
  private LinearLayout mLinSave;
  private CustomPopupWindow mPopStatu;
  private TextView mTxtCategroy;
  private TextView mTxtShowMore;
  private TextView mTxtStatus;
  private PhotoPopupWindow photoPopupWindow;
  private String productImg = "";
  
  private void init()
  {
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null) {
      this.mGoods = ((Goods)localBundle.getSerializable(Goods.class.getSimpleName()));
    }
    this.mGroup = this.mGoods.getGroup();
    this.mGoodsNewStatus = new GoodsStatus();
    this.mGoodsNewStatus.setId(this.mGoods.getStatus().getId());
    if (this.mGoods != null)
    {
      this.mEdtName.setText(this.mGoods.getName());
      this.mEdtCode.setText(this.mGoods.getCode());
      this.mEdtNumber.setText(this.mGoods.getNumber());
      this.mTxtCategroy.setText(this.mGoods.getGroup().getPG_Name());
      this.mEdtAmount.setText(DataUtil.getFormatString(this.mGoods.getAmount()));
      this.mEdtUnit.setText(this.mGoods.getUnit());
      this.mEdtSize.setText(this.mGoods.getSize());
      this.mEdtCostPrice.setText(DataUtil.getFormatString(this.mGoods.getCostPrice()));
      this.mEdtStockPrice.setText(DataUtil.getFormatString(this.mGoods.getStockPrice()));
      this.mEdtWolesalePrice.setText(DataUtil.getFormatString(this.mGoods.getWholesalePrice()));
      this.mEdtSalePrice.setText(DataUtil.getFormatString(this.mGoods.getSellPrice()));
      this.mTxtStatus.setText(this.mGoods.getStatus().getName());
      this.mEdtDescribe.setText(this.mGoods.getDescribe());
      this.productImg = this.mGoods.getPicture();
      ImgUtil.showImg(this.mGoodPicture, this.productImg, 2130837655, 3);
    }
  }
  
  private void insertGoods()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        boolean bool = ProductManager.updateProduct(EditGoodsActivity.this.mGoods);
        Message localMessage = EditGoodsActivity.this.mHandler.obtainMessage();
        if (bool) {}
        for (localMessage.what = 0;; localMessage.what = 1)
        {
          EditGoodsActivity.this.mHandler.sendMessage(localMessage);
          return;
        }
      }
    });
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
  
  private void loadProductImg(View paramView, String paramString)
  {
    ImgUtil.show(paramView, ImgUtil.getImgPathFromSev(paramString));
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
        EditGoodsActivity.this.mGoodsNewStatus.setId((byte)(paramAnonymousInt + 1));
        EditGoodsActivity.this.mTxtStatus.setText(EditGoodsActivity.this.mGoodsNewStatus.getName());
        if ((EditGoodsActivity.this.mPopStatu != null) && (EditGoodsActivity.this.mPopStatu.isShowing())) {
          EditGoodsActivity.this.mPopStatu.dismiss();
        }
      }
    });
    this.mPopStatu = new CustomPopupWindow(this, localListView, (int)ResUtil.getDimens(2131099679));
    this.mPopStatu.showMenu(this.mTxtStatus, 0, 1);
  }
  
  private void save()
  {
    if (updateGoods())
    {
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          String str1 = EditGoodsActivity.this.getEdtText(EditGoodsActivity.this.mEdtName);
          String str2 = EditGoodsActivity.this.getEdtText(EditGoodsActivity.this.mEdtCode);
          Message localMessage = EditGoodsActivity.this.mHandler.obtainMessage();
          if ((!str1.equals(EditGoodsActivity.this.mGoods.getName())) && (ManagerFactory.getProductDao().isProductNameExist(str1))) {
            localMessage.what = 2;
          }
          for (;;)
          {
            EditGoodsActivity.this.mHandler.sendMessage(localMessage);
            return;
            if ((!TextUtils.isEmpty(str2)) && (!str2.equals(EditGoodsActivity.this.mGoods.getCode())) && (ManagerFactory.getProductDao().isProductBarCodeExist(str2)))
            {
              localMessage.what = 3;
            }
            else
            {
              EditGoodsActivity.this.mGoods.setName(str1);
              EditGoodsActivity.this.mGoods.setCode(str2);
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
  
  private boolean updateGoods()
  {
    String str1 = getEdtText(this.mEdtName);
    String str2 = getEdtText(this.mEdtCostPrice);
    String str3 = getEdtText(this.mEdtStockPrice);
    String str4 = getEdtText(this.mEdtWolesalePrice);
    String str5 = getEdtText(this.mEdtSalePrice);
    String str6 = getEdtText(this.mEdtAmount);
    if ((isTextNull(str1, 2131165411)) || (isTextNull(str5, 2131165415)) || (isTextNull(str3, 2131165413))) {
      return false;
    }
    this.mGoods.setNumber(getEdtText(this.mEdtNumber));
    this.mGoods.setGroup(this.mGroup);
    this.mGoods.setUnit(getEdtText(this.mEdtUnit));
    this.mGoods.setSize(getEdtText(this.mEdtSize));
    if (!TextUtils.isEmpty(str2))
    {
      this.mGoods.setNewCostPrice(NumUtil.getPrice(str2));
      if (TextUtils.isEmpty(str4)) {
        break label275;
      }
      this.mGoods.setNewWholesalePrice(NumUtil.getPrice(str4));
    }
    for (;;)
    {
      this.mGoods.setNewSellPrice(NumUtil.getPrice(str5));
      this.mGoods.setNewStockPrice(NumUtil.getPrice(str3));
      this.mGoods.setNewStatus(this.mGoodsNewStatus);
      this.mGoods.setNewAmount(Double.valueOf(str6).doubleValue());
      this.mGoods.setDescribe(getEdtText(this.mEdtDescribe));
      this.mGoods.setPicture(this.productImg);
      return true;
      this.mGoods.setNewCostPrice(0.0D);
      break;
      label275:
      this.mGoods.setNewWholesalePrice(0.0D);
    }
  }
  
  private void uploadGoodimg()
  {
    showWaitDialog();
    this.mLinSave.setEnabled(false);
    if (NetUtil.checkNet(this))
    {
      if (TextUtils.isEmpty(this.imgHeadPath))
      {
        insertGoods();
        return;
      }
      new Thread()
      {
        public void run()
        {
          String str = ImgUtil.bitmapToString(EditGoodsActivity.this.imgHeadPath);
          EditGoodsActivity.this.updateImg(str, UserManager.getInstance().getCurrentUser().getMerchant_ID(), EditGoodsActivity.this.mGoods.getSkuId());
          super.run();
        }
      }.start();
      return;
    }
    insertGoods();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903054, paramViewGroup);
    this.mTopBar.setTitle(2131165395);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTxtCategroy = ((TextView)paramViewGroup.findViewById(2131361943));
    this.mEdtAmount = ((EditText)paramViewGroup.findViewById(2131361940));
    this.mTxtStatus = ((TextView)paramViewGroup.findViewById(2131361950));
    this.mEdtName = ((EditText)paramViewGroup.findViewById(2131361934));
    this.mEdtCode = ((EditText)paramViewGroup.findViewById(2131361936));
    this.mEdtNumber = ((EditText)paramViewGroup.findViewById(2131361937));
    this.mEdtUnit = ((EditText)paramViewGroup.findViewById(2131361948));
    this.mEdtSize = ((EditText)paramViewGroup.findViewById(2131361949));
    this.mEdtCostPrice = ((EditText)paramViewGroup.findViewById(2131361946));
    this.mEdtStockPrice = ((EditText)paramViewGroup.findViewById(2131361938));
    this.mEdtWolesalePrice = ((EditText)paramViewGroup.findViewById(2131361947));
    this.mEdtSalePrice = ((EditText)paramViewGroup.findViewById(2131361939));
    this.mEdtDescribe = ((EditText)paramViewGroup.findViewById(2131361951));
    this.mTxtShowMore = ((TextView)paramViewGroup.findViewById(2131361944));
    this.mLinHideItem = ((LinearLayout)paramViewGroup.findViewById(2131361945));
    this.mGoodPicture = ((ImageView)findViewById(2131361942));
    this.mLinSave = ((LinearLayout)findViewById(2131361952));
    findViewById(2131361935).setOnClickListener(this);
    findViewById(2131361941).setOnClickListener(this);
    this.mLinSave.setOnClickListener(this);
    this.mTxtCategroy.setOnClickListener(this);
    this.mEdtAmount.setOnClickListener(this);
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
            startActivityForResult((Intent)localObject, 5);
            continue;
            this.imgPhotoPath = PhotoPopupWindow.getPickPhotoResult(this, paramIntent);
            if (!TextUtils.isEmpty(this.imgPhotoPath))
            {
              LogUtils.i("相册  : " + this.imgPhotoPath);
              localObject = new Intent(this, CropImageActivity.class);
              ((Intent)localObject).putExtra(String.class.getSimpleName(), this.imgPhotoPath);
              startActivityForResult((Intent)localObject, 5);
              continue;
              this.imgHeadPath = PreferencesUtil.getPreference(this).getString("path_image_upload", "");
              LogUtils.i(this.imgHeadPath);
              if (!TextUtils.isEmpty(this.imgHeadPath))
              {
                ImgUtil.show(this.mGoodPicture, this.imgHeadPath);
                this.isImgChange = true;
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
    case 2131361935: 
      this.mIntent = new Intent(this, CaptureActivity.class);
      this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.PRODUCT);
      this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
      startActivityForResult(this.mIntent, 1);
      return;
    case 2131361941: 
      if (UserManager.getInstance().isExperienceAccount())
      {
        ToastUtil.toastLong(this, 2131165567);
        return;
      }
      this.photoPopupWindow = PhotoPopupWindow.create(this);
      this.photoPopupWindow.setOnPhotoPopupWindowClickListener(this);
      return;
    case 2131361940: 
      this.mIntent = new Intent(this, PurchaseActivity.class);
      startActivityForResult(this.mIntent, 2);
      return;
    case 2131361943: 
      this.mIntent = new Intent(this, GroupActivity.class);
      this.mIntent.putExtra("requestCode", 6);
      startActivityForResult(this.mIntent, 0);
      return;
    case 2131361950: 
      pickStatu();
      return;
    case 2131361952: 
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
    PhotoPopupWindow.pickPhoto(this, 4);
  }
  
  public void takePhoto()
  {
    this.imgPhotoPath = PhotoPopupWindow.takePhoto(this, 3);
  }
  
  public void updateImg(String paramString, int paramInt, long paramLong)
  {
    ProductManager.updateProductImg(new ImgProductParam(paramInt, paramLong, paramString), new RequestCallBack()
    {
      public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
      {
        EditGoodsActivity.this.dismissWaitDialog();
        EditGoodsActivity.this.mLinSave.setEnabled(true);
        LogUtils.i(paramAnonymousHttpException.getMessage());
        ToastUtil.toastShort(EditGoodsActivity.this, "图片上传失败");
        EditGoodsActivity.this.productImg = EditGoodsActivity.this.mGoods.getPicture();
        EditGoodsActivity.this.insertGoods();
      }
      
      public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
      {
        EditGoodsActivity.this.dismissWaitDialog();
        EditGoodsActivity.this.mLinSave.setEnabled(true);
        LogUtils.i((String)paramAnonymousResponseInfo.result);
        paramAnonymousResponseInfo = ProductManager.getUpLoadImgCUDResult((String)paramAnonymousResponseInfo.result);
        if (paramAnonymousResponseInfo != null) {
          EditGoodsActivity.this.productImg = paramAnonymousResponseInfo.Data;
        }
        EditGoodsActivity.this.mGoods.setPicture(EditGoodsActivity.this.productImg);
        EditGoodsActivity.this.insertGoods();
      }
    });
  }
}