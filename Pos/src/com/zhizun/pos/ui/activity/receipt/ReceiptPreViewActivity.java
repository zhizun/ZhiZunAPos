package com.zhizun.pos.ui.activity.receipt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.ReceiptManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.bloothprint.PrintManager;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.dao.S_MerchantDao;
import com.zizun.cs.biz.dao.impl.S_MerchantDaoImpl;
import com.zizun.cs.biz.receipt.SalesReceipt;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.biz.sale.V_TransactionDetail;
import com.zizun.cs.biz.sale.V_TransactionHeader;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.VIP;
import com.zizun.cs.entities.api.WXReceiptParam;
import com.zizun.cs.entities.api.WXReceiptParam.ProductItemJsonModel;
import com.zizun.cs.entities.api.WXReceiptResult;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.bluetoothPrint.BluePrintActivity;
import com.zhizun.pos.ui.adapter.receipt.ReceiptPreViewAdapter;
import com.zhizun.pos.ui.dialog.PayDialog;
import com.zhizun.pos.ui.dialog.PayDialog.OnCreateViewListener;
import com.zhizun.pos.ui.dialog.PayDialog.OnPayFinishDialogListener;
import com.zhizun.pos.ui.dialog.WXReceiptSendDialog;
import com.zhizun.pos.ui.dialog.WXReceiptSendDialog.OnWXReceiptSendEditFinishListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.InterceptListView;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ReceiptPreViewActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, WXReceiptSendDialog.OnWXReceiptSendEditFinishListener, PayDialog.OnPayFinishDialogListener, PayDialog.OnCreateViewListener
{
  private static final int FINISH_ACTIVITY = 3;
  private static final int GET_INTENT_DATA_FAIL = 2;
  private static final int GET_INTENT_DATA_SUCCESS = 1;
  private static final int GET_PAYMENTMETHOD_COMPLETE = 4;
  private String WXCode;
  private ReceiptPreViewAdapter adapter;
  private PaymentMethod aliPaymentMethod;
  private PaymentMethod cashPaymentMethod;
  private CheckBox cbPrint;
  private CheckBox cbSendWX;
  private Context context;
  private BluetoothDevice currentDevice;
  private PaymentMethod currentPaymentMethod;
  private VIP customer;
  private ArrayList<V_TransactionDetail> detailList;
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
        ReceiptPreViewActivity.this.refreshView();
        continue;
        ToastUtil.toastLong(ReceiptPreViewActivity.this.context, "数据获取失败");
        continue;
        ReceiptPreViewActivity.this.finishView();
        continue;
        paramAnonymousMessage = (List)paramAnonymousMessage.obj;
        if ((paramAnonymousMessage != null) && (paramAnonymousMessage.size() > 0)) {
          ReceiptPreViewActivity.this.initDefultPaymethod(paramAnonymousMessage);
        }
      }
    }
  });
  private V_TransactionHeader header;
  private boolean isNeedPrint = false;
  private boolean isNeedSendWX = false;
  private InterceptListView lsvProductList;
  private RadioButton mAliButton;
  private RadioButton mCashButton;
  private RadioGroup mPayRadioGroup;
  private ScrollView mScvContainer;
  private RadioButton mWXButton;
  private PayDialog payDialog;
  private PaymentMethodDao paymentMethodDao;
  private SharedPreferences preference;
  private SalesReceipt receipt;
  private RelativeLayout rlbtn_sure;
  private WXReceiptSendDialog sendDialog;
  private long transID = -1L;
  private TextView tvEndAmountPaid;
  private TextView tvEndAmountTotal;
  private TextView tvEndCompany;
  private TextView tvEndQuantityTotal;
  private TextView tvEndUserName;
  private TextView tvHeadAddress;
  private TextView tvHeadDate;
  private TextView tvHeadPhone;
  private TextView tvHeadTranNumber;
  private TextView tvHeadWelcome;
  private PaymentMethod wxPaymentMethod;
  
  private void doSendWXAction()
  {
    if (this.isNeedSendWX)
    {
      if ((this.customer == null) || (TextUtils.isEmpty(this.customer.getVIP_WX())))
      {
        if (this.sendDialog == null)
        {
          this.sendDialog = new WXReceiptSendDialog();
          this.sendDialog.setCancelable(false);
          this.sendDialog.setOnWXReceiptSendEditFinishDialogListener(this);
        }
        this.sendDialog.show(getSupportFragmentManager(), "WXReceiptSendDialog");
        return;
      }
      this.WXCode = this.customer.getVIP_WX();
      sendWXRecepit();
      return;
    }
    printData();
  }
  
  private void finishView()
  {
    saveSetting();
    finish();
  }
  
  private void getIntentData()
  {
    this.transID = getIntent().getLongExtra(Long.class.getSimpleName(), -1L);
    if (this.transID > 0L) {
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          ReceiptPreViewActivity.this.header = SalesManager.getV_TransactionHeaderOutUIThread(ReceiptPreViewActivity.this.transID);
          ReceiptPreViewActivity.this.customer = ReceiptManager.getVIPByVIPIDOutUIThread(ReceiptPreViewActivity.this.header.getBusinessID());
          ReceiptPreViewActivity.this.detailList = ((ArrayList)SalesManager.getV_TransactionDetailListOutUIThread(ReceiptPreViewActivity.this.transID));
          if (ReceiptPreViewActivity.this.header != null)
          {
            ReceiptPreViewActivity.this.handler.obtainMessage(1).sendToTarget();
            return;
          }
          ReceiptPreViewActivity.this.handler.obtainMessage(2).sendToTarget();
        }
      });
    }
  }
  
  private void getPaymentMethodList()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        List localList = SalesManager.getInstance().getPaymentMethodListOutUIThread(ReceiptPreViewActivity.this.paymentMethodDao);
        ReceiptPreViewActivity.this.handler.obtainMessage(4, localList).sendToTarget();
      }
    });
  }
  
  private void initDefultPaymethod()
  {
    this.paymentMethodDao = SalesManager.getInstance().getPaymentMethodDao();
    getPaymentMethodList();
  }
  
  private void initDefultPaymethod(List<PaymentMethod> paramList)
  {
    initPaymentMethod(paramList);
    String str = UserManager.getInstance().getCurrentPaymentMethod();
    paramList = str;
    if (TextUtils.isEmpty(str)) {
      paramList = "Cash";
    }
    initPayCheckButtion(paramList);
  }
  
  private void initPaymentMethod(List<PaymentMethod> paramList)
  {
    paramList = paramList.iterator();
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      PaymentMethod localPaymentMethod = (PaymentMethod)paramList.next();
      if (localPaymentMethod.getPaymentMethod_Code().endsWith("Cash")) {
        this.cashPaymentMethod = localPaymentMethod;
      }
      if (localPaymentMethod.getPaymentMethod_Code().endsWith("WX")) {
        this.wxPaymentMethod = localPaymentMethod;
      }
      if (localPaymentMethod.getPaymentMethod_Code().endsWith("Ali")) {
        this.aliPaymentMethod = localPaymentMethod;
      }
    }
  }
  
  private void initSetting()
  {
    this.preference = PreferencesUtil.getPreference(this.context);
    this.isNeedSendWX = this.preference.getBoolean("isNeedSendWX", true);
    this.isNeedPrint = this.preference.getBoolean("isNeedPrint", false);
    this.cbPrint.setChecked(this.isNeedPrint);
    this.cbSendWX.setChecked(this.isNeedSendWX);
  }
  
  private void initView()
  {
    this.tvHeadWelcome = ((TextView)findViewById(2131362368));
    this.tvHeadAddress = ((TextView)findViewById(2131362369));
    this.tvHeadPhone = ((TextView)findViewById(2131362370));
    this.tvHeadTranNumber = ((TextView)findViewById(2131362371));
    this.tvHeadDate = ((TextView)findViewById(2131362372));
    this.tvEndQuantityTotal = ((TextView)findViewById(2131362379));
    this.tvEndAmountTotal = ((TextView)findViewById(2131362380));
    this.tvEndAmountPaid = ((TextView)findViewById(2131362381));
    this.tvEndCompany = ((TextView)findViewById(2131362382));
    this.tvEndUserName = ((TextView)findViewById(2131362383));
    this.mScvContainer = ((ScrollView)findViewById(2131362367));
    this.lsvProductList = ((InterceptListView)findViewById(2131362378));
    this.cbPrint = ((CheckBox)findViewById(2131362389));
    this.cbSendWX = ((CheckBox)findViewById(2131362391));
    this.rlbtn_sure = ((RelativeLayout)findViewById(2131362393));
    this.mPayRadioGroup = ((RadioGroup)findViewById(2131362385));
    this.rlbtn_sure.setOnClickListener(this);
    this.mCashButton = ((RadioButton)findViewById(2131362386));
    this.mWXButton = ((RadioButton)findViewById(2131362387));
    this.mAliButton = ((RadioButton)findViewById(2131362388));
    this.mPayRadioGroup.setOnCheckedChangeListener(this);
    this.cbPrint.setOnCheckedChangeListener(new MyOnCheckedChangeListener(null));
    this.cbSendWX.setOnCheckedChangeListener(new MyOnCheckedChangeListener(null));
  }
  
  private void initbaseData()
  {
    this.context = this;
    getIntentData();
    initDefultPaymethod();
    initSetting();
  }
  
  private void refreshView()
  {
    this.tvHeadWelcome.setText(ResUtil.getFormatString(2131165632, this.header.getStore_Name()));
    this.tvHeadAddress.setText(ResUtil.getFormatString(2131165633, this.header.getMerchant_Address()));
    this.tvHeadPhone.setText(ResUtil.getFormatString(2131165634, this.header.getMerchant_Phone()));
    this.tvHeadTranNumber.setText(ResUtil.getFormatString(2131165635, this.header.getTransNumber()));
    this.tvHeadDate.setText(ResUtil.getFormatString(2131165636, this.header.getTransDate()));
    this.tvEndQuantityTotal.setText(ResUtil.getFormatString(2131165637, NumUtil.getDecimal(this.header.getTotalQuantiy())));
    this.tvEndAmountTotal.setText(ResUtil.getFormatString(2131165638, NumUtil.getDecimal(this.header.getTotalPlanAmount())));
    this.tvEndAmountPaid.setText(ResUtil.getFormatString(2131165639, NumUtil.getDecimal(this.header.getTotalActualAmount())));
    this.tvEndCompany.setText(ResUtil.getFormatString(2131165641, this.header.getBusinessName()));
    this.tvEndUserName.setText(ResUtil.getFormatString(2131165642, this.header.getTransStaffName()));
    setadapter();
  }
  
  private void saveSetting()
  {
    SharedPreferences.Editor localEditor = this.preference.edit();
    localEditor.putBoolean("isNeedSendWX", this.isNeedSendWX);
    localEditor.putBoolean("isNeedPrint", this.isNeedPrint);
    localEditor.commit();
  }
  
  private void sendWXRecepit()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.detailList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        ReceiptManager.sendWXReceipt(new WXReceiptParam(this.header.getMerchant_ID(), this.header.getMerchant_Name(), this.header.getMerchant_Address(), this.header.getMerchant_Phone(), this.header.getTransNumber(), this.header.getTotalQuantiy(), this.header.getTotalPlanAmount(), this.header.getTotalActualAmount(), this.header.getDiscountPercent(), this.header.getDiscountAmount(), this.header.getTransStaffName(), this.header.getTransType(), this.WXCode, localArrayList), new RequestCallBack()
        {
          public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString)
          {
            ToastUtil.toastLong(ReceiptPreViewActivity.this.context, "网络连接失败!");
          }
          
          public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo)
          {
            paramAnonymousResponseInfo = ReceiptManager.getResultFromJson((String)paramAnonymousResponseInfo.result);
            if (paramAnonymousResponseInfo.Code == 200)
            {
              ToastUtil.toastLong(ReceiptPreViewActivity.this.context, "微小票发送成功");
              if ((ReceiptPreViewActivity.this.customer != null) && (TextUtils.isEmpty(ReceiptPreViewActivity.this.customer.getVIP_WX())))
              {
                ReceiptPreViewActivity.this.customer.setVIP_WX(ReceiptPreViewActivity.this.WXCode);
                ExecutorUtils.getDBExecutor().execute(new Runnable()
                {
                  public void run()
                  {
                    if (ReceiptManager.updateVIPOutUIThread(ReceiptPreViewActivity.this.customer))
                    {
                      LogUtils.i("vip更新成功");
                      return;
                    }
                    LogUtils.i("vip更新失败");
                  }
                });
              }
            }
            while (paramAnonymousResponseInfo.Code != 500) {
              return;
            }
            ToastUtil.toastLong(ReceiptPreViewActivity.this.context, paramAnonymousResponseInfo.Message);
          }
        });
        return;
      }
      V_TransactionDetail localV_TransactionDetail = (V_TransactionDetail)localIterator.next();
      WXReceiptParam.ProductItemJsonModel localProductItemJsonModel = new WXReceiptParam.ProductItemJsonModel();
      localProductItemJsonModel.Product = localV_TransactionDetail.getProductSku_Name();
      localProductItemJsonModel.Count = localV_TransactionDetail.getTransQty();
      localProductItemJsonModel.Price = localV_TransactionDetail.getTransPrice();
      localArrayList.add(localProductItemJsonModel);
    }
  }
  
  private void setadapter()
  {
    this.adapter = new ReceiptPreViewAdapter(this.detailList);
    float f = ResUtil.getDimens(2131099704);
    this.lsvProductList.setAdapter(this.adapter);
    this.lsvProductList.setParentScrollView(this.mScvContainer);
    this.lsvProductList.setMaxHeight((int)(3.0F * f));
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903123, paramViewGroup);
    this.mTopBar.setTitle(2131165643);
    this.mTopBar.setLeftButton(2130837526, this);
    initView();
    initbaseData();
  }
  
  public void initPayCheckButtion(String paramString)
  {
    if (paramString.equals("Cash"))
    {
      this.currentPaymentMethod = this.cashPaymentMethod;
      this.mCashButton.setChecked(true);
    }
    do
    {
      return;
      if (paramString.equals("WX"))
      {
        this.currentPaymentMethod = this.wxPaymentMethod;
        this.mWXButton.setChecked(true);
        return;
      }
    } while (!paramString.equals("Ali"));
    this.currentPaymentMethod = this.aliPaymentMethod;
    this.mAliButton.setChecked(true);
  }
  
  public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    switch (paramRadioGroup.getCheckedRadioButtonId())
    {
    default: 
      return;
    case 2131362386: 
      this.currentPaymentMethod = this.cashPaymentMethod;
      UserManager.getInstance().setCurrentPaymentMethod("Cash");
      return;
    case 2131362387: 
      this.currentPaymentMethod = this.wxPaymentMethod;
      UserManager.getInstance().setCurrentPaymentMethod("WX");
      return;
    }
    this.currentPaymentMethod = this.aliPaymentMethod;
    UserManager.getInstance().setCurrentPaymentMethod("Ali");
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    if ((this.currentPaymentMethod != null) && (this.currentPaymentMethod != this.cashPaymentMethod))
    {
      this.paymentMethodDao = SalesManager.getInstance().getPaymentMethodDao();
      this.paymentMethodDao.updatePaymentMethod(this.currentPaymentMethod.getPaymentMethod_ID(), this.transID);
      if (this.payDialog == null)
      {
        this.payDialog = new PayDialog();
        this.payDialog.setContentText("应付金额 : " + this.header.getTotalPlanAmount());
        this.payDialog.setOnPayFinishDialogListener(this);
        this.payDialog.setOnCreateViewListener(this);
      }
      this.payDialog.show(getSupportFragmentManager(), "WXPayDialog");
      return;
    }
    doSendWXAction();
  }
  
  public void onCreateView(ImageView paramImageView)
  {
    Object localObject = new S_MerchantDaoImpl().getMerchant();
    String str = ((S_Merchant)localObject).getWX_QR();
    localObject = ((S_Merchant)localObject).getAliPay_QR();
    if ("WX".endsWith(this.currentPaymentMethod.getPaymentMethod_Code())) {
      if (!TextUtils.isEmpty(str)) {
        ImgUtil.showBig(paramImageView, ImgUtil.getImgPathFromSev(str));
      }
    }
    while ((!"Ali".endsWith(this.currentPaymentMethod.getPaymentMethod_Code())) || (TextUtils.isEmpty((CharSequence)localObject))) {
      return;
    }
    ImgUtil.showBig(paramImageView, ImgUtil.getImgPathFromSev((String)localObject));
  }
  
  public void onPayFinish(String paramString)
  {
    doSendWXAction();
  }
  
  public void onSendCancel()
  {
    this.sendDialog.dismiss();
  }
  
  public void onSendFinish(String paramString)
  {
    this.WXCode = paramString;
    sendWXRecepit();
  }
  
  public void onTopBarLeftClick()
  {
    finishView();
  }
  
  public void printData()
  {
    if (this.isNeedPrint)
    {
      this.currentDevice = PrintManager.getInstance().getCurrentRemoteDevice();
      this.receipt = new SalesReceipt();
      this.receipt.setHeader(this.header);
      this.receipt.setDetailList(this.detailList);
      if (this.currentDevice != null)
      {
        ExecutorUtils.getDBExecutor().execute(new Runnable()
        {
          public void run()
          {
            OutputStream localOutputStream = PrintManager.getOutputStreamOutUIThread(ReceiptPreViewActivity.this.currentDevice);
            if ((localOutputStream != null) && (ReceiptPreViewActivity.this.receipt != null)) {
              ReceiptPreViewActivity.this.receipt.printOutUIThread(localOutputStream, 1);
            }
            PrintManager.closeBluetoothPrintStream();
            LogUtils.i("打印完成");
            ReceiptPreViewActivity.this.handler.obtainMessage(3).sendToTarget();
          }
        });
        return;
      }
      startActivityForPrint(this.receipt);
      this.handler.obtainMessage(3).sendToTarget();
      return;
    }
    this.handler.obtainMessage(3).sendToTarget();
  }
  
  public void startActivityForPrint(SalesReceipt paramSalesReceipt)
  {
    Intent localIntent = new Intent(this.context, BluePrintActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable(SalesReceipt.class.getSimpleName(), paramSalesReceipt);
    localIntent.putExtras(localBundle);
    this.context.startActivity(localIntent);
  }
  
  private class MyOnCheckedChangeListener
    implements CompoundButton.OnCheckedChangeListener
  {
    private MyOnCheckedChangeListener() {}
    
    public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      switch (paramCompoundButton.getId())
      {
      case 2131362390: 
      default: 
        return;
      case 2131362389: 
        ReceiptPreViewActivity.this.isNeedPrint = paramBoolean;
        return;
      }
      ReceiptPreViewActivity.this.isNeedSendWX = paramBoolean;
    }
  }
}