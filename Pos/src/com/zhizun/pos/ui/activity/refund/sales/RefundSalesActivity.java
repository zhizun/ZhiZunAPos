package com.zhizun.pos.ui.activity.refund.sales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.enumType.DiscountType;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.manager.RefundManager;
import com.zizun.cs.biz.refund.sales.RefundSalesDocument;
import com.zizun.cs.biz.refund.sales.RefundSalesManager;
import com.zizun.cs.biz.refund.sales.RefundSalesShoppingCart;
import com.zizun.cs.biz.refund.sales.RefundableSalesSheetItem;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.PaymentMethod;
import com.zizun.cs.entities.biz.Associate;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.ChooseSupplierActivity;
import com.zhizun.pos.ui.activity.sales.SalesHistoryDetailActivity;
import com.zhizun.pos.ui.adapter.refund.RefundSalesAdapter;
import com.zhizun.pos.ui.adapter.sales.SalesAdapter;
import com.zhizun.pos.ui.dialog.PickTimeDialog;
import com.zhizun.pos.ui.dialog.PickTimeDialog.onDatePickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.InterceptListView;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import com.zhizun.pos.util.TextViewUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RefundSalesActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
  private static final int GET_REFUNDABLE_SALES_SHEETITEM_COMPLETE = 18;
  private static final int INTENT_REQUEST_CODE_GOODS = 2;
  private static final int INTENT_REQUEST_CODE_SUPPLIER = 1;
  private static final int SALES_DOCUMENT_SAVE_SUCCESS = 17;
  private Timestamp SO_Date;
  private Associate associate;
  private Context context;
  private PaymentMethod currentPaymentMethod;
  private double discountAmount = 0.0D;
  private double discountPercent = 1.0D;
  private DiscountType discountType = DiscountType.DISCOUNT_AMOUNT;
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
        RefundSalesActivity.this.clearViewData();
        RefundSalesActivity.this.finish();
        continue;
        RefundSalesActivity.this.refrashActivityData();
      }
    }
  });
  private LinearLayout llRefNumber;
  private Button mBtnDiscount;
  private EditText mEdtDiscount;
  private EditText mEdtPaid;
  private Intent mIntent;
  private InterceptListView mLsvGoods;
  private ScrollView mScvContainer;
  private TextView mTxtPayable;
  private TextView mTxtReferenceNumber;
  private TextView mTxtSupplier;
  private TextView mTxtTime;
  private LinearLayout mllSaveBtn;
  private List<PaymentMethod> paymentMethodList;
  private RefundableSheet refundableSheet;
  private RefundSalesDocument salesDocument;
  private ArrayList<RefundableSalesSheetItem> salesDocumentItems;
  private RefundSalesShoppingCart shoppingCart;
  
  private void calculateTotalMoney()
  {
    this.mTxtPayable.setText(DataUtil.getFormatString(this.shoppingCart.getTotalMoney()));
    this.mEdtPaid.setText(DataUtil.getFormatString(this.shoppingCart.getTotalMoney()));
  }
  
  private void changeDiscount()
  {
    String str = TextViewUtil.getTextString(this.mEdtDiscount);
    if (TextUtils.isEmpty(str))
    {
      ToastUtil.toastLong(this, "折扣不能为空");
      return;
    }
    double d1 = Double.parseDouble(str);
    double d2;
    if (this.discountType == DiscountType.DISCOUNT_AMOUNT) {
      if (d1 >= 0.0D)
      {
        this.discountAmount = d1;
        this.discountPercent = 1.0D;
        d2 = this.shoppingCart.getTotalMoney();
        if (this.discountType != DiscountType.DISCOUNT_AMOUNT) {
          break label173;
        }
        d1 = d2 - this.discountAmount;
      }
    }
    for (;;)
    {
      this.mTxtPayable.setText(NumUtil.getDecimal(d1));
      this.mEdtPaid.setText(NumUtil.getDecimal(d1));
      return;
      ToastUtil.toastLong(this.context, "折扣额输入错误");
      resetDiscoutAmountData();
      return;
      if (this.discountType != DiscountType.DISCOUNT_PERCENT) {
        break;
      }
      if ((0.0D < d1) && (d1 <= 100.0D))
      {
        this.discountAmount = 0.0D;
        this.discountPercent = (d1 / 100.0D);
        break;
      }
      ToastUtil.toastLong(this.context, "折扣率输入错误");
      resetDiscoutPercentData();
      return;
      label173:
      d1 = d2;
      if (this.discountType == DiscountType.DISCOUNT_PERCENT) {
        d1 = d2 * this.discountPercent;
      }
    }
  }
  
  private void clearViewData()
  {
    this.shoppingCart.clearShoppingCart();
    ((RefundSalesAdapter)this.mLsvGoods.getAdapter()).notifyDataSetChanged();
    this.mTxtSupplier.setText("");
    this.mTxtPayable.setText("0");
    this.mEdtPaid.setText("0");
    this.discountType = DiscountType.DISCOUNT_AMOUNT;
    this.mBtnDiscount.setText("折扣额");
    this.mEdtDiscount.setText("0");
    RefundSalesManager.clearData();
  }
  
  private void findviewbyId()
  {
    this.mScvContainer = ((ScrollView)findViewById(2131362093));
    this.mTxtReferenceNumber = ((TextView)findViewById(2131362100));
    this.llRefNumber = ((LinearLayout)findViewById(2131362099));
    this.mTxtTime = ((TextView)findViewById(2131362096));
    this.mTxtSupplier = ((TextView)findViewById(2131362098));
    this.mLsvGoods = ((InterceptListView)findViewById(2131362102));
    this.mTxtPayable = ((TextView)findViewById(2131362108));
    this.mEdtPaid = ((EditText)findViewById(2131362109));
    this.mBtnDiscount = ((Button)findViewById(2131362106));
    this.mEdtDiscount = ((EditText)findViewById(2131362107));
    this.mllSaveBtn = ((LinearLayout)findViewById(2131362111));
    findViewById(2131362094).setOnClickListener(this);
    findViewById(2131362101).setOnClickListener(this);
    findViewById(2131362111).setOnClickListener(this);
    this.mllSaveBtn.setOnClickListener(this);
    this.mBtnDiscount.setOnClickListener(this);
    this.llRefNumber.setOnClickListener(this);
    ((RadioGroup)findViewById(2131362103)).setOnCheckedChangeListener(this);
    this.mEdtDiscount.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        RefundSalesActivity.this.changeDiscount();
        Selection.setSelection(paramAnonymousEditable, paramAnonymousEditable.length());
      }
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    });
  }
  
  private void initBaseData()
  {
    getIntentData();
    this.context = this;
    this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
  }
  
  private void initPaymentMethodList()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        PaymentMethodDao localPaymentMethodDao = SalesManager.getInstance().getPaymentMethodDao();
        RefundSalesActivity.this.paymentMethodList = SalesManager.getInstance().getPaymentMethodListOutUIThread(localPaymentMethodDao);
      }
    }).start();
  }
  
  private void refrashActivityData()
  {
    initPaymentMethodList();
    this.refundableSheet = RefundSalesManager.getRefundableSheet();
    this.shoppingCart = RefundSalesManager.getShoppingCart();
    this.salesDocumentItems = this.shoppingCart.getSaleShoppingCartItemList();
    this.mTxtReferenceNumber.setText(this.refundableSheet.getTransNumber());
    this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
    this.SO_Date = DateTimeUtil.getCurrentTime();
    this.mTxtSupplier.setText(this.refundableSheet.getRelatedName());
    if (this.refundableSheet.getVIP_ID() > 0L)
    {
      this.associate = new Associate();
      this.associate.setAssciate_VIPID(this.refundableSheet.getVIP_ID());
    }
    setadpter();
    calculateTotalMoney();
  }
  
  private void resetDiscoutAmountData()
  {
    this.mBtnDiscount.setText("折扣额");
    this.discountType = DiscountType.DISCOUNT_AMOUNT;
    this.mEdtDiscount.setText("0");
    this.discountAmount = 0.0D;
    this.discountPercent = 1.0D;
  }
  
  private void resetDiscoutPercentData()
  {
    this.mBtnDiscount.setText("折扣率");
    this.discountType = DiscountType.DISCOUNT_PERCENT;
    this.mEdtDiscount.setText("100");
    this.discountAmount = 0.0D;
    this.discountPercent = 1.0D;
  }
  
  private void setCustomer()
  {
    this.salesDocument.setCustomer(this.associate);
  }
  
  private boolean setDiscount()
  {
    if (TextUtils.isEmpty(TextViewUtil.getTextString(this.mEdtDiscount)))
    {
      ToastUtil.toastLong(this, "折扣不能为空");
      resetDiscoutAmountData();
      return false;
    }
    this.salesDocument.setDiscountType(this.discountType);
    this.salesDocument.setDiscountAmount(this.discountAmount);
    this.salesDocument.setDiscountPercent(this.discountPercent);
    return true;
  }
  
  private void setPaid()
  {
    String str = TextViewUtil.trimPriceComma(TextViewUtil.getTextString(this.mEdtPaid));
    try
    {
      double d = Double.parseDouble(str);
      this.salesDocument.setPaid(d);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      ToastUtil.toastLong(this, "实付输入错误");
      localNumberFormatException.printStackTrace();
    }
  }
  
  private void setPaymentMethod()
  {
    this.currentPaymentMethod = ((PaymentMethod)this.paymentMethodList.get(0));
    this.salesDocument.setCurrentPaymentMethod(this.currentPaymentMethod);
  }
  
  private void setSO_Date()
  {
    this.salesDocument.setSO_Date(this.SO_Date);
  }
  
  private void setadpter()
  {
    float f = ResUtil.getDimens(2131099704);
    RefundSalesAdapter localRefundSalesAdapter = new RefundSalesAdapter(this.shoppingCart);
    this.mLsvGoods.setAdapter(localRefundSalesAdapter);
    this.mLsvGoods.setParentScrollView(this.mScvContainer);
    this.mLsvGoods.setMaxHeight((int)(3.0F * f));
    calculateTotalMoney();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903083, paramViewGroup);
    this.mTopBar.setTitle(2131165365);
    this.mTopBar.setLeftButton(2130837526, this);
    findviewbyId();
    initBaseData();
  }
  
  public void getIntentData()
  {
    Bundle localBundle = getIntent().getBundleExtra(Bundle.class.getSimpleName());
    if (localBundle != null)
    {
      this.refundableSheet = ((RefundableSheet)localBundle.getSerializable(RefundableSheet.class.getSimpleName()));
      RefundSalesManager.setRefundableSheet(this.refundableSheet);
      new Thread(new Runnable()
      {
        public void run()
        {
          RefundSalesActivity.this.shoppingCart = RefundSalesManager.getShoppingCart();
          ArrayList localArrayList;
          Iterator localIterator;
          if (RefundSalesActivity.this.shoppingCart == null)
          {
            localArrayList = RefundManager.getAllRefundableSalesSheetItemsOutUIThread(RefundSalesActivity.this.refundableSheet.getTransID());
            RefundSalesActivity.this.shoppingCart = new RefundSalesShoppingCart();
            localIterator = localArrayList.iterator();
          }
          for (;;)
          {
            if (!localIterator.hasNext())
            {
              RefundSalesActivity.this.shoppingCart.SetSaleShoppingCartItemList(localArrayList);
              RefundSalesManager.setShoppingCart(RefundSalesActivity.this.shoppingCart);
              RefundSalesActivity.this.handler.obtainMessage(18).sendToTarget();
              return;
            }
            ((RefundableSalesSheetItem)localIterator.next()).initShoppingCartItem();
          }
        }
      }).start();
      return;
    }
    refrashActivityData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      return;
    } while ((paramIntent == null) || (paramIntent.getExtras() == null));
    this.associate = ((Associate)paramIntent.getSerializableExtra(Associate.class.getSimpleName()));
    this.mTxtSupplier.setText(this.associate.getCompany());
  }
  
  public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    if ((this.salesDocumentItems != null) && (this.salesDocumentItems.size() != 0))
    {
      calculateTotalMoney();
      ((SalesAdapter)this.mLsvGoods.getAdapter()).notifyDataSetChanged();
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131362132: 
    default: 
      return;
    case 2131362094: 
      paramView = new PickTimeDialog();
      paramView.setOnTimePickListener(new PickTimeDialog.onDatePickListener()
      {
        public void onPickFinish(String paramAnonymousString)
        {
          RefundSalesActivity.this.mTxtTime.setText(paramAnonymousString);
          RefundSalesActivity.this.SO_Date = DateTimeUtil.getSimpleTimestampFromString(paramAnonymousString);
        }
      });
      paramView.show(getSupportFragmentManager(), "PickTimeDialog");
      return;
    case 2131362099: 
      this.mIntent = new Intent(this, SalesHistoryDetailActivity.class);
      this.mIntent.putExtra(Long.class.getSimpleName(), this.refundableSheet.getTransID());
      startActivity(this.mIntent);
      return;
    case 2131362097: 
      this.mIntent = new Intent(this, ChooseSupplierActivity.class);
      startActivityForResult(this.mIntent, 1);
      return;
    case 2131362101: 
      this.mIntent = new Intent(this, RefundSalesShoppingCartActivity.class);
      startActivity(this.mIntent);
      finish();
      return;
    case 2131362106: 
      if ("折扣额".equals(this.mBtnDiscount.getText().toString()))
      {
        this.mBtnDiscount.setText("折扣率%");
        this.discountType = DiscountType.DISCOUNT_PERCENT;
        this.mEdtDiscount.setText("100");
        return;
      }
      this.mBtnDiscount.setText("折扣额");
      this.discountType = DiscountType.DISCOUNT_AMOUNT;
      this.mEdtDiscount.setText("0");
      return;
    }
    this.salesDocument = new RefundSalesDocument();
    setCustomer();
    setSO_Date();
    setDiscount();
    setPaid();
    setPaymentMethod();
    this.salesDocument.setRefundableSheet(this.refundableSheet);
    this.salesDocument.initRefundSalesShoppingCart(this.shoppingCart);
    new Thread(new Runnable()
    {
      public void run()
      {
        RefundSalesActivity.this.salesDocument.initSalesDocument();
        if (RefundSalesManager.saveRefundSalesDocument(RefundSalesActivity.this.salesDocument)) {
          RefundSalesActivity.this.handler.obtainMessage(17).sendToTarget();
        }
      }
    }).start();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      clearViewData();
      finish();
    }
    return false;
  }
  
  public void onTopBarLeftClick()
  {
    clearViewData();
    finish();
  }
}