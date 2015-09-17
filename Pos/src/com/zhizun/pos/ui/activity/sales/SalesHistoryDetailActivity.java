package com.zhizun.pos.ui.activity.sales;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.bloothprint.PrintManager;
import com.zizun.cs.biz.receipt.SalesReceipt;
import com.zizun.cs.biz.refund.V_SOHeader;
import com.zizun.cs.biz.sale.SalesHistoryItemDetail;
import com.zizun.cs.biz.sale.SalesManager;
import com.zizun.cs.biz.sale.V_TransactionHeader;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ResUtil;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.bluetoothPrint.BluePrintActivity;
import com.zhizun.pos.ui.adapter.sales.SalesHistoryDetailAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class SalesHistoryDetailActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener
{
  private static final int FINISH_ACTIVITY = 3;
  private static final int GET_DATA_SUCCESS = 17;
  private ListView SalesHistoryItemDetailListView;
  private BluetoothDevice currentDevice;
  private SalesHistoryDetailAdapter detailAdapter;
  private Handler handler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      for (;;)
      {
        return false;
        SalesHistoryDetailActivity.this.refreshView();
        continue;
        SalesHistoryDetailActivity.this.finish();
      }
    }
  });
  private V_SOHeader header;
  private ArrayList<SalesHistoryItemDetail> historyItemDetails;
  private SalesReceipt receipt;
  private long transID;
  private TextView tvBillNumber;
  private TextView tvCompany;
  private TextView tvContact;
  private TextView tvDate;
  private TextView tvDiscountAmount;
  private TextView tvDiscountPer;
  private TextView tvMoneyAct;
  private TextView tvMoneyPlan;
  
  private void getSalesReceipt()
  {
    if (this.transID > 0L)
    {
      V_TransactionHeader localV_TransactionHeader = SalesManager.getV_TransactionHeaderOutUIThread(this.transID);
      ArrayList localArrayList = (ArrayList)SalesManager.getV_TransactionDetailListOutUIThread(this.transID);
      if (localV_TransactionHeader != null)
      {
        this.receipt = new SalesReceipt();
        this.receipt.setHeader(localV_TransactionHeader);
        this.receipt.setDetailList(localArrayList);
      }
    }
  }
  
  private void initData()
  {
    this.transID = getIntent().getLongExtra(Long.class.getSimpleName(), 0L);
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        SalesHistoryDetailActivity.this.header = SalesManager.getSalesHistorySO_header(SalesHistoryDetailActivity.this.transID);
        SalesHistoryDetailActivity.this.historyItemDetails = ((ArrayList)SalesManager.getSalesHistoryItemDetailOutOfThread(SalesHistoryDetailActivity.this.transID));
        SalesHistoryDetailActivity.this.handler.obtainMessage(17).sendToTarget();
      }
    });
  }
  
  private void initView()
  {
    this.tvDate = ((TextView)findViewById(2131362359));
    this.tvBillNumber = ((TextView)findViewById(2131362360));
    this.tvCompany = ((TextView)findViewById(2131362361));
    this.tvContact = ((TextView)findViewById(2131362362));
    this.tvDiscountPer = ((TextView)findViewById(2131362394));
    this.tvDiscountAmount = ((TextView)findViewById(2131362395));
    this.tvMoneyPlan = ((TextView)findViewById(2131362396));
    this.tvMoneyAct = ((TextView)findViewById(2131362397));
    this.SalesHistoryItemDetailListView = ((ListView)findViewById(2131362365));
  }
  
  private void refreshView()
  {
    this.tvDate.setText(ResUtil.getFormatString(2131165382, this.header.getSO_Date()));
    this.tvBillNumber.setText(ResUtil.getFormatString(2131165381, this.header.getSO_Number()));
    this.tvCompany.setText(ResUtil.getFormatString(2131165383, this.header.getVIP_Name()));
    this.tvContact.setText(ResUtil.getFormatString(2131165385, this.header.getVIP_Contact()));
    this.tvDiscountPer.setText(ResUtil.getFormatString(2131165386, DataUtil.getFormatString(this.header.getDiscount_Percent())));
    this.tvDiscountAmount.setText(ResUtil.getFormatString(2131165387, DataUtil.getFormatString(this.header.getDiscount_Amount())));
    this.tvMoneyPlan.setText(ResUtil.getFormatString(2131165390, DataUtil.getFormatString(this.header.getSO_PlanAmount())));
    this.tvMoneyAct.setText(ResUtil.getFormatString(2131165391, DataUtil.getFormatString(this.header.getSO_ActualAmount())));
    setAdapter();
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903125, paramViewGroup);
    this.mTopBar.setTitle(2131165380);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837695, this);
    initView();
    initData();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    printData();
  }
  
  public void printData()
  {
    this.currentDevice = PrintManager.getInstance().getCurrentRemoteDevice();
    getSalesReceipt();
    if (this.receipt == null) {
      return;
    }
    if (this.currentDevice != null)
    {
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          OutputStream localOutputStream = PrintManager.getOutputStreamOutUIThread(SalesHistoryDetailActivity.this.currentDevice);
          if ((localOutputStream != null) && (SalesHistoryDetailActivity.this.receipt != null)) {
            SalesHistoryDetailActivity.this.receipt.printOutUIThread(localOutputStream, 1);
          }
          PrintManager.closeBluetoothPrintStream();
          LogUtils.i("打印完成");
          SalesHistoryDetailActivity.this.handler.obtainMessage(3).sendToTarget();
        }
      });
      return;
    }
    startActivityForPrint(this.receipt);
    this.handler.obtainMessage(3).sendToTarget();
  }
  
  public void setAdapter()
  {
    this.detailAdapter = new SalesHistoryDetailAdapter(this, this.historyItemDetails);
    this.SalesHistoryItemDetailListView.setAdapter(this.detailAdapter);
  }
  
  public void startActivityForPrint(SalesReceipt paramSalesReceipt)
  {
    Intent localIntent = new Intent(this, BluePrintActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable(SalesReceipt.class.getSimpleName(), paramSalesReceipt);
    localIntent.putExtras(localBundle);
    startActivity(localIntent);
  }
}