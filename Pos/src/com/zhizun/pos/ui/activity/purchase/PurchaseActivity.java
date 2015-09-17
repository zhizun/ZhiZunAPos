package com.zhizun.pos.ui.activity.purchase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lidroid.xutils.util.LogUtils;
import com.yumendian.cs.activity.manager.UserManager;
import com.yumendian.cs.biz.dao.impl.PurchaseDaoImpl;
import com.yumendian.cs.biz.dao.trans.impl.PurchaseTransImpl;
import com.yumendian.cs.biz.dao.utils.IDUtil;
import com.yumendian.cs.common.utils.DateTimeUtil;
import com.yumendian.cs.common.utils.ExecutorUtils;
import com.yumendian.cs.common.utils.ToastUtil;
import com.yumendian.cs.entities.PO_Detail;
import com.yumendian.cs.entities.PO_Header;
import com.yumendian.cs.entities.S_User;
import com.yumendian.cs.entities.Store;
import com.yumendian.cs.entities.TransactionLog;
import com.yumendian.cs.entities.biz.Associate;
import com.yumendian.cs.ui.entity.PurchaseGoods;
import com.yunmendian.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.yunmendian.pos.ui.activity.MainActivity;
import com.yunmendian.pos.ui.activity.scan.BusinessType;
import com.yunmendian.pos.ui.activity.scan.CaptureActivity;
import com.yunmendian.pos.ui.adapter.StockAdapter;
import com.yunmendian.pos.ui.dialog.PickTimeDialog;
import com.yunmendian.pos.ui.dialog.PickTimeDialog.onDatePickListener;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.yunmendian.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.yunmendian.pos.ui.widget.InterceptListView;
import com.yunmendian.pos.ui.widget.TitleTopBar;
import com.yunmendian.pos.util.DataUtil;
import com.yunmendian.pos.util.ResUtil;
import com.yunmendian.pos.util.TextViewUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class PurchaseActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnClickListener
{
  private static final int INTENT_REQUEST_CODE_GOODS = 2;
  private static final int INTENT_REQUEST_CODE_SUPPLIER = 1;
  protected static final int SUCCESS = 10;
  protected static final int SUCCESS_INSERT = 20;
  private int MerchantID;
  private long PO_ID;
  private StockAdapter adapter;
  ArrayList<PurchaseGoods> allPurchaseGoods;
  private Associate associate;
  @SuppressLint({"HandlerLeak"})
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      int i = paramAnonymousMessage.what;
      if (paramAnonymousMessage.what == 20)
      {
        if (((Boolean)paramAnonymousMessage.obj).booleanValue())
        {
          ToastUtil.toastLong(PurchaseActivity.this, "单据生成成功！");
          PurchaseActivity.this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
          PurchaseActivity.this.mTxtSupplier.setText("");
          PurchaseActivity.this.mTxtPayable.setText("");
          PurchaseActivity.this.mEdtPaid.setText("");
          PurchasesManager.getPurchaseCart().clear(PurchasesManager.getPurchaseCart().getAllPurchaseGoods());
          PurchaseActivity.this.adapter.notifyDataSetChanged();
          PurchaseActivity.this.finish();
        }
      }
      else {
        return;
      }
      ToastUtil.toastLong(PurchaseActivity.this, "对不起,单据生成失败！");
    }
  };
  private EditText mEdtPaid;
  private Intent mIntent;
  private InterceptListView mLsvGoods;
  private ScrollView mScvContainer;
  private TextView mTxtPayable;
  private TextView mTxtSupplier;
  private TextView mTxtTime;
  private PO_Detail[] po_Detail;
  private PO_Header po_Header;
  private long storeId;
  private TransactionLog[] transactionLog;
  private int userId = UserManager.getInstance().getCurrentUser().getUser_ID();
  
  private void doTrans()
  {
    Message localMessage = this.handler.obtainMessage();
    localMessage.what = 20;
    int i = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    localMessage.obj = Boolean.valueOf(new PurchasesManager(new PurchaseDaoImpl(this, i), new PurchaseTransImpl(this, i)).doPurchaseTran(this.po_Header, this.po_Detail, this.transactionLog));
    this.handler.sendMessage(localMessage);
  }
  
  private void findviewbyId()
  {
    this.mScvContainer = ((ScrollView)findViewById(2131362228));
    this.mTxtTime = ((TextView)findViewById(2131362231));
    this.mTxtTime.setText(DateTimeUtil.getSimpleTimeString());
    this.mTxtSupplier = ((TextView)findViewById(2131362233));
    this.mLsvGoods = ((InterceptListView)findViewById(2131362236));
    this.mTxtPayable = ((TextView)findViewById(2131362237));
    this.mEdtPaid = ((EditText)findViewById(2131362238));
    findViewById(2131362239).setVisibility(8);
    findViewById(2131362229).setOnClickListener(this);
    findViewById(2131362232).setOnClickListener(this);
    findViewById(2131362234).setOnClickListener(this);
    findViewById(2131362235).setOnClickListener(this);
    findViewById(2131361928).setOnClickListener(this);
  }
  
  private void initData()
  {
    float f = ResUtil.getDimens(2131099704);
    this.adapter = new StockAdapter(this, PurchasesManager.getPurchaseCart().getAllPurchaseGoods());
    this.mLsvGoods.setAdapter(this.adapter);
    this.mLsvGoods.setParentScrollView(this.mScvContainer);
    this.mLsvGoods.setMaxHeight((int)(3.0F * f));
    this.mTxtPayable.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
    this.mEdtPaid.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
    TextViewUtil.addInputLimitTwoDecimal(this.mEdtPaid);
  }
  
  private void initEntity()
  {
    this.po_Header = new PO_Header();
    this.po_Header.setCreateEntitySync(this.userId);
    this.po_Header.setMerchant_ID(this.MerchantID);
    this.PO_ID = IDUtil.getID();
    this.po_Header.setPO_ID(this.PO_ID);
    this.po_Header.setPO_Number("PX" + this.po_Header.getPO_ID());
    this.po_Header.setPO_OtherAmount(0.0D);
    if (this.associate != null) {
      this.po_Header.setPO_SupplierID(this.associate.getAssciate_SupplierID());
    }
    this.po_Header.setPO_StoreID(this.storeId);
    Object localObject = this.mTxtTime.getText().toString().trim();
    int i;
    label256:
    int j;
    if (((String)localObject).equals(DateTimeUtil.getSimpleTimeString()))
    {
      localObject = DateTimeUtil.getCurrentTime();
      this.po_Header.setPO_Date((Timestamp)localObject);
      this.po_Header.setPO_Staff(UserManager.getInstance().getCurrentUser().getUser_ID());
      this.po_Header.setPO_Status((byte)1);
      double d1 = Double.parseDouble(trimPriceComma(TextViewUtil.getTextString(this.mTxtPayable)));
      double d2 = Double.parseDouble(trimPriceComma(TextViewUtil.getTextString(this.mEdtPaid)));
      this.po_Header.setPO_ProductAmount(d1);
      this.po_Header.setPO_PlanAmount(d1);
      this.po_Header.setPO_ActualAmount(d2);
      localObject = this.po_Header;
      if (d1 <= d2) {
        break label324;
      }
      i = 1;
      ((PO_Header)localObject).setIS_Settle((byte)i);
      j = PurchasesManager.getPurchaseCart().getAllPurchaseGoods().size();
      this.po_Detail = new PO_Detail[j];
      i = 0;
      label287:
      if (i < j) {
        break label330;
      }
      this.transactionLog = new TransactionLog[j];
      i = 0;
    }
    for (;;)
    {
      if (i >= j)
      {
        return;
        localObject = DateTimeUtil.getSimpleTimestampFromString((String)localObject);
        break;
        label324:
        i = 2;
        break label256;
        label330:
        this.po_Detail[i] = new PO_Detail();
        this.po_Detail[i].setCreateEntitySync(this.userId);
        this.po_Detail[i].setMerchant_ID(this.MerchantID);
        this.po_Detail[i].setPD_ID(IDUtil.getID());
        this.po_Detail[i].setPO_ID(this.po_Header.getPO_ID());
        this.po_Detail[i].setProductSku_ID(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getProductSku_ID());
        this.po_Detail[i].setPD_Quantiy(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getChooseAmount());
        this.po_Detail[i].setPD_PurchasePrice(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getPurchase_Price());
        this.po_Detail[i].setPD_CostPrice(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getFixed_Cost());
        this.po_Detail[i].setPD_RetailPrice(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getRetail_Price());
        i += 1;
        break label287;
      }
      this.transactionLog[i] = new TransactionLog();
      this.transactionLog[i].setCreateEntitySync(this.userId);
      this.transactionLog[i].setMerchant_ID(this.MerchantID);
      this.transactionLog[i].setTLog_ID(IDUtil.getID());
      this.transactionLog[i].setTLog_AffectStock((byte)1);
      this.transactionLog[i].setTLog_TransactionId(this.po_Header.getPO_ID());
      this.transactionLog[i].setTLog_TransactionNumber(this.po_Header.getPO_Number());
      this.transactionLog[i].setTLog_TransactionType(1);
      this.transactionLog[i].setTLog_PostingDate(this.po_Header.getPO_Date());
      this.transactionLog[i].setTLog_StoreID(this.storeId);
      this.transactionLog[i].setTLog_ProductSku(this.po_Detail[i].getProductSku_ID());
      this.transactionLog[i].setTLog_StockQuantity(((PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(i)).getChooseAmount());
      this.transactionLog[i].setTLog_CostPrice(this.po_Detail[i].getPD_CostPrice());
      this.transactionLog[i].setTLog_PriceOrignial(this.po_Detail[i].getPD_RetailPrice());
      this.transactionLog[i].setTLog_PriceSold(this.po_Detail[i].getPD_PurchasePrice());
      this.transactionLog[i].setTLog_IsLocal((byte)1);
      this.transactionLog[i].setTLog_Status((byte)1);
      i += 1;
    }
  }
  
  private boolean setPaid()
  {
    String str = TextViewUtil.trimPriceComma(TextViewUtil.getTextString(this.mEdtPaid));
    try
    {
      Double.parseDouble(str);
      return true;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      ToastUtil.toastLong(this, "实收输入错误");
      localNumberFormatException.printStackTrace();
    }
    return false;
  }
  
  private String trimPriceComma(String paramString)
  {
    return new String(paramString).replaceAll(",", "");
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903101, paramViewGroup);
    this.mTopBar.setTitle(2131165346);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837549, this);
    getWindow().setSoftInputMode(2);
    findviewbyId();
    initData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default: 
    case 1: 
      do
      {
        return;
      } while ((paramIntent == null) || (paramIntent.getExtras() == null));
      this.associate = ((Associate)paramIntent.getSerializableExtra(Associate.class.getSimpleName()));
      this.mTxtSupplier.setText(this.associate.getCompany());
      return;
    }
    final float f = ResUtil.getDimens(2131099704);
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        Object localObject = PurchasesManager.getPurchaseCart().getAllPurchaseGoods();
        ArrayList localArrayList = new ArrayList();
        localObject = ((ArrayList)localObject).iterator();
        for (;;)
        {
          if (!((Iterator)localObject).hasNext())
          {
            PurchaseActivity.this.adapter = new StockAdapter(PurchaseActivity.this, localArrayList);
            PurchaseActivity.this.mLsvGoods.setAdapter(PurchaseActivity.this.adapter);
            PurchaseActivity.this.mLsvGoods.setParentScrollView(PurchaseActivity.this.mScvContainer);
            PurchaseActivity.this.mLsvGoods.setMaxHeight((int)(f * 3.0F));
            PurchaseActivity.this.mTxtPayable.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
            PurchaseActivity.this.mEdtPaid.setText(DataUtil.getFormatString(PurchasesManager.getPurchaseCart().getTotalMoney()));
            return;
          }
          PurchaseGoods localPurchaseGoods = (PurchaseGoods)((Iterator)localObject).next();
          if (localPurchaseGoods.getOnhand_Quantity() > 0.0D) {
            localArrayList.add(localPurchaseGoods);
          }
        }
      }
    });
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362229: 
      paramView = new PickTimeDialog();
      paramView.setOnTimePickListener(new PickTimeDialog.onDatePickListener()
      {
        public void onPickFinish(String paramAnonymousString)
        {
          PurchaseActivity.this.mTxtTime.setText(paramAnonymousString);
        }
      });
      paramView.show(getSupportFragmentManager(), "PickTimeDialog");
      return;
    case 2131362232: 
      this.mIntent = new Intent(this, PurchaseChooseSupplierActivity.class);
      startActivityForResult(this.mIntent, 1);
      return;
    case 2131362234: 
      this.mIntent = new Intent(this, PurchaseChooseGoodsActivity.class);
      startActivityForResult(this.mIntent, 2);
      return;
    case 2131362235: 
      LogUtils.i("扫描");
      this.mIntent = new Intent(this, CaptureActivity.class);
      this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
      this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.PURCHASE);
      startActivity(this.mIntent);
      return;
    }
    ExecutorUtils.getDBExecutor().execute(new Runnable()
    {
      public void run()
      {
        if ((PurchasesManager.getPurchaseCart().getAllPurchaseGoods() == null) || (PurchasesManager.getPurchaseCart().getAllPurchaseGoods().size() < 1) || (!PurchaseActivity.this.setPaid())) {
          return;
        }
        PurchaseActivity.this.initEntity();
        PurchaseActivity.this.doTrans();
      }
    });
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.MerchantID = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.storeId = UserManager.getInstance().getCurrentStore().getStore_ID();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    this.mIntent = new Intent(this, PurchaseHistoryActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("Associate", this.associate);
    this.mIntent.putExtra("AssociateBundle", localBundle);
    startActivity(this.mIntent);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\activity\purchase\PurchaseActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */