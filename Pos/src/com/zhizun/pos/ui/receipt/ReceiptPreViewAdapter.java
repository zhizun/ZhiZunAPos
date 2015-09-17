package com.zhizun.pos.ui.receipt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zizun.cs.biz.sale.V_TransactionDetail;
import com.zizun.cs.common.utils.NumUtil;
import com.zhizun.pos.app.StoreApplication;
import java.util.ArrayList;

public class ReceiptPreViewAdapter
  extends BaseAdapter
{
  private ArrayList<V_TransactionDetail> list;
  private LayoutInflater mInflater;
  
  public ReceiptPreViewAdapter(ArrayList<V_TransactionDetail> paramArrayList)
  {
    this.list = paramArrayList;
    this.mInflater = LayoutInflater.from(StoreApplication.getContext());
  }
  
  public int getCount()
  {
    if (this.list == null) {
      return 0;
    }
    return this.list.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.list.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903196, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      V_TransactionDetail localV_TransactionDetail = (V_TransactionDetail)this.list.get(paramInt);
      paramViewGroup.txtName.setText(localV_TransactionDetail.getProductSku_Name());
      paramViewGroup.txtPrice.setText(NumUtil.getDecimal(localV_TransactionDetail.getTransPrice()));
      paramViewGroup.txtQuantity.setText(NumUtil.getDecimal(localV_TransactionDetail.getTransQty()));
      paramViewGroup.txtAmount.setText(NumUtil.getDecimal(localV_TransactionDetail.getTransAmount()));
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  private class ViewHolder
  {
    private TextView txtAmount;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtQuantity;
    
    public ViewHolder(View paramView)
    {
      this.txtName = ((TextView)paramView.findViewById(2131362374));
      this.txtPrice = ((TextView)paramView.findViewById(2131362375));
      this.txtQuantity = ((TextView)paramView.findViewById(2131362376));
      this.txtAmount = ((TextView)paramView.findViewById(2131362377));
    }
  }
}