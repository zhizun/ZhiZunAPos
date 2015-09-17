package com.zhizun.pos.ui.activity.statistics;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.ui.entity.StatisticsFilter;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.fragment.statistics.ExpenseStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.OweStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.ProductSaleStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.SaleStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.StockStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.TransDetailStatisticsFragment;
import com.zhizun.pos.ui.fragment.statistics.VIPSaleStatisticsFragment;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.Panel;
import com.zhizun.pos.ui.widget.TitleTopBar;

public class StatisticsActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, AdapterView.OnItemClickListener
{
  private static final int STATISTICS_TYPE_ACTUAL_PAY = 6;
  private static final int STATISTICS_TYPE_ACTUAL_RECEIVE = 5;
  private static final int STATISTICS_TYPE_EXPENSE = 7;
  private static final int STATISTICS_TYPE_PRODUCTSALE = 9;
  private static final int STATISTICS_TYPE_SALE = 1;
  private static final int STATISTICS_TYPE_SALE_RETURN = 2;
  private static final int STATISTICS_TYPE_STOCK = 3;
  private static final int STATISTICS_TYPE_STOCK_RETURN = 4;
  private static final int STATISTICS_TYPE_TRANSDETAIL = 16;
  private static final int STATISTICS_TYPE_VIPSALE = 8;
  private ExpenseStatisticsFragment mExpenseFragment;
  private Intent mIntent;
  private ListView mLsvPanel;
  private OweStatisticsFragment mOweFragment;
  private Panel mPanel;
  private ProductSaleStatisticsFragment mProductSaleFragment;
  private SaleStatisticsFragment mSaleFragment;
  private int mStatisticsType = 1;
  private StockStatisticsFragment mStockFragment;
  private TransDetailStatisticsFragment mTransDetailFragment;
  private VIPSaleStatisticsFragment mVIPSaleFragment;
  
  private void initView(ViewGroup paramViewGroup)
  {
    this.mSaleFragment = ((SaleStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362178));
    this.mStockFragment = ((StockStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362177));
    this.mOweFragment = ((OweStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362176));
    this.mExpenseFragment = ((ExpenseStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362175));
    this.mVIPSaleFragment = ((VIPSaleStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362174));
    this.mProductSaleFragment = ((ProductSaleStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362173));
    this.mTransDetailFragment = ((TransDetailStatisticsFragment)getSupportFragmentManager().findFragmentById(2131362172));
    this.mPanel = ((Panel)paramViewGroup.findViewById(2131362179));
    this.mLsvPanel = ((ListView)paramViewGroup.findViewById(2131362180));
    this.mLsvPanel.setAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296261)));
    this.mLsvPanel.setOnItemClickListener(this);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903096, paramViewGroup);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837635, this);
    this.mTopBar.setTitle(2131165486);
    initView(paramViewGroup);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramIntent != null))
    {
      paramIntent = (StatisticsFilter)paramIntent.getSerializableExtra(StatisticsFilter.class.getSimpleName());
      if (paramIntent != null) {}
    }
    else
    {
      return;
    }
    switch (paramInt1)
    {
    case 10: 
    case 11: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    default: 
      return;
    case 1: 
    case 2: 
      this.mSaleFragment.setCondition(paramIntent);
      return;
    case 3: 
    case 4: 
      this.mStockFragment.setCondition(paramIntent);
      return;
    case 5: 
    case 6: 
      this.mOweFragment.setCondition(paramIntent);
      return;
    case 7: 
      this.mExpenseFragment.setCondition(paramIntent);
      return;
    case 8: 
      this.mVIPSaleFragment.setCondition(paramIntent);
      return;
    case 9: 
      this.mProductSaleFragment.setCondition(paramIntent);
      return;
    }
    this.mTransDetailFragment.setCondition(paramIntent);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = getSupportFragmentManager().beginTransaction();
    paramAdapterView.hide(this.mSaleFragment).hide(this.mStockFragment).hide(this.mOweFragment).hide(this.mExpenseFragment).hide(this.mVIPSaleFragment).hide(this.mProductSaleFragment).hide(this.mTransDetailFragment);
    this.mTopBar.setTitle((String)this.mLsvPanel.getAdapter().getItem(paramInt));
    switch (paramInt)
    {
    }
    for (;;)
    {
      paramAdapterView.commit();
      this.mPanel.setOpen(false, true);
      return;
      paramAdapterView.show(this.mSaleFragment);
      this.mStatisticsType = 1;
      this.mSaleFragment.showSaleType();
      continue;
      paramAdapterView.show(this.mSaleFragment);
      this.mStatisticsType = 2;
      this.mSaleFragment.showSaleReturnType();
      continue;
      paramAdapterView.show(this.mStockFragment);
      this.mStatisticsType = 3;
      this.mStockFragment.showStockType();
      continue;
      paramAdapterView.show(this.mStockFragment);
      this.mStatisticsType = 4;
      this.mStockFragment.showStockReturnType();
      continue;
      paramAdapterView.show(this.mOweFragment);
      this.mStatisticsType = 5;
      this.mOweFragment.showReceiveType();
      continue;
      paramAdapterView.show(this.mOweFragment);
      this.mStatisticsType = 6;
      this.mOweFragment.showPayType();
      continue;
      paramAdapterView.show(this.mExpenseFragment);
      this.mStatisticsType = 7;
      this.mExpenseFragment.showReceiveType();
      continue;
      paramAdapterView.show(this.mVIPSaleFragment);
      this.mStatisticsType = 8;
      this.mVIPSaleFragment.show();
      continue;
      paramAdapterView.show(this.mProductSaleFragment);
      this.mStatisticsType = 9;
      this.mProductSaleFragment.show();
      continue;
      paramAdapterView.show(this.mTransDetailFragment);
      this.mStatisticsType = 16;
      this.mTransDetailFragment.show();
    }
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    if ((this.mStatisticsType == 5) || (this.mStatisticsType == 6)) {
      this.mIntent = new Intent(this, StatisticsFilterOweActivity.class);
    }
    for (;;)
    {
      startActivityForResult(this.mIntent, this.mStatisticsType);
      return;
      if (this.mStatisticsType == 7)
      {
        this.mIntent = new Intent(this, StatisticsFilterExpenseActivity.class);
      }
      else if (this.mStatisticsType == 16)
      {
        this.mIntent = new Intent(this, StatisticsFilterTransDetailActivity.class);
      }
      else
      {
        this.mIntent = new Intent(this, StatisticsFilterBusinessActivity.class);
        if ((this.mStatisticsType == 1) || (this.mStatisticsType == 2) || (this.mStatisticsType == 8) || (this.mStatisticsType == 9)) {
          this.mIntent.putExtra("requestCode", 2);
        } else if ((this.mStatisticsType == 3) || (this.mStatisticsType == 4)) {
          this.mIntent.putExtra("requestCode", 3);
        }
      }
    }
  }
}