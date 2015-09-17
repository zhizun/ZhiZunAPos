package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yumendian.cs.entities.ProductGroup;
import com.yunmendian.pos.util.ResUtil;
import java.util.ArrayList;

public class GroupSelectAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private ArrayList<ProductGroup> mList;
  private int mRankName;
  private int mSelectIndex = 0;
  
  public GroupSelectAdapter(Context paramContext, ArrayList<ProductGroup> paramArrayList, int paramInt)
  {
    this.mList = paramArrayList;
    this.mRankName = paramInt;
    if (this.mRankName == 0) {
      this.mRankName = 2131165372;
    }
    this.mInflater = LayoutInflater.from(paramContext);
  }
  
  public int getCount()
  {
    if (this.mList == null) {
      return 1;
    }
    return this.mList.size() + 1;
  }
  
  public Object getItem(int paramInt)
  {
    return this.mList.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public long getSelectGroup()
  {
    if (this.mSelectIndex == 0) {
      return 0L;
    }
    return ((ProductGroup)this.mList.get(this.mSelectIndex - 1)).getPG_ID();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903171, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      if (paramInt != 0) {
        break label87;
      }
      paramViewGroup.txtName.setText(this.mRankName);
    }
    for (;;)
    {
      if (paramInt != this.mSelectIndex) {
        break label117;
      }
      paramViewGroup.txtName.setTextColor(ResUtil.getColor(2131427339));
      paramView.setBackgroundColor(ResUtil.getColor(2131427328));
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label87:
      ProductGroup localProductGroup = (ProductGroup)this.mList.get(paramInt - 1);
      paramViewGroup.txtName.setText(localProductGroup.getPG_Name());
    }
    label117:
    paramViewGroup.txtName.setTextColor(ResUtil.getColor(2131427331));
    paramView.setBackgroundColor(ResUtil.getColor(2131427348));
    return paramView;
  }
  
  public void setSelect(int paramInt)
  {
    if (this.mSelectIndex != paramInt)
    {
      this.mSelectIndex = paramInt;
      notifyDataSetChanged();
    }
  }
  
  private class ViewHolder
  {
    private TextView txtName;
    
    public ViewHolder(View paramView)
    {
      this.txtName = ((TextView)paramView.findViewById(2131362552));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\adapter\GroupSelectAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */