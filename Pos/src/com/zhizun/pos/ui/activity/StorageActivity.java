package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
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
import com.zizun.cs.activity.manager.ProductManager;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsFilter;
import com.zhizun.pos.ui.activity.scan.BusinessType;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.adapter.SwipeGoodsAdapter;
import com.zhizun.pos.ui.adapter.SwipeGoodsAdapter.OnGroupListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarFrontRightClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.GroupSeleteView;
import com.zhizun.pos.ui.widget.GroupSeleteView.GroupChangeListener;
import com.zhizun.pos.ui.widget.SwipeMenuListView;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.io.Serializable;
import java.util.ArrayList;

public class StorageActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarFrontRightClickListener, BaseTopBar.OnTopBarRightClickListener, SwipeGoodsAdapter.OnGroupListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnKeyListener, GroupSeleteView.GroupChangeListener, View.OnClickListener
{
  private static final int INTENT_REQUEST_CODE_BARCODE = 4099;
  private static final int INTENT_REQUEST_CODE_EDITPRODUCT = 4098;
  private static final int INTENT_REQUEST_CODE_NEWPRODUCT = 4097;
  private Bundle mBundle;
  private EditText mEdtSearch;
  private SwipeGoodsAdapter mGoodsAdapter;
  private GoodsFilter mGoodsFilter;
  private int mGoodsIndex = 0;
  private GroupSeleteView mGroupView;
  private ImageView mImgScan;
  private Intent mIntent;
  private int mIntentRequestCode;
  private ArrayList<Goods> mListGoods;
  private SwipeMenuListView mLsvGoods;
  private CustomPopupWindow mPopStatu;
  private int mPopX;
  private String mSearch;
  private int visibleLastIndex = 0;
  
  private void deleteGoods(Goods paramGoods)
  {
    new DeleteGoodsAsync(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Goods[] { paramGoods });
  }
  
