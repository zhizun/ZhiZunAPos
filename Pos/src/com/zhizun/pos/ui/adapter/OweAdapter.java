package com.zhizun.pos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.biz.V_HeaderForPay;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import com.zhizun.pos.util.ResUtil;
import java.util.ArrayList;
import java.util.LinkedList;

public class OweAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private ArrayList<V_HeaderForPay> mList;
  private LinkedList<V_HeaderForPay> mListChoose;
  
  public OweAdapter(ArrayList<V_HeaderForPay> paramArrayList)
  {
    this.mList = paramArrayList;
    this.mInflater = LayoutInflater.from(StoreApplication.getContext());
    this.mListChoose = new LinkedList();
  }
  
  private void addChoose(V_HeaderForPay paramV_HeaderForPay)
  {
    if (this.mListChoose.contains(paramV_HeaderForPay))
    {
      this.mListChoose.remove(paramV_HeaderForPay);
      return;
    }
    this.mListChoose.add(paramV_HeaderForPay);
  }
  
  public ArrayList<V_HeaderForPay> getChooseOwe()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= this.mListChoose.size()) {
        return localArrayList;
      }
      localArrayList.add((V_HeaderForPay)this.mListChoose.get(i));
      i += 1;
    }
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
  
  public View getView(int paramInt, View paramView, final ViewGroup paramViewGroup)
  {
    final V_HeaderForPay localV_HeaderForPay;
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903205, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      localV_HeaderForPay = (V_HeaderForPay)this.mList.get(paramInt);
      if (!this.mListChoose.contains(localV_HeaderForPay)) {
        break label148;
      }
      paramViewGroup.cebCheck.setChecked(true);
    }
    for (;;)
    {
      paramViewGroup.txtBill.setText(ResUtil.getFormatString(2131165381, localV_HeaderForPay.getTransNumber()));
      paramViewGroup.txtAssociate.setText(ResUtil.getFormatString(2131165383, localV_HeaderForPay.getRelatedName()));
      paramViewGroup.txtRemain.setText(ResUtil.getFormatString(2131165388, DataUtil.getFormatString(localV_HeaderForPay.getRemainAmount())));
      paramViewGroup.cebCheck.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (OweAdapter.this.mListChoose.size() > 0)
          {
            if ((localV_HeaderForPay.getTransType().equals("PX")) || (localV_HeaderForPay.getTransType().equals("PR")))
            {
              if (localV_HeaderForPay.getSupplier_ID() == ((V_HeaderForPay)OweAdapter.this.mListChoose.get(0)).getSupplier_ID())
              {
                OweAdapter.this.addChoose(localV_HeaderForPay);
                return;
              }
              OweAdapter.ViewHolder.access$0(paramViewGroup).setChecked(false);
              ToastUtil.toastLong(StoreApplication.getContext(), 2131165458);
              return;
            }
            if (localV_HeaderForPay.getVIP_ID() == ((V_HeaderForPay)OweAdapter.this.mListChoose.get(0)).getVIP_ID())
            {
              OweAdapter.this.addChoose(localV_HeaderForPay);
              return;
            }
            OweAdapter.ViewHolder.access$0(paramViewGroup).setChecked(false);
            ToastUtil.toastLong(StoreApplication.getContext(), 2131165458);
            return;
          }
          OweAdapter.this.addChoose(localV_HeaderForPay);
        }
      });
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label148:
      paramViewGroup.cebCheck.setChecked(false);
    }
  }
  
  private class ViewHolder
  {
    private CheckBox cebCheck;
    private TextView txtAssociate;
    private TextView txtBill;
    private TextView txtRemain;
    
    public ViewHolder(View paramView)
    {
      this.txtBill = ((TextView)paramView.findViewById(2131362636));
      this.txtRemain = ((TextView)paramView.findViewById(2131362638));
      this.txtAssociate = ((TextView)paramView.findViewById(2131362637));
      this.cebCheck = ((CheckBox)paramView.findViewById(2131362635));
    }
  }
}