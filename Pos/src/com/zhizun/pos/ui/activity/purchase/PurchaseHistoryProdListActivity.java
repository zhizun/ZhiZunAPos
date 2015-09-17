package com.zhizun.pos.ui.activity.purchase;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.PurchaseDaoImpl;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.entities.PO_Header;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.biz.Associate;
import com.zizun.cs.ui.entity.History;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.ui.activity.BaseTitleTopBarActivity;
import com.zhizun.pos.ui.adapter.BillProdListAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class PurchaseHistoryProdListActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener
{
  private BillProdListAdapter adapter;
  private TextView billNum;
  private TextView contact;
  private TextView date;
  private History history;
  private ListView mBillProdList;
  private TextView paid;
  private TextView payable;
  private ArrayList<PurchaseGoods> purchaseGoodsList;
  private PurchasesManager purchasesManager;
  private TextView supplier;
  
  private void initData()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        PurchaseHistoryProdListActivity.this.purchasesManager = new PurchasesManager(new PurchaseDaoImpl(PurchaseHistoryProdListActivity.this, UserManager.getInstance().getCurrentUser().getMerchant_ID()));
        long l = PurchaseHistoryProdListActivity.this.getIntent().getLongExtra(Long.class.getSimpleName(), -1L);
        PurchaseHistoryProdListActivity.this.history = PurchaseHistoryProdListActivity.this.purchasesManager.getPurchaseByPo_Id(l);
        if (PurchaseHistoryProdListActivity.this.history == null) {
          return;
        }
        String str = PurchaseHistoryProdListActivity.this.history.getDate().substring(0, 19);
        PurchaseHistoryProdListActivity.this.date.setText("日期:" + str);
        PurchaseHistoryProdListActivity.this.billNum.setText("单据号:" + PurchaseHistoryProdListActivity.this.history.getBillNum());
        PurchaseHistoryProdListActivity.this.supplier.setText("往来单位:" + PurchaseHistoryProdListActivity.this.history.getAssociate().getCompany());
        PurchaseHistoryProdListActivity.this.contact.setText("联系人:" + PurchaseHistoryProdListActivity.this.history.getAssociate().getName());
        PurchaseHistoryProdListActivity.this.payable.setText("应付:" + DataUtil.getFormatString(PurchaseHistoryProdListActivity.this.history.getPo_Header().getPO_PlanAmount()));
        PurchaseHistoryProdListActivity.this.paid.setText("实付:" + DataUtil.getFormatString(PurchaseHistoryProdListActivity.this.history.getPo_Header().getPO_ActualAmount()));
        PurchaseHistoryProdListActivity.this.purchaseGoodsList = PurchaseHistoryProdListActivity.this.purchasesManager.getPurchaseGoodsList(l);
        PurchaseHistoryProdListActivity.this.adapter = new BillProdListAdapter(PurchaseHistoryProdListActivity.this, PurchaseHistoryProdListActivity.this.purchaseGoodsList);
        PurchaseHistoryProdListActivity.this.mBillProdList.setAdapter(PurchaseHistoryProdListActivity.this.adapter);
      }
    });
  }
  
  private void initView()
  {
    this.mBillProdList = ((ListView)findViewById(2131362645));
    this.date = ((TextView)findViewById(2131362615));
    this.billNum = ((TextView)findViewById(2131362614));
    this.supplier = ((TextView)findViewById(2131362616));
    this.contact = ((TextView)findViewById(2131362642));
    this.payable = ((TextView)findViewById(2131362643));
    this.paid = ((TextView)findViewById(2131362644));
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903210, paramViewGroup);
    this.mTopBar.setTitle(2131165736);
    this.mTopBar.setLeftButton(2130837526, this);
    initView();
    initData();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
}