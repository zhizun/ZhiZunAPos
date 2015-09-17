package com.zhizun.pos.ui.adapter.capture;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CaptureAdapter
  extends BaseAdapter
{
  public Context context;
  private LayoutInflater inflater;
  private List<Map<String, Object>> list = new ArrayList();
  private int location = 0;
  
  public CaptureAdapter(Context paramContext, List<Map<String, Object>> paramList, int paramInt)
  {
    this.context = paramContext;
    this.list = paramList;
    this.location = paramInt;
    this.inflater = LayoutInflater.from(paramContext);
  }
  
  public int getCount()
  {
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
    paramViewGroup = paramView;
    if (paramView == null) {
      paramViewGroup = this.inflater.inflate(2130903172, null);
    }
    paramView = (TextView)paramViewGroup.findViewById(2131362554);
    ImageView localImageView = (ImageView)paramViewGroup.findViewById(2131362555);
    RelativeLayout localRelativeLayout = (RelativeLayout)paramViewGroup.findViewById(2131362553);
    Map localMap = (Map)this.list.get(paramInt);
    paramView.setText((CharSequence)localMap.get("tv"));
    localImageView.setImageResource(((Integer)localMap.get("iv")).intValue());
    if (this.location == paramInt)
    {
      localRelativeLayout.setBackgroundColor(Color.parseColor("#FF620D"));
      return paramViewGroup;
    }
    localRelativeLayout.setBackgroundColor(Color.parseColor("#02A7D5"));
    return paramViewGroup;
  }
  
  public void setLocation(int paramInt)
  {
    this.location = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\adapter\capture\CaptureAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */