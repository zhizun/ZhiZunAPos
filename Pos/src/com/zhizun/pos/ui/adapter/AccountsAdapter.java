package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.ui.entity.Accounts;
import com.zizun.cs.ui.entity.AccountsTotal;
import com.zhizun.pos.util.DataUtil;

public class AccountsAdapter
  extends BaseExpandableListAdapter
{
  private AccountsTotal mAccountsTotal;
  private Context mContext;
  private LayoutInflater mInflater;
  private String[] mListName;
  private String[] mListTopic;
  
  public AccountsAdapter(Context paramContext, AccountsTotal paramAccountsTotal)
  {
    this.mContext = paramContext;
    this.mAccountsTotal = paramAccountsTotal;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mListName = ResUtil.getStringArrays(2131296266);
    this.mListTopic = ResUtil.getStringArrays(2131296267);
  }
  
  public Object getChild(int paramInt1, int paramInt2)
  {
    return null;
  }
  
  public long getChildId(int paramInt1, int paramInt2)
  {
    return paramInt1;
  }
  
  public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    View localView;
    ViewHolder localViewHolder;
    if (paramView == null)
    {
      localView = this.mInflater.inflate(2130903040, null);
      localViewHolder = new ViewHolder(localView);
      localView.setTag(localViewHolder);
      label36:
      if (paramInt2 == 0) {
        break label140;
      }
      localViewHolder.txtTitle.setText(this.mListName[(paramInt2 - 1)]);
      label56:
      if (paramInt1 != 0) {
        break label153;
      }
      paramViewGroup = this.mAccountsTotal.getYesterdayAccounts();
      paramView = this.mAccountsTotal.getTodayAccounts();
    }
    for (;;)
    {
      switch (paramInt2)
      {
      default: 
        return localView;
        localViewHolder = (ViewHolder)paramView.getTag();
        localView = paramView;
        break label36;
        label140:
        localViewHolder.txtTitle.setText("");
        break label56;
        label153:
        if (paramInt1 == 1)
        {
          paramViewGroup = this.mAccountsTotal.getLastWeekAccounts();
          paramView = this.mAccountsTotal.getCurrentWeekAccounts();
        }
        else
        {
          paramViewGroup = this.mAccountsTotal.getLastMonthAccounts();
          paramView = this.mAccountsTotal.getCurrentMonthAccounts();
        }
        break;
      }
    }
    localViewHolder.txtLast.setText(this.mListTopic[(paramInt1 * 3 + 0)]);
    localViewHolder.txtCurrent.setText(this.mListTopic[(paramInt1 * 3 + 1)]);
    localViewHolder.txtRatio.setText(this.mListTopic[(paramInt1 * 3 + 2)]);
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getAmt_Sale()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getAmt_Sale()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getAmt_Sale() - paramViewGroup.getAmt_Sale()));
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getCost_Sale()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getCost_Sale()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getCost_Sale() - paramViewGroup.getCost_Sale()));
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getProfit_Sale()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getProfit_Sale()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getProfit_Sale() - paramViewGroup.getProfit_Sale()));
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getDebt()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getDebt()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getDebt() - paramViewGroup.getDebt()));
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getAmt_SaleReturn()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getAmt_SaleReturn()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getAmt_SaleReturn() - paramViewGroup.getAmt_SaleReturn()));
    return localView;
    localViewHolder.txtLast.setText(DataUtil.getFormatString(paramViewGroup.getAmt_Order()));
    localViewHolder.txtCurrent.setText(DataUtil.getFormatString(paramView.getAmt_Order()));
    localViewHolder.txtRatio.setText(DataUtil.getFormatString(paramView.getAmt_Order() - paramViewGroup.getAmt_Order()));
    return localView;
  }
  
  public int getChildrenCount(int paramInt)
  {
    return this.mListName.length;
  }
  
  public Object getGroup(int paramInt)
  {
    return Integer.valueOf(paramInt);
  }
  
  public int getGroupCount()
  {
    return 3;
  }
  
  public long getGroupId(int paramInt)
  {
    return paramInt;
  }
  
  public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    if (paramView == null) {
      paramViewGroup = new View(this.mContext);
    }
    paramViewGroup.setBackgroundResource(ResUtil.getDrawableId(String.format("bg_report_%d", new Object[] { Integer.valueOf(paramInt) })));
    return paramViewGroup;
  }
  
  public boolean hasStableIds()
  {
    return false;
  }
  
  public boolean isChildSelectable(int paramInt1, int paramInt2)
  {
    return false;
  }
  
  private class ViewHolder
  {
    private TextView txtCurrent;
    private TextView txtLast;
    private TextView txtRatio;
    private TextView txtTitle;
    
    public ViewHolder(View paramView)
    {
      this.txtTitle = ((TextView)paramView.findViewById(2131361804));
      this.txtLast = ((TextView)paramView.findViewById(2131361805));
      this.txtCurrent = ((TextView)paramView.findViewById(2131361806));
      this.txtRatio = ((TextView)paramView.findViewById(2131361807));
    }
  }
}