package com.zhizun.pos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zhizun.pos.app.StoreApplication;

public class SimpleTextAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private String[] mListStatus;
  
  public SimpleTextAdapter(String[] paramArrayOfString)
  {
    this.mListStatus = paramArrayOfString;
    this.mInflater = LayoutInflater.from(StoreApplication.getContext());
  }
  
  public int getCount()
  {
    return this.mListStatus.length;
  }
  
  public Object getItem(int paramInt)
  {
    return this.mListStatus[paramInt];
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903218, null);
      paramView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      paramViewGroup.txtName.setText(this.mListStatus[paramInt]);
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  private class ViewHolder
  {
    private TextView txtName;
    
    public ViewHolder(View paramView)
    {
      this.txtName = ((TextView)paramView.findViewById(2131362658));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\adapter\SimpleTextAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */