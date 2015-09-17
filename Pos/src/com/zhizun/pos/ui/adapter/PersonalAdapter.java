package com.zhizun.pos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.ResUtil;

public class PersonalAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater = LayoutInflater.from(StoreApplication.getContext());
  private String[] mList = ResUtil.getStringArrays(2131296258);
  
  public int getCount()
  {
    if (this.mList == null) {
      return 0;
    }
    return this.mList.length;
  }
  
  public Object getItem(int paramInt)
  {
    return this.mList[paramInt];
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903130, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      paramViewGroup.txtTitle.setText(this.mList[paramInt]);
      paramViewGroup.imgIcon.setBackgroundResource(ResUtil.getDrawableId(String.format("ic_discover_%d", new Object[] { Integer.valueOf(paramInt) })));
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  private class ViewHolder
  {
    private ImageView imgIcon;
    private TextView txtTitle;
    
    public ViewHolder(View paramView)
    {
      this.txtTitle = ((TextView)paramView.findViewById(2131362423));
      this.imgIcon = ((ImageView)paramView.findViewById(2131362420));
    }
  }
}