  private void getAllGoods()
  {
    new getGoodsAsync(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
  }
  
  private void init()
  {
    this.mIntentRequestCode = getIntent().getIntExtra("requestCode", -1);
    this.mGoodsFilter = new GoodsFilter();
    if (this.mIntentRequestCode == 4)
    {
      this.mSearch = getIntent().getStringExtra("search_goods");
      searchProduct();
      return;
    }
    getAllGoods();
  }
  
  private void initFilter()
  {
    Object localObject = (ListView)getLayoutInflater().inflate(2130903178, null);
    ((ListView)localObject).setDivider(null);
    ((ListView)localObject).setBackgroundResource(2131427355);
    ((ListView)localObject).setAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296260)));
    ((ListView)localObject).setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if ((StorageActivity.this.mPopStatu != null) && (StorageActivity.this.mPopStatu.isShowing())) {
          StorageActivity.this.mPopStatu.dismiss();
        }
        if (StorageActivity.this.mGoodsFilter.getFilterType() != paramAnonymousInt)
        {
          StorageActivity.this.mGoodsFilter.setFilterType(paramAnonymousInt);
          StorageActivity.this.resetGoods();
        }
      }
    });
    int i = (int)ResUtil.getDimens(2131099679);
    this.mPopStatu = new CustomPopupWindow(this, (View)localObject, i);
    this.mPopStatu.setAnimationStyle(2131230758);
    localObject = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics((DisplayMetrics)localObject);
    this.mPopX = (((DisplayMetrics)localObject).widthPixels - i);
  }
  
  private void resetGoods()
  {
    this.mGoodsIndex = 0;
    if (this.mListGoods != null) {
      this.mListGoods.clear();
    }
    getAllGoods();
  }
  
  private void searchProduct()
  {
    if ((this.mSearch != null) && (!this.mSearch.equals(""))) {
      this.mGoodsFilter.setSearch(this.mSearch);
    }
    for (;;)
    {
      resetGoods();
      return;
      this.mGoodsFilter.setSearch("");
    }
  }
  
  private void setAdapter()
  {
    this.mGoodsAdapter = new SwipeGoodsAdapter(this, this.mListGoods);
    this.mGoodsAdapter.setOnGroupListener(this);
    this.mLsvGoods.setAdapter(this.mGoodsAdapter);
  }
  
  private void showFilter()
  {
    this.mPopStatu.showMenu(this.mTopBar, this.mPopX, 0);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903103, paramViewGroup);
    this.mTopBar.setTitle(2131165296);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setFrontRightButton(2130837532, this);
    this.mTopBar.setRightButton(2130837525, this);
    this.mLsvGoods = ((SwipeMenuListView)paramViewGroup.findViewById(2131362250));
    this.mGroupView = ((GroupSeleteView)paramViewGroup.findViewById(2131362249));
    this.mImgScan = ((ImageView)paramViewGroup.findViewById(2131362248));
    this.mGroupView.setOnGroupChangeListener(this);
    this.mLsvGoods.setOnItemClickListener(this);
    this.mLsvGoods.setOnScrollListener(this);
    this.mImgScan.setOnClickListener(this);
    this.mEdtSearch = ((EditText)paramViewGroup.findViewById(2131361842));
    this.mEdtSearch.setOnKeyListener(this);
    initFilter();
    init();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if (paramInt2 == -1) {
        resetGoods();
      }
      this.mGroupView.refreshGroup();
      continue;
      if (paramIntent != null)
      {
        String str = paramIntent.getStringExtra(String.class.getSimpleName());
        this.mGoodsFilter.setBarCode(str);
        this.mGoodsFilter.setGroup(0L);
        resetGoods();
      }
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    this.mIntent = new Intent(this, CaptureActivity.class);
    this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.SEARCH);
    this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
    startActivityForResult(this.mIntent, 4099);
  }
  
  public void onDelete(Goods paramGoods)
  {
    this.mLsvGoods.setHiddenSwipeMenu(Boolean.valueOf(true));
    deleteGoods(paramGoods);
  }
  
  public void onGroupChange(long paramLong)
  {
    this.mGoodsFilter.setGroup(paramLong);
    resetGoods();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (this.mIntentRequestCode)
    {
    default: 
      this.mIntent = new Intent(this, EditGoodsActivity.class);
      this.mBundle = new Bundle();
      this.mBundle.putSerializable(Goods.class.getSimpleName(), (Serializable)this.mListGoods.get(paramInt));
      this.mIntent.putExtras(this.mBundle);
      startActivityForResult(this.mIntent, 4098);
      return;
    }
    this.mIntent = getIntent();
    this.mBundle = new Bundle();
    this.mBundle.putSerializable(Goods.class.getSimpleName(), (Serializable)this.mListGoods.get(paramInt));
    this.mIntent.putExtras(this.mBundle);
    setResult(-1, this.mIntent);
    finish();
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
        searchProduct();
      }
    }
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.visibleLastIndex = (paramInt1 + paramInt2 - 1);
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    int i = this.mGoodsAdapter.getCount();
    if ((paramInt == 0) && (this.visibleLastIndex == i - 1))
    {
      this.mGoodsIndex += 20;
      if (this.mGoodsAdapter.getCount() >= this.mGoodsIndex) {
        getAllGoods();
      }
    }
    else
    {
      return;
    }
    ToastUtil.toastShort(this, 2131165317);
  }
  
  public void onTopBarFrontRightClick()
  {
    showFilter();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    startActivityForResult(new Intent(this, CreateGoodsActivity.class), 4097);
  }
  
  private class DeleteGoodsAsync
    extends AsyncTask<Goods, Void, Boolean>
  {
    private DeleteGoodsAsync() {}
    
    protected Boolean doInBackground(Goods... paramVarArgs)
    {
      return Boolean.valueOf(ProductManager.deleteProduct(paramVarArgs[0]));
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      StorageActivity.this.dismissWaitDialog();
      if (paramBoolean.booleanValue())
      {
        StorageActivity.this.resetGoods();
        return;
      }
      ToastUtil.toastShort(StorageActivity.this, 2131165315);
    }
    
    protected void onPreExecute()
    {
      StorageActivity.this.showWaitDialog();
      super.onPreExecute();
    }
  }
  
  private class getGoodsAsync
    extends AsyncTask<Void, Void, ArrayList<Goods>>
  {
    private getGoodsAsync() {}
    
    protected ArrayList<Goods> doInBackground(Void... paramVarArgs)
    {
      return ProductManager.getAllProduct(StorageActivity.this.mGoodsFilter);
    }
    
    protected void onPostExecute(ArrayList<Goods> paramArrayList)
    {
      super.onPostExecute(paramArrayList);
      StorageActivity.this.dismissWaitDialog();
      if (paramArrayList == null) {
        ToastUtil.toastShort(StorageActivity.this, 2131165316);
      }
      for (;;)
      {
        if ((StorageActivity.this.mGoodsFilter.getBarCode() != null) && (!StorageActivity.this.mGoodsFilter.getBarCode().equals(""))) {
          StorageActivity.this.mGoodsFilter.setBarCode("");
        }
        return;
        if (paramArrayList.size() != 0)
        {
          if ((StorageActivity.this.mListGoods == null) || (StorageActivity.this.mListGoods.size() == 0))
          {
            StorageActivity.this.mListGoods = paramArrayList;
            StorageActivity.this.setAdapter();
          }
          else
          {
            int i = 0;
            for (;;)
            {
              if (i >= paramArrayList.size())
              {
                StorageActivity.this.mGoodsAdapter.notifyDataSetChanged();
                break;
              }
              StorageActivity.this.mListGoods.add((Goods)paramArrayList.get(i));
              i += 1;
            }
          }
        }
        else
        {
          ToastUtil.toastShort(StorageActivity.this, 2131165317);
          if ((StorageActivity.this.mListGoods == null) || (StorageActivity.this.mListGoods.size() == 0)) {
            StorageActivity.this.setAdapter();
          } else {
            StorageActivity.this.mGoodsAdapter.notifyDataSetChanged();
          }
        }
      }
    }
    
    protected void onPreExecute()
    {
      StorageActivity.this.showWaitDialog();
      StorageActivity.this.mGoodsFilter.setOffset(StorageActivity.this.mGoodsIndex);
      super.onPreExecute();
    }
  }
}