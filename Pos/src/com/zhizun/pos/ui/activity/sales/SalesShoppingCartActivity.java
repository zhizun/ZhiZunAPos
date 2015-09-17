package com.zhizun.pos.ui.activity.sales;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zizun.cs.biz.action.sales.SaveSalesDocumentAction;
import com.zizun.cs.biz.action.sales.SaveSalesDocumentAction.OnSaveSalesDocumentActionListener;
import com.zizun.cs.biz.dao.PaymentMethodDao;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.biz.sale.SalesShoppingCart;
import com.zizun.cs.biz.sale.V_ProductSkuOnSale;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.PaymentMethod;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.receipt.ReceiptPreViewActivity;
import com.zhizun.pos.ui.adapter.sales.SalesShoppingCartAdapter;
import com.zhizun.pos.ui.adapter.sales.SalesShoppingCartAdapter.OnGoodsChangeListener;
import com.zhizun.pos.ui.dialog.EditCartItemDialog;
import com.zhizun.pos.ui.dialog.EditCartItemDialog.OnGoodsEditListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SalesShoppingCartActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, SalesShoppingCartAdapter.OnGoodsChangeListener, AdapterView.OnItemClickListener, View.OnClickListener, SaveSalesDocumentAction.OnSaveSalesDocumentActionListener
{
  private static final int GET_DEFULT_PAYMENT_METHOD_COMPLETE = 18;
  private static final int SALES_DOCUMENT_SAVE_SUCCESS = 17;
  private Context context;
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
        SalesShoppingCartActivity.this.pay(l);
        continue;
        SalesShoppingCartActivity.this.mBtnReceivePay.setEnabled(true);
        SalesShoppingCartActivity.this.clearViewData();
        SalesShoppingCartActivity.this.sendWXReceipt();
        SalesShoppingCartActivity.this.finish();
      }
    }
  });
  private SalesShoppingCartAdapter mAdapter;
  private Button mBtnReceivePay;
  private Intent mIntent;
  private ArrayList<V_ProductSkuOnSale> mListGoods;
  private LinearLayout mLlTotalBar;
  private ListView mLsvGoods;
  private TextView mTxtTotalAmount;
  private TextView mTxtTotalMoney;
  private SalesShoppingCart salesShoppingCart;
  private long transID = -1L;
  
  private void calculateTotal()
  {
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(this.salesShoppingCart.getTotalCount()));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(this.salesShoppingCart.getTotalMoney()));
  }
  
  private void clearViewData()
  {
    this.mTxtTotalAmount.setText("0");
    this.mTxtTotalMoney.setText("0");
    this.mListGoods.clear();
    SalesManager.clearData();
  }
  
  private void findViews(ViewGroup paramViewGroup)
  {
    this.mLsvGoods = ((ListView)paramViewGroup.findViewById(2131361827));
    this.mTxtTotalAmount = ((TextView)paramViewGroup.findViewById(2131361826));
    this.mTxtTotalMoney = ((TextView)paramViewGroup.findViewById(2131361825));
    this.mLlTotalBar = ((LinearLayout)paramViewGroup.findViewById(2131362587));
    this.mLlTotalBar.setVisibility(4);
    findViewById(2131361844).setOnClickListener(this);
    findViewById(2131362584).setOnClickListener(this);
    this.mBtnReceivePay = ((Button)findViewById(2131362589));
    this.mBtnReceivePay.setOnClickListener(this);
  }
  
  private void getPaymentMethod()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Object localObject = SalesManager.getInstance().getPaymentMethodDao();
        localObject = (PaymentMethod)SalesManager.getInstance().getPaymentMethodListOutUIThread((PaymentMethodDao)localObject).get(0);
        SalesShoppingCartActivity.this.handler.obtainMessage(18, Long.valueOf(((PaymentMethod)localObject).getPaymentMethod_ID())).sendToTarget();
      }
    });
  }
  
  private boolean hasShoppingCartItem()
  {
    ArrayList localArrayList = this.salesShoppingCart.getSaleShoppingCartItemList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
    {
      ToastUtil.toastLong(this.context, "请先选择商品");
      return false;
    }
    return true;
  }
  
  private void initData()
  {
    this.salesShoppingCart = SalesManager.getInstance().getSalesShoppingCart();
    if (this.salesShoppingCart == null)
    {
      this.salesShoppingCart = new SalesShoppingCart(SalesType.SALE);
      SalesManager.setSalesShoppingCart(this.salesShoppingCart);
    }
    this.mListGoods = this.salesShoppingCart.getSaleShoppingCartItemList();
    this.mAdapter = new SalesShoppingCartAdapter(this, this.salesShoppingCart);
    this.mLsvGoods.setAdapter(this.mAdapter);
    this.mLsvGoods.setOnItemClickListener(this);
    this.mAdapter.setOnGoodsChangeListener(this);
    this.mAdapter.notifyDataSetChanged();
    calculateTotal();
  }
  
  private void pay(long paramLong)
  {
    SaveSalesDocumentAction localSaveSalesDocumentAction = new SaveSalesDocumentAction(this.salesShoppingCart, paramLong);
    localSaveSalesDocumentAction.setOnSaveSalesDocumentActionListener(this);
    localSaveSalesDocumentAction.execute();
  }
  
  private void receivePay()
  {
    if (!hasShoppingCartItem()) {
      return;
    }
    this.mBtnReceivePay.setEnabled(false);
    getPaymentMethod();
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
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903045, paramViewGroup);
    this.mTopBar.setTitle(2131165369);
    this.mTopBar.setLeftButton(2130837526, this);
    this.context = this;
    findViews(paramViewGroup);
    initData();
  }
  
  public void onBackPressed()
  {
    finish();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    do
    {
      return;
      this.mIntent = new Intent(this.context, SalesProuductSelActivity.class);
      startActivity(this.mIntent);
      finish();
      return;
      receivePay();
      return;
    } while (!hasShoppingCartItem());
    this.mIntent = new Intent(this.context, SalesActivity.class);
    startActivity(this.mIntent);
    finish();
  }
  
  public void onGoodsChange(double paramDouble1, double paramDouble2)
  {
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(paramDouble1));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(paramDouble2));
  }
  
  public void onItemClick(final AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (V_ProductSkuOnSale)this.mListGoods.get(paramInt);
    paramView = new EditCartItemDialog();
    paramView.setPrice(paramAdapterView.getPricePromotional());
    paramView.setQuantity(paramAdapterView.getQuantiy());
    paramView.setOnGoodsEditListener(new EditCartItemDialog.OnGoodsEditListener()
    {
      public void onItemChange(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        paramAdapterView.setPricePromotional(NumUtil.getNum(paramAnonymousDouble1));
        paramAdapterView.setQuantiy(NumUtil.getNum(paramAnonymousDouble2));
        SalesShoppingCartActivity.this.onGoodsChange(SalesShoppingCartActivity.this.salesShoppingCart.getTotalCount(), SalesShoppingCartActivity.this.salesShoppingCart.getTotalMoney());
        if (paramAnonymousDouble2 == 0.0D) {
          SalesShoppingCartActivity.this.mListGoods.remove(paramAdapterView);
        }
        SalesShoppingCartActivity.this.mAdapter.notifyDataSetChanged();
      }
    });
    paramView.show(getSupportFragmentManager(), EditCartItemDialog.class.getSimpleName());
  }
  
  public void onSaveSalesDocumentFail(String paramString) {}
  
  public void onSaveSalesDocumentSuccess(long paramLong)
  {
    this.transID = paramLong;
    if (paramLong > 0L) {
      this.handler.obtainMessage(17).sendToTarget();
    }
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    finish();
  }
}