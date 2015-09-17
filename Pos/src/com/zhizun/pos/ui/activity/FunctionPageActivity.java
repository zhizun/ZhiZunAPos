package com.zhizun.pos.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.yumendian.cs.activity.manager.UserManager;
import com.yumendian.cs.common.utils.ToastUtil;
import com.yunmendian.pos.ui.activity.expense.ExpenseActivity;
import com.yunmendian.pos.ui.activity.stockinorout.StockInOrOutActivity;
import com.yunmendian.pos.ui.activity.transfer.TransferActivity;
import com.yunmendian.pos.ui.adapter.FunctionPageAdapter;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.yunmendian.pos.ui.widget.TitleTopBar;

public class FunctionPageActivity
  extends BaseFragmentTitleTopBarActivity
  implements AdapterView.OnItemClickListener, BaseTopBar.OnTopBarLeftClickListener
{
  private boolean IsStockOut = true;
  private Context context;
  private Intent intent;
  private boolean isExperienceAccount = UserManager.getInstance().isExperienceAccount();
  private ListView mListView;
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903179, paramViewGroup);
    this.mTopBar.setTitle(2131165587);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mListView = ((ListView)paramViewGroup.findViewById(2131362568));
    this.mListView.setAdapter(new FunctionPageAdapter());
    this.mListView.setOnItemClickListener(this);
    this.context = this;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (((com.yumendian.cs.ui.entity.Module)this.mListView.getAdapter().getItem(paramInt)).getModule_Code())
    {
    case Vip: 
    default: 
      return;
    case Staff: 
      startActivity(new Intent(this, ExpenseActivity.class));
      return;
    case Vendor: 
      this.intent = new Intent(this, GroupActivity.class);
      startActivity(this.intent);
      return;
    case Stock: 
      startActivity(new Intent(this, TransferActivity.class));
      return;
    case StockIn: 
      this.intent = new Intent(this, StockInOrOutActivity.class);
      this.intent.putExtra("STOCKTYPE", this.IsStockOut);
      startActivity(this.intent);
      return;
    case StockOut: 
      this.intent = new Intent(this, StockInOrOutActivity.class);
      paramAdapterView = this.intent;
      if (this.IsStockOut) {}
      for (boolean bool = false;; bool = true)
      {
        paramAdapterView.putExtra("STOCKTYPE", bool);
        startActivity(this.intent);
        return;
      }
    case Store: 
      if (this.isExperienceAccount)
      {
        ToastUtil.toastLong(this.context, 2131165569);
        return;
      }
      this.intent = new Intent(this, StoreManagerActivity.class);
      startActivity(this.intent);
      return;
    case Transfer: 
      if (this.isExperienceAccount)
      {
        ToastUtil.toastLong(this.context, 2131165568);
        return;
      }
      this.intent = new Intent(this, StaffManagerActivity.class);
      startActivity(this.intent);
      return;
    }
    if (this.isExperienceAccount)
    {
      ToastUtil.toastLong(this.context, 2131165569);
      return;
    }
    this.intent = new Intent(this, RoleManagerActivity.class);
    startActivity(this.intent);
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\activity\FunctionPageActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */