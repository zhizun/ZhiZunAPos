package com.zhizun.pos.ui.activity.invent;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.zizun.cs.activity.manager.InventManager;
import com.zizun.cs.activity.manager.ProductManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.Invent;
import com.zhizun.pos.ui.activity.BaseFragmentTitleTopBarActivity;
import com.zhizun.pos.ui.activity.CreateGoodsActivity;
import com.zhizun.pos.ui.activity.MainActivity;
import com.zhizun.pos.ui.activity.StorageActivity;
import com.zhizun.pos.ui.activity.scan.BusinessType;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.dialog.PickTimeDialog;
import com.zhizun.pos.ui.dialog.PickTimeDialog.onDatePickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;

public class CheckActivity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnClickListener
{
  private static final int INTENT_REQUEST_CODE_BARCODE = 2;
  private static final int INTENT_REQUEST_CODE_GOODS = 1;
  private boolean IsClickDate = false;
  private boolean IsScan = false;
  private double amount;
  private String barcode;
  private CheckActivity context;
  private double costPrice;
  private Invent invent;
  private InventManager inventManager;
  private EditText mEdtCheckAmount;
  private Goods mGoods;
  private Intent mIntent;
  private TextView mTxtGoodsAmount;
  private TextView mTxtGoodsCode;
  private TextView mTxtGoodsName;
  private TextView mTxtTime;
  private InputMethodManager manager;
  private int merchantID;
  private Goods scanGoods;
  private double sellPrice;
  private long skuId;
  private long storeID;
  private UserManager userManager;
  private int user_ID;
  
  private void findviewbyId()
  {
    this.mTxtTime = ((TextView)findViewById(2131361830));
    this.mTxtGoodsName = ((TextView)findViewById(2131361833));
    this.mTxtGoodsCode = ((TextView)findViewById(2131361836));
    this.mTxtGoodsAmount = ((TextView)findViewById(2131361838));
    this.mEdtCheckAmount = ((EditText)findViewById(2131361839));
    findViewById(2131361828).setOnClickListener(this);
    findViewById(2131361836).setOnClickListener(this);
    findViewById(2131361837).setOnClickListener(this);
    findViewById(2131361835).setOnClickListener(this);
    findViewById(2131361831).setOnClickListener(this);
    findViewById(2131361840).setOnClickListener(this);
    this.mTxtTime.setText(DateTimeUtil.getTimeStrForElevenLen(DateTimeUtil.getCurrentTime()));
  }
  
  private void getIntentData()
  {
    this.barcode = getIntent().getStringExtra(String.class.getSimpleName());
    if (!TextUtils.isEmpty(this.barcode))
    {
      this.IsScan = true;
      new ScanTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
    }
  }
  
  private void initBaseData()
  {
    this.context = this;
    this.userManager = UserManager.getInstance();
    this.merchantID = this.userManager.getCurrentUser().getMerchant_ID();
    this.user_ID = this.userManager.getCurrentUser().getUser_ID();
    this.storeID = this.userManager.getCurrentStore().getStore_ID();
    this.inventManager = new InventManager();
    getIntentData();
  }
  
  private void setInitGoods()
  {
    this.mTxtGoodsName.setText(this.mGoods.getName());
    this.mTxtGoodsCode.setText(this.mGoods.getCode());
    this.mTxtGoodsAmount.setText(DataUtil.getFormatString(this.mGoods.getAmount()));
    this.mEdtCheckAmount.setText(DataUtil.getFormatString(this.mGoods.getAmount()));
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903046, paramViewGroup);
    this.mTopBar.setTitle(2131165424);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837549, this);
    findviewbyId();
    initBaseData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      do
      {
        return;
      } while ((paramInt2 != -1) || (paramIntent == null));
      this.mGoods = ((Goods)paramIntent.getSerializableExtra(Goods.class.getSimpleName()));
      this.skuId = this.mGoods.getSkuId();
      this.amount = this.mGoods.getAmount();
      this.costPrice = this.mGoods.getCostPrice();
      this.sellPrice = this.mGoods.getSellPrice();
      this.mGoods.getWholesalePrice();
      this.mGoods.getStockPrice();
      setInitGoods();
      return;
    } while (paramIntent == null);
    this.barcode = paramIntent.getStringExtra(String.class.getSimpleName());
    LogUtil.logD("barcode:" + this.barcode);
    new ScanTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131361828: 
      this.IsClickDate = true;
      paramView = new PickTimeDialog();
      paramView.setOnTimePickListener(new PickTimeDialog.onDatePickListener()
      {
        public void onPickFinish(String paramAnonymousString)
        {
          CheckActivity.this.mTxtTime.setText(paramAnonymousString);
        }
      });
      paramView.show(getSupportFragmentManager(), "PickTimeDialog");
      return;
    case 2131361837: 
      this.IsScan = true;
      this.mIntent = new Intent(this, CaptureActivity.class);
      this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.CHECK);
      this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
      startActivityForResult(this.mIntent, 2);
      return;
    case 2131361831: 
      this.mIntent = new Intent(this, StorageActivity.class);
      this.mIntent.putExtra("requestCode", 1);
      startActivityForResult(this.mIntent, 1);
      return;
    }
    this.invent = new Invent();
    if (!this.IsClickDate)
    {
      this.invent.setTime(DateTimeUtil.getCurrentTime());
      this.invent.setIsCurtime(true);
    }
    for (;;)
    {
      paramView = getEdtText(this.mEdtCheckAmount);
      if (this.IsScan) {
        break label376;
      }
      if (this.mGoods == null) {
        break label365;
      }
      this.invent.setGoods(this.mGoods);
      if ((paramView != null) && (!paramView.equals(""))) {
        break;
      }
      ToastUtil.toastShort(this.context, "请填写数量！");
      return;
      this.invent.setTime(this.mTxtTime.getText().toString());
      this.invent.setIsCurtime(false);
    }
    this.invent.setOnhand_Scan(Double.valueOf(paramView).doubleValue());
    new InventTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Invent[] { this.invent });
    return;
    label365:
    ToastUtil.toastShort(this.context, "请选择商品！");
    return;
    label376:
    if (this.scanGoods != null)
    {
      this.invent.setGoods(this.scanGoods);
      if ((paramView == null) || (paramView.equals("")))
      {
        ToastUtil.toastShort(this.context, "请填写数量！");
        return;
      }
      this.invent.setOnhand_Scan(Double.valueOf(paramView).doubleValue());
      new InventTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Invent[] { this.invent });
      return;
    }
    ToastUtil.toastShort(this.context, "请选择商品！");
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    this.mIntent = new Intent(this, CheckHistoryActivity.class);
    startActivityForResult(this.mIntent, 1);
  }
  
  private class InventTask
    extends AsyncTask<Invent, Void, Boolean>
  {
    private InventTask() {}
    
    protected Boolean doInBackground(Invent... paramVarArgs)
    {
      return Boolean.valueOf(InventManager.createInvert(paramVarArgs[0]));
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      CheckActivity.this.dismissWaitDialog();
      if (paramBoolean.booleanValue())
      {
        CheckActivity.this.mTxtTime.setText(DateTimeUtil.getTimeStrForElevenLen(DateTimeUtil.getCurrentTime()));
        CheckActivity.this.mTxtGoodsName.setText("");
        CheckActivity.this.mTxtGoodsCode.setText("");
        CheckActivity.this.mTxtGoodsAmount.setText("");
        CheckActivity.this.mEdtCheckAmount.setText("");
        CheckActivity.this.IsClickDate = false;
        CheckActivity.this.mGoods = null;
        CheckActivity.this.scanGoods = null;
        ToastUtil.toastShort(CheckActivity.this.context, "盘点成功！");
        return;
      }
      ToastUtil.toastShort(CheckActivity.this.context, "盘点失败！");
    }
    
    protected void onPreExecute()
    {
      CheckActivity.this.showWaitDialog();
      super.onPreExecute();
    }
  }
  
  private class ScanTask
    extends AsyncTask<Void, Void, Void>
  {
    private ScanTask() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      CheckActivity.this.scanGoods = ProductManager.getSingleGood(CheckActivity.this.barcode);
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      super.onPostExecute(paramVoid);
      if (CheckActivity.this.scanGoods != null)
      {
        CheckActivity.this.mTxtGoodsName.setText(CheckActivity.this.scanGoods.getName());
        CheckActivity.this.mTxtGoodsCode.setText(CheckActivity.this.barcode);
        CheckActivity.this.mTxtGoodsAmount.setText(DataUtil.getFormatString(CheckActivity.this.scanGoods.getAmount()));
        CheckActivity.this.mEdtCheckAmount.setText(DataUtil.getFormatString(CheckActivity.this.scanGoods.getAmount()));
        return;
      }
      paramVoid = new Intent(CheckActivity.this.context, CreateGoodsActivity.class);
      paramVoid.putExtra(String.class.getSimpleName(), CheckActivity.this.barcode);
      CheckActivity.this.startActivity(paramVoid);
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
    }
  }
}