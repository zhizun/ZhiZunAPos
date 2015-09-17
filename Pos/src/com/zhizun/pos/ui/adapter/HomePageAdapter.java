package com.zhizun.pos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.ui.entity.Module;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.PermissionManager;
import java.util.ArrayList;

public class HomePageAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater = LayoutInflater.from(StoreApplication.getContext());
  private ArrayList<Module> mList = PermissionManager.getHomePageModule();
  
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
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903130, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      Module localModule = (Module)this.mList.get(paramInt);
      paramViewGroup.txtTitle.setText(localModule.getModule_Name());
      paramViewGroup.imgIcon.setBackgroundResource(PermissionManager.getHomeModuleIconId(localModule.getModule_Code()));
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