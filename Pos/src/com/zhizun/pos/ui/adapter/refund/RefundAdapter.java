package com.zhizun.pos.ui.adapter.refund;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.biz.refund.RefundableSheet;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.ResUtil;
import java.util.ArrayList;

public class RefundAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private ArrayList<RefundableSheet> mList;
  
  public RefundAdapter(ArrayList<RefundableSheet> paramArrayList)
  {
    this.mList = paramArrayList;
    this.mInflater = LayoutInflater.from(StoreApplication.getContext());
  }
  
  public int getCount()
  {
    if (this.mList == null) {
      return 0;
    }
    return this.mList.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.mList.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    RefundableSheet localRefundableSheet;
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903213, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      localRefundableSheet = (RefundableSheet)this.mList.get(paramInt);
      if (!localRefundableSheet.getTransType().endsWith("SX")) {
        break label112;
      }
      paramViewGroup.imgType.setBackgroundResource(2130837734);
    }
    for (;;)
    {
      paramViewGroup.txtBill.setText(ResUtil.getFormatString(2131165381, localRefundableSheet.getTransNumber()));
      paramViewGroup.txtCompany.setText(ResUtil.getFormatString(2131165383, localRefundableSheet.getRelatedName()));
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label112:
      if (localRefundableSheet.getTransType().endsWith("WX")) {
        paramViewGroup.imgType.setBackgroundResource(2130837734);
      } else if (localRefundableSheet.getTransType().endsWith("PX")) {
        paramViewGroup.imgType.setBackgroundResource(2130837736);
      }
    }
  }
  
  private class ViewHolder
  {
    private ImageView imgType;
    private TextView txtBill;
    private TextView txtCompany;
    private TextView txtDate;
    private TextView txtName;
    
    public ViewHolder(View paramView)
    {
      this.imgType = ((ImageView)paramView.findViewById(2131362649));
      this.txtBill = ((TextView)paramView.findViewById(2131362650));
      this.txtDate = ((TextView)paramView.findViewById(2131362651));
      this.txtCompany = ((TextView)paramView.findViewById(2131362652));
      this.txtName = ((TextView)paramView.findViewById(2131362653));
    }
  }
}