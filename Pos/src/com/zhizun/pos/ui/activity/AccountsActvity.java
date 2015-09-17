package com.zhizun.pos.ui.activity;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.zizun.cs.activity.manager.ManagerFactory;
import com.zizun.cs.biz.dao.AccountsDao;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.ui.entity.AccountsTotal;
import com.zhizun.pos.ui.adapter.AccountsAdapter;
import com.zhizun.pos.ui.dialog.PickTimeDialog;
import com.zhizun.pos.ui.dialog.PickTimeDialog.onDatePickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;

public class AccountsActvity
  extends BaseFragmentTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener
{
  private AccountsTotal mAccountsTotal;
  private String mCurrentDate;
  private ExpandableListView mELsvAccounts;
  
  private void initView()
  {
    this.mELsvAccounts = ((ExpandableListView)findViewById(2131361817));
  }
  
  private void showAccounts()
  {
    if ((this.mCurrentDate == null) || (this.mCurrentDate.equals(""))) {
      this.mCurrentDate = DateTimeUtil.getSimpleTimeString();
    }
    this.mTopBar.setTitle(this.mCurrentDate);
    new GetAllAccountsTask(null).executeOnExecutor(ExecutorUtils.getDBExecutor(), new Void[0]);
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903042, paramViewGroup);
    this.mTopBar.setTitle(2131165460);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837617, this);
    initView();
    showAccounts();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    PickTimeDialog localPickTimeDialog = new PickTimeDialog();
    localPickTimeDialog.setOnTimePickListener(new PickTimeDialog.onDatePickListener()
    {
      public void onPickFinish(String paramAnonymousString)
      {
        AccountsActvity.this.mCurrentDate = paramAnonymousString;
        AccountsActvity.this.showAccounts();
      }
    });
    localPickTimeDialog.show(getSupportFragmentManager(), "dateDialog");
  }
  
  private class GetAllAccountsTask
    extends AsyncTask<Void, Void, Void>
  {
    private GetAllAccountsTask() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      AccountsActvity.this.mAccountsTotal = ManagerFactory.getAccountsDao(AccountsActvity.this.mCurrentDate).getTotalAccounts();
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      AccountsActvity.this.dismissWaitDialog();
      AccountsAdapter localAccountsAdapter = new AccountsAdapter(AccountsActvity.this, AccountsActvity.this.mAccountsTotal);
      AccountsActvity.this.mELsvAccounts.setAdapter(localAccountsAdapter);
      int i = 0;
      for (;;)
      {
        if (i >= localAccountsAdapter.getGroupCount())
        {
          super.onPostExecute(paramVoid);
          return;
        }
        AccountsActvity.this.mELsvAccounts.expandGroup(i);
        i += 1;
      }
    }
    
    protected void onPreExecute()
    {
      AccountsActvity.this.showWaitDialog();
      super.onPreExecute();
    }
  }
}