package com.zhizun.pos.ui.activity.scan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.ProductManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.action.sales.SaveSalesDocumentAction;
import com.zizun.cs.biz.action.sales.SaveSalesDocumentAction.OnSaveSalesDocumentActionListener;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.biz.sale.SalesShoppingCart;
import com.zizun.cs.biz.sale.V_ProductSkuOnSale;
import com.zizun.cs.biz.sale.V_ProductSkuOnSaleDaoImpl;
import com.zizun.cs.biz.scan.CameraManager;
import com.zizun.cs.biz.scan.InactivityTimer;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.ui.activity.BaseTitleTopBarActivity;
import com.zhizun.pos.ui.activity.CreateGoodsActivity;
import com.zhizun.pos.ui.activity.EditGoodsActivity;
import com.zhizun.pos.ui.activity.MainActivity;
import com.zhizun.pos.ui.activity.StorageActivity;
import com.zhizun.pos.ui.activity.invent.CheckActivity;
import com.zhizun.pos.ui.activity.purchase.PurchaseCart;
import com.zhizun.pos.ui.activity.purchase.PurchaseCartActivity;
import com.zhizun.pos.ui.activity.purchase.PurchasesManager;
import com.zhizun.pos.ui.activity.receipt.ReceiptPreViewActivity;
import com.zhizun.pos.ui.activity.sales.SalesShoppingCartActivity;
import com.zhizun.pos.ui.adapter.capture.CaptureAdapter;
import com.zhizun.pos.ui.adapter.capture.CaptureScanAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarBehindLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarFrontRightClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class CaptureActivity
  extends BaseTitleTopBarActivity
  implements SurfaceHolder.Callback, BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, BaseTopBar.OnTopBarFrontRightClickListener, CaptureActivityHandler.OnDecodeStateChangeListener, BaseTopBar.OnTopBarBehindLeftClickListener, View.OnClickListener, SaveSalesDocumentAction.OnSaveSalesDocumentActionListener
{
  private static final int GET_CHECK_PRODUCT_BY_BARCODE = 23;
  private static final int GET_CREATE_PRODUCT_BY_BARCODE = 22;
  private static final int GET_DEFULT_PAYMENT_METHOD_COMPLETE = 18;
  private static final int GET_PURCHASE_PRODUCT_BY_BARCODE = 20;
  private static final int GET_SALES_PRODUCT_BY_BARCODE = 19;
  private static final int GET_SEARCH_PRODUCT_BY_BARCODE = 21;
  private static final int REQUEST_CREATE_PRODUCT = 1;
  private static final int SALES_DOCUMENT_SAVE_SUCCESS = 17;
  private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener()
  {
    public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
    {
      paramAnonymousMediaPlayer.seekTo(0);
    }
  };
  private Map<String, Object> bottomMenuMap = new HashMap();
  private List<Map<String, Object>> bottomMenuMapList = new ArrayList();
  private BusinessType businessType;
  private CaptureActivityHandler captureActivityHandler;
  private Context context;
  private int cropHeight = 0;
  private int cropWidth = 0;
  private SharedPreferences.Editor editor = null;
  private GridView gv_capt = null;
  Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        long l = ((Long)paramAnonymousMessage.obj).longValue();
        CaptureActivity.this.pay(l);
        CaptureActivity.this.finish();
        continue;
        CaptureActivity.this.mBtnSure.setEnabled(true);
        CaptureActivity.this.clearViewData();
        CaptureActivity.this.sendWXReceipt();
        continue;
        paramAnonymousMessage = (V_ProductSkuOnSale)paramAnonymousMessage.obj;
        CaptureActivity.this.scanForAddSalesProduct(paramAnonymousMessage);
        continue;
        paramAnonymousMessage = (PurchaseGoods)paramAnonymousMessage.obj;
        CaptureActivity.this.scanForAddPurchaseProduct(paramAnonymousMessage);
        continue;
        paramAnonymousMessage = (Goods)paramAnonymousMessage.obj;
        CaptureActivity.this.scanForSearchProduct(paramAnonymousMessage);
        continue;
        paramAnonymousMessage = (Goods)paramAnonymousMessage.obj;
        CaptureActivity.this.scanForCreateProduct(paramAnonymousMessage);
      }
    }
  });
  private boolean hasSurface = false;
  private InactivityTimer inactivityTimer;
  private boolean isMain = false;
  private boolean isNeedCapture = false;
  private boolean isNeedLight = true;
  private LinearLayout ll_scan;
  private Button mBtnSure;
  private RelativeLayout mContainer = null;
  private RelativeLayout mCropLayout = null;
  private Intent mIntent = null;
  private ImageView mQrLineView = null;
  private MediaPlayer mediaPlayer;
  private CaptureAdapter menuAdapter;
  private boolean playBeep;
  private SharedPreferences preferences = null;
  private String result = "";
  private V_ProductSkuOnSaleDaoImpl salesDaoImpl = null;
  private SalesType salesType;
  private CaptureScanAdapter setAdapter;
  private long transID = -1L;
  private boolean vibrate;
  private CustomPopupWindow window = null;
  private int x = 0;
  private int y = 0;
  
  private void clearViewData() {}
  
  private void finishScanProduct()
  {
    switch (this.businessType)
    {
    default: 
      return;
    case PRODUCT: 
      this.mIntent = new Intent(this.context, SalesShoppingCartActivity.class);
      startActivity(this.mIntent);
      return;
    }
    this.mIntent = new Intent(this.context, PurchaseCartActivity.class);
    startActivity(this.mIntent);
  }
  
  private void getActivityIntent()
  {
    try
    {
      Intent localIntent = getIntent();
      BusinessType localBusinessType = (BusinessType)localIntent.getExtras().getSerializable(BusinessType.class.getSimpleName());
      if (localBusinessType != null) {
        this.businessType = localBusinessType;
      }
      this.isMain = localIntent.getBooleanExtra(MainActivity.class.getSimpleName(), false);
      return;
    }
    catch (Exception localException) {}
  }
  
  private void getPaymentMethod()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Object localObject = SalesManager.getInstance().getPaymentMethodDao();
        localObject = (PaymentMethod)SalesManager.getInstance().getPaymentMethodListOutUIThread((PaymentMethodDao)localObject).get(0);
        CaptureActivity.this.handler.obtainMessage(18, Long.valueOf(((PaymentMethod)localObject).getPaymentMethod_ID())).sendToTarget();
      }
    });
  }
  
  private boolean hasShoppingCartItem()
  {
    ArrayList localArrayList = SalesManager.getInstance().getSalesShoppingCart().getSaleShoppingCartItemList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
    {
      ToastUtil.toastLong(this.context, "请先选择商品");
      return false;
    }
    return true;
  }
  
  private void initActivityView()
  {
    this.mContainer = ((RelativeLayout)findViewById(2131362347));
    this.inactivityTimer = new InactivityTimer(this);
    this.mQrLineView = ((ImageView)findViewById(2131362353));
    this.mCropLayout = ((RelativeLayout)findViewById(2131362351));
    this.gv_capt = ((GridView)findViewById(2131362350));
    this.mBtnSure = ((Button)findViewById(2131362354));
    this.mBtnSure.setOnClickListener(this);
    this.ll_scan = ((LinearLayout)findViewById(2131362349));
  }
  
  private void initBeepSound()
  {
    AssetFileDescriptor localAssetFileDescriptor;
    if ((this.playBeep) && (this.mediaPlayer == null))
    {
      setVolumeControlStream(3);
      this.mediaPlayer = new MediaPlayer();
      this.mediaPlayer.setAudioStreamType(3);
      this.mediaPlayer.setOnCompletionListener(this.beepListener);
      localAssetFileDescriptor = getResources().openRawResourceFd(2131034112);
    }
    try
    {
      this.mediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      localAssetFileDescriptor.close();
      this.mediaPlayer.setVolume(0.5F, 0.5F);
      this.mediaPlayer.prepare();
      return;
    }
    catch (IOException localIOException)
    {
      this.mediaPlayer = null;
    }
  }
  
  private void initBottomMenuData()
  {
    this.bottomMenuMapList.clear();
    this.bottomMenuMap = new HashMap();
    this.bottomMenuMap.put("tv", "卖货");
    this.bottomMenuMap.put("iv", Integer.valueOf(2130837742));
    this.bottomMenuMap.put("className", "com.mcs.store.PurchStock");
    this.bottomMenuMapList.add(this.bottomMenuMap);
    this.bottomMenuMap = new HashMap();
    this.bottomMenuMap.put("tv", "进货");
    this.bottomMenuMap.put("iv", Integer.valueOf(2130837739));
    this.bottomMenuMap.put("className", "com.mcs.store.SalesMarket");
    this.bottomMenuMapList.add(this.bottomMenuMap);
    this.bottomMenuMap = new HashMap();
    this.bottomMenuMap.put("tv", "商品");
    this.bottomMenuMap.put("iv", Integer.valueOf(2130837741));
    this.bottomMenuMap.put("className", StorageActivity.class.getName());
    this.bottomMenuMapList.add(this.bottomMenuMap);
    this.bottomMenuMap = new HashMap();
    this.bottomMenuMap.put("tv", "盘点");
    this.bottomMenuMap.put("iv", Integer.valueOf(2130837740));
    this.bottomMenuMap.put("className", "com.mcs.store.TakeStock");
    this.bottomMenuMapList.add(this.bottomMenuMap);
  }
  
  private void initBussnessTypeBySet()
  {
    setBussnessType(this.preferences.getInt("capturescanset", 0));
  }
  
  private void initCamera(SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      CameraManager.get().openDriver(paramSurfaceHolder);
      paramSurfaceHolder = CameraManager.get().getCameraResolution();
      int m = paramSurfaceHolder.y;
      int k = paramSurfaceHolder.x;
      int i = this.mCropLayout.getLeft() * m / this.mContainer.getWidth();
      int j = this.mCropLayout.getTop() * k / this.mContainer.getHeight();
      m = this.mCropLayout.getWidth() * m / this.mContainer.getWidth();
      k = this.mCropLayout.getHeight() * k / this.mContainer.getHeight();
      setX(i);
      setY(j);
      setCropWidth(m);
      setCropHeight(k);
      setNeedCapture(true);
      if (this.captureActivityHandler == null)
      {
        this.captureActivityHandler = new CaptureActivityHandler(this);
        this.captureActivityHandler.setDecodeStateChangeListener(this);
      }
      return;
    }
    catch (IOException paramSurfaceHolder)
    {
      paramSurfaceHolder.printStackTrace();
      return;
    }
    catch (RuntimeException paramSurfaceHolder)
    {
      paramSurfaceHolder.printStackTrace();
    }
  }
  
  private void initCameraManager()
  {
    this.context = this;
    this.preferences = PreferencesUtil.getPreference(this.context);
    this.editor = this.preferences.edit();
    CameraManager.init(getApplication());
  }
  
  private void initPopupWindow()
  {
    View localView = LayoutInflater.from(this.context).inflate(2130903150, null);
    localView.setBackgroundColor(Color.argb(200, 0, 0, 0));
    ListView localListView = (ListView)localView.findViewById(2131362465);
    this.setAdapter = new CaptureScanAdapter(this.context);
    localListView.setAdapter(this.setAdapter);
    localListView.setOnItemClickListener(new MySetClick(null));
    this.window = new CustomPopupWindow(this.context, localView);
    this.window.setWidth(300);
    this.window.setAnimationStyle(2131230758);
    this.window.showMenu(this.mTopBar, 0, 30);
  }
  
  private void initScanAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0, 0.0F, 0, 0.0F, 2, 0.0F, 2, 0.9F);
    localTranslateAnimation.setDuration(3000L);
    localTranslateAnimation.setRepeatCount(-1);
    localTranslateAnimation.setRepeatMode(1);
    localTranslateAnimation.setInterpolator(new LinearInterpolator());
    this.mQrLineView.setAnimation(localTranslateAnimation);
  }
  
  private void initTitle()
  {
    this.mTopBar.setTitle(2131165342);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setBehindLeftButton(2130837748, this);
    this.mTopBar.setFrontRightButton(2130837604, this);
  }
  
  private void pay(long paramLong)
  {
    SaveSalesDocumentAction localSaveSalesDocumentAction = new SaveSalesDocumentAction(SalesManager.getInstance().getSalesShoppingCart(), paramLong);
    localSaveSalesDocumentAction.setOnSaveSalesDocumentActionListener(this);
    localSaveSalesDocumentAction.execute();
  }
  
  private void playBeepSoundAndVibrate()
  {
    if ((this.playBeep) && (this.mediaPlayer != null)) {
      this.mediaPlayer.start();
    }
    if (this.vibrate) {
      ((Vibrator)getSystemService("vibrator")).vibrate(200L);
    }
  }
  
  private void receivePay()
  {
    switch (this.businessType)
    {
    default: 
    case PRODUCT: 
      do
      {
        return;
      } while (!hasShoppingCartItem());
      this.mBtnSure.setEnabled(false);
      getPaymentMethod();
      return;
    }
    this.mIntent = new Intent(this.context, PurchaseCartActivity.class);
    startActivity(this.mIntent);
  }
  
  private void registerListener()
  {
    this.gv_capt.setOnItemClickListener(new MyItemClick(null));
  }
  
  private void scanForAddPurchaseProduct()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        PurchaseGoods localPurchaseGoods = new PurchasesManager().getPurchaseGoodByBarcode(CaptureActivity.this.result);
        CaptureActivity.this.handler.obtainMessage(20, localPurchaseGoods).sendToTarget();
      }
    });
  }
  
  private void scanForAddPurchaseProduct(PurchaseGoods paramPurchaseGoods)
  {
    PurchaseCart localPurchaseCart2 = PurchasesManager.getPurchaseCart();
    PurchaseCart localPurchaseCart1 = localPurchaseCart2;
    if (localPurchaseCart2 == null)
    {
      localPurchaseCart1 = new PurchaseCart();
      PurchasesManager.setPurchaseCart(localPurchaseCart1);
    }
    if (paramPurchaseGoods != null)
    {
      localPurchaseCart1.addPurchaseGoods(paramPurchaseGoods);
      ToastUtil.toastShort(this.context, paramPurchaseGoods.getProductSku_Name() + " + 1");
      this.captureActivityHandler.sendEmptyMessageDelayed(2131361802, 2000L);
      return;
    }
    this.mIntent = new Intent(this.context, CreateGoodsActivity.class);
    this.mIntent.putExtra(String.class.getSimpleName(), this.result);
    startActivityForResult(this.mIntent, 1);
  }
  
  private void scanForAddSalesProduct()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        if (CaptureActivity.this.salesDaoImpl == null) {
          CaptureActivity.this.salesDaoImpl = new V_ProductSkuOnSaleDaoImpl();
        }
        V_ProductSkuOnSale localV_ProductSkuOnSale = CaptureActivity.this.salesDaoImpl.getProductOnSaleByProductBarcode(CaptureActivity.this.result);
        CaptureActivity.this.handler.obtainMessage(19, localV_ProductSkuOnSale).sendToTarget();
      }
    });
  }
  
  private void scanForAddSalesProduct(V_ProductSkuOnSale paramV_ProductSkuOnSale)
  {
    SalesShoppingCart localSalesShoppingCart = SalesManager.getInstance().getSalesShoppingCart();
    if (localSalesShoppingCart == null)
    {
      this.salesType = UserManager.getInstance().getCurrentSalesType();
      localSalesShoppingCart = new SalesShoppingCart(this.salesType);
      SalesManager.setSalesShoppingCart(localSalesShoppingCart);
    }
    while (paramV_ProductSkuOnSale != null)
    {
      paramV_ProductSkuOnSale.initShoppingCartItem(this.salesType);
      localSalesShoppingCart.addShoppingCartItem(paramV_ProductSkuOnSale);
      ToastUtil.toastShort(this.context, paramV_ProductSkuOnSale.getProductSku_Name() + " + 1");
      this.captureActivityHandler.sendEmptyMessageDelayed(2131361802, 2000L);
      return;
      this.salesType = SalesManager.getInstance().getSalesShoppingCart().getSalesType();
    }
    this.mIntent = new Intent(this.context, CreateGoodsActivity.class);
    this.mIntent.putExtra(String.class.getSimpleName(), this.result);
    startActivityForResult(this.mIntent, 1);
  }
  
  private void scanForCheckProduct()
  {
    this.mIntent = new Intent(this, CheckActivity.class);
    this.mIntent.putExtra(String.class.getSimpleName(), this.result);
    if (this.isMain) {
      startActivity(this.mIntent);
    }
    for (;;)
    {
      finish();
      return;
      setResult(-1, this.mIntent);
    }
  }
  
  private void scanForCreateProduct()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Goods localGoods = ProductManager.getSingleGood(CaptureActivity.this.result);
        CaptureActivity.this.handler.obtainMessage(22, localGoods).sendToTarget();
      }
    });
  }
  
  private void scanForCreateProduct(Goods paramGoods)
  {
    if (paramGoods == null)
    {
      this.mIntent = new Intent(this.context, CreateGoodsActivity.class);
      this.mIntent.putExtra(String.class.getSimpleName(), this.result);
      if (this.isMain) {
        startActivity(this.mIntent);
      }
    }
    for (;;)
    {
      finish();
      return;
      setResult(-1, this.mIntent);
      continue;
      this.mIntent = new Intent(this.context, EditGoodsActivity.class);
      this.mIntent.putExtra(String.class.getSimpleName(), this.result);
      this.mIntent.putExtra(Goods.class.getSimpleName(), paramGoods);
      if (this.isMain) {
        startActivity(this.mIntent);
      } else {
        setResult(-1, this.mIntent);
      }
    }
  }
  
  private void scanForSearchProduct()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Goods localGoods = ProductManager.getSingleGood(CaptureActivity.this.result);
        CaptureActivity.this.handler.obtainMessage(21, localGoods).sendToTarget();
      }
    });
  }
  
  private void scanForSearchProduct(Goods paramGoods)
  {
    if (paramGoods == null)
    {
      this.mIntent = new Intent(this.context, CreateGoodsActivity.class);
      this.mIntent.putExtra(String.class.getSimpleName(), this.result);
      startActivityForResult(this.mIntent, 0);
    }
    for (;;)
    {
      finish();
      return;
      this.mIntent = new Intent();
      this.mIntent.putExtra(String.class.getSimpleName(), this.result);
      setResult(-1, this.mIntent);
    }
  }
  
  private void sendWXReceipt()
  {
    if (this.transID > 0L)
    {
      this.mIntent = new Intent(this.context, ReceiptPreViewActivity.class);
      this.mIntent.putExtra(Long.class.getSimpleName(), this.transID);
      startActivity(this.mIntent);
      this.transID = -1L;
    }
  }
  
  private void setActivityView()
  {
    if (this.isMain)
    {
      int i = this.preferences.getInt("capturescanset", 0);
      this.menuAdapter = new CaptureAdapter(this.context, this.bottomMenuMapList, i);
      this.gv_capt.setAdapter(this.menuAdapter);
      initBussnessTypeBySet();
      return;
    }
    setBussnessType(this.businessType.getValue());
  }
  
  private void setBussnessType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 0: 
      this.businessType = BusinessType.SALL;
      this.mTopBar.showLeft();
      this.mTopBar.showRight();
      if ((this.ll_scan != null) && (this.ll_scan.getVisibility() == 8)) {
        this.ll_scan.setVisibility(0);
      }
      this.mTopBar.getBehindLeftButton().setVisibility(0);
      if ((this.mTopBar.getRightButton() != null) && (this.mTopBar.getRightButton().getVisibility() == 8)) {
        this.mTopBar.getRightButton().setVisibility(0);
      }
      if ((this.mTopBar.getFrontRightButton() != null) && (this.mTopBar.getFrontRightButton().getVisibility() == 8)) {
        this.mTopBar.getFrontRightButton().setVisibility(0);
      }
      if (this.isMain)
      {
        initBottomMenuData();
        return;
      }
      this.mTopBar.getBehindLeftButton().setVisibility(8);
      return;
    case 1: 
      this.businessType = BusinessType.PURCHASE;
      this.mTopBar.showLeft();
      this.mTopBar.showRight();
      if ((this.ll_scan != null) && (this.ll_scan.getVisibility() == 8)) {
        this.ll_scan.setVisibility(0);
      }
      this.mTopBar.getBehindLeftButton().setVisibility(0);
      if ((this.mTopBar.getRightButton() != null) && (this.mTopBar.getRightButton().getVisibility() == 8)) {
        this.mTopBar.getRightButton().setVisibility(0);
      }
      if ((this.mTopBar.getFrontRightButton() != null) && (this.mTopBar.getFrontRightButton().getVisibility() == 8)) {
        this.mTopBar.getFrontRightButton().setVisibility(0);
      }
      if (this.isMain)
      {
        initBottomMenuData();
        return;
      }
      this.mTopBar.getBehindLeftButton().setVisibility(8);
      return;
    case 2: 
      this.businessType = BusinessType.PRODUCT;
      if (this.isMain)
      {
        if ((this.ll_scan != null) && (this.ll_scan.getVisibility() == 8)) {
          this.ll_scan.setVisibility(0);
        }
        this.mTopBar.getRightButton().setVisibility(8);
        this.mTopBar.getBehindLeftButton().setVisibility(8);
        this.mTopBar.getFrontRightButton().setVisibility(8);
        initBottomMenuData();
        return;
      }
      this.mTopBar.dismisLeft();
      this.mTopBar.getBehindLeftButton().setVisibility(8);
      this.mTopBar.dismisRight();
      this.mTopBar.getFrontRightButton().setVisibility(8);
      return;
    }
    this.businessType = BusinessType.CHECK;
    if (this.isMain)
    {
      if ((this.ll_scan != null) && (this.ll_scan.getVisibility() == 8)) {
        this.ll_scan.setVisibility(0);
      }
      this.mTopBar.getRightButton().setVisibility(8);
      this.mTopBar.getBehindLeftButton().setVisibility(8);
      this.mTopBar.getFrontRightButton().setVisibility(8);
      initBottomMenuData();
      return;
    }
    this.mTopBar.dismisLeft();
    this.mTopBar.getBehindLeftButton().setVisibility(8);
    this.mTopBar.dismisRight();
    this.mTopBar.getFrontRightButton().setVisibility(8);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903120, paramViewGroup);
    initTitle();
    getActivityIntent();
    initCameraManager();
    initActivityView();
    setActivityView();
    initScanAnimation();
    registerListener();
  }
  
  public int getCropHeight()
  {
    return this.cropHeight;
  }
  
  public int getCropWidth()
  {
    return this.cropWidth;
  }
  
  public Handler getHandler()
  {
    return this.captureActivityHandler;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public boolean isNeedCapture()
  {
    return this.isNeedCapture;
  }
  
  protected void light()
  {
    if (this.isNeedLight)
    {
      this.isNeedLight = false;
      CameraManager.get().openLight();
      return;
    }
    this.isNeedLight = true;
    CameraManager.get().offLight();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1) {}
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    finishScanProduct();
  }
  
  public void onDecodeStateChange(CaptureActivityHandler.DecodeState paramDecodeState, String paramString)
  {
    switch (paramDecodeState)
    {
    }
    do
    {
      return;
      this.inactivityTimer.onActivity();
      playBeepSoundAndVibrate();
    } while (TextUtils.isEmpty(paramString));
    this.result = paramString;
    LogUtils.i("对焦成功" + paramString);
    switch (this.businessType)
    {
    default: 
      return;
    case PRODUCT: 
      scanForAddSalesProduct();
      return;
    case PURCHASE: 
      scanForAddPurchaseProduct();
      return;
    case SEARCH: 
      scanForCreateProduct();
      return;
    case SALL: 
      scanForCheckProduct();
      return;
    }
    scanForSearchProduct();
  }
  
  protected void onDestroy()
  {
    this.inactivityTimer.shutdown();
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      finish();
    }
    return false;
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.captureActivityHandler != null)
    {
      this.captureActivityHandler.quitSynchronously();
      this.captureActivityHandler = null;
    }
    CameraManager.get().closeDriver();
  }
  
  protected void onResume()
  {
    super.onResume();
    SurfaceHolder localSurfaceHolder = ((SurfaceView)findViewById(2131362348)).getHolder();
    if (this.hasSurface) {
      initCamera(localSurfaceHolder);
    }
    for (;;)
    {
      this.playBeep = true;
      if (((AudioManager)getSystemService("audio")).getRingerMode() != 2) {
        this.playBeep = false;
      }
      initBeepSound();
      this.vibrate = true;
      getActivityIntent();
      return;
      localSurfaceHolder.addCallback(this);
      localSurfaceHolder.setType(3);
    }
  }
  
  public void onSaveSalesDocumentFail(String paramString) {}
  
  public void onSaveSalesDocumentSuccess(long paramLong)
  {
    this.transID = paramLong;
    if (paramLong > 0L) {
      this.handler.obtainMessage(17).sendToTarget();
    }
  }
  
  public void onTopBarBehindLeftClick()
  {
    initPopupWindow();
  }
  
  public void onTopBarFrontRightClick()
  {
    finishScanProduct();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    finishScanProduct();
  }
  
  public void setCropHeight(int paramInt)
  {
    this.cropHeight = paramInt;
  }
  
  public void setCropWidth(int paramInt)
  {
    this.cropWidth = paramInt;
  }
  
  public void setNeedCapture(boolean paramBoolean)
  {
    this.isNeedCapture = paramBoolean;
  }
  
  public void setX(int paramInt)
  {
    this.x = paramInt;
  }
  
  public void setY(int paramInt)
  {
    this.y = paramInt;
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (!this.hasSurface)
    {
      this.hasSurface = true;
      initCamera(paramSurfaceHolder);
    }
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    this.hasSurface = false;
  }
  
  private class MyItemClick
    implements AdapterView.OnItemClickListener
  {
    private MyItemClick() {}
    
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      CaptureActivity.this.setBussnessType(paramInt);
      CaptureActivity.this.menuAdapter.setLocation(paramInt);
      CaptureActivity.this.menuAdapter.notifyDataSetChanged();
    }
  }
  
  private class MySetClick
    implements AdapterView.OnItemClickListener
  {
    private MySetClick() {}
    
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      CaptureActivity.this.setAdapter.setLocation(paramInt);
      CaptureActivity.this.setAdapter.notifyDataSetChanged();
      CaptureActivity.this.editor.putInt("capturescanset", paramInt);
      CaptureActivity.this.editor.commit();
      CaptureActivity.this.window.dismiss();
    }
  }
}