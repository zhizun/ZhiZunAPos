package com.zhizun.pos.ui.activity.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.PurchaseDaoImpl;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.ui.entity.GoodsFilter;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.ui.activity.BaseTitleTopBarActivity;
import com.zhizun.pos.ui.activity.CreateGoodsActivity;
import com.zhizun.pos.ui.activity.MainActivity;
import com.zhizun.pos.ui.activity.scan.BusinessType;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.adapter.PurchaseGoodsAdapter;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarFrontRightClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.GroupSeleteView;
import com.zhizun.pos.ui.widget.GroupSeleteView.GroupChangeListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import com.zhizun.pos.util.LogUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class PurchaseChooseGoodsActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, AdapterView.OnItemClickListener, View.OnClickListener, AbsListView.OnScrollListener, GroupSeleteView.GroupChangeListener, BaseTopBar.OnTopBarFrontRightClickListener, View.OnKeyListener
{
  protected static final int ADD_LIST = 2;
  protected static final int GET_LIST_SUCCESS = 1;
  private static final int INTENT_REQUEST_CODE_BARCODE = 4099;
  private static final int PageSize = 10;
  protected static final int SEARCH = 3;
  private Context context;
  private long groupId = 0L;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
      case 1: 
        do
        {
          return;
          PurchaseChooseGoodsActivity.this.mAdapter = new PurchaseGoodsAdapter(PurchaseChooseGoodsActivity.this, PurchaseChooseGoodsActivity.this.purchaseGoodsList);
        } while (PurchaseChooseGoodsActivity.this.purchaseGoodsList == null);
        PurchaseChooseGoodsActivity.this.mLsvGoods.setAdapter(PurchaseChooseGoodsActivity.this.mAdapter);
        return;
      case 2: 
        new ArrayList();
        paramAnonymousMessage = (ArrayList)paramAnonymousMessage.obj;
        PurchaseChooseGoodsActivity.this.purchaseGoodsList.addAll(paramAnonymousMessage);
        PurchaseChooseGoodsActivity.this.mAdapter.notifyDataSetChanged();
        PurchaseChooseGoodsActivity.this.dismissWaitDialog();
        return;
      }
      PurchaseChooseGoodsActivity.this.purchaseGoodsList.clear();
      PurchaseChooseGoodsActivity.this.purchaseGoodsList.add((PurchaseGoods)paramAnonymousMessage.obj);
      PurchaseChooseGoodsActivity.this.mAdapter.notifyDataSetChanged();
    }
  };
  private PurchaseGoodsAdapter mAdapter;
  private EditText mEdtSearch;
  private GoodsFilter mGoodsFilter;
  private ImageView mImgScan;
  private Intent mIntent;
  private ListView mLsvGoods;
  private CustomPopupWindow mPopStatu;
  private int mRefundIndex = 0;
  private String mSearch;
  private TextView mTxtTotalAmount;
  private TextView mTxtTotalMoney;
  private GroupSeleteView mlsvGroup;
  private int pageIndex = 1;
  PurchasesManager purchaManager = new PurchasesManager(new PurchaseDaoImpl(this, UserManager.getInstance().getCurrentUser().getMerchant_ID()));
  private ArrayList<PurchaseGoods> purchaseGoodsList;
  private String serachCondition;
  private int visibleLastIndex = 0;
  
  private void filterGoodsByGroupId(long paramLong)
  {
    this.visibleLastIndex = 0;
    this.mRefundIndex = 0;
    this.pageIndex = 1;
    this.groupId = paramLong;
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        PurchaseChooseGoodsActivity.this.purchaseGoodsList = PurchaseChooseGoodsActivity.this.purchaManager.getPurchaseGoodsByPG_ID(PurchaseChooseGoodsActivity.this.groupId, 10, PurchaseChooseGoodsActivity.this.pageIndex);
        Message localMessage = Message.obtain();
        localMessage.what = 1;
        PurchaseChooseGoodsActivity.this.handler.sendMessage(localMessage);
      }
    });
  }
  
  private void finishChoose()
  {
    startActivity(new Intent(this, PurchaseActivity.class));
  }
  
  private void getGoodsByBarCode()
  {
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        PurchaseGoods localPurchaseGoods = PurchaseChooseGoodsActivity.this.purchaManager.getPurchaseGoodsByBarCode(PurchaseChooseGoodsActivity.this.mGoodsFilter.getBarCode());
        Message localMessage = Message.obtain();
        localMessage.what = 3;
        localMessage.obj = localPurchaseGoods;
        PurchaseChooseGoodsActivity.this.handler.sendMessage(localMessage);
      }
    });
  }
  
  private void initData()
  {
    this.visibleLastIndex = 0;
    this.mRefundIndex = 0;
    this.pageIndex = 1;
    this.context = this;
    this.mGoodsFilter = new GoodsFilter();
    this.mGoodsFilter.setStockOrder();
    LogUtil.logD("store_id = " + UserManager.getInstance().getCurrentStore().getStore_ID());
    if (TextUtils.isEmpty(this.serachCondition))
    {
      this.mLsvGoods.setOnItemClickListener(this);
      this.mLsvGoods.setOnScrollListener(this);
      ExecutorUtils.getDBExecutor().execute(new Runnable()
      {
        public void run()
        {
          PurchaseChooseGoodsActivity.this.purchaseGoodsList = PurchaseChooseGoodsActivity.this.purchaManager.getPurchaseGoodsByPG_ID(0L, 10, 1);
          Message localMessage = Message.obtain();
          localMessage.what = 1;
          PurchaseChooseGoodsActivity.this.handler.sendMessage(localMessage);
        }
      });
    }
  }
  
  private void resetGoods()
  {
    this.groupId = 0L;
    if (this.purchaseGoodsList != null) {
      this.purchaseGoodsList.clear();
    }
    getGoodsByBarCode();
  }
  
  private void searchProduct(final String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      initData();
      return;
    }
    this.groupId = 0L;
    if (this.purchaseGoodsList != null) {
      this.purchaseGoodsList.clear();
    }
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        ArrayList localArrayList = PurchaseChooseGoodsActivity.this.purchaManager.getPurchaseGoodsByLikeCondtion(UserManager.getInstance().getCurrentStore().getStore_ID(), paramString);
        Message localMessage = Message.obtain();
        localMessage.what = 2;
        localMessage.obj = localArrayList;
        PurchaseChooseGoodsActivity.this.handler.sendMessage(localMessage);
      }
    });
  }
  
  private void showFilter()
  {
    ListView localListView = (ListView)getLayoutInflater().inflate(2130903178, null);
    localListView.setDivider(null);
    localListView.setBackgroundResource(2131427355);
    localListView.setAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296256)));
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        switch (paramAnonymousInt)
        {
        }
        for (;;)
        {
          if ((PurchaseChooseGoodsActivity.this.mPopStatu != null) && (PurchaseChooseGoodsActivity.this.mPopStatu.isShowing())) {
            PurchaseChooseGoodsActivity.this.mPopStatu.dismiss();
          }
          return;
          paramAnonymousAdapterView = new Intent(PurchaseChooseGoodsActivity.this.context, CreateGoodsActivity.class);
          PurchaseChooseGoodsActivity.this.startActivityForResult(paramAnonymousAdapterView, 0);
          continue;
          PurchaseChooseGoodsActivity.this.mIntent = new Intent(PurchaseChooseGoodsActivity.this, PurchaseHistoryActivity.class);
          PurchaseChooseGoodsActivity.this.startActivity(PurchaseChooseGoodsActivity.this.mIntent);
        }
      }
    });
    int i = (int)ResUtil.getDimens(2131099679);
    this.mPopStatu = new CustomPopupWindow(this, localListView, i);
    this.mPopStatu.setAnimationStyle(2131230758);
    this.mPopStatu.showMenu(this.mTopBar, getWindowManager().getDefaultDisplay().getWidth() - i, 0);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903047, paramViewGroup);
    this.mTopBar.setTitle(2131165346);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setFrontRightButton(2130837545, this);
    this.mTopBar.setRightButton(2130837525, this);
    this.mLsvGoods = ((ListView)paramViewGroup.findViewById(2131361849));
    this.mlsvGroup = ((GroupSeleteView)paramViewGroup.findViewById(2131361848));
    this.mlsvGroup.setOnGroupChangeListener(this);
    this.mTxtTotalAmount = ((TextView)paramViewGroup.findViewById(2131361846));
    this.mTxtTotalMoney = ((TextView)paramViewGroup.findViewById(2131361845));
    findViewById(2131361844).setOnClickListener(this);
    findViewById(2131361847).setOnClickListener(this);
    this.mImgScan = ((ImageView)paramViewGroup.findViewById(2131361843));
    this.mImgScan.setOnClickListener(this);
    this.mImgScan.setVisibility(8);
    this.mEdtSearch = ((EditText)paramViewGroup.findViewById(2131361842));
    this.mEdtSearch.setOnKeyListener(this);
    initData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Intent localIntent = null;
    Object localObject = localIntent;
    if (paramIntent != null)
    {
      localObject = localIntent;
      if (paramIntent.getExtras() != null) {
        localObject = (ArrayList)paramIntent.getExtras().getSerializable(PurchaseGoods.class.getSimpleName());
      }
    }
    switch (paramInt2)
    {
    default: 
      switch (paramInt1)
      {
      }
      break;
    }
    do
    {
      return;
      runOnUiThread(new Runnable()
      {
        public void run()
        {
          PurchaseChooseGoodsActivity.this.mTxtTotalAmount.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalCount()));
          PurchaseChooseGoodsActivity.this.mTxtTotalMoney.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
        }
      });
      break;
      this.mlsvGroup.refreshGroup();
      initData();
      break;
      localIntent = new Intent();
      if ((localObject != null) && (((ArrayList)localObject).size() != 0))
      {
        Bundle localBundle = new Bundle();
        localBundle.putSerializable(PurchaseGoods.class.getSimpleName(), (Serializable)localObject);
        localIntent.putExtras(localBundle);
      }
      setResult(0, localIntent);
      finish();
      break;
    } while (paramIntent == null);
    paramIntent = paramIntent.getStringExtra(String.class.getSimpleName());
    this.mGoodsFilter.setBarCode(paramIntent);
    this.mGoodsFilter.setGroup(0L);
    resetGoods();
  }
  
  public void onBackPressed()
  {
    if ((PurchasesManager.getPurchaseCart().getAllPurchaseGoods() != null) && (PurchasesManager.getPurchaseCart().getAllPurchaseGoods().size() > 0)) {
      PurchasesManager.getPurchaseCart().clear(PurchasesManager.getPurchaseCart().getAllPurchaseGoods());
    }
    finish();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131361845: 
    case 2131361846: 
    default: 
      return;
    case 2131361844: 
      this.mIntent = new Intent(this, PurchaseCartActivity.class);
      new Bundle().putSerializable(PurchaseGoods.class.getSimpleName(), PurchasesManager.getPurchaseCart().getAllPurchaseGoods());
      startActivityForResult(this.mIntent, 0);
      return;
    case 2131361847: 
      finishChoose();
      this.mEdtSearch.setText("");
      return;
    }
    this.mIntent = new Intent(this, CaptureActivity.class);
    this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.SEARCH);
    this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
    startActivityForResult(this.mIntent, 4099);
  }
  
  public void onGroupChange(long paramLong)
  {
    filterGoodsByGroupId(paramLong);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (PurchaseGoods)this.purchaseGoodsList.get(paramInt);
    PurchasesManager.getPurchaseCart().addPurchaseGoods(paramAdapterView);
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalCount()));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramView.getId())
    {
    }
    for (;;)
    {
      return false;
      if ((paramKeyEvent.getAction() == 1) && (paramInt == 66))
      {
        this.mSearch = this.mEdtSearch.getText().toString().trim();
        searchProduct(this.mSearch);
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    if (((this.mGoodsFilter.getBarCode() == null) || (this.mGoodsFilter.getBarCode().equals(""))) && (TextUtils.isEmpty(this.mEdtSearch.getText().toString().trim()))) {
      initData();
    }
    this.mTxtTotalAmount.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalCount()));
    this.mTxtTotalMoney.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
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
      if (this.mAdapter.getCount() >= this.mRefundIndex)
      {
        showWaitDialog();
        ExecutorUtils.getDBExecutor().execute(new Runnable()
        {
          public void run()
          {
            Object localObject1 = PurchaseChooseGoodsActivity.this.purchaManager;
            long l = PurchaseChooseGoodsActivity.this.groupId;
            Object localObject2 = PurchaseChooseGoodsActivity.this;
            int i = ((PurchaseChooseGoodsActivity)localObject2).pageIndex + 1;
            ((PurchaseChooseGoodsActivity)localObject2).pageIndex = i;
            localObject1 = ((PurchasesManager)localObject1).getPurchaseGoodsByPG_ID(l, 10, i);
            localObject2 = Message.obtain();
            ((Message)localObject2).what = 2;
            ((Message)localObject2).obj = localObject1;
            PurchaseChooseGoodsActivity.this.handler.sendMessage((Message)localObject2);
          }
        });
      }
    }
    else
    {
      return;
    }
    ToastUtil.toastShort(this, 2131165317);
    dismissWaitDialog();
  }
  
  public void onTopBarFrontRightClick()
  {
    LogUtils.i("扫描");
    this.mIntent = new Intent(this, CaptureActivity.class);
    this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
    this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.PURCHASE);
    startActivity(this.mIntent);
  }
  
  public void onTopBarLeftClick()
  {
    if ((PurchasesManager.getPurchaseCart().getAllPurchaseGoods() != null) && (PurchasesManager.getPurchaseCart().getAllPurchaseGoods().size() > 0)) {
      PurchasesManager.getPurchaseCart().clear(PurchasesManager.getPurchaseCart().getAllPurchaseGoods());
    }
    finish();
  }
  
  public void onTopBarRightClick()
  {
    showFilter();
  }
}