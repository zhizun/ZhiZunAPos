package com.zhizun.pos.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.zhizun.pos.interfaces.TopBarFunctional;
import com.zhizun.pos.ui.widget.RadioTopBar;

public abstract class BaseRadioTopBarFragment
  extends Fragment
  implements TopBarFunctional
{
  protected RadioTopBar mTopBar;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903182, null);
    this.mTopBar = ((RadioTopBar)paramLayoutInflater.findViewById(2131362585));
    addBody((RelativeLayout)paramLayoutInflater.findViewById(2131362586));
    return paramLayoutInflater;
  }
}