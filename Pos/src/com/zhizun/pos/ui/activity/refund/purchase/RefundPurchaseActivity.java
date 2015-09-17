package com.zhizun.pos.ui.activity.refund.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
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
import com.zizun.cs.biz.enumType.RTVType;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.manager.RefundManager;
import com.zizun.cs.biz.refund.purchase.RefundPurchaseDocument;
import com.zizun.cs.biz.refund.purchase.RefundPurchaseManager;
import com.zizun.cs.biz.refund.purchase.RefundPurchaseShoppingCart;
import com.zizun.cs.biz.refund.purchase.RefundablePurchaseSheetItem;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.biz.Associate;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.ChooseSupplierActivity;
import com.zhizun.pos.ui.activity.purchase.PurchaseHistoryProdListActivity;
import com.zhizun.pos.ui.adapter.refund.RefundPurchaseAdapter;
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

public class RefundPurchaseActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
  private static final int GET_REFUNDABLE_PURCHASE_SHEETITEM_COMPLETE = 18;
  private static final int INTENT_REQUEST_CODE_GOODS = 2;
  private static final int INTENT_REQUEST_CODE_SUPPLIER = 1;
  private static final int PURCHASE_DOCUMENT_SAVE_SUCCESS = 17;
  private Timestamp SO_Date;
  private Associate associate;
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
        RefundPurchaseActivity.this.clearViewData();
        RefundPurchaseActivity.this.finish();
        continue;
        RefundPurchaseActivity.this.refrashActivityData();
      }
    }
  });
  private LinearLayout llRefNumber;
  private Button mBtnDiscount;
  private EditText mEdtPaid;
  private Intent mIntent;
  private InterceptListView mLsvGoods;
  private ScrollView mScvContainer;
  private TextView mTxtPayable;
  private TextView mTxtReferenceNumber;
  private TextView mTxtSupplier;
  private TextView mTxtTime;
  private LinearLayout mllSaveBtn;
  private RefundPurchaseDocument purchaseDocument;
  private ArrayList<RefundablePurchaseSheetItem> purchaseDocumentItems;
  private RefundableSheet refundableSheet;
  private RefundPurchaseShoppingCart shoppingCart;
  
  private void calculateTotalMoney()
  {
    this.mTxtPayable.setText(DataUtil.getFormatString(this.shoppingCart.getTotalMoney()));
    this.mEdtPaid.setText(DataUtil.getFormatString(this.shoppingCart.getTotalMoney()));
  }
  
  private void clearViewData()
  {
    this.shoppingCart.clearShoppingCart();
    ((RefundPurchaseAdapter)this.mLsvGoods.getAdapter()).notifyDataSetChanged();
    this.mTxtSupplier.setText("");
    this.mTxtPayable.setText("0");
    this.mEdtPaid.setText("0");
    RefundPurchaseManager.clearData();
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
    this.mllSaveBtn = ((LinearLayout)findViewById(2131362111));
    this.mllSaveBtn.setOnClickListener(this);
    this.mBtnDiscount.setOnClickListener(this);
    this.llRefNumber.setOnClickListener(this);
    findViewById(2131362094).setOnClickListener(this);
    findViewById(2131362101).setOnClickListener(this);
    ((RadioGroup)findViewById(2131362103)).setOnCheckedChangeListener(this);
  }
  
  private void initBaseData()
  {
    this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
    this.SO_Date = DateTimeUtil.getCurrentTime();
    getIntentData();
  }
  
  private void refrashActivityData()
  {
    this.refundableSheet = RefundPurchaseManager.getRefundableSheet();
    this.shoppingCart = RefundPurchaseManager.getShoppingCart();
    this.purchaseDocumentItems = this.shoppingCart.getShoppingCartItemList();
    this.mTxtReferenceNumber.setText(this.refundableSheet.getTransNumber());
    this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
    this.SO_Date = DateTimeUtil.getCurrentTime();
    this.mTxtSupplier.setText(this.refundableSheet.getRelatedName());
    if (this.refundableSheet.getSupplier_ID() > 0L)
    {
      this.associate = new Associate();
      this.associate.setAssciate_SupplierID(this.refundableSheet.getSupplier_ID());
    }
    setadpter();
    calculateTotalMoney();
  }
  
  private void setDate()
  {
    if (this.SO_Date != null) {
      this.purchaseDocument.setSO_Date(this.SO_Date);
    }
  }
  
  private void setPaid()
  {
    String str = TextViewUtil.trimPriceComma(TextViewUtil.getTextString(this.mEdtPaid));
    try
    {
      double d = Double.parseDouble(str);
      this.purchaseDocument.setPaid(d);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      ToastUtil.toastLong(this, "实付输入错误");
      localNumberFormatException.printStackTrace();
    }
  }
  
  private void setRTVType()
  {
    this.purchaseDocument.setRtvType(RTVType.PR_STOCK_OUT);
  }
  
  private void setSupplier()
  {
    this.purchaseDocument.setSupplier(this.associate);
  }
  
  private void setadpter()
  {
    float f = ResUtil.getDimens(2131099704);
    RefundPurchaseAdapter localRefundPurchaseAdapter = new RefundPurchaseAdapter(this.shoppingCart);
    this.mLsvGoods.setAdapter(localRefundPurchaseAdapter);
    this.mLsvGoods.setParentScrollView(this.mScvContainer);
    this.mLsvGoods.setMaxHeight((int)(3.0F * f));
    calculateTotalMoney();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903082, paramViewGroup);
    this.mTopBar.setTitle(2131165366);
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
      RefundPurchaseManager.setRefundableSheet(this.refundableSheet);
      new Thread(new Runnable()
      {
        public void run()
        {
          RefundPurchaseActivity.this.shoppingCart = RefundPurchaseManager.getShoppingCart();
          ArrayList localArrayList;
          Iterator localIterator;
          if (RefundPurchaseActivity.this.shoppingCart == null)
          {
            localArrayList = RefundManager.getAllRefundablePurchaseSheetItemsOutUIThread(RefundPurchaseActivity.this.refundableSheet.getTransID());
            RefundPurchaseActivity.this.shoppingCart = new RefundPurchaseShoppingCart();
            if (localArrayList != null) {
              localIterator = localArrayList.iterator();
            }
          }
          for (;;)
          {
            if (!localIterator.hasNext())
            {
              RefundPurchaseActivity.this.shoppingCart.setShoppingCartItemList(localArrayList);
              RefundPurchaseManager.setShoppingCart(RefundPurchaseActivity.this.shoppingCart);
              RefundPurchaseActivity.this.handler.obtainMessage(18).sendToTarget();
              return;
            }
            ((RefundablePurchaseSheetItem)localIterator.next()).initShoppingCartItem();
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
    switch (paramInt2)
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
    if ((this.purchaseDocumentItems != null) && (this.purchaseDocumentItems.size() != 0))
    {
      calculateTotalMoney();
      ((RefundPurchaseAdapter)this.mLsvGoods.getAdapter()).notifyDataSetChanged();
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
          RefundPurchaseActivity.this.mTxtTime.setText(paramAnonymousString);
          RefundPurchaseActivity.this.SO_Date = DateTimeUtil.getSimpleTimestampFromString(paramAnonymousString);
        }
      });
      paramView.show(getSupportFragmentManager(), "PickTimeDialog");
      return;
    case 2131362099: 
      this.mIntent = new Intent(this, PurchaseHistoryProdListActivity.class);
      this.mIntent.putExtra(Long.class.getSimpleName(), this.refundableSheet.getTransID());
      startActivity(this.mIntent);
      return;
    case 2131362097: 
      this.mIntent = new Intent(this, ChooseSupplierActivity.class);
      startActivityForResult(this.mIntent, 1);
      return;
    case 2131362101: 
      this.mIntent = new Intent(this, RefundPurchaseShoppingCartActivity.class);
      startActivity(this.mIntent);
      finish();
      return;
    }
    this.purchaseDocument = new RefundPurchaseDocument();
    this.purchaseDocument.setRefundableSheet(this.refundableSheet);
    this.purchaseDocument.initRefundPurchaseShoppingCart(this.shoppingCart);
    setPaid();
    setRTVType();
    setSupplier();
    setDate();
    new Thread(new Runnable()
    {
      public void run()
      {
        RefundPurchaseActivity.this.purchaseDocument.initPurchaseDocument();
        if (RefundPurchaseManager.saveRefundPurchaseDocument(RefundPurchaseActivity.this.purchaseDocument)) {
          RefundPurchaseActivity.this.handler.obtainMessage(17).sendToTarget();
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