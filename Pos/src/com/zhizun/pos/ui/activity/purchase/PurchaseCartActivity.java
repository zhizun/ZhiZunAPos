package com.zhizun.pos.ui.activity.purchase;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.zizun.cs.common.utils.NumUtil;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.adapter.PurchaseCartAdapter;
import com.zhizun.pos.ui.adapter.PurchaseCartAdapter.OnGoodsChangeListener;
import com.zhizun.pos.ui.adapter.PurchaseCartAdapter.OnGoodsChangeListenerAddOrSub;
import com.zhizun.pos.ui.adapter.PurchaseCartAdapter.OnGoodsStockPriceChangeListener;
import com.zhizun.pos.ui.dialog.EditCartItemDialog;
import com.zhizun.pos.ui.dialog.EditCartItemDialog.OnGoodsEditListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class PurchaseCartActivity
  extends BaseFragmentTitleTopBarActivity
  implements AdapterView.OnItemClickListener, BaseTopBar.OnTopBarLeftClickListener, PurchaseCartAdapter.OnGoodsChangeListener, PurchaseCartAdapter.OnGoodsChangeListenerAddOrSub, PurchaseCartAdapter.OnGoodsStockPriceChangeListener, View.OnClickListener
{
  private TextView activity_choose_goods_txtAmount;
  private TextView activity_choose_goods_txtMoney;
  private Button btn_aty_sales_receive_pay;
  private PurchaseCartAdapter mAdapter;
  private ListView mLsvGoods;
  private double mTotalAmount;
  private double mTotalMoney;
  private TextView mTxtTotalAmount;
  private TextView mTxtTotalMoney;
  
  private void finishChoose(int paramInt)
  {
    setResult(paramInt);
    finish();
  }
  
  private void initData()
  {
    this.mAdapter = new PurchaseCartAdapter(this, PurchasesManager.getPurchaseCart());
    this.mLsvGoods.setAdapter(this.mAdapter);
    this.mAdapter.setOnGoodsChangeListener(this);
    this.mAdapter.setOnGoodsChangeListenerAddOrSub(this);
    this.mAdapter.setOnGoodsStockPriceChangeListener(this);
    this.mLsvGoods.setOnItemClickListener(this);
    this.mTotalAmount = PurchasesManager.getPurchaseCart().getTotalCount();
    this.mTotalMoney = PurchasesManager.getPurchaseCart().getTotalMoney();
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(this.mTotalAmount));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(this.mTotalMoney));
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903079, paramViewGroup);
    this.mTopBar.setTitle(2131165369);
    this.mTopBar.setLeftButton(2130837526, this);
    findViewById(2131361824).setOnClickListener(this);
    this.btn_aty_sales_receive_pay = ((Button)findViewById(2131362584));
    this.btn_aty_sales_receive_pay.setText(2131165362);
    this.btn_aty_sales_receive_pay.setTextColor(getResources().getColor(2131427339));
    this.btn_aty_sales_receive_pay.setOnClickListener(this);
    this.mLsvGoods = ((ListView)paramViewGroup.findViewById(2131361827));
    this.mTxtTotalAmount = ((TextView)paramViewGroup.findViewById(2131361826));
    this.mTxtTotalMoney = ((TextView)paramViewGroup.findViewById(2131361825));
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
    default: 
      return;
    case 2131361824: 
      finish();
      return;
    }
    paramView = new Intent(this, PurchaseActivity.class);
    paramView.putExtra("BACK_FROM_CART", "BACK_FROM_CART");
    startActivity(paramView);
    finish();
  }
  
  public void onGoodsChange(double paramDouble1, double paramDouble2)
  {
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(paramDouble1));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(paramDouble2));
  }
  
  public void onGoodsChangeAddOrSub(double paramDouble1, double paramDouble2)
  {
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(paramDouble1));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(paramDouble2));
  }
  
  public void onItemClick(final AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(paramInt);
    paramView = new EditCartItemDialog();
    paramView.setPrice(paramAdapterView.getPurchase_Price());
    paramView.setQuantity(paramAdapterView.getChooseAmount());
    paramView.setOnGoodsEditListener(new EditCartItemDialog.OnGoodsEditListener()
    {
      public void onItemChange(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        paramAdapterView.setPurchase_Price(NumUtil.getNum(paramAnonymousDouble1));
        paramAdapterView.setChooseAmount(NumUtil.getNum(paramAnonymousDouble2));
        PurchaseCartActivity.this.onGoodsChange(PurchasesManager.getPurchaseCart().getTotalCount(), PurchasesManager.getPurchaseCart().getTotalMoney());
        if (paramAnonymousDouble2 == 0.0D) {
          PurchasesManager.getPurchaseCart().getAllPurchaseGoods().remove(paramAdapterView);
        }
        PurchaseCartActivity.this.mAdapter.notifyDataSetChanged();
      }
    });
    paramView.show(getSupportFragmentManager(), EditCartItemDialog.class.getSimpleName());
  }
  
  public void onPriceChange(double paramDouble1, double paramDouble2)
  {
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(paramDouble1));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(paramDouble2));
  }
  
  public void onTopBarLeftClick()
  {
    finishChoose(4);
  }
}