package com.zhizun.pos.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.zhizun.pos.interfaces.TopBarFunctional;
import com.zhizun.pos.ui.widget.TitleTopBar;

public abstract class BaseTitleTopBarFragment
  extends Fragment
  implements TopBarFunctional
{
  protected TitleTopBar mTopBar;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903191, null);
    this.mTopBar = ((TitleTopBar)paramLayoutInflater.findViewById(2131362597));
    addBody((RelativeLayout)paramLayoutInflater.findViewById(2131362598));
    return paramLayoutInflater;
  }
}