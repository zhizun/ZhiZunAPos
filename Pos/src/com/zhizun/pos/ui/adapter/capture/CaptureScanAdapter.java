package com.zhizun.pos.ui.adapter.capture;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zizun.cs.common.utils.PreferencesUtil;

public class CaptureScanAdapter
  extends BaseAdapter
{
  public Context context;
  private final String[] data = { "卖货", "进货", "商品", "盘点" };
  private LayoutInflater inflater;
  private int location = 0;
  private SharedPreferences preferences;
  
  public CaptureScanAdapter(Context paramContext)
  {
    this.context = paramContext;
    this.preferences = PreferencesUtil.getPreference(paramContext);
    this.location = this.preferences.getInt("capturescanset", 0);
    this.inflater = LayoutInflater.from(paramContext);
  }
  
  public int getCount()
  {
    return this.data.length;
  }
  
  public Object getItem(int paramInt)
  {
    return this.data[paramInt];
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    if (paramView == null) {
      paramViewGroup = this.inflater.inflate(2130903136, null);
    }
    paramView = (RelativeLayout)paramViewGroup.findViewById(2131362432);
    ((TextView)paramViewGroup.findViewById(2131362433)).setText(this.data[paramInt]);
    if (this.location == paramInt)
    {
      paramView.setBackgroundColor(Color.parseColor("#FF620D"));
      return paramViewGroup;
    }
    paramView.setBackgroundColor(Color.parseColor("#00ff00ff"));
    return paramViewGroup;
  }
  
  public void setLocation(int paramInt)
  {
    this.location = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\adapter\capture\CaptureScanAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */