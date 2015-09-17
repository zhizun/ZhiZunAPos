package com.zhizun.pos.ui.activity.refund;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zizun.cs.biz.refund.manager.RefundManager;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ToastUtil;
import com.zhizun.pos.ui.activity.BaseTitleTopBarActivity;
import com.zhizun.pos.ui.activity.refund.purchase.RefundPurchaseActivity;
import com.zhizun.pos.ui.activity.refund.sales.RefundSalesActivity;
import com.zhizun.pos.ui.adapter.refund.RefundAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class RefundActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener
{
  private static final int ADD_LIST_COMPLETE = 18;
  private static final int INIT_LIST_COMPLETE = 17;
  private static final int PageSize = 10;
  private RefundAdapter mAdapter;
  private Handler mHandler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        RefundActivity.this.setAdapter();
        continue;
        paramAnonymousMessage = (ArrayList)paramAnonymousMessage.obj;
        if (paramAnonymousMessage != null)
        {
          RefundActivity.this.mList.addAll(paramAnonymousMessage);
          RefundActivity.this.mAdapter.notifyDataSetChanged();
        }
        RefundActivity.this.setAdapter();
      }
    }
  });
  private Intent mIntent;
  private ArrayList<RefundableSheet> mList;
  private ListView mLsv;
  private int mRefundIndex = 0;
  private int pageIndex = 1;
  private RefundableSheet selectedRefundableSheet;
  private int visibleLastIndex = 0;
  
  private void StartActivityForRefund(Class<? extends Activity> paramClass)
  {
    this.mIntent = new Intent(this, paramClass);
    paramClass = new Bundle();
    paramClass.putSerializable(RefundableSheet.class.getSimpleName(), this.selectedRefundableSheet);
    this.mIntent.putExtra(Bundle.class.getSimpleName(), paramClass);
    startActivity(this.mIntent);
  }
  
  private void addRefundableSheets()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Object localObject = RefundActivity.this;
        int i = ((RefundActivity)localObject).pageIndex + 1;
        ((RefundActivity)localObject).pageIndex = i;
        localObject = RefundManager.getAllRefundableSheetsOutUIThread(10, i);
        RefundActivity.this.mHandler.obtainMessage(18, localObject).sendToTarget();
      }
    });
  }
  
  private void initBaseData()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        RefundActivity.this.mList = RefundManager.getAllRefundableSheetsOutUIThread(10, 1);
        RefundActivity.this.mHandler.obtainMessage(17).sendToTarget();
      }
    });
  }
  
  private void setAdapter()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    this.mAdapter = new RefundAdapter(this.mList);
    this.mLsv.setAdapter(this.mAdapter);
    this.mLsv.setOnItemClickListener(this);
    this.mAdapter.notifyDataSetChanged();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903185, paramViewGroup);
    this.mTopBar.setTitle(2131165363);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837549, this);
    this.mLsv = ((ListView)paramViewGroup.findViewById(2131362591));
    this.mLsv.setOnScrollListener(this);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.selectedRefundableSheet = ((RefundableSheet)this.mList.get(paramInt));
    paramAdapterView = this.selectedRefundableSheet.getTransType();
    if ((paramAdapterView.equals("SX")) || (paramAdapterView.equals("WX"))) {
      StartActivityForRefund(RefundSalesActivity.class);
    }
    while (!paramAdapterView.equals("PX")) {
      return;
    }
    StartActivityForRefund(RefundPurchaseActivity.class);
  }
  
  protected void onResume()
  {
    LogUtils.i("onResume");
    this.visibleLastIndex = 0;
    this.mRefundIndex = 0;
    this.pageIndex = 1;
    initBaseData();
    super.onResume();
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.visibleLastIndex = (paramInt1 + paramInt2 - 1);
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    int i = this.mAdapter.getCount();
    if ((paramInt == 0) && (this.visibleLastIndex == i - 1))
    {
      this.mRefundIndex = (this.pageIndex * 10);
      if (this.mAdapter.getCount() >= this.mRefundIndex) {
        addRefundableSheets();
      }
    }
    else
    {
      return;
    }
    ToastUtil.toastShort(this, 2131165317);
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    startActivity(new Intent(this, RefundHistoryActivity.class));
  }
}