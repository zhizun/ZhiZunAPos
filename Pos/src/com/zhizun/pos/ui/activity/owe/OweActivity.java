package com.zhizun.pos.ui.activity.owe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.zizun.cs.activity.manager.VoucherManager;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.biz.V_HeaderForPay;
import com.zhizun.pos.ui.activity.BaseRadioTopBarFragmentActivity;
import com.zhizun.pos.ui.activity.purchase.PurchaseHistoryProdListActivity;
import com.zhizun.pos.ui.activity.sales.SalesHistoryDetailActivity;
import com.zhizun.pos.ui.adapter.OweAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarFrontRightClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.RadioTopBar;
import com.zhizun.pos.ui.widget.RadioTopBar.OnTopBarOptionChangeListener;
import java.util.ArrayList;

public class OweActivity
  extends BaseRadioTopBarFragmentActivity
  implements RadioTopBar.OnTopBarOptionChangeListener, BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, BaseTopBar.OnTopBarFrontRightClickListener, AdapterView.OnItemClickListener
{
  private Intent mIntent;
  private boolean mIsReceiveStatus = true;
  private ArrayList<V_HeaderForPay> mListPay;
  private ListView mLsvOrder;
  private OweAdapter mOweAdapter;
  
  private void refreshData()
  {
    new getPayListSync(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
  }
  
  private void resetData()
  {
    if (this.mListPay != null) {
      this.mListPay.clear();
    }
  }
  
  private void setAdapter()
  {
    this.mOweAdapter = new OweAdapter(this.mListPay);
    this.mLsvOrder.setAdapter(this.mOweAdapter);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903075, paramViewGroup);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837754, this);
    this.mTopBar.setFrontRightButton(2130837549, this);
    this.mTopBar.setOnOptionChangeListener(this);
    this.mTopBar.setLeftOptionText(2131165303);
    this.mTopBar.setRightOptionText(2131165304);
    this.mLsvOrder = ((ListView)paramViewGroup.findViewById(2131362051));
    this.mLsvOrder.setOnItemClickListener(this);
    refreshData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt2)
    {
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      refreshData();
    }
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (V_HeaderForPay)this.mListPay.get(paramInt);
    if ((paramAdapterView.getTransType().equals("PX")) || (paramAdapterView.getTransType().equals("PR"))) {}
    for (this.mIntent = new Intent(this, PurchaseHistoryProdListActivity.class);; this.mIntent = new Intent(this, SalesHistoryDetailActivity.class))
    {
      this.mIntent.putExtra(Long.class.getSimpleName(), paramAdapterView.getTransID());
      startActivity(this.mIntent);
      return;
    }
  }
  
  public void onTopBarFrontRightClick()
  {
    if (this.mIsReceiveStatus) {}
    for (this.mIntent = new Intent(this, ReceiveHistoryActivity.class);; this.mIntent = new Intent(this, PayHistoryActivity.class))
    {
      startActivityForResult(this.mIntent, 0);
      return;
    }
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarLeftOptionSelect()
  {
    this.mIsReceiveStatus = true;
    refreshData();
  }
  
  public void onTopBarRightClick()
  {
    if (this.mOweAdapter.getChooseOwe().size() > 0)
    {
      if (this.mIsReceiveStatus) {}
      for (this.mIntent = new Intent(this, ReceiveActivity.class);; this.mIntent = new Intent(this, PayActivity.class))
      {
        Bundle localBundle = new Bundle();
        localBundle.putSerializable(V_HeaderForPay.class.getSimpleName(), this.mOweAdapter.getChooseOwe());
        this.mIntent.putExtras(localBundle);
        startActivityForResult(this.mIntent, 0);
        return;
      }
    }
    ToastUtil.toastLong(this, 2131165459);
  }
  
  public void onTopBarRightOptionSelect()
  {
    this.mIsReceiveStatus = false;
    refreshData();
  }
  
  private class getPayListSync
    extends AsyncTask<Void, Void, Void>
  {
    private getPayListSync() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      if (OweActivity.this.mIsReceiveStatus) {}
      for (OweActivity.this.mListPay = VoucherManager.getOweReceiveList();; OweActivity.this.mListPay = VoucherManager.getOwePayList()) {
        return null;
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      OweActivity.this.dismissWaitDialog();
      OweActivity.this.setAdapter();
      super.onPostExecute(paramVoid);
    }
    
    protected void onPreExecute()
    {
      OweActivity.this.showWaitDialog();
      super.onPreExecute();
    }
  }
}